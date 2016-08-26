<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp" %>
<head>
    <link rel="stylesheet" href="<c:url value="/styles/font-awesome.css"/>"/>
    <link rel="stylesheet" href="<c:url value="/styles/bootstrap-datetimepicker.min.css"/>"/>

    <!-- page specific plugin styles -->
    <link rel="stylesheet" href="<c:url value="/styles/bootstrap-duallistbox.min.css"/>"/>
    <link rel="stylesheet" href="<c:url value="/styles/ztree/zTreeStyle.css"/>" type="text/css">
    <link rel="stylesheet" type="text/css" href="<c:url value='/styles/ui.jqgrid.css'/>"/>

    <script src="<c:url value="/scripts/jquery.bootstrap-duallistbox.min.js"/>"></script>
    <script src="<c:url value="/scripts/jquery.ztree.all-3.5.js"/>"></script>
    <script src="<c:url value="/scripts/layer/layer.js"/>"></script>
    <script type="text/javascript" src="/scripts/i18n/grid.locale-cn.js"></script>
    <script src="<c:url value="/scripts/jquery.jqGrid.js"/>"></script>

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

    .table-bordered > thead > tr > td, .table-bordered > thead > tr > th {
        background-color: #F5F5F6;
    }

    .laftnav {
        /*background: #ffffff;*/
        border-right: 1px solid #D9D9D9;
        border-left: 1px solid #D9D9D9;
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

    th[aria-selected=true] {
        background-image: none;
        filter: none;
    }

    .ui-jqgrid .ui-jqgrid-labels {
        border-bottom: none;
        background-image: none;
        filter: none;
    }
</style>
</head>
<div class="row" id="maincontent">
    <div class="col-xs-2">
        <div class="widget-box widget-color-green ui-sortable-handle">
            <div class="widget-header">
                <h6 class="widget-title">客户列表</h6>
                <div class="widget-toolbar">
                    <a href="#" data-action="collapse">
                        <i class="ace-icon fa fa-chevron-up"></i>
                    </a>
                </div>
            </div>

            <div class="widget-body" style="display: block;">
                <div class="widget-main padding-4 scrollable ace-scroll" style="position: relative;">
                    <div class="scroll-content" style="overflow: scroll">
                        <div class="content">
                            <table id="leftGrid"></table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="col-xs-10 leftContent">
        <div class="row">
            <div class="space-4"></div>
            <div class="col-xs-12">
                <div class="tabbable">
                    <ul class="nav nav-tabs" id="myTab">
                        <li class="active">
                            <a data-toggle="tab" href="#baseDiscount">
                                <i class="blue ace-icon fa  fa-home bigger-120"></i>
                                折扣基本信息
                            </a>
                        </li>
                        <li>
                            <a data-toggle="tab" href="#detailsDiscount">
                                <i class="pink ace-icon fa fa-tachometer bigger-120"></i>
                                折扣明细信息
                            </a>
                        </li>
                        <div style="padding-top: 2px;background: #F8F8F8;">
                            <div class="input-group col-sm-3 " style="float: right;">
                                <input type="text" id="query" class="form-control search-query" placeholder="输入编号或名称"/>
                                <span class="input-group-btn">
                                    <button type="button" class="btn btn-purple btn-sm" onclick="search()">
                                        <fmt:message key="button.search"/>
                                        <i class="ace-icon fa fa-search icon-on-right bigger-110"></i>
                                    </button>
                                </span>
                            </div>
                        </div>
                    </ul>

                    <div class="tab-content">
                        <!--基础折扣信息start-->
                        <div id="baseDiscount" class="tab-pane fade in active">
                            <div class="row" style="overflow: scroll">
                                <div class="col-xs-12">
                                    <table id="basetableList"></table>
                                    <div id="basepager"></div>
                                </div>
                            </div>
                        </div><!--基础折扣信息end-->
                        <!--明细折扣信息start-->
                        <div id="detailsDiscount" class="tab-pane fade">
                            <div class="row" style="overflow: scroll">
                                <div class="col-xs-12">
                                    <table id="detailstableList"></table>
                                    <div id="detailspager"></div>
                                </div>
                            </div>
                        </div>
                        <!--明细折扣信息end-->
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    $(function ($) {
        initLeftGrid();
        initBaseGrid();
        //keyPress 回车检索
        $("#query").keypress(function (e) {
            if (e.keyCode == 13) {
                search();
            }
        });
    })



    /***********************************
     * 初始化列表
     *************************************/
    //左边客户列表
    function initLeftGrid() {
        var clientHeight = $(window).innerHeight();
        var height = clientHeight - $('#head').height() - $('#toolbar').height() - $('.footer-content').height() - 120;

        $('.scroll-content').height(height + 55);
        //设置表格宽度
        $(window).on('resize.jqGrid', function () {
            $('#rightGrid').jqGrid('setGridWidth', $(".leftContent").width(), false);
        })
        $("#leftGrid").jqGrid({
            url: '/dsf/customer/getCustomerList',
            datatype: "json",
            height: height,
            shrinkToFit: false,
            regional: 'cn',
            width: $('.leftContent').width(),
            colNames: ['客户ID', '客户名称', '客户地址'],
            colModel: [
                {name: 'customerid', index: 'customerid', width: 50, sorttype: 'text', sortable: true},
                {name: 'customername', index: 'customername', width: 200},
                {name: 'address', index: 'address', width: 200},
            ],
            loadComplete: function () {
                var table = this;
                setTimeout(function () {
                    updatePagerIcons(table);
                }, 0);
            },
            onSelectRow: function () {
                getBaseData();
            },
            //multiselect : true,
            viewrecords: true,
            rownumbers: true, // 显示行号
            rownumWidth: 35, // the width of the row numbers columns
        });
        $(window).triggerHandler('resize.jqGrid');
    }

    /**
     * 获取基础折扣的数据
     * */
    function getBaseData() {
        var id = $('#leftGrid').jqGrid('getGridParam', 'selrow');
        if (id == null || id == 0) {
            layer.msg('请先选择要添加联系人的客户', {icon: 2, time: 1000});
            return false;
        }
        var rowData = $('#leftGrid').jqGrid('getRowData', id);

        jQuery("#basetableList").jqGrid('setGridParam', {
            url: "/dsf/customerDiscount/data",
            datatype: 'json',
            //发送数据
            postData: {
                "customerid": rowData.customerid,
            },
            page: 1
        }).trigger('reloadGrid');//重新载入
    }
    /*基础折扣列表------*/
    function initBaseGrid() {
        var clientHeight = $(window).innerHeight();
        var height = clientHeight - $('#head').height() - $('#toolbar').height() - $('.footer-content').height() - 198;
        $("#basetableList").jqGrid({
            caption: "客户检验项目对照",
            mtype: "GET",
            datatype: "json",
            width: $('.leftContent').width() - 10,
            colNames: ['id', 'CUSTOMERID', '折扣率','执行依据', 'thebasis', '开始时间', '结束时间', '客户名称', '备注'],
            colModel: [
                {name: 'id', index: 'id', width: 60, hidden: true, key: true},
                {name: 'customerid', index: 'customerid', hidden: true},
                {name: 'discountrate', index: 'discountrate', width: 60, sortable: false},
                {name: 'thebasisname', index: 'thebasisname', width: 50, sortable: false},
                {name: 'thebasis', index: 'thebasis', width: 50, hidden: true},
                {name: 'begintime', index: 'begintime', width: 50, sortable: false},
                {name: 'endtime', index: 'endtime', width: 60, sortable: false},
                {name: 'customername', index: 'customername', width: 60, sortable: false},
                {name: 'remark', index: 'remark', width: 60, sortable: false},
            ],
            loadComplete: function () {
                var table = this;
                setTimeout(function () {
                    updatePagerIcons(table);
                }, 0);
            },
            viewrecords: true,
            shrinkToFit: true,
            altRows: true,
            height: height,
            rowNum: 20,
            rownumbers: true, // 显示行号
            rownumWidth: 35, // the width of the row numbers columns
            pager: "#basepager"
        });

        //增加工具栏按钮
        var setting = $('<span class="widget-toolbar" style="right:1280px;top:1px"><button type="button" id="add" onclick="addNewDiscount()" class="btn btn-xs btn-success" data-toggle="button"> <i class="ace-icon fa fa-plus"></i>增加联系人</button>| <button type="button" id="contactEdit" onclick="editDiscount()" class="btn btn-xs btn-success" data-toggle="button"><i class="ace-icon fa fa-times"></i>编辑联系人</button>| <button type="button" id="delete" onclick="deleteDiscount()" class="btn btn-xs btn-success" data-toggle="button"><i class="ace-icon fa fa-times"></i>删除联系人</button></span>');
        $("#gview_basetableList").find('.ui-jqgrid-titlebar').append(setting);

        $('#basetableList').jqGrid('setGridWidth', $(".leftContent").width(), false);
    }
    ;

    //查询客户的查询方法
    function search() {
        var query = $('#query').val() || '';
        jQuery("#leftGrid").jqGrid('setGridParam', {
            url: "/dsf/customer/getCustomerList",
            datatype: 'json',
            //发送数据
            postData: {"query": query},
            page: 1
        }).trigger('reloadGrid');//重新载入
    }

    function addNewDiscount(method) {
        var activeTab = $('#myTab').find('li a').attr('href');
        var id = $('#leftGrid').jqGrid('getGridParam', 'selrow');
        if (id == null || id == 0) {
            layer.msg('请先选择要添加联系人的客户', {icon: 2, time: 1000});
            return false;
        }
        var rowData = $('#leftGrid').jqGrid('getRowData', id);

        //如果选中的是基础折扣标签
        if (activeTab == "#baseDiscount") {
            layer.open({
                type: 2,
                area: ['770px', '510px'],
                fix: false, //不固定
                maxmin: true,
                shade: 0.5,
                title: "添加基础折扣信息",
                content: '/dsf/customerDiscount/ajaxpopDiscount?method=addBaseDiscount&customerid='+ rowData.customerid+'&customername='+rowData.customername
            })
        }
    }

    function editDiscount() {
        var activeTab = $('#myTab').find('li a').attr('href');
        var id = $('#leftGrid').jqGrid('getGridParam', 'selrow');
        if (id == null || id == 0) {
            layer.msg('请先选择要添加联系人的客户', {icon: 2, time: 1000});
            return false;
        }
        var discountid = $('#basetableList').jqGrid('getGridParam', 'selrow');
        var discountRowData = $('#basetableList').jqGrid('getRowData', discountid);
        if (discountid == null || discountid == 0) {
            layer.msg('请先选择要修改的基础折扣', {icon: 2, time: 1000});
            return false;
        }
        //如果选中的是基础折扣标签
        if (activeTab == "#baseDiscount") {
            layer.open({
                type: 2,
                area: ['770px', '510px'],
                fix: false, //不固定
                maxmin: true,
                shade: 0.5,
                title: "修改基础折扣信息",
                content: '/dsf/customerDiscount/ajaxpopDiscount?method=editBaseDiscount&id=' + discountRowData.id+'&customername='+discountRowData.customername
            })
        }

    }
    function deleteDiscount() {
        var activeTab = $('#myTab').find('li a').attr('href');
        var leftGridid = $('#leftGrid').jqGrid('getGridParam', 'selrow');
        if (leftGridid == null || leftGridid == 0) {
            layer.msg('请先选择要删除联系人的客户', {icon: 2, time: 1000});
            return false;
        }
        var discountid = $('#basetableList').jqGrid('getGridParam', 'selrow');
        if (discountid == null || discountid == 0) {
            layer.msg('请先选择要删除的基础折扣信息', {icon: 2, time: 1000});
            return false;
        }
        var rowData = $('#basetableList').jqGrid('getRowData', discountid);
        //如果选中的是基础折扣标签
        if (activeTab == "#baseDiscount") {
            layer.confirm('确定删除选择数据？', {icon: 3, title: '警告'}, function (index) {
                $.post('/dsf/customerDiscount/deleteDiscount', {
                    id: rowData.id,
                    method: "deleteBaseDiscount"
                }, function (data) {
                    if (jQuery.parseJSON(data).result == 'success') {
                        layer.msg("数据删除成功", {icon: 1, time: 1000});
                        jQuery("#basetableList").jqGrid('delRowData', discountid);
                    } else {
                        layer.msg("数据删除失败", {icon: 2, time: 1000});
                    }
                });
                layer.close(index);
            });
        }
    }

</script>
