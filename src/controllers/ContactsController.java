package controllers;

import app.ChangeView;
import app.Database;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ContactsController implements Initializable {

    @FXML
    private Button signOutBtn, addBtn;
    @FXML
    private ListView<String> contactList;
    @FXML
    private Label contactNameLabel, contactNumberLabel;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        System.out.println("This is ContactsController");

        try {
            readFromDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        contactList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

                try {
                    reflectDataOfContactSelected();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        });
    }

    // sign out user and switch view to 'LogIn' from 'SignUp'
    public void signOut() throws IOException {
        ChangeView switchView = new ChangeView(signOutBtn);
        switchView.changeView("LogIn");
        System.out.println("User signed out");
    }

    // move to 'AddContact' view in order to add a new contact
    public void changeViewToAddContact() throws IOException {
        ChangeView changeView = new ChangeView(addBtn);
        changeView.changeView("AddContact");
    }

    // loads all contacts to the ListView 'contactList' from the database
    public void readFromDatabase() throws SQLException {

        String name;

        Database db = new Database();
        Connection conn = db.getConnection();

        String query = "SELECT name FROM contacts";

        PreparedStatement ps = conn.prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {

            name = rs.getString(1);
            contactList.getItems().add(name);

        }
    }

    // show name and number of the contact selected in the ListView 'contactList'
    public void reflectDataOfContactSelected() throws SQLException {

        String name;
        int number;

        name = contactList.getSelectionModel().getSelectedItem();

        Database db = new Database();
        Connection conn = db.getConnection();

        String query = "SELECT number from contacts WHERE name = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, name);

        ResultSet rs = ps.executeQuery();

        rs.next(); // make the 1st row the current row

        number = rs.getInt(1); // store the contact's number in this variable
        String RealNumber = Integer.toString(number);

        contactNameLabel.setText(name);
        contactNumberLabel.setText(RealNumber);
    }
}
