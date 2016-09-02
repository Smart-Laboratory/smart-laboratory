<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en" xmlns="http://www.w3.org/1999/xhtml">

<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp"%>


<head>
	
	<meta charset="utf-8" />
    <script type="text/javascript" src="<c:url value="/scripts/jquery-1.8.3.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/layer/layer.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/layer/extend/layer.ext.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/laydate/laydate.js"/>"></script>
    
	<link rel="stylesheet" type="text/css"  href="../styles/jquery-ui.min.css"/>
    <script type="text/javascript" src="../scripts/jquery-ui.min.js"></script>
    <script type="text/javascript" src="../scripts/i18n/grid.locale-cn.js"></script>
    <script type="text/javascript" src="../scripts/jquery.jqGrid.js"></script>
	<link rel="stylesheet" type="text/css"  href="<c:url value='../styles/ui.jqgrid.css'/>" />
	<script type="text/javascript" src="../scripts/quality/trace.js"></script>
	<link rel="stylesheet" type="text/css"  href="<c:url value='/styles/ruleLib.css'/>" /> 
	<link rel="stylesheet" type="text/css"  href="<c:url value='../styles/bootstrap.min.css'/>" />
	
	<script type="text/javascript" src="../scripts/quality/trace.js"></script>
	<title><fmt:message key="menu.quality.trace" /></title>
<style>
		.pLabel{
			margin-left:10px;
		}
		body, th, td, button, input, select, textarea {
            font-family: "Helvetica Neue","Helvetica,"Microsoft Yahei",tahoma,arial","Verdana","sans-serif","\5B8B\4F53";
            font-size: 13px;
            color: #222;

        }
        body{
            background: #ECF0F1;
        }
        .gred{
        	background: #d9edf7;
        }
        .pItem{
        	margin:5px 5px;
        }
        .ui-jqgrid .ui-jqgrid-bdiv{
        	overflow:hidden;
        }
		.laydate_body .laydate_bottom{
			border-bottom:hidden;
		}
</style>
	
</head>
<body>

<input type='hidden' id='type' value="${type }" />
<input type='hidden' id='name' value="${name }" />
<div class="form-inline" style="margin-top:15px;">
<table>
<tbody>
	<tr>
		<td>
			<label for="from" style="margin-left: 20px;"><b><fmt:message key="from" /></b></label>
			<input type="text" id="from" name="from" class="text"/>
		</td>
		<td>
			<label for="to" style="margin-left: 10px;"><b><fmt:message key="to" /></b></label>
			<input type="text" id="to" name="to" class="text" style="margin-left: 10px;"/>
		</td>
		<td>
			<label for="search_text" style="margin-left: 50px;"></label>
			<input type="text" id="search_text" name="search_text" class="text"/>
		</td>
		<td>
			<select id="search_select" class="select">
				<option value="1">物流点</option>
				<option value="2">姓名</option>
				<option value="3"><fmt:message key='patient.blh'/></option>
				<option value="4"><fmt:message key='sample.id'/></option>
				<option value="5">接收科室</option>
			</select>
		</td>
		<td>
			<label for="sampleState" style="margin-left: 50px;">样本状态</label>
			<select id="sampleState" class="select">
				<option value="1">全部</option>
				<option value="2">已采集</option>
				<option value="3">已送出</option>
				<option value="4">科室接收</option>
				<option value="5">组内接收</option>
				<option value="6">已审核</option>
			</select>
		</td>
		<td>
			<button id="searchBtn" class="btn btn-primary" style="margin-left: 120px;"><fmt:message key='search'/></button>
		</td>
	</tr>
</tbody>
</table>

</div>

<div class="form-inline" style="margin-top: 10px;">
	<div id="searchHeader" style="float: left;margin-left:30px; width: 35%;overflow:auto;">
			<table id="s3list" style="text-align:center;"></table>
			<div id="s3pager"></div>
	</div>
	<div id="midContent"
		style="float: left; width: 50%; margin-left: 30px; display: none;">
		<div class="clearfix">
			<div id="patient-info" class="gred" style="margin-bottom:2px;padding:0px;padding-left:10px;padding-bottom:4px;">
				<div class="pItem gred form-inline">
					<div class="gred" style="float:left;width:25%;">
						<span class="pLabel"><fmt:message key="patient.name" />:</span>
						<span class="pText"><b id="pName"></b></span>
					</div>
					<div class="gred" style="float:left;width:25%;">
						<span class="pLabel"><fmt:message key="patient.sex" />:</span>
						<span class="pText"><b id="pSex"></b></span>
					</div>
					<div class="gred"  style="float:left;width:25%;">
						<span class="pLabel"><fmt:message key="patient.age" />:</span>
						<span class="pText"><b id="pAge"></b></span>
					</div>
					<div class="gred"  style="float:left;width:25%;">
						<span class="pLabel"><fmt:message key="patient.sampleType" />:</span>
						<span class="pText"><b id="pType"></b></span>
					</div>
				</div>
				<div class="pItem gred form-inline" >
					<div class="gred"  style="float:left;width:40%;">
						<span class="pLabel"><fmt:message key="patient.blh" />:</span>
						<span class="pText"><b id="blh"></b></span>
					</div>
					<div class="gred"  style="float:left;width:60%;">
						<span class="pLabel"><fmt:message key="patient.patientId" />:</span>
						<span class="pText"><b id="pId"></b></span>
					</div>
				</div>

				<div class="pItem gred form-inline">
					<div class="gred"  style="float:left;width:40%;">
						<span class="pLabel"><fmt:message key="patient.section"/>:&nbsp;</span>
						<span class="pText"><b id="pSection"></b></span>
					</div>
					<div class="gred"  style="float:left;width:60%;">
						<span class="pLabel"><fmt:message key="diagnostic"/>:&nbsp;</span>
						<span class="pText"><b id="diagnostic"></b></span>
					</div>
				</div>
				<div class="pItem gred form-inline">
					<div class="gred"  style="float:left;width:40%;">
						<span class="pLabel">送检科室:&nbsp;</span>
						<span class="pText"><b id="sjSection"></b></span>
					</div>
					<div class="gred"  style="float:left;width:60%;">
						<span class="pLabel">抽血信息:&nbsp;</span>
						<span class="pText"><b id="cxxx"></b></span>
					</div>
				</div>
			</div>
		</div>
		<div style="height:20px"></div>
		<div>
		<table class="table">
			<tbody>
			<tr><th style="width:20%;"><fmt:message key='tat.request' /></th><td style="width:30%;"><span id="tat_request"></span></td>
			<th style="width:20%;"><fmt:message key='tat.requester' /></th><td style="width:30%;"><span id="tat_requester"></span></td></tr>
			<tr><th><fmt:message key='tat.execute' /></th><td><span id="tat_execute"></span></td>
			<th><fmt:message key='tat.executor' /></th><td><span id="tat_executor"></span></td></tr>
			<tr><th><fmt:message key='tat.send' /></th><td><span id="tat_send"></span></td>
			<th><fmt:message key='tat.sender' /></th><td><span id="tat_sender"></span></td></tr>	
			</tbody>
		</table>
				
			
		<div style="background:greenyellow;">
			<table class="table" >
				<tbody id="logistic">
				</tbody>
			</table>
		</div>
		<table class="table">
			<tbody>	
			<tr><th><fmt:message key='tat.ksreceive' /></th><td><span id="tat_ksreceive"></span></td>
			<th><fmt:message key='tat.ksreceiver' /></th><td><span id="tat_ksreceiver"></span></td></tr>
			<tr><th style="width:20%;"><fmt:message key='tat.receive' /></th><td style="width:30%;"><span id="tat_receive"></span></td>
			<th style="width:20%;"><fmt:message key='tat.receiver' /></th><td style="width:30%;"><span id="tat_receiver"></span></td></tr>
			<tr><th><fmt:message key='tat.tester' /></th><td><span id="tat_tester"></span></td>
			<th><fmt:message key='tat.audit' /></th><td><span id="tat_audit"></span></td></tr>
			<tr><th><fmt:message key='tat.auditor' /></th><td><span id="tat_auditor"></span></td>
			<th><fmt:message key='tat.audit.tat' /></th><td><span id="audit_tat"></span></td></tr>
			</tbody>
		</table>
		</div>
		<div style="font-size: 13px; display:none;margin-top: 10px;">
			<div style="margin-left:60px;">
				<input type="hidden" id="hiddenDocId"/>
			</div>
		</div>
	</div>
</div>
</body>
</html>