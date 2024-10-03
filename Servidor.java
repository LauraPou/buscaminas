import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.PrintWriter;

public class Servidor {
    public static void main(String[] args) {
        boolean resultado;
        Socket client;
        DataInputStream dis;
        DataOutputStream dos;
        String mensaje;
        Scanner input;
        try {
            input = new Scanner(System.in);
            System.out.println("Ingrese el puerto de donde se aceptarán jugadores...");
            int port = input.nextInt();
            ServerSocket socket = new ServerSocket(port);
            socket.setReuseAddress(true); // Reinicia el socket en caso de cerrarse
            System.out.println("-----------------------------------------------");
            System.out.println("|  Servidor iniciado. Esperando jugadores...  |");
            System.out.println("-----------------------------------------------");
            for (;;) {
                client = socket.accept();
                dis = new DataInputStream(client.getInputStream());
                dos = new DataOutputStream(client.getOutputStream());
                System.out.println(
                        "Jugador conectado desde " + client.getInetAddress() + ". Puerto: " + client.getPort());
                dos.writeUTF("----------------------------------------------------\n|  Conectado satisfactoriamente. Puedes jugar! :)  |\n|  Ingresa el nivel de dificultad:                 |\n|    1: Principiante (9 x 9. 10 minas)             |\n|    2: Intermedio (16 x 16. 40 minas)             |\n|    3: Experto (16 x 30. 99 minas)                |\n----------------------------------------------------");
                BuscaminasObj tableroServidor = new BuscaminasObj(dis.readInt());
                System.out.println(tableroServidor.imprimirMatriz(tableroServidor.getTablero()));
                mensaje = dis.readUTF();
                tableroServidor.setFilaInicial(mensaje.charAt(0) - 48);
                tableroServidor.setColumnaInicial((int) mensaje.charAt(2) - 48);
                tableroServidor.setTablero(tableroServidor.generarMatriz());
                System.out.println(tableroServidor.imprimirMatriz(tableroServidor.getTablero()));
                tableroServidor.setJuego(true);
                tableroServidor.setTableroJug(tableroServidor.liberarCasilla(tableroServidor.getTablero(), tableroServidor.getTableroJug(), tableroServidor.getFilaInicial(), tableroServidor.getColumnaInicial()));
                dos.writeUTF(tableroServidor.imprimirMatriz(tableroServidor.getTableroJug()));
                do{
                    mensaje = dis.readUTF();
                    tableroServidor.setTableroJug(tableroServidor.accion(mensaje.charAt(0) - 48, mensaje.charAt(2) - 48, mensaje.charAt(4) - 48));
                    dos.writeUTF(tableroServidor.imprimirMatriz(tableroServidor.getTableroJug()));
                    dos.writeBoolean(tableroServidor.isJuego());
                } while(tableroServidor.isJuego());
                if(tableroServidor.isGanar()){
                    System.out.println("El jugador ganó");
                    dos.writeUTF("Has ganado! :)");
                    resultado = true;
                } else {
                    System.out.println("El jugador perdió");
                    dos.writeUTF("Has perdido! :(");
                    resultado = false;
                }
                tableroServidor.detenerCronometro();
                guardarRecord(tableroServidor.getTiempoms(), resultado);
                System.out.println("Jugador desconectado...");
                dis.close();
                dos.close();
                client.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void guardarRecord(long tiempo, boolean resultado) {
        try (FileWriter fw = new FileWriter("records.txt", true);
            PrintWriter pw = new PrintWriter(fw)) {
            String res;
            long segundos = (tiempo / 1000) % 60;
            long minutos = (tiempo / (1000 * 60)) % 60;
            long horas = (tiempo / (1000 * 60 * 60)) % 24;
            String tiempoFormato = String.format("%02d:%02d:%02d", horas, minutos, segundos);
            if (resultado == true){
                res = "Ganó";
            }else{
                res = "Perdió";
            }
            pw.println("Resultado: " + res + ", Tiempo: " + tiempoFormato);
            System.out.println("Registro guardado... \nTiempo: " + tiempoFormato);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}