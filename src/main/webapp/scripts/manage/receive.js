$(function() {
	
	$("#doctin").focus();

	$("#refuse").click(function(){
		if($("#doctadviseno").val()) {
			window.open(baseUrl + "/quality/invalidSampleForm?id="+$("#doctadviseno").val());
		} else {
			layer.msg("无标本信息，请先输入条码号！", {icon: 2, time: 1000});
		}
	});
	
});

function getData(obj,event) {
	var e=e||event;
    var key = event.keyCode;
    if(navigator.appName=="Netscape"){
		key=e.which;
	}else{
		key=event.keyCode;
	}
	switch(key){
		case 13 : receiveSample(obj.value);
	}
}

function receiveSample(id) {
	if($("#operator").val() == "") {
		layer.msg("接收者不能为空，请输入接受者姓名！", {icon: 2, time: 1000});
	} else {
		var doct = $("#doctin").val();
		$.ajax({
	  		type:'GET',
			url: baseUrl + "/manage/receive/ajax/sample?barcode=" + doct + "&operator=" + $("#operator").val() + "(" + $("#point").val() + ")",
	  		success: function(data) {
	  			data = jQuery.parseJSON(data);
	  			$("#alert").css("display","block");
	  			if(data.type == 1) {
	  				$("#alert").removeClass().addClass("alert alert-warning alert-dismissable");
	  				$("#alert").html("医嘱号为" + doct + "的标本不存在，请确定医嘱号是否正确！");
	  				$("#text").css("display","none");
	  			} else {
	  				if(data.type == 2) {
	  					$("#alert").removeClass().addClass("alert alert-success alert-dismissable");
		  				$("#alert").html("医嘱号为" + doct + "的标本接收成功！");
	  				} else if(data.type == 3) {
	  					$("#alert").removeClass().addClass("alert alert-info alert-dismissable");
		  				$("#alert").html("医嘱号为" + doct + "的标本已接收，无需重复接收！");
	  				} else {
	  					$("#alert").removeClass().addClass("alert alert-warning alert-dismissable");
		  				$("#alert").html("医嘱号为" + doct + "的标本非本部门接收，请核对！");
	  				} 
	  				$("#examinaim").html(data.exam);
	  				$("#name").html(data.name);
	  				$("#sex").html(data.sex == 2 ? "女" : "男");
	  				$("#age").html(data.age);
	  				$("#lab").html(data.lab);
  					switch (data.stayhospitalmode) {
  					case 1:
  						$("#stayhospitalmode").html("门诊");
  						break;
  					case 2:
  						$("#stayhospitalmode").html("住院");
  						break;
  					case 3:
  						$("#stayhospitalmode").html("体检");
  						break;
  					default:
  						$("#stayhospitalmode").html("其他");
  						break;
  					}
  					if(data.stayhospitalmode == 2) {
  						$("#outpatient").css("display","none");
  						$("#wardtext").css("display","block");
  						$("#ward").html(data.section);
  		  				$("#bed").html(data.bed);
  		  				$("#type").html(data.wardType);
  		  				$("#phone").html(data.wardPhone);
  					} else {
  						$("#outpatient").css("display","block");
  						$("#wardtext").css("display","none");
  						$("#section").html(data.section);
  					}
	  				$("#text").css("display","block");
	  			}
	  			$("#doctin").val("");
	  	  	}
	  	});
	}
}