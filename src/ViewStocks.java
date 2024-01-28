import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.Connection;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class ViewStocks extends JFrame {
    Data db = new Data();
    Connection conn = db.connect_to_db("StockExchange", "postgres", "zeeshan");
  //  private JPanel panel1, panel2;

    public ViewStocks() {
        super("Customer Dashboard");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        JPanel panel1;

        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(new Color(231, 252, 231));
        rightPanel.setPreferredSize(new Dimension(200, 300));

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

        // DefaultTableModel for JTable
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Stock Name");
        model.addColumn("Price");

        // Create a JTable with the DefaultTableModel
        JTable table = new JTable(model);
        // table.setBackground(new Color(231, 252, 231));
        JTableHeader header = table.getTableHeader();
        header.setBackground(new Color(231, 252, 231));
        table.setSelectionBackground(new Color(231, 252, 231));
        table.getColumnModel().getColumn(0).setPreferredWidth(200);
        table.getColumnModel().getColumn(1).setPreferredWidth(200);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        rightPanel.add(scrollPane, BorderLayout.CENTER);

        // Fetch data from the database and add it to the table
        List<Object[]> databaseData = db.getstockFromDatabase(conn);
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
        contentPane.add(panel1, BorderLayout.WEST);
        contentPane.add(rightPanel, BorderLayout.CENTER);
    }
    public static void main(String[] args) {

            ViewStocks gui = new ViewStocks();
            gui.setVisible(true);

    }
}
