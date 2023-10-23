<%-- 
    Document   : login
    Created on : Sep 22, 2023, 7:44:31 PM
    Author     : Nhật Huy
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en" data-bs-theme="dark">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Login</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-4bw+/aepP/YC94hEpVNVgiZdgIC5+VKNBQNGCHeKRQN+PtmoHDEXuppvnDJzQIu9" crossorigin="anonymous">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css"
              integrity="sha512-z3gLpd7yknf1YoNbCzqRKc4qyor8gaKU1qmn+CShxbuBusANI9QpRohGBreCFkKxLhei6S9CQXFEbbKuqLg0DA=="
              crossorigin="anonymous" referrerpolicy="no-referrer" />

        <link rel="stylesheet" href="assets/css/navbar.css">
        <link rel="stylesheet" href="assets/css/login.css">
    </head>

    <body>
        <jsp:include page="./navbar.jsp"></jsp:include>

        <main class="container">
            <div class="row align-items-center justify-content-center" style="min-height: 100vh;">
                <div class="col-md-6">
                    <div class="login-box">
                        <p>Login</p>
                        <form id="login" action="identity" method="post">
                            <input type="hidden" name="type" value="LOGIN">
                            <div class="user-box">
                                <input required name="username" type="text" value="adminn">
                                <label><i class="fa-solid fa-user"></i> Username</label>
                            </div>
                            <div class="user-box">
                                <input required name="password" type="password" value="Bo_29102003">
                                <label><i class="fa-solid fa-lock"></i> Password</label>
                            </div>
                            <a href="#" class="w-100">
                                <span></span>
                                <span></span>
                                <span></span>
                                <span></span>
                                <button type="submit" class="btn text-uppercase fw-semibold w-100">Login</button>
                            </a>
                        </form>
                        <div class="row align-items-center">
                            <a href="register.jsp" class="col-lg-6 btn outline-none shadow-none text-center">
                                Don't have an account yet?
                            </a>
                            <a href="" class="col-lg-6 btn outline-none shadow-none text-center">
                                Forgot password?
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </main>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-HwwvtgBNo3bZJJLYd8oVXjrBZt8cqVSpeBNS5n7C8IVInixGAoxmnlMuBnhbgrkm"
        crossorigin="anonymous"></script>
    </body>

</html>
