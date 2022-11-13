package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    Connection connection = Util.getConnection();
    Statement statement = connection.createStatement();

    public UserDaoJDBCImpl() throws SQLException {

    }

    public void createUsersTable() {
        try {
            statement.execute("CREATE TABLE IF NOT EXISTS Users (" +
                    "id INT PRIMARY KEY AUTO_INCREMENT, " +
                    "name VARCHAR(100) NOT NULL, " +
                    "lastName VARCHAR(100) NOT NULL, " +
                    "age SMALLINT NOT NULL)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try {
            statement.execute("DROP TABLE IF EXISTS Users");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO Users (name, lastName, age) VALUES (?, ?, ?)");
            ps.setString(1, name);
            ps.setString(2, lastName);
            ps.setByte(3, age);
            ps.executeUpdate();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM Users WHERE Id = ?");
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> allUsers = new ArrayList<>();
        try {
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allUsers;
    }

    public void cleanUsersTable() {
        try {
            statement.executeUpdate("DELETE FROM Users");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
