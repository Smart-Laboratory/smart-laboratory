<%@ include file="/common/taglibs.jsp"%>

<head>
<title><fmt:message key="menu.sample.in"/></title>

<script type="text/javascript" src="<c:url value='/scripts/grid.locale-cn.js'/> "></script>
<link rel="stylesheet" type="text/css"  href="<c:url value='/styles/ruleLib.css'/>" />


<style></style>

<script type="text/javascript">

$(function() {
	
	$( "#from" ).datepicker({
		changeMonth: true,
		dateFormat:"yy-mm-dd",
		monthNamesShort: ['1\u6708','2\u6708','3\u6708','4\u6708','5\u6708','6\u6708','7\u6708','8\u6708','9\u6708','10\u6708','11\u6708','12\u6708'],
		dayNamesMin: ['\u65e5','\u4e00','\u4e8c','\u4e09','\u56db','\u4e94','\u516d'],
		onClose: function( selectedDate ) {
			$( "#to" ).datepicker( "option", "minDate", selectedDate );
		}
    });
	
	$( "#to" ).datepicker({
		changeMonth: true,
		dateFormat:"yy-mm-dd",
		monthNamesShort: ['1\u6708','2\u6708','3\u6708','4\u6708','5\u6708','6\u6708','7\u6708','8\u6708','9\u6708','10\u6708','11\u6708','12\u6708'],
		dayNamesMin: ['\u65e5','\u4e00','\u4e8c','\u4e09','\u56db','\u4e94','\u516d'],
		onClose: function( selectedDate ) {
			$( "#from" ).datepicker( "option", "maxDate", selectedDate );
		}
	});
	
	$("#searchBtn").click(function() {
		
		var from = $("#from").val();
		var to = $("#to").val();
		var pName = $("#patientName").val();
		var doct = $("#doctadviseno").val();
		var patientId = $("#patientId").val();
		var sampleno = $("#sampleno").val();

		window.location.href="<c:url value='/sample/list'/>?from="+from+"&to="+to+"&pName="+pName+"&doct="+doct+"&patientId="+patientId + "&sampleno=" + sampleno;
	});
	
	$("#sampleno").val("<c:out value='${sampleno}'/>");
	$("#from").val("<c:out value='${today}'/>");
	$("#to").val("<c:out value='${today}'/>");
});

</script>
</head>

<div class="form-inline">
	<label for="from" style="margin-left: 20px;"><b><fmt:message key="patient.searchfrom" /></b></label>
	<input type="text" id="from" name="from" class="span2"/>
	<label for="to" style="margin-left: 10px;"><b><fmt:message key="patient.searchto" /></b></label>
	<input type="text" id="to" name="to" class="span2" style="margin-left: 10px;"/>
	
	<label for="patientName" style="margin-left: 80px;"><b><fmt:message key="patientInfo.patientName" /></b></label>
	<input type="text" id="patientName" name="patientName" class="span2"/>
	
	<label for="patientId" style="margin-left: 80px;"><b><fmt:message key="patient.blh" /></b></label>
	<input type="text" id="patientId" name="patientId" class="span2"/>
</div>
<div class="form-inline" style="margin-top:10px;">	
	<label for="sampleno" style="margin-left: 20px;"><b><fmt:message key="patients.sampleNo" /></b></label>
	<input type="text" id="sampleno" name="sampleno" class="span2"/>
	
	<label for="doctadviseno" style="margin-left: 270px;"><b><fmt:message key="sample.id" /></b></label>
	<input type="text" id="doctadviseno" name="doctadviseno" class="span2"/>
	
	<button id="searchBtn" class="btn btn-info" style="margin-left: 160px;"><fmt:message key='search'/></button>
</div>

<display:table name="list" class="table" requestURI=""
	id="inlist" export="true" pagesize="30" style="margin-top:10px;width:200%;overflow:scroll;">
	<display:column property="DOCTADVISENO" media="csv excel xml pdf"/>
	<display:column property="DOCTADVISENO" sortable="true" titleKey="sample.id"/>
	<display:column property="SAMPLENO" sortable="true" titleKey="patients.sampleNo"/>
	<display:column property="PATIENTID" sortable="false" titleKey="patient.patientId"/>
	<display:column property="PATIENTNAME" sortable="true" style="width:40px;" titleKey="patient.name"/>
	<display:column property="sexValue" sortable="false" titleKey="patients.sex"/>
	<display:column property="age" sortable="false" titleKey="patients.age"/>
	<display:column property="SECTION" sortable="false" titleKey="hm.section"/>
	<display:column property="DIAGNOSTIC" sortable="false" titleKey="diagnostic"/>
	<display:column property="EXAMINAIM" sortable="false" titleKey="patients.examinaim"/>
	<display:column property="REQUESTER" sortable="false" titleKey="requester.name"/>
	<display:column property="requesttime" sortable="false" titleKey="tat.request"/>
	<display:column property="receivetime" sortable="false" titleKey="tat.receive"/>
	<display:column property="RESULTSTATUS" sortable="false" titleKey="audit.status"/>

	<display:setProperty name="export.excel.filename">samplein.xlsx</display:setProperty>
	<display:setProperty name="export.csv.filename">samplein.csv</display:setProperty>
	<display:setProperty name="export.pdf.filename">samplein.pdf</display:setProperty>
</display:table>

