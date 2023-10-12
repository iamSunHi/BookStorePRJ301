/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import models.Book;
import models.Category;
import models.CoverType;

/**
 *
 * @author Nhật Huy
 */
public class BookDAO {

    private Connection connection = null;
    private PreparedStatement stmt = null;
    private ResultSet rs = null;

    public int getNumberOfPage(int pageSize) throws NamingException {
        int result = 0;

        try {
            // Get connection.
            connection = DatabaseConfig.getConnection();

            // Execute SQL and return data results.
            String SQL = "SELECT COUNT(Id)\n"
                    + "FROM dbo.Books";
            stmt = connection.prepareStatement(SQL);
            rs = stmt.executeQuery();

            if (rs.next()) {
                result = rs.getInt(1);
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

        if (result % pageSize != 0) {
            return result / pageSize + 1;
        }
        return result / pageSize;
    }

    public List<Book> getBookWithPagination(int pageSize, int offset) throws NamingException {
        List<Book> bookList = new ArrayList<>();

        try {
            // Get connection.
            connection = DatabaseConfig.getConnection();

            // Execute SQL and return data results.
            String SQL = "SELECT * FROM dbo.Books\n"
                    + "JOIN dbo.CoverTypes ON CoverTypes.Id = Books.CoverTypeId\n"
                    + "ORDER BY Books.Id\n"
                    + "OFFSET ? ROWS\n"
                    + "FETCH NEXT ? ROWS ONLY";
            stmt = connection.prepareStatement(SQL);
            stmt.setInt(1, offset);
            stmt.setInt(2, pageSize);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Book book = new Book();
                book.setId(rs.getInt("Id"));
                book.setTitle(rs.getString("Title"));
                book.setDescription(rs.getString("Description"));
                book.setCoverType(new CoverType(rs.getInt("CoverTypeId"), rs.getString("Name")));
                book.setAuthor(rs.getString("Author"));
                book.setPrice(rs.getDouble("Price"));
                book.setPublisher(rs.getString("Publisher"));
                book.setYearOfPublication(rs.getInt("YearOfPublication"));
                book.setImageUrl(rs.getString("ImageUrl"));

                book.setCategories(new ArrayList<>());

                bookList.add(book);
            }

            for (Book book : bookList) {

                // Add Category(s)
                SQL = "SELECT CategoriesId, Name\n"
                        + "FROM dbo.BookCategory JOIN dbo.Categories ON Categories.Id = BookCategory.CategoriesId\n"
                        + "WHERE BooksId = ?";
                stmt = connection.prepareStatement(SQL);
                stmt.setInt(1, book.getId());
                rs = stmt.executeQuery();

                while (rs.next()) {
                    Category category = new Category();
                    category.setId(rs.getInt("CategoriesId"));
                    category.setName(rs.getString("Name"));
                    book.getCategories().add(category);
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

        return bookList;
    }

    public List<Book> getAll() throws NamingException {
        List<Book> bookList = new ArrayList<>();

        try {
            // Get connection.
            connection = DatabaseConfig.getConnection();

            // Execute SQL and return data results.
            String SQL = "SELECT * FROM dbo.Books\n"
                    + "JOIN dbo.CoverTypes ON CoverTypes.Id = Books.CoverTypeId\n";
            stmt = connection.prepareStatement(SQL);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Book book = new Book();
                book.setId(rs.getInt("Id"));
                book.setTitle(rs.getString("Title"));
                book.setDescription(rs.getString("Description"));
                book.setCoverType(new CoverType(rs.getInt("CoverTypeId"), rs.getString("Name")));
                book.setAuthor(rs.getString("Author"));
                book.setPrice(rs.getDouble("Price"));
                book.setPublisher(rs.getString("Publisher"));
                book.setYearOfPublication(rs.getInt("YearOfPublication"));
                book.setImageUrl(rs.getString("ImageUrl"));

                book.setCategories(new ArrayList<>());

                bookList.add(book);
            }

            for (Book book : bookList) {

                // Add Category(s)
                SQL = "SELECT CategoriesId, Name\n"
                        + "FROM dbo.BookCategory JOIN dbo.Categories ON Categories.Id = BookCategory.CategoriesId\n"
                        + "WHERE BooksId = ?";
                stmt = connection.prepareStatement(SQL);
                stmt.setInt(1, book.getId());
                rs = stmt.executeQuery();

                while (rs.next()) {
                    Category category = new Category();
                    category.setId(rs.getInt("CategoriesId"));
                    category.setName(rs.getString("Name"));
                    book.getCategories().add(category);
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

        return bookList;
    }

    public List<Book> search(String searchContent) throws NamingException {
        List<Book> bookList = new ArrayList<>();

        try {
            // Get connection.
            connection = DatabaseConfig.getConnection();

            // Execute SQL and return data results.
            String SQL = "SELECT Id, Title FROM dbo.Books WHERE Title LIKE N'%" + searchContent + "%'";
            stmt = connection.prepareStatement(SQL);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Book book = new Book();
                book.setId(rs.getInt("Id"));
                book.setTitle(rs.getString("Title"));

                bookList.add(book);
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

        return bookList;
    }

    public Book getBookById(int bookId) throws NamingException {
        Book book = null;

        try {
            // Get connection.
            connection = DatabaseConfig.getConnection();

            // Execute SQL and return data results.
            String SQL = "SELECT *\n"
                    + "FROM dbo.Books\n"
                    + "JOIN dbo.CoverTypes ON CoverTypes.Id = Books.CoverTypeId\n"
                    + "WHERE Books.Id = ?";
            stmt = connection.prepareStatement(SQL);
            stmt.setInt(1, bookId);
            rs = stmt.executeQuery();

            if (rs.next()) {
                book = new Book();
                book.setId(bookId);
                book.setTitle(rs.getString("Title"));
                book.setDescription(rs.getString("Description"));
                book.setCoverType(new CoverType(rs.getInt("CoverTypeId"), rs.getString("Name")));
                book.setAuthor(rs.getString("Author"));
                book.setPrice(rs.getDouble("Price"));
                book.setPublisher(rs.getString("Publisher"));
                book.setYearOfPublication(rs.getInt("YearOfPublication"));
                book.setImageUrl(rs.getString("ImageUrl"));

                book.setCategories(new ArrayList<>());

                // Add Category(s)
                SQL = "SELECT CategoriesId, Name\n"
                        + "FROM dbo.BookCategory JOIN dbo.Categories ON Categories.Id = BookCategory.CategoriesId\n"
                        + "WHERE BooksId = ?";
                stmt = connection.prepareStatement(SQL);
                stmt.setInt(1, bookId);
                rs = stmt.executeQuery();

                while (rs.next()) {
                    Category category = new Category();
                    category.setId(rs.getInt("CategoriesId"));
                    category.setName(rs.getString("Name"));
                    book.getCategories().add(category);
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

        return book;
    }

    public List<Book> getBookByCategory(String categoryName) throws NamingException {
        List<Book> bookList = new ArrayList<>();

        try {
            // Get connection.
            connection = DatabaseConfig.getConnection();

            // Execute SQL and return data results.
            String SQL = "SELECT BooksId\n"
                    + "FROM dbo.Categories JOIN dbo.BookCategory ON BookCategory.CategoriesId = Categories.Id\n"
                    + "WHERE dbo.Categories.Name = ?";
            stmt = connection.prepareStatement(SQL);
            stmt.setString(1, categoryName.replaceAll("_", " "));
            rs = stmt.executeQuery();

            List<Integer> bookIdList = new ArrayList<>();

            while (rs.next()) {
                bookIdList.add(rs.getInt("BooksId"));
            }

            for (int i : bookIdList) {
                Book book = getBookById(i);
                bookList.add(book);
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

        return bookList;
    }

    public boolean create(Book book) throws NamingException {
        boolean isSuccess = true;

        try {
            // Get connection.
            connection = DatabaseConfig.getConnection();

            // Execute SQL and return data results.
            String SQL = "SELECT Id FROM dbo.Books\n"
                    + "WHERE Title = ? AND Author = ?";
            stmt = connection.prepareStatement(SQL);
            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getAuthor());
            rs = stmt.executeQuery();

            if (!rs.next()) {
                SQL = "INSERT INTO dbo.Books\n"
                        + "(\n"
                        + "    Title,\n"
                        + "    Description,\n"
                        + "    CoverTypeId,\n"
                        + "    Author,\n"
                        + "    Price,\n"
                        + "    Publisher,\n"
                        + "    YearOfPublication,\n"
                        + "    ImageUrl\n"
                        + ")\n"
                        + "VALUES\n"
                        + "(   ?,  -- Title - nvarchar(max)\n"
                        + "    ?, -- Description - nvarchar(max)\n"
                        + "    (SELECT Id FROM dbo.CoverTypes WHERE Name = ?),    -- CoverTypeId - int\n"
                        + "    ?,  -- Author - nvarchar(50)\n"
                        + "    ?,  -- Price - float\n"
                        + "    ?,  -- Publisher - nvarchar(max)\n"
                        + "    ?,    -- YearOfPublication - int\n"
                        + "    ?   -- ImageUrl - nvarchar(max)\n"
                        + ")";
                stmt = connection.prepareStatement(SQL);
                stmt.setString(1, book.getTitle());
                stmt.setString(2, book.getDescription());
                stmt.setString(3, book.getCoverType().getName());
                stmt.setString(4, book.getAuthor());
                stmt.setDouble(5, book.getPrice());
                stmt.setString(6, book.getPublisher());
                stmt.setInt(7, book.getYearOfPublication());
                stmt.setString(8, "default.jfif");

                if (stmt.executeUpdate() != 0) {
                    for (Category category : book.getCategories()) {
                        SQL = "INSERT INTO dbo.BookCategory\n"
                                + "(\n"
                                + "    BooksId,\n"
                                + "    CategoriesId\n"
                                + ")\n"
                                + "VALUES\n"
                                + "(   (SELECT Id FROM dbo.Books WHERE Title = ? AND Author = ?), -- BooksId - int\n"
                                + "    (SELECT Id FROM dbo.Categories WHERE Name =?)  -- CategoriesId - int\n"
                                + ")";
                        stmt = connection.prepareStatement(SQL);
                        stmt.setString(1, book.getTitle());
                        stmt.setString(2, book.getAuthor());
                        stmt.setString(3, category.getName());
                        if (stmt.executeUpdate() == 0) {
                            isSuccess = false;
                        }
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

    public boolean update(Book book) throws NamingException {
        boolean isSuccess = true;

        try {
            // Get connection.
            connection = DatabaseConfig.getConnection();

            // Execute SQL and return data results.
            String SQL = "SELECT Title FROM dbo.Books WHERE Id = ?";
            stmt = connection.prepareStatement(SQL);
            stmt.setInt(1, book.getId());
            rs = stmt.executeQuery();

            if (rs.next()) {
                SQL = "UPDATE dbo.Books SET\n"
                        + "Title = ?,\n"
                        + "Description = ?,\n"
                        + "CoverTypeId = (SELECT Id FROM dbo.CoverTypes WHERE Name = ?),\n"
                        + "Author = ?,\n"
                        + "Price = ?,\n"
                        + "Publisher = ?,\n"
                        + "YearOfPublication = ?\n"
                        + "WHERE Id = ?";
                stmt = connection.prepareStatement(SQL);
                stmt.setString(1, book.getTitle());
                stmt.setString(2, book.getDescription());
                stmt.setString(3, book.getCoverType().getName());
                stmt.setString(4, book.getAuthor());
                stmt.setDouble(5, book.getPrice());
                stmt.setString(6, book.getPublisher());
                stmt.setInt(7, book.getYearOfPublication());
                stmt.setInt(8, book.getId());

                if (stmt.executeUpdate() != 0) {
                    SQL = "DELETE FROM dbo.BookCategory\n"
                            + "WHERE BooksId = ?";
                    stmt = connection.prepareStatement(SQL);
                    stmt.setInt(1, book.getId());
                    stmt.executeUpdate();

                    for (Category category : book.getCategories()) {
                        SQL = "INSERT INTO dbo.BookCategory\n"
                                + "(\n"
                                + "    BooksId,\n"
                                + "    CategoriesId\n"
                                + ")\n"
                                + "VALUES\n"
                                + "(   ?, -- BooksId - int\n"
                                + "    (SELECT Id FROM dbo.Categories WHERE Name = ?)  -- CategoriesId - int\n"
                                + ")";
                        stmt = connection.prepareStatement(SQL);
                        stmt.setInt(1, book.getId());
                        stmt.setString(2, category.getName());
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

    public void saveImageUrl(String image, int bookId) throws NamingException {
        try {
            // Get connection.
            connection = DatabaseConfig.getConnection();

            // Execute SQL and return data results.
            String SQL = "UPDATE dbo.Books SET ImageUrl = ? WHERE Id = ?";
            stmt = connection.prepareStatement(SQL);
            stmt.setString(1, image);
            stmt.setInt(2, bookId);
            stmt.executeUpdate();
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

    public boolean delete(int bookId) throws NamingException {
        boolean isSuccess = true;

        try {
            // Get connection.
            connection = DatabaseConfig.getConnection();

            // Execute SQL and return data results.
            String SQL = "SELECT Title FROM dbo.Books WHERE Id = ?";
            stmt = connection.prepareStatement(SQL);
            stmt.setInt(1, bookId);
            rs = stmt.executeQuery();

            if (rs.next()) {
                SQL = "DELETE FROM dbo.Books WHERE Id = ?";
                stmt = connection.prepareStatement(SQL);
                stmt.setInt(1, bookId);
                if (stmt.executeUpdate() == 0) {
                    isSuccess = false;
                }
            } else {
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
