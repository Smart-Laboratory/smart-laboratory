	$(function() {
		var width = $("#mid").width();
		jQuery("#list").jqGrid({
		   	url:'../reagent/getCombo?q=1',
			datatype: "json",
			width:width,
		   	colNames:['','名称','创建者','创建时间'],
		   	colModel:[
		   		{name:'id',index:'id', hidden:true},
		   		{name:'name',index:'name', width:"40%", sortable:true, editable:true},
		   		{name:'creator',index:'creator', width:"30%", align:"center", sortable:false, editable:false},
		   		{name:'createtime',index:'createtime', width:"30%", align:"center", sortable:false, editable:false}
		   	],
		   	rowNum:10,
		   	rownumbers:true,
		   	rowList:[10,20,30],
		   	pager: '#pager',
		   	sortname: 'id',
		   	height: '100%',
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
					url:"../reagent/getByCombo?q=2&id="+row_id,
					datatype: "json",
					colNames: ['','名称[规格]','产地', '品牌', '包装','单价'],
					colModel: [
					    {name:'id',index:'id', hidden:true},
						{name:'name',index:'name', width:255, editable:true, editoptions:{
							dataInit:function(elem){
								$(elem).autocomplete({
							        source: function( request, response ) {
							            $.ajax({
							            	url: "../ajax/reagent/getReagent",
							                dataType: "json",
							                data: {
							                    name : request.term
							                },
							                success: function( data ) {
							                	response( $.map( data, function( result ) {
							                		return {
							                            label: result.name,
							                            value: result.name,
							                            id : result.id
							                        }
							                    }));
							                    $(elem).removeClass("ui-autocomplete-loading");
							                }
							            });
							        },
							        minLength: 1
								});
							}
						}},
						{name:'place',index:'place', width:100, sortable:false},
						{name:'brand',index:'brand', width:80, sortable:false},
						{name:'baozhuang',index:'baozhuang', width:100, align:"right", sortable:false},
						{name:'price',index:'price', width:60, align:"right", sortable:false}
					],
				   	rowNum:20,
				   	pager: pager_id,
				   	sortname: 'num',
				    sortorder: "asc",
				    editurl : "../ajax/reagent/candr?cid=" + row_id,
				    height: '100%'
				});
				jQuery("#"+subgrid_table_id).jqGrid('navGrid',"#"+pager_id,{edit:false,add:true,del:true})
		    },
		    subGridRowColapsed : function(subgrid_id, row_id) {
	          // this function is called before removing the data 
	          //var subgrid_table_id; 
	          //subgrid_table_id = subgrid_id+"_t"; 
	          //jQuery("#"+subgrid_table_id).remove();
	        }
		});
		jQuery("#list").jqGrid('navGrid','#pager',{edit:false,add:false,del:true});
		jQuery("#list").jqGrid('inlineNav',"#pager");
	});