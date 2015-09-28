	function getRelativeTests(sample){
		$("#relative-tests").html(" ");
		$.get("../audit/ajax/relativeTest",{sample:sample}, function(data) {
			if(data != "") {
				data = jQuery.parseJSON(data);
				$("#relative-tests").html(data.html);
			}
		});
	}
	
	var isFirst = true;
	function getPatient(ret) {
 		var docNo = ret.id;
		$.get("../audit/patient",{id:docNo},function(data){
			if (data.isOverTime) {
				$('#tatDialog').dialog("option","title", "TAT超时");
				$("#tatBtn").html("<b style='color: #FF4500;'>TAT超时</b>");
			} else {
				$('#tatDialog').dialog("option","title", "TAT");
				$("#tatBtn").html("<b>TAT</b>");
			}
    		if(data.size > 30 && $("#oneColumnShowBtn").prop("checked") == false) {
    			$("#twosampleTable").css('display','block');
        		$("#patientRow").css('display','none');
        		if(isFirst){
    				getSample(ret.sample);
    				getTwoSample(ret.sample);
    				isFirst = false;
    			}
    			else{
    				var array = new Array();
    				$.ajaxSetup({
    					async:false
    				});
    				$.get("../audit/twosample", {id:ret.sample}, function(data){
    					for(var i=0; i< data.length; i++) {
    						array[i] = data[i];
    					}
    				});
    				if(array[0].rows==""){
    					jQuery("#sample0").jqGrid("clearGridData");
    					jQuery("#sample1").jqGrid("clearGridData");
    				}
    					
    				jQuery("#sample0").jqGrid("setGridParam",{
    					data:array[0].rows,
    					userdata:array[0].userdata
    				}).trigger("reloadGrid");
    				jQuery("#sample1").jqGrid("setGridParam",{
    					data:array[1].rows,
    					userdata:array[0].userdata
    				}).trigger("reloadGrid");
    			}
			} else {
				$("#patientRow").css('display','block');
    			$("#twosampleTable").css('display','none');
    			if(isFirst){
    				getSample(ret.sample);
    				getTwoSample(ret.sample);
    				isFirst = false;
    			}
    			else{
    				jQuery("#rowed3").jqGrid("setGridParam",{url:"../audit/sample?id="+ret.sample,editurl: "../audit/edit?sampleNo=" + ret.sample}).trigger("reloadGrid");
    			}
			}
    		
    		
			if($("#englishToChBtn").prop("checked") == true) {
				jQuery("#rowed3").setGridParam().showCol("ab");
				jQuery("#rowed3").setGridParam().hideCol("name");
				jQuery("#sample0").setGridParam().showCol("ab");
				jQuery("#sample0").setGridParam().hideCol("name");
				jQuery("#sample1").setGridParam().showCol("ab");
				jQuery("#sample1").setGridParam().hideCol("name");
			} else {
				jQuery("#rowed3").setGridParam().showCol("name");
				jQuery("#rowed3").setGridParam().hideCol("ab");
				jQuery("#sample0").setGridParam().showCol("name");
				jQuery("#sample0").setGridParam().hideCol("ab");
				jQuery("#sample1").setGridParam().showCol("name");
				jQuery("#sample1").setGridParam().hideCol("ab");
			}
			
    		if ($("#historyTabs").tabs('option', 'active') == 0) {
   				jQuery("#audit_information").jqGrid("setGridParam",{
   					url:"../audit/explain?id="+ret.id,
   					editurl: "../audit/explain/edit?id=" + ret.id
   				}).trigger("reloadGrid");
    		} else if ($("#historyTabs").tabs('option', 'active') == 1) {
				jQuery("#rowed3").setGridParam().showCol("last2");
				jQuery("#rowed3").setGridParam().showCol("last3");
				jQuery("#rowed3").setGridParam().showCol("last4");
				jQuery("#rowed3").setGridParam().showCol("device");
				jQuery("#rowed3").setGridParam().showCol("checktime");
    		} else {
//    			getSopSchedule($("#labSelect").val());
    		}
    		
    		$("#historyTabs").css('display','block');
			
			$("#mid").css('display','block');
			if(data.mode == 1) {
				$("#sampleTitle").html("<font color='red'>★</font>" + $("#hiddenSampleNo").val() + "  " + data.examinaim);
				$("#sample0").jqGrid("setCaption", "<font color='red'>★</font>&nbsp" + $("#hiddenSampleNo").val() + " (共" + data.size + "项)");        	
			} else {
        		$("#sampleTitle").html($("#hiddenSampleNo").val() + "  " + data.examinaim);
        		$("#sample0").jqGrid("setCaption", $("#hiddenSampleNo").val() + " (共" + data.size + "项)");
        	}
			if(data.isLack) {
				var html = $("#sampleTitle").html();
				$("#sampleTitle").html("<font color='red'>" + html + "</font>");
			}
			if (data.hasImages) {
				$("#imageBtn").css('display','inline');
			} else {
				$("#imageBtn").css('display','none');
			}
			$("#rowed3").jqGrid("setCaption", $("#sampleTitle").html());
			$("#sample1").jqGrid("setCaption", data.examinaim);
        	$("#audit_reason").html(data.reason);
        	$("#pName").html("<a href='../patientList?patientId=" + data.patientId + "&blh=" + data.blh + "' target='_blank'>" + data.name + "</a>");
        	/* $("#pName").html("<a href='../../patient/list?patientId=" + data.patientId + "&blh=" + data.blh + "' target='_blank'>" + data.name + "</a>"); */
        	$("#pAge").html(data.age);
        	$("#blh").html("<a href='http://192.168.17.102/ZWEMR/SysLogin.aspx?lcation=inside&ly=D&edt=N&pid=" + data.blh + "&gh=" + data.requester + "' target='_blank'>" + data.blh + "</a>");
        	$("#doctadviseno").html(data.id);
        	$("#pSex").html(data.sex);
        	$("#pSection").html(data.section);
        	$("#pType").html(data.type);
        	$("#stayhospitalmode").html(data.stayhospitalmode);
        	if(data.diagnosticKnow == "") {
        		$("#diagnostic").html(data.diagnostic);
        	} else {
        		$("#diagnostic").html("<a href='#' onclick='javascript:show_knowledge(\""+data.diagnosticKnow+"\")'>"+data.diagnostic+"</a>");
        	}
        	
        	var reason = data.passReason;
        	$("#passreason").html(reason);
        	if (reason != null) {
        		$("#passLabel").css('display','block');
        	} else {
        		$("#passLabel").css('display','none');
        	}
        	
        	if(data.bed == null){
        		$("#pBedText").css('display','none');
        		$("#pBedLabel").css('display','none');
        	}else{
        		$("#pBed").html(data.bed);
        		$("#pBedText").css('display','inline');
        		$("#pBedLabel").css('display','inline');
        	}
        	
        	$("#hiddenDocId").val(docNo);
        	
        	$("#critical_alert").removeClass('alert-error');
        	$("#critical_title").html("");
    		$("#critical_info").html("");
    		$("#critical_time").html("");

        	if (data.mark == 6 && data.dgFlag == 0) {
        		$("#critical_div").css('display','block');
        		$("#critical_alert").addClass('alert-error');
        		$("#critical_title").html("该危机值未处理");
        	} else if (data.mark == 6 && data.dgFlag == 1) {
        		$("#critical_div").css('display','block');
        		$("#critical_info").html(data.dgInfo);
        		$("#critical_time").html(data.dgTime);
        		$("#critical_title").html("该危机值已处理");
        	} else {
        		$("#critical_div").css('display','none');
        	}
        	if (data.isDanger) {
        		$("#blh").children().css({
        			"color":"red"
        		});
        		$("#blh").css({
        			"-moz-animation":"twinkling 2s infinite ease-in-out"
        		});
        	} else {
        		$("#blh").css({
        			"-moz-animation":"none"
        		});
        	}
        }, "json");
		//getRelativeTests(ret.sample);
	}
	
	function getSample(sampleNo) {

        var lastsel;
        var cl = "";
        var isEdit = false;
        
        jQuery("#rowed3").jqGrid({
		   	url:"../audit/sample?id="+sampleNo,
			datatype: "json",
			shrinkToFit:true,
			autowidth:true,
			jsonReader : {repeatitems : false, userdata : "userdata"},  
		   	colNames:['ID','Color','英文缩写','项目', '结果', '历史', '历史', '历史', '历史', '历史', '测定时间', '机器号', '参考值', '单位','KNOWLEDGE','EDITMARK','LASTEDIT'],
		   	colModel:[
		   		{name:'id',index:'id',hidden:true},
		   		{name:'color',index:'color',hidden:true},
		   		{name:'ab',index:'ab',width:"20%",hidden:true},
		   		{name:'name',index:'name',width:"20%",sortable:false},
		   		{name:'result',index:'result',width:"8%",sortable:false,editable:true},
		   		{name:'last',index:'last',width:"8%",sortable:false},
		   		{name:'last1',index:'last1',width:"8%",sortable:false},
		   		{name:'last2',index:'last2',width:"8%",hidden:true,sortable:false},
		   		{name:'last3',index:'last3',width:"8%",hidden:true,sortable:false},
		   		{name:'last4',index:'last4',width:"8%",hidden:true,sortable:false},
		   		{name:'checktime',index:'checktime',width:"8%",hidden:true,sortable:false},
		   		{name:'device',index:'device',width:"8%",hidden:true,sortable:false},
		   		{name:'scope',index:'scope',width:"8%",sortable:false},
		   		{name:'unit', sortable:false, width:"6%",index:'unit'},
		   		{name:'knowledgeName',index:'knowledgeName',hidden:true},
		   		{name:'editMark',index:'editMark',hidden:true},
		   		{name:'lastEdit',index:'lastEdit',hidden:true}
		   	],
		   	height: "100%",
		   	rowNum: 100,
		   	rownumbers: true,
		    caption: "",
			onSelectRow: function(id) {
				$("#rbcLabel").css('display','none');
				if($("#needEdit").val() == "true") {
					if (lastsel) {
						if (lastsel == id) {
							if (!isEdit) {
								isEdit = true;
								var ret = jQuery("#rowed3").jqGrid('getRowData',id);
								var pre = "<div class='"+$(ret.result).attr('class')+"'>";
								cl = pre + $(ret.result).html() + "</div>";
								lastval = $(ret.result).find(":eq(0)").html();
								jQuery("#rowed3").jqGrid('setRowData', id, {result:lastval});
								jQuery("#rowed3").jqGrid('editRow',id, {
									keys:true,
									aftersavefunc:function() {
										var newVal = jQuery("#rowed3").jqGrid('getRowData',id);
										var hl = newVal.scope.split("-");
					        			var h = parseFloat(hl[1]);
					        			var l = parseFloat(hl[0]);
					        			var va = parseFloat(newVal.result.replace("<","").replace(">",""));
					        			var res = "";
					        			
					        			if (!isNaN(h) && !isNaN(l)) {
					        				if (!isNaN(va)) {
					        					if (va < l) {
						        					res = "<font color='red'>↓</font>";
						        				} else if (va > h) {
						        					res = "<font color='red'>↑</font>";
						        				}
					        				}
					        			}
										jQuery("#rowed3").jqGrid('setRowData', id, {
											result:pre + "<span class='result_span'>" + newVal.result + "</span>"+res+"</div>"
										});
										switch (id) {
											case '9046':
												//9051
												var rbc = parseFloat($(jQuery("#rowed3").jqGrid('getRowData','9045').result).find(":eq(0)").html());
												var newva = (va * rbc/100).toFixed(1);
												var newVal = jQuery("#rowed3").jqGrid('getRowData','9051');
												var hl = newVal.scope.split("-");
							        			var h = parseFloat(hl[1]);
							        			var l = parseFloat(hl[0]);
							        			var nva = parseFloat(newva);
							        			var res = "";
							        			
							        			if (!isNaN(h) && !isNaN(l)) {
							        				if (!isNaN(nva)) {
							        					if (nva < l) {
								        					res = "<font color='red'>↓</font>";
								        				} else if (nva > h) {
								        					res = "<font color='red'>↑</font>";
								        				}
							        				}
							        			}
												jQuery("#rowed3").jqGrid('setRowData', '9051', {
													result:"<div><span class='result_span'>" + nva + "</span>"+res+"</div>"
												});
												var val0 = parseFloat($(jQuery("#rowed3").jqGrid('getRowData','9047').result).find(":eq(0)").html());
												var val1 = parseFloat($(jQuery("#rowed3").jqGrid('getRowData','9048').result).find(":eq(0)").html());
												var val2 = parseFloat($(jQuery("#rowed3").jqGrid('getRowData','9049').result).find(":eq(0)").html());
												var val3 = parseFloat($(jQuery("#rowed3").jqGrid('getRowData','9050').result).find(":eq(0)").html());
												$("#rbctotal").html(va + val0 + val1 + val2 + val3);
												$("#rbcLabel").css('display','inline');
												break;
											case '9047':
												//9055
												var rbc = parseFloat($(jQuery("#rowed3").jqGrid('getRowData','9045').result).find(":eq(0)").html());
												var newva = (va * rbc/100).toFixed(1);
												var newVal = jQuery("#rowed3").jqGrid('getRowData','9055');
												var hl = newVal.scope.split("-");
							        			var h = parseFloat(hl[1]);
							        			var l = parseFloat(hl[0]);
							        			var nva = parseFloat(newva);
							        			var res = "";
							        			
							        			if (!isNaN(h) && !isNaN(l)) {
							        				if (!isNaN(nva)) {
							        					if (nva < l) {
								        					res = "<font color='red'>↓</font>";
								        				} else if (nva > h) {
								        					res = "<font color='red'>↑</font>";
								        				}
							        				}
							        			}
												jQuery("#rowed3").jqGrid('setRowData', '9055', {
													result:"<div><span class='result_span'>" + nva + "</span>"+res+"</div>"
												});
												var val0 = parseFloat($(jQuery("#rowed3").jqGrid('getRowData','9046').result).find(":eq(0)").html());
												var val1 = parseFloat($(jQuery("#rowed3").jqGrid('getRowData','9048').result).find(":eq(0)").html());
												var val2 = parseFloat($(jQuery("#rowed3").jqGrid('getRowData','9049').result).find(":eq(0)").html());
												var val3 = parseFloat($(jQuery("#rowed3").jqGrid('getRowData','9050').result).find(":eq(0)").html());
												$("#rbctotal").html(va + val0 + val1 + val2 + val3);
												$("#rbcLabel").css('display','inline');
												break;
											case '9048':
												//9089
												var rbc = parseFloat($(jQuery("#rowed3").jqGrid('getRowData','9045').result).find(":eq(0)").html());
												var newva = (va * rbc/100).toFixed(2);
												var newVal = jQuery("#rowed3").jqGrid('getRowData','9089');
												var hl = newVal.scope.split("-");
							        			var h = parseFloat(hl[1]);
							        			var l = parseFloat(hl[0]);
							        			var nva = parseFloat(newva);
							        			var res = "";
							        			
							        			if (!isNaN(h) && !isNaN(l)) {
							        				if (!isNaN(nva)) {
							        					if (nva < l) {
								        					res = "<font color='red'>↓</font>";
								        				} else if (nva > h) {
								        					res = "<font color='red'>↑</font>";
								        				}
							        				}
							        			}
												jQuery("#rowed3").jqGrid('setRowData', '9089', {
													result:"<div><span class='result_span'>" + nva + "</span>"+res+"</div>"
												});
												var val0 = parseFloat($(jQuery("#rowed3").jqGrid('getRowData','9046').result).find(":eq(0)").html());
												var val1 = parseFloat($(jQuery("#rowed3").jqGrid('getRowData','9047').result).find(":eq(0)").html());
												var val2 = parseFloat($(jQuery("#rowed3").jqGrid('getRowData','9049').result).find(":eq(0)").html());
												var val3 = parseFloat($(jQuery("#rowed3").jqGrid('getRowData','9050').result).find(":eq(0)").html());
												$("#rbctotal").html(va + val0 + val1 + val2 + val3);
												$("#rbcLabel").css('display','inline');
												break;
											case '9049':
												//9091
												var rbc = parseFloat($(jQuery("#rowed3").jqGrid('getRowData','9045').result).find(":eq(0)").html());
												var newva = (va * rbc/100).toFixed(2);
												var newVal = jQuery("#rowed3").jqGrid('getRowData','9091');
												var hl = newVal.scope.split("-");
							        			var h = parseFloat(hl[1]);
							        			var l = parseFloat(hl[0]);
							        			var nva = parseFloat(newva);
							        			var res = "";
							        			
							        			if (!isNaN(h) && !isNaN(l)) {
							        				if (!isNaN(nva)) {
							        					if (nva < l) {
								        					res = "<font color='red'>↓</font>";
								        				} else if (nva > h) {
								        					res = "<font color='red'>↑</font>";
								        				}
							        				}
							        			}
												jQuery("#rowed3").jqGrid('setRowData', '9091', {
													result:"<div><span class='result_span'>" + nva + "</span>"+res+"</div>"
												});
												var val0 = parseFloat($(jQuery("#rowed3").jqGrid('getRowData','9046').result).find(":eq(0)").html());
												var val1 = parseFloat($(jQuery("#rowed3").jqGrid('getRowData','9047').result).find(":eq(0)").html());
												var val2 = parseFloat($(jQuery("#rowed3").jqGrid('getRowData','9048').result).find(":eq(0)").html());
												var val3 = parseFloat($(jQuery("#rowed3").jqGrid('getRowData','9050').result).find(":eq(0)").html());
												$("#rbctotal").html(va + val0 + val1 + val2 + val3);
												$("#rbcLabel").css('display','inline');
												break;
											case '9050':
												//9090
												var rbc = parseFloat($(jQuery("#rowed3").jqGrid('getRowData','9045').result).find(":eq(0)").html());
												var newva = (va * rbc/100).toFixed(2);
												var newVal = jQuery("#rowed3").jqGrid('getRowData','9090');
												var hl = newVal.scope.split("-");
							        			var h = parseFloat(hl[1]);
							        			var l = parseFloat(hl[0]);
							        			var nva = parseFloat(newva);
							        			var res = "";
							        			
							        			if (!isNaN(h) && !isNaN(l)) {
							        				if (!isNaN(nva)) {
							        					if (nva < l) {
								        					res = "<font color='red'>↓</font>";
								        				} else if (nva > h) {
								        					res = "<font color='red'>↑</font>";
								        				}
							        				}
							        			}
												jQuery("#rowed3").jqGrid('setRowData', '9090', {
													result:"<div><span class='result_span'>" + nva + "</span>"+res+"</div>"
												});
												var val0 = parseFloat($(jQuery("#rowed3").jqGrid('getRowData','9046').result).find(":eq(0)").html());
												var val1 = parseFloat($(jQuery("#rowed3").jqGrid('getRowData','9047').result).find(":eq(0)").html());
												var val2 = parseFloat($(jQuery("#rowed3").jqGrid('getRowData','9048').result).find(":eq(0)").html());
												var val3 = parseFloat($(jQuery("#rowed3").jqGrid('getRowData','9049').result).find(":eq(0)").html());
												$("#rbctotal").html(va + val0 + val1 + val2 + val3);
												$("#rbcLabel").css('display','inline');
												break;
											default:
												$("#rbcLabel").css('display','none');
												break;
										}
										
										isEdit = false;
									}				
								});
							}
						} else {
							jQuery('#rowed3').jqGrid('restoreRow', lastsel);
							if (isEdit) {
								jQuery("#rowed3").jqGrid('setRowData', lastsel, {result:cl});
							}
							isEdit = false;
						}
					}
					lastsel=id;
				}
			},
			onRightClickRow: function(id) {
				var sample=$("#hiddenSampleNo").val();
				$.get("../audit/ajax/singleChart",{id:id, sample:sample},function(data){
					$("#chartDialog").dialog("open");
					$("#hmInfo").empty();
					for (var i=0; i< data.hmList.length; i++) {
						$("#hmInfo").append("<p>");
						$("#hmInfo").append((i+1) + ". ");
						$("#hmInfo").append(data.hmList[i].hmName);
						$("#hmInfo").append(" 批号:");
						$("#hmInfo").append(" " + data.hmList[i].hmBatch);
						$("#hmInfo").append(" 出库日期:");
						$("#hmInfo").append(" " + data.hmList[i].outtime);
						$("#hmInfo").append("</p>");
					}
					
					if (data.num > 1) {
						$("#tongji_max").html(data.max);
						$("#tongji_min").html(data.min);
						$("#tongji_mid").html(data.mid);
						$("#tongji_ave").html(data.ave);
						$("#tongji_sd").html(data.sd);
						$("#tongji_cov").html(data.cov);
						var xset = data.time;
						var yset1 = data.lo;
						var yset2 = data.re;
						var yset3 = data.hi;
						var chart = new Highcharts.Chart({ 
							title: {
								text: data.name
							},
							credits: {
						          enabled:false
							},
				            chart: {  
				                renderTo: 'singleChartPanel',  
				                type: 'line',  
				                marginRight: 130,  
				                marginBottom: 25  
				            },  
				            xAxis: {
				            	type: 'datetime',
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
				            legend: {
				                layout: 'vertical',
				                align: 'right',
				                verticalAlign: 'middle',
				                borderWidth: 0
				            }, 
				            series: [{
				                name: '低值',
				                data: yset1
				            }, {
				            	name: '检验结果',
				            	data: yset2 
				            }, {
				            	name: '高值',
				            	data: yset3 
				            }]  
				        });
					} else {
						alert('该检验项目没有历史记录或检验结果不是数值或无参考范围！');
					}
		 		});
			},
			loadComplete: function() {
				if ($("#sampleTitle").html() == "") {
//					$("#rowed3").jqGrid("setCaption", $("#sampleTitle").html());
				}
				var hisDate = jQuery("#rowed3").jqGrid("getGridParam", "userData").hisDate;
				var sameSample = jQuery("#rowed3").jqGrid("getGridParam", "userData").sameSample;
				var isLastYear = jQuery("#rowed3").jqGrid("getGridParam", "userData").isLastYear;
				var isLastTwoYear = jQuery("#rowed3").jqGrid("getGridParam", "userData").isLastTwoYear;
				$("#jqgh_rowed3_last").html("历史");
				$("#jqgh_rowed3_last1").html("历史");
				$("#jqgh_rowed3_last2").html("历史");
				$("#jqgh_rowed3_last3").html("历史");
				$("#jqgh_rowed3_last4").html("历史");
				$("#jqgh_rowed3_last").css('color','#000000');
				$("#jqgh_rowed3_last1").css('color','#000000');
				$("#jqgh_rowed3_last2").css('color','#000000');
				$("#jqgh_rowed3_last3").css('color','#000000');
				$("#jqgh_rowed3_last4").css('color','#000000');
				if (hisDate != null && hisDate != "") {
					var his = hisDate.split(",");
					if (his.length == 1) {
						$("#jqgh_rowed3_last").html(his[0].split(":")[0]);
						$("#jqgh_rowed3_last").attr('title',his[0].split(":")[1]);
					}else if (his.length == 2) {
						$("#jqgh_rowed3_last").html(his[0].split(":")[0]);
						$("#jqgh_rowed3_last").attr('title',his[0].split(":")[1]);
						$("#jqgh_rowed3_last1").html(his[1].split(":")[0]);
						$("#jqgh_rowed3_last1").attr('title',his[1].split(":")[1]);
					}else if (his.length == 3) {
						$("#jqgh_rowed3_last").html(his[0].split(":")[0]);
						$("#jqgh_rowed3_last").attr('title',his[0].split(":")[1]);
						$("#jqgh_rowed3_last1").html(his[1].split(":")[0]);
						$("#jqgh_rowed3_last1").attr('title',his[1].split(":")[1]);
						$("#jqgh_rowed3_last2").html(his[2].split(":")[0]);
						$("#jqgh_rowed3_last2").attr('title',his[2].split(":")[1]);
					} else if (his.length == 4) {
						$("#jqgh_rowed3_last").html(his[0].split(":")[0]);
						$("#jqgh_rowed3_last").attr('title',his[0].split(":")[1]);
						$("#jqgh_rowed3_last1").html(his[1].split(":")[0]);
						$("#jqgh_rowed3_last1").attr('title',his[1].split(":")[1]);
						$("#jqgh_rowed3_last2").html(his[2].split(":")[0]);
						$("#jqgh_rowed3_last2").attr('title',his[2].split(":")[1]);
						$("#jqgh_rowed3_last3").html(his[3].split(":")[0]);
						$("#jqgh_rowed3_last3").attr('title',his[3].split(":")[1]);
					} else {
						$("#jqgh_rowed3_last").html(his[0].split(":")[0]);
						$("#jqgh_rowed3_last").attr('title',his[0].split(":")[1]);
						$("#jqgh_rowed3_last1").html(his[1].split(":")[0]);
						$("#jqgh_rowed3_last1").attr('title',his[1].split(":")[1]);
						$("#jqgh_rowed3_last2").html(his[2].split(":")[0]);
						$("#jqgh_rowed3_last2").attr('title',his[2].split(":")[1]);
						$("#jqgh_rowed3_last3").html(his[3].split(":")[0]);
						$("#jqgh_rowed3_last3").attr('title',his[3].split(":")[1]);
						$("#jqgh_rowed3_last4").html(his[4].split(":")[0]);
						$("#jqgh_rowed3_last4").attr('title',his[4].split(":")[1]);
					}
					
					if (isLastYear == 1) {
						$("#jqgh_rowed3_last4").css('color','#8F8F8F');
					} else if (isLastYear == 2) {
						$("#jqgh_rowed3_last3").css('color','#8F8F8F');
						$("#jqgh_rowed3_last4").css('color','#8F8F8F');
					}  else if (isLastYear == 3) {
						$("#jqgh_rowed3_last2").css('color','#8F8F8F');
						$("#jqgh_rowed3_last3").css('color','#8F8F8F');
						$("#jqgh_rowed3_last4").css('color','#8F8F8F');
					}  else if (isLastYear == 4) {
						$("#jqgh_rowed3_last1").css('color','#8F8F8F');
						$("#jqgh_rowed3_last2").css('color','#8F8F8F');
						$("#jqgh_rowed3_last3").css('color','#8F8F8F');
						$("#jqgh_rowed3_last4").css('color','#8F8F8F');
					}  else if (isLastYear == 5) {
						$("#jqgh_rowed3_last").css('color','#8F8F8F');
						$("#jqgh_rowed3_last1").css('color','#8F8F8F');
						$("#jqgh_rowed3_last2").css('color','#8F8F8F');
						$("#jqgh_rowed3_last3").css('color','#8F8F8F');
						$("#jqgh_rowed3_last4").css('color','#8F8F8F');
					}
					
					if (isLastTwoYear == 1) {
						$("#jqgh_rowed3_last4").css('color','#CFCFCF');
					} else if (isLastTwoYear == 2) {
						$("#jqgh_rowed3_last3").css('color','#CFCFCF');
						$("#jqgh_rowed3_last4").css('color','#CFCFCF');
					}  else if (isLastTwoYear == 3) {
						$("#jqgh_rowed3_last2").css('color','#CFCFCF');
						$("#jqgh_rowed3_last3").css('color','#CFCFCF');
						$("#jqgh_rowed3_last4").css('color','#CFCFCF');
					}  else if (isLastTwoYear == 4) {
						$("#jqgh_rowed3_last1").css('color','#CFCFCF');
						$("#jqgh_rowed3_last2").css('color','#CFCFCF');
						$("#jqgh_rowed3_last3").css('color','#CFCFCF');
						$("#jqgh_rowed3_last4").css('color','#CFCFCF');
					}  else if (isLastTwoYear == 5) {
						$("#jqgh_rowed3_last").css('color','#CFCFCF');
						$("#jqgh_rowed3_last1").css('color','#CFCFCF');
						$("#jqgh_rowed3_last2").css('color','#CFCFCF');
						$("#jqgh_rowed3_last3").css('color','#CFCFCF');
						$("#jqgh_rowed3_last4").css('color','#CFCFCF');
					}
				}
				//alert($("#rowed3").jqGrid("getCaption"));
				$.each(jQuery('#rowed3').jqGrid('getCol','id', false), function(k,v) {
        			var ret = jQuery("#rowed3").jqGrid('getRowData',v);
        			if (ret.last != null && ret.last != "")
        				$("#hisLastResult").val(1);
        			else
        				$("#hisLastResult").val(0);
        			var hl = ret.scope.split("-");
        			var h = parseFloat(hl[1]);
        			var l = parseFloat(hl[0]);
        			var color = "<div class='";
        			if (ret.color == 1) {
        				color += "diff_td'>";
        			} else if (ret.color == 2) {
        				color += "ratio_td'>";
        			} else if (ret.color == 3) {
        				color += "dan_td'>";
        			} else if (ret.color == 4) {
        				color += "re_td'>";
        			} else if (ret.color == 5) {
        				color += "al2_td'>";
        			} else if (ret.color == 6) {
        				color += "al3_td'>";
        			} else if (ret.color == 7) {
        				color += "ex_td'>";
        			} else {
        				color += "'>";
        			}
        			
        			jQuery("#rowed3").jqGrid('setRowData', v, {
    					name:"<a href='javascript:show_knowledge(\""+ret.knowledgeName+"\")'>"+ret.name+"</a>",
    					ab:"<a href='javascript:show_knowledge(\""+ret.knowledgeName+"\")'>"+ret.ab+"</a>"
    				});
        			
        			if (hl.length != 2) {
        				jQuery("#rowed3").jqGrid('setRowData', v, {
        					result:color+"<span class='result_span'>"+ret.result+"</span></div>"
        				});
        				return true;
        			}
        			
        			var va = parseFloat(ret.result.replace("<","").replace(">",""));
        			var la = parseFloat(ret.last.replace("<","").replace(">",""));
        			var la1 = parseFloat(ret.last1.replace("<","").replace(">",""));
        			var la2 = parseFloat(ret.last2.replace("<","").replace(">",""));
        			var la3 = parseFloat(ret.last3.replace("<","").replace(">",""));
        			var la4 = parseFloat(ret.last4.replace("<","").replace(">",""));
        			var res = "";
        			var res1 = "";
        			var res2 = "";
        			var res3 = "";
        			var res4 = "";
        			var res5 = "";
        			
        			if (!isNaN(h) && !isNaN(l)) {
        				if (!isNaN(va)) {
        					if (va < l) {
	        					res = "<font color='red'>↓</font>";
	        				} else if (va > h) {
	        					res = "<font color='red'>↑</font>";
	        				}
        				}
        				
        				if (!isNaN(la)) {
        					if (la < l) {
	        					res1 = "<font color='red'>↓</font>";
	        				} else if (la > h) {
	        					res1 = "<font color='red'>↑</font>";
	        				}
        				}
        				
        				if (!isNaN(la1)) {
        					if (la1 < l) {
	        					res2 = "<font color='red'>↓</font>";
	        				} else if (la1 > h) {
	        					res2 = "<font color='red'>↑</font>";
	        				}
        				}
        				
        				if (!isNaN(la2)) {
        					if (la2 < l) {
	        					res3 = "<font color='red'>↓</font>";
	        				} else if (la2 > h) {
	        					res3 = "<font color='red'>↑</font>";
	        				}
        				}
        				
        				if (!isNaN(la3)) {
        					if (la3 < l) {
	        					res4 = "<font color='red'>↓</font>";
	        				} else if (la3 > h) {
	        					res4 = "<font color='red'>↑</font>";
	        				}
        				}
        				
        				if (!isNaN(la4)) {
        					if (la4 < l) {
	        					res5 = "<font color='red'>↓</font>";
	        				} else if (la4 > h) {
	        					res5 = "<font color='red'>↑</font>";
	        				}
        				}
        			}
        			
					if (ret.editMark != 0 && ret.editMark % 33 == 0) {
        				jQuery("#rowed3").jqGrid('setRowData', v, {
        					result:color+"<span class='result_span'>"+ret.result+"</span>"+res+"</div>",
        					last:"<span class='last_span'>" + ret.last + "</span>"+res1,
        					last1:"<span class='last_span'>" + ret.last1 + "</span>"+res2,
        					last2:"<span class='last_span'>" + ret.last2 + "</span>"+res3,
        					last3:"<span class='last_span'>" + ret.last3 + "</span>"+res4,
        					last4:"<span class='last_span'>" + ret.last4 + "</span>"+res5
        				});
					} else {
						jQuery("#rowed3").jqGrid('setRowData', v, {
							result:color+"<span class='result_span'>"+ret.result+"</span>"+res+"</div>",
							last:"<span class='last_span'>" + ret.last + "</span>"+res1,
        					last1:"<span class='last_span'>" + ret.last1 + "</span>"+res2,
        					last2:"<span class='last_span'>" + ret.last2 + "</span>"+res3,
        					last3:"<span class='last_span'>" + ret.last3 + "</span>"+res4,
        					last4:"<span class='last_span'>" + ret.last4 + "</span>"+res5
						});
					}
					
					$('#' + v).find("td:eq(5)").attr("title",ret.lastEdit);
					if(ret.lastEdit.indexOf(" ") > 0) {
						$('#' + v).find("td:eq(13)").css("background-color","#d9edf7");
					}
        		}); 
				if (sameSample != null && sameSample != "" && ($("#lastDepLib").val() == '1300600' || $("#lastDepLib").val() == '1300101')) {
					alert( '该样本与' + sameSample + '可能为同一样本');
				}
			},
			editurl: "../audit/edit?sampleNo=" + sampleNo
		});
        
	}
	
	function getTwoSample(sampleNo) {
		var array = new Array();
		$.ajaxSetup({
			async:false
		});
		$.get("../audit/twosample", {id:sampleNo}, function(data){
			for(var i=0; i< data.length; i++) {
				array[i] = data[i];
			}
		});
//		alert(array[0].userdata);
		getSample0(sampleNo, array[0].userdata, array[0].rows);
		getSample1(sampleNo, array[0].userdata, array[1].rows);
	}
	
	function getSample0(sampleNo, userdata, mydata) {

        var lastsel;
        var cl = "";
        var isEdit = false;
		jQuery("#sample0").jqGrid({
		   	data:mydata,
			datatype: "local",
			shrinkToFit:true,
			jsonReader : {repeatitems : false, userdata : userdata},  
		   	colNames:['ID','Color','缩写','项目', '结果', '历史', '历史', '仪器号', '参考范围', '单位','KNOWLEDGE','EDITMARK','LASTEDIT'],
		   	colModel:[
		   		{name:'id',index:'id', hidden:true},
		   		{name:'color',index:'color', hidden:true},
		   		{name:'ab',index:'ab',width:85,hidden:true},
		   		{name:'name',index:'name',width:85,sortable:false},
		   		{name:'result',index:'result',width:60, sortable:false, editable:true},
		   		{name:'last',index:'last',width:40, sortable:false},
		   		{name:'last1',index:'last1',width:40, sortable:false},
		   		{name:'device',index:'device',width:50, hidden:true, sortable:false},
		   		{name:'scope',index:'scope',width:65,sortable:false},
		   		{name:'unit', sortable:false, width:40, index:'unit'},
		   		{name:'knowledgeName',index:'knowledgeName', hidden:true},
		   		{name:'editMark',index:'editMark',hidden:true},
		   		{name:'lastEdit',index:'lastEdit',hidden:true}
		   	],
		   	height: "100%",
		   	rowNum: 50,
		    caption: "",
			onSelectRow: function(id) {
				
				if($("#needEdit").val() == "true") {
					if (lastsel) {
						if (lastsel == id) {
							if (!isEdit) {
								isEdit = true;
								
								var ret = jQuery("#sample0").jqGrid('getRowData',id);
								alert(ret.result);
								var pre = "<div class='"+$(ret.result).attr('class')+"'>";
								cl = pre + $(ret.result).html() + "</div>";
								lastval = $(ret.result).find(":eq(0)").html();
								jQuery("#sample0").jqGrid('setRowData', id, {result:lastval});
								jQuery("#sample0").jqGrid('editRow',id, {
									keys:true,
									aftersavefunc:function() {
										var newVal = jQuery("#sample0").jqGrid('getRowData',id);
										var hl = newVal.scope.split("-");
					        			var h = parseFloat(hl[1]);
					        			var l = parseFloat(hl[0]);
					        			var va = parseFloat(newVal.result.replace("<","").replace(">",""));
					        			var res = "";
					        			
					        			if (!isNaN(h) && !isNaN(l) && !isNaN(va)) {
					        				if (va < l) {
					        					res = "<font color='red'>\u2193</font>";
					        				} else if (va > h) {
					        					res = "<font color='red'>\u2191</font>";
					        				}
					        			}
										jQuery("#sample0").jqGrid('setRowData', id, {result:pre + "<span class='two_result_span'>" + newVal.result + "</span>"+res+"</div>"});
										isEdit = false;
									}				
								});
							}
						} else {
							jQuery('#sample0').jqGrid('restoreRow', lastsel);
							if (isEdit) {
								jQuery("#sample0").jqGrid('setRowData', lastsel, {result:cl});
							}
							isEdit = false;
						}
					}
					lastsel=id;
				}
				
				var sample1_selected = jQuery("#sample1").jqGrid('getGridParam','selrow');
				if(sample1_selected!=null) {
					jQuery("#sample1").jqGrid("resetSelection", sample1_selected);
				}
			},
			loadComplete: function() {
				if ($("#sampleTitle").html() == "") {
//					$("#sample0").jqGrid("setCaption", $("#sampleTitle").html());
				}
				var hisDate = jQuery("#sample0").jqGrid("getGridParam", "userData").hisDate;
				var sameSample = jQuery("#sample0").jqGrid("getGridParam", "userData").sameSample;
				var isLastYear = jQuery("#sample0").jqGrid("getGridParam", "userData").isLastYear;
				$("#jqgh_sample0_last").css('color','#000000');
				$("#jqgh_sample0_last1").css('color','#000000');
				if (hisDate != null && hisDate != "") {
					var his = hisDate.split(",");
					if (his.length == 1) {
						$("#jqgh_sample0_last").html(his[0].split(":")[0]);
						$("#jqgh_sample0_last").attr('title',his[0].split(":")[1]);
					}else {
						$("#jqgh_sample0_last").html(his[0].split(":")[0]);
						$("#jqgh_sample0_last").attr('title',his[0].split(":")[1]);
						$("#jqgh_sample0_last1").html(his[1].split(":")[0]);
						$("#jqgh_sample0_last1").attr('title',his[1].split(":")[1]);
					}
					if (isLastYear == 1) {
						$("#jqgh_sample0_last1").css('color','#8F8F8F');
					} else if (isLastYear == 2) {
						$("#jqgh_sample0_last").css('color','#8F8F8F');
						$("#jqgh_sample0_last1").css('color','#8F8F8F');
					}
					
				}
				$.each(jQuery('#sample0').jqGrid('getCol','id', false), function(k,v) {
        			var ret = jQuery("#sample0").jqGrid('getRowData',v);
        			if (ret.last != null && ret.last != "")
        				$("#hisLastResult").val(1);
        			else
        				$("#hisLastResult").val(0);
        			var hl = ret.scope.split("-");
        			var h = parseFloat(hl[1]);
        			var l = parseFloat(hl[0]);
        			var color = "<div class='";
        			if (ret.color == 1) {
        				color += "diff_td'>";
        			} else if (ret.color == 2) {
        				color += "ratio_td'>";
        			} else if (ret.color == 3) {
        				color += "dan_td'>";
        			} else if (ret.color == 4) {
        				color += "re_td'>";
        			} else if (ret.color == 5) {
        				color += "al2_td'>";
        			} else if (ret.color == 6) {
        				color += "al3_td'>";
        			} else if (ret.color == 7) {
        				color += "ex_td'>";
        			} else {
        				color += "'>";
        			}
        			
        			jQuery("#sample0").jqGrid('setRowData', v, {
    					name:"<a href='javascript:show_knowledge(\""+ret.knowledgeName+"\")'>"+ret.name+"</a>",
    					ab:"<a href='javascript:show_knowledge(\""+ret.knowledgeName+"\")'>"+ret.ab+"</a>"
    				});
        			
        			if (hl.length != 2) {
        				jQuery("#sample0").jqGrid('setRowData', v, {
        					result:color+"<span class='two_result_span'>"+ret.result+"</span></div>"
        				});
        				return true;
        			}
        			
        			var va = parseFloat(ret.result.replace("<","").replace(">",""));
        			var la = parseFloat(ret.last.replace("<","").replace(">",""));
        			var la1 = parseFloat(ret.last1.replace("<","").replace(">",""));
        			var res = "";
        			var res1 = "";
        			var res2 = "";
        			
        			if (!isNaN(h) && !isNaN(l)) {
        				if (!isNaN(va)) {
        					if (va < l) {
	        					res = "<font color='red'>\u2193</font>";
	        				} else if (va > h) {
	        					res = "<font color='red'>\u2191</font>";
	        				}
        				}
        				
        				if (!isNaN(la)) {
        					if (la < l) {
	        					res1 = "<font color='red'>\u2193</font>";
	        				} else if (la > h) {
	        					res1 = "<font color='red'>\u2191</font>";
	        				}
        				}
        				
        				if (!isNaN(la1)) {
        					if (la1 < l) {
	        					res2 = "<font color='red'>\u2193</font>";
	        				} else if (la1 > h) {
	        					res2 = "<font color='red'>\u2191</font>";
	        				}
        				}
        			}
        			
					if (ret.editMark != 0 && ret.editMark % 33 == 0) {
        				jQuery("#sample0").jqGrid('setRowData', v, {
        					result:color+"<span class='two_result_span'>"+$(ret.result).find(":eq(0)")+"</span>"+res+"</div>",
        					last:"<span class='two_last_span'>" + $(ret.last).find(":eq(0)") + "</span>"+res1,
        					last1:"<span class='two_last_span'>" + $(ret.last1).find(":eq(0)") + "</span>"+res2
        				});
					} else {
						jQuery("#sample0").jqGrid('setRowData', v, {
							result:color+"<span class='two_result_span'>"+$(ret.result).find(":eq(0)")+"</span>"+res+"</div>",
							last:"<span class='two_last_span'>" + $(ret.last).find(":eq(0)") + "</span>"+res1,
        					last1:"<span class='two_last_span'>" + $(ret.last1).find(":eq(0)") + "</span>"+res2
						});
					}
					
					$('#' + v).find("td:eq(4)").attr("title",ret.lastEdit);
					if(ret.lastEdit.indexOf(" ") > 0) {
						$('#' + v).find("td:eq(8)").css("background-color","#d9edf7");
					}
        		}); 
				if (sameSample != null && sameSample != "" && ($("#lastDepLib").val() == '1300600' || $("#lastDepLib").val() == '1300101')) {
					alert( '该样本与' + sameSample + '可能为同一样本');
				}
			},
			editurl: "../audit/edit?sampleNo=" + sampleNo
		});
		
		$("#gbox_sample0").css('float','left');
	}
	
	function getSample1(sampleNo, userdata, mydata) {

        var lastsel;
        var cl = "";
        var isEdit = false;
		jQuery("#sample1").jqGrid({
		   	data: mydata,
			datatype: "local",
			shrinkToFit:true,
			jsonReader : {repeatitems : false, userdata : userdata},  
		   	colNames:['ID','Color','缩写','项目', '结果', '历史', '历史', '仪器号', '参考范围', '单位','KNOWLEDGE','EDITMARK','LASTEDIT'],
		   	colModel:[
		   	   	{name:'id',index:'id', hidden:true},
		   		{name:'color',index:'color', hidden:true},
		   		{name:'ab',index:'ab',width:85,hidden:true},
		   		{name:'name',index:'name',width:85,sortable:false},
		   		{name:'result',index:'result',width:60, sortable:false, editable:true},
		   		{name:'last',index:'last',width:40, sortable:false},
		   		{name:'last1',index:'last1',width:40, sortable:false},
		   		{name:'device',index:'device',width:50, hidden:true, sortable:false},
		   		{name:'scope',index:'scope',width:65,sortable:false},
		   		{name:'unit', sortable:false, width:40, index:'unit'},
		   		{name:'knowledgeName',index:'knowledgeName', hidden:true},
		   		{name:'editMark',index:'editMark',hidden:true},
		   		{name:'lastEdit',index:'lastEdit',hidden:true}
		   	],
		   	height: "100%",
		   	
		   	rowNum: 50,
		    caption: "",
			onSelectRow: function(id) {
				
				if($("#needEdit").val() == "true") {
					if (lastsel) {
						if (lastsel == id) {
							if (!isEdit) {
								isEdit = true;
								var ret = jQuery("#sample1").jqGrid('getRowData',id);
								var pre = "<div class='"+$(ret.result).attr('class')+"'>";
								cl = pre + $(ret.result).html() + "</div>";
								lastval = $(ret.result).find(":eq(0)").html();
								jQuery("#sample1").jqGrid('setRowData', id, {result:lastval});
								jQuery("#sample1").jqGrid('editRow',id, {
									keys:true,
									aftersavefunc:function() {
										var newVal = jQuery("#sample1").jqGrid('getRowData',id);
										var hl = newVal.scope.split("-");
					        			var h = parseFloat(hl[1]);
					        			var l = parseFloat(hl[0]);
					        			var va = parseFloat(newVal.result.replace("<","").replace(">",""));
					        			var res = "";
					        			
					        			if (!isNaN(h) && !isNaN(l) && !isNaN(va)) {
					        				if (va < l) {
					        					res = "<font color='red'>\u2193</font>";
					        				} else if (va > h) {
					        					res = "<font color='red'>\u2191</font>";
					        				}
					        			}
										jQuery("#sample1").jqGrid('setRowData', id, {result:pre + "<span class='two_result_span'>" + newVal.result + "</span>"+res+"</div>"});
										isEdit = false;
									}				
								});
							}
						} else {
							jQuery('#sample1').jqGrid('restoreRow', lastsel);
							if (isEdit) {
								jQuery("#sample1").jqGrid('setRowData', lastsel, {result:cl});
							}
							isEdit = false;
						}
					}
					lastsel=id;
				}
				
				var sample0_selected = jQuery("#sample0").jqGrid('getGridParam','selrow');
				if(sample0_selected!=null) {
					jQuery("#sample0").jqGrid("resetSelection", sample0_selected);
				}
			},
			loadComplete: function() {
				var hisDate = jQuery("#sample1").jqGrid("getGridParam", "userData").hisDate;
				var isLastYear = jQuery("#sample1").jqGrid("getGridParam", "userData").isLastYear;
				$("#jqgh_sample1_last").css('color','#000000');
				$("#jqgh_sample1_last1").css('color','#000000');
				if (hisDate != null && hisDate != "") {
					var his = hisDate.split(",");
					if (his.length == 1) {
						$("#jqgh_sample1_last").html(his[0].split(":")[0]);
						$("#jqgh_sample1_last").attr('title',his[0].split(":")[1]);
					}else {
						$("#jqgh_sample1_last").html(his[0].split(":")[0]);
						$("#jqgh_sample1_last").attr('title',his[0].split(":")[1]);
						$("#jqgh_sample1_last1").html(his[1].split(":")[0]);
						$("#jqgh_sample1_last1").attr('title',his[1].split(":")[1]);
					}
					if (isLastYear == 1) {
						$("#jqgh_sample1_last1").css('color','#8F8F8F');
					} else if (isLastYear == 2) {
						$("#jqgh_sample1_last").css('color','#8F8F8F');
						$("#jqgh_sample1_last1").css('color','#8F8F8F');
					}
				}
				$.each(jQuery('#sample1').jqGrid('getCol','id', false), function(k,v) {
        			var ret = jQuery("#sample1").jqGrid('getRowData',v);
        			if (ret.last != null && ret.last != "")
        				$("#hisLastResult").val(1);
        			else
        				$("#hisLastResult").val(0);
        			var hl = ret.scope.split("-");
        			var h = parseFloat(hl[1]);
        			var l = parseFloat(hl[0]);
        			var color = "<div class='";
        			if (ret.color == 1) {
        				color += "diff_td'>";
        			} else if (ret.color == 2) {
        				color += "ratio_td'>";
        			} else if (ret.color == 3) {
        				color += "dan_td'>";
        			} else if (ret.color == 4) {
        				color += "re_td'>";
        			} else if (ret.color == 5) {
        				color += "al2_td'>";
        			} else if (ret.color == 6) {
        				color += "al3_td'>";
        			} else if (ret.color == 7) {
        				color += "ex_td'>";
        			} else {
        				color += "'>";
        			}
        			
        			jQuery("#sample1").jqGrid('setRowData', v, {
    					name:"<a href='javascript:show_knowledge(\""+ret.knowledgeName+"\")'>"+ret.name+"</a>",
    					ab:"<a href='javascript:show_knowledge(\""+ret.knowledgeName+"\")'>"+ret.ab+"</a>"
    				});
        			
        			if (hl.length != 2) {
        				jQuery("#sample1").jqGrid('setRowData', v, {
        					result:color+"<span class='two_result_span'>"+ret.result+"</span></div>"
        				});
        				return true;
        			}
        			
        			var va = parseFloat(ret.result.replace("<","").replace(">",""));
        			var la = parseFloat(ret.last.replace("<","").replace(">",""));
        			var la1 = parseFloat(ret.last1.replace("<","").replace(">",""));
        			var res = "";
        			var res1 = "";
        			var res2 = "";
        			
        			if (!isNaN(h) && !isNaN(l)) {
        				if (!isNaN(va)) {
        					if (va < l) {
	        					res = "<font color='red'>\u2193</font>";
	        				} else if (va > h) {
	        					res = "<font color='red'>\u2191</font>";
	        				}
        				}
        				
        				if (!isNaN(la)) {
        					if (la < l) {
	        					res1 = "<font color='red'>\u2193</font>";
	        				} else if (la > h) {
	        					res1 = "<font color='red'>\u2191</font>";
	        				}
        				}
        				
        				if (!isNaN(la1)) {
        					if (la1 < l) {
	        					res2 = "<font color='red'>\u2193</font>";
	        				} else if (la1 > h) {
	        					res2 = "<font color='red'>\u2191</font>";
	        				}
        				}
        			}
        			
        			if (ret.editMark != 0 && ret.editMark % 33 == 0) {
        				jQuery("#sample0").jqGrid('setRowData', v, {
        					result:color+"<span class='two_result_span'>"+$(ret.result).find(":eq(0)")+"</span>"+res+"</div>",
        					last:"<span class='two_last_span'>" + $(ret.last).find(":eq(0)") + "</span>"+res1,
        					last1:"<span class='two_last_span'>" + $(ret.last1).find(":eq(0)") + "</span>"+res2
        				});
					} else {
						jQuery("#sample0").jqGrid('setRowData', v, {
							result:color+"<span class='two_result_span'>"+$(ret.result).find(":eq(0)")+"</span>"+res+"</div>",
							last:"<span class='two_last_span'>" + $(ret.last).find(":eq(0)") + "</span>"+res1,
        					last1:"<span class='two_last_span'>" + $(ret.last1).find(":eq(0)") + "</span>"+res2
						});
					}
					
					$('#' + v).find("td:eq(4)").attr("title",ret.lastEdit);
					if(ret.lastEdit.indexOf(" ") > 0) {
						$('#' + v).find("td:eq(8)").css("background-color","#d9edf7");
					}
        		}); 
			},
			editurl: "../audit/edit?sampleNo=" + sampleNo
		});
		
		$("#gbox_sample1").css('float','left');
		$("#gbox_sample1").css('margin-left','5px');
	}