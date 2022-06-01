package ru.avalon.server;

import ru.avalon.model.Order;
import ru.avalon.model.Product;
import ru.avalon.utils.Crypt;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//реализуем сервер через ClassHolder Singleton
public class DBServer implements IDataBase {

    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    private String testUrl = "jdbc:derby:/home/user/Документы/Avalontest_DB";
    private String testCred = "derby";

    private DBServer() {
    }

    private static class DBHolder {
        public static final DBServer HOLDER_INSTANCE = new DBServer();
    }

    public static DBServer getInstance() {
        return DBHolder.HOLDER_INSTANCE;
    }

    public void getDBConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(testUrl, testCred, testCred);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void checkStatement() throws SQLException {
        if (statement == null) {
            statement = connection.createStatement();
        }
    }

    private List<Product> fillList(ResultSet set) throws SQLException {
        List<Product> productsList = new ArrayList<>();
        while (set.next()) {
            Product prod = new Product(set.getString(1),
                    set.getString(2),
                    set.getString(3),
                    set.getInt(4),
                    set.getInt(5));
            productsList.add(prod);
        }
        return productsList;
    }

    @Override
    public List<Product> getAllProduct() {
        getDBConnection();
        try {
            checkStatement();
            resultSet = statement.executeQuery("SELECT * FROM products;");
            List<Product> products = fillList(resultSet);
            resultSet.close();
            return products;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Product> getProductsFromOrder(int id) {
        getDBConnection();
        if (id <= 0) {
            return null;
        }
        try {
            checkStatement();
            resultSet = statement.executeQuery("SELECT * FROM PRODUCTS" +
                    " JOIN Positions ON Positions.product_art_number = Products.art_number WHERE order_id = " + id + ";");
            List<Product> products = fillList(resultSet);
            resultSet.close();
            return products;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean addOrder(Order order) {
        //TODO
        if (order == null) {
            throw new IllegalArgumentException("Аргумент не может быть null");
        }
        if (order.getId() < 0 ||
                order.getCreationDate() == null ||
                order.getCustomerName() == null ||
                order.getCustomerPhone() == null ||
                order.getCustomerAddress() == null ) {
            throw new IllegalArgumentException("Номер заказа должен быть > 0; Заказ должен содержать информацию о клиенте");
        }
        try {
            getDBConnection();
            checkStatement();
            String query = "INSERT INTO orders VALUES (" +
                    order.getId() + ", '" +
                    order.getCreationDate() + "', '" +
                    order.getCustomerName() + "', '" +
                    order.getCustomerPhone() + "', '" +
                    order.getCustomerEmail() + "', '" +
                    order.getCustomerAddress() + "', '" +
                    order.getOrderState() + "' , '" +
                    order.getShipmentDate() + "')";
            System.out.println(query);
            statement.executeUpdate(query);
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public void close() throws Exception {
        if (connection != null) connection.close();
        if (statement != null) statement.close();
        if (resultSet != null) resultSet.close();
    }
}
