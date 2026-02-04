/**
 * The GUI class implements methods that add/delete/show the databases
 * that contain vehicles, users, and transactions. Vehicles, users, and
 * transactions are stored in three ArrayLists. 
 */
package assign5;


import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ConcurrentModificationException;
import java.util.Objects;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.Formatter;
import java.util.logging.SimpleFormatter;

/**
 * 
 */
public class GUI extends JFrame implements ItemListener 
{
    private JPanel cards; //a panel that uses CardLayout
    private final static String vehicleMngmt = "Vehicle Managment";
    private final static String userMngmt = "User Management";
    private final static String transMngmt = "Transaction Management";
    
    // Vehicle managment components
    private JRadioButton showVehiclesRadioButton;
    private JRadioButton addVehicleRadioButton;
    private JRadioButton deleteVehicleRadioButton;
    private JRadioButton searchByVinRadioButton;
    private JRadioButton searchByPriceRadioButton;
    private JButton manageVehicles;
    
    // User management components
    private JRadioButton showUsersRadioButton;
    private JRadioButton addUserRadioButton;
    private JRadioButton deleteUserRadioButton;
    private JRadioButton updateUserRadioButton;
    private JButton manageUsers;
    
    // User management components
    private JRadioButton showTransRadioButton;
    private JRadioButton addTransRadioButton;
    private JButton manageTrans;
    
    private final ArrayList<Vehicle> vDatabase = new ArrayList<>();
    private final ArrayList<User> uDatabase = new ArrayList<>();
    private final ArrayList<Sales> sDatabase = new ArrayList<>();

    public GUI() throws HeadlessException {
        super("Dealership");
        initializeGui();
        initializeEvents();
    }
    
    private void initializeGui() {
      
        this.setSize(500, 400);
        Dimension windowSize = this.getSize();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(screenSize.width/2 - windowSize.width/2, screenSize.height/2 - windowSize.height/2);
        Container pane = this.getContentPane();
        //pane.setLayout(new GridBagLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //Create the panel that contains the "cards".
        cards = new JPanel(new CardLayout());
        
        //Put the JComboBox in a JPanel to get a nicer look.
        JPanel comboBoxPane = new JPanel(); //use FlowLayout
        String comboBoxItems[] = { vehicleMngmt, userMngmt, transMngmt };
        
        JComboBox cb = new JComboBox(comboBoxItems);
        cb.setEditable(true);
        cb.addItemListener(this);
        comboBoxPane.add(cb);
        
        //Create the "cards".
        
        JPanel vehicleMngmtPanel = new JPanel(new GridLayout(0, 1));
        showVehiclesRadioButton =  new JRadioButton("Show all vehicles");
        showVehiclesRadioButton.setSelected(true);
        addVehicleRadioButton = new JRadioButton("Add a new vehicle");
        deleteVehicleRadioButton = new JRadioButton("Delete a vehicle");
        searchByVinRadioButton = new JRadioButton("Search vehicle by VIN");
        searchByPriceRadioButton = new JRadioButton("Search vehicles by price");
        
        ButtonGroup group = new ButtonGroup();
        group.add(showVehiclesRadioButton);
        group.add(addVehicleRadioButton);
        group.add(deleteVehicleRadioButton);
        group.add(searchByVinRadioButton);
        group.add(searchByPriceRadioButton);
        
        
        vehicleMngmtPanel.add(showVehiclesRadioButton);
        vehicleMngmtPanel.add(addVehicleRadioButton);
        vehicleMngmtPanel.add(deleteVehicleRadioButton);
        vehicleMngmtPanel.add(searchByVinRadioButton);
        vehicleMngmtPanel.add(searchByPriceRadioButton);
        
        manageVehicles = new JButton("Go");
        //manageVehicles.setActionCommand("OUCH!");
        vehicleMngmtPanel.add(manageVehicles);
        
        
        // User management
        JPanel userMngmtPanel = new JPanel(new GridLayout(0, 1));
        showUsersRadioButton =  new JRadioButton("Show all users");
        showUsersRadioButton.setSelected(true);
        addUserRadioButton = new JRadioButton("Add a new user");
        deleteUserRadioButton = new JRadioButton("Delete a user");
        updateUserRadioButton = new JRadioButton("Update user");
        
        ButtonGroup userRadioGroup = new ButtonGroup();
        userRadioGroup.add(showUsersRadioButton);
        userRadioGroup.add(addUserRadioButton);
        userRadioGroup.add(deleteUserRadioButton);
        userRadioGroup.add(updateUserRadioButton);
        
        
        userMngmtPanel.add(showUsersRadioButton);
        userMngmtPanel.add(addUserRadioButton);
        userMngmtPanel.add(deleteUserRadioButton);
        userMngmtPanel.add(updateUserRadioButton);
        
        manageUsers = new JButton("Go");
        //manageVehicles.setActionCommand("OUCH!");
        userMngmtPanel.add(manageUsers);
        
        
        // Transaction management
        JPanel transMngmtPanel = new JPanel(new GridLayout(0, 1));
        showTransRadioButton =  new JRadioButton("Show all sale transactions");
        showTransRadioButton.setSelected(true);
        addTransRadioButton = new JRadioButton("Add a new sale transaction");
        
        
        ButtonGroup transRadioGroup = new ButtonGroup();
        transRadioGroup.add(showTransRadioButton);
        transRadioGroup.add(addTransRadioButton);
        
        
        transMngmtPanel.add(showTransRadioButton);
        transMngmtPanel.add(addTransRadioButton);
        
        manageTrans = new JButton("Go");
        //manageVehicles.setActionCommand("OUCH!");
        transMngmtPanel.add(manageTrans);
        
        
        cards.add(vehicleMngmtPanel, vehicleMngmt);
        cards.add(userMngmtPanel, userMngmt);
        cards.add(transMngmtPanel, transMngmt);
        
        this.add(comboBoxPane, BorderLayout.PAGE_START);
        this.add(cards, BorderLayout.CENTER);
    }
    
    private void initializeEvents() {
        manageVehicles.addActionListener(new ActionListener () { 
            public void actionPerformed(ActionEvent e) {
                if (showVehiclesRadioButton.isSelected())
                    showAllVehicles();
                else if (addVehicleRadioButton.isSelected())
                    addNewVehicle();
                else if (deleteVehicleRadioButton.isSelected())
                    deleteVehicle();
                else if (searchByVinRadioButton.isSelected())
                    searchVehicleByVin();
                else if (searchByPriceRadioButton.isSelected())
                    searchVehicleByPrice();
            }
        });
        
        manageUsers.addActionListener(new ActionListener () {
            public void actionPerformed(ActionEvent e) {
                if (showUsersRadioButton.isSelected())
                    showAllUsers();
                else if (addUserRadioButton.isSelected())
                    addNewUser();
                else if (deleteUserRadioButton.isSelected())
                    deleteUser();
                else if (updateUserRadioButton.isSelected())
                    updateUser();
            }
        });
        
        manageTrans.addActionListener(new ActionListener () { 
            public void actionPerformed(ActionEvent e ) {
                if (showTransRadioButton.isSelected())
                    showAllTrans();
                else if (addTransRadioButton.isSelected())
                    addNewTrans();
            }
        });
    }
    
    public void itemStateChanged(ItemEvent evt) {
        CardLayout cl = (CardLayout)(cards.getLayout());
        cl.show(cards, (String)evt.getItem());
    }
    
    /**
     * This method displays all Vehicles currently 
     * in the database onto a table
     */
    private void showAllVehicles() {
        Logger logger = Logger.getLogger("showAllVehicles");
        try {
            FileHandler handler = new FileHandler("assign5-log.txt");
            handler.setFormatter(new SimpleFormatter());
            logger.addHandler(handler);
        } catch (IOException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        logger.log(Level.FINE, "LOGGED IN SHOW ALL VEHICLES");
        
        JPanel topPanel = new JPanel();
        topPanel.setSize(400, 300);
        topPanel.setLayout(new BorderLayout());
        getContentPane().add(topPanel);

        // Create columns names
        String columnNames[] = {"VIN", "MAKE", "MODEL", "YEAR", "PRICE"};

        DefaultTableModel tableModel = new DefaultTableModel(columnNames, vDatabase.size());

        
        // Create some data
        for(int i = 0; i < vDatabase.size(); i++) {
            String vin = vDatabase.get(i).getVin();
            String make = vDatabase.get(i).getMake();
            String model = vDatabase.get(i).getModel();
            Integer year = vDatabase.get(i).getYear();
            Float price = vDatabase.get(i).getPrice();
            
            Object[] data = {vin,make,model,year,price};
            tableModel.addRow(data);
        }
        
        // Create a new table instance
        JTable table = new JTable(tableModel);
        
        table.setFillsViewportHeight(true);
        
        // Add the table to a scrolling pane
        JScrollPane scrollPane = new JScrollPane(table);
        topPanel.add(scrollPane, BorderLayout.CENTER);
        JOptionPane.showMessageDialog(this, topPanel, "Search results", JOptionPane.PLAIN_MESSAGE);
    }
    /**
     * This method adds a new vehicle into the database after user inputs
     * vehicle information
     */
    private void addNewVehicle() {
        JPanel panel = new JPanel();
        panel.setSize(400, 300);
        getContentPane().add(panel);
        panel.setLayout(new FormLayout());
        
        JTextField vinTF = new JTextField(5);
        JTextField makeTF = new JTextField(20);
        JTextField modelTF = new JTextField(20);
        JTextField yearTF = new JTextField(4);
        JTextField mileTF = new JTextField(6);
        JTextField priceTF = new JTextField(6);
        JTextField extra1TF = new JTextField(20);
        JTextField extra2TF = new JTextField(20);
        
        panel.add(new JLabel("VIN"));
        panel.add(vinTF);
        panel.add(new JLabel("Make"));
        panel.add(makeTF);
        panel.add(new JLabel("Model"));
        panel.add(modelTF);
        panel.add(new JLabel("Year"));
        panel.add(yearTF);
        panel.add(new JLabel("Mileage"));
        panel.add(mileTF);
        panel.add(new JLabel("Price"));
        panel.add(priceTF);
        panel.add(new JLabel("Vehicle type:"));
        
        JRadioButton car = new JRadioButton("Passenger Car");
        car.setSelected(true);
        JRadioButton truck = new JRadioButton("Truck");
        JRadioButton motorcycle = new JRadioButton("Motorcycle");
        ButtonGroup group = new ButtonGroup();
        group.add(car);
        group.add(truck);
        group.add(motorcycle);
        
        JPanel radiosPanel = new JPanel(new FlowLayout());
        radiosPanel.add(car);
        radiosPanel.add(truck);
        radiosPanel.add(motorcycle);
        
        panel.add(radiosPanel);
        
        panel.add(new JLabel("Extra info(Style, Max Load, Type..)"));
        panel.add(extra1TF);
        
        panel.add(new JLabel("Extra info 2(Length, Engine..)"));
        panel.add(extra2TF);        
       
        JOptionPane.showMessageDialog(this, panel, "Add new vehicle", JOptionPane.PLAIN_MESSAGE);        
        
        String vin = vinTF.getText().toUpperCase();
        if(!checkVIN(vin))
            return;
        
        String make = makeTF.getText().toUpperCase();
        if(!checkMake(make))
            return;
        
        String model = modelTF.getText().toUpperCase();
        if(!checkModel(model))
            return;
       
        Integer year;
        try {
            year = Integer.parseInt(yearTF.getText());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Invalid Year", "Error", 
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        if(!checkYear(year))
            return;
        
        Integer mileage;
        try {
            mileage = Integer.parseInt(mileTF.getText());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Invalid Year", "Error", 
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        if(!checkMileage(mileage))
            return;
        
        Float price;
        try {
            price = Float.parseFloat(priceTF.getText());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Invalid Price", "Error", 
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        if(!checkPrice(price))
            return;
        
        if(car.isSelected()) {
            String style = extra1TF.getText().toUpperCase();
            String extra2 = extra2TF.getText().toUpperCase();
            if(!checkExtras(style, extra2))
                return;
            vDatabase.add(new Car(vin, make, model, year, mileage, price, 
                    style));
        }
        else if(truck.isSelected()) {
            String maxLoad = extra1TF.getText();
            String length = extra2TF.getText();
            if(!checkExtras(maxLoad, length))
                return;
            vDatabase.add(new Truck(vin, make, model, year, mileage, price, 
                    maxLoad, length));
        }
        else if(motorcycle.isSelected()) {
            String type = extra1TF.getText().toUpperCase();
            String engine = extra2TF.getText();
            if(!checkExtras(type, engine))
                return;
            vDatabase.add(new Motorcycle(vin, make, model, year, mileage, price,
                    type, engine));
        }
        
        Logger logger = Logger.getLogger("Add Car");
        try {
            FileHandler handler = new FileHandler("assign5-log.txt");
            handler.setFormatter(new SimpleFormatter());
            logger.addHandler(handler);
            logger.log(Level.INFO, "VEHICLE ADDED");
        } catch (IOException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * This method deletes a vehicle given its VIN
     */
    private void deleteVehicle() {
        JPanel panel = new JPanel();
        panel.setSize(400, 300);
        getContentPane().add(panel);
        panel.setLayout(new FormLayout());
        
        JTextField vinTF = new JTextField(5);
        
        panel.add(new JLabel("Enter VIN"));
        panel.add(vinTF);
        
        JOptionPane.showMessageDialog(this, panel, "Delete vehicle", JOptionPane.PLAIN_MESSAGE);
        
        Boolean carDeleted = false;
        String vin = vinTF.getText().toUpperCase();
        if(!checkVIN(vin))
            return;
        
        for(int i = 0; i < vDatabase.size(); i++) {
            if(vDatabase.get(i).getVin().toUpperCase().equals(vin)) {
                    ArrayList<Vehicle> toRemove = new ArrayList();
                    toRemove.add(vDatabase.get(i));
                    vDatabase.removeAll(toRemove);
                    carDeleted = true;
            }
        }
        if(!carDeleted) {
            JOptionPane.showMessageDialog(null, "Vehicle VIN does not Exist", "Error", 
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        Logger logger = Logger.getLogger("Delete Vehicle");
        try {
            FileHandler handler = new FileHandler("assign5-log.txt");
            handler.setFormatter(new SimpleFormatter());
            logger.addHandler(handler);
        } catch (IOException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        logger.log(Level.INFO, "Vehicle Deleted");
    }
    /**
     * This method searches for a vehicle given its VIN
     */
    private void searchVehicleByVin() {
        JPanel panel = new JPanel();
        panel.setSize(400, 300);
        getContentPane().add(panel);
        panel.setLayout(new FormLayout());
        JTextField vinTF = new JTextField(5);
        
        
        panel.add(new JLabel("Enter VIN"));
        panel.add(vinTF);
        JOptionPane.showMessageDialog(this, panel, "Search vehicle by VIN", JOptionPane.PLAIN_MESSAGE);
        
        String vin = vinTF.getText().toUpperCase();
        if(!checkVIN(vinTF.getText().toUpperCase()))
            return;

        if(!showOneVehicle(vin)) {
            JOptionPane.showMessageDialog(null, "Vehicle Not Found", "Error", 
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        Logger logger = Logger.getLogger(getClass().getName());
        try {
            FileHandler handler = new FileHandler("assign5-log.txt");
            handler.setFormatter(new SimpleFormatter());
            logger.addHandler(handler);
        } catch (IOException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        logger.log(Level.FINE, "Car with VIN " + vinTF.getText() + " was searched for");    
    }
    /**
     * This method searches for a vehicle given a price range(min and max), 
     * and after specifying vehicle type
     */
    private void searchVehicleByPrice() {
        JPanel panel = new JPanel();
        panel.setSize(400, 300);
        getContentPane().add(panel);
        panel.setLayout(new FormLayout());
        JTextField minTF = new JTextField(6);
        JTextField maxTF = new JTextField(6);
        
        
        panel.add(new JLabel("Minimum Price"));
        panel.add(minTF);
        panel.add(new JLabel("Maximum Price"));
        panel.add(maxTF);
        panel.add(new JLabel("Vehicle type:"));
        
        JRadioButton car = new JRadioButton("Passenger Car");
        car.setSelected(true);
        JRadioButton truck = new JRadioButton("Truck");
        JRadioButton motorcycle = new JRadioButton("Motorcycle");
        ButtonGroup group = new ButtonGroup();
        group.add(car);
        group.add(truck);
        group.add(motorcycle);
        
        JPanel radiosPanel = new JPanel(new FlowLayout());
        radiosPanel.add(car);
        radiosPanel.add(truck);
        radiosPanel.add(motorcycle);
        
        panel.add(radiosPanel);
        
        JOptionPane.showMessageDialog(this, panel, "Search vehicles by price", JOptionPane.PLAIN_MESSAGE);
        
        List<String> vinList = new ArrayList<>();
        Float min;
        try {
            min = Float.parseFloat(minTF.getText());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Invalid Minimum Price", "Error", 
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        if(!checkPrice(min))
            return;
        
        Float max;
        try {
            max = Float.parseFloat(maxTF.getText());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Invalid Maximum Price", "Error", 
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        if(!checkPrice(max))
            return;
        
        for(int i = 0; i < vDatabase.size(); i++) {
           if(car.isSelected() && vDatabase.get(i) instanceof Car) {
               if(vDatabase.get(i).getPrice() >= min && vDatabase.get(i).getPrice() <= max){
                   vinList.add(vDatabase.get(i).getVin());
               }
           }
           else if(truck.isSelected() && vDatabase.get(i) instanceof Truck) {
               if(vDatabase.get(i).getPrice() >= min && vDatabase.get(i).getPrice() <= max){
                   vinList.add(vDatabase.get(i).getVin());
               }
           }
           else if(motorcycle.isSelected() && vDatabase.get(i) instanceof Motorcycle) {
               if(vDatabase.get(i).getPrice() >= min && vDatabase.get(i).getPrice() <= max){
                   vinList.add(vDatabase.get(i).getVin());
               }
           }
        }
        showCertainVehicles(vinList);
        Logger logger = Logger.getLogger(getClass().getName());
        try {
            FileHandler handler = new FileHandler("assign5-log.txt");
            handler.setFormatter(new SimpleFormatter());
            logger.addHandler(handler);
        } catch (IOException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        logger.log(Level.FINE, "Vehicle searched for give price range.");
    }
    /**
     * This helper method searches for the provided VIN of
     * a single vehicle in the database, and outputs the vehicle information on
     * a table
     * @param vin The VIN provided by user input
     * @return Returns true if a vehicle with the provided VIN is shown.
     */
    private Boolean showOneVehicle(String vin) {
        JPanel topPanel = new JPanel();
        topPanel.setSize(400, 300);
        topPanel.setLayout(new BorderLayout());
        getContentPane().add(topPanel);
        
        // Create columns names
        String columnNames[] = {"VIN", "MAKE", "MODEL", "YEAR", "PRICE"};

        DefaultTableModel tableModel = new DefaultTableModel(columnNames, vDatabase.size());
        Boolean carFound = false;
        // Create some data
        for(int i = 0; i < vDatabase.size(); i++) {
            if(vDatabase.get(i).getVin().equals(vin)) {
                carFound = true;
                String tempVin = vDatabase.get(i).getVin();
                String make = vDatabase.get(i).getMake();
                String model = vDatabase.get(i).getModel();
                Integer year = vDatabase.get(i).getYear();
                Float price = vDatabase.get(i).getPrice();

                Object[] data = {tempVin, make, model, year, price};
                tableModel.addRow(data);
            }
        }
        
        if(!carFound)
            return false;
        
        // Create a new table instance
        JTable table = new JTable(tableModel);
        
        table.setFillsViewportHeight(true);
        
        // Add the table to a scrolling pane
        JScrollPane scrollPane = new JScrollPane(table);
        topPanel.add(scrollPane, BorderLayout.CENTER);
        JOptionPane.showMessageDialog(this, topPanel, "Search results", JOptionPane.PLAIN_MESSAGE);
        return true;
    }
    /**
     * This helper method searches for each vehicle VIN that matched the given
     * price range and outputs the vehicles on a table
     * @param vinSearch A list of VINs that match the price range provided
     * by user
     */
    private void showCertainVehicles(List vinSearch) {
        JPanel topPanel = new JPanel();
        topPanel.setSize(400, 300);
        topPanel.setLayout(new BorderLayout());
        getContentPane().add(topPanel);

        // Create columns names
        String columnNames[] = {"VIN", "MAKE", "MODEL", "YEAR", "PRICE"};

        DefaultTableModel tableModel = new DefaultTableModel(columnNames, vDatabase.size());
        
        // Create some data
       
        for(Object temp : vinSearch) {
            for(int j = 0; j < vDatabase.size(); j++) {
                if(vDatabase.get(j).getVin().equals(temp)) {
                    String vin = vDatabase.get(j).getVin();
                    String make = vDatabase.get(j).getMake();
                    String model = vDatabase.get(j).getModel();
                    Integer year = vDatabase.get(j).getYear();
                    Float price = vDatabase.get(j).getPrice();
                    
                    Object[] data = {vin,make,model,year,price};
                    tableModel.addRow(data);
                }
            }
        }
        // Create a new table instance
        JTable table = new JTable(tableModel);
        
        table.setFillsViewportHeight(true);
        
        // Add the table to a scrolling pane
        JScrollPane scrollPane = new JScrollPane(table);
        topPanel.add(scrollPane, BorderLayout.CENTER);
        JOptionPane.showMessageDialog(this, topPanel, "Search results", JOptionPane.PLAIN_MESSAGE);
    }
    /**
     * This method updates an existing user in the database 
     */
    private void updateUser() {
        JPanel panel = new JPanel();
        panel.setSize(400, 300);
        getContentPane().add(panel);
        panel.setLayout(new FormLayout());
        
        JTextField idTF = new JTextField(5);
        
        panel.add(new JLabel("Enter ID"));
        panel.add(idTF);
        
        JOptionPane.showMessageDialog(this, panel, "Update User", JOptionPane.PLAIN_MESSAGE);
        
        Boolean found = false;
        Integer id;
        try {
            id = Integer.parseInt(idTF.getText());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Invalid ID", "Error", 
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        if(!checkUserID(id))
            return;
        
        for(int i = 0; i < uDatabase.size(); i++) {
            if(uDatabase.get(i).getID().equals(id)) {
                ArrayList<User> toRemove = new ArrayList();
                toRemove.add(uDatabase.get(i));
                uDatabase.removeAll(toRemove);
                found = true;
            }
        }
        
        if(!found) {
            JOptionPane.showMessageDialog(null, "User Not Found", "Error", 
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        JPanel panel2 = new JPanel();
        panel2.setSize(400, 300);
        getContentPane().add(panel2);
        panel2.setLayout(new FormLayout());
        
        JTextField firstTF = new JTextField(20);
        JTextField lastTF = new JTextField(20);
        JTextField extra1TF = new JTextField(20);
        JTextField extra2TF = new JTextField(20);
        

        panel2.add(new JLabel("First"));
        panel2.add(firstTF);
        panel2.add(new JLabel("Last"));
        panel2.add(lastTF);
        panel2.add(new JLabel("User type:"));
        
        JRadioButton cust = new JRadioButton("Customer");
        cust.setSelected(true);
        JRadioButton emp = new JRadioButton("Employee");

        ButtonGroup group = new ButtonGroup();
        group.add(cust);
        group.add(emp);
        
        JPanel radiosPanel = new JPanel(new FlowLayout());
        radiosPanel.add(cust);
        radiosPanel.add(emp);
        
        panel2.add(radiosPanel);
        
        panel2.add(new JLabel("Extra info(Phone, Salary..)"));
        panel2.add(extra1TF);
        
        panel2.add(new JLabel("Extra info 2(D.License, Account...)"));
        panel2.add(extra2TF);        
       
        JOptionPane.showMessageDialog(this, panel2, "Add new User", JOptionPane.PLAIN_MESSAGE);        
        
        String first = firstTF.getText().toUpperCase();
        if(!checkFirstName(first))
            return;
        
        String last = lastTF.getText().toUpperCase();
        if(!checkLastName(last))
            return;
        
        if(cust.isSelected()) {
            String phone = extra1TF.getText();
            if(!checkPhone(phone))
                return;
            Integer dLicense;
            try {
                dLicense = Integer.parseInt(extra2TF.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid D. License", "Error", 
                    JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            if(!checkLicense(dLicense))
                return;
            
            uDatabase.add(new Customer(id, first, last, phone, dLicense));
        }
        else if(emp.isSelected()) {
            Float salary;
            try {
                salary = Float.parseFloat(extra1TF.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid Salary", "Error", 
                    JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            if(!checkSalary(salary))
                return;
            
            Integer account;
            try {
                account = Integer.parseInt(extra2TF.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid Account", "Error", 
                    JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            if(!checkAccount(account))
                return;
            uDatabase.add(new Employee(id, first, last, salary, account));
        }
        Logger logger = Logger.getLogger("User info Updated");
        try {
            FileHandler handler = new FileHandler("assign5-log.txt");
            handler.setFormatter(new SimpleFormatter());
            logger.addHandler(handler);
        } catch (IOException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        logger.log(Level.INFO, first + " " + last + "user info updated");
    }
    /**
     * This method deletes an existing user in the database
     */
    private void deleteUser() {
        JPanel panel = new JPanel();
        panel.setSize(400, 300);
        getContentPane().add(panel);
        panel.setLayout(new FormLayout());
        
        JTextField idTF = new JTextField(5);
        
        panel.add(new JLabel("Enter ID"));
        panel.add(idTF);
        
        JOptionPane.showMessageDialog(this, panel, "Delete User", JOptionPane.PLAIN_MESSAGE);
        
        Boolean userDeleted = false;
        Integer id;
        try {
            id = Integer.parseInt(idTF.getText());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Invalid ID", "Error", 
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        if(!checkUserID(id))
            return;
        
        for(int i = 0; i < uDatabase.size(); i++) {
            if(uDatabase.get(i).getID().equals(id)) {
                    ArrayList<User> toRemove = new ArrayList();
                    toRemove.add(uDatabase.get(i));
                    uDatabase.removeAll(toRemove);
                    userDeleted = true;
            }
        }
        if(!userDeleted) {
            JOptionPane.showMessageDialog(null, "User VIN does not exist", "Error", 
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        Logger logger = Logger.getLogger("Remove User");
        try {
            FileHandler handler = new FileHandler("assign5-log.txt");
            handler.setFormatter(new SimpleFormatter());
            logger.addHandler(handler);
        } catch (IOException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        logger.log(Level.INFO, "User Removed");
    }
    /**
     * This method outputs all users in the database through a table
     */
    private void showAllUsers() {
        JPanel topPanel = new JPanel();
        topPanel.setSize(400, 300);
        topPanel.setLayout(new BorderLayout());
        getContentPane().add(topPanel);

        // Create columns names
        String columnNames[] = {"ID", "FIRST", "LAST"};

        DefaultTableModel tableModel = new DefaultTableModel(columnNames, uDatabase.size());
        
        // Create some data
        for(int i = 0; i < uDatabase.size(); i++) {
            Integer id = uDatabase.get(i).getID();
            String first = uDatabase.get(i).getFirst();
            String last = uDatabase.get(i).getLast();
            
            Object[] data = {id, first, last};
            tableModel.addRow(data);
        }
        
        // Create a new table instance
        JTable table = new JTable(tableModel);
        
        table.setFillsViewportHeight(true);
        
        // Add the table to a scrolling pane
        JScrollPane scrollPane = new JScrollPane(table);
        topPanel.add(scrollPane, BorderLayout.CENTER);
        JOptionPane.showMessageDialog(this, topPanel, "Search results", JOptionPane.PLAIN_MESSAGE);
        
        Logger logger = Logger.getLogger(getClass().getName());
        try {
            FileHandler handler = new FileHandler("assign5-log.txt");
            handler.setFormatter(new SimpleFormatter());
            logger.addHandler(handler);
        } catch (IOException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        logger.log(Level.FINE, "All users viewed");
    }
    /**
     * This method adds a new user into the user database after all information
     * is provided
     */
    private void addNewUser() {
        JPanel panel = new JPanel();
        panel.setSize(400, 300);
        getContentPane().add(panel);
        panel.setLayout(new FormLayout());
        
        JTextField idTF = new JTextField(5);
        JTextField firstTF = new JTextField(20);
        JTextField lastTF = new JTextField(20);
        JTextField extra1TF = new JTextField(20);
        JTextField extra2TF = new JTextField(20);
        
        panel.add(new JLabel("ID"));
        panel.add(idTF);
        panel.add(new JLabel("First"));
        panel.add(firstTF);
        panel.add(new JLabel("Last"));
        panel.add(lastTF);
        panel.add(new JLabel("User type:"));
        
        JRadioButton cust = new JRadioButton("Customer");
        cust.setSelected(true);
        JRadioButton emp = new JRadioButton("Employee");

        ButtonGroup group = new ButtonGroup();
        group.add(cust);
        group.add(emp);
        
        JPanel radiosPanel = new JPanel(new FlowLayout());
        radiosPanel.add(cust);
        radiosPanel.add(emp);
        
        panel.add(radiosPanel);
        
        panel.add(new JLabel("Extra info(Phone, Salary..)"));
        panel.add(extra1TF);
        
        panel.add(new JLabel("Extra info 2(D.License, Account...)"));
        panel.add(extra2TF);        
       
        JOptionPane.showMessageDialog(this, panel, "Add new User", JOptionPane.PLAIN_MESSAGE);        
        
        Integer id;
        try {
            id = Integer.parseInt(idTF.getText());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Invalid ID", "Error", 
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        if(!checkUserID(id))
            return;
        
        String first = firstTF.getText().toUpperCase();
        if(!checkFirstName(first))
            return;
        
        String last = lastTF.getText().toUpperCase();
        if(!checkLastName(last))
            return;
        
        if(cust.isSelected()) {
            String phone = extra1TF.getText();
            if(!checkPhone(phone))
                return;
            Integer dLicense;
            try {
                dLicense = Integer.parseInt(extra2TF.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid D. License", "Error", 
                    JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            if(!checkLicense(dLicense))
                return;
            uDatabase.add(new Customer(id, first, last, phone, dLicense));
        }
        else if(emp.isSelected()) {
            Float salary;
            try {
                salary = Float.parseFloat(extra1TF.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid Salary", "Error", 
                    JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            if(!checkSalary(salary))
                return;
            Integer account;
            try {
                account = Integer.parseInt(extra2TF.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid Account", "Error", 
                    JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            if(!checkAccount(account))
                return;
            uDatabase.add(new Employee(id, first, last, salary, account));
        }
        Logger logger = Logger.getLogger("AddUser");
        try {
            FileHandler handler = new FileHandler("assign5-log.txt");
            handler.setFormatter(new SimpleFormatter());
            logger.addHandler(handler);
        } catch (IOException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        logger.log(Level.INFO, "User " + first + " " + last + "added.");
    }
    /**
     * This method adds a new transaction into the transaction database
     */
    private void addNewTrans() {
        JPanel panel = new JPanel();
        panel.setSize(400, 300);
        getContentPane().add(panel);
        panel.setLayout(new FormLayout());
        
        JTextField custIDTF = new JTextField(5);
        JTextField vinTF = new JTextField(5);
        JTextField dateTF = new JTextField(8);
        JTextField priceTF = new JTextField(6);
        JTextField empIDTF = new JTextField(5);
        
        panel.add(new JLabel("Customer ID"));
        panel.add(custIDTF);
        panel.add(new JLabel("Vehicle VIN"));
        panel.add(vinTF);
        panel.add(new JLabel("Sale Date"));
        panel.add(dateTF);
        panel.add(new JLabel("Sale Price"));
        panel.add(priceTF);
        panel.add(new JLabel("Employee ID"));
        panel.add(empIDTF);
       
        JOptionPane.showMessageDialog(this, panel, "Add new User", JOptionPane.PLAIN_MESSAGE);        
        
        Integer custID;
        try {
            custID = Integer.parseInt(custIDTF.getText());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Invalid ID", "Error", 
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        if(!checkUserID(custID))
            return;
        String vin = vinTF.getText().toUpperCase();
        if(!checkVIN(vin))
            return;
        String date = dateTF.getText();
        if(!checkDate(date))
            return;
        Float price;
        try {
            price = Float.parseFloat(priceTF.getText());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Invalid Price", "Error", 
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        if(!checkPrice(price))
            return;
        Integer empID;
        try {
            empID = Integer.parseInt(empIDTF.getText());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Invalid ID", "Error", 
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        if(!checkUserID(empID))
            return;
        Boolean custFound = false;
        Boolean empFound = false;
        Boolean vehicleFound = false;
        
        for(int i = 0; i < uDatabase.size(); i++) {
            if(Objects.equals(uDatabase.get(i).getID(), custID))
                custFound = true;
            if(Objects.equals(uDatabase.get(i).getID(), empID))
                empFound = true;
        }
        
        for(int j = 0; j < vDatabase.size(); j++) {
            if(vin.equals(vDatabase.get(j).getVin()))
                vehicleFound = true;
        }
        
        if(!custFound) {
            JOptionPane.showMessageDialog(null, "Customer ID not Found", "Error", 
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        if(!empFound) {
            JOptionPane.showMessageDialog(null, "Employee ID not Found", "Error", 
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        if(!vehicleFound) {
            JOptionPane.showMessageDialog(null, "Vehicle VIN not Found", "Error", 
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        sDatabase.add(new Sales(custID, vin, date, price, empID));
        
        for(int i = 0; i < vDatabase.size(); i++) {
            if(vDatabase.get(i).getVin().toUpperCase().equals(vin)) {
                    ArrayList<Vehicle> toRemove = new ArrayList();
                    toRemove.add(vDatabase.get(i));
                    vDatabase.removeAll(toRemove);
            }
        }
        Logger logger = Logger.getLogger("Add Transaction");
        try {
            FileHandler handler = new FileHandler("assign5-log.txt");
            handler.setFormatter(new SimpleFormatter());
            logger.addHandler(handler);
        } catch (IOException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        logger.log(Level.INFO, "Transaction was added.");
    }
    /**
     * This method shows all existing transactions in the database
     */
    private void showAllTrans() {
        JPanel topPanel = new JPanel();
        topPanel.setSize(400, 300);
        topPanel.setLayout(new BorderLayout());
        getContentPane().add(topPanel);

        // Create columns names
        String columnNames[] = {"CUSTOMER ID", "VIN", "DATE", "PRICE", "EMPLOYEE ID"};

        DefaultTableModel tableModel = new DefaultTableModel(columnNames, sDatabase.size());
        
        // Create some data
        for(int i = 0; i < sDatabase.size(); i++) {
            Integer custID = sDatabase.get(i).getCustID();
            String vin = sDatabase.get(i).getVehicleVIN();
            String date = sDatabase.get(i).getDate();
            Float price = sDatabase.get(i).getSalePrice();
            Integer empID = sDatabase.get(i).getEmpID();
            
            Object[] data = {custID, vin, date, price, empID};
            tableModel.addRow(data);
        }
        
        // Create a new table instance
        JTable table = new JTable(tableModel);
        
        table.setFillsViewportHeight(true);
        
        // Add the table to a scrolling pane
        JScrollPane scrollPane = new JScrollPane(table);
        topPanel.add(scrollPane, BorderLayout.CENTER);
        JOptionPane.showMessageDialog(this, topPanel, "Search results", JOptionPane.PLAIN_MESSAGE);
        Logger logger = Logger.getLogger(getClass().getName());
        try {
            FileHandler handler = new FileHandler("assign5-log.txt");
            handler.setFormatter(new SimpleFormatter());
            logger.addHandler(handler);
        } catch (IOException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        logger.log(Level.FINE, "All Sales Transactions viewed");
    }
    /**
     * This helper method checks if the VIN matches the correct format
     * and length
     * @param vin The VIN provided by user input
     * @return Returns true if the VIN provided is the correct format.
     */
    private boolean checkVIN(String vin) {
        if (!vin.matches("^[a-zA-Z0-9]*$") || !(vin.length() == 5)) {
            JOptionPane.showMessageDialog(null, "Invalid VIN", "Error", 
                    JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
        return true;
    }
    
    /**
     * This helper method checks if the make matches the correct format
     * @param make The make provided by user input
     * @return Returns true if the make is the correct format
     */
    private boolean checkMake(String make) {
        if (!make.matches("^[a-zA-Z]*$")) {
            JOptionPane.showMessageDialog(null, "Invalid Make", "Error", 
                    JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
        return true;
    }
    /**
     * This helper method checks if the vehicle model matches the correct format
     * @param model The model provided by user input
     * @return Returns true if the model is the correct format
     */
    private boolean checkModel(String model) {
        if (!model.matches("^[a-zA-Z]*$")) {
            JOptionPane.showMessageDialog(null, "Invalid Make", "Error", 
                    JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
        return true;
    }
    /**
     * This helper method checks if the year matches the correct format
     * @param year The year provided by user input
     * @return Returns true if the year is the correct format
     */
    private boolean checkYear(Integer year) {
        if (!(year.toString().length() == 4) || year > 2016) {
            JOptionPane.showMessageDialog(null, "Invalid Year", "Error", 
                    JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
        return true;
    }
    /**
     * This helper method checks if the User ID matches the correct length
     * @param id The ID provided by user input
     * @return Returns true if the ID is the correct length
     */
    private boolean checkUserID(Integer id) {
        if (!(id.toString().length() == 5)) {
            JOptionPane.showMessageDialog(null, "Invalid ID", "Error", 
                    JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
        return true;
    }
    /**
     * This helper method checks if the User first name matches the correct 
     * format
     * @param first The first name provided by user input
     * @return Returns true if the first name is the correct format
     */
    private boolean checkFirstName(String first) {
        if (!first.matches("^[a-zA-Z]*$")) {
            JOptionPane.showMessageDialog(null, "Invalid First Name Input", "Error", 
                    JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
        return true;
    }
    /**
     * This helper method checks if the date matches the correct 
     * format
     * @param date The date name provided by user input
     * @return Returns true if the date is the correct format
     */
    private boolean checkDate(String date) {
        if (!date.matches("^[a-zA-Z]*$")) {
            JOptionPane.showMessageDialog(null, "Invalid Date Input", "Error", 
                    JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
        return true;
    }
    /**
     * This helper method checks if the driver's license matches the correct 
     * format
     * @param dLicense The driver's license provided by user input
     * @return Returns true if the driver's licenses is the correct format
     */
    private boolean checkLicense(Integer dLicense) {
        if (!dLicense.toString().matches("^[0-9]\\d*$")) {
            JOptionPane.showMessageDialog(null, "Invalid License Input", "Error", 
                    JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
        return true;
    }
    /**
     * This helper method checks if the phone matches the correct 
     * format
     * @param phone The phone provided by user input
     * @return Returns true if the phone is the correct format
     */
    private boolean checkPhone(String phone) {
        if(!phone.matches("^[0-9]\\d*$")) {
            JOptionPane.showMessageDialog(null, "Invalid Phone# Input", "Error", 
                    JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
        return true;
    }
    /**
     * This helper method checks if the User last name matches the correct 
     * format
     * @param last The last name provided by user input
     * @return Returns true if the last name is the correct format
     */
    private boolean checkLastName(String last) {
        if (!last.matches("^[a-zA-Z]*$")) {
            JOptionPane.showMessageDialog(null, "Invalid Last Name Input", "Error", 
                    JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
        return true;
    }
    /**
     * This helper method checks if the mileage matches the correct 
     * format
     * @param mileage The mileage provided by user input
     * @return Returns true if the mileage is the correct format
     */
    private boolean checkMileage(Integer mileage) {
        if (!mileage.toString().matches("^[0-9]\\d*$")) {
            JOptionPane.showMessageDialog(null, "Invalid Mileage", "Error", 
                    JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
        return true;
    }
    /**
     * This helper method checks if the price matches the correct 
     * format
     * @param price The price provided by user input
     * @return Returns true if the price is the correct format
     */
    private boolean checkPrice(Float price) {
        if (!price.toString().matches("(?!=\\d-\\.)([\\d\\.]+)")) {
            JOptionPane.showMessageDialog(null, "Invalid Price", "Error", 
                    JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
        return true;
    }
    /**
     * This helper method checks if the account matches the correct 
     * format
     * @param account The account provided by user input
     * @return Returns true if the account is the correct format
     */
    private  boolean checkAccount(Integer account) {
        if (!account.toString().matches("^[0-9]\\d*$")) {
            JOptionPane.showMessageDialog(null, "Invalid Account", "Error", 
                    JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
        return true;
    }
    /**
     * This helper method checks if the salary matches the correct 
     * format
     * @param salary The salary provided by user input
     * @return Returns true if the salary is the correct format
     */
    private boolean checkSalary(Float salary) {
        if (!salary.toString().matches("(?!=\\d-\\.)([\\d\\.]+)")) {
            JOptionPane.showMessageDialog(null, "Invalid Salary", "Error", 
                    JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
        return true;
    }
    /**
     * This helper method checks if the 'extras' fields match the correct
     * format
     * @param extra1 The first extra text field provided by user input
     * @param extra2 The second extra text field provided by user input
     * @return Returns true if extra1 and extra2 are both correct format
     */
    private boolean checkExtras(String extra1, String extra2) {
        if(!extra1.isEmpty())
            if (!extra1.matches("^[a-zA-Z0-9]*$")) {
                JOptionPane.showMessageDialog(null, "Invalid Extra1", "Error", 
                        JOptionPane.INFORMATION_MESSAGE);
                return false;
            }
        if(!extra2.isEmpty())
            if (!extra2.matches("^[a-zA-Z0-9]*$")) {
                JOptionPane.showMessageDialog(null, "Invalid Extra2", "Error", 
                        JOptionPane.INFORMATION_MESSAGE);
                return false;
            }
        return true;
    }
}