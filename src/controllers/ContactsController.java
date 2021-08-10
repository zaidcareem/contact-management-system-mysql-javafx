package controllers;

import app.Alerts;
import app.ChangeView;
import app.Database;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class ContactsController implements Initializable {

    @FXML
    private Button signOutBtn, addBtn;
    @FXML
    private ListView<String> contactList;
    @FXML
    private Label contactNameLabel, contactNumberLabel;

    private Database db;
    private Connection conn;
    private Alerts alert;


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

        db = new Database();
        conn = db.getConnection();

        String query = "SELECT name FROM contacts";

        PreparedStatement ps = conn.prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {

            name = rs.getString(1);
            contactList.getItems().add(name);

        }

        rs.close();
        ps.close();
        conn.close();
    }

    // show name and number of the contact selected in the ListView 'contactList'
    public void reflectDataOfContactSelected() throws SQLException {

        /*
         * The below if block is important to prevent crash when clearing all contacts
         * Crash would happen if variable 'name' is null, i.e if variable 'name' is not initialized
         */
        if (contactList.getItems().isEmpty()) {
            contactNameLabel.setText("-");
            contactNumberLabel.setText("-");
            return;
        }

        String name;
        int number;

        name = contactList.getSelectionModel().getSelectedItem();

        db = new Database();
        conn = db.getConnection();

        String query = "SELECT number from contacts WHERE name = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, name);

        ResultSet rs = ps.executeQuery();

        rs.next(); // make the 1st row the current row

        number = rs.getInt(1); // store the contact's number in this variable
        String RealNumber = Integer.toString(number);   // convert to string

        contactNameLabel.setText(name);
        contactNumberLabel.setText(RealNumber);
    }

    // method to clear all contacts
    public void clearAllContacts() throws SQLException {

        db = new Database();
        conn = db.getConnection();

        String query = "DELETE FROM contacts";
        Statement st = conn.createStatement();
        int rowsAffected = st.executeUpdate(query);

        if (rowsAffected > 0) {

            contactList.getItems().clear();
            System.out.println("Contact list cleared");
            alert = new Alerts();
            alert.showAllContactsDeletedMessage();
        } else {
            System.out.println("Failed to clear all contacts or maybe you have no contacts");
        }

        st.close();
        conn.close();
    }

    // Delete contact from the ListView 'contactList'
    public void deleteContact() throws SQLException {

        // ONLY if a contact is selected do the below
        if (contactList.getSelectionModel().getSelectedItem() != null) {

            String contactName = contactList.getSelectionModel().getSelectedItem();

            db = new Database();
            conn = db.getConnection();

            String query = "DELETE FROM contacts WHERE name = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, contactName);

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected != 0) {
                contactList.getItems().remove(contactName);
                System.out.println(contactName + " deleted");
            } else {
                System.out.println("Could not delete contact");
            }
        } else {
            System.out.println("No contact selected");
        }
    }

    // When editBtn is pressed, send information of the contact selected for editing to the contact editing scene
    public void sendInfoToEditController(ActionEvent e) throws IOException {

        // terminate execution of function if no contact is selected
        if (contactList.getSelectionModel().getSelectedItem() == null) {
            System.out.println("No contact selected for editing");
            return;
        }

        String contactName = contactNameLabel.getText();
        String contactNumber = contactNumberLabel.getText();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../scenes/EditContact.fxml"));
        Parent root = loader.load();

        // return the controller of 'EditContacts'
        EditContactController editingController = loader.getController();
        editingController.setTextBoxes(contactName, contactNumber);

        Stage window = (Stage) addBtn.getScene().getWindow();
        Scene scene = new Scene(root, 900, 700);
        window.setScene(scene);
        window.setResizable(false);
        window.show();
    }

    public void moveToChangePasswordView() throws IOException {
        ChangeView cv = new ChangeView(signOutBtn);
        cv.changeView("ChangePassword");
    }
}
