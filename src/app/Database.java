package app;

/*
 * This is the class where we create the connection to our MySQL Database
 *
 *  -----> Assign your own credentials in lines 21 - 23 <-----
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    public Database() {}

    Connection link;

    public Connection getConnection() {

        String user = "root";
        String password = "Password"; //Please enter your database password to access the database
        String databaseName = "cms";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // register the driver
            link = DriverManager.getConnection("jdbc:mysql://localhost/" + databaseName, user, password); // create connection

        } catch (ClassNotFoundException e) {
            System.out.println("Class Not Found Exception");
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.out.println("Null Pointer Exception");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("SQL Exception");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Other Exception");
            e.printStackTrace();
        }
        return link;
    }
}
