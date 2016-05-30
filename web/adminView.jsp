<%@ page import="java.util.List" %>
<%@ page import="jTransfer.Types" %>
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
        <table>
            <tr>
                <th>id</th>
                <th>timestamp</th>
                <th>session id</th>
                <th>log</th>
            </tr>
            <%
                List<Types> results = (List) request.getAttribute("sessions");
                for (Types type : results){
                    out.print("<tr>");
                    out.print(type.toHTML());
                    out.print("</tr>");
                }
            %>
        </table>
    </div>
</div>

<%@include file="includes/bootstrapjs.jsp" %>
<script src="${pageContext.request.contextPath}/js/common.js" type="text/javascript"></script>
</body>
</html>