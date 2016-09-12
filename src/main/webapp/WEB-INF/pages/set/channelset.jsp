<%--
  Created by IntelliJ IDEA.
  User: zhou
  Date: 2016/6/2
  Time: 21:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp" %>
<head>
    <link rel="stylesheet" type="text/css"  href="<c:url value='/styles/ui.jqgrid.css'/>" />
    <link rel="stylesheet" href="<c:url value="/styles/ztree/zTreeStyle.css"/>"/>
    <script src="<c:url value="/scripts/jquery.ztree.all-3.5.js"/>"></script>
    <script src="<c:url value="/scripts/layer/layer.js"/>"></script>
    <script type="text/javascript" src="../scripts/jquery.jqGrid.js"></script>
    <script type="text/javascript" src="../scripts/i18n/grid.locale-cn.js"></script>
</head>
<style>
    .lazy_header{
        height: 35px;
        background: #F7F7F7 !important;
        border: 1px solid #D9D9D9;
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
    .ztree{
        border: 1px solid #D9D9D9;
        border-top: none;
        height: 100%;
    }
</style>
<div class="row">
    <div class="space-2"></div>
    <div class="col-xs-2">
        <div class="laftnav" >
            <div class="lazy_header">
                <span class="lazy-list-title">
                    <i class="fa fa-bars"></i>
                    <span class="tip" style="cursor:pointer;">仪器设备</span>
                </span>
            </div>
            <div class="tree">
                <ul id="treeList" class="ztree"></ul>
            </div>
        </div>

    </div>
    <div class="col-xs-10">
        <div>
            <button type="button" class="btn btn-sm  btn-info" title="保存" onclick="save()">
                <i class="aace-icon fa fa-floppy-o bigger-120"></i>
                保存
            </button>

        </div>
        <table id="tableList"></table>
        <div id="pager"></div>
    </div>
 </div>
<script type="text/javascript">
    $(function(){
        initGrid();
        var clientHeight = $(window).innerHeight();
        var height = clientHeight - $('#head').height()- $('.footer-content').height();
        $('.tree').height(height-70);
        var zNodes =${treeNodes};

        $.fn.zTree.init($("#treeList"), {
            data: {
                key: {
                    title:"name"
                },
                simpleData: {
                    enable: true
                }
            },
            callback: {
                //beforeClick: beforeClick,
                onClick: function(event, treeId, treeNode, clickFlag){
                    var deviceid =treeNode.id||'';
                    jQuery("#tableList").jqGrid('setGridParam',{
                        url: "../set/channelset/getData",
                        datatype : 'json',
                        mtype: 'POST',
                        repeatitems : false,
                        //发送数据
                        postData : {"deviceid":deviceid },
                    }).trigger('reloadGrid');//重新载入

                }
            }
        }, zNodes);
    });

    function save(){
        var ids = $("#tableList").jqGrid('getGridParam','selarrrow');
        jQuery("#tableList").saveRow(lastsel, false, 'clientArray');
        var zTree = $.fn.zTree.getZTreeObj("treeList");
        var node = zTree.getSelectedNodes()[0];
        var deviceid  = node.id||'' ;

        var obj = $("#tableList").jqGrid("getRowData");
        var datas = [];
        jQuery(obj).each(function(){
            var obj = {};
            obj.channel = this.channel;
            obj.testid = this.testid;
            obj.deviceid = deviceid;
            obj.sampletype= this.sampletype;
            datas.push(obj);
        });
        $.post('../set/channelset/save',{datas:arrayToJson(datas)},function(data){
            layer.msg("保存成功");
        })
    }
    var lastsel;
    function initGrid(){
        var clientHeight = $(window).innerHeight();
        var height = clientHeight - $('#head').height()- $('.footer-content').height()-150;
        $("#tableList").jqGrid({
            caption: "设置",
            //url: "../set/dictionary/getList",
            //postData:{deviceid:typeid},

            datatype: "json",
            colNames: ['通道名称', 'testid','试验名称', '样本类型'],
            colModel: [
                { name: 'channel', index: 'channel', width: 60,editable : true },
                { name: 'testid', index: 'testid', width: 60,hidden:true},
                { name: 'testname', index: 'testname', width: 60},
                { name: 'sampletype', index: 'sampletype', width: 100,editable : true }
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
            //height: 300,
            height: height,
            rownumbers: true, // 显示行号
            rownumWidth: 35
        });
    }

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