	function addRow(id, type) {
		$.ajax({
			type: "GET",
            url: "../ajax/reagent/outdata",
            data: {id:id, type:type},
            dataType: "json",
            success: function(data){
        		for(var i = 0; i < data.records; i++) {
        			if(typeof($("#list").jqGrid("getRowData", data.rows[i].id)["id"]) == 'undefined') {
        				$("#list").jqGrid("addRowData", data.rows[i].id, data.rows[i]);
        			} else {
        				if(type == 1) {
        					layer.alert($("#list").jqGrid("getRowData", data.rows[i].id)["name"] + "已存在，无需重复选取！", {icon: 0, title: "提示"});
        				}
        			}
        		}
            }
        });
	}

	function getData(obj,event) {
		var e=e||event;
        var key = event.keyCode;
        if(navigator.appName=="Netscape"){
			key=e.which;
		}else{
			key=event.keyCode;
		}
		switch(key){
			case 13 : 
				var barcode = $("#reagentdes").val();
				if(barcode.length>=10) {
					barcode = barcode.substring(0,10);
				}
				$.ajax({
					type: 'POST',
					url: "../ajax/reagent/saveout?barcode=" + barcode,
					dataType: "json",
					success: function(data){
						if(data.success == "success") {
							$("#alert").css("display","block");
							$("#alert").prop("class","alert alert-success");
							$("#alert").html("条码为<b>" + barcode + "</b>的试剂已出库！");
						} else {
							$("#alert").css("display","block");
							$("#alert").prop("class","alert alert-danger");
							$("#alert").html(data.error);
						}
						$("#reagentdes").val("").focus();
					}
				});

		}
	}
	
	$(function() {
		$('#reagent_select').change(function(){
			if($(this).children('option:selected').val() == 3) {
				$("#reagentdes").attr('placeholder','提示:输入关键字(支持中文名称和拼音首字母)，然后在下拉菜单中选择套餐');
			} else if($(this).children('option:selected').val() == 2) {
				$("#reagentdes").attr('placeholder','提示:输入关键字(支持中文名称和拼音首字母)，然后在下拉菜单中选择您要的物资');
			} else if($(this).children('option:selected').val() == 1) {
				$("#reagentdes").attr('placeholder','提示:输入或扫描试剂条形码');
			}
			$("#reagentdes").val("").focus();
		});
		
		$("#outBtn").click(function(){
			var selectids = $("#list").jqGrid("getGridParam", "selarrrow");
			var str = "";
			for(var i in selectids) {
				var id = selectids[i];
				var batch = $("#" + selectids[i] + "_batch").val();
				var num = $("#" + selectids[i] + "_num").val();
				 str += id + ":" + batch + ":" + num +";";
			}
			if(str == "") {
		    	alert("请至少选择一种需要出/入库的试剂耗材！");
		    } else {
		    	$.ajax({
					type: 'POST',
					url: "../ajax/reagent/saveout?text=" + str,
					dataType: "json",
					success: function(data){
						if(data.success == "success") {
							$("#alert").css("display","block");
							$("#alert").prop("class","alert alert-success");
							if($("#reagent_select").val() == 2) {
								$("#alert").html("单项试剂已出库！");
							} else {
								$("#alert").html("套餐试剂已出库！");
							}
						} else {
							$("#alert").css("display","block");
							$("#alert").prop("class","alert alert-danger");
							$("#alert").html(data.error);
						}
					}
				});
		    }
		});
		
		$("#reagentdes").autocomplete({
	        source: function( request, response ) {
	            $.ajax({
	            	url: "../ajax/reagent/getByType",
	                dataType: "json",
	                data: {
	                	type : $("#reagent_select").val(),
	                    name : request.term
	                },
	                success: function( data ) {$(this).children('option:selected').val();
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
		
		var width = $("#mid").width();
		jQuery("#list").jqGrid({
		   	url:'../reagent/getOut',
			datatype: "json",
			width:width,
		   	colNames:['','名称[规格]','批号','产地', '品牌', '单位[包装]','单价','数量'],
		   	colModel:[
		   		{name:'id',index:'id', hidden:true},
		   		{name:'name',index:'name', width:"30%"},
		   		{name:'batch',index:'batch', width:"18%"},
		   		{name:'place',index:'place', width:"10%", sortable:false},
		   		{name:'brand',index:'brand', width:"10%", sortable:false},
		   		{name:'baozhuang',index:'baozhuang', width:"12%", align:"right", sortable:false},
		   		{name:'price',index:'price', width:"10%", align:"right", sortable:false},		
		   		{name:'num',index:'num', width:"10%", sortable:false}
		   	],
		   	rowNum: 50,
		   	sortname: 'id',
		   	height: '100%',
		   	multiselect: true,
		    viewrecords: true,
		    sortorder: "asc",
		    caption: "<h5><b>试剂入库</b></h5>"
		});

		$("#reagentdes").focus();

		labChange=function(select){
			$.ajax({
				type: 'POST',
				url: "../audit/labChange?lab="+$(select).children().attr("title"),
				success:function(data){
					var section = $(select).children().attr("title");
					$("#labText").html($(select).children().html());
					jQuery("#list").trigger("reloadGrid");
				}
			});

		};
	});