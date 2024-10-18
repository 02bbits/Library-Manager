package model;

public class Reader {
    private int id;
    private String name;
    private String _class;
    private String email;
    private String phoneNumber;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String get_class() {
        return _class;
    }

    public void set_class(String _class) {
        this._class = _class;
    }

    public Reader(int id, String name, String _class, String email, String phoneNumber) {
        this.id = id;
        this.name = name;
        this._class = _class;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public Reader(String name, String _class, String email, String phoneNumber) {
        this.name = name;
        this._class = _class;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
}
