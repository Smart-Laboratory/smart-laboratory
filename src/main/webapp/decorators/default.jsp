<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<html lang="en">
<head>
    <meta http-equiv="Cache-Control" content="no-store"/>
    <meta http-equiv="Pragma" content="no-cache"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="icon" href="<c:url value="/images/favicon.ico"/>"/>
    <title><decorator:title/> | <fmt:message key="webapp.name"/></title>
    <t:assets/>
    <decorator:head/>
    
    <%-- <link rel="stylesheet" type="text/css"  href="<c:url value='../styles/bootstrap.min.css'/>" /> --%>
</head>
<body<decorator:getProperty property="body.id" writeEntireProperty="true"/><decorator:getProperty property="body.class" writeEntireProperty="true"/>  class="no-skin">
    <c:set var="currentMenu" scope="request">
        <decorator:getProperty property="meta.menu"/></c:set>

    <div id="head" class="navbar navbar-default " role="navigation">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#navbar">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>

            <a class="navbar-brand" href="<c:url value='/'/>"><fmt:message key="webapp.name"/></a>
        </div>

        <%@ include file="/common/menu.jsp" %>
        <c:if test="${pageContext.request.locale.language ne 'en'}">
            <div id="switchLocale" ><a href="<c:url value='/?locale=en'/>">
                <fmt:message key="webapp.name"/> IN ENGLISH</a>
            </div>
        </c:if>
    </div>

    <div class="main-container" sytle="padding-top:50px;" id="content">
        <%@ include file="/common/messages.jsp" %>
            <decorator:body/>
    </div>
    <div class="footer">
        <div class="footer-inner" style="left: 0px;">
            <div class="footer-content">
				 <span class="">
        	<fmt:message key="project.name"/> |
        	<fmt:message key="webapp.version"/>
            <c:if test="${pageContext.request.remoteUser != null}">
                | <fmt:message key="user.status"/> ${pageContext.request.remoteUser}
            </c:if>
            | <a target="_blank" href="<fmt:message key="update.url"/>"><fmt:message key="update.name"/></a>
       	    | <a target="_blank" href="<fmt:message key="wsdjk.url"/>"><fmt:message key="wsdjk.name"/></a>
   		</span>
   		<span style="float: right;">
       		 &copy; <fmt:message key="copyright.year"/> <a target="_blank" href="<fmt:message key="company.url"/>"><fmt:message key="company.name"/></a>
        </span>
            </div>
        </div>
    </div>


<%= (request.getAttribute("scripts") != null) ?  request.getAttribute("scripts") : "" %>
</body>
</html>
