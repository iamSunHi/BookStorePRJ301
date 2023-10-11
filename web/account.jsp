<%-- 
    Document   : home
    Created on : Sep 22, 2023, 7:36:54 PM
    Author     : Nhật Huy
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="models.User"%>
<!DOCTYPE html>
<html lang="en" data-bs-theme="dark">
    <%
        User user = (User) session.getAttribute("user");
    %>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title><%= user.getName() %></title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-4bw+/aepP/YC94hEpVNVgiZdgIC5+VKNBQNGCHeKRQN+PtmoHDEXuppvnDJzQIu9" crossorigin="anonymous">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css"
              integrity="sha512-z3gLpd7yknf1YoNbCzqRKc4qyor8gaKU1qmn+CShxbuBusANI9QpRohGBreCFkKxLhei6S9CQXFEbbKuqLg0DA=="
              crossorigin="anonymous" referrerpolicy="no-referrer" />
        <link rel="stylesheet" href="assets/css/navbar.css">
        <link rel="stylesheet" href="assets/css/account.css">
    </head>

    <body>
        <jsp:include page="./navbar.jsp"></jsp:include>
            <main class="container pb-3" style="margin-top: 60px;">
                <div class="row">
                    <div class="d-none d-lg-block col-lg-3">
                        <div class="account-sidebar p-0 border shadow">
                            <div class="account-sidebar__header border-bottom">
                                <h3 class="p-2 m-0 text-uppercase">Account</h3>
                            </div>
                            <div class="account-sidebar__list d-flex flex-column">
                                <a onclick="changeAccountTab(this)" class="account-sidebar__list-item active" aria-current="true">
                                    <i class="fa-solid fa-circle-user me-1"></i>
                                    Personal Information
                                </a>
                                <a onclick="changeAccountTab(this)" class="account-sidebar__list-item">
                                    <i class="fa-solid fa-lock me-1"></i>
                                    Login & Password
                                </a>
                                <a onclick="changeAccountTab(this)" class="account-sidebar__list-item">
                                    <i class="fa-solid fa-gears me-1"></i>
                                    Settings
                                </a>
                                <a href="identity?type=LOGOUT" class="account-sidebar__list-item">
                                    <i class="fa-solid fa-right-from-bracket me-1"></i>
                                    Logout
                                </a>
                            </div>
                        </div>
                    </div>

                    <div class="col-12 col-lg-9 account-info">
                        <form id="personal-info" class="account-info__personal-info active" action="user" method="post">
                            <input type="hidden" name="method" value="info">
                            <input type="hidden" name="id" value="${user.getId()}">
                        <div class="row">
                            <div class="col-9">
                                <h4>Personal Information</h4>
                                <div class="mb-3">
                                    <label class="fw-semibold" for="username">
                                        <i class="fa-solid fa-user"></i>
                                        Username
                                    </label>
                                    <input type="text" class="form-control border-0 border-bottom outline-none shadow-none" id="username"
                                           placeholder="Username" name="username" value="<%= user.getUsername() %>">
                                </div>
                                <div class="mb-3">
                                    <label class="fw-semibold" for="name">
                                        Full Name
                                    </label>
                                    <input type="text" class="form-control border-0 border-bottom outline-none shadow-none" id="name"
                                           placeholder="Name" name="fullname" value="<%= user.getName() %>">
                                </div>
                                <div class="mb-3">
                                    <label class="fw-semibold" for="email">
                                        <i class="fa-solid fa-envelope"></i>
                                        Email
                                    </label>
                                    <input type="email" class="form-control border-0 border-bottom outline-none shadow-none" id="email"
                                           placeholder="Email" name="email" value="<%= user.getEmail().equals("null") ? "" : user.getEmail() %>">
                                </div>
                                <div class="mb-3">
                                    <label class="fw-semibold" for="phonenumber">
                                        <i class="fa-solid fa-phone"></i>
                                        Phone Number
                                    </label>
                                    <input type="text" class="form-control border-0 border-bottom outline-none shadow-none" id="phonenumber"
                                           placeholder="Phone Number" name="phonenumber" value="<%= user.getPhone().equals("null") ? "" : user.getPhone() %>">
                                </div>
                                <div class="mb-3">
                                    <label class="fw-semibold" for="address">
                                        <i class="fa-solid fa-location-dot"></i>
                                        Address
                                    </label>
                                    <input type="text" class="form-control border-0 border-bottom outline-none shadow-none" id="address"
                                           placeholder="Address" name="address" value="<%= user.getAddress().equals("null") ? "" : user.getAddress() %>">
                                </div>
                            </div>
                            <div class="col-3">
                                <img src="assets/img/user/${user.getImageUrl()}" class="img-thumbnail">
                                <button type="button" class="btn btn-outline-light mt-1 w-100" data-bs-toggle="modal"
                                        data-bs-target="#uploadAvatar">Change Avatar</button>
                            </div>
                            <button type="submit" class="btn btn-outline-light save-btn w-50">
                                Save
                            </button>
                        </div>
                    </form>
                    <form id="login-info" class="account-info__login-info" action="user" method="post">
                        <h4>Login Information</h4>
                        <input type="hidden" name="method" value="login">
                        <input type="hidden" name="id" value="${user.getId()}">
                        <div class="mb-3">
                            <label class="fw-semibold" for="oldpassword">
                                Old Password
                            </label>
                            <input type="password" class="form-control border-0 border-bottom outline-none shadow-none" id="oldpassword"
                                   placeholder="Old Password" name="oldpassword">
                        </div>
                        <div class="mb-3">
                            <label class="fw-semibold" for="newpassword">
                                New Password
                            </label>
                            <input id="newpassword" type="password" class="form-control border-0 border-bottom outline-none shadow-none" id="newpassword"
                                   placeholder="New Password" name="newpassword">
                        </div>
                        <div class="mb-3">
                            <label class="fw-semibold" for="confirmpassword">
                                Confirm New Password
                            </label>
                            <input type="password" class="form-control border-0 border-bottom outline-none shadow-none"
                                   id="confirmpassword" placeholder="Confirm New Password" name="confirmpassword">
                        </div>
                        <div class="row">
                            <button type="submit" class="btn btn-outline-light save-btn w-50">
                                Save
                            </button>
                        </div>
                    </form>
                    <form class="account-info__setting">
                        <h2>Settings</h2>
                        <input type="hidden" name="method" value="setting">
                        <input type="hidden" name="id" value="${user.getId()}">
                        <h4>The feature is not supported currently or will never be :D</h4>
                    </form>
                </div>
            </div>
        </main>

        <div class="modal fade" id="uploadAvatar" tabindex="-1" aria-labelledby="uploadAvatarLabel" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header">
                        <h1 class="modal-title fs-5" id="uploadAvatarLabel">Update Avatar</h1>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <form action="image?for=user" method="post" enctype="multipart/form-data" class="row">
                            <div class="p-3">
                                <div class="input-group mb-3">
                                    <input name="image" type="file" class="form-control" id="image">
                                    <label class="input-group-text" for="image">Upload Avatar</label>
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

        <jsp:include page="footer.jsp"></jsp:include>

        <jsp:include page="validationScripts.jsp"></jsp:include>
            <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
            <script src="assets/javascript/account.js"></script>
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
