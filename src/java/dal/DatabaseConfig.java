/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author Nhật Huy
 */
public class DatabaseConfig {

    public static Connection getConnection() throws SQLException, NamingException {
        Connection connection = null;
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            DataSource dataSource = (DataSource) envContext.lookup("jdbc/BookstoreDB");
            connection = dataSource.getConnection();
        } catch (NamingException e) {
            throw new SQLException("DataSource lookup failed", e);
        }
        return connection;
    }
}
