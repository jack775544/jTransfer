<!DOCTYPE html>
<html>
<head>
    <%@include file="includes/head.jsp" %>
</head>
<body>
<div class="container">
    <header id="filetitle">
        <h1>Browser Transfer</h1>
    </header>
    <div class="jumbotron">
        <form action="./adminValidate" method="post" id="loginform">
            <div class="row">
                <h2>Admin login</h2>
                <label for="admin-username">Username:</label>
                <input type="text" name="username" id="admin-username" class="form-control"/><br>
                <label for="admin-password">Password:</label>
                <input type="password" name="password" id="admin-password" class="form-control"/><br>
                <input type="submit" name="submit" value="Submit" class="btn"/>
            </div>
        </form>
    </div>
</div>
<%@include file="includes/bootstrapjs.jsp" %>
<script src="${pageContext.request.contextPath}/js/common.js" type="text/javascript"></script>
</body>
</html>
