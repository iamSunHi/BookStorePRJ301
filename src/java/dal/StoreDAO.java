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
import java.util.UUID;
import javax.naming.NamingException;
import models.Store;

/**
 *
 * @author Sun Hi
 */
public class StoreDAO {

    private Connection connection = null;
    private PreparedStatement stmt = null;
    private ResultSet rs = null;

    public List<Store> getAll() throws NamingException {
        List<Store> storeList = new ArrayList<>();

        try {
            // Get connection.
            connection = DatabaseConfig.getConnection();

            // Execute SQL and return data results.
            String SQL = "SELECT * FROM dbo.Stores";
            stmt = connection.prepareStatement(SQL);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Store store = new Store();
                storeList.add(store);
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

        return storeList;
    }

    public List<Store> getNotVerified() throws NamingException {
        List<Store> storeList = new ArrayList<>();

        try {
            // Get connection.
            connection = DatabaseConfig.getConnection();

            // Execute SQL and return data results.
            String SQL = "SELECT * FROM dbo.Stores WHERE isVerified = ?";
            stmt = connection.prepareStatement(SQL);
            stmt.setBoolean(1, false);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Store store = new Store();
                store.setId(rs.getString("Id"));
                store.setName(rs.getString("Name"));
                store.setEmail(rs.getString("Email"));
                store.setPhone(rs.getString("Phone"));
                store.setAddress(rs.getString("Address"));
                storeList.add(store);
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

        return storeList;
    }

    public boolean create(Store store) throws NamingException {
        boolean isSuccess = true;

        try {
            // Get connection.
            connection = DatabaseConfig.getConnection();

            // Execute SQL and return data results.
            do {
                store.setId(this.generateStoreId());
                String SQL = "SELECT Id FROM dbo.Stores WHERE Id = ?";
                stmt = connection.prepareStatement(SQL);
                stmt.setString(1, store.getId());
                rs = stmt.executeQuery();
            } while (rs.next());

            String SQL = "INSERT INTO dbo.Stores\n"
                    + "(\n"
                    + "    Id,\n"
                    + "    UserId,\n"
                    + "    Name,\n"
                    + "    Email,\n"
                    + "    Phone,\n"
                    + "    Address,\n"
                    + "    isVerified\n"
                    + ")\n"
                    + "VALUES\n"
                    + "(   ?,    -- Id - nvarchar(32)\n"
                    + "    ?,    -- UserId - nvarchar(32)\n"
                    + "    ?,    -- Name - nvarchar(50)\n"
                    + "    ?,   -- Email - nvarchar(max)\n"
                    + "    ?,    -- Phone - nvarchar(20)\n"
                    + "    ?,    -- Address - nvarchar(100)\n"
                    + "    ? -- isVerified - bit\n"
                    + ")";
            stmt = connection.prepareStatement(SQL);
            stmt.setString(1, store.getId());
            stmt.setString(2, store.getUserId());
            stmt.setString(3, store.getName());
            stmt.setString(4, store.getEmail());
            stmt.setString(5, store.getPhone());
            stmt.setString(6, store.getAddress());
            stmt.setBoolean(7, store.isVerified());
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

    public boolean update(Store store) throws NamingException {
        boolean isSuccess = true;

        try {
            // Get connection.
            connection = DatabaseConfig.getConnection();

            // Execute SQL and return data results.
            String SQL = "UPDATE dbo.Stores "
                    + "SET "
                    + "    Name = ?, "
                    + "    Email = ?, "
                    + "    Phone = ?, "
                    + "    Address = ? "
                    + "WHERE "
                    + "    Id = ?";
            stmt = connection.prepareStatement(SQL);
            stmt.setString(1, store.getName());
            stmt.setString(2, store.getEmail());
            stmt.setString(3, store.getPhone());
            stmt.setString(4, store.getAddress());
            stmt.setString(5, store.getId());
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

    public boolean delete(String storeId) throws NamingException {
        boolean isSuccess = true;

        try {
            // Get connection.
            connection = DatabaseConfig.getConnection();

            // Execute SQL and return data results.
            String SQL = "DELETE FROM dbo.Stores WHERE Id = ?";
            stmt = connection.prepareStatement(SQL);
            stmt.setString(1, storeId);
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

    public boolean confirm(String storeId) throws NamingException {
        boolean isSuccess = true;

        try {
            // Get connection.
            connection = DatabaseConfig.getConnection();

            // Execute SQL and return data results.
            String SQL = "UPDATE dbo.Stores SET isVerified = ? WHERE Id = ?";
            stmt = connection.prepareStatement(SQL);
            stmt.setBoolean(1, true);
            stmt.setString(2, storeId);
            int numberOfAffectedRows = stmt.executeUpdate();

            if (numberOfAffectedRows != 0) {
                SQL = "INSERT INTO dbo.RoleUser\n"
                        + "(\n"
                        + "    RolesId,\n"
                        + "    UsersId\n"
                        + ")\n"
                        + "VALUES\n"
                        + "(   3,\n"
                        + "    (SELECT UserId FROM dbo.Stores WHERE Id = ?)\n"
                        + ")";
                stmt = connection.prepareStatement(SQL);
                stmt.setString(1, storeId);
                stmt.executeUpdate();
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

    private String generateStoreId() {
        UUID uuid = UUID.randomUUID();
        String guid = uuid.toString().replaceAll("-", "");

        return guid;
    }
}
