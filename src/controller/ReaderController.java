package controller;

import model.Book;
import model.Reader;
import util.BookStatus;
import util.SQLConnection;

import java.sql.*;
import java.util.ArrayList;

public class ReaderController implements IController<Reader> {

    @Override
    public void add(Reader reader) {
        String sql = "INSERT INTO Reader(name,class,email,phoneNumber) VALUES(?,?,?,?)";
        String newName = reader.getName();
        String newClass = reader.get_class();
        String newEmail = reader.getEmail();
        String newPhoneNumber = reader.getPhoneNumber();

        try (Connection connection = SQLConnection.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, newName);
            preparedStatement.setString(2, newClass);
            preparedStatement.setString(3, newEmail);
            preparedStatement.setString(4, newPhoneNumber);
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
    }

    @Override
    public ArrayList<Reader> getAll() {
        String sql = "SELECT * FROM Reader";
        ArrayList<Reader> readers = new ArrayList<>();

        try (Connection connection = SQLConnection.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String _class = resultSet.getString("class");
                String email = resultSet.getString("email");
                String phoneNumber = resultSet.getString("phoneNumber");

                Reader newReader = new Reader(id, name, _class, email, phoneNumber);
                readers.add(newReader);
            }
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }

        return readers;
    }

    @Override
    public ArrayList<Reader> findRowsWithString(String value, String label) {
        String sql = "SELECT * FROM Reader WHERE " + label + " = ?";
        ArrayList<Reader> readers = new ArrayList<>();

        try (Connection connection = SQLConnection.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, value);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String _class = resultSet.getString("class");
                String email = resultSet.getString("email");
                String phoneNumber = resultSet.getString("phoneNumber");

                Reader newReader = new Reader(id, name, _class, email, phoneNumber);
                readers.add(newReader);
            }

        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }

        return readers;
    }

    @Override
    public ArrayList<Reader> findRowsWithInt(int value, String label) {
        String sql = "SELECT * FROM Reader WHERE " + label + " = ?";
        ArrayList<Reader> readers = new ArrayList<>();

        try (Connection connection = SQLConnection.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, value);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String _class = resultSet.getString("class");
                String email = resultSet.getString("email");
                String phoneNumber = resultSet.getString("phoneNumber");

                Reader newReader = new Reader(id, name, _class, email, phoneNumber);
                readers.add(newReader);
            }

        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }

        return readers;
    }
}
