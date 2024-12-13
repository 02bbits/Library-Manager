package util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLConnection {
    private static final String url = "jdbc:sqlite:LibraryManagement/resources/LibraryManagementSystem.db";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url);
    }
}
