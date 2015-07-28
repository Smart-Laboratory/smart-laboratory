<%@ include file="/common/taglibs.jsp"%>

<head>
<title><fmt:message key="invalidSamplesList.title" /></title>
<meta name="heading"
	content="<fmt:message key='invalidSamplesList.heading'/>" />
<meta name="menu" content="InvalidSamplesMenu" />

</head>

<body>

<div class="col-sm-7">
<h1><fmt:message key='invalidSamples.title'/></h1>
	<div class="form-inline" style="margin-bottom: 50px;">
		<div>
			<label for="sampleSearch"><fmt:message key="invalidSamplesList.search"/></label>
			<input id="sampleSearch" type="text" class="form-control" placeholder="please enter id">
			<button type="button" class="btn btn-info"><fmt:message key="button.search"/></button>
			<button type="button" class="btn btn-primary"><fmt:message key="button.add"/></button>
		</div>
	</div>
	
<display:table name="invalidSamples" cellspacing="0" cellpadding="0" requestURI="/quality/invalidSamples" partialList="true"
id="invalidSamples" defaultsort="5" defaultorder="ascending" sort="external"  class="table"  pagesize="25">
	<display:column property="sample.id" sortable="true"
		href="invalidSamplesform" media="html" paramId="id"
		paramProperty="sample.id" titleKey="invalidSamples.id" />
	<display:column property="sample.sampleType" sortable="true"
		titleKey="sample.sampleType" />
	<display:column property="sample.section" sortable="true"
		titleKey="sample.section" />
	<display:column property="sample.labDepartMent" sortable="true"
		titleKey="sample.labDepartment" />
	<display:column property="rejectTime" sortable="true"
		titleKey="invalidSamples.rejectTime" />
	<display:column name="containerType" sortable="true"
		titleKey="invalidSamples.containerType" >
		<c:choose>
			<c:when test="${containerType==2}"><fmt:message key="containerType.2"/></c:when>
			<c:otherwise><fmt:message key="containerType.1"/></c:otherwise>
		</c:choose>
		
	</display:column>
	<display:column property="labelTypeString" sortable="true"
		titleKey="invalidSamples.labelType" />
	<display:column property="requestionTypeString" sortable="true"
		titleKey="invalidSamples.requestionType" />
	<display:column property="rejectSampleReasonString" sortable="true"
		titleKey="invalidSamples.rejectSampleReason" />
	<display:column property="measureTakenString" sortable="true"
		titleKey="invalidSamples.measureTaken" />
	<display:column property="notes" sortable="true"
		titleKey="invalidSamples.notes" />
	<display:column property="rejectPerson" sortable="true"
		titleKey="invalidSamples.rejectPerson" />



	<display:setProperty name="paging.banner.item_name">
		<fmt:message key="invalidSamplesList.invalidSample" />
	</display:setProperty>

	<display:setProperty name="export.excel.filename">invalidSample.xls</display:setProperty>
	<display:setProperty name="export.csv.filename">invalidSample.csv</display:setProperty>
	<display:setProperty name="export.pdf.filename">invalidSample.pdf</display:setProperty>
	
</display:table>



</div>

</body>
