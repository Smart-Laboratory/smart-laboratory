
/************************************
 *  添加字典
 *  add by zcw 2015-05-16
 * **********************************/
function  Add(){
	clearData();
	layer.open({
		type: 1,
		area: ['520px','500px'],
		fix: false, //不固定
		maxmin: false,
		shade:0.6,
		title: "添加数据",
		content: $("#addDialog"),
		btn:["保存","取消"],
		yes: function(index, layero){
			$("#addForm").submit();
			//layer.close(index); //如果设定了yes回调，需进行手工关闭
		}
	});
}
/************************************
 *  删除字典
 *  add by zcw 2015-05-16
 * **********************************/
function Delete(){

	var id = $('#tableList').jqGrid('getGridParam','selrow');
	if(id==null || id==''){
		layer.msg('请先选择要删除的数据', {icon: 2,time: 1000});
		return false;
	}
	layer.confirm('确定删除选择数据？', {icon: 2, title:'警告'}, function(index){
		$.post('../set/device/deleteDevie',{id:id},function(data) {
			jQuery("#tableList").jqGrid('delRowData',id);
		});
		layer.close(index);
	});
}
/**
 * 查询字典
 */
function search(){
	var query = $('#query').val()||'';
	var typeid =$('#type').val()||'';
	jQuery("#tableList").jqGrid('setGridParam',{
		url: "../set/device/getDeviceList",
		datatype : 'json',
		//发送数据
		postData : {"type":typeid,"query":query },
		page : 1
	}).trigger('reloadGrid');//重新载入
}

/**
 * 编辑字典
 */
function Edit(){
	var rowId = $("#tableList").jqGrid('getGridParam','selrow');
	var rowData = $("#tableList").jqGrid('getRowData',rowId);
	if(!rowId || rowId =='' || rowId==null){
		layer.alert("请先选择要编辑的数据",{icon:1,title:"提示"});
		return false;
	}
	//设置数据
	$('#id').val(rowData.id);
	$('#type').val(rowData.type);
	$('#name').val(rowData.name);
	$('#lab').val(rowData.lab);
	$('#comport').val(rowData.comport);
	$('#baudrate').val(rowData.baudrate);
	$('#parity').val(rowData.parity);
	$('#databit').val(rowData.databit);
	$('#stopbit').val(rowData.stopbit);
	$('#handshark').val(rowData.handshark);
	$('#datawind').val(rowData.datawind);
	layer.open({
		type: 1,
		area: ['520px','500px'],
		fix: false, //不固定
		maxmin: false,
		shade:0.6,
		title: "编辑数据",
		content: $("#addDialog"),
		btn:["保存","取消"],
		yes: function(index, layero){
			$("form").submit();
			//layer.close(index); //如果设定了yes回调，需进行手工关闭
		}
	});
}

$(function(){
	var height = document.documentElement.clientHeight;
	initGrid();
	if(height>150) $('#ullist').height(height-150);
	var isfirst = true;


	//$('#ullist').children("li").eq(0).click();
	//表单校验
	//$("#addResultForm").Validform({
	//	tiptype:4
	//});
	//keyPress 回车检索
	$("#query").keypress(function(e){
		if (e.keyCode == 13){
			search();
		}
	});

	//$(window).triggerHandler('resize.jqGrid');

})

function  clearData(){

}

/**
 * 设置JQGRID 上下页图标
 * @param table
 */
function updatePagerIcons(table) {
	var replacement =
	{
		'ui-icon-seek-first' : 'ace-icon fa fa-angle-double-left bigger-140',
		'ui-icon-seek-prev' : 'ace-icon fa fa-angle-left bigger-140',
		'ui-icon-seek-next' : 'ace-icon fa fa-angle-right bigger-140',
		'ui-icon-seek-end' : 'ace-icon fa fa-angle-double-right bigger-140'
	};
	$('.ui-pg-table:not(.navtable) > tbody > tr > .ui-pg-button > .ui-icon').each(function(){
		var icon = $(this);
		var $class = $.trim(icon.attr('class').replace('ui-icon', ''));
		if($class in replacement) icon.attr('class', 'ui-icon '+replacement[$class]);
	})
}

function initGrid(typeid){
	$("#tableList").jqGrid({
		caption: "设置",
		url: "../set/device/getDeviceList",
		//postData:{type:typeid},
		mtype: "GET",
		datatype: "json",
		colNames: ['编号', '类型ID', '类型名称','名称','实验室','COM口','波特率','数据位','奇偶位','停止位','握手','数据窗口'],
		colModel: [
			{ name: 'id', index: 'id', width: 60},
			{ name: 'type', index: 'type', width: 60,hidden:true},
			{ name: 'typename', index: 'typename', width: 60},
			{ name: 'name', index: 'name', width: 200 },
			{ name: 'lab', index: 'lab', width: 100},
			{ name: 'comport', index: 'comport', width: 60},
			{ name: 'baudrate', index: 'baudrate', width: 60},
			{ name: 'databit', index: 'databit', width: 60},
			{ name: 'parity', index: 'parity', width: 60 },
			{ name: 'stopbit', index: 'stopbit', width: 60},
			{ name: 'handshark', index: 'handshark', width: 60},
			{ name: 'datawind', index: 'datawind', width: 60}
		],
		loadComplete : function() {
			var table = this;
			setTimeout(function(){
				updatePagerIcons(table);
			}, 0);
		},
		viewrecords: true,
		multiselect: true,
		shrinkToFit: true,
		altRows:true,
		autowidth:true,
		//height: 300,
		height: "100%",
		rowNum: 10,
		rowList:[10,30,50],
		rownumbers: true, // 显示行号
		rownumWidth: 35, // the width of the row numbers columns
		pager: "#pager",//分页控件的id
		subGrid: false//是否启用子表格
	});
}