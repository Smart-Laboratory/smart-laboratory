	function changeTab(str) {
		if(str.indexOf("in") >=0) {
			$("#inpre").attr("class", "active");
			$("#outpre").removeClass("active");
			$("#indiv").css("display","block");
			$("#outdiv").css("display","none");
			jQuery("#list").trigger("reloadGrid"); 
		} else {
			$("#inpre").removeClass("active");
			$("#outpre").attr("class", "active");
			$("#indiv").css("display","none");
			$("#outdiv").css("display","block");
			jQuery("#list2").trigger("reloadGrid");
		}
	}
	
	function reprint(id) {
		$('#printFrame').empty();
		$("#printFrame").append("<iframe id='iframe_print' name='iframe_print' frameborder=0 style='background-color:transparent' width='99%' height='93%' src=\"/reagent/print?id=" + id + "\"/>");
		$("#printDialog").dialog("open");
	}
	
	function cancel(id) {
		$.post("../../ajax/reagent/cancelout",{id:id},function(data) {
    		window.location.href="";
		});
	}
	
	$(function() {
		$("#printDialog").dialog({
			title: "条码打印",
			autoOpen: false,
			resizable: false,
			modal:true,
		    width: 680,
		    height: 600
		});
		
		jQuery("#list").jqGrid({
		   	url:'../reagent/detail/getIn',
			datatype: "json",
		   	colNames:['','名称[规格]','批号','失效日期', '验收是否合格', '入库量','入库人','入库时间',''],
		   	colModel:[
		   		{name:'id',index:'id', hidden:true},
		   		{name:'name',index:'name', width:245},
		   		{name:'batch',index:'batch', width:100, sortable:false},
		   		{name:'exdate',index:'exdate', width:100, sortable:false},
		   		{name:'isqualified',index:'isqualified', width:100, align:"center", sortable:false},
		   		{name:'num',index:'num', width:80, align:"center", sortable:false},		
		   		{name:'operator',index:'operaator', width:100, align:"center", sortable:false},
		   		{name:'indate',index:'indate', width:120, sortable:false},
		   		{name:'reprint',index:'reprint', width:120, align:"center", sortable:false}
		   	],
		   	rowNum: 20,
		   	sortname: 'id',
		   	pager: '#pager',
		   	height: '100%',
		    viewrecords: true,
		    sortorder: "asc",
		    caption: "<h5><b>入库明细</b></h5>"
		});
		jQuery("#list").jqGrid('navGrid','#pager',{edit:false,add:false,del:false});
		
		jQuery("#list2").jqGrid({
		   	url:'../reagent/detail/getOut',
			datatype: "json",
		   	colNames:['','名称[规格]','批号', '出库量','出库人','出库时间', '完成试验项目数', ''],
		   	colModel:[
		   		{name:'id',index:'id', hidden:true},
		   		{name:'name',index:'name', width:245},
		   		{name:'batch',index:'batch', width:120},
		   		{name:'num',index:'num', width:120, align:"center", sortable:false},
		   		{name:'operator',index:'operator', align:"center", width:120, sortable:false},
		   		{name:'outdate',index:'outdate', width:120, sortable:false},
		   		{name:'testnum',index:'testnum', width:120, align:"center", sortable:false},		
		   		{name:'cancel',index:'cancel', width:120, align:"center", sortable:false},		
		   	],
		   	rowNum: 20,
		   	sortname: 'id',
		   	pager: '#pager2',
		   	height: '100%',
		    viewrecords: true,
		    sortorder: "asc",
		    caption: "<h5><b>出库明细</b></h5>"
		});
		jQuery("#list2").jqGrid('navGrid','#pager2',{edit:false,add:false,del:false});
		
		$("#outdiv").css("display","none");
	});
	
	

