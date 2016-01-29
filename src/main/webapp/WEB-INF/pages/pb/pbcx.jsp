<%@ include file="/common/taglibs.jsp"%>

<head>
	<title><fmt:message key="menu.pb" /></title>
	<script type="text/javascript" src="../scripts/pb/pbcx.js"></script>
	<script type="text/javascript" src="<c:url value='/scripts/jquery.jqGrid.min.js'/> "></script>
	<link rel="stylesheet" type="text/css" href="<c:url value='/styles/ui.jqgrid.css'/> " />
	<link rel="stylesheet" type="text/css"  href="<c:url value='/styles/jquery-ui.min.css'/>" />
	<link rel="stylesheet" type="text/css"  href="<c:url value='/styles/bootstrap.min.css'/>" />
	
	<script type="text/javascript" src="../scripts/jquery-2.1.4.min.js"></script>
	<script type="text/javascript" src="../scripts/bootstrap.min.js"></script>
    <script type="text/javascript" src="../scripts/jquery-ui.min.js"></script>
    <script type="text/javascript" src="../scripts/i18n/grid.locale-cn.js"></script>
</head>

<style>
select {
	width:auto;
	padding:0px 0px;
}
table tr td {
	width:60px;
}
table tr th {
	width:60px;
}
</style>

<input id="section" value="${section }" type="hidden"/>
<input id="month" value="${month }" type="hidden"/>
<input id="type" value="${type }" type="hidden"/>

<div class="form-inline">
	<input type="text" id="date" class="form-control" style="margin-left:20px;float:left;">
	<select id="sectionSel" class="form-control" style="margin-left:20px;float:left;">
		<option value="1300000"><fmt:message key="labDepartment.1300000"/></option>
		<option value="1300100"><fmt:message key="labDepartment.1300100"/></option>
		<option value="1300200"><fmt:message key="labDepartment.1300200"/></option>
		<option value="1300400"><fmt:message key="labDepartment.1300400"/></option>
		<option value="1300500"><fmt:message key="labDepartment.1300500"/></option>
		<option value="1300501"><fmt:message key="labDepartment.1300501"/></option>
		<option value="1300600"><fmt:message key="labDepartment.1300600"/></option>
		<option value="1300700"><fmt:message key="labDepartment.1300700"/></option>
		<option value="1300800"><fmt:message key="labDepartment.1300800"/></option>
	</select>
		<select id="typeSel" class="form-control" style="margin-left:20px;float:left;display:none">
			<option value="1" ><fmt:message key="pb.yb"/></option>
			<option value="2" ><fmt:message key="pb.lz"/></option>
			<option value="3" ><fmt:message key="pb.wc"/></option>
			<option value="4" ><fmt:message key="pb.ybb"/></option>
			<option value="5" ><fmt:message key="pb.ry"/></option>
			<option value="6" ><fmt:message key="pb.hcy"/></option>
			<option value="7" ><fmt:message key="pb.jjr"/></option>
		</select>
	<button id="searchPB" type="button" class="btn btn-success" style="margin-left:20px;float:left;"><fmt:message key='search'/></button>
	<button id="daochu" class="btn btn-info" style="margin-left:20px;" ><fmt:message key='pb.daochu' /></button>
</div>


<c:choose>
	<c:when test="${size == 0}">
	<div style="margin-top:70px;font-size:25px;">
		<p><b><fmt:message key="pb.nodata"/></b></p>
	</div>
	</c:when>
	<c:otherwise>
		<button id="print" type="button" class="btn btn-info" style="float:right;margin-top:-20px; margin-right:15px;" onclick='javascript:window.print()'><fmt:message key='audit.print'/></button>
		<div>
			<h3 style="margin-left:320px;"><c:out value="${month}"/><fmt:message key="labDepartment.${section}"/><fmt:message key="pb.biaoti"/></h3>		
			<input id="cxdata" value="${arrString }" type="hidden"/>
			<table id="data" style="margin-top:10px;font-size:12px;text-align:center;" border="1px;">
				
			</table>
		</div>
	</c:otherwise>
</c:choose>