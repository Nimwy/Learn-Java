import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Main {

    public static void main(String[] args) {
    
        String url = "jdbc:postgresql://localhost:5432/shop_db";
        String username = "postgres";
        String password = "123456";

        String sql = "SELECT * FROM products";

        try {
            Connection connection =
                    DriverManager.getConnection(url, username, password);

            System.out.println("Connected!");

            PreparedStatement statement = 
                connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String category = resultSet.getString("category");
                BigDecimal price = resultSet.getBigDecimal("price");

                System.out.println(
                        id + " | " +
                        name + " | " +
                        category + " | " +
                        price
                );
            }

            resultSet.close();
            statement.close();
            connection.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
