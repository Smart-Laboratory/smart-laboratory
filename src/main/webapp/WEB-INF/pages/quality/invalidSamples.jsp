<%@ include file="/common/taglibs.jsp"%>

<head>
<title><fmt:message key="invalidSamplesList.title" /></title>
<meta name="heading"
	content="<fmt:message key='invalidSamplesList.heading'/>" />
<meta name="menu" content="Quality" />
<script type="text/javascript">
function searchSample(){
	var text = $("#sampleSearch").val();
	location.href="../quality/invalidSamples?text="+text;
}
$(function(){
	$("#addInvalidSample").click(function(){
		var id = $("#sampleSearch").val();
		window.location.href="../quality/invalidSampleForm?id="+id;
	});
	
})

</script>
</head>
<style>

</style>

<body>

<div class="col-sm-10">
<h1><fmt:message key='invalidSamples.title'/></h1>

<div class="col-sm-12 form-inline" style="margin-bottom:20px; width:auto;">
	<div class="col-sm-2" style="margin-top:10px;"><b><fmt:message key="invalidSamplesList.search"/>:</b></div>
	<div class="col-sm-6"><input id="sampleSearch" type="text" class="form-control" placeholder="please enter id"/></div>
	<button type="button" class="btn btn-info" onclick="searchSample();"><fmt:message key="button.search"/></button>
	<button type="button" id="addInvalidSample" class="btn btn-primary"><fmt:message key="button.add"/></button>
</div>

<display:table name="invalidSamples" cellspacing="0" cellpadding="0" requestURI="" 
				defaultsort="1" id="invalidSamples"  class="table table-condensed table-striped table-hover"  pagesize="25" >
	<display:column property="sampleId" sortable="true"
		href="/quality/invalidSampleView" media="html" paramId="id" style="width:10%"
		paramProperty="id" titleKey="sample.id" />
	<display:column property="sampleType" sortable="true" style="width:9%"
		titleKey="sample.sampleType" />
	<%-- <display:column property="sample.hosSection" sortable="true" style="width:5%"
		titleKey="sample.hosSection" /> 
	<display:column property="sample.section.name" sortable="true" style="width:9%"
		titleKey="sample.section" />  --%>
	<display:column  sortable="true" style="width:10%"
		titleKey="invalidSamples.containerType" >
		<c:choose>
			<c:when test="${invalidSamples.containerType==2}"><fmt:message key="containerType.2"/></c:when>
			<c:otherwise><fmt:message key="containerType.1"/></c:otherwise>
		</c:choose>		
	</display:column> 
	<display:column sortable="false" style="width:5%"
		titleKey="invalidSamples.labelType" >
		<c:choose>
			<c:when test="${invalidSamples.labelType==1}"><fmt:message key="labelType.1"/></c:when>
			<c:when test="${invalidSamples.labelType==2}"><fmt:message key="labelType.2"/></c:when>
			<c:when test="${invalidSamples.labelType==3}"><fmt:message key="labelType.3"/></c:when>
			<c:otherwise><fmt:message key="labelType.4"/></c:otherwise>
		</c:choose>	
	</display:column>
	<display:column  sortable="false" style="width:7%"
		titleKey="invalidSamples.requestionType" >
		<c:choose>
			<c:when test="${invalidSamples.requestionType==1}"><fmt:message key="requestionType.1"/></c:when>
			<c:when test="${invalidSamples.requestionType==2}"><fmt:message key="requestionType.2"/></c:when>
			<c:otherwise><fmt:message key="requestionType.3"/></c:otherwise>
		</c:choose>
	</display:column>
	<display:column property="rejectSampleReasonStr" sortable="true" style="width:11%"
		titleKey="invalidSamples.rejectSampleReason" />
	<display:column  sortable="true" style="width:11%"
		titleKey="invalidSamples.measureTaken" >
		<c:choose>
			<c:when test="${invalidSamples.measureTaken==1}"><fmt:message key="measureTaken.1"/></c:when>
			<c:when test="${invalidSamples.measureTaken==2}"><fmt:message key="measureTaken.2"/></c:when>
			<c:when test="${invalidSamples.measureTaken==3}"><fmt:message key="measureTaken.3"/></c:when>
			<c:when test="${invalidSamples.measureTaken==4}"><fmt:message key="measureTaken.4"/></c:when>
			<c:otherwise><fmt:message key="measureTaken.5"/></c:otherwise>
		</c:choose>	
	</display:column>
	<display:column property="notes" sortable="true" style="width:5%"
		titleKey="invalidSamples.notes" />
	<display:column property="rejectPerson" sortable="true" style="width:7%"
		titleKey="invalidSamples.rejectPerson" />
	<display:column property="rejectTime" sortable="true" style="width:14%"
		titleKey="invalidSamples.rejectTime" />
</display:table>



</div>

</body>
