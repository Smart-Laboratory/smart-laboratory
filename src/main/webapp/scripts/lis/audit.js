function tabChange(id) {
	if(id == 1) {
		jQuery("#rowed3").setGridParam().showCol("last2");
		jQuery("#rowed3").setGridParam().showCol("last3");
		jQuery("#rowed3").setGridParam().showCol("last4");
		//jQuery("#rowed3").setGridParam().showCol("device");
		//jQuery("#rowed3").setGridParam().showCol("unit");
	} else {
		$("#patientRow").css('display','block');
		$("#twosampleTable").css('display','none');
		jQuery("#rowed3").setGridParam().hideCol("last2");
		jQuery("#rowed3").setGridParam().hideCol("last3");
		jQuery("#rowed3").setGridParam().hideCol("last4");
		//jQuery("#rowed3").setGridParam().hideCol("device");
		//jQuery("#rowed3").setGridParam().hideCol("unit");
		var s = jQuery("#list").jqGrid('getGridParam','selrow');
		if (id == 0) {
			getExplain(s);
		}
	}
}

$(function(){
/*	var LODOP=getLodop();
	LODOP.PRINT_INIT("打印任务名");               //首先一个初始化语句 
	LODOP.ADD_PRINT_TEXT(0,0,100,20,"文本内容一");//然后多个ADD语句及SET语句 
	LODOP.PRINT();                               //最后一个打印(或预览、维护、设计)语句
*/	
	$("#footer").css('display','none');
	$("#labSelect").val($("#lab").val());
	
	getList($("#sampletext").val(),$("#labSelect").val());

	getSopSchedule($("#labSelect").val());
	
	$("#reason_block").click(function() {
		$("#reason_none").css('display','inline');
		$("#reason_block").css('display','none');
		jQuery("#audit_information").setGridParam().showCol("content");
		jQuery("#audit_information").setGridWidth(190,true);
		
	});
	
	$("#reason_none").click(function() {
		$("#reason_block").css('display','inline');
		$("#reason_none").css('display','none');
		jQuery("#audit_information").setGridParam().hideCol("content");
		jQuery("#audit_information").setGridWidth(190,true);
	});
	
	$("#resultAdd").click(function() {
		var docNo = $("#hiddenDocId").val();
		$("#span_docNo").val(docNo);
		$("#result_docNo").val(docNo);
		openAddResultDialog();
	});
	
	$("#resultDelete").click(function() {
		var ii = jQuery("#audit_information").jqGrid('getGridParam','selrow');
		if (ii != null) {
			$.post("../audit/deleteResult",{docNo:$("#hiddenDocId").val(),id:ii}, function(data) {
				if (data == true) {
					$("#audit_information").jqGrid('delRowData',ii);
				} else {
					alert("Fail!!!")
				}
			});
		} 
	});
	
	$("#englishToChBtn").click(function(){
		if($("#englishToChBtn").prop("checked") == true) {
			jQuery("#rowed3").setGridParam().hideCol("name");
			jQuery("#rowed3").setGridParam().showCol("ab");
			jQuery("#sample0").setGridParam().hideCol("name");
			jQuery("#sample0").setGridParam().showCol("ab");
			jQuery("#sample1").setGridParam().hideCol("name");
			jQuery("#sample1").setGridParam().showCol("ab");
		} else {
			jQuery("#rowed3").setGridParam().hideCol("ab");
			jQuery("#rowed3").setGridParam().showCol("name");
			jQuery("#sample0").setGridParam().hideCol("ab");
			jQuery("#sample0").setGridParam().showCol("name");
			jQuery("#sample1").setGridParam().hideCol("ab");
			jQuery("#sample1").setGridParam().showCol("name");
		}
	});
	
	$("#oneColumnShowBtn").click(function(){

 		if ($("#oneColumnShowBtn").prop("checked") == true) {
     		var s = jQuery("#list").jqGrid('getGridParam','selrow');
			var ret = jQuery("#list").jqGrid('getRowData',s);
       		$("#twosampleTable").css('display','none');
       		$("#patientRow").css('display','block');
       		if(isFirst){
				getSample(ret.sample);
				getTwoSample(ret.sample);
				isFirst = false;
			}
			else{
				jQuery("#rowed3").jqGrid("setGridParam",{url:"../audit/sample?id="+ret.sample,editurl: "../audit/edit?sampleNo=" + ret.sample}).trigger("reloadGrid");
			}
 		} else {
 			var s = jQuery("#list").jqGrid('getGridParam','selrow');
			var ret = jQuery("#list").jqGrid('getRowData',s);
 			$("#patientRow").css('display','none');
			$("#twosampleTable").css('display','block');
			if(isFirst){
				getSample(ret.sample);
				getTwoSample(ret.sample);
				isFirst = false;
			}
			else{
				var array = [];
				$.ajaxSetup({
					async:false
				});
				$.get("../audit/twosample", {id:ret.sample}, function(data){
					for(var i=0; i< data.length; i++) {
						array[i] = data[i];
					}
				});
				jQuery("#sample0").jqGrid("clearGridData");
				jQuery("#sample1").jqGrid("clearGridData");
					
				jQuery("#sample0").jqGrid("setGridParam",{
					data:array[0].rows,
					userdata:array[0].userdata
				}).trigger("reloadGrid");
				jQuery("#sample1").jqGrid("setGridParam",{
					data:array[1].rows,
					userdata:array[0].userdata
				}).trigger("reloadGrid");
			}
 		}
 	});
	
	$(document).keydown(function(e){
		if(e.keyCode == 40)
		{
			var s = jQuery("#list").jqGrid('getGridParam','selrow');
			var next = $("#"+s).next("tr").attr("id");
			
			if (next != null) {
				$("#list").setSelection(s, false);
				$("#list").setSelection(next, true);
			} else {
				var page = parseInt(jQuery("#list").jqGrid('getGridParam','page'));
				page = page + 1;
				var records = parseInt(jQuery("#list").jqGrid('getGridParam','records'));
				var total = (records - records % 25) / 25 + 1;
				if (page <= total) {
					$("#list").setGridParam({page:page}).trigger("reloadGrid");
				}
			}
			e.preventDefault();
		} else if (e.keyCode == 38) {
			var s = jQuery("#list").jqGrid('getGridParam','selrow');
			var prev = $("#"+s).prev("tr").attr("id");
			
			if (prev != null) {
				$("#list").setSelection(s, false);
				$("#list").setSelection(prev, true);
			}
			e.preventDefault();
		}
	});
	
	//对样本的操作
	var selectNoteAdd = true;
	$("#auditPassBtn").click(function() {
		$("#hiddenIsPass").val(true);
		$("#passNotes").html("<b>通过原因</b>");
		
		if (selectNoteAdd) {
			if ($("#lastDepLab").val() == '1300101' || $("#lastDepLab").val() == '1300200' || $("#lastDepLab").val() == '1300100' || $("#lastDepLab").val() == '1300201') {
				$("#selectNoteDiv").append("<label class='radio-inline'><input type='radio' name='passReason' checked><span class='label selectLabel'>标本重新检测</span></label>");
				$("#selectNoteDiv").append("<label class='radio-inline'><input type='radio' name='passReason'><span class='label selectLabel'>推片显微镜检查</span></label>");
				$("#selectNoteDiv").append("<label class='radio-inline'><input type='radio' name='passReason'><span class='label selectLabel'>手工分类</span></label>");
				$("#selectNoteDiv").append("<label class='radio-inline'><input type='radio' name='passReason'><span class='label selectLabel'>符合逻辑规则</span></label>");
				$("#selectNoteDiv").append("<label class='radio-inline'><input type='radio' name='passReason'><span class='label selectLabel'>其它</span></label>");
			} else if ($("#lastDepLab").val() == '1300600') {
				$("#selectNoteDiv").append("<label class='radio-inline'><input type='radio' name='passReason'><span class='label selectLabel'>重新检测</span></label>");
				$("#selectNoteDiv").append("<label class='radio-inline'><input type='radio' name='passReason'><span class='label selectLabel'>稀释或加量检测</span></label>");
				$("#selectNoteDiv").append("<label class='radio-inline'><input type='radio' name='passReason'><span class='label selectLabel'>查看标本</span></label>");
				$("#selectNoteDiv").append("<label class='radio-inline'><input type='radio' name='passReason'><span class='label selectLabel'>符合逻辑规则</span></label>");
				$("#selectNoteDiv").append("<label class='radio-inline'><input type='radio' name='passReason'><span class='label selectLabel'>其它</span></label>");
			}
			selectNoteAdd = false;
		}
		openOpStatusDialog();
	});
	
	$("#auditUnpassBtn").click(function(){
		$("#hiddenIsPass").val(false);
		$("#passNotes").html("<b>未通过原因</b>");
		openOpStatusDialog();
	});

	$("#collectBtn").click(function(){
		$("#collectText").val("");
		$("#collect_bamc").val("");
		$("#collect_type").val("");
		openCollectDialog();
	});
	
	$("#uploadBtn").click(function(){
		$("#more").html("");
		$("#image_note").val("");
		$("#galleria").html("");
		$("#galleria").css("height", "0px");
		openUploadDialog();
	});
	
	$("#imageBtn").click(function(){
		getImages($("#hiddenSampleNo").val());
		openImageDialog();
	});
	
	$("#auditBtn2").click(function(){
		var text = $("#auditText2").val();
		if (text != "") {
			var reaudit = false;
			if ($("#auditAllbtn").prop("checked")) {
				reaudit = true;
			}
			$.get("../audit/result",{sample:text,reaudit:reaudit},function() {});
		}
	});

	$("#auditBtn").click(function(){
		var s = jQuery("#list").jqGrid('getGridParam','selrow');
		var ret=jQuery("#list").jqGrid('getRowData',s);
		
		$("#auditText2").val(ret.sample);
		openAuditDialog();
		$("#isContinued").html("1");
		getProValue();
	});
	
	//张晋南 2016-5-12 染色体打印报告
	$("#auditPrintBtn").click(function() {
		$('#printFrame').empty();
		var id = $("#hiddenDocId").val();
		var sample = $("#hiddenSampleNo").val();
		var last = 0;
		if ($("#hisLastResult").val() == 1) {
			last = 1;
		}
		$("#printFrame").append("<iframe id='iframe_print' name='iframe_print' frameborder=0 style='background-color:transparent' width='99%' src=\"../print/sample?docId=" + id + "&sampleNo=" + sample + "&last=" + last + "\"/>");
		openAuditPrintDialog();
		$("#iframe_print").height(450);
		
		//alert($("#chartPanel").height());
	});
	
	$("#samplePrintBtn").click(function(){
		var text = $("#gs_sample").val();
		$('#samplePrintFrame').empty();
		$("#samplePrintFrame").append("<iframe id='iframe_sample' name='iframe_sample' frameborder=0 style='background-color:transparent' width='99%' height='99%' src=\"<c:url value='/explain/audit/samplePrint'/>?text="+text+"\" />");
		openSamplePrintDialog();
	});
	
	$("#collectConfirm").click(function() {
		var sample = $("#hiddenSampleNo").val();
		var text = $("#collectText").val();
		var bamc = $("#collect_bamc").val();
		var type = $("#collect_type").val();
		
		$.post("../audit/collect",{sample:sample, text:text, type:type, bamc:bamc},function(data) {
			if (data == true) {
				alert("收藏成功！");
			} else {
				alert("您已收藏过该样本！");
			}
			layer.closeAll();
		});
	});
	
	$("#collectCancel").click(function() {
		layer.closeAll();
	});
	
	$("#unaudit_reason_btn").hover(function(){
 		$("#unaudit_reason_btn").popover({title:"未通过原因",html:true,content:$("#audit_reason").html(),trigger:'manual',placement:'bottom'});
 		$("#unaudit_reason_btn").popover("show");
 	},function(){
 		$("#unaudit_reason_btn").popover("destroy");
 	});
	
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
	
 	setInterval(function() {
 		
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
 			$("#audit_status_info").html("");
 		},'json');
 	}, 15000);
// 	alert($("#menuheader").getAttribute("display"));
 	$("#fullScreen").click(function(){
 		if ($("#isfulltag").val() == 0) {
 			$("#head").css("display","none");
     		$("#content").css("margin-top","3px");
     		$("#fullScreen .glyphicon").removeClass("glyphicon-fullscreen");
     		$("#fullScreen .glyphicon").addClass("glyphicon-resize-small");
     		$("#isfulltag").val(1);
 		} else {
 			$("#head").css("display","block");
     		$("#fullScreen .glyphicon").removeClass("glyphicon-resize-small");
     		$("#fullScreen .glyphicon").addClass("glyphicon-fullscreen");
     		$("#isfulltag").val(0);
 		}
 	});
 	
 	$("#div_dangerous").click(function() {
		window.open("../critical/undeal");
	});
 	
});

function getProValue() {
	$.get("../task/ajax/audit",{},function(data){

		var ids = {
 			put : function(key,value){this[key] = value},
 			get : function(key){return this[key]},
 			contains : function(key){return this[key] == null?false:true},
 			remove : function(key){delete this[key]}
 		};
		
		$("#auditDialog div.proId").each(function(index,self) {
			ids.put($(self).html(),$(self).html())
		});
		
		var map = {
				put : function(key,value){this[key] = value},
				get : function(key){return this[key]},
				contains : function(key){return this[key] == null?false:true},
				remove : function(key){delete this[key]}
			};
		var array = jQuery.parseJSON(data);
		for (var i=0 ; i < array.length ; i++) {
			map.put(array[i].id,i);
			if (!ids.contains(array[i].id)) {
				var text = array[i].text;
				/* if (text.length > 11) {
					text=text.slice(0,11)+"...";
				} */
				var content = "<div><table style='margin:10px;'><tr><td style='width:30%'><div class='proRatio'></div></td><td style='width:5%'><div class='proId'>"
				+ array[i].id
				+ "</div></td><td style='width:50%'><div><div class='proStart'>"
				+ "</div><div class='proEnd'></div></div></td><td style='width:15%'></td></tr><tr><td style='width:30%;'><span>"
				+ text
				+ "</span></td><td style='width:5%'>"
				+ "<div class='proStatus'></div><input class='hiddenValue' type='hidden' value='100' ></td><td style='width:50%'><div class='proValue'>"
				+ "</div></td><td style='width:15%;text-align:center;'><input type='button' class='stopAudit btn btn-xs' value='\u505c\u6b62'/></td></tr>"
				+ "</table><div style='border-top: 1px solid #E1E1E1;margin-bottom:10px;'></div></div>";
				//$("#auditDialog").prepend(content);
				$(content).insertAfter($("#searchPanel"));
			}
		}
		
			$("#auditDialog table").each(function(index,self) {
   			var id = $(self).find("div.proId").html();
   			var da = array[map.get(id)];
   			if (da == null) return true; //continue;
   			$(self).find("div.proStart").html(da.start);
   			$(self).find("div.proEnd").html(da.end);
   			$(self).find("div.proRatio").html(da.ratio);
   			$(self).find("div.proValue").progressbar({value:da.value});
   			if (da.status == "1") {
   				$(self).find("div.proStatus").html("<img src='../images/status.run.png' class='status_icon'/>");
   				$(self).find(".stopAudit").removeAttr("disabled");
   				$(self).find(".stopAudit").click(function(){
   					$.get("../task/ajax/cancel",{id : id}, function() {});
   				});
    		} else if (da.status =="2") {
    			var value = $(self).find("input.hiddenValue").val();
    			$(self).find("div.proStatus").html("<img src='../images/status.finished.png' class='status_icon'/>");
    			$(self).find(".stopAudit").attr('disabled',"true");
    			if (value != 100 && da.value != 0) {
    				jQuery("#s3list").trigger("reloadGrid");
    			}
    		} else if (da.status == "3") {
    			$(self).find("div.proStatus").html("<img src='../images/status.cancel.png' class='status_icon'/>");
    			$(self).find(".stopAudit").attr('disabled',"true");
    		} else if (da.status == "0") {
    			$(self).find("div.proStatus").html("<img src='../images/status.wait.gif' style='width:20px;height:20px;margin-left:1px;' class='status_icon'/>");
    			$(self).find(".stopAudit").removeAttr("disabled");
   				$(self).find(".stopAudit").click(function(){
   					$.get("../task/ajax/cancel",{id : id}, function() {});
   				});
    		}
   			$(self).find("input.hiddenValue").val(da.value);
   		});
			
			if ($("#isContinued").html() == "1") {
				//getProValue(1000);
				setTimeout(getProValue,1000);
			}
	});
}

function getImages(sampleno){
	$("#showGalleria").html("");
	$.get("../audit/ajax/getImage",{sampleno:sampleno}, function(data) {
		data = jQuery.parseJSON(data);
//		data=JSON.stringify(data);
		var html = data.html.split("fxg").join("/");
		alert(html);
		$('#showGalleria').css('height','600px');//#galleria{height:320px}
		Galleria.loadTheme('../scripts/galleria.classic.min.js');
	    Galleria.run('#showGalleria', {
	        dataSource: html,
	        keepSource: false,
//	        imageCrop: false
		});
	});
}
