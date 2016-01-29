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

table tr td {
	width:30px;
	height:30px;
}
table tr th  {
	width:40px ;
	height:30px;
	padding:0px 1px ;
}
#day0{
	width:45px important; 
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
function labChange(item){
	window.location.href="../pb/pb?section=" + $(item).val();
}

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
	window.location.href="../pb/pb?section=1300000&type=" + select.value;
}

$(function() {
	$("#kstable").html($("#tabledata").val());
	$("#labSelect").val($("#section").val());
	
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
			
			$("td[name^='td']").each(function(i){
				var array = $(this).attr("id").split("-");
				var day = "";
				if(array[1].length == 1) {
					day = '0' + array[1];
				} else {
					day = array[1];
				}
				var date = $("#month").val();
				var value = $(this).html();
					text = text + array[0] + ":" + date + "-" + day + ":" + value  +",";
			});
			$.post("../pb/pb/kssubmit",{text:text,type:type},function(data) {
				alert("success!");
				window.location.href="../pb/pb?date=" + $("#date").val()+"&section=" + $("#section").val();
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
				if(v.value == "\u516C\u4F11"){
					name = name.replace("td","");
					$("td[name$='"+name+"']").css("background","#FDFF7F");
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
		window.location.href="../pb/pb?date=" + $("#date").val()+"&section=" + $("#section").val();
	});
	
	$("#date").val($("#month").val());
});
</script>
</head>
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
				<option value="5" ><fmt:message key="pb.ry"/></option>
				<option value="6" ><fmt:message key="pb.hcy"/></option>
				<option value="7" ><fmt:message key="pb.jjr"/></option>
			</select>
			<button id="shiftBtn" class="btn btn-success form-control"><fmt:message key='button.submit' /></button>
			
			
			<select id="labSelect" onchange="labChange(this)" class="form-control" style="margin-bottom:5px;float:right;width:400px;">
				<span ><c:forEach var="depart" items="${departList}">
				<option value='<c:out value="${depart.key}" />'><c:out value="${depart.value}" /></option>
			</c:forEach></span>
			</select>
</div>
<div id="shiftSelect" class="checkbox">
			<c:forEach items="${wshifts }" var="shift">
				<label>
      				<input type="checkbox" name="${shift.key }" value="${shift.key }"> ${shift.value } 
    			</label>
			</c:forEach>
</div>
<div class="title">
<input id="tableTitle" value="${arrTitle }" type="hidden" />
<table id="ksTitle" class=" table-hover" style="margin-bottom:0px;font-size:12px;text-align:center;margin-top:5px;" border="1px;">

</table>
</div>
<div class="fixed">
<input id="tabledata" value="${arrString }" type="hidden" />
<table id="kstable" class=" table-hover" style="font-size:12px;text-align:center;table-layout:fixed;;" border="1px;">

</table>
</div>
<div style="display:none;">	
	<c:forEach var="dsh" items="${dshList}">
		<div id='<c:out value="${dsh.day}"/>'><c:out value="${dsh.shift}"/></div>
	</c:forEach>
</div>

