<%--
  Created by IntelliJ IDEA.
  User: zhou
  Date: 2016/7/5
  Time: 9:49
  Description: 微生物基础信息--涂片结果
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
    <script src="<c:url value="/scripts/micro/smearresult.js"/>"></script>
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
<div id="addDialog" style="display: none;width: 500px;overflow: hidden" class="row">
    <form id="addForm" class="form-horizontal" action="<c:url value='../micro/cultureresult/save'/>" method="post">
        <input type="hidden" id="id" name="id"  />
        <div class="form-group">
            <div class="space-4"></div>
            <label class="col-xs-3 control-label no-padding-right" for="name"> 编号 </label>
            <div class="col-xs-8">
                <input type="text" id="indexid" name="indexid" placeholder="编号" class="col-xs-8"/>
            </div>
        </div>
        <div class="form-group">
            <div class="space-4"></div>
            <label class="col-xs-3 control-label no-padding-right" for="name"> 名称 </label>
            <div class="col-xs-8">
                <input type="text" id="name" name="name" placeholder="名称" class="col-xs-8"/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label no-padding-right" for="english">英文名</label>
            <div class="col-xs-8">
                <input type="text" id="english" name="english" placeholder="英文名" class="col-xs-8"/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label no-padding-right" for="english">结果类型</label>
            <div class="col-xs-8">
                <select name="type" id="type">
                    <option value="0">阴性结果</option>
                    <option value="1">阳性结果</option>
                    <option value="2">其他结果</option>
                </select>
            </div>
        </div>
    </form>
</div>



