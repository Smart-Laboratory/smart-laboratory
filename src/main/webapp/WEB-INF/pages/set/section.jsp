<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp" %>
<title><fmt:message key="set.section"/></title>
<!--<meta name="menu" content="SampleSet"/>-->
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
	<link rel="stylesheet" type="text/css"  href="<c:url value='/styles/ui.jqgrid.css'/>" />
	<!--script type="text/javascript" src="../scripts/bootstrap.min.js"></script-->
	<script type="text/javascript" src="../scripts/jquery-ui.min.js"></script>
	<script type="text/javascript" src="../scripts/i18n/grid.locale-cn.js"></script>
	<script type="text/javascript" src="../scripts/jquery.jqGrid.js"></script>
	<script type="text/javascript" src="../scripts/validform/Validform.min.js"></script>
	<script type="text/javascript" src="../scripts/layer/layer.js"></script>
	<script type="text/javascript" src="../scripts/set/section.js"></script>
</head>
<style>
	 .ui-jqgrid {
         border: 1px solid #ddd;
     }
     ::-webkit-scrollbar-track {
         background-color: #F5F5F5
     }

     ::-webkit-scrollbar {
         width: 6px;
         background-color: #F5F5F5
     }

     ::-webkit-scrollbar-thumb {
         background-color: #999
     }
     .table-bordered>thead>tr>td, .table-bordered>thead>tr>th{
         background-color: #F5F5F6;
     }
     .ui-autocomplete {
         z-index: 99999999;
     }
</style>
<div class="row" id="toolbar">
	<div id="mainTable" class="col-xs-9">
		<div  style="padding-top: 5px;">
			<button type="button" class="btn btn-sm btn-primary " title="添加科室" onclick="AddSection()">
				<i class="ace-icon fa fa-fire bigger-110"></i>
				<fmt:message key="button.add" />
			</button>
			<button type="button" class="btn btn-sm  btn-success" title="编辑科室" onclick="editSection()">
				<i class="ace-icon fa fa-pencil-square bigger-110"></i>
				<fmt:message key="button.edit" />
			</button>
			<button type="button" class="btn btn-sm btn-danger" title="删除科室" onclick="deleteSection()">
				<i class="ace-icon fa fa-times bigger-110"></i>
				<fmt:message key="button.delete" />
			</button>
			<div class="input-group col-sm-3 " style="float: right;" >
				<input type="text" id="query" class="form-control search-query" placeholder="输入编号或名称" />
				<span class="input-group-btn">
					<button type="button" class="btn btn-purple btn-sm" onclick="search()">
						<fmt:message key="button.search"/>
						<i class="ace-icon fa fa-search icon-on-right bigger-110"></i>
					</button>
				</span>
			</div>
		</div>
	</div>
</div>

<div class="row" id="maincontent">
    <div class="col-xs-9 leftContent">
        <table id="sectionList"></table>
		<div id="pager"></div>
    </div>
    <div class="col-xs-3 rightContent">
        <table id="sectionCode"></table>
        <div id="rightPager"></div>
    </div>
</div>


<div id="addDialog" style="display: none;width:760px;" class="main-container">
	<form class="form-horizontal" id="addSectionForm" action="#" method="post">
	<div class="row" style="padding-top: 5px;">
		<div class="input-group col-xs-6 " style="float: right;">
			<button type="button" class="btn btn-sm btn-primary col-xs-3" style="height:33px;" title="保存" onclick="AddCode()">
                    <i class="ace-icon fa fa-fire bigger-110"></i>新增检验段</button>
            <div class="col-xs-1"> &nbsp;</div>
            <input class="col-xs-8"  id="searchCode" type="text" placeholder="检验段..">
        </div>
		
	
	</div>
	<div class="row" style="padding-top: 5px;">
		<div class="col-xs-6">
			<input type="hidden" name="id" id="id" value="0"/>
			<div class="form-group" style="margin-left:0px;margin-right:0px;">
				<div class="space-4"></div>
				<label class="col-xs-3 control-label no-padding-right" for="code"> 科室编号 </label>
				<div class="col-xs-8">
					<input type="text" id="code" name= "code" placeholder="科室编号" class="col-xs-8" datatype="*2-16" nullmsg="编号不能为空"/>
				</div>
			</div>
			<div class="form-group" style="margin-left:0px;margin-right:0px;">
				<label class="col-xs-3 control-label no-padding-right" for="name"> 科室名称 </label>
				<div class="col-xs-8">
					<input type="text" id="name" name="name" placeholder="科室名称" class="col-xs-8" datatype="*2-16" nullmsg="名称不能为空"/>
				</div>
			</div>	
		</div>
		<input type="hidden" id="segment" name="segment"/>
		<div class="col-xs-6 sectionCode">
            <table id="codeEdit"></table>
        </div>
	</div>
	</form>
</div>

<div id="addSectionCode" style="display: none;" class="main-container">
	<form class="form-horizontal" id="addSectionCodeForm" action="#" method="post">
		<div class="row" style="padding-top: 5px;">
			<div class="form-group" style="margin-left:0px;margin-right:0px;">
				<div class="space-4"></div>
				<label class="col-xs-3 control-label no-padding-right" for="sectioncode">检验段</label>
				<div class="col-xs-8">
					<input type="text" id="sectioncode" name= "sectioncode" placeholder="检验段 " class="col-xs-8" datatype="*2-16" nullmsg="检验段不能为空"/>
				</div>
			</div>
			<div class="form-group" style="margin-left:0px;margin-right:0px;">
				<label class="col-xs-3 control-label no-padding-right" for="describe">检验段描述</label>
				<div class="col-xs-8">
					<input type="text" id="describe" name="describe" placeholder="检验段描述" class="col-xs-8" datatype="*2-16" nullmsg="描述不能为空"/>
				</div>
			</div>
		</div>
	</form>
</div>