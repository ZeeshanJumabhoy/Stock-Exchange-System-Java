import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.*;

public class CustomerDashboard extends JFrame {
    private JPanel panel1, panel2;

    public CustomerDashboard() {
        super("Customer Dashboard");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create the first panel with buttons for customer options
        panel1 = new JPanel();
        panel1.setLayout(new GridLayout(6, 1,10,10));
        panel1.setPreferredSize(new Dimension(200, 600));
        panel1.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel1.setBackground(new Color(231, 252, 231)); // Light green background color

        JButton viewStocks = new JButton("View Stocks");
        viewStocks.setFont(new Font("Arial", Font.BOLD, 18));
        JButton buyStocks = new JButton("Buy Stocks");
        buyStocks.setFont(new Font("Arial", Font.BOLD, 18));
        JButton sellStocks = new JButton("Sell Stocks");
        sellStocks.setFont(new Font("Arial", Font.BOLD, 18));
        JButton viewInsiders = new JButton("View Insiders");
        viewInsiders.setFont(new Font("Arial", Font.BOLD, 18));
        JButton payment = new JButton("Payment");
        payment.setFont(new Font("Arial", Font.BOLD, 18));
        JButton portfolio = new JButton("Portfolio");
        portfolio.setFont(new Font("Arial", Font.BOLD, 18));

        viewStocks.setBackground(Color.LIGHT_GRAY); // Light red button color
        buyStocks.setBackground(Color.LIGHT_GRAY);
        sellStocks.setBackground(Color.LIGHT_GRAY);
        viewInsiders.setBackground(Color.LIGHT_GRAY);
        payment.setBackground(Color.LIGHT_GRAY);
        portfolio.setBackground(Color.LIGHT_GRAY);

        viewStocks.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Create a new panel to show stock information
                setVisible(false);
                ViewStocks gui = new ViewStocks();
                gui.setVisible(true);

            }
        });

        buyStocks.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            }
        });

        sellStocks.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            }
        });

        viewInsiders.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                ViewInsider gui = new ViewInsider();
                gui.setVisible(true);

            }
        });

        payment.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                Payment gui = new Payment();
                gui.setVisible(true);
            }
        });
        portfolio.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            }
        });

        panel1.add(viewStocks);
        panel1.add(buyStocks);
        panel1.add(sellStocks);
        panel1.add(viewInsiders);
        panel1.add(payment);
        panel1.add(portfolio);

        panel2 = new JPanel(new BorderLayout());
        panel2.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel2.setBackground(new Color(231, 252, 231));

        try (InputStream stream = getClass().getResourceAsStream("/logo1.png")) {
            ImageIcon logoIcon = new ImageIcon(ImageIO.read(stream));
            JLabel logoLabel = new JLabel(logoIcon);
            panel2.add(logoLabel, BorderLayout.CENTER);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Add the panels to the frame
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(panel1, BorderLayout.WEST);
        getContentPane().add(panel2, BorderLayout.CENTER);
    }
    public static void main(String[] args) {
        boolean isLoggedIn = true;

        if (isLoggedIn) {
            CustomerDashboard gui = new CustomerDashboard();
            gui.setVisible(true);
        } else {
            // Handle unsuccessful login
        }

    }
}
