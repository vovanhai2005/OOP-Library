package org.example.ooplibrary.Controller;

import javafx.scene.control.DatePicker;

import java.sql.*;
import java.time.format.DateTimeFormatter;

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
            ResultSet resultSet = statement.executeQuery("select password from user_info where username=\"" + username + "\"");
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

    static public boolean checkSignUp(String username, String password) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/librosync_db", USER, PASSWORD
            );
            Statement statement = connection.createStatement();
            ResultSet checkResult = statement.executeQuery("SELECT COUNT(*) FROM user_info WHERE username = '" + username + "'");

            checkResult.next();
            int count = checkResult.getInt(1);

            if (count > 0) {
                System.out.println("Username already exists. Please choose a different username.");
                connection.close();
                return false;
            }


            connection.close();
            return true;

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

            //Create a "user_info" table if it doesn't exist
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS `user_info` (\n" +
                    "  `username` varchar(32) NOT NULL,\n" +
                    "  `password` varchar(32) DEFAULT NULL,\n" +
                    "  `fullName` varchar(255) DEFAULT NULL,\n" +
                    "  `dateOfBirth` date DEFAULT NULL,\n" +
                    "  `gender` varchar(16) DEFAULT NULL,\n" +
                    "  `email` varchar(255) DEFAULT NULL,\n" +
                    "  `phoneNumber` varchar(16) DEFAULT NULL,\n" +
                    "  `isAdmin` tinyint(1) DEFAULT NULL\n" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;");
            statement.executeUpdate("ALTER TABLE `user_info` " +
                    "ADD PRIMARY KEY (`username`);");

            //Add admin account
            statement.executeUpdate("INSERT INTO `user_info` (`username`, `password`" +
                    ", `fullName`, `dateOfBirth`, `gender`, `email`, `phoneNumber`, `isAdmin`)" +
                    " VALUES ('admin', '1', NULL, NULL, NULL, NULL, NULL, '1');");

            //Create a "book_info" table if it doesn't exist
            statement.executeUpdate("CREATE TABLE `book_info` (\n" +
                    "  `ISBN` varchar(32) NOT NULL,\n" +
                    "  `bookName` varchar(255) DEFAULT NULL,\n" +
                    "  `yearOfPublication` date DEFAULT NULL,\n" +
                    "  `author` varchar(255) DEFAULT NULL,\n" +
                    "  `genre` varchar(255) DEFAULT NULL,\n" +
                    "  `description` mediumtext DEFAULT NULL\n" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;");
            statement.executeUpdate("ALTER TABLE `book_info`\n" +
                    "  ADD PRIMARY KEY (`ISBN`);");

            //Create a "book_loans" table if it doesn't exist
            statement.executeUpdate("CREATE TABLE `book_loans` (\n" +
                    "  `ISBN` varchar(32) NOT NULL,\n" +
                    "  `username` varchar(32) NOT NULL,\n" +
                    "  `note` text DEFAULT NULL,\n" +
                    "  `dueDate` date DEFAULT NULL,\n" +
                    "  `returnDate` date DEFAULT NULL\n" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;");
            statement.executeUpdate("ALTER TABLE `book_loans`\n" +
                    "  ADD PRIMARY KEY (`ISBN`,`username`);");


            connection.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    static public String getDateOfBirthAsString(DatePicker dateOfBirth) {
        if (dateOfBirth.getValue() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // Định dạng ngày theo ý bạn
            return dateOfBirth.getValue().format(formatter);
        }
        return ""; // Trả về chuỗi rỗng nếu chưa chọn ngày
    }

    static public void addUser(String username,
                                 String password,
                                 String fullName,
                                 String dateOfBirth,
                                 String email) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/librosync_db", USER, PASSWORD
            );
            Statement statement = connection.createStatement();

            // If username doesn't exist, add the new user to the database
            statement.executeUpdate("INSERT INTO `user_info` (`username`, `password`" +
                    ", `fullName`, `dateOfBirth`, `gender`, `email`, `phoneNumber`, `isAdmin`)" +
                    " VALUES ('" + username + "', ' " + password + " ', '" + fullName + "', '" +
                    dateOfBirth + "', NULL, '" + email + "', NULL, '0');");

            connection.close();


        } catch (Exception e) {
            System.out.println(e);
        }

    }


}
