
var isFirstSop = true;
var g1, g2, g3, g4;
function getSopSchedule(lab) {
	$.get("<c:url value='/sop/ajax/schedule'/>",{lab:lab},function(data){
		data = jQuery.parseJSON(data);
		if(isFirstSop) {
			isFirstSop = false;
			g1 = new JustGage({
				id: "g1", 
		        value: data.g1, 
		        min: 0,
		        max: 100,
		        title: "<fmt:message key='sop.g1'/>",
			});
				g2 = new JustGage({
		            id: "g2", 
		            value: data.g2,
		            min: 0,
		            max: 100,
		            title: "<fmt:message key='sop.g2'/>",
				});
				g3 = new JustGage({
		            id: "g3", 
		            value: data.g3, 
		            min: 0,
		            max: 100,
		            title: "<fmt:message key='sop.g3'/>",
				});
				g4 = new JustGage({
		        	id: "g4", 
					value: data.g4, 
					min: 0,
					max: 100,
					title: "<fmt:message key='sop.g4'/>",
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
		$.get("<c:url value='/sop/ajax/detail'/>",{type:type, lab:$("#labSelect").val()},function(data){
			data = jQuery.parseJSON(data);
			$("#sopDetailHtml").html(data.html);
		});		
		switch (type) {
		case 0:
			$('#sopDetailDialog').dialog("option","title", "<fmt:message key='sop.detail.g1'/>").dialog('open');
			break;
		case 1:
			$('#sopDetailDialog').dialog("option","title", "<fmt:message key='sop.detail.g2'/>").dialog('open');
			break;
		case 2:
			$('#sopDetailDialog').dialog("option","title", "<fmt:message key='sop.detail.g3'/>").dialog('open');
			break;
		case 3:
			$('#sopDetailDialog').dialog("option","title", "<fmt:message key='sop.detail.g4'/>").dialog('open');
			break;
		}
	}

$(function() {
	
	
	
	
	

});


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
	    	"<fmt:message key='button.add'/>":function() {
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
		    			$.post("<c:url value='/explain/audit/add'/>",{test:postStr,sample:sample},function(data){
		    				if (data) {
		    					$("#addTestResultDialog").dialog("close");
		    					var s = jQuery("#s3list").jqGrid('getGridParam','selrow');
		    					var ret = jQuery("#s3list").jqGrid('getRowData',s);
		    					
		    					if(ret.size > 30) {
		    						jQuery("#sample0").jqGrid("setGridParam",{url:"<c:url value='/explain/audit/sample0'/>?id="+sample,editurl: "<c:url value='/explain/audit/edit'/>?sampleNo=" + sample}).trigger("reloadGrid");
			    					jQuery("#sample1").jqGrid("setGridParam",{url:"<c:url value='/explain/audit/sample1'/>?id="+sample,editurl: "<c:url value='/explain/audit/edit'/>?sampleNo=" + sample}).trigger("reloadGrid");
			    					
		    	    			} else {
		    	    				jQuery("#rowed3").jqGrid("setGridParam",{url:"<c:url value='/explain/audit/sample'/>?id="+sample,editurl: "<c:url value='/explain/audit/edit'/>?sampleNo=" + sample}).trigger("reloadGrid");
		    	    			}
		    					
		    				} else {
		    					alert("Failed!");
		    				}
		    			});
	    			}
	    		}
	    	},
	    	"<fmt:message key='button.cancel'/>":function() {
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
	
	$("#opStatusDialog").dialog({
		autoOpen: false,
		resizable: false,
		modal:true,
	    width: 340,
	    height: 350
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
	    width: 340,
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
	
	getList($("#sampletext").val(),$("#lab").val());
	
	$("#historyTabs").tabs({
		active : 1,
		activate : function(event, ui) {
//			alert(ui.newTab.html);
//			alert(ui.oldTab.index);
			if(ui.index == 1) {
				/* jQuery("#rowed3").setGridParam().showCol("last1"); */
				jQuery("#rowed3").setGridParam().showCol("last2");
				jQuery("#rowed3").setGridParam().showCol("last3");
				jQuery("#rowed3").setGridParam().showCol("last4");
				jQuery("#rowed3").setGridParam().showCol("device");
				jQuery("#rowed3").setGridParam().showCol("checktime");
			} else {
				/* jQuery("#rowed3").setGridParam().hideCol("last1"); */
				jQuery("#rowed3").setGridParam().hideCol("last2");
				jQuery("#rowed3").setGridParam().hideCol("last3");
				jQuery("#rowed3").setGridParam().hideCol("last4");
				jQuery("#rowed3").setGridParam().hideCol("device");
				jQuery("#rowed3").setGridParam().hideCol("checktime");
				var s = jQuery("#list").jqGrid('getGridParam','selrow');
				if (ui.index == 0) {
					getExplain(s);
				}
			}
		}
	});
	
	
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
		$("#addResultDialog").dialog("open");
	});
	
	$("#resultDelete").click(function() {
		var ii = jQuery("#audit_information").jqGrid('getGridParam','selrow');
		if (ii != null) {
			$.post("<c:url value='/explain/audit/deleteResult'/>",{docNo:$("#hiddenDocId").val(),id:ii}, function(data) {
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
	});
	
	$("#oneColumnShowBtn").click(function(){

 		if ($("#oneColumnShowBtn").prop("checked") == true) {
     		var s = jQuery("#list").jqGrid('getGridParam','selrow');
			var ret = jQuery("#list").jqGrid('getRowData',s);
       		$("#twosampleTable").css('display','none');
       		$("#patientRow").css('display','block');
       		if (isFirst) {
       			getSample(ret.sample);
       			getSample0(ret.sample);
       			getSample1(ret.sample);
    			isFirst = false;
       		} else {
       			jQuery("#rowed3").jqGrid("setGridParam",{url:"../audit/sample?id="+ret.sample,editurl: "/audit/edit?sampleNo=" + ret.sample}).trigger("reloadGrid");
       		}
 		} else {
 			var s = jQuery("#list").jqGrid('getGridParam','selrow');
			var ret = jQuery("#list").jqGrid('getRowData',s);
 			$("#patientRow").css('display','none');
			$("#twosampleTable").css('display','block');
			if (isFirst) {
				getSample(ret.sample);
    			getSample0(ret.sample);
    			getSample1(ret.sample);
    			isFirst = false;
    		} else {
    			jQuery("#sample0").jqGrid("setGridParam",{url:"../audit/twosample?id="+ret.sample,editurl: "<c:url value='/explain/audit/edit'/>?sampleNo=" + ret.sample}).trigger("reloadGrid");
    			jQuery("#sample1").jqGrid("setGridParam",{url:"../audit/twosample?id="+ret.sample,editurl: "<c:url value='/explain/audit/edit'/>?sampleNo=" + ret.sample}).trigger("reloadGrid");
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
		$("#passNotes").html("不通过原因");
		
		if (selectNoteAdd) {
			if ($("#lastDepLib").val() == '1300101' || $("#lastDepLib").val() == '1300200' || $("#lastDepLib").val() == '1300100' || $("#lastDepLib").val() == '1300201') {
				$("#selectNoteDiv").append("<label class='radio span'><input type='radio' name='passReason' checked><span class='label selectLabel'>\u6807\u672c\u91cd\u65b0\u68c0\u6d4b</span></label>");
				$("#selectNoteDiv").append("<label class='radio span'><input type='radio' name='passReason'><span class='label selectLabel'>\u63a8\u7247\u663e\u5fae\u955c\u68c0\u67e5</span></label>");
				$("#selectNoteDiv").append("<label class='radio span'><input type='radio' name='passReason'><span class='label selectLabel'>\u624b\u5de5\u5206\u7c7b</span></label>");
				$("#selectNoteDiv").append("<label class='radio span'><input type='radio' name='passReason'><span class='label selectLabel'>\u7b26\u5408\u903b\u8f91\u89c4\u5219</span></label>");
				$("#selectNoteDiv").append("<label class='radio span'><input type='radio' name='passReason'><span class='label selectLabel'>\u5176\u5b83</span></label>");
			} else if ($("#lastDepLib").val() == '1300600') {
				$("#selectNoteDiv").append("<label class='radio span'><input type='radio' name='passReason'><span class='label selectLabel'>\u91cd\u65b0\u68c0\u6d4b</span></label>");
				$("#selectNoteDiv").append("<label class='radio span'><input type='radio' name='passReason'><span class='label selectLabel'>\u7a00\u91ca\u6216\u52a0\u91cf\u68c0\u6d4b</span></label>");
				$("#selectNoteDiv").append("<label class='radio span'><input type='radio' name='passReason'><span class='label selectLabel'>\u67e5\u770b\u6807\u672c</span></label>");
				$("#selectNoteDiv").append("<label class='radio span'><input type='radio' name='passReason'><span class='label selectLabel'>\u7b26\u5408\u903b\u8f91\u89c4\u5219</span></label>");
				$("#selectNoteDiv").append("<label class='radio span'><input type='radio' name='passReason'><span class='label selectLabel'>\u5176\u5b83</span></label>");
			}
			selectNoteAdd = false;
		}
		$("#opStatusDialog").dialog("open");
	});
	
	$("#opConfirm").click(function() {
		var sample = $("#hiddenSampleNo").val();
		var id = $("#hiddenDocId").val();
		
		if ($("#hiddenIsPass").val() == "true") {
			var note = $("#selectNoteDiv input[name='passReason']:checked").parent().find(".selectLabel").html();
			var text = $("#noteText").val();
			$.post("<c:url value='/explain/audit/manual'/>",{sample:sample, operate:"pass", note:note, text:text},function(data) {
				if (data == true) {
					var s = jQuery("#s3list").jqGrid('getGridParam','selrow');
					jQuery("#s3list").jqGrid('setRowData', s, {status:"<fmt:message key='audit.pass'/>"});
					
					/* var next = $("#"+s).next("tr").attr("id");
					if (next != null) {
						$("#s3list").setSelection(s, false);
						$("#s3list").setSelection(next, true);
					} */
					$("#testAdd").css('display','none');
	    			$("#testDelete").css('display','none');
	    			$("#auditUnpassBtn").css('display','inline');
	    			$("#auditPassBtn").css('display','none');
	    			$("#collectBtn").css('display','inline');
	    			$("#auditPrintBtn").css('display','inline');
	    			$("#opStatusDialog").dialog("close");
	    			$("#twoColumnDialog").dialog("close");
				}
			});
		} else {
			$.post("<c:url value='/explain/audit/manual'/>",{sample:sample, operate:"unpass", note:""},function(data) {
				if (data == true) {
					var s = jQuery("#s3list").jqGrid('getGridParam','selrow');
					jQuery("#s3list").jqGrid('setRowData', s, {status:"<font color='red'><fmt:message key='audit.unpass'/></font>"});
					$("#testAdd").css('display','inline');
	    			$("#testDelete").css('display','inline');
	    			$("#auditUnpassBtn").css('display','none');
	    			$("#auditPassBtn").css('display','inline');
	    			$("#collectBtn").css('display','none');
	    			$("#auditPrintBtn").css('display','none');
	    			$("#opStatusDialog").dialog("close");
	    			$("#twoColumnDialog").dialog("close");
				}
			});
		}
	});
	
	$("#opCancel").click(function() {
		$("#opStatusDialog").dialog("close");
	});
	
	$("#auditUnpassBtn").click(function(){
		$("#hiddenIsPass").val(false);
		$("#passNotes").html("<fmt:message key='unpass.notes' />");
		$("#opStatusDialog").dialog("open");
	});

	$("#collectBtn").click(function(){
		$("#collectDialog").dialog("open");
	});
	
	$("#uploadBtn").click(function(){
		$("#more").html("");
		$("#image_note").val("");
		$("#galleria").html("");
		$("#galleria").css("height", "0px");
		$('#cellSelect option:first').attr('selected','selected');
		$("#cellTemplateSelect").html("");
		$("#cellTemplateSelect").css('display', 'none');
		$("#uploadDialog").dialog("open");
	});
	
	$("#imageBtn").click(function(){
		getImages($("#hiddenSampleNo").val());
		$("#imageDialog").dialog("open");
	});
	
	$("#tcPassBtn").click(function() {
		$("#hiddenIsPass").val(true);
		$("#passNotes").html("<fmt:message key='pass.notes' />");
		$("#opStatusDialog").dialog("open");
	});
	$("#tcUnpassBtn").click(function(){
		$("#hiddenIsPass").val(false);
		$("#passNotes").html("<fmt:message key='unpass.notes' />");
		$("#opStatusDialog").dialog("open");
	});
	$("#tcCloseBtn").click(function(){
		$("#twoColumnDialog").dialog("close");
	});
	
	$("#auditBtn2").click(function(){
		var text = $("#auditText2").val();
		if (text != "") {
			var reaudit = false;
			if (!!$("#auditAllbtn").attr("checked")) {
				reaudit = true;
			}
			$.get("<c:url value='/explain/audit/result'/>",{sample:text,reaudit:reaudit},function() {});
		}
	});
	$("#auditBtn").click(function(){
		var s = jQuery("#s3list").jqGrid('getGridParam','selrow');
		var ret = jQuery("#s3list").jqGrid('getRowData',s);
		$("#auditText2").val(ret.sample);
		$("#auditDialog").dialog("open");
		//$("#auditDialog").modal('show');
		$("#isContinued").html("1");
		getProValue();
	});
	
	var isFirstCompare = true;
	$("#compareBtn").click(function(){
		var text = $("#sample_compare").val();
		if(isFirstCompare){
			getCompareTable(text);
			isFirstCompare=false;
		}else{
			jQuery("#sample_compare_information").jqGrid("setGridParam",{
				url:"<c:url value='/explain/audit/sampleCompare'/>?sampleNo="+text
			}).trigger("reloadGrid"); 
		} 
		$("#sampleCompareDialog").dialog("open");
	});
	
	$("#auditPrintBtn").click(function() {
		$('#printFrame').empty();
		var id = $("#hiddenDocId").val();
		var sample = $("#hiddenSampleNo").val();
		if ($("#hisLastResult").val() == 1) {
			$("#printFrame").append("<iframe id='iframe_print' name='iframe_print' frameborder=0 style='background-color:transparent' width='99%' src=\"<c:url value='/explain/audit/print'/>?docId="+id+"&sampleNo="+sample+"&last=1\" />")
		} else {
			$("#printFrame").append("<iframe id='iframe_print' name='iframe_print' frameborder=0 style='background-color:transparent' width='99%' src=\"<c:url value='/explain/audit/print'/>?docId="+id+"&sampleNo="+sample+"&last=0\" />")
		}
		$("#auditPrint").dialog("open");
		var height1 = $("#midContent").height();
		var height2 = $("#chartPanel").height()/195/3 * 195 + 195;
		
		$("#iframe_print").height(height1 + height2 + $("#rowed3 tr").length * 5);
		//alert($("#chartPanel").height());
	});
	
	$("#samplePrintBtn").click(function(){
		var text = $("#gs_sample").val();
		$('#samplePrintFrame').empty();
		$("#samplePrintFrame").append("<iframe id='iframe_sample' name='iframe_sample' frameborder=0 style='background-color:transparent' width='99%' height='99%' src=\"<c:url value='/explain/audit/samplePrint'/>?text="+text+"\" />");
		$("#samplePrint").dialog("open");
		
	});
	
	
});