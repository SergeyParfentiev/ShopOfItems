package shopOfItems.DBManagers;

import shopOfItems.tools.audioPlayer.AudioPlayer;

import java.io.IOException;
import java.sql.*;

public class Derby extends AbstractManagementSystem{

    private static String autoIncrement = "GENERATED ALWAYS AS IDENTITY(START WITH 1, INCREMENT BY 1)";

    public Derby(AudioPlayer audioPlayer) {
        super(autoIncrement);
        this.audioPlayer = audioPlayer;
    }

    @Override
    public boolean checkConnection(int localhostNumber, String username, String password) {

        return true;
    }

    @Override
    public Connection getConnection() {
        Connection connection = null;
        inputStream = getClass().getClassLoader().getResourceAsStream("shopOfItems/properties/DerbyDB");
        try {
            properties.load(inputStream);

            Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();

            String URL = properties.getProperty("URL");
            String userName = properties.getProperty("UserName");
            String password = properties.getProperty("Password");

            connection = DriverManager.getConnection(URL, userName, password);

        } catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException | IOException  e) {
            e.printStackTrace();
        }
        return connection;
    }

    @Override
    public void closeConnection(Connection connection) {
        try {
            DriverManager.getConnection("jdbc:derby:shopOfItemsDB;shutdown=true");
        } catch (SQLException e) {
            if (e.getSQLState().equals("08006") ) {
                System.out.println("Database shut down normally");
            } else {
                e.printStackTrace();
            }
        }
    }
}
