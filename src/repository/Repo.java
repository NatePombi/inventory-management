package repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import exceptions.DataCorruptionException;
import exceptions.EmptyItemNameException;
import exceptions.ItemAlreadyExistsException;
import exceptions.NoItemPresentException;
import model.IInventoryItem;
import model.InventoryItem;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/**
 * Repository class for managing inventory items
 * Provides CRUD Operations (Create, Remove, Update, Delete)
 * with Json base persistence
 * Implements IRepo interface
 */
public class Repo implements IRepo{
    private final Gson gson;
    private final Path path;

    /**
     * in memory storage of inventory item
     * Uses id as key for uniqueness
     */
    private final Map<String, IInventoryItem> inventoryItemMap = new HashMap<>();


    /**
     * Constructs the Repository with specified Gson and Path
     * Automatically loads existing items from Json file into the in memory storage
     *
     * @param gson :Gson instance for Json serialization and deserialization
     * @param path :Path to the Json file
     */
    public Repo(Gson gson, Path path){
        this.gson = gson;
        this.path = path;
        loadListOfItems();
    }


    /**
     * Adds a single item to the repository
     * saves the updated inventory into the Json file
     * @param item: The item to add
     * @return: returns true of the item was added successfully
     * @throws: NoItemPresentException if the item is null
     */
    @Override
    public boolean addItem(IInventoryItem item){
        if(item == null){
            throw new NoItemPresentException();
        }

        inventoryItemMap.put(item.getId(),item);
        saveItems();
        return true;
    }

    /**
     * Adds a List of item into the repository
     * saves the updated inventory into the Json file
     * @param items: The List of items to be added
     * @return: True if the items are successfully added
     * @throws: NoItemPresentException if the list of items is null
     * @throws: ItemAlreadyExistsException if any item in the list exists by ID
     */
    @Override
    public boolean addListOfItems (List<IInventoryItem> items){
        if(items == null){
            throw new NoItemPresentException();
        }


        for(IInventoryItem in : items){
            if(inventoryItemMap.containsKey(in.getId())){
                throw new ItemAlreadyExistsException();
            }

            inventoryItemMap.put(in.getId(),in);
        }
        saveItems();
        return true;
    }

    /**
     * Retrieves and item by its name (Case insensitive)
     * @param name: Name of the item.
     * @return: A Single Inventory item matching the name
     * @throws: EmptyItemNameException if the name input is blank
     * @throws: NoItemPresentException if there are no items in the list to search/ if there are no items that match the name.
     */
    @Override
    public IInventoryItem getItem (String name){
        if(name.isBlank()){
            throw new EmptyItemNameException();
        }

        if(inventoryItemMap.isEmpty()){
            throw new NoItemPresentException();
        }

       for(Map.Entry<String , IInventoryItem> in : inventoryItemMap.entrySet()){
           if(in.getValue().getName().equalsIgnoreCase(name)){
               return in.getValue();
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
    public List<IInventoryItem> getItems() {
        if(inventoryItemMap.isEmpty()){
            throw new NoItemPresentException();
        }
        List<IInventoryItem> listItems = new ArrayList<>();
        for(Map.Entry<String , IInventoryItem> in : inventoryItemMap.entrySet()){
            listItems.add(in.getValue());
        }
        return listItems;
    }


    /**
     * Saves the current inventory into the Json file
     * creates directories if it doesn't exist
     */
    private void saveItems(){
        try{
            Files.createDirectories(path.getParent());

            try(FileWriter fileWriter = new FileWriter(path.toFile())){
                List<IInventoryItem> itemList = new ArrayList<>();
                for(Map.Entry<String, IInventoryItem> in : inventoryItemMap.entrySet()){
                    itemList.add(in.getValue());
                }

                gson.toJson(itemList,fileWriter);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to save items to file",e);
        }
    }

    /**
     * Removes item from the inventory by id
     * Saves the updated inventory to the Json file
     * @param id: ID of item to remove
     * @return: True if item has been removed Successfully and false if not found
     * @throws: EmptyItemNameException if id field is blank/empty
     * @throws: NoItemPresentException if the inventory is empty
     */
    @Override
    public boolean removeItem(String id) {
        if(id.isBlank()){
            throw new EmptyItemNameException();
        }

        if(inventoryItemMap.isEmpty()){
            throw new NoItemPresentException();
        }

        boolean remove = inventoryItemMap.entrySet().removeIf(
          entry -> entry.getKey().equalsIgnoreCase(id)
        );

        saveItems();
        return remove;
    }

    /**
     * Loads items from the Json file into the in memory map
     * If file doesn't exist, it Initializes with an empty inventory
     * Handles the Json parsing errors
     */
    private void loadListOfItems(){
        try{
            Files.createDirectories(path.getParent());

            if(!Files.exists(path)){
                return;
            }

            try(FileReader fileReader = new FileReader(path.toFile())){
                Type type = new TypeToken<List<InventoryItem>>() {}.getType();
                List<IInventoryItem> itemList = gson.fromJson(fileReader,type);

                if(itemList.isEmpty()){
                    System.out.println("No items present");
                }
                else{
                    for(IInventoryItem in : itemList){
                        inventoryItemMap.put(in.getId(),in);
                    }
                }
            }
            catch (Exception e){
                throw new DataCorruptionException("Failed to parse Json file. File might be corrupted ", e);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to access the data directory. ", e);
        }
    }
}
