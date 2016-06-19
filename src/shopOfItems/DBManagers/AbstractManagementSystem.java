package shopOfItems.DBManagers;

import shopOfItems.tools.audioPlayer.AudioPlayer;

import javax.swing.*;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

public abstract class AbstractManagementSystem implements DBManagementSystem {

    protected AudioPlayer audioPlayer;

    protected int localhostNumber;
    protected String username;
    protected String password;

    protected InputStream inputStream;
    protected Properties properties;

    private String customerTable;
    private String customerGenderTable;
    private String insertCustomerMale;
    private String insertCustomerFemale;
    private String customerContactsTable;
    private String itemTable;
    private String salesTable;

    public AbstractManagementSystem(String autoIncrement) {
        properties = new Properties();

        customerGenderTable = "CREATE TABLE customerGender(id INT, gender VARCHAR(10) NOT NULL, " +
                "PRIMARY KEY (id))";

        insertCustomerMale = "INSERT INTO customerGender VALUES (1, 'Male')";
        insertCustomerFemale = "INSERT INTO customerGender VALUES (2, 'Female')";

        customerTable = "CREATE TABLE customer(id INT " + autoIncrement + ", firstName VARCHAR(30) NOT NULL, secondName VARCHAR(30) NOT NULL," +
                " age INT NOT NULL, gender_id INT, PRIMARY KEY (id), FOREIGN KEY (gender_id) REFERENCES customerGender (id))";

        customerContactsTable = "CREATE TABLE customerContacts(id INT " + autoIncrement + ", address VARCHAR(40) NOT NULL, " +
                "phoneNumber VARCHAR(20) NOT NULL, PRIMARY KEY (id), FOREIGN KEY (id) REFERENCES customer (id))";

        itemTable = "CREATE TABLE item(id INT " + autoIncrement + ", name VARCHAR(30) NOT NULL, " +
                "cost INT, number INT, PRIMARY KEY (id))";

        salesTable = "CREATE TABLE sales(id INT " + autoIncrement + ", item_id INT NOT NULL, customer_id INT NOT NULL, " +
                "PRIMARY KEY (id), cost INT NOT NULL, sold INT NOT NULL, totalCost INT NOT NULL, date DATE NOT NULL, " +
                "FOREIGN KEY (item_id) REFERENCES item (id), FOREIGN KEY (customer_id) REFERENCES customer (id))";
    }

    private final String selectAllItems = "SELECT * FROM item";
    private final String selectAllCustomers = "SELECT c.id, c.firstName, c.secondName, c.age, cg.gender, cc.address, cc.phoneNumber " +
            "FROM customer c NATURAL JOIN customerContacts cc INNER JOIN customerGender cg ON c.gender_id = cg.id";
    private final String selectAllCustomersWithoutGender = "SELECT c.id, c.firstName, c.secondName, c.age, cc.address, cc.phoneNumber " +
            "FROM customer c NATURAL JOIN customerContacts cc";
    private final String selectAllSales = "SELECT s.id, i.name, s.cost, s.sold, s.totalCost, c.firstName, c.secondName, s.date FROM item i " +
            "INNER JOIN sales s ON i.id = s.item_id " +
            "INNER JOIN customer c ON c.id = s.customer_id";

    private final String selectItemName = "SELECT name FROM item";
    private final String addItem = "INSERT INTO item(name, cost, number) VALUES (?, ?, ?)";

    private final String selectCustomerName = "SELECT id, firstName, secondName FROM customer";
    private final String selectCustomerPhoneNumber = "SELECT phoneNumber FROM customerContacts";

    private final String addCustomer = "INSERT INTO customer(firstName, secondName, age, gender_id) VALUES (?, ?, ?, ?)";
    private final String addCustomerContacts = "INSERT INTO customerContacts(address, phoneNumber) VALUES (?, ?)";

    private final String addBuy = "INSERT INTO sales(item_id, customer_id, cost, sold, totalCost, date) VALUES (?, ?, ?, ?, ?, ?)";


    protected Connection connection;
    private Statement statement;
    private PreparedStatement preparedStatement;
    private DatabaseMetaData dmd;
    private ResultSet resultSet;
    private ResultSetMetaData resultSetMetaData;

    private int row;
    private Object[][] doubleArray;
    private Object[] array;


    @Override
    public boolean connection(int localhostNumber, String username, String password) {

            connection = getConnection();
        try {
            statement = connection.createStatement();
            dmd = connection.getMetaData();
            resultSet = dmd.getTables(null, "APP", "%", null);
            if (!resultSet.next()) {
                System.out.println("Creating tables");

                statement.executeUpdate(customerGenderTable);
                statement.execute(insertCustomerMale);
                statement.execute(insertCustomerFemale);
                statement.executeUpdate(customerTable);
                statement.executeUpdate(customerContactsTable);
                statement.executeUpdate(itemTable);
                statement.executeUpdate(salesTable);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeConnection(connection);
        }
        return true;
    }

    @Override
    public Object[][] listOfItems() {
        return getListAllFromTable(selectAllItems, "selectAllItems");
    }

    @Override
    public Object[][] listOfCustomers() {

        return getListAllFromTable(selectAllCustomers, "selectAllCustomers");
    }

    @Override
    public Object[][] listOfSales() {
        if(this instanceof Derby) {
            System.out.println("Derby instanceof");
        }
        return getListAllFromTable(selectAllSales, "selectAllSales");
    }

    private Object[][] getListAllFromTable(String command, String name) {
        int id = 0;
        connection = getConnection();
        try {
            preparedStatement = connection.prepareStatement(command);
            preparedStatement.executeQuery();
            resultSet = preparedStatement.getResultSet();

            row = 0;
            while (resultSet.next()) {
                row++;
            }
            id = row;
            preparedStatement.executeQuery();
            resultSet = preparedStatement.getResultSet();
            doubleArray = new Object[row][];
            row = 0;
            switch (name) {
                case "selectAllItems":
                    while (resultSet.next()) {
                        array = new Object[]{
                                resultSet.getInt(1), resultSet.getString(2), resultSet.getInt(3), resultSet.getInt(4)
                        };
                        doubleArray[row] = array;
                        row++;
                    }
                    break;
                case "selectAllCustomers":
                    while (resultSet.next()) {
                        array = new Object[]{
                                resultSet.getInt(1), resultSet.getString(2) + " " + resultSet.getString(3), resultSet.getInt(4),
                                resultSet.getString(5), resultSet.getString(6), resultSet.getString(7)
                        };
                        doubleArray[row] = array;
                        row++;
                    }
                    break;
                case "selectAllSales":
                    for (int i = 1; i <= id; i++) {
                    String selectAllSales = "SELECT s.id, i.name, s.cost, s.sold, s.totalCost, c.firstName, c.secondName, s.date FROM sales s " +
                            "INNER JOIN item i ON i.id = s.item_id " +
                            "INNER JOIN customer c ON c.id = s.customer_id WHERE s.id = " + (i);
                    preparedStatement = connection.prepareStatement(selectAllSales);
                    preparedStatement.executeQuery();
                    resultSet = preparedStatement.getResultSet();
                    while (resultSet.next()) {
                        array = new Object[]{
                                resultSet.getInt(1), resultSet.getString(2), resultSet.getInt(3), resultSet.getInt(4),
                                resultSet.getInt(5), resultSet.getString(6) + " " + resultSet.getString(7), resultSet.getDate(8)
                        };
                        doubleArray[row] = array;
                        row++;
                        }
                    }
                break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(connection);
        }
        return doubleArray;
    }

    @Override
    public boolean addItem(String name, int cost, int number) {
        connection = getConnection();
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(selectItemName);
            while (resultSet.next()) {
                if(resultSet.getString("name").toLowerCase().equals(name.toLowerCase())) {
                    audioPlayer.haha();
                    JOptionPane.showMessageDialog(null, "Item already exists in the database", "Inane warning", JOptionPane.WARNING_MESSAGE);
                    return false;
                }
            }

            preparedStatement = connection.prepareStatement(addItem);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, cost);
            preparedStatement.setInt(3, number);

            preparedStatement.execute();

        } catch (SQLException e) {
            audioPlayer.haha();
            JOptionPane.showMessageDialog(null, "Data are not made successfully", "Inane error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return false;
        } finally {
            closeConnection(connection);
        }
        audioPlayer.excellent();
        JOptionPane.showMessageDialog(null, "Data is entered successfully");
        return true;
    }

    @Override
    public boolean addCustomer(String firstName, String secondName, int age, int gender_id, String address, String phoneNumber) {
        connection = getConnection();
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(selectCustomerName);
            while (resultSet.next()) {
                if(resultSet.getString("firstName").toLowerCase().equals(firstName.toLowerCase()) &&
                        resultSet.getString("secondName").toLowerCase().equals(secondName.toLowerCase())) {
                    audioPlayer.haha();
                    JOptionPane.showMessageDialog(null, "Customer already exists in the database", "Inane warning", JOptionPane.WARNING_MESSAGE);
                    return false;
                }
            }

            statement = connection.createStatement();
            resultSet = statement.executeQuery(selectCustomerPhoneNumber);
            while (resultSet.next()) {
                if(resultSet.getString("phoneNumber").equals(phoneNumber)) {
                    audioPlayer.haha();
                    JOptionPane.showMessageDialog(null, "Phone number already exists in the database", "Inane warning", JOptionPane.WARNING_MESSAGE);
                    return false;
                }
            }
            System.out.println(gender_id + "g");
            preparedStatement = connection.prepareStatement(addCustomer);
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, secondName);
            preparedStatement.setInt(3, age);
            preparedStatement.setInt(4, gender_id);
            preparedStatement.execute();

            preparedStatement = connection.prepareStatement(addCustomerContacts);
            preparedStatement.setString(1, address);
            preparedStatement.setString(2, phoneNumber);
            preparedStatement.execute();

        } catch (SQLException e) {
            audioPlayer.haha();
            JOptionPane.showMessageDialog(null, "Data are not made successfully", "Inane error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return false;
        } finally {
            closeConnection(connection);
        }
        audioPlayer.excellent();
        JOptionPane.showMessageDialog(null, "Data is entered successfully");
        return true;
    }

    @Override
    public List<String> listOfItemsName() {
        List<String> listOfItemsName = new ArrayList<>();
        connection = getConnection();
        try {
            statement = connection.createStatement();
            statement.executeQuery(selectItemName);
            resultSet = statement.getResultSet();

            while (resultSet.next()) {
                listOfItemsName.add(resultSet.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(connection);
        }
        return listOfItemsName;
    }

    @Override
    public List<String> listOfCustomersName() {
        List<String> listOfCustomersName = new ArrayList<>();
        connection = getConnection();
        try {
            statement = connection.createStatement();
            statement.executeQuery(selectCustomerName);
            resultSet = statement.getResultSet();

            while (resultSet.next()) {
                listOfCustomersName.add(resultSet.getString("firstName") + " " + resultSet.getString("secondName"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(connection);
        }
        return listOfCustomersName;
    }

    @Override
    public void listOFItemColumns(JComboBox<String> jComboBox) {
        listOfColumns(selectAllItems, jComboBox);
    }

    @Override
    public void listOCustomersColumns(JComboBox<String> jComboBox) {
        listOfColumns(selectAllCustomersWithoutGender, jComboBox);
    }

    private void listOfColumns(String command, JComboBox<String> jComboBox) {
        connection = getConnection();
        try {
            resultSet = connection.createStatement().executeQuery(command);

            resultSetMetaData = resultSet.getMetaData();
            int columnsCount = resultSetMetaData.getColumnCount();

            for(int i = 2; i <= columnsCount; i ++) {
                jComboBox.addItem(resultSetMetaData.getColumnName(i).toUpperCase());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            closeConnection(connection);
        }
    }

    @Override
    public boolean buyItem(int idItem, int idCustomer, int number) {
        String selectItemCost = "SELECT cost, number FROM item WHERE id = " + idItem;
        int itemNumber = 0;
        int itemCost = 0;
        Date date = new Date(Calendar.getInstance().getTime().getTime());
        connection = getConnection();
        try {
            statement = connection.createStatement();
            statement.executeQuery(selectItemCost);
            resultSet = statement.getResultSet();

            while (resultSet.next()) {
                if((itemNumber = resultSet.getInt("number")) < number) {
                    audioPlayer.haha();
                    JOptionPane.showMessageDialog(null, "Only " + itemNumber +
                            " left in stock ", "Inane warning", JOptionPane.WARNING_MESSAGE);
                    return false;
                }
                itemCost = resultSet.getInt("cost");
                System.out.println("Searching");
            }

            String changeItemNumber = "UPDATE item SET number = " + (itemNumber - number) + " WHERE id = " + idItem;
            statement = connection.createStatement();
            statement.execute(changeItemNumber);

            preparedStatement = connection.prepareStatement(addBuy);
            preparedStatement.setInt(1, idItem);
            preparedStatement.setInt(2, idCustomer);
            preparedStatement.setInt(3, itemCost);
            preparedStatement.setInt(4, number);
            preparedStatement.setInt(5, (itemCost * number));
            preparedStatement.setDate(6, date);
            preparedStatement.execute();
        } catch (SQLException e) {
            audioPlayer.haha();
            JOptionPane.showMessageDialog(null, "Data are not made successfully", "Inane error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return false;
        } finally {
            closeConnection(connection);
        }
        audioPlayer.excellent();
        JOptionPane.showMessageDialog(null, "Successful purchase");
        return true;
    }

    @Override
    public boolean editItem(int itemId, String column, String stringData, int intData) {
        String tableName = "item";

        return edit(tableName, itemId, column, stringData, intData);
    }

    @Override
    public boolean editCustomer(int customerId, String column, String stringData, int intData) {
        String tableName;
        if("address".toUpperCase().equals(column.toUpperCase()) || "phoneNumber".toUpperCase().equals(column.toUpperCase())) {
            tableName = "customerContacts";
        } else {
            tableName = "customer";
        }
        return edit(tableName, customerId, column, stringData, intData);
    }

    private boolean edit(String tableName, int id, String column, String stringData, int intData) {
        String editData = "UPDATE " + tableName + " SET " + column + " = ? WHERE id = ?";
        connection = getConnection();
        try {
            preparedStatement = connection.prepareStatement(editData);
            if(stringData.equals("")) {
                preparedStatement.setInt(1, intData);
            } else {
                preparedStatement.setString(1, stringData);
            }

            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Data are not edit successfully", "Inane error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return false;
        }
        audioPlayer.excellent();
        JOptionPane.showMessageDialog(null, "Data is edit successfully");
        return true;
    }
}
