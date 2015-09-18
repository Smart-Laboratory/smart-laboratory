function labChange(select) {
		$("#lastDepLab").val(select.value);
		jQuery("#list").jqGrid("setGridParam",{
			url:"../audit/data?lab="+select.value+"&text="+"${strToday}"}).trigger("reloadGrid");
		
		$.ajax({
			  type: 'POST',
			  url: "../audit/labChange?lab="+select.value
		});
		selectNoteAdd = true;
		
//		getSopSchedule(select.value);
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
        	$.post("<c:url value='/explain/audit/drag'/>",{docNo:$("#hiddenDocId").val(), content:content},function(data) {
			});
        } 
    });
	jQuery("#audit_information").jqGrid({
		url:"<c:url value='/explain/audit/explain'/>?id="+docNo,
		datatype: "json",
		jsonReader : {repeatitems : false}, 
		colNames:['ID','OLDRESULT','<fmt:message key="addResult.result"/>','<fmt:message key="content"/>','RANK'],
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
		editurl: "<c:url value='/explain/audit/explain/edit'/>?docNo=" + docNo,
	   	height: '100%'
	});
	
}

$(function() {
	
	
	$("#opStatusDialog").dialog({
		autoOpen: false,
		resizable: false,
		modal:true,
	    width: 340,
	    height: 350
	});
	
	

});