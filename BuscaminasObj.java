import java.util.concurrent.ThreadLocalRandom;

public class BuscaminasObj {
    //Atributos
    private int columnaInicial;
    private int columnas;
    private int contadorCasillas;
    private int contadorMinas;
    private int dificultad;
    private int filaInicial;
    private int filas;
    private int minas;
    private int numCasillas;
    private boolean juego;
    private boolean ganar;
    private char[][] tablero;
    private char[][] tableroJug;
    
    //Constructor
    public BuscaminasObj(int dificultad) {
        setDificultad(dificultad);
        setTablero(generarMatrizInicial());
        setTableroJug(generarMatrizInicial());
    }

    //Getters y Setters
    public int getColumnaInicial() {
        return columnaInicial;
    }
    public void setColumnaInicial(int columnaInicial) {
        this.columnaInicial = columnaInicial;
    }
    public int getColumnas() {
        return columnas;
    }
    public void setColumnas(int columnas) {
        this.columnas = columnas;
    }
    public int getContadorCasillas() {
        return contadorCasillas;
    }
    public void setContadorCasillas(int contadorCasillas) {
        this.contadorCasillas = contadorCasillas;
    }
    public int getContadorMinas() {
        return contadorMinas;
    }
    public void setContadorMinas(int contadorMinas) {
        this.contadorMinas = contadorMinas;
    }
    public int getDificultad() {
        return dificultad;
    }
    public void setDificultad(int dificultad) {
        this.dificultad = dificultad;
    }
    public int getFilaInicial() {
        return filaInicial;
    }
    public void setFilaInicial(int filaInicial) {
        this.filaInicial = filaInicial;
    }
    public int getFilas() {
        return filas;
    }
    public void setFilas(int filas) {
        this.filas = filas;
    }
    public int getMinas() {
        return minas;
    }
    public void setMinas(int minas) {
        this.minas = minas;
    }
    public int getNumCasillas() {
        return numCasillas;
    }
    public void setNumCasillas(int numCasillas) {
        this.numCasillas = numCasillas;
    }
    public boolean isJuego() {
        return juego;
    }
    public void setJuego(boolean juego) {
        this.juego = juego;
    }
    public boolean isGanar() {
        return ganar;
    }
    public void setGanar(boolean ganar) {
        this.ganar = ganar;
    }
    public char[][] getTablero() {
        return tablero;
    }
    public void setTablero(char[][] tablero) {
        this.tablero = tablero;
    }
    public char[][] getTableroJug() {
        return tableroJug;
    }
    public void setTableroJug(char[][] tableroJug) {
        this.tableroJug = tableroJug;
    }
      
    //Métodos 
    public char[][] accion(int accion, int fila, int columna) {
        char[][] matrizAux;
        if (accion == 1) {
            matrizAux = ponerBandera(getTableroJug(), fila, columna);
            if(getContadorCasillas() == getNumCasillas() && getMinas() == getContadorMinas()){
                setGanar(true);
                setJuego(false);
            }
            return matrizAux;
        } else if (accion == 2) {
            return quitarBandera(getTableroJug(), fila, columna);
        } else {
            matrizAux = liberarCasilla(getTablero(), getTableroJug(), fila, columna);
            if(matrizAux[fila][columna] == 'M'){
                setJuego(false);
                setGanar(false);
            }
            if(getContadorCasillas() == getNumCasillas() && getMinas() == getContadorMinas()){
                setGanar(true);
                setJuego(false);
            }
            return matrizAux;
        }
    }
    public char[][] generarMatriz() {
        char[][] matrizAux;
        int c = 0;
        if (getDificultad() == 1) { //Principiante
            setMinas(10);
            setContadorMinas(0);
            setFilas(9);
            setColumnas(9);
        }
        if (getDificultad() == 2) { //Intermedio
            setMinas(40);
            setContadorMinas(0);
            setFilas(16);
            setColumnas(16);
        }
        if (getDificultad() == 3) { //Experto
            setMinas(99);
            setFilas(16);
            setContadorMinas(0);
            setColumnas(30);
        }
        setNumCasillas((getColumnas() * getFilas()) - getMinas());

        setTablero(new char[getFilas()][getColumnas()]);
        matrizAux = getTablero();
        //Primero llenar con ceros la matriz
        for (int i = 0; i < getFilas(); i++) {
            for (int j = 0; j < getColumnas(); j++) {
                matrizAux[i][j] = '0';
            }
        }
        //Posicionar las minas en coordenadas aleatorias
        int X, Y;
        while (c < minas) {
            X = ThreadLocalRandom.current().nextInt(0, getFilas());
            Y = ThreadLocalRandom.current().nextInt(0, getColumnas());
            if (matrizAux[X][Y] != 'M' && X != getFilaInicial() && Y != getColumnaInicial()) {
                matrizAux[X][Y] = 'M';
                c++;
            }
        }
        //Poner adyacencias
        int[] x = { -1, -1, -1, 0, 0, 0, 1, 1, 1 };
        int[] y = { -1, 0, 1, -1, 0, 1, -1, 0, 1 };
        for (int i = 0; i < getFilas(); i++) {
            for (int j = 0; j < getColumnas(); j++) {
                if (matrizAux[i][j] == 'M') {
                    for (int k = 0; k < y.length; k++) {
                        if (x[k] + i >= 0 && j + y[k] >= 0 && x[k] + i < getFilas() && j + y[k] < getColumnas()
                                && matrizAux[x[k] + i][y[k] + j] != 'M') {
                            matrizAux[x[k] + i][y[k] + j]++;
                        }
                    }
                }

            }
        }
        return matrizAux;
    }
    public char[][] generarMatrizInicial() {
        if (getDificultad() == 1) { //Principiante
            setFilas(9);
            setColumnas(9);
        }
        if (getDificultad() == 2) { //Intermedio
            setFilas(16);
            setColumnas(16);
        }
        if (getDificultad() == 3) { //Experto
            setFilas(16);
            setColumnas(30);
        }
        char[][] matrizAux = new char[getFilas()][getColumnas()];

        for (int i = 0; i < getFilas(); i++) {
            for (int j = 0; j < getColumnas(); j++) {
                matrizAux[i][j] = '-';
            }
        }
        return matrizAux;
    }
    public String imprimirMatriz(char[][] matriz) {
        String cadena = "";
        cadena = cadena + "-------------------------------------------\n";
        cadena = cadena + "Minas restantes: " + (getMinas() - getContadorMinas()) + "\n";
        cadena = cadena + "Has abierto " + getContadorCasillas() + " casillas ";
        cadena = cadena + "-------------------------------------------\n";
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz.length; j++) {
                cadena = cadena + matriz[i][j] + " | ";
            }
            cadena = cadena + "\n";
        }
        return cadena;
    }
    public char[][] liberarCasilla(char[][] matrizServidor, char[][] matrizCliente, int fila, int columna) {
        int[] x = { -1, -1, -1, 0, 0, 0, 1, 1, 1 };
        int[] y = { -1, 0, 1, -1, 0, 1, -1, 0, 1 };
        if (matrizCliente[fila][columna] != '-') {
            return matrizCliente;
        }
        //Se libera la casilla
        matrizCliente[fila][columna] = matrizServidor[fila][columna];
        setContadorCasillas(getContadorCasillas() + 1);
        if (matrizServidor[fila][columna] != '0') {
            return matrizCliente;
        }
        for (int i = 0; i < x.length; i++) {
            int nuevaFila = fila + x[i];
            int nuevaColumna = columna + y[i];
            if (nuevaFila >= 0 && nuevaFila < matrizServidor.length && nuevaColumna >= 0
                    && nuevaColumna < matrizServidor[0].length) {
                liberarCasilla(matrizServidor, matrizCliente, nuevaFila, nuevaColumna);
            }
        }
        return matrizCliente;
    }
    public char[][] ponerBandera(char[][] matrizCliente, int fila, int columna) {
        if (matrizCliente[fila][columna] == '-') {
            matrizCliente[fila][columna] = '>';
            setContadorMinas(getContadorMinas() + 1);
        }
        return matrizCliente;
    }
    public char[][] quitarBandera(char[][] matrizCliente, int fila, int columna) {
        if (matrizCliente[fila][columna] == '>') {
            matrizCliente[fila][columna] = '-';
            setContadorMinas(getContadorMinas() - 1);
        }
        return matrizCliente;
    }   
}