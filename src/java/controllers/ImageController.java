/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import dal.BookDAO;
import dal.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import models.Book;
import models.User;

/**
 *
 * @author Nhật Huy
 */
@MultipartConfig
public class ImageController extends HttpServlet {

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
            out.println("<title>Servlet AvatarController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AvatarController at " + request.getContextPath() + "</h1>");
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
        processRequest(request, response);
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

        String forEntity = request.getParameter("for");
        switch (forEntity) {
            case "user": {
                UserDAO userDAO = new UserDAO();
                User user = (User) request.getSession().getAttribute("user");
                String uploadPath = getServletContext().getRealPath("");

                Part filePart = request.getPart("image");
                String fileName = generateImageName(filePart.getSubmittedFileName());
                Path filePath = Paths.get(uploadPath, "assets\\img\\user", fileName);

                try ( InputStream fileContent = filePart.getInputStream()) {
                    // save new image
                    Files.copy(fileContent, filePath);
                    // delete old image
                    if (!user.getImageUrl().equals("profile-no-image.jfif")) {
                        File oldImage = new File(uploadPath + File.separator + "assets\\img\\user" + File.separator + user.getImageUrl());
                        if (oldImage.exists()) {
                            oldImage.delete();
                        }
                    }
                    userDAO.saveImageUrl(fileName, user.getId());
                    user.setImageUrl(fileName);

                    request.getSession().setAttribute("user", user);
                } catch (Exception e) {
                }
                response.sendRedirect("account.jsp");
                return;
            }
            case "book": {
                BookDAO bookDAO = new BookDAO();
                Book book = null;
                try {
                    book = bookDAO.getBookById(Integer.parseInt(request.getParameter("id")));
                } catch (NamingException ex) {
                    Logger.getLogger(ImageController.class.getName()).log(Level.SEVERE, null, ex);
                }

                String uploadPath = getServletContext().getRealPath("");

                Part filePart = request.getPart("image");
                String fileName = generateImageName(filePart.getSubmittedFileName());
                Path filePath = Paths.get(uploadPath, "assets\\img\\books", fileName);

                try ( InputStream fileContent = filePart.getInputStream()) {
                    // save new image
                    Files.copy(fileContent, filePath);
                    // delete old image
                    if (!book.getImageUrl().equals("default.jfif")) {
                        File oldImage = new File(uploadPath + File.separator + "assets\\img\\books" + File.separator + book.getImageUrl());
                        if (oldImage.exists()) {
                            oldImage.delete();
                        }
                    }
                    bookDAO.saveImageUrl(fileName, book.getId());
                } catch (Exception e) {
                }
                response.sendRedirect("book?role=seller&method=get&bookId=" + book.getId());
            }
        }

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

    private String generateImageName(String fileName) {
        UUID uuid = UUID.randomUUID();
        String guid = uuid.toString().replaceAll("-", "");

        int i = fileName.lastIndexOf('.');
        if (i > 0) {
            guid += fileName.substring(i);
        }
        return guid;
    }
}
