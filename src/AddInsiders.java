import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.util.List;

public class AddInsiders extends JFrame{
    Data db = new Data();
    Connection conn = db.connect_to_db("StockExchange", "postgres", "zeeshan");

    private JButton addStocksButton, addInsidersButton, customerDetailsButton, portfolioButton;
    public AddInsiders() {
        setTitle("AddInsider");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

// Create the left panel with a darker color
        JPanel leftPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        leftPanel.setBackground(new Color(231, 252, 231));;
        leftPanel.setPreferredSize(new Dimension(200, 300));

// Create the right panel with a lighter color
        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(new Color(231, 252, 231));
        rightPanel.setPreferredSize(new Dimension(200, 300));


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
                SwingUtilities.invokeLater(() -> {
                    AddStocks dashboard = new AddStocks();
                    dashboard.setVisible(true);
                });
            }
        });
        addStocksButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                addStocksButton.setBackground(new Color(169, 169, 169)); // Darker gray on hover
                addStocksButton.setBorderPainted(true);
            }

            public void mouseExited(MouseEvent evt) {
                addStocksButton.setBackground(new Color(192, 192, 192)); // Restore original color
                addStocksButton.setBorderPainted(false);
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
        addInsidersButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                addInsidersButton.setBackground(new Color(169, 169, 169)); // Darker gray on hover
                addInsidersButton.setBorderPainted(true);
            }

            public void mouseExited(MouseEvent evt) {
                addInsidersButton.setBackground(new Color(192, 192, 192)); // Restore original color
                addInsidersButton.setBorderPainted(false);
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
        customerDetailsButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                customerDetailsButton.setBackground(new Color(169, 169, 169)); // Darker gray on hover
                customerDetailsButton.setBorderPainted(true);
            }

            public void mouseExited(MouseEvent evt) {
                customerDetailsButton.setBackground(new Color(192, 192, 192)); // Restore original color
                customerDetailsButton.setBorderPainted(false);
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
        portfolioButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                portfolioButton.setBackground(new Color(169, 169, 169)); // Darker gray on hover
                portfolioButton.setBorderPainted(true);
            }

            public void mouseExited(MouseEvent evt) {
                portfolioButton.setBackground(new Color(192, 192, 192)); // Restore original color
                portfolioButton.setBorderPainted(false);
            }
        });

        leftPanel.add(addStocksButton);
        leftPanel.add(addInsidersButton);
        leftPanel.add(customerDetailsButton);
        leftPanel.add(portfolioButton);

// Right Panel

        JLabel lblNewLabel4 = new JLabel("Stock Name : ");
        lblNewLabel4.setForeground(Color.BLACK);
        lblNewLabel4.setFont(new Font("Serif", Font.BOLD, 22));
        lblNewLabel4.setBounds(110, 154, 172, 22);
        rightPanel.add(lblNewLabel4);

        JComboBox<String> type = new JComboBox<>();
        type.setBounds(280, 158, 172, 22);
        rightPanel.add(type);
        populatetypeComboBox(type);

        JTextField ItemInfo = new JTextField();
        ItemInfo.setBounds(280, 208, 172, 28);
        rightPanel.add(ItemInfo);

        JLabel lblNewLabel6 = new JLabel("About Insider : ");
        lblNewLabel6.setForeground(Color.BLACK);
        lblNewLabel6.setFont(new Font("Serif", Font.BOLD, 22));
        lblNewLabel6.setBounds(110, 204, 172, 22);
        rightPanel.add(lblNewLabel6);


        JButton Enter = new JButton("Add Insider");
        Enter.setFocusPainted(false);
        Enter.setBounds(175, 304, 210, 25);
        Enter.setBackground(new Color(192, 192, 192));
        Enter.setForeground(Color.BLACK);
        Enter.setBorderPainted(false);
        Enter.setOpaque(true);
        Enter.setFont(new Font("Arial", Font.BOLD, 20));
        rightPanel.add(Enter, BorderLayout.CENTER);
        Enter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String Type = (String) type.getSelectedItem();
                String info = ItemInfo.getText();
                if (info.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Inside cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
                    return; // Don't proceed with database insertion
                }
                boolean val;
                val=db.insert_row_insider(conn,"insider",Type,info);
                if(val==true)
                {
                    JOptionPane.showMessageDialog(rightPanel, "Data Added Successfully...", "Message", 1);
                    setVisible(false);
                    new BrokerDashboard();
                }
                else{

                }

           }
        });
        Enter.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                Enter.setBackground(new Color(169, 169, 169)); // Darker gray on hover
                Enter.setBorderPainted(true);
            }

            public void mouseExited(MouseEvent evt) {
                Enter.setBackground(new Color(192, 192, 192)); // Restore original color
                Enter.setBorderPainted(false);
            }
        });



     //   leftPanel.setLayout(new BorderLayout());
        rightPanel.setLayout(new BorderLayout());

        // Add the panels to the content pane
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(leftPanel, BorderLayout.WEST);
        contentPane.add(rightPanel);
    }
    public void populatetypeComboBox(JComboBox<String> sellerComboBox) {
        List<String> restaurantNames = db.readDataStock(conn,"stock");

        for (String restaurantName : restaurantNames) {
            sellerComboBox.addItem(restaurantName);
        }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AddInsiders dashboard = new AddInsiders();
            dashboard.setVisible(true);
        });
    }
}

