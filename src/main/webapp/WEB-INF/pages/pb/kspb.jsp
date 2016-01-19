<%@ include file="/common/taglibs.jsp"%>

<head>
<title><fmt:message key="menu.pb" /></title>
<script type="text/javascript" src="<c:url value='/scripts/grid.locale-cn.js'/> "></script>

<style>
.day{
border: solid 1px #cfcfcf;
cursor: pointer;
position: relative;
font-size:12px;
margin-bottom:1px;
margin-top:1px;
width: 50px;
height: 25px;
padding: 0;
list-style-type: none;
}
table tr td {
	width:50px;
}
table tr th {
	width:50px;
}
div .fixed{ 
overflow-y: scroll; 
overflow-x: hidden;
width:auto;
height: 480px; 
border: 0px solid #009933; 
} 
input[type="checkbox"] {
    margin: 0 0 0;
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
	window.location.href="<c:url value='/pb/pb'/>?type=" + select.value;
}

$(function() {
	$("#date").datepicker({
		changeMonth: true,
	    changeYear: true,
		dateFormat: 'yy-mm-dd',
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
	
	$("#repeatBtn").click(function() {
		$("#reDialog").dialog("open");
	});
	
	$("#arrBtn").click(function() {
		var date = $("#date").val();
		var name = $("#name").val();
		var shift = $("#shift").val();
		$.post("<c:url value='/pb/pb/resubmit'/>",{date:date,name:name,shift:shift},function(data) {
			$("#reDialog").dialog("close");
		});
	});
	
	$("#shiftBtn").click(function() {
		var ischecked = true;
		/* var dateSize = <c:out value="${dateSize}"/>;
		for (var i = 1; i <= dateSize; i++) {
			if(!checkShift(i)) {
				ischecked = false;
			}
		} */
		if (ischecked) {
			var text = "";
			var type = $("#typeSel").val();
			$("select[name^='select']").each(function(i){
				var array = $(this).attr("id").split("-");
				var day = "";
				if(array[1].length == 1) {
					day = '0' + array[1];
				} else {
					day = array[1];
				}
				var date = '${month}';
				var value = $(this).val();
				if(value != "") {
					text = text + array[0] + ":" + date + "-" + day + ":" + value +";";
				}
			});
			$.post("<c:url value='/pb/pb/kssubmit'/>",{text:text,type:type},function(data) {
				window.location.href="<c:url value='/pb/pbcx'/>";
			});
		}
	});
	
	$("#typeSel").val("${type}");
	
});
</script>
</head>
<button id="shiftBtn" class="btn btn-success" style="margin-left:20px;"><fmt:message key='button.submit' /></button>

<button id="repeatBtn" class="btn btn-info" style="margin-left:5px;width:120px;"><fmt:message key='pb.repeat' /></button>

<select id='typeSel' style="margin-bottom:5px;float:left;height:30px;width:100px;" onchange="changeType(this)">
	<option value="1" ><fmt:message key="pb.yb"/></option>
	<option value="2" ><fmt:message key="pb.lz"/></option>
	<option value="3" ><fmt:message key="pb.wc"/></option>
	<option value="4" ><fmt:message key="pb.ybb"/></option>
</select>

<table style="font-size:12px;text-align:center;margin-top:5px;" border="1px;">
<c:forEach items="${arrArray}" var="arr" varStatus="status">
	<tr>
		<c:forEach items="${arr}" var="shift">${shift}</c:forEach>
	</tr>
</c:forEach>
</table>

<div style="display:none;">	
	<c:forEach var="dsh" items="${dshList}">
		<div id='<c:out value="${dsh.day}"/>'><c:out value="${dsh.shift}"/></div>
	</c:forEach>
</div>

<div id="reDialog" title="<fmt:message key='pb.repeat' />" style="text-align:left;">
	<div>
		<span class="label label-info"><fmt:message key="current.dateTime" /></span>
		<input type="text" style="height:20px;width:100px;" id="date"></input>
		<span class="label label-info"><fmt:message key="patients.patientName" /></span>
		<input type="text" style="height:20px;width:100px;" id="name"></input>
		<span class="label label-info"><fmt:message key="pb.shift" /></span>
		<input type="text" style="height:20px;width:100px;" id="shift"></input>
	</div>
	<div>
		<button id="arrBtn" class="btn btn-success" style="margin-left:65px;"><fmt:message key='button.submit' /></button>
	</div>
</div>
