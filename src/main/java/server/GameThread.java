package server;

import server.database.Game;
import server.database.GameService;

import java.io.*;
import java.net.Socket;

public class GameThread implements Runnable {

    private Socket firstClient;
    private Socket secondClient;
    private GameService gameService = new GameService();

    private boolean saved;
    private String id1, id2;

    public GameThread(Socket firstClient, Socket secondClient) {
        this.firstClient = firstClient;
        this.secondClient = secondClient;
        saved = false;
    }

    @Override
    public void run() {

        OutputStream firstClientWriter = null;
        OutputStream secondClientWriter = null;
        InputStream firstClientReader = null;
        InputStream secondClientReader = null;
        System.out.println("game starts...");
        try {
            firstClientWriter = firstClient.getOutputStream();
            secondClientWriter = secondClient.getOutputStream();
            firstClientReader = firstClient.getInputStream();
            secondClientReader = secondClient.getInputStream();

            Thread first = new Thread(new ClientThread(secondClientReader, firstClientWriter, true, this));
            Thread second = new Thread(new ClientThread(firstClientReader, secondClientWriter, false, this));
            first.start();
            second.start();

            first.join();
            second.join();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                firstClientWriter.close();
                firstClientReader.close();
                secondClientWriter.close();
                secondClientReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void endGame(boolean firstPlayer){
        if (saved){
            return;
        }
        saved = true;
        System.out.println("end of the game...");
        Game game = new Game();
        game.setFirstPlayer(id1);
        game.setSecondPlayer(id2);
        if (firstPlayer){
            game.setWinner(id1);
        } else {
            game.setWinner(id2);
        }
        try {
            gameService.save(game);
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("не удалось сделать запись в базу");
        }
    }

    public void setId1(String id1) {
        this.id1 = id1;
    }

    public void setId2(String id2) {
        this.id2 = id2;
    }
}
