package app;

import javafx.scene.control.Alert;

public class Alerts {

    Alert alert;

    public void showInvalidInformationMessage() {
        alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Invalid");
        alert.setHeaderText("Invalid Information");
        alert.setContentText("The info provided does not match");
        alert.show();
    }

    public void showLoginUnsuccessfulMessage() {
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Log In Unsuccessful");
        alert.setHeaderText("You failed to log in");
        alert.setContentText("Unsuccessful Login");
        alert.show();
    }

    public void showNumberNotValid() {
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid Number");
        alert.setHeaderText("Number is Invalid");
        alert.setContentText("Please enter a 10 digit number <= (2^31 - 1)");
        alert.show();
    }

    public void showInsufficientInformationMessage() {
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Insufficient Information");
        alert.setHeaderText("Please fill in all required fields");
        alert.setContentText("Empty fields found");
        alert.show();
    }

    public void showPasswordsDoesNotMatch() {
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Password Mismatch");
        alert.setHeaderText("Passwords does not match!");
        alert.setContentText("Re-enter password");
        alert.show();
    }

    public void showSignUpSuccessfulMessage() {
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sign Up Successful");
        alert.setHeaderText("You Signed Up Successfully!");
        alert.setContentText("Successful Sign Up");
        alert.showAndWait();
    }

    public void showAllContactsDeletedMessage() {
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Contacts deleted");
        alert.setHeaderText("You deleted all contacts!");
        alert.setContentText("All contacts cleared");
        alert.showAndWait();
    }
}
