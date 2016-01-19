function labChange(select) {
		$("#lastDepLab").val(select.value);
		jQuery("#list").jqGrid("setGridParam",{
			url:"../audit/data?lab="+select.value+"&text="+"${strToday}"}).trigger("reloadGrid");
		
		$.ajax({
			  type: 'POST',
			  url: "../audit/labChange?lab="+select.value
		});
		selectNoteAdd = true;
		
		getSopSchedule(select.value);
}

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
	$.get("../sop/ajax/schedule",{lab:lab},function(data){
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
	$.get("../sop/ajax/detail",{type:type, lab:$("#labSelect").val()},function(data){
		data = jQuery.parseJSON(data);
		$("#sopDetailHtml").html(data.html);
	});		
	switch (type) {
	case 0:
		$('#sopDetailDialog').dialog("option","title", "通用文档熟悉度").dialog('open');
		break;
	case 1:
		$('#sopDetailDialog').dialog("option","title", "专业文档熟悉度").dialog('open');
		break;
	case 2:
		$('#sopDetailDialog').dialog("option","title", "仪器文档熟悉度").dialog('open');
		break;
	case 3:
		$('#sopDetailDialog').dialog("option","title", "项目文档熟悉度").dialog('open');
		break;
	}
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
				$("#addResultDialog").dialog("close");
				jQuery("#audit_information").trigger("reloadGrid");
			} else {
				alert("Fail!!!")
			}
	    }
	});
	
	$("#AuditCodeSetting").click(function(){
		if ($("#hiddenAuditConfirm").val() == 'true') {
			$("#codeSetDiv .input-ctl").attr('disabled', 'disabled');
 		} else {
			$("#codeSetDiv .input-ctl").removeAttr('disabled');
 		}
		$("#codeSetDialog").dialog("open");
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

});