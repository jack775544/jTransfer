<html>
<head>
    <%@include file="includes/head.jsp" %>
</head>
<body>
<div class="container">
    <div id="filetitle">
        <h1>Browser Transfer</h1>
    </div>
    <div class="jumbotron">
        <div id="error"></div>
        <form action="<%= request.getAttribute("formUrl") %>" method="post" id="loginform" onSubmit="common.validateForm()">
            <div class="row">
                <div class="form-group">
                    <label for="remote">SFTP Server Address:</label>
                    <input type="text" name="remote" id="remote" value="remote.labs.eait.uq.edu.au" class="form-control"/><br>
                </div>
                <h2>User Login</h2>
                <label for="username">Username:</label>
                <input type="text" name="username" id="username" class="form-control"/><br>
                <label for="password">Password:</label>
                <input type="password" name="password" id="password" class="form-control"/><br>
                <input type="submit" name="submit" value="Submit" class="btn"/>
            </div>
        </form>
        <a href="./adminLogin">Log in to admin view</a>
    </div>
</div>
<%@include file="includes/bootstrapjs.jsp" %>
<script src="${pageContext.request.contextPath}/js/common.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/js/login.js" type="text/javascript"></script>
</body>
</html>