package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;

import java.io.IOException;

public class MainMenuController {


    @FXML
    protected void handlePlayButtonAction(ActionEvent event) throws IOException {
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        GameController gameController = new GameController();
        gameController.playGame(currentStage, currentStage.getScene().getRoot().getId());
    }

    @FXML
    protected void handleQuitButtonAction(ActionEvent event) {

        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }
}