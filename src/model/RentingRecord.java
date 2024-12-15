package model;

public class RentingRecord {
    private int id;
    private int userId;
    private int bookId;
    private String rentDate;
    private String dueDate;

    public RentingRecord(int rentingId, int userId, int bookId, String rentDate, String dueDate) {
        this.id = rentingId;
        this.userId = userId;
        this.bookId = bookId;
        this.rentDate = rentDate;
        this.dueDate = dueDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getRentDate() {
        return rentDate;
    }

    public void setRentDate(String rentDate) {
        this.rentDate = rentDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }
}
