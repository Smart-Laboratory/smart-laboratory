	var LODOP; //声明为全局变量

	function printSetting() {
		//readPrintFile();
		if (LODOP.CVERSION) {
			LODOP.On_Return = function (TaskID, Value) {
				if (Value >= 0)
					setCookie("lis_print", Value);
				else
					alert("选择失败！");
			};
			LODOP.SELECT_PRINTER();
			return;
		}
	};
	function getCookie(name) {
		var arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");
		if (arr = document.cookie.match(reg))
			return unescape(arr[2]);
		else
			return null;
	};
	function setCookie(name, value) {
		var Days = 9999;
		var exp = new Date();
		exp.setTime(exp.getTime() + Days * 24 * 60 * 60 * 1000);
		document.cookie = name + "=" + escape(value) + ";expires=" + exp.toGMTString();
	};
	function setPrintIndex(index) {
		var index = getCookie("lis_print");
		LODOP = getLodop();
		LODOP.SET_PRINTER_INDEX(index)
	}
	function CreateDataBill(data) {
		if (data && data != null) {
			LODOP.PRINT_INIT("");
			LODOP.SET_PRINT_PAGESIZE(0, 500, 350, "A4");
			LODOP.ADD_PRINT_BARCODEA("barcode", "10.42mm", "2.84mm", "34.66mm", 35, "128Auto", data.barcode);
			LODOP.SET_PRINT_STYLEA(0, "ShowBarText", 0);
			LODOP.SET_PRINT_STYLEA(0, "Horient", 2);
			LODOP.ADD_PRINT_TEXTA("code", "19.39mm", "2.85mm", 161, 25, "*" + data.barcode + "*");
			LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
			LODOP.SET_PRINT_STYLEA(0, "Bold", 1);
			LODOP.ADD_PRINT_TEXTA("name", "2.89mm", "2.95mm", 190, 25, data.name);
			LODOP.ADD_PRINT_TEXTA("batch", "23.36mm", "2.85mm", 180, 20, "LOT:" + data.batch + " " + "EXP:" + data.exdate);
			LODOP.ADD_PRINT_TEXTA("indate", "27.36mm", "2.85mm", 180, 20, "REP:" + data.indate);
			LODOP.ADD_PRINT_TEXTA("exdate", "31.36mm", "2.85mm", 180, 20, "CON:" + data.condition);

		}
	}
	function startPrint(data) {
		CreateDataBill(data);
		setPrintIndex();
		//开始打印
		LODOP.PRINT();
	}

	function printSet() {
		var data ={
			"barcode": "0000492001",
			"batch": "ABR169A",
			"name": "ABO/Rh正反定型血型定型试剂卡[20盒/箱]",
			"exdate": "20170402",
			"indate": "2016-10-29 13:27:13 ",
			"condition": "在2-8℃下"
		}
		CreateDataBill(data);
		LODOP.PRINT_DESIGN();
	}

	function addRow(id, type) {
		$.ajax({
			type: "GET",
            url: "../ajax/reagent/indata",
            data: {id:id, type:type},
            dataType: "json",
            success: function(data){
        		for(var i = 0; i < data.records; i++) {
        			if(typeof($("#list").jqGrid("getRowData", data.rows[i].id)["id"]) == 'undefined') {
        				$("#list").jqGrid("addRowData", data.rows[i].id, data.rows[i]);
        			} else {
        				if(type == 1) {
							layer.alert($("#list").jqGrid("getRowData", data.rows[i].id)["name"] + "已存在，无需重复选取！",{icon:0,title:"提示"});
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
				if($("#reagent_select").val()==1) {
					addRow(obj.value, $("#reagent_select").val());
				}
		}
	}

	//JS校验form表单信息
	function checkData(){
		var fileDir = $("#fileUpload").val();
		var suffix = fileDir.substr(fileDir.lastIndexOf("."));
		if("" == fileDir){
			layer.alert("选择需要导入的Excel文件！",{icon:0,title:"提示"});
			return false;
		}
		if(".xls" != suffix && ".xlsx" != suffix ){
			layer.alert("选择Excel格式的文件导入！",{icon:0,title:"提示"});
			return false;
		}
		return true;
	}

	$(function() {
		$('#reagent_select').change(function(){
			if($(this).children('option:selected').val() == 3) {
				$("#reagentdes").attr('placeholder','提示:输入关键字(支持中文名称和拼音首字母)，然后在下拉菜单中选择套餐');
			} else if($(this).children('option:selected').val() == 2) {
				$("#reagentdes").attr('placeholder','提示:输入关键字(支持中文名称和拼音首字母)，然后在下拉菜单中选择您要的物资');
			} else if($(this).children('option:selected').val() == 1) {
				$("#reagentdes").attr('placeholder','提示:输入或扫描医院库房出库单条码');
			}
			$("#reagentdes").val("").focus();
		});
		
		$("#inBtn").click(function(){
			var selectids = $("#list").jqGrid("getGridParam", "selarrrow");
			var str = "";
			for(var i in selectids) {
				var id = selectids[i];
				var batch = $("#" + selectids[i] + "_batch").val();
				var num = $("#" + selectids[i] + "_num").val();
				var exedate = $("#" + selectids[i] + "_exedate").val();
				if(batch=="" || exedate==""){
					layer.alert("请输入批号/失效日期！",{icon:0,title:"提示"});
					return;
				}
				str += id + ":" + batch + ":" + num + ":" + exedate +";";
			}
			if(str == "") {
				layer.alert("请至少选择一种需要出/入库的试剂耗材！",{icon:0,title:"提示"});
		    } else {
		    	$.post(baseUrl + "/ajax/reagent/savein",{text:str},function(data) {
					for (i = 0; i < data.length; i++) {
						startPrint(data[i]);
					}
				});
		    }
		});

		$("#readExcel").click(function(){
			if(checkData()){
				var uplist = $("input[name^=fileUpload]");
				var arrId = [];
				for (var i=0; i< uplist.length; i++){
					if(uplist[i].value){
						arrId[i] = uplist[i].id;
					}
				}
				$.ajaxFileUpload({
					url:'../ajax/reagent/readExcel',
					fileElementId: arrId,
					dataType: 'json',
					success: function (data, status){
						for(var i = 0; i < data.length; i++) {
							if(typeof($("#list").jqGrid("getRowData", data[i].id)["id"]) == 'undefined') {
								data[i].batch = "<input type='text' id='" + data[i].id + "_batch' style='height:18px;width:98%' value='" + data[i].batch + "'/>";
								data[i].num = "<input type='text' id='" + data[i].id  + "_num' style='height:18px;width:98%' value='" + data[i].num + "'/>";
								data[i].exedate = "<input type='text' id='" + data[i].id + "_exedate' style='height:18px;width:98%' value='" + data[i].exedate + "'/>";

								$("#list").jqGrid("addRowData", data[i].id, data[i]);
							} else {
								layer.msg($("#list").jqGrid("getRowData", data[i].id)["name"] + "已存在，无需重复选取！", {icon: 2, time: 1000});
							}
						}
					},
					error: function () {
						layer.alert("导入excel出错，请核对Excel格式后重新导入！",{icon:2,title:"提示"});
					}
				});
			}
		});

		$("#reagentdes").autocomplete({
	        source: function( request, response ) {
	        	if($("#reagent_select").val() > 1) {
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
	        	}
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
		   	url:'../reagent/getIn',
			datatype: "json",
			width:width,
		   	colNames:['','名称[规格]','批号','产地', '品牌', '单位[包装]','单价','数量','验收是否合格','失效日期'],
		   	colModel:[
		   		{name:'id',index:'id', hidden:true},
		   		{name:'name',index:'name', width:"30%"},
		   		{name:'batch',index:'batch', width:"10%"},
		   		{name:'place',index:'place', width:"8%", sortable:false},
		   		{name:'brand',index:'brand', width:"8%", sortable:false},
		   		{name:'baozhuang',index:'baozhuang', width:"8%", align:"right", sortable:false},
		   		{name:'price',index:'price', width:"6%", align:"right", sortable:false},		
		   		{name:'num',index:'num', width:"6%", sortable:false},		
		   		{name:'isqualified',index:'isqualified', width:"8%", align:"center", sortable:false},
		   		{name:'exedate',index:'exedate', width:"16%", align:"center", sortable:false}
		   	],
		   	rowNum: 50,
		   	sortname: 'id',
		   	height: '100%',
		   	multiselect: true,
		    viewrecords: true,
		    sortorder: "asc",
		    caption: "<h5><b>试剂入库</b></h5>"
		});

		$("#reagentdes").val("").focus();

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