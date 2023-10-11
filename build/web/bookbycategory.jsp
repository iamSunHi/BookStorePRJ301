<%-- 
    Document   : bookByCategory
    Created on : Sep 29, 2023, 12:49:27 PM
    Author     : Nhật Huy
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="models.Book"%>
<%@page import="java.util.List"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.util.Locale"%>
<!DOCTYPE html>
<html lang="en" data-bs-theme="dark">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title><%= request.getParameter("category").trim().replaceAll("_", " ") %> Books</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-4bw+/aepP/YC94hEpVNVgiZdgIC5+VKNBQNGCHeKRQN+PtmoHDEXuppvnDJzQIu9" crossorigin="anonymous">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css"
              integrity="sha512-z3gLpd7yknf1YoNbCzqRKc4qyor8gaKU1qmn+CShxbuBusANI9QpRohGBreCFkKxLhei6S9CQXFEbbKuqLg0DA=="
              crossorigin="anonymous" referrerpolicy="no-referrer" />
        <link rel="stylesheet" href="assets/css/navbar.css">
        <link rel="stylesheet" href="assets/css/home.css">
    </head>

    <body>
        <% if (session.getAttribute("BookList") == null) { %>
        <jsp:forward page="book?page=1"></jsp:forward>
        <% } %>
        <jsp:include page="navbar.jsp"></jsp:include>

            <main class="container pb-3" style="margin-top: 60px;">
                <div class="row book-list">
                <% 
                    if (((List<Book>) request.getAttribute("bookListByCategory")).size() > 0) {
                        Locale locale = new Locale("en", "US");
                        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
                        for (Book book : (List<Book>) request.getAttribute("bookListByCategory")) { 
                %>
                <div class="col-12 col-md-6 col-lg-3 book-list-item my-2">
                    <div class="card" >
                        <a href="book?method=get&bookId=<%= book.getId() %>">
                            <img src="assets/img/books/<%= book.getImageUrl() %>" class="card-img-top" style="height: 300px; width: 100%;" alt="...">
                            <div class="card-body">
                                <a href="book?method=get&bookId=<%= book.getId() %>" class="card-title book-list-item__title fs-4 text-decoration-none">
                                    <%= book.getTitle() %>
                                </a>
                                <div class="d-flex align-items-baseline justify-content-between">
                                    <h6 class="card-text book-list-item__author">Author: <i><%= book.getAuthor() %></i></h6>
                                    <h6 class="card-text book-list-item__price"><%= book.getPrice() != 0 ? currencyFormatter.format(book.getPrice()) : "Free" %></h6>
                                </div>
                                <div class="row">
                                    <div class="col-6">
                                        <a href="#" class="w-100 btn btn-secondary">Rent</a>
                                    </div>
                                    <div class="col-6">
                                        <a href="#" class="w-100 btn btn-success">Buy</a>
                                    </div>
                                </div>
                            </div>
                        </a>
                    </div>
                </div>
                <% 
                        }
                    } else {
                %>
                <div class="text-center">
                    <h1>There is no book with category "<%= request.getParameter("category").trim().replaceAll("_", " ") %>"</h1>
                </div>
                <% } %>
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
        </script>
    </body>

</html>
