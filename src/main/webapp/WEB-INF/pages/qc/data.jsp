<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp" %>

<head>
	<title><fmt:message key="menu.quality.qc.data"/></title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
	<link rel="stylesheet" type="text/css"  href="<c:url value='/styles/ui.jqgrid.css'/>" />
	<script type="text/javascript" src="../scripts/jquery-ui.min.js"></script>
	<script type="text/javascript" src="../scripts/i18n/grid.locale-cn.js"></script>
	<script type="text/javascript" src="../scripts/jquery.jqGrid.js"></script>
	<script type="text/javascript" src="../scripts/validform/Validform.min.js"></script>
	<script type="text/javascript" src="../scripts/layer/layer.js"></script>
	<script type="text/javascript" src="<c:url value="/scripts/laydate/laydate.js"/>"></script>
	<script type="text/javascript" src="../scripts/qc/data.js"></script>
</head>

<div class="row">
	<div class="col-xs-12">
		<div  style="padding-top: 5px;">
			<div class="input-group col-sm-2 " style="float: left;" >
				<span class="input-group-addon">测量日期</span>
				<input type="text" id="measuretime" class="form-control search-query" value="${ today }"/>
			</div>
			<div class="input-group col-sm-1 " style="float: left;"></div>
			<div class="input-group col-sm-2 " style="float: left;" >
				<span class="input-group-addon">选择仪器</span>
				<select id="device" class="form-control search-query">
					<c:forEach var="device" items="${deviceList}">
						<option value="${device.id}">${device.id} | ${device.name}</option>
			        </c:forEach>
				</select>
			</div>
			<div class="input-group col-sm-1 " style="float: left;"></div>
			<div class="input-group col-sm-2 " style="float: left;" >
				<span class="input-group-addon">质控批号</span>
				<select id="qcBatch" class="form-control search-query">
				</select>
			</div>
			<div class="input-group col-sm-1 " style="float: left;"></div>
			<div class="col-sm-3" style="float: right;">
				<button type="button" class="btn btn-sm btn-primary " title="添加质控规则" onclick="Add()">
					<i class="ace-icon fa fa-fire bigger-110"></i>
					<fmt:message key="button.add" />
				</button>
				<button type="button" class="btn btn-sm  btn-success" title="编辑质控规则" onclick="Edit()">
					<i class="ace-icon fa fa-pencil-square bigger-110"></i>
					<fmt:message key="button.edit" />
				</button>
				<button type="button" class="btn btn-sm btn-danger" title="删除质控规则" onclick="Delete()">
					<i class="ace-icon fa fa-times bigger-110"></i>
					<fmt:message key="button.delete" />
				</button>
			
			</div>
			
		</div>
	</div>
</div>
<div class="row">
	<div class="col-xs-9" id="qcDataTable">
		<table id="list"></table>
		<div id="pager"></div>
	</div>
	<div class="col-xs-3">
		<table id="qcInfo"></table>
		<div id="pager"></div>
	</div>
</div>

<div id="editRule" style="display: none;width:500px;overflow:hidden" class="main-container">
	<form class="form-horizontal" id="ruleForm" action="#" method="post">
		<input type="hidden" name="id" id="id" value="0"/>
		<div class="row" style="padding-top: 5px;">
			<div class="form-group">
	            <div class="space-4"></div>
	            <label class="col-xs-3 control-label no-padding-right" for="name"> 规则名称 </label>
	            <div class="col-xs-8">
	                <input type="text" id="name" name="name" placeholder="规则名称" class="col-xs-8"/>
	            </div>
	        </div>
			<div class="form-group">
	            <div class="space-4"></div>
	            <label class="col-xs-3 control-label no-padding-right" for="describe"> 规则描述 </label>
	            <div class="col-xs-8">
	                <textarea type="text" id="describe" name="describe" placeholder="规则描述 " class="col-xs-8"></textarea>
	            </div>
	        </div>
	        <div class="form-group">
	            <div class="space-4"></div>
	            <label class="col-xs-3 control-label no-padding-right" for="inuse"> 是否使用 </label>
	            <div class="col-xs-8">
	            	<select class="col-xs-8" id="inuse" name="inuse">
	                    <option value="1">是</option>
	                    <option value="0">否</option>
	                </select>
	            </div>
	        </div>
		</div>
	</form>
</div>