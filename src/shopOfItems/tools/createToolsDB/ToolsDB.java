package shopOfItems.tools.createToolsDB;

import java.sql.*;

public class ToolsDB {

    private String createToolsDB = "jdbc:derby:TollsForShopDB;create=true";
    private final String soundTable = "CREATE TABLE sound(id INTEGER, " +
            "sound BOOLEAN)";
    private final String insertSoundValue = "INSERT INTO sound VALUES(1, true)";
    private final String decorateTable = "CREATE TABLE decorate(id INTEGER, " +
            "decorate BOOLEAN)";
    private final String insertsDecorateValue = "INSERT INTO decorate VALUES(1, true)";

    private Connection connection;
    Statement statement;

    public ToolsDB() {
        try {
            connection = DriverManager.getConnection(createToolsDB);
            statement = connection.createStatement();
            DatabaseMetaData dmd = connection.getMetaData();
            ResultSet rs = dmd.getTables(null, "APP", "SOUND", null);

            if (!rs.next()) {
                statement.executeUpdate(soundTable);
                statement.executeUpdate(insertSoundValue);
                statement.executeUpdate(decorateTable);
                statement.executeUpdate(insertsDecorateValue);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
