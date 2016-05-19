function getList(text) {
	var width=$("#sampleListPanel").width();
		var mygrid = jQuery("#list").jqGrid({
        	url:"../manage/sampleQuery/data?type=1&text="+text, 
        	datatype: "json", 
        	width:width,
        	colNames:['ID', '样本号', '状态','写回状态','检验目的', '临床诊断','病人姓名','病历号','性别','出生日期','就诊方式','科室','就诊号','样本类型','操作'], 
        	colModel:[ 
        		{name:'id',index:'id', hidden:true},
        		{name:'sample',index:'sample',width:120, sortable:false},
        		{name:'status',index:'status',width:50,sortable:false},
        		{name:'lisPass',index:'lisPass',width:50, sortable:false},
        		{name:'inspection',index:'inspection',width:300, sortable:false}, 
        		{name:'diagnostic',index:'diagnostic',width:200, sortable:false},
        		{name:'name',index:'name',width:60, sortable:false},
        		{name:'blh',index:'blh',width:100, sortable:false},
        		{name:'sex',index:'sex',width:40, sortable:false},
        		{name:'birthday',index:'birthday',width:80, sortable:false},
        		{name:'stayHospitalMode',index:'stayHospitalMode',width:60, sortable:false,searchoptions:{value:"1:门诊;2:住院;3:急诊;"}},
        		{name:'section',index:'section',width:100, sortable:false},
        		{name:'patientid',index:'patientid',width:100, sortable:false},
        		{name:'sampleType',index:'sampleType',width:60, sortable:false},
        		{name:'operation',index:'operation',width:120, sortable:false},
        		],
        	rownumbers:true,
        	rowNum:20,
        	height: '100%',
        	jsonReader : {repeatitems : false},
        	mtype: "GET", 
        	pager: '#pager',
        	shrinkToFit:false,   
        	autoScroll: false,
        	onSelectRow: function(id) {    
        		
        	},
        	loadComplete: function() {
        		
        	}
        }).trigger("reloadGrid"); 
		jQuery("#list").jqGrid('navGrid','#pager',{edit:false,add:false,del:false,search:false,refresh:false});
        jQuery("#list").jqGrid('navButtonAdd',"#pager",{caption:"",title:"", buttonicon :'ui-icon-pin-s', onClickButton:function(){ mygrid[0].toggleToolbar() } });
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
	
	$( "#from" ).datepicker({
		changeMonth: true,
		dateFormat:"yy-mm-dd",
		monthNamesShort: ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'],
		dayNamesMin: ['日','一','二','三','四','五','六'],
		onClose: function( selectedDate ) {
			$( "#to" ).datepicker( "option", "minDate", selectedDate );
		}
	});
	$( "#to" ).datepicker({
		changeMonth: true,
		dateFormat:"yy-mm-dd",
		monthNamesShort: ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'],
		dayNamesMin: ['日','一','二','三','四','五','六'],
		onClose: function( selectedDate ) {
			$( "#from" ).datepicker( "option", "maxDate", selectedDate );
	    }
	});
	$( "#from" ).val(new Date().Format("yyyy-MM-dd"));
	$( "#to" ).val(new Date().Format("yyyy-MM-dd"));
	$("#searchPrint").dialog({
		autoOpen: false,
		resizable: false,
		modal:true,
	    width: 750,
	    height: 600
	});
	
	$("#searchBtn").click(function(){
		var text = $("#search_text").val();
		var type = $("[name='select_type']:checked").val();
		var stayhospitalmode = $("#search_select").val();
		var from = $( "#from" ).val();
		var to = $( "#to" ).val();
		
		var PostData = {from:from,to:to,section:$("#labSelect_seach").val(),sampleType:$("#sampleTypeSelect").val()};
		if(text!=null){
			jQuery("#list").clearGridData();
    		jQuery("#list").jqGrid("setGridParam",{url:"../manage/sampleQuery/data?text="+text+"&type="+type+"&stayhospitalmode="+stayhospitalmode,postData:PostData}).trigger("reloadGrid");
		}
	});
	getList("");
	$("#search_text").focus(function(){
		if($("[name='select_type']:checked").val()==1){
			$("#search_text").val(new Date().Format("yyyyMMdd"));
		}
	});
	
});

//张晋南 2016-5-19 查询结果详细信息打印报告----------
function search_printBtn(SampleNo) {
	$('#printFrame').empty();
	var id = $("#hiddenDocId").val();
	
	var last = 0;
	if ($("#hisLastResult").val() == 1) {
		last = 1;
	}
	$("#printFrame").append("<iframe id='iframe_print' name='iframe_print' frameborder=0 style='background-color:transparent' width='99%' src=\"../print/sample?docId=" + id + "&sampleNo=" + SampleNo + "&last=" + last + "\"/>")
	$("#searchPrint").dialog("open");
	$("#iframe_print").height(450);
};

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
}  