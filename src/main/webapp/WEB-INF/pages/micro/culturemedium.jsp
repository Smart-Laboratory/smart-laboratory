<%--
  Created by IntelliJ IDEA.
  User: zhou
  Date: 2016/7/5
  Time: 9:49
  Description: 微生物基础信息--培养结果
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp" %>
<head>
    <link rel="stylesheet" href="<c:url value="/styles/font-awesome.css"/>" />
    <link rel="stylesheet" href="<c:url value="/styles/bootstrap-datetimepicker.min.css"/>" />

    <!-- page specific plugin styles -->
    <link rel="stylesheet" href="<c:url value="/styles/bootstrap-duallistbox.min.css"/>"/>
    <link rel="stylesheet" href="<c:url value="/styles/ztree/zTreeStyle.css"/>" type="text/css">
    <link rel="stylesheet" type="text/css"  href="<c:url value='/styles/ui.jqgrid.css'/>" />

    <script src="<c:url value="/scripts/jquery.bootstrap-duallistbox.min.js"/>"></script>
    <script src="<c:url value="/scripts/jquery.ztree.all-3.5.js"/>"></script>
    <script src="<c:url value="/scripts/layer/layer.js"/>"></script>
    <script src="<c:url value="/scripts/i18n/grid.locale-cn.js"/>"></script>
    <script src="<c:url value="/scripts/jquery.jqGrid.js"/>"></script>
    <script src="<c:url value="/scripts/micro/culturemedium.js"/>"></script>
</head>

<div class="row">
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
				<button type="button" class="btn btn-purple btn-sm" onclick="TSLAB.Custom.search()">
                    <fmt:message key="button.search"/>
                    <i class="ace-icon fa fa-search icon-on-right bigger-110"></i>
                </button>
			</span>
            </div>
        </div>
        <table id="tableList"></table>
        <div id="pager"></div>
    </div>
</div>
<div style="clear: both"></div>
<div id="addDialog" style="display: none;overflow: hidden">
    <form id="addForm" class="form-horizontal" action="<c:url value='../micro/cultureresult/save'/>" method="post">
        <input type="hidden" id="id" name="id"  />

        <div class="form-group">
            <div class="space-4"></div>
            <label class="col-xs-2 control-label no-padding-right" for="name"> 名称 </label>
            <div class="col-xs-4">
                <input type="text" id="name" name="name" placeholder="名称" class="col-xs-10"/>
            </div>
            <label class="col-xs-2 control-label no-padding-right" for="name"> 简称 </label>
            <div class="col-xs-4">
                <input type="text" id="shortName" name="shortName" placeholder="简称" class="col-xs-10"/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-2 control-label no-padding-right" for="name"> 温度 </label>
            <div class="col-xs-4">
                <input type="text" id="temperature" name="temperature" placeholder="温度" class="col-xs-10"/>
            </div>
            <label class="col-xs-2 control-label no-padding-right" for="name"> 湿度 </label>
            <div class="col-xs-4">
                <input type="text" id="humidity" name="humidity" placeholder="湿度" class="col-xs-10"/>
            </div>
        </div>
        <div class="form-group">
            <div class="space-4"></div>
            <label class="col-xs-2 control-label no-padding-right" for="name"> 空气 </label>
            <div class="col-xs-4">
                <input type="text" id="air" name="air" placeholder="空气" class="col-xs-10"/>
            </div>
            <label class="col-xs-2 control-label no-padding-right" for="name"> 培养周期 </label>
            <div class="col-xs-4">
                <input type="text" id="cycle" name="cycle" placeholder="培养周期" class="col-xs-10"/>
            </div>
        </div>
        <div class="form-group">
            <div class="space-4"></div>
            <label class="col-xs-2 control-label no-padding-right" for="name"> 接种方法 </label>
            <div class="col-xs-4">
                <input type="text" id="vaccinateMethod" name="vaccinateMethod" placeholder="接种方法" class="col-xs-10"/>
            </div>
            <label class="col-xs-2 control-label no-padding-right" for="name"> 培养方法 </label>
            <div class="col-xs-4">
                <input type="text" id="cultureMethod" name="cultureMethod" placeholder="培养方法" class="col-xs-10"/>
            </div>
        </div>
    </form>
</div>



