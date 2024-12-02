package org.example.ooplibrary.Core;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.ooplibrary.Controller.LogInController;
import org.example.ooplibrary.Controller.SQLController;
import org.example.ooplibrary.Controller.SignUpController;

import java.io.IOException;
import java.net.URL;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/ooplibrary/View/LogIn_View.fxml"));
            Parent root = fxmlLoader.load();
            SQLController.initialize();

            LogInController logInController = fxmlLoader.getController();
            logInController.setLanguageToEn();
            Scene scene = new Scene(root);
            stage.setTitle("LibroSync");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }

}