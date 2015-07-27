	$(function() {
		
		
		jQuery("#list").jqGrid({
		   	url:'../reagent/getReagent?q=1',
			datatype: "json",
		   	colNames:['','名称','规格','产地', '品牌', '单位','单价','存放位置','环境条件','当前温度','自制试剂'],
		   	colModel:[
		   		{name:'id',index:'id', hidden:true},
		   		{name:'name',index:'name', width:150},
		   		{name:'specification',index:'specification', width:100},
		   		{name:'place',index:'place', width:100, sortable:false},
		   		{name:'brand',index:'brand', width:80, sortable:false},
		   		{name:'baozhuang',index:'baozhuang', width:100, align:"right", sortable:false},
		   		{name:'price',index:'price', width:60, align:"right", sortable:false},		
		   		{name:'address',index:'address', width:120, sortable:false},		
		   		{name:'condition',index:'condition', width:110, sortable:false},
		   		{name:'temp',index:'temp', width:60, align:"center", sortable:false},
		   		{name:'isself',index:'isself', width:60, align:"center", sortable:false}
		   	],
		   	rowNum:10,
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
		
	});
	
	
	
	
	