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
    <script src="<c:url value="/scripts/jquery-2.1.4.min.js"/>"></script>
    <script src="<c:url value="/scripts/jquery-ui.min.js"/>"></script>
    <script src="<c:url value="/scripts/i18n/grid.locale-cn.js"/>"></script>
    <script src="<c:url value="/scripts/jquery.jqGrid.js"/>"></script>
    <script src="<c:url value="/scripts/layer/layer.js"/>"></script>

    <link rel="stylesheet" type="text/css" href="<c:url value='/styles/ui.jqgrid.css'/>"/>
    <script src="<c:url value="/scripts/jquery.ztree.all-3.5.js"/>"></script>
    <link rel="stylesheet" type="text/css" href="<c:url value='/styles/ztree/zTreeStyle.css'/>"/>
    <title>标本采集</title>
</head>
<style>
    .laftnav {
        /*background: #ffffff;*/
        /*border-right: 1px solid #D9D9D9;*/
        /*border-left: 1px solid #D9D9D9;*/
        border: 1px solid #D9D9D9;
    }

    .lazy_header {
        height: 40px;
        background: #F7F7F7 !important;
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
</style>
<div class="row">
    <div class="col-xs-2 treelist">
        <div class="laftnav">
            <div class="lazy_header">
                <span class="lazy-list-title">
                <i class="fa fa-bars"></i>
                <span class="tip" style="cursor:pointer;">病人列表</span>
                </span>
            </div>
            <div>
                <%--<ul class="nav nav-pills nav-stacked" id="tree">--%>
                <ul id="tree" class="ztree"></ul>
                </ul>
            </div>
        </div>
    </div>
    <div class="col-xs-10">
        <div style="padding-top: 5px;">
            <button type="button" class="btn btn-sm btn-primary " title="打印" onclick="AddSection()">
                <i class="ace-icon fa fa-fire bigger-110"></i>
                打印
            </button>
            <button type="button" class="btn btn-sm  btn-success" title="补打" onclick="editSection()">
                <i class="ace-icon fa fa-pencil-square bigger-110"></i>
                补打
            </button>
        </div>
        <div class="widget-box widget-color-green" id="widget-box-1">
            <div class="widget-header widget-header-small">
                <h6 class="widget-title">病人信息</h6>
                <div class="widget-toolbar">
                    <a href="#" data-action="collapse">
                        <i class="ace-icon fa fa-chevron-down"></i>
                    </a>
                </div>
            </div>
            <div class="widget-body">
                <div class="widget-main">
                    <p class="alert alert-info">
                        Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque commodo massa sed ipsum
                        porttitor facilisis.
                    </p>
                </div>
            </div>
        </div>
        <div class="widget-box widget-color-green" id="widget-box-2">
            <div class="widget-header widget-header-small">
                <h6 class="widget-title">未采集标本</h6>
                <div class="widget-toolbar">
                    <a href="#" data-action="collapse">
                        <i class="ace-icon fa fa-chevron-down"></i>
                    </a>
                </div>
            </div>
            <div class="widget-body">
                <div class="widget-main">
                    <table id="tableList"></table>
                    <div id="pager"></div>
                </div>
            </div>
        </div>
        <div class="widget-box widget-color-green" id="widget-box-3">
            <div class="widget-header widget-header-small">
                <h6 class="widget-title">已采集标本</h6>
                <div class="widget-toolbar">
                    <a href="#" data-action="collapse">
                        <i class="ace-icon fa fa-chevron-down"></i>
                    </a>
                </div>
            </div>
            <div class="widget-body">
                <div class="widget-main">
                    <table id="tableList1"></table>
                    <div id="pager1"></div>
                </div>
            </div>
        </div>
    </div>
</div>
<div style="clear: both"></div>
<textarea id="requestList" style="display: none">${requestList}</textarea>
<script type="text/javascript">
    var TSLAB = TSLAB || {};
    TSLAB.Custom = (function () {
        var private = {
            initTree: function () {
                var setting = {
                    view: {
                        selectedMulti: false
                    },
                    data: {
                        simpleData: {
                            enable: true
                        }
                    }
                };
                $.ajax({
                    url: '../nursestation/inexecute/getList',
                    data: {ward: "1025"},
                    type: 'POST',
                    dataType: "json",
                    ContentType: "application/json; charset=utf-8",
                    success: function (data) {
                        console.log(data)
                        $.fn.zTree.init($("#tree"), setting, data);
                    },
                    error: function (msg) {
                        alert("失败");
                    }
                });
            },
            initGrid:function () {
                $("#tableList").jqGrid({
                    datatype: "json",
                    colNames: ['床号', '姓名','性别', '年龄','项目代码','项目名称','标本种类','金额','申请时间','是否急诊'],
                    colModel: [
                        { name: 'bedno', index: 'channel', width: 60 },
                        { name: 'name', index: 'testid', width: 100},
                        { name: 'sex', index: 'testname', width: 60},
                        { name: 'age', index: 'sampletype', width: 100},
                        { name: 'testId', index: 'sampletype', width: 100,hidden:true},
                        { name: 'testName', index: 'sampletype', width: 120},
                        { name: 'sampleType', index: 'sampletype', width: 100},
                        { name: 'amount', index: 'sampletype', width: 60},
                        { name: 'requestTime', index: 'sampletype', width: 100},
                        { name: 'emergency', index: 'emergency', width: 60}
                    ],
                    onSelectRow: function(id) {
                        if(id && id!==lastsel){
                            jQuery('#tableList').saveRow(lastsel, false, 'clientArray');
                            lastsel=id;
                        }
                        jQuery('#tableList').editRow(id, false);
                    },
                    repeatitems:false,
                    viewrecords: true,
                    autowidth:true,
                    altRows:true,
                    height: "100%",
                    rownumbers: true, // 显示行号
                    rownumWidth: 35
                });

            },
            getSampleList:function(bedno,patientId){


            }
        }
        var public = {
            init: function () {
                private.initTree();
                private.initGrid();
            }

        }
        return public;
    })()

    $(function () {
        var clientHeight = $(window).innerHeight();
        var height = clientHeight - $('#head').height() - $('#toolbar').height() - $('.footer-content').height() - 30;
        $('.laftnav').height(height);
        TSLAB.Custom.init();
        //$.fn.zTree.init($("#tree"), setting);
    })
</script>

