<%@ include file="/common/taglibs.jsp" %>
 
<head>
    <title><fmt:message key="invalidSamplesDetail.title"/></title>
    <meta name="heading" content="<fmt:message key='invalidSamplesDetail.heading'/>"/>
    <meta name="menu" content="Quality" />
    <style>
    .sinfo{
    	width:100px;
    	text-align:right;
    }
    .sinfo2{
    	width:120px;
    	text-align:right;
    }
    </style>
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
		<th><appfuse:label key="patient.name"/>  :</th>
		<td><span class="pText"> <c:out value="${invalidSample.patientName}"/></span>   </td>
		<th><appfuse:label key="patient.sex"/> :</th>
		<td><span class="pText"> <c:out value="${invalidSample.sexValue}"/></span>   </td>
		<th><appfuse:label key="patient.age"/> : </th>
		<td><span class="pText"> <c:out value="${invalidSample.age}"/></span>   </td>
	</tr>
	<tr>
		<th><appfuse:label key="sample.stayHospitalMode"/> : </th>
		<td><span class="pText"> <c:out value="${sample.stayHospitalModelValue}"/></span>   </td>
		<th><appfuse:label key="sample.hosSection"/> :</th>
		<td><span class="pText"> <c:out value="${sample.hosSection}"/></span>  </td>
		<th><appfuse:label key="sample.sampleNo"/> :</th>
		<td><span class="pText"> <c:out value="${sample.sampleNo}"/></span> </td>
		<th><appfuse:label key="sample.sampleType"/> : </th>
		<td><span class="pText"> <c:out value="${invalidSample.sampleType}"/></span>   </td>
	</tr>
	<tr>
		<th><appfuse:label key="sample.inspectionName"/> :</th>
		<td colspan="3"><span class="pText"> <c:out value="${sample.inspectionName}"/></span>   </td>
		<th><appfuse:label key="invalidSamples.rejectTime"/> :</th>
		<td><span class="pText"> <c:out value="${invalidSample.rejectTime}"/></span>   </td>
		<th><appfuse:label key="invalidSamples.rejectPerson"/> :</th>
		<td><span class="pText"> <c:out value="${invalidSample.rejectPerson}"/></span>   </td>
	</tr>
	<tr>
    	<th colspan="2"><appfuse:label key="invalidSamples.rejectSampleReason"/> : </th>
    	<td colspan="2"><span class="pText"> <c:out value="${invalidSample.rejectSampleReasonStr}"/></span>   </td>
    	<th colspan="2"><appfuse:label key="invalidSamples.measureTaken"/> : </th>
   		<td colspan="2">
	   		<c:choose>
				<c:when test="${invalidSample.measureTaken==1}"><fmt:message key="measureTaken.1"/></c:when>
				<c:when test="${invalidSample.measureTaken==2}"><fmt:message key="measureTaken.2"/></c:when>
				<c:when test="${invalidSample.measureTaken==3}"><fmt:message key="measureTaken.3"/></c:when>
				<c:when test="${invalidSample.measureTaken==4}"><fmt:message key="measureTaken.4"/></c:when>
				<c:otherwise><fmt:message key="measureTaken.5"/></c:otherwise>
			</c:choose>
		</td>
    </tr>    
	<tr>
		<th><appfuse:label key="invalidSamples.requestionType"/> : </th>
		<td>	
			<c:choose>
				<c:when test="${invalidSample.requestionType==1}"><fmt:message key="requestionType.1"/></c:when>
				<c:when test="${invalidSample.requestionType==2}"><fmt:message key="requestionType.2"/></c:when>
				<c:otherwise><fmt:message key="requestionType.3"/></c:otherwise>
			</c:choose>
        </td>
        <th><appfuse:label key="invalidSamples.labelType"/> : </th>
        <td>   
	        <c:choose>
				<c:when test="${invalidSample.labelType==1}"><fmt:message key="labelType.1"/></c:when>
				<c:when test="${invalidSample.labelType==2}"><fmt:message key="labelType.2"/></c:when>
				<c:when test="${invalidSample.labelType==3}"><fmt:message key="labelType.3"/></c:when>
				<c:otherwise><fmt:message key="labelType.4"/></c:otherwise>
			</c:choose>	
		</td>
        <th colspan="2"><appfuse:label key="invalidSamples.containerType"/> : </th>
        <td colspan="2">   
	        <c:choose>
				<c:when test="${invalidSample.containerType==2}"><fmt:message key="containerType.2"/></c:when>
				<c:otherwise><fmt:message key="containerType.1"/></c:otherwise>
			</c:choose>
		</td>
    </tr>
	<tr>
		<th><appfuse:label styleClass="desc2" key="invalidSamples.notes"/> : </th>
	    <td colspan="7">    
	    	<form:errors path="notes" cssClass="fieldError"/>
	        <form:textarea path="notes" id="notes"  rows="4" cols="40" cssStyle="overflow:auto"/>
		</td>
	</tr>
</table>
<div>
	<input type="button" id="editBtn" class="btn btn-info" name="edit" value="<fmt:message key="button.edit"/>" onclick="location.href='../quality/invalidSampleForm?id=<c:out value="${invalidSample.sampleId}" />'"/> 
	<input type="button" id="deleteBtn" class="btn btn-danger" name="delete" onclick="deleteConfirm()"
	    value="<fmt:message key="button.delete"/>" />
	<input type="button" class="btn btn-info" name="cancel" value="<fmt:message key="button.return"/>" onclick="history.go(-1)"/>
</div>
</div>
</form:form>
</div>
