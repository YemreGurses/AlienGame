package application;

import ceng453.entity.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

public class LoginController {

    public String userId;

    @FXML
    private TextField nameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Button registerButton;

    @FXML
    protected void handleRegisterButtonAction(ActionEvent event) throws IOException {

        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent registerPage = FXMLLoader.load(getClass().getResource("/fxml/registrationForm.fxml"));
        Scene scene = new Scene(registerPage, 600, 800);
        currentStage.setScene(scene);
        currentStage.show();
    }

    @FXML
    protected void handleLoginButtonAction(ActionEvent event) throws IOException {
        Window owner = loginButton.getScene().getWindow();
        if (nameField.getText().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter your name");
            return;
        }

        if (passwordField.getText().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter a password");
            return;
        }

        User user = User.builder().name(nameField.getText()).password(passwordField.getText()).build();

        RestServiceConsumer restServiceConsumer = new RestServiceConsumer();
        String output = restServiceConsumer.login(user);
        if (output.equals("No Player")) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "No Player Found!");
            return;
        } else if (output.equals("Wrong Password!")) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "" +
                            "Wrong Password!");
            return;
        } else if (output.contains("Logged In")) {
            AlertHelper.showAlert(Alert.AlertType.INFORMATION, owner, "Confirmed!",
                    "Login is Succesfull!");
            String loggedIn[] = output.split(" ");
            userId = loggedIn[4];
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Parent loginPage = FXMLLoader.load(getClass().getResource("/fxml/playGame.fxml"));
            loginPage.setId(userId);
            Scene scene = new Scene(loginPage, 600, 800);
            currentStage.setScene(scene);
            currentStage.show();
        }


    }
}