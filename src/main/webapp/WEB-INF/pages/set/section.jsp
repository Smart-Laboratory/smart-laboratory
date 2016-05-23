<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp" %>
<title><fmt:message key="set.section"/></title>
<!--<meta name="menu" content="SampleSet"/>-->
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
	<script type="text/javascript" src="../scripts/jquery-2.1.4.min.js"></script>
	<!--script type="text/javascript" src="../scripts/bootstrap.min.js"></script-->
	<script type="text/javascript" src="../scripts/jquery-ui.min.js"></script>
	<script type="text/javascript" src="../scripts/i18n/grid.locale-cn.js"></script>
	<script type="text/javascript" src="../scripts/jquery.jqGrid.js"></script>
	<script type="text/javascript" src="../scripts/validform/Validform.min.js"></script>
	<script type="text/javascript" src="../scripts/layer/layer.js"></script>
	<script type="text/javascript" src="../scripts/set/section.js"></script>

	<link rel="stylesheet" type="text/css"  href="<c:url value='/styles/ui.jqgrid.css'/>" />
</head>
<div class="row">
<div id="mainTable" class="col-xs-12">
	<div  style="padding-top: 5px;">
		<button type="button" class="btn btn-sm btn-primary " title="添加科室" onclick="AddSection()">
			<i class="ace-icon fa fa-fire bigger-110"></i>
			<fmt:message key="button.add" />
		</button>
		<button type="button" class="btn btn-sm  btn-success" title="编辑科室" onclick="editSection()">
			<i class="ace-icon fa fa-pencil-square bigger-110"></i>
			<fmt:message key="button.edit" />
		</button>
		<button type="button" class="btn btn-sm btn-danger" title="删除科室" onclick="deleteSection()">
			<i class="ace-icon fa fa-times bigger-110"></i>
			<fmt:message key="button.delete" />
		</button>
		<div class="input-group col-sm-3 " style="float: right;" >
			<input type="text" id="query" class="form-control search-query" placeholder="输入编号或名称" />
			<span class="input-group-btn">
				<button type="button" class="btn btn-purple btn-sm" onclick="search()">
					<fmt:message key="button.search"/>
					<i class="ace-icon fa fa-search icon-on-right bigger-110"></i>
				</button>
			</span>
		</div>
	</div>
	<table id="sectionList"></table>
	<div id="pager"></div>
</div>
</div>
<div id="addDialog" style="display: none;width: 500px;" class="row">
	<form id="addResultForm" class="form-horizontal" action="<c:url value='../set/section/edit'/>" method="post">
		<input type="hidden" name="id" id="id" value="0"/>
		<div class="form-group">
			<div class="space-4"></div>
			<label class="col-xs-3 control-label no-padding-right" for="code"> 科室编号 </label>
			<div class="col-xs-8">
				<input type="text" id="code" name= "code" placeholder="科室编号" class="col-xs-8" datatype="*2-16" nullmsg="编号不能为空"/>
			</div>
		</div>
		<div class="form-group">
			<label class="col-xs-3 control-label no-padding-right" for="name"> 科室名称 </label>
			<div class="col-xs-8">
				<input type="text" id="name" name="name" placeholder="科室名称" class="col-xs-8" datatype="*2-16" nullmsg="名称不能为空"/>
			</div>
		</div>
	</form>
</div>