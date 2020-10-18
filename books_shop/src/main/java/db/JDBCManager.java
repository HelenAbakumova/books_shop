package com.epam.preprod.store.db;

import com.epam.preprod.store.exception.RepositoryException;
import com.epam.preprod.store.util.DataBaseUtils;

import java.sql.Connection;
import java.util.function.Function;

public class JDBCManager {
    private DataBaseUtils dataBaseUtils;

    public JDBCManager(DataBaseUtils dataBaseUtils) {
        this.dataBaseUtils = dataBaseUtils;
    }

    public <T> T doInTransaction(Function<Connection, T> operation) throws RepositoryException {
        Connection connection = dataBaseUtils.getConnection();
        T result;
        try {
            connection.setAutoCommit(false);
            result = operation.apply(connection);
            connection.commit();
        } catch (Exception e) {
            dataBaseUtils.rollback(connection);
            throw new RepositoryException(e);
        } finally {
            dataBaseUtils.closeConnection(connection);
        }
        return result;
    }
}
