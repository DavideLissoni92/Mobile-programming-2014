
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Daniele
 */
public class DBManager {
    public static Connection db;
    
    public DBManager(String dburl) throws SQLException {
        try {

            Class.forName("org.apache.derby.jdbc.ClientDriver", true, getClass().getClassLoader());

            db = DriverManager.getConnection(dburl);

           

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
}

public static void shutdown() throws SQLException {
        try {
            DriverManager.getConnection("jdbc:derby:;shutdown=true");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
