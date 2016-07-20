<%@ include file="/common/taglibs.jsp"%>

<head>
	<title><fmt:message key="menu.pb.sxcx" /></title>
	<script type="text/javascript" src="<c:url value='../scripts/pb/sxcx.js'/> "></script>
	
	<link rel="stylesheet" type="text/css" href="<c:url value='/styles/ui.jqgrid.css'/> " />
	<link rel="stylesheet" type="text/css"  href="<c:url value='/styles/jquery-ui.min.css'/>" />
	<link rel="stylesheet" type="text/css"  href="<c:url value='/styles/bootstrap.min.css'/>" />
	
	<script type="text/javascript" src="../scripts/jquery-2.1.4.min.js"></script>
	<script type="text/javascript" src="../scripts/bootstrap.min.js"></script>
    <script type="text/javascript" src="../scripts/jquery-ui.min.js"></script>
    <script type="text/javascript" src="../scripts/i18n/grid.locale-cn.js"></script>
    <script type="text/javascript" src="../scripts/jquery.jqGrid.js"></script>
    
	<script type="text/javascript" src="../scripts/jquery.tablednd_0_5.js"></script>
    <script type="text/javascript" src="../scripts/jquery.validate.js"></script>
    <script type="text/javascript" src="../scripts/raphael.2.1.0.min.js"></script>
    <script type="text/javascript" src="../scripts/justgage.1.0.1.min.js"></script>
    <script type="text/javascript" src="../scripts/jquery.form.js"></script>
    

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
	width:40px;
	height:24px;
	text-align:center;
}
table tr td {
	width:40px;
	height:24px;
}

div .data{ 
height: 720px; 
border: 0px solid #009933; 
} 

.ui-datepicker-calendar { 
display: none; 
} 


</style>
</head>
<input id="section" value="${section }" type="hidden"/>
<input id="month" value="${month }" type="hidden"/>
<input id="page" value="${page }" type="hidden"/>
<input id="pages" value="${pages }" type="hidden"/>


		<div class="form-inline" style="width:1024x;">
			<label for="from" style="margin-left : 20px;"><b><fmt:message key="from" /></b></label>
			<input type="text" id="from" name="from" class="form-control"  value="${from }"/>
			<label for="to" style="margin-left : 10px;" ><b><fmt:message key="to" /></b></label>
			<input type="text" id="to" name="to" class="form-control" value="${to }">
			
			<button id="changeMonth" class="btn btn-info form-control" style="margin-left:10px;"><fmt:message key='pb.changemonth' /></button>
			
			<div class="form-inline" style="float:right;">
			<button id="ksCount" class="btn btn-success btn-sm" ><fmt:message key='pb.kscount' /></button>
			<button id="stuPb" class="btn btn-success btn-sm" style="margin-right:20px;" ><fmt:message key='pb.stuPb' /></button>
			<button id="preBtn" class="btn btn-success btn-sm" ><fmt:message key='pb.pre' /></button>
			<button id="nextBtn" class="btn btn-success btn-sm"><fmt:message key='pb.next' /></button>
			<span  id="pagelist"></span>
			</div>
			
		</div>
		
		

	
<div class="col-sm-12" style="margin-top:20px;">	
	<div class = "col-sm-2">
		<div id="hisdataPanal">
			<table id='hisList'></table>
			<div id="pager"></div>
		</div>
	</div>
	
	<div class = "col-sm-10">
		<div class="fixed data" style=" ">
		<input id="test1" value="${pbdate}" type="hidden"/>
		<table id="pbdata" class=" table-hover" style="font-size:12px;text-align:center;" border="1px;">
		
		</table>
		</div>
	</div>	
		
		
</div>	
