<%-- 
    Document   : bookedit
    Created on : Oct 4, 2023, 8:00:31 PM
    Author     : Nhật Huy
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="models.Book"%>
<%@page import="models.Category"%>
<%@page import="models.CoverType"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<!DOCTYPE html>
<html lang="en" data-bs-theme="dark">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title><%= request.getAttribute("Book") == null ? "Add Book" : ((Book)request.getAttribute("Book")).getTitle() %></title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-4bw+/aepP/YC94hEpVNVgiZdgIC5+VKNBQNGCHeKRQN+PtmoHDEXuppvnDJzQIu9" crossorigin="anonymous">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css"
              integrity="sha512-z3gLpd7yknf1YoNbCzqRKc4qyor8gaKU1qmn+CShxbuBusANI9QpRohGBreCFkKxLhei6S9CQXFEbbKuqLg0DA=="
              crossorigin="anonymous" referrerpolicy="no-referrer" />
        <script src="https://cdn.tiny.cloud/1/g4t4lpw49wbyvwwpv8yjtn4h8fptfswo2lmvweymaqjxgkzo/tinymce/6/tinymce.min.js"
        referrerpolicy="origin"></script>
        <link rel="stylesheet" href="assets/css/navbar.css">
    </head>
    <body>
        <% if (!((boolean) session.getAttribute("isAdmin")) && !((boolean) session.getAttribute("isSeller"))) { 
            response.sendRedirect("../accessdenied.jsp");
            return;
        } %>
        
        <% 
            List<Category> categoryList = (List<Category>) session.getAttribute("Categories");
            List<CoverType> coverTypeList = (List<CoverType>) session.getAttribute("CoverTypes");
            List<Category> bookCategoryList = null;
            if (request.getAttribute("Book") != null) {
                bookCategoryList = ((Book)request.getAttribute("Book")).getCategories();
            }
        %>

        <jsp:include page="../navbar.jsp"></jsp:include>

            <main class="container pb-3" style="margin-top: 60px;">
                <div class="row justify-content-center mx-1">
                    <div class="card col-lg-10 py-3 position-relative">
                        <a href="book?method=seller&page=1" class="btn btn-warning position-absolute d-none d-md-block"
                           style="z-index:1;top:12px;<%= request.getAttribute("Book") == null ? "right" : "left"%>:8px;">Back</a>
                        <div class="row g-0 align-items-center">
                        <% if (request.getAttribute("Book") != null) { %>
                        <div class="col-md-4">
                            <img alt="<%= ((Book)request.getAttribute("Book")).getTitle() %>" src="assets/img/books/<%= ((Book)request.getAttribute("Book")).getImageUrl() %>"
                                 class="img-fluid rounded w-100">
                            <button type="button" class="btn btn-outline-light mt-1 w-100" data-bs-toggle="modal"
                                    data-bs-target="#uploadImage">Change Image</button>
                        </div>
                        <% } %>
                        <div class="<%= request.getAttribute("Book") == null ? "col-md-12" : "col-md-8"%>">
                            <div class="card-body">
                                <form class="" method="post" action="book?method=<%= request.getAttribute("Book") == null ? "add" : "update" %>">
                                    <input type="hidden" name="id" class="form-control" value="<%= request.getAttribute("Book") == null ? "" : ((Book)request.getAttribute("Book")).getId() %>">
                                    <div class="form-floating mb-3">
                                        <input type="text" name="title" class="form-control" id="title" placeholder="Title"
                                               value="<%= request.getAttribute("Book") == null ? "" : ((Book)request.getAttribute("Book")).getTitle() %>">
                                        <label for="title">Title</label>
                                    </div>
                                    <div class="description mb-3">
                                        <label for="description" style="padding-left: 7.5px;">Description</label>
                                        <textarea name="description" class="form-control" placeholder="Description" id="description">
                                            <%= request.getAttribute("Book") == null ? "" : ((Book)request.getAttribute("Book")).getDescription() %>
                                        </textarea>
                                    </div>
                                    <div class="categories mb-3">
                                        <div class="selected-categories d-flex justify-content-between align-items-center">
                                            <div class="selected-category-list">
                                                <% if (bookCategoryList != null)
                                                    for (Category category : bookCategoryList) { %>
                                                <span class="selected-category me-1 badge text-bg-success" title="<%= category.getName() %>">
                                                    <%= category.getName() %>
                                                    <input class="form-check-input" type="checkbox" name="category" value="<%= category.getName() %>" checked hidden>
                                                </span>
                                                <% } %>
                                            </div>
                                            <button type="button"
                                                    class="btn btn-outline-light dropdown-toggle dropdown-toggle-split"
                                                    data-bs-toggle="dropdown" aria-expanded="false">
                                                Categories
                                            </button>
                                            <ul class="dropdown-menu dropdown-menu-end" style="max-height: 150px; overflow-y: scroll;">
                                                <% for (Category category : categoryList) { %>
                                                <li><a onclick="selectCategory(this)" class="dropdown-item"
                                                       href="#"><%= category.getName() %></a></li>
                                                    <% } %>
                                            </ul>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-6">
                                            <div class="form-floating mb-3">
                                                <input type="text" name="author" class="form-control" id="author"
                                                       placeholder="Author" value="<%= request.getAttribute("Book") == null ? "" : ((Book)request.getAttribute("Book")).getAuthor() %>">
                                                <label for="author">Author</label>
                                            </div>
                                        </div>
                                        <div class="col-6">
                                            <div class="form-floating mb-3">
                                                <input type="text" name="publisher" class="form-control" id="publisher"
                                                       placeholder="Publisher" value="<%= request.getAttribute("Book") == null ? "" : ((Book)request.getAttribute("Book")).getPublisher() %>">
                                                <label for="publisher">Publisher</label>
                                            </div>
                                        </div>
                                    </div>
                                    <select class="form-select mb-3" name="covertype">
                                        <% for (CoverType coverType : coverTypeList) { %>
                                        <% if (request.getAttribute("Book") != null && coverType.getName().equals(((Book)request.getAttribute("Book")).getCoverType().getName())) { %>
                                        <option value="<%= coverType.getName() %>" selected><%= coverType.getName() %></option>
                                        <% } else { %>
                                        <option value="<%= coverType.getName() %>"><%= coverType.getName() %></option>
                                        <% } %>
                                        <% } %>
                                    </select>
                                    <div class="form-floating mb-3">
                                        <input type="text" name="price" class="form-control" id="price" placeholder="Price"
                                               value="<%= request.getAttribute("Book") == null ? "" : ((Book)request.getAttribute("Book")).getPrice() %>">
                                        <label for="price">Price</label>
                                    </div>
                                    <div class="form-floating mb-3">
                                        <input type="text" name="publicationDate" class="form-control" id="publication-year"
                                               placeholder="Publication year" value="<%= request.getAttribute("Book") == null ? "" : ((Book)request.getAttribute("Book")).getYearOfPublication() %>">
                                        <label for="publication-year">Publication year</label>
                                    </div>
                                    <button class="btn btn-outline-success w-100 text-uppercase"><%= request.getAttribute("Book") == null ? "Add" : "Save" %></button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </main>

        <% if (request.getAttribute("Book") != null) { %>
        <div class="modal fade" id="uploadImage" tabindex="-1" aria-labelledby="uploadImageLabel" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header">
                        <h1 class="modal-title fs-5" id="uploadAvatarLabel">Update Image</h1>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <form action="image?for=book" method="post" enctype="multipart/form-data" class="row">
                            <input type="hidden" name="id" class="form-control" value="<%= request.getAttribute("Book") == null ? "" : ((Book)request.getAttribute("Book")).getId() %>">
                            <div class="p-3">
                                <div class="input-group mb-3">
                                    <input name="image" type="file" class="form-control" id="image">
                                    <label class="input-group-text" for="image">Upload Image</label>
                                </div>
                                <div class="row mt-3">
                                    <div class="col-md-6 col-12 mb-lg-0 mb-3">
                                        <button type="submit" class="btn btn-success form-control">
                                            Save
                                        </button>
                                    </div>
                                    <div class="col-md-6 col-12">
                                        <a data-bs-dismiss="modal" class="btn btn-outline-secondary form-control">
                                            Close
                                        </a>
                                    </div>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <% } %>

        <jsp:include page="../footer.jsp"></jsp:include>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-HwwvtgBNo3bZJJLYd8oVXjrBZt8cqVSpeBNS5n7C8IVInixGAoxmnlMuBnhbgrkm"
        crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.7.1/jquery.min.js"
                integrity="sha512-v2CJ7UaYy4JwqLDIrZUI/4hqeoQieOmAZNXBeQyjo21dadnwR+8ZaIJVT8EE2iyI61OV8e6M8PP2/4hpQINQ/g=="
        crossorigin="anonymous" referrerpolicy="no-referrer"></script>
        <script>
                                                    $(document).ready(function () {
                                                        const selectedCategoryList = document.querySelectorAll('.selected-category');
                                                        const categoryList = document.querySelectorAll('.selected-categories ul li a');
                                                        for (let i = 0; i < categoryList.length; i++) {
                                                            for (let category of selectedCategoryList) {
                                                                if (categoryList[i].textContent.trim() === category.textContent.trim()) {
                                                                    categoryList[i].classList.add('active');
                                                                }
                                                            }
                                                        }
                                                    });
                                                    tinymce.init({
                                                        selector: 'textarea',
                                                        plugins: 'anchor autolink charmap codesample emoticons image link lists media searchreplace visualblocks wordcount',
                                                        toolbar: 'undo redo | bold italic underline strikethrough | align lineheight | tinycomments | checklist numlist bullist indent outdent | emoticons | removeformat',
                                                        skin: "oxide-dark",
                                                        content_css: "dark",
                                                        height: '300px',
                                                    });
                                                    function selectCategory(category) {
                                                        if (!category.classList.contains('active')) {
                                                            const selectedCategoryList = document.querySelector('.selected-category-list');
                                                            const selectedCategory = document.createElement('span');
                                                            selectedCategory.textContent = category.textContent.trim();
                                                            selectedCategory.classList.add('selected-category');
                                                            selectedCategory.classList.add('me-1');
                                                            selectedCategory.classList.add('badge');
                                                            selectedCategory.classList.add('text-bg-success');
                                                            selectedCategory.innerHTML += `<input class="form-check-input" type="checkbox" name="category" value="` + category.textContent.trim() + `" checked hidden>`;
                                                            selectedCategoryList.appendChild(selectedCategory);
                                                        } else {
                                                            let selectedCategoryList = document.querySelectorAll('.selected-category');
                                                            for (let i = 0; i < selectedCategoryList.length; i++) {
                                                                if (selectedCategoryList[i].textContent.trim() === category.textContent.trim())
                                                                    selectedCategoryList[i].remove();
                                                            }
                                                        }
                                                        category.classList.toggle('active');
                                                    }
        </script>
    </body>

</html>
