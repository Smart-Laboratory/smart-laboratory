<%@ include file="/common/taglibs.jsp" %>
 
<head>
    <title><fmt:message key="invalidSamplesDetail.title"/></title>
    <meta name="heading" content="<fmt:message key='invalidSamplesDetail.heading'/>"/>
    <meta name="menu" content="Quality" />
    
    <script type="text/javascript">
    function deleteConfirm() {
    	if (confirm('<fmt:message key="confirm.delete" />')) {
    		location.href='../quality/invalidSample/delete?id=<c:out value="${invalidSample.id}" />';
    	}	
    }
    
    
    
    </script>
</head>
<div class="col-sm-10">
<h1><fmt:message key="invalidSamplesDetail.heading"/></h1>
<form:form commandName="invalidSample" method="post" action="invalidSampleForm" name="invalidSample">
<form:errors path="*" cssClass="error" element="div"/>
<div class="col-sm-12" style="float:left">
<table class="table table-striped table-hover" >
	<tr>
		<th><appfuse:label key="sample.id"/> :</th>
		<td><span class="pText"> <c:out value="${invalidSample.sampleId}"/></span>  </td>
		<th><appfuse:label key="patient.patientName"/>  :</th>
			<td><span class="pText"> <c:out value="${invalidSample.patientName}"/></span>   </td>
		<th><appfuse:label key="patient.sex"/> :</th>
			<td><span class="pText"> <c:out value="${invalidSample.sex}"/></span>   </td>
		<th><appfuse:label key="patient.age"/> : </th>
			<td><span class="pText"> <c:out value="${invalidSample.age}"/></span>   </td>
		<th><appfuse:label key="sample.stayHospitalMode"/> : </th>
			<td><span class="pText"> <c:out value="${invalidSample.sample.stayHospitalMode}"/></span>   </td>
	</tr>
	<tr>
		<th><appfuse:label key="sample.hosSection"/> :</th>
			<td><span class="pText"> <c:out value="${invalidSample.sample.hosSection}"/></span>  </td>
		<th><appfuse:label key="sample.sampleNo"/> :</th>
			<td><span class="pText"> <c:out value="${invalidSample.sample.sampleNo}"/></span> </td>
		<th><appfuse:label key="sample.inspectionName"/> :</th>
			<td><span class="pText"> <c:out value="${invalidSample.sample.inspectionName}"/></span>   </td>
		<th><appfuse:label key="sample.sampleType"/> : </th>
			<td><span class="pText"> <c:out value="${invalidSample.sampleType}"/></span>   </td>
		<th><appfuse:label key="invalidSamples.rejectPerson"/> :</th>
			<td><span class="pText"> <c:out value="${invalidSample.rejectPerson}"/></span>   </td>
	</tr>
	<tr>
		<th><appfuse:label key="invalidSamples.rejectTime"/> :</th>
			<td><span class="pText"> <c:out value="${invalidSample.rejectTime}"/></span>   </td>
		<td></td><td></td><td></td><td></td>
	</tr>

</table>
</div>

<div class="col-sm-12" style="float:left">
<table class="table table-striped table-hover">
	<tr >
    	<th><appfuse:label key="invalidSamples.rejectSampleReason"/> : </th>
    		<td><span class="pText"> <c:out value="${invalidSample.rejectSampleReasonStr}"/></span>   </td>
    	<th><appfuse:label key="invalidSamples.measureTaken"/> : </th>
    		<td><c:choose>
				<c:when test="${invalidSample.measureTaken==1}"><fmt:message key="measureTaken.1"/></c:when>
				<c:when test="${invalidSample.measureTaken==2}"><fmt:message key="measureTaken.2"/></c:when>
				<c:when test="${invalidSample.measureTaken==3}"><fmt:message key="measureTaken.3"/></c:when>
				<c:when test="${invalidSample.measureTaken==4}"><fmt:message key="measureTaken.4"/></c:when>
				<c:otherwise><fmt:message key="measureTaken.5"/></c:otherwise>
			</c:choose>
		</td>
    </tr>    
	<tr>
		<th>
			<appfuse:label key="invalidSamples.requestionType"/> : </th>
		<td>	<c:choose>
			<c:when test="${invalidSample.requestionType==1}"><fmt:message key="requestionType.1"/></c:when>
			<c:when test="${invalidSample.requestionType==2}"><fmt:message key="requestionType.2"/></c:when>
			<c:otherwise><fmt:message key="requestionType.3"/></c:otherwise>
			</c:choose>
            </td>
        <th>
        	<appfuse:label key="invalidSamples.labelType"/> : </th>
        <td>   <c:choose>
			<c:when test="${invalidSample.labelType==1}"><fmt:message key="labelType.1"/></c:when>
			<c:when test="${invalidSample.labelType==2}"><fmt:message key="labelType.2"/></c:when>
			<c:when test="${invalidSample.labelType==3}"><fmt:message key="labelType.3"/></c:when>
			<c:otherwise><fmt:message key="labelType.4"/></c:otherwise>
			</c:choose>	</td>
        <th>
        	<appfuse:label key="invalidSamples.containerType"/> : </th>
         <td>   <c:choose>
			<c:when test="${invalidSample.containerType==2}"><fmt:message key="containerType.2"/></c:when>
			<c:otherwise><fmt:message key="containerType.1"/></c:otherwise>
			</c:choose>	</td>
    </tr>
	<tr>
		<th>
			<appfuse:label styleClass="desc2" key="invalidSamples.notes"/> : </th>
	    <td>    <form:errors path="notes" cssClass="fieldError"/>
	        <form:textarea path="notes" id="notes"  rows="4" cols="40" cssStyle="overflow:auto"/>
		</td>
	</tr>
</table>
<div>
        <input type="button" id="editBtn" class="btn btn-info" name="edit" value="<fmt:message key="button.edit"/>" onclick="location.href='../quality/invalidSampleForm?id=<c:out value="${invalidSample.id}" />'"/> 
        <input type="button" id="deleteBtn" class="btn btn-danger" name="delete" onclick="javascipt:deleteConfirm()"
            value="<fmt:message key="button.delete"/>" />
        <!-- confirmDelete('\u6837\u672c'); -->
        <input type="button" class="btn btn-info" name="cancel" value="<fmt:message key="button.return"/>" onclick="javascript:history.go(-1)"/>
</div>
</div>
</form:form>
</div>