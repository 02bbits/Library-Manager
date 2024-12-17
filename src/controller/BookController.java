package controller;

import model.Book;
import util.FormatUtil;
import util.SQLConnection;

import java.sql.*;
import java.util.ArrayList;

public class BookController {
    public ArrayList<Book> getBooks() {
        String sql = "SELECT * FROM Book";
        ArrayList<Book> books = new ArrayList<>();

        try (Connection connection = SQLConnection.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                int id = resultSet.getInt("BookID");
                String title = resultSet.getString("Title");
                String author = resultSet.getString("Author");
                String publisher = resultSet.getString("Publisher");
                String publicationDate = resultSet.getString("PublicationDate");
                String status = resultSet.getString("Status");

                Book newBook = new Book(id, title, author, publisher, publicationDate, status);
                books.add(newBook);
            }

            return books;

        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    public Book getBook(int bookID) {
        String sql = "SELECT * FROM Book WHERE BookID = ?";

        try (Connection connection = SQLConnection.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, bookID);
            ResultSet resultSet = statement.executeQuery();

            String title = resultSet.getString("Title");
            String author = resultSet.getString("Author");
            String publisher = resultSet.getString("Publisher");
            String publicationDate = resultSet.getString("PublicationDate");
            String status = resultSet.getString("Status");

            return new Book(bookID, title, author, publisher, publicationDate, status);

        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    public void addBook(String title, String author, String publisher, String genres, String publicationDate, String status) {
        String sql = "INSERT INTO Book ('Title', 'Author', 'Publisher', 'PublicationDate', 'Status') VALUES (?,?,?,?,?)";
        String sql2 = "INSERT INTO BookGenre VALUES (?,?)";

        ArrayList<String> genresList = FormatUtil.genresStringToList(genres);

        try (Connection connection = SQLConnection.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, author);
            preparedStatement.setString(3, publisher);
            preparedStatement.setString(4, publicationDate);
            preparedStatement.setString(5, status);
            preparedStatement.executeUpdate();

            PreparedStatement preparedStatement2 = connection.prepareStatement(sql2);
            for (String genre : genresList) {
                int genreID = getGenreID(genre);
                preparedStatement2.setInt(1, genreID);
                preparedStatement2.setInt(2, getLatestID() + 1);
                preparedStatement2.executeUpdate();
            }
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
    }

    public void updateBook(int BookID, String title, String author, String publisher, String genres, String publicationDate, String status) {
        String sql = "UPDATE Book SET Title=?, Author=?, Publisher=?, PublicationDate=?, Status=? WHERE BookID=?";

        try (Connection connection = SQLConnection.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, author);
            preparedStatement.setString(3, publisher);
            preparedStatement.setString(4, publicationDate);
            preparedStatement.setString(5, status);
            preparedStatement.setInt(6, BookID);
            preparedStatement.executeUpdate();

            updateGenres(BookID, genres);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteBook(int BookID) {
        String sql = "DELETE FROM Book WHERE BookID=?";
        String sql2 = "DELETE FROM RentingRecord WHERE BookID=?";

        try (Connection connection = SQLConnection.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, BookID);
            preparedStatement.executeUpdate();

            PreparedStatement preparedStatement2 = connection.prepareStatement(sql2);
            preparedStatement2.setInt(1, BookID);
            preparedStatement2.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void rentBook(int bookID, int userID, String rentDate, String dueDate) {
        String sql = "INSERT INTO RentingRecord (BookID, UserID, RentDate, DueDate) VALUES (?,?,?,?)";
        String updateBookStatusSql = "UPDATE Book SET Status='UNAVAILABLE' WHERE BookID=?";

        try (Connection connection = SQLConnection.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userID);
            preparedStatement.setInt(2, bookID);
            preparedStatement.setString(3, rentDate);
            preparedStatement.setString(4, dueDate);
            preparedStatement.executeUpdate();

            PreparedStatement preparedStatement2 = connection.prepareStatement(updateBookStatusSql);
            preparedStatement2.setInt(1, bookID);
            preparedStatement2.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateGenres(int BookID, String genres) {
        String deleteGenresSql = "DELETE FROM BookGenre WHERE BookID=?";
        String updateNewGenresSql = "INSERT INTO BookGenre VALUES (?,?)";

        try (Connection connection = SQLConnection.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(deleteGenresSql);
            preparedStatement.setInt(1, BookID);
            preparedStatement.executeUpdate();

            ArrayList<String> genresList = FormatUtil.genresStringToList(genres);

            PreparedStatement preparedStatement2 = connection.prepareStatement(updateNewGenresSql);
            for (String genre : genresList) {
                int genreID = getGenreID(genre);
                preparedStatement2.setInt(1, genreID);
                preparedStatement2.setInt(2, BookID);
                preparedStatement2.executeUpdate();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getGenreID(String genre) {
        String insertSQL = "INSERT OR IGNORE INTO Genre (GenreName) VALUES (?);";
        String selectSQL = "SELECT GenreID FROM Genre WHERE GenreName = ?;";

        try (Connection connection = SQLConnection.getConnection()) {
            try (PreparedStatement insertStatement = connection.prepareStatement(insertSQL)) {
                insertStatement.setString(1, genre);
                insertStatement.executeUpdate();
            }

            try (PreparedStatement selectStatement = connection.prepareStatement(selectSQL)) {
                selectStatement.setString(1, genre);
                ResultSet resultSet = selectStatement.executeQuery();
                if (resultSet.next()) {
                    return resultSet.getInt("GenreID");
                } else {
                    throw new RuntimeException();
                }
            }
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }


    public ArrayList<String> getGenres(int bookID) {
        String sql = "SELECT * FROM BookGenre WHERE BookID = ?";
        String sql2 = "SELECT GenreName FROM Genre WHERE GenreID = ?";

        ArrayList<String> genres = new ArrayList<>();
        ArrayList<Integer> genreIDs = new ArrayList<>();

        try (Connection connection = SQLConnection.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, bookID);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("GenreID");
                genreIDs.add(id);
            }

            PreparedStatement statement2 = connection.prepareStatement(sql2);

            for (int id : genreIDs) {
                statement2.setInt(1, id);
                ResultSet resultSet2 = statement2.executeQuery();
                genres.add(resultSet2.getString("GenreName"));
            }

            return genres;
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    public String genresToString(int bookID) {
        String sql = "SELECT * FROM BookGenre WHERE BookID = ?";
        String sql2 = "SELECT GenreName FROM Genre WHERE GenreID = ?";

        ArrayList<String> genres = new ArrayList<>();
        ArrayList<Integer> genreIDs = new ArrayList<>();

        try (Connection connection = SQLConnection.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, bookID);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("GenreID");
                genreIDs.add(id);
            }

            PreparedStatement statement2 = connection.prepareStatement(sql2);

            for (int id : genreIDs) {
                statement2.setInt(1, id);
                ResultSet resultSet2 = statement2.executeQuery();
                genres.add(resultSet2.getString("GenreName"));
            }
            return genres.toString().substring(1, genres.toString().length() - 1);

        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    public int getLatestID() {
        String sql = "SELECT BookID FROM Book ORDER BY BookID DESC LIMIT 1";

        try (Connection connection = SQLConnection.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            return resultSet.getInt("BookID");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Book> searchBook(String searchQuery, String columnName) {
        String sql = "SELECT * FROM BOOK WHERE + " + columnName + " LIKE ?";
        ArrayList<Book> books = new ArrayList<>();

        try (Connection connection = SQLConnection.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,"%" + searchQuery + "%");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("BookID");
                String title = resultSet.getString("Title");
                String author = resultSet.getString("Author");
                String publisher = resultSet.getString("Publisher");
                String publicationDate = resultSet.getString("PublicationDate");
                String status = resultSet.getString("Status");

                Book newBook = new Book(id, title, author, publisher, publicationDate, status);
                books.add(newBook);
            }

            return books;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
