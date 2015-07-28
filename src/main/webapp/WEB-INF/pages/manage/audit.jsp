<%@ include file="/common/taglibs.jsp" %>

<head>
    <title><fmt:message key="sample.manage.audit"/></title>
    
    <script type="text/javascript" src="../scripts/jquery-2.1.4.min.js"></script>
	<link rel="stylesheet" type="text/css"  href="<c:url value='../styles/jquery-ui.min.css'/>" />
    <script type="text/javascript" src="../scripts/jquery-ui.min.js"></script>
    <script type="text/javascript" src="../scripts/i18n/grid.locale-cn.js"></script>
    <script type="text/javascript" src="../scripts/jquery.jqGrid.js"></script>
	<link rel="stylesheet" type="text/css"  href="<c:url value='../styles/ui.jqgrid.css'/>" />
	
	<script type="text/javascript" src="../scripts/lis/audit.js"></script>
	
</head>
<body>
<input type="hidden" id="lab" value="${lab}">
<input type="hidden" id="sampletext" value="${today}">


<%@ include file="../audit/top.jsp" %>
<div class="col-sm-12">
	<%@ include file="../audit/left.jsp" %>
	<%@ include file="../audit/middle.jsp" %>
	<%@ include file="../audit/right.jsp" %>
</div>
<%@ include file="../audit/dialog.jsp" %>
</body>