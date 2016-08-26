<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp" %>

<head>
    <title><fmt:message key="set.ylsf"/></title>
    <meta name="menu" content="SampleSet"/>

    <script type="text/javascript" src="/scripts/jquery-2.1.4.min.js"></script>
    <script type="text/javascript" src="/scripts/jquery-ui.min.js"></script>
    <script type="text/javascript" src="/scripts/i18n/grid.locale-cn.js"></script>
    <script type="text/javascript" src="/scripts/jquery.jqGrid.js"></script>
    <script type="text/javascript" src="/scripts/validform/Validform.min.js"></script>
    <script type="text/javascript" src="/scripts/layer/layer.js"></script>
    <script type="text/javascript" src="/scripts/dsf/testObjective.js"></script>
	<script type="text/javascript" src="<c:url value="/scripts/bootstrap-datetimepicker.min.js"/>"></script>

	<link rel="stylesheet" type="text/css"  href="<c:url value='/styles/jquery-ui.min.css'/>" />
    <link rel="stylesheet" type="text/css"  href="<c:url value='/styles/ui.jqgrid.css'/>" />
	<link rel="stylesheet" type="text/css" href="<c:url value="/styles/bootstrap-datetimepicker.min.css"/>"/>

	<style>

		.nav-tabs.tab-color-green>li>a,.nav-tabs.tab-color-green>li>a:focus {
		    background-color: #87b87f
		}

		.nav-tabs[class*="tab-color-"]>li.active>a,.nav-tabs[class*="tab-color-"]>li.active>a:focus,.nav-tabs[class*="tab-color-"]>li.active>a:hover {
		    background-color: #FFF;
		    color: #4f80a0;
		    -webkit-box-shadow: none !important;
		    box-shadow: none !important
		}

		.nav-tabs.tab-color-green>li.active>a,.nav-tabs.tab-color-green>li.active>a:focus,.nav-tabs.tab-color-green>li.active>a:hover {
		    color: #4c718a;
		    border-color: #87b87f #87b87f transparent
		}

		.tabs-below .nav-tabs.tab-color-green>li.active>a {
		    border-color: transparent #87b87f #87b87f
		}

		.nav-tabs.tab-color-green {
		    border-bottom-color: #C5D0DC
		}

		.nav-tabs.background-green {
		    padding-top: 6px;
		    background-color: #EFF3F8;
		    border: 1px solid #C5D0DC
		}
		.ui-autocomplete {
			z-index: 999;
			margin:0;
			padding:0;
			border:0;
		}
		#profileTab .table>tbody>tr, .table>tbody>tr, .table>tfoot>tr, .table>tfoot>tr, .table>thead>tr, .table>thead>tr {
			border: 1px solid #ddd;
		}

	</style>
</head>
<body>
<div class="row" id="toolbar">
	<div class="col-xs-2">
        <div class="input-group col-sm-12" style="padding-top: 5px;">
            <input type="text" id="searchCustomer" class="form-control search-query" placeholder="输入编号或名称" />
		<span class="input-group-btn">
			<button type="button" class="btn btn-info btn-sm" onclick="searchCustomer()" style="margin-left: 5px">
				<i class="ace-icon fa fa-fire bigger-110"></i>
				<fmt:message key="button.search"/>
			</button>
		</span>
        </div>

		<div class="widget-box widget-color-green ui-sortable-handle">
			<div class="widget-header">
				<h6 class="widget-title">客户列表</h6>
				<div class="widget-toolbar">
					<a href="#" data-action="collapse">
						<i class="ace-icon fa fa-chevron-up"></i>
					</a>
				</div>
			</div>

			<div class="widget-body" style="display: block;">
				<div class="widget-main padding-4 scrollable ace-scroll" style="position: relative;">
					<div class="scroll-content" style="overflow: scroll">
						<div class="content">
							<table id="leftGrid"></table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="col-sm-7">
		<div style="padding-top: 5px;">
			<button type="button" class="btn btn-sm btn-primary " title="添加检验目的" onclick="AddYlxh()">
				<i class="ace-icon fa fa-fire bigger-110"></i>
				<fmt:message key="button.add" />
			</button>
			<button type="button" class="btn btn-sm  btn-success" title="编辑检验目的" onclick="editYlxh()">
				<i class="ace-icon fa fa-pencil-square bigger-110"></i>
				<fmt:message key="button.edit" />
			</button>
			<div class="input-group col-sm-4 " style="float: right;margin-right: 10px" >
				<input type="text" id="query" class="form-control search-query"  placeholder="输入检验目的编号或名称" />
				<span class="input-group-btn">
					<button type="button" class="btn btn-purple btn-sm" onclick="search()" style="margin-left: 5px">
						<fmt:message key="button.search"/>
						<i class="ace-icon fa fa-search icon-on-right bigger-110"></i>
					</button>
				</span>
			</div>
		</div>
	</div>
	<div class="input-group col-sm-3" style="padding-top: 5px;">
		<input type="text" id="searchIndex" class="form-control search-query" placeholder="输入项目ID或者名称" />
		<input type="hidden" id="searchIndexId" name="searchIndexId" value=''/>
		<span class="input-group-btn">
			<button type="button" class="btn btn-info btn-sm" onclick="addTest()" style="margin-left: 5px">
				<i class="ace-icon fa fa-fire bigger-110"></i>
				<fmt:message key="button.add"/>
			</button>
		</span>
	</div>

<div class="" style="padding-top: 5px;">
<div id="searchHeader" class="col-sm-7 leftContent">
	<table id="s3list"></table>
	<div id="s3pager"></div>
</div>

<div class="col-sm-3">
	<div class="tabbable">
		<ul class="nav nav-tabs padding-12 tab-color-green background-green" id="profileTab">
			<li class="active">
				<a data-toggle="tab" id="tab1"  href="#tab-1" aria-expanded="true">必做项目</a>
			</li>

			<li class="">
				<a data-toggle="tab" id="tab2" href="#tab-2" aria-expanded="false">可选项目</a>
			</li>

			<li class="">
				<a data-toggle="tab" id="tab3" href="#tab-3" aria-expanded="false">关联项目</a>
			</li>
		</ul>

		<div class="tab-content">
			<div id="tab-1" class="tab-pane active">
				<table class="table" id="testTable">
					<tr>
						<th>必做项目</th>
						<th>&nbsp;</th>
					</tr>
				</table>
			</div>

			<div id="tab-2" class="tab-pane">
				<table class="table" id="testTable2">
					<tr>
						<th>可选项目</th>
						<th>&nbsp;</th>
					</tr>
				</table>
			</div>

			<div id="tab-3" class="tab-pane">
				<table class="table" id="testTable3">
					<tr>
						<th>关联项目</th>
						<th>&nbsp;</th>
					</tr>
				</table>
			</div>
		</div>
	</div>
</div>

<input type="hidden" id="lab" value="${lab}">
</div>

<div id="YlxhDialog" style="display: none;width:600px;" class="main-container">
	<form class="form-horizontal" id="YlxhForm" action="#" method="post">
		<input type="hidden" id="customerid" name="customerid" value="">
	<div class="row" style="padding-top: 5px;">
		<div class="col-xs-6">
			<div class="form-group" style="margin-left:0px;margin-right:0px;">
				<div class="space-4"></div>
				<label class="col-xs-4 control-label no-padding-right" for="ylxh"> 检验目的序号 </label>
				<div class="col-xs-8">
					<input type="text" id="ylxh" name= "ylxh" placeholder="检验目的序号" class="col-xs-12" datatype="*1-16" nullmsg="序号不能为空"/>
				</div>
			</div>
			<div class="form-group" style="margin-left:0px;margin-right:0px;">
				<label class="col-xs-4 control-label no-padding-right" for="ylmc"> 检验目的名称 </label>
				<div class="col-xs-8">
					<input type="text" id="ylmc" name="ylmc" placeholder="检验目的" class="col-xs-12" datatype="*1-16" nullmsg="名称不能为空"/>
				</div>
			</div>
			<div class="form-group" style="margin-left:0px;margin-right:0px;">
				<label class="col-xs-4 control-label no-padding-right" for="english"> 英文名称 </label>
				<div class="col-xs-8">
					<input type="text" id="english" name="english" placeholder="英文名称" class="col-xs-12"/>
				</div>
			</div>
			<div class="form-group" style="margin-left:0px;margin-right:0px;">
				<label class="col-xs-4 control-label no-padding-right" for="price"> 价格 </label>
				<div class="col-xs-8">
					<input type="text" id="price" name="price" placeholder="价格" class="col-xs-12" datatype="*1-16" nullmsg="价格不能为空"/>
				</div>
			</div>
			<div class="form-group" style="margin-left:0px;margin-right:0px;">
				<label class="col-xs-4 control-label no-padding-right" for="beginTime"> 开始时间 </label>
				<div class="col-xs-8 input-group" >
					<input type="text" id="beginTime" name="beginTime" placeholder="开始时间" class="col-xs-12 form-control" />
					<span class="input-group-addon" >
						<i class="fa fa-calendar bigger-110"></i>
					</span>
				</div>
			</div>
			<div class="form-group" style="margin-left:0px;margin-right:0px;">
				<label class="col-xs-4 control-label no-padding-right" for="endTime"> 结束时间 </label>
				<div class="col-xs-8 input-group">
					<input type="text" id="endTime" name="endTime" placeholder="结束时间" class="col-xs-12 form-control"/>
					<span class="input-group-addon">
						<i class="fa fa-calendar bigger-110"></i>
					</span>
				</div>
			</div>
		</div>
		<div class="col-xs-6">
			<div class="form-group" style="margin-left:0px;margin-right:0px;">
				<label class="col-xs-4 control-label no-padding-right" for="yblx"> 样本类型 </label>
				<div class="col-xs-8">
					<input type="text" id="yblx" name="yblx" placeholder="样本类型" class="col-xs-12"/>
				</div>
			</div>
			<div class="form-group" style="margin-left:0px;margin-right:0px;">
				<label class="col-xs-4 control-label no-padding-right" for="sglx"> 试管类型 </label>
				<div class="col-xs-8">
					<input type="text" id="sglx" name="sglx" placeholder="试管类型 " class="col-xs-12"/>
				</div>
			</div>
			<div class="form-group" style="margin-left:0px;margin-right:0px;">
				<label class="col-xs-4 control-label no-padding-right" for="bbl"> 标本量 </label>
				<div class="col-xs-8">
					<input type="text" id="bbl" name="bbl" placeholder="标本量 " class="col-xs-12"/>
				</div>
			</div>
			<div class="form-group" style="margin-left:0px;margin-right:0px;">
				<label class="col-xs-4 control-label no-padding-right" for="qbgsj"> 取报告时间 </label>
				<div class="col-xs-8">
					<input type="text" id="qbgsj" name="qbgsj" placeholder="取报告时间" class="col-xs-12"/>
				</div>
			</div>
			<div class="form-group" style="margin-left:0px;margin-right:0px;">
				<label class="col-xs-4 control-label no-padding-right" for="qbgdd"> 取报告地点 </label>
				<div class="col-xs-8">
					<input type="text" id="qbgdd" name="qbgdd" placeholder="取报告地点" class="col-xs-12"/>
				</div>
			</div>
		</div>
		<input type="hidden" id="ksdm" name="ksdm"/>
		<input type="hidden" id="profiletest" name="profiletest"/>
		<input type="hidden" id="profiletest2" name="profiletest2"/>
		<input type="hidden" id="profiletest3" name="profiletest3"/>
	</div>
	</form>
</div>
</div>

</body>