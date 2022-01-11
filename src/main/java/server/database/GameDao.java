package server.database;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class GameDao {

    public void save(Game game) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.save(game);
        tx.commit();
        session.close();
    }
}
