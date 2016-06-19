package shopOfItems.dataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@RunWith(JUnit4.class)
public class TestDataSource {

    DataSource dataSource;
    Connection connection;

    int localhostNumber = 3306;
    String username = "root";
    String password = "root";

    @Before
    public void init() {
        connection = null;
        try {
            dataSource = DataSource.getInstance("jdbc:mysql://localhost:" + localhostNumber + "/shopOfItems?useSSL=false", username, password);

        } catch (IOException | SQLException | PropertyVetoException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetConnection() {
        connection = dataSource.getConnection();
        try {
            assertFalse(connection.isClosed());
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Test()
    public void testCloseConnection() {
        connection = dataSource.getConnection();

        try {
            connection.close();
            assertTrue(connection.isClosed());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
