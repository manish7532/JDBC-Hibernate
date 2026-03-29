import java.sql.*;

public class TransactionEx {

    static boolean hasSufficientBalance(Connection con, int account_number, double amt) {
        String sql = "SELECT balance FROM accounts WHERE account_number = ?";
        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, account_number);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("balance") >= amt;
                }
            }
        } catch (SQLException e) {
            System.err.println("Balance check error: " + e.getMessage());
        }
        return false;
    }

    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/lenden";
        String user = "";
        String password = "";
        try (Connection con = DriverManager.getConnection(url, user, password)) {
            con.setAutoCommit(false);
            String debit_query = "UPDATE accounts SET balance = balance - ? WHERE account_number = ? ";
            String credit_query = "UPDATE accounts SET balance = balance + ? WHERE account_number = ? ";

            try (PreparedStatement debitPreparedStatement = con.prepareStatement(debit_query);
                    PreparedStatement creditPreparedStatement = con.prepareStatement(credit_query)) {

                // Debit 500 from Thara Bhai Joginder's account        
                debitPreparedStatement.setDouble(1, 500);
                debitPreparedStatement.setInt(2, 103);

                // Crebit 500 to yeahhhhhhhhhh! Puneet SuperStar's account
                creditPreparedStatement.setDouble(1, 500);
                creditPreparedStatement.setInt(2, 104);

                if (hasSufficientBalance(con, 103, 500)) {
                    debitPreparedStatement.executeUpdate();
                    creditPreparedStatement.executeUpdate();
                    con.commit();
                    System.out.println("Transaction Successful");
                } else {
                    con.rollback();
                    System.out.println("Transaction failed!!, InSufficient Balance!!");
                }

            } catch (Exception e) {
                con.rollback();
                System.out.println(e.getMessage());
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
