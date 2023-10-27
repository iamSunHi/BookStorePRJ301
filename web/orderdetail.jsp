<%-- 
    Document   : orderdetail
    Created on : Oct 27, 2023, 1:21:21 PM
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
<jsp:useBean id="orderDetail" class="models.OrderDetail" scope="session">
    <jsp:setProperty name="orderDetail" property="*"></jsp:setProperty>
</jsp:useBean>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
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
                    <br>
                    <div class="container">
                        <div class="card shadow border-0">
                            <div class="card-header ml-0 py-4 text-center">
                                <h3 class="pt-2 text-white text-uppercase">
                                    Order Summary
                                </h3>
                                <a href="order?method=history" class="btn btn-outline-warning position-absolute d-none d-md-block"
                                   style="top:8px;left:8px;">Back</a>
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
                                                    <input disabled class="form-control" type="text" name="name" value="${user.getName()}"">
                                            </div>
                                        </div>
                                        <div class="row my-1 align-items-center">
                                            <div class="col-3 fw-semibold">
                                                <label>Email</label>
                                            </div>
                                            <div class="col-9">
                                                <input disabled class="form-control" type="email" name="email" value="${user.getEmail().equals("null") ? "" : user.getEmail()}"">
                                            </div>
                                        </div>
                                        <div class="row my-1 align-items-center">
                                            <div class="col-3 fw-semibold">
                                                <label>Phone</label>
                                            </div>
                                            <div class="col-9">
                                                <input disabled class="form-control" type="text" name="phone" value="${orderDetail.getOrderHeader().getPhone()}"">
                                            </div>
                                        </div>
                                        <div class="row my-1 align-items-center">
                                            <div class="col-3 fw-semibold">
                                                <label>Address</label>
                                            </div>
                                            <div class="col-9">
                                                <input disabled class="form-control" type="text" name="address" value="${orderDetail.getOrderHeader().getAddress()}"">
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-12 col-lg-5 offset-lg-1">
                                        <h4 class="d-flex justify-content-between align-items-center mb-3">
                                            <span class="text-info">Order Summary:</span>
                                        </h4>
                                        <ul class="list-group mb-3" style="max-height:200px; overflow-y:scroll;">
                                            <%--
                                                Locale locale = new Locale("en", "US");
                                                NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
                                                List<Book> userBookList = orderDetail.getBooks();
                                                double total = 0;
                                                if (userBookList != null && userBookList.size() > 0) {
                                                    for (Book book : userBookList) {
                                            --%>
                                            <c:choose>
                                                <c:when test="${orderDetail.getBooks() != null && orderDetail.getBooks().size() > 0}">
                                                    <c:set var ="total" value ="${0}"/>
                                                    <c:forEach var="i" begin="0" end="${orderDetail.getBooks().size() - 1}">
                                                        <li class="d-flex justify-content-between align-items-center border-bottom py-1">
                                                            <img class="shopping-cart__wrapper__body__list-item__img border rounded"
                                                                 src="assets/img/books/${ orderDetail.getBooks().get(i).getImageUrl() }" style="width:100%;height:auto;">
                                                            <div class="ms-1 w-100">
                                                                <div>
                                                                    <h5 class="my-0" style="display:-webkit-box;overflow:hidden;-webkit-box-orient:vertical;-webkit-line-clamp:1;">${ orderDetail.getBooks().get(i).getTitle() }</h5>
                                                                </div>
                                                                <div class="d-flex align-items-center justify-content-between">
                                                                    <span>
                                                                        Author: <i>${ orderDetail.getBooks().get(i).getAuthor() }</i>
                                                                    </span>
                                                                    <span style="color:#ee4d2d; margin-right:4px;">
                                                                        <fmt:setLocale value = "en_US"/>
                                                                        <c:choose>
                                                                            <c:when test="${orderDetail.getBooks().get(i).getPrice() > 0}">
                                                                                <fmt:formatNumber value="${orderDetail.getBooks().get(i).getPrice()}" type="currency" />
                                                                            </c:when>
                                                                            <c:otherwise>
                                                                                Free
                                                                            </c:otherwise>
                                                                        </c:choose>
                                                                    </span>
                                                                </div>
                                                            </div>
                                                        </li>
                                                        <c:set var ="total" value ="${total + orderDetail.getBooks().get(i).getPrice()}"/>
                                                    </c:forEach>
                                                </c:when>
                                                <c:otherwise>
                                                    <div class="text-center">
                                                        <h4 class="m-0 py-4">No thing here . . .</h4>
                                                    </div>
                                                </c:otherwise>
                                            </c:choose>
                                        </ul>

                                        <div class="d-flex justify-content-between align-items-center">
                                            <h5 class="text-info m-0">Total (${ orderDetail.getBooks().size() }):</h5>
                                            <strong class="text-info">
                                                <c:choose>
                                                    <c:when test="${total > 0}">
                                                        <fmt:formatNumber value="${total}" type="currency" />
                                                    </c:when>
                                                    <c:otherwise>
                                                        Free
                                                    </c:otherwise>
                                                </c:choose>
                                            </strong>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </main>
        </div>

        <jsp:include page="footer.jsp"></jsp:include>

        <jsp:include page="validationScripts.jsp"></jsp:include>
    </body>

</html>
