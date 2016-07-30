


$(function(){
	
	laydate({
        elem: '#measuretime',
        event: 'focus',
        format: 'YYYY-MM-DD'
    });
	
	$('#device').change(function() {
		$.get('../qc/data/ajax/getQcBatch',{deviceid:$('#device').val()},function(data) {
			
		});
	})
});