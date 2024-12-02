package org.example.ooplibrary.Controller;

import javafx.collections.FXCollections;
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
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class SQLController {

    // Change "user" and "password" variables to your MySQL username and password
    final static private String USER = "root";
    final static private String PASSWORD = "";

    /**
     * Thêm ký tự '\' trước dấu " trong chuỗi
     * @param input Chuỗi cần chuẩn hóa
     * @return Chuỗi đã được chuẩn hóa
     */
    public static String normalizeString(String input) {
        if (input == null) {
            return null;
        }
        // Thay thế tất cả dấu " bằng \"
        return input.replace("\"", "\\\"");
    }

    /**
     * Tạo database có tên là "librosync_db", đồng thời tạo các bảng "user_info",
     * "book_info", "book_loans" nếu chưa tồn tại. Sau đó, thêm một tài khoản admin
     * dùng dể quản lý hệ thống.
     */
    public static void initialize() {
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306", USER, PASSWORD
            );

            //Create a "librosync_db" database if it doesn\"t exist
            Statement statement = connection.createStatement();
            statement.executeUpdate("SET SQL_MODE = \"NO_AUTO_VALUE_ON_ZERO\"");
            statement.executeUpdate("SET time_zone = \"+07:00\";");
            statement.executeUpdate("CREATE DATABASE IF NOT EXISTS librosync_db DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_vietnamese_ci;");

            //Go to "librosync_db" database
            statement.executeUpdate("USE librosync_db");

            //Create a "user_info" table if it doesn\"t exist
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS `user_info` (\n" +
                    "  `username` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_vietnamese_ci NOT NULL,\n" +
                    "  `password` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,\n" +
                    "  `fullName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,\n" +
                    "  `dateOfBirth` date DEFAULT NULL,\n" +
                    "  `gender` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,\n" +
                    "  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,\n" +
                    "  `phoneNumber` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,\n" +
                    "  `userImage` mediumblob NOT NULL,\n" +
                    "  `isAdmin` tinyint(1) DEFAULT NULL,\n" +
                    "  PRIMARY KEY (`username`)\n" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;");


            //Create a "book_info" table if it doesn\"t exist
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS `book_info` (\n" +
                    "  `ISBN` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_vietnamese_ci NOT NULL,\n" +
                    "  `bookName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,\n" +
                    "  `yearOfPublication` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,\n" +
                    "  `author` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,\n" +
                    "  `genre` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,\n" +
                    "  `description` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,\n" +
                    "  `bookImage` mediumblob DEFAULT NULL,\n" +
                    "  PRIMARY KEY (`ISBN`)\n" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;");


            //Create a "book_loans" table if it doesn\"t exist
            System.out.println("CREATE TABLE IF NOT EXISTS `book_loans` (\n" +
                    "  `bookLoanID` int(11) NOT NULL AUTO_INCREMENT,\n" +
                    "  `ISBN` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_vietnamese_ci NOT NULL,\n" +
                    "  `username` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_vietnamese_ci NOT NULL,\n" +
                    "  `note` text CHARACTER SET utf8mb4 COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,\n" +
                    "  `dueDate` date DEFAULT NULL,\n" +
                    "  `returnDate` date DEFAULT NULL,\n" +
                    "  PRIMARY KEY (`bookLoanID`,`ISBN`,`username`)\n" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS `book_loans` (\n" +
                    "  `bookLoanID` int(11) NOT NULL AUTO_INCREMENT,\n" +
                    "  `ISBN` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_vietnamese_ci NOT NULL,\n" +
                    "  `username` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_vietnamese_ci NOT NULL,\n" +
                    "  `note` text CHARACTER SET utf8mb4 COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,\n" +
                    "  `borrowDate` DATE NOT NULL DEFAULT CURRENT_TIMESTAMP,\n" +
                    "  `dueDate` date DEFAULT NULL,\n" +
                    "  `returnDate` date DEFAULT NULL,\n" +
                    "  PRIMARY KEY (`bookLoanID`,`ISBN`,`username`)\n   " +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;");

            //Create a "user_ratings" table if it doesn't exist
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS `user_ratings` (\n" +
                    "  `ratingID` int(11) NOT NULL AUTO_INCREMENT,\n" +
                    "  `ISBN` varchar(32) NOT NULL,\n" +
                    "  `username` varchar(32) NOT NULL,\n" +
                    "  `rating` float NOT NULL,\n" +
                    "  `review` mediumtext NOT NULL,\n" +
                    "  `ratingDate` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,\n" +
                    "  PRIMARY KEY (`ratingID`)\n" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;");


            //Add admin account
            statement.executeQuery("SELECT COUNT(*) FROM user_info WHERE username = \"admin\";");
            ResultSet checkResult = statement.getResultSet();
            if (checkResult.next()) {
                int count = checkResult.getInt(1);

                if (count == 0) {
                    statement.executeUpdate("INSERT INTO `user_info` (`username`, `password`, `fullName`, `dateOfBirth`, `gender`, `email`, `phoneNumber`, `isAdmin`) "
                            + "VALUES (\"" + normalizeString("admin") + "\", \""
                            + normalizeString("1") + "\", NULL, NULL, NULL, NULL, NULL, \""
                            + normalizeString("1") + "\");");
                }
            }
            connection.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    //User

    /**
     * Kiểm tra xem người dùng có tồn tại ko
     *
     * @param username Tên tài khoản
     * @param password Mật khẩu
     * @return boolean
     */
    public static boolean checkPassword(String username, String password) {
        System.out.println("username: " + username);
        System.out.println("password: " + password);
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/librosync_db?useUnicode=true&characterEncoding=UTF-8", USER, PASSWORD
            );
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT password FROM user_info WHERE username= \"" + normalizeString(username) + "\"");

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

    /**
     * Kiểm tra xem tên tài khoản đã tồn tại chưa, trả về true nếu chưa tồn tại, ngược lại trả về false
     *
     * @param username Tên tài khoản
     * @return boolean
     */
    public static boolean checkSignUp(String username) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/librosync_db?useUnicode=true&characterEncoding=UTF-8", USER, PASSWORD
            );
            Statement statement = connection.createStatement();
            ResultSet checkResult = statement.executeQuery("SELECT COUNT(*) FROM user_info WHERE username = \"" + normalizeString(username) + "\"");

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

    /**
     * Trả về ngày sinh dưới dạng chuỗi từ DatePicker
     *
     * @param dateOfBirth
     * @return
     */
    public static String getDateOfBirthAsString(DatePicker dateOfBirth) {
        if (dateOfBirth.getValue() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return dateOfBirth.getValue().format(formatter);
        }
        return "";
    }

    /**
     * Thêm người dùng mới vào database
     *
     * @param username    Tên đăng nhập của người dùng
     * @param password    Mật khẩu của người dùng
     * @param fullName    Họ và tên của người dùng
     * @param dateOfBirth Ngày sinh của người dùng
     * @param email       Email của người dùng
     * @param phoneNumber Số điện thoại của người dùng
     * @param userImage   Hình ảnh người dùng
     * @param gender      Giới tính của người dùng
     */
    public static void addUser(String username,
                               String password,
                               String fullName,
                               String dateOfBirth,
                               String email,
                               String phoneNumber,
                               byte[] userImage,
                               String gender) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/librosync_db?useUnicode=true&characterEncoding=UTF-8", USER, PASSWORD
            );
            Statement statement = connection.createStatement();

            // If username doesn\"t exist, add the new user to the database
            statement.executeUpdate("INSERT INTO `user_info` (`username`, `password`, `fullName`, `dateOfBirth`, `gender`, `email`, `phoneNumber`, `userImage`, `isAdmin`) "
                    + "VALUES (\"" + normalizeString(username) + "\", \""
                    + normalizeString(password) + "\", \"" + normalizeString(fullName) + "\", \""
                    + normalizeString(dateOfBirth) + "\", \"" + normalizeString(gender) + "\", \""
                    + normalizeString(email) + "\", \"" + normalizeString(phoneNumber) + "\",\""
                    + convertByteArrayToString(userImage) + "\",  \"0\");");

            connection.close();
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    /**
     * Lấy thông tin người dùng từ database
     *
     * @return ArrayList<User>
     */
    public static ArrayList<User> getUserInfoData() {
        ArrayList<User> data = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/librosync_db?useUnicode=true&characterEncoding=UTF-8", USER, PASSWORD
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

    /**
     * Lấy thông tin người dùng từ database với từ khóa
     *
     * @param keyword Từ khóa tìm kiếm
     * @return ArrayList<User>
     */
    public static ArrayList<User> getUserInfoDataWithKeyword(String keyword) {
        ArrayList<User> data = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/librosync_db?useUnicode=true&characterEncoding=UTF-8", USER, PASSWORD
            );
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM user_info WHERE isAdmin = 0 AND ( username LIKE \"%" + normalizeString(keyword) + "%\" OR fullName LIKE \"%" + normalizeString(keyword) + "%\" OR gender LIKE \"%" + normalizeString(keyword) + "%\" OR email LIKE \"%" + normalizeString(keyword) + "%\" OR phoneNumber LIKE \"%" + normalizeString(keyword) + "%\");");

            while (resultSet.next()) {
                data.add(new User(resultSet.getString(1), resultSet.getString(3), resultSet.getString(5), resultSet.getString(4), resultSet.getString(6), resultSet.getString(7), convertStringToByteArray(resultSet.getString(8))));
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        return data;
    }

    /**
     * Kiểm tra xem người dùng có phải là admin không
     *
     * @param username Tên đăng nhập của người dùng
     * @return boolean
     */
    public static boolean isAdmin(String username) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/librosync_db?useUnicode=true&characterEncoding=UTF-8", USER, PASSWORD
            );
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT isAdmin FROM user_info WHERE username = \"" + username + "\";");

            if (resultSet.next()) {
                if (resultSet.getInt(1) == 1) {
                    return true;
                }
            }
            connection.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    /**
     * Lấy thông tin người dùng từ database với tên đăng nhập
     *
     * @param username Tên đăng nhập của người dùng
     * @return User
     */
    public static User getUserInfoDataByUsername(String username) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/librosync_db?useUnicode=true&characterEncoding=UTF-8", USER, PASSWORD
            );
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM user_info WHERE username = \"" + username + "\";");

            if (resultSet.next()) {
                return new User(resultSet.getString(1), resultSet.getString(3), resultSet.getString(5), resultSet.getString(4), resultSet.getString(6), resultSet.getString(7), convertStringToByteArray(resultSet.getString(8)));
            }
            connection.close();

        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    /**
     * Trả về mật khẩu của người dùng
     *
     * @param username Tên đăng nhập của người dùng
     * @return String
     */
    public static String getUserPassword(String username) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/librosync_db?useUnicode=true&characterEncoding=UTF-8", USER, PASSWORD
            );
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT password FROM user_info WHERE username = \"" + username + "\";");

            if (resultSet.next()) {
                return resultSet.getString(1);
            }
            connection.close();

        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    /**
     * Cập nhật mật khẩu người dùng
     *
     * @param username    Tên đăng nhập của người dùng
     * @param newPassword Mật khẩu mới
     */
    public static void updateUserPassword(String username, String newPassword) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/librosync_db?useUnicode=true&characterEncoding=UTF-8", USER, PASSWORD
            );
            Statement statement = connection.createStatement();

            statement.executeUpdate("UPDATE user_info SET password = \"" + newPassword + "\" WHERE username = \"" + username + "\";");

            connection.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Xoá người dùng khỏi database
     *
     * @param username Tên đăng nhập của người dùng
     */
    public static void deleteUser(String username) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/librosync_db?useUnicode=true&characterEncoding=UTF-8", USER, PASSWORD
            );
            Statement statement = connection.createStatement();

            statement.executeUpdate("DELETE FROM user_info WHERE username = \"" + username + "\";");

            connection.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // Book

    /**
     * Thêm sách mới vào database, trả về true nếu thêm sách thành công, ngược lại trả về false
     *
     * @param ISBN              Mã ISBN
     * @param bookName          Tên sách
     * @param yearOfPublication Năm xuất bản
     * @param author            Tác giả
     * @param genre             Thể loại
     * @param description       Mô tả
     * @param bookImage         Hình ảnh sách
     * @return boolean
     */
    public static boolean addBook(String ISBN,
                                  String bookName,
                                  String yearOfPublication,
                                  String author,
                                  String genre,
                                  String description,
                                  byte[] bookImage) {
        try {
            System.out.println(bookName);
            if (ISBN.isEmpty()) {
                System.out.println("ISBN cannot be empty.");
                return false;
            }
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/librosync_db?useUnicode=true&characterEncoding=UTF-8", USER, PASSWORD
            );
            Statement statement = connection.createStatement();
            //Check if ISBN already exists
            ResultSet checkResult = statement.executeQuery("SELECT COUNT(*) FROM book_info WHERE ISBN = \"" + ISBN + "\";");
            if (checkResult.next()) {
                if (checkResult.getInt(1) > 0) {
                    System.out.println("Book already exists. Please choose a different ISBN.");
                    connection.close();
                    return false;
                }
            }
            // If ISBN doesn\"t exist, add the new book to the database
            statement.executeUpdate("INSERT INTO `book_info` (`ISBN`, `bookName`, `yearOfPublication`, `author`, `genre`, `description`, `bookImage`) "
                    + "VALUES (\"" + normalizeString(ISBN) + "\", \""
                    + normalizeString(bookName) + "\", \"" + normalizeString(yearOfPublication) + "\", \""
                    + normalizeString(author) + "\", \"" + normalizeString(genre) + "\", \""
                    + normalizeString(description) + "\", \"" + convertByteArrayToString(bookImage) + "\");");

            connection.close();

        } catch (Exception e) {
            System.out.println(e);
        }
        return true;

    }

    public static ArrayList<Book> getBookInfoData() {
        ArrayList<Book> data = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/librosync_db?useUnicode=true&characterEncoding=UTF-8", USER, PASSWORD
            );
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM book_info");

            while (resultSet.next()) {
                data.add(new Book(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), normalizeToList(resultSet.getString(5)), resultSet.getString(6), convertStringToByteArray(resultSet.getString(7))));
            }
            connection.close();

        } catch (Exception e) {
            System.out.println(e);
        }
        return data;
    }

    /**
     * Lấy thông tin sách từ database
     *
     * @return ArrayList<Book>
     */
    public static ArrayList<Book> getBookInfoDataSortedByYearPublished() {
        ArrayList<Book> data = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/librosync_db?useUnicode=true&characterEncoding=UTF-8", USER, PASSWORD
            );
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM book_info ORDER BY yearOfPublication DESC");

            while (resultSet.next()) {
                data.add(new Book(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), normalizeToList(resultSet.getString(5)), resultSet.getString(6), convertStringToByteArray(resultSet.getString(7))));
            }
            connection.close();

        } catch (Exception e) {
            System.out.println(e);
        }
        return data;
    }

    /**
     * Lấy thông tin sách từ database với từ khóa
     *
     * @param keyword Từ khóa tìm kiếm
     * @return ArrayList<Book>
     */
    public static ArrayList<Book> getBookInfoDataWithKeyword(String keyword) {
        ArrayList<Book> data = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/librosync_db?useUnicode=true&characterEncoding=UTF-8", USER, PASSWORD
            );
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM user_info WHERE isAdmin = 0 AND ( username LIKE \"%" + normalizeString(keyword) + "%\" OR fullName LIKE \"%" + normalizeString(keyword) + "%\" OR gender LIKE \"%" + normalizeString(keyword) + "%\" OR email LIKE \"%" + normalizeString(keyword) + "%\" OR phoneNumber LIKE \"%" + normalizeString(keyword) + "%\");");


            while (resultSet.next()) {
                data.add(new Book(resultSet.getString(1), resultSet.getString(
                        2), resultSet.getString(3), resultSet.getString(4), normalizeToList(resultSet.getString(5)), resultSet.getString(6), convertStringToByteArray(resultSet.getString(7))));
            }
            connection.close();

        } catch (Exception e) {
            System.out.println(e)
            ;
        }
        return data;
    }


    /**
     * Lấy thông tin sách từ database với mã ISBN
     *
     * @param ISBN Mã ISBN của sách
     * @return Book
     */
    public static Book getBookInfoDataWithISBN(String ISBN) {
        Book book = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/librosync_db?useUnicode=true&characterEncoding=UTF-8", USER, PASSWORD
            );
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM book_info WHERE ISBN = \"" + ISBN + "\";");

            if (resultSet.next()) {
                book = new Book(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), normalizeToList(resultSet.getString(5)), resultSet.getString(6), convertStringToByteArray(resultSet.getString(7)));
            }
            connection.close();

        } catch (Exception e) {
            System.out.println(e);
        }
        return book;
    }

    public static void updateUserInfo(User user) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/librosync_db?useUnicode=true&characterEncoding=UTF-8", USER, PASSWORD
            );

            PreparedStatement statement = connection.prepareStatement("UPDATE user_info SET fullName = ?, dateOfBirth = ?, gender = ?, email = ?, phoneNumber = ?, userImage = ? WHERE username = ?");
            statement.setString(1, user.getFullName());
            statement.setString(2, user.getDob());
            statement.setString(3, user.getGender());
            statement.setString(4, user.getEmail());
            statement.setString(5, user.getPhoneNumber());
            statement.setString(6, convertByteArrayToString(user.getImage()));
            statement.setString(7, user.getUsername());
            statement.executeUpdate();

            connection.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    //Book Loan

    /**
     * Thêm lượt mượn sách mới vào database và trả về mã lượt mượn sách
     *
     * @param ISBN                Mã ISBN của sách
     * @param username            Tên đăng nhập của người dùng
     * @param note                Ghi chú
     * @param dateOfBirthAsString Ngày hết hạn dưới dạng chuỗi
     * @return String
     */
    public static String addBookLoan(String ISBN, String username, String note, String dateOfBirthAsString) {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/librosync_db?useUnicode=true&characterEncoding=UTF-8", USER, PASSWORD
            );
            Statement statement = connection.createStatement();

            //Create a new book loan with auto increament id
            statement.executeUpdate("INSERT INTO `book_loans` (`ISBN`, `username`, `note`, `dueDate`) "
                    + "VALUES (\"" + normalizeString(ISBN) + "\", \""
                    + normalizeString(username) + "\", \"" + normalizeString(note) + "\", \""
                    + normalizeString(dateOfBirthAsString) + "\");");
            //returns the bookloanid
            ResultSet resultSet = statement.executeQuery("SELECT LAST_INSERT_ID();");

            if (resultSet.next()) {
                return resultSet.getString(1);
            }
            connection.close();

        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
        return null;
    }

    /**
     * Lấy thông tin lượt mượn sách từ database với mã lượt mượn sách
     *
     * @param bookLoanId
     * @return
     */
    public static BookLoan getBookLoansDataWithBookLoanID(String bookLoanId) {
        BookLoan bookLoan = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/librosync_db?useUnicode=true&characterEncoding=UTF-8", USER, PASSWORD
            );
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT bl.bookLoanID, b.bookName, u.fullName, bl.dueDate, bl.returnDate, bl.note\n" +
                    "FROM book_loans bl\n" +
                    "JOIN book_info b ON b.ISBN = bl.ISBN\n" +
                    "JOIN user_info u ON u.username = bl.username\n" +
                    "WHERE bl.bookLoanID = \"" + bookLoanId + "\";");

            if (resultSet.next()) {
                bookLoan = new BookLoan(resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6)
                );
            }
            connection.close();

        } catch (Exception e) {
            System.out.println(e);
        }
        return bookLoan;
    }

    /**
     * Trả về lượt mượn sách của người dùng
     *
     * @param username Tên đăng nhập của người dùng
     * @return ArrayList<BookLoan>
     */
    public static ArrayList<BookLoan> getBookLoansDataWithUser(String username) {
        ArrayList<BookLoan> data = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/librosync_db?useUnicode=true&characterEncoding=UTF-8", USER, PASSWORD
            );
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT bl.bookLoanID, b.bookName, bl.dueDate, bl.returnDate, bl.note\n" +
                    "FROM book_loans bl\n" +
                    "JOIN book_info b ON b.ISBN = bl.ISBN\n" +
                    "WHERE bl.username = \"" + username + "\";");
            System.out.println("SELECT bl.bookLoanID, b.bookName, bl.dueDate, bl.returnDate, bl.note\n" +
                    "FROM book_loans bl\n" +
                    "JOIN book_info b ON b.ISBN = bl.ISBN\n" +
                    "WHERE bl.username = \"" + username + "\";");

            while (resultSet.next()) {
                data.add(new BookLoan(resultSet.getString(1),
                                resultSet.getString(2),
                                null,
                                resultSet.getString(3),
                                resultSet.getString(4),
                                resultSet.getString(5)
                        )
                );
            }
            connection.close();

        } catch (Exception e) {
            System.out.println(e);
        }
        return data;
    }

    /**
     * Xóa lượt mượn sách có chỉ số là bookLoanID, trả về true nếu xóa thành công, ngược lại trả về false
     *
     * @param bookLoanID
     * @return
     */
    public static boolean deleteBookLoan(String bookLoanID) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/librosync_db?useUnicode=true&characterEncoding=UTF-8", USER, PASSWORD
            );
            Statement statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM book_loans WHERE bookLoanID = \"" + bookLoanID + "\";");
            connection.close();

        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        return true;
    }

    /**
     * Lấy thông tin lượt mượn sách từ database
     *
     * @return ArrayList<BookLoan>
     */
    public static ArrayList<BookLoan> getBookLoansData() {
        ArrayList<BookLoan> data = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/librosync_db?useUnicode=true&characterEncoding=UTF-8", USER, PASSWORD
            );
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT bl.bookLoanID, b.bookName, u.username, bl.dueDate, bl.returnDate, bl.note\n" +
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

    /**
     * Lấy thông tin lượt mượn sách từ database với từ khóa
     *
     * @param keyword Từ khóa tìm kiếm
     * @return ArrayList<BookLoan>
     */
    public static ArrayList<BookLoan> getBookLoansDataWithKeyword(String keyword) {
        ArrayList<BookLoan> data = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/librosync_db?useUnicode=true&characterEncoding=UTF-8", USER, PASSWORD
            );
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT bl.bookLoanID, b.bookName, u.fullName, bl.dueDate, bl.returnDate, bl.note\n" +
                    "FROM book_loans bl\n" +
                    "JOIN book_info b ON b.ISBN = bl.ISBN\n" +
                    "JOIN user_info u ON u.username = bl.username\n" +
                    "WHERE b.bookName LIKE \"%" + keyword + "%\" OR u.fullName LIKE \"%" + keyword + "%\" OR bl.dueDate LIKE \"%" + keyword + "%\" OR bl.returnDate LIKE \"%" + keyword + "%\" OR bl.note LIKE \"%" + keyword + "%\";");

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

    /**
     * Xóa sách khỏi database, trả về true nếu xóa sách thành công, ngược lại trả về false
     *
     * @param ISBN Mã ISBN của sách cần xóa
     * @return
     */
    public static boolean deleteBook(String ISBN) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/librosync_db?useUnicode=true&characterEncoding=UTF-8", USER, PASSWORD
            );
            Statement statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM book_info WHERE ISBN = \"" + ISBN + "\";");
            connection.close();

        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        return true;
    }

    // Functions for dashboards (why the fuck did you put in here vohai)

    /**
     * @return int[]
     */
    public static int[] estimateUserAge() {
        int[] ageGroups = new int[3];

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/librosync_db?useUnicode=true&characterEncoding=UTF-8", USER, PASSWORD
            );
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT dateOfBirth FROM user_info WHERE dateOfBirth IS NOT NULL");

            LocalDate currentDate = LocalDate.now();

            while (resultSet.next()) {
                String dateOfBirthString = resultSet.getString("dateOfBirth");
                LocalDate dateOfBirth = LocalDate.parse(dateOfBirthString);

                int age = Period.between(dateOfBirth, currentDate).getYears();

                if (age < 18) {
                    ageGroups[0]++;
                } else if (age <= 25) {
                    ageGroups[1]++;
                } else {
                    ageGroups[2]++;
                }
            }

            connection.close();
        } catch (Exception e) {
            System.out.println("Error estimating user age groups: " + e.getMessage());
        }

        return ageGroups;
    }

    /**
     * @return int[][]
     */
    public static int[][] estimateTransactions() {
        int[][] transactions = null; // Mảng lưu số lượng giao dịch mượn và trả trong từng khoảng thời gian

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Kết nối đến cơ sở dữ liệu
            try (Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/librosync_db?useUnicode=true&characterEncoding=UTF-8", USER, PASSWORD);
                 Statement statement = connection.createStatement()) {

                // Xác định ngày bắt đầu và ngày kết thúc (ngày 1 đến ngày cuối cùng của tháng trước)
                LocalDate endDate = LocalDate.now();
                LocalDate startDate = endDate.withDayOfMonth(1); // Ngày 1 của tháng trước

                // Chia khoảng thời gian thành các đoạn 5 ngày
                int timeRanges = (int) (java.time.Duration.between(startDate.atStartOfDay(), endDate.atStartOfDay()).toDays() / 5) + 1;

                transactions = new int[timeRanges][2]; // Cập nhật kích thước cho mảng dựa trên số khoảng

                // Truy vấn số lượng sách mượn và trả trong từng khoảng thời gian 5 ngày
                for (int i = 0; i < timeRanges; i++) {
                    LocalDate rangeStart = startDate.plusDays(i * 5); // Ngày bắt đầu của khoảng
                    LocalDate rangeEnd = rangeStart.plusDays(4); // Ngày kết thúc của khoảng

                    // Giới hạn ngày kết thúc không vượt quá ngày cuối của tháng trước
                    if (rangeEnd.isAfter(endDate)) {
                        rangeEnd = endDate;
                    }
                    // Truy vấn số lượng sách mượn trong khoảng thời gian này
                    ResultSet borrowResultSet = statement.executeQuery(
                            "SELECT COUNT(*) as borrowCount " +
                                    "FROM book_loans " +
                                    "WHERE borrowDate >= '" + rangeStart + "' AND borrowDate <= '" + rangeEnd + "';"
                    );

                    if (borrowResultSet.next()) {
                        transactions[i][0] = borrowResultSet.getInt("borrowCount"); // Số lượng mượn
                    }

                    // Truy vấn số lượng sách trả trong khoảng thời gian này
                    ResultSet returnResultSet = statement.executeQuery(
                            "SELECT COUNT(*) as returnCount " +
                                    "FROM book_loans " +
                                    "WHERE borrowDate >= '" + rangeStart + "' AND borrowDate <= '" + rangeEnd + "';"
                    );

                    if (returnResultSet.next()) {
                        transactions[i][1] = returnResultSet.getInt("returnCount"); // Số lượng trả
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error estimating borrow and return transactions: " + e.getMessage());
        }

        return transactions;
    }

    /**
     * @return Map<String, int [ ]>
     */
    public static Map<String, int[]> getTransactionsByGenres() {
        // Map lưu trữ số lượng giao dịch theo thể loại
        Map<String, int[]> genreTransactions = new HashMap<>();

        String query = """
                    SELECT genre,
                           SUM(CASE WHEN dueDate IS NOT NULL THEN 1 ELSE 0 END) AS borrow_count,
                           SUM(CASE WHEN returnDate IS NOT NULL THEN 1 ELSE 0 END) AS return_count
                    FROM book_loans
                    JOIN book_info ON book_loans.ISBN = book_info.ISBN
                    GROUP BY genre
                """;
        // Khối try-with-resources đảm bảo tự động đóng tài nguyên
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/librosync_db", "root", "");
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String genre = resultSet.getString("genre");
                int borrowCount = resultSet.getInt("borrow_count");
                int returnCount = resultSet.getInt("return_count");
                genreTransactions.put(genre, new int[]{borrowCount, returnCount});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return genreTransactions;
    }

    // Support functions

    /**
     * Chuyển đổi ImageView thành mảng byte[]
     *
     * @param imageView ImageView cần chuyển đổi
     * @return byte[]
     */
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

    /**
     * Chuyển đổi định dạng từ mảng byte[] sang chuỗi
     *
     * @param byteArray Mảng byte cần chuyển đổi
     * @return
     */
    public static String convertByteArrayToString(byte[] byteArray) {
        if (byteArray == null) {
            return null;  // Nếu byte array là null, trả về null
        }
        // Mã hóa mảng byte thành chuỗi Base64
        return Base64.getEncoder().encodeToString(byteArray);
    }

    /**
     * Chuyển đổi định dạng từ chuỗi sang mảng byte[]
     *
     * @param string Chuỗi cần chuyển đổi
     * @return
     */
    public static byte[] convertStringToByteArray(String string) {
        if (string == null) {
            return null;  // Nếu byte array là null, trả về null
        }
        // Mã hóa mảng byte thành chuỗi Base64
        return Base64.getDecoder().decode(string);
    }

    /**
     * Chuẩn hóa chuỗi thể loại sách thành danh sách thể loại sách
     *
     * @param genres Chuỗi thể loại sách
     * @return ObservableList<String>
     */
    private static ObservableList<String> normalizeToList(String genres) {
        String[] genresArray = genres.split(",");
        //Remove extra spaces
        for (int i = 0; i < genresArray.length; i++) {
            genresArray[i] = genresArray[i].trim();
        }
        return FXCollections.observableArrayList(genresArray);
    }

    public static double getRecentlyUserRating(String isbn, String username) {
        double rating = 0;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/librosync_db?useUnicode=true&characterEncoding=UTF-8", USER, PASSWORD
            );
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT rating FROM user_ratings WHERE ISBN = \"" + normalizeString(isbn) + "\" AND username = \"" + normalizeString(username) + "\" ORDER BY ratingDate DESC;");

            if (resultSet.next()) {
                rating = resultSet.getDouble(1);
            }
            connection.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return rating;
    }

    public static String getRecentlyUserComment(String isbn, String username) {
        String comment = "";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/librosync_db?useUnicode=true&characterEncoding=UTF-8", USER, PASSWORD
            );
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT review FROM user_ratings WHERE ISBN = \"" + normalizeString(isbn) + "\" AND username = \"" + normalizeString(username) + "\" ORDER BY ratingDate DESC;");

            if (resultSet.next()) {
                comment = resultSet.getString(1);
            }
            connection.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return comment;
    }

    public static ArrayList<String> getRecentlyUsernameFromUserRatingsByISBN(String ISBN) {
        ArrayList<String> data = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/librosync_db?useUnicode=true&characterEncoding=UTF-8", USER, PASSWORD
            );
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT username FROM user_ratings WHERE ISBN = \"" + normalizeString(ISBN) + "\" ORDER BY ratingDate DESC;");

            while (resultSet.next()) {
                data.add(resultSet.getString(1));
            }
            connection.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return data;
    }

    public static void addUserRatings(String ISBN, String username, double rating, String reviewText) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/librosync_db?useUnicode=true&characterEncoding=UTF-8", USER, PASSWORD
            );
            Statement statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO `user_ratings` (`ISBN`, `username`, `rating`, `review`) " +
                    "VALUES (\"" + normalizeString(ISBN) + "\", \"" + normalizeString(username) + "\", \"" + rating + "\", \"" + normalizeString(reviewText) + "\");");

            connection.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static boolean userReviewed(String text, String username) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/librosync_db?useUnicode=true&characterEncoding=UTF-8", USER, PASSWORD
            );
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM user_ratings WHERE ISBN = \"" + normalizeString(text) + "\" AND username = \"" + normalizeString(username) + "\";");

            if (resultSet.next()) {
                if (resultSet.getInt(1) > 0) {
                    return true;
                }
            }
            connection.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    public static int getBookCount() {
        int count = 0;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/librosync_db?useUnicode=true&characterEncoding=UTF-8", USER, PASSWORD
            );
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM book_info;");

            if (resultSet.next()) {
                count = resultSet.getInt(1);
            }
            connection.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return count;
    }

    public static int getUserCount() {
        int count = 0;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/librosync_db?useUnicode=true&characterEncoding=UTF-8", USER, PASSWORD
            );
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM user_info WHERE isAdmin = 0;");

            if (resultSet.next()) {
                count = resultSet.getInt(1);
            }
            connection.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return count;
    }
}
