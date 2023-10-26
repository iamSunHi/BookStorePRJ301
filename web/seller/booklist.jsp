<%-- 
    Document   : booklist
    Created on : Sep 29, 2023, 4:00:51 PM
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
        <% if (!((boolean) session.getAttribute("isAdmin")) && !((boolean) session.getAttribute("isSeller"))) { 
            response.sendRedirect("../accessdenied.jsp");
            return;
        } %>
        
        <jsp:include page="../navbar.jsp"></jsp:include>

            <main class="container pb-3" style="margin-top: 60px;">
                <div class="w-100 text-center">
                    <h1>Book List</h1>
                </div>
                <div class="text-end">
                    <a class="btn btn-light" href="book?role=seller&method=get">
                        <i class="fa-solid fa-circle-plus"></i>
                        Add New Book
                    </a>
                </div>
                <div class="row book-list">
                <% 
                    if (request.getAttribute("bookList") != null && ((List<Book>) request.getAttribute("bookList")).size() > 0) {
                    Locale locale = new Locale("en", "US");
                    NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
                %>
                <table class="table table-dark table-striped">
                    <thead>
                        <tr>
                            <th class="text-center">Title</th>
                            <th class="text-center">Author</th>
                            <th class="text-center">Price</th>
                            <th class="text-center">Publisher</th>
                            <th class="text-center"></th>
                        </tr>
                    </thead>
                    <tbody>
                        <% for (Book book : (List<Book>) request.getAttribute("bookList")) { %>
                        <tr>
                            <td>
                                <%= book.getTitle() %>
                            </td>
                            <td class="text-center">
                                <%= book.getAuthor() %>
                            </td>
                            <td class="text-center" style="color: #ee4d2d;">
                                <%= book.getPrice() != 0 ? currencyFormatter.format(book.getPrice()) : "Free" %>
                            </td>
                            <td class="text-center">
                                <%= book.getPublisher() %>
                            </td>
                            <td>
                                <div class="btn-group d-flex" role="group">
                                    <a href="book?role=seller&method=get&bookId=<%= book.getId() %>" class="btn btn-primary mx-2">
                                        <i class="fa-solid fa-pencil"></i> Edit
                                    </a>
                                    <a onclick="confirmRemoveBook(<%= book.getId() %>)" class="btn btn-danger mx-2">
                                        <i class="fa-solid fa-trash"></i> Delete
                                    </a>
                                </div>
                            </td>
                        </tr>
                        <% } %>
                    </tbody>
                </table>
                <% } %>
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
