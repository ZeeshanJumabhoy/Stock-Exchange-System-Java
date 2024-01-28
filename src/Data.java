import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Data {
    public Connection connect_to_db(String dbname, String user, String pass) {
        Connection conn = null;
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + dbname, user, pass);
            if (conn != null) {
                System.out.println("Connection Established");
            } else {
                System.out.println("Connection Failed");
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        return conn;
    }
    public boolean insert_row_registration(Connection conn,String table_name,String username, String email, String unique_number,String cnic,String password, String type){
        Statement statement;
        boolean val;
        try {
            String query=String.format("insert into %s(username,email,unique_number,cnic,password,type) values('%s','%s','%s','%s','%s','%s');",table_name,username, email, unique_number,cnic,password,type);
            statement=conn.createStatement();
            statement.executeUpdate(query);
            System.out.println("Row Inserted");
            val=true;
        }catch (Exception e){
            System.out.println(e);
            val=false;
        }
        return val;
    }
    public boolean checkAdminCredentials(Connection connection,String table_name, String email, String password,String type) {
        String query = "SELECT * FROM customer WHERE email = ? AND password = ? AND type=?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3,type);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next(); // Returns true if a record is found
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean insert_row_stock(Connection conn,String table_name,String stockname,String price){
        Statement statement;
        boolean val;
        try {
            String query=String.format("insert into %s(stockname,price) values('%s', %s);",table_name,stockname, price);
            statement=conn.createStatement();
            statement.executeUpdate(query);
            System.out.println("Row Inserted");
            val=true;
        }catch (Exception e){
            System.out.println(e);
            val=false;
        }
        return val;
    }
    public boolean insert_row_insider(Connection conn,String table_name,String stockname,String inside){
        Statement statement;
        boolean val;
        try {
            String query=String.format("insert into %s(stockname,inside) values('%s', '%s');",table_name,stockname, inside);
            statement=conn.createStatement();
            statement.executeUpdate(query);
            System.out.println("Row Inserted");
            val=true;
        }catch (Exception e){
            System.out.println(e);
            val=false;
        }
        return val;
    }
    public List<String> readDataStock(Connection conn, String tableName) {
        List<String> restaurantNames = new ArrayList<>();
        try (Statement statement = conn.createStatement()) {
            String query = String.format("SELECT DISTINCT stockname FROM %s", tableName);
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                restaurantNames.add(rs.getString("stockname"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return restaurantNames;
    }
    public List<Object[]> getDataFromDatabase(Connection conn) {
        List<Object[]> data = new ArrayList<>();

        try (Statement statement = conn.createStatement()) {

            String query = "SELECT username, email, unique_number, cnic FROM customer WHERE type='Customer'";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                Object[] row = new Object[]{
                        resultSet.getString("username"),
                        resultSet.getString("email"),
                        resultSet.getString("unique_number"),
                        resultSet.getString("cnic")
                };
                data.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return data;
    }
    public List<Object[]> getstockFromDatabase(Connection conn) {
        List<Object[]> data = new ArrayList<>();

        try (Statement statement = conn.createStatement()) {

            String query = "SELECT distinct stockname, price FROM stock ";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                Object[] row = new Object[]{
                        resultSet.getString("stockname"),
                        resultSet.getString("price")
                };
                data.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return data;
    }
    public List<Object[]> getinsideFromDatabase(Connection conn) {
        List<Object[]> data = new ArrayList<>();

        try (Statement statement = conn.createStatement()) {

            String query = "SELECT distinct stockname, inside FROM insider ";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                Object[] row = new Object[]{
                        resultSet.getString("stockname"),
                        resultSet.getString("inside")
                };
                data.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return data;
    }
    public boolean delete_row_by_name(Connection conn,String table_name,String name,int id){
        Statement statement;
        boolean val;
        try{
            String query=String.format("delete from %s where id=%s AND stockname='%s' ",table_name,id,name);
            statement=conn.createStatement();
            statement.executeUpdate(query);
            System.out.println("Data Deleted");
            val=true;
        }catch (Exception e){
            System.out.println(e);
            val=false;
        }
        return val;
    }

        public boolean processPayment(Connection conn, String customerEmail, double amount) {
            try {
                // Check if the customer email is present in the Customer table
                if (!isCustomerPresent(conn, customerEmail)) {
                    System.out.println("Customer not found. Payment not processed.");
                    return false;
                }

                // Check if the customer email is present in the Payment table
                if (isPaymentRecordExists(conn, customerEmail)) {
                    // Update the existing payment record
                    updatePaymentRecord(conn, customerEmail, amount);
                } else {
                    // Insert a new payment record
                    insertPaymentRecord(conn, customerEmail, amount);
                }

                System.out.println("Payment processed successfully.");
                return true;

            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Error processing payment: " + e.getMessage());
                return false;
            }
        }

        private boolean isCustomerPresent(Connection conn, String customerEmail) throws SQLException {
            String query = "SELECT * FROM customer WHERE email = ? AND type = 'Customer'";

            try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                preparedStatement.setString(1, customerEmail);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    return resultSet.next(); // Returns true if a customer record is found
                }
            }
        }

        private boolean isPaymentRecordExists(Connection conn, String customerEmail) throws SQLException {
            String query = "SELECT * FROM payment WHERE email = ?";

            try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                preparedStatement.setString(1, customerEmail);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    return resultSet.next(); // Returns true if a payment record is found
                }
            }
        }

        private void insertPaymentRecord(Connection conn, String customerEmail, double amount) throws SQLException {
            String query = "INSERT INTO payment (amount, email, reference_id) VALUES (?, ?, ?)";

            try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                preparedStatement.setDouble(1, amount);
                preparedStatement.setString(2, customerEmail);

                // Get the reference_id from the customer table based on the email
                int referenceId = getCustomerId(conn, customerEmail);
                preparedStatement.setInt(3, referenceId);

                preparedStatement.executeUpdate();
            }
        }

        private void updatePaymentRecord(Connection conn, String customerEmail, double amount) throws SQLException {
            String query = "UPDATE payment SET amount = amount + ? WHERE email = ?";

            try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                preparedStatement.setDouble(1, amount);
                preparedStatement.setString(2, customerEmail);

                preparedStatement.executeUpdate();
            }
        }

        private int getCustomerId(Connection conn, String customerEmail) throws SQLException {
            String query = "SELECT id FROM customer WHERE email = ?";

            try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                preparedStatement.setString(1, customerEmail);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getInt("id");
                    } else {
                        throw new SQLException("Customer id not found for email: " + customerEmail);
                    }
                }
            }
        }

    public static void main(String[] args) {
        Data db = new Data();
            Connection conn = db.connect_to_db("StockExchange", "postgres", "zeeshan");
            db.processPayment(conn,"mustaf@.com",400);

    }
}
