<%--
  Created by IntelliJ IDEA.
  User: zhou
  Date: 2016/6/7
  Time: 15:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp" %>
<head>
    <link rel="stylesheet" type="text/css"  href="<c:url value='/styles/ui.jqgrid.css'/>" />
    <link rel="stylesheet" type="text/css"  href="<c:url value="/styles/bootstrap-datetimepicker.min.css"/>" />
    <script type="text/javascript" src="../scripts/i18n/grid.locale-cn.js"></script>
    <script type="text/javascript" src="../scripts/jquery.jqGrid.js"></script>
    <script type="text/javascript" src="../scripts/layer/layer.js"></script>
    <script type="text/javascript" src="<c:url value="/scripts/jquery-ui.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/i18n/grid.locale-cn.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/jquery.form.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/jquery.bootstrap-duallistbox.min.js"/>"></script>


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
        .bootstrap-duallistbox-container .buttons{
            display: none;
        }
        .bootstrap-duallistbox-container .info-container{
            display: none;
        }
        .ui-autocomplete{
            z-index: 9999;
        }
        /*.ui-jqgrid-btable th,*/
        /*.ui-jqgrid tr.ui-row-ltr td, .ui-jqgrid tr.ui-row-rtl td{*/
            /*padding: 3px 4px !important;*/
        /*}*/
        li {list-style-type:none;}
    </style>
</head>
<div class="row" id="toolbar">
    <div class="col-xs-12">
        <div  style="padding-top: 5px;">
            <button type="button" class="btn btn-sm btn-primary " title="添加" onclick="TSLAB.Custom.Add()">
                <i class="ace-icon fa fa-fire bigger-110"></i>
                <fmt:message key="button.add" />
            </button>
            <button type="button" class="btn btn-sm  btn-success" title="编辑" onclick="TSLAB.Custom.Edit()">
                <i class="ace-icon fa fa-pencil-square bigger-110"></i>
                <fmt:message key="button.edit" />
            </button>
            <button type="button" class="btn btn-sm btn-danger" title="删除" onclick="TSLAB.Custom.Delete()">
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
    <div class="col-xs-3 leftContent">
        <table id="leftGrid"></table>
        <div id="leftPager"></div>
    </div>
    <div class="col-xs-9 rightContent">
        <table id="rightGrid"></table>
        <div id="rightPager"></div>
    </div>
</div>


<div style="clear: both"></div>
<div id="addDialog" style="display: none;overflow: hidden">
    <form id="addForm" class="form-horizontal" action="<c:url value='../micro/cultureresult/save'/>" method="post">
        <input type="hidden" id="id" name="id" />
        <div class="form-group">
            <div class="space-4"></div>
            <label class="col-xs-2 control-label no-padding-right" for="drugGroupName">药敏组名称 </label>
            <div class="col-xs-10">
                <input type="text" id="drugGroupName" name="drugGroupName" placeholder="名称" class="col-xs-11"/>
            </div>
        </div>
        <div class="form-group">
            <div class="panel panel-default" style="border:none !important;">
                <div class="panel-body" style="margin: 0px 15px !important;">
                <select multiple="multiple" size="8" name="druglist" id="druglist">
                </select>
                </div>
            </div>
        </div>
    </form>
</div>
<textarea name="antibiotics" id="antibiotics" style="display: none">${antibiotics}</textarea>
<script type="text/javascript" src="<c:url value="/scripts/micro/druggroup.js"/>"></script>
