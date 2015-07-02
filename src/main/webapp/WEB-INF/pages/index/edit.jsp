<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="set.rule"/></title>
    <meta name="menu" content="SampleSet"/>
    <script type="text/javascript">
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

<form:form class="form-horizontal" commandName="index" method="post" action="edit" onsubmit="return true" id="editIndexForm">
	<div class="control-groups col-sm-8">
        <appfuse:label styleClass="control-label" key="index.indexId"/>
        <div class="control">
	        <form:hidden path="id"/>
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
        <appfuse:label styleClass="control-label" key="index.description"/>
        <div class="controls">
        	<form:input path="description" id="description" cssClass="text form-control input-group-sm"/>
        </div>
    </div>

    <div class="control-group col-sm-8">
    	<label class="control-label" ></label>
 		<div class="controls">
        	<input type="submit" class="btn" style="width:80px;" name="save"  value="<fmt:message key="button.save"/>"/>
        	<input type="button" class="btn" style="width:80px;" name="cancel" onclick="location.href='<c:url value="/index/list"/>'" value="<fmt:message key="button.cancel"/>"/>
   		</div>
    </div>

</form:form>
</div>