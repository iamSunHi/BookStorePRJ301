<%-- 
    Document   : confirmation
    Created on : Oct 25, 2023, 10:35:34 AM
    Author     : Sun Hi
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="models.Store" %>
<%@page import="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="storeList" class="java.util.ArrayList" scope="request">
    <jsp:setProperty name="storeList" property="*"></jsp:setProperty>
</jsp:useBean>
<!DOCTYPE html>
<html lang="en" data-bs-theme="dark">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Confirmations</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-4bw+/aepP/YC94hEpVNVgiZdgIC5+VKNBQNGCHeKRQN+PtmoHDEXuppvnDJzQIu9" crossorigin="anonymous">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css"
              integrity="sha512-z3gLpd7yknf1YoNbCzqRKc4qyor8gaKU1qmn+CShxbuBusANI9QpRohGBreCFkKxLhei6S9CQXFEbbKuqLg0DA=="
              crossorigin="anonymous" referrerpolicy="no-referrer" />

        <link rel="stylesheet" href="assets/css/navbar.css">
    </head>
    <body>
        <% if (!((boolean) session.getAttribute("isAdmin"))) { 
            response.sendRedirect("../accessdenied.jsp");
            return;
        } %>

        <jsp:include page="../navbar.jsp"></jsp:include>

            <main class="container" style="margin-top: 60px;">
                <div class="card shadow border-0 pt-4">
                    <div class="card-header bg-dark bg-gradient m-lg-0 py-3">
                        <div class="row">
                            <div class="col-12 text-center">
                                <h2 class="text-white py-2">Request List</h2>
                            </div>
                        </div>
                    </div>
                    <div class="card-body">
                    <c:choose>
                        <c:when test="${storeList.size() > 0}">
                            <table class="table table-dark table-striped table-bordered">
                                <thead>
                                    <tr>
                                        <th class="text-center">
                                            Store Name
                                        </th>
                                        <th class="text-center">
                                            Email
                                        </th>
                                        <th class="text-center">
                                            Phone
                                        </th>
                                        <th class="text-center">
                                            Address
                                        </th>
                                        <th class="text-center">Action</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="i" begin="0" end="${storeList.size() - 1}">
                                        <tr>
                                            <td>
                                                ${storeList.get(i).getName()}
                                            </td>
                                            <td>
                                                ${storeList.get(i).getEmail()}
                                            </td>
                                            <td>
                                                ${storeList.get(i).getPhone()}
                                            </td>
                                            <td>
                                                ${storeList.get(i).getAddress()}
                                            </td>
                                            <td>
                                                <div class="btn-group d-flex" role="group">
                                                    <a onclick="confirmVerifyStore('${storeList.get(i).getId()}')" class="btn btn-primary mx-2">
                                                        Verify
                                                    </a>
                                                    <a onclick="confirmDeleteStore('${storeList.get(i).getId()}')" class="btn btn-danger mx-2">
                                                        Delete
                                                    </a>
                                                </div>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </c:when>
                        <c:otherwise>
                            <center><h1>No request here . . .</h1></center>
                            </c:otherwise>
                        </c:choose>
                </div>
            </div>
        </main>

        <jsp:include page="../footer.jsp"></jsp:include>

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
                                                            timer: 4000
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
                                                        function confirmVerifyStore(storeId) {
                                                            Swal.fire({
                                                                title: 'Are you sure?',
                                                                text: "You can delete later!",
                                                                icon: 'question',
                                                                showCancelButton: true,
                                                                confirmButtonColor: '#3085d6',
                                                                cancelButtonColor: '#d33',
                                                                confirmButtonText: 'Yes, verify it!',
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
                                                                    window.location.href = 'store?type=confirm&id=' + storeId;
                                                                }
                                                            });
                                                        }
                                                        function confirmDeleteStore(storeId) {
                                                            Swal.fire({
                                                                title: 'Are you sure?',
                                                                text: "You won't be able to revert this!",
                                                                icon: 'warning',
                                                                showCancelButton: true,
                                                                confirmButtonColor: '#3085d6',
                                                                cancelButtonColor: '#d33',
                                                                confirmButtonText: 'Yes, delete it!',
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
                                                                    window.location.href = 'store?type=delete&id=' + storeId;
                                                                }
                                                            });
                                                        }
        </script>
    </body>
</html>
