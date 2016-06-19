package shopOfItems.dataSource;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.PooledConnection;

public class DataSource {

    private static DataSource datasource = null;
    private ComboPooledDataSource cpds = null;
    private Connection connection;
    private PooledConnection connectionPoolDataSource;

    private int maxSize = 20;

    private DataSource(String url, String userName, String password) throws IOException, SQLException, PropertyVetoException {
        cpds = new ComboPooledDataSource();

        cpds.setJdbcUrl(url);
        cpds.setUser(userName);
        cpds.setPassword(password);

        cpds.setMinPoolSize(3);
        cpds.setAcquireIncrement(5);
        cpds.setMaxPoolSize(maxSize);
        cpds.setMaxStatements(100);

        connectionPoolDataSource = cpds.getConnectionPoolDataSource().getPooledConnection();
    }

    public static DataSource getInstance(String url, String userName, String password) throws IOException, SQLException, PropertyVetoException {

        if (datasource == null){
        datasource = new DataSource(url, userName, password);
            return datasource;
        } else {
            return datasource;
        }
    }

    public Connection getConnection()  {
        try {
            if(cpds.getNumBusyConnections() == maxSize){
                cpds.softResetAllUsers();
            }
            connection = cpds.getConnection();
            System.out.println(cpds.getNumBusyConnections());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
