package service;

import exceptions.EmptyItemNameException;
import exceptions.NoItemPresentException;
import model.IInventoryItem;

import java.util.List;

public interface IManagement {
    public boolean addItem(IInventoryItem item);

    public boolean addList(List<IInventoryItem> items);

    public IInventoryItem getItem(String name);

    public List<IInventoryItem> getItems();


    public boolean removeItem(String name);
}
