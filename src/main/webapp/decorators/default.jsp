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
            | <a target="_blank" href="http://192.168.15.73/lab"><fmt:message key="update.name"/></a>
       	    | <a target="_blank" href="<fmt:message key="wsdjk.url"/>"><fmt:message key="wsdjk.name"/></a>
   		</span>
   		<span style="float: right;">
       		 &copy; <fmt:message key="copyright.year"/> <a target="_blank" href="http://192.168.15.73/zhishi"><fmt:message key="company.name"/></a>
        </span>
            </div>
        </div>
    </div>


<%= (request.getAttribute("scripts") != null) ?  request.getAttribute("scripts") : "" %>
</body>
<script type="text/javascript">
    /**
     * 设置JQGRID 上下页图标
     * @param table
     */
    function updatePagerIcons(table) {
        var replacement =
        {
            'ui-icon-seek-first' : 'ace-icon fa fa-angle-double-left bigger-140',
            'ui-icon-seek-prev' : 'ace-icon fa fa-angle-left bigger-140',
            'ui-icon-seek-next' : 'ace-icon fa fa-angle-right bigger-140',
            'ui-icon-seek-end' : 'ace-icon fa fa-angle-double-right bigger-140'
        };
        $('.ui-pg-table:not(.navtable) > tbody > tr > .ui-pg-button > .ui-icon').each(function(){
            var icon = $(this);
            var $class = $.trim(icon.attr('class').replace('ui-icon', ''));
            if($class in replacement) icon.attr('class', 'ui-icon '+replacement[$class]);
        })
    }
</script>
</html>
