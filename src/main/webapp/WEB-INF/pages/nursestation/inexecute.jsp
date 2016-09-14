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
    <script src="<c:url value="/scripts/LodopFuncs.js"/>"></script>
    <script src="<c:url value="/scripts/layer/layer.js"/>"></script>
    <link rel="stylesheet" type="text/css" href="<c:url value='/styles/ui.jqgrid.css'/>"/>
    <script src="<c:url value="/scripts/jquery.ztree.all-3.5.js"/>"></script>
    <link rel="stylesheet" type="text/css" href="<c:url value='/styles/ztree/zTreeStyle_flat.css'/>"/>
    <title>标本采集</title>
    <!--打印控件-->
    <%--<object id="LODOP_OB" classid="clsid:2105C259-1E0C-4534-8141-A753534CB4CA" width=0 height=0>--%>
    <%--<embed id="LODOP_EM" type="application/x-print-lodop" width=0 height=0 pluginspage="install_lodop32.exe"></embed>--%>
    <%--</object>--%>
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

    .ui-jqgrid .ui-jqgrid-htable th span.ui-jqgrid-resize {
        height: 30px !important;
    }

    .ui-jqgrid .ui-jqgrid-htable th div {
        padding-top: 5px !important;
    }

    .ui-jqgrid .ui-jqgrid-view input, .ui-jqgrid .ui-jqgrid-view select, .ui-jqgrid .ui-jqgrid-view textarea, .ui-jqgrid .ui-jqgrid-view button {
        margin: 0 0;
    }

    .ui-jqgrid tr.ui-row-ltr td, .ui-jqgrid tr.ui-row-rtl td {
        padding: 2px 4px;
    }

    .ui-jqgrid-htable th div .cbox {
        margin-left: 3px !important;
    }

    /*定义滚动条高宽及背景 高宽分别对应横竖滚动条的尺寸*/
    ::-webkit-scrollbar {
        width: 12px;
        height: 12px;
        background-color: #F5F5F5;
    }

    /*定义滚动条轨道 内阴影+圆角*/
    ::-webkit-scrollbar-track {
        -webkit-box-shadow: inset 0 0 6px rgba(0, 0, 0, 0.3);
        border-radius: 5px;
        background-color: #F5F5F5;
    }

    /*定义滑块 内阴影+圆角*/
    ::-webkit-scrollbar-thumb {
        border-radius: 5px;
        -webkit-box-shadow: inset 0 0 6px rgba(151, 151, 151, 0.3);
        background-color: #9f9f9f;
    }

    table.alert-info tr td {
        padding: 0px 10px;
    }

    .widget-main {
        padding: 5px;
    }

    .widget-header-small {
        min-height: 21px;
        /* padding-left: 10px; */
    }
</style>
<div class="row">
    <div class="col-xs-3 treelist">
        <div class="laftnav">
            <div class="lazy_header">
                <span class="lazy-list-title">
                <i class="fa fa-bars"></i>
                <span class="tip" style="cursor:pointer;">病人列表</span>
                </span>
            </div>
            <div style="overflow: auto;" id="leftTree">
                <%--<ul class="nav nav-pills nav-stacked" id="tree">--%>
                <ul id="tree" class="ztree"></ul>
                </ul>
            </div>
        </div>
    </div>
    <div class="col-xs-9">
        <div style="padding-top: 5px;">
            <button type="button" class="btn btn-sm btn-primary " title="打印" onclick="TSLAB.Custom.printInfo()">
                <i class="ace-icon fa fa-fire bigger-110"></i>
                打印
            </button>
            <button type="button" class="btn btn-sm  btn-success" title="补打" onclick="TSLAB.Custom.printOldInfo()">
                <i class="ace-icon fa fa-pencil-square bigger-110"></i>
                补打
            </button>
            <button type="button" class="btn btn-sm  btn-success" title="打印设计" onclick="TSLAB.Custom.printSet()">
                <i class="ace-icon fa fa-pencil-square bigger-110"></i>
                打印设计
            </button>
        </div>
        <div class="widget-box widget-color-green" id="widget-box-1" style="display: none">
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
                    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="alert alert-info"
                           id="patientInfo">
                    </table>
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
                <div class="widget-main" id="colletionList">
                    <table id="tableList1"></table>
                    <div id="pager1"></div>
                </div>
            </div>
        </div>
    </div>
</div>
<div style="clear: both"></div>
<script type="text/javascript">
    var TSLAB = TSLAB || {};
    TSLAB.Custom = (function () {
        var private = {
            initHieght: function () {
                var clientHeight = $(window).innerHeight();
                var height = clientHeight - $('#head').height() - $('#toolbar').height() - $('.footer-content').height() - 35;
                $('.laftnav').height(height);
                $('#leftTree').height(height - 40);

            },
            initTree: function () {
                var setting = {
                    view: {
                        selectedMulti: false
                    },
                    data: {
                        simpleData: {
                            enable: true
                        }
                    },
                    callback: {
                        onClick: private.clickTree
                    }
                };
                $.ajax({
                    url: '../nursestation/inexecute/getList',
                    data: {ward: "1025"},
                    type: 'POST',
                    dataType: "json",
                    ContentType: "application/json; charset=utf-8",
                    success: function (data) {
                        $.fn.zTree.init($("#tree"), setting, data);
                        var zTree = $.fn.zTree.getZTreeObj("tree");//获取ztree对象
                        var nodes = zTree.getNodes();
                        if (nodes.length > 0) {
                            zTree.selectNode(nodes[0]);
                            zTree.setting.callback.onClick(null, zTree.setting.treeId, nodes[0]);//调用事件
                        }
                    },
                    error: function (msg) {
                        alert("失败");
                    }
                });
            },
            clickTree: function (event, treeId, treeNode, clickFlag) {
                var data = {};
                if (treeNode.level == 0) {
                    data.ward = treeNode.id;
                } else {
                    data.ward = treeNode.ward;
                    data.bedNo = treeNode.bedNo;
                    data.patientId = treeNode.id
                }
                var index;
                $.ajax({
                    url: '../nursestation/inexecute/getRequestList',
                    data: data,
                    type: 'POST',
                    dataType: "json",
                    ContentType: "application/json; charset=utf-8",
                    beforeSend: function () {
                        index = layer.load(1, {offset: ['30%']});
                        //index =layer.msg('加载中...', {icon: 16,scrollbar: false, time:100000}) ; ;
                    },
                    success: function (data) {
                        var beollected = data.beollected;
                        var spidered = data.spidered;

                        //加载未采集标本
                        $("#tableList").clearGridData();   //清空原grid数据
                        jQuery("#tableList").jqGrid('setGridParam', {
                            datatype: 'local',
                            rowNum: beollected.length,
                            data: beollected
                        }).trigger('reloadGrid');//重新载入

                        //加载已采集标本
                        jQuery("#tableList1").jqGrid('setGridParam', {
                            datatype: 'local',
                            rowNum: spidered.length,
                            data: spidered
                        }).trigger('reloadGrid');//重新载入

                        //全选
                        $("#tableList").trigger("jqGridSelectAll", true);
                        //$("#tableList1").trigger("jqGridSelectAll",true);

                        //加载病人信息
                        private.addPatientInfo(treeNode.level, treeNode);

                        layer.close(index);
                    },
                    error: function (msg) {
                        layer.close(index);
                        layer.msg('获取采集信息失败！');
                    }
                });
                //layer.closeAll('loading');
            },
            initGrid: function () {
                $("#tableList").jqGrid({
                    datatype: "json",
                    //caption:"未采集标本",
                    colNames: ['申请ID', '申请明细ID', '床号', '姓名', '性别', '年龄', '单位',
                        '项目代码', '项目名称', '标本种类', '金额', '申请时间',
                        '申请医生', '是否急诊', 'patientid', 'ylxh',
                        'sampleno', 'hossection', 'hossectionName', 'birthday', 'stayhospitalmode', 'blh',
                        'diagnostic', 'labdepartment', 'requestNum', 'count', 'wardid', 'wardname', 'qbgsj'],
                    colModel: [{name: 'requestId', index: 'requestId', width: 40, hidden: true},
                        {name: 'laborderorg', index: 'laborderorg', width: 40, hidden: true},
                        {name: 'bed', index: 'bed', width: 30},
                        {name: 'patientname', index: 'patientname', width: 60},
                        {name: 'sex', index: 'sex', width: 30, formatter: 'select', editoptions: {value: "1:男;0:女"}},
                        {name: 'age', index: 'age', width: 30},
                        {name: 'ageUnit', index: 'ageUnit', width: 30},
                        {name: 'testId', index: 'testId', width: 60, hidden: true},
                        {name: 'examitem', index: 'examitem', width: 150},
                        {name: 'sampleTypeName', index: 'sampleTypeName', width: 60},
                        {name: 'price', index: 'price', width: 60},
                        {name: 'requesttime', index: 'requesttime', width: 100},
                        {name: 'requester', index: 'requester', width: 60},
                        {
                            name: 'requestmode',
                            index: 'requestmode',
                            width: 60,
                            formatter: 'select',
                            editoptions: {value: "1:是;0:否"}
                        },
                        {name: 'patientid', index: 'patientid', width: 40, hidden: true},
                        {name: 'ylxh', index: 'ylxh', width: 40, hidden: true},
                        {name: 'sampleno', index: 'sampleno', width: 40, hidden: true},
                        {name: 'hossection', index: 'hossection', width: 40, hidden: true},
                        {name: 'hossectionName', index: 'hossectionName', width: 40, hidden: true},
                        {name: 'birthday', index: 'birthday', width: 40, hidden: true},
                        {name: 'stayhospitalmode', index: 'stayhospitalmode', width: 40, hidden: true},
                        {name: 'blh', index: 'blh', width: 40, hidden: true},
                        {name: 'diagnostic', index: 'diagnostic', width: 40, hidden: true},
                        {name: 'labdepartment', index: 'labdepartment', width: 40, hidden: true},
                        {name: 'requestNum', index: 'requestNum', width: 40, hidden: true},
                        {name: 'count', index: 'count', width: 40, hidden: true},
                        {name: 'wardId', index: 'wardId', width: 40, hidden: true},
                        {name: 'wardName', index: 'wardName', width: 40, hidden: true},
                        {name: 'qbgsj', index: 'qbgsj', width: 40, hidden: true}],
                    onSelectRow: function (id) {
                        if (id && id !== lastsel) {
                            jQuery('#tableList').saveRow(lastsel, false, 'clientArray');
                            lastsel = id;
                        }
                        jQuery('#tableList').editRow(id, false);
                    },
                    gridComplete: function () {
                        var ids = jQuery("#tableList").jqGrid('getDataIDs');
                        for (var i = 0; i < ids.length; i++) {
                            var rowData = $("#tableList").getRowData(ids[i]);
                            if (rowData.requestmode == 1) {
                                $('#' + ids[i]).find("td").css("color", "red");
                            }
                        }
                    },
                    multiselect: true,
                    //multikey : "ctrlKey",
                    repeatitems: false,
                    viewrecords: true,
                    autowidth: true,
                    altRows: true,
                    height: 200,
                    gridview: true,
                    rownumbers: true, // 显示行号
                    rownumWidth: 35
                });
                //初始化已采集标本
                // var height=$('.laftnav').height()-$('#widget-box-2').height()-116;
                $("#tableList1").jqGrid({
                    datatype: "json",
                    //caption:"未采集标本",
                    colNames: ['laborder', 'requestId', 'laborderOrg', 'patientId', '病区', '床号', '病历号', '姓名', '性别', '年龄', '单位',
                        '项目名称', '标本类型', '样本条码', '申请科室', '申请时间', '诊断', '患者类型',
                        '打印时间', '急诊'],
                    colModel: [{name: 'requestId', index: 'requestId', width: 40, hidden: true},
                        {name: 'laborder', index: 'laborder', width: 40, hidden: true,key:true},
                        {name: 'laborderOrg', index: 'laborderOrg', width: 40, hidden: true},
                        {name: 'patientId', index: 'patientId', width: 40, hidden: true},
                        {name: 'ward', index: 'ward', width: 100},
                        {name: 'bedNo', index: 'bedNo', width: 60},
                        {name: 'patientCode', index: 'patientCode', width: 100},
                        {name: 'patientName', index: 'patientName', width: 100},
                        {name: 'sex', index: 'sex', width: 40},
                        {name: 'age', index: 'age', width: 40},
                        {name: 'ageUnit', index: 'ageUnit', width: 40},
                        {name: 'examitem', index: 'examitem', width: 150},
                        {name: 'sampleType', index: 'sampleType', width: 60},
                        {name: 'barcode', index: 'barcode', width: 100},
                        {name: 'hossection', index: 'hossection', width: 100},
                        {name: 'requestTime', index: 'requestTime', width: 60},
                        {name: 'diagnose', index: 'diagnose', width: 100},
                        {name: 'patientType', index: 'patientType', width: 60},
                        {name: 'printTime', index: 'printTime', width: 60},
                        {
                            name: 'requestMode',
                            index: 'requestMode',
                            width: 60,
                            formatter: 'select',
                            editoptions: {value: "1:是;0:否"}
                        }
                    ],
                    onSelectRow: function (id) {
                        if (id && id !== lastsel) {
                            jQuery('#tableList1').saveRow(lastsel, false, 'clientArray');
                            lastsel = id;
                        }
                        jQuery('#tableList').editRow(id, false);
                    },
                    gridComplete: function () {
                        var ids = jQuery("#tableList1").jqGrid('getDataIDs');
                        for (var i = 0; i < ids.length; i++) {
                            var rowData = $("#tableList1").getRowData(ids[i]);
                            if (rowData.requestMode == 1) {
                                $('#tableList1 #' + ids[i]).find("td").css("color", "red");
                            }
                        }
                    },
                    multiselect: true,
                    //multikey : "ctrlKey",
                    repeatitems: false,
                    viewrecords: true,
                    //autowidth:true,
                    width: $('#widget-box-3').width() - 10,
                    altRows: true,
                    shrinkToFit: false,
                    height: $('.laftnav').height() - $('#widget-box-2').height() - $('#widget-box-1').height() - 74,
                    rownumbers: true, // 显示行号
                    rownumWidth: 35
                });
            },
            printInfo: function () {
                //打印标本条码号
                var ids = $("#tableList").jqGrid('getGridParam', 'selarrrow');
                var saveDatas = [];
                $.each(ids, function (key, val) {
                    var rowData = $("#tableList").jqGrid("getRowData", ids[key]);
                    saveDatas.push(rowData)
                });
                $.ajax({
                    type: "POST",
                    async: false,
                    url: "../nursestation/inexecute/printRequestList",
                    dataType: "json",
                    contentType: "application/json",
                    data: JSON.stringify(saveDatas),
                    success: function (data) {
                        var printDatas = data.printOrders
                        var noPrintDatas = data.noPrintOrders;
                        for (i = 0; i < printDatas.length; i++) {
                            startPrint(printDatas[i]);
                        }
                        for (i = 0; i < noPrintDatas.length; i++) {

                        }
                    }

                });
                //刷新当前节点数据
                var zTree = $.fn.zTree.getZTreeObj("tree");
                var nodes = zTree.getSelectedNodes();
                if (nodes.length > 0) {
                    zTree.selectNode(nodes[0]);
                    zTree.setting.callback.onClick(null, zTree.setting.treeId, nodes[0]);//调用事件
                }
            },
            printOldInfo: function () {
                //补打标本条码号
                var ids = $("#tableList1").jqGrid('getGridParam', 'selarrrow');
                var saveDatas = [];
                $.each(ids, function (key, val) {
                    var rowData = $("#tableList1").jqGrid("getRowData", ids[key]);
                    saveDatas.push(rowData.laborder)
                });

                $.ajax({
                    type: "POST",
                    async: false,
                    url: "../nursestation/inexecute/printOldLaborderList",
                    dataType: "json",
                    contentType: "application/json",
                    data: JSON.stringify(saveDatas),
                    success: function (data) {
                        for (i = 0; i < data.length; i++) {
                            startPrint(data[i]);
                        }
                    }
                });
            },
            addPatientInfo: function (level, data) {
                if (!data) return;
                if (level == 0) {
                    $('#widget-box-1').hide();
                    var height = $('.laftnav').height() - $('#widget-box-2').height() - 115;
                    $("#tableList1").setGridHeight(height);
                } else {
                    var table = $('#patientInfo');
                    var html = '<tr><td>姓&#12288;名：' + data.patientName + '</td><td>性&#12288;&#12288;别：' + ((data.sex == 1) ? '男' : '女') + '</td><td>年&#12288;&#12288;龄：' + data.age + data.ageUnit + '</td><td>病&#12288;&#12288;区：' + data.wardName + '</td><td>床&#12288;&#12288;号：' + data.bedNo + '</td></tr>';
                    html += '<tr><td>病员号：' + data.patientCode + '</td><td>出生日期：' + data.birthday + '</td><td>送检医生：' + data.requester + '</td><td>送检单位：' + data.hossectionName + '</td><td>接收日期：' + data.requestTime + '</td></tr>';
                    table.html("");
                    table.append(html);
                    var height = $('.laftnav').height() - $('#widget-box-2').height() - $('#widget-box-1').height() - 120;
                    $("#tableList1").setGridHeight(height);
                    $('#widget-box-1').show();
                }
            },
            getSampleList: function (bedno, patientId) {
            },
            printSet: function () {
                var data = {
                    "age": "55",
                    "barcode": "A12000000098",
                    "bedNo": "04",
                    "hossection": "测试科室",
                    "patientCode": "00000496",
                    "patientName": "电子病历测试",
                    "requestTime": "2016-08-31 01:40:47",
                    "requestMode": "1",
                    "sampleNo": "",
                    "sampleQuantity": "2mL",
                    "sampleType": "",
                    "sex": "男",
                    "examitem": "抗精子抗体测定(IgM)",
                    "testTube": "黄色管",
                    "ward": "测试病区",
                    "wardId": "1025"
                }
                CreateDataBill(data)
                LODOP.PRINT_DESIGN()
            },
            printReport:function(){
                $.get("/print/ajax/printReport",{sampleno:'20160530URF001', haslast:'0', type:''}, function(data){
                    console.log(data)
                    Preview(data);
                })
            }
        }
        var public = {
            init: function () {
                private.initHieght();
                private.initTree();
                private.initGrid();
            },
            printInfo: function () {
                private.printInfo();
            },
            printOldInfo: function () {
                private.printOldInfo();
            },
            printSet: function () {
               // private.printSet();
                private.printReport();
            }

        }
        return public;
    })()

    $(function () {
        TSLAB.Custom.init();
    })

</script>

<script>
    var LODOP; //声明为全局变量

    function Preview(strHtml) {//打印预览
        LODOP = getLodop();
        //CreateDataBill(data)
        LODOP=getLodop();
        LODOP.PRINT_INIT("打印报告单");
        LODOP.ADD_PRINT_HTM("0",0,"RightMargin:0cm","BottomMargin:0mm",strHtml);
        //LODOP.ADD_PRINT_HTM(0,0,"100%","100%",strHtml);
        LODOP.PREVIEW();
    }

    function Setup() {//打印维护
        LODOP = getLodop();
        LODOP.PRINT_SETUP();
    }

    function CreateDataBill(data) {

        if (data && data != null) {
            var patientInfo = data.bedNo + "  " + data.patientName + " " + data.testTube + data.sampleQuantity;
            var patientInfo1 = data.patientCode + "  " + data.hossection;
            LODOP = getLodop();
            LODOP.PRINT_INIT("");
            LODOP.SET_PRINT_PAGESIZE(0, 500, 350, "A4");
            //LODOP.ADD_PRINT_TEXTA("patientinfo", "2.99mm", "2.95mm", 180, 25, patientInfo);
            //LODOP.ADD_PRINT_BARCODEA("barcode", "10.42mm", "2.94mm", "42.6mm", 35, "128B", data.barcode);
            LODOP.ADD_PRINT_BARCODEA("barcode","10.42mm","2.94mm","34.66mm",35,"128Auto",data.barcode);
            LODOP.SET_PRINT_STYLEA(0, "ShowBarText", 0);
            LODOP.SET_PRINT_STYLEA(0, "Horient", 2);
            LODOP.ADD_PRINT_TEXTA("code", "19.39mm", "2.95mm", 161, 25, "*" + data.barcode + "*");
            LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
            LODOP.SET_PRINT_STYLEA(0, "Bold", 1);
            if(data.requestMode =='1'){
                LODOP.ADD_PRINT_TEXTA("patientinfo","2.99mm","8.23mm",169,25,patientInfo);
                LODOP.ADD_PRINT_ELLIPSE(8,12,14,15,0,1);
                LODOP.ADD_PRINT_TEXT(9,12,18,16,"急");
                LODOP.SET_PRINT_STYLEA(0,"FontSize",10);
                LODOP.SET_PRINT_STYLEA(0,"Bold",1);
            }else{
                LODOP.ADD_PRINT_TEXTA("patientinfo", "2.99mm", "2.95mm", 180, 25, patientInfo);
            }
            LODOP.ADD_PRINT_TEXTA("patientinfo1", 23, "2.95mm", 180, 20, patientInfo1);
            LODOP.ADD_PRINT_TEXTA("testinfo", "23.36mm", "2.95mm", 180, 20, data.examitem);
            LODOP.ADD_PRINT_TEXTA("datetime", "31.56mm", "2.95mm", 180, 25, "采集时间 " + data.requestTime);


        }
    }
    function startPrint(data) {
        CreateDataBill(data);
        //开始打印
        LODOP.PRINT();
    }
</script>

