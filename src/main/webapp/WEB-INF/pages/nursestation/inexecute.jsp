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
    .ui-jqgrid .ui-jqgrid-htable th span.ui-jqgrid-resize{
        height: 30px !important;
    }
    .ui-jqgrid .ui-jqgrid-htable th div {
        padding-top: 5px !important;
    }
    .ui-jqgrid .ui-jqgrid-view input, .ui-jqgrid .ui-jqgrid-view select, .ui-jqgrid .ui-jqgrid-view textarea, .ui-jqgrid .ui-jqgrid-view button{
        margin: 0 0;
    }
    .ui-jqgrid tr.ui-row-ltr td, .ui-jqgrid tr.ui-row-rtl td{
        padding: 2px 4px;
    }
    .ui-jqgrid-htable th div .cbox {
        margin-left: 3px !important;
    }

    /*定义滚动条高宽及背景 高宽分别对应横竖滚动条的尺寸*/
    ::-webkit-scrollbar
    {
        width: 12px;
        height: 12px;
        background-color: #F5F5F5;
    }

    /*定义滚动条轨道 内阴影+圆角*/
    ::-webkit-scrollbar-track
    {
        -webkit-box-shadow: inset 0 0 6px rgba(0,0,0,0.3);
        border-radius: 5px;
        background-color: #F5F5F5;
    }

    /*定义滑块 内阴影+圆角*/
    ::-webkit-scrollbar-thumb
    {
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
            <button type="button" class="btn btn-sm  btn-success" title="补打" onclick="editSection()">
                <i class="ace-icon fa fa-pencil-square bigger-110"></i>
                补打
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
                    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="alert alert-info" id="patientInfo">
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
                <div class="widget-main">
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
            initHieght:function () {
                var clientHeight = $(window).innerHeight();
                var height = clientHeight - $('#head').height() - $('#toolbar').height() - $('.footer-content').height() - 30;
                $('.laftnav').height(height);
                $('#leftTree').height(height-40);
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
                        onClick:private.clickTree
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
                        if (nodes.length>0)
                        {
                            zTree.selectNode(nodes[0]);
                            zTree.setting.callback.onClick(null, zTree.setting.treeId, nodes[0]);//调用事件
                        }
                    },
                    error: function (msg) {
                        alert("失败");
                    }
                });
            },
            clickTree:function (event, treeId, treeNode, clickFlag) {
                var data ={};
                if(treeNode.level==0){
                    data.ward=treeNode.id;
                }else{
                    data.ward=treeNode.ward;
                    data.bedNo=treeNode.bedNo;
                    data.patientId=treeNode.id
                }
                $.ajax({
                    url: '../nursestation/inexecute/getRequestList',
                    data:data,
                    type: 'POST',
                    dataType: "json",
                    ContentType: "application/json; charset=utf-8",
                    success: function (data) {
                        var data = eval("("+data+")");
                        var beollected = data.beollected;
                        console.log(beollected)
                        $("#tableList" ).clearGridData();   //清空原grid数据
                        jQuery("#tableList").jqGrid('setGridParam',{
                            datatype : 'local',
                            rowNum:beollected.length,
                            data:beollected
                        }).trigger('reloadGrid');//重新载入
                        //全选
                        $("#tableList").trigger("jqGridSelectAll",true);
                        var patientinfo="";
                        private.addPatientInfo(treeNode.level,beollected[0]);
                    },
                    error: function (msg) {
                        alert("获取采集信息失败");
                    }
                });
            },
            initGrid:function () {
                $("#tableList").jqGrid({
                    datatype: "json",
                    //caption:"未采集标本",
                    colNames: ['申请ID','申请明细ID','patientid','ylxh','labdepartment','examitem','qbgsj','zxbz','床号', '姓名','性别', '年龄','项目代码','项目名称','标本种类','金额','申请时间','是否急诊'],
                    colModel: [
                        { name: 'requestId', index: 'requestId', width: 40,hidden:true },
                        { name: 'laborderorg', index: 'laborderorg', width: 40,hidden:true },
                        { name: 'patientid', index: 'patientid', width: 40,hidden:true },
                        { name: 'ylxh', index: 'ylxh', width: 40,hidden:true },
                        { name: 'labdepartment', index: 'labdepartment', width: 40,hidden:true },
                        { name: 'examitem', index: 'examitem', width: 40,hidden:true },
                        { name: 'qbgsj', index: 'qbgsj', width: 40,hidden:true },
                        { name: 'zxbz', index: 'zxbz', width: 40,hidden:true },
                        { name: 'bed', index: 'bed', width: 40},
                        { name: 'patientname', index: 'patientname', width: 60},
                        { name: 'sex', index: 'sex', width: 40,formatter:'select',editoptions : {value : "1:男;0:女"}},
                        { name: 'age', index: 'sampletype', width: 40},
                        { name: 'testId', index: 'sampletype', width: 60,hidden:true},
                        { name: 'examitem', index: 'examitem', width: 150},
                        { name: 'sampletype', index: 'sampletype', width: 60},
                        { name: 'price', index: 'price', width: 60},
                        { name: 'requesttime', index: 'requesttime', width: 100},
                        { name: 'requestmode', index: 'requestmode', width: 60,formatter:'select',editoptions : {value : "1:是;0:否"}}
                    ],
                    onSelectRow: function(id) {
                        if(id && id!==lastsel){
                            jQuery('#tableList').saveRow(lastsel, false, 'clientArray');
                            lastsel=id;
                        }
                        jQuery('#tableList').editRow(id, false);
                    },
                    gridComplete: function () {
                        var ids = jQuery("#tableList").jqGrid('getDataIDs');
                        for (var i = 0; i < ids.length; i++) {
                            var rowData = $("#tableList").getRowData(ids[i]);
                            if(rowData.requestmode==1){
                                $('#'+ids[i]).find("td").css("color","red");
                            }
                        }
                    },
                    multiselect : true,
                    //multikey : "ctrlKey",
                    repeatitems:false,
                    viewrecords: true,
                    autowidth:true,
                    altRows:true,
                    height:200,
                    rownumbers: true, // 显示行号
                    rownumWidth: 35
                });
                //初始化已采集标本
               // var height=$('.laftnav').height()-$('#widget-box-2').height()-116;
                $("#tableList1").jqGrid({
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
                            jQuery('#tableList1').saveRow(lastsel, false, 'clientArray');
                            lastsel=id;
                        }
                        jQuery('#tableList1').editRow(id, false);
                    },
                    multiselect : true,
                    multikey : "ctrlKey",
                    repeatitems:false,
                    viewrecords: true,
                    autowidth:true,
                    altRows:true,
                    height:$('.laftnav').height()-$('#widget-box-2').height()-118,
                    rownumbers: true, // 显示行号
                    rownumWidth: 35
                });
            },
            printInfo:function () {
                //打印标本条码号
                var ids = $("#tableList").jqGrid('getGridParam','selarrrow');
                var saveDatas =[];
                $.each(ids, function(key, val) {
                    var rowData = $("#tableList").jqGrid("getRowData", ids[key]);
                    saveDatas.push(rowData)
                });
                $.ajax({
                    type:"POST",
                    url:"../nursestation/inexecute/printRequestList",
                    dataType:"json",
                    contentType:"application/json",
                    data:JSON.stringify(saveDatas),
                    success:function(data){
                        //var printDatas = data.printOrders
                        var noPrintDatas = data.noPrintOrders;
                        var printDatas=[{testinfo:'检测项目1',barcode:'12345678901234',patientinfo:'张三丰',sampletype:'全血',datetime:'2016-08-27 18:30'},{testname:'检测项目2',barcode:'12345678901234',patientinfo:'张三丰2',sampletype:'全血2',datetime:'2016-08-27 18:30'}];
                        for(i=0;i<printDatas.length;i++){
                            //startPrint(printDatas[i]);
                        }
                        for(i=0;i<noPrintDatas.length;i++){

                        }
                    }
                });
                console.log(saveDatas);
            },
            addPatientInfo:function (level,data) {
                if(level==0){
                    $('#widget-box-1').hide();
                    var height=$('.laftnav').height()-$('#widget-box-2').height()-118;
                   $("#tableList1").setGridHeight(height);
                }else {
                    var table = $('#patientInfo');
                    var html = '<tr><td>姓&#12288;名：'+data.patientname+'</td><td>性&#12288;&#12288;别：'+((data.sex==1)?'男':'女')+'</td><td>年&#12288;&#12288;龄：'+data.age+'</td><td>病&#12288;&#12288;区：'+data.hossectionName+'</td><td>床&#12288;&#12288;号：'+data.bed+'</td></tr>';
                    html += '<tr><td>病员号：'+data.blh+'</td><td>出生日期：'+data.birthday+'</td><td>送检医生：</td><td>送检单位：'+data.labdepartment+'</td><td>接收日期：'+data.requesttime+'</td></tr>';
                    table.html("");
                    table.append(html);
                    var height=$('.laftnav').height()-$('#widget-box-2').height()-$('#widget-box-1').height()-122;
                   $("#tableList1").setGridHeight(height);
                    $('#widget-box-1').show();
                }
            },
            getSampleList:function(bedno,patientId){
            }
        }
        var public = {
            init: function () {
                private.initHieght();
                private.initTree();
                private.initGrid();
            },
            printInfo:function () {
                private.printInfo();
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

    function Preview() {//打印预览
        LODOP = getLodop();
        LODOP.PRINT_INITA(-1, -1, 824, 1129, "运单套打");

        CreateLicenseData();
        LODOP.SET_PREVIEW_WINDOW(2, 0, 0, 800, 600, "");
        LODOP.SET_PRINT_PAGESIZE(1, 0, 0, "A4");
        LODOP.PREVIEW();
    };
    function Setup() {//打印维护
        LODOP = getLodop();
        LODOP.PRINT_INITA(-1, -1, 824, 1129, "运单套打");

        CreateLicenseData();
        LODOP.PRINT_SETUP();
    };
    function Design() {//打印设计
        LODOP = getLodop();
        LODOP.PRINT_INITA(-1, -1, 824, 1129, "运单套打");
        CreateLicenseData();
        LODOP.PRINT_DESIGN();
    }
    function CreateLicenseData(data) {

    }
    function startPrint(data) {
        LODOP = getLodop();
        LODOP.SET_PRINT_PAGESIZE(0,500,350,"A4");
        LODOP.ADD_PRINT_TEXTA("testinfo","3mm","4mm",175,25,data.testinfo);
        LODOP.SET_PRINT_STYLEA(0,"FontSize",10);
        LODOP.ADD_PRINT_BARCODEA("barcode","12.00mm","4mm",180,35,"128A",data.barcode);
        LODOP.SET_PRINT_STYLEA(0,"ShowBarText",0);
        LODOP.ADD_PRINT_TEXTA("code","21.50mm","4mm",125,25,data.barcode);
        LODOP.SET_PRINT_STYLEA(0,"FontSize",10);
        LODOP.ADD_PRINT_TEXTA("patientinfo",26,"4mm",175,20,data.patientinfo);
        LODOP.SET_PRINT_STYLEA(0,"FontSize",12);
        LODOP.ADD_PRINT_TEXTA("sampletype","21.50mm","30mm",55,25,data.sampletype);
        LODOP.SET_PRINT_STYLEA(0,"FontSize",10);
        LODOP.SET_PRINT_STYLEA(0,"Alignment",2);
        LODOP.ADD_PRINT_TEXTA("datetime","26.00mm","4mm",174,25,data.datetime);
        LODOP.SET_PRINT_STYLEA(0,"FontSize",10);
        //开始打印
        LODOP.PRINT();
    }
</script>

