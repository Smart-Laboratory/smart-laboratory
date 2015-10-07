$(function() {
	$("#operationDlg").dialog({
		autoOpen: false,
		resizable: false,
		modal:true,
	    width: 500
	});
	
	$("#print").dialog({
		autoOpen: false,
		resizable: false,
		modal:true,
	    width: 1000,
	    height: 600
	});
	
	$("#dealBtn").html("处理");
	$("#dealBtn").click(function() {
		var doc = $("#doc").val();
		// post info
		$.post("../critical/operate",{
			docId : doc,
			target : $("#targetText").val(),
			result : $("#resultText").val(),
			role : $("input[name='role']:checked").val(),
			success : $("input[name='success']:checked").val()
		},function(data) {
			if (data == true) {
				$("#ctl tbody tr").each(function() {
					var docId = $(this).find(".docId").val();
					if (docId == doc) {
						$(this).remove();
						return false;
					}
				});
			}
			$("#operationDlg").dialog("close");
		});
	});
	
	$("#deleteBtn").click(function() {
		var doc = $("#doc").val();
		// delete info
		if (confirm("确认删除该危急值 ?")) {
			$.post("../critical/delete",{
				docId : doc
			},function(data) {
				if (data == true) {
					$("#ctl tbody tr").each(function() {
						var docId = $(this).find(".docId").val();
						if (docId == doc) {
							$(this).remove();
							return false;
						}
					});
				}
				$("#operationDlg").dialog("close");
			});
		}
	});

	$("#ctl tbody tr").each(function() {
		var id = $(this).find(".reqId").html();
		$(this).find(".reqName").html("Name"+id);
		$(this).find(".reqPhone").html("Phone"+id);
		var doc = $(this).find(".operation").html();
		$(this).find(".operation").html("<input type='button' class='span1 btn' value='\u5904\u7406'/><input type='hidden' class='docId' value='"+doc+"'/>");
		$(this).find('.btn').click(function() {
			$("#doc").val(doc);
			$("#historyText").html("");
			$("#historyInfo").css('display','none');
			$("#patientPhoneText").html("");
			$("#patientAddressText").html("");
			$("#requesterNameText").html("");
			$("#requesterSectionText").html("");
			$("#requesterPhoneText").html("");
			$("#targetText").val(id);
			$("#resultText").val("");
			$("#failed-radio").click();
			$("#role-radio").click();
			
			$("#operationDlg").dialog("open");
			// get info
			$.get("../critical/ajax/info",{
				docId : doc
			},function(data) {
				data = jQuery.parseJSON(data);
				if (data.history != "") {
					$("#historyText").html(data.history);
					$("#historyInfo").css('display','block');
				}
				$("#patientPhoneText").html(data.patientPhone);
				$("#patientAddressText").html(data.patientAddress);
				$("#requesterNameText").html(data.requesterName);
				$("#requesterSectionText").html(data.requesterSection);
				$("#requesterPhoneText").html(data.requesterPhone);
				
				if (data.isInHospital) {
					$("#wardTypeText").html(data.wardType);
					$("#wardSectionText").html(data.wardSection);
					$("#wardPhoneText").html(data.wardPhone);	
					$("#ward").css('display','block');
				}

			});
		});
	});
	
	$("#printBtn").click(function(){
		$('#samplePrintFrame').empty();
		$("#samplePrintFrame").append("<iframe id='iframe_sample' name='iframe_sample' frameborder=0 style='background-color:transparent' width='99%' height='99%' src=\"../explain/audit/samplePrint\" />");
		$("#print").dialog("open");
		
	});
});