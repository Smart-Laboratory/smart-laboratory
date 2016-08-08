<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp"%>


<head>
	<title><fmt:message key="menu.quality.trace" /></title>
	<link rel="stylesheet" type="text/css"  href="../styles/jquery-ui.min.css"/>
    <script type="text/javascript" src="../scripts/jquery-ui.min.js"></script>
    <script type="text/javascript" src="../scripts/i18n/grid.locale-cn.js"></script>
    <script type="text/javascript" src="../scripts/jquery.jqGrid.js"></script>
	<link rel="stylesheet" type="text/css"  href="<c:url value='../styles/ui.jqgrid.css'/>" />
	<script type="text/javascript" src="../scripts/quality/trace.js"></script>
	<link rel="stylesheet" type="text/css"  href="<c:url value='/styles/ruleLib.css'/>" /> 
	<link rel="stylesheet" type="text/css"  href="<c:url value='../styles/bootstrap.min.css'/>" />
</head>
<style>
.pLabel{
	margin-left:10px;
}

</style>

<div class="form-inline">
	<label for="from" style="margin-left: 20px;"><b><fmt:message key="from" /></b></label>
	<input type="text" id="from" name="from" class="form-control"/>
	<label for="to" style="margin-left: 10px;"><b><fmt:message key="to" /></b></label>
	<input type="text" id="to" name="to" class="form-control" style="margin-left: 10px;"/>
	
	<label for="search_text" style="margin-left: 50px;"></label>
	<input type="text" id="search_text" name="search_text" class="form-control"/>
	<select id="search_select" class="form-control select">
		<option value="1"><fmt:message key='patient.name'/></option>
		<option value="2"><fmt:message key='patient.section'/></option>
		<option value="3"><fmt:message key='patient.blh'/></option>
		<option value="4"><fmt:message key='sample.id'/></option>
	</select>
	<button id="searchBtn" class="btn btn-info" style="margin-left: 120px;"><fmt:message key='search'/></button>
</div>

<div style="margin-top: 10px;">
	<div id="searchHeader" style="float: left;margin-left：15px; width: 40%;">
		<div id="sampleListPanel">
			<table id="s3list"></table>
			<div id="s3pager"></div>
		</div>
	</div>
	<div id="midContent"
		style="float: left; width: 50%; margin-left: 30px; display: none;">
		<div class="clearfix">
			<div id="patient-info" class="alert alert-info col-sm-12" style="margin-bottom:2px;padding:0px;padding-left:10px;padding-bottom:4px;">
				<div class="pItem col-sm-12 form-inline">
					<div class="col-sm-3">
						<span class="pLabel"><fmt:message key="patient.name" />:</span>
						<span class="pText"><b id="pName"></b></span>
					</div>
					<div class="col-sm-3">
						<span class="pLabel"><fmt:message key="patient.sex" />:</span>
						<span class="pText"><b id="pSex"></b></span>
					</div>
					<div class="col-sm-3">
						<span class="pLabel"><fmt:message key="patient.age" />:</span>
						<span class="pText"><b id="pAge"></b></span>
					</div>
					<div class="col-sm-3">
						<span class="pLabel"><fmt:message key="patient.sampleType" />:</span>
						<span class="pText"><b id="pType"></b></span>
					</div>
				</div>
				<div class="pItem">
					<div class="col-sm-5">
						<span class="pLabel"><fmt:message key="patient.blh" />:</span>
						<span class="pText"><b id="blh"></b></span>
					</div>
					<div class="col-sm-7">
						<span class="pLabel"><fmt:message key="patient.patientId" />:</span>
						<span class="pText"><b id="pId"></b></span>
					</div>
				</div>

				<div class="pItem">
					<div class="col-sm-5">
						<span class="pLabel"><fmt:message key="patient.section"/>:&nbsp;</span>
						<span class="pText"><b id="pSection"></b></span>
					</div>
					<div class="col-sm-7">
						<span class="pLabel"><fmt:message key="diagnostic"/>:&nbsp;</span>
						<span class="pText"><b id="diagnostic"></b></span>
					</div>
				</div>
				<div class="pItem">
					<div class="col-sm-5">
						<span class="pLabel">送检科室:&nbsp;</span>
						<span class="pText"><b id="sjSection"></b></span>
					</div>
					<div class="col-sm-7">
						<span class="pLabel">抽血信息:&nbsp;</span>
						<span class="pText"><b id="cxxx"></b></span>
					</div>
				</div>
			</div>
		</div>
		<div style="height:20px"></div>
		<table class="table">
			<tbody>
			<tr><th><fmt:message key='tat.request' /></th><td><span id="tat_request"></span></td>
			<th><fmt:message key='tat.requester' /></th><td><span id="tat_requester"></span></td></tr>
			<tr><th><fmt:message key='tat.execute' /></th><td><span id="tat_execute"></span></td>
			<th><fmt:message key='tat.executor' /></th><td><span id="tat_executor"></span></td></tr>
			<tr><th><fmt:message key='tat.send' /></th><td><span id="tat_send"></span></td>
			<th><fmt:message key='tat.sender' /></th><td><span id="tat_sender"></span></td></tr>
			<tr><th><fmt:message key='tat.ksreceive' /></th><td><span id="tat_ksreceive"></span></td>
			<th><fmt:message key='tat.ksreceiver' /></th><td><span id="tat_ksreceiver"></span></td></tr>
			<tr><th><fmt:message key='tat.receive' /></th><td><span id="tat_receive"></span></td>
			<th><fmt:message key='tat.receiver' /></th><td><span id="tat_receiver"></span></td></tr>
			<tr><th><fmt:message key='tat.tester' /></th><td><span id="tat_tester"></span></td>
			<th><fmt:message key='tat.audit' /></th><td><span id="tat_audit"></span></td></tr>
			<tr><th><fmt:message key='tat.auditor' /></th><td><span id="tat_auditor"></span></td>
			<th><fmt:message key='tat.audit.tat' /></th><td><span id="audit_tat"></span></td></tr>
			</tbody>
		</table>
		<div style="font-size: 13px; display:none;margin-top: 10px;">
			<div style="margin-left:60px;">
				<input type="hidden" id="hiddenDocId"/>
			</div>
		</div>
	</div>
</div>
