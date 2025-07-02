package ui;

import exceptions.EmptyItemNameException;
import exceptions.InputEmptyException;
import exceptions.NoItemPresentException;
import model.IInventoryItem;
import model.InventoryItem;
import repository.IRepo;
import service.IManagement;
import util.ExceptionHandler;
import util.UserPrompts;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

public class Start {
    private final Menu menu;
    private final UserPrompts userPrompts;
    private final IManagement management;

    public Start(Menu menu, UserPrompts userPrompts,IRepo repo,IManagement management){
        this.menu = menu;
        this.userPrompts = userPrompts;
        this.management = management;
    }


    public void runApp(){
        boolean cont = true;

        while (cont) {
            menu.menu();
            try {
                int input = userPrompts.userIntegerPrompt("Enter the number of the actions above: ");

                switch (input) {
                    case 1 -> addItem();
                    case 2 -> addItems();
                    case 3 -> searchItem();
                    case 4 -> viewAllItems();
                    case 5 -> removeItem();
                    case 6 -> cont = exitApp();
                    default -> throw new IllegalArgumentException("Invalid option chosen");
                }
            }catch (IllegalArgumentException | InputMismatchException e){
                ExceptionHandler.exception(e);
                System.out.println();
            }
        }

    }

    public void addItem(){
        try {
            String name = userPrompts.userPrompt("Enter the item name: ");
            int quantity = userPrompts.userIntegerPrompt("Enter the quantity of the item: ");
            double price = userPrompts.userDoublePrompt("Enter the price of the item: R");

            IInventoryItem item = new InventoryItem(name,quantity,price);

            boolean check = management.addItem(item);

            if(check){
                System.out.println("You have successfully added an item");
            }

            else{
                System.out.println("Failed to add new item");
            }
        } catch (NoItemPresentException | NullPointerException | InputMismatchException | SQLException e) {
            ExceptionHandler.exception(e);
        }
    }

    public void addItems(){

            try {
                int num = userPrompts.userIntegerPrompt("How many items you want to add: ");

                List<IInventoryItem> items = new ArrayList<>();

                for (int i = 0; i < num; i++) {
                    System.out.println("Item (" + (i+1) + ")");
                    String name = userPrompts.userPrompt("Enter the item name: ");
                    int quantity = userPrompts.userIntegerPrompt("Enter the quantity of the item: ");
                    double price = userPrompts.userDoublePrompt("Enter the price of the item: R");

                    items.add(new InventoryItem(name, quantity, price));
                }

                if (management.addList(items)) {
                    System.out.println("Items successfully added");
                } else {
                    System.out.println("Failed to add items");
                }

            } catch (NoItemPresentException | NullPointerException | InputMismatchException | SQLException e) {
                ExceptionHandler.exception(e);
            }
    }

    public void searchItem(){
        try{

            String itemName = userPrompts.userPrompt("Enter the name of item: ");

            IInventoryItem item = management.getItem(itemName);

            System.out.printf("""
                        Item id: %s
                        Item name: %s
                        Quantity: %d
                        Price: R%.2f\n 
                    """,item.getId(),item.getName(),item.getQuantity(),item.getPrice());

            System.out.println();
        } catch (EmptyItemNameException | NullPointerException | InputEmptyException | SQLException e) {
            ExceptionHandler.exception(e);
        }
    }

    public void viewAllItems(){

        try{
            List<IInventoryItem> listItems = management.getItems();
            int counter = 0;
            for(IInventoryItem in : listItems){
                counter++;
                System.out.println("--".repeat(20));
                System.out.println("Item (" + counter + ")");
                System.out.println("Item id: " + in.getId());
                System.out.println("Item name: " + in.getName());
                System.out.println("Quantity: " + in.getQuantity());
                System.out.println("Price: R" + in.getPrice());
                System.out.println("--".repeat(20));
            }

        } catch (NoItemPresentException | InputEmptyException | SQLException e) {
            ExceptionHandler.exception(e);
        }
    }

    public void removeItem(){
        try{
            String name = userPrompts.userPrompt("Enter id of item you want to remove: ");

            management.removeItem(name);

            System.out.println("Successfully removed the item!");
        } catch (NoItemPresentException | EmptyItemNameException | InputEmptyException | SQLException e) {
            ExceptionHandler.exception(e);
        }
    }

    public boolean exitApp(){

        String input = userPrompts.userPrompt("Are you sure you want to exit? enter (Y/N) to confirm decision: ");

        if(input.equalsIgnoreCase("y")){
            System.out.print("Exiting");
            for(int i =0; i<3; i++){
                try {
                    Thread.sleep(500);
                    System.out.print(".");
                } catch (InterruptedException | InputEmptyException e) {
                    ExceptionHandler.exception(e);
                }
            }
            System.out.println();
            System.out.println("GoodBye");
            return false;
        }

        return true;
    }


}
