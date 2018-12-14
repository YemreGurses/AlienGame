package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AlienGameApplicationGUI extends Application {

    private static final String loginPageURL = "/fxml/loginPage.fxml";

    private void mainMenuScene(Stage stage) throws IOException {

        Parent mainMenu = FXMLLoader.load(getClass().getResource(loginPageURL));

        Scene mainMenuScene = new Scene(mainMenu);

        stage.setScene(mainMenuScene);

        stage.show();

    }


    @Override
    public void start(Stage stage) throws Exception {

        stage.setTitle("Dünyayı Kurtaran Adamın Oğlu ve Uzay Gemisi");

        mainMenuScene(stage);


    }


    public static void main(String[] args) {
        launch(args);
    }
}