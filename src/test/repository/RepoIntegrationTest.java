package test.repository;

import dbConnection.DBConnection;
import exceptions.EmptyItemNameException;
import exceptions.NoItemPresentException;
import model.IInventoryItem;
import model.InventoryItem;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.IRepo;
import repository.Repo;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RepoTest {
    private IRepo repo;

    @BeforeEach
    void startUp(){
        DBConnection.config = true;
        repo = new Repo();
    }

    @Test
    public void shouldAddItemAndGetItemSuccessfully() throws SQLException {
        IInventoryItem item = new InventoryItem("test",2,4);

        boolean validate = repo.addItem(item);

        assertTrue(validate);

        IInventoryItem retrieve = repo.getItem("test");

        assertEquals(item.getId(),retrieve.getId());
        assertEquals(item.getName(),retrieve.getName());
        assertEquals(item.getQuantity(),retrieve.getQuantity());
        assertEquals(item.getPrice(),retrieve.getPrice());
    }

    @Test
    public void shouldThrowExceptionWhenTryingToAddNull(){
        assertThrows(Exception.class, ()->{
            repo.addItem(null);
        });
    }

    @Test
    public void shouldAddListItemAndGetListItemSuccessfully() throws SQLException {
        List<IInventoryItem> itemList = new ArrayList<>(Arrays.asList(
                new InventoryItem("test1", 2, 23.2),
                new InventoryItem("test2", 1, 3.2),
                new InventoryItem("test3", 4, 43.2)
        ));

        boolean validate = repo.addListOfItems(itemList);

        assertTrue(validate);

        IInventoryItem retrieveSingleItem = repo.getItem("test2");
        List<IInventoryItem> retrieved = repo.getItems();
        itemList.sort(Comparator.comparing(IInventoryItem::getId));
        for(IInventoryItem in : itemList){
            if(in.getName().equals("test2")){
            assertEquals(in.getId(),retrieveSingleItem.getId());
            }
        }
            assertEquals(new ArrayList<>(itemList), new ArrayList<>(retrieved));
    }

    @Test
    public void shouldThrowExceptionWhenTryingToAddNullList(){
        assertThrows(NoItemPresentException.class, ()->{
            repo.addListOfItems(null);
        });
    }

    @Test
    public void shouldRemoveItemSuccessfully() throws SQLException {
        IInventoryItem item = new InventoryItem("test",2,4);
        repo.addItem(item);

        String id = item.getId();
        boolean validate = repo.removeItemById(id);
        assertTrue(validate);
    }

    @Test
    public void shouldThrowExceptionWhenTryingToRemove(){

        assertThrows(EmptyItemNameException.class, ()->{
            repo.removeItemById("");
        });

    }

    @AfterEach
    void cleanUp() throws SQLException {
        String clean = "DELETE FROM inventory";

        try(Connection con = DBConnection.getConnection();
            Statement stm = con.createStatement()
        ){
            stm.execute(clean);
        }
    }



}
