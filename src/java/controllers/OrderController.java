/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import dal.CartDAO;
import dal.OrderDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import models.Book;
import models.Cart;
import models.OrderDetail;
import models.OrderHeader;
import models.User;

/**
 *
 * @author Sun Hi
 */
public class OrderController extends HttpServlet {

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
            out.println("<title>Servlet OrderController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet OrderController at " + request.getContextPath() + "</h1>");
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
        OrderDAO orderDAO = new OrderDAO();

        if (request.getParameter("method") != null) {
            String method = request.getParameter("method");
            switch (method) {
                case "history": {
                    try {
                        List<OrderHeader> orderList = orderDAO.getAllByUserId(((User) session.getAttribute("user")).getId());
                        request.setAttribute("orderList", orderList);
                    } catch (NamingException ex) {
                        Logger.getLogger(OrderController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    request.getRequestDispatcher("orderhistory.jsp").forward(request, response);
                    return;
                }
                case "orderdetail": {
                    OrderDetail orderDetail = null;
                    try {
                        orderDetail = orderDAO.getOrder(Integer.parseInt(request.getParameter("orderId")));
                    } catch (NamingException ex) {
                        Logger.getLogger(OrderController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    request.setAttribute("orderDetail", orderDetail);
                    request.getRequestDispatcher("orderdetail.jsp").forward(request, response);
                    return;
                }

            }
        }
        Cart cart = (Cart) session.getAttribute("userCart");
        OrderHeader orderHeader = (OrderHeader) session.getAttribute("orderHeader");

        Stripe.apiKey = "...";
        Session stripeSession = null;
        try {
            stripeSession = Session.retrieve(orderHeader.getSessionId());
        } catch (StripeException ex) {
            Logger.getLogger(OrderController.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (stripeSession.getPaymentStatus().toLowerCase().equals("paid")) {
            orderHeader.setPaymentIntentId(stripeSession.getPaymentIntent());
            try {
                orderDAO.updateStripePaymentId(orderHeader);
                orderDAO.updateOrderStatus(orderHeader.getId(), "Approved", "Approved");
            } catch (NamingException ex) {
                Logger.getLogger(OrderController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        CartDAO cartDAO = new CartDAO();
        try {
            cartDAO.removeAll(cart.getId());
            Cart userCart = cartDAO.get(cart.getUserId());
            session.setAttribute("userCart", userCart);
        } catch (NamingException ex) {
            Logger.getLogger(OrderController.class.getName()).log(Level.SEVERE, null, ex);
        }

        request.setAttribute("orderId", orderHeader.getId());
        session.removeAttribute("orderHeader");
        request.getRequestDispatcher("orderconfirmation.jsp").forward(request, response);
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
        User user = (User) session.getAttribute("user");
        Cart cart = (Cart) session.getAttribute("userCart");

        OrderDAO orderDAO = new OrderDAO();

        OrderHeader orderHeader = new OrderHeader();
        orderHeader.setUser(user);
        orderHeader.setOrderDate(new Date());
        orderHeader.setPayment("Stripe");
        orderHeader.setOrderStatus("Pending");
        orderHeader.setPaymentStatus("Pending");
        orderHeader.setPhone(request.getParameter("phone"));
        orderHeader.setAddress(request.getParameter("address"));
        try {
            int orderHeaderId = orderDAO.createOrderHeader(orderHeader);
            orderHeader.setId(orderHeaderId);
        } catch (NamingException | ParseException ex) {
            Logger.getLogger(OrderController.class.getName()).log(Level.SEVERE, null, ex);
        }

        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrderHeader(orderHeader);
        orderDetail.setBooks(cart.getBookList());
        cart.getBookList().forEach(book -> {
            orderDetail.setTotalPrice(orderDetail.getTotalPrice() + book.getPrice());
        });
        try {
            if (orderDAO.createOrderDetail(orderDetail)) {
                // Configuration for Stripe
                String domain = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
                Stripe.apiKey = "...";

                SessionCreateParams.Builder params = SessionCreateParams.builder();
                for (Book book : orderDetail.getBooks()) {
                    SessionCreateParams.LineItem.PriceData priceData = new SessionCreateParams.LineItem.PriceData.Builder()
                            .setProductData(
                                    new SessionCreateParams.LineItem.PriceData.ProductData.Builder()
                                            .setName(book.getTitle())
                                            .build()
                            )
                            .setCurrency("USD")
                            .setUnitAmount((long) (book.getPrice() * 100))
                            .build();
                    SessionCreateParams.LineItem lineItem = new SessionCreateParams.LineItem.Builder()
                            .setPriceData(priceData)
                            .setQuantity(Long.parseLong("1"))
                            .build();
                    params.addLineItem(lineItem);
                }

                Session stripeSession = Session.create(params
                        .setSuccessUrl(domain + "order")
                        .setCancelUrl(domain + "order?method=history")
                        .setMode(SessionCreateParams.Mode.PAYMENT)
                        .build()
                );
                orderHeader.setSessionId(stripeSession.getId());
                orderHeader.setPaymentIntentId(stripeSession.getPaymentIntent());

                orderDAO.updateStripePaymentId(orderHeader);

                session.setAttribute("orderHeader", orderHeader);

                CartDAO cartDAO = new CartDAO();
                try {
                    cartDAO.removeAll(cart.getId());
                    Cart userCart = cartDAO.get(cart.getUserId());
                    session.setAttribute("userCart", userCart);
                } catch (NamingException ex) {
                    Logger.getLogger(OrderController.class.getName()).log(Level.SEVERE, null, ex);
                }
                response.sendRedirect(stripeSession.getUrl());
            }
        } catch (NamingException | StripeException ex) {
            Logger.getLogger(OrderController.class.getName()).log(Level.SEVERE, null, ex);
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
