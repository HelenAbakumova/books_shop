package com.epam.preprod.store.util;

import com.epam.preprod.store.exception.RepositoryException;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Util class for data base.
 */
public class DataBaseUtils {
    private DataSource dataSource;
    private static final Logger LOGGER = Logger.getLogger(DataBaseUtils.class);
    private static final String CLOSE_CONNECTION_ERROR = "Cant close connection";
    private static final String ROLLBACK_ERROR = "Can't rollback";
    private static final String NEW_CONNECTION_ERROR = "Can't get a new connection";

    public DataBaseUtils(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void rollback(Connection connection) {
        try {
            connection.rollback();
        } catch (SQLException e) {
            LOGGER.warn(ROLLBACK_ERROR, e);
            throw new RepositoryException(e);
        }
    }

    public void closeConnection(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            LOGGER.warn(CLOSE_CONNECTION_ERROR, e);
            throw new RepositoryException(e);
        }
    }

    public Connection getConnection() {
        Connection connection;
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            LOGGER.warn(NEW_CONNECTION_ERROR);
            throw new RepositoryException(e);
        }
        return connection;
    }
}
