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
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.naming.NamingException;
import models.Book;
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

    public int createOrderHeader(OrderHeader orderHeader) throws NamingException, ParseException {
        int orderHeaderId = 0;

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
            stmt.setString(1, orderHeader.getUser().getId());
            stmt.setString(2, orderHeader.getPayment());
            Timestamp orderDate = new Timestamp(orderHeader.getOrderDate().getTime());
            stmt.setTimestamp(3, orderDate);
            java.util.Date paymentDate = new java.util.Date(new SimpleDateFormat("yyyy-MM-dd").parse("2000-01-01").getTime());
            Timestamp sqlPaymentDate = new Timestamp(paymentDate.getTime());
            stmt.setTimestamp(4, sqlPaymentDate);
            stmt.setString(5, orderHeader.getOrderStatus());
            stmt.setString(6, orderHeader.getPaymentStatus());
            stmt.setString(7, orderHeader.getSessionId());
            stmt.setString(8, orderHeader.getPaymentIntentId());

            int numberOfAffectedRows = stmt.executeUpdate();
            if (numberOfAffectedRows != 0) {
                SQL = "SELECT Id FROM dbo.OrderHeaders WHERE OrderDate = ?";
                stmt = connection.prepareStatement(SQL);
                stmt.setTimestamp(1, orderDate);

                rs = stmt.executeQuery();
                if (rs.next()) {
                    orderHeaderId = rs.getInt("Id");
                }
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
        return orderHeaderId;
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
                    + "(   ?,  -- OrderHeaderId - int\n"
                    + "    ? -- TotalPrice - float\n"
                    + ")";
            stmt = connection.prepareStatement(SQL);
            stmt.setInt(1, orderDetail.getOrderHeader().getId());
            stmt.setDouble(2, orderDetail.getTotalPrice());
            int numberOfAffectedRows = stmt.executeUpdate();
            if (numberOfAffectedRows != 0) {
                SQL = "SELECT Id FROM dbo.OrderDetails WHERE OrderHeaderId = ?";
                stmt = connection.prepareStatement(SQL);
                stmt.setInt(0, orderDetail.getOrderHeader().getId());
                rs = stmt.executeQuery();

                int orderDetailId = 0;
                if (rs.next()) {
                    orderDetailId = rs.getInt("Id");
                }
                if (orderDetailId != 0) {
                    for (Book book : orderDetail.getBooks()) {
                        SQL = "INSERT INTO dbo.BookOrderDetail\n"
                                + "(\n"
                                + "    BooksId,\n"
                                + "    OrderDetailsId\n"
                                + ")\n"
                                + "VALUES\n"
                                + "(   0, -- BooksId - int\n"
                                + "    0  -- OrderDetailsId - int\n"
                                + ")";
                        stmt = connection.prepareStatement(SQL);
                        stmt.setInt(1, book.getId());
                        stmt.setInt(2, orderDetailId);
                    }
                } else {
                    isSuccess = false;
                }
            } else {
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
