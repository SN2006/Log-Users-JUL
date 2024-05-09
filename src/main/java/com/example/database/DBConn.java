package com.example.database;

import com.example.utils.Constants;
import com.example.view.AppView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConn {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(DBConn.class);

    public static Connection getConnection() {
        Connection conn = null;
        AppView appView = new AppView();

        try {
            conn = DriverManager.getConnection(Constants.DB_DRIVER + Constants.DB_BASE_URL
            + Constants.DB_NAME);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            appView.getOutput(e.getMessage());
        }
        return conn;
    }

}
