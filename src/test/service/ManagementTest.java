package test.service;

import exceptions.EmptyItemNameException;
import exceptions.NoItemPresentException;
import model.IInventoryItem;
import model.InventoryItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.IRepo;
import repository.Repo;
import service.IManagement;
import service.Management;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ManagementTest {

    private IRepo repo;
    private IManagement management;

    @BeforeEach
     void startUp(){
        repo = mock(Repo.class);
        management = new Management(repo);
    }

    @Test
    public void shouldAddItemSuccessfully() throws SQLException {
        IInventoryItem item = new InventoryItem("Rice",3,33.3);

        when(repo.addItem(item)).thenReturn(true);

        boolean validate = management.addItem(item);

        assertTrue(validate);
        verify(repo,times(1)).addItem(item);
    }

    @Test
    public void ShouldThrowExceptionWhenAddItemFails(){
        assertThrows(NoItemPresentException.class, ()->{
            management.addItem(null);
        });
    }

    @Test
    public void shouldAddListSuccessfully() throws SQLException {
        List<IInventoryItem> itemList = List.of(
                new InventoryItem("test1",2,23.2),
                new InventoryItem("test2",1,3.2),
                new InventoryItem("test3",4,43.2)
        );

        when(repo.addListOfItems(itemList)).thenReturn(true);

        boolean validate = management.addList(itemList);

        assertTrue(validate);
        verify(repo, times(1)).addListOfItems(itemList);
    }

    @Test
    public void shouldThrowAnExceptionWhenTryingToAddList(){
       List<IInventoryItem> empty = new ArrayList<>();
        assertThrows(NoItemPresentException.class, ()->{
            management.addList(empty);
        });
    }

    @Test
    public void shouldGetItemSuccessfully() throws SQLException {
        IInventoryItem item = new InventoryItem("testing", 3,44.2);
        String name = "testing";

        when(repo.getItem(name)).thenReturn(item);

        IInventoryItem retrieve = management.getItem(name);

        assertEquals("testing",retrieve.getName());
        assertEquals(3,retrieve.getQuantity());
        assertEquals(44.2,retrieve.getPrice());
        verify(repo,times(1)).getItem(name);
    }


    @Test
    public void shouldThrowAnExceptionWhenTryingToGetItem(){
        assertThrows(EmptyItemNameException.class, ()->{
            management.getItem("");
        });
    }

    @Test
    public void shouldGetItemsSuccessfully() throws SQLException {
        List<IInventoryItem> items = new ArrayList<>(Arrays.asList(
                new InventoryItem("Rice", 44,45.3),
                new InventoryItem("Bread", 2,33.1)
        ));

        when(repo.getItems()).thenReturn(items);

        List<IInventoryItem> retrievedList = management.getItems();
        items.sort(Comparator.comparing(IInventoryItem::getId));

        assertEquals(items.get(1).getId(), retrievedList.get(1).getId());
        assertEquals(items,retrievedList);
    }


    @Test
    public void shouldThrowAnExceptionWhenTryingToGetAnEmptyList(){
        assertThrows(NoItemPresentException.class, ()->{
            management.getItems();
        });
    }

    @Test
    public void shouldRemoveItemSuccessfully() throws SQLException {
        IInventoryItem item = new InventoryItem("testing", 3,44.2);
        String id = "1234";

        when(repo.removeItemById(id)).thenReturn(true);

        boolean validate = management.removeItem(id);

        assertTrue(validate);
        verify(repo, times(1)).removeItemById(id);
    }

    @Test
    public void shouldThrowAnExceptionWhenTryingToRemoveItem(){
        assertThrows(EmptyItemNameException.class,()->{
            management.removeItem("");
        });
    }

    @Test
    public void shouldThrowSQLExceptionIfRepoFails() throws SQLException {
        IInventoryItem item = new InventoryItem("Rice", 2, 39.2);

        when(repo.addItem(item)).thenThrow(new SQLException("DB Error"));

        assertThrows(SQLException.class, ()-> management.addItem(item));
    }

}
