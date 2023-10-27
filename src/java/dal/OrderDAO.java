/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
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

    public List<OrderHeader> getAllByUserId(String userId) throws NamingException {
        List<OrderHeader> orderList = new ArrayList<>();

        try {
            // Get connection.
            connection = DatabaseConfig.getConnection();

            // Execute SQL and return data results.
            String SQL = "SELECT * FROM dbo.OrderHeaders WHERE UserId = ?";
            stmt = connection.prepareStatement(SQL);
            stmt.setString(1, userId);
            rs = stmt.executeQuery();

            while (rs.next()) {
                OrderHeader order = new OrderHeader();
                order.setId(rs.getInt("Id"));
                order.setPayment(rs.getString("Payment"));
                order.setOrderDate(rs.getTimestamp("OrderDate"));
                order.setPaymentDate(rs.getTimestamp("PaymentDate"));
                order.setOrderStatus(rs.getString("OrderStatus"));
                order.setPaymentStatus(rs.getString("PaymentStatus"));
                order.setPhone(rs.getString("Phone"));
                order.setAddress(rs.getString("Address"));

                orderList.add(order);
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

        return orderList;
    }

    public OrderDetail getOrder(int orderId) throws NamingException {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setBooks(new ArrayList<>());

        try {
            // Get connection.
            connection = DatabaseConfig.getConnection();

            // Execute SQL and return data results.
            String SQL = "SELECT * FROM dbo.OrderHeaders WHERE Id = ?";
            stmt = connection.prepareStatement(SQL);
            stmt.setInt(1, orderId);
            rs = stmt.executeQuery();

            if (rs.next()) {
                OrderHeader order = new OrderHeader();
                order.setId(rs.getInt("Id"));
                order.setPayment(rs.getString("Payment"));
                order.setOrderDate(rs.getTimestamp("OrderDate"));
                order.setPaymentDate(rs.getTimestamp("PaymentDate"));
                order.setOrderStatus(rs.getString("OrderStatus"));
                order.setPaymentStatus(rs.getString("PaymentStatus"));
                order.setPhone(rs.getString("Phone"));
                order.setAddress(rs.getString("Address"));

                orderDetail.setOrderHeader(order);
            }
            
            SQL = "SELECT BooksId FROM dbo.OrderDetails JOIN dbo.BookOrderDetail ON BookOrderDetail.OrderDetailsId = OrderDetails.Id\n"
                    + "WHERE OrderHeaderId = ?";
            stmt = connection.prepareStatement(SQL);
            stmt.setInt(1, orderId);
            rs = stmt.executeQuery();

            while (rs.next()) {
                int bookId = rs.getInt("BooksId");
                SQL = "SELECT Title, Author, Price, ImageUrl FROM dbo.Books WHERE Id = ?";
                stmt = connection.prepareStatement(SQL);
                stmt.setInt(1, bookId);
                ResultSet rs_temp = stmt.executeQuery();
                if (rs_temp.next()) {
                    Book book = new Book();
                    book.setTitle(rs_temp.getString("Title"));
                    book.setAuthor(rs_temp.getString("Author"));
                    book.setPrice(rs_temp.getDouble("Price"));
                    book.setImageUrl(rs_temp.getString("ImageUrl"));

                    orderDetail.getBooks().add(book);
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

        return orderDetail;
    }

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
                    + "    PaymentIntentId,\n"
                    + "    Phone,\n"
                    + "    Address\n"
                    + ")\n"
                    + "VALUES\n"
                    + "(   ?,          -- UserId - nvarchar(32)\n"
                    + "    ?,           -- Payment - nvarchar(max)\n"
                    + "    ?, -- OrderDate - datetime2(7)\n"
                    + "    ?, -- PaymentDate - datetime2(7)\n"
                    + "    ?,          -- OrderStatus - nvarchar(max)\n"
                    + "    ?,          -- PaymentStatus - nvarchar(max)\n"
                    + "    ?,          -- SessionId - nvarchar(max)\n"
                    + "    ?,          -- PaymentIntentId - nvarchar(max)\n"
                    + "    ?,          -- Phone - nvarchar(20)\n"
                    + "    ?           -- Address - nvarchar(max)\n"
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
            stmt.setString(9, orderHeader.getPhone());
            stmt.setString(10, orderHeader.getAddress());

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
                stmt.setInt(1, orderDetail.getOrderHeader().getId());
                rs = stmt.executeQuery();

                int orderDetailId = 0;
                if (rs.next()) {
                    orderDetailId = rs.getInt("Id");
                }
                if (orderDetailId != 0) {
                    SQL = "INSERT INTO dbo.BookOrderDetail\n"
                            + "(\n"
                            + "    BooksId,\n"
                            + "    OrderDetailsId\n"
                            + ")\n"
                            + "VALUES\n"
                            + "(   ?, -- BooksId - int\n"
                            + "    ?  -- OrderDetailsId - int\n"
                            + ")";
                    stmt = connection.prepareStatement(SQL);
                    for (Book book : orderDetail.getBooks()) {
                        stmt.setInt(1, book.getId());
                        stmt.setInt(2, orderDetailId);
                        stmt.executeUpdate();
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

    public boolean updateStripePaymentId(OrderHeader orderHeader) throws NamingException {
        boolean isSuccess = true;

        try {
            // Get connection.
            connection = DatabaseConfig.getConnection();

            // Execute SQL and return data results.
            String SQL = "UPDATE dbo.OrderHeaders \n"
                    + "SET SessionId = ?, PaymentIntentId = ?\n"
                    + "WHERE Id = ?";
            stmt = connection.prepareStatement(SQL);
            stmt.setString(1, orderHeader.getSessionId());
            stmt.setString(2, orderHeader.getPaymentIntentId());
            stmt.setInt(3, orderHeader.getId());
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

    public boolean updateOrderStatus(int orderHeaderId, String orderStatus, String paymentStatus) throws NamingException {
        boolean isSuccess = true;

        try {
            // Get connection.
            connection = DatabaseConfig.getConnection();

            // Execute SQL and return data results.
            String SQL = "UPDATE dbo.OrderHeaders \n"
                    + "SET OrderStatus = ?, PaymentStatus = ?, PaymentDate = ?\n"
                    + "WHERE Id = ?";
            stmt = connection.prepareStatement(SQL);
            stmt.setString(1, orderStatus);
            stmt.setString(2, paymentStatus);
            Timestamp sqlPaymentDate = new Timestamp((new java.util.Date()).getTime());
            stmt.setTimestamp(3, sqlPaymentDate);
            stmt.setInt(4, orderHeaderId);
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
