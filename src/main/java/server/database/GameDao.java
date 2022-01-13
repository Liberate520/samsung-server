package server.database;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class GameDao {

    public void save(Game game) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.save(game);
        tx.commit();
        session.close();
    }

    public int[] readStat(String androidID){
        int[] stat = new int[2];
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        String q = "from Game where winner = '"+androidID+"'";
        System.out.println(q);
        Query query = session.createQuery(q);
        List list = query.list();
        System.out.println(list.size());
        stat[0] = list.size();
        q = "from Game where (firstPlayer = '"+androidID+"' or secondPlayer = '"+androidID+"') and winner != '"+androidID+"'";
        query = session.createQuery(q);
        List list1 = query.list();
        System.out.println(list1.size());
        stat[1] = list1.size();
        session.close();
        return stat;
    }
}
