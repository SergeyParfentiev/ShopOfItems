package shopOfItems.shopGUI;

import shopOfItems.logic.InterfaceShopOfItems;
import shopOfItems.logic.ShopOfItems;
import shopOfItems.tools.audioPlayer.AudioPlayer;
import shopOfItems.tools.createToolsDB.ToolsDB;
import shopOfItems.tools.decorate.Decorate;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.text.MaskFormatter;
import javax.swing.JFormattedTextField.AbstractFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.text.ParseException;
import java.util.List;

public class ShopOfItemsGUI {

    private InterfaceShopOfItems shop;

    private ToolsDB toolsDB;
    private AudioPlayer audioPlayer;
    private Decorate decorate;

    private JFrame f;

    private JRadioButtonMenuItem derbyItem;
    private JRadioButtonMenuItem mySQLItem;

    private JPanel panelShow;

    private JPanel noDatabaseConnection;
    private JPanel chooseDB;
    private JPanel connectionSuccessful;
    private JPanel connection;

    private JPanel listOfItemsPanel;
    private JPanel listOfCustomers;
    private JPanel listOfSales;

    private JPanel addItem;
    private JPanel addCustomer;
    private JPanel buy;

    private JPanel editItem;
    private JPanel editCustomer;

    private JFormattedTextField jFormattedTextField;

    private int DBMSGroupIdx = 0;

    private int customerGenderIdx = 0;

//    private JTextField insertDataField = new JTextField();

    private JRadioButton radioButton;

    private Object[][] data;

//    boolean fad;

    public ShopOfItemsGUI() {
        jFormattedTextField = new JFormattedTextField();
//        fad = true;
        toolsDB = new ToolsDB();
        audioPlayer = new AudioPlayer();
        decorate = new Decorate();
//        int DBMSGroupIdx = 0;
        shop = new ShopOfItems(audioPlayer);
        createJFrame(chooseDBPanel());
    }

    private void changeDecorate(Container pane) {
        audioPlayer.excellent();
        f.dispose();
        createJFrame(pane);
    }
    private void createJFrame(Container panel) {

        JFrame.setDefaultLookAndFeelDecorated(decorate.getDecorate());

        f = new JFrame("Bird Shop");
        f.setMinimumSize(new Dimension(800, 600));
//        f.setMaximumSize(new Dimension(800, 600));
//        f.setPreferredSize(new Dimension(800, 600));
//        f.setLocation(400, 100);
        f.setLocationRelativeTo(null);
//        JFrame.setDefaultLookAndFeelDecorated(true);

        final JMenuItem decorateItem = new JMenuItem("Change decorate");
        decorateItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                System.out.println(fad);
                decorate.setDecorate(!decorate.getDecorate());
//                System.out.println(fad);
                changeDecorate(f.getContentPane());
            }
        });

        JMenuItem playRingtone = new JMenuItem("On");
        playRingtone.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                audioPlayer.setVolume(true);
                audioPlayer.excellent();
//                soundText.setText("On");
            }
        });

        JMenuItem notPlayRingtone = new JMenuItem("Off");
        notPlayRingtone.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                audioPlayer.setVolume(false);
//                soundText.setText("Off");
            }
        });

        JMenu sound = new JMenu("Sound");
        sound.add(playRingtone);
        sound.add(notPlayRingtone);

        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                audioPlayer.excellent();
                f.dispose();
            }
        });

        JMenuItem viewItems = new JMenuItem("View items");
        viewItems.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(null != shop.getManagementSystem()) {
                    takeListOfItemsPanel();
                } else {
                    takeNoDatabaseConnectionPanel();
                }
            }
        });

        JMenuItem addItem = new JMenuItem("Add item");
        addItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(null != shop.getManagementSystem()) {
                    takeAddItemPanel();
                } else {
                    takeNoDatabaseConnectionPanel();
                }
            }
        });

        JMenuItem editItem = new JMenuItem("Edit item");
        editItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(null != shop.getManagementSystem()) {
                    takeEditItemPanel();
                } else {
                    takeNoDatabaseConnectionPanel();
                }
            }
        });

        JMenu items = new JMenu("Items");
        items.add(viewItems);
        items.add(addItem);
        items.add(editItem);

        JMenuItem viewCustomers = new JMenuItem("View customers");
        viewCustomers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(null != shop.getManagementSystem()) {
                    takeListOfCustomersPanel();
                } else {
                    takeNoDatabaseConnectionPanel();
                }
            }
        });

        JMenuItem addCustomer = new JMenuItem("Add customer");
        addCustomer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(null != shop.getManagementSystem()) {
                    takeAddCustomerPanel();
                } else {
                    takeNoDatabaseConnectionPanel();
                }
            }
        });

        JMenuItem editCustomer = new JMenuItem("Edit customer");
        editCustomer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(null != shop.getManagementSystem()) {
                    takeEditCustomerPanel(0, 0, defaultFormat());
                } else {
                    takeNoDatabaseConnectionPanel();
                }
            }
        });

        JMenu customers = new JMenu("Customers");
        customers.add(viewCustomers);
        customers.add(addCustomer);
        customers.add(editCustomer);

        JMenuItem viewOrder = new JMenuItem("View order");
        viewOrder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(null != shop.getManagementSystem()) {
                    takeListOfSalesPanel();
                } else {
                    takeNoDatabaseConnectionPanel();
                }
            }
        });

        JMenuItem makeOrder = new JMenuItem("Make order");
        makeOrder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(null != shop.getManagementSystem()) {
                    takeBuyPanel();
                } else {
                    takeNoDatabaseConnectionPanel();
                }
            }
        });

        JMenu order = new JMenu("Order");
        order.add(viewOrder);
        order.add(makeOrder);

        ButtonGroup DBMSGroupItem = new ButtonGroup();
//        ActionListener DBMSGroupListener = new RBListener("DBMSGroup");

        derbyItem = new JRadioButtonMenuItem("Choose derby");
//        derbyItem.setActionCommand("0");
//        derbyItem.addActionListener(DBMSGroupListener);
//        derby.setSelected(true);
        derbyItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                shop.closeConnection();
                DBMSGroupIdx = 0;
                if(shop.derbyConnection(0, null, null)) {
                    System.out.println("Derby Start");
                    derbyItem.setSelected(true);
                    takeConnectionSuccessfulPanel();
                } else {
                    System.out.println("Connection failed to Derby");
                }
            }
        });

        mySQLItem = new JRadioButtonMenuItem("Choose MySQL");
//        derbyItem.setActionCommand("1");
//        derbyItem.addActionListener(DBMSGroupListener);
        mySQLItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DBMSGroupIdx = 1;
                takeChooseMySQLDataConnectionPanel();
            }
        });

        DBMSGroupItem.add(derbyItem);
        DBMSGroupItem.add(mySQLItem);

        JMenuItem chooseDBMS = new JMenuItem("Panel for choosing");
        chooseDBMS.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                takeChooseDBMSPanel();
            }
        });

        JMenu file = new JMenu("File");
        file.add(decorateItem);
        file.add(sound);
        file.add(exit);

        JMenu shop = new JMenu("Shop");
        shop.add(items);
        shop.add(customers);
        shop.add(order);

        JMenu managerSystem = new JMenu("Management System");
        managerSystem.add(derbyItem);
        managerSystem.add(mySQLItem);
        managerSystem.add(chooseDBMS);



        JMenuBar jMenuBar = new JMenuBar();
        jMenuBar.add(file);
        jMenuBar.add(shop);
        jMenuBar.add(managerSystem);
//        jMenuBar.add(tools);
        f.getRootPane().setJMenuBar(jMenuBar);

//        transactionsPanel();
//        createSellingPanel();
        f.getContentPane().add(panel);

        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.pack();
        f.setVisible(true);


    }

    private JPanel noDatabaseConnectionPanel() {
        audioPlayer.haha();
        noDatabaseConnection = new JPanel(new GridBagLayout());

        JLabel noConnection = new JLabel("There is no database connection");

        JButton chooseDB = new JButton("Choose Database Management System");

        noDatabaseConnection.add(noConnection, new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 50, 0), 0, 0));
        noDatabaseConnection.add(chooseDB, new GridBagConstraints(0, 1, 1, 1, 0, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));

        chooseDB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                takeChooseDBMSPanel();
            }
        });

        return noDatabaseConnection;
    }

    private JPanel chooseDBPanel() {
//        soundText = new JLabel("   ");
//        soundText.setText("f");
        audioPlayer.chooseYourDestiny();

        shop.backToChooseDB();

        DBMSGroupIdx = 0;

        chooseDB = new JPanel(new GridBagLayout());
        JLabel choose = new JLabel("Choose Database Management System");
        JLabel or = new JLabel("Or");

        ButtonGroup DBMSGroup = new ButtonGroup();
//        ActionListener DBMSGroupListener = new RBListener("DBMSGroup");

        final JRadioButton derby = new JRadioButton("Derby");
        derby.setActionCommand("0");
        derby.addActionListener(new RBListener());
        derby.setSelected(true);

        final JRadioButton mySQL = new JRadioButton("MySQL");
        mySQL.setActionCommand("1");
        mySQL.addActionListener(new RBListener());

        DBMSGroup.add(derby);
        DBMSGroup.add(mySQL);

        JButton acceptChoose = new JButton("Accept");

        chooseDB.add(choose, new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        chooseDB.add(derby, new GridBagConstraints(0, 1, 1, 1, 0, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(30, 0, 0, 0), 0, 0));
        chooseDB.add(or, new GridBagConstraints(1, 1, 1, 1, 0, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(30, -120, 0, 0), 0, 0));
        chooseDB.add(mySQL, new GridBagConstraints(2, 1, 1, 1, 0, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(30, -60, 0, 0), 0, 0));
        chooseDB.add(acceptChoose, new GridBagConstraints(0, 2, 1, 1, 0, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(40, 75, 80, 0), 0, 0));
//        chooseDB.add(soundText, new GridBagConstraints(0, 3, 1, 1, 0, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(40, 75, 0, 0), 0, 0));



        acceptChoose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(DBMSGroupIdx == 0 ) {
                    if(shop.derbyConnection(0, null, null)) {
                        System.out.println("Derby Start");
                        derbyItem.setSelected(true);
//                        shop.dC();
                        takeConnectionSuccessfulPanel();
                    } else {
                        audioPlayer.haha();
                        System.out.println("Connection failed to Derby");
                    }
                } else {
                    takeChooseMySQLDataConnectionPanel();
                    mySQLItem.setSelected(true);
                }
            }
        });

        return chooseDB;
    }


    private JPanel transactionsPanel() {
        panelShow = new JPanel();
        panelShow.setMinimumSize(new Dimension(800, 600));
        String[] columnNames = {"TID", "Bird", "Count", "Customer", "Date"};

        JTable tTransactions = new JTable(data, columnNames);
//        tTransactions.setMaximumSize(new Dimension(100, 100));
        tTransactions.setRowSelectionAllowed(true);
        tTransactions.setCellSelectionEnabled(true);
        tTransactions.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tTransactions.setShowGrid(true);
        tTransactions.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
//        tTransactions.setPreferredSize(new Dimension(800, data.length * 16));
//        tTransactions.getColumnModel().getColumn(0).setMaxWidth(50);
//        tTransactions.getColumnModel().getColumn(1).setMaxWidth(150);
//     //   tTransactions.getColumnModel().getColumn(2).setMaxWidth(50);
//        tTransactions.getColumnModel().getColumn(3).setPreferredWidth(150);
//        tTransactions.getColumnModel().getColumn(4).setWidth(100);
     //   tTransactions.getColumnModel().getColumn(4).setMaxWidth(50);
//        tTransactions.getTableHeader().setReorderingAllowed(true);
//        DefaultTableCellRenderer re = new DefaultTableCellRenderer();
//        re.setHorizontalAlignment(JLabel.CENTER);
//        tTransactions.getColumnModel().getColumn(0).setCellRenderer(re);
        DefaultTableCellRenderer r = (DefaultTableCellRenderer) tTransactions.getDefaultRenderer(String.class);
        r.setHorizontalAlignment(JLabel.CENTER);
//        System.out.println(tTransactions.getColumnModel().getTotalColumnWidth());
//        for(int i = 0; i < 5; i++) {
//            tTransactions.setDefaultRenderer(tTransactions.getColumnClass(1), new DefaultTableCellRenderer(){
//                public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
//                    super.setHorizontalAlignment(SwingConstants.CENTER);
//                    super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
//                    return this;
//                }
//
//            });
//            tTransactions.getColumnModel().getColumn(i).setCellRenderer(new DefaultTableCellRenderer());
//            tTransactions.getColumnModel().getColumn(i).setMaxWidth(50);
//        }

//        tTransactions.getColumnModel().getColumn(0).setPreferredWidth(120);
//        tTransactions.getColumnModel().getColumn(1).setPreferredWidth(120);

        JScrollPane jScrollPane = new JScrollPane();
        jScrollPane.setPreferredSize(new Dimension(780, 530));
//        jScrollPane.setLocation(0,0);
//        jScrollPane.setMinimumSize(new Dimension(780, 500));
        jScrollPane.setViewportView(tTransactions);
//        jScrollPane.setMaximumSize(new Dimension(100, 100));
        panelShow.add(jScrollPane);
        return panelShow;
    }
//    private Object[][] getData() {
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yy HH:mm:ss");
//        transactions = shop.allTransactions();
//        data = new Object[transactions.size()][];
//        for(int i = 0; i < transactions.size(); i++) {
//            Transaction transaction = transactions.get(i);
//            Object[] tObject = new Object[]{transaction.getIdx(),
//                    transaction.getBird().getName(), transaction.getCount(), transaction.getCustomer().getName(),
//                    simpleDateFormat.format(transaction.getDate())};
//            data[i] = tObject;
//        }
//        return data;
//    }

    private JPanel listOfItemsPanel() {
        listOfItemsPanel = new JPanel();
        String[] columnNames = {"id", "name", "cost", "number"};
        scrollPane(listOfItemsPanel, shop.listOfItems(), columnNames);
        return listOfItemsPanel;
    }

    private JPanel listOfCustomersPanel() {
        listOfCustomers = new JPanel();
        String[] columnNames = {"id", "Name", "Age", "Gender", "Address", "Phone number"};
        scrollPane(listOfCustomers, shop.listOfCustomers(), columnNames);
        return listOfCustomers;
    }

    private JPanel listOfSalesPanel() {
        listOfSales = new JPanel();
        String[] columnNames = {"id", "Name", "Cost", "Sold", "Total cost", "Customer", "Date"};
        scrollPane(listOfSales, shop.listOfSales(), columnNames);
        return listOfSales;
    }

    private void scrollPane(JPanel jPanel, Object[][] list, String[] columnNames) {
        audioPlayer.excellent();
        jPanel.setLayout(new GridBagLayout());
        JTable getAll = new JTable(list, columnNames);

        getAll.getColumnModel().getColumn(0).setMaxWidth(30);

        for(int i = 1; i < getAll.getColumnCount(); i++) {
//            pack(getAll, i);
        }
        DefaultTableCellRenderer r = (DefaultTableCellRenderer) getAll.getDefaultRenderer(String.class);
        r.setHorizontalAlignment(JLabel.CENTER);

        JScrollPane jScrollPane = new JScrollPane(getAll);
        jScrollPane.setPreferredSize(new Dimension(780, 430));

        JButton back = new JButton("Back");
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                audioPlayer.excellent();
                takeConnectionSuccessfulPanel();
            }
        });

        jPanel.add(jScrollPane, new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 47, 0), 0, 0));
        jPanel.add(back, new GridBagConstraints(0, 1, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 40, 0), 0, 0));
    }

    private void pack(JTable jTable, int columnIdx) {
        int width = 0;
        for (int row = 0; row < jTable.getRowCount(); row++) {
            TableCellRenderer renderer = jTable.getCellRenderer(row, columnIdx);
            Component comp = jTable.prepareRenderer(renderer, row, columnIdx);
            width = Math.max(comp.getPreferredSize().width, width);
        }
        jTable.getColumnModel().getColumn(columnIdx).setMinWidth(width);
    }

    private JPanel addItemPanel() {
        audioPlayer.excellent();
        addItem = new JPanel(new GridBagLayout());

        JLabel itemText = new JLabel("Add a new item");
        JLabel fillText = new JLabel("Fill in the details");
        JLabel name = new JLabel("Name");
        JLabel cost = new JLabel("Cost");
        JLabel number = new JLabel("Number");

        final JTextField itemName = new JTextField(10);

        MaskFormatter formatter = null;
        try {
            formatter = new MaskFormatter("(###)###-##-##");

        } catch (ParseException e) {
            e.printStackTrace();
        }
//        JFormattedTextField itemCost = new JFormattedTextField(formatter);
        final JTextField itemCost = new JTextField(7);
//        itemCost.setPreferredSize(new Dimension(95, 30));
        itemCost.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char a = e.getKeyChar();
                if (!Character.isDigit(a)){
                    e.consume();
                }
            }
        });

        final JTextField itemNumber = new JTextField(7);
        itemNumber.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char a = e.getKeyChar();
                if (!Character.isDigit(a)){
                    e.consume();
                }
            }
        });

        JButton addItemButton = new JButton("Accept");
        addItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                if(!itemName.getText().equals("") && !itemCost.getText().equals("") && !itemNumber.getText().equals("")) {

                    shop.addItem(itemName.getText(), itemCost.getText(), itemNumber.getText()) ;


//                } else {
//                    audioPlayer.haha();
//                    JOptionPane.showMessageDialog(null, "Not correct data", "Inane warning", JOptionPane.WARNING_MESSAGE);
//                }
            }
        });

        JButton back = new JButton("Back");
        back.setPreferredSize(new Dimension(75, 25));
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                audioPlayer.excellent();
                takeConnectionSuccessfulPanel();
            }
        });

//        addItem.add(name, new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
//        addItem.add(cost, new GridBagConstraints(1, 0, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
//        addItem.add(number, new GridBagConstraints(2, 0, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
//        addItem.add(itemName, new GridBagConstraints(0, 1, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
//        addItem.add(itemCost, new GridBagConstraints(1, 1, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
//        addItem.add(itemNumber, new GridBagConstraints(2, 1, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        addItem.add(itemText, new GridBagConstraints(1, 0, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 20, 0), 0, 0));
        addItem.add(fillText, new GridBagConstraints(1, 1, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 20, 0), 0, 0));

        addItem.add(name, new GridBagConstraints(0, 2, 1, 1, 0, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(0, 0, 20, 0), 0, 0));
        addItem.add(cost, new GridBagConstraints(0, 3, 1, 1, 0, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        addItem.add(number, new GridBagConstraints(0, 4, 1, 1, 0, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(20, 0, 0, 0), 0, 0));

        addItem.add(itemName, new GridBagConstraints(1, 2, 1, 1, 0, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(0, 50, 20, 0), 0, 0));
        addItem.add(itemCost, new GridBagConstraints(1, 3, 1, 1, 0, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(0, 50, 0, 0), 0, 0));
        addItem.add(itemNumber, new GridBagConstraints(1, 4, 1, 1, 0, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(20, 50, 0, 0), 0, 0));

        addItem.add(addItemButton, new GridBagConstraints(1, 5, 1, 1, 0, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(60, 50, 0, 0), 0, 0));
        addItem.add(back, new GridBagConstraints(1, 6, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(30, 10, 0, 0), 0, 0));

        return addItem;
    }

    private JPanel addCustomerPanel() {
        audioPlayer.excellent();
        addCustomer = new JPanel(new GridBagLayout());
        customerGenderIdx = 0;

        JLabel customerText = new JLabel("Add a new customer");
        JLabel fillText = new JLabel("Fill in the details");

        JLabel firstName = new JLabel("First name");
        JLabel secondName = new JLabel("Second name");
        JLabel age = new JLabel("Age");
        JLabel gender = new JLabel("Gender");
        JLabel address = new JLabel("Address");
        JLabel phoneNumber = new JLabel("Phone Number");

        final JTextField customerFirstName = new JTextField(20);
        customerFirstName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char a = e.getKeyChar();
                if (!Character.isAlphabetic(a)){
                    e.consume();
                }
            }
        });
        final JTextField customerSecondName = new JTextField(20);
        customerSecondName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char a = e.getKeyChar();
                if (!Character.isAlphabetic(a)){
                    e.consume();
                }
            }
        });
        final JTextField customerAge = new JTextField(5);
        customerAge.setText("16");
        customerAge.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char a = e.getKeyChar();
                if (!Character.isDigit(a)){
                    e.consume();
                }
            }
        });

        final JComboBox<String> customerGenderBox = new JComboBox<>();
        customerGenderBox.addItem("Male");
        customerGenderBox.addItem("Female");
        customerGenderBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                customerGenderIdx = customerGenderBox.getSelectedIndex();
                JComboBox box = (JComboBox)e.getSource();
            }
        });

        final JTextField customerAddress = new JTextField(20);

        final JFormattedTextField customerPhoneNumber = new JFormattedTextField(phoneNumberFormat());
        customerPhoneNumber.setPreferredSize(new Dimension(95, 20));

        JButton addCustomerButton = new JButton("Accept");
        addCustomerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                if(!"".equals(customerFirstName.getText()) && !"".equals(customerSecondName.getText()) &&
//                        !"".equals(customerAge.getText()) && !"".equals(customerAddress.getText()) && !customerPhoneNumber.getText().contains(" ")) {
                    shop.addCustomer(customerFirstName.getText(), customerSecondName.getText(), customerAge.getText(),
                            customerGenderIdx + 1, customerAddress.getText(), customerPhoneNumber.getText());
//                        takeConnectionSuccessfulPanel();

//                    }
//                } else {
//                    audioPlayer.haha();
//                    JOptionPane.showMessageDialog(null, "Not correct data", "Inane warning", JOptionPane.WARNING_MESSAGE);

            }
        });

        JButton back = new JButton("Back");
        back.setPreferredSize(new Dimension(75, 25));
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                audioPlayer.excellent();
                takeConnectionSuccessfulPanel();
            }
        });

        addCustomer.add(customerText, new GridBagConstraints(1, 0, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 20, 0), 0, 0));
        addCustomer.add(fillText, new GridBagConstraints(1, 1, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 20, 0), 0, 0));

        addCustomer.add(firstName, new GridBagConstraints(0, 2, 1, 1, 0, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(0, 0, 20, 0), 0, 0));
        addCustomer.add(secondName, new GridBagConstraints(0, 3, 1, 1, 0, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        addCustomer.add(age, new GridBagConstraints(0, 4, 1, 1, 0, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(20, 0, 0, 0), 0, 0));
        addCustomer.add(gender, new GridBagConstraints(0, 5, 1, 1, 0, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(20, 0, 0, 0), 0, 0));
        addCustomer.add(address, new GridBagConstraints(0, 6, 1, 1, 0, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(20, 0, 0, 0), 0, 0));
        addCustomer.add(phoneNumber, new GridBagConstraints(0, 7, 1, 1, 0, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(20, 0, 0, 0), 0, 0));

        addCustomer.add(customerFirstName, new GridBagConstraints(1, 2, 1, 1, 0, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(0, 50, 20, 0), 0, 0));
        addCustomer.add(customerSecondName, new GridBagConstraints(1, 3, 1, 1, 0, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(0, 50, 0, 0), 0, 0));
        addCustomer.add(customerAge, new GridBagConstraints(1, 4, 1, 1, 0, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(20, 50, 0, 0), 0, 0));
        addCustomer.add(customerGenderBox, new GridBagConstraints(1, 5, 1, 1, 0, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(20, 50, 0, 0), 0, 0));
        addCustomer.add(customerAddress, new GridBagConstraints(1, 6, 1, 1, 0, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(20, 50, 0, 0), 0, 0));
        addCustomer.add(customerPhoneNumber, new GridBagConstraints(1, 7, 1, 1, 0, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(20, 50, 0, 0), 0, 0));
        addCustomer.add(addCustomerButton, new GridBagConstraints(1, 8, 1, 1, 0, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(20, 50, 0, 0), 0, 0));
        addCustomer.add(back, new GridBagConstraints(1, 9, 1, 1, 0, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(20, 50, 0, 0), 0, 0));

        return addCustomer;
    }

    private JPanel buyPanel() {
        audioPlayer.excellent();
        buy = new JPanel(new GridBagLayout());


        JLabel choose = new JLabel("Make a purchase");
        JLabel item = new JLabel("Choose item");
        final JLabel number = new JLabel("Choose number");
        JLabel customer = new JLabel("Choose customer");

        List<String> listOfItemsName = shop.listOfItemsName();

        final JComboBox<String> comboBoxItemsName = new JComboBox<>();
        for(String itemName : listOfItemsName) {
            comboBoxItemsName.addItem(itemName);
        }
//        comboBoxItemsName.addActionListener(new JComboBoxListener("Item"));

        final JTextField itemNumber = new JTextField(5);
        itemNumber.setText("1");
        itemNumber.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char a = e.getKeyChar();
                if (!Character.isDigit(a)){
                    e.consume();
                }
            }
        });

        List<String> listOfCustomersName = shop.listOfCustomersName();

        final JComboBox<String> comboBoxCustomersName = new JComboBox<>();
        for(String customerName : listOfCustomersName) {
            comboBoxCustomersName.addItem(customerName);
        }
//        comboBoxCustomersName.addActionListener(new JComboBoxListener("Customer"));

        JButton buyButton = new JButton("Buy");
        buyButton.setPreferredSize(new Dimension(75, 25));
        buyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                if(itemNumber.getText().length() > 0 && Integer.parseInt(itemNumber.getText()) != 0) {
                    shop.buyItem(comboBoxItemsName.getSelectedIndex() + 1, comboBoxCustomersName.getSelectedIndex() + 1,
                            itemNumber.getText());
                        System.out.println(comboBoxItemsName.getSelectedIndex() + 1 + " " + (comboBoxCustomersName.getSelectedIndex() + 1) + " " +
                                itemNumber.getText());

//                } else {
//                    audioPlayer.haha();
//                    JOptionPane.showMessageDialog(null, "Not correct data", "Inane warning", JOptionPane.WARNING_MESSAGE);
//                }
            }
        });

        JButton back = new JButton("Back");
        back.setPreferredSize(new Dimension(75, 25));
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                takeConnectionSuccessfulPanel();
            }
        });

        buy.add(choose, new GridBagConstraints(1, 0, 1, 1, 0, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(0, 0, 100, 250), 0, 0));
        buy.add(item, new GridBagConstraints(0, 1, 1, 1, 0, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(20, 150, 0, 0), 0, 0));
        buy.add(comboBoxItemsName, new GridBagConstraints(1, 1, 1, 1, 0, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(20, 80, 0, 0), 0, 0));
        buy.add(number, new GridBagConstraints(0, 2, 1, 1, 0, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(20, 150, 0, 0), 0, 0));
        buy.add(itemNumber, new GridBagConstraints(1, 2, 1, 1, 0, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(20, 80, 0, 0), 0, 0));
        buy.add(customer, new GridBagConstraints(0, 3, 1, 1, 0, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(20, 150, 0, 0), 0, 0));
        buy.add(comboBoxCustomersName, new GridBagConstraints(1, 3, 1, 1, 0, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(20, 80, 0, 0), 0, 0));
        buy.add(buyButton, new GridBagConstraints(1, 4, 1, 1, 0, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(60, 10, 0, 0), 0, 0));
        buy.add(back, new GridBagConstraints(1, 5, 1, 1, 0, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(30, 10, 50, 0), 0, 0));

        return this.buy;
    }

    private JPanel ediItemPanel() {
        audioPlayer.excellent();
        editItem = new JPanel(new GridBagLayout());

        JLabel editItemText = new JLabel("Edit Item");
        JLabel fillItemText = new JLabel("Fill the details");

        JLabel selectItemName = new JLabel("Select item name");
        JLabel selectItemColumn = new JLabel("Select item column");
        JLabel insertData = new JLabel("Insert data");

        List<String> listOfItemsName = shop.listOfItemsName();
//        fromBoxItemName = listOfItemsName.get(0);
        final JComboBox<String> comboBoxItemsName = new JComboBox<>();
        for(String itemName : listOfItemsName) {
            comboBoxItemsName.addItem(itemName);
        }

        final JTextField insertDataField = new JTextField(10);

        final JComboBox<String> jComboBoxItemColumns = new JComboBox<>();
        shop.itemColumns(jComboBoxItemColumns);
        jComboBoxItemColumns.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                insertDataField.setText("");

            }
        });
//        insertDataField = new JTextField(5);
        insertDataField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char a = e.getKeyChar();
//                System.out.println(jComboBoxItemColumns.getSelectedIndex());
                if (!Character.isAlphabetic(a) && jComboBoxItemColumns.getSelectedIndex() == 0){
                    e.consume();
                }
                if(!Character.isDigit(a) && jComboBoxItemColumns.getSelectedIndex() != 0) {
                    e.consume();
                }
            }
        });

        JButton accept = new JButton("Accept");
        accept.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                shop.editItem(comboBoxItemsName.getSelectedIndex() + 1, jComboBoxItemColumns.getSelectedIndex(),
                        (String) jComboBoxItemColumns.getSelectedItem(), insertDataField.getText());

                takeEditItemPanel();
            }
        });

        JButton back = new JButton("Back");
        back.setPreferredSize(new Dimension(75, 25));
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                takeConnectionSuccessfulPanel();
            }
        });

        editItem.add(editItemText, new GridBagConstraints(1, 0, 1, 1, 0, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(0, 5, 0, 0), 0, 0));
        editItem.add(fillItemText, new GridBagConstraints(1, 1, 1, 1, 0, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(20, -7, 0, 0), 0, 0));
        editItem.add(selectItemName, new GridBagConstraints(0, 2, 1, 1, 0, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(30, 0, 0, 0), 0, 0));
        editItem.add(comboBoxItemsName, new GridBagConstraints(1, 2, 1, 1, 0, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(30, 50, 0, 0), 0, 0));
        editItem.add(selectItemColumn, new GridBagConstraints(0, 3, 1, 1, 0, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(30, 0, 0, 0), 0, 0));
        editItem.add(jComboBoxItemColumns, new GridBagConstraints(1, 3, 1, 1, 0, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(30, 50, 0, 0), 0, 0));
        editItem.add(insertData, new GridBagConstraints(0, 4, 1, 1, 0, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(30, 0, 0, 0), 0, 0));
        editItem.add(insertDataField, new GridBagConstraints(1, 4, 1, 1, 0, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(30, 50, 0, 0), 0, 0));
        editItem.add(accept, new GridBagConstraints(1, 5, 1, 1, 0, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(50, -10, 0, 0), 0, 0));
        editItem.add(back, new GridBagConstraints(1, 6, 1, 1, 0, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(20, -10, 60, 0), 0, 0));

        return editItem;
    }

    private JPanel editCustomerPanel(int customerNameIdx, int customerColumnIdx, AbstractFormatter formatter) {
        audioPlayer.excellent();
        editCustomer = new JPanel(new GridBagLayout());

        JLabel editItemText = new JLabel("Edit Customer");
        JLabel fillItemText = new JLabel("Fill the details");

        JLabel selectCustomerName = new JLabel("Select customer name");
        JLabel selectCustomerColumn = new JLabel("Select customer column");
        JLabel insertData = new JLabel("Insert data");

        List<String> listOfCustomersName = shop.listOfCustomersName();
        final JComboBox<String> comboBoxItemsName = new JComboBox<>();
        for(String itemName : listOfCustomersName) {
            comboBoxItemsName.addItem(itemName);
        }
        comboBoxItemsName.setSelectedIndex(customerNameIdx);

        final JFormattedTextField insertDataField = new JFormattedTextField(formatter);
        insertDataField.setPreferredSize(new Dimension(150, 20));

        final JComboBox<String> jComboBoxCustomerColumns = new JComboBox<>();
        shop.customerColumns(jComboBoxCustomerColumns);

        jComboBoxCustomerColumns.setSelectedIndex(customerColumnIdx);
        jComboBoxCustomerColumns.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(jComboBoxCustomerColumns.getSelectedIndex() == 4) {
                    takeEditCustomerPanel(comboBoxItemsName.getSelectedIndex(), jComboBoxCustomerColumns.getSelectedIndex(), phoneNumberFormat());
                } else {
                    takeEditCustomerPanel(comboBoxItemsName.getSelectedIndex(), jComboBoxCustomerColumns.getSelectedIndex(), defaultFormat());
                }
                insertDataField.setText("");
            }
        });

        insertDataField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char a = e.getKeyChar();
                if(jComboBoxCustomerColumns.getSelectedIndex() == 3) {
                    e.setKeyCode(a);
                } else {
                    if (!Character.isDigit(a) && (jComboBoxCustomerColumns.getSelectedIndex() == 2 || jComboBoxCustomerColumns.getSelectedIndex() == 4)) {
                        e.consume();
                    }
                    if (!Character.isAlphabetic(a) && !(jComboBoxCustomerColumns.getSelectedIndex() == 2 || jComboBoxCustomerColumns.getSelectedIndex() == 4)) {
                        e.consume();
                    }
                }
            }
        });

        JButton accept = new JButton("Accept");
        accept.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                shop.editCustomer(comboBoxItemsName.getSelectedIndex() + 1, jComboBoxCustomerColumns.getSelectedIndex(),
                                (String) jComboBoxCustomerColumns.getSelectedItem(), insertDataField.getText());

                takeEditCustomerPanel(comboBoxItemsName.getSelectedIndex(), jComboBoxCustomerColumns.getSelectedIndex(),insertDataField.getFormatter());
            }
        });

        JButton back = new JButton("Back");
        back.setPreferredSize(new Dimension(75, 25));
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                takeConnectionSuccessfulPanel();
            }
        });

        editCustomer.add(editItemText, new GridBagConstraints(1, 0, 1, 1, 0, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(0, -13, 0, 0), 0, 0));
        editCustomer.add(fillItemText, new GridBagConstraints(1, 1, 1, 1, 0, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(20, -13, 0, 0), 0, 0));
        editCustomer.add(selectCustomerName, new GridBagConstraints(0, 2, 1, 1, 0, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(30, 0, 0, 0), 0, 0));
        editCustomer.add(comboBoxItemsName, new GridBagConstraints(1, 2, 1, 1, 0, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(30, 50, 0, 0), 0, 0));
        editCustomer.add(selectCustomerColumn, new GridBagConstraints(0, 3, 1, 1, 0, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(30, 0, 0, 0), 0, 0));
        editCustomer.add(jComboBoxCustomerColumns, new GridBagConstraints(1, 3, 1, 1, 0, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(30, 50, 0, 0), 0, 0));
        editCustomer.add(insertData, new GridBagConstraints(0, 4, 1, 1, 0, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(30, 0, 0, 0), 0, 0));
        editCustomer.add(insertDataField, new GridBagConstraints(1, 4, 1, 1, 0, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(30, 50, 0, 0), 0, 0));
        editCustomer.add(accept, new GridBagConstraints(1, 5, 1, 1, 0, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(50, -10, 0, 0), 0, 0));
        editCustomer.add(back, new GridBagConstraints(1, 6, 1, 1, 0, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(20, -10, 60, 0), 0, 0));

        return editCustomer;
    }

    private JPanel allFromSQL() {
        JPanel panel = new JPanel();
        panel.setMinimumSize(new Dimension(800, 600));

        String[] columnNames = {"id", "title", "desc", "rating", "published", "date", "icon length"};
//        List list = shop.getAll();
//        byte[] bytes = (byte[]) list.get(6);
        Object[][] objects = new Object[1][];
        objects[0] = new Object[7];
//        objects[0] = new Object[]{list.get(0), list.get(1), list.get(2), list.get(3), list.get(4), list.get(5), bytes.length};

//        String s = "id: " + list.get(0) + "\n title: " + list.get(1) + "\n desc: " + list.get(2) + "\n rating: " + list.get(3)
//                + "\n published: " + list.get(4) + "\n date: " + list.get(5) + "\n icon length: " + bytes.length;

        JTable gatAll = new JTable(objects, columnNames);

        gatAll.setRowSelectionAllowed(true);
        gatAll.setCellSelectionEnabled(true);
        gatAll.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        gatAll.setShowGrid(true);
        gatAll.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

        DefaultTableCellRenderer r = (DefaultTableCellRenderer) gatAll.getDefaultRenderer(String.class);
        r.setHorizontalAlignment(JLabel.CENTER);

//        gatAll.getColumnModel().getColumn(1).setPreferredWidth(120);
//        gatAll.getColumnModel().getColumn(2).setPreferredWidth(120);
//        gatAll.getColumnModel().getColumn(3).setPreferredWidth(120);
//        gatAll.getColumnModel().getColumn(4).setPreferredWidth(120);
//        gatAll.getColumnModel().getColumn(5).setPreferredWidth(120);
//        for(int i = 0; i < 7; i++) {
//            gatAll.getColumnModel().getColumn(i).setPreferredWidth(60);
//        }
        JScrollPane jScrollPane = new JScrollPane(gatAll);
        jScrollPane.setPreferredSize(new Dimension(780, 530));

        panel.setLayout(new GridBagLayout());

//        panel.add(gatAll, new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));

        panel.add(jScrollPane);
        return panel;
    }

    private JPanel chooseMySQLDataConnection() {
        audioPlayer.excellent();
        connection = new JPanel(new GridBagLayout());
        connection.setFocusable(true);

        JLabel notification = new JLabel("Fill in the details");
        final JLabel localhost = new JLabel("Localhost");
        JLabel user = new JLabel("User");
        JLabel password = new JLabel("Password");

//        NumberFormat nfLocalhost = NumberFormat.getNumberInstance();
        final JTextField localhostNumber = new JTextField(3);
        localhostNumber.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char a = e.getKeyChar();
                if (!Character.isDigit(a)){
                    e.consume();
                }
            }
        });
        localhostNumber.setText("3306");

        final JTextField userData = new JTextField(10);

        final JTextField passwordData = new JTextField(10);

        JButton connect = new JButton("Connect");

        final JLabel wrongData = new JLabel(" ");

        connection.add(notification, new GridBagConstraints(1, 0, 1, 1, 0, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(0, 0, 15, 0), 0, 0));
        connection.add(localhost, new GridBagConstraints(0, 1, 1, 1, 0, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(0, 0, 15, 0), 0, 0));
        connection.add(localhostNumber, new GridBagConstraints(1, 1, 1, 1, 0, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(0, 50, 15, 0), 0, 0));
        connection.add(user, new GridBagConstraints(0, 2, 1, 1, 0, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(0, 0, 15, 0), 0, 0));
        connection.add(userData, new GridBagConstraints(1, 2, 1, 1, 0, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(0, 50, 15, 0), 0, 0));
        connection.add(password, new GridBagConstraints(0, 3, 1, 1, 0, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        connection.add(passwordData, new GridBagConstraints(1, 3, 1, 1, 0, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(0, 50, 0, 0), 0, 0));
        connection.add(connect, new GridBagConstraints(1, 4, 1, 1, 0, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(30, 0, 70, 0), 0, 0));
//        connection.add(wrongData, new GridBagConstraints(1, 5, 1, 1, 0, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(20, -50, 0, -50), 0, 0));

        connect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(shop.mySQLConnection(Integer.parseInt(localhostNumber.getText()), userData.getText(), passwordData.getText())) {
                    System.out.println("MySQL Start");
                    audioPlayer.excellent();
                    takeConnectionSuccessfulPanel();
                } else {
                    audioPlayer.haha();
                    JOptionPane.showMessageDialog(null, "Not correct data", "Inane warning", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        return connection;
    }

    private JPanel connectionSuccessfulPanel() {
        audioPlayer.excellent();
        connectionSuccessful = new JPanel(new GridBagLayout());

        JLabel connectionLabel = new JLabel("Connection Successful");
        JLabel canWorkLabel = new JLabel("You can work now");

        connectionSuccessful.add(connectionLabel, new GridBagConstraints(1, 0, 1, 1, 0, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(0, 29, 20, 0), 0, 0));
        connectionSuccessful.add(canWorkLabel, new GridBagConstraints(1, 1, 1, 1, 0, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(0, 42, 100, 0), 0, 0));

        JButton items = new JButton("View items");
        JButton customers = new JButton("View customers");
        JButton sales = new JButton("View order");
        JButton addItem = new JButton("Add item");
        JButton addCustomer = new JButton("Add customer");
        JButton buy = new JButton("Make order");
        JButton editItem = new JButton("Edit item");
        JButton editCustomer = new JButton("Edit customer");
        JButton back = new JButton("Back");

        Dimension dimension = new Dimension(130, 25);

        items.setPreferredSize(dimension);
        customers.setPreferredSize(dimension);
        sales.setPreferredSize(dimension);
        addItem.setPreferredSize(dimension);
        addCustomer.setPreferredSize(dimension);
        buy.setPreferredSize(dimension);
        editItem.setPreferredSize(dimension);
        editCustomer.setPreferredSize(dimension);
        back.setPreferredSize(dimension);

        connectionSuccessful.add(items, new GridBagConstraints(0, 2, 1, 1, 0, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(0, -20, 0, 0), 0, 0));
        connectionSuccessful.add(customers, new GridBagConstraints(1, 2, 1, 1, 0, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(0, 30, 0, 0), 0, 0));
        connectionSuccessful.add(sales, new GridBagConstraints(2, 2, 1, 1, 0, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(0, 30, 0, 0), 0, 0));

        connectionSuccessful.add(addItem, new GridBagConstraints(0, 3, 1, 1, 0, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(30, -20, 0, 0), 0, 0));
        connectionSuccessful.add(addCustomer, new GridBagConstraints(1, 3, 1, 1, 0, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(30, 30, 0, 0), 0, 0));
        connectionSuccessful.add(buy, new GridBagConstraints(2, 3, 1, 1, 0, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(30, 30, 0, 0), 0, 0));

        connectionSuccessful.add(editItem, new GridBagConstraints(0, 4, 1, 1, 0, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(30, -20, 0, 0), 0, 0));
        connectionSuccessful.add(editCustomer, new GridBagConstraints(1, 4, 1, 1, 0, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(30, 30, 0, 0), 0, 0));
        connectionSuccessful.add(back, new GridBagConstraints(2, 4, 1, 1, 0, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(30, 30, 0, 0), 0, 0));

        items.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                takeListOfItemsPanel();
            }
        });

        customers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                takeListOfCustomersPanel();
            }
        });

        sales.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                takeListOfSalesPanel();
            }
        });

        addItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                takeAddItemPanel();
            }
        });

        addCustomer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                takeAddCustomerPanel();
            }
        });

        buy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(shop.listOfItems().length > 0 && shop.listOfCustomers().length > 0) {
                    takeBuyPanel();
                } else {
                    JOptionPane.showMessageDialog(null, "There is no single item or customer", "Inane warning", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        editItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                takeEditItemPanel();
            }
        });

        editCustomer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                takeEditCustomerPanel(0, 0, defaultFormat());
            }
        });

        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                takeChooseDBMSPanel();
            }
        });

        return connectionSuccessful;
    }

    private void takeNoDatabaseConnectionPanel() {
        f.getContentPane().removeAll();
        f.getContentPane().add(noDatabaseConnectionPanel());

        f.pack();
        f.repaint();
    }

    private void takeConnectionSuccessfulPanel() {
        f.getContentPane().removeAll();
        f.getContentPane().add(connectionSuccessfulPanel());

        f.pack();
        f.repaint();
    }

    private void takeChooseDBMSPanel() {
        f.getContentPane().removeAll();
        f.getContentPane().add(chooseDBPanel());

        f.pack();
        f.repaint();
    }

    private void takeShowPanel() {
        f.getContentPane().removeAll();
        f.getContentPane().add(transactionsPanel());

        f.pack();
        f.repaint();
    }
    private void takeChooseMySQLDataConnectionPanel() {
        f.getContentPane().removeAll();
        f.getContentPane().add(chooseMySQLDataConnection());

        f.pack();
        f.repaint();
    }

    private void takeListOfItemsPanel() {
        f.getContentPane().removeAll();
        f.getContentPane().add(listOfItemsPanel());

        f.pack();
        f.repaint();
    }

    private void takeListOfCustomersPanel() {
        f.getContentPane().removeAll();
        f.getContentPane().add(listOfCustomersPanel());

        f.pack();
        f.repaint();
    }

    private void takeListOfSalesPanel() {
        f.getContentPane().removeAll();
        f.getContentPane().add(listOfSalesPanel());

        f.pack();
        f.repaint();
    }

    private void takeAddItemPanel() {
        f.getContentPane().removeAll();
        f.getContentPane().add(addItemPanel());

        f.pack();
        f.repaint();
    }

    private void takeAddCustomerPanel() {
        f.getContentPane().removeAll();
        f.getContentPane().add(addCustomerPanel());

        f.pack();
        f.repaint();
    }

    private void takeBuyPanel() {
        f.getContentPane().removeAll();
        f.getContentPane().add(buyPanel());

        f.pack();
        f.repaint();
    }

    private void takeEditItemPanel() {
        f.getContentPane().removeAll();
        f.getContentPane().add(ediItemPanel());

        f.pack();
        f.repaint();
    }

    private void takeEditCustomerPanel(int customerNameIdx, int customerColumnIdx, AbstractFormatter formatter) {
        f.getContentPane().removeAll();
        f.getContentPane().add(editCustomerPanel(customerNameIdx, customerColumnIdx, formatter));

        f.pack();
        f.repaint();
    }

    private class JComboBoxListener implements ActionListener {
        private String name;
        JComboBoxListener(String name) {
            this.name = name;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            if(name.equals("Item")) {
//                JComboBox box = (JComboBox) e.getSource();
//                fromBoxItemName = (String) box.getSelectedItem();
//                fromBoxItemIdx = (Integer) e.g
//                fromBoxItemIdx = Integer.parseInt(e.getActionCommand());
//                System.out.println(fromBoxItemIdx);
            }

            if(name.equals("Customer")) {
//                JComboBox box = (JComboBox)e.getSource();
//                fromBoxCustomerName = (String)box.getSelectedItem();
//                fromBoxCustomerIdx = Integer.parseInt(e.getActionCommand());
//                System.out.println(fromBoxCustomerName);
            }
        }
    };

    private AbstractFormatter defaultFormat() {
        return jFormattedTextField.getFormatter();
    }

    private AbstractFormatter phoneNumberFormat() {
        MaskFormatter formatter = null;
        try {
            formatter = new MaskFormatter("(###)###-##-##");

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formatter;
    }

    private class RBListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
                DBMSGroupIdx = Integer.parseInt(e.getActionCommand());
        }
    }
}
