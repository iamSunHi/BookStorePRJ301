/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import dal.BookDAO;
import dal.CategoryDAO;
import dal.CoverTypeDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import models.Book;
import models.Category;
import models.CoverType;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Nhật Huy
 */
@WebServlet(name = "BookController", urlPatterns = {"/book"})
public class BookController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet BookController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet BookController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (request.getParameter("method") != null) {
            switch (request.getParameter("method")) {
                case "get": {
                    try {
                        this.getBookDetail(request, response);
                    } catch (NamingException ex) {
                        Logger.getLogger(BookController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    return;
                }
                case "search": {
                    BookDAO bookDAO = new BookDAO();
                    List<Book> bookList = null;
                    try {
                        bookList = bookDAO.search(request.getParameter("content"));
                    } catch (NamingException ex) {
                        Logger.getLogger(BookController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    JSONArray searchResult = new JSONArray();
                    for (Book book : bookList) {
                        JSONObject data = new JSONObject();
                        data.put("id", book.getId());
                        data.put("title", book.getTitle());
                        searchResult.put(data);
                    }
                    JSONObject data = new JSONObject();
                    data.put("searchResult", searchResult);

                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().write(searchResult.toString());

                    return;
                }
                case "pagination": {
                    int pageSize = 8;
                    int offset = (Integer.parseInt(request.getParameter("page")) - 1) * pageSize;

                    BookDAO bookDAO = new BookDAO();
                    List<Book> bookList = null;
                    try {
                        bookList = bookDAO.getBookWithPagination(pageSize, offset);
                    } catch (NamingException ex) {
                        Logger.getLogger(BookController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    JSONArray pagination = new JSONArray();
                    for (Book book : bookList) {
                        JSONObject data = new JSONObject();
                        data.put("id", book.getId());
                        data.put("title", book.getTitle());
                        data.put("author", book.getAuthor());
                        data.put("price", book.getPrice());
                        data.put("imageUrl", book.getImageUrl());
                        pagination.put(data);
                    }
                    JSONObject data = new JSONObject();
                    data.put("pagination", pagination);

                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().write(pagination.toString());

                    return;
                }
                case "category": {
                    try {
                        this.getBookByCategory(request, response);
                    } catch (NamingException ex) {
                        Logger.getLogger(BookController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    return;
                }
                case "seller": {
                    BookDAO bookDAO = new BookDAO();
                    {
                        try {
                            List<Book> bookList = bookDAO.getAll();
                            request.setAttribute("bookList", bookList);
                        } catch (NamingException ex) {
                            Logger.getLogger(BookController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    request.getRequestDispatcher("seller/booklist.jsp").forward(request, response);
                    return;
                }
                case "delete": {
                    BookDAO bookDAO = new BookDAO();
                    try {
                        Book book = bookDAO.getBookById(Integer.parseInt(request.getParameter("bookId")));
                        if (!book.getImageUrl().equals("default.jfif")) {
                            File oldImage = new File(getServletContext().getRealPath("") + File.separator + "assets\\img\\books" + File.separator + book.getImageUrl());
                            if (oldImage.exists()) {
                                oldImage.delete();
                            }
                        }
                        if (bookDAO.delete(Integer.parseInt(request.getParameter("bookId")))) {
                            request.setAttribute("error", null);
                            String msg = "Delete successful!";
                            request.setAttribute("success", msg);
                        } else {
                            request.setAttribute("success", null);
                            String msg = "Delete failed!";
                            request.setAttribute("error", msg);
                        }
                    } catch (NamingException ex) {
                        Logger.getLogger(BookController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    try {
                        List<Book> bookList = bookDAO.getAll();
                        request.setAttribute("bookList", bookList);
                    } catch (NamingException ex) {
                        Logger.getLogger(BookController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    request.getRequestDispatcher("seller/booklist.jsp").forward(request, response);
                    return;
                }
            }
        }

        BookDAO bookDAO = new BookDAO();
        List<Book> bookList = null;

        int pageSize = 8;
        int offset = (Integer.parseInt(request.getParameter("page")) - 1) * pageSize;

        try {
            bookList = bookDAO.getBookWithPagination(pageSize, offset);
        } catch (NamingException ex) {
            Logger.getLogger(BookController.class.getName()).log(Level.SEVERE, null, ex);
        }

        HttpSession session = request.getSession();

        if (session.getAttribute("numberOfPage") == null) {
            int numberOfPage = 0;
            try {
                numberOfPage = bookDAO.getNumberOfPage(pageSize);
            } catch (NamingException ex) {
                Logger.getLogger(BookController.class.getName()).log(Level.SEVERE, null, ex);
            }
            session.setAttribute("numberOfPage", numberOfPage);
        }

        // Get categories for navbar
        if (session.getAttribute("Categories") == null) {
            CategoryDAO categoryDAO = new CategoryDAO();
            try {
                session.setAttribute("Categories", categoryDAO.getAll());
            } catch (NamingException ex) {
                Logger.getLogger(BookController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        // Get cover type for navbar
        if (session.getAttribute("CoverTypes") == null) {
            CoverTypeDAO coverTypeDAO = new CoverTypeDAO();
            try {
                session.setAttribute("CoverTypes", coverTypeDAO.getAll());
            } catch (NamingException ex) {
                Logger.getLogger(BookController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        session.setAttribute("bookList", bookList);
        session.setAttribute("currentPage", Integer.parseInt(request.getParameter("page")));
        response.sendRedirect("home.jsp");
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String categoryList[] = request.getParameterValues("category");
        String author = request.getParameter("author");
        String publisher = request.getParameter("publisher");
        String covertype = request.getParameter("covertype");
        double price = Double.parseDouble(request.getParameter("price"));
        int yearOfPublication = Integer.parseInt(request.getParameter("publicationDate"));

        Book book = new Book();
        if (request.getParameter("id") != null && !request.getParameter("id").equals("")) {
            int id = Integer.parseInt(request.getParameter("id"));
            book.setId(id);
        }
        book.setTitle(title);
        book.setDescription(description);
        book.setCategories(new ArrayList<>());
        for (String categoryName : categoryList) {
            Category category = new Category();
            category.setName(categoryName);
            book.getCategories().add(category);
        }
        book.setAuthor(author);
        book.setPublisher(publisher);
        book.setCoverType(new CoverType(covertype));
        book.setPrice(price);
        book.setYearOfPublication(yearOfPublication);

        BookDAO bookDAO = new BookDAO();

        String method = request.getParameter("method");
        switch (method) {
            case "add": {
                try {
                    if (bookDAO.create(book)) {
                        request.setAttribute("error", null);
                        String msg = "Add successful! Please add book's image later!";
                        request.setAttribute("success", msg);
                    } else {
                        request.setAttribute("success", null);
                        String msg = "Add failed!";
                        request.setAttribute("error", msg);
                    }
                } catch (NamingException ex) {
                    Logger.getLogger(BookController.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    List<Book> bookList = bookDAO.getAll();
                    request.setAttribute("bookList", bookList);
                } catch (NamingException ex) {
                    Logger.getLogger(BookController.class.getName()).log(Level.SEVERE, null, ex);
                }
                request.getRequestDispatcher("seller/booklist.jsp").forward(request, response);
                return;
            }
            case "update": {
                try {
                    if (bookDAO.update(book)) {
                        request.setAttribute("error", null);
                        String msg = "Update successful!";
                        request.setAttribute("success", msg);
                    } else {
                        request.setAttribute("success", null);
                        String msg = "Update failed!";
                        request.setAttribute("error", msg);
                    }
                } catch (NamingException ex) {
                    Logger.getLogger(BookController.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    List<Book> bookList = bookDAO.getAll();
                    request.setAttribute("bookList", bookList);
                } catch (NamingException ex) {
                    Logger.getLogger(BookController.class.getName()).log(Level.SEVERE, null, ex);
                }
                request.getRequestDispatcher("seller/booklist.jsp").forward(request, response);
            }

        }
    }

    public void getBookDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, NamingException {
        BookDAO bookDAO = new BookDAO();

        Book book = null;
        if (request.getParameter("bookId") != null) {
            book = bookDAO.getBookById(Integer.parseInt(request.getParameter("bookId")));
        }
        request.setAttribute("Book", book);

        if (request.getParameter("role") != null && request.getParameter("role").equals("seller")) {
            request.getRequestDispatcher("seller/bookupsert.jsp").forward(request, response);
            return;
        }
        request.getRequestDispatcher("bookdetail.jsp").forward(request, response);
    }

    public void getBookByCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, NamingException {
        BookDAO bookDAO = new BookDAO();
        List<Book> bookList = bookDAO.getBookByCategory(request.getParameter("category"));

        request.setAttribute("bookListByCategory", bookList);
        request.getRequestDispatcher("bookbycategory.jsp").forward(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
