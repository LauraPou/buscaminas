import java.net.Socket;

public class Cliente {
    public static void main(String[] args) {
        try {
            int port = 8000;
            String adress = "127.0.0.1";
            Socket client = new Socket(adress, port);
            System.out.println("----------------------------------------------------");
            System.out.println("|  Conectado satisfactoriamente. Puedes jugar! :)  |");
            System.out.println("|  Ingresa el nivel de dificultad:                 |");
            System.out.println("|    1: Principiante (9 x 9. 10 minas)             |");
            System.out.println("|    2: Intermedio (16 x 16. 40 minas)             |");
            System.out.println("|    3: Experto (16 x 30. 99 minas)                |");

            client.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}