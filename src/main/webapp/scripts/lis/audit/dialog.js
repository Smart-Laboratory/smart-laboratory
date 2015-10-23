$(function(){
	
	$("#tatDialog").dialog({
		autoOpen: false,
		resizable: false,
		modal:true
	});
	
	$("#auditDialog").dialog({
		autoOpen: false,
	    resizable: false,
	    width: 600,
	    height: 360,
	    close: function(event, ui) {
	    	$("#isContinued").html("0");
	    }
	});
	$("#auditPrint").dialog({
		autoOpen: false,
		resizable: false,
		modal:true,
	    width: 700,
	    height: 500
	});
	$("#chartDialog").dialog({
		autoOpen: false,
		resizable: false,
		modal:true,
	    width: 680,
	    height: 480
	});
	$("#samplePrint").dialog({
		autoOpen: false,
		resizable: false,
		modal:true,
	    width: 1000,
	    height: 600
	});
	
	$( "#dialog" ).dialog({
        autoOpen: false,
        modal:true,
        width: 680,
		height: 360
    });
	
	$("#addTestResultDialog").dialog({
		autoOpen: false,
		resizable: false,
		modal:true,
	    width: 450,
	    height: 400,
	    buttons:{
	    	"添加":function() {
	    		var result = false;
	    		$("#addTestList .testValue").each(function(index,self){
		    		if ($(self).val() != "") {
		    			result = true;
		    		}
		    	});
	    		if (!result) {
	    			//alert("<fmt:message key='alert.input.testresult'/>");
	    		} else {
	    			var postStr = "";
	    			var sample = $("#hiddenSampleNo").val();
	    			$("#addTestList div").each(function(index,self){
    		    		var id = $(self).find(".testID").val();
    		    		var value = $(self).find(".testValue").val();
    		    		if (value != null && value != "") {
	    		    		if (postStr != "") postStr+=";";
	    		    		postStr += id + ":" + value;
    		    		}
    		    	});
	    			//alert(postStr);
	    			if (postStr != "") {
		    			$.post("../audit/add",{test:postStr,sample:sample},function(data){
		    				if (data) {
		    					$("#addTestResultDialog").dialog("close");
		    					var s = jQuery("#list").jqGrid('getGridParam','selrow');
		    					var ret = jQuery("#list").jqGrid('getRowData',s);
		    					
		    					if(data.size > 30 && $("#oneColumnShowBtn").prop("checked") == false) {
		    		    			$("#twosampleTable").css('display','block');
		    		        		$("#patientRow").css('display','none');
		    		    				twsSampleReload(ret.sample);
		    					} else {
		    						$("#patientRow").css('display','block');
		    		    			$("#twosampleTable").css('display','none');
		    		    				jQuery("#rowed3").jqGrid("setGridParam",{url:"../audit/sample?id="+ret.sample,editurl: "../audit/edit?sampleNo=" + ret.sample}).trigger("reloadGrid");
		    					}
		    					
		    				} else {
		    					alert("Failed!");
		    				}
		    			});
	    			}
	    		}
	    	},
	    	"取消":function() {
	    		$(this).dialog("close");
	    	}
	    }
	});	
	
	$(".ui-dialog-buttonset button").each(function(index,self){
		$(self).addClass('btn');
	});

	$("#addResultDialog").dialog({
		autoOpen: false,
		resizable: false,
	    width: 340,
	    height: 300
	});
	
	$("#tatDialog").dialog({
		autoOpen: false,
		resizable: false,
		modal:true
	});
	
	$("#twoColumnDialog").dialog({
		autoOpen: false,
		resizable: false,
		modal:true
	});
	
	
	
	$("#collectDialog").dialog({
		autoOpen: false,
		resizable: false,
		modal:true,
	    width: 320,
	    height: 400
	});
	
	$("#uploadDialog").dialog({
		autoOpen: false,
		resizable: false,
		modal:true,
	    width: 400,
	    height: 500
	});
	
	$("#templateDialog").dialog({
		autoOpen: false,
		resizable: false,
		modal:true,
	    width: 380,
	    height: 460
	});
	
	$("#imageDialog").dialog({
		autoOpen: false,
		resizable: false,
		modal:true,
	    width: 800,
	    height: 700
	});
	
	$("#codeSetDialog").dialog({
		autoOpen: false,
		resizable: false,
		modal:true,
	    width: 360,
	    height: 250
	});
	
	$("#writeBackPartDialog").dialog({
		autoOpen: false,
		resizable: false,
		modal:true,
	    width: 480
	});
	
	$("#testModifyDialog").dialog({
		autoOpen: false,
		resizable: false,
		modal:true,
	    width: 480,
	    height: 320
	});
	
	$("#sampleCompareDialog").dialog({
		autoOpen: false,
	    width: 600,
	    height: 500
	});
	
	$("#auditTraceDialog").dialog({
		autoOpen: false,
	    width: 600,
	    height: 500
	});
	
	$("#allNeedWriteBackDialog").dialog({
		autoOpen: false,
		resizable: false,
		modal:true,
	    width: 580,
	    height: 320
	});
	
	$("#taskListDialog").dialog({
		autoOpen: false,
		resizable: false,
		modal:true,
	    width: 580,
	    height: 320
	});
	
	$("#statisticDialog").dialog({
		autoOpen: false,
	    width: 600,
	    height: 500
	});
	
	$("#sopDetailDialog").dialog({
		autoOpen: false,
	    width: 400
	});
	
	$("#testAdd").click(function() {
		$("#profileList").empty();
		$("#addTestList").html("");
		var lastProfile;
//		$.get("../audit/ajax/profileList",{lab:$("#labSelect").val()},function(data){
//			//alert(data);
//			var array = jQuery.parseJSON(data);
//			for (var i=0 ; i < array.length ; i++) {
//				var html = array[i].describe+","+array[i].device;
//				if(lastProfile == array[i].test) {
//					$("#profileList").append("<option value='"+array[i].test+"' selected>"+html+"</option>");
//				} else {
//					$("#profileList").append("<option value='"+array[i].test+"'>"+html+"</option>");
//				}
//			}
//		 });
		$("#addTestResultDialog").dialog("open");
//		
//		if (lastProfile != "") {
//			$.post("../audit/ajax/profileTest",{test:lastProfile,sample:$("#hiddenSampleNo").val()},function(data) {
//     			var array = jQuery.parseJSON(data);
//    			for (var i=0 ; i < array.length ; i++) {
//    				var result = true;
//    				$("#addTestList .testID").each(function(index,self){
//    		    		if ($(self).val() == array[i].test)
//    		    			result = false;
//    		    	});
//    				if (result) {
//    					$("#addTestList").append("<div><input type='hidden' class='testID' value='"+array[i].test+"'/><span class='testName span2'>"+array[i].name+"</span><input type='text' class='testValue span2'/></div>")
//    				}
//    			}
//     		});
//		}
	});
	
	$("#searchProject").autocomplete({
        source: function( request, response ) {
            $.ajax({
            	url: "../ajax/searchTest",
                dataType: "json",
                data: {
                	maxRows : 12,
                    name : request.term
                },
                success: function( data ) {
  					
                	response( $.map( data, function( item ) {
                        return {
                            label: item.id + " : "  + item.name,
                            value: item.name,
                            id : item.id
                        }
                    }));

                    $("#searchProject").removeClass("ui-autocomplete-loading");
                }
            });
        },
        minLength: 1,
        delay : 500,
        select : function(event, ui) {
        	var result = true;
			$("#addTestList .testID").each(function(index,self){
	    		if ($(self).val() == ui.item.id)
	    			result = false;
	    	});
			if(result){
				$("#addTestList").append("<div><input type='hidden' class='testID' value='"+ui.item.id+"'/><span class='testName span2'>"+ui.item.value+"</span><input type='text' class='testValue span2'/></div>")
			}else{
				alert("样本列表或者添加列表中已包含该检验项目!");
			}
        	
        }
	});
	
	$("#testDelete").click(function() {
		var ii = jQuery("#rowed3").jqGrid('getGridParam','selrow');
		if (ii != null) {
			$.post("../audit/delete",{sampleNo:$("#hiddenSampleNo").val(),id:ii}, function(data) {
				if (data == true) {
					var s = jQuery("#rowed3").jqGrid('getGridParam','selrow');
					var next = $("#"+s).next("tr").attr("id");
					$("#rowed3").jqGrid('delRowData',ii);
					if (next != null) {
						$("#rowed3").setSelection(next, true);
					}
				} else {
					alert("Fail!!!");
				}
			});
		}
		
		var ii0 = jQuery("#sample0").jqGrid('getGridParam','selrow');
		if (ii0 != null) {
			$.post("../audit/delete",{sampleNo:$("#hiddenSampleNo").val(),id:ii0}, function(data) {
				if (data == true) {
					var s = jQuery("#sample0").jqGrid('getGridParam','selrow');
					var next = $("#"+s).next("tr").attr("id");
					$("#sample0").jqGrid('delRowData',ii0);
					if (next != null) {
						$("#sample0").setSelection(next, true);
					}
				} else {
					alert("Fail!!!");
				}
			});
		}
		
		var ii1 = jQuery("#sample1").jqGrid('getGridParam','selrow');
		if (ii1 != null) {
			$.post("../audit/delete",{sampleNo:$("#hiddenSampleNo").val(),id:ii1}, function(data) {
				if (data == true) {
					var s = jQuery("#sample1").jqGrid('getGridParam','selrow');
					var next = $("#"+s).next("tr").attr("id");
					$("#sample1").jqGrid('delRowData',ii1);
					if (next != null) {
						$("#sample1").setSelection(next, true);
					}
				} else {
					alert("Fail!!!");
				}
			});
		}
	});
	
	$("#tatBtn").click(function() {
		
		$("#tat_request").html("");
		$("#tat_execute").html("");
		$("#tat_receive").html("");
		$("#tat_audit").html("");
		$("#tat_result").html("");
		$("#tat_send").html("");
		$("#tat_ksreceive").html("");
		$("#audit_tat").html("");
		$("#tatDialog").dialog("open");
		var doc = $("#hiddenDocId").val();
		$.get("../audit/tat",{id:doc},function(data){
			data = jQuery.parseJSON(data);
			$("#tat_request").html(data.request);
			$("#tat_execute").html(data.execute);
			$("#tat_receive").html(data.receive);
//			$("#tat_audit").html("<a href='javascript:void(0);' class='btn btn-info' onclick='getAuditHistory()'>" + data.audit + "</a>");
			$("#tat_audit").html(data.audit);
			$("#tat_auditor").html(data.auditor);
			$("#tat_result").html(data.result);
			$("#tat_send").html(data.send);
			$("#tat_ksreceive").html(data.ksreceive);
			var time = parseInt(data.tat);
			var tStr = "";
			if (time >= 1440) {
				var day = Math.floor(time / 1440);
				tStr += day.toString();
				tStr += "天";
				time = time - day * 1440;
			}
			
			if (time >= 60) {
				var hour = Math.floor(time / 60);
				tStr += hour.toString();
				tStr += "小时";
				time = time - hour * 60;
			}
			tStr += time.toString();
			tStr += "分钟";
			
			$("#audit_tat").html(tStr);
		});
	});
	
	$("#codeSetDiv .codeCheck").click(function(){
 		var code = $(this).parent().find(".codeText").html();
 		alert($(this).attr("checked"));
 		if ($(this).attr("checked")){
 			alert(code);
 			$(this).parent().parent().parent().find(".scopeDiv").css('display','block');
 			$.post("../audit/activeCode",{code:code,active:true},function() {}); 
        } else {
        	$(this).parent().parent().parent().find(".scopeDiv").css('display','none');
        	$.post("../audit/activeCode",{code:code,active:false},function() {});
        }
 	});
	
	$("#autoAuditNote").html("参考范围为<strong class='text-warning'>3位</strong>数字,不输入审核<strong class='text-warning'>整个段</strong>");


	
});
















