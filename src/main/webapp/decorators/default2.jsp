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
    <script type="text/javascript" src="<c:url value="/scripts/jquery-2.1.4.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/bootstrap.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/ace.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/ace-elements.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/bootstrap-tag.min.js"/>"></script>
    <decorator:head/>
    <link rel="stylesheet" type="text/css"  href="<c:url value='/styles/bootstrap.min.css'/>" />
    <link rel="stylesheet" type="text/css"  href="<c:url value='/styles/font-awesome.css'/>" />
    <link rel="stylesheet" type="text/css"  href="<c:url value='/styles/ace.min.css'/>" />
    
    <%-- <link rel="stylesheet" type="text/css"  href="<c:url value='../styles/bootstrap.min.css'/>" /> --%>
</head>
<body<decorator:getProperty property="body.id" writeEntireProperty="true"/><decorator:getProperty property="body.class" writeEntireProperty="true"/>>
    <c:set var="currentMenu" scope="request"><decorator:getProperty property="meta.menu"/></c:set>

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
        <div clas="main-content">
            <decorator:body/>

            <c:if test="${currentMenu == 'SampleManage'}">
                <div class="col-sm-2">
                <menu:useMenuDisplayer name="Velocity" config="navlistMenu.vm" permissions="rolesAdapter">
                    <menu:displayMenu name="SampleManage"/>
                </menu:useMenuDisplayer>
                </div>
            </c:if>
            <c:if test="${currentMenu == 'SampleSet'}">
                <div class="col-sm-2">
                <menu:useMenuDisplayer name="Velocity" config="navlistMenu.vm" permissions="rolesAdapter">
                    <menu:displayMenu name="SampleSet"/>
                </menu:useMenuDisplayer>
                </div>
            </c:if>
            <c:if test="${currentMenu == 'Reagent'}">
                <div class="col-sm-2">
                <menu:useMenuDisplayer name="Velocity" config="navlistMenu.vm" permissions="rolesAdapter">
                    <menu:displayMenu name="Reagent"/>
                </menu:useMenuDisplayer>
                </div>
            </c:if>
            <c:if test="${currentMenu == 'Statistic'}">
                <div class="col-sm-2">
                <menu:useMenuDisplayer name="Velocity" config="navlistMenu.vm" permissions="rolesAdapter">
                    <menu:displayMenu name="Statistic"/>
                </menu:useMenuDisplayer>
                </div>
            </c:if>
            <c:if test="${currentMenu == 'Quality'}">
                <div class="col-sm-2">
                <menu:useMenuDisplayer name="Velocity" config="navlistMenu.vm" permissions="rolesAdapter">
                    <menu:displayMenu name="Quality"/>
                </menu:useMenuDisplayer>
                </div>
            </c:if>
            <c:if test="${currentMenu == 'Individual'}">
                <div class="col-sm-2">
                <menu:useMenuDisplayer name="Velocity" config="navlistMenu.vm" permissions="rolesAdapter">
                    <menu:displayMenu name="Individual"/>
                </menu:useMenuDisplayer>
                </div>
            </c:if>
        </div>
    </div>

    <div id="footer" class="container navbar-fixed-bottom">
        <span class="col-sm-6 text-left">
        	<fmt:message key="project.name"/> | 
        	<fmt:message key="webapp.version"/>
            <c:if test="${pageContext.request.remoteUser != null}">
            | <fmt:message key="user.status"/> ${pageContext.request.remoteUser}
            </c:if>
            | <a target="_blank" href="http://192.168.15.73/lab"><fmt:message key="update.name"/></a>
       	    | <a target="_blank" href="<fmt:message key="wsdjk.url"/>"><fmt:message key="wsdjk.name"/></a>
   		</span>
   		<span style="float: right;">
       		 &copy; <fmt:message key="copyright.year"/> <a target="_blank" href="http://192.168.15.73/zhishi"><fmt:message key="company.name"/></a>
        </span>
        </div>
    </div>
<%= (request.getAttribute("scripts") != null) ?  request.getAttribute("scripts") : "" %>
</body>
</html>
