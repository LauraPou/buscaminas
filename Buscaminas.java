import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Buscaminas {
    public static void main (String args[]){
        Scanner entrada = new Scanner(System.in);
        int n;

        // Nivel principiante: tablero de 9 × 9 casillas y 10 minas. 
        System.out.println("Ingresa el nivel del juego:\n1 Principiante\n2 Intermedio\n3 Experto");
        n = entrada.nextInt();
        char[][] matriz =generarMatriz(n);
        imprimirMatriz(matriz);
        
    }


    public static void imprimirMatriz(char[][] matriz){
        System.out.println("-------------------------------------------\n");
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz.length; j++) {
                System.out.print(matriz[i][j]+" | ");
            }                                 
            System.out.println("\n");
        }
    }

    public static char[][] generarMatriz(int nivel){
        int minas = 0, n = 0, m = 0, c = 0;
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
        char[][] matriz = new char[n][m];

        // primero llenar con ceros la matriz
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                matriz[i][j] = '0';
            }
        }
        imprimirMatriz(matriz);
        // posicionar las minas en coordenadas aleatorias
        int X,Y;
        while ( c < minas) {
            X = ThreadLocalRandom.current().nextInt(0, n);
            Y = ThreadLocalRandom.current().nextInt(0, m);
            if( matriz[X][Y] != 'M' ){
                matriz[X][Y] = 'M';
                c++;
            }
        }
        imprimirMatriz(matriz);
        int nMinas=0;
        // poner adyacencias
        int[] x = {-1, -1, -1, 0, 0, 0, 1, 1, 1};
        int[] y = {-1, 0, 1, -1, 0, 1, -1, 0, 1};
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                    if(matriz[i][j] == 'M'){
                        nMinas++;
                        for (int k = 0; k < y.length; k++) {
                            if( x[k]+i >= 0 &&  j+y[k] >= 0 && x[k]+i < 9 && j+y[k] < 9 && matriz[x[k]+i][y[k]+j] != 'M'){
                                matriz[x[k]+i][y[k]+j]++;
                        }
                    }
                }

            }
        }
        System.out.println("minas encontradas: "+ nMinas);
        
        return matriz;
    }

}

