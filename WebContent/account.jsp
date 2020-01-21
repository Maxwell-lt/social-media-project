<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="./css/bootstrap.min.css" type="text/css" rel="stylesheet"/>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <script src="./js/jquery.min.js"></script>
    <script src="./js/popper.min.js"></script>
    <script src="./js/bootstrap.min.js" type="text/javascript"></script>
    <script src="./js/getposts.js" type="text/javascript"></script>
    <title>Account</title>
</head>
<body>
<%@ include file="header.html" %>
<div class="container">
    <div class="row">
        <div class="card">
            <div class="row no-gutters">
                <div class="col-md-4">
                    <img src="https://images.unsplash.com/photo-1488462237308-ecaa28b729d7" class="card-img">
                </div>
                <div class="col-md-8">
                    <div class="card-header col-md-12">
                        <h5 class="card-title">
                            <a href="post.jsp?postid=13">Lounging</a>
                            <span class="float-right"><i class="material-icons" style="color: #ff006b; cursor: pointer">favorite_border</i> 9001</span>
                        </h5>
                    </div>
                    <div class="card-body">
                        <p class="card-text"><small>Posted by <a
                                href="account.jsp?user=helloworld">helloworld</a></small>
                        </p>
                        <p class="card-text">Beach umbrellas are super cool, use them, but don't throw away those
                            funny-looking ones that are so perfect. You want to stick to the one that makes you look
                            good on your own. Making shopping easier is also a good thing, but it's not a show stopper
                            if you don't have the right one.</p>
                        <p class="card-text"><small class="text-muted">Posted 3 days ago</small></p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
