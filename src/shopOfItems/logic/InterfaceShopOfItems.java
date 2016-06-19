package shopOfItems.logic;

import shopOfItems.DBManagers.DBManagementSystem;

import javax.swing.*;
import java.util.List;

public interface InterfaceShopOfItems {

    void backToChooseDB();

    boolean mySQLConnection(int localhostNumber, String username, String password);

    boolean derbyConnection(int localhostNumber, String username, String password);

    Object[][] listOfItems();

    Object[][] listOfCustomers();

    Object[][] listOfSales();

    boolean addItem(String name, String cost, String number);

    boolean addCustomer(String fName, String sName, String age, int gender_id, String address, String phoneNumber);

    List<String> listOfItemsName();

    List<String> listOfCustomersName();

    boolean buyItem(int idItem, int idCustomer, String itemNumber);

    void itemColumns(JComboBox<String> jComboBox);

    void customerColumns(JComboBox<String> jComboBox);

    boolean editItem(int itemId, int boxColumnIdx, String columnName, String data);

    boolean editCustomer(int customerId, int boxColumnIdx, String column, String data);

    DBManagementSystem getManagementSystem();
}
