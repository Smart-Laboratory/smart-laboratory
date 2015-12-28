
<%@ include file="/common/taglibs.jsp" %>
 
<head>
    <title><fmt:message key="invalidSamplesDetail.title"/></title>
    <meta name="heading" content="<fmt:message key='invalidSamplesDetail.heading'/>"/>
    <meta name="menu" content="Quality" />
    <link rel="stylesheet" type="text/css"  href="<c:url value='../styles/jquery-ui.min.css'/>" />
    <script type="text/javascript" src="../scripts/jquery-ui.min.js"></script>
    
    <script type="text/javascript">
    
    $(function() {
    	if("${msg}" != ""){
    		alert("${msg}");
    	}
    	else if("${msg}" == 1){
    		alert("<fmt:message key="require.sampleId"/>");
    	}
    	$("#addInvalidSample").click(function(){
    		var id = $("#sampleSearch").val();
    		location.href="../quality/invalidSampleForm?id="+id;
    	});
    	
    	$("#rejectTime","sampleInfo").datepicker();
	});
    
    </script>
</head>

<div class="col-sm-10">
<h1><fmt:message key="invalidSamplesDetail.heading"/></h1>
<form:form commandName="invalidSample" method="post" action="invalidSampleForm" name="invalidSampleForm">
<form:errors path="*" cssClass="error" element="div"/>
<form:hidden path="id"/>
<form:hidden path="rejectTime"/>
<form:hidden path="rejectPerson"/>
<form:hidden path="sample.id"/>

<div class="col-sm-12" style="float:left">
<div class="form-inline" style="margin-bottom: 50px;">
	<label for="sampleSearch"><fmt:message key="invalidSamplesList.search"/></label>
	<input id="sampleSearch" type="text" class="form-control" placeholder="please enter id"/>
	<button type="button" id="addInvalidSample" class="btn btn-primary"><fmt:message key="button.add"/></button>
</div>
<table id="sampleInfo" class="table table-striped table-hover" >
	<tr>
		<td><appfuse:label key="sample.id"/> :
			<form:input path="sampleId" cssclass="text form-control input-group-sm" /></td>
		<td><appfuse:label key="patient.name"/>  :
			<form:input path="patientName" cssclass="text form-control input-group-sm" />
			</td>
		<td><appfuse:label key="patient.sex"/> :
			<form:input path="sex" cssclass="text form-control input-group-sm" /></td>
		<td><appfuse:label key="patient.age"/> : 
			<form:input path="age" cssclass="text form-control input-group-sm" /></td>
		<td><appfuse:label key="sample.sampleType"/> : 
			<form:input path="sampleType" cssclass="text form-control input-group-sm" /></td>
	</tr>
	<tr>
		<td><appfuse:label key="sample.hosSection"/> :
			<span class="pText"> <c:out value="${invalidSample.sample.hosSection}"/></span>  </td>
		<td><appfuse:label key="sample.sampleNo"/> :
			<span class="pText"> <c:out value="${invalidSample.sample.sampleNo}"/></span>   </td>
		<td><appfuse:label key="sample.inspectionName"/> :
			<span class="pText"> <c:out value="${invalidSample.sample.inspectionName}"/></span>   </td>
		<td><appfuse:label key="sample.description"/> :
			<span class="pText"> <c:out value="${invalidSample.sample.description}"/></span>   </td>
		<td><appfuse:label key="invalidSamples.rejectPerson"/> :
			<span class="pText"> <c:out value="${invalidSample.rejectPerson}"/></span>   </td>
	</tr>
	<tr>
		<td><appfuse:label key="invalidSamples.rejectTime"/> :
			<form:input path="rejectTime"  id="rejectTime" type="text" />   </td>
		<td></td><td></td><td></td><td></td>
	</tr> 

</table>
</div>

<div class="col-sm-12">
<table  class="table">
	<tr height="60">
    	<td><appfuse:label key="invalidSamples.rejectSampleReason"/>
    	<form:select path="rejectSampleReason" id="rejectReasonSel">
    		<c:forEach var="s" items="${rejectReason}">
    			<form:option value="${s.key}"><c:out value="${s.value}"/></form:option>
    		</c:forEach>
    		
		</form:select></td>
    	<td><appfuse:label key="invalidSamples.measureTaken"/>
    	<form:select path="measureTaken">
  			<form:option value="1"><fmt:message key="measureTaken.1"/></form:option>
  			<form:option value="2"><fmt:message key="measureTaken.2"/></form:option>
  			<form:option value="3"><fmt:message key="measureTaken.3"/></form:option>
  			<form:option value="4"><fmt:message key="measureTaken.4"/></form:option>
  			<form:option value="5"><fmt:message key="measureTaken.5"/></form:option>
		</form:select></td>
    </tr>    
	<tr>
		<td><fieldset>
			<appfuse:label key="invalidSamples.requestionType"/>
            <%-- <legend><fmt:message key="invalidSamples.requestionType"/></legend> --%>
            <form:radiobutton path="requestionType" value="1" checked="true"/><fmt:message key="requestionType.1"/>
            <form:radiobutton path="requestionType" value="2"/><fmt:message key="requestionType.2"/>
            <form:radiobutton path="requestionType" value="3"/><fmt:message key="requestionType.3"/>	
        </fieldset></td>
        <td><fieldset>
        	<appfuse:label key="invalidSamples.labelType"/>
            <%-- <legend><fmt:message key="invalidSamples.labelType"/></legend> --%>
            <form:radiobutton path="labelType" value="1" checked="true"/><fmt:message key="labelType.1"/>
            <form:radiobutton path="labelType" value="2"/><fmt:message key="labelType.2"/>
            <form:radiobutton path="labelType" value="3"/><fmt:message key="labelType.3"/>
            <form:radiobutton path="labelType" value="4"/><fmt:message key="labelType.4"/>
        </fieldset></td>
        <td><fieldset>
        	<appfuse:label key="invalidSamples.containerType"/>
            <%-- <legend><fmt:message key="invalidSamples.containerType"/></legend> --%>
	        <form:radiobutton path="containerType" value="1" checked="true"/><fmt:message key='containerType.1'/>
		    <form:radiobutton path="containerType" value="2"/><fmt:message key="containerType.2"/>
        </fieldset></td>
    </tr>
	<tr>
		<td>
			<appfuse:label styleClass="desc2" key="invalidSamples.notes"/>
	        <form:errors path="notes" cssClass="fieldError"/>
	        <form:textarea path="notes" id="notes"  rows="4" cols="40" cssStyle="overflow:auto"/>
		</td>
	</tr>
</table>
<div>
        <input type="button" class="btn btn-info" name="save" value="<fmt:message key="button.save"/>" onclick="submit()"/>
        <input type="button" class="btn btn-info" name="cancel" value="<fmt:message key="button.cancel"/>" onclick="javascript:history.go(-1)"/>
</div>
</div>
</form:form>
</div>
