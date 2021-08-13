package controllers;

import app.Alerts;
import app.ChangeView;
import app.Database;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class EditContactController implements Initializable {

    @FXML
    private Button cancelBtn;
    @FXML
    private TextField newContactName, newContactNumber;

    private String oldContactName, oldContactNumber;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("This is EditContactController");
    }

    // move to the contacts view when cancel, back, or confirm buttons are clicked
    public void backToContactsView() throws IOException {
        ChangeView cv = new ChangeView(cancelBtn);
        cv.changeView("Contacts");
    }

    /*
     * Receives data given from the 'ContactsController' and then
     * displays the current name and number of the contact selected for editing in the TextFields where user should
     * enter the new values of the contacts' properties.
     */
    public void setTextBoxes(String name, String number) {

        newContactName.setText(name);
        newContactNumber.setText(number);

        /* save the contacts' name and number in the below variables so that it can be used in sql queries
         * since this method runs when the scene loads [i.e. at the start itself when loading the respective .fxml file]
         * the variables below will not be having null values in any other method where they are called
         */
        oldContactName = name;
        oldContactNumber = number;
    }

    // edit contact and update database
    public void editContact() throws SQLException, IOException {

        if (!(newContactName.getText().equals("")) && !(newContactNumber.getText().equals("")) && (!(newContactName.getText().equals(oldContactName)) || !(newContactNumber.getText().equals(oldContactNumber)))) {

            String newName = newContactName.getText();
            String newNumber = newContactNumber.getText();
            String query = "UPDATE contacts SET name = ?, number = ? WHERE name = ? and number = ?";

            Database db = new Database();
            Connection conn = db.getConnection();

            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, newName);
            ps.setString(2, newNumber);
            ps.setString(3, oldContactName);
            ps.setString(4, oldContactNumber);

            AddContactController number = new AddContactController();
            boolean isNewNumberValid = number.validateNumber(newNumber);

            /*
             * end function execution if new number entered by user is not valid, i.e. is not less than [2^31 - 1]
             * or is a negative number
             */
            if (!isNewNumberValid) {

                Alerts alert = new Alerts();
                alert.showNumberNotValid();
                ps.close();
                conn.close();
                return;
            }

            int rowsAffected = 0;

            try {
                rowsAffected = ps.executeUpdate();

            } catch (Exception e) {

                e.printStackTrace();
                e.getCause();
            }

            if (rowsAffected != 0) {

                System.out.println(oldContactName + " edited successfully into " + newName);
                Alerts alert = new Alerts();
                alert.showEditionSuccessful();
                backToContactsView();
            }
            ps.close();
            conn.close();

        } else {
            System.out.println("Empty fields exist or No changes made");
        }
    }
}
