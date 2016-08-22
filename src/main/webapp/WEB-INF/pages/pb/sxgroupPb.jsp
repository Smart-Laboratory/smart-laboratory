<%@ include file="/common/taglibs.jsp"%>

<head>
	<title><fmt:message key="menu.pb" /></title>
	<script type="text/javascript" src="../scripts/pb/sxgroupPb.js"></script>
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
	display: block;
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
	width:40px;
	height:24px;
	text-align:center;
}
table tr td {
	width:40px;
	height:24px;
}

div .data{ 
height:500px;
border: 0px solid #009933; 
} 

.ui-datepicker-calendar { 
display: none; 
} 

table td.sx{
	background: #00E6FF!important;
}

</style>  
    
</head>

<input id="section" value="${section }" type="hidden"/>
<input id="month" value="${month }" type="hidden"/>
<input id="view" value="${view }" type="hidden"/>

		<div class="form-inline" style="width:1024x;">
			<input type="text" id="date" class="form-control" sytle="width:50px;">
			<button id="changeMonth" class="btn btn-info form-control" style="margin-left:10px;"><fmt:message key='pb.changemonth' /></button>
			<button id="shiftBtn" class="btn btn-success form-control"><fmt:message key='button.submit' /></button>
			<button id="publish" class="btn btn-danger form-control"><fmt:message key='button.publish' /></button>
			
			
			
		</div>

		<div id="shiftSelect" class="checkbox form-inline" >
			<c:forEach items="${wshifts}" var="shift">
				<div class="form-control" style="width:110px;padding:1px 2px;height:25px;margin-bottom: 1px;"><label >
      				<input type="checkbox" name="${shift.key }" value="${shift.key }"> ${shift.value } 
    			</label></div>
			</c:forEach>
			
		</div>
		

<c:choose>
	<c:when test="${size == 0}">
	<div style="margin-top:70px;font-size:25px;">
		<p><b><fmt:message key="pb.nodata"/></b></p>
	</div>
	</c:when>
	<c:otherwise>
		<button id="print" type="button" class="btn btn-info" style="float:right;margin-top:-20px; margin-right:15px;" onclick='window.print()'><fmt:message key='audit.print'/></button>
		<div class = "col-sm-12  data" style="margin-top:20px;">
			<input id="pbtext" value="${pbdate}" type="hidden"/>
			<table id="pbdata" class=" table-hover" style="font-size:12px;text-align:center;" border="1px;">
			</table>
			<label for="bz" style="margin-top:10px;"><fmt:message key="patient.note"/></label>
			<input type="text" id="bz" name="bz" class="span2" style="margin-left:20px; width:800px;" value="${bz}">
		</div>
	</c:otherwise>
</c:choose>



