<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp"%>

<head>
	<title><fmt:message key="menu.pb" /></title>
	<script type="text/javascript" src="<c:url value='../scripts/pb/pb.js'/> "></script>
	
	<script type="text/javascript" src="<c:url value='/scripts/jquery.jqGrid.min.js'/> "></script>
	<link rel="stylesheet" type="text/css" href="<c:url value='/styles/ui.jqgrid.css'/> " />
	<link rel="stylesheet" type="text/css"  href="<c:url value='/styles/jquery-ui.min.css'/>" />
	<link rel="stylesheet" type="text/css"  href="<c:url value='/styles/bootstrap.min.css'/>" />
	
	<script type="text/javascript" src="../scripts/jquery-2.1.4.min.js"></script>
	<script type="text/javascript" src="../scripts/bootstrap.min.js"></script>
    <script type="text/javascript" src="../scripts/jquery-ui.min.js"></script>
    <script type="text/javascript" src="../scripts/i18n/grid.locale-cn.js"></script>

<style>
tr:hover{
	background:#87cefa;
}

table tr th {
	width:50px;
	height:24px;
	text-align:center;
	padding:0px 0px  !important;
}
table tr td {
	width:50px;
	height:24px;
	padding:0px 0px !important;
}

div .fixed{ 
overflow-y: scroll; 
overflow-x: hidden;
height: auto; 
border: 0px solid #009933; 
} 

div .data{ 
overflow-y: scroll; 
overflow-x: hidden;
height: 550px; 
border: 0px solid #009933; 
} 



.ui-datepicker-calendar { 
display: none; 
} 

/* table .sx{
	background:#00F1FF!important;
} */

</style>
</head>
<input id="section" value="${section }" type="hidden"/>
<input id="month" value="${month }" type="hidden"/>


		<div class="form-inline" style="width:1024x;">
			<input type="text" id="date" class="form-control" sytle="width:50px;">
			<button id="changeMonth" class="btn btn-info form-control" style="margin-left:10px;"><fmt:message key='pb.changemonth' /></button>
			<button id="shiftBtn2" class="btn btn-success form-control"><fmt:message key='button.count' /></button>
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
		
		
		<div class="fixed">
		<input id="test" value="${arrString}" type="hidden"/>
		<table id="pbhead" style="margin-top:10px;margin-bottom:0px;font-size:12px;width:100%;text-align:center;" border="1px;">
		
		
		</table>
		</div>
		<div class="data">
		<input id="test1" value="${arrBodyString}" type="hidden"/>
		<table id="pbdata" style="font-size:12px;text-align:center;width:100%;" border="1px;">
		
		</table>
		<label for="bz" style="margin-top:10px;"><fmt:message key="patient.note"/></label>
		<input type="text" id="bz" name="bz" class="span2" style="margin-left:20px; width:800px;" value="${bz}">
		</div>
		<div style="display:none;">	
			<c:forEach var="dsh" items="${dshList}">
				<div id='<c:out value="${dsh.day}"/>'><c:out value="${dsh.shift}"/></div>
			</c:forEach>
		</div>

