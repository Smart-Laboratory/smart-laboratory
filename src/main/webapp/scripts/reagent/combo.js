	$(function() {
		jQuery("#list").jqGrid({
		   	url:'../reagent/getCombo',
			datatype: "json",
		   	colNames:['','名称','创建者','创建时间'],
		   	colModel:[
		   		{name:'id',index:'id', hidden:true},
		   		{name:'name',index:'name', width:440, sortable:true, editable:true},
		   		{name:'creator',index:'creator', width:250, align:"center", sortable:false, editable:false},
		   		{name:'createtime',index:'createtime', width:250, align:"center", sortable:false, editable:false}
		   	],
		   	rowNum:10,
		   	rowList:[10,20,30],
		   	pager: '#pager',
		   	sortname: 'id',
		    viewrecords: true,
		    editurl : "editCombo",
		    sortorder: "asc",
		    subGrid : true,
		    caption:"<h5><b>套餐信息</b></h5>",
		    subGridRowExpanded : function(subgrid_id, row_id) {
		    	var subgrid_table_id, pager_id;
				subgrid_table_id = subgrid_id+"_t";
				pager_id = "p_"+subgrid_table_id;
				$("#"+subgrid_id).html("<table id='"+subgrid_table_id+"' class='scroll'></table><div id='"+pager_id+"' class='scroll'></div>");
				jQuery("#"+subgrid_table_id).jqGrid({
					url:"subgrid.php?q=2&id="+row_id,
					datatype: "xml",
					colNames: ['No','Item','Qty','Unit','Line Total'],
					colModel: [
						{name:"num",index:"num",width:80,key:true},
						{name:"item",index:"item",width:130},
						{name:"qty",index:"qty",width:70,align:"right"},
						{name:"unit",index:"unit",width:70,align:"right"},
						{name:"total",index:"total",width:70,align:"right",sortable:false}
					],
				   	rowNum:20,
				   	pager: pager_id,
				   	sortname: 'num',
				    sortorder: "asc",
				    height: '100%'
				});
				jQuery("#"+subgrid_table_id).jqGrid('navGrid',"#"+pager_id,{edit:false,add:false,del:false})
		    },
		    subGridRowColapsed : function(subgrid_id, row_id) {
	          // this function is called before removing the data 
	          //var subgrid_table_id; 
	          //subgrid_table_id = subgrid_id+"_t"; 
	          //jQuery("#"+subgrid_table_id).remove();
	        }
		});
		jQuery("#list").jqGrid('navGrid','#pager',{edit:true,add:true,del:true});
	});