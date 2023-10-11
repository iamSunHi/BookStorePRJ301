<%-- 
    Document   : covertype
    Created on : Sep 25, 2023, 1:24:35 PM
    Author     : Nhật Huy
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="models.CoverType"%>
<!DOCTYPE html>
<html lang="en" data-bs-theme="dark">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Cover Types</title>
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
                                <h2 class="text-white py-2">Cover Type List</h2>
                            </div>
                        </div>
                    </div>
                    <div class="card-body">
                        <div class="row d-flex align-items-center pb-3">
                            <div class="text-end">
                                <a class="btn btn-light" data-bs-toggle="modal" data-bs-target="#create">
                                    <i class="fa-solid fa-circle-plus"></i>
                                    New Cover Type
                                </a>
                            </div>
                        </div>
                    <% if (request.getAttribute("CoverTypeList") != null && ((List<CoverType>) request.getAttribute("CoverTypeList")).size() >=1) { %>
                    <%
                        List<CoverType> coverTypeList = (List<CoverType>) request.getAttribute("CoverTypeList");
                    %>
                    <table class="table table-dark table-striped">
                        <thead>
                            <tr>
                                <th class="text-center">
                                    Cover Type Name
                                </th>
                                <th class="text-center">Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <% for (CoverType c : coverTypeList) { %>
                            <tr>
                                <td>
                                    <%= c.getName() %>
                                </td>
                                <td>
                                    <div class="btn-group d-flex" role="group">
                                        <a onclick="getCoverType(this)" class="btn btn-primary mx-2" data-bs-toggle="modal" data-bs-target="#edit">
                                            <input type="text" class="coverTypeId" value="<%= c.getId() %>" hidden>
                                            <input type="text" class="coverTypeName" value="<%= c.getName() %>" hidden>
                                            Edit
                                        </a>
                                        <a onclick="getCoverType(this)" class="btn btn-danger mx-2" data-bs-toggle="modal" data-bs-target="#delete">
                                            <input type="text" class="coverTypeId" value="<%= c.getId() %>" hidden>
                                            <input type="text" class="coverTypeName" value="<%= c.getName() %>" hidden>
                                            Delete
                                        </a>
                                    </div>
                                </td>
                            </tr>
                            <% } %>
                        </tbody>
                    </table>
                    <% } else { %>
                    <center><h1>No Cover Type here . . .</h1></center>
                        <% } %>
                </div>
            </div>

            <!-- Modal -->
            <div class="modal fade" id="create" tabindex="-1" aria-labelledby="createLabel" aria-hidden="true">
                <div class="modal-dialog modal-dialog-centered">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h1 class="modal-title fs-5" id="createLabel">Create Cover Type</h1>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <form action="covertype" method="post" class="row">
                                <div class="p-3">
                                    <div class="form-floating mb-3">
                                        <input name="coverTypeName" type="text" class="form-control" id="coverTypeName"
                                               placeholder="CoverType Name">
                                        <label for="coverTypeName">Cover Type Name</label>
                                    </div>
                                    <div class="row mt-3">
                                        <div class="col-md-6 col-12 mb-lg-0 mb-3">
                                            <button type="submit" class="btn btn-success form-control">
                                                Create
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
            <div class="modal fade" id="edit" tabindex="-1" aria-labelledby="editLabel" aria-hidden="true">
                <div class="modal-dialog modal-dialog-centered">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h1 class="modal-title fs-5" id="editLabel">Create Cover Type</h1>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <form action="covertype" method="post" class="row">
                                <input type="hidden" name="_method" value="PUT">
                                <div class="p-3">
                                    <div class="form-floating mb-3">
                                        <input name="coverTypeId" type="text" class="coverTypeId" hidden>
                                        <input name="coverTypeName" type="text" class="form-control" id="coverTypeName"
                                               placeholder="CoverType Name">
                                        <label for="coverTypeName">Cover Type Name</label>
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
                            <h1 class="modal-title fs-5" id="editLabel">Delete Cover Type</h1>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <form action="covertype" method="post" class="row">
                                <input type="hidden" name="_method" value="DELETE">
                                <div class="p-3">
                                    <div class="form-floating mb-3">
                                        <input name="coverTypeId" type="text" class="coverTypeId" hidden>
                                        <input name="coverTypeName" type="text" class="form-control" id="coverTypeName"
                                               placeholder="CoverType Name" disabled>
                                        <label for="coverTypeName">Cover Type Name</label>
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

                                            function getCoverType(e) {
                                                const coverTypeId = e.querySelector('.coverTypeId').value;
                                                const coverTypeName = e.querySelector('.coverTypeName').value;
                                                
                                                const actionType = e.innerText;

                                                document.querySelector('#' + actionType.toLowerCase() + ' .coverTypeId').value = coverTypeId;
                                                document.querySelector('#' + actionType.toLowerCase() + ' #coverTypeName').value = coverTypeName;
                                            }
        </script>
    </body>

</html>

