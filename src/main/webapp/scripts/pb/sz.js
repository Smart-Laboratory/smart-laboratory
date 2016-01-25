function labChange(select){
	var depart = select.value;
	$("#labSelect").val(depart);
	jQuery("#witable").jqGrid("setGridParam",{
			url:"../pb/sz/ajax/getWinfo?section="+depart}).trigger("reloadGrid");
	
}


var wiFirst = true;
function getWI() {
	if (wiFirst) {
		wiFirst = false;
		jQuery("#witable").jqGrid({
			url:"../pb/sz/ajax/getWinfo",
			datatype: "json",
			jsonReader : {repeatitems : false}, 
			colNames:['ID','工号','姓名','性别','科室','开始工作时间','类型','电话','班次','组内顺序','夜班顺序','良渚班','外出班','海创园','入院','中班','下午班次','年休','积休'],
		   	colModel:[
				{name:'id',index:'id',hidden:true,editable:false},
				{name:'workid',index:'workid',width:50,editable:true,editoptions:{size:10}},
				{name:'name',index:'name',width:50,editable:true,editoptions:{size:10}},
		   		{name:'sex',index:'sex',width:30,editable:true,edittype:"select",editoptions:{value:"0:\u7537;1:\u5973"}},
		   		{name:'section',index:'section',width:60,editable:true,edittype:"select",editoptions:{value:"1300000:\u533b\u5b66\u68c0\u9a8c\u79d1;1300100:\u95e8\u8bca\u5316\u9a8c\u5ba4;1300200:\u75c5\u623f\u5316\u9a8c\u5ba4;1300400:\u62bd\u8840\u4e2d\u5fc3;1300500:\u7ec6\u83cc\u5ba4;1300501:\u5185\u5206\u6ccc\u5ba4;1300600:\u751f\u5316\u5ba4;1300700:\u514d\u75ab\u5ba4;1300800:\u5206\u5b50\u5b9e\u9a8c\u5ba4"}},
		   		{name:'worktime',index:'worktime',width:80,editable:true, edittype:"text", editrules:{date: true},editoptions:{ size: 10, maxlengh: 10,  
	            	dataInit: function(element) {  
	                	$(element).datepicker({dateFormat: 'yy-mm-dd'});  
	            	}  
	         	}},
		   		{name:'type',index:'type',width:40,editable:true,edittype:"select",editoptions:{value:"0:\u5728\u804c;1:\u8fdb\u4fee;2:\u5b9e\u4e60;3:\u5de5\u4eba"}},
		   		{name:'phone',index:'phone',width:60,editable:true,editoptions:{size:20}},
		   		{name:'shift',index:'shift',width:200,editable:true,editoptions:{size:30}},
		   		{name:'ord2',index:'ord2',width:40,editable:true,editrules: {required: true, integer: true, minValue: 0, maxValue: 50},editoptions:{size:10}},
		   		{name:'ord1',index:'ord1',width:40,editable:true,editrules: {required: true, integer: true, minValue: 0, maxValue: 100},editoptions:{size:10}},
		   		{name:'ord3',index:'ord3',width:40,editable:true,editrules: {required: true, integer: true, minValue: 0, maxValue: 100},editoptions:{size:10}},
		   		{name:'ord4',index:'ord4',width:40,editable:true,editrules: {required: true, integer: true, minValue: 0, maxValue: 80},editoptions:{size:10}},
		   		{name:'ord5',index:'ord5',width:40,editable:true,editrules: {required: true, integer: true, minValue: 0, maxValue: 80},editoptions:{size:10}},
		   		{name:'ord6',index:'ord6',width:40,editable:true,editrules: {required: true, integer: true, minValue: 0, maxValue: 80},editoptions:{size:10}},
		   		{name:'ismid',index:'ismid',width:40,editable:true,edittype:"checkbox",editoptions:{value : "1:0"}},
		   		{name:'pmshift',index:'pmshift',width:100,editable:true,editoptions:{size:30}},
		   		{name:'holiday',index:'holiday',width:40,editable:true,editrules: {required: true, integer: true, minValue: 0, maxValue: 80},editoptions:{size:10}},
		   		{name:'defeHoliday',index:'defeHoliday',width:40,editable:true,editrules: {required: true, integer: true, minValue: 0, maxValue: 80},editoptions:{size:10}}
		   	],
		   	rowNum:15,
		   	pager: '#wipager',
		   	viewrecords: true,
		   	rownumbers: true,
		   	height: '100%',
		   	editurl: "../pb/sz/wiedit"
		});
		jQuery("#witable").jqGrid('navGrid','#wipager',{});
	} else {
		jQuery("#witable").jqGrid("setGridParam",{
			url:"../pb/sz/ajax/getWinfo"
		}).trigger("reloadGrid");
	}
}

var bcFirst = true;
function getBC() {
	if (bcFirst) {
		bcFirst = false;
		jQuery("#bctable").jqGrid({
			url:"../pb/sz/ajax/getShift",
			datatype: "json",
			jsonReader : {repeatitems : false}, 
			colNames:['ID','姓名','缩写','工作时间段','科室','显示顺序'],
		   	colModel:[
				{name:'id',index:'id',hidden:true,editable:false},
				{name:'name',index:'name',width:200,editable:true,editoptions:{size:20}},
				{name:'ab',index:'ab',width:30,editable:true,editoptions:{size:5}},
				{name:'wtime',index:'wtime',width:100,editable:true,editoptions:{size:30}},
				{name:'section',index:'section',width:100,editable:true,edittype:"select",editoptions:{value:"1300000:\u533b\u5b66\u68c0\u9a8c\u79d1;1300100:\u95e8\u8bca\u5316\u9a8c\u5ba4;1300200:\u75c5\u623f\u5316\u9a8c\u5ba4;1300500:\u7ec6\u83cc\u5ba4;1300400:\u62bd\u8840\u4e2d\u5fc3;1300500:\u7ec6\u83cc\u5ba4;1300501:\u5185\u5206\u6ccc\u5ba4;1300600:\u751f\u5316\u5ba4;1300700:\u514d\u75ab\u5ba4;1300800:\u5206\u5b50\u5b9e\u9a8c\u5ba4"}},
				{name:'order',index:'order',width:40,editable:true,editoptions:{size:10}},
		   	],
		   	rowNum:15,
		   	pager: '#bcpager',
		   	viewrecords: true,
		   	rownumbers: true,
		   	height: '100%',
		   	editurl: "../pb/sz/bcedit"
		});
		jQuery("#bctable").jqGrid('navGrid','#bcpager',{});
	} else {
		jQuery("#bctable").jqGrid("setGridParam",{
			url:"../pb/sz/ajax/getShift"
		}).trigger("reloadGrid");
	}
}

var dbcFirst = true;
function getDBC() {
	if (dbcFirst) {
		dbcFirst = false;
		jQuery("#dbctable").jqGrid({
			url:"../pb/sz/ajax/getDShift",
			datatype: "json",
			jsonReader : {repeatitems : false}, 
			colNames:['ID','星期','科室','班次'],
		   	colModel:[
				{name:'id',index:'id',hidden:true,editable:false},
				{name:'week',index:'week',width:60,editable:true,editoptions:{size:10}},
				{name:'section',index:'section',width:100,editable:true,edittype:"select",editoptions:{value:"1300000:\u533b\u5b66\u68c0\u9a8c\u79d1;1300100:\u95e8\u8bca\u5316\u9a8c\u5ba4;1300200:\u75c5\u623f\u5316\u9a8c\u5ba4;1300500:\u7ec6\u83cc\u5ba4;1300400:\u62bd\u8840\u4e2d\u5fc3;1300500:\u7ec6\u83cc\u5ba4;1300501:\u5185\u5206\u6ccc\u5ba4;1300600:\u751f\u5316\u5ba4;1300700:\u514d\u75ab\u5ba4;1300800:\u5206\u5b50\u5b9e\u9a8c\u5ba4"}},
				{name:'shift',index:'shift',width:200,editable:true,editoptions:{size:30}}
		   	],
		   	rowNum:15,
		   	pager: '#dbcpager',
		   	viewrecords: true,
		   	rownumbers: true,
		   	height: '100%',
		   	editurl: "../pb/sz/dbcedit"
		});
		jQuery("#dbctable").jqGrid('navGrid','#dbcpager',{});
	} else {
		jQuery("#dbctable").jqGrid("setGridParam",{
			url:"../pb/sz/ajax/getDShift"
		}).trigger("reloadGrid");
	}
}

var kqFirst = true;
function getKQ() {
	if (kqFirst) {
		kqFirst = false;
		jQuery("#kqtable").jqGrid({
			url:"../pb/sz/ajax/getAtype",
			datatype: "json",
			jsonReader : {repeatitems : false}, 
			colNames:['ID','姓名','缩写','类型','状态','显示顺序'],
		   	colModel:[
				{name:'id',index:'id',hidden:true,editable:false},
				{name:'name',index:'name',width:100,editable:true,editoptions:{size:10}},
		   		{name:'ab',index:'ab',width:30,editable:true,editoptions:{size:5}},
		   		{name:'type',index:'type',width:120,editable:true,edittype:"select",editoptions:{value:"0:\u51fa\u52e4;1:\u7f3a\u52e4;2:\u4f11\u5047"}},
		   		{name:'iscancel',index:'iscancel',width:200,editable:true,edittype:"select",editoptions:{value:"0:\u662f;1:\u5426"}},
		   		{name:'order',index:'order',width:200,editable:true}
		   	],
		   	rowNum:15,
		   	pager: '#kqpager',
		   	viewrecords: true,
		   	rownumbers: true,
		   	height: '100%',
		   	editurl: "../pb/sz/kqedit"
		});
		jQuery("#kqtable").jqGrid('navGrid','#kqpager',{});
	} else {
		jQuery("#kqtable").jqGrid("setGridParam",{
			url:"../pb/sz/ajax/getAtype"
		}).trigger("reloadGrid");
	}
}

$(function() {
	$("#labSelect").val($("#section").val());
	$( "#tabs" ).tabs({
		active : 0,
		activate : function(event, ui) {
			var id = ui.newPanel.attr("id");
			if(id == "tabs-1") {
				getWI();
			}else if(id == "tabs-2") {
				getBC();
			} else if(id == "tabs-3") {
				getDBC();
			} else if(id == "tabs-4") {
				getKQ();
			} else{
				getWI();
			}
		}
	});
	
	getWI();
	
	$("#resetHoliday").click(function(){
		$.get("../pb/sz/resetHoliday");
	});
});