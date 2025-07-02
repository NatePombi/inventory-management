package service;

import exceptions.EmptyItemNameException;
import exceptions.NoItemPresentException;
import model.IInventoryItem;
import repository.IRepo;

import java.sql.SQLException;
import java.util.List;

/**
 * Service layer for managing the inventory actions
 * implements IManagement interface
 * with operations like adding, retrieving and removing items
 */
public class Management implements IManagement{
    private final IRepo repo;

    /**
     * Constructor the management layer with specified IRepo
     * @param repo instance for delegating work to repository
     */
    public Management(IRepo repo){
        this.repo = repo;
    }

    /**
     * Delegates the adding of an item to repository
     * @param item Item that's to be added
     * @return True if the item is successfully added and false if failed to add
     * @throws NoItemPresentException if the item is null
     */
    @Override
    public boolean addItem(IInventoryItem item) throws SQLException {
        if(item == null){
            throw new NoItemPresentException();
        }

        return repo.addItem(item);
    }

    /**
     * delegates the adding of the list of items to the repository
     * @param items The list of item that's to be added.
     * @return True if the items has been successfully added
     * @throws NoItemPresentException if the list of items is empty
     */
    @Override
    public boolean addList(List<IInventoryItem> items) throws SQLException {
        if(items == null || items.isEmpty()){
            throw new NoItemPresentException();
        }

        return repo.addListOfItems(items);
    }

    /**
     * Delegates the retrieval of an Inventory item by its name to repository
     * @param name Name of the item
     * @return an Inventory item matching the name
     * @throws EmptyItemNameException if the name is empty
     */
    @Override
    public IInventoryItem getItem(String name) throws SQLException {
        if(name.isBlank()){
            throw new EmptyItemNameException();
        }

        return repo.getItem(name);
    }

    /**
     * Delegates the retrieval of the list of items to the repository
     * @return List of Inventory items
     */
    @Override
    public List<IInventoryItem> getItems() throws SQLException {
        List<IInventoryItem> items = repo.getItems();

        if(items.isEmpty()){
            throw new NoItemPresentException();
        }

        return items;
    }


    /**
     * Delegates the removal of an Inventory item by id to the repository.
     * @param id ID of the item
     * @return True if the item has been successfully removed
     */
    @Override
    public boolean removeItem(String id) throws SQLException {
        if(id.isBlank()){
            throw new EmptyItemNameException();
        }

        return repo.removeItemById(id);
    }
}
