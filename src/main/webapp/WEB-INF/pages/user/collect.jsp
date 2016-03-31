<%@ include file="/common/taglibs.jsp" %>

<head>
    <title><fmt:message key="sample.manage.collect"/></title>
        
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
    <script type="text/javascript" src="../scripts/highcharts.js"></script>
    <script type="text/javascript" src="../scripts/ajaxfileupload.js"></script>
    <script type="text/javascript" src="../scripts/galleria-1.3.5.min.js"></script>
	<script type="text/javascript" src="../scripts/galleria.classic.min.js"></script>
	<script type="text/javascript" src="../scripts/manage/collect.js"></script>

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

<%@ include file="../collect/top.jsp" %>
<div class="col-sm-12">
	<%@ include file="../collect/left.jsp" %>
	<%@ include file="../collect/middle.jsp" %>
</div>
<%@ include file="../collect/dialog.jsp" %>



</body>