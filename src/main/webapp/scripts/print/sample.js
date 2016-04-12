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
		
		if(data.hisTitle1 != "") {
			$("#hisTitle1").html(data.hisTitle1);
		}
		for (var i=0;i<data.testresult.length;i++) {
			var s =  "<div style='height:20px;margin-left:10px;width:100%;'>";
			s += "<div style='float:left;width:5%;'>" + data.testresult[i].num + "</div>";
			s += "<div style='float:left;width:35%;'>" + data.testresult[i].name + "</div>";
			s += "<div style='float:left;width:10%;'>" + data.testresult[i].result + "</div>";
			s += "<div style='float:left;width:5%;'>" + data.testresult[i].resultflag + "</div>";
			s += "<div style='float:left;width:10%;'>" + data.testresult[i].last + "</div>";
			s += "<div style='float:left;width:5%;'>" + data.testresult[i].lastflag + "</div>";
			s += "<div style='float:left;width:20%;text-align:center;'>" + data.testresult[i].scope + "</div>";
			s += "<div style='float:left;width:10%;text-align:center;'>" + data.testresult[i].unit + "</div>";
			s += "</div>";
			$("#resultDiv").append(s);
		}
	});
});
