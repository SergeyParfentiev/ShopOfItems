package shopOfItems.logic;

import shopOfItems.DBManagers.*;
import shopOfItems.tools.audioPlayer.AudioPlayer;

import javax.swing.*;
import java.util.List;

public class ShopOfItems implements InterfaceShopOfItems{

    DBManagementSystem derby;
    DBManagementSystem mySQL;
    DBManagementSystem managementSystem;

    AudioPlayer audioPlayer;

    public ShopOfItems(AudioPlayer audioPlayer) {
        managementSystem = null;
        derby = new Derby(audioPlayer);
        mySQL = new MySQL(audioPlayer);
        this.audioPlayer = audioPlayer;
    }

    public boolean mySQLConnection(int localhostNumber, String username, String password) {
        managementSystem = mySQL;

        return managementSystem.checkConnection(localhostNumber, username, password) && managementSystem.connection(localhostNumber, username, password);
    }

    public boolean derbyConnection(int localhostNumber, String username, String password) {
        managementSystem = derby;

        return managementSystem.connection(localhostNumber, username, password);
    }

    public Object[][] listOfItems() {

        return managementSystem.listOfItems();
    }

    public Object[][] listOfCustomers() {
        return managementSystem.listOfCustomers();
    }

    public Object[][] listOfSales() {
        return managementSystem.listOfSales();
    }

    public boolean addItem(String name, String cost, String number) {
        if(!"".equals(name) && !"".equals(String.valueOf(cost)) && !"".equals(number)) {
            managementSystem.addItem(name, Integer.parseInt(cost), Integer.parseInt(number));
            return true;
        } else {
            audioPlayer.haha();
            JOptionPane.showMessageDialog(null, "Not correct data", "Inane warning", JOptionPane.WARNING_MESSAGE);
            return false;
        }
    }

    public boolean addCustomer(String fName, String sName, String age, int gender_id, String address, String phoneNumber) {
        if(!"".equals(fName) && !"".equals(sName) && !"".equals(age) && !"".equals(address) && !phoneNumber.contains(" ")) {
            managementSystem.addCustomer(fName, sName, Integer.parseInt(age), gender_id, address, phoneNumber);
            return true;
        } else {
            audioPlayer.haha();
            JOptionPane.showMessageDialog(null, "Not correct data", "Inane warning", JOptionPane.WARNING_MESSAGE);
            return false;
        }
    }

    public List<String> listOfItemsName() {
        return managementSystem.listOfItemsName();
    }

    public List<String> listOfCustomersName() {
        return managementSystem.listOfCustomersName();
    }

    public boolean buyItem(int idItem, int idCustomer, String itemNumber) {
        if(itemNumber.length() > 0 && Integer.parseInt(itemNumber) != 0) {
            managementSystem.buyItem(idItem, idCustomer, Integer.parseInt(itemNumber));
            return true;
        } else {
            audioPlayer.haha();
            JOptionPane.showMessageDialog(null, "Not correct data", "Inane warning", JOptionPane.WARNING_MESSAGE);
            return false;
        }
    }

    public void itemColumns(JComboBox<String> jComboBox) {
        managementSystem.listOFItemColumns(jComboBox);
    }

    public void customerColumns(JComboBox<String> jComboBox) {
        managementSystem.listOCustomersColumns(jComboBox);
    }

    public boolean editItem(int itemId, int boxColumnIdx, String column, String data) {
        if(!"".equals(data)) {
            if(boxColumnIdx == 0) {
                managementSystem.editItem(itemId, column, data, 0);
            } else {
                managementSystem.editItem(itemId, column, "", Integer.parseInt(data));
            }
            return true;
        } else {
            audioPlayer.haha();
            JOptionPane.showMessageDialog(null, "Not correct data", "Inane warning", JOptionPane.WARNING_MESSAGE);
            return false;
        }
    }

    public boolean editCustomer(int customerId, int boxColumnIdx,  String column, String data) {
        if(!"".equals(data)) {
            if(boxColumnIdx == 2) {
                managementSystem.editCustomer(customerId, column, "", Integer.parseInt(data));
            }else {
                managementSystem.editCustomer(customerId, column, data, 0);
            }
            return true;
        } else {
            audioPlayer.haha();
            JOptionPane.showMessageDialog(null, "Not correct data", "Inane warning", JOptionPane.WARNING_MESSAGE);
            return false;
        }

    }

    public DBManagementSystem getManagementSystem() {
        return managementSystem;
    }

    public void setManagementSystem(DBManagementSystem dbManagementSystem) {
        this.managementSystem = dbManagementSystem;
    }

    public void backToChooseDB() {
        managementSystem = null;
    }
}
