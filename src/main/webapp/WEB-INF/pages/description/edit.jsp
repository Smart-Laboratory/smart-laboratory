<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="set.description"/></title>
    <meta name="menu" content="SampleSet"/>
    <script type="text/javascript">
    function validateIndex(form){
    	var id = form["bagId"];
    	var val = id.value;
    	if(val==""||!val){
    		alert("bagid can't be null!");
    		return false;
    	}
    	var name = form["name"];
    	var nameval = name.value;
    	if(nameval==""||!nameval){
    		alert("description Name can't be null!");
    		return false;
    	}
    	
    	return true;
    }
    
    </script>
</head>

<div class="col-sm-7">
<h1><fmt:message key='des.edit'/></h1>
<spring:bind path="description.*">
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

<form:form class="form-horizontal" commandName="description" method="post" action="edit" onsubmit="return validateIndex(this)" id="editDescriptionForm">
	<div class="control-groups col-sm-8">
        <appfuse:label styleClass="control-label" key="des.category"/>
        <div class="control">
	        <form:hidden path="id"/>
	       
	        <form:select path="bagId" id="bagId" cssClass="selects form-control input-group-sm">
	        	<form:options items="${bags }" />
	        </form:select>
        </div>
	</div>
    <div class="control-group col-sm-8">
        <appfuse:label styleClass="control-label" key="des.name"/>
        <div class="controls">
        	<form:input path="name" id="name" cssClass="text form-control input-group-sm" />
        </div>
    </div>
    <div class="control-group col-sm-8">
        <appfuse:label styleClass="control-label" key="des.description"/>
        <div class="controls">
        	<form:textarea path="description" id="description" rows="3" cols="20" cssClass="text form-control input-group-sm" />
        </div>
    </div>
    <div class="control-group col-sm-8">
        <appfuse:label styleClass="control-label" key="des.type"/>
        <div class="controls">
        	<form:select path="type" id="type" cssClass="selects form-control input-group-sm">
	        	<form:options items="${typeList}"  />
	        </form:select>
        </div>
    </div>

    <div class="control-group col-sm-8">
    	<label class="control-label" ></label>
 		<div class="controls">
        	<input type="submit" class="btn btn-success" style="width:80px;" name="save"  value="<fmt:message key="button.save"/>"/>
        	<input type="button" class="btn btn-info" style="width:80px;" name="cancel" onclick="location.href='<c:url value="/description/list"/>'" value="<fmt:message key="button.cancel"/>"/>
   		</div>
    </div>
</form:form>
</div>