package model;

import util.AutoGenerator;

import java.io.Serializable;

/**
 * Represents a single Inventory item with an ID, Name, Quantity and price
 * implements IInventory interface, and supports Serializable
 */
public class InventoryItem implements Serializable , IInventoryItem{
    //Unique Identifier for the Item, which is auto Generated
    private final String id;
    //Inventory Item name
    private final String name;
    //Inventory Item quantity
    private final int quantity;
    //Inventory Item price
    private final double price;

    /**
 * Constructs the Inventory item with the specified , Name, Quantity and price
     * The ID is auto generated using AutoGenerator.
     * @param name: Name of item
     * @param quantity: Quantity of the item
     * @param price: Price of the item
     */
    public InventoryItem(String name,int quantity,double price){
        this.id = AutoGenerator.idGenerator();
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }
    //==========Getters================

    //Returns the items unique ID
    @Override
    public String getId() {
        return id;
    }

    //returns the name of the item
    @Override
    public String getName() {
        return name;
    }

    //returns the quantity of the item
    @Override
    public int getQuantity() {
        return quantity;
    }

    //returns the price of the item
    @Override
    public double getPrice() {
        return price;
    }

    //returns a String representation of the Inventory item
    @Override
    public String toString() {
        return "InventoryItem{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}
