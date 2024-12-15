package controller;

import model.Book;
import model.RentingRecord;
import util.SQLConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class RentingRecordController {
    public ArrayList<RentingRecord> getRecords() {
        String sql = "SELECT * FROM RentingRecord";
        ArrayList<RentingRecord> records = new ArrayList<>();

        try (Connection connection = SQLConnection.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                int id = resultSet.getInt("RentalID");
                int bookID = resultSet.getInt("BookID");
                int userID = resultSet.getInt("UserID");
                String rentDate = resultSet.getString("RentDate");
                String dueDate = resultSet.getString("DueDate");

                records.add(new RentingRecord(id, bookID, userID, rentDate, dueDate));
            }

            return records;

        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }

    }
}
