package shopOfItems.tools.decorate;

import java.sql.*;

public class Decorate {

    private boolean decorate = true;

    private final String toolsDB = "jdbc:derby:TollsForShopDB";
    private final String getAll = "SELECT * FROM decorate";

    Connection connection;
    Statement statement;
    PreparedStatement preparedStatement;
    ResultSet resultSet;

    public Decorate() {
        try {
            connection = DriverManager.getConnection(toolsDB);
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setDecorate(boolean decorate) {
        try {
            statement.execute("UPDATE decorate SET decorate = " + decorate + " WHERE id = 1");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean getDecorate() {
        try {
            preparedStatement = connection.prepareStatement(getAll);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                decorate = resultSet.getBoolean("decorate");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return decorate;
    }
}
