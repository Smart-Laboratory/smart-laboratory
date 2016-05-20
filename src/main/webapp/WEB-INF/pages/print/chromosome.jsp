<%@ include file="/common/taglibs.jsp"%>
<head>
<link rel="stylesheet" type="text/css"
	href="<c:url value='/styles/jquery-ui.min.css'/>" />
<link rel="stylesheet" type="text/css"
	href="<c:url value='/styles/bootstrap.min.css'/>" />

<script type="text/javascript" src="../scripts/jquery-2.1.4.min.js"></script>
<script type="text/javascript" src="../scripts/bootstrap.min.js"></script>
<script type="text/javascript" src="../scripts/jquery-ui.min.js"></script>
<script type="text/javascript" src="../scripts/print/chromosome.js"></script>
<script type="text/javascript" src="../scripts/highcharts.js"></script>
</head>
<!-- 张晋南2016-5-12 染色体打印报告 -->
<html>
<div style="height:50px;width:100%;text-align:center">
	<img src="../images/zy_head_logo.png" />
</div>
<div style="height:50px;width:100%;text-align:center">
	<b id="pType" style="font-size:25px">标本类型</b>
</div>
<div style="border-top:1px solid #000000;">
	<div style="float:left;margin-left:10px;width:100%;">
		<!-- 病人姓名 -->
		<div style="float:left;width:10%">
			<fmt:message key="patientInfo.patientName" />
		</div>
		<div style="float:left;width:10%">
			<b id="pName">ceshiyuan</b>
		</div>
		<!-- 病人性别 -->
		<div style="float:left;width:5%">
			<fmt:message key="patient.sex" />
		</div>
		<div style="float:left;width:8%">
			<b id="pSex">nan</b>
		</div>
		<!-- 病人年龄 -->
		<div style="float:left;width:5%">
			<fmt:message key="patient.age" />
		</div>
		<div style="float:left;width:8%">
			<b id="pAge">100</b>
		</div>
		<!-- 标本类型 -->
		<div style="float:left;width:5%">
			<fmt:message key="sample.type" />
		</div>
		<div style="float:left;width:10%">
			<b id="pType2">00000</b>
		</div>
		<!-- 门诊：就诊卡号，住院：住院号 -->
		<div style="float:left;width:10%;" id="staymodetitle">jiuzhen</div>
		<div style="float:left;width:23%">
			<b id="patientId">990190010005089661</b>
		</div>
	</div>
	<div style="float:left;width:50%;margin-top:15px;">
		<div style="float:left;margin-left:10px;width:100%;">
			<!-- 病区-->
			<div style="float:left;width:10%" id="staymodesection">bqu</div>
			<div style="float:left;margin-left:5%">
				<b id="pSection">nan</b>
			</div>
		</div>
		<div style="float:left;margin-left:10px;width:100%;padding-top:15px">
			<!-- 床号 -->
			<div style="float:left;" id="bedtitle">
				<fmt:message key="patient.departbed" />
			</div>
			<div style="float:left;margin-left:6%">
				<b id="pBed">naojiye</b>
			</div>
		</div>
		<!-- 标本编号 -->
		<div style="float:left;margin-left:10px;width:100%;padding-top:15px">
			<div style="float:left;width:15%">
				<fmt:message key="sample.no" />
			</div>
			<b style="float:left;margin-left:2%">${sampleNo}</b>
		</div>
		<!-- 诊断 -->
		<div style="float:left;margin-left:10px;width:100%;padding-top:15px">
			<div style="float:left;">
				<fmt:message key="diagnostic" />
			</div>
			<div style="float:left;margin-left:6%">
				<b id="diagnostic">xiaohuadao</b>
			</div>
		</div>
		<!-- 检测方法 -->
		<div style="float:left;margin-left:10px;width:100%;padding-top:15px">
			<div style="float:left;">
				<fmt:message key="index.method" />
			</div>
			<b style="float:left;margin-left:2%">223132</b>
		</div>
	</div>
</div>
<!-- 小图1 -->
<div 
	style="float:right;margin-top:1px;width:50%;height:200px;">
	<img id="imageDiv1" alt="" src="" width="98%" height="98%" style="border:1px solid #000000;width:325px;height:190px">
</div>
<div style="float:left;width:5%;">&nbsp;</div>
<!-- 大图2 -->
<div 
	style="float:left;width:100%;border:1px;text-align:center;">
	<img id="imageDiv2" alt="" src="" border="1" width="650px" height="470px" style="border:1px solid #000000;">
</div>
<div style="float:left;width:5%;">&nbsp;</div>

<div
	style="float:left;width:96%;margin-left:2%;padding-top:5px;font-size:12px;border-top:1px solid #000000;">
	<!-- 核型 -->
	<div style="margin-left:10px;width:100%;">
		<div style="float:left;font-size:20px">
			<fmt:message key="chromosome.karyotype" />
		</div>
		<div style="float:left;margin-left:2%;font-size:20px">
			<b id="karyotype"></b>
		</div>
	</div>
	<!-- 结果描述 -->
	<div style="float:left;margin-left:10px;width:100%;">
		<div style="float:left;font-size:10px">
			<fmt:message key="chromosome.resultDescription" />
		</div>
		<div style="margin-left:10%;font-size:10px">
			<b id="resultDescription"></b>
		</div>
	</div>
</div>
<div
	style="float:left;width:96%;margin-left:2%;padding-top:5px;font-size:12px;border-top:1px solid #000000;">
	<div style="margin-left:10px;width:100%;">
		<div style="float:left;width:5%;">&nbsp;</div>
		<div style="float:left;width:10%;">
			<fmt:message key="requester.name" />
		</div>
		<div style="float:left;width:20%;">
			<b id="requester">&nbsp;</b>
		</div>
		<div style="float:left;width:10%;">
			<fmt:message key="tat.tester" />
		</div>
		<div style="float:left;width:20%;">
			<b id="tester">&nbsp;</b>
		</div>
		<div style="float:left;width:10%;">
			<fmt:message key="tat.auditor" />
		</div>
		<div style="float:left;width:20%;">
			<img id="auditor" alt="" src="" border="0" width="60px" height="17px">
		</div>
		<div style="float:left;width:5%;">&nbsp;</div>
	</div>
	<div style="margin-left:10px;width:100%;">
		<div style="float:left;width:5%;">&nbsp;</div>
		<div style="float:left;width:10%;">
			<fmt:message key="tat.receive" />
		</div>
		<div style="float:left;width:20%;">
			<b id="receivetime">&nbsp;</b>
		</div>
		<div style="float:left;width:10%;">
			<fmt:message key="tat.audit" />
		</div>
		<div style="float:left;width:20%;">
			<b id="checktime">&nbsp;</b>
		</div>
		<div style="float:left;width:10%;">
			<fmt:message key="tat.execute" />
		</div>
		<div style="float:left;width:20%;">
			<b id="executetime">&nbsp;</b>
		</div>
		<div style="float:left;width:5%;">&nbsp;</div>
	</div>
</div>
<div
	style="float:left;width:92%;padding-top:10px;font-size:10px;text-align:left;margin-left:4%;border-top:1px solid #000000;">
	<fmt:message key="chromosome.print.tip" />
</div>
<div
	style="float:left;width:92%;padding-top:10px;font-size:10px;text-align:center;margin-left:4%;border-top:1px solid #000000;">
	<fmt:message key="chromosome.annotation" />
</div>

<input type="hidden" id="hiddenDocId" value="${docId}" />
<input type="hidden" id="hiddenSampleNo" value="${sampleNo}" />
<input type="hidden" id="hasLast" value="${showLast}" />
</html>