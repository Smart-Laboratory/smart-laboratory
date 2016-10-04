function getList(text) {
	var width=$("#sampleListPanel").width();
	var mygrid = jQuery("#list").jqGrid({
		url: baseUrl + "/manage/sampleQuery/data?type=1&text="+text,
		datatype: "json",
		width:width,
		colNames:['ID', '样本号','条码号','SAMPLENO', '状态','写回状态','检验目的', '临床诊断','病人姓名','病历号','性别','出生日期','年龄','就诊方式','科室','床号','就诊号','申请方式','样本类型'],
		colModel:[
			{name:'id',index:'id', hidden:true},
			{name:'sample',index:'sample',width:120, sortable:false},
			{name:'barcode',index:'barcode',width:120, sortable:false},
			{name:'sampleno',index:'sampleno',hidden:true},
			{name:'status',index:'status',width:50,sortable:false},
			{name:'lisPass',index:'lisPass',width:50, sortable:false},
			{name:'inspection',index:'inspection',width:300, sortable:false},
			{name:'diagnostic',index:'diagnostic',width:200, sortable:false},
			{name:'name',index:'name',width:60, sortable:false},
			{name:'blh',index:'blh',width:100, sortable:false},
			{name:'sex',index:'sex',width:40, sortable:false},
			{name:'birthday',index:'birthday',width:80, sortable:false},
			{name:'age',index:'age',width:60, sortable:false},
			{name:'stayHospitalMode',index:'stayHospitalMode',width:60, sortable:false,searchoptions:{value:"1:门诊;2:住院;3:急诊;"}},
			{name:'section',index:'section',width:100, sortable:false},
			{name:'bed',index:'bed',width:40, sortable:false},
			{name:'patientid',index:'patientid',width:100, sortable:false},
			{name:'requestMode',index:'requestMode',hidden:true},
			{name:'sampleType',index:'sampleType',width:60, sortable:false}
			],
		rownumbers:true,
		rowNum:-1,
		height: '100%',
		jsonReader : {repeatitems : false},
		mtype: "GET",
		pager: '#pager',
		shrinkToFit:false,
		autoScroll: false,
		onSelectRow: function(id) {

		},
		loadComplete: function() {
			var table = this;
			setTimeout(function(){
				updatePagerIcons(table);
			}, 0);
		}
	}).trigger("reloadGrid");
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

$(function() {
	$("#labSelect").val($("#lastlab").val());

	function getQueryString(name) {
	    var reg = new RegExp('(^|&)' + name + '=([^&]*)(&|$)', 'i');
	    var r = window.location.search.substr(1).match(reg);
	    if (r != null) {
	        return unescape(r[2]);
	    }
	    return null;
	}
	$.ajaxSetup({cache:false});

	$("#searchBtn").click(function(){
		var text = $("#search_text").val();
		var type = $("[name='select_type']:checked").val();
		var stayhospitalmode = $("#search_select").val();
		var from = $( "#from" ).val();
		var to = $( "#to" ).val();

		var PostData = {from:from,to:to,section:$("#labSelect_seach").val(),sampleType:$("#hiddenSampleType").val()};
		if(text!=null){
			jQuery("#list").clearGridData();
    		jQuery("#list").jqGrid("setGridParam",{url: baseUrl + "/manage/sampleQuery/data?text="+text+"&type="+type+"&stayhospitalmode="+stayhospitalmode,postData:PostData}).trigger("reloadGrid");
		}
	});
	getList("");
	$("#search_text").focus(function(){
		if($("[name='select_type']:checked").val()==1){
			$("#search_text").val(new Date().Format("yyyyMMdd"));
		}
	});
	laydate.skin('molv');
	laydate({
		elem: '#from',
		event: 'focus',
		festival: true,
		format: 'YYYYMMDD'
	});
	laydate({
		elem: '#to',
		event: 'focus',
		festival: true,
		format: 'YYYYMMDD'
	});
	$( "#from" ).val(new Date().Format("yyyyMMdd"));
	$( "#to" ).val(new Date().Format("yyyyMMdd"));

	$("#sampleTypeSearch").autocomplete({
		source: function( request, response ) {
			$.ajax({
				url: baseUrl + "/ajax/searchSampleType",
				dataType: "json",
				data: {
					name : request.term
				},
				success: function( data ) {
					response( $.map( data, function( result ) {
						return {
							label: result.sign + " : " + result.value,
							value: result.value,
							sign : result.sign
						}
					}));
				}
			});
		},
		minLength: 1,
		select : function(event, ui) {
			$("#sampleTypeSearch").val(ui.item.value);
			$("#hiddenSampleType").val(ui.item.sign);
		}
	});

	$("#print").click(function() {
		if($("#printType").val() == 1) {
			var obj = $("#list").jqGrid("getRowData");
			jQuery(obj).each(function(){
				startPrint(this);
			});
		}
	})
});

var LODOP; //声明为全局变量
function startPrint(data) {
	CreateDataBill(data);
	//开始打印
	LODOP.PRINT();
}

function CreateDataBill(data) {
	if (data && data != null) {
		var patientInfo = data.bed + "  " + data.name + " " + data.age;
		var patientInfo1 = data.blh + "  " + data.section;
		LODOP = getLodop();
		LODOP.PRINT_INIT("");
		LODOP.SET_PRINT_PAGESIZE(0, 500, 350, "A4");
		//LODOP.ADD_PRINT_TEXTA("patientinfo", "2.99mm", "2.95mm", 180, 25, patientInfo);
		//LODOP.ADD_PRINT_BARCODEA("barcode", "10.42mm", "2.94mm", "42.6mm", 35, "128B", data.barcode);
		LODOP.ADD_PRINT_BARCODEA("barcode","10.42mm","2.94mm","34.66mm",35,"128Auto",data.barcode);
		LODOP.SET_PRINT_STYLEA(0, "ShowBarText", 0);
		LODOP.SET_PRINT_STYLEA(0, "Horient", 2);
		LODOP.ADD_PRINT_TEXTA("code", "19.39mm", "2.95mm", 161, 25, "*" + data.sampleno + "*");
		LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
		LODOP.SET_PRINT_STYLEA(0, "Bold", 1);
		if(data.requestMode =='1'){
			LODOP.ADD_PRINT_TEXTA("patientinfo","2.99mm","8.23mm",169,25,patientInfo);
			LODOP.ADD_PRINT_ELLIPSE(8,12,14,15,0,1);
			LODOP.ADD_PRINT_TEXT(9,12,18,16,"急");
			LODOP.SET_PRINT_STYLEA(0,"FontSize",10);
			LODOP.SET_PRINT_STYLEA(0,"Bold",1);
		}else{
			LODOP.ADD_PRINT_TEXTA("patientinfo", "2.99mm", "2.95mm", 180, 25, patientInfo);
		}
		LODOP.ADD_PRINT_TEXTA("patientinfo1", 23, "2.95mm", 180, 20, patientInfo1);
		LODOP.ADD_PRINT_TEXTA("testinfo", "23.36mm", "2.95mm", 180, 20, data.sampleType + " " + data.inspection);
	}
}

//张晋南 2016-5-19 查询结果详细信息打印报告----------
function search_printBtn(SampleNo) {
	$('#printFrame').empty();
	var id = $("#hiddenDocId").val();

	var last = 0;
	if ($("#hisLastResult").val() == 1) {
		last = 1;
	}
	$("#printFrame").append("<iframe id='iframe_print' name='iframe_print' frameborder=0 style='background-color:transparent' width='99%' src=\"../print/sample?docId=" + id + "&sampleNo=" + SampleNo + "&last=" + last + "\"/>");
	$("#searchPrint").dialog("open");
	$("#iframe_print").height(450);
}
//------------------------------------------
Date.prototype.Format = function(fmt)
{ //author: meizz
  var o = {
    "M+" : this.getMonth()+1,                 //月份
    "d+" : this.getDate(),                    //日
    "h+" : this.getHours(),                   //小时
    "m+" : this.getMinutes(),                 //分
    "s+" : this.getSeconds(),                 //秒
    "q+" : Math.floor((this.getMonth()+3)/3), //季度
    "S"  : this.getMilliseconds()             //毫秒
  };
  if(/(y+)/.test(fmt))
    fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
  for(var k in o)
    if(new RegExp("("+ k +")").test(fmt))
  fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
  return fmt;
};