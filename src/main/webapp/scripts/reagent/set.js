	$(function() {
		jQuery("#list").jqGrid({
		   	url:'../ajax/getReagent',
			datatype: "json",
		   	colNames:['','名称[规格]','产地', '品牌', '单位','单价','存放位置','环境条件','当前温度','自制试剂'],
		   	colModel:[
		   		{name:'id',index:'id', hidden:true},
		   		{name:'name',index:'name', width:225, editable:true},
		   		{name:'place',index:'place', width:120, sortable:false, editable:true},
		   		{name:'brand',index:'brand', width:80, sortable:false, editable:true},
		   		{name:'unit',index:'unit', width:100, align:"right", sortable:false, editable:true},
		   		{name:'price',index:'price', width:60, align:"right", sortable:false, editable:true},		
		   		{name:'address',index:'address', width:100, sortable:false, editable:true},		
		   		{name:'condition',index:'condition', width:120, sortable:false, editable:true},
		   		{name:'temp',index:'temp', width:80, sortable:false, editable:true},
		   		{name:'isself',index:'isself', width:80, sortable:false, editable:true}
		   	],
		   	rowNum:10,
		   	rowList:[10,20,30],
		   	pager: '#pager',
		   	sortname: 'id',
		    viewrecords: true,
		    editurl : "editReagent",
		    sortorder: "asc",
		    caption:"<h5><b>试剂基本信息</b></h5>"
		});
		jQuery("#list").jqGrid('navGrid','#pager',{edit:true,add:true,del:true});
	});