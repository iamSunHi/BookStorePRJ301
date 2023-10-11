/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import models.Category;

/**
 *
 * @author Nhật Huy
 */
public class CategoryDAO {

    private Connection connection = null;
    private Statement stmt = null;
    private ResultSet rs = null;

    public List<Category> getAll() throws NamingException {
        List<Category> categoryList = new ArrayList<>();

        try {
            // Get connection.
            connection = DatabaseConfig.getConnection();

            // Execute SQL and return data results.
            String SQL = "SELECT * FROM dbo.Categories ORDER BY Name";
            stmt = connection.createStatement();
            rs = stmt.executeQuery(SQL);

            while (rs.next()) {
                Category category = new Category();
                category.setId(rs.getInt("Id"));
                category.setName(rs.getString("Name"));
                categoryList.add(category);
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

        return categoryList;
    }

    public Category get(int id) throws NamingException {
        Category category = null;

        try {
            // Get connection.
            connection = DatabaseConfig.getConnection();

            // Execute SQL and return data results.
            String SQL = "SELECT * FROM dbo.Categories WHERE Id = " + id;
            stmt = connection.createStatement();
            rs = stmt.executeQuery(SQL);

            if (rs.next()) {
                category = new Category();
                category.setId(rs.getInt("Id"));
                category.setName(rs.getString("Name"));
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
        
        return category;
    }

    public boolean create(String name) throws NamingException {
        boolean isSuccess = true;

        try {
            // Get connection.
            connection = DatabaseConfig.getConnection();

            // Execute SQL and return data results.
            String SQL = "SELECT * FROM dbo.Categories WHERE Name = N'" + name + "'";
            stmt = connection.createStatement();
            rs = stmt.executeQuery(SQL);

            if (!rs.next()) {
                SQL = "INSERT INTO dbo.Categories\n"
                        + "(\n"
                        + "    Name\n"
                        + ")\n"
                        + "VALUES\n"
                        + "(\n"
                        + "	N'" + name + "'\n"
                        + ")";
                rs = stmt.executeQuery(SQL);
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

    public boolean delete(int id) throws NamingException {
        boolean isSuccess = true;
        try {
            // Get connection.
            connection = DatabaseConfig.getConnection();

            // Execute SQL and return data results.
            String SQL = "SELECT * FROM dbo.Categories WHERE id = " + id;
            stmt = connection.createStatement();
            rs = stmt.executeQuery(SQL);

            if (rs.next()) {
                SQL = "DELETE FROM dbo.Categories WHERE Id = " + id;
                rs = stmt.executeQuery(SQL);
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

    public boolean update(int id, String newName) throws NamingException {
        boolean isSuccess = true;

        try {
            // Get connection.
            connection = DatabaseConfig.getConnection();

            // Execute SQL and return data results.
            String SQL = "SELECT * FROM dbo.Categories WHERE Name = N'" + newName + "'";
            stmt = connection.createStatement();
            rs = stmt.executeQuery(SQL);

            if (!rs.next()) {
                SQL = "UPDATE dbo.Categories SET Name = N'" + newName + "' WHERE Id = " + id;
                rs = stmt.executeQuery(SQL);
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
