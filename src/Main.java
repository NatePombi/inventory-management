import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dbConnection.DBConnection;
import repository.IRepo;
import repository.Repo;
import service.IManagement;
import service.Management;
import ui.Menu;
import ui.Start;
import util.UserPrompts;

import java.sql.SQLException;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws SQLException {
        IRepo repo = new Repo();
        DBConnection.databaseInit();
        IManagement management = new Management(repo);
        Menu menu = new Menu();
        Scanner scanner = new Scanner(System.in);
        UserPrompts userPrompts = new UserPrompts(scanner);
        Start start = new Start(menu,userPrompts,repo,management);
        start.runApp();
    }
}