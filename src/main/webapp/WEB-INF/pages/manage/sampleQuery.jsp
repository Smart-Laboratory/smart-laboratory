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
    
    <script type="text/javascript" src="../scripts/manage/sampleQuery.js"></script>
<style>
.ui-jqgrid-title{
	font-size:16px;
	color:#000;
}
.ui-jqgrid-sortable{
	height:20px!important;
}
.date{
	width:15%!important;
}

</style>

</head>
<body>
<input id='lastlab' value="${lastlab }" type='hidden' />

<h2><fmt:message key="sample.sampleQuery" /></h2>
<div class="form-inline">
	<label for="search_text" style="margin-left : 20px;"><fmt:message key="sample.query" /></label>
	<input type="text" id="search_text" name="search_text" class="form-control" />
	
	<button id="searchBtn" class="btn btn-info form-control" style="margin-left:10px;"><fmt:message key="search" /></button>

	
	<label for="from" style="margin-left : 20px;"><b><fmt:message key="from" /></b></label>
	<input type="text" id="from" name="from" class="form-control date" />
	<label for="to" style="margin-left : 10px;" ><b><fmt:message key="to" /></b></label>
	<input type="text" id="to" name="to" class="form-control date">
	
	<label style="margin-left : 10px;"><fmt:message key="sample.stayHospitalMode" /></label>
	<select id="search_select" class="form-control select" >
		<option value="0"><fmt:message key="treatmentType.5" /></option>
		<option value="1"><fmt:message key="treatmentType.1" /></option>
		<option value="2"><fmt:message key="treatmentType.2" /></option>
		<option value="3"><fmt:message key="treatmentType.4"></fmt:message></option>
	</select>
</div>

<div class="form-inline" style="margin:10px 20px;">
		<label class="radio-inline">
  			<input type="radio" name="select_type" id="q_sampleno" value="1" checked>
  			<fmt:message key="sample.query.sampleno" />
		</label>
		<label class="radio-inline">
  			<input type="radio" name="select_type" id="q_id" value="2" >
  			<fmt:message key="sample.query.id" />
		</label>
		<label class="radio-inline">
  			<input type="radio" name="select_type" id="q_name" value="3" >
  			<fmt:message key="sample.query.name" />
		</label>
		<label class="radio-inline">
  			<input type="radio" name="select_type" id="q_blh" value="4"  >
  			<fmt:message key="sample.query.blh" />
		</label>
		

 		<label class="radio-inline">
  			<input type="radio" name="select_type" id="q_patientid" value="5"  >
  			<fmt:message key="sample.query.patientid" />
		</label>
 		
		
		<label style="margin-left : 10px;"><fmt:message key="sample.section" /></label>
		<select id="labSelect"  class="form-control" style="">
			<span ><c:forEach var="depart" items="${departList}">
			<option value='<c:out value="${depart.key}" />'><c:out value="${depart.value}" /></option>
			</c:forEach></span>
		</select>
		<label style="margin-left : 0px;"><fmt:message key="sample.sampleType" /></label>
		<select id="sampleTypeSelect"  class="form-control" style="width:100px;">
			<span ><c:forEach var="sType" items="${sampleTypes}">
			<option value='<c:out value="${sType.key}" />'><c:out value="${sType.value}" /></option>
			</c:forEach></span>
		</select>
</div>

<div id="sampleListPanel" class="col-sm-12">
			<table id="list"></table>
			<div id="pager"></div>
</div>
<div style="font-size: 13px; display:none;margin-top: 10px;">
	<div style="margin-left:60px;">
		<input type="hidden" id="hiddenDocId"/>
		<input type="hidden" id="hiddenSampleNo"/>
		<input type="hidden" id="hisLastResult"/>
	</div>
</div>


</body>