package shopOfItems.DBManagers;

import shopOfItems.dataSource.DataSource;
import shopOfItems.tools.audioPlayer.AudioPlayer;

import javax.swing.*;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.*;

public class MySQL extends AbstractManagementSystem {

    private static String autoIncrement = "AUTO_INCREMENT";

    private String LOCALHOST = "jdbc:mysql://localhost:";
    private final String SSL = "?useSSL=false";

    private final String createDB = "CREATE DATABASE IF NOT EXISTS shopOfItems";

    Connection connection;
    Statement statement;
    DataSource dataSource;

    public MySQL(AudioPlayer audioPlayer) {
        super(autoIncrement);
        this.audioPlayer = audioPlayer;
    }

    @Override
    public boolean checkConnection(int localhostNumber, String username, String password) {
        this.localhostNumber = localhostNumber;
        this.username = username;
        this.password = password;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            connection = DriverManager.getConnection(LOCALHOST + localhostNumber + SSL, username, password);

            statement = connection.createStatement();
            statement.executeUpdate(createDB);

        } catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            audioPlayer.haha();
            JOptionPane.showMessageDialog(null, "Not correct data", "Inane warning", JOptionPane.WARNING_MESSAGE);
            return false;
        } finally {
            closeConnection(connection);
        }
        return true;
    }

    @Override
    public Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            dataSource = DataSource.getInstance(LOCALHOST + localhostNumber + "/shopOfItems?useSSL=false", username, password);
            connection = dataSource.getConnection();
        }
        catch(SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException | IOException| PropertyVetoException e) {
            e.printStackTrace();
        }
        return connection;
    }

    @Override
    public void closeConnection(Connection connection) {
        try {
            connection.close();
            if(connection.isClosed()) {
                System.out.println("Database shut down normally");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
