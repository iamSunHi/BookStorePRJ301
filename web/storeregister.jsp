<%-- 
    Document   : storeregister
    Created on : Oct 24, 2023, 9:02:22 PM
    Author     : Sun Hi
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en" data-bs-theme="dark">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Register</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-4bw+/aepP/YC94hEpVNVgiZdgIC5+VKNBQNGCHeKRQN+PtmoHDEXuppvnDJzQIu9" crossorigin="anonymous">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css"
              integrity="sha512-z3gLpd7yknf1YoNbCzqRKc4qyor8gaKU1qmn+CShxbuBusANI9QpRohGBreCFkKxLhei6S9CQXFEbbKuqLg0DA=="
              crossorigin="anonymous" referrerpolicy="no-referrer" />

        <link rel="stylesheet" href="assets/css/navbar.css">
        <link rel="stylesheet" href="assets/css/login.css">
    </head>

    <body style="background:url(assets/img/store.jpg);">
        <jsp:include page="./navbar.jsp"></jsp:include>

            <main class="container">
                <div class="row align-items-center justify-content-center position-relative" style="min-height: calc(100vh - 60px); margin-top: 60px;">
                    <div class="col-md-6">
                        <div class="login-box">
                            <p>Store Register</p>
                            <form id="shopregister" action="store?method=register" method="post">
                                <div class="user-box">
                                    <input name="name" type="text" placeholder="Shop Name">
                                </div>
                                <div class="user-box">
                                    <input name="email" type="email" placeholder="Email">
                                </div>
                                <div class="user-box">
                                    <input name="phone" type="text" placeholder="Phone">
                                </div>
                                <div class="user-box">
                                    <input name="address" type="text" placeholder="Address">
                                </div>
                                <a href="#" class="w-100">
                                    <span></span>
                                    <span></span>
                                    <span></span>
                                    <span></span>
                                    <button class="btn text-uppercase fw-semibold w-100">Register</button>
                                </a>
                            </form>
                        </div>
                    </div>
                </div>
            </main>

        <jsp:include page="validationScripts.jsp"></jsp:include>
    </body>

</html>
