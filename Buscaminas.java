import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Buscaminas {
    public static int contadorCasillas = 0;
    public static void main (String args[]){
        Scanner entrada = new Scanner(System.in);
        int nivel;
        System.out.println("Ingresa el nivel del juego:\n1 Principiante\n2 Intermedio\n3 Experto");
        nivel = entrada.nextInt();
        iniciarJuego(nivel);
    }

    public static void iniciarJuego(int nivel){
        int minas = 0, contadorMinas = 0, n = 0, m = 0, numCasillas = 0;
        if(nivel == 1){ // principiante
            minas = 10;
            n = m = 9;
        }
        if(nivel == 2){ // intermedio
            minas = 40;
            n = m = 16;
        }
        if(nivel == 3){ // experto
            minas = 99;
            n = 16;
            m = 30;
        }
        numCasillas = (m*n) - minas;
        boolean enJuego = true, ganar = false, perder = false;
        Scanner entrada = new Scanner(System.in);
        int fila=0, columna=0, movimiento;
        //  se genera una matriz solo para mostrar las casillas tapadas al inicio
        char[][] matrizCliente = generarMatriz(nivel, fila, columna, minas, n ,m);
        for (int i = 0; i < matrizCliente.length; i++) {
            for (int j = 0; j < matrizCliente.length; j++) {
                matrizCliente[i][j]='-';
            }
        }
        imprimirMatriz(matrizCliente, minas);
        System.out.println("Indica la coordenada inicial: (fila) (columna), ejemplo: 0 2 (columna=0, fila=2)");
        fila = entrada.nextInt();
        columna = entrada.nextInt();
        char[][] matrizServidor = generarMatriz(nivel, fila, columna, minas, n, m);
        imprimirMatriz(matrizServidor, minas);
        matrizCliente = liberarCasilla(matrizServidor, matrizCliente, fila, columna);
        imprimirMatriz(matrizCliente, minas);
        do{
            System.out.println("¿Qué quieres hacer?\n1 Poner bandera\n2 Quitar bandera\n3 Abrir casilla");
            movimiento = entrada.nextInt();
            System.out.println("Indica la coordenada: (fila) (columna)");
            fila = entrada.nextInt();
            columna = entrada.nextInt();
            switch (movimiento) {
                case 1:
                    if(contadorMinas < minas){
                        matrizCliente = ponerBandera(matrizCliente, fila, columna);
                        contadorMinas++;
                        imprimirMatriz(matrizCliente, minas-contadorMinas);
                    }
                    
                    break;
                case 2:
                    matrizCliente = quitarBandera(matrizCliente, fila, columna);
                    contadorMinas--;
                    imprimirMatriz(matrizCliente,minas-contadorMinas);
                    break;
                case 3:
                    matrizCliente = liberarCasilla(matrizServidor, matrizCliente, fila, columna);
                    imprimirMatriz(matrizCliente,minas-contadorMinas);
                    if(matrizCliente[fila][columna] == 'M'){
                        enJuego = false;
                        perder = true;
                        System.out.println("Has perdido :(");
                    }
                    contadorCasillas++;
                    if(contadorCasillas+1 == numCasillas){
                        enJuego = false;
                        ganar = true;
                        System.out.println("Has ganado :)");
                    }
                    break;
            
                default:
                    break;
            }
        }while(enJuego == true);
        
        


    }

    public static char[][] ponerBandera(char[][] matrizCliente, int fila, int columna){
        if(matrizCliente[fila][columna]=='-'){
            matrizCliente[fila][columna] = '>';
        }
        return matrizCliente;
    }

    public static char[][] quitarBandera(char[][] matrizCliente, int fila, int columna){
        if(matrizCliente[fila][columna]=='>'){
            matrizCliente[fila][columna] = '-';
        }
        return matrizCliente;
    }

    public static char[][] liberarCasilla(char[][] matrizServidor,char[][] matrizCliente, int fila, int columna){
        int[] x = {-1, -1, -1, 0, 0, 0, 1, 1, 1};
        int[] y = {-1, 0, 1, -1, 0, 1, -1, 0, 1};
        if(matrizCliente[fila][columna]!='-'){return matrizCliente;}
        // se libera la casilla
        matrizCliente[fila][columna] = matrizServidor[fila][columna];
        contadorCasillas ++;
        if (matrizServidor[fila][columna] != '0') {return matrizCliente;}
        for(int i = 0; i < x.length; i++) {
            int nuevaFila = fila + x[i];
            int nuevaColumna = columna + y[i];
            if (nuevaFila >= 0 && nuevaFila < matrizServidor.length && nuevaColumna >= 0 && nuevaColumna < matrizServidor[0].length) {
                liberarCasilla(matrizServidor, matrizCliente, nuevaFila, nuevaColumna);
            }
        }
        return matrizCliente;
    }

    public static void imprimirMatriz(char[][] matriz, int minas){
        System.out.println("-------------------------------------------\n");
        System.out.println("Minas restantes: " + minas);
        System.out.println("-------------------------------------------\n");
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz.length; j++) {
                System.out.print(matriz[i][j]+" | ");
            }                                 
            System.out.println("\n");
        }
    }

    public static char[][] generarMatriz(int nivel, int fila, int columna, int minas, int n, int m){
        int c = 0;
        char[][] matriz = new char[n][m];
        // primero llenar con ceros la matriz
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                matriz[i][j] = '0';
            }
        }
        // posicionar las minas en coordenadas aleatorias
        int X,Y;
        while ( c < minas) {
            X = ThreadLocalRandom.current().nextInt(0, n);
            Y = ThreadLocalRandom.current().nextInt(0, m);
            if( matriz[X][Y] != 'M' && X!=fila && Y!=columna ){
                matriz[X][Y] = 'M';
                c++;
            }
        }
        // poner adyacencias
        int[] x = {-1, -1, -1, 0, 0, 0, 1, 1, 1};
        int[] y = {-1, 0, 1, -1, 0, 1, -1, 0, 1};
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                    if(matriz[i][j] == 'M'){
                        for (int k = 0; k < y.length; k++) {
                            if( x[k]+i >= 0 &&  j+y[k] >= 0 && x[k]+i < n && j+y[k] < m && matriz[x[k]+i][y[k]+j] != 'M'){
                                matriz[x[k]+i][y[k]+j]++;
                        }
                    }
                }

            }
        }
        return matriz;
    }

    

}

