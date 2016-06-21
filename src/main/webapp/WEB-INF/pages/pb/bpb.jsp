<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp"%>

<head>
	<title><fmt:message key="menu.pb" /></title>
	<script type="text/javascript" src="<c:url value='../scripts/pb/pb.js'/> "></script>
	
	<script type="text/javascript" src="<c:url value='/scripts/jquery.jqGrid.min.js'/> "></script>
	<link rel="stylesheet" type="text/css" href="<c:url value='/styles/ui.jqgrid.css'/> " />
	<link rel="stylesheet" type="text/css"  href="<c:url value='/styles/jquery-ui.min.css'/>" />
	<link rel="stylesheet" type="text/css"  href="<c:url value='/styles/bootstrap.min.css'/>" />
	
	<script type="text/javascript" src="../scripts/jquery-2.1.4.min.js"></script>
	<script type="text/javascript" src="../scripts/bootstrap.min.js"></script>
    <script type="text/javascript" src="../scripts/jquery-ui.min.js"></script>
    <script type="text/javascript" src="../scripts/i18n/grid.locale-cn.js"></script>

<style>
</style>
</head>

<div class="form-inline" style="width:1024x;">
	<input type="text" id="date" class="form-control" sytle="width:50px;">
	<button id="changeMonth" class="btn btn-info form-control" style="margin-left:10px;"><fmt:message key='pb.changemonth' /></button>
	<button id="shiftBtn2" class="btn btn-success form-control"><fmt:message key='button.count' /></button>
	<button id="shiftBtn" class="btn btn-success form-control"><fmt:message key='button.submit' /></button>
	<button id="publish" class="btn btn-danger form-control"><fmt:message key='button.publish' /></button>
	
</div>

<div class="fixed">
	<input id="test" value="${arrString}" type="hidden"/>
	<table id="pbhead" class="table" style="margin-top:10px;margin-bottom:0px;font-size:12px;text-align:center;" border="1px;">
	
	
	</table>
</div>
<div class="fixed data">
	<input id="test1" value="${arrBodyString}" type="hidden"/>
	<table id="pbdata" class="table" style="font-size:12px;text-align:center;" border="1px;">
	
	</table>
	<label for="bz" style="margin-top:10px;"><fmt:message key="patient.note"/></label>
	<input type="text" id="bz" name="bz" class="span2" style="margin-left:20px; width:800px;" value="${bz}">
</div>

