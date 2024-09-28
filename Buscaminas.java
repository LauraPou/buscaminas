import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Buscaminas {
    public static void main (String args[]){
        Scanner entrada = new Scanner(System.in);
        int n;

        // Nivel principiante: tablero de 9 × 9 casillas y 10 minas. 
        System.out.println("Ingresa el nivel del juego:\n1 Principiante\n2 Intermedio\n3 Experto");
        n = entrada.nextInt();
        int[][] matriz =generarMatriz(n);
        imprimirMatriz(matriz);
        
    }


    public static void imprimirMatriz(int[][] matriz){
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz.length; j++) {
                System.out.print(matriz[i][j]+"    ");
            }
            System.out.println("\n");
        }
    }

    public static int[][] generarMatriz(int nivel){
        int minas = 0, n = 0, m = 0, x, y;
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
        int[][] matriz = new int[n][m];
        // primero llenar con ceros la matriz
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                matriz[i][j] = 0;
            }
        }
        // posicionar las minas en coordenadas aleatorias
        for (int i = 0; i < minas; i++) {
            x = ThreadLocalRandom.current().nextInt(0, n);
            y = ThreadLocalRandom.current().nextInt(0, m);
            for (int j = 0; j < minas; j++) {
                matriz[x][y] = -1;
                if(x>0 && y>0 && x<n-1 && y<m-1){
                    matriz[x-1][y-1] += 1;
                    matriz[x-1][y] += 1;
                    matriz[x-1][y+1] += 1;
                    matriz[x][y-1] += 1;
                    matriz[x][y+1] += 1;
                    matriz[x+1][y-1] += 1;
                    matriz[x+1][y] += 1;
                    matriz[x+1][y+1] += 1; 
            
                }
                
                
            }
        }


        return matriz;
    }

}

