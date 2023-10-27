<%-- 
    Document   : orderconfirmation
    Created on : Oct 20, 2023, 3:13:09 PM
    Author     : Sun Hi
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en" data-bs-theme="dark">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Order Success</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-4bw+/aepP/YC94hEpVNVgiZdgIC5+VKNBQNGCHeKRQN+PtmoHDEXuppvnDJzQIu9" crossorigin="anonymous">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css"
              integrity="sha512-z3gLpd7yknf1YoNbCzqRKc4qyor8gaKU1qmn+CShxbuBusANI9QpRohGBreCFkKxLhei6S9CQXFEbbKuqLg0DA=="
              crossorigin="anonymous" referrerpolicy="no-referrer" />
        <link rel="stylesheet" href="assets/css/orderconfirmation.css">
        <link rel="stylesheet" href="assets/css/navbar.css">
    </head>
    <body>
        <jsp:include page="navbar.jsp"></jsp:include>

        <div class="container">
            <main class="pb-3" style="margin-top: 60px;">
                <div class="container row pt-4">
                    <div class="col-12 text-center mb-3">
                        <h1 class="text-success text-center">Order Placed Successfully!</h1>
                        Your Order ID is: <%= request.getAttribute("orderId") %>
                    </div>
                    <div class="col-12">
                        <div class="d-flex justify-content-center align-items-center">

                            <div class="gearbox">
                                <div class="overlay"></div>
                                <div class="gear one">
                                    <div class="gear-inner">
                                        <div class="bar"></div>
                                        <div class="bar"></div>
                                        <div class="bar"></div>
                                    </div>
                                </div>
                                <div class="gear two">
                                    <div class="gear-inner">
                                        <div class="bar"></div>
                                        <div class="bar"></div>
                                        <div class="bar"></div>
                                    </div>
                                </div>
                                <div class="gear three">
                                    <div class="gear-inner">
                                        <div class="bar"></div>
                                        <div class="bar"></div>
                                        <div class="bar"></div>
                                    </div>
                                </div>
                                <div class="gear four large">
                                    <div class="gear-inner">
                                        <div class="bar"></div>
                                        <div class="bar"></div>
                                        <div class="bar"></div>
                                        <div class="bar"></div>
                                        <div class="bar"></div>
                                        <div class="bar"></div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-12 text-center pt-4">
                        Your order will be delivered soon!
                    </div>
                </div>
            </main>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-HwwvtgBNo3bZJJLYd8oVXjrBZt8cqVSpeBNS5n7C8IVInixGAoxmnlMuBnhbgrkm"
        crossorigin="anonymous"></script>
    </body>

</html>
