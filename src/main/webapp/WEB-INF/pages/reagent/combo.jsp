<%@ include file="/common/taglibs.jsp" %>

<head>
    <title><fmt:message key="reagent.combo"/></title>
    <meta name="menu" content="Reagent"/>
    <link rel="stylesheet" type="text/css"  href="<c:url value='../styles/jquery-ui.min.css'/>" />
    <script type="text/javascript" src="../scripts/jquery-ui.min.js"></script>
    <script type="text/javascript" src="../scripts/i18n/grid.locale-cn.js"></script>
    <script type="text/javascript" src="../scripts/jquery.jqGrid.js"></script>
	<link rel="stylesheet" type="text/css"  href="<c:url value='../styles/ui.jqgrid.css'/>" />
	<script type="text/javascript" src="../scripts/reagent/combo.js"></script>
	
	<style type="text/css">
	.ui-autocomplete {
		z-index:999;
	}
	
	</style>

</head>
<body>
<div id="mid" class="col-sm-10">

<table id="list" class="table table-condensed table-striped"></table>
<div id="pager"></div>

</div>
</body>