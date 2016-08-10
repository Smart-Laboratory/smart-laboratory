<%@ include file="/common/taglibs.jsp" %>

<head>
    <title><fmt:message key="sample.manage.audit"/></title>
    
    <link rel="stylesheet" type="text/css"  href="<c:url value='/styles/jquery-ui.min.css'/>" />
    <link rel="stylesheet" type="text/css"  href="<c:url value='/styles/ui.jqgrid.css'/>" />
	<link rel="stylesheet" type="text/css"  href="<c:url value='/styles/ruleLib.css'/>" />
	<link rel="stylesheet" type="text/css"  href="<c:url value='../styles/bootstrap.min.css'/>" />
    
    <script type="text/javascript" src="../scripts/jquery-2.1.4.min.js"></script>
	<script type="text/javascript" src="<c:url value='/scripts/jquery.tablednd_0_5.js'/> "></script>
    <script type="text/javascript" src="../scripts/jquery-ui.min.js"></script>
    <script type="text/javascript" src="../scripts/i18n/grid.locale-cn.js"></script>
    <script type="text/javascript" src="../scripts/jquery.jqGrid.js"></script>
    <script type="text/javascript" src="../scripts/jquery.form.js"></script>
    
    <script type="text/javascript" src="../scripts/manage/patientList.js"></script>
<style>
.ui-jqgrid-title{
	font-size:16px;
	color:#000;
}
</style>



</head>
<body>

<div class="form-inline">
	<label for="from" style="margin-left : 20px;"><b><fmt:message key="from" /></b></label>
	<input type="text" id="from" name="from" class="form-control" />
	<label for="to" style="margin-left : 10px;" ><b><fmt:message key="to" /></b></label>
	<input type="text" id="to" name="to" class="form-control">
	<label for="search_text" style="margin-left : 50px;"></label>
	<input type="text" id="search_text" name="search_text" class="form-control" />
	<select id="search_select" class="form-control select" >
		<option value="1"><fmt:message key="patient.blh" /></option>
		<option value="2"><fmt:message key="patient.name" /></option>
		<option value="3"><fmt:message key="sample.id"></fmt:message></option>
	</select>
	<button id="searchBtn" class="btn btn-info" style="margin-left:20px;"><fmt:message key="search" /></button>
	<button id="search_detailed_printBtn" class="btn btn-info" style="margin-left:20px;"><fmt:message key="print" /></button>
</div>


<div class="col-sm-12" style="margin-top: 10px;">
	<div class="col-sm-3" id="leftContent" style="float: left; ">
		<div id="sampleListPanel">
			<table id="list"></table>
			<div id="pager"></div>
		</div>
	</div>
	<div id="midContent" class="col-sm-7"
		style="float: left; display: none;">
		<div class="clearfix">
			<h2 style="display:none;" id="sampleTitle"></h2>
			<div id="patient-info" class="alert alert-info" style="width:630px;margin-bottom:2px;padding:0px;padding-left:10px;padding-bottom:4px;">
				<div class="pItem">
					<span class="pLabel"><fmt:message key="patient.name" /></span>
					<span class="pText"><b id="pName"></b></span>
					<span class="pLabel"><fmt:message key="patient.sex" /></span>
					<span class="pText"><b id="pSex"></b></span>
					<span class="pLabel"><fmt:message key="patient.age" /></span>
					<span class="pText"><b id="pAge"></b></span>
					<span class="pLabel"><fmt:message key="patient.sampleType" /></span>
					<span class="pText"><b id="pType"></b></span>
				</div>
				
				<div class="pItem">
					<span class="pLabel"><fmt:message key="patient.blh" /></span>
					<span class="pText"><b id="blh"></b></span>
					<span class="pLabel"><fmt:message key="patient.patientId" /></span>
					<span class="pText"><b id="pId"></b></span>
				</div>

				<div class="pItem">
					<span class="pLabel"><fmt:message key="patient.section"/>&nbsp;</span>
					<span class="pText"><b id="pSection"></b></span>
					<span id="pBedLabel" class="pLabel"><fmt:message key="patient.departbed"/>&nbsp;</span>
					<span id="pBedText" class="pText"><b id="pBed"></b></span>
					<span class="pLabel"><fmt:message key="diagnostic"/>&nbsp;</span>
					<span class="pText"><b id="diagnostic"></b></span>
				</div>
			</div>
		</div>
		<div class="col-sm-11" style="margin-top:10px;">
		<div id="patientRow" style="font-size: 13px;">
			<table id="rowed3"></table>
		</div>
		</div>
		<div style="font-size: 13px; display:none;margin-top: 10px;">
			<div style="margin-left:60px;">
				<input type="hidden" id="hiddenDocId"/>
				<input type="hidden" id="hiddenSampleNo"/>
				<input type="hidden" id="hisLastResult"/>
			</div>
		</div>
	</div>
	<!-- 2016-5-19  张晋南 查询详细信息打印 -->
		<div id="auditPrint" align="left"
			title='<fmt:message key="audit.preview" />'>
			<button class="btn btn-success"
				onclick="document.getElementById('iframe_print').contentWindow.print();">
				<fmt:message key="audit.print" />
			</button>
			<div id="printFrame"></div>
			<button class="btn btn-success"
				onclick="document.getElementById('iframe_print').contentWindow.print();">
				<fmt:message key="audit.print" />
			</button>
		</div>
		<%-- <div id="rightContent" class="col-sm-2" style="position:absolute;right:0px;">
		<div id="historyTabs" style="display:none;">
			<ul>
				<li><a href="#tabs-0"><fmt:message key="addResult.result"/></a></li>
				<li><a href="#tabs-1"><fmt:message key="result.history"/></a></li>
			</ul>
			<div id="tabs-0" style="padding:5px;">
				<div style="margin: 10px;">
					<span class="label label-info"><fmt:message key="audit.infomation" /></span>
				</div>
				<div style="height:465px;overflow-y:auto;">
					<div id="explainRow" style="margin-top: 4px; font-size: 13px;">
						<table id="audit_information"></table>
					</div>
				</div>
			</div>
			<div id="tabs-1" style="padding:5px;">
				<div style="margin: 10px;">
					<span class="label label-info"><fmt:message key="show.three.history" /></span>
				</div>
			</div>
		</div>
	</div> --%>
</div>

</body>