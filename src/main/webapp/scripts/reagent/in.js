	function addRow(id, type) {
		$.ajax({
			type: "GET",
            url: "../../ajax/reagent/indata",
            data: {id:id, type:type},
            dataType: "json",
            success: function(data){
        		for(var i = 0; i < data.records; i++) {
        			if(typeof($("#list").jqGrid("getRowData", data.rows[i].id)["id"]) == 'undefined') {
        				$("#list").jqGrid("addRowData", data.rows[i].id, data.rows[i]);
        			} else {
        				if(type == 1) {
        					alert($("#list").jqGrid("getRowData", data.rows[i].id)["name"] + "已存在，无需重复选取！");
        				}
        			}
        		}
            }
        });
		
	}

	$(function() {
		$("#printDialog").dialog({
			title: "条码打印",
			autoOpen: false,
			resizable: false,
			modal:true,
		    width: 680,
		    height: 600
		});
		
		$('#reagent_select').change(function(){
			if($(this).children('option:selected').val() == 3) {
				$("#reagentdes").attr('placeholder','提示:输入关键字(支持中文名称和拼音首字母)，然后在下拉菜单中选择套餐');
			} else if($(this).children('option:selected').val() == 2) {
				$("#reagentdes").attr('placeholder','提示:输入关键字(支持中文名称和拼音首字母)，然后在下拉菜单中选择您要的物资');
			} else if($(this).children('option:selected').val() == 1) {
				$("#reagentdes").attr('placeholder','提示:输入或扫描医院库房出库单条码');
			}
		});
		
		$("#inBtn").click(function(){
			var selectids = $("#list").jqGrid("getGridParam", "selarrrow");
			var str = "";
			for(var i in selectids) {
				var id = selectids[i];
				var batch = $("#" + selectids[i] + "_batch").val();
				var num = $("#" + selectids[i] + "_num").val();
				var exedate = $("#" + selectids[i] + "_exedate").val();
				 str += id + ":" + batch + ":" + num + ":" + exedate +";";
			}
			if(str == "") {
		    	alert("请至少选择一种需要出/入库的试剂耗材！");
		    } else {
		    	$.post("../../ajax/reagent/savein",{text:str},function(data) {
		    		$('#printFrame').empty();
			    	$("#printFrame").append("<iframe id='iframe_print' name='iframe_print' frameborder=0 style='background-color:transparent' width='99%' height='93%' src=\"/reagent/print?time=" + data + "\"/>");
					$("#printDialog").dialog("open");
		    		window.location.href="";
				});
		    }
		});
		
		$("#reagentdes").autocomplete({
	        source: function( request, response ) {
	            $.ajax({
	            	url: "../../ajax/reagent/getByType",
	                dataType: "json",
	                data: {
	                	type : $("#reagent_select").val(),
	                    name : request.term
	                },
	                success: function( data ) {$(this).children('option:selected').val()
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
	        	addRow(ui.item.id, $("#reagent_select").val());
	        },
	        close: function( event, ui ) {
	        	$("#reagentdes").val("");
	        }
		});
		
		jQuery("#list").jqGrid({
		   	url:'../reagent/getIn',
			datatype: "json",
		   	colNames:['','名称[规格]','批号','产地', '品牌', '单位[包装]','单价','数量','验收是否合格','失效日期'],
		   	colModel:[
		   		{name:'id',index:'id', hidden:true},
		   		{name:'name',index:'name', width:200},
		   		{name:'batch',index:'batch', width:100},
		   		{name:'place',index:'place', width:100, sortable:false},
		   		{name:'brand',index:'brand', width:80, sortable:false},
		   		{name:'baozhuang',index:'baozhuang', width:100, align:"right", sortable:false},
		   		{name:'price',index:'price', width:60, align:"right", sortable:false},		
		   		{name:'num',index:'num', width:60, sortable:false},		
		   		{name:'isqualified',index:'isqualified', width:80, align:"center", sortable:false},
		   		{name:'exedate',index:'exedate', width:160, align:"center", sortable:false}
		   	],
		   	rowNum: 50,
		   	sortname: 'id',
		   	height: '100%',
		   	multiselect: true,
		    viewrecords: true,
		    sortorder: "asc",
		    caption: "<h5><b>试剂入库</b></h5>"
		});
	});