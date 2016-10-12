<%@ include file="/common/taglibs.jsp" %>

<head>
    <title><fmt:message key="sample.manage.audit"/></title>
        
    <%--<link rel="stylesheet" type="text/css"  href="<c:url value='/styles/jquery-ui.min.css'/>" />--%>
    <link rel="stylesheet" type="text/css"  href="<c:url value='/styles/ui.jqgrid.css'/>" />
	<link rel="stylesheet" type="text/css"  href="<c:url value='/styles/ruleLib.css'/>" />
	<link rel="stylesheet" type="text/css"  href="<c:url value='/styles/lis/audit.css'/>" />
	
	<script type="text/javascript" src="<c:url value='/scripts/jquery.tablednd_0_5.js'/> "></script>
    <script type="text/javascript" src="<c:url value='/scripts/jquery-ui.min.js'/> "></script>
    <script type="text/javascript" src="<c:url value='/scripts/i18n/grid.locale-cn.js'/> "></script>
    <script type="text/javascript" src="<c:url value='/scripts/jquery.jqGrid.js'/> "></script>
    <script type="text/javascript" src="<c:url value='/scripts/jquery.validate.js'/> "></script>
    <script type="text/javascript" src="<c:url value='/scripts/raphael.2.1.0.min.js'/> "></script>
    <script type="text/javascript" src="<c:url value='/scripts/justgage.1.0.1.min.js'/> "></script>
    <script type="text/javascript" src="<c:url value='/scripts/jquery.form.js'/> "></script>
    <script type="text/javascript" src="<c:url value='/scripts/highcharts.js'/> "></script>
    <script type="text/javascript" src="<c:url value='/scripts/ajaxfileupload.js'/> "></script>
    <script type="text/javascript" src="<c:url value='/scripts/galleria-1.3.5.min.js'/> "></script>
	<script type="text/javascript" src="<c:url value='/scripts/galleria.classic.min.js'/> "></script>
	<script type="text/javascript" src="<c:url value='/scripts/layer/layer.js'/> "></script>
	<script type="text/javascript" src="<c:url value='/scripts/layer/extend/layer.ext.js'/> "></script>
	<script type="text/javascript" src="<c:url value='/scripts/lis/audit.js'/> "></script>

	<script language="javascript" src="<c:url value='/scripts/LodopFuncs.js'/> "></script>

<style>
.ui-tabs-anchor {
	width:60px;
	padding: 0 10px;
}
p{
	margin-bottom:1px;
}
.ui-autocomplete {
    z-index: 999999999999999999!important;
}
.form-horizontal .form-group{
	margin: 5px 10px!important;
}

.layui-layer-title {
    padding: 0 20px 0 20px;
    height: 42px;
    line-height: 42px;
    border-bottom: 1px solid #eee;
    font-size: 14px;
    color: #333;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    background-color: #F8F8F8;
    border-radius: 2px 2px 0 0;
}

#addTestList .form-inline{
	margin: 5px 5px;
}

</style>
    <script>
        var baseUrl = baseUrl || "<%=request.getContextPath()%>";
    </script>
</head>


<body>

<input type="hidden" id="lab" value="${lab}">
<input type="hidden" id="sampletext" value="${today}">
<input id="strTody" type="hidden" value="${today}" />
<input type="hidden" value="0" id="isfulltag">
<div id="header" class="col-sm-12">
	<%@ include file="../audit/top.jsp" %>
</div>

<div class="row">
	<div class="col-sm-12">
		<%@ include file="../audit/left.jsp" %>
		<%@ include file="../audit/middle.jsp" %>
	</div>
</div>
<%@ include file="../audit/dialog.jsp" %>
</body>
