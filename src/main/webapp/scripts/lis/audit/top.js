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
 		
 		$("#allNeedWriteBackDialog").dialog("open");
 	});
	
	$("#writeBackBtn").click(function() {
		if($("#hiddenAuditConfirm").val() == "false") {
			alert("写回未开启，请到写回配置中开启写回！");
			return;
		}
		var code = "";
		$("#codeSetDiv  .codeItem").each(function(index,self) {
			if ($(self).find(".codeCheck").attr("checked") == "checked"){
				code += $(self).find(".codeText").html() + ",";
			}
		});
		code = code.substring(0,code.length-1);
		$.get("../audit/testset",{code:code},function(data){
			if(data) {
				if ($("#need_write_back").html() != "0") {
					if (confirm("确认写回"+$("#need_write_back").html()+"条样本")) {
						writeBackOnce($("#userLabCode").val(), $("#lastDepLib").val(), $("#userText").html());
					}
				}
			} else {
				alert(code + "中有未设置的检验者，请先查看检验者设置！");
			}
		});
		
	});
	
	var isFirstCliclkPart = true;
	$("#writeBackPartBtn").click(function() {
		if($("#hiddenAuditConfirm").val() == "false") {
			alert("写回未开启，请到写回配置中开启写回！");
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
			$("#writeBackPartDialog").dialog("open");
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
					writeBackPart($("#writeBack_text").val(), $("#lastDepLib").val(), $("#userText").html());
				}
			} else {
				alert(code + "中有未设置的检验者，请先查看检验者设置！");
			}
		});
	});
	
	$("#passAndwriteBackBtn").click(function() {
	if ($("#writeBack_text").val() != "") {
		$.post("<c:url value='/explain/audit/passAndWrite'/>",{text:$("#writeBack_text").val(),op:"pass"},function(data) {
			if (data == true) {
				writeBackPart($("#writeBack_text").val(), $("#lastDepLib").val(), $("#userText").html());
			}
		});
		
		}
	});
	
});

function writeBackOnce(code, lab, user) {
	$.getJSON($("#writebackurl").val() + "ajax/writeBack/once.htm?callback=?",{code:code, lib:lab, operator:user},function(data){
		if (data.result == 0) {
			alert("正在写回中...");
		} else if (data.result == 1) {
			$.get("。。/audit/count",{}, function(data) {
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
			alert("写回成功！");
		 } else {
			alert("写回失败！");
		 }
	});
}

function writeBackPart(text, lab, user) {
		
	$.getJSON($("#writebackurl").val() + "ajax/writeBack/part.htm?callback=?",{text:text.toUpperCase(), lib:lab, operator:user},function(data){
		if (data.result == 0) {
			alert("正在写回中...");
		} else if (data.result == 1) {
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
			alert("写回成功！");
			$("#writeBackPartDialog").dialog("close");
		 } else {
			alert("写回失败！");
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
	 
