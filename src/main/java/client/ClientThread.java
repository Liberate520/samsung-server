package client;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;

import static client.Utils.read;

public class ClientThread extends Thread {

    Socket socket;
    InputStream reader;
    OutputStream writer;

    @Override
    public void run() {

        try {
            socket = new Socket("localhost", 8080);
            reader = socket.getInputStream();
            writer = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scanner sc = new Scanner(System.in);
        byte[] buffer = new byte[4096];
        Thread write = new Thread(() -> {
            try {
                while (true) {
                    writer.write(Utils.write(new Packet(sc.nextLine())));
                    writer.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        Thread read = new Thread(() -> {
            try {
                while (true) {
                    reader.read(buffer);
                    System.out.println(read(buffer).getA());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        read.start();
        write.start();
        try {
            write.join();
            read.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
