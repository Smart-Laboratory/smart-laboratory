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
    <script type="text/javascript" src="/scripts/dsf/testItems.js"></script>
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
            <input type="text" id="searchCustomer" class="form-control search-query" placeholder="输入客户编号或名称" />
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
			<button type="button" class="btn btn-sm  btn-success" title="自动对照相同名称的检验项目" onclick="autoComparison()">
				<i class="ace-icon fa fa-pencil-square bigger-110"></i>
				<fmt:message key="button.autoComparison" />
			</button>
			<div class="input-group col-sm-4 " style="float: right;margin-right: 10px" >
				<input type="text" id="query" class="form-control search-query"  placeholder="输入客户检验目的编号或名称" />
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
		<input type="text" id="searchIndex" class="form-control search-query" placeholder="输入本地检验项目ID或者名称" />
	</div>

<div class="" style="padding-top: 5px;">
<div id="searchHeader" class="col-sm-7 leftContent">
	<table id="s3list"></table>
	<div id="s3pager"></div>
</div>

<div class="col-sm-3">
	<div class="tabbable">
		<ul class="nav nav-tabs padding-12 tab-color-green background-green" id="profileTab">
			<li class="">
				<a data-toggle="tab" id="tab3" href="#tab-3" aria-expanded="false">关联项目</a>
			</li>
		</ul>

		<div class="tab-content">
			<div id="tab-3" class="tab-pane active">
				<table class="table" id="testTable">
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
</div>

</body>