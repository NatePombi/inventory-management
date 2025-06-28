import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import repository.IRepo;
import repository.Repo;
import seed.SeedData;
import service.IManagement;
import service.Management;
import ui.Menu;
import ui.Start;
import util.UserPrompts;

import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        SeedData seed = new SeedData();
        IRepo repo = new Repo(gson,seed.getPath());
        IManagement management = new Management(repo);
        Menu menu = new Menu();
        Scanner scanner = new Scanner(System.in);
        UserPrompts userPrompts = new UserPrompts(scanner);
        Start start = new Start(menu,userPrompts,repo,management);
        start.runApp();
    }
}