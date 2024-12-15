package util;

import controller.BookController;
import controller.RentingRecordController;
import controller.UserController;
import model.Book;
import model.RentingRecord;
import model.User;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class TableUtil {
    public static DefaultTableModel usersToTableModel(ArrayList<User> users) {
        String[] columnNames = {"ID", "Username", "Email", "Role"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        for (User user : users) {
            Object[] row = new Object[4];
            row[0] = user.getId();
            row[1] = user.getUsername();
            row[2] = user.getEmail();
            row[3] = user.getRole();
            model.addRow(row);
        }

        return model;
    }

    public static DefaultTableModel readersToTableModel(ArrayList<User> users) {
        String[] columnNames = {"ID", "Username", "Email"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        for (User user : users) {
            Object[] row = new Object[3];
            row[0] = user.getId();
            row[1] = user.getUsername();
            row[2] = user.getEmail();
            model.addRow(row);
        }

        return model;
    }

    public static DefaultTableModel booksToTableModel(ArrayList<Book> books) {
        String[] columnNames = {"ID", "Title", "Author", "Publisher", "Genre", "Publication Date", "Status"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        BookController bookController = new BookController();

        for (Book book : books) {
            Object[] row = new Object[7];
            row[0] = book.getId();
            row[1] = book.getTitle();
            row[2] = book.getAuthor();
            row[3] = book.getPublisher();
            row[4] = bookController.genresToString(book.getId());
            row[5] = book.getPublicationDate();
            row[6] = book.getStatus();
            model.addRow(row);
        }

        return model;
    }

    public static DefaultTableModel rentingRecordsToTableModel(ArrayList<RentingRecord> records) {
        String[] columnNames = {"ID", "User", "Book", "Renting Date", "Due Date"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        BookController bookController = new BookController();
        UserController userController = new UserController();

        for (RentingRecord record : records) {
            Object[] row = new Object[5];
            row[0] = record.getId();
            row[1] = userController.getUser(record.getUserId()).getUsername();
            row[2] = bookController.getBook(record.getBookId()).getTitle();
            row[3] = record.getRentDate();
            row[4] = record.getDueDate();
            model.addRow(row);
        }

        return model;
    }
}
