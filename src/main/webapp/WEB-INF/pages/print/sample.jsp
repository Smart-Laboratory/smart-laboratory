<%@ include file="/common/taglibs.jsp" %>
<head>
	<link rel="stylesheet" type="text/css"  href="<c:url value='/styles/jquery-ui.min.css'/>" />
    <link rel="stylesheet" type="text/css"  href="<c:url value='/styles/bootstrap.min.css'/>" />
    
    <script type="text/javascript" src="../scripts/jquery-2.1.4.min.js"></script>
	<script type="text/javascript" src="../scripts/bootstrap.min.js"></script>
	<script type="text/javascript" src="../scripts/jquery-ui.min.js"></script>
	<script type="text/javascript" src="../scripts/print/sample.js"></script>
	
</head>

<html>
	<div style="float:left;width:96%;margin-left:14px;height:15px;text-align:center;margin-top:10px;">
		<div id="examinaim" style="float:left;width:80%;">&nbsp;</div>
		<div id="date" style="float:left;width:20%;">&nbsp;</div>
	</div>
	<div style="float:left;width:96%;margin-left:14px;margin-top:5px;">
		<div style="height:30px;width:100%;">
			<div style="float:left;margin-top:20px;font-size:10px;width:20%">
				<fmt:message key="patient.blh"/>:<div id="blh" style="width:70%;float:right;">03122086</div>
			</div>
			<img src="/images/zy_logo.png" style="float:left;margin-left:20px;"/>
			<div style="float:right;margin-top:20px;font-size:10px;">No:${sampleNo}</div>
		</div>
		<hr style="height:1px;background-color:#000000;width:100%;margin-top:10px;margin-bottom:5px;"/>
		<div style="height:20px;margin-left:10px;width:100%;">
			<div style="float:left;width:10%"><fmt:message key="patientInfo.patientName"/></div>
			<div style="float:left;width:23%"><b id="pName">ceshiyuan</b></div>
			<div style="float:left;width:5%"><fmt:message key="patient.sex"/></div>
			<div style="float:left;width:5%"><b id="pSex">nan</b></div>
			<div style="float:left;width:5%"><fmt:message key="patient.age"/></div>
			<div style="float:left;width:5%"><b id="pAge">100</b></div>
			<div style="float:left;width:5%"><fmt:message key="sample.type"/></div>
			<div style="float:left;width:10%"><b id="pType">naojiye</b></div>
			<div style="float:left;width:5%"><fmt:message key="diagnostic"/></div>
			<div style="float:left;width:27%"><b id="diagnostic">xiaohuadao</b></div>
		</div>
		<div style="height:20px;margin-left:10px;width:100%;">
			<div style="float:left;width:10%;" id="staymodetitle">jiuzhen</div>
			<div style="float:left;width:23%"><b id="patientId">990190010005089661</b></div>
			<div style="float:left;width:5%" id="staymodesection">bqu</div>
			<div style="float:left;width:30%"><b id="pSection">nan</b></div>
			<div style="float:left;width:5%" id="bedtitle"><fmt:message key="patient.departbed"/></div>
			<div style="float:left;width:10%"><b id="pBed">naojiye</b></div>
			<div style="float:left;width:5%"><fmt:message key="patient.note"/></div>
			<div style="float:left;width:12%"><b id="pNote"></b></div>
		</div>
		<hr style="height:1px;background-color:#000000;width:100%;margin-top:5px;margin-bottom:5px;"/>
	</div>
	<div id="resultDiv" style="float:left;width:96%;margin-left:14px;margin-top:2px;font-size:12px;height:750px;">
		<div style="height:20px;margin-left:10px;width:100%;"><b>
			<div style="float:left;width:5%;">No.</div>
			<div style="float:left;width:25%;"><fmt:message key="project"/></div>
			<div style="float:left;width:10%;text-align:center;"><fmt:message key="result"/></div>
			<div style="float:left;width:10%;text-align:center;"><fmt:message key="result.history"/>1</div>
			<div style="float:left;width:10%;text-align:center;"><fmt:message key="result.history"/>2</div>
			<div style="float:left;width:10%;text-align:center;"><fmt:message key="result.history"/>3</div>
			<div style="float:left;width:20%;text-align:center;"><fmt:message key="scope"/></div>
			<div style="float:left;width:10%;text-align:center;"><fmt:message key="unit"/></div>
		</b></div>
		<hr style="height:1px;background-color:#000000;width:100%;margin-top:0px;margin-bottom:0px;"/>
	</div>
	<div style="float:left;width:96%;margin-left:14px;margin-top:2px;font-size:12px;height:40px;">
		<hr style="height:1px;background-color:#000000;width:100%;margin-top:0px;margin-bottom:0px;"/>
		<div style="height:20px;margin-left:10px;width:100%;">
			<div style="float:left;width:5%;">&nbsp;</div>
			<div style="float:left;width:10%;"><fmt:message key="requester.name"/></div>
			<div style="float:left;width:20%;"><b id="requester">&nbsp;</b></div>
			<div style="float:left;width:10%;"><fmt:message key="tat.tester"/></div>
			<div style="float:left;width:20%;"><b id="tester">&nbsp;</b></div>
			<div style="float:left;width:10%;"><fmt:message key="tat.auditor"/></div>
			<div style="float:left;width:20%;"><b id="auditor">&nbsp;</b></div>
			<div style="float:left;width:5%;">&nbsp;</div>
		</div>
		<div style="height:20px;margin-left:10px;width:100%;">
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
	<div style="float:left;width:96%;margin-top:2px;font-size:10px;text-align:center;"><fmt:message key="sample.print.note"/></div>
	<input type="hidden" id="hiddenDocId" value="${docId}"/>
	<input type="hidden" id="hiddenSampleNo" value="${sampleNo}"/>
	<input type="hidden" id="hasLast" value="${showLast}"/>
</html>