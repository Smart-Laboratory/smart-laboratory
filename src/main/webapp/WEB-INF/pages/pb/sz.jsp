<%@ include file="/common/taglibs.jsp"%>

<head>
	<title><fmt:message key="menu.pb" /></title>
	<script type="text/javascript" src="<c:url value='/scripts/pb/sz.js'/> "></script>
	<script type="text/javascript" src="<c:url value='/scripts/jquery.jqGrid.min.js'/> "></script>
	<link rel="stylesheet" type="text/css" href="<c:url value='/styles/ui.jqgrid.css'/> " />
	<link rel="stylesheet" type="text/css"  href="<c:url value='/styles/jquery-ui.min.css'/>" />
    <link rel="stylesheet" type="text/css"  href="<c:url value='/styles/ui.jqgrid.css'/>" />
	
	 <script type="text/javascript" src="../scripts/jquery-2.1.4.min.js"></script>
	<script type="text/javascript" src="../scripts/bootstrap.min.js"></script>
	<script type="text/javascript" src="../scripts/jquery.tablednd_0_5.js"></script>
    <script type="text/javascript" src="../scripts/jquery-ui.min.js"></script>
    <script type="text/javascript" src="../scripts/i18n/grid.locale-cn.js"></script>
    <script type="text/javascript" src="../scripts/jquery.jqGrid.js"></script>
    <script type="text/javascript" src="../scripts/jquery.validate.js"></script>
    <script type="text/javascript" src="../scripts/raphael.2.1.0.min.js"></script>
    <script type="text/javascript" src="../scripts/jquery.form.js"></script>
    <script type="text/javascript" src="../scripts/galleria-1.3.5.min.js"></script>
	<script type="text/javascript" src="../scripts/galleria.classic.min.js"></script>
	
</head>

<div id="lab">
	<select id="labSelect" onchange="labChange(this)" class="form-control" >
		<c:forEach var="depart" items="${departList }">
			<option value="${depart.key }"><c:out value="${depart.value}" /></option>
		</c:forEach>
	</select>
</div>

<input id="section" type="hidden" value="${section }"/>
<div id="tabs">
	<ul>
		<li><a href="#tabs-1"><fmt:message key="pb.wi"/></a></li>
		<li><a href="#tabs-2"><fmt:message key="pb.bc"/></a></li>
		<li><a href="#tabs-3"><fmt:message key="pb.dbc"/></a></li>
		<li><a href="#tabs-4"><fmt:message key="pb.kq"/></a></li>
	</ul>
	<div id="tabs-1">
		<button id="resetHoliday" class="bth btn-success"  ><fmt:message key="pb.resetHoliday"/></button>
		<table id="witable"></table>
		<div id="wipager"></div>
	</div>
	<div id="tabs-2">
		<table id="bctable"></table>
		<div id="bcpager"></div>
	</div>
	<div id="tabs-3">
		<table id="dbctable"></table>
		<div id="dbcpager"></div>
	</div>
	<div id="tabs-4">
		<table id="kqtable"></table>
		<div id="kqpager"></div>
	</div>
</div>