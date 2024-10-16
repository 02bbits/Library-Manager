package util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JavaDBConnection {
    private static final String url = "jdbc:sqlite:LibraryManagement/resources/Library.db";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url);
    }
}
