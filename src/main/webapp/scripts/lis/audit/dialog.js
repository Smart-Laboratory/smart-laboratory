function addtotext(i){
	var item = $(i).parent().find("span");
	var ids = $("#checkId").val();
	var id = $(i).attr("id");
	if($(i).prop("checked")){
		if($("#noteText").val().indexOf($(item).html())<0){
			$("#noteText").val($("#noteText").val()+$(item).html()+";\n");
		}
		if(ids==null){
			ids = id;
		}else if(ids.indexOf(id)<0){
			ids = ids + id+";";
		}
		$("#checkId").val(ids);
	}else{
		var text = $("#noteText").val().replace($(item).html()+";","");
		text = text.replace(/[\r\n]/g, "&").replace("&&","&");
		if(text.indexOf("&") == 0) {
			text = text.replace("&","");
		}
		text = text.replace(/[&]/g, "\r\n");
		$("#noteText").val(text);
		if(ids!=null && ids.indexOf(id)>=0){
			ids=ids.replace(id+";","");
			$("#checkId").val(ids);
		}
	}
	
};
function setDefaultValue(){
// var date = new Date().Format("yyyyMMdd");
		$("#batchAddResults_statistic_date").val(new Date().Format("yyyyMMdd"));
	}
$(function(){
	// 对Date的扩展，将 Date 转化为指定格式的String
	// 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，
	// 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
	// 例子：
	// (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423
	// (new Date()).Format("yyyy-M-d h:m:s.S") ==> 2006-7-2 8:9:4.18
	Date.prototype.Format = function (fmt) { // author: meizz
	    var o = {
	        "M+": this.getMonth() + 1, // 月份
	        "d+": this.getDate(), // 日
	        "h+": this.getHours(), // 小时
	        "m+": this.getMinutes(), // 分
	        "s+": this.getSeconds(), // 秒
	        "q+": Math.floor((this.getMonth() + 3) / 3), // 季度
	        "S": this.getMilliseconds() // 毫秒
	    };
	    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	    for (var k in o)
	    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
	    return fmt;
	}
	
	
	$("#opStatusDialog").dialog({
		autoOpen: false,
		resizable: false,
		modal:true,
	    width: 680,
	    height: 460,
	    buttons:{
	    	"审核":function() {
	    		var sample = $("#hiddenSampleNo").val();
	    		var id = $("#hiddenDocId").val();
	    		var ids = $("#checkId").val();
	    		var checktest="";
    			$("#chartList :checkbox").each(function(){
    				if($(this).prop("checked")==true){
    					if(checktest=="")
    						checktest=$(this).attr("id").replace("check","");
    					else
    						checktest = checktest + ";" + $(this).attr("id").replace("check","");
    				}
    			});
    			
	    		if ($("#hiddenIsPass").val() == "true") {
	    			var note = $("#selectNoteDiv input[name='passReason']:checked").parent().find(".selectLabel").html();
	    			var text = $("#noteText").val().replace(/[\r\n]/g, "").replace(/[\n]/g, "");
	    			$.post("../audit/manual",{sample:sample, operate:"pass", note:note, text:text, checktest:checktest,ids:ids},function(data) {
	    				if (data == true) {
	    					$("#passreason").html(text);
	    					var s = jQuery("#list").jqGrid('getGridParam','selrow');
	    					jQuery("#list").jqGrid('setRowData', s, {status:"已通过"});
	    					
	    					$("#needEdit").val(false);
	    					$("#testAdd").css('display','inline');
	    	    			$("#testDelete").css('display','inline');
	    	    			$("#auditUnpassBtn").css('display','inline');
	    	    			$("#auditPassBtn").css('display','none');
	    	    			$("#collectBtn").css('display','inline');
	    	    			$("#opStatusDialog").dialog("close");
	    	    			$("#twoColumnDialog").dialog("close");
	    				}
	    			});
	    		} else {
	    			var text = $("#noteText").val();
	    			var text = $("#noteText").val().replace(/[\r\n]/g, "").replace(/[\n]/g, "");
	    			$.post("../audit/manual",{sample:sample, operate:"unpass", note:"", text:text, checktest:checktest,ids:ids},function(data) {
	    				if (data == true) {
	    					$("#passreason").html(text);
	    					var s = jQuery("#list").jqGrid('getGridParam','selrow');
	    					jQuery("#list").jqGrid('setRowData', s, {status:"<font color='red'>未通过</font>"});
	    					$("#testAdd").css('display','inline');
	    	    			$("#testDelete").css('display','inline');
	    	    			$("#auditUnpassBtn").css('display','none');
	    	    			$("#auditPassBtn").css('display','inline');
	    	    			$("#collectBtn").css('display','none');
	    	    			$("#opStatusDialog").dialog("close");
	    	    			$("#twoColumnDialog").dialog("close");
	    				}
	    			});
	    		}
	    	},
	    	"取消":function() {
	    		$(this).dialog("close");
	    	}
	    },
	    open:function(){
	    	$("#noteText").val("");
	    	$("#checkId").val("");
	    	var description;
	    	var ids="";
	    	$.get("../diagnosis/getDes",{diagnosis:$("#diagnosisValue").val(),sampleNo:$("#hiddenSampleNo").val()},function(data){
	    		$("#disease").val(data.disease);
	    		$("#descriptionDiv").html("");
	    		$("#guideDiv").html("");
	    		
	    		var sample = data.sample;
	    		description = sample.description;
	    		if(description !=null && description != "") {
	    			description = description.replace(/<p>/g,"").replace(/<\/p>/g,";\n");
	    			$("#noteText").val(description);
	    			$("#passreason").html(description);
	    		}
	    		
	    		var glist = data.guides;
	    		if(glist !=null && glist!="undefined"){
	    			var guide;
		    		for(var j=0; j<glist.length; j++){
		    			guide = glist[j];
		    			var id = "R"+guide.id;
		    			if(description !=null && description != "" && description.indexOf(guide)>=0){
		    				$("#guideDiv").append("<div class='checkbox'><label><input type='checkbox' id='"+id+"' onclick=addtotext(this) checked><span  id='descriptionSelect'>"+guide+"</span>  </label></div>");
		    				ids = ids + id+";";
		    			}else
		    				$("#guideDiv").append("<div class='checkbox'><label><input type='checkbox' id='"+id+"' onclick=addtotext(this) ><span  id='descriptionSelect'>"+guide+"</span>  </label></div>");
		    		}
	    		}
	    		if(data.dlist != null) {
	    			for(var i=0; i<data.dlist.length; i++){
		    			var item = data.dlist[i];
		    			var id = "D"+item.id;
		    			if(description !=null && description != "" && description.indexOf(item.description)>=0){
		    				$("#descriptionDiv").append("<div class='checkbox' ><label><input type='checkbox' id='"+id+"' onclick=addtotext(this) checked><span  id='descriptionSelect'>"+item.description+"</span>  </label></div>");
		    				ids = ids + id+";";
		    			}else
		    				$("#descriptionDiv").append("<div class='checkbox' ><label><input type='checkbox' id='"+id+"' onclick=addtotext(this)><span  id='descriptionSelect'>"+item.description+"</span>  </label></div>");
	                }
	    		}
	    	});
	    	if($("#hisLastResult").val() == 1) {
	    		$("#historyChart").css("display", "block");
	    		$.get("../print/historyChart",{sampleno:$("#hiddenSampleNo").val(), haslast:$("#hisLastResult").val()}, function(data) {
	    			$("#chartList").html("");
	    			data = jQuery.parseJSON(data);
					if(data.chartlist.length > 0) {
						for(var i = 0; i< data.chartlist.length; i++) {
							var check = data.chartlist[i].check;
							if(i > 1) {
								if(check == 1) {
									$("#chartList").append("<input type='checkbox' id='check" + data.chartlist[i].id + "' style='margin-top:10px;float:left;width:5%' checked></input>");
								} else {
									$("#chartList").append("<input type='checkbox' id='check" + data.chartlist[i].id + "' style='margin-top:10px;float:left;width:5%'></input>");
								}
								$("#chartList").append("<div id='chart" + data.chartlist[i].id + "' style='margin-top:10px;float:left;width:45%;height:200px'></div>");
							} else {
								if(check == 1) {
									$("#chartList").append("<input type='checkbox' id='check" + data.chartlist[i].id + "' style='float:left;width:5%' checked></input>");
								} else {
									$("#chartList").append("<input type='checkbox' id='check" + data.chartlist[i].id + "' style='float:left;width:5%'></input>");
								}
								$("#chartList").append("<div id='chart" + data.chartlist[i].id + "' style='float:left;width:45%;height:200px'></div>");
							}
							var xset = data.chartlist[i].time;
							var yset1 = data.chartlist[i].low;
							var yset2 = data.chartlist[i].result;
							var yset3 = data.chartlist[i].high;
							var chart = new Highcharts.Chart({ 
								title: {
									text: data.chartlist[i].title
								},
								credits: {
							          enabled:false
								},
								plotOptions: {
						            line: {
						                dataLabels: {
						                    enabled: true
						                },
						                enableMouseTracking: false
						            }
						        },
					            chart: {  
					                renderTo: 'chart' + data.chartlist[i].id,  
					                type: 'line',  
					            },
					            xAxis: {
					                categories: xset  
					            },  
					            yAxis: {
					                title: {
					                    text: '结果'
					                },
					                plotLines: [{
					                    value: 0,
					                    width: 1,
					                    color: '#808080'
					                }]
					            },
					            series: [{
					            	name: '检验结果',
					            	data: yset2 
					            }]  
					        });
						}
					}
				});
	    	} else {
				$("#historyChart").css("display", "none");
			}
	    	$.get("../audit/explain",{id:$("#hiddenSampleNo").val(),needReason:false},function(data){
	    		$("#explainDiv").html("");
	    		var rows = data.rows;
	    		if(!(rows==null || rows == undefined)){
	    			for(var i=0;i<rows.length;i++){
		    			row = rows[i];
		    			var id = "R"+row.id;
		    			if(description !=null && description != "" && description.indexOf(row.result)>=0){
		    				$("#explainDiv").append("<div class='checkbox'><label><input type='checkbox' id='"+id+"' onclick=addtotext(this) checked><span  id='descriptionSelect'>"+row.result+"</span>  </label></div>");
		    				ids = ids + id+";";
		    			}else
		    				$("#explainDiv").append("<div class='checkbox'><label><input type='checkbox' id='"+id+"' onclick=addtotext(this)><span  id='descriptionSelect'>"+row.result+"</span>  </label></div>");
		    		}
	    		}
	    	});
	    	$("#checkId").val(ids);
	    }
	});
	
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
	    width: 750,
	    height: 600
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
	    	// 张晋南1016-5-25 添加按钮获取
	    	"添加":function() {
	    		var result = false;
	    		$("#addTestList .testValue").each(function(index,self){
		    		if ($(self).val() != "") {
		    			result = true;
		    		}
		    	});
	    		if (!result) {
	    			// alert("<fmt:message key='alert.input.testresult'/>");
	    		} else {
	    			var postStr = ""; var tcValue = "";
	    			var sample = $("#hiddenSampleNo").val();
	    			$("#addTestList div").each(function(index,self){
    		    		var id = $(self).find(".testID").val();
    		    		var value = $(self).find(".testValue").val();
    		    		if (value != null && value != "") {
	    		    		if (postStr != "") postStr+=";";
	    		    		postStr += id + ":" + value;
	    		    		if(tcValue!=""){
	    		    			tcValue+=",";
	    		    		}
	    		    		tcValue +=id;
    		    		}
    		    	});
	    			if (postStr != "") {
		    			$.post("../audit/add",{test:postStr,sample:sample,tcValues:tcValue},function(data){
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
		modal:true,
	    width: 340,
	    height: 260
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
	    width: 520,
	    height: 460
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
	
	$("#batchAddResultsDialog").dialog({
		autoOpen: false,
	    width: 800,
	    height: 500
	});
	
	$("#sopDetailDialog").dialog({
		autoOpen: false,
	    width: 400
	});
	
	$("#diseaseSelect").autocomplete({
        source: function( request, response ) {
            $.ajax({
            	url: "../ajax/description/searchBag",
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

                    $("#diseaseSelect").removeClass("ui-autocomplete-loading");
                }
            });
        },
        minLength: 1,
        delay : 500,
        select : function(event, ui) {
        	$.get("../diagnosis/getDes",{id:ui.item.id},function(data){
	    		$("#disease").html(data.disease);
	    		var dlist = data.dlist;
	    		$("#descriptionDiv").html("");
	    		for(var i=0; i<dlist.length; i++){
	    			var item = dlist[i];
	    			var id  = "D"+item.id;
	    			$("#descriptionDiv").append("<div class='checkbox'><label><input type='checkbox' id='"+id+"' onclick=addtotext(this)><span  id='descriptionSelect'>"+item.description+"</span>  </label></div>");
                }
	    	});
        	
        }
	});
	
	$("#addCancel").click(function() {
		$("#addResultDialog").dialog("close");
	});
	
	$("#testAdd").click(function() {
		$("#profileList").empty(); 
		$("#addTestList").html("");
		var lastProfile =$("#lastprofile").val();
		$.get("../set/ylsf/ajax/ylsfList",{lab:$("#labSelect").val()},function(data){
			var array = jQuery.parseJSON(data);
			for (var i=0 ; i < array.length ; i++) {
				// var html = array[i].ksdm+","+array[i].ylmc;
					$("#addTestList").append("<div class='form-inline'><input type='hidden' class='testID' value='"+array[i].test+"'/><span class='testName span2'>"+array[i].name+"</span><input type='text' class='testValue span2 form-control'/></div>");
				}
		 });
		$("#addTestResultDialog").dialog("open");
//		
// if (lastProfile != "") {
// $.post("../audit/ajax/profileTest",{test:lastProfile,sample:$("#hiddenSampleNo").val()},function(data)
// {
// var array = jQuery.parseJSON(data);
// for (var i=0 ; i < array.length ; i++) {
// var result = true;
// $("#addTestList .testID").each(function(index,self){
// if ($(self).val() == array[i].test)
// result = false;
// });
// if (result) {
// $("#addTestList").append("<div><input type='hidden' class='testID'
// value='"+array[i].test+"'/><span class='testName
// span2'>"+array[i].name+"</span><input type='text' class='testValue
// span2'/></div>")
// }
// }
// });
// }
	});
	
	// 张晋南2016-5-25
	// 套餐选项改为autocomplete
	$("#packages").autocomplete({
		source: function( request, response ) {
            $.ajax({
            	url: "../set/ylsf/ajax/ylsfList",
                dataType: "json",
                data: {
                	maxRows : 12,
                	ylmc : request.term,
                    lab :$("#labSelect").val()
                },
                success: function( data ) {
                	response( $.map( data, function( item ) {
                        return {
                            label:  item.ylmc,
                            value: item.ylmc,
                            testIds:item.profiletest
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
     		$.post("../set/ylsf/ajax/profileTest",{test:ui.item.testIds,sample:$("#hiddenSampleNo").val()},function(data) {
     			var array = jQuery.parseJSON(data);
    			for (var i=0 ; i < array.length ; i++) {
    				var result = true;
    				$("#addTestList .testID").each(function(index,self){
    		    		if ($(self).val() == array[i].test)
    		    			result = false;
    		    	});
    				if(result){
    					$("#addTestList").append("<div class='form-inline'><input type='hidden' class='testID' value='"+array[i].test+"'/><span class='testName span2'>"+array[i].name+"</span><input type='text' class='testValue span2 form-control'/></div>")
    				}else{
    					alert("样本列表或者添加列表中已包含该检验项目!");
    				}
    			}
    			// alert("<fmt:message key='alert.add.profile.finished' />");
     		});
        }
// var result = true;
// $("#addTestList.testID").each(function(index,self){
// alert(ui.item.id);
// if ($(self).val() == ui.item.id)
// result = false;
// });
// if(result){
// $("#addTestList").append("<div class='form-inline'><input type='hidden'
// class='testID' value='"+ui.item.id+"'/><span class='testName
// span2'>"+ui.item.value+"</span><input type='text' class='testValue span2
// form-control'/></div>")
// }else{
// alert("样本列表或者添加列表中已包含该检验项目!");
// }
//        	
// }
       
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
			$("#addTestList.testID").each(function(index,self){
	    		if ($(self).val() == ui.item.id)
	    			result = false;
	    	});
			if(result){
				$("#addTestList").append("<div class='form-inline'><input type='hidden' class='testID' value='"+ui.item.id+"'/><span class='testName span2'>"+ui.item.value+"</span><input type='text' class='testValue span2 form-control'/></div>")
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
			$("#tat_audit").html("<a href='javascript:void(0);' class='btn btn-info' onclick='getAuditHistory()'>" + data.audit + "</a>");
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
	
	$("#codeSetDiv :checkbox").click(function(){
 		var code = $(this).parent().find(".codeText").html();
 		if ($(this).prop("checked")){
 			$(this).parent().parent().parent().find(".scopeDiv").css('display','block');
 			$.post("../audit/activeCode",{code:code,active:true},function() {}); 		
        } else {
        	$(this).parent().parent().parent().find(".scopeDiv").css('display','none');
        	$.post("../audit/activeCode",{code:code,active:false},function() {});
        }
 	});
	
	$("#autoAuditNote").html("参考范围为<strong class='text-warning'>3位</strong>数字,不输入审核<strong class='text-warning'>整个段</strong>");

// 点击套餐按钮,已合并，暂时取消
	// 张晋南2016-5-25
	/*
	 * $("#addProfileBtn").click(function() { var testIds =
	 * $("#profileList").val();
	 * $.post("../set/ylsf/ajax/profileTest",{test:testIds,sample:$("#hiddenSampleNo").val()},function(data) {
	 * var array = jQuery.parseJSON(data); for (var i=0 ; i < array.length ;
	 * i++) { var result = true; $("#addTestList
	 * .testID").each(function(index,self){ if ($(self).val() == array[i].test)
	 * result = false; }); if (result) { $("#addTestList").append("<div><input
	 * type='hidden' class='testID' value='"+array[i].test+"'/><span
	 * class='testName span2'>"+array[i].name+"</span><input type='text'
	 * class='testValue form-control'/></div>") } } //alert("<fmt:message
	 * key='alert.add.profile.finished' />"); }); });
	 */
	
	$("#deleteAllTest").click(function(){
 		$("#addTestList").html("");
	});
	
 	$("#statisticDialogBtn").click(function() {
		$("#statisticDialog").dialog("open");
	});
 	
 	/**
	 * 张晋南2016-06-02 标本批量添加默认值
	 */
 	$("#batchAddResultsBtn").click(function(){
 		$.get("../audit/ajax/batchAddResults_statistic_getLoadValue",{lab:$("#labSelect").val()},function(data){
			var array = jQuery.parseJSON(data);
			for (var i=0 ; i < array.length ; i++) {
					$("#batchAddResults_statistic_packages").append("<option value='"+array[i].value+"'>"+array[i].name+"</option>");
				}
		 });
 		$("#batchAddResultsDialog").dialog("open");
 	});
 	/**
	 * 张晋南2016-06-02 标本批量添加默认值
	 */
 	$("#batchAddResults_statisticBtn_get").click(function() {
 		var bdate = $("#batchAddResults_statistic_date").val();
 		var packages =$("#batchAddResults_statistic_packages").val();
 		var bsc = $("#batchAddResults_statistic_code").val();
 		var bsb = $("#batchAddResults_statistic_begin").val();
 		var bse = $("#batchAddResults_statistic_end").val();
 		if(""==bdate){
 			alert("请输入日期！");
 			return ;
 		}
 		if(""==bsc){
 			alert("请输入检验段！");
 			return ;
 		}
 		if(""==bsb){
 			alert("请输入开始编号！");
 			return ;
 		}
 		if(""==bse){
 			alert("请输入结束编号！");
 			return ;
 		}
 		$.get("../audit/ajax/batchAddResults_statistic_get",{packages:packages,bdate:bdate,bsc:bsc,bsb:bsb,bse:bse},function(data){
 				var array = jQuery.parseJSON(data);
 				for (var i=0 ; i < array.length ; i++) {
 					$("#batchAddResults_statistic_table").append("<div class='form-group'><label class='col-xs-4 control-label no-padding-right' for='profiledescribe'>"+array[i].name+"</label><div class='col-xs-8'><input type='text' id='profiledescribe' name='profiledescribe' placeholder='默认值' value="+array[i].value+"></div> </div>")
 			   }
 		});
 	});
 	$("#batchAddResults_statisticBtn_save").click(function() {
	 	$.post("../audit/batchAddResults_statistic_save",{sampleNo:$("#hiddenSampleNo").val(),id:ii0}, function(data) {
			if (data == true) {
				alert("Success!!!");
			} else {
				alert("Fail!!!");
			}
		});
 	});
	var isFirstStatistic = true;
	$("#statisticBtn").click(function() {
		var code = $("#statistic_code").val();
		var from = $("#statistic_from").val();
		var to = $("#statistic_to").val();
		
		if (isFirstStatistic) {
			jQuery("#statistic_table").jqGrid({
				url:"../audit/statistic?code="+code+"&from="+from+"&to="+to,
				datatype: "json",
				jsonReader : {repeatitems : false}, 
				colNames:['ID','项目','项目数','平均值','最大值','最小值','标准差','变异系数'],
			   	colModel:[{name:'id',index:'id',hidden:true,sortable:false},
			   	    {name:'name',index:'name',width:120,sortable:false},
			   	 	{name:'num',index:'num',width:40,sortable:false},
			   	 	{name:'average',index:'average',width:80,sortable:false},
			   	 	{name:'max',index:'max',width:60,sortable:false},
			   	 	{name:'min',index:'min',width:60,sortable:false},
			   	 	{name:'standardDeviation',index:'standardDeviation',width:80,sortable:false},
			   	 	{name:'coefficientOfVariation',index:'coefficientOfVariation',width:80,sortable:false}],
			   	rowNum: 80,
			   	rownumbers: true,
			   	height:'100%',
			});
			isFirstStatistic = false;
		} else {
			jQuery("#statistic_table").jqGrid("setGridParam",{
				url:"../audit/statistic?code="+code+"&from="+from+"&to="+to
			}).trigger("reloadGrid"); 
		}
	});
	
});

var isFirstTrace = true;
function getAuditHistory() {
	var sample = $("#hiddenSampleNo").val();
	if(isFirstTrace){
		jQuery("#audit_trace_information").jqGrid({
			url:"../audit/trace?sample="+sample,
			datatype: "json",
			jsonReader : {repeatitems : false}, 
			colNames:['样本号','审核者','报告时间','状态','审核类型'],
		   	colModel:[{name:'sampleno',index:'sampleno',width:120,sortable:false},
		   		{name:'checker',index:'checker',width:60,sortable:false},
		   		{name:'checktime',index:'checktime',width:160,sortable:false},
		   		{name:'status',index:'status',width:60,sortable:false},
		   		{name:'type',index:'type',width:80,sortable:false}],
		   	height: '100%'
		});
		isFirstTrace=false;
	}else{
		jQuery("#audit_trace_information").jqGrid("setGridParam",{
			url:"../audit/trace?sample="+sample
		}).trigger("reloadGrid"); 
	} 
	$("#auditTraceDialog").dialog("open");
	
}

var count=0;
function createInput(){
    count++;
    var str = '<div class="col-sm-12" style="margin-top:5px;"><input type="file" contentEditable="false" id="uploads' + count + '' +
    '" name="uploads'+ count +'" class="col-sm-10"/><button onclick="removeInput(event,\'more\')" class="col-sm-2">'+'删除</button></div>';
    // document.getElementById(parentId).insertAdjacentHTML("beforeEnd",str);
    $("#more").append(str);
}

function ajaxFileUpload(){
    var uplist = $("input[name^=uploads]");
	var arrId = [];
	for (var i=0; i< uplist.length; i++){
	    if(uplist[i].value){
	    	arrId[i] = uplist[i].id;
		}
    }
	$.ajaxFileUpload({
		url:'../audit/ajax/uploadFile?sampleno=' + $("#hiddenSampleNo").val() + '&imgnote=' + $("#image_note").val(),
		secureuri:false,
		fileElementId: arrId,  
		success: function (data){
			alert("上传成功");
			$("#uploadDialog").dialog("close");
			jQuery("#list").trigger("reloadGrid");
		},
		error: function(){
			alert("error");
		}
	});
}

function removeInput(evt, parentId){
	   var el = evt.target == null ? evt.srcElement : evt.target;
	   var div = el.parentNode;
	   var cont = document.getElementById(parentId);       
	   if(cont.removeChild(div) == null){
	    return false;
	   }
	   return true;
}
function savePDF(){
	var sample = $("#hiddenSampleNo").val();
	var docId =$("#hiddenDocId").val();
	var last = 0;
	if ($("#hisLastResult").val() == 1) {
		last = 1;
	}
	$.post("../audit/ajax/saveHtml",{sampleNo:sample,lastNo:last,docIdNo:docId},function(data) {
		if (data == true) {
			alert("保存成功！");
		} else {
			alert("保存失败！");
		}
	});
}
