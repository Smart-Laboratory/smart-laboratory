<%@ include file="/common/taglibs.jsp" %>

<head>
    <title><fmt:message key="sample.manage.audit"/></title>
        
    <link rel="stylesheet" type="text/css"  href="<c:url value='/styles/jquery-ui.min.css'/>" />
    <link rel="stylesheet" type="text/css"  href="<c:url value='/styles/bootstrap.min.css'/>" />
    <link rel="stylesheet" type="text/css"  href="<c:url value='/styles/ui.jqgrid.css'/>" />
	<link rel="stylesheet" type="text/css"  href="<c:url value='/styles/ruleLib.css'/>" />
	<link rel="stylesheet" type="text/css"  href="<c:url value='/styles/lis/audit.css'/>" />
	
    
    <script type="text/javascript" src="../scripts/jquery-2.1.4.min.js"></script>
	<script type="text/javascript" src="../scripts/bootstrap.min.js"></script>
	<script type="text/javascript" src="../scripts/jquery.tablednd_0_5.js"></script>
    <script type="text/javascript" src="../scripts/jquery-ui.min.js"></script>
    <script type="text/javascript" src="../scripts/i18n/grid.locale-cn.js"></script>
    <script type="text/javascript" src="../scripts/jquery.jqGrid.js"></script>
    <script type="text/javascript" src="../scripts/jquery.validate.js"></script>
    <script type="text/javascript" src="../scripts/raphael.2.1.0.min.js"></script>
    <script type="text/javascript" src="../scripts/justgage.1.0.1.min.js"></script>
    <script type="text/javascript" src="../scripts/jquery.form.js"></script>
    <script type="text/javascript" src="../scripts/lis/audit.js"></script>
    <script type="text/javascript" src="../scripts/highcharts.js"></script>
    <script type="text/javascript" src="../scripts/ajaxfileupload.js"></script>
    <script type="text/javascript" src="../scripts/galleria-1.3.5.min.js"></script>
	<script type="text/javascript" src="../scripts/galleria.classic.min.js"></script>

<style>
.ui-tabs-anchor {
	padding: 0 10px;
}
</style>
</head>


<body>

<input type="hidden" id="lab" value="${lab}">
<input type="hidden" id="sampletext" value="${today}">
<input id="strTody" type="hidden" value="${today}" />
<input type="hidden" value="0" id="isfulltag">
<div id="header" class="col-sm-12">
	<%@ include file="../audit/top.jsp" %>
</div>

<div class="col-sm-12">
	<%@ include file="../audit/left.jsp" %>
	<%@ include file="../audit/middle.jsp" %>
	<%@ include file="../audit/right.jsp" %>
</div>


<div id="footer2" class="col-sm-12" style="padding-top:20px;">
		<hr style="width:100%;height:1px;border:none;border-top:1px solid #555555; "/>
        <span class="col-sm-6" >
        	<fmt:message key="project.name"/> | 
        	<fmt:message key="webapp.version"/>
            <c:if test="${pageContext.request.remoteUser != null}">
            | <fmt:message key="user.status"/> ${pageContext.request.remoteUser}
            </c:if>
            | <a target="_blank" href="<fmt:message key="update.url"/>"><fmt:message key="update.name"/></a>
       	    | <a target="_blank" href="<fmt:message key="wsdjk.url"/>"><fmt:message key="wsdjk.name"/></a>
   		</span>
   		<span class="col-sm-2" style="float:right;">
       		 &copy; <fmt:message key="copyright.year"/> <a target="_blank" href="<fmt:message key="company.url"/>"><fmt:message key="company.name"/></a>
        </span>
        
        
</div>


<%@ include file="../audit/dialog.jsp" %>
</body>