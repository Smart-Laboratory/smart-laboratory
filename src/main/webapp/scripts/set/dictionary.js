
/************************************
 *  添加字典
 *  add by zcw 2015-05-16
 * **********************************/
function  AddSection(){
	clearData();
	layer.open({
		type: 1,
		area: ['500px','250px'],
		fix: false, //不固定
		maxmin: false,
		shade:0.6,
		title: "添加数据",
		content: $("#addDialog"),
		btn:["保存","取消"],
		yes: function(index, layero){
			$("#addDictionaryForm").submit();
			//layer.close(index); //如果设定了yes回调，需进行手工关闭
		}
	});
}
/************************************
 *  删除字典
 *  add by zcw 2015-05-16
 * **********************************/
function deleteSection(){
	var id=$('#tableList').jqGrid('getGridParam','selrow');
	if(id==null || id==0){
		layer.msg('请先选择要删除的数据', {icon: 2,time: 1000});
		return false;
	}
	layer.confirm('确定删除选择数据？', {icon: 2, title:'警告'}, function(index){
		$.post('../set/dictionary/remove',{id:id},function(data) {
			jQuery("#tableList").jqGrid('delRowData',id );
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
		url: "../set/dictionary/getList",
		datatype : 'json',
		//发送数据
		postData : {type:typeid,"query":query },
		page : 1
	}).trigger('reloadGrid');//重新载入
}

/**
 * 编辑字典
 */
function editSection(){
	var rowId = $("#tableList").jqGrid('getGridParam','selrow');
	var rowData = $("#tableList").jqGrid('getRowData',rowId);
	if(!rowId || rowId =='' || rowId==null){
		layer.alert("请先选择要编辑的数据",{icon:1,title:"提示"});
		return false;
	}
	//设置数据
	$('#id').val(rowData.id);
	$('#sign').val(rowData.sign);
	$('#value').val(rowData.value);
	layer.open({
		type: 1,
		area: ['500px','250px'],
		fix: false, //不固定
		maxmin: false,
		shade:0.6,
		title: "编辑数据",
		content: $("#addDialog"),
		btn:["保存","取消"],
		yes: function(index, layero){
			$("#addDictionaryForm").submit();
			//layer.close(index); //如果设定了yes回调，需进行手工关闭
		}
	});
}

$(function(){
	var height = document.documentElement.scrollHeight;
	$('.laftnav').height(height-110);
	var isfirst = true;
	/*
	 * 生成字典类别列表
	 * add by zcw 2016-05-19
	 * */
	$.post('../set/dictionaryType/getList', function(data){
		if(data){
			var ul = $('#ullist');
			for (i=0; i< data.length; i++) {
				var li = $('<li typeid="'+data[i].id+'" ></li>');
				li.html('<a href="#_" title="'+ data [i].name+'">'+data[i].name+'</a>');
				li.bind('click',function(){
					ul.children('li').removeClass('active');
					$(this).addClass("active");
					$('#type').val($(this).attr("typeid"));
					if(isfirst) {
						initGrid($(this).attr("typeid"));
						isfirst = false;
					} else {
						reloadGrid($(this).attr("typeid"));
					}
				});
				ul.append(li);
			}
			if(typeId != ''){
				$('#ullist').find("li[typeid="+typeId+"]").click();
			}else{
				ul.children("li").eq(0).click();
			}

		}
	});

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

});

function  clearData(){
	$('#id').val('0');
	$('#sign').val('');
	$('#value').val('');
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

/*
 加载数据
 */
function reloadGrid(typeid){
	var query = $('#query').val()||'';
	jQuery("#tableList").jqGrid('setGridParam',{
		url: "../set/dictionary/getList",
		type : "POST",
		dataType : "json",
		contentType : 'application/json;charset=UTF-8',
		page:1,
		//发送数据
		postData :{type:typeid}
	}).trigger('reloadGrid');//重新载入
}

function initGrid(typeid){
	$("#tableList").jqGrid({
		caption: "设置",
		url: "../set/dictionary/getList",
		postData:{type:typeid},
		mtype: "GET",
		datatype: "json",
		colNames: ['ID', '标记', '名称'],
		colModel: [
			{ name: 'id', index: 'id', width: 60, hidden: true },
			{ name: 'sign', index: 'sign', width: 60},
			{ name: 'value', index: 'value', width: 100 }
		],
		loadComplete : function() {
			var table = this;
			setTimeout(function(){
				updatePagerIcons(table);
			}, 0);
		},
		viewrecords: true,
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

/**
 * 添加类别
 */
function addType(){
	layer.open({
		type: 1,
		area: ['500px','150px'],
		fix: false, //不固定
		maxmin: false,
		shade:0.6,
		title: "添加对照类别",
		content: $("#addType"),
		btn:["保存","取消"],
		yes: function(index, layero){
			$("#addTypeForm").submit();
			//layer.close(index); //如果设定了yes回调，需进行手工关闭
		}
	});
}
//删除类别
function removeType(){
	var id = $('#ullist').children("li.active").attr("typeid");
	if(id==null || id==0){
		layer.msg('请先选择要删除的数据', {icon: 2,time: 1000});
		return false;
	}
	layer.confirm('确定删除选择数据？', {icon: 2, title:'警告'}, function(index){
		$.post("../set/dictionaryType/removeType",{id:id},function(data) {
			//console.log(data)
			if(data.success=="true"){
				$('#ullist').children("li.active").remove();
				$('#ullist').children("li").eq(0).click();
			}else if(data.success=="false"){
				layer.alert("包含明细数据不允许删除",{icon:1,title:"提示"});
			}
		});
		layer.close(index);
	});
}