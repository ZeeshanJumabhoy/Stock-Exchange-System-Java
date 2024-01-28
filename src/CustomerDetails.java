import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.util.List;

public class CustomerDetails extends JFrame {
    Data db = new Data();
    Connection conn = db.connect_to_db("StockExchange", "postgres", "zeeshan");

    private JButton addStocksButton, addInsidersButton, customerDetailsButton, portfolioButton;

    public CustomerDetails() {
        setTitle("Customer Details");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Create the left panel with a darker color
        JPanel leftPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        leftPanel.setBackground(new Color(231, 252, 231));
        leftPanel.setPreferredSize(new Dimension(200, 300));

        // Create the right panel with a lighter color
        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(new Color(231, 252, 231));
        rightPanel.setPreferredSize(new Dimension(200, 300));

        // Initialize rightPanel with BorderLayout
        rightPanel.setLayout(new BorderLayout());

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


        // DefaultTableModel for JTable
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Username");
        model.addColumn("Email");
        model.addColumn("Number");
        model.addColumn("CNIC");

        // Create a JTable with the DefaultTableModel
        JTable table = new JTable(model);
       // table.setBackground(new Color(231, 252, 231));
        JTableHeader header = table.getTableHeader();
        header.setBackground(new Color(231, 252, 231));
        table.setSelectionBackground(new Color(231, 252, 231));
        table.getColumnModel().getColumn(0).setPreferredWidth(200);
        table.getColumnModel().getColumn(1).setPreferredWidth(200);
        table.getColumnModel().getColumn(2).setPreferredWidth(200);
        table.getColumnModel().getColumn(3).setPreferredWidth(200);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        rightPanel.add(scrollPane, BorderLayout.CENTER);

        // Fetch data from the database and add it to the table
        List<Object[]> databaseData = db.getDataFromDatabase(conn);
        for (Object[] row : databaseData) {
            model.addRow(row);
        }

        // Create a button panel and set its layout
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.white);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        // Add the button panel to the top right corner of the right panel
        rightPanel.add(buttonPanel, BorderLayout.NORTH);

        // Set the layout of the content pane
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(leftPanel, BorderLayout.WEST);
        contentPane.add(rightPanel, BorderLayout.CENTER);
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CustomerDetails dashboard = new CustomerDetails();
            dashboard.setVisible(true);
        });
    }
}



