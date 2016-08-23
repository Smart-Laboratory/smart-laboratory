<%--
  Created by IntelliJ IDEA.
  User: zhou
  Date: 2016/8/22
  Time: 16:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp" %>
<head>
    <script  src="<c:url value="/scripts/jquery-2.1.4.min.js"/>"> </script>
    <script  src="<c:url value="/scripts/jquery-ui.min.js"/>"></script>
    <script src="<c:url value="/scripts/i18n/grid.locale-cn.js"/>"></script>
    <script src="<c:url value="/scripts/jquery.jqGrid.js"/>"></script>
    <script src="<c:url value="/scripts/jquery.ztree.all-3.5.js"/>"></script>
    <script src="<c:url value="/scripts/layer/layer.js"/>"></script>
    <link rel="stylesheet" type="text/css"  href="<c:url value='/styles/ui.jqgrid.css'/>" />
    <%--<link rel="stylesheet" type="text/css"  href="<c:url value='/styles/ztree/zTreeStyle.css'/>" />--%>
    <link rel="stylesheet" type="text/css"  href="<c:url value='/styles/ztree/zTreeStyle_flat.css'/>" />
    <title>标本采集</title>
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
                <span class="tip" style="cursor:pointer;">病人列表</span>
                </span>
            </div>
            <div>
                <ul class="nav nav-pills nav-stacked" id="tree">
                </ul>
            </div>
        </div>
    </div>
    <div class="col-xs-10">
        <div  style="padding-top: 5px;">
            <button type="button" class="btn btn-sm btn-primary " title="打印" onclick="AddSection()">
                <i class="ace-icon fa fa-fire bigger-110"></i>
                打印
            </button>
            <button type="button" class="btn btn-sm  btn-success" title="补打" onclick="editSection()">
                <i class="ace-icon fa fa-pencil-square bigger-110"></i>
                补打
            </button>
        </div>
        <table id="tableList"></table>
        <div id="pager"></div>
    </div>
</div>
<div style="clear: both"></div>
<textarea id="requestList" style="display: none">${requestList}</textarea>
<script type="text/javascript">
    var TSLAB=TSLAB ||{};
    TSLAB.Custom=(function(){
        var zNode={

        }
        var setting = {
            view: {
                selectedMulti: false
            },
            async: {
                enable: true,
                url:"../nursestation/inexecute/getList",
                autoParam:[],
                otherParam:{},
                dataFilter: filter
            },
            callback: {
                beforeClick: beforeClick,
                beforeAsync: beforeAsync,
                onAsyncError: onAsyncError,
                onAsyncSuccess: onAsyncSuccess
            }
        };
        var public= {
            init:function () {
                $.fn.zTree.init($("#tree"), setting);
            }

        }

        return public;
    })()
    
    $(function () {
        $.fn.zTree.init($("#tree"), setting);
    })
</script>

