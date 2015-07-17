<%@ include file="/common/taglibs.jsp" %>

<head>
    <title><fmt:message key="reagent.inandout"/></title>
    <meta name="menu" content="Reagent"/>
    <link rel="stylesheet" type="text/css"  href="<c:url value='../styles/jquery-ui.min.css'/>" />
    <script type="text/javascript" src="../scripts/jquery-ui.min.js"></script>
    <script type="text/javascript" src="../scripts/i18n/grid.locale-cn.js"></script>
    <script type="text/javascript" src="../scripts/jquery.jqGrid.js"></script>
	<link rel="stylesheet" type="text/css"  href="<c:url value='../styles/ui.jqgrid.css'/>" />
	<script type="text/javascript" src="../scripts/reagent/out.js"></script>
	
	<style type="text/css">
	
	</style>

</head>
<body>
<div class="col-sm-10">
<ul class="nav nav-pills">
  <li role="presentation"><a href="/reagent/in"><fmt:message key="reagent.in"/></a></li>
  <li role="presentation" class="active"><a href="#"><fmt:message key="reagent.out"/></a></li>
</ul>

<table id="list" class="table table-condensed table-striped" style="margin-top:5px;"></table>
<div id="pager"></div>

</div>
</body>