<%@ include file="/common/taglibs.jsp" %>

<head>
    <title><fmt:message key="set.ylsf"/></title>
    <meta name="menu" content="SampleSet"/>
    
    <script type="text/javascript" src="../scripts/jquery-2.1.4.min.js"></script>
	<script type="text/javascript" src="../scripts/bootstrap.min.js"></script>
    <script type="text/javascript" src="../scripts/jquery-ui.min.js"></script>
    <script type="text/javascript" src="../scripts/i18n/grid.locale-cn.js"></script>
    <script type="text/javascript" src="../scripts/jquery.jqGrid.js"></script>
    <script type="text/javascript" src="../scripts/jquery.form.js"></script>
    <script type="text/javascript" src="../scripts/set/ylsf.js"></script>
    
    <link rel="stylesheet" type="text/css"  href="<c:url value='../styles/jquery-ui.min.css'/>" />
    <link rel="stylesheet" type="text/css"  href="<c:url value='../styles/ui.jqgrid.css'/>" />
	<link rel="stylesheet" type="text/css"  href="<c:url value='../styles/ruleLib.css'/>" />
	<link rel="stylesheet" type="text/css"  href="<c:url value='../styles/bootstrap.min.css'/>" />
</head>
<body>
<div class="col-sm-10">

<div id="searchHeader" style="margin-left:50px;float: left; width: 450px;">
	<div class="form-inline" style="margin-bottom:15px;">
	<label for="search_text" style=""><b></b></label>
	<input type="text" id="search_text" name="search_text" class="form-control"  style="width:150px;" placeholder="<fmt:message key="placeholder1"></fmt:message>" />
	<button id="searchBtn" class="btn btn-info" ><fmt:message key="search" /></button>
	<select id="labSelect" onchange="labChange(this)" class="form-control" style="margin-left:10px; width:180px;">
		<span><c:forEach var="depart" items="${departList}">
			<option value='<c:out value="${depart.key}" />'><c:out value="${depart.value}" /></option>
		</c:forEach></span>
	</select>
	</div>
	<div id="sampleListPanel">
		<table id="s3list"></table>
		<div id="s3pager"></div>
	</div>
</div>

<div id="testlist" style="float: left; margin-left: 50px; width: 320px;">
	<input id="addIndexId" type="hidden"></input>
	<div style="float:left;" class="form-inline">
		<input class="span4 form-control" id="searchProject" type="text" placeholder="<fmt:message key='add.single'/>">
		<button id="testAdd" class="btn btn-info" style="font-size:12px;"><b><fmt:message key="button.add" /></b></button>
		<button id="testDelete" class="btn btn-danger" style="font-size:12px;"><b><fmt:message key="button.delete" /></b></button>
	</div>
	<div style="margin-top:50px;">
		<table id="tlist"></table>
	</div>
	<div style="float:left;margin-top:15px;" class="form-inline">
		<input class="span4 form-control" id="searchProject2" type="text" placeholder="<fmt:message key='add.single'/>">
		<button id="testAdd2" class="btn btn-info" style="font-size:12px;"><b><fmt:message key="button.add" /></b></button>
		<button id="testDelete2" class="btn btn-danger" style="font-size:12px;"><b><fmt:message key="button.delete" /></b></button>
	</div>
	<div style="margin-top:55px;">
		<table id="tlist2"></table>
	</div>
</div>



</div>
</body>