<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<html lang="en">
<head>
    <meta http-equiv="Cache-Control" content="no-store"/>
    <meta http-equiv="Pragma" content="no-cache"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="icon" href="<c:url value="/images/favicon.ico"/>"/>
    <link rel="stylesheet" type="text/css"  href="<c:url value='/styles/ace.min.css'/>" />
    <link rel="stylesheet" type="text/css"  href="<c:url value='/styles/font-awesome.css'/>" />
    <title><decorator:title/> | <fmt:message key="webapp.name"/></title>
    <t:assets/>
    <decorator:head/>
    <%-- <link rel="stylesheet" type="text/css"  href="<c:url value='../styles/bootstrap.min.css'/>" /> --%>
</head>
<body<decorator:getProperty property="body.id" writeEntireProperty="true"/><decorator:getProperty property="body.class" writeEntireProperty="true"/>  class="no-skin">
    <!--<a href="../pb/pbcx">排班查询</a>-->
    <div class="main-container" sytle="padding-top:50px;" id="content">
        <div class="row">
        <%@ include file="/common/messages.jsp" %>
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
<%= (request.getAttribute("scripts") != null) ?  request.getAttribute("scripts") : "" %>
</body>
</html>
