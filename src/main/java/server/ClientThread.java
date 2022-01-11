package server;

import java.io.*;
import java.util.Scanner;

public class ClientThread implements Runnable {

    private PrintWriter writer;
    private Scanner reader;
    private boolean firstPlayer;
    private GameThread gameThread;
    private boolean work = true;

    ClientThread(InputStream reader, OutputStream writer, boolean firstPlayer, GameThread gameThread) {
        this.firstPlayer = firstPlayer;
        this.reader = new Scanner(reader);
        this.writer = new PrintWriter(writer);
        this.gameThread = gameThread;
    }

    @Override
    public void run() {

        if (firstPlayer){
            writer.println("поехали 1");
        } else {
            writer.println("поехали 2");
        }

        writer.flush();

        while (work) {
            if (reader.hasNext()) {
                String inMes = reader.nextLine();
                if (inMes.equals("##session##end##")){
                    gameThread.endGame(!firstPlayer);
                    inMes = "leave 11";
                    work = false;
                }
                if (inMes.charAt(0) == 'i' && inMes.charAt(1) == 'd'){
                    if (firstPlayer){
                        gameThread.setId1(inMes.substring(3));
                        continue;
                    } else {
                        gameThread.setId2(inMes.substring(3));
                        continue;
                    }
                }
                if (inMes.equals("win 11")){
                    gameThread.endGame(firstPlayer);
                    inMes = "lose 11";
                    work = false;
                }
                writer.println(inMes);
                System.out.println(inMes);
                writer.flush();
            }
        }
    }
}
