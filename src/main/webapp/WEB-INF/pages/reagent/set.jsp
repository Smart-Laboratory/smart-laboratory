<%@ include file="/common/taglibs.jsp" %>

<head>
    <title><fmt:message key="reagent.set"/></title>
    <meta name="menu" content="Reagent"/>
    <%--<link rel="stylesheet" type="text/css"  href="<c:url value='../styles/jquery-ui.min.css'/>" />--%>
    <script type="text/javascript" src="../scripts/jquery-ui.min.js"></script>
    <script type="text/javascript" src="../scripts/i18n/grid.locale-cn.js"></script>
    <script type="text/javascript" src="../scripts/jquery.jqGrid.js"></script>
	<link rel="stylesheet" type="text/css"  href="<c:url value='../styles/ui.jqgrid.css'/>" />
	<script type="text/javascript" src="../scripts/reagent/set.js"></script>

</head>
<body>
<div class="row">
<div id="mid" class="col-sm-12">

<div style="margin-top:5px;" >
<table id="list" class="table table-condensed table-striped"></table>
<div id="pager"></div>
</div>

</div>
</div>
</body>