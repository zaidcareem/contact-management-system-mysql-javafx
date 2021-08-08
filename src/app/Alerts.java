package app;

import javafx.scene.control.Alert;

public class Alerts {

    Alert alert;

    public void auxAlert(Alert.AlertType alertType, String setTitle, String setHeaderText) {

        alert = new Alert(alertType);
        alert.setTitle(setTitle);
        alert.setHeaderText(setHeaderText);
        alert.showAndWait();
    }

    public void showInvalidInformationMessage() {
        auxAlert(Alert.AlertType.WARNING, "Invalid", "Invalid Information");
    }

    public void showLoginUnsuccessfulMessage() {
        auxAlert(Alert.AlertType.ERROR, "Log In MSG", "You failed to log in");
    }

    public void showNumberNotValid() {
        auxAlert(Alert.AlertType.ERROR, "Invalid Number", "Please enter a 10 digit number <= (2^31 - 1)" );
    }

    public void showInsufficientInformationMessage() {
        auxAlert(Alert.AlertType.ERROR, "Insufficient Information", "Please fill in all required fields" );
    }

    public void showPasswordsDoesNotMatch() {
        auxAlert(Alert.AlertType.ERROR, "Password Mismatch", "Passwords does not match!" );
    }

    public void showSignUpSuccessfulMessage() {
        auxAlert(Alert.AlertType.INFORMATION, "Sign Up Successful", "You Signed Up Successfully!" );
    }

    public void showAllContactsDeletedMessage() {
        auxAlert(Alert.AlertType.INFORMATION, "Contacts deleted", "All contacts cleared!" );
    }
}
