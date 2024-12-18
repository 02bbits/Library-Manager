package util;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLConnection {
    private static final String path = "LibraryManagement" + File.separator + "resources" + File.separator + "LibraryManagementSystem.db";
    private static final String url = "jdbc:sqlite:" + path;

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url);
    }
}
