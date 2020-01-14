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
    <title>Register</title>
</head>
<body>
<%@ include file="header.html" %>
<div class="container-fluid">
    <div class="row">
        <div class="col-md-6 mx-auto my-5">
            <form id="loginForm" action="./register.jsp" method="post">
                <div class="form-group">
                    <label for="username">Username</label>
                    <input type="text" class="form-control" id="username" placeholder="Username"/>
                </div>
                <div class="form-group">
                    <label for="emailInput">Email address</label>
                    <input type="email" class="form-control" id="emailInput" placeholder="Enter email"/>
                </div>
                <div class="form-group">
                    <label for="passwordInput">Password</label>
                    <input type="password" class="form-control" id="passwordInput" placeholder="Password" aria-describedby="passwordFeedback"/>
                    <div class="progress">
                        <div class="progress-bar bg-danger" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="4" style="width: 20%" id="passwordMeter">
                            Extremely Weak
                        </div>
                    </div>
                    <small id="passwordFeedback" class="form-text text-muted"></small>
                </div>
                <div class="form-group">
                    <label for="passwordInput">Confirm password</label>
                    <input type="password" class="form-control" id="passwordConfirm" placeholder="Password"/>
                    <small id="passwordsMatch" class="form-text text-muted"></small>
                </div>
                <button type="submit" class="btn btn-primary">Create account</button>
            </form>
        </div>
    </div>
</div>
<script src="./js/zxcvbn.js"></script>
<script src="./js/login.js"></script>
<script>
    let formValidator = getValidator(1, 2, document.getElementById("passwordFeedback"), 3);
    document.getElementById("loginForm").addEventListener("submit", formValidator, true);
    document.getElementById("passwordInput").oninput = getFeedbackDisplay(document.getElementById("passwordMeter"),
        document.getElementById("passwordFeedback"));
    document.getElementById("passwordConfirm").oninput = getPasswordMatchDisplay(document.getElementById("passwordInput"),
        document.getElementById("passwordConfirm"),
        document.getElementById("passwordsMatch"));
</script>
</body>
</html>