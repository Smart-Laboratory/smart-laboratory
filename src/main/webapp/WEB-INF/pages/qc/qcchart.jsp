<%--
  Created by IntelliJ IDEA.
  User: LZH
  Date: 2016/10/10
  Time: 9:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp" %>
<head>
    <title>质控图</title>
    <link rel="stylesheet" href="<c:url value="/styles/font-awesome.css"/>" />
    <link rel="stylesheet" href="/styles/ace.min.css"  />
    <link rel="stylesheet" type="text/css"  href="<c:url value='/styles/ui.jqgrid.css'/>" />
    <script type="text/javascript" src="<c:url value="/scripts/jquery-1.8.3.min.js"/>"></script>
    <script type="text/javascript" src="../scripts/jquery.jqGrid.js"></script>
    <script type="text/javascript" src="../scripts/i18n/grid.locale-cn.js"></script>
    <script type="text/javascript" src="<c:url value="/scripts/layer/layer.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/layer/extend/layer.ext.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/laydate/laydate.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/highcharts.js"/>"></script>
    <link rel="stylesheet" href="<c:url value="/styles/ztree/zTreeStyle.css"/>"/>
    <script src="<c:url value="/scripts/jquery.ztree.all-3.5.js"/>"></script>
</head>
<style>
    .form-horizontal .form-group{
        margin : 2px 5px;
    }
    .ztree{
        border: 1px solid #D9D9D9;
        border-top: none;
        height: 100%;
    }
    /*{
        font-family:"Microsoft Yahei","Helvetica Neue","Helvetica,tahoma,arial","Verdana","sans-serif","\5B8B\4F53";
        font-size:15px;
    }*/
</style>
<div class="row" id="mainContent">
    <div class="space-2"></div>
    <div class="col-xs-2" id="leftContent" >
        <div id="qcbatchinfo" class="widget-box widget-color-green ui-sortable-handle">
            <div class="widget-header">
                <span class="widget-title">质控图设置</span>
                <div class="widget-toolbar">
                    <a href="#" data-action="collapse">
                        <i class="ace-icon fa fa-chevron-up"></i>
                    </a>
                </div>
            </div>

            <div class="widget-body" style="display: block;">
                <div class="widget-main padding-4 scrollable ace-scroll" style="position: relative;">
                    <div class="scroll-content">
                        <div class="content">
                            <form   class="form-horizontal"  method="post" >
                                <div class="form-group">
                                    <div class="space-2"></div>
                                    <label class="col-xs-4 control-label no-padding-right" for="deviceid">仪器 </label>
                                    <div class="col-xs-8">
                                        <select id="deviceid" name="deviceid" class="col-xs-12">
                                            <c:forEach var="device" items="${deviceList}">
                                                <option value="${device.id}">${device.id} | ${device.name}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-xs-4 control-label no-padding-right" for="qctime">统计月份 </label>
                                    <div class="col-xs-8">
                                        <input type="text" id="qctime" name="qctime" placeholder="入库时间" value="" class="col-xs-12 text"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-xs-4 control-label no-padding-right" for="qcmode">质控模式 </label>
                                    <div class="col-xs-8">
                                        <select id="qcmode" name="qcmode" class="col-xs-12">
                                            <option value="1">1</option>
                                            <option value="2">2</option>
                                            <option value="3">3</option>
                                            <option value="4">4</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-xs-3 control-label no-padding-right" for="qcmode"> </label>
                                    <div class="col-xs-9 checkbox">
                                        <label >
                                            <input type="checkbox" id="allCheck" name="allCheck"  > 显示所有未审核数据
                                        </label>

                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="tree ">
            <ul id="treeList" class="ztree"></ul>
        </div>
    </div>


    <div class="col-xs-9" id="rightContent" style="margin-top: 3px;">
        <div id="qcbatchList">
            <table id="qcList"></table>
            <div id="pager"></div>
        </div>
        <div id="qcchartdiv">
            <p class="alert alert-danger" id="chartAlert" style="display:none">该月没有质控数据</p>
            <div id="qcChartPanel" ></div>
            <table id="chartTongji" class="table" >
                <tbody>
                <tr><th><fmt:message key='tongji.min' /></th><td><span id="tongji_min"></span></td><th><fmt:message key='tongji.max'/></th><td><span id="tongji_max"></span></td><th><fmt:message key='tongji.mid' /></th><td><span id="tongji_mid"></span></td></tr>
                <tr><th><fmt:message key='tongji.ave'/></th><td><span id="tongji_ave"></span></td><th><fmt:message key='tongji.sd' /></th><td><span id="tongji_sd"></span></td><th><fmt:message key='tongji.cov'/></th><td><span id="tongji_cov"></span></td></tr>
                </tbody>
            </table>
        </div>
    </div>

</div>

<script type="text/javascript">
    $(function(){

        labChange=function(select){
            $.ajax({
                type: 'POST',
                url: "../audit/labChange?lab="+$(select).children().attr("title"),
                success:function(){
                    $("#labText").html($(select).children().html());
                    window.location.href= baseUrl + "/set/channelset?department=" + $(select).children().attr("title");
                }
            });

        };
        laydate({
            elem: '#qctime',
            event: 'focus',
            format: 'YYYY-MM'
        });
        $('#qctime').val("${tomonth}");

        initGrid();
        var clientHeight = $(window).innerHeight();
        var height = clientHeight - $('#head').height()- $('.footer-content').height();

        $('.tree').height(height-70);

        $("#deviceid").change(function () {
            $("#qcList").jqGrid("setGridParam",{url: "../qc/qcchart/getqcbatch",
                postData:{deviceid: $("#deviceid").val() },}).trigger("reloadGrid");
        })
    });

    function initGrid(){
        var height = $('#qcbatchinfo').height();
        $("#qcbatchList").css("height",height+10);
        $("#qcList").jqGrid({
            caption: "质控品",
            url: "../qc/qcchart/getqcbatch",
            postData:{deviceid: $("#deviceid").val() },
            datatype: "json",
            colNames: ['ID','质控批号','质控品名称','质控水平','样本类型','有效期','方法'],
            colModel: [
                {name : 'id',index : 'id',width : 60,sorttype : "int",hidden:true,key:true},
                { name: 'qcBatch', index: 'qcBatch', width: 60,editable : false },
                { name: 'qcBatchName', index: 'qcBatchName', width: 60,editable : false },
                { name: 'Level', index: 'Level', width: 60,editable : false },
                { name: 'sampleType', index: 'sampleType', width: 100,editable : false  },
                { name: 'expDate', index: 'expDate', width: 60,editable : false },
                { name: 'method', index: 'medthod', width: 60,editable : false }
            ],
            onSelectRow: function(id) {
                refreshIndex();
            },
            loadComplete : function () {
                var length = jQuery('#list').jqGrid('getCol', 'id', false).length;
                if(length==0)
                    refreshIndex();
                $("#qcList").setSelection(1);
            },
            viewrecords: true,
            autowidth:true,
            rowNum:20,
            altRows:true,
            height: height,
            rownumbers: true, // 显示行号
            rownumWidth: 35,
            multiselect: true,
        });
    }
    function refreshIndex() {
        var id = $("#qcList").jqGrid("getGridParam","selrow");
        var rowDate = $("#qcList").jqGrid("getRowData",id);
        $.get("../qc/qcchart/ajax/gettest",{qcbatch:rowDate.qcBatch},function (data) {
            data = jQuery.parseJSON(data);
            $.fn.zTree.init($("#treeList"), {
                data: {
                    key: {
                        title: "name"
                    },
                    simpleData: {
                        enable: true
                    }
                },
                callback: {
                    //beforeClick: beforeClick,
                    onClick: function (event, treeId, treeNode, clickFlag) {
                        var indexId = treeNode.id || '';
                        showChart(indexId);
                    }
                }
            }, data);
        });
    }
    function showChart(indexid){
        var clientHeight = $(window).innerHeight();
        var height = clientHeight - $('#head').height()- $('.footer-content').height()-$('#qcbatchList').height()-50;
        $("#qcChartPanel").height(height);
        var id = $("#qcList").jqGrid("getGridParam","selrow");
        var rowDate = $("#qcList").jqGrid("getRowData",id);
        $.get("../qc/qcchart/qcchartdata",{deviceid:$("#deviceid").val(),qcbatch:rowDate.qcBatch,indexid:indexid,month:$("#qctime").val()},function (data) {
            $("#chartAlert").css("display","none");
            if (data.num > 1) {
                $("#tongji_max").html(data.max);
                $("#tongji_min").html(data.min);
                $("#tongji_mid").html(data.mid);
                $("#tongji_ave").html(data.ave);
                $("#tongji_sd").html(data.sd);
                $("#tongji_cov").html(data.cov);
                var targetValue = data.targetValue;
                var stdv = data.stdv;
                var xset = data.time;
                var yset2 = data.re;
                var chart = new Highcharts.Chart({
                    title: {
                        text: data.name
                    },
                    credits: {
                        enabled:false
                    },
                    chart: {
                        renderTo: 'qcChartPanel',
                        type: 'line',
                        marginRight: 130,
                        marginBottom: 25
                    },
                    xAxis: {
                        type: 'linear',
                        categories: xset,
                        dateTimeLabelFormats:{day: '%e. %b'}
                    },
                    yAxis: {
                        title: {
                            text: '结果'
                        },
                        tickPositioner:function () {
                            var positions = [];
                            positions.push(targetValue-4*stdv);
                            positions.push(targetValue-3*stdv);
                            positions.push(targetValue-2*stdv);
                            positions.push(targetValue-1*stdv);
                            positions.push(targetValue-0*stdv);
                            positions.push(targetValue+1*stdv);
                            positions.push(targetValue+2*stdv);
                            positions.push(targetValue+3*stdv);
                            positions.push(targetValue+4*stdv);
                            return positions;
                        },
                        labels:{
                            formatter:function () {
                                if(this.value == targetValue-4*stdv)
                                    return "-4SD";
                                else if(this.value == targetValue-3*stdv)
                                    return "-3SD";
                                else if(this.value == targetValue-2*stdv)
                                    return "-2SD";
                                else if(this.value == targetValue-1*stdv)
                                    return "-SD";
                                else if(this.value == targetValue-0*stdv)
                                    return "X";
                                else if(this.value == targetValue+1*stdv)
                                    return "SD";
                                else if(this.value == targetValue+2*stdv)
                                    return "2SD";
                                else if(this.value == targetValue+3*stdv)
                                    return "3SD";
                                else if(this.value == targetValue+3*stdv)
                                    return "4SD";
                            }
                        }
                    },
                    legend: {
                        layout: 'vertical',
                        align: 'right',
                        verticalAlign: 'middle',
                        borderWidth: 0
                    },
                    series: [ {
                        name: '检验结果',
                        data: yset2
                    }]
                });
            } else {
                $("#chartAlert").css("display","block");
            }
        });
    }
</script>
