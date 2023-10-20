<%-- 
    Document   : summary
    Created on : Oct 19, 2023, 2:56:51 PM
    Author     : Sun Hi
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="models.Book"%>
<%@page import="models.User"%>
<%@page import="java.text.SimpleDateFormat" %>
<%@page import="java.util.Calendar" %>
<%@page import="java.util.Date" %>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.util.Locale"%>
<%@page import="java.util.List"%>
<jsp:useBean id="user" class="models.User" scope="session">
    <jsp:setProperty name="user" property="*"></jsp:setProperty>
</jsp:useBean>
<jsp:useBean id="userCart" class="models.Cart" scope="session">
    <jsp:setProperty name="userCart" property="*"></jsp:setProperty>
</jsp:useBean>
<!DOCTYPE html>
<html lang="en" data-bs-theme="dark">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Order Summary</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-4bw+/aepP/YC94hEpVNVgiZdgIC5+VKNBQNGCHeKRQN+PtmoHDEXuppvnDJzQIu9" crossorigin="anonymous">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css"
              integrity="sha512-z3gLpd7yknf1YoNbCzqRKc4qyor8gaKU1qmn+CShxbuBusANI9QpRohGBreCFkKxLhei6S9CQXFEbbKuqLg0DA=="
              crossorigin="anonymous" referrerpolicy="no-referrer" />
        <link rel="stylesheet" href="assets/css/navbar.css">
    </head>
    <body>
        <jsp:include page="navbar.jsp"></jsp:include>

            <div class="container">
                <main class="pb-3" style="margin-top: 60px;">
                    <form id="order" method="post" action="order">
                        <br>
                        <div class="container">
                            <div class="card shadow border-0">

                                <div class="card-header ml-0 py-4 text-center">
                                    <h3 class="pt-2 text-white text-uppercase">
                                        Order Summary
                                    </h3>
                                </div>
                                <div class="card-body">
                                    <div class="container rounded p-2">
                                        <div class="row">
                                            <div class="col-12 col-lg-6 pb-4">
                                                <div class="row">
                                                    <h4 class="d-flex justify-content-between align-items-center mb-3">
                                                        <span class="text-info">Shipping Details:</span>
                                                    </h4>
                                                </div>
                                                <div class="row my-1 align-items-center">
                                                    <div class="col-3 fw-semibold">
                                                        <label>Name</label>
                                                    </div>
                                                    <div class="col-9">
                                                        <input class="form-control" type="text" name="name" value="${user.getName()}"">
                                                </div>
                                            </div>
                                            <div class="row my-1 align-items-center">
                                                <div class="col-3 fw-semibold">
                                                    <label>Email</label>
                                                </div>
                                                <div class="col-9">
                                                    <input class="form-control" type="email" name="email" value="${user.getEmail().equals("null") ? "" : user.getEmail()}"">
                                                </div>
                                            </div>
                                            <div class="row my-1 align-items-center">
                                                <div class="col-3 fw-semibold">
                                                    <label>Phone</label>
                                                </div>
                                                <div class="col-9">
                                                    <input class="form-control" type="text" name="phone" value="${user.getPhone().equals("null") ? "" : user.getPhone()}"">
                                                </div>
                                            </div>
                                            <div class="row my-1 align-items-center">
                                                <div class="col-3 fw-semibold">
                                                    <label>Address</label>
                                                </div>
                                                <div class="col-9">
                                                    <input class="form-control" type="text" name="address" value="${user.getAddress().equals("null") ? "" : user.getAddress()}"">
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-12 col-lg-5 offset-lg-1">
                                            <h4 class="d-flex justify-content-between align-items-center mb-3">
                                                <span class="text-info">Order Summary:</span>
                                            </h4>
                                            <ul class="list-group mb-3" style="max-height:200px; overflow-y:scroll;">
                                                <%
                                                    Locale locale = new Locale("en", "US");
                                                    NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
                                                    List<Book> userBookList = userCart.getBookList();
                                                    double total = 0;
                                                    if (userBookList != null && userBookList.size() > 0) {
                                                        for (Book book : userBookList) {
                                                %>
                                                <li class="d-flex justify-content-between align-items-center border-bottom py-1">
                                                    <img class="shopping-cart__wrapper__body__list-item__img border rounded"
                                                         src="assets/img/books/<%= book.getImageUrl() %>" style="width:100%;height:auto;">
                                                    <div class="ms-1 w-100">
                                                        <div>
                                                            <h5 class="my-0" style="display:-webkit-box;overflow:hidden;-webkit-box-orient:vertical;-webkit-line-clamp:1;"><%= book.getTitle() %></h5>
                                                        </div>
                                                        <div class="d-flex align-items-center justify-content-between">
                                                            <span>
                                                                Author: <i><%= book.getAuthor() %></i>
                                                            </span>
                                                            <span style="color:#ee4d2d;"><%= book.getPrice() != 0 ? currencyFormatter.format(book.getPrice()) : "Free" %></span>
                                                        </div>
                                                    </div>
                                                </li>
                                                <%  
                                                    total += book.getPrice();
                                                    }
                                            } else { %>
                                                <div class="text-center">
                                                    <h4 class="m-0 py-4">No thing here . . .</h4>
                                                </div>
                                                <% } %>
                                            </ul>

                                            <div class="d-flex justify-content-between align-items-center">
                                                <h5 class="text-info m-0">Total (<%= userBookList.size() %>):</h5>
                                                <strong class="text-info"><%= currencyFormatter.format(total) %></strong>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="card-footer py-3">
                                <div class="row align-items-center">
                                    <div class="col-12 col-md-8">
                                        <p style="font-size:14px; margin-bottom:0;">
                                            Estimate Arrival Date:
                                            <%
                                                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                                                Calendar calendar = Calendar.getInstance();
                                                calendar.setTime(new Date());

                                                // Calculate 7 days ahead
                                                calendar.add(Calendar.DAY_OF_MONTH, 7);
                                                Date startDate = calendar.getTime();
            
                                                // Calculate 30 days ahead
                                                calendar.add(Calendar.DAY_OF_MONTH, 23); // 30 - 7 = 23
                                                Date endDate = calendar.getTime();

                                                out.print(dateFormat.format(startDate) + " - " + dateFormat.format(endDate));
                                            %>
                                        </p>
                                    </div>
                                    <div class="col-12 col-md-4">
                                        <button type="submit" value="Place Order" class="btn btn-secondary form-control">Place Order</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
            </main>
        </div>

        <jsp:include page="footer.jsp"></jsp:include>

        <jsp:include page="validationScripts.jsp"></jsp:include>
    </body>

</html>
