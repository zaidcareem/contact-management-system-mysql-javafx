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

public class LoginController implements Initializable {

    @FXML
    private Button signUpBtn, logInBtn, delUserBtn;
    @FXML
    public TextField usernameTf;
    @FXML
    private PasswordField passwordPf;

    private Database db;
    private Connection conn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("This is LoginController");
        try {
            setDelUserBtn();
            disableSignUpBtn();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // change view to to signUp view
    public void switchToSignUpView() throws IOException {

        // switch to view 'SignUp', currently program is in view 'LogIn'
        ChangeView switchToView = new ChangeView(signUpBtn);
        switchToView.changeView("SignUp");
    }

    // change view to to Contacts view
    public void switchToContactsView() throws IOException {

        // switch to view 'Contacts', currently program is in view 'LogIn'
        ChangeView switchToView = new ChangeView(logInBtn);
        switchToView.changeView("Contacts");
    }

    private void clearUserNamePasswordFields() {
        usernameTf.clear();
        passwordPf.clear();
    }

    // switch scene to 'Contacts' if user login successful, if not alert to user login is unsuccessful
    public void logIn() throws SQLException, IOException {

        Alerts alert = new Alerts();

        String username = usernameTf.getText();
        String password = passwordPf.getText();

        if (username.equals("") && password.equals("")) {
            alert.showLoginUnsuccessfulMessage();

        } else {

            if (username.equals("") || password.equals("")) {
                alert.showInsufficientInformationMessage();

            } else {    // if both username and password fields is not empty

                db = new Database();
                conn = db.getConnection();

                String sqlQuery = "SELECT username, password from users WHERE username = ? AND password = ?";
                PreparedStatement ps = conn.prepareStatement(sqlQuery);
                ps.setString(1, username);
                ps.setString(2, password);

                ResultSet rs = ps.executeQuery();

                // if sql query is valid then take user to the contact list page, i.e. switch to 'Contacts' view from
                //                                                                                       'LogIn' view
                if (rs.next()) {
                    System.out.println(username + "'s login is successful");
                    switchToContactsView();
                } else {
                    alert.showLoginUnsuccessfulMessage();
                }

                rs.close();
                ps.close();
                conn.close();
            }
            clearUserNamePasswordFields();
        }
    }

    // this is a temporary feature
    // this method makes the MAX no:of users = 1,
    // going to make it an multi user application some day.
    public void disableSignUpBtn() throws SQLException {

        db = new Database();
        conn = db.getConnection();
        String query = "select count(username) from users";
        PreparedStatement ps = conn.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        rs.next();
        String noOfUsers = rs.getString(1);
        if (noOfUsers.equals("1")) {
            signUpBtn.setDisable(true);
            System.out.println("Sign Up Button is Disabled");
        }
        rs.close();
        ps.close();
        conn.close();
    }

    // move to view 'DeleteUser'
    public void moveToDeleteUserView() throws IOException {

      ChangeView cv = new ChangeView(delUserBtn);
      cv.changeView("DeleteUser");
    }

    // set the delUserBtn / "Delete User" Button in the gui as a disabled button
    public void setDelUserBtn() throws SQLException {

        db = new Database();
        conn = db.getConnection();

        String query = "SELECT count(username) FROM users";
        PreparedStatement ps = conn.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        rs.next();

        int noOfUsers = Integer.parseInt(rs.getString(1));

        if (noOfUsers == 0) {
            delUserBtn.setDisable(true);
            System.out.println("Delete user button is disabled");
        }
    }
}
