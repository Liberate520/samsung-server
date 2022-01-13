package server.database;

import server.database.Game;
import server.database.GameDao;

import java.util.List;

public class GameService {
    private GameDao gameDao = new GameDao();

    public GameService() {

    }

    public void save(Game game) {
        gameDao.save(game);
    }

    public int[] readStat(String androidID){
        return gameDao.readStat(androidID);
    }
}
