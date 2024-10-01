import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) {
        int port = 8000;
        String adress = "127.0.0.1";
        String mensaje;
        DataInputStream dis;
        DataOutputStream dos;
        Scanner input;
        boolean juego;

        try {
            Socket client = new Socket(adress, port);
            input = new Scanner(System.in);
            dis = new DataInputStream(client.getInputStream());
            dos = new DataOutputStream(client.getOutputStream());
            
            System.out.println(dis.readUTF());
            int dificultad = input.nextInt();
            dos.writeInt(dificultad);
            input.nextLine();
            System.out.println("Ingresa la primera casilla que abrirás (fila) (columna)");
            mensaje = input.nextLine();
            dos.writeUTF(mensaje);
            mensaje = dis.readUTF();
            System.out.println(mensaje);
            juego = true;
            do{
                System.out.println("Ingresa la acción que quieres realizar:");
                System.out.println("1: Colocar bandera");
                System.out.println("2: Quitar bandera");
                System.out.println("3: Abrir casilla");
                mensaje = input.nextLine() + " ";
                System.out.println("Ingresa la casilla (fila) (columna)");
                mensaje = mensaje + input.nextLine();
                dos.writeUTF(mensaje);
                mensaje = dis.readUTF();
                System.out.println(mensaje);
                juego = dis.readBoolean();
            } while(juego);
            mensaje = dis.readUTF();
            System.out.println(mensaje);

            dos.close();
            dis.close();
            client.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}