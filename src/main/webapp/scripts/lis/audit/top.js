$(function(){
	
	$("#need_write_back_div").click(function() {
 		$("#need_writeback_table").html("");
 		$.get("../audit/ajax/writeBack",{},function(data){
 			var array = jQuery.parseJSON(data);
 			for(var i=0; i<array.length; i++) {
 				var trLine = "<tr>";
 				trLine += "<td class='wb_checker'>" + array[i].checker + "</rd>";
 				trLine += "<td>" + array[i].name + "<div class='sample_list_div' style='display:none;'>"+array[i].list+"</div></td>";
 				trLine += "<td class='wb_code' style='width:200px;word-break:break-all;'>" + array[i].code + "</td>";
 				trLine += "<td class='wb_lab'>" + array[i].lab + "</td>";
 				trLine += "<td class='wb_count'>" + array[i].count + "</td>";
 				trLine += "<td><button class='btn wb_detail'>Detail</button><button class='btn btn-info wb_button'><fmt:message key='writeback' /></button></td>";
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