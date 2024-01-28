import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;

public class BrokerDashboard extends JFrame {
    private JPanel panel1, panel2, currentPanel;
    private JButton addStocksButton, addInsidersButton, customerDetailsButton, portfolioButton;

    public BrokerDashboard() {
        // Set the title and size of the frame
        setTitle("Broker Dashboard");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create the first panel with buttons for various options
        panel1 = new JPanel(new GridLayout(4, 1, 10, 10));
        panel1.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel1.setBackground(new Color(231, 252, 231));

        addStocksButton = new JButton("Add Stocks");
        addStocksButton.setBackground(Color.LIGHT_GRAY);
        addStocksButton.setFont(new Font("Arial", Font.BOLD, 18));
        addInsidersButton = new JButton("Add Insiders");
        addInsidersButton.setFont(new Font("Arial", Font.BOLD, 18));
        addInsidersButton.setBackground(Color.LIGHT_GRAY);
        customerDetailsButton = new JButton("Customer Details");
        customerDetailsButton.setFont(new Font("Arial", Font.BOLD, 18));
        customerDetailsButton.setBackground(Color.LIGHT_GRAY);
        portfolioButton = new JButton("Edit Stocks");
        portfolioButton.setFont(new Font("Arial", Font.BOLD, 18));
        portfolioButton.setBackground(Color.LIGHT_GRAY);

        addStocksButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                AddStocks dashboard = new AddStocks();
                dashboard.setVisible(true);
            }
        });

        addInsidersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                SwingUtilities.invokeLater(() -> {
                    AddInsiders dashboard = new AddInsiders();
                    dashboard.setVisible(true);
                });
            }
        });

        customerDetailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                SwingUtilities.invokeLater(() -> {
                    CustomerDetails dashboard = new CustomerDetails();
                    dashboard.setVisible(true);
                });
            }
        });

        portfolioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                SwingUtilities.invokeLater(() -> {
                    Edit dashboard = new Edit();
                    dashboard.setVisible(true);
                });
            }
        });

        panel1.add(addStocksButton);
        panel1.add(addInsidersButton);
        panel1.add(customerDetailsButton);
        panel1.add(portfolioButton);
        // Create the second panel with the system logo
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
        // Set the current panel to be the second panel
        currentPanel = panel2;


        // Add the two panels to the frame
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(panel1, BorderLayout.WEST);
        getContentPane().add(panel2, BorderLayout.CENTER);

        // Set the frame to be visible
        setVisible(true);
    }

    private void showPanel(JPanel panel) {
        getContentPane().remove(currentPanel);
        getContentPane().add(panel, BorderLayout.CENTER);
        currentPanel = panel;
        revalidate();
        repaint();
    }
    public static void main(String[] args) {
        new BrokerDashboard();
    }
}

