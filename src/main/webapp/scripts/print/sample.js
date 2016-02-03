$(function(){
	$.get("../print/sampleData",{sampleno:$("#hiddenSampleNo").val(), haslast:$("#hasLast").val()}, function(data) {
		data = jQuery.parseJSON(data);
		$("#blh").html(data.blh);
		$("#pName").html(data.pName);
		$("#pSex").html(data.sex);
		$("#pAge").html(data.age);
		$("#pType").html(data.pType);
		$("#diagnostic").html(data.diagnostic);
		$("#staymodetitle").html(data.staymodetitle);
		$("#patientId").html(data.pId);
		$("#staymodesection").html(data.staymodesection);
		$("#pSection").html(data.section);
		$("#requester").html(data.requester);
		$("#tester").html(data.tester);
		$("#auditor").html(data.auditor);
		$("#receivetime").html(data.receivetime);
		$("#checktime").html(data.checktime);
		$("#executetime").html(data.executetime);
		if(data.staymode == 2) {
			$("#bedtitle").css("display","inline");
			$("#pBed").css("display","inline");
			$("#pBed").html(data.bed);
		} else {
			$("#bedtitle").css("display","none");
			$("#pBed").css("display","none");
		}
		$("#examinaim").html(data.examinaim);
		$("#date").html(data.date);
	});
});
