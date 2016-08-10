<%@ include file="/common/taglibs.jsp"%>

<head>
<title><fmt:message key="auditView.title" /></title>
<meta name="menu" content="IntelAuditAndExplain" />
<script type="text/javascript" src="../scripts/jquery-2.1.4.min.js"></script>
<link rel="stylesheet" href="../styles/jquery-ui.min.css" type="text/css">
<script type="text/javascript" src="../scripts/jquery-ui.min.js"></script>
<script type="text/javascript" src="<c:url value='/scripts/jquery.json-2.3.min.js'/> "></script>
<script type="text/javascript" src="<c:url value='/scripts/jquery.bgiframe-2.1.2.js'/> "></script>
<link rel="stylesheet" type="text/css" href="<c:url value='/styles/ui.jqgrid.css'/> " />
<link rel="stylesheet" type="text/css" href="<c:url value='/styles/critical.css'/> " />
<script type="text/javascript" src="<c:url value='/scripts/manage/critical.js'/> "></script>
</head>

<div>
	<div class="">
	<div class="row clearfix">
		<div class="span9" style="padding-top:4px;">
		<ul class="nav nav-pills">
			<li class="active"><a href="<c:url value='/critical/undeal'/>"><span style="font-size:18px;"><fmt:message key='info.danger.undeal' /></span></a></li>
			<li><a href="<c:url value='/critical/dealed' />"><span style="font-size:18px;"><fmt:message key='info.danger.deal' /></span></a></li>
			<li><a id="printBtn"><span style="font-size:18px;"><fmt:message key="print" /></span></a></li>
		</ul>
		</div>
	</div>
	</div>
	
	<display:table name="criticals" id="ctl" cellspacing="0" cellpadding="0" class="table table-hover" pagesize="10" requestURI="">
    	<display:column property="id" titleKey="" sortable="false" escapeXml="true"/>
    	<display:column property="sampleNo" titleKey="sample.no" sortable="false" escapeXml="true"/>
    	<display:column property="patientName" titleKey="patient.name" sortable="false" style="width:60px;" escapeXml="true" url="/explain/patientList" paramId="patientId" paramProperty="patientId"/>
    	<display:column property="blh" titleKey="patient.blh" sortable="false" style="width:60px;" escapeXml="true"/>
    	<display:column property="section" titleKey="patient.section" sortable="false" escapeXml="true"/>
    	<display:column property="infoValue" titleKey="info.danger" sortable="false" escapeXml="true"/>
    	<display:column property="requester" titleKey="workid" sortable="false" class="reqId" escapeXml="true"/>
    	<display:column property="docId" titleKey="operation" sortable="false" class="operation" escapeXml="true"/>
	</display:table>
</div>

<div id="operationDlg" align="left" title='<fmt:message key='info.danger' /><fmt:message key="operation" />'>
	<input id="doc" type="hidden" />
	<input id="index" type="hidden">
	<div id="historyInfo" class="alert alert-info" style="margin-bottom:5px;padding-right:8px;">
		<div id="historyText"></div>
	</div>
	<div class="row">
		<label class="spana"><fmt:message key="patient.info" /> : </label>
		<label class="spanb" id="patientPhoneText"></label>
		<label class="spand" style="width:270px;" id="patientAddressText"></label>
	</div>
	<div class="row">
		<label class="spana"><fmt:message key="requester.name" /> : </label>
		<label class="spanb" id="requesterNameText"></label>
		<label class="spanb"><fmt:message key="requester.section" /> : </label>
		<label class="spanb" id="requesterSectionText"></label>
	</div>
	<div class="row">
		<label class="spana"><fmt:message key="requester.phone" /> : </label>
		<label class="spand" id="requesterPhoneText"></label>
	</div>
	
	<div id="ward" style="display:none;">
		<div class="row">
			<label class="spana"><fmt:message key="ward.type" /> : </label>
			<label class="spanb" id="wardTypeText"></label>
			<label class="spanb"><fmt:message key="ward.phone" /> : </label>
			<label class="spanb" id="wardPhoneText"></label>
		</div>
		<div class="row">
			<label class="spana"><fmt:message key="ward.section" /> : </label>
			<label class="spand" id="wardSectionText"></label>
		</div>		
	</div>
	
	<div class="row">
		<label class="spana" style="float:left;"><fmt:message key="role" /> : </label>
		<label class="radio spanb" id="role-radio" style="float:left;margin-top:0px;">
			<input type="radio" name="role" value="0" checked><fmt:message key="doctor" />
		</label>
		<label class="radio spanb" style="float:left;margin-top:0px;">
			<input type="radio" name="role" value="1"><fmt:message key="nurse" />
		</label>
		<label class="radio spanb" style="float:left;margin-top:0px;">
			<input type="radio" name="role" value="2"><fmt:message key="patient" />
		</label>
	</div>
	<div class="row">
		<label class="spana" style="margin-top:5px;"><fmt:message key="number" /> : </label>
		<input type="text" id="targetText" class="spand">
	</div>
	<div class="row">
		<label class="spana" style="float:left;"><fmt:message key="contact" /> : </label>
		<label class="radio spanb" style="float:left;margin-top:0px;">
			<input type="radio" name="success" value="1" ><fmt:message key="success" />
		</label>
		<label class="radio spanb" style="float:left;margin-top:0px;">
			<input type="radio" id="failed-radio" name="success" value="0" checked><fmt:message key="failed" />
		</label>
	</div>
	<div class="row">
		<label class="spana" style="margin-top:5px;"><fmt:message key="contact.info" /> : </label>
		<input type="text" id="resultText" class="spand">
	</div>
	<div class="row">
		<label class="spana"></label>
		<button id="dealBtn" class="btn btn-info spanb" style="margin-left:0px;"></button>
		<button id="deleteBtn" class="btn btn-danger spanb" style="margin-left:10px;"><fmt:message key="button.delete" /></button>
	</div>
	
</div>

<div id="print" align="left" title='<fmt:message key="audit.preview" />'>
    <button class="btn btn-success"
            onclick="document.getElementById('iframe_sample').contentWindow.print();"><fmt:message
            key="audit.print"/></button>
    <div id="samplePrintFrame" style="height:480px;">
	</div>
    <button class="btn btn-success"
            onclick="document.getElementById('iframe_sample').contentWindow.print();"><fmt:message
            key="audit.print"/></button>
</div>