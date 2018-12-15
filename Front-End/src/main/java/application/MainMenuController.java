package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainMenuController {

    private static final String loginPageUrl = "/fxml/loginPage.fxml";
    private static final String leaderBoardUrl = "/fxml/leaderBoard.fxml";


    @FXML
    protected void handlePlayButtonAction(ActionEvent event) throws IOException {
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        GameController gameController = new GameController();
        gameController.playGame(currentStage, currentStage.getScene().getRoot().getId());
    }

    @FXML
    protected void handleLogoutButtonAction(ActionEvent event) throws IOException {
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent loginPage = FXMLLoader.load(getClass().getResource(loginPageUrl));
        Scene scene = new Scene(loginPage, 600, 800);
        currentStage.setScene(scene);
        currentStage.show();
    }

    @FXML
    protected void handleLeaderBoardButtonAction(ActionEvent event) throws IOException {
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent registerPage = FXMLLoader.load(getClass().getResource(leaderBoardUrl));
        Scene scene = new Scene(registerPage, 600, 800);
        currentStage.setScene(scene);
        currentStage.show();
    }
}