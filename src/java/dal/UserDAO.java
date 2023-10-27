/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;
import javax.naming.NamingException;
import models.Role;
import models.User;

/**
 *
 * @author Nhật Huy
 */
public class UserDAO {

    private Connection connection = null;
    private Statement stmt = null;
    private ResultSet rs = null;

    public User get(String userId) throws NamingException {
        User user = new User();
        boolean isSuccess = true;

        try {
            // Get connection.
            connection = DatabaseConfig.getConnection();

            // Execute SQL and return data results.
            String SQL = "SELECT * FROM dbo.Users WHERE Id = '" + userId + "'";
            stmt = connection.createStatement();
            rs = stmt.executeQuery(SQL);

            if (rs.next()) {
                user.setId(userId);
                user.setUsername(rs.getString("UserName"));
                user.setName(rs.getString("Name"));
                user.setPhone(rs.getString("Phone"));
                user.setAddress(rs.getString("Address"));
                user.setEmail(rs.getString("Email"));
                user.setImageUrl(rs.getString("ImageUrl"));

                SQL = "SELECT Name FROM dbo.Roles JOIN dbo.RoleUser ON RoleUser.RolesId = Roles.Id WHERE UsersId = '" + userId + "'";
                rs = stmt.executeQuery(SQL);

                user.setRoleList(new ArrayList<>());
                while (rs.next()) {
                    Role role = new Role();
                    role.setName(rs.getString("Name"));
                    user.getRoleList().add(role);
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

        if (!isSuccess) {
            return null;
        }
        return user;
    }

    public List<User> getAll() throws NamingException {
        List<User> userList = new ArrayList<>();

        try {
            // Get connection.
            connection = DatabaseConfig.getConnection();

            // Execute SQL and return data results.
            String SQL = "SELECT Id, UserName, Name, Email, Phone FROM dbo.Users";
            stmt = connection.createStatement();
            rs = stmt.executeQuery(SQL);

            while (rs.next()) {
                User user = new User();
                user.setId(rs.getString("Id"));
                user.setUsername(rs.getString("UserName"));
                user.setName(rs.getString("Name"));
                user.setEmail(rs.getString("Email"));
                user.setPhone(rs.getString("Phone"));

                SQL = "SELECT Name FROM dbo.Roles JOIN dbo.RoleUser ON RoleUser.RolesId = Roles.Id WHERE UsersId = '" + user.getId() + "'";
                try ( Statement stmtTemp = connection.createStatement();  ResultSet rsTemp = stmtTemp.executeQuery(SQL)) {
                    user.setRoleList(new ArrayList<>());
                    while (rsTemp.next()) {
                        Role role = new Role();
                        role.setName(rsTemp.getString("Name"));
                        user.getRoleList().add(role);
                    }
                }
                userList.add(user);
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

        return userList;
    }

    public String getUserId(String username, String password) throws NamingException {
        String result = "";

        try {
            // Get connection.
            connection = DatabaseConfig.getConnection();

            // Execute SQL and return data results.
            String SQL = "SELECT Id FROM dbo.Users WHERE UserName = '" + username + "' AND Password = '" + hashPassword(password) + "'";
            stmt = connection.createStatement();
            rs = stmt.executeQuery(SQL);

            if (rs.next()) {
                result = rs.getString("Id");
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
        return result;
    }

    public boolean updateRole(User user) throws NamingException {
        boolean isSuccess = true;
        try {
            // Get connection.
            connection = DatabaseConfig.getConnection();

            // Execute SQL and return data results.
            String SQL = "DELETE FROM dbo.RoleUser WHERE UsersId = '" + user.getId() + "'";
            stmt = connection.createStatement();

            stmt.executeUpdate(SQL);
            if (!user.getRoleList().isEmpty()) {
                for (Role role : user.getRoleList()) {
                    SQL = "INSERT INTO dbo.RoleUser\n"
                            + "(\n"
                            + "    RolesId,\n"
                            + "    UsersId\n"
                            + ")\n"
                            + "VALUES\n"
                            + "(   (SELECT Id FROM dbo.Roles WHERE Name = '" + role.getName() + "'),  -- RolesId - int\n"
                            + "    N'" + user.getId() + "' -- UsersId - nvarchar(32)\n"
                            + ")";
                    stmt.executeUpdate(SQL);
                }
            } else {
                SQL = "INSERT INTO dbo.RoleUser\n"
                        + "(\n"
                        + "    RolesId,\n"
                        + "    UsersId\n"
                        + ")\n"
                        + "VALUES\n"
                        + "(   2,  -- RolesId - int\n"
                        + "    N'" + user.getId() + "' -- UsersId - nvarchar(32)\n"
                        + ")";
                stmt.executeUpdate(SQL);
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

    public boolean updateInfo(User user, String role) throws NamingException {
        boolean isSuccess = true;
        try {
            // Get connection.
            connection = DatabaseConfig.getConnection();

            // Execute SQL and return data results.
            String SQL = "UPDATE dbo.Users SET\n"
                    + "	UserName = N'" + user.getUsername() + "',\n"
                    + "	Name = N'" + user.getName() + "',\n"
                    + "	Email = '" + user.getEmail() + "',\n"
                    + "	Phone = '" + user.getPhone() + "',\n"
                    + "	Address = N'" + user.getAddress() + "'\n"
                    + "WHERE Id = '" + user.getId() + "'";
            stmt = connection.createStatement();
            rs = stmt.executeQuery(SQL);

            if (rs.next()) {
                if (role != null && !role.isEmpty()) {
                    SQL = "SELECT Id FROM dbo.RoleUser JOIN dbo.Roles ON Roles.Id = RoleUser.RolesId\n"
                            + "WHERE UsersId = '" + user.getId() + "' AND Name = '" + role + "'";
                    rs = stmt.executeQuery(SQL);

                    if (!rs.next()) {
                        SQL = "INSERT INTO dbo.RoleUser\n"
                                + "(\n"
                                + "    RolesId,\n"
                                + "    UsersId\n"
                                + ")\n"
                                + "VALUES\n"
                                + "(   (SELECT Id FROM dbo.Roles WHERE Name = '" + role + "'),\n"
                                + "    N'" + user.getId() + "'\n"
                                + ")";
                        rs = stmt.executeQuery(SQL);
                    }
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

    public boolean updateLoginInfo(String oldPassword, User user) throws NamingException {
        boolean isSuccess = true;
        try {
            // Get connection.
            connection = DatabaseConfig.getConnection();

            // Execute SQL and return data results.
            String SQL = "SELECT Id FROM dbo.Users WHERE Password = '" + hashPassword(oldPassword) + "'";
            stmt = connection.createStatement();
            rs = stmt.executeQuery(SQL);
            if (!rs.next()) {
                isSuccess = false;
            } else {
                SQL = "UPDATE dbo.Users SET\n"
                        + "Password = '" + hashPassword(user.getPassword()) + "'\n"
                        + "WHERE Id = '" + user.getId() + "'";
                stmt = connection.createStatement();
                rs = stmt.executeQuery(SQL);

                if (!rs.next()) {
                    isSuccess = false;
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

        return isSuccess;
    }

    public String delete(String userId) throws NamingException {
        String imageUrl = "";
        try {
            // Get connection.
            connection = DatabaseConfig.getConnection();

            // Execute SQL and return data results.
            stmt = connection.createStatement();
            String SQL = "DELETE FROM dbo.Carts WHERE UserId = '" + userId + "'";
            stmt.executeUpdate(SQL);
            SQL = "SELECT ImageUrl FROM dbo.Users WHERE Id = '" + userId + "'";
            rs = stmt.executeQuery(SQL);
            if (rs.next()) {
                imageUrl = rs.getString("ImageUrl");
            }

            SQL = "DELETE FROM dbo.Stores WHERE UserId = '" + userId + "'";
            stmt.executeUpdate(SQL);
            SQL = "DELETE FROM dbo.Users WHERE Id = '" + userId + "'";
            int numberOfAffectedRows = stmt.executeUpdate(SQL);
            if (numberOfAffectedRows == 0) {
                imageUrl = "";
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

        return imageUrl;
    }

    public User login(String username, String password) throws NamingException {
        User currentUser = new User();
        boolean isSuccess = true;

        try {
            // Get connection.
            connection = DatabaseConfig.getConnection();

            // Execute SQL and return data results.
            String SQL = "SELECT * FROM dbo.Users WHERE UserName = '" + username + "' AND Password = '" + hashPassword(password) + "'";
            stmt = connection.createStatement();
            rs = stmt.executeQuery(SQL);

            if (rs.next()) {
                currentUser.setId(rs.getString("Id"));
                currentUser.setUsername(rs.getString("UserName"));
                currentUser.setName(rs.getString("Name"));
                currentUser.setPhone(rs.getString("Phone"));
                currentUser.setAddress(rs.getString("Address"));
                currentUser.setEmail(rs.getString("Email"));
                currentUser.setImageUrl(rs.getString("ImageUrl"));

                SQL = "SELECT Name FROM dbo.Roles JOIN dbo.RoleUser ON RoleUser.RolesId = Roles.Id WHERE UsersId = '" + currentUser.getId() + "'";
                rs = stmt.executeQuery(SQL);

                currentUser.setRoleList(new ArrayList<>());
                while (rs.next()) {
                    Role role = new Role();
                    role.setName(rs.getString("Name"));
                    currentUser.getRoleList().add(role);
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

//            if (connection != null) {
//                try {
//                    connection.close();
//                } catch (SQLException ex) {
//                }
//            }
        }

        if (!isSuccess) {
            return null;
        }
        return currentUser;
    }

    public boolean register(User user) throws NamingException {
        boolean isSuccess = true;

        try {
            // Get connection.
            connection = DatabaseConfig.getConnection();
            stmt = connection.createStatement();
            do {
                user.setId(this.generateUserId());
                String SQL = "SELECT Id FROM dbo.Users WHERE Id = '" + user.getId() + "'";
                rs = stmt.executeQuery(SQL);
            } while (rs.next());

            // Execute SQL and return data results.
            String SQL = "SELECT Id FROM dbo.Users WHERE UserName = N'" + user.getUsername() + "'";
            rs = stmt.executeQuery(SQL);

            if (!rs.next()) {
                SQL = "INSERT INTO dbo.Users\n"
                        + "(\n"
                        + "    Id,\n"
                        + "    UserName,\n"
                        + "    Password,\n"
                        + "    Name,\n"
                        + "    Email,\n"
                        + "    Phone,\n"
                        + "    Address,\n"
                        + "    ImageUrl\n"
                        + ")\n"
                        + "VALUES\n"
                        + "(   N'" + user.getId() + "',\n"
                        + "    N'" + user.getUsername() + "',\n"
                        + "    N'" + hashPassword(user.getPassword()) + "',\n"
                        + "    N'" + user.getName() + "',\n"
                        + "    N'" + user.getEmail() + "',\n"
                        + "    N'" + user.getPhone() + "',\n"
                        + "    N'" + user.getAddress() + "',\n"
                        + "    '" + user.getImageUrl() + "'\n"
                        + ")\n"
                        + "INSERT INTO dbo.RoleUser\n"
                        + "(\n"
                        + "    RolesId,\n"
                        + "    UsersId\n"
                        + ")\n"
                        + "VALUES\n"
                        + "(   2,\n"
                        + "    N'" + user.getId() + "'\n"
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

    public void saveImageUrl(String image, String userId) throws NamingException {
        try {
            // Get connection.
            connection = DatabaseConfig.getConnection();
            stmt = connection.createStatement();

            // Execute SQL and return data results.
            String SQL = "UPDATE dbo.Users SET ImageUrl = '" + image + "' WHERE Id = '" + userId + "'";
            rs = stmt.executeQuery(SQL);
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
    }

    private String generateUserId() {
        UUID uuid = UUID.randomUUID();
        String guid = uuid.toString().replaceAll("-", "");

        return guid;
    }

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            return password;
        }
    }
}
