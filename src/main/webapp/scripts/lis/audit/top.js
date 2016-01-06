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
	
	
});