<%@ include file="/common/taglibs.jsp"%>

<head>
	<title><fmt:message key="menu.pb" /></title>
	<script type="text/javascript" src="../scripts/pb/kscount.js"></script>
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
	width:100px;
	height:24px;
}

div .data{ 
border: 0px solid #009933; 
} 

.ui-datepicker-calendar { 
display: none; 
} 


</style>  
    
</head>

<input id="weeks" type="hidden" value="${weeks }" />

<div class="form-inline" style="width:90%;margin-left:50px;">
			<label for="from" style="margin-left : 20px;"><b><fmt:message key="from" /></b></label>
			<input type="text" id="from" name="from" class="form-control"  value="${from }"/>
			<label for="to" style="margin-left : 10px;" ><b><fmt:message key="to" /></b></label>
			<input type="text" id="to" name="to" class="form-control" value="${to }">
			
			<button id="changeMonth" class="btn btn-info form-control" style="margin-left:10px;"><fmt:message key='pb.changemonth' /></button>
			
			<button id="print" type="button" class="btn btn-info btn-sm" style="float:right;margin-right:15px;" onclick='return print()'><fmt:message key='audit.print'/></button>
</div>

<div id="weekSelect" class="form-inline" style="margin-top:10px;">		
</div>

	<div style="margin-top:20px;font-size:50px;">
		<p><b><fmt:message key="pb.kscount.detail"/></b></p>
	</div>

<div class = "col-sm-12  data" style="margin-top:20px; margin-left:50px;">
	<input id="pbtext" value="${pbdate}" type="hidden"/>
	<table id="pbdata" class=" table-hover" style="font-size:12px;text-align:center;width:90%" border="1px;">
	</table>

</div>

<div id="footer2" class="col-sm-12" style="padding-top:20px;">
		<hr style="width:100%;height:1px;border:none;border-top:1px solid #555555; "/>
        <span class="col-sm-6" >
        	<fmt:message key="project.name"/> | 
        	<fmt:message key="webapp.version"/>
            <c:if test="${pageContext.request.remoteUser != null}">
            | <fmt:message key="user.status"/> ${pageContext.request.remoteUser}
            </c:if>
            | <a target="_blank" href="<fmt:message key="update.url"/>"><fmt:message key="update.name"/></a>
       	    | <a target="_blank" href="<fmt:message key="wsdjk.url"/>"><fmt:message key="wsdjk.name"/></a>
   		</span>
   		<span class="col-sm-2" style="float:right;">
       		 &copy; <fmt:message key="copyright.year"/> <a target="_blank" href="<fmt:message key="company.url"/>"><fmt:message key="company.name"/></a>
        </span>
        
        
</div>
