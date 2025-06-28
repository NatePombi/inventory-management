package test.repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import exceptions.DataCorruptionException;
import exceptions.EmptyItemNameException;
import exceptions.NoItemPresentException;
import model.IInventoryItem;
import model.InventoryItem;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.IRepo;
import repository.Repo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RepoTest {
    private Gson gson;
    private Path path;
    private IRepo repo;

    @BeforeEach
    void startUp(){
        gson = new GsonBuilder().setPrettyPrinting().create();
        path = Paths.get("data","test.json");
        repo = new Repo(gson,path);
    }

    @Test
    public void shouldAddItemAndGetItemSuccessfully(){
        IInventoryItem item = new InventoryItem("test",2,4);

        boolean validate = repo.addItem(item);

        assertTrue(validate);

        IInventoryItem retrieve = repo.getItem("test");

        assertEquals(item,retrieve);
    }

    @Test
    public void shouldThrowExceptionWhenTryingToAddNull(){
        assertThrows(Exception.class, ()->{
            repo.addItem(null);
        });
    }

    @Test
    public void shouldAddListItemAndGetListItemSuccessfully(){
        List<IInventoryItem> itemList = List.of(
                new InventoryItem("test1",2,23.2),
                new InventoryItem("test2",1,3.2),
                new InventoryItem("test3",4,43.2)
        );

        boolean validate = repo.addListOfItems(itemList);

        assertTrue(validate);

        IInventoryItem retrieveSingleItem = repo.getItem("test2");
        List<IInventoryItem> retrieved = repo.getItems();

        retrieved.sort(Comparator.comparing(IInventoryItem::getName));
        assertEquals(itemList.get(1),retrieveSingleItem);
        assertEquals(itemList, retrieved);
    }

    @Test
    public void shouldThrowExceptionWhenTryingToAddNullList(){
        assertThrows(NoItemPresentException.class, ()->{
            repo.addListOfItems(null);
        });
    }

    @Test
    public void shouldRemoveItemSuccessfully(){
        IInventoryItem item = new InventoryItem("test",2,4);
        repo.addItem(item);

        String id = item.getId();
        boolean validate = repo.removeItem(id);
        assertTrue(validate);
    }

    @Test
    public void shouldThrowExceptionWhenTryingToRemove(){

        assertThrows(EmptyItemNameException.class, ()->{
            repo.removeItem("");
        });

    }

    @Test
    public void shouldHandleCorruptJsonFile() throws IOException {
        Files.createDirectories(path.getParent());
        Files.writeString(path,"Testing 1 2 3");

        Exception exception = assertThrows(DataCorruptionException.class, ()->{
            new Repo(gson,path);
        });

        String exceptionContains = "Failed to parse Json file. File might be corrupted ";
        String actualException = exception.getMessage();

        assertEquals(exceptionContains,actualException);
    }

    @AfterEach
    void cleanUp(){
        try{
            Files.deleteIfExists(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
