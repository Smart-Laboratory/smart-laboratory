<%@ include file="/common/taglibs.jsp" %>
<head>
	<link rel="stylesheet" type="text/css"  href="<c:url value='/styles/jquery-ui.min.css'/>" />
    <link rel="stylesheet" type="text/css"  href="<c:url value='/styles/bootstrap.min.css'/>" />
    
    <script type="text/javascript" src="../scripts/jquery-2.1.4.min.js"></script>
	<script type="text/javascript" src="../scripts/bootstrap.min.js"></script>
	<script type="text/javascript" src="../scripts/jquery-ui.min.js"></script>
	<script type="text/javascript" src="../scripts/print/sample.js"></script>
	<script type="text/javascript" src="../scripts/highcharts.js"></script>
	
	
</head>

<html>
	<div style="float:left;width:96%;margin-left:2%;margin-top:20px;">
		<div style="height:50px;width:100%;">
			<img src="../images/zy_logo.png" style="float:left;margin-left:10%;"/>
			<b style="float:left;font-size:10px;margin-top:25px;margin-left:5%">No:${sampleNo}</b>
		</div>
		<div style="border-top:1px solid #000000;">
			<div style="float:left;margin-left:10px;width:100%;">
				<div style="float:left;width:10%"><fmt:message key="patientInfo.examinaim"/></div>
				<div style="float:left;width:58%"><b id="examinaim">ceshi</b></div>
				<div style="float:left;width:10%"><fmt:message key="sample.type"/></div>
				<div style="float:left;width:22%"><b id="pType">naojiye</b></div>
			</div>
			<div style="float:left;margin-left:10px;width:100%;">
				<div style="float:left;width:10%"><fmt:message key="patientInfo.patientName"/></div>
				<div style="float:left;width:10%"><b id="pName">ceshiyuan</b></div>
				<div style="float:left;width:5%"><fmt:message key="patient.sex"/></div>
				<div style="float:left;width:8%"><b id="pSex">nan</b></div>
				<div style="float:left;width:5%"><fmt:message key="patient.age"/></div>
				<div style="float:left;width:8%"><b id="pAge">100</b></div>
				<div style="float:left;width:7%"><fmt:message key="patient.blh"/></div>
				<div style="float:left;width:15%"><b id="blh">03122086</b></div>
				<div style="float:left;width:5%"><fmt:message key="diagnostic"/></div>
				<div style="float:left;width:27%"><b id="diagnostic">xiaohuadao</b></div>
			</div>
			<div style="float:left;margin-left:10px;width:100%;">
				<div style="float:left;width:10%;" id="staymodetitle">jiuzhen</div>
				<div style="float:left;width:23%"><b id="patientId">990190010005089661</b></div>
				<div style="float:left;width:5%" id="staymodesection">bqu</div>
				<div style="float:left;width:30%"><b id="pSection">nan</b></div>
				<div style="float:left;width:5%" id="bedtitle"><fmt:message key="patient.departbed"/></div>
				<div style="float:left;width:27%"><b id="pBed">naojiye</b></div>
			</div>
		</div>
	</div>
	<div id="resultDiv" style="float:left;width:96%;margin-left:2%;padding:10px;font-size:12px;border-top:1px solid #000000;"></div>
	<div id="imageDiv"></div>
	<div style="float:left;width:92%;margin-left:4%;margin-top:5px;font-size:12px;" id="advise">
		<h4><b><fmt:message key="sample.diagadvise"/>:</b></h4>
		<div id="adviseHtml"></div>
	</div>
	<div id="historyChart" style="float:left;width:96%;margin-left:2%;padding:10px;border-top:1px solid #000000;">
	<p><fmt:message key="sample.history.chart"/></p>
	
	</div>
	<div style="float:left;width:96%;margin-left:2%;padding-top:5px;font-size:12px;border-top:1px solid #000000;">
		<div style="margin-left:10px;width:100%;">
			<div style="float:left;width:5%;">&nbsp;</div>
			<div style="float:left;width:10%;"><fmt:message key="requester.name"/></div>
			<div style="float:left;width:20%;"><b id="requester">&nbsp;</b></div>
			<div style="float:left;width:10%;"><fmt:message key="tat.tester"/></div>
			<div style="float:left;width:20%;"><b id="tester">&nbsp;</b></div>
			<div style="float:left;width:10%;"><fmt:message key="tat.auditor"/></div>
			<div style="float:left;width:20%;">	<img id="auditor" alt="" src="" border="0" width="60px" height="17px"></div>
			<div style="float:left;width:5%;">&nbsp;</div>
		</div>
		<div style="margin-left:10px;width:100%;">
			<div style="float:left;width:5%;">&nbsp;</div>
			<div style="float:left;width:10%;"><fmt:message key="tat.receive"/></div>
			<div style="float:left;width:20%;"><b id="receivetime">&nbsp;</b></div>
			<div style="float:left;width:10%;"><fmt:message key="tat.audit"/></div>
			<div style="float:left;width:20%;"><b id="checktime">&nbsp;</b></div>
			<div style="float:left;width:10%;"><fmt:message key="tat.execute"/></div>
			<div style="float:left;width:20%;"><b id="executetime">&nbsp;</b></div>
			<div style="float:left;width:5%;">&nbsp;</div>
		</div>
	</div>
	<div style="float:left;width:92%;padding-top:10px;font-size:10px;text-align:center;margin-left:4%;border-top:1px solid #000000;"><fmt:message key="sample.print.tip"/></div>
	<input type="hidden" id="hiddenDocId" value="${docId}"/>
	<input type="hidden" id="hiddenSampleNo" value="${sampleNo}"/>
	<input type="hidden" id="hasLast" value="${showLast}"/>
</html>