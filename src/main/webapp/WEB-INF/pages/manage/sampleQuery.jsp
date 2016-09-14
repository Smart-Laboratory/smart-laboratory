<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp" %>
<head>
    <title>标本查询</title>

	<script type="text/javascript" src="../scripts/jquery-2.1.4.min.js"></script>
	<script type="text/javascript" src="../scripts/jquery-ui.min.js"></script>
	<script type="text/javascript" src="../scripts/i18n/grid.locale-cn.js"></script>
	<script type="text/javascript" src="../scripts/jquery.jqGrid.js"></script>
	<script type="text/javascript" src="../scripts/layer/layer.js"></script>
	<script type="text/javascript" src="<c:url value="/scripts/layer/extend/layer.ext.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/scripts/laydate/laydate.js"/>"></script>
	<script type="text/javascript" src="../scripts/manage/sampleQuery.js"></script>

	<link rel="stylesheet" type="text/css"  href="<c:url value='/styles/ui.jqgrid.css'/>" />
</head>
<style>
	.laftnav {
		/*background: #ffffff;*/
		/*border-right: 1px solid #D9D9D9;*/
		/*border-left: 1px solid #D9D9D9;*/
		border: 1px solid #82af6f;
	}

	.lazy_header {
		height: 40px;
		background: #82af6f !important;
		color: white;
		border-bottom: 1px solid #D9D9D9;
		border-top: 1px solid #D9D9D9;
		line-height: 35px;
		margin-top: 1px;

	}

	.lazy-list-title {
		font-size: 14px;
		max-width: 100px;
		display: inline-block;
		text-overflow: ellipsis;
		-o-text-overflow: ellipsis;
		white-space: nowrap;
		overflow: hidden;
		padding-left: 5px;
	}

	.no-skin .nav-list > li.active:after {
		border: 1px;
	}

	.no-skin .nav-list > li > a {
		padding-left: 20px;
	}

	ul.nav {
		margin-left: 0px;
	}

	.nav-pills > li > a {
		border-radius: 0px;
	}

	.ui-jqgrid .ui-jqgrid-htable th span.ui-jqgrid-resize {
		height: 30px !important;
	}

	.ui-jqgrid .ui-jqgrid-htable th div {
		padding-top: 5px !important;
	}

	.ui-jqgrid .ui-jqgrid-view input, .ui-jqgrid .ui-jqgrid-view select, .ui-jqgrid .ui-jqgrid-view textarea, .ui-jqgrid .ui-jqgrid-view button {
		margin: 0 0;
	}

	.ui-jqgrid tr.ui-row-ltr td, .ui-jqgrid tr.ui-row-rtl td {
		padding: 2px 4px;
	}

	.ui-jqgrid-htable th div .cbox {
		margin-left: 3px !important;
	}

	/*定义滚动条高宽及背景 高宽分别对应横竖滚动条的尺寸*/
	::-webkit-scrollbar {
		width: 12px;
		height: 12px;
		background-color: #F5F5F5;
	}

	/*定义滚动条轨道 内阴影+圆角*/
	::-webkit-scrollbar-track {
		-webkit-box-shadow: inset 0 0 6px rgba(0, 0, 0, 0.3);
		border-radius: 5px;
		background-color: #F5F5F5;
	}

	/*定义滑块 内阴影+圆角*/
	::-webkit-scrollbar-thumb {
		border-radius: 5px;
		-webkit-box-shadow: inset 0 0 6px rgba(151, 151, 151, 0.3);
		background-color: #9f9f9f;
	}

	table.alert-info tr td {
		padding: 0px 10px;
	}

	.widget-main {
		padding: 5px;
	}

</style>
<input id='lastlab' value="${lastlab }" type='hidden' />

<div class="row" style="margin-top: 5px;">
	<div class="col-xs-3">
		<div class="laftnav">
			<div class="lazy_header">
                <span class="lazy-list-title">
                <i class="fa fa-bars"></i>
                <span class="tip" style="cursor:pointer;">查询条件</span>
                </span>
			</div>
			<div class="row" style="margin:5px;overflow: auto;">
				<input type="text" id="search_text" name="search_text" class="col-xs-9" placeholder="<fmt:message key='sample.query' />"/>
				<button id="searchBtn" class="btn btn-sm btn-info col-xs-3"><fmt:message key="search" /></button>
			</div>
			<div class="row" style="margin:5px;overflow: auto;">
				<div class="col-xs-3" style="text-align:center;line-height: 33px;"><b>起始日期:</b></div>
				<input type="text" id="from" name="from" class="col-xs-9 laydate-icon-molv" style="height:33px;" />
			</div>
			<div class="row" style="margin:5px;overflow: auto;">
				<div class="col-xs-3" style="text-align:center;line-height: 33px;"><b>结束日期:</b></div>
				<input type="text" id="to" name="to" class="col-xs-9 laydate-icon-molv" style="height:33px;">
			</div>
			<div class="row" style="margin:5px;overflow: auto;">
				<div class="col-xs-6">
					<label>
						<input type="radio" name="select_type" id="q_sampleno" value="1" checked class="ace" />
						<span class="lbl"><fmt:message key="sample.query.sampleno" /></span>
					</label>
				</div>
				<div class="col-xs-6">
					<label>
						<input type="radio" name="select_type" id="q_id" value="2" class="ace" />
						<span class="lbl"><fmt:message key="sample.query.id" /></span>
					</label>
				</div>
				<div class="col-xs-6">
					<label>
						<input type="radio" name="select_type" id="q_name" value="3" class="ace" />
						<span class="lbl"><fmt:message key="sample.query.name" /></span>
					</label>
				</div>
				<div class="col-xs-6">
					<label>
						<input type="radio" name="select_type" id="q_blh" value="4" class="ace" />
						<span class="lbl"><fmt:message key="sample.query.blh" /></span>
					</label>
				</div>
				<div class="col-xs-6">
					<label>
						<input type="radio" name="select_type" id="q_patientid" value="5" class="ace" />
						<span class="lbl"><fmt:message key="sample.query.patientid" /></span>
					</label>
				</div>
			</div>
			<div class="row" style="margin:5px;overflow: auto;">
				<div class="col-xs-3" style="text-align:center;line-height: 33px;"><b>就诊方式:</b></div>
				<select id="search_select" class="col-xs-9 select" >
					<option value="0"><fmt:message key="treatmentType.5" /></option>
					<option value="1"><fmt:message key="treatmentType.1" /></option>
					<option value="2"><fmt:message key="treatmentType.2" /></option>
					<option value="3"><fmt:message key="treatmentType.4"></fmt:message></option>
				</select>
			</div>
			<div class="row" style="margin:5px;overflow: auto;">
				<div class="col-xs-3" style="text-align:center;line-height: 33px;"><b>标本类型:</b></div>
				<input type="hidden" id="hiddenSampleType"/>
				<input type="text" id="sampleTypeSearch"  class="col-xs-9"/>
			</div>
			<div class="row" style="margin:5px;overflow: auto;">
				<div class="col-xs-3" style="text-align:center;line-height: 33px;"><b>检验部门:</b></div>
				<select id="labSelect_seach"  class="col-xs-9">
					<option value='all'>全部</option>
					<c:forEach var="depart" items="${departList}">
						<option value='<c:out value="${depart.key}" />'><c:out value="${depart.value}" /></option>
					</c:forEach>
				</select>
			</div>
			<div class="row" style="margin:5px;overflow: auto;">
				<select id="printType"  class="col-xs-9" style="height:34px;">
					<option value='0'>报告单</option>
					<option value='1'>条&nbsp;&nbsp;&nbsp;码</option>
				</select>
				<button id="print" class="btn btn-sm btn-info col-xs-3"><fmt:message key="print" /></button>
			</div>
		</div>
	</div>

	<div class="col-xs-9">
		<div class="widget-box widget-color-green">
			<div class="widget-header">
				<h5 class="widget-title">标本查询列表</h5>
				<div class="widget-toolbar">
					<a href="#" data-action="collapse">
						<i class="ace-icon fa fa-chevron-down"></i>
					</a>
				</div>
			</div>
			<div class="widget-body">
				<div class="widget-main" id="sampleListPanel">
					<table id="list"></table>
					<div id="pager"></div>
				</div>
			</div>
		</div>
	</div>
</div>
<div style="font-size: 13px; display:none;margin-top: 10px;">
	<div style="margin-left:60px;">
		<input type="hidden" id="hiddenDocId"/>
		<input type="text" id="hiddenSampleNo"/>
		<input type="hidden" id="hisLastResult"/>
	</div>
</div>