<!DOCTYPE html>
<html>
<head>
    <%@include file="includes/head.jsp" %>
</head>
<body>
<header class="row">
    <div class="col-sm-11">
        <h1>Browser Transfer</h1>
    </div>
    <div class="col-sm-1">
        <form method="get" action="logout">
            <input type="submit" name="logout" value="Logout" class="btn" id="logout">
        </form>
    </div>
</header>
<div class="jumbotron files row">
    <div id="filecontainer" class="row">
        <h2 style="width: 70%; margin: auto; padding: 10px;">Password Change for <%= request.getAttribute("loginName")%></h2>
        <form action="./changePass" method="post" id="loginform" style="width: 70%; margin: auto">
            <div class="form-group">
                <label for="oldPass">Current Password:</label>
                <input id="oldPass" name="oldPassword" class="form-control" type="password">
            </div>
            <div class="form-group">
                <label for="newPass">New Password:</label>
                <input id="newPass" name="newPassword" class="form-control" type="password">
                <label for="newPassConfirm">Confirm New Password:</label>
                <input id="newPassConfirm" name="newPasswordConfirm" class="form-control" type="password">
            </div>
            <button type="submit" class="btn">Change Password</button>
        </form>
    </div>
</div>

<%@include file="includes/bootstrapjs.jsp" %>
<script src="${pageContext.request.contextPath}/js/common.js" type="text/javascript"></script>
</body>
</html>