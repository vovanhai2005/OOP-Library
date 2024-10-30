package org.example.ooplibrary.Controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class SQLController {

    // Change "user" and "password" variables to your MySQL username and password
    final static private String USER = "root";
    final static private String PASSWORD = "";


    static public boolean checkPassword(String username, String password) {
        System.out.println("username: " + username);
        System.out.println("password: " + password);
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/librosync_db", USER, PASSWORD
            );
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select password from account_info where username=\"" + username + "\"");
            if (resultSet.next()) {
                if (resultSet.getString(1).equals(password)) {
                    return true;
                }
            }
            connection.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;


    }

    static public void initialize() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306", USER, PASSWORD
            );

            //Create a "librosync_db" database if it doesn't exist
            Statement statement = connection.createStatement();
            statement.executeUpdate("SET SQL_MODE = \"NO_AUTO_VALUE_ON_ZERO\"");
            statement.executeUpdate("SET time_zone = \"+07:00\";");
            statement.executeUpdate("CREATE DATABASE IF NOT EXISTS librosync_db");

            //Go to "librosync_db" database
            statement.executeUpdate("USE librosync_db");

            //Create a "account_info" table if it doesn't exist
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS `account_info` (\n" +
                    "  `accountID` int(11) NOT NULL,\n" +
                    "  `username` varchar(255) DEFAULT NULL,\n" +
                    "  `password` varchar(255) DEFAULT NULL\n" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;");
            statement.executeUpdate("ALTER TABLE `account_info`\n" +
                    "  ADD PRIMARY KEY (`accountID`);");
            //I will fix this later so that it won't drop sql syntax error exception - Duc
            statement.executeUpdate("INSERT INTO `account_info` (`accountID`, `username`, `password`) VALUES\n" +
                    "(1, 'admin', 'root'),\n" +
                    "(2, 'leeminhduc2', '123456') ");

            //Create a "books" table if it doesn't exist
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS `books` (\n" +
                    "  `bookID` int(11) NOT NULL,\n" +
                    "  `bookName` int(11) DEFAULT NULL,\n" +
                    "  `publishDate` date DEFAULT NULL,\n" +
                    "  `author` int(11) DEFAULT NULL,\n" +
                    "  `genre` int(11) DEFAULT NULL,\n" +
                    "  `borrowerID` int(11) NOT NULL\n" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;\n");
            statement.executeUpdate("ALTER TABLE `books`\n" +
                    "  ADD PRIMARY KEY (`bookID`);");

            //Create a "users" table if it doesn't exist
            statement.executeUpdate("CREATE TABLE `users` (\n" +
                    "  `userID` int(11) NOT NULL,\n" +
                    "  `username` varchar(255) DEFAULT NULL,\n" +
                    "  `email` varchar(255) DEFAULT NULL,\n" +
                    "  `phoneNumber` varchar(11) DEFAULT NULL\n" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;");
            statement.executeUpdate("ALTER TABLE `users`\n" +
                    "  ADD PRIMARY KEY (`userID`);");

            //Create a "borrow_event" table if it doesn't exist
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS `borrow_event` (\n" +
                    "  `bookID` int(11) NOT NULL,\n" +
                    "  `userID` int(11) NOT NULL,\n" +
                    "  `dueDate` date NOT NULL\n" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;");
            statement.executeUpdate("ALTER TABLE `borrow_event`\n" +
                    "  ADD PRIMARY KEY (`bookID`,`userID`);");

            //Create a "return_event" table if it doesn't exist
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS `return_event` (\n" +
                    "  `bookID` int(11) NOT NULL,\n" +
                    "  `userID` int(11) NOT NULL,\n" +
                    "  `returnDate` date NOT NULL\n" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;");
            statement.executeUpdate("ALTER TABLE `return_event`\n" +
                    "  ADD PRIMARY KEY (`bookID`,`userID`);");


            connection.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Add a book to the database.
     */
    static public void addBook(String bookName, String publishDate, String author, String genre, String description) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/librosync_db", USER, PASSWORD
            );
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT MAX(book_id) FROM books");
            int bookID = 0;
            if (resultSet.next()) {
                bookID = resultSet.getInt(1) + 1;
            }
            statement.executeUpdate("INSERT INTO books (book_id, book_name, publish_date, author, genre, description) VALUES (" + bookID + ", \"" + bookName + "\", \"" + publishDate + "\", \"" + author + "\", \"" + genre + "\", \"" + description + "\")");
            connection.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Remove a book from the database.
     */
    static public void removeBook(int bookID) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/librosync_db", USER, PASSWORD
            );
            Statement statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM books WHERE book_id = " + bookID);
            connection.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    static public void changeBookDetail(int bookID, String bookName, String publishDate, String author, String genre, String description) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/librosync_db", USER, PASSWORD
            );
            Statement statement = connection.createStatement();
            statement.executeUpdate("UPDATE books SET book_name = \"" + bookName + "\", publish_date = \"" + publishDate + "\", author = \"" + author + "\", genre = \"" + genre + "\", description = \"" + description + "\" WHERE book_id = " + bookID);
            connection.close();
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    /**
     * Add a user to the database with an user ID.
     */
    static public void addUser(String username, String email, String phoneNumber) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/librosync_db", USER, PASSWORD
            );
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT MAX(user_id) FROM users");
            int userID = 0;
            if (resultSet.next()) {
                userID = resultSet.getInt(1) + 1;
            }
            statement.executeUpdate("INSERT INTO users (user_id, username, email, phone_number) VALUES (" + userID + ", \"" + username + "\", \"" + email + "\", \"" + phoneNumber + "\")");
            connection.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Remove a user from the database.
     */
    static public void addBorrowEvent(int bookID, int userID, String dueDate) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/librosync_db", USER, PASSWORD
            );
            Statement statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO borrow_event (book_id, user_id, due_date) VALUES (" + bookID + ", " + userID + ", \"" + dueDate + "\")");
            connection.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Add a return event to the database.
     */
    static public void addReturnEvent(int bookID, int userID, String returnDate, String note) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/librosync_db", USER, PASSWORD
            );
            Statement statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO return_event (book_id, user_id, return_date, note) VALUES (" + bookID + ", " + userID + ", \"" + returnDate + "\", \"" + note + "\")");
            connection.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }


}
