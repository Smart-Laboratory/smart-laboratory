function personal(name){
	window.location.href="<c:url value='/pb/grcx'/>?name=" + name;
}


$(function() {
	
	$("#date").datepicker({
		changeMonth: true,
	    changeYear: true,
		dateFormat: 'yy-mm',
		monthNamesShort: ['1\u6708','2\u6708','3\u6708','4\u6708','5\u6708','6\u6708','7\u6708','8\u6708','9\u6708','10\u6708','11\u6708','12\u6708'],
		dayNamesMin: ['\u65e5','\u4e00','\u4e8c','\u4e09','\u56db','\u4e94','\u516d']
	});
	
	$("#search").click(function() {
		window.location.href="<c:url value='/pb/pbcx'/>?date=" + $("#date").val() + "&section=" + $("#section").val() + "&type=" + $("#typeSel").val();
		//$.get("<c:url value='/pb/pbcx'/>",{date:$("#date").val(),section:$("#section").val()},function() {});
	});
	
	$("#section").change(function() {
		if($("#section").val() == '1300000') {
			$("#type").css("display","block");
		} else {
			$("#type").css("display","none");
		}
	});
	
	$("#typeSel").val("${type}");
	$("#date").val('${date}');
	$("#section").val('${section}');
	
	var section = "${section}";
	if(section == '1300000') {
		$("#type").css("display","block");
	}
});