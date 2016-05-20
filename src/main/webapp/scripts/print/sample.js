$(function(){
	$.get("../print/sampleData",{sampleno:$("#hiddenSampleNo").val(), haslast:$("#hasLast").val(), type:$("#type").val()}, function(data) {
		data = jQuery.parseJSON(data);
		$("#blh").html(data.blh);
		$("#pName").html(data.pName);
		$("#pSex").html(data.sex);
		$("#pAge").html(data.age);
		$("#pType").html(data.pType);
		$("#diagnostic").html(data.diagnostic);
		$("#staymodetitle").html(data.staymodetitle);
		$("#patientId").html(data.pId);
		$("#staymodesection").html(data.staymodesection);
		$("#pSection").html(data.section);
		$("#requester").html(data.requester);
		$("#tester").html(data.tester);
		//更改为电子签名
		//$("#auditor").html(data.auditor);
		$("#auditor").attr("src",data.auditro);
		$("#receivetime").html(data.receivetime);
		$("#checktime").html(data.checktime);
		$("#executetime").html(data.executetime);
		if(data.staymode == 2) {
			$("#bedtitle").css("display","inline");
			$("#pBed").css("display","inline");
			$("#pBed").html(data.bed);
		} else {
			$("#bedtitle").css("display","none");
			$("#pBed").css("display","none");
		}
		$("#examinaim").html(data.examinaim);
		$("#resultDiv").html(data.html);
		if(data.imghtml == "") {
			$("#imageDiv").css("display", "none");
		} else {
			$("#imageDiv").css("display", "block");
			$("#imageDiv").html(data.imghtml);
		}
		if(data.advise == "") {
			$("#advise").css("display", "none");
		} else {
			$("#advise").css("display", "block");
			$("#adviseHtml").html(data.advise);
		}
	});
	
	if($("#hasLast").val() == 1) {
		$.get("../print/chart",{sampleno:$("#hiddenSampleNo").val()}, function(data) {
			if(data == "") {
				$("#historyChart").css("display", "none");
			} else {
				$("#historyChart").css("display", "block");
				data = jQuery.parseJSON(data);
				if(data.chartlist.length > 0) {
					for(var i = 0; i< data.chartlist.length; i++) {
						$("#historyChart").append("<div id='chart" + i + "' style='float:left;width:50%;height:250px'></div>");
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
				                renderTo: 'chart' + i,  
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
			}
			
		});
	} else {
		$("#historyChart").css("display", "none");
	}
});
