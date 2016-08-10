<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp"%>

<head>
	<title><fmt:message key="menu.pb" /></title>
	<script type="text/javascript" src="<c:url value='/scripts/jquery.jqGrid.min.js'/> "></script>
	<link rel="stylesheet" type="text/css" href="<c:url value='/styles/ui.jqgrid.css'/> " />
	<link rel="stylesheet" type="text/css"  href="<c:url value='/styles/jquery-ui.min.css'/>" />
	<link rel="stylesheet" type="text/css"  href="<c:url value='/styles/bootstrap.min.css'/>" />
	<link rel="stylesheet" type="text/css"  href="<c:url value="/styles/bootstrap-duallistbox.min.css"/>"/>
	
	<script type="text/javascript" src="../scripts/jquery-2.1.4.min.js"></script>
	<script type="text/javascript" src="../scripts/bootstrap.min.js"></script>
    <script type="text/javascript" src="../scripts/jquery-ui.min.js"></script>
    <script type="text/javascript" src="../scripts/i18n/grid.locale-cn.js"></script>
    <script type="text/javascript" src="<c:url value="/scripts/jquery.bootstrap-duallistbox.min.js"/>"></script>

<style>
</style>
</head>

<input type="hidden" id="selperson" /> 
<input type="hidden" id="weeknum" value="${week }" /> 
<input id="section" value="${section }" type="hidden"/>
<input id="month" value="${month }" type="hidden"/>

<div class="form-inline" style="width:1024x;">
	<input type="text" id="date" class="form-control" sytle="width:50px;">
	<button id="changeMonth" class="btn btn-info form-control" style="margin-left:10px;"><fmt:message key='pb.changemonth' /></button>
	<button id="print" type="button" class="btn btn-info btn-sm" style=" margin-left:15px;" onclick='preview1()'><fmt:message key='audit.print'/></button>
</div>

<div id="weekSelect" class="form-inline" >		
</div>
<c:choose>
	<c:when test="${size == 0}">
	<div style="margin-top:70px;font-size:25px;">
		<h2><b>该周没有排班记录</b></h2>
	</div>
	</c:when>
	<c:otherwise>
		<button id="print" type="button" class="btn btn-info" style="float:right;margin-top:-20px; margin-right:15px;" onclick='preview1()'><fmt:message key='audit.print'/></button>
		<!--startprint-->
		<div class="fixed">
			<h2 style="text-align:center;"><b>${sectionStr} ${month } 第 ${week } 周排班 </b></h2>
			<input id="test" value="${arrString}" type="hidden"/>
			<table id="pbhead" class="table" style="margin-top:10px;margin-bottom:0px;font-size:12px;text-align:center;width:90%;" border="1px;">
			
			
			</table>
		</div>
		<!--endprint-->
	</c:otherwise>
</c:choose>



		
<script type="text/javascript">
function preview1() {
	bdhtml=window.document.body.innerHTML;
	sprnstr="<!--startprint-->"; //开始打印的地方
	eprnstr="<!--endprint-->";  //结束打印标记
	prnhtml=bdhtml.substr(bdhtml.indexOf(sprnstr)+17);
	prnhtml=prnhtml.substring(0,prnhtml.indexOf(eprnstr));
	window.document.body.innerHTML=prnhtml;
	window.print();
}

labChange=function(select){
	$.ajax({
		  type: 'POST',
		  url: "../audit/labChange?lab="+$(select).children().attr("title"),
		  success:function(data){

			  var section = $(select).children().attr("title");
			  if(section == '1320511'){
				  window.location.href="../pb/bpbcx?date=" + $("#date").val()+"&section=" + section;
			  }
			  $("#labText").html($(select).children().html());
			  window.location.href="../pb/pbcx?date=" + $("#date").val()+"&section=" + $(select).children().attr("title");
		  }
	});
	
};
	 
	 function getdata(item){
		 var week = $(item).attr("name");
		 window.location.href="../pb/bpbcx?week=" + week+"&date=" + $("#date").val()+"&section=" + $("#section").val();
	 }
	
	$(function(){
		$("#pbhead").html($("#test").val());
		$("#labSelect").val($("#section").val());
		$("#selperson").val("${selperson}");
		
		
		$("#date").datepicker({
			changeMonth: true,
		    changeYear: true,
			dateFormat: 'yy-mm',
			monthNamesShort: ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'],
			dayNamesMin: ['一','二','三','四','五','六','日'],
			showButtonPanel: true, 
			onClose: function(dateText, inst) { 
			var month = $("#ui-datepicker-div .ui-datepicker-month :selected").val(); 
			var year = $("#ui-datepicker-div .ui-datepicker-year :selected").val(); 
			$(this).datepicker('setDate', new Date(year, month, 1)); 
			} 
		});
		
		$("#changeMonth").click(function() {
			$.get("../pb/bpb/ajax/getWeek",{date:$("#date").val()},function(data){
				if(data!=null){
					$("#weekSelect").html(data);
				}
			});
		});
		$("#date").val("${month}");
		if($("#date").val()==null || $("#date").val()==''){
			$("#date").val(new Date().Format("yyyy-MM"));
		}
		
	});

	
Date.prototype.Format = function(fmt)   
{ //author: meizz   
  var o = {   
    "M+" : this.getMonth()+1,                 //月份   
    "d+" : this.getDate(),                    //日   
    "h+" : this.getHours(),                   //小时   
    "m+" : this.getMinutes(),                 //分   
    "s+" : this.getSeconds(),                 //秒   
    "q+" : Math.floor((this.getMonth()+3)/3), //季度   
    "S"  : this.getMilliseconds()             //毫秒   
  };   
  if(/(y+)/.test(fmt))   
    fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));   
  for(var k in o)   
    if(new RegExp("("+ k +")").test(fmt))   
  fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));   
  return fmt;   
}  
	

</script>
