package model;

import util.BookStatus;

public class Book {
    private int id;
    private String name;
    private String author;
    private String publisher;
    private int status = BookStatus.AVAILABLE;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
   }

    public Book(int id, String name, String author, String publisher, int status) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.publisher = publisher;
        this.status = status;
    }

    public Book(String name, String author, String publisher, int status) {
        this.name = name;
        this.author = author;
        this.publisher = publisher;
        this.status = status;
    }
}
