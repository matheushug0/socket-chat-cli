package ChatServer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class ServerMain {
    private static final Set<PrintWriter> clients = new HashSet<>();

    public static void main(String[] args) {
        System.out.println("Servidor de Chat - Iniciado...");

        try (ServerSocket serverSocket = new ServerSocket(12345)) {
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Servidor de Chat - Novo Cliente Conectado!");
                new Thread(new ClientHandler(socket)).start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static class ClientHandler implements Runnable {
        private Socket socket;
        private PrintWriter writer;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                InputStream input = socket.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));

                OutputStream output = socket.getOutputStream();
                writer = new PrintWriter(output, true);

                synchronized (clients) {
                    clients.add(writer);
                }

                String message;
                while ((message = reader.readLine()) != null) {
                    System.out.println("Mensagem: " +  message);

                    for (PrintWriter client : clients) {
                        if(client != writer) {
                            client.println(message);
                        }
                    }
                }

            }catch (IOException e){
                e.printStackTrace();
            }finally {
                synchronized (clients) {
                    clients.remove(writer);
                }

                try {
                    socket.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }
}
