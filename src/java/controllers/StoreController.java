/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import dal.StoreDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import models.Store;
import models.User;

/**
 *
 * @author Sun Hi
 */
public class StoreController extends HttpServlet {

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
            out.println("<title>Servlet StoreController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet StoreController at " + request.getContextPath() + "</h1>");
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
        if (request.getParameter("type") != null || !request.getParameter("type").equals("")) {
            String type = request.getParameter("type");
            switch (type) {
                case "getall": {
                    StoreDAO storeDAO = new StoreDAO();
                    List<Store> storeList = new ArrayList<>();
                    try {
                        storeList = storeDAO.getAll();
                    } catch (NamingException ex) {
                        Logger.getLogger(StoreController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    request.setAttribute("storeList", storeList);
                    request.getRequestDispatcher("admin/store.jsp").forward(request, response);
                    return;
                }
                case "confirm": {
                    StoreDAO storeDAO = new StoreDAO();
                    try {
                        if (storeDAO.confirm(request.getParameter("id"))) {
                            request.removeAttribute("error");
                            String msg = "Verify store successful!";
                            request.setAttribute("success", msg);
                        } else {
                            request.removeAttribute("success");
                            String msg = "Something went wrong!";
                            request.setAttribute("error", msg);
                        }
                    } catch (NamingException ex) {
                        Logger.getLogger(StoreController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                }
                case "delete": {
                    StoreDAO storeDAO = new StoreDAO();
                    try {
                        if (storeDAO.delete(request.getParameter("id"))) {
                            request.removeAttribute("error");
                            String msg = "Delete request successful!";
                            request.setAttribute("success", msg);
                        } else {
                            request.removeAttribute("success");
                            String msg = "Something went wrong!";
                            request.setAttribute("error", msg);
                        }
                    } catch (NamingException ex) {
                        Logger.getLogger(StoreController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                }
            }
        }

        StoreDAO storeDAO = new StoreDAO();
        List<Store> storeList = null;
        try {
            storeList = storeDAO.getNotVerified();
        } catch (NamingException ex) {
            Logger.getLogger(StoreController.class.getName()).log(Level.SEVERE, null, ex);
        }

        request.setAttribute("storeList", storeList);
        request.getRequestDispatcher("admin/confirmation.jsp").forward(request, response);
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
        String method = request.getParameter("method");
        switch (method) {
            case "register": {
                Store store = new Store();
                store.setUserId(((User) session.getAttribute("user")).getId());
                store.setName(request.getParameter("name"));
                store.setEmail(request.getParameter("email"));
                store.setPhone(request.getParameter("phone"));
                store.setAddress(request.getParameter("address"));
                store.setVerified(false);

                StoreDAO storeDAO = new StoreDAO();
                try {
                    if (storeDAO.create(store)) {
                        request.removeAttribute("error");
                        String msg = "Please wait for your request to be verified!";
                        request.setAttribute("success", msg);
                    } else {
                        request.removeAttribute("success");
                        String msg = "Something went wrong!";
                        request.setAttribute("error", msg);
                    }
                } catch (NamingException ex) {
                    Logger.getLogger(StoreController.class.getName()).log(Level.SEVERE, null, ex);
                }

                request.getRequestDispatcher("home.jsp").forward(request, response);
                return;
            }
            case "update": {
                Store store = new Store();
                store.setName(request.getParameter("name"));
                store.setEmail(request.getParameter("email"));
                store.setPhone(request.getParameter("phone"));
                store.setAddress(request.getParameter("address"));

                StoreDAO storeDAO = new StoreDAO();
                try {
                    if (storeDAO.create(store)) {
                        request.removeAttribute("error");
                        String msg = "Update successful!";
                        request.setAttribute("success", msg);
                    } else {
                        request.removeAttribute("success");
                        String msg = "Something went wrong!";
                        request.setAttribute("error", msg);
                    }
                } catch (NamingException ex) {
                    Logger.getLogger(StoreController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
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

}
