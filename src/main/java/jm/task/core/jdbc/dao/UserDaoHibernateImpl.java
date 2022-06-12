package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

import static jm.task.core.jdbc.util.Util.getSessionFactory;

public class UserDaoHibernateImpl implements UserDao {

    private Session session = null;
    private Transaction transaction = null;

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        session = getSessionFactory().openSession();
        transaction = session.beginTransaction();
        session.createSQLQuery("CREATE TABLE IF NOT EXISTS users (id INT PRIMARY KEY AUTO_INCREMENT," +
                "name VARCHAR(25), lastName VARCHAR(25), age INT);").executeUpdate();
        transaction.commit();
        session.close();
    }

    @Override
    public void dropUsersTable() {
        session = getSessionFactory().openSession();
        transaction = session.beginTransaction();
        session.createSQLQuery("DROP TABLE IF EXISTS users").executeUpdate();
        transaction.commit();
        session.close();

    }


    @Override
    public void saveUser(String name, String lastName, byte age) {
        session = getSessionFactory().openSession();
        transaction = session.beginTransaction();
        session.save(new User(name, lastName, age));
        transaction.commit();
        session.close();
    }

    @Override
    public void removeUserById(long id) {
        session = getSessionFactory().openSession();
        transaction = session.beginTransaction();
        session.delete(session.get(User.class, id));
        transaction.commit();
        session.close();
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = null;
        session = getSessionFactory().openSession();
        transaction = session.beginTransaction();
        userList = session.createQuery("from User").getResultList();
        session.close();
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        session = getSessionFactory().openSession();
        transaction = session.beginTransaction();
        session.createQuery("delete User").executeUpdate();
        transaction.commit();
        session.close();
    }
}