<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="text/html; charset=UTF-8">
    <title>Document</title>
    <link rel="stylesheet" href="/css/form-style.css">
</head>
<body>
    <div class="main">
        <section class="signup">
            <!-- <img src="images/signup-bg.jpg" alt=""> -->
            <div class="container">
                <div class="signup-content">
                    <form id="signup-form" class="signup-form" action="create-user" method="POST" modelAttribute="user">
                        <h2 class="form-title">Create account</h2>
                        <div class="form-group">
                            <input type="text" class="form-input" name="phoneNumber" id="name" placeholder="Phone Number"/>
                        </div>
                        <div class="form-group">
                            <input type="password" class="form-input" name="password" id="password" placeholder="Password"/>
                            <span toggle="#password" class="zmdi zmdi-eye field-icon toggle-password"></span>
                        </div>
                        <%--<div class="form-group">--%>
                            <%--<input type="password" class="form-input" name="re_password" id="re_password" placeholder="Repeat your password"/>--%>
                        <%--</div>--%>
                        <div class="form-group">
                            <input type="text" class="form-input" name="firstName" id="name" placeholder="First Name"/>
                        </div>
                        <div class="form-group">
                            <input type="text" class="form-input" name="lastName" id="name" placeholder="Last Name"/>
                        </div>
                        <div class="form-group">
                            <input type="text" class="form-input" name="vehicleNumber" id="name" placeholder="Vehicle Number"/>
                        </div>
                        <div class="form-group">
                            <input type="text" class="form-input" name="licensePlateId" id="name" placeholder="License Plate"/>
                        </div>
                        <div class="form-group">
                            <input type="submit" name="submit" id="submit" class="form-submit" value="Sign up"/>
                        </div>
                    </form>
                    <%--<p class="loginhere">--%>
                        <%--Have already an account ? <a href="#" class="loginhere-link">Login here</a>--%>
                    <%--</p>--%>
                </div>
            </div>
        </section>

    </div>
    </form>
    <script src="/assets/vendor/jquery/jquery.min.js"></script>
    <script src="/assets/vendor/bootstrap/js/bootstrap.min.js"></script>
</body>
</html>