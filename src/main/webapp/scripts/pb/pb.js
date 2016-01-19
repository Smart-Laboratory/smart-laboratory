function unescape(str) {
    var elem = document.createElement('div');
    elem.innerHTML = str;
    document.write("111111111");
    return elem.innerText || elem.textContent;
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
	var date = $("#day0").html();
	var week = $("#day" + day).html().substr(-1);
	var shift = $("#" + week).html();
	var array = shift.split(",");
	$("select[name='select"+ day +"']").each(function(){
		for(var j = 0;j < array.length; j++) {
			if(array[j] == $(this).val()) {
				array.splice(j,1);
			}
		}
	});
	if (array.length > 0) {
		alert(date + "-" + day + " 缺少" + array.join(" ") + "等班次！");
		return false;
	}
	return true;
}

function changeType(select) {
	window.location.href="../pb/pb?type=" + select.value;
}

$(function() {
	
	$("#date").datepicker({
		changeMonth: true,
	    changeYear: true,
		dateFormat: 'yy-mm',
		monthNamesShort: ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'],
		dayNamesMin: ['一','二','三','四','五','六','日']
	});
	
	$("#dialog").dialog({
		autoOpen: true,
		resizable: false,
		modal:true,
	    width: 300,
	    height: 160
	});
	
	$("#btn").click(function() {
		$("#dialog").dialog("close");
		window.location.href="../pb/pb?section=" + $("#section").val();
	});
	
	$("#shiftBtn").click(function() {
		var ischecked = true;
		if (ischecked) {
			var text = "";
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
				var ismid = '0';
				var pm = "";
				if ($("#check" + $(this).attr("id")).length>0) {
					if ($("#check" + $(this).attr("id")).is(':checked')) {
						ismid = '1';
					}
				}
				if ($("#pm" + $(this).attr("id")).length>0) {
					pm = $("#pm" + $(this).attr("id")).val();
				}
				if(value != "" || pm != "") {
					text = text + array[0] + ":" + date + "-" + day + ":" + value + ":" + ismid + ":" + pm +";";
				}
			});
			$.post("../pb/pb/submit",{text:text},function(data) {
				window.location.href="../pb/pbcx";
			});
		}
	});
	
	$("#shiftBtn2").click(function() {
		var ischecked = true;
		if (ischecked) {
			var text = "";
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
				var ismid = '0';
				var pm = "";
				if ($("#check" + $(this).attr("id")).length>0) {
					if ($("#check" + $(this).attr("id")).is(':checked')) {
						ismid = '1';
					}
				}
				if ($("#pm" + $(this).attr("id")).length>0) {
					pm = $("#pm" + $(this).attr("id")).val();
				}
				if(value != "" || pm != "") {
					text = text + array[0] + ":" + date + "-" + day + ":" + value + ":" + ismid + ":" + pm +";";
				}
			});
			$.post("../pb/pb/submit",{text:text},function(data) {
				window.location.href="../pb/pb?date=" + $("#date").val();
			});
		}
	});
	
	$("#changeMonth").click(function() {
		window.location.href="../pb/pb?date=" + $("#date").val();
	});
	
	$("#date").val('${month}');
});