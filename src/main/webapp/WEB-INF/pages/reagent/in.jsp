<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp" %>

<head>
    <title><fmt:message key="reagent.inandout"/></title>
    <meta name="menu" content="Reagent"/>
	<script type="text/javascript" src="../scripts/jquery-ui.min.js"></script>
	<script type="text/javascript" src="../scripts/i18n/grid.locale-cn.js"></script>
	<script type="text/javascript" src="../scripts/jquery.jqGrid.js"></script>
	<script type="text/javascript" src="../scripts/ajaxfileupload.js"></script>
	<script type="text/javascript" src="../scripts/layer/layer.js"></script>
	<script src="<c:url value="/scripts/LodopFuncs.js"/>"></script>
	<script type="text/javascript" src="../scripts/reagent/in.js"></script>

	<%--<link rel="stylesheet" type="text/css"  href="<c:url value='../styles/jquery-ui.min.css'/>" />--%>
	<link rel="stylesheet" type="text/css"  href="<c:url value='/styles/ui.jqgrid.css'/>" />
	
	<style type="text/css">
	
	</style>

</head>
<body>
<div class="row">
<div id="mid" class="col-sm-12">
<ul class="nav nav-pills">
  <li role="presentation" class="active"><a href="#"><fmt:message key="reagent.in"/></a></li>
  <li role="presentation"><a href="../reagent/out"><fmt:message key="reagent.out"/></a></li>
</ul>

<div class="form-inline" style="margin-top:10px;">
	<label for="reagentdes"><fmt:message key="guanjianzi"/></label>
	<input type="text" id="reagentdes" name="reagentdes" class="form-control" style="width:600px;" onkeypress="getData(this,event);" placeholder="<fmt:message key='reagent.tips'/>">
	<select id="reagent_select" class="form-control select">
		<option value="1"><fmt:message key='barcode'/></option>
		<option value="2" selected><fmt:message key='reagent.single'/></option>
		<option value="3"><fmt:message key='reagent.combo.select'/></option>
	</select>
	<button id="inBtn" type="button" class="btn btn-success" style="float:right;margin-left:10px;"><fmt:message key='reagent.in'/></button>
	<button type="button" class="btn btn-pink" style="float:right;margin-left:10px;" title="打印机选择" onclick="printSetting()">
		<i class="ace-icon fa fa-pencil-square bigger-110"></i>
		打印设定
	</button>
	<button type="button" class="btn btn-purple" style="float:right;" onclick="printSet()">
		<i class="ace-icon fa fa-pencil-square bigger-110"></i>
		打印设计
	</button>
	<form action="#" method="post" enctype ="multipart/form-data" id="excelForm" class="form-control">
		<input type="file" id="fileUpload" name="fileUpload" label=“上传Excel文件" style="padding:0px 0px;float:left;width:200px;"/>
		<input id="readExcel" type="button" class="btn btn-minier btn-success" value="Excel导入" style="padding:0px 0px;float:left;">
	</form>
</div>
<div style="margin-top:10px;">
<table id="list" class="table table-condensed table-striped"></table>
</div>
</div>

</div>
</body>