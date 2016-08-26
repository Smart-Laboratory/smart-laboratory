	$(function() {
		labChange=function(select){
			$.ajax({
				  type: 'POST',
				  url: "../audit/labChange?lab="+$(select).children().attr("title"),
				  success:function(data){
					  var section = $(select).children().attr("title");
					  $("#labText").html($(select).children().html());
					  getReagentSet();
					  
				  }
			});
			
		};
		getReagentSet();
	});
	
	var isFirst = true;
	function getReagentSet(){
		if(isFirst){
			isFirst = false;
			var width = $("#mid").width();
			var clientHeight= $(window).innerHeight();
			var height =clientHeight-$('#head').height()- $('.tabbable').height()-$('.footer-content').height()-170;
			$(window).on('resize.jqGrid', function () {
				$('#list').jqGrid('setGridWidth', width,false);
			});
			jQuery("#list").jqGrid({
			   	url:'../reagent/getReagent',
				datatype: "json",
				width:width,
			   	colNames:['ID','名称','规格','配套仪器', '品牌', '单位','子数量','子单位','单价','存放位置','环境条件','库存界值','注册证号','自制试剂'],
			   	colModel:[
			   		{name:'id',index:'id', width:"3%"},
			   		{name:'name',index:'name', width:"20%", editable:true},
			   		{name:'specification',index:'specification', width:"10%", editable:true},
			   		{name:'place',index:'place', width:"8%", sortable:false, editable:true},
			   		{name:'brand',index:'brand', width:"6%", sortable:false, editable:true},
			   		{name:'unit',index:'unit', width:"4%", align:"right", sortable:false, editable:true},
			   		{name:'subnum',index:'subnum', width:"5%", align:"right", sortable:false, editable:true},
			   		{name:'subunit',index:'subunit', width:"5%", align:"right", sortable:false, editable:true},
			   		{name:'price',index:'price', width:"6%", align:"right", sortable:false, editable:true},		
			   		{name:'address',index:'address', width:"8%", sortable:false, editable:true},
			   		{name:'condition',index:'condition', width:"6%", sortable:false, editable:true},
			   		{name:'margin',index:'margin', width:"6%", align:"right", sortable:false, editable:true},
			   		{name:'pcode',index:'pcode', width:"7%", sortable:false, editable:true},
			   		{name:'isself',index:'isself', width:"6%", align:"center", sortable:false, editable:true,edittype:"select",editoptions:{value:"0:×;1:√"}}
			   	],
				loadComplete : function() {
					var table = this;
					setTimeout(function(){
						updatePagerIcons(table);
					}, 0);
				},
			   	rowNum:20,
			   	rownumbers:true,
			   	rowList:[10,20,30],
			   	pager: '#pager',
			   	sortname: 'id',
			   	height: height,
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
				page : 1
			}).trigger("reloadGrid");
		}
		
	}
