<html>
<head>
	<%@include file="includes/head.jsp" %>
</head>
<body>
<div class="container">
    <div class="jumbotron">
        <h1>Browser Transfer</h1>
        <form action="login" method="post" id="loginform">
            <div class="form-group">
                <label for="remote">SFTP Server Address:</label>
                <input type="text" name="remote" id="remote" value="remote.labs.eait.uq.edu.au" class="form-control"/><br>
            </div>
            <div class="form-group">
                <label for="username">Username:</label>
                <input type="text" name="username" id="username" class="form-control"/><br>
                <label for="password">Password:</label>
                <input type="password" name="password" id="password" class="form-control"/><br>
            </div>
            <input type="submit" name="submit" value="Submit" class="btn"/>
        </form>
    </div>
</div>
<%@include file="includes/bootstrapjs.jsp" %>
<script src="${pageContext.request.contextPath}/js/common.js" type="text/javascript"></script>
</body>
</html>
