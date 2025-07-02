package dbConnection;

import java.sql.*;

public class DBConnection {
    public static boolean config = false;
    private final static String url = "jdbc:mysql://localhost:3306/inventory_db";
    private final static String username = "root";
    private final static String password = "MasokoBums";

    private DBConnection(){}

    public static Connection getConnection() throws SQLException {
        if(!config) {
            return DriverManager.getConnection(url, username, password);
        }
        else {
            String url = "jdbc:mysql://localhost:3306/testData";
            String name = "root";
            String password = "MasokoBums";

            return DriverManager.getConnection(url,name,password);
        }
    }


    public static void databaseInit() throws SQLException {
        String createTable = """
                CREATE TABLE IF NOT EXISTS inventory(
                id VARCHAR(100) PRIMARY KEY,
                name VARCHAR(100) NOT NULL,
                quantity INT NOT NULL,
                price DOUBLE NOT NULL
                );
                """;

        try(Connection con = DBConnection.getConnection();
            Statement stm = con.createStatement()
        ){
            stm.execute(createTable);
        }
    }


    public static void testDataInit() throws SQLException {


        String createTable = """
                CREATE TABLE IF NOT EXISTS inventory(
                id VARCHAR(100) PRIMARY KEY,
                name VARCHAR(100) NOT NULL,
                quantity INT NOT NULL,
                price DOUBLE NOT NULL
                );
                """;


        try(Connection con = getConnection();
                Statement stm = con.createStatement()
        ){
            stm.execute(createTable);
        }

    }

}
