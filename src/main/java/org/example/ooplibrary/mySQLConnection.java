package org.example.ooplibrary;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.Scanner;

public class mySQLConnection {
    public static boolean checkLogIn(String userName, String password){
        String url = "jdbc:mysql://localhost:3306/admin";
        String username = "root";
        String Password = "";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection(url , username, Password);

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("select password from admin where username = \"" + userName + "\"");

            if (resultSet.next()){
                if (resultSet.getString(1).equals(password)){
                    return true;
                }
                return false;
            }
            connection.close();
        }
        catch(Exception e) {
            System.out.println(e);
        }
        return false;
    }
    public static void main(String[] args) {
        boolean successLogIn = false;
        while(!successLogIn) {
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter username: ");
            String userName = sc.nextLine();
            System.out.println("Enter password: ");
            String password = sc.nextLine();

            if (checkLogIn(userName, password)) {
                System.out.println("Logged in");
                successLogIn = true;
            } else {
                System.out.println("Wrong username or password");
            }
        }
    }
}
