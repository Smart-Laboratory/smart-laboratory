<%@ include file="/common/taglibs.jsp" %>

<head>
    <title><fmt:message key="reagent.inandout"/></title>
    <meta name="menu" content="Reagent"/>
    <link rel="stylesheet" type="text/css"  href="<c:url value='../styles/jquery-ui.min.css'/>" />
    <script type="text/javascript" src="../scripts/jquery-ui.min.js"></script>
    <script type="text/javascript" src="../scripts/i18n/grid.locale-cn.js"></script>
    <script type="text/javascript" src="../scripts/jquery.jqGrid.js"></script>
	<link rel="stylesheet" type="text/css"  href="<c:url value='../styles/ui.jqgrid.css'/>" />
	<script type="text/javascript" src="../scripts/reagent/in.js"></script>
	
	<style type="text/css">
	
	</style>

</head>
<body>
<div class="col-sm-10">
<ul class="nav nav-pills">
  <li role="presentation" class="active"><a href="#"><fmt:message key="reagent.in"/></a></li>
  <li role="presentation"><a href="/reagent/out"><fmt:message key="reagent.out"/></a></li>
</ul>

<div class="form-inline" style="margin-top:10px;">
	<label for="reagentdes"><fmt:message key="guanjianzi"/></label>
	<input type="text" id="reagentdes" name="reagentdes" class="form-control" style="width:500px;" onkeypress="getData(this,event);">
	<select id="reagent_select" class="form-control select">
		<option value="1"><fmt:message key='reagent.single'/></option>
		<option value="2"><fmt:message key='reagent.combo.select'/></option>
	</select>
	<button id="hmCombo" type="button" class="btn btn-success" style="float:right;"><fmt:message key='reagent.in'/></button>
	<h5 style="margin-left:50px;"><b><fmt:message key="reagent.tips"/></b></h5>
</div>
<table id="list" class="table table-condensed table-striped" style="margin-top:10px;"></table>
<div id="pager"></div>

</div>
</body>