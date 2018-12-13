package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class PlayGameController {


    @FXML
    private Button playButton;

    @FXML
    private Button leaderBoardButton;

    @FXML
    private Button quitButton;

    @FXML
    protected void handlePlayButtonAction(ActionEvent event) throws IOException {
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        AllienGameApplicationGUI allienGameApplicationGUI = new AllienGameApplicationGUI();
        allienGameApplicationGUI.playGame(currentStage, currentStage.getScene().getRoot().getId());
    }

    @FXML
    protected void handleQuitButtonAction(ActionEvent event) throws IOException {

        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }
}