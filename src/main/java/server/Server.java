package server;

import server.database.Game;
import server.database.GameService;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class Server {
    static ServerSocket serverSocket;
    static Map<String, Socket> clients = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        GameService gameService = new GameService();
        try {
            serverSocket = new ServerSocket(8081);
            System.out.println("server started...");
            while (true) {
                Socket client = serverSocket.accept();
                System.out.println("Client connected " + client.getInetAddress());
                clients.put(UUID.randomUUID().toString(), client);
                if (clients.size() == 2) {
                    List<Socket> pair = new ArrayList<>(clients.values());
                    Thread thread = new Thread(new GameThread(pair.get(0), pair.get(1)));
                    clients = new ConcurrentHashMap<>();
                    thread.start();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
