package ru.avalon.server;

import java.util.List;

public interface IDataBase<P, O> extends AutoCloseable {
    List<P> getAllProduct();
    List<P> getProductsByID(int id);
    void addRow(O o);
}
