<!DOCTYPE html>
<html>
<head>
    <%@include file="includes/head.jsp" %>
</head>
<body>
<header class="row">
    <div class="col-sm-11">
        <h1>Browser Transfer</h1>
        <div id="error" class="alert alert-danger"></div>
    </div>
    <div class="col-sm-1">
        <form method="get" action="logout">
            <input type="submit" name="logout" value="Logout" class="btn" id="logout">
        </form>
    </div>
</header>
<div class="jumbotron row files">
    <div class="row" id="editoptions">
        <button type="button" id="save" class="btn"><span class="glyphicon glyphicon-floppy-disk"
                                                          aria-hidden="true"></span>Save
        </button>
        <button type="button" id="download" class="btn"><span class="glyphicon glyphicon-save"
                                                              aria-hidden="true"></span>Save and Download
        </button>
        <select id="modes"></select>
    </div>
    <div class="row">
        <%--DO THIS--%>

    </div>
</div>
<div class="jumbotron files row" id="editjumbo">
    <div class="row" id="editrow">
        <pre id="editor"></pre>
    </div>
</div>

<%@include file="includes/bootstrapjs.jsp" %>
<script src="${pageContext.request.contextPath}/js/common.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/js/edit.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/ace/ace.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/ace/ext-modelist.js" type="text/javascript"></script>

</body>
</html>