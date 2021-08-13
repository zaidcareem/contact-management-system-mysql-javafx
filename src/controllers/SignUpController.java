package controllers;

import app.Alerts;
import app.ChangeView;
import app.Database;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {

    @FXML
    private Button backBtn;
    @FXML
    private TextField usernameTf;
    @FXML
    private PasswordField passwordPf, reTypePasswordPf;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("This is SignUpController");
    }

    // back to 'LogIn' view
    public void backToLoginView() throws IOException {
        ChangeView switchToView = new ChangeView(backBtn);
        switchToView.changeView("LogIn");
    }

    // clear userName TextField, password and re-typePassword PasswordFields
    public void clearAllFields() {
        usernameTf.clear();
        passwordPf.clear();
        reTypePasswordPf.clear();
    }

    // check whether fields[username, password] are not empty
    public boolean completedFields() { return ((!usernameTf.getText().equals("")) && (!passwordPf.getText().equals("")) && (!reTypePasswordPf.getText().equals(""))); }

    // check whether passwords match
    public boolean passwordMatches() { return passwordPf.getText().equals(reTypePasswordPf.getText()) && passwordPf.getText() != null && reTypePasswordPf.getText()!=null; }

    // sign up a new user
    public void signUp() throws SQLException, IOException {

        Alerts alert = new Alerts();

        if ((completedFields()) && (passwordMatches())) {

            addUserToDatabase();
            alert.showSignUpSuccessfulMessage();
            System.out.println(usernameTf.getText() + "'s sign up is successful");
            backToLoginView();

        } else {

            if (!completedFields()) {
                alert.showInsufficientInformationMessage();
            } else {
                alert.showPasswordsDoesNotMatch();
            }
        }
        clearAllFields();
    }

    // add the new user to the database
    public void addUserToDatabase() throws SQLException {

        Database db = new Database();
        Connection conn = db.getConnection();

        String username = usernameTf.getText();
        String password = passwordPf.getText();

        PreparedStatement ps = conn.prepareStatement("INSERT INTO users VALUES (?, ?)");
        ps.setString(1, username);
        ps.setString(2, password);

        int rowsAffected = ps.executeUpdate();

        System.out.println("rows affected = " + rowsAffected);

        ps.close();
        conn.close();
    }
}
