
function getList(text, lab) {
	jQuery("#list").jqGrid({ 
    	url:"../audit/data?sample="+text+"&lab="+lab, 
    	datatype: "json", 
    	autowidth:true,
    	colNames:['ID','状态', '标记', '', '样本号','FLAG','SIZE'], 
    	colModel:[ 
    		{name:'id',index:'id', hidden:true}, 
    		{name:'status',index:'status', width:50, sortable:false, stype:'select', searchoptions:{value:"-3:所有;-1:无结果;1:已通过;2:未通过;3:已修改;4:未核实;5:含图片"}},
    		{name:'mark',index:'mark', width:50, sortable:false, stype:'select', searchoptions:{value:"0:所有;1:自动;2:差值;3:比值;4:少做;5:复检;6:危急;7:警戒1;8:警戒2;9:极值;10:Bayes"}}, 
    		{name:'lisPass',index:'lisPass', width:15, sortable:false},
    		{name:'sample',index:'sample', width:120, align:"right", sortable:false},
    		{name:'flag',index:'flag', hidden:true},
    		{name:'size',index:'size', hidden:true}], 
    	rowNum:25,
    	viewrecords:true,
    	height: '100%',
    	jsonReader : {repeatitems : false},
    	mtype: "GET", 
    	pager: '#pager',
    	multiselect: true,
    	multiboxonly:true,
    	onSelectRow: function(id) {
    		$("#rbcLabel").css('display','none');
    		var ret = jQuery("#list").jqGrid('getRowData',id);
    		$("#hiddenSampleNo").val(ret.sample);
    		getPatient(ret);
    		
    		$("#collectBtn").css('display','inline');
    		$("#testAdd").css('display','inline');
			$("#testDelete").css('display','inline');
			$("#auditUnpassBtn").css('display','inline');
			$("#auditPassBtn").css('display','inline');
			$("#auditPrintBtn").css('display','none');
			
			$("#needEdit").val(true);
    		if(ret.status == "未审核") {
    			$("#unaudit_reason_btn").hide();	
    		} else if(ret.status == "已通过") {
    			$("#unaudit_reason_btn").hide();
    			$("#testAdd").css('display','none');
    			$("#testDelete").css('display','none');
    			$("#auditPrintBtn").css('display','inline');
    			$("#auditPassBtn").css('display','none');
    			$("#needEdit").val(false);
    		} else if (ret.status == "无结果"){
    			$("#unaudit_reason_btn").hide();
    			$("#testAdd").css('display','none');
    			$("#testDelete").css('display','none');
    			$("#auditPrintBtn").css('display','none');
    			$("#auditUnpassBtn").css('display','none');
    			$("#auditPassBtn").css('display','none');
    			$("#collectBtn").css('display','none');
    		} else {
    			$("#unaudit_reason_btn").show();
    			$("#auditUnpassBtn").css('display','none');
    			$("#collectBtn").css('display','none');
    		}
    		
    		
    		$("#sampleTitle").html("");
    		$("#hisLastResult").val(null);
    		$("#span_sampleNo").html(ret.sample);
    		$("#test_sampleNo").val(ret.sample);
    		
    		if(ret.flag==0){
    			$("#modifyBtn").css('display','none');
    		}else{
    			$("#modifyBtn").css('display','inline');
    		}
    	},
    	loadComplete: function() {
    		jQuery("#list").jqGrid("setGridParam",{url:"../audit/data?lab=" + $("#labSelect").val()});
    		
    		var firstDocId, firstSampleNo;
    		var length = jQuery('#list').jqGrid('getCol','id', false).length;
    		if(length==0){
    			$("#mid").css("display","none");
    		}
    		$.each(jQuery('#list').jqGrid('getCol','id', false), function(k,v) {
    			var ret = jQuery("#list").jqGrid('getRowData',v);
    			if (k == 0) {
    				firstDocId = ret.id;
    				firstSampleNo = ret.sample;
    			}
        		if (ret.status == "未通过") {
        			jQuery("#list").jqGrid('setRowData', v, {status:"<font color='red'>"+ret.status+"</font>"});
        	 	}
        		if (ret.mark == "危急") {
    				jQuery("#list").jqGrid('setRowData', v, {mark:"<div class='dan_td'>"+ret.mark+"</div>"});
        		} else if (ret.mark == "差值") {
        			jQuery("#list").jqGrid('setRowData', v, {mark:"<div class='diff_td'>"+ret.mark+"</div>"});
        		} else if (ret.mark == "比值") {
        			jQuery("#list").jqGrid('setRowData', v, {mark:"<div class='ratio_td'>"+ret.mark+"</div>"});
        		} else if (ret.mark == "复检") {
        			jQuery("#list").jqGrid('setRowData', v, {mark:"<div class='re_td'>"+ret.mark+"</div>"});
        		} else if (ret.mark == "警戒1") {
        			jQuery("#list").jqGrid('setRowData', v, {mark:"<div class='al2_td'>"+ret.mark+"</div>"});
        		} else if (ret.mark == "警戒2") {
        			jQuery("#list").jqGrid('setRowData', v, {mark:"<div class='al3_td'>"+ret.mark+"</div>"});
        		} else if (ret.mark == "极值") {
        			jQuery("#list").jqGrid('setRowData', v, {mark:"<div class='ex_td'>"+ret.mark+"</div>"});
        		}
    		}); 
    		$("#list").setSelection(firstDocId, true);
    		
    		if ($("#gs_status").children('option:selected').val() != 1 && $("#gs_status").children('option:selected').val() != 2) {
				$("#gs_mark").css('display','none');
			}
    		$("#gs_status").change(function(){

    			if ($(this).children('option:selected').val() == 1 || $(this).children('option:selected').val() == 2) {
    				$("#gs_mark").css('display','inline');
    			} else {
    				$("#gs_mark").css('display','none');
    			}
    		});
    		
    		$("#gs_status").css('width','60px');
    		$("#gs_mark").css('width','60px');
    	}
    }).trigger("reloadGrid");
	jQuery("#list").jqGrid('navGrid','#pager',{edit:false,add:false,del:false,search:false,refresh:false});
    jQuery("#list").jqGrid('filterToolbar');
    jQuery("#list").jqGrid('navButtonAdd',"#pager",{caption:"",title:"", buttonicon :'ui-icon-pin-s', onClickButton:function(){ mygrid[0].toggleToolbar() } });
}

$(function(){
	$("#manualAuditPassBtn").click(function() {
		var s; 
		s = jQuery("#list").jqGrid('getGridParam','selarrrow'); 
		var ids = "";
		for (var c in s) {
			if (ids != "") {
				ids += ",";
			}
			ids += s[c];
		}
		if (s.length != 0 && confirm("确认通过这 "+s.length+" 个样本？")) {
			$.post("../audit/batch",{ids:ids,op:"pass"},function(data) {
				if (data == true) {
					jQuery("#list").trigger("reloadGrid");
				}
			});
		}
	});
	$("#manualAuditUnPassBtn").click(function() {
		var s; 
		s = jQuery("#list").jqGrid('getGridParam','selarrrow'); 
		var ids = "";
		for (var c in s) {
			if (ids != "") {
				ids += ",";
			}
			ids += s[c];
		}
		if (s.length != 0 && confirm("确认不通过这 "+s.length+" 条样本？")) {
			$.post("../audit/batch",{ids:ids,op:"unpass"},function(data) {

				if (data == true) {
					for (var c in s) {
						jQuery("#list").jqGrid('setRowData', s[c], {status:"<font color='red'><fmt:message key='audit.unpass'/></font>"});	
					}
				}
			});
		}
	});
	$("#manualWriteBackBtn").click(function() {
		var s; 
		s = jQuery("#list").jqGrid('getGridParam','selarrrow'); 
		var samples = "";
		for (var i=0; i<s.length; i++) {
			var sample = jQuery("#list").jqGrid('getCell',s[i],'sample'); 
			if (samples != "") {
				samples += ",";
			}
			samples += "'"+sample+"'";
		}
		if (s.length != 0 && confirm("确认写回这 "+s.length+" 条样本？")) {
			$.getJSON("${catcherUrl}ajax/writeBack/batch.htm?callback=?",{samples:samples,lab:$("#lastDepLib").val(),user:"${checkOperator}"},function(data){
   				 if (data.result == 0) {
   					alert("正在写回中...");
   				 } else if (data.result == 1) {
   					alert("写回成功");
   				 } else {
   					alert("写回失败");
   				 }
   			});
		}
	});
	$("#sampleDelete").click(function(){
		
		var id = jQuery("#list").jqGrid('getGridParam','selrow');
		confirm("确认删除"+id);
		$.post("../audit/sampleDelete",{id:id},function(data) {
			if (data == true) {
				jQuery("#list").trigger("reloadGrid");
			}
		});
	});
	
	$("#sampleListRefreshBtn").click(function() {
 		jQuery("#list").trigger("reloadGrid");
 		/*$.getJSON("${catcherUrl}ajax/remoteSyncOnce.htm?callback=?", {
			start : "<c:out value='${date}'/>",
			end : "<c:out value='${date}'/>"
		}, function(data) {
			//$.get("<c:url value='/explain/audit/result'/>",{sample:"<c:out value='${strToday}'/>"},function() {});
		});*/
	});
});