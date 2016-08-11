	$(function() {
		var width = $("#mid").width();
		jQuery("#list").jqGrid({
		   	url:'../reagent/getReagent?q=1',
			datatype: "json",
			width:width,
		   	colNames:['','名称','规格','配套仪器', '品牌', '单位','单价','存放位置','环境条件','当前温度','总量'],
		   	colModel:[
		   		{name:'id',index:'id', hidden:true},
		   		{name:'name',index:'name', width:"20%"},
		   		{name:'specification',index:'specification', width:"12%"},
		   		{name:'place',index:'place', width:"8%", sortable:false},
		   		{name:'brand',index:'brand', width:"8%", sortable:false},
		   		{name:'baozhuang',index:'baozhuang', width:"8%", align:"right", sortable:false},
		   		{name:'price',index:'price', width:"8%", align:"right", sortable:false},		
		   		{name:'address',index:'address', width:"12%", sortable:false},		
		   		{name:'condition',index:'condition', width:"8%", sortable:false},
		   		{name:'temp',index:'temp', width:"8%", align:"center", sortable:false},
		   		{name:'totalnum',index:'totalnum', width:"8%", align:"center", sortable:false}
		   	],
		   	rowNum:20,
		   	rownumbers:true,
		   	rowList:[10,20,30],
		   	pager: '#pager',
		   	sortname: 'id',
		   	height: '100%',
		    viewrecords: true,
		    sortorder: "asc",
		    subGrid : true,
		    caption:"<h5><b>仓储信息</b></h5>",
		    subGridRowExpanded : function(subgrid_id, row_id) {
		    	var subgrid_table_id, pager_id;
				subgrid_table_id = subgrid_id+"_t";
				pager_id = "p_"+subgrid_table_id;
				$("#"+subgrid_id).html("<table id='"+subgrid_table_id+"' class='scroll'></table><div id='"+pager_id+"' class='scroll'></div>");
				jQuery("#"+subgrid_table_id).jqGrid({
					url:"../reagent/getBatch?q=2&id="+row_id,
					datatype: "json",
					colNames: ['','批号','库存', '失效日期'],
					colModel: [
					    {name:'id',index:'id', hidden:true},
						{name:'batch',index:'batch', width:150, sortable:false},
						{name:'num',index:'num', width:120, align:"right", sortable:false},
						{name:'exdate',index:'exdate', width:180, sortable:false}
					],
				   	rowNum:20,
				   	sortname: 'id',
				    sortorder: "asc",
				    height: '100%'
				});
		    },
		    subGridRowColapsed : function(subgrid_id, row_id) {
	        }
		});
		jQuery("#list").jqGrid('navGrid','#pager',{edit:false,add:false,del:false});

		labChange = function(select) {
			var code = $(select).children().attr("title");
			$.ajax({
				type: 'POST',
				url: "../audit/labChange?lab="+code
			});
			$("#labText").html($(select).children().html());
			jQuery("#list").jqGrid('setGridParam',{
				url:'../reagent/getReagent?q=1',
				datatype : 'json',
				page : 1
			}).trigger('reloadGrid');
		}
		
	});
	
	
	
	
	
