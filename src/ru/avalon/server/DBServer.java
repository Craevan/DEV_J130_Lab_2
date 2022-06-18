package ru.avalon.server;

import ru.avalon.model.Order;
import ru.avalon.model.Product;
import ru.avalon.utils.ConsoleHelper;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

//реализуем сервер через ClassHolder Singleton
public class DBServer implements IDataBase<Product, Order> {

    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    private final Properties properties = new Properties();

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
            try (FileReader fReader = new FileReader("src/ru/avalon/resources/Credentials.properties")) {
                properties.load(fReader);
                connection = DriverManager.getConnection(properties.getProperty("db.host"),
                        properties.getProperty("db.login"),
                        properties.getProperty("db.password"));
            } catch (SQLException e) {
                ConsoleHelper.writeErrorMessage("Ошибка при подключении к БД");
            } catch (FileNotFoundException e) {
                ConsoleHelper.writeErrorMessage("Не найден файл настроек");
            } catch (IOException e) {
                ConsoleHelper.writeErrorMessage("Ошибка ввода/вывода");
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
            resultSet = statement.executeQuery("SELECT * FROM products");
            List<Product> products = fillList(resultSet);
            resultSet.close();
            return products;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Product> getProductsByID(int id) {
        getDBConnection();
        if (id <= 0) {
            return null;
        }
        try {
            checkStatement();
            resultSet = statement.executeQuery("SELECT * FROM PRODUCTS" +
                    " JOIN Positions ON Positions.product_art_number = Products.art_number WHERE order_id = " + id);
            List<Product> products = fillList(resultSet);
            resultSet.close();
            return products;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void addRow(Order order) {
        if (order == null) {
            throw new IllegalArgumentException("Аргумент не может быть null");
        }
        if (order.getId() < 0 ||
                order.getCreationDate() == null ||
                order.getCustomerName() == null ||
                order.getCustomerPhone() == null ||
                order.getCustomerAddress() == null) {
            throw new IllegalArgumentException("Номер заказа должен быть > 0; Заказ должен содержать информацию о клиенте");
        }
        try {
            getDBConnection();
            checkStatement();
            String query;
            if (order.getShipmentDate() != null) {
                query = "INSERT INTO orders VALUES (" +
                        order.getId() + ", '" +
                        order.getCreationDate() + "', '" +
                        order.getCustomerName() + "', '" +
                        order.getCustomerPhone() + "', '" +
                        order.getCustomerEmail() + "', '" +
                        order.getCustomerAddress() + "', '" +
                        order.getOrderState() + "' , '" +
                        order.getShipmentDate() + "')";
            }
            else {
                query = "INSERT INTO orders VALUES (" +
                        order.getId() + ", '" +
                        order.getCreationDate() + "', '" +
                        order.getCustomerName() + "', '" +
                        order.getCustomerPhone() + "', '" +
                        order.getCustomerEmail() + "', '" +
                        order.getCustomerAddress() + "', '" +
                        order.getOrderState() + "' , " +
                        order.getShipmentDate() + ")";
            }
            statement.executeUpdate(query);
        } catch (SQLException ex) {
            ConsoleHelper.writeErrorMessage("Ошибка при добавлении записи в БД");
        }
    }

    @Override
    public void close() throws Exception {
        if (connection != null) connection.close();
        if (statement != null) statement.close();
        if (resultSet != null) resultSet.close();
    }
}
