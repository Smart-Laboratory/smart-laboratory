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
<div class="row" id="toolbar">
    <div class="col-xs-12">
        <div style="padding-top: 2px;background: #F8F8F8;">
            <button type="button" class="btn btn-sm btn-primary " title="添加客户" onclick="Add()">
                <i class="ace-icon fa fa-fire bigger-110"></i>
                <fmt:message key="button.add"/>
            </button>
            <button type="button" class="btn btn-sm btn-primary " title="编辑客户" onclick="Edit()">
                <i class="ace-icon fa fa-fire bigger-110"></i>
                <fmt:message key="button.modify"/>
            </button>
            <button type="button" class="btn btn-sm btn-danger" title="删除客户" onclick="DeleteCustomer()">
                <i class="ace-icon fa fa-times bigger-110"></i>
                <fmt:message key="button.delete"/>
            </button>
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
    </div>
</div>
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
                            <a data-toggle="tab" href="#customer">
                                <i class="blue ace-icon fa  fa-home bigger-120"></i>
                                基本信息
                            </a>
                        </li>
                        <li>
                            <a data-toggle="tab" href="#contacts">
                                <i class="pink ace-icon fa fa-tachometer bigger-120"></i>
                                联系人信息
                            </a>
                        </li>
                    </ul>
                    <form class="form-horizontal" name="infoForm" id="infoForm" action="/dsf/customer/saveCustomer">
                        <div class="tab-content">
                            <!--常用信息start-->
                            <div id="customer" class="tab-pane fade in active">
                                <div class="row">
                                    <div class="col-xs-12">
                                        <input type="hidden" name="customerid" id="customerid"
                                               value="${customerInfo.customerid}">
                                        <%--    <input type="hidden" name="serialnumber" id="serialnumber" value="">--%>
                                        <fieldset>
                                            <div class="space-10"></div>
                                            <div class="form-group controls controls-row">
                                                <label class="col-sm-1 control-label" for="customername">客户名称</label>

                                                <div class="col-sm-5">
                                                    <input class="form-control" id="customername" name="customername"
                                                           type="text"
                                                           placeholder="客户名称" value="${customerInfo.customername}"
                                                           datatype="*"
                                                           nullmsg="客户名称不能为空"/>
                                                </div>
                                                <label class="col-sm-1 control-label" for="address">客户地址</label>

                                                <div class="col-sm-5">
                                                    <input class="form-control" id="address" name="address" type="text"
                                                           value="${customerInfo.address}" placeholder="客户地址"/>
                                                </div>
                                            </div>
                                            <div class="form-group controls controls-row">
                                                <label class="col-sm-1 control-label" for="clientnumber">客户号</label>
                                                <div class="col-sm-5">
                                                    <input class="form-control" id="clientnumber" name="clientnumber"
                                                           type="text"
                                                           placeholder="客户号为六位识别编号" value="${customerInfo.clientnumber}"
                                                           datatype="*"
                                                           nullmsg="客户号不能为空"/>
                                                </div>
                                                <label class="col-sm-1 control-label" for="sequence">序列</label>
                                                <div class="col-sm-5">
                                                    <input class="form-control" id="sequence" name="sequence" type="text"
                                                           value="${customerInfo.sequence}" placeholder="序列，请输入正整数"/>
                                                </div>
                                            </div>

                                        </fieldset>

                                    </div>
                                </div>
                            </div><!--常用信息end-->
                            <!--客户联系人start-->
                            <div id="contacts" class="tab-pane fade">
                                <div class="row" style="overflow: scroll">
                                    <div class="col-xs-12">
                                        <table id="tableList"></table>
                                        <div id="pager"></div>
                                    </div>
                                </div>
                            </div>
                            <!--客户联系人end-->
                        </div>

                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    $(function ($) {
        initLeftGrid();
        initContactGrid();
        //keyPress 回车检索
        $("#query").keypress(function (e) {
            if (e.keyCode == 13) {
                search();
            }
        });
    })

    /**
     * 新增指标项目
     * */
    function Add() {
        layer.open({
            type: 2,
            area: ['1000px', '610px'],
            fix: false, //不固定
            maxmin: true,
            shade: 0.5,
            title: "添加客户信息",
            content: '/dsf/customer/ajaxcustomerRelation?method=addNewCustomer'
        })
    }
    function Edit() {
        var id = $('#leftGrid').jqGrid('getGridParam', 'selrow');
        var rowData = $("#leftGrid").jqGrid('getRowData', id);
        if (id == null || id == 0) {
            layer.msg('请先选择要编辑的数据', {icon: 2, time: 1000});
            return false;
        }
        layer.open({
            type: 2,
            area: ['1000px', '610px'],
            fix: false, //不固定
            maxmin: true,
            shade: 0.5,
            title: "添加客户信息",
            content: '/dsf/customer/ajaxcustomerRelation?method=editCustomer&customerid=' + rowData.customerid
        })
    }

    function viewCustomerInfo() {
        var id = $('#leftGrid').jqGrid('getGridParam', 'selrow');
        if (id == null || id == 0) {
            layer.msg('请先选择要编辑的数据', {icon: 2, time: 1000});
            return false;
        }
        var rowData = $("#leftGrid").jqGrid('getRowData', id);
        $.ajax({
            url: "/dsf/customer/ajaxcustomer",
            type: 'GET',
            dataType: "json",
            data: {"method": "edit", "id": rowData.customerid},
            success: function (data) {
                //赋值基础信息
                $("#customername").val(data.baseCust.customername);
                $("#customerid").val(data.baseCust.customerid);
                $("#address").val(data.baseCust.address);
                $("#clientnumber").val(data.baseCust.clientnumber);
                $("#sequence").val(data.baseCust.sequence);
                //赋值联系人
                $("#tableList").clearGridData();
                if (data.cdList != undefined) {
                    for (var i = 0; i < data.cdList.length; i++) {
                        $("#tableList").jqGrid("addRowData", data.cdList[i].serialnumber, data.cdList[i]);
                    }
                }
            }
        })
    }


    function DeleteCustomer() {
        var rowid = $('#leftGrid').jqGrid('getGridParam', 'selrow');
        if (rowid == null || rowid == 0) {
            layer.msg('请先选择要删除的数据', {icon: 2, time: 1000});
            return false;
        }
        var rowData = $('#leftGrid').jqGrid('getRowData', rowid);
        layer.confirm('确定删除选择数据？', {icon: 3, title: '警告'}, function (index) {
            $.post('/dsf/customer/deleteCustomer', {id: rowData.customerid}, function (data) {
                if (jQuery.parseJSON(data).result == 'success') {
                    layer.msg("数据删除成功", {icon: 1, time: 1000});
                    jQuery("#rightGrid").jqGrid('delRowData', rowid);
                } else {
                    layer.msg("数据删除失败", {icon: 2, time: 1000});
                }
            });
            layer.close(index);
        });
    }
    /***********************************
     * 初始化列表
     *************************************/
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
                viewCustomerInfo();
            },
            //multiselect : true,
            viewrecords: true,
            rownumbers: true, // 显示行号
            rownumWidth: 35, // the width of the row numbers columns
        });
        $(window).triggerHandler('resize.jqGrid');

    }
    /*客户联系人------*/
    function initContactGrid() {
        var clientHeight = $(window).innerHeight();
        var height = clientHeight - $('#head').height() - $('#toolbar').height() - $('.footer-content').height() - 198;
        $("#tableList").jqGrid({
            caption: "设置",
            datatype: "json",
            colNames: ['customerid', '联系人ID', '名称', '年龄', 'sex','性别', '职位', '爱好', '生日', '工作电话', '家庭电话', '手机', '对公司态度', '最佳拜访时间', '最佳拜访地点', '最佳拜访路线', 'maritalstatus','婚姻状况', '配偶姓名', '配偶职业', '配偶爱好', '备注'],
            colModel: [
                {name: 'customerid', index: 'customerid', width: 60, hidden: true},
                {name: 'serialnumber', index: 'serialnumber', width: 70, key: true},
                {name: 'name', index: 'name', width: 100},
                {name: 'age', index: 'age', width: 60,},
                {name: 'sex', index: 'sex', width: 60,hidden: true},
                {name: 'sexname', index: 'sexname', width: 60,},
                {name: 'position', index: 'position', width: 100,},
                {name: 'hobby', index: 'hobby', width: 160,},
                {name: 'birthday', index: 'birthday', width: 160,},
                {name: 'worktelephone', index: 'worktelephone', width: 160,},
                {name: 'homephone', index: 'homephone', width: 120,},
                {name: 'phonenumber', index: 'phonenumber', width: 120,},
                {name: 'scepticsofcompany', index: 'scepticsofcompany', width: 120,},
                {name: 'besttimetovisit', index: 'besttimetovisit', width: 120,},
                {name: 'bestplacetovisit', index: 'bestplacetovisit', width: 120,},
                {name: 'bestcallroute', index: 'bestcallroute', width: 120,},
                {name: 'maritalstatus', index: 'maritalstatus', width: 120,hidden:true},
                {name: 'maritalstatusname', index: 'maritalstatusname', width: 120,},
                {name: 'spousename', index: 'spousename', width: 120,},
                {name: 'spouseoccupation', index: 'spouseoccupation', width: 120,},
                {name: 'spousehobby', index: 'spousehobby', width: 120,},
                {name: 'remarks', index: 'remarks', width: 120,},
            ],
            repeatitems: false,
            viewrecords: true,
            autowidth: true,
            altRows: true,
            height: height,
            rownumbers: true, // 显示行号
            rownumWidth: 35
        });

        //增加工具栏按钮
        var setting = $('<span class="widget-toolbar" style="right:1830px;top:1px"><button type="button" id="add" onclick="addNewContact()" class="btn btn-xs btn-success" data-toggle="button"> <i class="ace-icon fa fa-plus"></i>增加联系人</button>| <button type="button" id="contactEdit" onclick="editContact()" class="btn btn-xs btn-success" data-toggle="button"><i class="ace-icon fa fa-times"></i>编辑联系人</button>| <button type="button" id="delete" onclick="deleteContact()" class="btn btn-xs btn-success" data-toggle="button"><i class="ace-icon fa fa-times"></i>删除联系人</button></span>');
        $("#gview_tableList").find('.ui-jqgrid-titlebar').append(setting);

        $('#tableList').jqGrid('setGridWidth', $(".rightContent").width(), false);
    }
    ;
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

    function addNewContact() {
        var id = $('#leftGrid').jqGrid('getGridParam', 'selrow');
        if (id == null || id == 0) {
            layer.msg('请先选择要添加联系人的客户', {icon: 2, time: 1000});
            return false;
        }
        var rowData = $('#leftGrid').jqGrid('getRowData', id);
        layer.open({
            type: 2,
            area: ['1000px', '610px'],
            fix: false, //不固定
            maxmin: true,
            shade: 0.5,
            title: "添加客户信息",
            content: '/dsf/customer/ajaxcustomerRelation?method=addNewContact&customerid=' + rowData.customerid
        })
    }

    function editContact() {
        var id = $('#leftGrid').jqGrid('getGridParam', 'selrow');
        var rowData = $('#leftGrid').jqGrid('getRowData', id);
        if (id == null || id == 0) {
            layer.msg('请先选择要添加联系人的客户', {icon: 2, time: 1000});
            return false;
        }
        var contactid = $('#tableList').jqGrid('getGridParam', 'selrow');
        var contactRowData = $('#tableList').jqGrid('getRowData', contactid);
        if (contactid == null || contactid == 0) {
            layer.msg('请先选择要删除联系人', {icon: 2, time: 1000});
            return false;
        }
        layer.open({
            type: 2,
            area: ['1000px', '620px'],
            fix: false, //不固定
            maxmin: true,
            shade: 0.5,
            title: "添加客户信息",
            content: '/dsf/customer/ajaxcustomerRelation?method=editContact&serialnumber=' + contactRowData.serialnumber + "&customerid=" + rowData.customerid
        })
    }
    function deleteContact() {
        var leftGridid = $('#leftGrid').jqGrid('getGridParam', 'selrow');
        if (leftGridid == null || leftGridid == 0) {
            layer.msg('请先选择要删除联系人的客户', {icon: 2, time: 1000});
            return false;
        }
        var contactid = $('#tableList').jqGrid('getGridParam', 'selrow');
        if (contactid == null || contactid == 0) {
            layer.msg('请先选择要删除联系人', {icon: 2, time: 1000});
            return false;
        }
        var rowData = $('#tableList').jqGrid('getRowData', contactid);
        layer.confirm('确定删除选择数据？', {icon: 2, title: '警告'}, function (index) {
            $.post('/dsf/customer/deleteCustomer', {
                id: rowData.customerid,
                serialnumber: rowData.serialnumber,
                method: "deleteContact"
            }, function (data) {
                if (jQuery.parseJSON(data).result == 'success') {
                    layer.msg("数据删除成功", {icon: 1, time: 1000});
                    jQuery("#tableList").jqGrid('delRowData', contactid);
                } else {
                    layer.msg("数据删除失败", {icon: 2, time: 1000});
                }
            });
            layer.close(index);
        });

    }

</script>
