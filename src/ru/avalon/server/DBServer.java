package ru.avalon.server;

import ru.avalon.utils.Crypt;

import java.sql.*;
import java.util.Map;

//реализуем сервер через ClassHolder Singleton
public class DBServer {

    private DBServer() {}

    private static class DBHolder {
        public static final DBServer HOLDER_INSTANCE = new DBServer();
    }

    public static DBServer getInstance() {
        return DBHolder.HOLDER_INSTANCE;
    }

    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    public void getDBConnection() {
        if (connection == null) {
            Map<String, String> credentials = Crypt.getCredentials();

        }
    }

}
