<%--
  Created by IntelliJ IDEA.
  User: yuzh
  Date: 2016/9/27
  Time: 9:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp" %>

<head>
    <title>样本流转地点设置</title>
    <script type="text/javascript" src="<c:url value='/scripts/jquery-2.1.4.min.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/scripts/jquery-ui.min.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/scripts/i18n/grid.locale-cn.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/scripts/jquery.jqGrid.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/scripts/validform/Validform.min.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/scripts/layer/layer.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/scripts/layer/layer.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/scripts/set/point.js'/>"></script>
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
    </div>
    <div class="col-xs-12 content">
        <table id="tableList"></table>
        <div id="pager"></div>
    </div>
</div>
<div style="clear: both"></div>
<div id="addDialog" style="display: none;width: 400px;overflow: hidden" class="row">
    <form id="addForm" class="form-horizontal" action="<c:url value='../set/point/savePoint'/>" method="post">
        <input type="hidden" id="id" name="id"/>
        <div class="form-group">
            <div class="space-4"></div>
            <label class="col-xs-4 control-label no-padding-right" for="code"> 编码 </label>
            <div class="col-xs-8">
                <input type="text" id="code" name="code" placeholder="编码" class="col-xs-8"/>
            </div>
        </div>
        <div class="form-group">
            <div class="space-4"></div>
            <label class="col-xs-4 control-label no-padding-right" for="name"> 名称 </label>
            <div class="col-xs-8">
                <input type="text" id="name" name="name" placeholder="名称" class="col-xs-8"/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-4 control-label no-padding-right" for="type">类型</label>
            <div class="col-xs-8">
                <select id="type" name="type" class="col-xs-8" style="height: 33px;">
                    <option value="0">接收点</option>
                    <option value="1">送出点</option>
                </select>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-4 control-label no-padding-right" for="lab"> 所属科室 </label>
            <div class="col-xs-8">
                <select id="lab" name="lab" class="col-xs-8" style="height: 33px;">
                    <option value=" "></option>
                    <c:forEach var="item" items="${departList}">
                        <option value='<c:out value="${item.key}"/>' ><c:out value="${item.value}"/> </option>
                    </c:forEach>
                </select>
            </div>
        </div>
    </form>
</div>