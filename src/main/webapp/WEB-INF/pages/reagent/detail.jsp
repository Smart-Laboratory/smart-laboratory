<%@ include file="/common/taglibs.jsp" %>

<head>
    <title><fmt:message key="reagent.detail"/></title>
    <meta name="menu" content="Reagent"/>
    <link rel="stylesheet" type="text/css"  href="<c:url value='../styles/jquery-ui.min.css'/>" />
    <script type="text/javascript" src="../scripts/jquery-ui.min.js"></script>
    <script type="text/javascript" src="../scripts/i18n/grid.locale-cn.js"></script>
    <script type="text/javascript" src="../scripts/jquery.jqGrid.js"></script>
	<link rel="stylesheet" type="text/css"  href="<c:url value='../styles/ui.jqgrid.css'/>" />
	<script type="text/javascript" src="../scripts/reagent/detail.js"></script>
	
	<style type="text/css">
	
	</style>

</head>
<body>
<div class="col-sm-10">
<ul class="nav nav-pills">
  <li id="inpre" role="presentation" class="active"><a onclick="changeTab('in')"><fmt:message key="reagent.detail.in"/></a></li>
  <li id="outpre" role="presentation"><a onclick="changeTab('out')"><fmt:message key="reagent.detail.out"/></a></li>
</ul>

<table id="list" class="table table-condensed table-striped"></table>
<div id="pager"></div>

</div>
</body>