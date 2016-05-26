<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp" %>
<title>医疗收费项目设置</title>
<!--<meta name="menu" content="SampleSet"/>-->
<head>
	<script type="text/javascript" src="../scripts/jquery-2.1.4.min.js"></script>
	<script type="text/javascript" src="../scripts/bootstrap.min.js"></script>
	<!--script type="text/javascript" src="../scripts/jquery.tablednd_0_5.js"></script-->
	<script type="text/javascript" src="../scripts/jquery-ui.min.js"></script>
	<script type="text/javascript" src="../scripts/i18n/grid.locale-cn.js"></script>
	<script type="text/javascript" src="../scripts/jquery.jqGrid.js"></script>
	<script type="text/javascript" src="../scripts/jquery.validate.js"></script>
	<script type="text/javascript" src="../scripts/jquery.validate.js"></script>
	<script type="text/javascript" src="../scripts/layer/layer.js"></script>
	<script type="text/javascript" src="../scripts/request/sfxm.js"></script>
	<!--link rel="stylesheet" type="text/css"  href="<c:url value='/styles/jquery-ui.min.css'/>" /-->
	<link rel="stylesheet" type="text/css"  href="<c:url value='/styles/bootstrap.min.css'/>" />
	<link rel="stylesheet" type="text/css"  href="<c:url value='/styles/ui.jqgrid.css'/>" />

<style>

</style>

</head>
<div class="row">
<div class="col-sm-12" style="background: #ffffff" id="mainTable">
	<div  style="padding-top: 10px;">
		<button type="button" class="btn btn-sm btn-success " title="添加科室" onclick="AddSection('添加科室')">
			<i class="ace-icon fa fa-fire bigger-110"></i>
			<fmt:message key="button.add" />
		</button>
		<button type="button" class="btn btn-sm btn-danger " title="删除科室">
			<i class="ace-icon fa fa-times bigger-110"></i>
			<fmt:message key="button.delete" />
		</button>
		<div class="input-group col-sm-3 " style="float: right;" >
			<input type="text" class="form-control search-query" placeholder="输入查询内容" />
			<span class="input-group-btn">
				<button type="button" class="btn btn-purple btn-sm">
					<fmt:message key="button.search"/>
					<i class="ace-icon fa fa-search icon-on-right bigger-110"></i>
				</button>
			</span>
		</div>
	</div>
	<table id="sfxmList" style="width: 100%;"></table>
	<div id="pager" style="width: 100%"></div>
</div>
</div>