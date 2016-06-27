<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp" %>

<head>
    <title><fmt:message key="sample.manage.receive"/></title>
    <link rel="stylesheet" type="text/css"  href="<c:url value='/styles/ui.jqgrid.css'/>" />
    <meta name="menu" content="SampleManage"/>
    <script type="text/javascript" src="../scripts/jquery-2.1.4.min.js"></script>
	<script type="text/javascript" src="../scripts/jquery-ui.min.js"></script>
	<script type="text/javascript" src="../scripts/bootstrap.min.js"></script>
	<script type="text/javascript" src="../scripts/i18n/grid.locale-cn.js"></script>
	<script type="text/javascript" src="../scripts/jquery.jqGrid.js"></script>
	<script type="text/javascript" src="<c:url value="/scripts/layer/layer.js"/>"></script>
    <script type="text/javascript" src="/scripts/manage/modify.js"></script>

</head>
<style>
	select {
		height:34px;
	}
</style>
<body>
<div class="col-sm-12">
	<div class="row" style="margin-top:10px;">
		<div style="float:left;width:150px;margin-left:10px;">
			<input type="text" id="search_date" name="search_date" onfocus="setDefaultValue()"  class="form-control" />
		</div>
		<div style="float:left;width:150px;margin-left:10px;">
			 <input type="text" id="testSection" name="testSection" class="form-control" onkeyup="this.value=this.value.toUpperCase()"  placeholder="<fmt:message key='sample.modify.testSection'/>">
		</div>
		<div style="float:left;width:300px;margin-left:10px;">	 
			 <input type="text" id="sampleNumber" name="sampleNumber" class="form-control" placeholder="<fmt:message key='sample.modify.sampleNumber'/>">
		</div>
		<div style="float:left;width:150px;margin-left:10px;">
				<select id="operation" name="operation" class="form-control">
					<option value="add">样本号增加</option>
					<option value="reduce">样本号减少</option>
					<option value="inversion">样本号倒置</option>
					<option value="replace">样本号替换</option>
				</select>
		</div>
		<div style="float:left;width:150px;margin-left:10px;">
			<input type="text" id="operationValue" name="operationValue" class="form-control" placeholder="<fmt:message key='sample.modify.operationValue'/>">
		</div>
		<div style="float:left;width:150px;margin-left:10px;">
				<select id="modifyResult" name="modifyResult" class="form-control">
					<option value="1">样本号信息修改</option>
					<option value="0">样本号结果修改</option>
				</select>
		</div>
		<div style="float:left;width:10%;margin-left:10px;">
			<button type="button" onclick="modifySample()" class="btn btn-sm btn-danger"><fmt:message key='sample.modify.button'/></button>
		</div>
	</div>
	
	<div class="row">
		<div class="col-sm-12">
			<div class="widget-box widget-color-blue">
				<div class="widget-header">
					<h4 class="widget-title">
						<i class="ace-icon fa fa-pencil-square-o" aria-hidden="true"></i>
						样本信息恢复
					</h4>
					<div class="widget-toolbar">
						<a href="#" data-action="collapse">
							<i class="ace-icon fa fa-chevron-up"></i>
						</a>
					</div>
				</div>
			</div>
			<div class="widget-body" style="overflow:auto;">
				<div class="widget-main">
					<div class="row">
						<div class="col-sm-1"></div>
						<input type="text" id="logtext" name="logtext" class="col-sm-5" placeholder="医嘱号、样本号">
						<select id="logtexttype" name="logtexttype" class="col-sm-2">
							<option value="0">医嘱号</option>
							<option value="1">样本号</option>
						</select>
						<button type="button" onclick="showLogData()" class="btn btn-sm btn-info">显示日志信息</button>
						<button type="button" onclick="recoverLog()" class="btn btn-sm btn-success">恢复日志记录</button>
					</div>
					<div class="row" style="margin-top:10px;">
						<table id="sampleLog" class="table table-striped table-bordered table-hover"></table>
					</div>
				</div>
			</div>
		</div>
	</div>
	
</div>
</body>