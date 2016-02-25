<%@ include file="/common/taglibs.jsp"%>

<head>
	<title><fmt:message key="menu.pb" /></title>
	<script type="text/javascript" src="<c:url value='../scripts/pb/sxpb.js'/> "></script>
	
	<script type="text/javascript" src="<c:url value='/scripts/jquery.jqGrid.min.js'/> "></script>
	<link rel="stylesheet" type="text/css" href="<c:url value='/styles/ui.jqgrid.css'/> " />
	<link rel="stylesheet" type="text/css"  href="<c:url value='/styles/jquery-ui.min.css'/>" />
	<link rel="stylesheet" type="text/css"  href="<c:url value='/styles/bootstrap.min.css'/>" />
	
	<script type="text/javascript" src="../scripts/jquery-2.1.4.min.js"></script>
	<script type="text/javascript" src="../scripts/bootstrap.min.js"></script>
    <script type="text/javascript" src="../scripts/jquery-ui.min.js"></script>
    <script type="text/javascript" src="../scripts/i18n/grid.locale-cn.js"></script>

<style>
span {
border: solid 1px #cfcfcf;
cursor: pointer;
position: relative;
font-size:12px;
margin-bottom:1px;
margin-top:1px;
width: 75px;
height: 25px;
padding: 0;
list-style-type: none;
}
#head  th  {
	width:40px;
	height:24px;
	text-align:center;
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
div .fixed{ 
overflow-y: scroll; 
overflow-x: hidden;
width:auto;
height: auto; 
border: 0px solid #009933; 
} 
div .data{ 
overflow-y: scroll; 
overflow-x: hidden;
width:auto;
height: 480px; 
border: 0px solid #009933; 
} 

.ui-datepicker-calendar { 
display: none; 
} 


</style>
</head>
<input id="section" value="${section }" type="hidden"/>
<input id="month" value="${month }" type="hidden"/>


		<div class="form-inline" style="width:1024x;">
			<input type="text" id="date" class="form-control" sytle="width:50px;">
			<button id="changeMonth" class="btn btn-info form-control" style="margin-left:10px;"><fmt:message key='pb.changemonth' /></button>
			<button id="shiftBtn" class="btn btn-success form-control"><fmt:message key='button.submit' /></button>
			
			
		</div>
		<div id="shiftSelect" class="checkbox form-inline" >
			<c:forEach items="${wshifts}" var="shift">
				<div class="form-control" style="width:110px;padding:1px 2px;height:25px;margin-bottom: 1px;"><label >
      				<input type="checkbox" name="${shift.key }" value="${shift.key }"> ${shift.value } 
    			</label></div>
			</c:forEach>
			
		</div>
		
		
		<%-- div class="fixed">
		<input id="test" value="${arrString}" type="hidden"/>
		<table id="pbhead" class="table" style="margin-top:10px;margin-bottom:0px;font-size:12px;text-align:center;" border="1px;">
		<thead id="head">
		</thead>
		</table>
		</div> --%>
		<div class="fixed data">
		<input id="test1" value="${pbdate}" type="hidden"/>
		<table id="pbdata" class="table table-hover" style="font-size:12px;text-align:center;" border="1px;">
		
		</table>
		

