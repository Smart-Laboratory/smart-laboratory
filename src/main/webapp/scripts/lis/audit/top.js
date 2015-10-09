$(function(){
	$.get("../audit/count",{}, function(data) {
		$("#today_info_unaudit").html(data.todayunaudit);
		$("#today_info_unpass").html(data.todayunpass);
		$("#need_write_back").html(data.needwriteback);
		$("#info_dangerous_undeal").html(data.dangerous);
		if (data.dangerous != 0) {
			$("#div_dangerous").removeClass('alert-success');
			$("#div_dangerous").addClass('alert-error');
		} else {
			$("#div_dangerous").removeClass('alert-error');
			$("#div_dangerous").addClass('alert-success');
		}
	},'json');

	setInterval(function() {
		
		$.get("../audit/count",{}, function(data) {
			$("#today_info_unaudit").html(data.todayunaudit);
			$("#today_info_unpass").html(data.todayunpass);
			$("#need_write_back").html(data.needwriteback);
			$("#info_dangerous_undeal").html(data.dangerous);
			if (data.dangerous != 0) {
				$("#div_dangerous").removeClass('alert-success');
				$("#div_dangerous").addClass('alert-error');
			} else {
				$("#div_dangerous").removeClass('alert-error');
				$("#div_dangerous").addClass('alert-success');
			}
 			$("#audit_status_info").html("");
 			/*if (data.status == 1) {
 				$("#audit_status_info").html("自动审核中...");
 			} else if (data.status == 2) {
 				if ($("#hiddenAuditConfirm").val() == 'true') {
 					$.get("。。/audit/result",{sample:"<c:out value='${strToday}'/>", auto:"true"},function() {});
     				$("#audit_status_info").html("自动审核中...");
 				}
 			}*/
		},'json');
	}, 15000);
	
	$("#pageRefreshBtn").click(function() {
 		window.location.reload();
	});
});