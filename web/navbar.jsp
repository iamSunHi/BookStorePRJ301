<%-- 
    Document   : navbar.jsp
    Created on : Sep 22, 2023, 7:35:52 PM
    Author     : Nhật Huy
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="models.User"%>
<%@page import="models.Cart"%>
<%@page import="models.Book"%>
<%@page import="models.Category"%>
<%@page import="java.util.List"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.util.Locale"%>
<nav class="navbar navbar-expand-lg bg-body-tertiary fixed-top">
    <div class="container-fluid">
        <a class="navbar-brand" href="home.jsp">
            <img id="logo-img" src="assets/img/MyLogo.png" class="img-thumbnail" alt="Logo">
        </a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav"
                aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse justify-content-between align-items-center" id="navbarNav">
            <ul class="navbar-nav">
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="book?page=1">Home</a>
                </li>
                <% 
                    if (session.getAttribute("Categories") != null) {
                        List<Category> categoryList = (List<Category>) session.getAttribute("Categories"); 
                %>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown"
                       aria-expanded="false">
                        Category
                    </a>
                    <div class="dropdown-menu" style="min-width:400px;">
                        <div class="container">
                            <div class="row">
                                <% for (Category category : categoryList) { %>
                                <a href="book?method=category&category=<%= category.getName().trim().replaceAll(" ", "_") %>" class="col-3 link-body-emphasis link-offset-2 link-offset-2-hover link-underline link-underline-opacity-0 link-underline-opacity-75-hover">
                                    <%= category.getName() %>
                                </a>
                                <% } %>
                            </div>
                        </div>
                    </div>
                </li>
                <% } %>
                <% if (session.getAttribute("isAdmin") != null && (boolean) session.getAttribute("isAdmin")) { %>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                        Manage
                    </a>
                    <ul class="dropdown-menu">
                        <li><a class="dropdown-item" href="category">Category</a></li>
                        <li><a class="dropdown-item" href="covertype">Cover Type</a></li>
                        <li><a class="dropdown-item" href="book?method=seller&page=1">Books</a></li>
                        <li><hr class="dropdown-divider"></li>
                        <li><a class="dropdown-item" href="user?method=getall">Users</a></li>
                        <li><hr class="dropdown-divider"></li>
                        <li><a class="dropdown-item" href="#">Pending Confirmation</a></li>
                    </ul>
                </li>
                <% } else if (session.getAttribute("isSeller") != null && (boolean) session.getAttribute("isSeller")) { %>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                        Manage
                    </a>
                    <ul class="dropdown-menu">
                        <li><a class="dropdown-item" href="book?method=seller&page=1">Books</a></li>
                    </ul>
                </li>
                <% } %>
            </ul>
            <div class="col-lg-6 navbar-nav d-none d-lg-flex justify-content-between align-items-center">
                <div class="navbar-search" onblur="hideSearchResults(this);">
                    <div class="navbar-search-bar p-2 d-flex align-items-center bg-dark">
                        <div class="navbar-search__input d-flex align-items-center">
                            <i class="fa-solid fa-magnifying-glass p-2 rounded-circle bg-light text-dark"></i>
                            <input id="searchInput" type="text" class="w-100 border-0 bg-dark mx-2" placeholder="Search something . . ." onkeyup="showSearchResults(this);">
                        </div>
                        <%--<button class="btn btn-dark rounded-pill">
                            Search
                        </button>--%>
                    </div>
                    <div class="navbar-search__result bg-dark shadow">
                        <div class="text-center py-2">
                            <svg class="wrapper" viewBox="25 25 50 50">
                            <circle r="20" cy="50" cx="50"></circle>
                            </svg>
                        </div>
                        <div class="navbar-search__result--has-result">
                            <div class="list-group">
                            </div>
                        </div>
                        <div class="navbar-search__result--no-result text-center">
                            <img src="assets/img/no_result.png" alt="No result found!">
                        </div>
                    </div>
                </div>
                <%
                    User user = (User) session.getAttribute("user");
                    if (user != null) {
                %>
                <li class="nav-item ms-3">
                    <div class="shopping-cart p-2 d-none d-lg-block">
                        <i class="fa-solid fa-cart-shopping"></i>
                        <div class="shopping-cart__wrapper bg-dark shadow rounded-3">
                            <div class="shopping-cart__wrapper__header">
                                <h6 class="mx-2 my-1">Added books</h6>
                            </div>
                            <div class="shopping-cart__wrapper__body">
                                <%
                                    Locale locale = new Locale("en", "US");
                                    NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
                                    Cart userCart = (Cart) session.getAttribute("userCart");
                                    List<Book> userBookList = userCart.getBookList();
                                    if (userBookList != null && userBookList.size() > 0) {
                                %>
                                <ul class="shopping-cart__wrapper__body__list m-0">
                                    <% for (Book book : userBookList) { %>
                                    <li
                                        class="shopping-cart__wrapper__body__list-item d-flex align-items-center p-1">
                                        <div class="shopping-cart__wrapper__body__list-item__remove m-1">
                                            <i class="fa-solid fa-x"></i>
                                            <button onclick="confirmRemoveBook(<%= book.getId() %>)" class="btn btn-danger py-0">
                                                Remove
                                            </button>
                                        </div>
                                        <img class="shopping-cart__wrapper__body__list-item__img border rounded"
                                             src="assets/img/books/<%= book.getImageUrl() %>" style="width:100%;height:auto;">
                                        <div class="ms-1 w-100">
                                            <a href=""
                                               class="shopping-cart__wrapper__body__list-item__name nav-link p-0">
                                                <%= book.getTitle() %>
                                            </a>
                                            <div class="d-flex align-items-center justify-content-between">
                                                <span class="shopping-cart__wrapper__body__list-item__author">
                                                    Author: <i><%= book.getAuthor() %></i>
                                                </span>
                                                <div
                                                    class="shopping-cart__wrapper__body__list-item__price mx-1 text-end">
                                                    <b><%= book.getPrice() != 0 ? currencyFormatter.format(book.getPrice()) : "Free" %></b>
                                                </div>
                                            </div>
                                        </div>
                                    </li>
                                    <% } %>
                                </ul>
                                <% } else { %>
                                <div class="text-center">
                                    <h4 class="m-0 py-4">No thing here . . .</h4>
                                </div>
                                <% } %>
                            </div>
                            <div class="shopping-cart__wrapper__footer">
                                <a class="btn btn-dark shopping-cart__wrapper__footer__order" href="summary.jsp">
                                    <h5 class="m-0">Order</h5>
                                </a>
                            </div>
                        </div>
                    </div>
                    <div class="d-lg-none">
                        <a class="nav-link" href="">Cart</a>
                    </div>
                </li>
                <% } %>
            </div>

            <% if (user == null) { %>
            <ul class="navbar-nav">
                <li class="nav-item">
                    <a class="nav-link" href="register.jsp">Register</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="login.jsp">Login</a>
                </li>
            </ul>
            <% } else { %>
            <ul class="navbar-nav">
                <li class="nav-item dropdown-center d-flex align-items-center">
                    <a class="nav-link dropdown-toggle py-1 d-flex align-items-center" type="button" data-bs-toggle="dropdown" aria-expanded="false">
                        <img class="img-thumbnail rounded-circle" src="assets/img/user/${user.getImageUrl()}" alt="<%= user.getName() %>"
                             style="height: 31px;width: 31px;">
                        <div class="mx-2">
                            <h4 class="m-0"><%= user.getName() %></h4>
                            <% if ((boolean) session.getAttribute("isAdmin")) { %>
                            <h6 class="m-0 text-danger text-uppercase">administrator</h6>
                            <% } else if ((boolean) session.getAttribute("isSeller")) { %>
                            <h6 class="m-0 text-success text-uppercase">seller</h6>
                            <% } %>
                        </div>
                    </a>
                    <ul class="dropdown-menu shadow menu" style="left: unset; right: 0;">
                        <li class="menu-item">
                            <a href="account.jsp" id="account" class="btn btn-dark dropdown-item text-uppercase">Account</a>
                        </li>
                        <% if (!((boolean) session.getAttribute("isSeller"))) { %>
                        <li>
                            <hr class="dropdown-divider">
                        </li>
                        <li class="menu-item">
                            <a href="" class="btn btn-dark dropdown-item text-uppercase">Register to open sale</a>
                        </li>
                        <% } %>
                        <li>
                            <hr class="dropdown-divider">
                        </li>
                        <li class="menu-item">
                            <a href="identity?type=LOGOUT" class="btn btn-dark dropdown-item text-uppercase">Logout</a>
                        </li>
                    </ul>
                </li>
            </ul>
            <% } %>
        </div>
    </div>
</nav>

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
                                                function confirmAddBook(bookId) {
                                                    Swal.fire({
                                                        title: 'Are you sure?',
                                                        text: "You can remove later!",
                                                        icon: 'question',
                                                        showCancelButton: true,
                                                        confirmButtonColor: '#3085d6',
                                                        cancelButtonColor: '#d33',
                                                        confirmButtonText: 'Yes, add it!',
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
                                                            window.location.href = 'cart?method=add&bookId=' + bookId;
                                                        }
                                                    });
                                                }
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
                                                            window.location.href = 'cart?method=remove&bookId=' + bookId;
                                                        }
                                                    });
                                                }

                                                let debounceTimer;

                                                function debounceSearch() {
                                                    clearTimeout(debounceTimer);
                                                    document.querySelector(".navbar-search__result--has-result .list-group").innerHTML = "";
                                                    document.querySelector('.navbar-search__result--no-result').removeAttribute('style');
                                                    document.querySelector(".wrapper").setAttribute('style', 'display: inline');
                                                    debounceTimer = setTimeout(performSearch, 300);
                                                }

                                                function performSearch() {
                                                    const searchTerm = document.getElementById("searchInput").value;
                                                    const resultElement = document.querySelector(".navbar-search__result--has-result .list-group");

                                                    if (searchTerm !== "") {
                                                        // AJAX
                                                        var request = new XMLHttpRequest();
                                                        request.open("GET", "book?method=search&content=" + searchTerm);

                                                        request.onreadystatechange = function () {
                                                            document.querySelector(".wrapper").removeAttribute('style');
                                                            if (this.readyState === 4 && this.status === 200) {
                                                                var response = JSON.parse(this.responseText);
                                                                if (response.length > 0) {
                                                                    document.querySelector('.navbar-search__result--no-result').removeAttribute('style');
                                                                    response.forEach(book => {
                                                                        resultElement.innerHTML += "<a href=book?method=get&bookId=" + book.id + " class='list-group-item list-group-item-action border-0'>" + book.title + "</a>";
                                                                    });
                                                                } else {
                                                                    document.querySelector('.navbar-search__result--no-result').setAttribute('style', 'display: block');
                                                                }
                                                            }
                                                        };

                                                        // Gửi yêu cầu đến máy chủ
                                                        request.send();
                                                    } else {
                                                        document.querySelector(".wrapper").removeAttribute('style');
                                                    }
                                                }
                                                const searchBar = document.querySelector('.navbar-search-bar');
                                                const searchResults = document.querySelector('.navbar-search__result');
                                                function showSearchResults(e) {
                                                    const searchInput = e.value;
                                                    if (searchInput !== '') {
                                                        debounceSearch();
                                                        searchBar.classList.add('active');
                                                        searchResults.classList.add('active');
                                                    } else {
                                                        hideSearchResults();
                                                    }
                                                }
                                                function hideSearchResults() {
                                                    searchBar.classList.remove('active');
                                                    searchResults.classList.remove('active');
                                                }

</script>