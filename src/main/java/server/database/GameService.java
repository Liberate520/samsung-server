package server.database;

import server.database.Game;
import server.database.GameDao;

public class GameService {
    private GameDao gameDao = new GameDao();

    public GameService() {

    }

    public void save(Game game) {
        gameDao.save(game);
    }
}
