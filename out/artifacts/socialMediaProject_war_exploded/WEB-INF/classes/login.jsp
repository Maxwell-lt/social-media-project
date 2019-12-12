<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="./css/bootstrap.min.css" type="text/css" rel="stylesheet"/>
    <script src="./js/jquery.min.js"></script>
    <script src="./js/popper.min.js"></script>
    <script src="./js/bootstrap.min.js" type="text/javascript"></script>
    <title>Login</title>
</head>
<body>
<%@ include file="header.html" %>
<div class="container-fluid">
    <div class="row">
        <div class="col-md-6 mx-auto my-5">
            <form id="loginForm" action="./login.jsp" method="post">
                <div class="form-group">
                    <label for="emailInput">Email address</label>
                    <input type="email" class="form-control" id="emailInput" placeholder="Enter email"/>
                </div>
                <div class="form-group">
                    <label for="passwordInput">Password</label>
                    <input type="password" class="form-control" id="passwordInput" placeholder="Password"/>
                    <small id="passwordFeedback" class="form-text"></small>
                </div>
                <div class="form-check">
                    <input type="checkbox" class="form-check-input" id="persistLogin"/>
                    <label class="form-check-label" for="persistLogin">Keep me logged in</label>
                </div>
                <button type="submit" class="btn btn-primary">Login</button>
            </form>
        </div>
    </div>
</div>
<script src="./js/zxcvbn.js"></script>
<script src="./js/login.js"></script>
</body>
</html>