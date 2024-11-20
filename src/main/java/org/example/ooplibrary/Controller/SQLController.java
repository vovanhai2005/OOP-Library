package org.example.ooplibrary.Controller;

import javafx.collections.ObservableList;
import javafx.scene.control.DatePicker;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.example.ooplibrary.Object.Book;
import org.example.ooplibrary.Object.BookLoan;
import org.example.ooplibrary.Object.User;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;

public class SQLController {

    // Change "user" and "password" variables to your MySQL username and password
    final static private String USER = "root";
    final static private String PASSWORD = "";

    public static void initialize() {
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
                    "  `userImage` mediumblob NOT NULL,\n" +
                    "  `isAdmin` tinyint(1) DEFAULT NULL\n" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;");
            statement.executeUpdate("ALTER TABLE `user_info` " +
                    "ADD PRIMARY KEY (`username`);");

            //Add admin account
            statement.executeUpdate("INSERT INTO `user_info` (`username`, `password`" +
                    ", `fullName`, `dateOfBirth`, `gender`, `email`, `phoneNumber`, `isAdmin`)" +
                    " VALUES ('admin', '1', NULL, NULL, NULL, NULL, NULL, '1');");

            //Create a "book_info" table if it doesn't exist
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS `book_info` (\n" +
                    "  `ISBN` varchar(32) NOT NULL,\n" +
                    "  `bookName` varchar(255) DEFAULT NULL,\n" +
                    "  `yearOfPublication` varchar(255) DEFAULT NULL,\n" +
                    "  `author` varchar(255) DEFAULT NULL,\n" +
                    "  `genre` varchar(255) DEFAULT NULL,\n" +
                    "  `description` mediumtext DEFAULT NULL,\n" +
                    "  `bookImage` mediumblob DEFAULT NULL\n" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;");
            statement.executeUpdate("ALTER TABLE `book_info`\n" +
                    "  ADD PRIMARY KEY (`ISBN`);");

            //Create a "book_loans" table if it doesn't exist
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS `book_loans` (\n" +
                    "  `bookLoanID` int(11) NOT NULL,\n" +
                    "  `ISBN` varchar(32) NOT NULL,\n" +
                    "  `username` varchar(32) NOT NULL,\n" +
                    "  `note` text DEFAULT NULL,\n" +
                    "  `dueDate` date DEFAULT NULL,\n" +
                    "  `returnDate` date DEFAULT NULL\n" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;");
            statement.executeUpdate("ALTER TABLE `book_loans`\n" +
                    "  ADD PRIMARY KEY (`bookLoanID`,`ISBN`,`username`);");


            connection.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static boolean checkPassword(String username, String password) {
        System.out.println("username: " + username);
        System.out.println("password: " + password);
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/librosync_db", USER, PASSWORD
            );
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select password from user_info where username= '" + username + "'");

            if (resultSet.next()) {
                System.out.println("password: " + resultSet.getString(1));
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

    public static boolean checkSignUp(String username) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/librosync_db", USER, PASSWORD
            );
            Statement statement = connection.createStatement();
            ResultSet checkResult = statement.executeQuery("SELECT COUNT(*) FROM user_info WHERE username = '" + username + "'");

            if (checkResult.next()) {
                int count = checkResult.getInt(1);

                if (count > 0) {
                    System.out.println("Username already exists. Please choose a different username.");
                    connection.close();
                    return false;
                }
            }


            connection.close();
            return true;

        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    public static String getDateOfBirthAsString(DatePicker dateOfBirth) {
        if (dateOfBirth.getValue() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return dateOfBirth.getValue().format(formatter);
        }
        return "";
    }

    public static boolean addBook(String ISBN,
                                  String bookName,
                                  String yearOfPublication,
                                  String author,
                                  String genre,
                                  String description,
                                  byte[] bookImage) {
        try {
            if (ISBN.isEmpty()) {
                System.out.println("ISBN cannot be empty.");
                return false;
            }
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/librosync_db", USER, PASSWORD
            );
            Statement statement = connection.createStatement();
            //Check if ISBN already exists
            ResultSet checkResult = statement.executeQuery("SELECT COUNT(*) FROM book_info WHERE ISBN = '" + ISBN + "';");
            if (checkResult.next()) {
                if (checkResult.getInt(1) > 0) {
                    System.out.println("Book already exists. Please choose a different ISBN.");
                    connection.close();
                    return false;
                }
            }
            // If ISBN doesn't exist, add the new book to the database
            statement.executeUpdate("INSERT INTO `book_info` (`ISBN`, `bookName`" +
                    ", `yearOfPublication`, `author`, `genre`, `description`,`bookImage`)" +
                    " VALUES ('" + ISBN + "', ' " + bookName + " ', '" + yearOfPublication + "', '" +
                    author + "', '" + genre + "', '" + description + "', '" + convertByteArrayToString(bookImage) + "');");
            connection.close();

        } catch (Exception e) {
            System.out.println(e);
        }
        return true;

    }

    public static String convertByteArrayToString(byte[] byteArray) {
        if (byteArray == null) {
            return null;  // Nếu byte array là null, trả về null
        }
        // Mã hóa mảng byte thành chuỗi Base64
        return Base64.getEncoder().encodeToString(byteArray);
    }

    public static byte[] convertStringToByteArray(String string) {
        if (string == null) {
            return null;  // Nếu byte array là null, trả về null
        }
        // Mã hóa mảng byte thành chuỗi Base64
        return Base64.getDecoder().decode(string);
    }


    public static ArrayList<Book> getBookInfoData() {
        ArrayList<Book> data = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/librosync_db", USER, PASSWORD
            );
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM book_info");

            while (resultSet.next()) {
                data.add(new Book(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6), convertStringToByteArray(resultSet.getString(7))));
            }
            connection.close();

        } catch (Exception e) {
            System.out.println(e);
        }
        return data;
    }

    public static ArrayList<Book> getBookInfoDataWithKeyword(String keyword) {
        ArrayList<Book> data = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/librosync_db", USER, PASSWORD
            );
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM book_info WHERE bookName LIKE '%" + keyword + "%' OR author LIKE '%" + keyword + "%' OR genre LIKE '%" + keyword + "%' OR yearOfPublication LIKE '%" + keyword + "%' OR ISBN LIKE '%" + keyword + "%';");


            while (resultSet.next()) {
                data.add(new Book(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6), convertStringToByteArray(resultSet.getString(7))));
            }
            connection.close();

        } catch (Exception e) {
            System.out.println(e);
        }
        return data;
    }

    public static boolean deleteBook(String ISBN) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/librosync_db", USER, PASSWORD
            );
            Statement statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM book_info WHERE ISBN = '" + ISBN + "';");
            connection.close();

        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        return true;
    }

    public static void addUser(String username,
                               String password,
                               String fullName,
                               String dateOfBirth,
                               String email,
                               String phoneNumber,
                               byte[] userImage){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/librosync_db", USER, PASSWORD
            );
            Statement statement = connection.createStatement();

            // If username doesn't exist, add the new user to the database
            statement.executeUpdate("INSERT INTO `user_info` (`username`, `password`" +
                    ", `fullName`, `dateOfBirth`, `gender`, `email`, `phoneNumber`, `userImage`, `isAdmin`)" +
                    " VALUES ('" + username + "', '" + password + "', '" + fullName + "', '" +
                    dateOfBirth + "', NULL, '" + email + "', '" + phoneNumber +  "','" + convertByteArrayToString(userImage) +"',  '0');");
            connection.close();


        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public static ArrayList<User> getUserInfoData() {
        ArrayList<User> data = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/librosync_db", USER, PASSWORD
            );
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM user_info WHERE isAdmin = 0");

            while (resultSet.next()) {
                data.add(new User(resultSet.getString(1), resultSet.getString(3), resultSet.getString(5), resultSet.getString(4), resultSet.getString(6), resultSet.getString(7), convertStringToByteArray(resultSet.getString(8))));
            }
            connection.close();

        } catch (Exception e) {
            System.out.println(e);
        }
        return data;
    }

    public static ArrayList<User> getUserInfoDataWithKeyword(String keyword) {
        ArrayList<User> data = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/librosync_db", USER, PASSWORD
            );
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM user_info WHERE isAdmin = 0 AND ( username LIKE '%" + keyword + "%' OR fullName LIKE '%" + keyword + "%' OR gender LIKE '%" + keyword + "%' OR email LIKE '%" + keyword + "%' OR phoneNumber LIKE '%" + keyword + "%');");

            while (resultSet.next()) {
                data.add(new User(resultSet.getString(1), resultSet.getString(3), resultSet.getString(5), resultSet.getString(4), resultSet.getString(6), resultSet.getString(7), convertStringToByteArray(resultSet.getString(8))));
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        return data;
    }

    public static boolean deleteBookLoan(String bookLoanID) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/librosync_db", USER, PASSWORD
            );
            Statement statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM book_loans WHERE bookLoanID = '" + bookLoanID + "';");
            connection.close();

        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        return true;
    }

    public static ArrayList<BookLoan> getBookLoansData() {
        ArrayList<BookLoan> data = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/librosync_db", USER, PASSWORD
            );
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT bl.bookLoanID, b.bookName, u.fullName, bl.dueDate, bl.returnDate, bl.note\n" +
                    "FROM book_loans bl\n" +
                    "JOIN book_info b ON b.ISBN = bl.ISBN\n" +
                    "JOIN user_info u ON u.username = bl.username;");

            while (resultSet.next()) {
                data.add(new BookLoan(resultSet.getString(1),
                                      resultSet.getString(2),
                                      resultSet.getString(3),
                                      resultSet.getString(4),
                                      resultSet.getString(5),
                                      resultSet.getString(6)
                                     )
                        );
            }
            connection.close();

        } catch (Exception e) {
            System.out.println(e);
        }
        return data;
    }

    public static ArrayList<BookLoan> getBookLoansDataWithKeyword(String keyword) {
        ArrayList<BookLoan> data = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/librosync_db", USER, PASSWORD
            );
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT bl.bookLoanID, b.bookName, u.fullName, bl.dueDate, bl.returnDate, bl.note\n" +
                    "FROM book_loans bl\n" +
                    "JOIN book_info b ON b.ISBN = bl.ISBN\n" +
                    "JOIN user_info u ON u.username = bl.username\n" +
                    "WHERE b.bookName LIKE '%" + keyword + "%' OR u.fullName LIKE '%" + keyword + "%' OR bl.dueDate LIKE '%" + keyword + "%' OR bl.returnDate LIKE '%" + keyword + "%' OR bl.note LIKE '%" + keyword + "%';");

            while (resultSet.next()) {
                data.add(new BookLoan(resultSet.getString(1),
                                      resultSet.getString(2),
                                      resultSet.getString(3),
                                      resultSet.getString(4),
                                      resultSet.getString(5),
                                      resultSet.getString(6)
                                     )
                        );
            }
            connection.close();

        } catch (Exception e) {
            System.out.println(e);
        }
        return data;
    }

    public static byte[] convertImageViewToBlob(ImageView imageView) {
        Image image = imageView.getImage();  // Lấy Image từ ImageView

        if (image == null) {
            return null; // Nếu không có hình ảnh thì trả về null
        }

        // Chuyển Image thành BufferedImage
        BufferedImage bufferedImage = javafx.embed.swing.SwingFXUtils.fromFXImage(image, null);

        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            // Ghi ảnh dưới dạng JPEG vào ByteArrayOutputStream
            ImageIO.write(bufferedImage, "jpg", byteArrayOutputStream);

            // Trả về mảng byte của ảnh (BLOB)
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
