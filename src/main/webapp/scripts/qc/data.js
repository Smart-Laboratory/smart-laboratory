


$(function(){
	
	laydate({
        elem: '#measuretime',
        event: 'focus',
        format: 'YYYY-MM-DD'
    });
	
	$('#device').change(function() {
		$.get('../qc/data/ajax/getQcBatch',{deviceid:$('#device').val()},function(data) {
			data=jQuery.parseJSON(data);
			if(data!=null){
				var qcbatchSelect = $("#qcBatch");
				qcbatchSelect.empty();
				for(var i=0;i<data.length;i++){
					var item = data[i];
					var option = $("<option value='"+item.id+"'>"+item.name+"</option>");
					qcbatchSelect.append(option);
				}
				refreshjqGid();
			}
		});
	});
	$("#qcBatch").change(function () {
		refreshjqGid();
	});

	initjQgrid();
});
var lastsel = "";
function initjQgrid() {
	var qctable = $("#list");
	$(window).on('resize.jqGrid', function () {
		qctable.jqGrid('setGridWidth', $("#qcDataTable").width(),false);
	});
	var clientHeight= $(window).innerHeight();
	var height =clientHeight-$('#head').height()- $('#toolbar').height()-$('.footer-content').height()-150;
	qctable.jqGrid( {
		url:'../qc/data/getList',
		datatype : "json",
		height : height,
		shrinkToFit:false,
		postData : {measutetime:$("#measuretime").val(),qcbatch:$("#qcBatch").val(),deviceid:$("#device").val()},
		width:$('.leftContent').width(),
		colNames : ['id','指控批号','testid','检验项目','项目结果','检测时间','质控等级','是否有效','分析人'],
		colModel : [
			{name : 'id',index : 'id',width : 60,sorttype : "int",hidden:true,key:true},
			{name : 'qcBatch',index : 'qcBatch',width : 100,editable:false},
			{name : 'testid',index : 'testid',width : 100,hidden:true,editable:true},
			{name : 'testname',index : 'testname',width : 150,editable:false},
			{name : 'testresult',index : 'testresult',width : 100,editable:true},
			{name : 'measuretime',index : 'measuretime',width : 150,edittype:'text',editable:true,editoptions: {
				dataInit:function(element){$(element).datepicker({dateFormat: 'yy-mm-dd'})}}},
			{name : 'qclevel',index : 'qclevel',width : 100,editable:true},
			{name : 'inquality',index : 'inquality',width : 100,editable:true,edittype:'checkbox',editoptions:{value:"1:0"},formatter:'checkbox'},
			{name : 'analyser',index : 'analyser',width : 100,editable:true},
		],
		loadComplete : function() {
			// var table = this;
			// setTimeout(function(){
			// 	public.updatePagerIcons(table);
			// }, 0);
		},
		onSelectRow: function(id){
			if(id!=lastsel){
				qctable.jqGrid('saveRow',lastsel);
				qctable.jqGrid('editRow',id,{keys:true});
				lastsel=id;
			}

		},
		ondblClickRow:function(id){
		},
		//multiselect : true,
		rowNum: 10,
		viewrecords:true,
		rowList:[10,30],
		rownumbers: true, // 显示行号
		rownumWidth: 35, // the width of the row numbers columns
		pager: "#leftPager",//分页控件的id
		caption : "质控数据输入",
		editurl : "../qc/data/saveData?measutetime="+$("#measuretime").val()+"&qcbatch="+$("#qcBatch").val()+"&deviceid="+$("#device").val()
	});
	$(window).triggerHandler('resize.jqGrid');
}
function refreshjqGid() {
	$("#list").jqGrid('setGridParam',{
		url:'../qc/data/getList',
		postData : {measutetime:$("#measuretime").val(),qcbatch:$("#qcBatch").val(),deviceid:$("#device").val()},
		editurl : "../qc/data/saveData?measutetime="+$("#measuretime").val()+"&qcbatch="+$("#qcBatch").val()+"&deviceid="+$("#device").val()
	}).trigger('reloadGrid');
}

function Save(){
	if(selRows==""){
		layer.msg("没有更改记录",{icon:0,time:1000});
		return;
	}
	var Rows = selRows.split(";");
	for(var i=0;i<Rows.length;i++){
		var row = Rows[i];
		$("#list").jqGrid("saveRow",row);
	}
	layer.msg("保存成功",{icon:0,time:1000});

}