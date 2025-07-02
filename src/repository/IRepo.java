package repository;

import model.IInventoryItem;

import java.sql.SQLException;
import java.util.List;

/**
 * Define the contract for the inventory Repository
 * Any class that implements this interface must provide methods
 * To add , retrieve and remove items
 * From persistent storage
 */
public interface IRepo {

    /**
     * Add to single item to repository.
     * @param item Item to add to inventory.
     * @return True if item is added successfully and false if failed
     */
     boolean addItem(IInventoryItem item) throws SQLException;

    /**
     * Add a list of items in the repository.
     * @param items List of items to add to the inventory.
     * @return True if the list was successfully added
     */
     boolean addListOfItems (List<IInventoryItem> items) throws SQLException;

    /**
     * Retrieve an item by its name.
     * @param name Name of the item
     * @return a single Inventory item matching the name.
     */
     IInventoryItem getItem (String name) throws SQLException;

    /**
     * Retrieves all the items from the Inventory
     * @return a list of Inventory items
     */
     List<IInventoryItem> getItems() throws SQLException;

    /**
     * Removes an item by its name
     * @param id The id of the item
     * @return True if removed successfully
     */
     boolean removeItemById(String id) throws SQLException;
}
