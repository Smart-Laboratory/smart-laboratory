<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp"%>

<head>
	<title><fmt:message key="menu.pb" /></title>
	<script type="text/javascript" src="../scripts/pb/sxgroupPbcx.js"></script>
	<script type="text/javascript" src="<c:url value='/scripts/jquery.jqGrid.min.js'/> "></script>
	<link rel="stylesheet" type="text/css" href="<c:url value='/styles/ui.jqgrid.css'/> " />
	<link rel="stylesheet" type="text/css"  href="<c:url value='/styles/jquery-ui.min.css'/>" />
	<link rel="stylesheet" type="text/css"  href="<c:url value='/styles/bootstrap.min.css'/>" />
	
	<script type="text/javascript" src="../scripts/jquery-2.1.4.min.js"></script>
	<script type="text/javascript" src="../scripts/bootstrap.min.js"></script>
    <script type="text/javascript" src="../scripts/jquery-ui.min.js"></script>
    <script type="text/javascript" src="../scripts/i18n/grid.locale-cn.js"></script>
    


<style>
thead.fixedHeader tr {
	position: relative;
	display: block
}
html>body thead.fixedHeader {
	overflow-y: scroll;	
	display: block;
}
html>body tbody.scrollContent {
	display: block;
	height: 500px;
	overflow-y: scroll;	
	width: 100%
}

table tr th {
	width:100px;
	height:24px;
	text-align:center;
}
table tr td {
	width:50px;
	height:24px;
}

div .data{ 
height:500px;
border: 0px solid #009933; 
} 

table td.sx{
	background: #00E6FF!important;
}

.footer{
	display:none;
}

</style>  
    
</head>

<input id="section" value="${section }" type="hidden"/>
<input id="month" value="${month }" type="hidden"/>
<input id="view" value="${view }" type="hidden"/>

		<div class="form-inline noprint" style="width:1024x;">
			<input type="text" id="date" class="form-control" sytle="width:50px;">
			<button id="changeMonth" class="btn btn-info form-control" style="margin-left:10px;">更改日期</button>
			
			
			<select id="sectionSelect" onchange="labChange(this)" class="form-control" style="margin-right:15px;float:right;width:400px;">
				<c:forEach items="${pbSections }" var="pbsection" >
					<option value="${pbsection.code }">${pbsection.name }</option>
				</c:forEach>
			</select>
			<button id="print" type="button" class="btn btn-info btn-sm" style="float:right;margin-right:15px;" onclick='pbprint()'><fmt:message key='audit.print'/></button>
		</div>

<c:choose>
	<c:when test="${size == 0}">
	<div style="margin-top:70px;font-size:25px;">
		<p><b><fmt:message key="pb.nodata"/></b></p>
	</div>
	</c:when>
	<c:otherwise>
		
		<div class = "col-sm-12  data" style="margin-top:20px;">
			<input id="pbtext" value="${pbdate}" type="hidden"/>
			<table id="pbdata" class=" table-hover" style="font-size:12px;text-align:center;" border="1px;">
			</table>
			<input id="html" value="${html }" type="hidden"/>
			<div class="col-sm-12" id="daypb"></div>
			<%-- <label for="bz" style="margin-top:10px;"><fmt:message key="patient.note"/></label>
			<input type="text" id="bz" name="bz" class="span2" style="margin-left:20px; width:800px;" value="${bz}"> --%>
		</div>
	</c:otherwise>
</c:choose>



