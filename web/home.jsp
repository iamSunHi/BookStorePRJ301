<%-- Document : home Created on : Sep 22, 2023, 7:36:54 PM Author : Nhật Huy --%>

    <%@page contentType="text/html" pageEncoding="UTF-8" %>
        <%@page import="models.Book" %>
            <%@page import="java.util.List" %>
                <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
                    <jsp:useBean id="bookList" class="java.util.ArrayList" scope="session">
                        <jsp:setProperty name="bookList" property="*"></jsp:setProperty>
                    </jsp:useBean>
                    <!DOCTYPE html>
                    <html lang="en" data-bs-theme="dark">

                    <head>
                        <meta charset="UTF-8">
                        <meta name="viewport" content="width=device-width, initial-scale=1.0">
                        <title>Home</title>
                        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css"
                            rel="stylesheet"
                            integrity="sha384-4bw+/aepP/YC94hEpVNVgiZdgIC5+VKNBQNGCHeKRQN+PtmoHDEXuppvnDJzQIu9"
                            crossorigin="anonymous">
                        <link rel="stylesheet"
                            href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css"
                            integrity="sha512-z3gLpd7yknf1YoNbCzqRKc4qyor8gaKU1qmn+CShxbuBusANI9QpRohGBreCFkKxLhei6S9CQXFEbbKuqLg0DA=="
                            crossorigin="anonymous" referrerpolicy="no-referrer" />
                        <link rel="stylesheet" href="assets/css/navbar.css">
                        <link rel="stylesheet" href="assets/css/home.css">
                    </head>

                    <body>
                        <c:if test="${bookList == null || bookList.isEmpty()}">
                            <jsp:forward page="book?page=1"></jsp:forward>
                        </c:if>
                        <jsp:include page="navbar.jsp"></jsp:include>

                        <main class="container pb-3" style="margin-top: 60px;">
                            <div class="row book-list">
                                <c:forEach var="book" items="${bookList}">
                                    <div class="col-12 col-md-6 col-lg-3 book-list-item my-2">
                                        <a href="book?method=get&bookId=${book.getId()}">
                                            <div class="card" href="book?method=get&bookId=${book.getId()}">
                                                <img src="assets/img/books/${book.getImageUrl()}" class="card-img-top"
                                                    style="height: 300px; width: 100%;" alt="...">
                                                <div class="card-body">
                                                    <a href="book?method=get&bookId=${book.getId()}"
                                                        class="card-title book-list-item__title fs-4 text-decoration-none">
                                                        ${book.getTitle()}
                                                    </a>
                                                    <div class="d-flex align-items-baseline justify-content-between">
                                                        <h6 class="card-text book-list-item__author">Author:
                                                            <i>
                                                                ${book.getAuthor()}
                                                            </i>
                                                        </h6>
                                                        <h6 class="card-text book-list-item__price">
                                                            <c:choose>
                                                                <c:when test="${book.getPrice() > 0}">
                                                                    <fmt:formatNumber value="${book.getPrice()}"
                                                                        type="currency" />
                                                                </c:when>
                                                                <c:otherwise>
                                                                    Free
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </h6>
                                                    </div>
                                                    <div class="">
                                                        <button onclick="confirmAddBook(${book.getId()})"
                                                            class="w-100 btn btn-success">Add to Cart</button>
                                                    </div>
                                                </div>
                                            </div>
                                        </a>
                                    </div>
                                </c:forEach>
                            </div>
                        </main>

                        <nav>
                            <ul class="pagination justify-content-center pagination-lg">
                                <c:set var="currentPage" value="${sessionScope.currentPage}" />
                                <c:set var="numberOfPage" value="${sessionScope.numberOfPage}" />
                                <c:set var="maxPagesToShow" value="3" />
                                <c:set var="startPage"
                                    value="${(currentPage - maxPagesToShow / 2) > 1 ? (currentPage - maxPagesToShow / 2) : 1}" />
                                <c:set var="endPage"
                                    value="${(startPage + maxPagesToShow - 1) > numberOfPage ? numberOfPage : (startPage + maxPagesToShow - 1)}" />

                                <c:if test="${endPage - startPage + 1 < maxPagesToShow}">
                                    <c:set var="startPage"
                                        value="${(endPage - maxPagesToShow + 1) > 1 ? (endPage - maxPagesToShow + 1) : 1}" />
                                </c:if>

                                <c:if test="${showEllipsisBefore}">
                                    <li class="page-item">
                                        <a class="page-link" onclick="loadPage(1)" aria-label="First">
                                            <span aria-hidden="true">1</span>
                                        </a>
                                    </li>
                                    <li class="page-item disabled">
                                        <span class="page-link">&hellip;</span>
                                    </li>
                                </c:if>

                                <c:forEach var="i" begin="${startPage}" end="${endPage}">
                                    <li class="page-item ${currentPage == i ? " active" : "" }">
                                        <a class="page-link" onclick="loadPage(${i})">
                                            ${i}
                                        </a>
                                    </li>
                                </c:forEach>

                                <c:if test="${showEllipsisAfter}">
                                    <li class="page-item disabled">
                                        <span class="page-link">&hellip;</span>
                                    </li>
                                    <li class="page-item">
                                        <a class="page-link" onclick="loadPage(${numberOfPage})" aria-label="Last">
                                            <span aria-hidden="true">
                                                ${numberOfPage}
                                            </span>
                                        </a>
                                    </li>
                                </c:if>

                                <c:if test="${currentPage != numberOfPage}">
                                    <li class="page-item">
                                        <a class="page-link next" onclick="loadPage(2)" aria-label="Next">
                                            <span aria-hidden="true">&raquo;</span>
                                        </a>
                                    </li>
                                </c:if>
                            </ul>
                        </nav>

                        <jsp:include page="footer.jsp"></jsp:include>

                        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js"
                            integrity="sha384-HwwvtgBNo3bZJJLYd8oVXjrBZt8cqVSpeBNS5n7C8IVInixGAoxmnlMuBnhbgrkm"
                            crossorigin="anonymous"></script>
                        <script type="text/javascript">
                            // AJAX - Asynchronous Javascript and XML
                            function loadPage(page) {
                                var bookList = document.querySelector('.book-list');
                                var request = new XMLHttpRequest();
                                request.open("GET", "book?method=pagination&page=" + page);

                                request.onreadystatechange = function () {
                                    if (this.readyState === 4 && this.status === 200) {
                                        var response = JSON.parse(this.responseText);
                                        if (response.length > 0) {
                                            // Clear the existing bookList content
                                            bookList.innerHTML = '';

                                            // Add new books to the bookList
                                            for (let i = 0; i < response.length; i++) {
                                                bookList.innerHTML += `
                                                    <div class="col-12 col-md-6 col-lg-3 book-list-item my-2">
                                                        <a href="book?method=get&amp;bookId=` + response[i].id + `"></a>
                                                        <div class="card" href="book?method=get&amp;bookId=9">
                                                            <a href="book?method=get&amp;bookId=` + response[i].id + `">
                                                                <img src="assets/img/books/` + response[i].imageUrl + `" class="card-img-top" style="height: 300px; width: 100%;" alt="...">
                                                            </a>
                                                            <div class="card-body">
                                                                <a href="book?method=get&amp;bookId=` + response[i].id + `"></a>
                                                                <a href="book?method=get&amp;bookId=` + response[i].id + `" class="card-title book-list-item__title fs-4 text-decoration-none">
                                                                    Git Pocket Guide
                                                                </a>
                                                                <div class="d-flex align-items-baseline justify-content-between">
                                                                    <h6 class="card-text book-list-item__author">Author:
                                                                        <i>
                                                                            ` + response[i].author + `
                                                                        </i>
                                                                    </h6>
                                                                    <h6 class="card-text book-list-item__price">
                                                                        ` + (response[i].price === 0 ? "Free" : "$" + response[i].price) + `
                                                                    </h6>
                                                                </div>
                                                                <div class="">
                                                                    <button onclick="confirmAddBook(` + response[i].id + `)" class="w-100 btn btn-success">Add to Cart</button>
                                                                </div>
                                                            </div>
                                                        </div>              
                                                    </div>
                                                `;
                                            }

                                            const pageItems = document.querySelectorAll('.page-link');
                                            for (let pageItem of pageItems) {
                                                if (parseInt(pageItem.innerText) === parseInt(page)) {
                                                    pageItem.parentNode.classList.add('active');
                                                    const currentPage = parseInt(page);
                                                    updatePagination(currentPage);
                                                } else {
                                                    if (pageItem.parentNode.classList.contains('active')) {
                                                        pageItem.parentNode.classList.remove('active');
                                                    }
                                                }
                                            }
                                        }
                                    }
                                };

                                // Send request to server
                                request.send();
                            }

                            function updatePagination(currentPage) {
                                const numberOfPage = ${ numberOfPage };
                                if (currentPage !== 1) {
                                    addPreviousButton(currentPage);
                                } else {
                                    removePreviousButton();
                                }

                                if (currentPage < numberOfPage) {
                                    addNextButton(currentPage);
                                } else {
                                    removeNextButton();
                                }
                            }

                            function addPreviousButton(currentPage) {
                                let previousBtn = document.querySelector(".page-link.previous");
                                if (previousBtn === null) {
                                    previousBtn = document.createElement("li");
                                    previousBtn.classList.add("page-item");
                                    previousBtn.innerHTML = `
                                        <a class="page-link previous" aria-label="Previous">
                                            <span aria-hidden="true">&laquo;</span>
                                        </a>
                                    `;
                                    document.querySelector(".pagination").insertBefore(previousBtn, document.querySelector(".pagination li:first-child"));
                                }
                                previousBtn = document.querySelector(".page-link.previous");
                                previousBtn.setAttribute("onclick", "loadPage(" + (currentPage - 1) + ")");
                            }

                            function removePreviousButton() {
                                const previousBtn = document.querySelector(".page-link.previous");
                                if (previousBtn) {
                                    previousBtn.parentNode.remove();
                                }
                            }

                            function addNextButton(currentPage) {
                                let nextBtn = document.querySelector(".page-link.next");
                                if (nextBtn === null) {
                                    nextBtn = document.createElement("li");
                                    nextBtn.classList.add("page-item");
                                    nextBtn.innerHTML = `
    <a class="page-link next" aria-label="Next">
        <span aria-hidden="true">&raquo;</span>
    </a>
    `;
                                    document.querySelector(".pagination").appendChild(nextBtn);
                                }
                                nextBtn = document.querySelector(".page-link.next");
                                nextBtn.setAttribute("onclick", "loadPage(" + (currentPage + 1) + ")");
                            }

                            function removeNextButton() {
                                const nextBtn = document.querySelector(".page-link.next");
                                if (nextBtn) {
                                    nextBtn.parentNode.remove();
                                }
                            }

                            // Initialize pagination when the page loads
                            window.onload = function () {
                                updatePagination(1); // Replace 1 with the initial page number
                            }
                        </script>

                    </body>

                    </html>