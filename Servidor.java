import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Servidor {
    public static void main(String[] args) {
        try {
            Socket client;
            Scanner input = new Scanner(System.in);
            System.out.println("Ingrese el puerto de donde se aceptarán jugadores...");
            int port = input.nextInt();
            ServerSocket socket = new ServerSocket(port);
            socket.setReuseAddress(true); // Reinicia el socket en caso de cerrarse
            System.out.println("-----------------------------------------------");
            System.out.println("|  Servidor iniciado. Esperando jugadores...  |");
            System.out.println("-----------------------------------------------");
            for (;;) {
                client = socket.accept();
                System.out.println("Jugador conectado desde " + client.getInetAddress() + ". Puerto: " + client.getPort());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}