	$(function() {
		jQuery("#list").jqGrid({
		   	url:'../reagent/getReagent',
			datatype: "json",
		   	colNames:['','名称','规格','产地', '品牌', '单位','子数量','子单位','单价','存放位置','环境条件','库存界值','商品码','自制试剂'],
		   	colModel:[
		   		{name:'id',index:'id', hidden:true},
		   		{name:'name',index:'name', width:145, editable:true},
		   		{name:'specification',index:'specification', width:80, editable:true},
		   		{name:'place',index:'place', width:80, sortable:false, editable:true},
		   		{name:'brand',index:'brand', width:60, sortable:false, editable:true},
		   		{name:'unit',index:'unit', width:40, align:"right", sortable:false, editable:true},
		   		{name:'subnum',index:'subnum', width:50, align:"right", sortable:false, editable:true},
		   		{name:'subunit',index:'subunit', width:50, align:"right", sortable:false, editable:true},
		   		{name:'price',index:'price', width:60, align:"right", sortable:false, editable:true},		
		   		{name:'address',index:'address', width:100, sortable:false, editable:true},		
		   		{name:'condition',index:'condition', width:120, sortable:false, editable:true},
		   		{name:'margin',index:'margin', width:60, align:"right", sortable:false, editable:true},
		   		{name:'pcode',index:'pcode', width:60, sortable:false, editable:true},
		   		{name:'isself',index:'isself', width:60, align:"center", sortable:false, editable:true,edittype:"select",editoptions:{value:"0:×;1:√"}}
		   	],
		   	rowNum:10,
		   	rowList:[10,20,30],
		   	pager: '#pager',
		   	sortname: 'id',
		   	height: '100%',
		    viewrecords: true,
		    editurl : "editReagent",
		    sortorder: "asc",
		    caption:"<h5><b>试剂基本信息</b></h5>"
		});
		jQuery("#list").jqGrid('navGrid','#pager',{edit:false,add:false,del:true});
		jQuery("#list").jqGrid('inlineNav',"#pager");
	});