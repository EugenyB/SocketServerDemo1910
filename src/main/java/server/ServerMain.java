package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerMain {

    List<ClientHandler> handlers = new ArrayList<>();

    public static void main(String[] args) {
        new ServerMain().run();
    }

    private void run() {
        try (ServerSocket serverSocket = new ServerSocket(12345)) {
            while (true) {
                Socket socket = serverSocket.accept();
                ClientHandler handler = new ClientHandler(socket, this);
                handlers.add(handler);
                new Thread(handler).start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized void broadcast(String s) {
        for (ClientHandler handler : handlers) {
            handler.send(s);
        }
    }
}
