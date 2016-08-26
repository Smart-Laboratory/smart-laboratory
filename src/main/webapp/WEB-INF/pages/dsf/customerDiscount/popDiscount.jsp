<%--
  Created by IntelliJ IDEA.
  User: zjn
  Date: 2016/8/24
  Time: 16:46
  Description:折扣编辑表单
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp" %>
<head>
    <script type="text/javascript" src="<c:url value="/scripts/jquery-2.1.4.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/jquery-ui.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/bootstrap.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/ace.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/ace-elements.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/bootstrap-tag.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/i18n/grid.locale-cn.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/jquery.form.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/jquery.jqGrid.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/layer/layer.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/validform/Validform.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/jquery.bootstrap-duallistbox.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/bootstrap-datetimepicker.min.js"/>"></script>

    <link rel="stylesheet" type="text/css" href="<c:url value='/styles/ui.jqgrid.css'/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value='/styles/bootstrap.min.css'/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value='/styles/font-awesome.css'/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value='/styles/ace.min.css'/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/styles/font-awesome.css"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/styles/bootstrap-datetimepicker.min.css"/>"/>
    <!-- page specific plugin styles -->
    <link rel="stylesheet" type="text/css" href="<c:url value="/styles/bootstrap-duallistbox.min.css"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/styles/ztree/zTreeStyle.css"/>" type="text/css">

</head>
<style>
    .tab-content {
        padding: 2px 12px;
        min-height: 510px;
    }

    .widget-toolbar {
        z-index: 999;
        line-height: 34px;
    }

    .bootstrap-duallistbox-container .buttons {
        display: none;
    }

    .lazy_header .input-icon, .nav-search-input {
        width: 100%;
    }

    .form-horizontal .form-group {
        margin-right: 0px;
        margin-left: 0px;
    }

    div.treeList {
        overflow: auto;
        min-height: 510px;
        overflow: auto;
        border: 1px solid #eeeeee;
    }

    .info-container {
        display: none;
    }
</style>
<div class="main-container" id="content">
    <div class="row">
        <div class="space-4"></div>
        <div class="col-xs-12">
            <div class="widget-toolbar no-border">
                <button class="btn btn-xs btn-info" onclick="save()">
                    <i class="ace-icon fa fa-floppy-o bigger-120"></i>
                    保存
                </button>
            </div>

            <div class="tabbable">
                <ul class="nav nav-tabs" id="myTab">
                    <c:if test="${method=='addBaseDiscount'||method=='editBaseDiscount'}">
                        <li class="active">
                            <a data-toggle="tab" href="#baseDiscount">
                                <i class="blue ace-icon fa  fa-home bigger-120"></i>
                                常用信息
                            </a>
                        </li>
                    </c:if>
                    <c:if test="${method=='1'||method=='2'}">
                        <li>
                            <a data-toggle="tab" href="#detailsDiscount">
                                <i class="pink ace-icon fa fa-tachometer bigger-120"></i>
                                不常用信息
                            </a>
                        </li>
                    </c:if>
                </ul>
                <form class="form-horizontal" name="infoForm" id="infoForm" action="/dsf/customerDiscount/saveDiscount">
                    <input type="text" name="customerid" id="customerid" value="${customerid}">
                    <div class="tab-content">
                        <c:if test="${method=='addBaseDiscount'||method=='editBaseDiscount'}">
                            <!--常用信息start-->
                            <div id="baseDiscount" class="tab-pane fade in active">
                                <div class="row">
                                    <div class="col-xs-12">
                                        <input type="hidden" name="id" id="id"
                                               value="${discountInfo.id}">
                                        <fieldset>
                                            <div class="space-10"></div>
                                            <div class="form-group controls controls-row">
                                                <label class="col-sm-2 control-label" for="customername">客户名称</label>

                                                <div class="col-sm-8">
                                                    <input class="form-control" id="customername" name="customername" readonly
                                                           type="text"
                                                           placeholder="客户名称" value="${customername}" datatype="*"
                                                           nullmsg="客户名称不能为空"/>
                                                </div>
                                            </div>
                                            <div class="form-group controls controls-row">
                                                <label class="col-sm-2 control-label" for="discountrate">折扣率(%)</label>

                                                <div class="col-sm-8">
                                                    <input class="form-control" id="discountrate" name="discountrate"
                                                           type="text"
                                                           value="${discountInfo.discountrate}"
                                                           placeholder="折扣率(%),只输入数字即可"/>
                                                </div>
                                            </div>
                                            <div class="form-group controls controls-row">
                                                <label class="col-sm-2 control-label" for="thebasis">执行依据</label>

                                                <div class="col-sm-8">
                                                    <select id="thebasis" name="thebasis" class="form-control">
                                                        <option value="1"
                                                                <c:if test="${discountInfo.thebasis=='1'}">selected="selected"</c:if>  >
                                                            合同
                                                        </option>
                                                        <option value="2"
                                                                <c:if test="${discountInfo.thebasis=='2'}">selected="selected"</c:if> >
                                                            备案
                                                        </option>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="form-group controls controls-row">
                                                <label class="col-sm-2 control-label" for="remark">备注</label>
                                                <div class="col-sm-8">
                                                    <input class="form-control" id="remark" name="remark"
                                                           type="text"
                                                           value="${discountInfo.remark}" placeholder="备注"/>
                                                </div>
                                            </div>
                                            <div class="form-group controls controls-row">
                                                <label class="col-sm-2 control-label" for="begintime">开始时间</label>
                                                <div class="col-sm-8">
                                                    <div class="input-group">
                                                        <input class="form-control" id="begintime" name="begintime"
                                                               type="text"
                                                               placeholder="开始时间" value="${discountInfo.begintime}"/>
                                                        <span class="input-group-addon">
                                                                <i class="fa fa-calendar bigger-110"></i>
                                                        </span>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="form-group controls controls-row">
                                                <label class="col-sm-2 control-label" for="endtime">结束时间</label>
                                                <div class="col-sm-8">
                                                    <div class="input-group">
                                                        <input id="endtime" type="text" name="endtime"
                                                               value="${discountInfo.endtime}"
                                                               class="form-control" placeholder="结束时间"/>
                                                        <span class="input-group-addon">
                                                            <i class="fa fa-calendar bigger-110"></i>
                                                        </span>
                                                    </div>
                                                </div>
                                            </div>
                                        </fieldset>
                                    </div>
                                </div>
                            </div>
                            <!--常用信息end-->
                        </c:if>
                        <c:if test="${method=='2'||method=='1'}">
                            <!--不常用信息start-->
                            <div id="detailsDiscount" class="tab-pane fade in active">

                            </div>
                            <!--不常用信息end-->
                        </c:if>
                    </div>

                </form>
            </div>
        </div>
    </div>
</div>
<input type="hidden" name="action_Method" id="action_Method" value="${method}">
<script type="text/javascript">
    jQuery(function ($) {
        $('#begintime').datetimepicker().next().on(ace.click_event, function () {
            $(this).prev().focus();
        });
        $('#endtime').datetimepicker().next().on(ace.click_event, function () {
            $(this).prev().focus();
        });
    })

    /***保存数据****/
    function save() {
        //当前选中Tab
        var activeTab = $('#myTab').find('li a').attr('href');
        var method = $('#action_Method').val();
        if (activeTab == '#baseDiscount') {
            //检测必填项
            if ($('#customername').val() == '') {
                layer.msg("客户名称不能为空,请输入！", {icon: 2, time: 1000});
                return false;
            }
            if ($('#discountrate').val() == '') {
                layer.msg("折扣不能为空,请重新输入！", {icon: 2, time: 1000});
                return false;
            }
            if (parseInt($('#discountrate').val().length) > 2) {
                layer.msg("折扣为2位数,请重新输入！", {icon: 2, time: 1000});
                return false;
            }
            var re = /^[0-9]*[1-9][0-9]*$/;
            if (!re.test($('#discountrate').val())) {
                layer.msg("折扣必须为正整数,请重新输入！", {icon: 2, time: 1000});
                return false;
            }
            if ($('#begintime').val()==''||$('#endtime').val()=='') {
                layer.msg("起止时间不能为空,请重新输入！", {icon: 2, time: 1000});
                return false;
            }
            if ('editBaseDiscount' == method) {
                $.ajax({
                    url: "/dsf/customerDiscount/editDiscount",
                    type: 'POST',
                    dataType: "json",
                    data: $('#infoForm').serialize() + "&method=" + method,
                    success: function (data) {
                        if (data && data.msg == 'success') {
                            layer.msg("数据保存成功", {icon: 1, time: 1000});
                            window.parent.getBaseData();
                            setTimeout(function () {
                                var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
                                parent.layer.close(index);
                            }, 1000)
                        }
                        if (data && data.msg == 'error') {
                            layer.msg("数据保存失败", {icon: 1, time: 1000});
                            setTimeout(function () {
                                var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
                                parent.layer.close(index);
                            }, 1000)
                        }
                    }
                })
            }
            if ('addBaseDiscount' == method) {
                $.ajax({
                    url: "/dsf/customerDiscount/saveDiscount",
                    type: 'POST',
                    dataType: "json",
                    data: $('#infoForm').serialize() + "&method=" + method,
                    success: function (data) {
                        if (data && data.msg == 'success') {
                            layer.msg("数据保存成功", {icon: 1, time: 1000});
                            window.parent.getBaseData();
                            setTimeout(function () {
                                var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
                                parent.layer.close(index);
                            }, 1000)
                        }
                        if (data && data.msg == 'repeat') {
                            layer.msg("客户名称重复，请检查后重新提交", {icon: 1, time: 1000});
                        }
                        if (data && data.msg == 'error') {
                            layer.msg("数据保存失败", {icon: 1, time: 1000});
                            setTimeout(function () {
                                var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
                                parent.layer.close(index);
                            }, 1000)
                        }
                    }
                })
            }
        } else if (activeTab == '#detailsDiscount') {

        }
    }
</script>