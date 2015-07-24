<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="editResult.title"/></title>
    <meta name="heading" content="<fmt:message key='editResult.heading'/>"/>
    <meta name="menu" content="RulesManage"/>
    <script type="text/javascript">
	    function button(obj){
			document.getElementById("buttonresult").value=obj;
		}
	    
	    function validateIndex(form){
	    	
	    }
	</script>
</head>

<div class="col-sm-7">

<h1><fmt:message key='result.edit'/></h1>
<spring:bind path="result.*">
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

<form:form commandName="result" method="post" action="edit" onsubmit="return validateResult(this)" id="editresult">
	<div class="control-groups col-sm-8"  style="display:none;">
        <appfuse:label styleClass="control-label" key="result.id"/>
        <div class="control">
	        <form:hidden path="id"/>
        </div>
	</div>
	<div class="control-groups col-sm-8">
        <appfuse:label styleClass="control-label" key="result.content"/>
        <div class="control">
	        <form:input path="content" id="content" cssClass="text form-control input-group-sm"/>
        </div>
	</div>
	<div class="control-groups col-sm-8" style="display:none;">
        <appfuse:label styleClass="desc" key="result.level"/>
        <form:select path="level">
    		<form:option value="1"></form:option>
    		<form:option value="2"></form:option>
    		<form:option value="3"></form:option>
    		<form:option value="4"></form:option>
    		<form:option value="5"></form:option>
    		<form:option value="6"></form:option>
    		<form:option value="7"></form:option>
    		<form:option value="8"></form:option>
    		<form:option value="9"></form:option>
    		<form:option value="10"></form:option>
		</form:select>
	</div>
	<div class="control-groups col-sm-8" style="display:none;">
        <appfuse:label styleClass="control-label" key="result.reject"/>
        <div class="control">
	        <form:input path="reject" id="reject" cssClass="text form-control input-group-sm"/>
        </div>
	</div>
	<div class="control-groups col-sm-8">
        <appfuse:label styleClass="control-label" key="result.category"/>
        <div class="control">
	        <form:input path="category" id="category" cssClass="text form-control input-group-sm"/>
        </div>
	</div>
	<div class="control-groups col-sm-8">
        <appfuse:label styleClass="desc" key="result.percent"/>
        <div class="control">
	        <form:input path="percent" id="percent" cssClass="text form-control input-group-sm"/>
        </div>
	</div>
    <div class="control-group col-sm-8">
    	<label class="control-label" ></label>
 		<div class="controls">
        	<input type="submit" class="btn" style="width:80px;" name="save"  value="<fmt:message key="button.save"/>"/>
        	<input type="button" class="btn" style="width:80px;" name="cancel" onclick="location.href='<c:url value="/result/view"/>?id=${result.id}'" value="<fmt:message key="button.cancel"/>"/>
   		</div>
    </div>

</form:form>
</div>