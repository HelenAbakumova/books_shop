package repository;

import entity.Order;
import entity.OrderProduct;
import entity.Product;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import repository.exception.RepositoryException;

import java.sql.*;


public class OrderRepository implements repository.api.OrderRepository {

    private static final Logger LOGGER = LogManager.getLogger(OrderRepository.class);
    private static final String ADD_ORDER = "INSERT INTO books_shop.orders(user_id, status_id, date, amount)" +
            " VALUES (?, ?, ?, ?);";
    private static final String ADD_ORDER_PRODUCT = "INSERT INTO books_shop.order_products (id_product, price, count,id_order)" +
            " VALUES (?, ?, ?, ?);";

    @Override
    public int create(Order order, Connection connection) {
        int id = 0;
        try (PreparedStatement statement = connection.prepareStatement(ADD_ORDER, Statement.RETURN_GENERATED_KEYS)) {
            int index = 0;
            statement.setInt(index++, order.getUserId());
            statement.setInt(index++, getStatusIdByName(order.getStatus(), connection));
            statement.setTimestamp(index++, order.getDate());
            statement.setString(index++, order.getAmount());

            if (statement.executeUpdate() > 0) {
                ResultSet resultSet = statement.getGeneratedKeys();
                if (resultSet.next()) {
                    id = resultSet.getInt(1);
                    order.setId(id);
                    resultSet.close();
                    createOrderProduct(order, id, connection);
                    return id;
                }
                resultSet.close();
            }
        } catch (SQLException e) {
            LOGGER.warn("Trouble with data base", e);
        }
        return id;
    }

    private int getStatusIdByName(String statusName, Connection connection) {
        int id;
        try (PreparedStatement statement = connection.prepareStatement("SELECT id FROM books_shop.statuses when books_shop.statuses.name " + statusName)) {
            ResultSet rs = statement.executeQuery();
           id = rs.getInt("id");
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        }
        return id;
    }

    private void createOrderProduct(Order order, int id, Connection connection) {
        try {
            PreparedStatement statement = connection.prepareStatement(ADD_ORDER_PRODUCT);
            for (OrderProduct orderProduct : order.getProducts()) {
                int index = 0;
                statement.setInt(index++, orderProduct.getProduct());
                statement.setDouble(index++, orderProduct.getPrice());
                statement.setInt(index++, orderProduct.getCount());
                statement.setInt(index++, id);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            LOGGER.warn("Trouble with data base", e);
        }
    }
}
