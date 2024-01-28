import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.sql.Connection;

public class MainDashboard extends JFrame implements ActionListener,MouseListener {
    Data db = new Data();
    Connection conn = db.connect_to_db("StockExchange", "postgres", "zeeshan");
    private JPanel panel;
    private JLabel userLabel, passLabel, typeLabel;
    private JTextField userText;
    private JPasswordField passText;
    private JComboBox<String> typeBox;
    private JButton loginButton, registerButton;
    private ImageIcon bgImage;
    private Color defaultButtonColor;
    private Color hoverButtonColor;

    public MainDashboard() {


        setTitle("Login Page");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel();
        panel.setLayout(null);


        bgImage = new ImageIcon(getClass().getResource("/Trade2.png"));
        JLabel bgLabel = new JLabel(bgImage);
        bgLabel.setBounds(0, 0, 500, 400);
        panel.add(bgLabel);

        userLabel = new JLabel("Email:");
        userLabel.setBounds(50, 100, 80, 25);
        userLabel.setForeground(Color.green);
        bgLabel.add(userLabel);

        userText = new JTextField();
        userText.setBounds(140, 100, 200, 25);
        bgLabel.add(userText);

        passLabel = new JLabel("Password:");
        passLabel.setBounds(50, 150, 80, 25);
        passLabel.setForeground(Color.green);
        bgLabel.add(passLabel);

        passText = new JPasswordField();
        passText.setBounds(140, 150, 200, 25);
        bgLabel.add(passText);

        typeLabel = new JLabel("User Type:");
        typeLabel.setBounds(50, 200, 80, 25);
        typeLabel.setForeground(Color.green);
        bgLabel.add(typeLabel);

        String[] types = {"Broker", "Customer"};
        typeBox = new JComboBox<>(types);
        typeBox.setBounds(140, 200, 100, 25);
        bgLabel.add(typeBox);

        loginButton = new JButton("Login");
        loginButton.setBounds(150, 250, 100, 25);
        loginButton.addActionListener(this);
        bgLabel.add(loginButton);

        registerButton = new JButton("Register");
        registerButton.setBounds(280, 250, 100, 25);
        registerButton.addActionListener(this);
        bgLabel.add(registerButton);

        defaultButtonColor = loginButton.getBackground();
        hoverButtonColor = Color.GREEN;

        // Add MouseListener to buttons
        loginButton.addMouseListener((MouseListener) this);
        registerButton.addMouseListener((MouseListener) this);

        add(panel);
        setVisible(true);
        setResizable(false);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getSource() == loginButton) {
            loginButton.setBackground(hoverButtonColor);
        } else if (e.getSource() == registerButton) {
            registerButton.setBackground(hoverButtonColor);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource() == loginButton) {
            loginButton.setBackground(defaultButtonColor);
        } else if (e.getSource() == registerButton) {
            registerButton.setBackground(defaultButtonColor);
        }
    }

    // Other mouse event methods
    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    public void actionPerformed(ActionEvent e) {
        String email1 = userText.getText();
        String password = new String(passText.getPassword());
        String userType = (String) typeBox.getSelectedItem();


        if (email1.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Name  and Password cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        else{
            boolean isAdmin = db.checkAdminCredentials(conn,"customer",email1,password,userType);
            System.out.println(userType);

            if (isAdmin && userType.equals("Broker")) {
                setVisible(false);
                new BrokerDashboard();
            }
            else if(isAdmin &&userType.equals("Customer"))
                {
                    setVisible(false);
                    CustomerDashboard gui = new CustomerDashboard();
                    gui.setVisible(true);
                }
        else {
                // Credentials are not valid, do something else
                JOptionPane.showMessageDialog(null, "Invalid Credentials", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

    }
    public static void main(String[] args) {
        MainDashboard obj = new MainDashboard();
    }
}