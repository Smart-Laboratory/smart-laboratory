function personal(name){
	window.location.href="../pb/grcx?name=" + name;
}


$(function() {
	
	$("#data").html($("#cxdata").val());
	
	$("#date").datepicker({
		changeMonth: true,
	    changeYear: true,
		dateFormat: 'yy-mm',
		monthNamesShort: ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'],
		dayNamesMin: ['一','二','三','四','五','六','日']
	});
	
	$("#searchPB").click(function() {
		window.location.href="../pb/pbcx?date=" + $("#date").val() + "&section=" + $("#sectionSel").val() + "&type=" + $("#type").val();
		//$.get("<c:url value='/pb/pbcx'/>",{date:$("#date").val(),section:$("#section").val()},function() {});
	});
	
	$("#sectionSel").change(function() {
		if($("#sectionSel").val() == '1300000') {
			$("#type").css("display","block");
		} else {
			$("#type").css("display","none");
		}
	});
	
	$("#typeSel").val($("#type").val());
	$("#date").val($("#month").val());
	$("#sectionSel").val($("#section").val());
	
	var section = $("#sectionSel").val();
	if(section == '1300000') {
		$("#type").css("display","block");
	}
});