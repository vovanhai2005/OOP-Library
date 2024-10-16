package org.example.ooplibrary.Core;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        try {
            URL url = this.getClass().getClassLoader().getResource("org/example/ooplibrary/View/LogInView.fxml");
            if (url == null) {
                throw new IOException("FXML file not found. Check the path: " + "org/example/ooplibrary/View/LogInView.fxml");
            }
            System.out.println("FXML Resource URL: " + url);
            Parent root = FXMLLoader.load(url);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}