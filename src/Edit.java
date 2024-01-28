import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.util.List;

public class Edit extends JFrame{
    Data db = new Data();
    Connection conn = db.connect_to_db("StockExchange", "postgres", "zeeshan");

    private JButton addStocksButton, addInsidersButton, customerDetailsButton, portfolioButton;
    public Edit() {
        setTitle("Edit Stock");
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

        JLabel lblNewLabel3 = new JLabel("Select Stock: ");
        lblNewLabel3.setForeground(Color.BLACK);
        lblNewLabel3.setFont(new Font("Serif", Font.ITALIC, 25));
        lblNewLabel3.setBounds(100, 214, 150, 30);
        rightPanel.add(lblNewLabel3);

        JComboBox delete = new JComboBox();
        delete.setBounds(280, 214, 140, 33);
        rightPanel.add(delete);
        populatetypeComboBox(delete);

        JTextField Special = new JTextField();
        Special.setBounds(280, 274, 140, 30);
        rightPanel.add(Special);
        Special.setColumns(10);

        JLabel lblNewLabel5 = new JLabel("ID : ");
        lblNewLabel5.setForeground(Color.BLACK);
        lblNewLabel5.setFont(new Font("Serif", Font.ITALIC, 18));
        lblNewLabel5.setBounds(100, 274, 150, 30);
        rightPanel.add(lblNewLabel5);

        JButton Enter = new JButton("DELETE");
        Enter.setFocusPainted(false);
        Enter.setBounds(155, 400, 170, 25);
        Enter.setBackground(new Color(192, 192, 192));
        Enter.setForeground(Color.BLACK);
        Enter.setBorderPainted(false);
        Enter.setOpaque(true);
        Enter.setFont(new Font("Arial", Font.BOLD, 20));
        rightPanel.add(Enter, BorderLayout.CENTER);
        Enter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = Special.getText();
                String stockname = (String) delete.getSelectedItem();
                if (name.isEmpty() ) {
                    JOptionPane.showMessageDialog(null, "Name  cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
                    return;// Don't proceed with database insertion
                }
                int id;
                boolean val;
                try {
                    id = Integer.parseInt(name);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid id format", "Error", JOptionPane.ERROR_MESSAGE);
                    return; // Don't proceed with database insertion
                }
                val=db.delete_row_by_name(conn,"stock",stockname,id);
                if(val==true)
                {
                    JOptionPane.showMessageDialog(rightPanel, "Data deleted Successfully...", "Message", 1);
                    setVisible(false);
                    new BrokerDashboard();
                }
                else{
                    JOptionPane.showMessageDialog(rightPanel, "Data Not Found...", "Message", 1);
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
        Edit dashboard = new Edit();
        dashboard.setVisible(true);
    });
}
}
