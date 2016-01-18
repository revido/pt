package proj.dbs;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;

public class DBConnector {
    public static Connection getConnection() {
        Connection conn;

        String jdbcDriver = "org.h2.Driver";
        String user = "";
        String pass = "";

        try {
            String dburl= "jdbc:h2:~/.pt/pt";
            Class.forName(jdbcDriver);
            conn = DriverManager.getConnection(dburl, user, pass);
        } catch (SQLException ex) {
            conn = null;

            System.err.println(ex.getMessage());
            System.err.println("Cannot connect to database ...");
        } catch (ClassNotFoundException ex) {
            conn = null;

            System.err.println("Database driver not available ...");
        }

        return conn;
    }

}
