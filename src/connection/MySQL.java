package connection;

import java.awt.Component;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class MySQL {

    private static Connection connection;
    private static Component MainPanel;

    static {

        try {
            
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/tranquil", "root", "Vithu2005@");

        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(MainPanel,"Database connection failed","Oops !",JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException();
        }

    }

    public static ResultSet execute(String query) throws SQLException {

        Statement statement = connection.createStatement();

        if (query.startsWith("SELECT")) {

            return statement.executeQuery(query);

        } else {
            statement.executeUpdate(query);
            return null;
        }

    }

}
