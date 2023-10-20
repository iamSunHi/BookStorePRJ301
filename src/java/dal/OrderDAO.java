/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import models.OrderDetail;
import models.OrderHeader;

/**
 *
 * @author Sun Hi
 */
public class OrderDAO {

    private Connection connection = null;
    private PreparedStatement stmt = null;
    private ResultSet rs = null;

    public boolean createOrderHeader(OrderHeader orderHeader) throws NamingException {
        boolean isSuccess = true;

        try {
            // Get connection.
            connection = DatabaseConfig.getConnection();

            // Execute SQL and return data results.
            String SQL = "INSERT INTO dbo.OrderHeaders\n"
                    + "(\n"
                    + "    UserId,\n"
                    + "    Payment,\n"
                    + "    OrderDate,\n"
                    + "    PaymentDate,\n"
                    + "    OrderStatus,\n"
                    + "    PaymentStatus,\n"
                    + "    SessionId,\n"
                    + "    PaymentIntentId\n"
                    + ")\n"
                    + "VALUES\n"
                    + "(   ?,          -- UserId - nvarchar(32)\n"
                    + "    ?,           -- Payment - nvarchar(max)\n"
                    + "    ?, -- OrderDate - datetime2(7)\n"
                    + "    ?, -- PaymentDate - datetime2(7)\n"
                    + "    ?,          -- OrderStatus - nvarchar(max)\n"
                    + "    ?,          -- PaymentStatus - nvarchar(max)\n"
                    + "    ?,          -- SessionId - nvarchar(max)\n"
                    + "    ?           -- PaymentIntentId - nvarchar(max)\n"
                    + ")";
            stmt = connection.prepareStatement(SQL);
            stmt.setString(0, orderHeader.getUser().getId());
            stmt.setString(1, orderHeader.getPayment());
            stmt.setDate(2, (Date) orderHeader.getOrderDate());
            stmt.setDate(3, (Date) orderHeader.getPaymentDate());
            stmt.setString(4, orderHeader.getOrderStatus());
            stmt.setString(5, orderHeader.getPaymentStatus());
            stmt.setString(6, orderHeader.getSessionId());
            stmt.setString(7, orderHeader.getPaymentIntentId());
            int numberOfAffectedRows = stmt.executeUpdate();
            if (numberOfAffectedRows == 0) {
                isSuccess = false;
            }
        } catch (SQLException ex) {
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                }
            }

            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                }
            }
        }

        return isSuccess;
    }

    public boolean createOrderDetail(OrderDetail orderDetail) throws NamingException {
        boolean isSuccess = true;

        try {
            // Get connection.
            connection = DatabaseConfig.getConnection();

            // Execute SQL and return data results.
            String SQL = "INSERT INTO dbo.OrderDetails\n"
                    + "(\n"
                    + "    OrderHeaderId,\n"
                    + "    TotalPrice\n"
                    + ")\n"
                    + "VALUES\n"
                    + "(   (SELECT Id FROM dbo.OrderHeaders WHERE OrderDate = ?),  -- OrderHeaderId - int\n"
                    + "    ? -- TotalPrice - float\n"
                    + ")";
            stmt = connection.prepareStatement(SQL);
            stmt.setDate(0, (Date) orderDetail.getOrderHeader().getOrderDate());
            stmt.setDouble(1, orderDetail.getTotalPrice());
            int numberOfAffectedRows = stmt.executeUpdate();
            if (numberOfAffectedRows == 0) {
                isSuccess = false;
            }
        } catch (SQLException ex) {
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                }
            }

            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                }
            }
        }

        return isSuccess;
    }
}
