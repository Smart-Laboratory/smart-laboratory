<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="set.rule"/></title>
    <meta name="menu" content="SampleSet"/>
    <script type="text/javascript">
    function validateIndex(form){
    	var id = form["indexId"];
    	var val = id.value;
    	if(val==""||!val){
    		alert("indexid can't be null!");
    		return false;
    	}
    	var name = form["name"];
    	var nameval = name.value;
    	if(nameval==""||!nameval){
    		alert("index Name can't be null!");
    		return false;
    	}
    	
    	return true;
    }
    
    
    </script>
</head>

<div class="col-sm-7">
<h1><fmt:message key='index.edit'/></h1>
<spring:bind path="index.*">
    <c:if test="${not empty status.errorMessages}">
    <div class="error">    
        <c:forEach var="error" items="${status.errorMessages}">
            <img src="<c:url value="/images/iconWarning.gif"/>"
                alt="<fmt:message key="icon.warning"/>" class="icon" />
            <c:out value="${error}" escapeXml="false"/><br />
        </c:forEach>
    </div>
    </c:if>
</spring:bind>

<div class="separator"></div>

<form:form class="form-horizontal" commandName="index" method="post" action="edit" onsubmit="return validateIndex(this)" id="editIndexForm">
	<div class="control-groups col-sm-8">
        <appfuse:label styleClass="control-label" key="index.indexId"/>
        <div class="control">
	        <form:hidden path="id"/>
	        <form:hidden path="english"/>
	        <form:hidden path="enum_data"/>
	        <form:hidden path="importance"/>
	        <form:hidden path="isprint"/>
	        <form:hidden path="knowledgename"/>
	        <form:hidden path="printord"/>
	        <form:input path="indexId" id="indexId" cssClass="text form-control input-group-sm"/>
        </div>
	</div>
    <div class="control-group col-sm-8">
        <appfuse:label styleClass="control-label" key="index.name"/>
        <div class="controls">
        	<form:input path="name" id="name" cssClass="text form-control input-group-sm" />
        </div>
    </div>
    <div class="control-group col-sm-8">
        <appfuse:label styleClass="control-label" key="index.sampleFrom"/>
        <div class="controls">
        	<form:select path="sampleFrom" id="sampleFrom" cssClass="selects form-control input-group-sm">
	        	<form:options items="${sampleList}" />
	        </form:select>
        </div>
    </div>
    <div class="control-group col-sm-8">
        <appfuse:label styleClass="control-label" key="index.algorithm"/>
        <div class="controls">
			<form:select path="diffAlgo" id="diffAlgo" cssClass="selects form-control input-group-sm">
				<form:options items="${algorithmList}" />
	        </form:select>
        </div>
    </div>
    <div class="control-group col-sm-8">
        <appfuse:label styleClass="control-label" key="index.unit"/>
        <div class="controls">
        	<form:input path="unit" id="unit" cssClass="text form-control input-group-sm" />
        </div>
    </div>
    <div class="control-group col-sm-8">
        <appfuse:label styleClass="control-label" key="index.type"/>
        <div class="controls">
        	<form:select path="type" id="type" cssClass="selects form-control input-group-sm">
	        	<form:options items="${typeList}"  />
	        </form:select>
        </div>
    </div>
    <div class="control-group col-sm-8">
        <appfuse:label styleClass="control-label" key="index.history"/>
        <div class="controls">
        	<form:select path="needhistory" id="needhistory" cssClass="selects form-control input-group-sm">
	        	<form:options items="${ishistory}"  />
	        </form:select>
        </div>
    </div>
    <div class="control-group col-sm-8">
        <appfuse:label styleClass="control-label" key="index.method"/>
        <div class="controls">
        	<form:input path="method" id="method" cssClass="text form-control input-group-sm" />
        </div>
    </div>
	<div class="control-group col-sm-8">
        <appfuse:label styleClass="control-label" key="index.guide"/>
        <div class="controls">
        	<form:textarea path="guide" id="guide" cols="20" rows="3" cssClass="text form-control input-group-sm"/>
        </div>
    </div>

    <div class="control-group col-sm-8">
    	<label class="control-label" ></label>
 		<div class="controls">
        	<input type="submit" class="btn btn-success" style="width:80px;" name="save"  value="<fmt:message key="button.save"/>"/>
        	<input type="button" class="btn btn-info" style="width:80px;" name="cancel" onclick="location.href='<c:url value="/index/list"/>'" value="<fmt:message key="button.cancel"/>"/>
   		</div>
    </div>
</form:form>
</div>