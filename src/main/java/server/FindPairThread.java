package server;

import java.net.Socket;
import java.nio.channels.SocketChannel;
import java.util.*;
import java.util.stream.Collectors;

import static server.Server.clients;

public class FindPairThread extends Thread{

    private List<Socket> pair;

    @Override
    public void run() {
        while (true) {
            if (clients.size() != 0 && clients.size() % 2 == 0) {
                Map<String, Socket> pairMap = clients
                        .entrySet()
                        .stream()
                        .limit(2)
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
                pair = new ArrayList<>(pairMap.values());
                Iterator<String> iterator = pairMap.keySet().iterator();
                clients.remove(iterator.next());
                clients.remove(iterator.next());
                GameThread gameThread = new GameThread(pair.get(0), pair.get(1));
                Thread thread = new Thread(gameThread);
                thread.start();
            }
        }
    }
}
