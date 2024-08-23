package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.*;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        String sql = "CREATE TABLE IF NOT EXISTS Users " +
                "(id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                "name VARCHAR(50) NOT NULL, lastName VARCHAR(50) NOT NULL, " +
                "age TINYINT NOT NULL)";

        Query query = session.createSQLQuery(sql).addEntity(User.class);

        transaction.commit();
        session.close();
    }

    @Override
    public void dropUsersTable() {
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        String sql = "DROP TABLE IF EXISTS Users";

        Query query = session.createSQLQuery(sql).addEntity(User.class);

        transaction.commit();
        session.close();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {

        try {
            Session session = Util.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();

            String sql = "saveUpdate Users" + "SET name = :name"
                + ", lastName = :lastName" + ", age = :age";

            Query query = session.createSQLQuery(sql).addEntity(User.class);

            query.setParameter("name", name)
                .setParameter("lastName", lastName)
                .setParameter("age", age)
                .executeUpdate();
            transaction.commit();
            session.close();
        } catch (Exception e) {
            System.out.println("Ошибка при добавлении User в базу данных");
        }

    }

    @Override
    public void removeUserById(long id) {
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        String sql = "DELETE FROM Users WHERE id = :id";

        Query query = session.createSQLQuery(sql).addEntity(User.class);
        query.setParameter("id", id).executeUpdate();

        transaction.commit();
        session.close();

    }

    @Override
    public List<User> getAllUsers() {
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        String sql = "SELECT * FROM Users";

        Query query = session.createNativeQuery(sql).addEntity(User.class);
        List<User> userList = query.list();

        transaction.commit();
        session.close();
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();

            String sql = "TRUNCATE Users";

            Query query = session.createSQLQuery(sql).addEntity(User.class);
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            System.out.println( "Unable to truncate the targetted file.");
        } finally {
            session.close();
        }

    }
}
