package org.example.ooplibrary.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.example.ooplibrary.Core.BackgroundTask;

import java.io.IOException;

abstract class AbstractMenuController {

    protected Stage stage;
    protected Scene scene;
    protected Parent root;

    protected String language;

    @FXML
    void switchToDashBoardView(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/ooplibrary/View/DashBoard_View.fxml"));
            root = loader.load();
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            DashBoardController dashBoardController = loader.getController();
            if (this.language.equals("en")) {
                dashBoardController.setLanguageToEn();
            } else {
                dashBoardController.setLanguageToVi();
            }

            stage.setScene(scene);
            stage.show();

            // Start multiple background tasks in separate threads
            for (int i = 1; i <= 3; i++) {
                new Thread(new BackgroundTask(i)).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void switchToBorrowDocumentView(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/ooplibrary/View/BorrowManagement_View.fxml"));
            root = loader.load();
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            BorrowManagementController borrowManagementController = loader.getController();
            if (this.language.equals("en")) {
                borrowManagementController.setLanguageToEn();
            } else {
                borrowManagementController.setLanguageToVi();
            }
            stage.setScene(scene);
            stage.show();

            // Start multiple background tasks in separate threads
            for (int i = 1; i <= 3; i++) {
                new Thread(new BackgroundTask(i)).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void switchToDocumentArchiveView(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/ooplibrary/View/DocumentArchive_View.fxml"));
            root = loader.load();
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            DocumentArchiveController documentArchiveController = loader.getController();
            if (this.language.equals("en")) {
                documentArchiveController.setLanguageToEn();
            } else {
                documentArchiveController.setLanguageToVi();
            }
            stage.setScene(scene);
            stage.show();

            // Start multiple background tasks in separate threads
            for (int i = 1; i <= 3; i++) {
                new Thread(new BackgroundTask(i)).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void switchToLoginView(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/ooplibrary/View/LogIn_View.fxml"));
            root = loader.load();
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            LogInController logInController = loader.getController();
            if (language.equals("en")) {
                logInController.setLanguageToEn();
            } else {
                logInController.setLanguageToVi();
            }
            stage.setScene(scene);
            stage.show();

            // Start multiple background tasks in separate threads
            for (int i = 1; i <= 3; i++) {
                new Thread(new BackgroundTask(i)).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void switchToReturnDocumentView(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/ooplibrary/View/ReturnDocument_View.fxml"));
            root = loader.load();
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            ReturnDocumentController returnDocumentController = loader.getController();
            if (this.language.equals("en")) {
                returnDocumentController.setLanguageToEn();
            } else {
                returnDocumentController.setLanguageToVi();
            }
            stage.setScene(scene);
            stage.show();

            // Start multiple background tasks in separate threads
            for (int i = 1; i <= 3; i++) {
                new Thread(new BackgroundTask(i)).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void switchToUserManagementView(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/ooplibrary/View/AdminUserManagement_View.fxml"));
            root = loader.load();
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            UserManagementController userManagementController = loader.getController();
            if (this.language.equals("en")) {
                userManagementController.setLanguageToEn();
            } else {
                userManagementController.setLanguageToVi();
            }
            stage.setScene(scene);
            stage.show();

            // Start multiple background tasks in separate threads
            for (int i = 1; i <= 3; i++) {
                new Thread(new BackgroundTask(i)).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void switchToMainMenuView(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/ooplibrary/View/MainMenu_View.fxml"));
            root = loader.load();
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            MainMenuController mainMenuController = loader.getController();
            if (this.language.equals("en")) {
                mainMenuController.setLanguageToEn();
            } else {
                mainMenuController.setLanguageToVi();
            }
            stage.setScene(scene);
            stage.show();

            // Start multiple background tasks in separate threads
            for (int i = 1; i <= 3; i++) {
                new Thread(new BackgroundTask(i)).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
