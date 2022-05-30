package ru.avalon.server;

import ru.avalon.model.Order;

import java.util.List;

public interface IDataBase<T> extends AutoCloseable {
    List<T> getAllProduct();
    List<T> getProductsFromOrder(int id);
    boolean addOrder(Order order);
}
