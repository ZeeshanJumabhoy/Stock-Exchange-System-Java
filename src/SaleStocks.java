import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

class StockSellGUI extends JFrame {
    private JTextField usernameTextField;
    private JTextField stocknameTextField;
    JPanel panel1;

    public StockSellGUI() {
        super("Stock Sell");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create the first panel with buttons for customer options
        panel1 = new JPanel();
        panel1.setLayout(new GridLayout(6, 1));
        panel1.setPreferredSize(new Dimension(200, 600));
        panel1.setBackground(new Color(186, 220, 88)); // Light green background color

        JButton viewStocks = new JButton("View Stocks");
        JButton buyStocks = new JButton("Buy Stocks");
        JButton sellStocks = new JButton("Sell Stocks");
        JButton viewInsiders = new JButton("View Insiders");
        JButton payment = new JButton("Payment");
        JButton portfolio = new JButton("Portfolio");
        panel1.add(viewStocks);
        panel1.add(buyStocks);
        panel1.add(sellStocks);
        panel1.add(viewInsiders);
        panel1.add(payment);
        panel1.add(portfolio);

        viewStocks.setBackground(new Color(255, 102, 102)); // Light red button color
        buyStocks.setBackground(new Color(255, 102, 102));
        sellStocks.setBackground(new Color(255, 102, 102));
        viewInsiders.setBackground(new Color(255, 102, 102));
        payment.setBackground(new Color(255, 102, 102));
        portfolio.setBackground(new Color(255, 102, 102));

        // Create the right panel with username, stockname, and confirm button
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new FlowLayout());

        JLabel usernameLabel = new JLabel("Username:");
        JLabel stocknameLabel = new JLabel("Stock Name:");

        usernameTextField = new JTextField(10);
        stocknameTextField = new JTextField(10);

        JButton confirmButton = new JButton("Confirm");

        rightPanel.add(usernameLabel);
        rightPanel.add(usernameTextField);
        rightPanel.add(stocknameLabel);
        rightPanel.add(stocknameTextField);
        rightPanel.add(confirmButton);

        // Add action listeners to the buttons
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameTextField.getText();
                String stockname = stocknameTextField.getText();
                if (validateUserAndStock(username, stockname)) {
                    int quantity = getStockQuantity(stockname);
                    double price = getStockPrice(stockname);
                    double amount = quantity * price;
                    updatePaymentFile(username, amount);
                    JOptionPane.showMessageDialog(null, "Payment successful. Amount: $" + amount);
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid username or stock name");
                }
            }
        });

        // Set up the main frame
        setLayout(new GridLayout(1, 2));
        add(panel1);
        add(rightPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private boolean validateUserAndStock(String username, String stockname) {
        try {
            // Read the Buy file and check if the username and stockname exist
            BufferedReader reader = new BufferedReader(new FileReader("D:\\File\\Buy.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(username) && parts[1].equals(stockname)) {
                    reader.close();
                    return true;
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private int getStockQuantity(String stockname) {
        try {
            // Read the Buy file and find the stockname to get the quantity
            BufferedReader reader = new BufferedReader(new FileReader("D:\\File\\Buy.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[1].equals(stockname)) {
                    int quantity = Integer.parseInt(parts[3]);
                    reader.close();
                    return quantity;
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private double getStockPrice(String stockname) {
        try {
            // Read the Stocks file and find the stockname to get the price
            BufferedReader reader = new BufferedReader(new FileReader("D:\\File\\Stocks.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(stockname)) {
                    double price = Double.parseDouble(parts[1]);
                    reader.close();
                    return price;
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    private void updatePaymentFile(String username, double amount) {
        try {
            // Read the Payment file and check if the username exists
            File paymentFile = new File("D:\\File\\Payment.txt");
            BufferedReader reader = new BufferedReader(new FileReader(paymentFile));
            StringBuilder fileContent = new StringBuilder();
            String line;
            boolean usernameFound = false;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(username)) {
                    double existingAmount = Double.parseDouble(parts[1]);
                    amount += existingAmount; // Add the new amount to the existing amount
                    usernameFound = true;
                }
                fileContent.append(line).append(System.lineSeparator());
            }
            reader.close();

            // If the username was not found, add it to the Payment file
            if (!usernameFound) {
                fileContent.append(username).append(",").append(amount).append(System.lineSeparator());
            }

            // Write the updated content back to the Payment file
            BufferedWriter writer = new BufferedWriter(new FileWriter(paymentFile));
            writer.write(fileContent.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}



public class SaleStocks {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new StockSellGUI();
            }
        });

    }
}