  --Inventory Management Console App

    Welcome to the Inventory Management System a simple yet powerful console based application that allows you to manage products in an inventory. You can add items, remove them, search for specific products, and view the full list now backed by MySQL database storage for better reliability and scalability.

    The system was built entirely using Java, with a focus on Object Oriented Programming principles, clean architecture, and fully tested business logic.



  --Features

    - Add single or multiple inventory items
    - Search for items by name
    - View all items in the inventory
    - Remove items from inventory
    - Switchable data layer: previously used JSON, now upgraded to MySQL database** via JDBC
    - Handles edge cases with custom exceptions
    - Fully tested with unit and integration tests using JUnit 5 and Mockito



  --Project Structure

    dbConnection/         - Database creation logic
    exceptions/           - Custom exception classes
    repository/           - Repository logic
    model/                - Inventory item models & interfaces
    service/              - Business logic (CRUD operations)
    test/                 - All unit and integration tests
    ui/                   - Console menus and user interaction
    util/                 - Utilities
    Main.java             - App entry point
    README.md             - Project documentation


   --  Data Storage (Now MySQL-Based)

        The application connects to a local MySQL database using JDBC. You’ll need to set up a database named: inventory_db

        The required table is auto generated if it does not exist

        Default credentials:
        Username: root
        Password: (set in DbConnection.java)

        NOTE: Ensure your local MySQL server is running before launching the app.

    --Tests

        Unit tests for all service and utility classes
        Integration tests for database interactions using a real MySQL instance
        Mock-based testing using Mockito
        Run all tests via your IDE

    --Setup Instructions

        Install MySQL and ensure it’s running
        Clone the repo
        import the project into IntelliJ or any Java IDE
        Adjust DB credentials in DbConnection.java if needed
        Run Main.java to start the application

   -- Future Improvements

      Add sorting and filtering (by price, quantity, or name)
      Build a GUI version using JavaFX
      Add user accounts and authentication
      Migrate to Spring Boot for cleaner dependency management and scalability

      -- Author

        Nathan Pombi
        Java Developer & Software Graduate