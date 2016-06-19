package shopOfItems.DBManagers;

import javax.swing.*;
import java.sql.Connection;
import java.util.List;

public interface DBManagementSystem {

    boolean checkConnection(int localhostNumber, String username, String password);

    boolean connection(int localhostNumber, String username, String password);

    void closeConnection(Connection connection);

    Object[][] listOfItems();

    Object[][] listOfCustomers();

    Object[][] listOfSales();

    boolean addItem(String name, int cost, int number);

    boolean addCustomer(String firstName, String secondName, int age, int gender_id, String address, String phoneNumber);

    List<String> listOfItemsName();

    List<String> listOfCustomersName();

    boolean buyItem(int idItem, int idCustomer, int number);

    void listOFItemColumns(JComboBox<String> jComboBox);

    void listOCustomersColumns(JComboBox<String> jComboBox);

    boolean editItem(int itemId, String column, String stringData, int intData);

    boolean editCustomer(int customerId, String column, String stringData, int intData);

    Connection getConnection();
}
