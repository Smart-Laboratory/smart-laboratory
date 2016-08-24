<%--
  Created by IntelliJ IDEA.
  User: zhou
  Date: 2016/5/17
  Time: 16:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp" %>
<head>
    <script type="text/javascript" src="../scripts/jquery-2.1.4.min.js"></script>
    <script type="text/javascript" src="../scripts/jquery-ui.min.js"></script>
    <script type="text/javascript" src="../scripts/i18n/grid.locale-cn.js"></script>
    <script type="text/javascript" src="../scripts/jquery.jqGrid.js"></script>
    <script type="text/javascript" src="../scripts/validform/Validform.min.js"></script>
    <script type="text/javascript" src="../scripts/layer/layer.js"></script>
    <script type="text/javascript" src="../scripts/set/dictionary.js"></script>
    <link rel="stylesheet" type="text/css"  href="<c:url value='/styles/ui.jqgrid.css'/>" />
    <title><fmt:message key="set.dictionary"/></title>
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
    <div class="col-xs-2 treelist">
        <div class="laftnav" >
            <div class="lazy_header">
                <span class="lazy-list-title">
                <i class="fa fa-bars"></i>
                <span class="tip" style="cursor:pointer;">全部类别</span>
                </span>
                <span class="widget-toolbar">
                    <a href="#" data-action="settings" onclick="addType()" title="新增类别">
                        <i class="ace-icon fa fa-plus"></i>
                    </a>
                    <a href="#" data-action="reload"  onclick="removeType()"  title="删除类别">
                        <i class="ace-icon fa fa-times"></i>
                    </a>
                </span>
            </div>
            <div>
                <ul class="nav nav-pills nav-stacked" id="ullist">
                </ul>
            </div>
        </div>
    </div>
    <div class="col-xs-10">
        <div  style="padding-top: 5px;">
            <button type="button" class="btn btn-sm btn-primary " title="添加" onclick="AddSection()">
                <i class="ace-icon fa fa-fire bigger-110"></i>
                <fmt:message key="button.add" />
            </button>
            <button type="button" class="btn btn-sm  btn-success" title="编辑" onclick="editSection()">
                <i class="ace-icon fa fa-pencil-square bigger-110"></i>
                <fmt:message key="button.edit" />
            </button>
            <button type="button" class="btn btn-sm btn-danger" title="删除" onclick="deleteSection()">
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
        <table id="tableList"></table>
        <div id="pager"></div>
    </div>
</div>
<div style="clear: both"></div>
<div id="addDialog" style="display: none;width: 500px;overflow: hidden" class="row">
    <form id="addDictionaryForm" class="form-horizontal" action="<c:url value='../set/dictionary/saveDictionary'/>" method="post">
        <input type="hidden" name="id" id="id" value="0"/>
        <input type="hidden" name="type" id="type" value="${type}"/>
        <div class="form-group">
            <div class="space-4"></div>
            <label class="col-xs-3 control-label no-padding-right" for="sign">标记 </label>
            <div class="col-xs-8">
                <input type="text" id="sign" name= "sign" placeholder="标记" class="col-xs-8"/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label no-padding-right" for="value"> 名称 </label>
            <div class="col-xs-8">
                <input type="text" id="value" name="value" placeholder="名称" class="col-xs-8"/>
            </div>
        </div>
    </form>
</div>
<div id="addType" style="display: none;width: 500px;overflow: hidden" class="row">
    <form id="addTypeForm" class="form-horizontal" action="<c:url value='../set/dictionaryType/saveType'/>" method="post">
        <div class="form-group">
            <div class="space-4"></div>
            <label class="col-xs-3 control-label no-padding-right" for="value"> 名称 </label>
            <div class="col-xs-8">
                <input type="text" id="name" name="name" placeholder="名称" class="col-xs-8"/>
            </div>
        </div>
    </form>
</div>
<script>
    var typeId  ='<%=request.getParameter("type")%>';
</script>
