<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp" %>
<title><fmt:message key="set.section"/></title>
<!--<meta name="menu" content="SampleSet"/>-->
<head>
	<script type="text/javascript" src="../scripts/jquery-2.1.4.min.js"></script>
	<script type="text/javascript" src="../scripts/bootstrap.min.js"></script>
	<!--script type="text/javascript" src="../scripts/jquery.tablednd_0_5.js"></script-->
	<script type="text/javascript" src="../scripts/jquery-ui.min.js"></script>
	<script type="text/javascript" src="../scripts/i18n/grid.locale-cn.js"></script>
	<script type="text/javascript" src="../scripts/jquery.jqGrid.js"></script>
	<script type="text/javascript" src="../scripts/jquery.validate.js"></script>
	<script type="text/javascript" src="../scripts/jquery.validate.js"></script>
	<script type="text/javascript" src="../scripts/layer/layer.js"></script>
	<!--link rel="stylesheet" type="text/css"  href="<c:url value='/styles/jquery-ui.min.css'/>" /-->
	<link rel="stylesheet" type="text/css"  href="<c:url value='/styles/bootstrap.min.css'/>" />
	<link rel="stylesheet" type="text/css"  href="<c:url value='/styles/ui.jqgrid.css'/>" />

<style>

</style>

</head>
<div class="col-sm-12" style="background: #ffffff" id="mainTable">
	<div  style="padding-top: 10px;">
		<button type="button" class="btn btn-sm btn-success " title="添加科室" onclick="AddSection('添加科室')">
			<i class="ace-icon fa fa-fire bigger-110"></i>
			<fmt:message key="button.add" />
		</button>
		<button type="button" class="btn btn-sm btn-danger " title="删除科室">
			<i class="ace-icon fa fa-times bigger-110"></i>
			<fmt:message key="button.delete" />
		</button>
		<div class="input-group col-sm-3 " style="float: right;" >
			<input type="text" class="form-control search-query" placeholder="输入查询内容" />
			<span class="input-group-btn">
				<button type="button" class="btn btn-purple btn-sm">
					<fmt:message key="button.search"/>
					<i class="ace-icon fa fa-search icon-on-right bigger-110"></i>
				</button>
			</span>
		</div>
	</div>
	<table id="sectionList" style="width: 100%;"></table>
	<div id="pager" style="width: 100%"></div>
</div>



<script>
	//添加科室
	function  AddSection(title,url,w,h){
		layer_show(title,url,w,h);
	}
	$(function(){

		//var jqGrid = $("#sectionList");
		$("#sectionList").jqGrid({
			caption: "科室设置",
			url: "../set/section/data",
			mtype: "GET",
			datatype: "json",
			colNames: ['ID', '编号', '名称'],
			colModel: [
				{ name: 'id', index: 'id', width: 60, hidden: true },
				{ name: 'code', index: 'code', width: 60 },
				{ name: 'name', index: 'name', width: 100 }
			],
			loadComplete : function() {
				var table = this;
				setTimeout(function(){
					//styleCheckbox(table);
					//updateActionIcons(table);
					updatePagerIcons(table);
					enableTooltips(table);
				}, 0);
			},

			viewrecords: true,
			multiselect: true,
			autowidth: true,
			altRows:true,

			height: 380,
			//height: "100%",
			rowNum: 20,
			rowList:[10,20,30],
			rownumbers: true, // 显示行号
			rownumWidth: 35, // the width of the row numbers columns
			pager: "#pager",//分页控件的id
			subGrid: false//是否启用子表格
		});
		//$(window).triggerHandler('resize.jqGrid');
	})
	function enableTooltips(table) {
		$('.navtable .ui-pg-button').tooltip({container:'body'});
		$(table).find('.ui-pg-div').tooltip({container:'body'});
	}
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
	// 设置jqgrid的宽度
	/*$(window).bind('resize', function () {
		var width = $('.jqGrid_wrapper').width();
		jqGrid.setGridWidth(width);
	});
	*/

	/*弹出层*/
	/*
	 参数解释：
	 title	标题
	 url		请求的url
	 id		需要操作的数据id
	 w		弹出层宽度（缺省调默认值）
	 h		弹出层高度（缺省调默认值）
	 */
	function layer_show(title,url,w,h){
		if (title == null || title == '') {
			title=false;
		};
		if (url == null || url == '') {
			url="404.html";
		};
		if (w == null || w == '') {
			w=800;
		};
		if (h == null || h == '') {
			h=($(window).height() - 50);
		};
		layer.open({
			type: 2,
			area: [w+'px', h +'px'],
			fix: false, //不固定
			maxmin: true,
			shade:0.4,
			title: title,
			content: url
		});
	}
	/*关闭弹出框口*/
	function layer_close(){
		var index = parent.layer.getFrameIndex(window.name);
		parent.layer.close(index);
	}
</script>
