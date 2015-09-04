//var today = "${strToday}";
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
	
	getList($("#sampletext").val(),$("#lab").val());
	
	$("#historyTabs").tabs({
		selected: 1,
		select: function(event, ui) { 
			if(ui.index == 2) {
			} else if(ui.index == 1) {
			} else {
			}
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
	

});