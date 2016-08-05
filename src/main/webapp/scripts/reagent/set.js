	$(function() {
		$("#labSelect").val($("#sectionval").val());
		
		labChange=function(select){
			$.ajax({
				  type: 'POST',
				  url: "../audit/labChange?lab="+$(select).children().attr("title"),
				  success:function(data){
					  selectNoteAdd = true;
					  $("#nowLab").val($(select).children().attr("title"));
					  var section = $(select).children().attr("title");
					  $("#labText").html($(select).children().html());
					  window.location.href="../reagent/set?section=" + $(select).children().attr("title");
					  
				  }
			});
			
		}
		getCome();
	});
	
	var isfirst = true;
	function getCome(){
		if(isfirst){
			isfirst = false;
			var width = $("#mid").width();
			jQuery("#list").jqGrid({
			   	url:'../reagent/getReagent',
				datatype: "json",
				width:width,
			   	colNames:['','名称','规格','配套仪器', '品牌', '单位','子数量','子单位','单价','存放位置','环境条件','库存界值','注册证号','自制试剂'],
			   	colModel:[
			   		{name:'id',index:'id', hidden:true},
			   		{name:'name',index:'name', width:"20%", editable:true},
			   		{name:'specification',index:'specification', width:"10%", editable:true},
			   		{name:'place',index:'place', width:"8%", sortable:false, editable:true},
			   		{name:'brand',index:'brand', width:"6%", sortable:false, editable:true},
			   		{name:'unit',index:'unit', width:"4%", align:"right", sortable:false, editable:true},
			   		{name:'subnum',index:'subnum', width:"5%", align:"right", sortable:false, editable:true},
			   		{name:'subunit',index:'subunit', width:"5%", align:"right", sortable:false, editable:true},
			   		{name:'price',index:'price', width:"6%", align:"right", sortable:false, editable:true},		
			   		{name:'address',index:'address', width:"10%", sortable:false, editable:true},		
			   		{name:'condition',index:'condition', width:"7%", sortable:false, editable:true},
			   		{name:'margin',index:'margin', width:"6%", align:"right", sortable:false, editable:true},
			   		{name:'pcode',index:'pcode', width:"7%", sortable:false, editable:true},
			   		{name:'isself',index:'isself', width:"6%", align:"center", sortable:false, editable:true,edittype:"select",editoptions:{value:"0:×;1:√"}}
			   	],
			   	rowNum:10,
			   	rownumbers:true,
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
		}
		else{
			jQuery("#list").jqGrid("setGridParam",{
				url:"../reagent/getReagent"
			}).trigger("reloadGrid");
		}
		
	}
	