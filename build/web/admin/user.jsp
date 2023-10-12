<%-- 
    Document   : user
    Created on : Oct 11, 2023, 2:59:53 PM
    Author     : Sun Hi
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="models.User"%>
<%@page import="models.Role"%>
<!DOCTYPE html>
<html lang="en" data-bs-theme="dark">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Users</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-4bw+/aepP/YC94hEpVNVgiZdgIC5+VKNBQNGCHeKRQN+PtmoHDEXuppvnDJzQIu9" crossorigin="anonymous">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css"
              integrity="sha512-z3gLpd7yknf1YoNbCzqRKc4qyor8gaKU1qmn+CShxbuBusANI9QpRohGBreCFkKxLhei6S9CQXFEbbKuqLg0DA=="
              crossorigin="anonymous" referrerpolicy="no-referrer" />

        <link rel="stylesheet" href="assets/css/navbar.css">
        <link rel="stylesheet" href="assets/css/home.css">
    </head>

    <body>
        <jsp:include page="../navbar.jsp"></jsp:include>

            <main class="container" style="margin-top: 60px;">
                <div class="card shadow border-0 pt-4">
                    <div class="card-header bg-dark bg-gradient m-lg-0 py-3">
                        <div class="row">
                            <div class="col-12 text-center">
                                <h2 class="text-white py-2">User List</h2>
                            </div>
                        </div>
                    </div>
                    <div class="card-body">
                    <% if (request.getAttribute("UserList") != null && ((List<User>) request.getAttribute("UserList")).size() >=1) { %>
                    <%
                        List<User> userList = (List<User>) request.getAttribute("UserList");
                    %>
                    <table class="table table-dark table-striped table-bordered">
                        <thead>
                            <tr>
                                <th class="text-center">
                                    User Name
                                </th>
                                <th class="text-center">
                                    Name
                                </th>
                                <th class="text-center">
                                    Email
                                </th>
                                <th class="text-center">
                                    Phone
                                </th>
                                <th class="text-center">
                                    Role
                                </th>
                                <th class="text-center">Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <% for (User user : userList) { %>
                            <tr>
                                <td>
                                    <%= user.getUsername() %>
                                </td>
                                <td>
                                    <%= user.getName() %>
                                </td>
                                <td>
                                    <%= user.getEmail() %>
                                </td>
                                <td>
                                    <%= user.getPhone() %>
                                </td>
                                <td>
                                    <% for (Role role : user.getRoleList()) { %>
                                    <span><%= role.getName() %></span><br>
                                    <% } %>
                                </td>
                                <td>
                                    <div class="btn-group d-flex align-items-center" role="group">
                                        <a onclick="getUser(this)" class="btn btn-primary mx-2" data-bs-toggle="modal" data-bs-target="#permission">
                                            <input type="text" class="userId" value="<%= user.getId() %>" hidden>
                                            <input type="text" class="userName" value="<%= user.getName() %>" hidden>
                                            <input type="text" class="userRoles" value="<% for (Role role : user.getRoleList()) { %><%= role.getName() + ";" %><% } %>" hidden>
                                            <i class="fa-solid fa-pen-to-square"></i> Permission
                                        </a>
                                        <a onclick="getUser(this)" class="btn btn-danger mx-2" data-bs-toggle="modal" data-bs-target="#delete">
                                            <input type="text" class="userId" value="<%= user.getId() %>" hidden>
                                            <input type="text" class="userName" value="<%= user.getName() %>" hidden>
                                            <input type="text" class="userRoles" value="<% for (Role role : user.getRoleList()) { %><%= role.getName() + ";" %><% } %>" hidden>
                                            <i class="fa-solid fa-trash"></i> Delete
                                        </a>
                                    </div>
                                </td>
                            </tr>
                            <% } %>
                        </tbody>
                    </table>
                    <% } else { %>
                    <center><h1>No user here . . .</h1></center>
                        <% } %>
                </div>
            </div>

            <!-- Modal -->
            <div class="modal fade" id="permission" tabindex="-1" aria-labelledby="permissionLabel" aria-hidden="true">
                <div class="modal-dialog modal-dialog-centered">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h1 class="modal-title fs-5" id="editLabel">Permission of User</h1>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <form action="user" method="post" class="row">
                                <input type="hidden" name="method" value="updaterole">
                                <div class="p-3">
                                    <div class="form-floating mb-3">
                                        <input name="id" type="text" class="userId" hidden>
                                        <input name="name" type="text" class="form-control" id="userName"
                                               placeholder="User's Name">
                                        <label for="userName">Name</label>
                                    </div>
                                    <div class="">
                                        <label for="roles">Role (Hold Ctrl for multiple select, the default will be CUSTOMER if you don't choose anything)</label>
                                        <select name="roles" class="form-select userRoles" multiple>
                                            <option value="CUSTOMER" style="padding: 4px 8px; margin-top: 2px;">CUSTOMER</option>
                                            <option value="SELLER" style="padding: 4px 8px; margin-top: 2px;">SELLER</option>
                                            <option value="ADMIN" style="padding: 4px 8px; margin-top: 2px;">ADMIN</option>
                                        </select>
                                    </div>
                                    <div class="row mt-3">
                                        <div class="col-md-6 col-12 mb-lg-0 mb-3">
                                            <button type="submit" class="btn btn-success form-control">
                                                Save changes
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
            <div class="modal fade" id="delete" tabindex="-1" aria-labelledby="deleteLabel" aria-hidden="true">
                <div class="modal-dialog modal-dialog-centered">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h1 class="modal-title fs-5" id="editLabel">Delete User</h1>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <form action="user" method="post" class="row">
                                <input type="hidden" name="method" value="delete">
                                <div class="p-3">
                                    <div class="form-floating mb-3">
                                        <input name="id" type="text" class="userId" hidden>
                                        <input name="name" type="text" class="form-control" id="userName"
                                               placeholder="User's Name" disabled>
                                        <label for="userName">Name</label>
                                    </div>
                                    <div class="">
                                        <label for="roles">Role</label>
                                        <select name="roles" class="form-select userRoles" multiple disabled>
                                            <option value="CUSTOMER" style="padding: 4px 8px; margin-top: 2px;">CUSTOMER</option>
                                            <option value="SELLER" style="padding: 4px 8px; margin-top: 2px;">SELLER</option>
                                            <option value="ADMIN" style="padding: 4px 8px; margin-top: 2px;">ADMIN</option>
                                        </select>
                                    </div>
                                    <div class="row mt-3">
                                        <div class="col-md-6 col-12 mb-lg-0 mb-3">
                                            <button type="submit" class="btn btn-danger form-control">
                                                Delete
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

                                            function getUser(e) {
                                                const userId = e.querySelector('.userId').value;
                                                const userName = e.querySelector('.userName').value;
                                                const userRoles = e.querySelector('.userRoles').value.split(";");

                                                const actionType = e.innerText;

                                                document.querySelector('#' + actionType.trim().toLowerCase() + ' .userId').value = userId;
                                                document.querySelector('#' + actionType.trim().toLowerCase() + ' #userName').value = userName;

                                                const roleList = document.querySelectorAll('#' + actionType.trim().toLowerCase() + ' .userRoles option');
                                                for (let i = 0; i < roleList.length; i++) {
                                                    if (userRoles.includes(roleList[i].innerText)) {
                                                        roleList[i].setAttribute("selected", "");
                                                    } else {
                                                        roleList[i].removeAttribute("selected");
                                                    }
                                                }
                                            }
        </script>
    </body>

</html>
