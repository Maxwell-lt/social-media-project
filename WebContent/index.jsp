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
  <script src="./js/getposts.js" type="text/javascript"></script>
  <title>Home</title>
</head>
<body onload="reloadPosts()">
<nav class="navbar navbar-dark bg-dark">
  <div class="" id="navbarSupportedContent">
    <ul class="navbar-nav list-inline">
      <li class="nav-item active list-inline-item">
        <a class="nav-link" href="index.jsp">Home</a>
      </li>
      <li class="nav-item list-inline-item">
        <a class="nav-link" href="post.jsp">New Post</a>
      </li>
      <li class="nav-item list-inline-item">
        <a class="nav-link" href="login.jsp">Login</a>
      </li>
      <li class="nav-item list-inline-item">
        <a class="nav-link" href="register.jsp">Register</a>
      </li>
      <li class="nav-item list-inline-item">
        <a class="nav-link" href="account.jsp">Account</a>
      </li>
    </ul>
  </div>
</nav>
<div class="container-fluid">
  <div class="row" id="post-container">
  </div>
</div>
</body>
</html>