/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import dal.CartDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import models.Cart;
import models.User;

/**
 *
 * @author Nhật Huy
 */
public class CartController extends HttpServlet {

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
            out.println("<title>Servlet CartController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CartController at " + request.getContextPath() + "</h1>");
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

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String method = request.getParameter("method");
        CartDAO cartDAO = new CartDAO();
        Cart userCart = (Cart) session.getAttribute("userCart");

        if (method != null) {
            switch (method) {
                case "add": {
                    try {
                        if (cartDAO.addBook(userCart.getId(), Integer.parseInt(request.getParameter("bookId")))) {
                            request.removeAttribute("error");
                            String msg = "Add book successful!";
                            request.setAttribute("success", msg);
                        } else {
                            request.removeAttribute("success");
                            String msg = "The book has been existed in your cart!";
                            request.setAttribute("error", msg);
                        }
                    } catch (NamingException ex) {
                        Logger.getLogger(CartController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    try {
                        userCart = cartDAO.get(user.getId());
                        session.setAttribute("userCart", userCart);
                    } catch (NamingException ex) {
                        Logger.getLogger(IdentityController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    request.getRequestDispatcher("home.jsp").forward(request, response);
                }
                break;
                case "remove": {
                    try {
                        if (cartDAO.removeBook(userCart.getId(), Integer.parseInt(request.getParameter("bookId")))) {
                            request.removeAttribute("error");
                            String msg = "Remove book successful!";
                            request.setAttribute("success", msg);
                        } else {
                            request.removeAttribute("success");
                            String msg = "This book is not existed in your cart!";
                            request.setAttribute("error", msg);
                        }
                    } catch (NamingException ex) {
                        Logger.getLogger(CartController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    try {
                        userCart = cartDAO.get(user.getId());
                        session.setAttribute("userCart", userCart);
                    } catch (NamingException ex) {
                        Logger.getLogger(IdentityController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    request.getRequestDispatcher("home.jsp").forward(request, response);
                }
                break;
            }
        }
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
        processRequest(request, response);
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
