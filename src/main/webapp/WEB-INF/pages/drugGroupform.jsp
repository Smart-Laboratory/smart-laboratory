<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="drugGroupDetail.title"/></title>
    <meta name="heading" content="<fmt:message key='drugGroupDetail.heading'/>"/>
</head>

<c:set var="delObject" scope="request"><fmt:message key="drugGroupList.drugGroup"/></c:set>
<script type="text/javascript">var msgDelConfirm =
   "<fmt:message key="delete.confirm"><fmt:param value="${delObject}"/></fmt:message>";
</script>

<div class="col-sm-2">
    <h2><fmt:message key="drugGroupDetail.heading"/></h2>
    <fmt:message key="drugGroupDetail.message"/>
</div>

<div class="col-sm-7">
<form:errors path="*" cssClass="alert alert-danger alert-dismissable" element="div"/>
<form:form commandName="drugGroup" method="post" action="drugGroupform" cssClass="well"
           id="drugGroupForm" onsubmit="return validateDrugGroup(this)">
<ul>
    <spring:bind path="drugGroup.id">
    <div class="form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
    </spring:bind>
        <appfuse:label key="drugGroup.id" styleClass="control-label"/>
        <form:input path="id" id="id"/>
        <form:errors path="id" cssClass="help-block"/>
    </div>
    <spring:bind path="drugGroup.name">
    <div class="form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
    </spring:bind>
        <appfuse:label key="drugGroup.name" styleClass="control-label"/>
        <form:input cssClass="form-control" path="name" id="name"  maxlength="255"/>
        <form:errors path="name" cssClass="help-block"/>
    </div>
    <spring:bind path="drugGroup.state">
    <div class="form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
    </spring:bind>
        <appfuse:label key="drugGroup.state" styleClass="control-label"/>
        <form:input cssClass="form-control" path="state" id="state"  maxlength="255"/>
        <form:errors path="state" cssClass="help-block"/>
    </div>

    <div class="form-group">
        <button type="submit" class="btn btn-primary" name="save" onclick="bCancel=false">
            <i class="icon-ok icon-white"></i> <fmt:message key="button.save"/>
        </button>
        <c:if test="${not empty drugGroup.id}">
            <button type="submit" class="btn btn-warning" name="delete" onclick="bCancel=true;return confirmMessage(msgDelConfirm)">
                <i class="icon-trash icon-white"></i> <fmt:message key="button.delete"/>
            </button>
        </c:if>

        <button type="submit" class="btn btn-default" name="cancel" onclick="bCancel=true">
            <i class="icon-remove"></i> <fmt:message key="button.cancel"/>
        </button>
    </div>
</form:form>
</div>

<v:javascript formName="drugGroup" cdata="false" dynamicJavascript="true" staticJavascript="false"/>
<script type="text/javascript" src="<c:url value='/scripts/validator.jsp'/>"></script>

<script type="text/javascript">
    $(document).ready(function() {
        $("input[type='text']:visible:enabled:first", document.forms['drugGroupForm']).focus();
    });
</script>
