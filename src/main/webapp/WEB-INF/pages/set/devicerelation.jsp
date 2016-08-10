<%--
  Created by IntelliJ IDEA.
  User: zhou
  Date: 2016/5/31
  Time: 10:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp" %>
<head>
    <!-- bootstrap & fontawesome -->
    <link rel="stylesheet" href="<c:url value="/styles/font-awesome.css"/>" />
    <link rel="stylesheet" href="<c:url value="/styles/bootstrap-datetimepicker.min.css"/>" />

    <!-- page specific plugin styles -->
    <link rel="stylesheet" href="<c:url value="/styles/bootstrap-duallistbox.min.css"/>"/>
    <link rel="stylesheet" href="<c:url value="/styles/ztree/zTreeStyle.css"/>" type="text/css">
    <link rel="stylesheet" type="text/css"  href="<c:url value='/styles/ui.jqgrid.css'/>" />

    <script src="<c:url value="/scripts/jquery.bootstrap-duallistbox.min.js"/>"></script>
    <script src="<c:url value="/scripts/jquery.ztree.all-3.5.js"/>"></script>
    <script src="<c:url value="/scripts/layer/layer.js"/>"></script>
    <script src="<c:url value="/scripts/bootstrap-datetimepicker.min.js"/>"></script>

    <script type="text/javascript" src="../scripts/jquery.jqGrid.js"></script>
</head>
<style>
    .tab-content{
        padding: 2px 12px;
        min-height: 510px;
    }
    .widget-toolbar{
        z-index: 999;
        line-height:34px;
    }
    .bootstrap-duallistbox-container .buttons{
        display: none;
    }
    .lazy_header .input-icon, .nav-search-input{
        width: 100%;
    }
    .form-horizontal .form-group{
        margin-right: 0px;
        margin-left: 0px;
    }
    div.treeList{
        overflow: auto;
        min-height: 510px;
        overflow: auto;
        border: 1px solid #eeeeee;
    }
</style>

<div class="row">
    <div class="space-2"></div>
    <div class="col-xs-2">
        <div class="laftnav" >
            <div class="lazy_header">
                    <span class="input-icon">
                        <input type="text" id="search"  placeholder="搜索 ..." class="nav-search-input" id="nav-search-input" autocomplete="off">
                        <i class="ace-icon fa fa-search nav-search-icon"></i>
                    </span>
            </div>
            <div class="treeList">
                <ul id="treeList" class="ztree"></ul>
            </div>
        </div>
    </div>
    <div class="col-xs-10">
        <div class="widget-toolbar no-border">
            <button class="btn btn-xs btn-info" onclick="add()">
                <i class="ace-icon fa fa-floppy-o bigger-120"></i>
                添加
            </button>
            <button class="btn btn-xs btn-info" onclick="save()">
                <i class="ace-icon fa fa-floppy-o bigger-120"></i>
                保存
            </button>
        </div>

        <div class="tabbable">
            <ul class="nav nav-tabs" id="myTab">
                <li class="active">
                    <a data-toggle="tab" href="#activeinfo">
                        <i class="blue ace-icon fa  fa-home bigger-120"></i>
                        常用信息
                    </a>
                </li>
                <li>
                    <a data-toggle="tab" href="#inactiveinfo">
                        <i class="pink ace-icon fa fa-tachometer bigger-120"></i>
                        不常用信息
                    </a>
                </li>
                <li >
                    <a data-toggle="tab" href="#relation">
                        <i class="green ace-icon fa fa-cogs bigger-120"></i>
                        测定仪器/部门选择
                    </a>
                </li>
                <li>
                    <a data-toggle="tab" href="#reference">
                        <i class="purple ace-icon fa  fa-comments bigger-120"></i>
                        参考范围
                    </a>
                </li>
            </ul>

            <div class="tab-content">
                <!--常用信息start-->
                <div id="activeinfo" class="tab-pane fade in active">
                    <div class="row">
                        <div class="col-xs-12">
                            <form class="form-horizontal" name="activeInfoForm" id="activeInfoForm"  action="../set/devicerelation/saveActiveInfo">
                                <fieldset>
                                    <div class="space-10"></div>
                                    <div class="form-group controls controls-row">
                                        <label class="col-sm-1 control-label" for="indexid">试验编号</label>
                                        <div class="col-sm-5">
                                            <input class="form-control" id="indexid" type="text" placeholder="试验编号" value="" disabled/>
                                        </div>
                                        <label class="col-sm-1 control-label" for="testclass">分类代码</label>
                                        <div class="col-sm-5">
                                            <input class="form-control" id="testclass" type="text" placeholder="分类代码"/>
                                        </div>
                                    </div>
                                    <div class="form-group controls controls-row">
                                        <label class="col-sm-1 control-label" for="name">中文名称</label>
                                        <div class="col-sm-5">
                                            <input class="form-control" id="name" name="name" type="text" placeholder="中文名称"/>
                                        </div>
                                        <label class="col-sm-1 control-label" for="eglish">英文名称</label>
                                        <div class="col-sm-5">
                                            <input class="form-control" id="eglish" name="eglish" type="text" placeholder="英文名称"/>
                                        </div>
                                    </div>

                                    <div class="form-group controls controls-row">
                                        <label class="col-sm-1 control-label" for="samplefrom">样本类型</label>
                                        <div class="col-sm-5">
                                            <select class="form-control" id="samplefrom" name="samplefrom" placeholder="样本类型" >
                                                <c:forEach items="${samplelist}" var="entry">
                                                    <option value="<c:out value="${entry.key}" />"><c:out value="${entry.value}" /></option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <label class="col-sm-1 control-label" for="labdepartment">检验部门</label>
                                        <div class="col-sm-5">
                                            <input class="form-control" id="labdepartment"  name="labdepartment" type="text" placeholder="检验部门" disabled/>
                                        </div>
                                    </div>
                                    <div class="form-group controls controls-row">
                                        <label class="col-sm-1 control-label" for="type">指标类型</label>
                                        <div class="col-sm-5">
                                            <select id="type" name="type" class="form-control">
                                                <option value="S">字符型</option>
                                                <option value="N">数值型</option>
                                                <option value="E">枚举型</option>
                                            </select>
                                        </div>
                                        <label class="col-sm-1 control-label" for="algorithm">差值算法</label>
                                        <div class="col-sm-5">
                                            <input class="form-control" id="algorithm"  name="algorithm" type="text" placeholder="差值算法" />
                                        </div>
                                    </div>
                                    <div class="form-group controls controls-row">
                                        <label class="col-sm-1 control-label" for="method">检测方法</label>
                                        <div class="col-sm-5">
                                            <input class="form-control" id="method"   name="method" type="text" placeholder="检测方法"/>
                                        </div>
                                        <label class="col-sm-1 control-label" for="description">指标说明</label>
                                        <div class="col-sm-5">
                                            <input class="form-control" id="description"  name="description" type="text" placeholder="指标说明" />
                                        </div>
                                    </div>
                                    <div class="form-group controls controls-row">
                                        <label class="col-sm-1 control-label" for="guide">指南</label>
                                        <div class="col-sm-5">
                                            <input class="form-control" id="guide" name="guide" type="text" placeholder="指南"/>
                                        </div>
                                        <label class="col-sm-1 control-label" for="labdepartment"  style="padding-left: 2px;">显示历史记录</label>
                                        <div class="col-sm-5">
                                            <label class="control-label pull-left inline">
                                                <input id="needhistory" type="checkbox" name="needhistory" value="1" class="ace ace-switch ace-switch-3">
                                                <span class="lbl middle"></span>
                                            </label>
                                        </div>
                                    </div>
                                    <div class="form-group controls controls-row">
                                        <label class="col-sm-1 control-label" for="unit">单位</label>
                                        <div class="col-sm-5">
                                            <input class="form-control" id="unit" name="unit" type="text" placeholder="单位"/>
                                        </div>
                                        <label class="col-sm-1 control-label" for="defaultvalue">默认结果</label>
                                        <div class="col-sm-5">
                                            <input class="form-control" id="defaultvalue" name="defaultvalue" type="text" placeholder="默认结果"/>
                                        </div>
                                    </div>


                                    <div class="form-group controls controls-row">
                                        <label class="col-xs-2 col-sm-1 control-label">质控需要</label>
                                        <div class="col-xs-12 col-sm-5">
                                            <label class="radio-inline">
                                                <input name="qcneed" type="radio" class="ace" value="0">
                                                <span class="lbl"> 不需</span>
                                            </label>
                                            <label class="radio-inline">
                                                <input name="qcneed" type="radio" class="ace"  value="1">
                                                <span class="lbl"> 1个</span>
                                            </label>
                                            <label class="radio-inline">
                                                <input name="qcneed" type="radio" class="ace"  value="2">
                                                <span class="lbl"> 2个</span>
                                            </label>
                                            <label class="radio-inline">
                                                <input name="qcneed" type="radio" class="ace"  value="3">
                                                <span class="lbl"> 3个</span>
                                            </label>
                                        </div>
                                        <label class="col-sm-1 control-label" for="tea">TEa</label>
                                        <div class="col-xs-12 col-sm-5">
                                            <input type="text" class=" col-sm-5" name="tea" id="tea" />
                                            <label class="col-sm-2 control-label right" for="ccv">CCV</label>
                                            <input type="text" class=" col-sm-5"  name="ccv" id="ccv" />
                                        </div>
                                    </div>
                                    <div class="form-group controls controls-row">
                                        <label class="col-sm-1 control-label" for="outdate">失效日期</label>
                                        <div class="col-sm-5">
                                            <div class="input-group">
                                                <input id="outdate" type="text"  name="outdate" class="form-control" />
                                                <span class="input-group-addon">
                                                    <i class="fa fa-calendar bigger-110"></i>
                                                </span>
                                            </div>
                                        </div>
                                        <label class="col-sm-1 control-label" for="inuredate">生效日期</label>
                                        <div class="col-sm-5">
                                            <input class="form-control" id="inuredate" type="text" placeholder="生效日期" disabled/>
                                        </div>
                                    </div>
                                </fieldset>
                            </form>
                        </div>
                    </div>
                </div><!--常用信息end-->
                <!--不常用信息start-->
                <div id="inactiveinfo" class="tab-pane fade">
                    <div class="row">
                        <div class="col-xs-12">
                            <form class="form-horizontal" role="form" id="saveInActiveInfo" action="/set/devicereation/saveInActiveInfo">
                                <fieldset>
                                    <div class="space-10"></div>
                                    <div class="form-group controls controls-row">
                                        <label class="col-sm-1 control-label" for="name2">中文名称</label>
                                        <div class="col-sm-11">
                                            <input  type="text" class="col-sm-12" id="name2" disabled>
                                        </div>
                                        <%--<div class="col-sm-6 controls controls-row">--%>
                                        <%--<label class="control-label" for="name">试验名称</label>--%>
                                        <%--<input class="span1" type="text" placeholder="name">--%>

                                        <%--<label class="control-label pull-right inline">--%>
                                        <%--<span class="muted">是否显示加粗:</span>--%>
                                        <%--<input id="id-button-borders" checked="checked" type="checkbox" class="ace ace-switch ace-switch-3">--%>
                                        <%--<span class="lbl middle"></span>--%>
                                        <%--</label>--%>
                                        <%--</div>--%>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-1 control-label" for="principle">测定原理</label>
                                        <div class="col-sm-11">
                                            <textarea class="form-control textarea" id="principle" name="principle" placeholder="测定原理"></textarea>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-1 control-label" for="methodname">方法名称</label>
                                        <div class="col-sm-11">
                                            <input class="form-control textarea" id="methodname" name="methodname" type="text" placeholder="方法名称"/>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-1 control-label" for="workcriterion">操作规范</label>
                                        <div class="col-sm-11">
                                            <textarea class="form-control textarea" id="workcriterion" name="workcriterion" placeholder="操作规范"></textarea>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-1 control-label" for="increasedhint">升高意义</label>
                                        <div class="col-sm-11">
                                            <textarea class="form-control textarea" id="increasedhint" name="increasedhint" placeholder="升高意义"></textarea>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-1 control-label" for="decreasedhint"> 降低意义</label>
                                        <div class="col-sm-11">
                                            <textarea class="form-control textarea" id="decreasedhint" name="decreasedhint" placeholder="降低意义"></textarea>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-1 control-label" for="notes">注意事项</label>
                                        <div class="col-sm-11">
                                            <textarea class="form-control textarea" id="notes" name="notes" placeholder="注意事项"></textarea>
                                        </div>
                                    </div>
                                </fieldset>
                            </form>
                        </div>
                    </div>
                </div><!--不常用信息end-->
                <!--仪器/部门选择start-->
                <div id="relation" class="tab-pane fade">
                    <div class="row">
                        <div class="col-xs-12">
                            <h4 class="widget-title lighter">
                                <i class="ace-icon fa fa-star orange"></i>
                                仪器选择
                            </h4>
                            <div class="col-xs-12" style="margin-top: -15px" >
                                <div class="form-group">
                                    <select multiple="multiple" size="8" name="devicelist[]" id="devicelist">
                                    </select>
                                    <!--div class="hr hr-16 hr-dotted"></div-->
                                </div>
                            </div>
                            <h4 class="widget-title lighter">
                                <i class="ace-icon fa fa-star orange"></i>
                                部门选择
                            </h4>
                            <div class="col-xs-12"  style="margin-top: -15px">
                                <div class="form-group">
                                    <select multiple="multiple" size="8" name="departlist[]" id="departlist">
                                    </select>
                                </div>
                            </div>
                        </div>
                    </div>
                </div><!--仪器/部门选择end-->
                <!--参考范围start-->
                <div id="reference" class="tab-pane fade">
                    <div class="row">
                        <div class="col-xs-12">
                            <table id="tableList"></table>
                            <div id="pager"></div>
                        </div>
                    </div>
                </div><!--参考范围end-->
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    var departlist = '${departlist}';
    var devicelist =  '${devicelist}';
    var zNodes = '${treeNodes}';
    if(departlist !='') departlist = eval("("+departlist+")");
    if(devicelist !='') devicelist = eval("("+devicelist+")");
    if(zNodes !='') zNodes = eval("("+zNodes+")");

    /*******************************
     * 添加数据
     ********************************/
    function add(){
        $('#indexid').removeAttr("disabled");
        //$('#myTab a[href="#activeinfo"]').tab('show');
        $('#myTab a:first').tab('show');
        //document.getElementByIdx_x("form1").reset();
        loadDualListbox();
    }
    /*******************************
     * 保存数据
     ********************************/
    function save() {
        //获取选中数据
        var zTree = $.fn.zTree.getZTreeObj("treeList");
        var node = zTree.getSelectedNodes()[0];

        var id = node.id || '';
        //当前节点没有值或者为根节点则不保存
        if (id == '' || node.level == 0) return false;

        //当前选中Tab
        var activeTab = $('#myTab').find('li.active a').attr('href');
        var url = "";
        if (activeTab == '#relation') {
            //保存仪器部门选择信息
            var department = $('select[name="departlist[]"]').val().toString() || '';
            var instrument = $('select[name="devicelist[]"]').val().toString() || '';

            department += (department == "" ? "" : ",");
            instrument += (instrument == "" ? "" : ",");
            $.ajax({
                url: '../set/devicerelation/saveRelation',
                type: 'POST',
                dataType: "json",
                data: {id: id, department: department, instrument: instrument},
                success: function (data) {
                    if (data && data.result == 'true')
                        layer.msg("数据保存成功");
                }
            })
        }
        else if (activeTab == '#activeinfo') {
            //保存常用信息
            $.ajax({
                url: '../set/devicerelation/saveActiveInfo',
                type: 'POST',
                dataType: "json",
                data: $('#activeInfoForm').serialize() + "&id=" + id,
                success: function (data) {
                    // console.log(data);
                    if (data && data.result == 'true')
                        layer.msg("数据保存成功");
                }
            })
        }
        else if (activeTab == '#inactiveinfo') {
            //保存常用信息
            $.ajax({
                url: '../set/devicerelation/saveInActiveInfo',
                type: 'POST',
                dataType: "json",
                data: $('#saveInActiveInfo').serialize() + "&id=" + id,
                success: function (data) {
                    //console.log(data);
                    if (data && data.result == 'true')
                        layer.msg("数据保存成功");
                }
            })
        }
        else if (activeTab == '#reference') {
            //参考范围
            $("#tableList").saveRow(lastsel, false, 'clientArray');
            var obj = $("#tableList").jqGrid("getRowData");
            var datas = [];
            $(obj).each(function(){
                var obj = {};
                obj.sampletype = this.sampletype;
                obj.testid = this.testid;
                obj.deviceid = this.deviceid;
                obj.sex= this.sex;
                obj.age= this.age;
                obj.ageunit = this.ageunit,
                        obj.orderno= this.orderno;
                obj.sampletype = this.sampletype;
                obj.direct = this.direct;
                obj.reflower = this.reflower;
                obj.refhigh= this.refhigh;
                datas.push(obj);
            });
            if(datas && datas.length>0) {
                $.ajax({
                    url: '../set/devicerelation/saveReference',
                    type: 'POST',
                    dataType: "json",
                    data: {datas: arrayToJson(datas)},
                    success: function (data) {
                        if (data && data.result == 'true')
                            layer.msg("数据保存成功");
                    }
                })
            }else {
                layer.msg('请先增加数据', {icon: 2});
                return false;
            }
        }
    }

    /*******************************
     * 初始化选择列表框
     ********************************/
    function initDualListbox() {
        var demo1 = $('select[name="devicelist[]"]').bootstrapDualListbox({
            infoText: false,
            filterPlaceHolder: '过滤',
            selectorMinimalHeight: 200,
            preserveSelectionOnMove: false
        });
        var container1 = demo1.bootstrapDualListbox('getContainer');
        container1.find('.btn').addClass('btn-white btn-info btn-bold');
        var demo2 = $('select[name="departlist[]"]').bootstrapDualListbox({
            infoText: false,
            filterPlaceHolder: '过滤',
            selectorMinimalHeight: 200
        });
        var container2 = demo2.bootstrapDualListbox('getContainer');
        container2.find('.btn').addClass('btn-white btn-info btn-bold');
    }


    /*******************************
     * 加载选择列表数据
     ********************************/
    function loadDualListbox(id) {
        $.ajax({
            url: '../set/devicerelation/getDataList',
            type: 'POST',
            dataType: "json",
            data: {id: id},
            success: function (data) {
                if (data) {
                    var labdepartment = data.index.labdepartment || '';     //已选部门信息
                    var instrument = data.index.instrument || '';           //已选仪器信息
                    var references= data.references;        //参考范围

                    var selectDev = $('#devicelist');
                    selectDev.empty();
                    for (var k in devicelist) {
                        var option = document.createElement("option");
                        option.value = k;
                        option.text = devicelist[k];
                        if ((instrument.toString()).indexOf(k + ",") >= 0) {
                            option.selected = 'selected';
                        }
                        selectDev[0].options.add(option);
                    }

                    //加载部门
                    var selectDep = $('#departlist');
                    selectDep.empty();

                    for (var k in departlist) {
                        var option = document.createElement("option");
                        option.value = k;
                        option.text = departlist[k];
                        if ((labdepartment.toString()).indexOf(k + ",") >= 0) {
                            option.selected = 'selected';
                        }
                        selectDep[0].options.add(option);
                    }
                }
                $('select[name="departlist[]"]').bootstrapDualListbox("refresh");
                $('select[name="devicelist[]"]').bootstrapDualListbox("refresh");

                //加载表单数据
                setFromValue(data.index);

                //reloadGrid
                $("#tableList").jqGrid('setGridParam',{
                    datatype:'local',
                    rowNum:references.length,
                    data:references
                }).trigger('reloadGrid');//重新载入
            }

        })
    }

    /*******************************
     * ztree 设置参数
     ********************************/
    var setting = {
        data: {
            simpleData: {
                enable: true
            }
        },
        callback: {
            onClick: function (event, treeId, treeNode, clickFlag) {
                loadDualListbox(treeNode.id)
            }
        },
        view: {}
    };


    jQuery(function ($) {
        initGrid();
        $('#outdate').datetimepicker().next().on(ace.click_event, function () {
            $(this).prev().focus();
        });
        //初始化选择列表
        initDualListbox();

        //加载树形列表
        $.fn.zTree.init($("#treeList"), setting, zNodes);

        $("#search").keypress(function (e) {
            if (e.keyCode == 13) {
                search(this.value);
            }
        });
    });

    /********************************
     * 搜索树形节点
     *********************************/
    function search(k) {
        if (k == "") {
            $.fn.zTree.init($("#treeList"), setting, zNodes);
        }

        var arr = [];
        var patt = new RegExp(k);
        for (var i = 0; i < zNodes.length; i++) {
            if (patt.test(zNodes[i].name)) {
                arr.push(zNodes[i]);
            }
        }
        if (arr.length) {
            $.fn.zTree.init($("#treeList"), setting, arr);
        } else {
            layer.msg("对不起，没有找到数据");
            //$.fn.zTree.init($("#treeList"), "", zNodes);
        }
    }

    /********************************
     * 设置表单数据
     *********************************/
    function setFromValue(obj) {
        $('#indexid').val(obj.indexid);
        $('#name').val(obj.name);
        $('#name2').val(obj.name);
        $('#testclass').val(obj.testclass);
        $('#eglish').val(obj.eglish);
        $('#samplefrom').val(obj.samplefrom);
        $('#labdepartment').val(obj.labdepartment);
        $('#instrument').val(obj.instrument);
        $('#type').val(obj.type);
        $('#algorithm').val(obj.algorithm);
        $('#method').val(obj.method);
        $('#description').val(obj.description);
        $('#guide').val(obj.guide);
        $('#unit').val(obj.unit);
        //$('#needhistory').val(obj.needhistory);
        $('#inuredate').val(obj.inuredate);
        $('#tea').val(obj.tea);
        $('#ccv').val(obj.ccv);
        $('#outdate').val(obj.outdate);
        $('#defaultvalue').val(obj.defaultvalue);

        //质控需要
        $("input[type=radio]").removeAttr("checked");
        if (obj.qcneed != '') {
            $("input[type=radio][value=" + obj.qcneed + "]").prop('checked', true);
        }
        //是否历史记录
        if (obj.needhistory == '1') {
            $("#needhistory").prop('checked', true);
        } else {
            $("#needhistory").prop('checked', false);
        }

        //不常用信息
        $('#principle').val(obj.principle);
        $('#workcriterion').val(obj.workcriterion);
        $('#increasedhint').val(obj.increasedhint);
        $('#decreasedhint').val(obj.decreasedhint);
        $('#notes').val(obj.notes);
        $('#methodname').val(obj.methodname);

    }

    /*******************************
     * 加载参考范围表格
     ********************************/
    var lastsel;

    function initGrid() {
        $("#tableList").jqGrid({
            caption: "设置",
            //url: "../set/dictionary/getList",
            //postData:{deviceid:typeid},
            datatype: "json",
            colNames: ['testid', '项目名称', '标本名称', '性别', '年龄','年龄单位', '序号', '设备ID', '周期', '参考范围低值', '参考范围高值'],
            colModel: [
                {name: 'testid', index: 'testid', width: 60, hidden: true},
                {name: 'testname', index: 'testname', width: 200},
                {name: 'sampletype', index: 'sampletype', width: 100},
                {
                    name: 'sex',
                    index: 'sex',
                    width: 60,
                    editable: true,
                    formatter: "select",
                    edittype: "select",
                    editoptions: {value: "0:男;1:女;2:其他"}
                },
                {name: 'age', index: 'age', width: 60, editable: true},
                {name: 'ageunit', index: 'ageunit', width: 60, editable: true,formatter: "select", edittype: "select", editoptions: {value: "岁:岁;月:月;周:周;天:天"}},
                {name: 'orderno', index: 'orderno', width: 60, editable: true},
                {name: 'deviceid', index: 'deviceid', width: 60, editable: true},
                {name: 'direct', index: 'direct', width: 60, editable: true},
                {name: 'reflower', index: 'reflower', width: 120, editable: true},
                {name: 'refhigh', index: 'refhigh', width: 120, editable: true}
            ],
            onSelectRow: function (id) {
                if (id && id !== lastsel) {
                    $('#tableList').saveRow(lastsel, false, 'clientArray');
                    lastsel = id;
                }
                $('#tableList').editRow(id, false);
            },
            repeatitems: false,
            viewrecords: true,
            autowidth: true,
            altRows: true,
            //height: 300,
            height: "100%",
            rownumbers: true, // 显示行号
            rownumWidth: 35
        });

        //增加工具栏按钮
        var setting = $('<span class="widget-toolbar" style="right:20px;top:4px"><button type="button" id="add" class="btn btn-xs btn-success" data-toggle="button"> <i class="ace-icon fa fa-plus"></i>增加</button>| <button type="button" id="delete" class="btn btn-xs btn-success" data-toggle="button"><i class="ace-icon fa fa-times"></i>删除</button></span>');
        $("#gview_tableList").find('.ui-jqgrid-titlebar').append(setting);

        $('#tableList').jqGrid('setGridWidth', $(".rightContent").width(),false);

        //增加表格行
        $('#add').bind('click', function () {
            var ids = $('#tableList').jqGrid('getDataIDs');
            var newId = parseInt(ids[ids.length - 1] || 0) + 1;
            var zTree = $.fn.zTree.getZTreeObj("treeList");
            var node = zTree.getSelectedNodes()[0];
            var id = node.id || '';
            //当前节点没有值或者为根节点则不保存
            if (id == '' || node.level == 0) {
                layer.alert('请先选择检验项目', {icon: 2, time: 1000});
                return false;
            }
            var rowData = {
                testid:node.indexid,
                testname: node.name,
                sampletype: node.sampletype,
                sex: "0",
                age: "0",
                ageunit:'岁',
                orderno: "0",
                deviceid: "",
                direct: "0",
                reflower: "",
                refhigh: ""
            };
            $("#tableList").jqGrid('addRowData', newId, rowData);
        });
        //删除表格数据
        $('#delete').bind('click', function () {
            var id = $('#tableList').jqGrid('getGridParam', 'selrow');
            if (id == null || id == 0) {
                layer.msg('请先选择要删除的数据', {icon: 2, time: 1000});
                return false;
            }
            layer.confirm('确定删除选择数据？', {icon: 2, title: '警告'}, function (index) {
                var rowData = $('#tableList').jqGrid('getRowData',id);
                var sex= $('#'+id + "_sex").val();
                var orderno= $('#'+id + "_orderno").val();      //编辑状态数据获取
                $.post('../set/devicerelation/deleteReference',{testid:rowData.testid,sex:sex,orderno:orderno},function(data) {
                    var data = eval("("+data+")");
                    if(data && data.result=='true'){
                        layer.msg("删除成功！");
                        $("#tableList").jqGrid('delRowData',id );
                    }else{
                        layer.msg("删除失败！",{icon:2});
                    }

                });
                layer.close(index);
            });
        })
    }



    /*******************************
     * 数组转JSON
     * @param o
     * @returns {*}
     ********************************/
    function arrayToJson(o) {
        var r = [];
        if (typeof o == "string") return "\"" + o.replace(/([\'\"\\])/g, "\\$1").replace(/(\n)/g, "\\n").replace(/(\r)/g, "\\r").replace(/(\t)/g, "\\t") + "\"";
        if (typeof o == "object") {
            if (!o.sort) {
                for (var i in o)
                    r.push(i + ":" + arrayToJson(o[i]));
                if (!!document.all && !/^\n?function\s*toString\(\)\s*\{\n?\s*\[native code\]\n?\s*\}\n?\s*$/.test(o.toString)) {
                    r.push("toString:" + o.toString.toString());
                }
                r = "{" + r.join() + "}";
            } else {
                for (var i = 0; i < o.length; i++) {
                    r.push(arrayToJson(o[i]));
                }
                r = "[" + r.join() + "]";
            }
            return r;
        }
        return o.toString();
    }
</script>