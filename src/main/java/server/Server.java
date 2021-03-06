package server;

import server.database.Game;
import server.database.GameService;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Server {
    static ServerSocket serverSocket;
    static Map<String, Socket> clients = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        GameService gameService = new GameService();
        Scanner in;
        try {
            serverSocket = new ServerSocket(8081);
            System.out.println("server started...");
            while (true) {
                Socket client = serverSocket.accept();
                System.out.println("Client connected " + client.getInetAddress());
                in = new Scanner(client.getInputStream());
                String androidID = "";
                System.out.println("точка 1");
                if (in.hasNext()) {
                    androidID = in.nextLine();
                    System.out.println(androidID);
                    if (androidID.split(" ")[0].equals("stat")) {
                        PrintWriter printWriter = new PrintWriter(client.getOutputStream());
                        int[] stat = gameService.readStat(androidID.split(" ")[1]);
                        printWriter.println("stat " + stat[0] + " " + stat[1]);
                        printWriter.flush();
                        printWriter.close();
                        in.close();
                        client.close();
                        continue;
                    }
                }

                System.out.println("точка 2");

                clients.put(androidID.split(" ")[1], client);
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
