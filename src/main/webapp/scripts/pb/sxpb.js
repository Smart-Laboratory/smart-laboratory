$(function() {
	$("#pbdata").html($("#test1").val());
	$("#date").val($("#month").val());
	
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
		window.location.href="../pb/sxpb?month=" + $("#date").val();
	});
	
	$("#pbdata tr td").click(function(){
		var id=this.id;
		var shifts=$("#"+id).html();
		
		
		$.each($("#shiftSelect input"),function(name,v){
			if(v.checked){
				if(shifts.indexOf(v.value)>=0){
					shifts=shifts.replace(v.value,"");
				}else{
					shifts = shifts + v.value;
				}
			}
		});
		$("#"+id).html(shifts);
	});
	
	
	
	$("#shiftBtn").click(function() {
		var ischecked = true;
		if (ischecked) {
			var text = "";
			var date = $("#month").val();
			$("td[name^='td']").each(function(i){
				var array = $(this).attr("id").split("-");
				var day = "";
				day = array[1];
				
				var value = $(this).html();
					text = text + array[0] + ":" + date + ":" + day + ":" + value  +",";
			});
			
			$.post("../pb/sxpb/submit",{text:text,date:date},function(data) {
				alert("success!");
				window.location.href="../pb/sxpb?month=" + $("#date").val();
			});
			
		}
	});
})