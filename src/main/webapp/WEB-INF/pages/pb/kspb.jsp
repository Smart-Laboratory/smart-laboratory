<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp"%>

<head>
	<title><fmt:message key="menu.pb" /></title>
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

table tr td {
	width:48px;
	height:30px;
}
table tr th  {
	width:48px ;
	height:30px;
	padding:0px 1px ;
}
#day0{
	width:48px important; 
}

#nmshow{
	width: 60px;
}

div .fixed{ 
overflow-y: scroll; 
overflow-x: hidden;
height: 550px; 
border: 0px solid #009933; 
} 
div .title{ 
overflow-y: scroll; 
overflow-x: hidden;
height: auto; 
border: 0px solid #009933; 
} 
</style>

<script type="text/javascript">

function randomShift(day) {
	var week = $("#day" + day).html().substr(-1);
	var shift = $("#" + week).html();
	var array = shift.split(",");
	array.sort(randomsort);
	var alen = $("select[name='select"+ day +"']").toArray().length - array.length;
    var i = 0;
    var arand = parseInt(Math.random()*(alen));
    $("select[name='select"+ day +"']").each(function(){
    	$(this).val("");
    	if (i >= arand-1) {
    		$(this).val(array[i-arand]);
    	}
		i++;
    });
}

function randomsort(a, b) {
	return Math.random()>.5 ? -1 : 1;
}


function checkShift(day) {
	return true;
}

function changeType(select) {
	if(select.value == 8){
		window.location.href="../pb/sxpb?month=" + $("#date").val();
	}else{
		window.location.href="../pb/pb?section="+$("#jykCode").val()+"&type=" + select.value+"&date=" + $("#date").val();
	}
	
}

$(function() {
	$("#kstable").html($("#tabledata").val());
	$("#labText").html('${sectionStr}');
	
	$("#ksTitle").html($("#tableTitle").val());
	
	$("#date").datepicker({
		changeMonth: true,
	    changeYear: true,
		dateFormat: 'yy-mm',
		monthNamesShort: ['1\u6708','2\u6708','3\u6708','4\u6708','5\u6708','6\u6708','7\u6708','8\u6708','9\u6708','10\u6708','11\u6708','12\u6708'],
		dayNamesMin: ['\u65e5','\u4e00','\u4e8c','\u4e09','\u56db','\u4e94','\u516d']
	});
	
	$("#reDialog").dialog({
		autoOpen: false,
		resizable: false,
		modal:true,
	    width: 180,
	    height: 240
	});
	
	
	$("#shiftBtn").click(function() {
		var ischecked = true;
		if (ischecked) {
			var type = $("#typeSel").val();
			var text = "";
			var date = $("#month").val();
			
			$("td[name^='td']").each(function(i){
				var array = $(this).attr("id").split("-");
				var day = "";
				if(array[1].length == 1) {
					day = '0' + array[1];
				} else {
					day = array[1];
				}
				
				var value = $(this).html();
				if($(this).attr("class").indexOf("gx")>=0)
					value += "公休;";
				if($(this).attr("class").indexOf("rx")>=0)
					value += "日休;";
				text = text + array[0] + ":" + date + "-" + day + ":" + value  +",";
			});
			$.post("../pb/pb/kssubmit",{text:text,type:type,date:date},function(data) {
				alert("success!");
				window.location.href="../pb/pb?date=" + $("#date").val()+"&section=" + $("#section").val()+"&type=" + type;
			});
			
		}
	});
	
	$("#typeSel").val("${type}");
	
	$("#kstable tr td").click(function(){
		var id=this.id;
		
		
		var name = $("#"+id).attr("name");
		var shifts=$("#"+id).html();
		$.each($("#shiftSelect input"),function(n,v){
			if(v.checked){
				if(v.value == "公休"){
					if($("#"+id).attr("class").indexOf("gx")>=0){
						$("td[name='"+name+"']").css("background","#FFF");
						$("td[name='"+name+"']").removeClass("gx");
					}else{
						$("td[name='"+name+"']").css("background","#FDFF7F");
						$("td[name='"+name+"']").addClass("gx");
						$("td[name='"+name+"']").removeClass("rx");
					}
					
				}else if(v.value == "日休"){
					if($("#"+id).attr("class").indexOf("rx")>=0){
						$("td[name='"+name+"']").css("background","#FFF");
						$("td[name='"+name+"']").removeClass("rx");
					}else{
						$("td[name='"+name+"']").css("background","#7CFC00");
						$("td[name='"+name+"']").addClass("rx");
						$("td[name='"+name+"']").removeClass("gx");
					}
					
				}else if(shifts.indexOf(v.value)>=0){
					shifts=shifts.replace(v.value+";","");
				}else{
					shifts = shifts + v.value+";";
				}
			}
		});
		$("#"+id).html(shifts);
		
	});
	$("#changeMonth").click(function() {
		window.location.href="../pb/pb?date=" + $("#date").val()+"&section=" + $("#section").val()+"&type=" + $("#typeSel").val();
	});
	
	$("#date").val($("#month").val());
	
	$("#publish").click(function(){
		$.post("../pb/pb/publish",{section:$("#section").val(), month:$("#date").val(),state:0},function(data){
			if(data){
				alert("Publish seccess!");
			}
		})
	});
	
	labChange=function(select){
		$.ajax({
			  type: 'POST',
			  url: "../audit/labChange?lab="+$(select).children().attr("title"),
			  success:function(){
				  $("#labText").html($(select).children().html());
				  window.location.href="../pb/pb?section=" + $(select).children().attr("title")+"&date=" + $("#date").val();
			  }	
		});
		
	}
});
</script>
</head>
<input id="jykCode" value="${jykCode }" type="hidden"/>
<input id="section" value="${section }" type="hidden"/>
<input id="month" value="${month }" type="hidden"/>


<div class="form-inline" style="">
			<input type="text" id="date" class="form-control" sytle="width:50px;">
			<button id="changeMonth" class="btn btn-info form-control" style="margin-left:10px;"><fmt:message key='pb.changemonth' /></button>
			<select id='typeSel' class="form-control" style="margin-bottom:5px;margin-right:15px;float:left;width:100px;" onchange="changeType(this)">
				<option value="1" ><fmt:message key="pb.yb"/></option>
				<option value="2" ><fmt:message key="pb.lz"/></option>
				<option value="3" ><fmt:message key="pb.wc"/></option>
				<option value="4" ><fmt:message key="pb.ybb"/></option>
				<option value="5" ><fmt:message key="pb.hcy"/></option>
				<option value="6" ><fmt:message key="pb.ry"/></option>
				<option value="7" ><fmt:message key="pb.jjr"/></option>
				<option value="8" ><fmt:message key="pb.ssx"/></option>
			</select>
			<button id="shiftBtn" class="btn btn-success form-control"><fmt:message key='button.submit' /></button>
			<button id="publish" class="btn btn-danger form-control"><fmt:message key='button.publish' /></button>
			
</div>
<div id="shiftSelect" class="checkbox form-inline">
			<c:forEach items="${wshifts }" var="shift">
				<div class="form-control" style="width:110px;padding:1px 2px;height:25px;margin-bottom: 1px;"><label >
      				<input type="checkbox" name="${shift.key }" value="${shift.key }"> ${shift.value } 
    			</label></div>
			</c:forEach>
</div>
<div class="title">
<input id="tableTitle" value="${arrTitle }" type="hidden" />
<table id="ksTitle" class=" " style="margin-bottom:0px;font-size:12px;text-align:center;margin-top:5px;" border="1px;">

</table>
</div>
<div class="fixed">
<input id="tabledata" value="${arrString }" type="hidden" />
<table id="kstable" class=" " style="font-size:12px;text-align:center;table-layout:fixed;;" border="1px;">

</table>
</div>
<div style="display:none;">	
	<c:forEach var="dsh" items="${dshList}">
		<div id='<c:out value="${dsh.day}"/>'><c:out value="${dsh.shift}"/></div>
	</c:forEach>
</div>

