<%@ include file="/common/taglibs.jsp"%>

<head>
	<title><fmt:message key="menu.pb" /></title>
	<script type="text/javascript" src="../scripts/pb/pbcx.js"></script>
	<script type="text/javascript" src="<c:url value='/scripts/grid.locale-cn.js'/> "></script>
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

<div>
	<input type="text" id="date" style="margin-left:20px;float:left;">
	<select id="section" style="margin-left:20px;float:left;">
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
	<div id="type" style="display:none;">
		<select id='typeSel' style="margin-left:20px;float:left;">
			<option value="1" ><fmt:message key="pb.yb"/></option>
			<option value="2" ><fmt:message key="pb.lz"/></option>
			<option value="3" ><fmt:message key="pb.wc"/></option>
			<option value="4" ><fmt:message key="pb.ybb"/></option>
			<option value="5" ><fmt:message key="pb.sxjxlg"/></option>
		</select>
	</div>
	<button id="search" type="button" class="btn btn-success" style="margin-left:20px;float:left;height:30px;"><fmt:message key='search'/></button>
</div>


<c:choose>
	<c:when test="${size == 0}">
	<div style="margin-top:70px;font-size:25px;">
		<p><b><fmt:message key="pb.nodata"/></b></p>
	</div>
	</c:when>
	<c:otherwise>
		<button id="print" type="button" class="btn btn-info" style="margin-left:5px;height:30px;" onclick='javascript:window.print()'><fmt:message key='audit.print'/></button>
		<div>
			<h3 style="margin-left:320px;"><c:out value="${date}"/><fmt:message key="labDepartment.${section}"/><fmt:message key="pb.biaoti"/></h3>		
			<table id="data" style="margin-top:10px;font-size:12px;text-align:center;" border="1px;">
				<c:forEach items="${arrArray}" var="arr" >
				<tr>
					<c:forEach items="${arr}" var="shift">${shift}</c:forEach>
				</tr>
				</c:forEach>
			</table>
		</div>
	</c:otherwise>
</c:choose>