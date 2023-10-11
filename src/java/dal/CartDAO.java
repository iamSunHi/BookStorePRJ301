/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import models.Book;
import models.Cart;

/**
 *
 * @author Nhật Huy
 */
public class CartDAO {

    private Connection connection = null;
    private PreparedStatement stmt = null;
    private ResultSet rs = null;

    public Cart get(String userId) throws NamingException {
        Cart cart = null;

        try {
            // Get connection.
            connection = DatabaseConfig.getConnection();

            // Execute SQL and return data results.
            String SQL = "SELECT Id FROM dbo.Carts WHERE UserId = ?";
            stmt = connection.prepareStatement(SQL);
            stmt.setString(1, userId);

            rs = stmt.executeQuery();

            if (!rs.next()) {
                if (this.create(userId)) {
                    SQL = "SELECT Id FROM dbo.Carts WHERE UserId = ?";
                    stmt = connection.prepareStatement(SQL);
                    stmt.setString(1, userId);
                    rs = stmt.executeQuery();
                    rs.next();
                }
            }

            cart = new Cart();
            cart.setId(rs.getInt("Id"));
            cart.setUserId(userId);
            cart.setBookList(new ArrayList<>());

            SQL = "SELECT BooksId FROM dbo.BookCart WHERE CartsId = ?";
            stmt = connection.prepareStatement(SQL);
            stmt.setInt(1, cart.getId());

            rs = stmt.executeQuery();

            List<Integer> bookIdList = new ArrayList<>();
            while (rs.next()) {
                bookIdList.add(rs.getInt("BooksId"));
            }

            BookDAO bookDAO = new BookDAO();
            for (int i : bookIdList) {
                Book book = bookDAO.getBookById(i);
                cart.getBookList().add(book);
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

        return cart;
    }

    public boolean create(String userId) throws NamingException {
        boolean isSuccess = true;

        try {
            // Get connection.
            connection = DatabaseConfig.getConnection();

            // Execute SQL and return data results.
            String SQL = "INSERT INTO dbo.Carts\n"
                    + "(\n"
                    + "     UserId\n"
                    + ")\n"
                    + "VALUES\n"
                    + "("
                    + "     ?\n"
                    + ")";
            stmt = connection.prepareStatement(SQL);
            stmt.setString(1, userId);
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

    public boolean addBook(int cartId, int bookId) throws NamingException {
        boolean isSuccess = true;

        try {
            // Get connection.
            connection = DatabaseConfig.getConnection();

            // Execute SQL and return data results.
            String SQL = "SELECT * FROM dbo.BookCart\n"
                    + "WHERE BooksId = ? AND CartsId = ?";
            stmt = connection.prepareStatement(SQL);
            stmt.setInt(1, bookId);
            stmt.setInt(2, cartId);
            rs = stmt.executeQuery();

            if (!rs.next()) {
                SQL = "INSERT INTO dbo.BookCart\n"
                        + "(\n"
                        + "    BooksId,\n"
                        + "    CartsId\n"
                        + ")\n"
                        + "VALUES\n"
                        + "(   ?,\n"
                        + "    ?\n"
                        + ")";
                stmt = connection.prepareStatement(SQL);
                stmt.setInt(1, bookId);
                stmt.setInt(2, cartId);
                int numberOfAffectedRows = stmt.executeUpdate();
                if (numberOfAffectedRows == 0) {
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

    public boolean removeBook(int cartId, int bookId) throws NamingException {
        boolean isSuccess = true;

        try {
            // Get connection.
            connection = DatabaseConfig.getConnection();

            // Execute SQL and return data results.
            String SQL = "DELETE FROM dbo.BookCart WHERE CartsId = ? AND BooksId = ?";
            stmt = connection.prepareStatement(SQL);
            stmt.setInt(1, cartId);
            stmt.setInt(2, bookId);

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
