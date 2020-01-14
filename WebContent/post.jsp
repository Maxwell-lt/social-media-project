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
    <title>Create a post</title>
</head>
<body>
<%@ include file="header.html" %>
<div class="container-fluid">
    <div class="row">
        <div class="col-md-6 mx-auto my-5">
            <form id="postForm" action="./post.jsp" method="post">
                <div class="form-group">
                    <label for="titleInput">Title</label>
                    <input type="text" class="form-control" id="titleInput" placeholder="Enter title..." />
                </div>
                <div class="form-group">
                    <label for="pictureInput">Upload a picture (optional)</label>
                    <input type="file" class="form-control-file" id="pictureInput" />
                </div>
                <div class="form-group">
                    <label for="postInput">Post Text</label>
                    <textarea class="form-control" id="postInput" rows="8"></textarea>
                </div>
                <button type="submit" class="btn btn-primary">Create Post</button>
            </form>
        </div>
    </div>
</div>
</body>
</html>