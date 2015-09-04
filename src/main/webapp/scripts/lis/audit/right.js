function labChange(select) {
		$("#lastDepLib").val(select.value);
		jQuery("#s3list").jqGrid("setGridParam",{
			url:"<c:url value='/explain/audit/data'/>?lab="+select.value+"&text="+${strToday}}).trigger("reloadGrid"); 
		$.ajax({
			  type: 'POST',
			  url: "<c:url value='/explain/audit/labChange'/>?lab="+select.value
		});
		selectNoteAdd = true;
		
//		getSopSchedule(select.value);
	}

$(function() {
	$("#historyTabs").tabs({
		selected: 2,
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
