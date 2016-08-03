function Add(){
	clear();
	layer.open({
		type: 1,
		area: ['520px','300px'],
		skin: 'layui-layer-lan',
		shade:0.6,
		title: "添加质控规则",
		content: $("#editRule"),
		btn:["保存","取消"],
		yes: function(index, layero){
			$.ajax({
				url:'../qc/rule/add',
				type:"POST",
				dataType:"json",
				data:$('#ruleForm').serialize(),
				success:function(data){
					if(parseInt(data.success)==0){
						layer.close(index);
						$('#list').trigger('reloadGrid');
					}else{
						layer.alert(data.success);
					}
				}
			})
		}
	});
}

function Edit(){
	var rowId = $("#list").jqGrid('getGridParam','selrow');
	var rowData = $("#list").jqGrid('getRowData',rowId);
	if(!rowId || rowId =='' || rowId==null){
		layer.alert("请先选择要编辑的数据",{icon:1,title:"提示"});
		return false;
	}
	
	//设置数据
	$('#id').val(rowData.id);
	$('#name').val(rowData.name);
	$('#describe').val(rowData.describe);
	$('#inuse').val(rowData.inuse);
	
	layer.open({
		type: 1,
		area: ['520px','300px'],
		skin: 'layui-layer-lan',
		shade:0.6,
		title: "编辑质控规则",
		content: $("#editRule"),
		btn:["保存","取消"],
		yes: function(index, layero){
			$.ajax({
				url:'../qc/rule/edit',
				type:"POST",
				dataType:"json",
				data:$('#ruleForm').serialize(),
				success:function(data){
					if(parseInt(data.success)==0){
						layer.close(index);
						$('#list').trigger('reloadGrid');
					}else{
						layer.alert(data.success);
					}
				}
			})
		}
	});
}

function Delete(){

	var id = $('#list').jqGrid('getGridParam','selrow');
	if(id==null || id==''){
		layer.msg('请先选择要删除的数据', {icon: 2,time: 1000});
		return false;
	}
	layer.confirm('确定删除选择数据？', {icon: 2, title:'警告'}, function(index){
		$.post('../qc/rule/delete',{id:id},function(data) {
			jQuery("#list").jqGrid('delRowData',id);
		});
		layer.close(index);
	});
}

function clear(){
	$("#ruleForm")[0].reset();
}

function Search(){
	var query = $('#query').val()||'';
	jQuery("#list").jqGrid('setGridParam',{
		url: "../qc/rule/list",
		datatype : 'json',
		//发送数据
		postData : {"query":query },
		page : 1
	}).trigger('reloadGrid');//重新载入
}


$(function(){
	var height = document.documentElement.clientHeight;
	if(height>300) height = height-300;
	$("#list").jqGrid({
		caption: "质控规则设置",
		url: "../qc/rule/list",
		mtype: "GET",
		datatype: "json",
		width:$('#ruleTable').width(),
		colNames: ['ID', '名称', '描述', 'INUSE', '是否使用'],
		colModel: [
			{ name: 'id', index: 'id', width: 60, hidden: true },
			{ name: 'name', index: 'name', width: 60},
			{ name: 'describe', index: 'describe', width: 200 },
			{ name: 'inuse', index: 'inuse', hidden: true },
			{ name: 'useinfo', index: 'useinfo', width: 40 }
		],
		viewrecords: true,
		shrinkToFit: true,
		altRows:true,
		height:height,
		rowNum: 10,
		rowList:[10,20,30],
		rownumbers: true, // 显示行号
		rownumWidth: 35, // the width of the row numbers columns
		pager: "#pager"
	});
	
});