<%-- 
    Document   : bookdetail
    Created on : Sep 29, 2023, 10:12:43 AM
    Author     : Nhật Huy
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="models.Book"%>
<%@page import="models.Category"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.util.Locale"%>
<!DOCTYPE html>
<html lang="en" data-bs-theme="dark">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title><%= ((Book)request.getAttribute("Book")).getTitle() %></title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-4bw+/aepP/YC94hEpVNVgiZdgIC5+VKNBQNGCHeKRQN+PtmoHDEXuppvnDJzQIu9" crossorigin="anonymous">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css"
              integrity="sha512-z3gLpd7yknf1YoNbCzqRKc4qyor8gaKU1qmn+CShxbuBusANI9QpRohGBreCFkKxLhei6S9CQXFEbbKuqLg0DA=="
              crossorigin="anonymous" referrerpolicy="no-referrer" />
        <link rel="stylesheet" href="assets/css/navbar.css">
    </head>
    <body>
        <jsp:include page="navbar.jsp"></jsp:include>

            <main class="container pb-3" style="margin-top: 60px;">
            <% 
                Locale locale = new Locale("en", "US");
                NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
                Book book = (Book) request.getAttribute("Book");
                if (book != null) {
            %>
            <div class="row justify-content-center mx-1">
                <div class="card col-lg-8 py-3 position-relative">
                    <a href="home.jsp" class="btn btn-outline-warning position-absolute d-none d-md-block"
                       style="top:12px;right:8px;">Back to Home</a>
                    <div class="row g-0 align-items-center">
                        <div class="col-md-4">
                            <img src="assets/img/books/<%= book.getImageUrl() %>"
                                 class="img-fluid rounded w-100">
                        </div>
                        <div class="col-md-8">
                            <div class="card-body">
                                <h5 class="d-flex">
                                    <% 
                                        String bg[] = { "danger", "info", "success", "primary-subtle" };
                                        int i = 0;
                                        for (Category category : book.getCategories()) {
                                            if (i == book.getCategories().size()) i = 0;
                                    %>
                                    <span class="badge bg-<%= bg[i] %> me-1"><%= category.getName() %></span>
                                    <%
                                        i += 1;
                                        }
                                    %>
                                </h5>
                                <h3 class="card-title"><%= book.getTitle() %></h3>
                                <h6 class="text-white-50 fw-semibold mb-0">by <i><%= book.getAuthor() %></i></h6>
                                <div class="d-flex justify-content-between mt-4">
                                    <div class="">
                                        <span>Publisher:</span>
                                        <a><%= book.getPublisher() %></a>
                                    </div>
                                    <div class="">
                                        <span>Cover type:</span>
                                        <a><%= book.getCoverType().getName() %></a>
                                    </div>
                                </div>
                                <h3 class="card-text py-3 m-0" style="color: #ee4d2d;"><%= book.getPrice() != 0 ? currencyFormatter.format(book.getPrice()) : "Free" %></h3>
                                <%= book.getDescription() %>
                                <p class="card-text text-white-50">Publication date: <%= book.getYearOfPublication() %></p>
                                <div class="">
                                    <button onclick="confirmAddBook(<%= book.getId() %>)" class="w-100 btn btn-success">Add to Cart</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <% } else { %>
            <div class="text-center">
                <h1> Book is not exist!</h1>
            </div>
            <% } %>
        </main>

        <jsp:include page="footer.jsp"></jsp:include>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-HwwvtgBNo3bZJJLYd8oVXjrBZt8cqVSpeBNS5n7C8IVInixGAoxmnlMuBnhbgrkm"
        crossorigin="anonymous"></script>
    </body>

</html>