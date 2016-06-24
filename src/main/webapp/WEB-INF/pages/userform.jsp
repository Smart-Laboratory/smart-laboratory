<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<head>
    <title><fmt:message key="userProfile.title"/></title>
    <meta name="menu" content="Individual"/>
</head>

<c:set var="delObject" scope="request"><fmt:message key="userList.user"/></c:set>
<script type="text/javascript">var msgDelConfirm =
   "<fmt:message key="delete.confirm"><fmt:param value="${delObject}"/></fmt:message>";
</script>

<div class="row">
<div class="col-sm-2">
    <h2><fmt:message key="userProfile.heading"/></h2>
    <c:choose>
        <c:when test="${param.from == 'list'}">
            <p><fmt:message key="userProfile.admin.message"/></p>
        </c:when>
        <c:otherwise>
            <p><fmt:message key="userProfile.message"/></p>
        </c:otherwise>
    </c:choose>
</div>
<div class="col-sm-7">
    <spring:bind path="user.*">
        <c:if test="${not empty status.errorMessages}">
            <div class="alert alert-danger alert-dismissable">
                <a href="#" data-dismiss="alert" class="close">&times;</a>
                <c:forEach var="error" items="${status.errorMessages}">
                    <c:out value="${error}" escapeXml="false"/><br/>
                </c:forEach>
            </div>
        </c:if>
    </spring:bind>

    <form:form commandName="user" method="post" action="userform" id="userForm" autocomplete="off"
               cssClass="well" onsubmit="return validateUser(this)">
        <form:hidden path="id"/>
        <form:hidden path="version"/>
        <input type="hidden" name="from" value="<c:out value="${param.from}"/>"/>

        <spring:bind path="user.username">
        <div class="form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
        </spring:bind>
            <appfuse:label styleClass="control-label" key="user.username"/>
            <form:input cssClass="form-control" path="username" id="username"/>
            <form:errors path="username" cssClass="help-block"/>
            <c:if test="${pageContext.request.remoteUser == user.username}">
                <span class="help-block">
                    <a href="<c:url value="/updatePassword" />"><fmt:message key='updatePassword.changePasswordLink'/></a>
                </span>
            </c:if>
        </div>

        <spring:bind path="user.passwordHint">
        <div class="form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
        </spring:bind>
            <appfuse:label styleClass="control-label" key="user.passwordHint"/>
            <form:input cssClass="form-control" path="passwordHint" id="passwordHint"/>
            <form:errors path="passwordHint" cssClass="help-block"/>
        </div>
        <div class="row">
            <spring:bind path="user.name">
            <div class="col-sm-6 form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
            </spring:bind>
                <appfuse:label styleClass="control-label" key="user.name"/>
                <form:input cssClass="form-control" path="name" id="name" maxlength="50"/>
                <form:errors path="name" cssClass="help-block"/>
            </div>
            <div class="col-sm-6 form-group">
                <appfuse:label styleClass="control-label" key="user.hospitalId"/>
                <div class="controls">
					<form:select path="hospitalId" id="hospitalId" cssClass="selects form-control input-group-sm">
						<form:options items="${hospitals}" />
	       		 	</form:select>
      			 </div>
            </div>
        </div>
        <div class="row">
            <spring:bind path="user.email">
            <div class="col-sm-6 form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
            </spring:bind>
                <appfuse:label styleClass="control-label" key="user.email"/>
                <form:input cssClass="form-control" path="email" id="email"/>
                <form:errors path="email" cssClass="help-block"/>
            </div>
            <div class="col-sm-6 form-group">
                <appfuse:label styleClass="control-label" key="user.phoneNumber"/>
                <form:input cssClass="form-control" path="phoneNumber" id="phoneNumber"/>
            </div>
        </div>
        <div class="form-group">
            <appfuse:label styleClass="control-label" key="user.website"/>
            <form:input cssClass="form-control" path="website" id="website"/>
        </div>
        <div>
            <legend class="accordion-heading">
                <a data-toggle="collapse" href="#collapse-address"><fmt:message key="user.address.address"/></a>
            </legend>
            <div id="collapse-address" class="accordion-body collapse">
                <div class="form-group">
                    <appfuse:label styleClass="control-label" key="user.address.address"/>
                    <form:input cssClass="form-control" path="address.address" id="address.address"/>
                </div>
                <div class="row">
                    <div class="col-sm-7 form-group">
                        <appfuse:label styleClass="control-label" key="user.address.city"/>
                        <form:input cssClass="form-control" path="address.city" id="address.city"/>
                    </div>
                    <div class="col-sm-2 form-group">
                        <appfuse:label styleClass="control-label" key="user.address.province"/>
                        <form:input cssClass="form-control" path="address.province" id="address.province"/>
                    </div>
                    <div class="col-sm-3 form-group">
                        <appfuse:label styleClass="control-label" key="user.address.postalCode"/>
                        <form:input cssClass="form-control" path="address.postalCode" id="address.postalCode"/>
                    </div>
                </div>
                <div class="form-group">
                    <appfuse:label styleClass="control-label" key="user.address.country"/>
                    <appfuse:country name="address.country" prompt="" default="${user.address.country}"/>
                </div>
            </div>
        </div>
        
        <c:if test="${param.from == 'list'}">
        <div>
            <legend class="accordion-heading">
                <a data-toggle="collapse" href="#collapse-labset"><fmt:message key="user.lab.set"/></a>
            </legend>
            <div id="collapse-labset" class="accordion-body collapse">
                <div class="form-group">
                    <appfuse:label styleClass="control-label" key="user.lab.set"/>
                    <form:input cssClass="form-control" path="department" id="department"/>
                </div>
                <div class="form-group">
                    <appfuse:label styleClass="control-label" key="lab.pbcode"/>
                    <form:input cssClass="form-control" path="pbsection" id="pbsection"/>
                </div>
            </div>
        </div>
        </c:if>
        
<c:choose>
    <c:when test="${param.from == 'list' or param.method == 'Add'}">
        <div class="form-group">
            <label class="control-label"><fmt:message key="userProfile.accountSettings"/></label>
            <label class="checkbox-inline">
                <form:checkbox path="enabled" id="enabled"/>
                <fmt:message key="user.enabled"/>
            </label>

            <label class="checkbox-inline">
                <form:checkbox path="accountExpired" id="accountExpired"/>
                <fmt:message key="user.accountExpired"/>
            </label>

            <label class="checkbox-inline">
                <form:checkbox path="accountLocked" id="accountLocked"/>
                <fmt:message key="user.accountLocked"/>
            </label>

            <label class="checkbox-inline">
                <form:checkbox path="credentialsExpired" id="credentialsExpired"/>
                <fmt:message key="user.credentialsExpired"/>
            </label>
        </div>
        <div class="form-group">
            <label for="userRoles" class="control-label"><fmt:message key="userProfile.assignRoles"/></label>
            <select id="userRoles" name="userRoles" multiple="true" class="form-control">
                <c:forEach items="${availableRoles}" var="role">
                <option value="${role.value}" ${fn:contains(user.roles, role.label) ? 'selected' : ''}>${role.label}</option>
                </c:forEach>
            </select>
        </div>
    </c:when>
    <c:when test="${not empty user.username}">
        <div class="form-group">
            <label class="control-label"><fmt:message key="user.roles"/>:</label>
            <div class="readonly">
                <c:forEach var="role" items="${user.roleList}" varStatus="status">
                    <c:out value="${role.label}"/><c:if test="${!status.last}">,</c:if>
                    <input type="hidden" name="userRoles" value="<c:out value="${role.label}"/>"/>
                </c:forEach>
            </div>
            <form:hidden path="enabled"/>
            <form:hidden path="accountExpired"/>
            <form:hidden path="accountLocked"/>
            <form:hidden path="credentialsExpired"/>
        </div>
    </c:when>
</c:choose>
        <div class="form-group">
            <button type="submit" class="btn btn-primary" name="save" onclick="bCancel=false">
                <i class="icon-ok icon-white"></i> <fmt:message key="button.save"/>
            </button>

            <c:if test="${param.from == 'list' and param.method != 'Add'}">
              <button type="submit" class="btn btn-default" name="delete" onclick="bCancel=true;return confirmMessage(msgDelConfirm)">
                  <i class="icon-trash"></i> <fmt:message key="button.delete"/>
              </button>
            </c:if>

            <button type="submit" class="btn btn-default" name="cancel" onclick="bCancel=true">
                <i class="icon-remove"></i> <fmt:message key="button.cancel"/>
            </button>
        </div>
    </form:form>
</div>
<div class="col-sm-3">

	<div id="uploadDialog" style="text-align:left;" >
		<fieldset style="width:95%; margin-left:4px;">
		<legend style="margin-top:3px;"><fmt:message key='electronic.Signature.titel' /></legend>
			<div>
		    <button class="btn btn-info" onclick="createInput();"><fmt:message key='add.point'/></button>
		    <button class="btn btn-success" onclick="ajaxFileUploads()"><fmt:message key='upload.title'/></button>
			<div id="more" style="float:left;"></div>
			</div>
		</fieldset>
	    <h5><fmt:message key='electronic.Signature.display'/></h5>
	    <img id="electronicSignature" alt="" src="${dzqm_imghtm}" border="0" width="120px" height="40px" style="border:1px solid #000000;">
	    <div id="galleria"></div>
	</div>
</div>
</div>
<c:set var="scripts" scope="request">
<script type="text/javascript">
// This is here so we can exclude the selectAll call when roles is hidden
function onFormSubmit(theForm) {
    return validateUser(theForm);
}

function createInput(){
    var count=1;
    var str = '<div class="col-sm-12" style="margin-top:5px;"><input type="file" contentEditable="false" id="uploads' + count + '' +
    '" name="uploads'+ count +'" class="col-sm-10"/><button onclick="removeInput(event,\'more\')" class="col-sm-2">'+'删除</button></div>';
    //document.getElementById(parentId).insertAdjacentHTML("beforeEnd",str);
     if($("#more").html()==""){
   		 $("#more").append(str);
    }else{
    	alert("只能上传一个图片文件");
    	
    }
}
function removeInput(evt, parentId){
	   var el = evt.target == null ? evt.srcElement : evt.target;
	   var div = el.parentNode;
	   var cont = document.getElementById(parentId);       
	   if(cont.removeChild(div) == null){
	    return false;
	   }
	   return true;
}

function ajaxFileUploads(){
    var uplist = $("input[name^=uploads]");
	var arrId = [];
	for (var i=0; i< uplist.length; i++){
	    if(uplist[i].value){
	    	arrId[i] = uplist[i].id;
		}
    }
	$.ajaxFileUpload({
		url:'../audit/ajax/uploadElectronicSignatureImg',
		secureuri:false,
		fileElementId: arrId,  
		dataType: 'json',//返回数据的类型  
		success: function (data){
			alert("修改电子签名成功！");
			$("#electronicSignature").attr("src",data.imgurl);
			$("#more").html("");
		},
		error: function(){
			alert("error");
		}
	});
}
</script>
</c:set>

<v:javascript formName="user" staticJavascript="false"/>
<script type="text/javascript" src="<c:url value="/scripts/validator.jsp"/>"></script>
<script type="text/javascript" src="../scripts/ajaxfileupload.js"></script>
