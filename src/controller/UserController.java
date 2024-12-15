package controller;

import model.User;
import util.HashUtil;
import util.SQLConnection;

import javax.management.relation.Role;
import java.sql.*;
import java.util.ArrayList;

public class UserController {
    public int login(String username, String password) throws RuntimeException {
        String sql = "SELECT UserID, Username, Password FROM User WHERE Username= ? AND Password= ?";

        try (Connection connection = SQLConnection.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, HashUtil.SHA256(password));
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.isBeforeFirst()) {
                return resultSet.getInt("UserID");
            } else {
                return 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getLatestID() {
        String sql = "SELECT UserID FROM User ORDER BY UserID DESC LIMIT 1";

        try (Connection connection = SQLConnection.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            return resultSet.getInt("UserID");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean register(String username, String password, String email) throws RuntimeException{
        String sql = "INSERT INTO User (Username, Password, Email, Role) VALUES(?, ?, ?, 'User')";

        try (Connection connection = SQLConnection.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, HashUtil.SHA256(password));
            statement.setString(3, email);

            if (!exist(username)) {
                statement.executeUpdate();
                return true;
            }
            return false;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User getUser(int id) {
        String sql = "SELECT * FROM User WHERE UserID = ?";

        try (Connection connection = SQLConnection.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            String name = resultSet.getString("Username");
            String email = resultSet.getString("Email");
            String role = resultSet.getString("Role");

            User newUser = new User(id, name, email, role);
            return newUser;

        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    public ArrayList<User> getUsers() {
        String sql = "SELECT * FROM User";
        ArrayList<User> users = new ArrayList<>();

        try (Connection connection = SQLConnection.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                int id = resultSet.getInt("UserID");
                String name = resultSet.getString("Username");
                String email = resultSet.getString("Email");
                String role = resultSet.getString("Role");

                User newUser = new User(id, name, email, role);
                users.add(newUser);
            }
            return users;

        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    public void addUser(String username, String email) {
        String sql = "INSERT INTO User ('Username', 'Email', 'Role') VALUES(?, ?, 'Reader')";

        try (Connection connection = SQLConnection.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, email);

            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    public ArrayList<User> getStaffs() {
        String sql = "SELECT * FROM User WHERE Role = 'Staff'";
        ArrayList<User> users = new ArrayList<>();

        try (Connection connection = SQLConnection.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                int id = resultSet.getInt("UserID");
                String name = resultSet.getString("Username");
                String email = resultSet.getString("Email");
                String role = resultSet.getString("Role");

                User newUser = new User(id, name, email, role);
                users.add(newUser);
            }
            return users;

        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    public void updateUser(int id, String username, String email, String role) {
        String sql = "UPDATE User SET Username=?, Email=?, Role=? WHERE UserID=?";

        try (Connection connection = SQLConnection.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, role);
            preparedStatement.setInt(4, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteUser(int id) {
        String sql = "DELETE FROM User WHERE UserID = ?";
        String sql2 = "DELETE FROM RentingRecord WHERE UserID = ?";

        try (Connection connection = SQLConnection.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

            PreparedStatement preparedStatement2 = connection.prepareStatement(sql2);
            preparedStatement2.setInt(1, id);
            preparedStatement2.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<User> getAdmins() {
        String sql = "SELECT * FROM User WHERE Role = 'Admin'";
        ArrayList<User> users = new ArrayList<>();

        try (Connection connection = SQLConnection.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                int id = resultSet.getInt("UserID");
                String name = resultSet.getString("Username");
                String email = resultSet.getString("Email");
                String role = resultSet.getString("Role");

                User newUser = new User(id, name, email, role);
                users.add(newUser);
            }
            return users;

        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    public ArrayList<User> getReaders() {
        String sql = "SELECT * FROM User WHERE Role = 'Reader'";
        ArrayList<User> users = new ArrayList<>();

        try (Connection connection = SQLConnection.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                int id = resultSet.getInt("UserID");
                String name = resultSet.getString("Username");
                String email = resultSet.getString("Email");
                String role = resultSet.getString("Role");

                User newUser = new User(id, name, email, role);
                users.add(newUser);
            }
            return users;

        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    public boolean exist(String username) {
        String sql = "SELECT * FROM User WHERE Username=?";

        try (Connection connection = SQLConnection.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            return resultSet.isBeforeFirst();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
