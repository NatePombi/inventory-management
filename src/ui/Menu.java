package ui;

public class Menu {

    public Menu(){

    }

    public void menu(){
        System.out.println("""
                    Welcome to Jake's Inventory
                    ---------------------------
                    1) Add Item
                    2) Add List of Items
                    3) Search for Item
                    4) View All Items
                    5) Remove Item
                    6) Exit
                """);
    }
}
