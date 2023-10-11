/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import dal.CategoryDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import models.Category;

/**
 *
 * @author Nhật Huy
 */
public class CategoryController extends HttpServlet {

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
            out.println("<title>Servlet CategoryController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CategoryController at " + request.getContextPath() + "</h1>");
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
        CategoryDAO categoryDAO = new CategoryDAO();
        List<Category> categoryList = null;
        try {
            categoryList = categoryDAO.getAll();
        } catch (NamingException ex) {
            Logger.getLogger(CategoryController.class.getName()).log(Level.SEVERE, null, ex);
        }

        request.setAttribute("CategoryList", categoryList);
        request.getRequestDispatcher("admin/category.jsp").forward(request, response);
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
        if (request.getParameter("_method") == null || request.getParameter("_method").trim().equals("")) {
            String categoryName = request.getParameter("categoryName");
            if (categoryName.equals("")) {
                request.setAttribute("success", null);
                String msg = "Do not leave the name blank!";
                request.setAttribute("error", msg);
                this.doGet(request, response);
                return;
            }
            CategoryDAO categoryDAO = new CategoryDAO();

            try {
                if (categoryDAO.create(categoryName)) {
                    request.setAttribute("error", null);
                    String msg = "Create new category with name \'" + categoryName + "\' successful!";
                    request.setAttribute("success", msg);
                } else {
                    request.setAttribute("success", null);
                    String msg = "The category with name \'" + categoryName + "\' has been existed!";
                    request.setAttribute("error", msg);
                }
            } catch (NamingException ex) {
                Logger.getLogger(CategoryController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            switch (request.getParameter("_method")) {
                case "PUT":
                    this.doPut(request, response);
                    return;
                case "DELETE":
                    this.doDelete(request, response);
                    return;
            }
        }

        this.doGet(request, response);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int categoryId = Integer.parseInt(req.getParameter("categoryId"));
        String categoryName = req.getParameter("categoryName");
        if (categoryName.equals("")) {
            req.setAttribute("success", null);
            String msg = "Do not leave the name blank!";
            req.setAttribute("error", msg);
            this.doGet(req, resp);
            return;
        }

        CategoryDAO categoryDAO = new CategoryDAO();

        try {
            if (categoryDAO.update(categoryId, categoryName)) {
                req.setAttribute("error", null);
                String msg = "Update category with name \'" + categoryName + "\' successful!";
                req.setAttribute("success", msg);
            } else {
                req.setAttribute("success", null);
                String msg = "The category with name \'" + categoryName + "\' has been existed!";
                req.setAttribute("error", msg);
            }
        } catch (NamingException ex) {
            Logger.getLogger(CategoryController.class.getName()).log(Level.SEVERE, null, ex);
        }

        this.doGet(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int categoryId = Integer.parseInt(req.getParameter("categoryId"));
        CategoryDAO categoryDAO = new CategoryDAO();

        try {
            if (categoryDAO.delete(categoryId)) {
                req.setAttribute("error", null);
                String msg = "The category has been deleted!";
                req.setAttribute("success", msg);
            } else {
                req.setAttribute("success", null);
                String msg = "The category is not existed!";
                req.setAttribute("error", msg);
            }
        } catch (NamingException ex) {
            Logger.getLogger(CategoryController.class.getName()).log(Level.SEVERE, null, ex);
        }

        this.doGet(req, resp);
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
