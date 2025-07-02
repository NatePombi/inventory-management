package service;

import exceptions.EmptyItemNameException;
import exceptions.NoItemPresentException;
import model.IInventoryItem;

import java.sql.SQLException;
import java.util.List;

public interface IManagement {
    public boolean addItem(IInventoryItem item) throws SQLException;

    public boolean addList(List<IInventoryItem> items) throws SQLException;

    public IInventoryItem getItem(String name) throws SQLException;

    public List<IInventoryItem> getItems() throws SQLException;


    public boolean removeItem(String name) throws SQLException;
}
