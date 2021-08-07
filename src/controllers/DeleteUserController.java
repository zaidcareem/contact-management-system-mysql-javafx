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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class DeleteUserController implements Initializable {

    @FXML
    private Button deleteBtn;
    @FXML
    private TextField usernameTf;
    @FXML
    private PasswordField passwordPf;


    Database db;
    Connection conn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("This is DeleteUserController");
    }


    // delete the only user in the system
    public void deleteUser() throws SQLException, IOException {

        String username = usernameTf.getText();
        String password = passwordPf.getText();
        Alerts alert = new Alerts();

        if (!(username.equals("")) && !(password.equals(""))) {

            if (validUser()) {

                db = new Database();
                conn = db.getConnection();

                String query = "DELETE FROM users WHERE username = ?";
                PreparedStatement ps = conn.prepareStatement(query);
                ps.setString(1, username);
                int rowsAffected = ps.executeUpdate();

                if (rowsAffected == 1) {

                    String deleteContactsQuery = "DELETE FROM contacts";
                    PreparedStatement preparedStatement = conn.prepareStatement(deleteContactsQuery);
                    preparedStatement.executeUpdate();

                    System.out.println("User " + username + "; deleted from system");
                    back();
                }
                ps.close();
                conn.close();

            } else {
                alert.showInvalidInformationMessage();
            }
        } else {
            alert.showInsufficientInformationMessage();
        }
        clearUserNamePasswordFields();
    }


    // check whether it is a valid user
    public boolean validUser() throws SQLException {

        String username = usernameTf.getText();
        String password = passwordPf.getText();

        db = new Database();
        conn = db.getConnection();

        String sqlQuery = "SELECT username, password from users WHERE username = ? AND password = ?";
        PreparedStatement ps = conn.prepareStatement(sqlQuery);
        ps.setString(1, username);
        ps.setString(2, password);

        ResultSet rs = ps.executeQuery();

        boolean isValidUser = rs.next();

        ps.close();
        conn.close();

        return isValidUser;
    }

    // go back to login screen
    public void back() throws IOException {
        ChangeView cv = new ChangeView(deleteBtn);
        cv.changeView("LogIn");
    }

    // clear the username and password fields
    private void clearUserNamePasswordFields() {
        usernameTf.clear();
        passwordPf.clear();
    }
}
