
<%@ include file="/common/taglibs.jsp" %>
 
<head>
    <title><fmt:message key="invalidSamplesDetail.title"/></title>
    <meta name="heading" content="<fmt:message key='invalidSamplesDetail.heading'/>"/>
    <meta name="menu" content="Quality" />
    <link rel="stylesheet" type="text/css"  href="<c:url value='../styles/jquery-ui.min.css'/>" />
    <link rel="stylesheet" type="text/css"  href="<c:url value='../styles/bootstrap.min.css'/>" />
    <script type="text/javascript" src="../scripts/jquery-ui.min.js"></script>
 <style>

</style>   
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
<div class="row">
<div class="col-sm-10">
<h1><fmt:message key="invalidSamplesDetail.heading"/></h1>
<form:form commandName="invalidSample" method="post" action="invalidSampleForm" name="invalidSampleForm">
<form:errors path="*" cssClass="error" element="div"/>
<form:hidden path="id"/>
<form:hidden path="rejectTime"/>
<form:hidden path="rejectPerson"/>
<form:hidden path="sampleId"/>

<div class="col-sm-12" style="float:left">
	<div class="form-inline" style="margin-bottom:10px;">
		<label for="sampleSearch"><fmt:message key="invalidSamplesList.search"/></label>
		<input id="sampleSearch" type="text" class="form-control" placeholder="please enter id"/>
		<button type="button" id="addInvalidSample" class="btn btn-primary"><fmt:message key="button.add"/></button>
	</div>
	<div id="sampleInfo" class="clearfix alert alert-info " style="margin-bottom:10px;">
		<div class="col-sm-12" style="margin-bottom:10px;">
			<div class="col-sm-3 form-inline">
				<div style="margin-top:10px;float:left;width:30%"><b><fmt:message key="sample.id"/>:</b></div>
				<form:input path="sampleId" class="form-control" style="float:left;width:70%" />
			</div>
			<div class="col-sm-3 form-inline">
				<div style="margin-top:10px;float:left;width:30%"><b><fmt:message key="patient.name"/>:</b></div>
				<form:input path="patientName" class="form-control" style="float:left;width:70%" />
			</div>
			<div class="col-sm-3 form-inline">
				<div style="margin-top:10px;float:left;width:30%"><b><fmt:message key="patient.sex"/>:</b></div>
				<form:input path="sex" class="form-control" style="float:left;width:70%" />
			</div>
			<div class="col-sm-3 form-inline">
				<div style="margin-top:10px;float:left;width:30%"><b><fmt:message key="patient.age"/>:</b></div>
				<form:input path="age" class="form-control" style="float:left;width:70%" />
			</div>
			
		</div>
		<div class="col-sm-12">
			<div class="col-sm-3 form-inline">
				<div style="margin-top:10px;float:left;width:40%"><b><fmt:message key="patient.sampleType"/>:</b></div>
				<form:input path="sampleType" class="form-control" style="float:left;width:60%" />
			</div>
			<div class="col-sm-3">
				<div style="margin-top:10px;float:left;width:30%"><b><fmt:message key="sample.hosSection"/>:</b></div>
				<span class="pText" style="margin-top:10px;float:left;width:70%"> <c:out value="${hosSection}"/></span>
			</div>
			<div class="col-sm-3">
				<div style="margin-top:10px;float:left;width:40%"><b><fmt:message key="sample.sampleNo"/>:</b></div>
				<span class="pText" style="margin-top:10px;float:left;width:60%"> <c:out value="${sampleNo}"/></span>
			</div>
			<div class="col-sm-3">
				<div style="margin-top:10px;float:left;width:30%"><b><fmt:message key="sample.description"/>:</b></div>
				<span class="pText" style="margin-top:10px;float:left;width:70%"> <c:out value="${description}"/></span>
			</div>
		</div>
		<div class="col-sm-12">
			<div style="margin-top:10px;float:left;width:10%" class="col-sm-2"><b><fmt:message key="sample.inspectionName"/>:</b></div>
			<span class="pText" style="margin-top:10px;float:left;width:90%"class="col-sm-10"> <c:out value="${inspectionName}"/></span>
		</div>
		<div class="col-sm-12">
			
			<div class="col-sm-3">
				<div style="margin-top:10px;float:left;width:30%"><b><fmt:message key="invalidSamples.rejectPerson"/>:</b></div>
				<span class="pText" style="margin-top:10px;float:left;width:70%"> <c:out value="${rejectPerson}"/></span>
			</div>
			<div class="col-sm-9">
				<div style="margin-top:10px;float:left;width:15%"><b><fmt:message key="invalidSamples.rejectTime"/>:</b></div>
				<span class="pText" style="margin-top:10px;float:left;width:85%"> <c:out value="${rejectTime}"/></span>
			</div>
		</div>
	</div>
</div>
<div class="col-sm-12" style="border-top:1px solid #000000;background:white;">
	<div class="col-sm-6" style="margin-top:10px;">
		<div style="margin-top:10px;float:left;width:20%"><b><fmt:message key="invalidSamples.rejectSampleReason"/>:</b></div>	
		<form:select path="rejectSampleReason" id="rejectReasonSel" class="form-control" style="float:left;width:60%">
    		<c:forEach var="s" items="${rejectReason}">
    			<form:option value="${s.key}"><c:out value="${s.value}"/></form:option>
    		</c:forEach>
		</form:select>
	</div>
	<div class="col-sm-6" style="margin-top:10px;">
		<div style="margin-top:10px;float:left;width:20%"><b><fmt:message key="invalidSamples.rejectSampleReason"/>:</b></div>
		<form:select path="measureTaken" class="form-control" style="float:left;width:60%">
  			<form:option value="1"><fmt:message key="measureTaken.1"/></form:option>
  			<form:option value="2"><fmt:message key="measureTaken.2"/></form:option>
  			<form:option value="3"><fmt:message key="measureTaken.3"/></form:option>
  			<form:option value="4"><fmt:message key="measureTaken.4"/></form:option>
  			<form:option value="5"><fmt:message key="measureTaken.5"/></form:option>
		</form:select>
	</div>
	<div class="col-sm-4" style="margin-top:10px;">
		<fieldset>
            <legend style="margin-bottom:10px;"><fmt:message key="invalidSamples.requestionType"/></legend>
            <form:radiobutton path="requestionType" value="1" checked="true"/><fmt:message key="requestionType.1"/>
            <form:radiobutton path="requestionType" value="2"/><fmt:message key="requestionType.2"/>
            <form:radiobutton path="requestionType" value="3"/><fmt:message key="requestionType.3"/>	
        </fieldset>
	</div>
	<div class="col-sm-4" style="margin-top:10px;">
		<fieldset>
            <legend style="margin-bottom:10px;"><fmt:message key="invalidSamples.labelType"/></legend>
            <form:radiobutton path="labelType" value="1" checked="true"/><fmt:message key="labelType.1"/>
            <form:radiobutton path="labelType" value="2"/><fmt:message key="labelType.2"/>
            <form:radiobutton path="labelType" value="3"/><fmt:message key="labelType.3"/>
            <form:radiobutton path="labelType" value="4"/><fmt:message key="labelType.4"/>
        </fieldset>
	</div>
	<div class="col-sm-4" style="margin-top:10px;">
		<fieldset>
            <legend style="margin-bottom:10px;"><fmt:message key="invalidSamples.containerType"/></legend>
	        <form:radiobutton path="containerType" value="1" checked="true"/><fmt:message key='containerType.1'/>
		    <form:radiobutton path="containerType" value="2"/><fmt:message key="containerType.2"/>
        </fieldset>
	</div>
	<div class="col-sm-12" style="margin-top:10px;">
		<fieldset>
			<legend style="margin-bottom:10px;"><fmt:message key="invalidSamples.notes"/></legend>
			<form:errors path="notes" cssClass="fieldError"/>
	   		<form:textarea path="notes" id="notes" rows="4" cols="40" cssStyle="overflow:auto" class="form-control"/>
		</fieldset>
	</div>
	<div class="col-sm-12"style="margin-top:10px;">
		<input type="button" class="btn btn-info" name="save" value="<fmt:message key="button.save"/>" onclick="submit()"/>
		<input type="button" class="btn btn-info" name="cancel" value="<fmt:message key="button.cancel"/>" onclick="history.go(-1)"/>
	</div>
</div>
</form:form>
</div>
</div>
