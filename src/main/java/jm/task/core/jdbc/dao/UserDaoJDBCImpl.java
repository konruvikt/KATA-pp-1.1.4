package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() throws SQLException {
        Connection connection = Util.getConnection();
        try (Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS Users (" +
                    "id INT PRIMARY KEY AUTO_INCREMENT, " +
                    "name VARCHAR(100) NOT NULL, " +
                    "lastName VARCHAR(100) NOT NULL, " +
                    "age SMALLINT NOT NULL)");
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
        }
    }

    public void dropUsersTable() throws SQLException {
        Connection connection = Util.getConnection();
        try (Statement statement = connection.createStatement()) {
            statement.execute("DROP TABLE IF EXISTS Users");
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
        }
    }

    public void saveUser(String name, String lastName, byte age) throws SQLException {
        Connection connection = Util.getConnection();
        try (PreparedStatement ps = connection.prepareStatement("INSERT INTO Users (name, lastName, age) VALUES (?, ?, ?)")) {
            ps.setString(1, name);
            ps.setString(2, lastName);
            ps.setByte(3, age);
            ps.executeUpdate();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
        }
    }

    public void removeUserById(long id) throws SQLException {
        Connection connection = Util.getConnection();
        try (PreparedStatement ps = connection.prepareStatement("DELETE FROM Users WHERE Id = ?")) {
            ps.setLong(1, id);
            ps.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
        }
    }

    public List<User> getAllUsers() throws SQLException {
        List<User> allUsers = new ArrayList<>();
        Connection connection = Util.getConnection();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Users");
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                allUsers.add(user);
                System.out.println(user);
            }
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
        }
        return allUsers;
    }

    public void cleanUsersTable() throws SQLException {
        Connection connection = Util.getConnection();
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("DELETE FROM Users");
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
        }
    }
}
