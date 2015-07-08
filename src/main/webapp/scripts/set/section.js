function addSection(){
	var html = $('#tbody').html();
	html += "<tr id=0><td>新增</td><td id='code'><input id='codein' type='text'/></td><td id='name'><input id='namein' type='text'/></td>";
	html += "<td><button id='saveButton' type='button' class='btn btn-info btn-sm' onclick='saveSection(0)'>保存</button> " +
			"<button id='deleteButton' type='button' class='btn btn-danger btn-sm' onclick='deleteSection(0)'>删除</button></td></tr>";
	$('#tbody').html(html);
}

function editSection(id){
	var curCode = $('#' + id).children('#code');
    curCode.html("<input id='codein' type='text' value='" + curCode.html() + "'/>");
    var curName = $('#' + id).children('#name');
    curName.html("<input id='namein' type='text' value='" + curName.html() + "'/>");
    $('#' + id).children().eq(3).find('#editButton').css('display','none');
    $('#' + id).children().eq(3).find('#saveButton').css('display','inline');
}

function saveSection(id){
	var code=$('#' + id).children('#code').children().eq(0).val();
	var name = $('#' + id).children('#name').children().eq(0).val();
	$.post('section/edit',{id:id,code:code,name:name},function(data) {
		if(data){
			location.reload();
		}
	});
}

function deleteSection(id){
	$.post('section/delete',{id:id},function(data) {
		if(data){
			$('#' + id).remove();
		}
	});
}