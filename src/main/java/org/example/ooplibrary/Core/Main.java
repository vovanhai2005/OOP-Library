package org.example.ooplibrary.Core;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.ooplibrary.Controller.LogInController;
import org.example.ooplibrary.Controller.SQLController;

public class Main extends Application {
    @Override
    public void start(Stage stage) {
        try {
            // Load FXML and initialize UI
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/ooplibrary/View/LogIn_View.fxml"));
            Parent root = fxmlLoader.load();

            // Initialize database
            SQLController.initialize();

            // Configure LogInController
            LogInController logInController = fxmlLoader.getController();
            logInController.setLanguageToEn();

            // Set up and show the scene
            Scene scene = new Scene(root);
            stage.setTitle("LibroSync");
            stage.setScene(scene);
            stage.show();

            // Start multiple background tasks in separate threads
            for (int i = 1; i <= 3; i++) {
                new Thread(new BackgroundTask(i)).start();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
