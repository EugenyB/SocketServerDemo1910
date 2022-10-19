package server;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Closeable, Runnable {

    private final ServerMain main;
    private Socket socket;

    private PrintWriter out;
    private BufferedReader in;


    public ClientHandler(Socket socket, ServerMain main) throws IOException {
        this.main = main;
        this.socket = socket;
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public void close() {
        try {
            if (socket!=null) socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        try {
            out.println("Hello from server");
            String line;
            while ((line = in.readLine()) != null && !line.isBlank()) {
                //out.println(">>> " + line);
                main.broadcast(">>> " + line);
            }
            close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void send(String s) {
        out.println(s);
    }
}
