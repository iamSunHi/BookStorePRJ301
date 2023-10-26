/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import dal.CartDAO;
import dal.StoreDAO;
import dal.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import models.Cart;
import models.Role;
import models.User;

/**
 *
 * @author Nhật Huy
 */
public class IdentityController extends HttpServlet {

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
            out.println("<title>Servlet IdentityController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet IdentityController at " + request.getContextPath() + "</h1>");
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
        if (request.getParameter("type").equals("LOGOUT")) {
            session.removeAttribute("user");
            session.removeAttribute("userCart");
            session.removeAttribute("isAdmin");
            session.removeAttribute("isSeller");
        }
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
        User user = new User();
        UserDAO userDAO = new UserDAO();
        HttpSession session = request.getSession();
        boolean isAdmin = false;
        boolean isSeller = false;

        switch (request.getParameter("type")) {
            case "REGISTER": {
                user.setUsername(request.getParameter("username"));
                user.setName(request.getParameter("fullname"));
                user.setEmail(request.getParameter("email"));
                user.setPhone(request.getParameter("phone"));
                user.setPassword(request.getParameter("password"));
                user.setImageUrl("profile-no-image.jfif");
                try {
                    if (userDAO.register(user)) {
                        user = userDAO.login(request.getParameter("username"), request.getParameter("password"));
                    } else {
                        request.setAttribute("error", "Username '" + request.getParameter("username") + "' is existed, please choose another!");
                        request.getRequestDispatcher("register.jsp").forward(request, response);
                        return;
                    }
                } catch (NamingException ex) {
                    Logger.getLogger(IdentityController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            }
            case "LOGIN": {
                try {
                    user = userDAO.login(request.getParameter("username"), request.getParameter("password"));
                } catch (NamingException ex) {
                    Logger.getLogger(IdentityController.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (user != null) {
                    for (Role role : user.getRoleList()) {
                        if (role.getName().toLowerCase().equals("admin")) {
                            isAdmin = true;
                        } else if (role.getName().toLowerCase().equals("seller")) {
                            isSeller = true;
                            StoreDAO storeDAO = new StoreDAO();
                            try {
                                session.setAttribute("storeId", storeDAO.getStoreId(user.getId()));
                            } catch (NamingException ex) {
                                Logger.getLogger(IdentityController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                    request.removeAttribute("error");
                } else {
                    request.setAttribute("error", "Username or password is wrong!");
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                    return;
                }
                break;
            }
        }

        CartDAO cartDAO = new CartDAO();

        try {
            Cart userCart = cartDAO.get(user.getId());
            session.setAttribute("userCart", userCart);
        } catch (NamingException ex) {
            Logger.getLogger(IdentityController.class.getName()).log(Level.SEVERE, null, ex);
        }

        session.setAttribute("user", user);
        session.setAttribute("isAdmin", isAdmin);
        session.setAttribute("isSeller", isSeller);

        response.sendRedirect("home.jsp");
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
