/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import dal.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
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
import models.Role;
import models.User;

/**
 *
 * @author Nhật Huy
 */
public class UserController extends HttpServlet {

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
            out.println("<title>Servlet UserController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UserController at " + request.getContextPath() + "</h1>");
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
        String method = request.getParameter("method");
        switch (method) {
            case "getall": {
                UserDAO userDAO = new UserDAO();
                List<User> userList = null;
                try {
                    userList = userDAO.getAll();
                } catch (NamingException ex) {
                    Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
                }
                request.setAttribute("UserList", userList);
                request.getRequestDispatcher("admin/user.jsp").forward(request, response);
                return;
            }
            default:
                throw new AssertionError();
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
        HttpSession session = request.getSession();
        UserDAO userDAO = new UserDAO();
        boolean isSuccess = false;
        User user = new User();
        user.setId(request.getParameter("id"));

        String method = request.getParameter("method");
        switch (method) {
            case "info":
                user.setUsername(request.getParameter("username"));
                user.setName(request.getParameter("fullname"));
                user.setEmail(request.getParameter("email"));
                user.setPhone(request.getParameter("phonenumber"));
                user.setAddress(request.getParameter("address"));
                try {
                    isSuccess = userDAO.updateInfo(user, request.getParameter("role"));
                } catch (NamingException ex) {
                    Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (isSuccess) {
                    User currentUser = (User) session.getAttribute("user");
                    currentUser.setUsername(user.getUsername());
                    currentUser.setName(user.getName());
                    currentUser.setEmail(user.getEmail());
                    currentUser.setPhone(user.getPhone());
                    currentUser.setAddress(user.getAddress());
                    session.setAttribute("user", currentUser);
                }
                break;

            case "login":
                user.setPassword(request.getParameter("newpassword"));
                try {
                    isSuccess = userDAO.updateLoginInfo(request.getParameter("oldpassword"), user);
                } catch (NamingException ex) {
                    Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;

            case "setting":
                // not supported yet
                break;

            case "updaterole": {
                String roleList[] = request.getParameterValues("roles");
                user.setRoleList(new ArrayList<>());
                if (roleList != null && roleList.length > 0) {
                    for (String roleName : roleList) {
                        Role role = new Role();
                        role.setName(roleName);
                        user.getRoleList().add(role);
                    }
                }

                try {
                    if (userDAO.updateRole(user)) {
                        request.setAttribute("error", null);
                        String msg = "Update successful!";
                        request.setAttribute("success", msg);
                    } else {
                        request.setAttribute("success", null);
                        String msg = "Update failed!";
                        request.setAttribute("error", msg);
                    }
                } catch (NamingException ex) {
                    Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
                }

                List<User> userList = null;
                try {
                    userList = userDAO.getAll();
                } catch (NamingException ex) {
                    Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
                }
                request.setAttribute("UserList", userList);
                request.getRequestDispatcher("admin/user.jsp").forward(request, response);
                return;
            }

            case "delete": {
                try {
                    String oldImageUrl = userDAO.delete(user.getId());
                    if (!oldImageUrl.equals("")) {
                        if (!oldImageUrl.equals("profile-no-image.jfif")) {
                            File oldImage = new File(getServletContext().getRealPath("") + File.separator + "assets\\img\\user" + File.separator + oldImageUrl);
                            if (oldImage.exists()) {
                                oldImage.delete();
                            }
                        }
                        request.removeAttribute("error");
                        String msg = "Delete user successful!";
                        request.setAttribute("success", msg);
                    } else {
                        request.removeAttribute("success");
                        String msg = "Delete failed!";
                        request.setAttribute("error", msg);
                    }
                } catch (NamingException ex) {
                    Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
                }

                List<User> userList = null;
                try {
                    userList = userDAO.getAll();
                } catch (NamingException ex) {
                    Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
                }
                request.setAttribute("UserList", userList);
                request.getRequestDispatcher("admin/user.jsp").forward(request, response);
                return;
            }
        }

        if (isSuccess) {
            request.setAttribute("error", null);
            String msg = "Update successful!";
            request.setAttribute("success", msg);
        } else {
            request.setAttribute("success", null);
            String msg = "Update failed! Something is wrong!";
            request.setAttribute("error", msg);
        }

        request.getRequestDispatcher("account.jsp").forward(request, response);
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
