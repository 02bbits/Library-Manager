package controller;

import java.sql.SQLException;
import java.util.ArrayList;

public interface IController<T> {
    public void add(T item);
    public ArrayList<T> getAll();
    public ArrayList<T> findRowsWithString(String value, String label);
    public ArrayList<T> findRowsWithInt(int value, String label);
}
