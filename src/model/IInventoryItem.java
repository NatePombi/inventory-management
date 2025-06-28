package model;

/**
 * Defines the contract of the Inventory Item in the system.
 * Any class that implements this interface must provide methods
 * to retrieve ID, Name, Quantity and Price
 */
public interface IInventoryItem {

    //Returns the Unique identifier
     String getId();

    //returns the item name
     String getName();

    //returns the quantity of the item
     int getQuantity();

    //returns the price of the item
     double getPrice();

    //returns the string representation of the inventory item
    @Override
     String toString();
}
