package model;
import java.text.DateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;


public class Rental {
    private int id;
    private int bookId;
    private int readerId;
    private DateTimeFormatter returnDate;
    private DateTimeFormatter rentalDate;

    public int getId() {
        return id;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getReaderId() {
        return readerId;
    }

    public void setReaderId(int readerId) {
        this.readerId = readerId;
    }

    public DateTimeFormatter getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(DateTimeFormatter returnDate) {
        this.returnDate = returnDate;
    }

    public DateTimeFormatter getRentalDate() {
        return rentalDate;
    }

    public void setRentalDate(DateTimeFormatter rentalDate) {
        this.rentalDate = rentalDate;
    }

    public Rental(int id, int bookId, int readerId, DateTimeFormatter returnDate, DateTimeFormatter rentalDate) {
        this.id = id;
        this.bookId = bookId;
        this.readerId = readerId;
        this.returnDate = returnDate;
        this.rentalDate = rentalDate;
    }

    public Rental(int bookId, int readerId, DateTimeFormatter returnDate, DateTimeFormatter rentalDate) {
        this.bookId = bookId;
        this.readerId = readerId;
        this.returnDate = returnDate;
        this.rentalDate = rentalDate;
    }
}
