	$(function() {
		$("#reagentdes").autocomplete({
	        source: function( request, response ) {
	            $.ajax({
	            	url: "../../ajax/reagent/getByType",
	                dataType: "json",
	                data: {
	                	type : $("#reagent_select").val(),
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
	                    $("#reagentdes").removeClass("ui-autocomplete-loading");
	                }
	            });
	        },
	        minLength: 1,
	        select : function(event, ui) {
	        	$("#reagentdes").val("dsfsd");
	        }
		});
		
		
		jQuery("#list").jqGrid({
		   	url:'../reagent/getIn',
			datatype: "json",
		   	colNames:['','名称[规格]','批号','产地', '品牌', '单位[包装]','单价','数量','外观是否完整','验收是否合格','失效日期'],
		   	colModel:[
		   		{name:'id',index:'id', hidden:true},
		   		{name:'name',index:'name', width:205},
		   		{name:'batch',index:'batch', width:100},
		   		{name:'place',index:'place', width:100, sortable:false},
		   		{name:'brand',index:'brand', width:80, sortable:false},
		   		{name:'baozhuang',index:'baozhuang', width:100, align:"right", sortable:false},
		   		{name:'price',index:'price', width:60, align:"right", sortable:false},		
		   		{name:'num',index:'num', width:60, sortable:false},		
		   		{name:'iscomplete',index:'iscomplete', width:60, sortable:false},
		   		{name:'isqualified',index:'isqualified', width:60, align:"center", sortable:false},
		   		{name:'exedate',index:'exedate', width:140, align:"center", sortable:false}
		   	],
		   	rowNum: 50,
		   	sortname: 'id',
		    viewrecords: true,
		    sortorder: "asc",
		    caption: "<h5><b>试剂入库</b></h5>"
		});
	});