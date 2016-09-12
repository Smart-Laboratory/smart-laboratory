<%--
  Created by IntelliJ IDEA.
  User: zhou
  Date: 2016/9/09
  Time: 16:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp" %>
<head>
    <title>试管设置</title>
    <script type="text/javascript" src="../scripts/jquery-2.1.4.min.js"></script>
    <script type="text/javascript" src="../scripts/jquery-ui.min.js"></script>
    <script type="text/javascript" src="../scripts/i18n/grid.locale-cn.js"></script>
    <script type="text/javascript" src="../scripts/jquery.jqGrid.js"></script>
    <script type="text/javascript" src="../scripts/validform/Validform.min.js"></script>
    <script type="text/javascript" src="../scripts/layer/layer.js"></script>
    <script type="text/javascript" src="../scripts/set/testTube.js"></script>
    <link rel="stylesheet" type="text/css"  href="<c:url value='/styles/ui.jqgrid.css'/>" />
</head>
<style>
    .laftnav{
        /*background: #ffffff;*/
        border-right: 1px solid #D9D9D9;
        border-left: 1px solid #D9D9D9;
    }
    .lazy_header{
        height: 40px;
        background: #F7F7F7 !important;
        border-bottom: 1px solid #D9D9D9;
        border-top: 1px solid #D9D9D9;
        line-height: 35px;
        margin-top:1px;
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
    .no-skin .nav-list>li.active:after{
        border: 1px;
    }
    .no-skin .nav-list>li>a{
        padding-left:20px;
    }
    ul.nav{
        margin-left:0px;
    }
    .nav-pills>li>a{
        border-radius: 0px;
    }
</style>
<div class="row">
    <div class="col-xs-12">
        <div  style="padding-top: 5px;" id="toolbar">
            <button type="button" class="btn btn-sm btn-primary " title="添加" onclick="Add()">
                <i class="ace-icon fa fa-fire bigger-110"></i>
                <fmt:message key="button.add" />
            </button>
            <button type="button" class="btn btn-sm  btn-success" title="编辑" onclick="Edit()">
                <i class="ace-icon fa fa-pencil-square bigger-110"></i>
                <fmt:message key="button.edit" />
            </button>
            <button type="button" class="btn btn-sm btn-danger" title="删除" onclick="Delete()">
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
        <div class="col-xs-12 content">
            <table id="tableList"></table>
            <div id="pager"></div>
        </div>
    </div>
</div>
<div style="clear: both"></div>
<div id="addDialog" style="display: none;width: 400px;overflow: hidden" class="row">
    <form id="addForm" class="form-horizontal" action="<c:url value='../set/device/saveDevice'/>" method="post">
        <input type="hidden" id="id" name="id" placeholder="编号" class="col-xs-8"/>
        <div class="form-group">
            <div class="space-4"></div>
            <label class="col-xs-3 control-label no-padding-right" for="name"> 名称 </label>
            <div class="col-xs-8">
                <input type="text" id="name" name="name" placeholder="名称" class="col-xs-8"/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label no-padding-right" for="price">单价</label>
            <div class="col-xs-8">
                <input type="text" id="price" name="price" placeholder="单价" datatype="/^-?[1-9]+(\.\d+)?$|^-?0(\.\d+)?$|^-?[1-9]+[0-9]*(\.\d+)?$/" nullmsg="不能为空" errormsg="必须为数字，可以有小数" class="col-xs-8"/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label no-padding-right" for="feeItemId">费用项目ID</label>
            <div class="col-xs-8">
                <input type="text" id="feeItemId" name="feeItemId" placeholder="费用项目ID" class="col-xs-8"/>
            </div>
        </div>
    </form>
</div>


