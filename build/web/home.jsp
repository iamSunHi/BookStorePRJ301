<%-- 
    Document   : home
    Created on : Sep 22, 2023, 7:36:54 PM
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
        <title>Home</title>
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
                    Locale locale = new Locale("en", "US");
                    NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
                    for (Book book : (List<Book>) session.getAttribute("BookList")) { 
                %>
                <div class="col-12 col-md-6 col-lg-3 book-list-item my-2">
                    <a href="book?method=get&bookId=<%= book.getId() %>">
                        <div class="card" href="book?method=get&bookId=<%= book.getId() %>">
                            <img src="assets/img/books/<%= book.getImageUrl() %>" class="card-img-top" style="height: 300px; width: 100%;" alt="...">
                            <div class="card-body">
                                <a href="book?method=get&bookId=<%= book.getId() %>" class="card-title book-list-item__title fs-4 text-decoration-none">
                                    <%= book.getTitle() %>
                                </a>
                                <div class="d-flex align-items-baseline justify-content-between">
                                    <h6 class="card-text book-list-item__author">Author: <i><%= book.getAuthor() %></i></h6>
                                    <h6 class="card-text book-list-item__price"><%= book.getPrice() != 0 ? currencyFormatter.format(book.getPrice()) : "Free" %></h6>
                                </div>
                                <div class="">
                                    <button onclick="confirmAddBook(<%= book.getId() %>)" class="w-100 btn btn-success">Add to Cart</button>
                                </div>
                            </div>
                        </div>
                    </a>
                </div>
                <% } %>
            </div>
        </main>

        <nav>
            <ul class="pagination justify-content-center pagination-lg">
                <li class="page-item">
                    <a class="page-link previous" href="#" aria-label="Previous">
                        <span aria-hidden="true">&laquo;</span>
                    </a>
                </li>
                <%
                    int currentPage = (int) session.getAttribute("currentPage");
                    int numberOfPage = (int) session.getAttribute("numberOfPage");
                    int maxPagesToShow = 3;

                    int startPage = Math.max(1, currentPage - maxPagesToShow / 2);
                    int endPage = Math.min(numberOfPage, startPage + maxPagesToShow - 1);

                    if (endPage - startPage + 1 < maxPagesToShow) {
                        startPage = Math.max(1, endPage - maxPagesToShow + 1);
                    }

                    boolean showEllipsisBefore = (startPage > 1);
                    boolean showEllipsisAfter = (endPage < numberOfPage);

                    if (showEllipsisBefore) {
                %>
                <li class="page-item">
                    <a class="page-link" href="book?page=1" aria-label="First">
                        <span aria-hidden="true">1</span>
                    </a>
                </li>
                <li class="page-item disabled">
                    <span class="page-link">&hellip;</span>
                </li>
                <%
                    }
                    for (int i = startPage; i <= endPage; i++) { 
                %>
                <li class="page-item <%= currentPage == i ? "active" : "" %>">
                    <a class="page-link" href="book?page=<%= i %>"><%= i %></a>
                </li>
                <%
                    }
                    if (showEllipsisAfter) {
                %>
                <li class="page-item disabled">
                    <span class="page-link">&hellip;</span>
                </li>
                <li class="page-item">
                    <a class="page-link" href="book?page=<%= numberOfPage %>" aria-label="Last">
                        <span aria-hidden="true"><%= numberOfPage %></span>
                    </a>
                </li>
                <%
                    }
                %>
                <li class="page-item">
                    <a class="page-link next" href="#" aria-label="Next">
                        <span aria-hidden="true">&raquo;</span>
                    </a>
                </li>
            </ul>
        </nav>

        <jsp:include page="footer.jsp"></jsp:include>

            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js"
                    integrity="sha384-HwwvtgBNo3bZJJLYd8oVXjrBZt8cqVSpeBNS5n7C8IVInixGAoxmnlMuBnhbgrkm"
            crossorigin="anonymous"></script>
            <script type="text/javascript">
                                        const previousBtn = document.querySelector(".page-link.previous");
                                        const nextBtn = document.querySelector(".page-link.next");
                                        const currentPage = document.querySelector(".page-item.active .page-link").href.split("=")[1];

                                        if ((parseInt(currentPage) - 1) !== 0) {
                                            previousBtn.href = "book?page=" + (parseInt(currentPage) - 1);
                                        } else {
                                            previousBtn.href = "#";
                                        }
                                        if (parseInt(currentPage) < <%= session.getAttribute("numberOfPage") %>) {
                                            nextBtn.href = "book?page=" + (parseInt(currentPage) + 1);
                                        } else {
                                            nextBtn.href = "#";
                                        }
        </script>
    </body>

</html>
