$(function(){
	
	$("#need_write_back_div").click(function() {
 		$("#need_writeback_table").html("");
 		$.get("../audit/ajax/writeBack",{},function(data){
 			var array = jQuery.parseJSON(data);
 			for(var i=0; i<array.length; i++) {
 				var trLine = "<tr>";
 				trLine += "<td style='width:40%'>" + array[i].code + "<div class='sample_list_div' style='display:none;'>"+array[i].list+"</div></td>";
  				trLine += "<td class='wb_count' style='width:30%'>" + array[i].count + "</td>";
 				trLine += "<td style='width:30%'><button class='btn btn-success wb_detail' style='padding:2px;width:60px;'>详情</button><button class='btn btn-info wb_button' style='margin-left:5px;padding:2px;width:60px;'>写回</button></td>";
 				$("#need_writeback_table").append(trLine);
 			}
 			$("#need_writeback_table .wb_button").click(function(){
 				var checker = $(this).parent().parent().find('.wb_checker').html();
 				var count = $(this).parent().parent().find('.wb_count').html();
 				var code = $(this).parent().parent().find('.wb_code').html();
 				var lab = $(this).parent().parent().find('.wb_lab').html();
 				if (confirm("确认写回"+count+"条样本？")) {
     				writeBackOnce(code, lab, checker);
     			}
 			});
 			$("#need_writeback_table .wb_detail").click(function(){
 				var sampleDiv = $(this).parent().parent().find('.sample_list_div');
 				if (sampleDiv.css('display') == 'none')
 					sampleDiv.css('display','block');
 				else
 					sampleDiv.css('display','none');
 			});
 		});
 		openAllNeedWriteBackDialog();
 	});
	
	$("#writeBackBtn").click(function() {
		if($("#hiddenAuditConfirm").val() == "false") {
			layer.msg("写回未开启，请到写回配置中开启写回！", {icon: 0, time: 1000});
			return;
		}
		var btnText = $("#controlAuditBtn").html().trim();
 		var status = 0;
 		if (btnText == "停止") {
 			status = 1;
 		}
 		var code = "";
 		var codeAll = "";
		var codeScope = "";
		if (status == 1) {
     		$("#codeSetDiv .codeItem").each(function(index,self) {
    			if ($(self).find(".codeCheck").attr("checked") == "checked"){
    				var test = $(self).find(".codeText").html();
    				var lo = $(self).find(".val-lo").val();
        			var hi = $(self).find(".val-hi").val();
        			if (codeScope != "") codeScope += ";";
        			if (lo.length == 0 && hi.length == 0) {
        				code += $(self).find(".codeText").html() + ",";
        			} else if (lo.length == 3 && hi.length == 3) {
        				codeScope += test + lo + "-" + hi;
        			}
        			codeAll += $(self).find(".codeText").html() + ",";
    			}
    		});
		}
		code = code.substring(0,code.length-1);
		codeAll = codeAll.substring(0,code.length-1);
		$.get("../audit/testset",{code:codeAll},function(data){
			if(data) {
				if ($("#need_write_back").html() != "0") {
					if (confirm("确认写回"+$("#need_write_back").html()+"条样本")) {
						writeBackOnce(code, $("#labSelect").val(), $("#userText").html());
						if(codeScope != "") {
							for(var i=0; i< codeScope.split(";").length; i++) {
								if(codeScope.split(";")[i] != "") {
									writeBackPart(scope, $("#labSelect").val(), $("#userText").html());
								}
							}
						}
					}
				}
			} else {
				layer.msg(codeAll + "中有未设置的检验者，请先查看检验者设置！", {icon: 0, time: 1000});
			}
		});
		
	});
	
	var isFirstCliclkPart = true;
	$("#writeBackPartBtn").click(function() {
		if($("#hiddenAuditConfirm").val() == "false") {
			layer.msg("写回未开启，请到写回配置中开启写回！", {icon: 0, time: 1000});
			return;
		}
		if ($("#need_write_back").html() != "0") {
			if (isFirstCliclkPart) {
				var isFirstChecked = true;
				var code = "";
				$("#codeSetDiv  .codeItem").each(function(index,self) {
					if ($(self).find(".codeCheck").attr("checked") == "checked" && isFirstChecked){
						isFirstChecked = false;
						code = $(self).find(".codeText").html();
					}
				});
				$("#writeBack_text").val(code + "001-999");
				isFirstCliclkPart = false;
			}
			//getWriteBackList($("#lastDepLib").val(), "${checkOperator}");
			openWriteBackPartDialog();
		}
	});
	
	$("#writePartBtn").click(function() {
		var code = "";
		$("#codeSetDiv  .codeItem").each(function(index,self) {
			if ($(self).find(".codeCheck").attr("checked") == "checked"){
				code += $(self).find(".codeText").html() + ",";
			}
		});
		code = code.substring(0,code.length-1);
		$.get("../audit/testset",{code:code},function(data){
			if(data) {
				if ($("#writeBack_text").val() != "") {
					writeBackPart($("#writeBack_text").val(), $("#labSelect").val(), $("#userText").html());
				}
			} else {
				layer.msg(code + "中有未设置的检验者，请先查看检验者设置！", {icon:0, time: 1000});
			}
		});
	});
	
	$("#passAndwriteBackBtn").click(function() {
	if ($("#writeBack_text").val() != "") {
		$.post("<c:url value='/explain/audit/passAndWrite'/>",{text:$("#writeBack_text").val(),op:"pass"},function(data) {
			if (data == true) {
				writeBackPart($("#writeBack_text").val(), $("#labSelect").val(), $("#userText").html());
			}
		});
		
		}
	});
	
	$("#AuditCodeSetting").click(function(){
		if ($("#hiddenAuditConfirm").val() == 'true') {
			$("#codeSetDiv .input-ctl").attr('disabled', 'disabled');
 		} else {
			$("#codeSetDiv .input-ctl").removeAttr('disabled');
 		}
		openCodeSetDialog();
	});
	
});

function writeBackOnce(code, lab, user) {
	$.getJSON($("#writebackurl").val() + "ajax/writeBack/once.htm?callback=?",{code:code, lib:lab, operator:user},function(data){
		if (data.result == 0) {
			layer.msg("正在写回中...", {time: 1000});
		} else if (data.result == 1) {
			$.get("。。/audit/count",{}, function(data) {
	 			$("#today_info_unaudit").html(data.todayunaudit);
	 			$("#today_info_unpass").html(data.todayunpass);
	 			$("#need_write_back").html(data.needwriteback);
	 			$("#info_dangerous_undeal").html(data.dangerous);
	 			if (data.dangerous != 0) {
	 				$("#div_dangerous").removeClass('alert-success');
	 				$("#div_dangerous").addClass('alert-danger');
	 			} else {
	 				$("#div_dangerous").removeClass('alert-danger');
	 				$("#div_dangerous").addClass('alert-success');
	 			}
	 		},'json');
			layer.msg("写回成功！", {icon: 1, time: 1000});
		 } else {
			layer.msg("写回失败！", {icon: 2, time: 1000});
		 }
	});
}

function writeBackPart(text, lab, user) {
		
	$.getJSON($("#writebackurl").val() + "ajax/writeBack/part.htm?callback=?",{text:text.toUpperCase(), lib:lab, operator:user},function(data){
		if (data.result == 0) {
			layer.msg("正在写回中...", {time: 1000});
		} else if (data.result == 1) {
			$.get("../audit/count",{}, function(data) {
	 			$("#today_info_unaudit").html(data.todayunaudit);
	 			$("#today_info_unpass").html(data.todayunpass);
	 			$("#need_write_back").html(data.needwriteback);
	 			$("#info_dangerous_undeal").html(data.dangerous);
	 			if (data.dangerous != 0) {
	 				$("#div_dangerous").removeClass('alert-success');
	 				$("#div_dangerous").addClass('alert-danger');
	 			} else {
	 				$("#div_dangerous").removeClass('alert-danger');
	 				$("#div_dangerous").addClass('alert-success');
	 			}
	 		},'json');
			layer.msg("写回成功！", {icon: 1, time: 1000});
			setTimeout(function(){
				layer.closeAll();
			}, 1000);
		 } else {
			layer.msg("写回失败！", {icon: 2, time: 1000});
		 }
	 });
}

function getWriteBackList(lab, operator) {
	$.getJSON("../audit/ajax/writeBackList",{lab:lab, operator:operator},function(data){
		var array = data.sample.split(",");
		var html = "";
		for (var i=0; i<array.length; i++) {
			html += "<div class='checkbox' style='float:left;'><input type='checkbox' name='writeBackSampleCheck' value='" + array[i] + "'/>" + array[i];
			html += "</div>";
		}
		$("#writeBackList").append(html);
	});
}
	 
