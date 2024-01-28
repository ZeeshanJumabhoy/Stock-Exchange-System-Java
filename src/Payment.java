import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import javax.swing.*;;

public class Payment extends JFrame {
    Data db = new Data();
    Connection conn = db.connect_to_db("StockExchange", "postgres", "zeeshan");
    //  private JPanel panel1, panel2;

    public Payment() {
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

        JLabel lblNewLabel4 = new JLabel("Email : ");
        lblNewLabel4.setForeground(Color.BLACK);
        lblNewLabel4.setFont(new Font("Serif", Font.BOLD, 22));
        lblNewLabel4.setBounds(110, 154, 172, 22);
        rightPanel.add(lblNewLabel4);

        JTextField Name = new JTextField();
        Name.setBounds(280, 158, 172, 22);
        rightPanel.add(Name);

        JTextField ItemInfo = new JTextField();
        ItemInfo.setBounds(280, 208, 172, 22);
        rightPanel.add(ItemInfo);

        JLabel lblNewLabel6 = new JLabel("Amount : ");
        lblNewLabel6.setForeground(Color.BLACK);
        lblNewLabel6.setFont(new Font("Serif", Font.BOLD, 22));
        lblNewLabel6.setBounds(110, 204, 172, 22);
        rightPanel.add(lblNewLabel6);


        JButton Enter = new JButton("Submit");
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
                String stockname = Name.getText();
                String price = ItemInfo.getText();

                if (!stockname.contains("@") || !stockname.contains(".com")) {
                    JOptionPane.showMessageDialog(null, "Email must contain '@' and '.com'");
                    return;
                }
                if (stockname.isEmpty() || price.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Email and Price cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
                }

                double priceTex;
                    try {
                        priceTex = Double.parseDouble(price);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Invalid price format", "Error", JOptionPane.ERROR_MESSAGE);
                        return; // Don't proceed with database insertion
                    }
                    boolean val;
                    val = db.processPayment(conn, stockname, priceTex);
                    if (val == true) {
                        JOptionPane.showMessageDialog(rightPanel, "Amount Added Successfully...", "Message", 1);
                        setVisible(false);
                        new CustomerDashboard();
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
        contentPane.add(panel1, BorderLayout.WEST);
        contentPane.add(rightPanel);
    }
    public static void main(String[] args) {

        Payment gui = new Payment();
        gui.setVisible(true);

    }
}