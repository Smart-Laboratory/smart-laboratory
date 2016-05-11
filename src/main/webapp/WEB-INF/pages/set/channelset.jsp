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

</head>
<div class="row">
    <div class="space-2"></div>
    <div class="col-xs-2">
        <ul id="treeList" class="ztree"></ul>
    </div>
    <div class="col-xs-10">
        <div  style="padding-top: 5px;">
            <button type="button" class="btn btn-sm  btn-success" title="保存" onclick="save()">
                <i class="ace-icon fa fa-pencil-square bigger-110"></i>
                <fmt:message key="button.edit" />
            </button>

        </div>
        <table id="tableList"></table>
        <div id="pager"></div>
    </div>
 </div>
<script type="text/javascript">
    $(function(){
        var zNodes =${treeNodes};
        $.fn.zTree.init($("#treeList"), {
            data: {
                key: {
                    title:"t"
                },
                simpleData: {
                    enable: true
                }
            },
            callback: {
                //beforeClick: beforeClick,
                onClick: function(event, treeId, treeNode, clickFlag){
                    //alert();
                    initGrid(treeNode.id)
                }
            }

        }, zNodes);
    })

    function save(){
        //alert(obj.length);
        var ids = $("#tableList").jqGrid('getGridParam','selarrrow');
        //alert(ids);
        //$("#tableList").jqGrid('saveRow',lastsel, 'clientArray');
        jQuery("#tableList").saveRow(lastsel, false, 'clientArray');
        //jQuery('#tableList').restoreRow(lastsel);

        var zTree = $.fn.zTree.getZTreeObj("treeList");
        var node = zTree.getSelectedNodes()[0];
        var deviceid  = node.id||'' ;

        var obj = $("#tableList").jqGrid("getRowData");
        var datas = [];
        jQuery(obj).each(function(){
            console.log(this);
            var obj = {};
            obj.channel = this.channel;
            obj.testid = this.testid;
            obj.deviceid = deviceid;
            datas.push(obj);
        });
        console.log(datas);
        $.post('../set/channelset/save',{datas:arrayToJson(datas)},function(data){
            alert(1);
        })
        //jQuery("#tableList").jqGrid('saveRow', "13");
    }
    var lastsel;
    function initGrid(typeid){

        $("#tableList").jqGrid({
            caption: "设置",
            url: "../set/dictionary/getList",
            //postData:{type:typeid},
            //mtype: "GET",
            datatype: "local",
            colNames: ['通道名称', 'testid','试验名称', '样本名称'],
            colModel: [
                { name: 'channel', index: 'channel', width: 60,editable : true },
                { name: 'testid', index: 'testid', width: 60,hidden:true},
                { name: 'testname', index: 'testname', width: 60},
                { name: 'sampletype', index: 'sampletype', width: 100 }
            ],
            //cellEdit:true,
            loadComplete : function() {
                var table = this;
                setTimeout(function(){
                    updatePagerIcons(table);
                }, 0);
            },
            onSelectRow: function(id) {
                if(id && id!==lastsel){
                    jQuery('#tableList').saveRow(lastsel, false, 'clientArray');
                    lastsel=id;
                }
                jQuery('#tableList').editRow(id, false);
            },
            //jQuery("#grid_id").saveRow(rowid, false, 'clientArray');
           // cellsubmit:'clientArray',
            viewrecords: true,
            autowidth:true,
            altRows:true,
            //height: 300,
            height: "100%",
            rownumbers: true, // 显示行号
            rownumWidth: 35
        });


        var mydata = [
            {channel : "11",testid : "9162",testname : "test1",sampletype : "C"},
            {channel : "6",testid : "9163",testname : "test2",sampletype : "C"},
            {channel : "4",testid : "9164",testname : "test3",sampletype : "C"},
            {channel : "",testid : "9165",testname : "test4",sampletype : "Q"},
            {channel : "",testid : "9166",testname : "test5 ",sampletype : "f"},
        ];

        for ( var i = 0; i <= mydata.length; i++){
            jQuery("#tableList").jqGrid('addRowData', i + 1, mydata[i]);
        }
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