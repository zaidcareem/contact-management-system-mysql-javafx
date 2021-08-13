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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddContactController implements Initializable {

    @FXML
    private TextField contactNameTf, contactNumberTf;
    @FXML
    private Button okBtn;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("This is AddContactController");
    }

    // clear name and number fields
    public void clearFields() {
        contactNameTf.clear();
        contactNumberTf.clear();
    }

    public void addContact() throws SQLException {

        String contactName = contactNameTf.getText();
        String contactNumber = contactNumberTf.getText();

        if (!(contactName.equals("")) && !(contactNumber.equals(""))) {

            // stop execution if number is not valid
            if (!(validateNumber(contactNumber))) {

                Alerts alert = new Alerts();
                alert.showNumberNotValid();
                System.out.println("Number not valid");
                clearFields();
                return;
            }

            Database db = new Database();
            Connection conn = db.getConnection();

            String query = "INSERT INTO contacts VALUES (?, ?)";

            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, contactName);
            ps.setString(2, contactNumber);

            int rowsAffected = ps.executeUpdate();

            ps.close();
            conn.close();

            if (rowsAffected == 1) {
                System.out.println(contactName + " added to database");
            }
        }
        clearFields();
    }

    /*
     * The below method;
     *
     * Checks whether the number of digits of the contact's number is 10, and no more or no less
     *
     * And also prevents SQL 'Data Truncation Error' from occurring due to entering a larger value than the maximum an int
     * can hold.
     *
     * This is done because in the 'contacts' table which is the table to hold every contacts' number in its number column
     * the data type given to it is 'int'
     *
     * So we should give it a value less than or equal to (2^31 - 1), but greater than 0 which is the max value an int can hold
     *
     * Thus, preventing 'Data Truncation Error'
     *
     * ----    method is used in 'EditContactController' too
     */
    public boolean validateNumber(String number) {

        int MAX_NUMBER = (int) (Math.pow(2, 31) - 1);

        double contactNumber = Double.parseDouble(number);

        /*
         * number should not be more than (2^31 - 1) or a negative number
         */
        if ((contactNumber > MAX_NUMBER) || (contactNumber < 0)) {
            return false;
        }

        if (number.length() == 10) {

            String regex = "[0-9]+";
            Pattern onlyDigits = Pattern.compile(regex);
            Matcher matcher = onlyDigits.matcher(number);
            return matcher.matches();

        } else {

            return false;
        }
    }

    public void backToContactList() throws IOException {
        ChangeView switchView = new ChangeView(okBtn);
        switchView.changeView("Contacts");
    }
}
