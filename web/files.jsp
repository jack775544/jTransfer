<html>
<head>
    <%@include file="includes/head.jsp" %>
</head>
<body>
<div class="row">
    <div class="col-sm-11">
        <h1>Browser Transfer</h1>
    </div>
    <div class="col-sm-1">
        <form method="get" action="logout">
            <input type="submit" name="logout" value="Logout" class="btn" id="logout">
        </form>
    </div>
</div>
<div class="jumbotron row files">
    <div class="row" id="editoptions">
        <button type="button" id="refresh" class="btn"><span class="glyphicon glyphicon-refresh"
                                                             aria-hidden="true"></span>Refresh
        </button>
        <%--<button type="button" id="upload" class="btn"><span class="glyphicon glyphicon-open" aria-hidden="true"></span>Upload
        </button>--%>
        <%--<form id="uploadForm" action="put" method="post" enctype="multipart/form-data">
            <input type="file" class="btn" name="file"/>
            <input type="submit" class="btn" id="uploadButton"/>
        </form>--%>
        <form id="uploadForm" action="./put" method="POST">
            <input type="file" id="file-select" name="file"/>
            <button type="submit" id="upload-button">Upload</button>
        </form>
    </div>
    <div class="row" id="pwd"></div>
</div>
<div class="jumbotron files row">
    <div id="filecontainer" class="row">
        <ol id="items">

        </ol>
    </div>
</div>

<%@include file="includes/bootstrapjs.jsp" %>
<script src="${pageContext.request.contextPath}/js/common.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/js/interface.js" type="text/javascript"></script>
</body>
</html>