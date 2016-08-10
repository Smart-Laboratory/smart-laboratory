function format(cellvalue) {
	alert(1);
	if(cellvalue == '1') {
		return "是";
	} else {
		return "否";
	}
}

$(function(){

	//var jqGrid = $("#sectionList");
	var lastsel;
	$("#sfxmList").jqGrid({
		caption: "科室设置",
		url: "../request/sfxm/data",
		mtype: "GET",
		datatype: "json",
		colNames: ['序号', '收费项目名称', '英文','拼音码','五笔码','单价','执行科室','单位','门诊判别','住院判别','体检判别'],
		colModel: [
			{ name: 'id', index: 'id', width: 60, hidden: true },
			{ name: 'name', index: 'name', width: 200,editable : true},
			{ name: 'english', index: 'english', width: 100,editable : true },
			{ name: 'pinyin', index: 'pinyin', width: 100 },
			{ name: 'wubi', index: 'wubi', width: 100 },
			{ name: 'price', index: 'price', width: 100,editable : true },
			{ name: 'section', index: 'section', width: 100,editable : true },
			{ name: 'unit', index: 'unit', width: 100,editable : true },
			{ name: 'mzpb', index: 'zmpb', width: 100,editable : true,edittype : "select",editoptions : {value : "1:是;0:否"},formatter:format },
			{ name: 'zypb', index: 'zypb', width: 100,editable : true,edittype : "select",editoptions : {value : "1:是;0:否"},formatter:format },
			{ name: 'tjpb', index: 'tjpb', width: 100,editable : true,edittype : "select",editoptions : {value : "1:是;0:否"},formatter:format }
		],
		onSelectRow: function(id) {
			if(id && id!==lastsel){
				jQuery('#sfxmList').jqGrid('restoreRow',lastsel);
				jQuery('#sfxmList').jqGrid('editRow',id,true);
				lastsel=id;
			}
		},
		loadComplete : function() {
			var table = this;
			setTimeout(function(){
				//styleCheckbox(table);
				//updateActionIcons(table);
				updatePagerIcons(table);
				enableTooltips(table);
			}, 0);
		},
		editurl: "../request/sfxm/edit",
		viewrecords: true,
		autowidth: true,
		altRows:true,
		height: "100%",
		rowNum: 20,
		rowList:[10,20,30],
		rownumbers: true, // 显示行号
		rownumWidth: 35, // the width of the row numbers columns
		pager: "#pager"
	});
	jQuery("#sfxmList").jqGrid('navGrid',"#pager",{edit:false,add:false,del:false});
	//$(window).triggerHandler('resize.jqGrid');
});
function enableTooltips(table) {
	$('.navtable .ui-pg-button').tooltip({container:'body'});
	$(table).find('.ui-pg-div').tooltip({container:'body'});
}
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
