

var wiFirst = true;
function getWI() {
	if (wiFirst) {
		wiFirst = false;
		var schools="";
		var sectionListStr="";
		$.get("../pb/sz/ajax/getSchool",function(data){
			{
                data = jQuery.parseJSON(data);
                schools = data.schools;
                sectionListStr = data.sectionListStr;
                jQuery("#witable").jqGrid({
                    url: "../pb/sz/ajax/getWinfo",
                    datatype: "json",
                    jsonReader: {repeatitems: false},
                    colNames: ['ID', '使用', '工号', '姓名', '性别', '科室', '开始工作时间', '类型', '电话', '班次', '组内顺序', '夜班顺序', '良渚班', '外出班', '海创园', '入院', '年休', '积休', '历年积休', '历休使用', '所在学校', '备注'],
                    colModel: [
                        {name: 'id', index: 'id', hidden: true, editable: true},
                        {
                            name: 'isactive',
                            index: 'isactive',
                            width: 30,
                            editable: true,
                            search: false,
                            edittype: "select",
                            editoptions: {value: "1:使用;0:不使用"}
                        },
                        {
                            name: 'workid',
                            index: 'workid',
                            width: 50,
                            editable: true,
                            editoptions: {size: 10},
                            sopt: 'eq'
                        },
                        {
                            name: 'name',
                            index: 'name',
                            width: 50,
                            editable: true,
                            editoptions: {size: 10},
                            sortable: false,
                            search: true,
                            sopt: ('eq', 'cn')
                        },
                        {
                            name: 'sex',
                            index: 'sex',
                            width: 30,
                            editable: true,
                            edittype: "select",
                            editoptions: {value: "0:\u7537;1:\u5973"},
                            sortable: false,
                            search: false
                        },
                        {name: 'section', index: 'section', width: 120, editable: true, 
                        	edittype: 'select',
                            editoptions: {value: sectionListStr}
                        },
                        {
                            name: 'worktime',
                            index: 'worktime',
                            width: 80,
                            editable: true,
                            sortable: false,
                            search: false,
                            edittype: "text",
                            editrules: {date: true},
                            editoptions: {
                                size: 10, maxlengh: 10,
                                dataInit: function (element) {
                                    $(element).datepicker({dateFormat: 'yy-mm-dd'});
                                }
                            }
                        },
                        {
                            name: 'type',
                            index: 'type',
                            width: 40,
                            editable: true,
                            edittype: "select",
                            editoptions: {value: "0:在职;1:进修;2:实习;3:工人"}
                        },
                        {
                            name: 'phone',
                            index: 'phone',
                            width: 60,
                            editable: true,
                            editoptions: {size: 20},
                            sortable: false,
                            search: false
                        },
                        {
                            name: 'shift',
                            index: 'shift',
                            width: 100,
                            editable: false,
                            editoptions: {size: 30},
                            sortable: false,
                            search: false
                        },
                        {
                            name: 'ord2',
                            index: 'ord2',
                            width: 40,
                            search: false,
                            editable: true,
                            editrules: {required: true, integer: true, minValue: 0},
                            editoptions: {size: 10}
                        },
                        {
                            name: 'ord1',
                            index: 'ord1',
                            width: 40,
                            search: false,
                            editable: true,
                            editrules: {integer: true, minValue: 0, maxValue: 300},
                            editoptions: {size: 10}
                        },
                        {
                            name: 'ord3',
                            index: 'ord3',
                            width: 40,
                            search: false,
                            editable: true,
                            editrules: {integer: true, minValue: 0, maxValue: 300},
                            editoptions: {size: 10}
                        },
                        {
                            name: 'ord4',
                            index: 'ord4',
                            width: 40,
                            search: false,
                            editable: true,
                            editrules: {integer: true, minValue: 0, maxValue: 80},
                            editoptions: {size: 10}
                        },
                        {
                            name: 'ord5',
                            index: 'ord5',
                            width: 40,
                            search: false,
                            editable: true,
                            editrules: {integer: true, minValue: 0, maxValue: 80},
                            editoptions: {size: 10}
                        },
                        {
                            name: 'ord6',
                            index: 'ord6',
                            width: 40,
                            search: false,
                            editable: true,
                            editrules: {integer: true, minValue: 0, maxValue: 80},
                            editoptions: {size: 10}
                        },
                        {name: 'holiday', index: 'holiday', width: 40, search: false, editable: true},
                        {name: 'defeHoliday', index: 'defeHoliday', width: 40, search: false, editable: false},
                        {name: 'defeHolidayhis', index: 'defeHolidayhis', width: 60, search: false, editable: true},
                        {name: 'lxsy', index: 'lxsy', width: 60, search: false, editable: false},
                        {
                            name: 'school',
                            index: 'school',
                            width: 100,
                            search: false,
                            editable: true,
                            edittype: 'select',
                            editoptions: {value: schools}
                        },
                        {name: 'usercomment', index: 'usercomment', width: 60, search: false, editable: true}
                    ],
                    rowNum: 15,
                    pager: '#wipager',
                    viewrecords: true,
                    rownumbers: true,
                    height: '100%',
                    editurl: "../pb/sz/wiedit"
                });
                jQuery("#witable").jqGrid('navGrid', '#wipager', {});
            }
		})
		
		
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
		var sectionListStr="";
		$.get("../pb/sz/ajax/getSchool",function(data){
			
			data = jQuery.parseJSON(data);
            schools = data.schools;
            sectionListStr = data.sectionListStr;
			jQuery("#bctable").jqGrid({
				url:"../pb/sz/ajax/getShift",
				datatype: "json",
				jsonReader : {repeatitems : false}, 
				colNames:['ID','名称','缩写','工作时间段','科室','显示顺序',"工作量"],
			   	colModel:[
					{name:'id',index:'id',hidden:true,editable:true},
					{name:'name',index:'name',width:200,editable:true,editoptions:{size:20}},
					{name:'ab',index:'ab',width:60,editable:true,editoptions:{size:5}},
					{name:'wtime',index:'wtime',width:100,editable:true,editoptions:{size:30}},
	//				{name:'section',index:'section',width:100,editable:true,edittype:"select",editoptions:{value:"22:检验科;220100:临检组;220200:生化组;220300:免疫组;220400:微生物组;220600:血库组;220700:分子实验室;"}},
//					{name:'section',index:'section',width:100,editable:true,edittype:"select",editoptions:{value:"1300000:\u533b\u5b66\u68c0\u9a8c\u79d1;1300100:\u95e8\u8bca\u5316\u9a8c\u5ba4;1300200:\u75c5\u623f\u5316\u9a8c\u5ba4;1300400:\u62bd\u8840\u4e2d\u5fc3;1300500:\u7ec6\u83cc\u5ba4;1300501:\u5185\u5206\u6ccc\u5ba4;1300600:\u751f\u5316\u5ba4;1300700:\u514d\u75ab\u5ba4;1300800:\u5206\u5b50\u5b9e\u9a8c\u5ba4;1400100:超声医学科(医生);1400200:超声医学科(报告)"}},
					{name:'section',index:'section',width:100,editable:true,edittype:"select",editoptions:{value:sectionListStr}},
					{name:'order',index:'order',width:40,editable:true,editoptions:{size:10}},
					{name:'days',index:'days',width:40,editable:true}
			   	],
			   	rowNum:15,
			   	pager: '#bcpager',
			   	viewrecords: true,
			   	rownumbers: true,
			   	height: '100%',
			   	editurl: "../pb/sz/bcedit"
			});
			jQuery("#bctable").jqGrid('navGrid','#bcpager',{});
		});
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
		jQuery("#sxschooltable").jqGrid({
			url:"../pb/sz/ajax/getDShift",
			datatype: "json",
			jsonReader : {repeatitems : false}, 
			colNames:['ID','学校名称','缩写','电话','地址','制度'],
		   	colModel:[
				{name:'id',index:'id',hidden:true,editable:true},
				{name:'name',index:'name',width:100,editable:true},
				{name:'namesx',index:'namesx',width:50,editable:true},
				{name:'phone',index:'phone',width:50,editable:true},
				{name:'address',index:'address',width:100,editable:true},
				{name:'system',index:'system',width:600,editable:true}
		   	],
		   	rowNum:15,
		   	pager: '#sxschoolpager',
		   	viewrecords: true,
		   	rownumbers: true,
		   	height: '100%',
		   	editurl: "../pb/sz/dbcedit"
		});
		jQuery("#sxschooltable").jqGrid('navGrid','#sxschoolpager',{});
	} else {
		jQuery("#sxschooltable").jqGrid("setGridParam",{
			url:"../pb/sz/ajax/getDShift"
		}).trigger("reloadGrid");
	}
}

var kqFirst = true;
function getWorkcout() {
	if (kqFirst) {
		kqFirst = false;
		jQuery("#workData").jqGrid({
			url:"../pb/pb/ajax/countWorkall?section="+$("#section").val()+"&from="+$("#from").val()+"&to="+$("#to").val(),
			datatype: "json",
			jsonReader : {repeatitems : false}, 
			colNames:['姓名','工作量','休息时间','班次'],
		   	colModel:[
				{name:'name',index:'name',width:100,editable:false},
		   		{name:'worktime',index:'worktime',width:30,editable:false},
		   		{name:'monthOff',index:'monthOff',width:120,editable:false},
		   		{name:'shift',index:'shift',width:1000,editable:false},
		   	],
		   	rowNum:20,
		   	pager: '#workpager',
		   	viewrecords: true,
		   	rownumbers: true,
		   	height: '100%',
		});
		jQuery("#workData").jqGrid('navGrid','#workpager',{});
	} else {
		jQuery("#workData").jqGrid("setGridParam",{
			url:"../pb/pb/ajax/countWorkall?section="+$("#section").val()+"&from="+$("#from").val()+"&to="+$("#to").val()
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
				getWorkcout();
			} else{
				getWI();
			}
		}
	});
	
	getWI();
	getBC();
	getWorkcout();
	
	$("#resetHoliday").click(function(){
		$.get("../pb/sz/resetHoliday");
	});
	
	labChange=function(select){
		$.ajax({
			  type: 'POST',
			  url: "../audit/labChange?lab="+$(select).children().attr("title"),
			  success:function(){
				  $("#labText").html($(select).children().html());
				  $("#section").val($(select).children().attr("title"));
				  jQuery("#witable").jqGrid("setGridParam",{
						url:"../pb/sz/ajax/getWinfo?section="+$(select).children().attr("title")}).trigger("reloadGrid");
				  getBC();
					
			  }
		});
		
	};
	
	
	$("#workCount").click(function(){
//		$.get("../pb/pb/countWorkall",{from:$("#from").val(),to:$("#to").val(),section:$("#section").val()},function(data){
//			
//		});
		getWorkcout();
	});
	$( "#from" ).datepicker({
		changeMonth: true,
		changeYear: true,
		dateFormat:"yy-mm",
		monthNamesShort: ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'],
		dayNamesMin: ['日','一','二','三','四','五','六'],
		showButtonPanel: true, 
		onClose: function(dateText, inst) { 
		var month = $("#ui-datepicker-div .ui-datepicker-month :selected").val(); 
		var year = $("#ui-datepicker-div .ui-datepicker-year :selected").val(); 
		$(this).datepicker('setDate', new Date(year, month, 1)); 
//		$( "#to" ).datepicker( "option", "minDate", new Date(year, month, 1) );
		} 
		
	});
	$( "#to" ).datepicker({
		changeMonth: true,
		changeYear: true,
		dateFormat:"yy-mm",
		monthNamesShort: ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'],
		dayNamesMin: ['日','一','二','三','四','五','六'],
		showButtonPanel: true, 
		onClose: function(dateText, inst) { 
			var month = $("#ui-datepicker-div .ui-datepicker-month :selected").val(); 
			var year = $("#ui-datepicker-div .ui-datepicker-year :selected").val(); 
			$(this).datepicker('setDate', new Date(year, month, 1));
//			$( "#from" ).datepicker( "option", "maxDate", new Date(year, month, 1) );
		} 
	});
	if($("#from").val()==null || $("#from").val()==''){
		$( "#from" ).val(new Date().Format("yyyy-MM"));
		$( "#to" ).val(new Date().Format("yyyy-MM"));
	}
});

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