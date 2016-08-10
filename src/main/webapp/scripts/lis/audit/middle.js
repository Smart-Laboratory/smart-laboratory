	function getRelativeTests(sample){
		$("#relative-tests").html(" ");
		$.get("../audit/ajax/relativeTest",{sample:sample}, function(data) {
			if(data != "") {
				data = jQuery.parseJSON(data);
				$("#relative-tests").html(data.html);
			}
		});
	}
	
	function twoSampleReload(sample){
		var array = [];
		$.ajaxSetup({
			async:false
		});
		$.get("../audit/twosample", {id:sample}, function(data){
			for(var i=0; i< data.length; i++) {
				array[i] = data[i];
			}
		});
		jQuery("#sample0").jqGrid("clearGridData");
		jQuery("#sample1").jqGrid("clearGridData");
			
		jQuery("#sample0").jqGrid("setGridParam",{
			data:array[0].rows,
			userdata:array[0].userdata,
			editurl: "../audit/edit?sampleNo=" + sample
		}).trigger("reloadGrid");
		jQuery("#sample1").jqGrid("setGridParam",{
			data:array[1].rows,
			userdata:array[0].userdata,
			editurl: "../audit/edit?sampleNo=" + sample
		}).trigger("reloadGrid");
	}
	
	var isFirst = true;
	function getPatient(ret) {
 		var docNo = ret.id;
		$.get("../audit/patient",{id:docNo},function(data){
			if (data.isOverTime) {
				$("#tatBtn").html("<b style='color: #FF4500;'>TAT超时</b>");
			} else {
				$("#tatBtn").html("<b>TAT</b>");
			}
			
    		if(data.size > 30 && $("#oneColumnShowBtn").prop("checked") == false && $('#historyTabs').find('li.active a').attr('href') == "#tabs-1") {
    			$("#twosampleTable").css('display','block');
        		$("#patientRow").css('display','none');
        		if(isFirst){
    				getSample(ret.sample);
    				getTwoSample(ret.sample);
    				isFirst = false;
    			}
    			else{
    				twoSampleReload(ret.sample);
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
			
			var activeTab = $('#historyTabs').find('li.active a').attr('href');
    		if (activeTab == "#tabs-0") {
    			$("#patientRow").css('display','block');
    			$("#twosampleTable").css('display','none');
    			jQuery("#audit_information").jqGrid("setGridParam",{
   					url:"../audit/explain?id="+ret.id,
   					editurl: "../audit/explain/edit?docNo=" + ret.id
   				}).trigger("reloadGrid");
    		} else if (activeTab == "#tabs-1") {
				jQuery("#rowed3").setGridParam().showCol("last2");
				jQuery("#rowed3").setGridParam().showCol("last3");
				jQuery("#rowed3").setGridParam().showCol("last4");
				//jQuery("#rowed3").setGridParam().showCol("device");
				jQuery("#rowed3").setGridParam().showCol("unit");
    		} else {
    			$("#patientRow").css('display','block');
    			$("#twosampleTable").css('display','none');
    			getSopSchedule($("#labSelect").val());
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
        	$("#pName").html("<a href='../manage/patientList?patientId=" + data.patientId + "&blh=" + data.blh + "' target='_blank'>" + data.name + "</a>");
        	$("#pAge").html(data.age);
        	$("#blh").html("<a href='http://192.168.17.102/ZWEMR/SysLogin.aspx?lcation=inside&ly=D&edt=N&pid=" + data.blh + "&gh=" + data.requester + "' target='_blank'>" + data.blh + "</a>");
        	$("#doctadviseno").html(data.id);
        	$("#pSex").html(data.sex);
        	$("#pSection").html(data.section);
        	//$("#pSection").attr("title",data.section);
        	$("#pType").html(data.type);
        	$("#stayhospitalmode").html(data.stayhospitalmode);
        	if(data.diagnosticKnow == "") {
        		$("#diagnostic").html(data.diagnostic);
        	} else {
        		$("#diagnostic").html("<a href='#' onclick='show_knowledge(\""+data.diagnosticKnow+"\")'>"+data.diagnostic+"</a>");
        	}
        	$("#diagnosisValue").val(data.diagnostic);
        	if(data.description != null) {
        		var reason = data.description.replace(/<p>/g,"").replace(/<\/p>/g,";");
            	$("#passreason").html(reason);
            	$("#passLabel").css('display','block');
        	} else {
        		$("#passLabel").css('display','none');
        	}
        	
        	if(data.bed == null){
        		$("#pBedHtml").css('display','none');
        		$("#pBedLabel").css('display','none');
        		$("#pDiaHtml").attr('class','col-sm-8 pinfo');
        		$("#pDiaHtml span").attr('class','col-sm-2');
        	}else{
        		$("#pBed").html(data.bed);
        		$("#pDiaHtml span").attr('class','col-sm-3');
        		$("#pDiaHtml").attr('class','col-sm-5 pinfo');
        		$("#pBedHtml").css('display','inline');
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
		getRelativeTests(ret.sample);
	}
	
	function getSample(sampleNo) {

        var lastsel;
        var cl = "";
        var isEdit = false;
        var width = $("#mid").width();
        jQuery("#rowed3").jqGrid({
		   	url:"../audit/sample?id="+sampleNo,
			datatype: "json",
			width:width,
			jsonReader : {repeatitems : false, userdata : "userdata"},  
		   	colNames:['ID','Color','英文缩写','项目', '结果', '历史', '历史', '历史', '历史', '历史', '测定时间', '机器号', '参考值', '单位','KNOWLEDGE','EDITMARK','LASTEDIT'],
		   	colModel:[
		   		{name:'id',index:'id',hidden:true},
		   		{name:'color',index:'color',hidden:true},
		   		{name:'ab',index:'ab',width:width*0.15,hidden:true},
		   		{name:'name',index:'name',width:width*0.15,sortable:false},
		   		{name:'result',index:'result',width:width*0.08,sortable:false,editable:true},
		   		{name:'last',index:'last',width:width*0.08,sortable:false},
		   		{name:'last1',index:'last1',width:width*0.08,sortable:false},
		   		{name:'last2',index:'last2',width:width*0.08,sortable:false},
		   		{name:'last3',index:'last3',width:width*0.09,sortable:false},
		   		{name:'last4',index:'last4',width:width*0.08,sortable:false},
		   		{name:'checktime',index:'checktime',width:width*0.08,sortable:false},
		   		{name:'device',index:'device',width:width*0.08,sortable:false},
		   		{name:'scope',index:'scope',width:width*0.09,sortable:false},
		   		{name:'unit', sortable:false, width:width*0.08,index:'unit'},
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
										$("#modifyBtn").css('display','inline');
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
				//关闭浏览器右键
				document.oncontextmenu=function(){return false;};
				var sample=$("#hiddenSampleNo").val();
				$.get("../audit/ajax/singleChart",{id:id, sample:sample},function(data){
					$("#chartAlert").css("display","none");
					openChartDialog();
					$("#hmInfo").empty();
					for (var i=0; i< data.hmList.length; i++) {
						$("#hmInfo").append(data.hmList[i]);
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
						$("#chartAlert").css("display","block");
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
					$("#hisLastResult").val(1);
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
				} else {
					$("#hisLastResult").val(0);
				}
				$.each(jQuery('#rowed3').jqGrid('getCol','id', false), function(k,v) {
        			var ret = jQuery("#rowed3").jqGrid('getRowData',v);
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
		var array = [];
		$.ajaxSetup({
			async:false
		});
		$.get("../audit/twosample", {id:sampleNo}, function(data){
			for(var i=0; i< data.length; i++) {
				array[i] = data[i];
			}
		});
		getSample0(sampleNo, array[0].userdata, array[0]);
		getSample1(sampleNo, array[0].userdata, array[1].rows);
		
		
	}
	
	function getSample0(sampleNo, userdata, mydata) {

        var lastsel;
        var cl = "";
        var isEdit = false;
        var width = $("#mid").width()*0.48;
		jQuery("#sample0").jqGrid({
		   	data:mydata.rows,
			datatype: "local",
			width:width,
			jsonReader : {repeatitems : false,  userdata : userdata},  
		   	colNames:['ID','Color','缩写','项目', '结果', '历史', '历史', '仪器号', '参考范围', '测定时间', '单位','KNOWLEDGE','EDITMARK','LASTEDIT'],
		   	colModel:[
		   		{name:'id',index:'id', hidden:true},
		   		{name:'color',index:'color', hidden:true},
		   		{name:'ab',index:'ab',width:width*0.25,hidden:true},
		   		{name:'name',index:'name',width:width*0.25,sortable:false},
		   		{name:'result',index:'result',width:width*0.15, sortable:false, editable:true},
		   		{name:'last',index:'last',width:width*0.1, sortable:false},
		   		{name:'last1',index:'last1',width:width*0.1, sortable:false},
		   		{name:'device',index:'device',width:width*0.2, hidden:true, sortable:false},
		   		{name:'scope',index:'scope',width:width*0.25,sortable:false},
		   		{name:'checktime',index:'checktime',width:width*0.15,sortable:false},
		   		{name:'unit', index:'unit', width:width*0.15, sortable:false, hidden:true},
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
								var pre = "<div class=''>";
								cl = ret.result;
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
				var user = jQuery("#sample0").jqGrid("getGridParam", "userdata");
				if(user == null) {
					user = userdata;
				}
				var hisDate = user.hisDate;
				var sameSample = user.sameSample;
				var isLastYear = user.isLastYear;
				$("#jqgh_sample0_last").css('color','#000000');
				$("#jqgh_sample0_last1").css('color','#000000');
				if (hisDate != null && hisDate != "") {
					$("#hisLastResult").val(1);
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
				} else {
					$("#hisLastResult").val(0);
				}
				$.each(jQuery('#sample0').jqGrid('getCol','id', false), function(k,v) {
        			var ret = jQuery("#sample0").jqGrid('getRowData',v);
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
        					result:color+"<span class='two_result_span'>"+ret.result+"</span>"+res+"</div>",
        					last:"<span class='two_last_span'>" + ret.last + "</span>"+res1,
        					last1:"<span class='two_last_span'>" + ret.last1 + "</span>"+res2
        				});
					} else {
						jQuery("#sample0").jqGrid('setRowData', v, {
							result:color+"<span class='two_result_span'>"+ret.result+"</span>"+res+"</div>",
        					last:"<span class='two_last_span'>" + ret.last + "</span>"+res1,
        					last1:"<span class='two_last_span'>" + ret.last1 + "</span>"+res2
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
        var width = $("#mid").width()*0.48;
		jQuery("#sample1").jqGrid({
		   	data: mydata,
			datatype: "local",
			width:width,
			jsonReader : {repeatitems : false, userdata : userdata},  
		   	colNames:['ID','Color','缩写','项目', '结果', '历史', '历史', '仪器号', '参考范围', '测定时间', '单位','KNOWLEDGE','EDITMARK','LASTEDIT'],
		   	colModel:[
		   	   	{name:'id',index:'id', hidden:true},
		   		{name:'color',index:'color', hidden:true},
		   		{name:'ab',index:'ab',width:width*0.25,hidden:true},
		   		{name:'name',index:'name',width:width*0.25,sortable:false},
		   		{name:'result',index:'result',width:width*0.15, sortable:false, editable:true},
		   		{name:'last',index:'last',width:width*0.1, sortable:false},
		   		{name:'last1',index:'last1',width:width*0.1, sortable:false},
		   		{name:'device',index:'device',width:width*0.2, hidden:true, sortable:false},
		   		{name:'scope',index:'scope',width:width*0.25,sortable:false},
		   		{name:'checktime',index:'checktime',width:width*0.15,sortable:false},
		   		{name:'unit', sortable:false, width:width*0.15, index:'unit', hidden:true},
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
								var pre = "<div class=''>";
								cl=ret.result;
								lastval =  $(ret.result).find(":eq(0)").html();
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
				var user = jQuery("#sample1").jqGrid("getGridParam", "userdata");
				if(user == null) {
					user = userdata;
				}
				var hisDate = user.hisDate;
				var isLastYear = user.isLastYear;
				$("#jqgh_sample1_last").css('color','#000000');
				$("#jqgh_sample1_last1").css('color','#000000');
				if (hisDate != null && hisDate != "") {
					$("#hisLastResult").val(1);
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
				} else {
					$("#hisLastResult").val(0);
				}
				$.each(jQuery('#sample1').jqGrid('getCol','id', false), function(k,v) {
        			var ret = jQuery("#sample1").jqGrid('getRowData',v);
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
        				jQuery("#sample1").jqGrid('setRowData', v, {
        					result:color+"<span class='two_result_span'>"+ret.result+"</span>"+res+"</div>",
        					last:"<span class='two_last_span'>" + ret.last + "</span>"+res1,
        					last1:"<span class='two_last_span'>" + ret.last1 + "</span>"+res2
        				});
					} else {
						jQuery("#sample1").jqGrid('setRowData', v, {
							result:color+"<span class='two_result_span'>"+ret.result+"</span>"+res+"</div>",
        					last:"<span class='two_last_span'>" + ret.last + "</span>"+res1,
        					last1:"<span class='two_last_span'>" + ret.last1 + "</span>"+res2
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
	
	function getTestModifyTable(sample) {
		jQuery("#test_modify_information").jqGrid({
			url:"../audit/testModify?sampleNo="+sample,
			datatype: "json",
			jsonReader : {repeatitems : false}, 
			colNames:['项目名称','修改类型','修改前'
			          ,'修改后','修改时间','修改者'],
		   	colModel:[{name:'test',index:'test',width:80,sortable:false},
		   		{name:'type',index:'type',width:70,sortable:false},
		   		{name:'oldValue',index:'oldValue',width:50,sortable:false},
		   		{name:'newValue',index:'newValue',width:50,sortable:false},
		   		{name:'modifyTime',index:'modifyTime',sortable:false},
		   		{name:'modifyUser',index:'modifyUser',width:60,sortable:false}],
		   	height: '100%'
		});
	}
	
	function show_knowledge(item) {
 		jQuery.ajax({
	  		type:'GET',
			url: encodeURI('/item.jsp?page='+item),
	  		dataType: 'html',
	  		success: function(data) {
	  			openKnowledgeLayer(data);
				/*var data2=dataProcess(data);
	  	    	document.getElementById("dialog").innerHTML = data2;
	  	    	$( "#tabs" ).tabs().addClass( "ui-tabs-vertical ui-helper-clearfix" );
				$( "#tabs li" ).removeClass( "ui-corner-top" ).addClass( "ui-corner-left" );
				openKnowledgeDialog();*/
	  	  	}
	  	});
 	}
	
	function openKnowledgeLayer(data) {
		var dataArray = data.split('<div class="tab-');
		var title = [];
		for(var i=0; i<dataArray.length;i++){
			var str = dataArray[i].replace('">',"!@#$%^&*");
			if(i!=0){
				var arr = str.split("!@#$%^&*");
				title[i] = arr[0];
				dataArray[i] = arr[1].replace("<\/div>",""); 
			}
			if(i==dataArray.length-1){
				dataArray[i] = dataArray[i].replace("<\/div>","");
				dataArray[i] = dataArray[i].replace("<\/div>","");
			}
		}
		var jsonArr = [];
		for(var j=0;j<dataArray.length;j++){
			if(j!=0) {
				var jsonObj = {};
				jsonObj["title"] = title[j];
				jsonObj["content"] = dataArray[j];
				jsonArr.push(jsonObj)
			}
		}
		layer.tab({
		  area: ['1000px', '360px'],
		  tab: jsonArr
		});
		
	}
	
	function dataProcess(data){
		var title="<ul>";
		/* data = data.replace('<div class="tabbedSection">',"!@#$%^&*");
		var array = data.split("!@#$%^&*");
		//var data = array[1];
		alert(array);
		array = data.split("<\/body>");
		data = "<div> "+array[0]; */
		var dataArray = data.split('<div class="tab-');
		for(var i=0; i<dataArray.length;i++){
			var str = dataArray[i].replace('">',"!@#$%^&*");
			if(i!=0){
				var arr = str.split("!@#$%^&*");
				
				title = title+'<li><a href="#tabs-'+i+'">'+arr[0]+'<\/a><\/li>';
				dataArray[i] = '<div id="tabs-'+i+'">'+arr[1]; 
			}
			if(i==dataArray.length-1){
				dataArray[i] = dataArray[i].replace("<\/div>","");
				dataArray[i] = dataArray[i].replace("<\/div>","");
			}
		}
		title = title + "<\/ul>";
		var result = '<div id="tabs">'+title;
		for(var j=0;j<dataArray.length;j++){
			if(j!=0){
				result = result + dataArray[j]; 
			}
		}
		result = result + "<\/div>";
		return result;
	}
	
	labChange = function(select) {
		$("#lastDepLab").val($(select).children().attr("title"));
		jQuery("#list").jqGrid("setGridParam",{
			url:"../audit/data?lab="+$(select).children().attr("title")+"&sample="+$("#gs_sample").val()}).trigger("reloadGrid");
		$.ajax({
			  type: 'POST',
			  url: "../audit/labChange?lab="+$(select).children().attr("title")
		});
		selectNoteAdd = true;
		$("#lab").val($(select).children().attr("title"));
		$("#labText").html($(select).children().html());
		getSopSchedule($(select).children().attr("title"));
	};

function getExplain(docNo){
	var lastsel;
    jQuery("#audit_information").tableDnD({
        onDrop:function(table){
        	var rows = $("tr",table);
        	var content = rows[1].id;
        	for(var i=2;i<rows.length;i++){
        		content = content + "," + rows[i].id;
        	}
        	$.post("../audit/drag",{id:$("#hiddenDocId").val(), content:content},function(data) {
			});
        } 
    });
	jQuery("#audit_information").jqGrid({
		url:"../audit/explain?id="+docNo,
		datatype: "json",
		jsonReader : {repeatitems : false}, 
		colNames:['ID','OLDRESULT','解释','原因','RANK'],
	   	colModel:[{name:'id',index:'id',sortable:false,hidden:true},
	   		{name:'oldResult',index:'oldResult',sortable:false,hidden:true,editable:true},
	   		{name:'result',index:'result',width:190,sortable:false,editable:true},
	   		{name:'content',index:'content',width:190,sortable:false,hidden:true,editable:true},
	   		{name:'rank',index:'rank',sortable:false,hidden:true,editable:false}],
	    gridComplete: function() {
	    	jQuery("#audit_information").tableDnDUpdate();
	    }, 
	   	onSelectRow: function(id){
	   		if(id && id!==lastsel){
				jQuery('#audit_information').jqGrid('restoreRow',lastsel);
				jQuery('#audit_information').jqGrid('editRow',id,true);
				lastsel=id;
			}
		},
		editurl: "../audit/explain/edit?docNo=" + docNo,
	   	height: '100%'
	});
	
}


var isFirstSop = true;
var g1, g2, g3, g4;
function getSopSchedule(lab) {
	$.get("../sop/ajax/schedule",{lab:lab, sampleno:$("#hiddenSampleNo").val()},function(data){
		data = jQuery.parseJSON(data);
		if(isFirstSop) {
			isFirstSop = false;
			g1 = new JustGage({
				id: "g1", 
		        value: data.g1,
		        width: 100,
		        height: 100,
		        min: 0,
		        max: 100,
		        title: "通用文档",
			});
				g2 = new JustGage({
		            id: "g2", 
		            value: data.g2,
		            min: 0,
		            max: 100,
		            title: "专业文档",
				});
				g3 = new JustGage({
		            id: "g3", 
		            value: data.g3, 
		            min: 0,
		            max: 100,
		            title: "仪器文档",
				});
				g4 = new JustGage({
		        	id: "g4", 
					value: data.g4, 
					min: 0,
					max: 100,
					title: "项目文档",
				});
			} else {
				g1.refresh(data.g1);
				g2.refresh(data.g2);          
				g3.refresh(data.g3);
				g4.refresh(data.g4);
			}
			
		});
	}

function getDetailSop(type) {
	$.get("../sop/ajax/detail",{type:type, lab:$("#labSelect").val(), sampleno:$("#hiddenSampleNo").val()},function(data){
		data = jQuery.parseJSON(data);
		$("#sopDetailHtml").html(data.html);
	});
	openSopDetailDialog(type);
}

function validate(formData, jqForm, options) {
	
	for (var i=0; i < formData.length; i++) {
        if (!formData[i].value) {
            return false;  
        }
	}
	return true;
}

$(function() {
	
	
	$("#addResultForm").ajaxForm({
		beforeSubmit: validate,
		success: function(data) {
			if (data == true) {
				layer.closeAll();
				jQuery("#audit_information").trigger("reloadGrid");
			} else {
				alert("Fail!!!")
			}
	    }
	});
	
	$("#controlAuditBtn").click(function() {
 		var btnText = $("#controlAuditBtn").html().trim();
 		var status = 0;
 		if (btnText == "启动") {
 			status = 1;
 		}
 		var flag = true;
		var codeScope = "";
		if (status == 1) {
     		$("#codeSetDiv .codeItem").each(function(index,self) {
    			if ($(self).find(".codeCheck").attr("checked") == "checked"){
    				var code = $(self).find(".codeText").html();
    				var lo = $(self).find(".val-lo").val();
        			var hi = $(self).find(".val-hi").val();
        			if (codeScope != "") codeScope += ";";
        			if (lo.length == 0 && hi.length == 0) {
        			} else if (lo.length == 3 && hi.length == 3) {
        				codeScope += code + ":" + lo + "-" + hi;
        			} else {
        				flag = false;
        			}
    			}
    		});
		}
 		if (flag) {
			$.get("../audit/autoAudit",{status:status, scope:codeScope},function(data){
     			if (data) {
     				if (status == 1) {
     					$("#controlAuditBtn").html("停止");
     					$("#hiddenAuditConfirm").val(true);
     					$("#codeSetDiv .input-ctl").attr('disabled', 'disabled');
     				} else {
     					$("#controlAuditBtn").html("启动");
     					$("#hiddenAuditConfirm").val(false);
     					$("#codeSetDiv .input-ctl").removeAttr('disabled');
     				}
     			}
     		});
		} else {
			alert("输入错误！");
		}	
 	});
	
	var isTestModifyFirst = true;
	$("#modifyBtn").click(function() {
		if (isTestModifyFirst) {
			getTestModifyTable($("#hiddenSampleNo").val());
			isTestModifyFirst = false;
		} else {
			jQuery("#test_modify_information").jqGrid("setGridParam",{
				url:"../audit/testModify?sampleNo="+$("#hiddenSampleNo").val()
			}).trigger("reloadGrid");
		}
		openTestModifyDialog();
	});
	$("#colorHelp").append("<span class='c_td diff_td'> </span>\u5dee\u503c <span class='c_td ratio_td'> </span>\u6bd4\u503c <span class='c_td re_td'> </span>\u590d\u68c0<span class='c_td dan_td'></span>\u5371\u6025 <span class='c_td al2_td'> </span>\u8b66\u62121 <span class='c_td al3_td'> </span>\u8b66\u62122 <span class='c_td ex_td'> </span>\u6781\u503c");


});
