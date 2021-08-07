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

    // checks whether the number of digits of the contact's number is 10, and no more or no less
    public boolean validateNumber(String number) {

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
