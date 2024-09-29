import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class PruebaBuscaminas {
    public static void main (String args[]){
        
        int[][] matriz =generarMatriz(9);
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
        int minas = 0, n = 0, m = 0;
        
        int[][] matriz = {  {2, 0, 0, 0, 0, 0, 0, 0, 2},
                            {0, 0, 0, 0, 0, 0, 0, 0, 0},
                            {0, 0, 0, 0, 0, 0, 0, 0, 0},
                            {0, 0, 0, 0, 0, 0, 0, 0, 0},
                            {0, 0, 0, 0, 2, 0, 0, 0, 0},
                            {0, 0, 0, 0, 0, 0, 0, 0, 0},
                            {0, 0, 0, 0, 0, 0, 0, 0, 0},
                            {0, 0, 0, 0, 0, 0, 0, 0, 0},
                            {2, 0, 0, 0, 0, 0, 0, 0, 2}

        };
        //imprimirMatriz(matriz);
        int x=8,y=0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
               if( x+i >= 0 &&  j+y >= 0 && x+i<9 && j+y<9 && matriz[x+i][y+j]!=2){
                    matriz[x+i][y+j]++;
               }
               
            }
        }
        
        
        
        return matriz;
    }

}

