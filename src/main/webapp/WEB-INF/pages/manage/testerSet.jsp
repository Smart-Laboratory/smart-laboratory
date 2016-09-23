<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp" %>

<head>
    <title>检验者设置</title>
    <script type="text/javascript" src="../scripts/i18n/grid.locale-cn.js"></script>
    <script type="text/javascript" src="../scripts/jquery.jqGrid.js"></script>
    <link rel="stylesheet" type="text/css"  href="<c:url value='/styles/ui.jqgrid.css'/>" />
    <script type="text/javascript" src="<c:url value='/scripts/layer/layer.js'/>"></script>
</head>

<script type="text/javascript">
    /**
     * 设置JQGRID 上下页图标
     * @param table
     */
    function updatePagerIcons(table) {
        var replacement =
        {
            'ui-icon-seek-first' : 'ace-icon fa fa-angle-double-left bigger-140',
            'ui-icon-seek-prev' : 'ace-icon fa fa-angle-left bigger-140',
            'ui-icon-seek-next' : 'ace-icon fa fa-angle-right bigger-140',
            'ui-icon-seek-end' : 'ace-icon fa fa-angle-double-right bigger-140'
        };
        $('.ui-pg-table:not(.navtable) > tbody > tr > .ui-pg-button > .ui-icon').each(function(){
            var icon = $(this);
            var $class = $.trim(icon.attr('class').replace('ui-icon', ''));
            if($class in replacement) icon.attr('class', 'ui-icon '+replacement[$class]);
        })
    }

    $(function() {
        labChange=function(select) {
            $.ajax({
                type: 'POST',
                url: baseUrl + "/audit/labChange?lab="+$(select).children().attr("title"),
                success:function(data){
                    var section = $(select).children().attr("title");
                    $("#labText").html($(select).children().html());
                    window.location.reload();
                }
            });
        }
        initGrid();
    })

    function initGrid() {
        var clientHeight= $(window).innerHeight();
        var height =clientHeight-$('#head').height()- $('#toolbar').height()-$('.footer-content').height()-150;
        $("#list").jqGrid({
            caption: "检验者设置",
            url: baseUrl + "/manage/testerSet/getList",
            mtype: "GET",
            datatype: "json",
            colNames: ['仪器号', '检验段', '已设置','检验者','设置时间'],
            colModel: [
                { name: 'deviceId', index: 'deviceId', width: 60},
                { name: 'segment', index: 'segment', width: 60},
                { name: 'isSet', index: 'isSet', width: 60, align:"center"},
                { name: 'tester', index: 'tester', width: 60},
                { name: 'setTime', index: 'setTime', width: 60}
            ],
            loadComplete : function() {
                var table = this;
                setTimeout(function(){
                    updatePagerIcons(table);
                }, 0);
            },
            viewrecords: true,
            autowidth:true,
            height: height,
            rowNum: -1,
            rownumbers: true, // 显示行号
            rownumWidth: 35, // the width of the row numbers columns
            pager: "#pager"//分页控件的id
        });
        jQuery("#list").jqGrid('navGrid','#pager',{edit:false,add:false,del:false});
    }

    function setTester(deviceId, segment) {
        $.get(baseUrl + "/manage/testerSet/set",{deviceId: deviceId, segment: segment},function(data){
            if(data) {
                $("#list").jqGrid().trigger("reloadGrid");
            }
        });
    }
</script>

    <div id="mid" class="col-sm-12">

        <table id="list" class="table"></table>
        <div id="pager"></div>

    </div>