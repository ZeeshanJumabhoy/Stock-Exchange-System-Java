import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.URL;
import java.sql.Connection;

public class RegistrationForm extends JFrame implements ActionListener {
    Data db = new Data();
    Connection conn = db.connect_to_db("StockExchange", "postgres", "zeeshan");
    private JLabel nameLabel, addressLabel, numberLabel, cnicLabel, passwordLabel, confirmPasswordLabel, typeLabel;
    private JTextField nameField, addressField, numberField, cnicField;
    private JPasswordField passwordField, confirmPasswordField;
    private JButton registerButton;
    private JComboBox<String> typeComboBox;

    public RegistrationForm() {
        // Setting up the JFrame
        setTitle("Registration Form");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);

        setContentPane(new JLabel(new ImageIcon("src/Trade1.png"))); // Adjust the path accordingly
        // Setting up the labels
        nameLabel = new JLabel("Name:");
        nameLabel.setBounds(50, 50, 100, 20);
        nameLabel.setForeground(Color.green);
        add(nameLabel);

        addressLabel = new JLabel("Email:");
        addressLabel.setBounds(50, 80, 100, 20);
        addressLabel.setForeground(Color.green);
        add(addressLabel);

        numberLabel = new JLabel("Number:");
        numberLabel.setBounds(50, 110, 100, 20);
        numberLabel.setForeground(Color.green);
        add(numberLabel);

        cnicLabel = new JLabel("CNIC Number:");
        cnicLabel.setBounds(50, 140, 100, 20);
        cnicLabel.setForeground(Color.green);
        add(cnicLabel);

        passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(50, 170, 100, 20);
        passwordLabel.setForeground(Color.green);
        add(passwordLabel);

        confirmPasswordLabel = new JLabel("Confirm Password:");
        confirmPasswordLabel.setBounds(50, 200, 150, 20);
        confirmPasswordLabel.setForeground(Color.green);
        add(confirmPasswordLabel);

        typeLabel = new JLabel("Type:");
        typeLabel.setBounds(50, 230, 100, 20);
        typeLabel.setForeground(Color.green);
        add(typeLabel);

        // Setting up the fields
        nameField = new JTextField();
        nameField.setBounds(200, 50, 200, 20);
        add(nameField);

        addressField = new JTextField();
        addressField.setBounds(200, 80, 200, 20);
        add(addressField);

        numberField = new JTextField();
        numberField.setBounds(200, 110, 200, 20);
        add(numberField);

        cnicField = new JTextField();
        cnicField.setBounds(200, 140, 200, 20);
        add(cnicField);

        passwordField = new JPasswordField();
        passwordField.setBounds(200, 170, 200, 20);
        add(passwordField);

        confirmPasswordField = new JPasswordField();
        confirmPasswordField.setBounds(200, 200, 200, 20);
        add(confirmPasswordField);

        // Setting up the combo box
        String[] types = {"Broker", "Customer"};
        typeComboBox = new JComboBox<>(types);
        typeComboBox.setBounds(200, 230, 100, 20);
        add(typeComboBox);

        // Setting up the button
        registerButton = new JButton("Register");
        registerButton.setBounds(200, 270, 100, 20);
        add(registerButton);
        registerButton.addActionListener(this);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String name=nameField.getText();
        String email=addressField.getText();
        String number=numberField.getText();
        String cnic=cnicField.getText();
        String password=passwordField.getText();


        // Check if all fields are filled
        if (nameField.getText().isEmpty() || addressField.getText().isEmpty() || numberField.getText().isEmpty()
                || cnicField.getText().isEmpty() || passwordField.getPassword().length == 0
                || confirmPasswordField.getPassword().length == 0) {
            JOptionPane.showMessageDialog(this, "All fields are mandatory!");
            return;
        }
        if (numberField.getText().length() != 11) {
            JOptionPane.showMessageDialog(this, "Number must be 11 digits!");
            return;
        }
        if (cnicField.getText().length() != 13) {
            JOptionPane.showMessageDialog(this, "CNIC must be 13 digits!");
            return;
        }
        if (!addressField.getText().contains("@") || !addressField.getText().contains(".com")) {
            JOptionPane.showMessageDialog(this, "Address must contain '@' and '.com'");
            return;
        }
        // Check if password and confirm password are the same
        if (!new String(passwordField.getPassword()).equals(new String(confirmPasswordField.getPassword()))) {
            JOptionPane.showMessageDialog(this, "Password and confirm password do not match!");
            return;
        }

        boolean val;
        String type = (String) typeComboBox.getSelectedItem();
        val=db.insert_row_registration(conn,"customer",name,email,number,cnic,password,type);


        if(val==true)
        {
            JOptionPane.showMessageDialog(this, "Registration successful!");
            setVisible(false);
            new MainDashboard();
        }
        else{

        }

    }

    public static void main(String[] args) {
        new RegistrationForm();
    }
}