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

	function changeTab(str) {
		if(str.indexOf("in") >=0) {
			$("#inpre").attr("class", "active");
			$("#outpre").removeClass("active");
			$("#indiv").css("display","block");
			$("#outdiv").css("display","none");
			jQuery("#list").trigger("reloadGrid"); 
		} else {
			$("#inpre").removeClass("active");
			$("#outpre").attr("class", "active");
			$("#indiv").css("display","none");
			$("#outdiv").css("display","block");
			jQuery("#list2").trigger("reloadGrid");
		}
	}
	
	function reprint(id) {
		$.get(baseUrl + "/ajax/reagent/print",{id:id},function(data) {
			//layer.msg("试剂重打条码成功！",{icon:1,time:1000});
			for (i = 0; i < data.length; i++) {
				startPrint(data[i]);
			}
		});
	}
	
	function cancel(id) {
		$.post("../ajax/reagent/cancelout",{id:id},function(data) {
    		window.location.href="";
		});
	}
	/**
	 * 设置JQGRID 上下页图标
	 * @param table
	 */
	function updatePagerIcons(table) {
		var replacement =
		{
			'ui-icon-seek-first' : 'ace-icon fa fa-angle-double-left bigger-140',
			'ui-icon-seek-prev' : 'ace-icon fa fa-angle-left bigger-140',
			'ui-icon-seek-next' : 'ace-icon fa fa-angle-right bigger-140',
			'ui-icon-seek-end' : 'ace-icon fa fa-angle-double-right bigger-140'
		};
		$('.ui-pg-table:not(.navtable) > tbody > tr > .ui-pg-button > .ui-icon').each(function(){
			var icon = $(this);
			var $class = $.trim(icon.attr('class').replace('ui-icon', ''));
			if($class in replacement) icon.attr('class', 'ui-icon '+replacement[$class]);
		})
	}
	$(function() {
		var clientHeight= $(window).innerHeight();
		var height =clientHeight-$('#head').height()- $('.tabbable').height()-$('.footer-content').height()-200;

		$('.scroll-content').height(height+65);
		//设置表格宽度
		$(window).on('resize.jqGrid', function () {
			$('#list').jqGrid('setGridWidth', $(".tab-content").width(),false);
			$('#list2').jqGrid('setGridWidth', $(".tab-content").width(),false);
		});
		var width = $('.tab-content').width();
		jQuery("#list").jqGrid({
		   	url:'../reagent/detail/getIn',
			datatype: "json",
			width: width,
			height:height,
		   	colNames:['','名称[规格]','批号','失效日期', '验收是否合格', '入库量','入库人','入库时间',''],
		   	colModel:[
		   		{name:'id',index:'id', hidden:true},
		   		{name:'name',index:'name', width:"24%"},
		   		{name:'batch',index:'batch', width:"10%", sortable:false},
		   		{name:'exdate',index:'exdate', width:"8%", sortable:false},
		   		{name:'isqualified',index:'isqualified', width:"10%", align:"center", sortable:false},
		   		{name:'num',index:'num', width:"6%", align:"center", sortable:false},
		   		{name:'operator',index:'operaator', width:"8%", align:"center", sortable:false},
		   		{name:'indate',index:'indate', width:"12%", sortable:false},
		   		{name:'reprint',index:'reprint', width:"12%", align:"center", sortable:false}
		   	],
			loadComplete : function() {
				var table = this;
				setTimeout(function(){
					updatePagerIcons(table);
				}, 0);
			},
		   	rowNum: 15,
		   	rownumbers:true,
		   	sortname: 'id',
		   	pager: '#pager',
		   	//height: '100%',
		    viewrecords: true,
		    sortorder: "asc",
		    caption: "<h5><b>入库明细</b></h5>"
		});

		jQuery("#list").jqGrid('navGrid','#pager',{edit:false,add:false,del:false});
		
		jQuery("#list2").jqGrid({
		   	url:'../reagent/detail/getOut',
			datatype: "json",
			width:width,
		   	colNames:['','名称[规格]','批号', '出库量','出库人','出库时间', '完成试验项目数', ''],
		   	colModel:[
		   		{name:'id',index:'id', hidden:true},
		   		{name:'name',index:'name', width:"28%"},
		   		{name:'batch',index:'batch', width:"12%"},
		   		{name:'num',index:'num', width:"12%", align:"center", sortable:false},
		   		{name:'operator',index:'operator', align:"center", width:"12%", sortable:false},
		   		{name:'outdate',index:'outdate', width:"12%", sortable:false},
		   		{name:'testnum',index:'testnum', width:"12%", align:"center", sortable:false},		
		   		{name:'cancel',index:'cancel', width:"12%", align:"center", sortable:false},		
		   	],
		   	rowNum: 15,
		   	rownumbers:true,
		   	sortname: 'id',
		   	pager: '#pager2',
			height:height,
		    viewrecords: true,
		    sortorder: "asc",
		    caption: "<h5><b>出库明细</b></h5>"
		});
		jQuery("#list2").jqGrid('navGrid','#pager2',{edit:false,add:false,del:false});
		
		//$("#outdiv").css("display","none");

		labChange=function(select){
			$.ajax({
				type: 'POST',
				url: "../audit/labChange?lab="+$(select).children().attr("title"),
				success:function(data){
					var section = $(select).children().attr("title");
					$("#labText").html($(select).children().html());
					jQuery("#list").trigger("reloadGrid");
					jQuery("#list2").trigger("reloadGrid");
				}
			});

		};
	});
	
	

