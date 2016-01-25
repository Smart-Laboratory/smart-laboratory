

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

function getHoliday(){
	var section = $("#section").val();
	$.get("../pb/pb/getholiday",{section:1300600},function(data){
		alert(11);
		for(var i=0;i<data.length;i++){
			var name = data[i].name;
			var holiday = data[i].holiday;
			alert(name + holiday);
		}
	});
}
$(function() {
	$("#labSelect").val($("#section").val());
	$("#head").html($("#test").val());
	$("#pbdata").html($("#test1").val());
	
//	getHoliday();
	
	$("#pbdata tr td").click(function(){
		var id=this.id;
		var name = this.name;
		var shifts=$("#"+id).html();
		$.each($("#shiftSelect input"),function(name,v){
			if(v.checked){
				
				
				if(shifts.indexOf(v.value)>=0){
					shifts=shifts.replace(v.value+";","");
				}else{
					shifts = shifts + v.value+";";
				}
			}
		});
		$("#"+id).html(shifts);
		
	});
	
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
			var section = $("#section").val();
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
				if(value != "") {
					text = text + array[0] + ":" + date + "-" + day + ":" + value  +",";
				}
			});
			
			$.post("../pb/pb/submit",{text:text,section:section},function(data) {
				window.location.href="../pb/pb?date=" + $("#date").val();
			});
		}
	});
	
	$("#shiftBtn2").click(function() {
		var section = $("#section").val();
		var month = $("#month").val();
		$.get("../pb/pb/workCount",{section:section,month:month},function(data){
			for(var i=0;i<data.length;i++){
				var worker = data[i];
				var name = worker.worker; 
				$("#nx"+name).html(worker.holiday);
				$("#yx"+name).html(worker.monthOff);
				$("#yb"+name).html(worker.workTime);
			}
		});

	});
	
	$("#changeMonth").click(function() {
		window.location.href="../pb/pb?date=" + $("#date").val();
	});
	
	$("#date").val($("#month").val());
});