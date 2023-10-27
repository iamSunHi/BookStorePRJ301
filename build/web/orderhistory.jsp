<%-- 
    Document   : orderhistory
    Created on : Oct 26, 2023, 9:23:13 PM
    Author     : Sun Hi
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="models.OrderHeader" %>
<%@page import="java.util.List" %>
<%@page import="java.util.Date" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="orderList" class="java.util.ArrayList" scope="request">
    <jsp:setProperty name="orderList" property="*"></jsp:setProperty>
</jsp:useBean>
<!DOCTYPE html>
<html lang="en" data-bs-theme="dark">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Books</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-4bw+/aepP/YC94hEpVNVgiZdgIC5+VKNBQNGCHeKRQN+PtmoHDEXuppvnDJzQIu9" crossorigin="anonymous">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css"
              integrity="sha512-z3gLpd7yknf1YoNbCzqRKc4qyor8gaKU1qmn+CShxbuBusANI9QpRohGBreCFkKxLhei6S9CQXFEbbKuqLg0DA=="
              crossorigin="anonymous" referrerpolicy="no-referrer" />
        <link rel="stylesheet" href="assets/css/navbar.css">
        <link rel="stylesheet" href="assets/css/home.css">
    </head>

    <body>
        <fmt:setLocale value = "en_US"/>

        <jsp:include page="navbar.jsp"></jsp:include>

            <main class="container" style="margin-top: 60px;">
                <div class="card shadow border-0 pt-4">
                    <div class="card-header bg-dark bg-gradient m-lg-0 py-3">
                        <div class="row">
                            <div class="col-12 text-center">
                                <h2 class="text-white py-2">Your Orders</h2>
                            </div>
                        </div>
                    </div>
                    <div class="card-body">
                    <c:choose>
                        <c:when test="${orderList.size() > 0}">
                            <table class="table table-dark table-striped table-bordered">
                                <thead>
                                    <tr>
                                        <th class="text-center">
                                            Order Id
                                        </th>
                                        <th class="text-center">
                                            Payment
                                        </th>
                                        <th class="text-center">
                                            Order Date
                                        </th>
                                        <th class="text-center">
                                            Payment Date
                                        </th>
                                        <th class="text-center">
                                            Order Status
                                        </th>
                                        <th class="text-center">
                                            Payment Status
                                        </th>
                                        <th class="text-center">
                                            Phone
                                        </th>
                                        <th class="text-center">
                                            Address
                                        </th>
                                        <th class="text-center"></th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="i" begin="0" end="${orderList.size() - 1}">
                                        <tr>
                                            <td class="text-center">
                                                ${orderList.get(i).getId()}
                                            </td>
                                            <td class="text-center">
                                                ${orderList.get(i).getPayment()}
                                            </td>
                                            <td class="text-center">
                                                <fmt:formatDate type="both" dateStyle="long" timeStyle="short" value="${orderList.get(i).getOrderDate()}" />
                                            </td>
                                            <td class="text-center">
                                                <c:choose>
                                                    <c:when test="${orderList.get(i).getPaymentDate().getTime() == 946659600000}">
                                                        <b class="text-danger">Unpaid</b>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <fmt:formatDate type="both" dateStyle="long" timeStyle="short" value="${orderList.get(i).getPaymentDate()}" />
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td class="text-center">
                                                ${orderList.get(i).getOrderStatus()}
                                            </td>
                                            <td class="text-center">
                                                ${orderList.get(i).getPaymentStatus()}
                                            </td>
                                            <td class="text-center">
                                                ${orderList.get(i).getPhone()}
                                            </td>
                                            <td>
                                                ${orderList.get(i).getAddress()}
                                            </td>
                                            <td>
                                                <div class="btn-group d-flex" role="group">
                                                    <a href="order?method=orderdetail&orderId=${orderList.get(i).getId()}" class="btn btn-success mx-2">
                                                        Detail
                                                    </a>
                                                </div>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </c:when>
                        <c:otherwise>
                            <center><h1>No Order here . . .</h1></center>
                            </c:otherwise>
                        </c:choose>
                </div>
            </div>
        </main>

        <jsp:include page="footer.jsp"></jsp:include>

            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js"
                    integrity="sha384-HwwvtgBNo3bZJJLYd8oVXjrBZt8cqVSpeBNS5n7C8IVInixGAoxmnlMuBnhbgrkm"
            crossorigin="anonymous"></script>
            <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
            <script type="text/javascript">
            <% if (request.getAttribute("success") != null) { %>
                Swal.fire({
                    icon: 'success',
                    title: 'Done!',
                    text: "<%= request.getAttribute("success") %>",
                    timer: 5000
                });
            <% } %>
            <% if (request.getAttribute("error") != null) { %>
                Swal.fire({
                    icon: 'error',
                    title: 'Oops...',
                    text: "<%= request.getAttribute("error") %>",
                    timer: 4000
                });
            <% } %>
                function confirmRemoveBook(bookId) {
                    Swal.fire({
                        title: 'Are you sure?',
                        text: "You won't be able to revert this!",
                        icon: 'warning',
                        showCancelButton: true,
                        confirmButtonColor: '#3085d6',
                        cancelButtonColor: '#d33',
                        confirmButtonText: 'Yes, remove it!',
                        customClass: {
                            confirmButton: 'full-width-button' // Add a custom class to the confirm button
                        },
                        onOpen: function () {
                            // Apply custom CSS to the button
                            document.querySelector('.full-width-button').style.width = '100%';
                            document.querySelector('.full-width-button').style.display = 'block';
                        },
                        preConfirm: function () {
                            // Add the logic to navigate to the specified URL when the button is clicked
                            window.location.href = 'book?method=delete&bookId=' + bookId;
                        }
                    });
                }
        </script>
    </body>
</html>
