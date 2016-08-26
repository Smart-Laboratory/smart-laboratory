<%--
  Created by IntelliJ IDEA.
  User: zjn
  Date: 2016/8/4
  Time: 16:22
  Description:指标编辑表单
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
                    <c:if test="${method=='addNewCustomer'||method=='editCustomer'}">
                        <li class="active">
                            <a data-toggle="tab" href="#customer">
                                <i class="blue ace-icon fa  fa-home bigger-120"></i>
                                常用信息
                            </a>
                        </li>
                    </c:if>
                    <c:if test="${method=='addNewContact'||method=='editContact'}">
                        <li>
                            <a data-toggle="tab" href="#contacts">
                                <i class="pink ace-icon fa fa-tachometer bigger-120"></i>
                                不常用信息
                            </a>
                        </li>
                    </c:if>
                </ul>
                <form class="form-horizontal" name="infoForm" id="infoForm" action="/dsf/customer/saveCustomer">
                    <input type="hidden" name="customerid" id="customerid" value="${customerid}">

                    <div class="tab-content">
                        <c:if test="${method=='addNewCustomer'||method=='editCustomer'}">
                            <!--常用信息start-->
                            <div id="customer" class="tab-pane fade in active">
                                <div class="row">
                                    <div class="col-xs-12">
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
                            </div>
                            <!--常用信息end-->
                        </c:if>
                        <c:if test="${method=='addNewContact'||method=='editContact'}">
                            <!--不常用信息start-->
                            <div id="contacts" class="tab-pane fade in active">
                                <div class="row">
                                    <div class="col-xs-12">
                                        <input type="hidden" name="serialnumber" id="serialnumber"
                                               value="${contactInfo.serialnumber}">
                                        <fieldset>
                                            <div class="space-10"></div>
                                            <div class="form-group controls controls-row">
                                                <label class="col-sm-1 control-label" for="name">名字</label>

                                                <div class="col-sm-5">
                                                    <input class="form-control" id="name" name="name"
                                                           type="text"
                                                           placeholder="名字" value="${contactInfo.name}" datatype="*"
                                                           nullmsg="名字不能为空"/>
                                                </div>
                                                <label class="col-sm-1 control-label" for="age">年龄</label>

                                                <div class="col-sm-5">
                                                    <input class="form-control" id="age" name="age" type="text"
                                                           value="${contactInfo.age}" placeholder="年龄"/>
                                                </div>
                                            </div>
                                            <div class="form-group controls controls-row">
                                                <label class="col-sm-1 control-label" for="sex">性别</label>
                                                <div class="col-sm-5">
                                                    <select id="sex" name="sex" class="form-control">
                                                        <option value="1"
                                                                <c:if test="${contactInfo.sex=='1'}">selected="selected"</c:if>  >
                                                            男
                                                        </option>
                                                        <option value="2"
                                                                <c:if test="${contactInfo.sex=='2'}">selected="selected"</c:if> >
                                                            女
                                                        </option>
                                                    </select>
                                                </div>

                                                <label class="col-sm-1 control-label" for="position">职位</label>

                                                <div class="col-sm-5">
                                                    <input class="form-control" id="position" name="position"
                                                           type="text"
                                                           value="${contactInfo.position}" placeholder="职位"/>
                                                </div>
                                            </div>
                                            <div class="form-group controls controls-row">
                                                <label class="col-sm-1 control-label" for="hobby">爱好</label>

                                                <div class="col-sm-5">
                                                    <input class="form-control" id="hobby" name="hobby"
                                                           type="text"
                                                           placeholder="爱好" value="${contactInfo.hobby}"/>
                                                </div>
                                                <label class="col-sm-1 control-label" for="birthday">生日</label>

                                                <div class="col-sm-5">
                                                    <div class="input-group">
                                                        <input id="birthday" type="text" name="birthday"
                                                               value="${contactInfo.birthday}"
                                                               class="form-control" placeholder="生日"/>
                                                        <span class="input-group-addon">
                                                            <i class="fa fa-calendar bigger-110"></i>
                                                        </span>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="form-group controls controls-row">
                                                <label class="col-sm-1 control-label" for="worktelephone">单位电话</label>

                                                <div class="col-sm-5">
                                                    <input class="form-control" id="worktelephone" name="worktelephone"
                                                           type="text"
                                                           placeholder="单位电话" value="${contactInfo.worktelephone}"/>
                                                </div>
                                                <label class="col-sm-1 control-label" for="homephone">家庭电话</label>

                                                <div class="col-sm-5">
                                                    <input class="form-control" id="homephone" name="homephone"
                                                           type="text"
                                                           value="${contactInfo.homephone}" placeholder="家庭电话"/>
                                                </div>
                                            </div>
                                            <div class="form-group controls controls-row">
                                                <label class="col-sm-1 control-label" for="phonenumber">手机号码</label>

                                                <div class="col-sm-5">
                                                    <input class="form-control" id="phonenumber" name="phonenumber"
                                                           type="text"
                                                           placeholder="手机号码" value="${contactInfo.phonenumber}"/>
                                                </div>
                                                <label class="col-sm-1 control-label"
                                                       for="scepticsofcompany">对公司态度</label>

                                                <div class="col-sm-5">
                                                    <input class="form-control" id="scepticsofcompany"
                                                           name="scepticsofcompany" type="text"
                                                           value="${contactInfo.scepticsofcompany}"
                                                           placeholder="对公司态度"/>
                                                </div>
                                            </div>
                                            <div class="form-group controls controls-row">
                                                <label class="col-sm-1 control-label"
                                                       for="besttimetovisit">最佳拜访时间</label>

                                                <div class="col-sm-5">
                                                    <input class="form-control" id="besttimetovisit"
                                                           name="besttimetovisit"
                                                           type="text"
                                                           placeholder="最佳拜访时间" value="${contactInfo.besttimetovisit}"/>
                                                </div>
                                                <label class="col-sm-1 control-label"
                                                       for="bestplacetovisit">最佳拜访地点</label>

                                                <div class="col-sm-5">
                                                    <input class="form-control" id="bestplacetovisit"
                                                           name="bestplacetovisit" type="text"
                                                           value="${contactInfo.bestplacetovisit}"
                                                           placeholder="最佳拜访地点"/>
                                                </div>
                                            </div>
                                            <div class="form-group controls controls-row">
                                                <label class="col-sm-1 control-label" for="bestcallroute">最佳拜访路线</label>

                                                <div class="col-sm-5">
                                                    <input class="form-control" id="bestcallroute" name="bestcallroute"
                                                           type="text"
                                                           placeholder="最佳拜访路线" value="${contactInfo.bestcallroute}"/>
                                                </div>
                                                <label class="col-sm-1 control-label" for="maritalstatus">婚姻状况</label>

                                                <div class="col-sm-5">
                                                    <select id="maritalstatus" name="maritalstatus"
                                                            class="form-control">
                                                        <option value="1"
                                                                <c:if test="${contactInfo.maritalstatus=='1'}">selected="selected"</c:if> >
                                                            已婚
                                                        </option>
                                                        <option value="0"
                                                                <c:if test="${contactInfo.maritalstatus=='0'}">selected="selected"</c:if> >
                                                            未婚
                                                        </option>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="form-group controls controls-row">
                                                <label class="col-sm-1 control-label" for="spousename">配偶姓名</label>

                                                <div class="col-sm-5">
                                                    <input class="form-control" id="spousename" name="spousename"
                                                           type="text"
                                                           placeholder="配偶姓名" value="${contactInfo.spousename}"/>
                                                </div>
                                                <label class="col-sm-1 control-label"
                                                       for="spouseoccupation">配偶职业</label>

                                                <div class="col-sm-5">
                                                    <input class="form-control" id="spouseoccupation"
                                                           name="spouseoccupation" type="text"
                                                           value="${contactInfo.spouseoccupation}" placeholder="配偶职业"/>
                                                </div>
                                            </div>
                                            <div class="form-group controls controls-row">
                                                <label class="col-sm-1 control-label" for="spousehobby">配偶爱好</label>

                                                <div class="col-sm-5">
                                                    <input class="form-control" id="spousehobby" name="spousehobby"
                                                           type="text"
                                                           placeholder="配偶爱好" value="${contactInfo.spousehobby}"/>
                                                </div>
                                                <label class="col-sm-1 control-label" for="remarks">备注</label>

                                                <div class="col-sm-5">
                                                    <input class="form-control" id="remarks" name="remarks" type="text"
                                                           value="${contactInfo.remarks}" placeholder="备注"/>
                                                </div>
                                            </div>
                                        </fieldset>

                                    </div>
                                </div>
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
        $('#birthday').datetimepicker().next().on(ace.click_event, function () {
            $(this).prev().focus();
        });
    })

    /***保存数据****/
    function save() {
        //当前选中Tab
        var activeTab = $('#myTab').find('li a').attr('href');
        var method = $('#action_Method').val();
        if (activeTab == '#customer') {
            //检测必填项
            if ($('#customername').val() == '') {
                layer.msg("客户名称不能为空,请输入！", {icon: 2, time: 1000});
                return false;
            }

            if ($('#clientnumber').val() == '') {
                layer.msg("客户号不能为空,请输入！", {icon: 2, time: 1000});
                return false;
            }
            if ($('#sequence').val() == '') {
                layer.msg("序列不能为空,请输入！", {icon: 2, time: 1000});
                return false;
            }
            var re = /^[0-9]*[1-9][0-9]*$/;
            if (!re.test($('#sequence').val())) {
                layer.msg("序列必须为正整数,请重新输入！", {icon: 2, time: 1000});
                return false;
            }
            if ('editCustomer' == method) {
                $.ajax({
                    url: "/dsf/customer/editCustomer",
                    type: 'POST',
                    dataType: "json",
                    data: $('#infoForm').serialize() + "&method=" + method,
                    success: function (data) {
                        if (data && data.msg == 'success') {
                            layer.msg("数据保存成功", {icon: 1, time: 1000});
                            window.parent.search();
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
            } else {
                $.ajax({
                    url: "/dsf/customer/saveCustomer",
                    type: 'POST',
                    dataType: "json",
                    data: $('#infoForm').serialize() + "&method=" + method,
                    success: function (data) {
                        if (data && data.msg == 'success') {
                            layer.msg("数据保存成功", {icon: 1, time: 1000});
                            window.parent.search();
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
        } else if (activeTab == '#contacts') {
            if('editContact' == method){
                //检测必填项
                if ($('#name').val() == '') {
                    layer.msg("客户联系人名称不能为空,请输入！", {icon: 2, time: 1000});
                    return false;
                }
                $.ajax({
                    url: "/dsf/customer/editCustomer",
                    type: 'POST',
                    dataType: "json",
                    data: $('#infoForm').serialize() + "&method=" + method,
                    success: function (data) {
                        if (data && data.msg == 'success') {
                            layer.msg("数据保存成功", {icon: 1, time: 1000});
                            window.parent.viewCustomerInfo();
                            setTimeout(function () {
                                var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
                                parent.layer.close(index);
                            }, 1000)
                        }
                        if (data && data.msg == 'repeat') {
                            layer.msg("联系人名称重复，请检查后重新提交", {icon: 1, time: 1000});
                        }
                        if (data && data.msg == 'error') {
                            layer.msg("出现错误，数据保存失败", {icon: 1, time: 1000});
                            setTimeout(function () {
                                var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
                                parent.layer.close(index);
                            }, 1000)
                        }
                    }
                })
            }else{
                //检测必填项
                if ($('#name').val() == '') {
                    layer.msg("客户联系人名称不能为空,请输入！", {icon: 2, time: 1000});
                    return false;
                }
                $.ajax({
                    url: "/dsf/customer/saveCustomer",
                    type: 'POST',
                    dataType: "json",
                    data: $('#infoForm').serialize() + "&method=" + method,
                    success: function (data) {
                        if (data && data.msg == 'success') {
                            layer.msg("数据保存成功", {icon: 1, time: 1000});
                            window.parent.viewCustomerInfo();
                            setTimeout(function () {
                                var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
                                parent.layer.close(index);
                            }, 1000)
                        }
                        if (data && data.msg == 'repeat') {
                            layer.msg("联系人名称重复，请检查后重新提交", {icon: 1, time: 1000});
                        }
                        if (data && data.msg == 'error') {
                            layer.msg("出现错误，数据保存失败", {icon: 1, time: 1000});
                            setTimeout(function () {
                                var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
                                parent.layer.close(index);
                            }, 1000)
                        }
                    }
                })
            }
        }
    }
</script>