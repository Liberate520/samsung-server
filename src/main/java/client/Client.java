package client;

public class Client {
    public static void main(String[] args) {
        ClientThread clientThread = new ClientThread();
        clientThread.start();
    }
}
