package repository;


import dbConnection.DBConnection;

import exceptions.EmptyItemNameException;

import exceptions.ItemAlreadyExistsException;
import exceptions.NoItemPresentException;
import model.IInventoryItem;
import model.InventoryItem;

import java.sql.*;
import java.util.*;

/**
 * Repository class for managing inventory items
 * Provides CRUD Operations (Create, Remove, Update, Delete)
 * with Json base persistence
 * Implements IRepo interface
 */
public class Repo implements IRepo{



    /**
     * Constructs the Repository with specified Gson and Path
     * Automatically loads existing items from Json file into the in memory storage
     */
    public Repo(){
        if(DBConnection.config){
            try {
                DBConnection.testDataInit();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }


    /**
     * Adds a single item to the repository
     * @param item: The item to add
     * @return: returns true of the item was added successfully
     * @throws: NoItemPresentException if the item is null
     */
    @Override
    public boolean addItem(IInventoryItem item) throws SQLException {
        if(item == null){
            throw new NoItemPresentException();
        }

        String add = "INSERT INTO inventory(id,name,quantity,price) VALUES(?,?,?,?)";

        try(Connection con = DBConnection.getConnection();
            PreparedStatement pstm = con.prepareStatement(add);
        ){
            pstm.setString(1, item.getId());
            pstm.setString(2,item.getName());
            pstm.setInt(3,item.getQuantity());
            pstm.setDouble(4,item.getPrice());
            int row = pstm.executeUpdate();
            return row > 0;
        }
        catch (SQLIntegrityConstraintViolationException e){
            throw new ItemAlreadyExistsException();
        }
    }

    /**
     * Adds a List of item into the repository
     * @param items: The List of items to be added
     * @return: True if the items are successfully added
     * @throws: NoItemPresentException if the list of items is null
     * @throws: ItemAlreadyExistsException if any item in the list exists by ID
     */
    @Override
    public boolean addListOfItems (List<IInventoryItem> items) throws SQLException {
        if(items == null || items.isEmpty()){
            throw new NoItemPresentException();
        }

        String addItems = "INSERT INTO inventory(id,name,quantity,price) VALUES(?,?,?,?)";

        try(Connection con = DBConnection.getConnection();
            PreparedStatement pstm = con.prepareStatement(addItems))
        {
            for(IInventoryItem in : items){
                pstm.setString(1,in.getId());
                pstm.setString(2,in.getName());
                pstm.setInt(3,in.getQuantity());
                pstm.setDouble(4,in.getPrice());
                pstm.addBatch();
            }

            int[] rows = pstm.executeBatch();
            for(int in : rows){
                if(in == Statement.EXECUTE_FAILED){
                    return false;
                }
            }

            return true;
        }
        catch (SQLIntegrityConstraintViolationException e){
            throw new ItemAlreadyExistsException();
        }
    }

    /**
     * Retrieves and item by its name (Case insensitive)
     * @param name: Name of the item.
     * @return: A Single Inventory item matching the name
     * @throws: EmptyItemNameException if the name input is blank
     * @throws: NoItemPresentException if there are no items in the list to search/ if there are no items that match the name.
     */
    @Override
    public IInventoryItem getItem (String name) throws SQLException {
        if(name.isBlank()){
            throw new EmptyItemNameException();
        }

        String getItem = "SELECT * FROM inventory WHERE name = ?";

        try(Connection con = DBConnection.getConnection();
            PreparedStatement pstm = con.prepareStatement(getItem)
        ){
            pstm.setString(1,name);

            ResultSet rs = pstm.executeQuery();

            if(rs.next()){
                String id = rs.getString("id");
                String itemName = rs.getString("name");
                int quantity = rs.getInt("quantity");
                double price = rs.getDouble("price");

                return new InventoryItem(id,itemName,quantity,price);
            }
        }

       throw new NoItemPresentException();
    }

    /**
     * Retrieves a list of all the items in the repository.
     * @return: List of Inventory items
     * @throws: NoItemPresentException if there is no items in the inventory
     */
    @Override
    public List<IInventoryItem> getItems() throws SQLException {
        String selectAll = "SELECT * FROM inventory ORDER BY id";

        try(Connection con = DBConnection.getConnection();
            PreparedStatement pstm = con.prepareStatement(selectAll);
            ResultSet rs = pstm.executeQuery()
        ){
            List<IInventoryItem> items = new ArrayList<>();
            while(rs.next()){
                String id = rs.getString("id");
                String name = rs.getString("name");
                int quantity = rs.getInt("quantity");
                double price = rs.getDouble("price");
                items.add(new InventoryItem(id,name,quantity,price));
            }


            if(items.isEmpty()){
                throw new NoItemPresentException();
            }


            return items;
        }
    }



    /**
     * Removes item from the inventory by id
     * @param id: ID of item to remove
     * @return: True if item has been removed Successfully and false if not found
     * @throws: EmptyItemNameException if id field is blank/empty
     * @throws: NoItemPresentException if the inventory is empty
     */
    @Override
    public boolean removeItemById(String id) throws SQLException {
        if(id.isBlank()){
            throw new EmptyItemNameException();
        }

       String remove = "DELETE FROM inventory WHERE id = ?";

        try(Connection con = DBConnection.getConnection();
            PreparedStatement pstm = con.prepareStatement(remove)
        ){
            pstm.setString(1,id);
            int rows = pstm.executeUpdate();
            return rows>0;
        }
    }

}
