package controller;

import model.RentingRecord;
import util.SQLConnection;

import java.sql.*;
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

    public ArrayList<RentingRecord> searchRecords(String searchQuery, String columnName, boolean dateBefore) {
        String sql = dateBefore ? "SELECT RentalID, RentingRecord.UserID, BookID, RentDate, DueDate FROM RentingRecord JOIN User ON RentingRecord.UserID=User.UserID WHERE " + columnName + "< ?" : "SELECT RentalID, RentingRecord.UserID, BookID, RentDate, DueDate FROM RentingRecord JOIN User ON RentingRecord.UserID=User.UserID WHERE " + columnName + "> ?";
        ArrayList<RentingRecord> records = new ArrayList<>();

        try (Connection connection = SQLConnection.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, searchQuery);
            ResultSet resultSet = preparedStatement.executeQuery();

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
