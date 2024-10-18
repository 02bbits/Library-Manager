package controller;
import model.Book;
import util.BookStatus;
import util.SQLConnection;

import java.sql.*;
import java.util.ArrayList;

public class BookController implements IController<Book> {
    @Override
    public void add(Book book) {
        String sql = "INSERT INTO Book(name,author,publisher,status) VALUES(?,?,?,?)";
        String newName = book.getName();
        String newAuthor = book.getAuthor();
        String newPublisher = book.getPublisher();
        int newStatus = book.getStatus();

        try (Connection connection = SQLConnection.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, newName);
            preparedStatement.setString(2, newAuthor);
            preparedStatement.setString(3, newPublisher);
            preparedStatement.setInt(4, newStatus);
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
    }

    @Override
    public ArrayList<Book> getAll() {
        String sql = "SELECT * FROM Book";
        ArrayList<Book> books = new ArrayList<>();

        try (Connection connection = SQLConnection.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String author = resultSet.getString("author");
                String publisher = resultSet.getString("publisher");
                int status = resultSet.getInt("status");
                status = (status == 1) ? BookStatus.AVAILABLE : BookStatus.NOT_AVAILABLE;

                Book newBook = new Book(id, name, author, publisher, status);
                books.add(newBook);
            }
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }

        return books;
    }

    @Override
    public ArrayList<Book> findRowsWithString(String value, String label) {
        String sql = "SELECT * FROM Book WHERE " + label + " = ?";
        ArrayList<Book> books = new ArrayList<>();

        try (Connection connection = SQLConnection.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, value);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String author = resultSet.getString("author");
                String publisher = resultSet.getString("publisher");
                int status = resultSet.getInt("status");
                status = (status == 1) ? BookStatus.AVAILABLE : BookStatus.NOT_AVAILABLE;

                Book newBook = new Book(id, name, author, publisher, status);
                books.add(newBook);
            }

        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }

        return books;
    }

    @Override
    public ArrayList<Book> findRowsWithInt(int value, String label) {
        String sql = "SELECT * FROM Book WHERE " + label + " = ?";
        ArrayList<Book> books = new ArrayList<>();

        try (Connection connection = SQLConnection.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, value);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String author = resultSet.getString("author");
                String publisher = resultSet.getString("publisher");
                int status = resultSet.getInt("status");
                status = (status == 1) ? BookStatus.AVAILABLE : BookStatus.NOT_AVAILABLE;

                Book newBook = new Book(id, name, author, publisher, status);
                books.add(newBook);
            }

        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }

        return books;
    }
}
