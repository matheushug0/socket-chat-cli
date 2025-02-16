package ChatServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientMain {
    public static void main(String[] args) {
        try(Socket socket = new Socket("127.0.0.1", 12345)) {
            System.out.println("Status de conexão com o Servidor: " + socket.isConnected());

            new Thread(new ServerHandler(socket)).start();

            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

            String message;
            while ((message = in.readLine()) != null) {
                writer.println(message);
            }
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static class ServerHandler implements Runnable {
        private Socket socket;

        public ServerHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String message;
                while ((message = reader.readLine()) != null) {
                    System.out.println("Mensagem recebida: " + message);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
