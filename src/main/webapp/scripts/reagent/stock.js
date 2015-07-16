	$(function() {
		jQuery("#list").jqGrid({
		   	url:'../reagent/getReagent',
			datatype: "json",
		   	colNames:['','名称','规格','产地', '品牌', '单位','单价','存放位置','环境条件','当前温度','自制试剂'],
		   	colModel:[
		   		{name:'id',index:'id', hidden:true},
		   		{name:'name',index:'name', width:155},
		   		{name:'specification',index:'specification', width:120},
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
		    viewrecords: true,
		    sortorder: "asc",
		    caption:"<h5><b>仓储信息</b></h5>"
		});
		jQuery("#list").jqGrid('navGrid','#pager',{edit:false,add:false,del:false});
	});
