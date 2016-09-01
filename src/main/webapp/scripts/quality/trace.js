function getSample(docNo) {
	$.get("../doctor/sampleTrace/sample",{id:docNo},function(data){
		$("#midContent").css('display','block');
    	$("#pName").html(data.name);
    	$("#pAge").html(data.age);
    	$("#blh").html(data.blh);
    	$("#pId").html(data.id);
    	$("#pSex").html(data.sex);
    	$("#pSection").html(data.section);
    	$("#pType").html(data.type);
    	$("#diagnostic").html(data.diagnostic);
    	$("#sjSection").html(data.sjSection);
    	$("#cxxx").html("");
    	$("#cxxx").html(data.cxxx);
    	
    	$("#tat_request").html(data.request);
		$("#tat_execute").html(data.execute);
		$("#tat_receive").html(data.receive);
		$("#tat_audit").html(data.audit);
		
		$("#tat_send").html(data.send);
		$("#tat_sender").html(data.sender);
		$("#tat_ksreceive").html(data.ksreceive);
		$("#tat_ksreceiver").html(data.ksreceiver);
		var logisticlist = data.logisticList;
		var html="";
		for(var i=0 ;i<logisticlist.length;i++){
			var logistic = logisticlist[i];
			html += "<tr><th>"+logistic.operatetype+"</th><td></td>"+
			"<th>时间</th><td>"+logistic.operatetime+"</td>"+
			"<th>操作者</th><td>"+logistic.operator+"</td>"+
			"<th>地点</th><td>"+logistic.location+"</td></tr>";
		}
		$("#logistic").html(html);
		
		
		$("#tat_requester").html(data.requester);
		$("#tat_executor").html(data.executor);
		$("#tat_receiver").html(data.receiver);
		$("#tat_auditor").html(data.auditor);
		
		
		$("#tat_tester").html(data.tester);
		var time = parseInt(data.tat);
		var tStr = "";
		if (time >= 1440) {
			var day = Math.floor(time / 1440);
			tStr += day.toString();
			tStr += "天";
			time = time - day * 1440;
		}
		
		if (time >= 60) {
			var hour = Math.floor(time / 60);
			tStr += hour.toString();
			tStr += "小时";
			time = time - hour * 60;
		}
		tStr += time.toString();
		tStr += "分钟";
		
		$("#audit_tat").html(tStr);
    	
    	
    	
    	$("#hiddenDocId").val(docNo);
    }, "json");
}

function getList(from, to, name, type) {
	//列表初始化
	$(window).on('resize.jqGrid',function () {
		jQuery("#s3list").jqGrid("setGridWidth",$("#searchHeader").width(),false);
	});
	var clienthight = $(window).innerHeight();
	var height = clienthight - $("#head").height() - $("#footer").height()-150;

	var width=$("#searchHeader").width();
	var sampleState = $("#sampleState").val();
	var mygrid = jQuery("#s3list").jqGrid({
    	url:"../doctor/sampleTrace/data?from=" + from + "&to=" + to + "&name=" + name + "&type=" 
    			+ type + "&sampleState=" +sampleState, 
    	datatype: "json", 
    	colNames:['ID','样本状态','医嘱号', '样本号', '检验目的','操作时间','送出时间','科室接收时间'], 
    	colModel:[ 
    		{name:'id',index:'id', width:100, sortable:false,hidden:true},
    		{name:'samplestatus',index:'samplestatus', width:60, sortable:false},
    		{name:'doctadviseno',index:'doctadviseno', width:100, sortable:false},
    		{name:'sample',index:'sample',width:150, sortable:false},
    		{name:'examinaim',index:'examinaim',width:200, sortable:false},
    		{name:'operatetime',index:'operatetime',width:180, sortable:false},
    		{name:'sendtime',index:'operatetime',width:180, sortable:false},
    		{name:'ksreceivetime',index:'operatetime',width:180, sortable:false}
    		], 
    	rowNum:20,
    	height: height,
    	jsonReader : {repeatitems : false},
    	mtype: "GET", 
    	pager: '#s3pager',
    	onSelectRow: function(id) {  
    		var ret = jQuery("#s3list").jqGrid('getRowData',id);
    		getSample(ret.doctadviseno);
    		
    	},
    	loadComplete: function() {

    		var firstDocId;
    		$.each(jQuery('#s3list').jqGrid('getCol','id', false), function(k,v) {
    			var ret = jQuery("#s3list").jqGrid('getRowData',v);
    			if (k == 0) {
    				firstDocId = ret.id;
    			}
    		}); 
    		$("#s3list").setSelection(firstDocId, true);
    	}
    }).trigger("reloadGrid"); 
	jQuery("#s3list").jqGrid('navGrid','#s3pager',{edit:false,add:false,del:false,search:false,refresh:false});
}

$(function() {
	$.ajaxSetup({cache:false});
	
	laydate({
        elem: '#from',
        event: 'focus',
        format: 'YYYY-MM-DD hh:mm:ss',
        istime: true, //是否开启时间选择
        isclear: true, //是否显示清空
        issure: true, 
        choose: function(dates){ //选择好日期的回调
        	laydate({
                elem: '#to',
                min: dates, //最小日期
                event: 'focus',
                format: 'YYYY-MM-DD hh:mm:ss',
                istime: true, //是否开启时间选择
                isclear: true, //是否显示清空
                issure: true, 
            });
        }
    });
    laydate({
        elem: '#to',
        event: 'focus',
        format: 'YYYY-MM-DD hh:mm:ss',
        istime: true, //是否开启时间选择
        isclear: true, //是否显示清空
        issure: true, 
        choose: function(dates){ //选择好日期的回调
        	laydate({
                elem: '#from',
                max: dates, //最小日期
                event: 'focus',
                format: 'YYYY-MM-DD hh:mm:ss',
                istime: true, //是否开启时间选择
                isclear: true, //是否显示清空
                issure: true, 
            });
        }
    });
    
    
    $("#search_text").autocomplete({
        source: function( request, response ) {
            $.ajax({
            	url: "../doctor/sampleTrace/ajax/searchSection",
                dataType: "json",
                data: {
                    name : request.term,
                    type : $("#search_select").val()
                },
                success: function( data ) {
  					
                	response( $.map( data, function( result ) {
                        return {
                            label: result.id + " : " + result.name,
                            value: result.name,
                            id : result.id
                        }
                    }));
                    $("#search_text").removeClass("ui-autocomplete-loading");
                }
            });
        },
        minLength: 1
	});
    if($('#search_select').val() !=1 && $('#search_select').val() !=4){
    	$( "#search_text" ).autocomplete( "disable" );
    }
    
    $('#search_select').change(function(){
		if($(this).children('option:selected').val() == 1 || $('#search_select').val() !=4) {
			$( "#search_text" ).autocomplete( "enable" );
		} else {
			$( "#search_text" ).autocomplete( "disable" );
		}
	});
    
    var isFirst = true;
	$("#searchBtn").click(function() {
		$("#midContent").css("display","none");
		var from = $("#from").val();
		var to = $("#to").val();
		var name = $("#search_text").val();
		var type = $("#search_select").val();
		
		if (isFirst) {
			getList(from, to, name, type);
			isFirst = false;
		}
		if(type == 1){
			jQuery("#s3list").jqGrid("showCol","operatetime");
		}else{
			jQuery("#s3list").jqGrid("hideCol","operatetime");
		}

		jQuery("#s3list").jqGrid("setGridParam",{
			url:"../doctor/sampleTrace/data?from="+from+"&to="+to+"&name="+name+"&type="+type+ "&sampleState=" +$("#sampleState").val()
		}).trigger("reloadGrid"); 
		
	});
	var type = $("#type").val();
	var name = $("#name").val();


	if(type!=null && type ==4){
		if (isFirst) {
			getList("", "", name, type);
			isFirst = false;
			$("#search_select").val("4");
			$("#search_text").val(name);
		}
	}
	
	$(document).keydown(function(e){
		if(e.keyCode == 40)
		{
			var s = jQuery("#s3list").jqGrid('getGridParam','selrow');
			var next = $("#"+s).next("tr").attr("id");
			
			if (next != null) {
				$("#s3list").setSelection(s, false);
				$("#s3list").setSelection(next, true);
			} else {
				var page = parseInt(jQuery("#s3list").jqGrid('getGridParam','page'));
				page = page + 1;
				var records = parseInt(jQuery("#s3list").jqGrid('getGridParam','records'));
				var total = (records - records % 10) / 10 + 1;
				if (page <= total) {
					$("#s3list").setGridParam({page:page}).trigger("reloadGrid");
				}
			}
			e.preventDefault();
		} else if (e.keyCode == 38) {
			var s = jQuery("#s3list").jqGrid('getGridParam','selrow');
			var prev = $("#"+s).prev("tr").attr("id");
			
			if (prev != null) {
				$("#s3list").setSelection(s, false);
				$("#s3list").setSelection(prev, true);
			}
			e.preventDefault();
		}
	});
	
	$("#from").val(new Date().Format("yyyy-MM-dd") + " 00:00:00");
	$("#to").val(new Date().Format("yyyy-MM-dd") + " 23:59:59");
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