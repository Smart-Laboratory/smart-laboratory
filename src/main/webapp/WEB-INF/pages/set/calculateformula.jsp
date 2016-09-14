<%--
  Created by IntelliJ IDEA.
  User: zhou
  Date: 2016/6/14
  Time: 9:02a
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp" %>
<head>
    <link rel="stylesheet" href="<c:url value="/styles/font-awesome.css"/>" />
    <link rel="stylesheet" href="<c:url value="/styles/bootstrap-datetimepicker.min.css"/>" />

    <!-- page specific plugin styles -->
    <link rel="stylesheet" href="<c:url value="/styles/bootstrap-duallistbox.min.css"/>"/>
    <link rel="stylesheet" href="<c:url value="/styles/ztree/zTreeStyle.css"/>" type="text/css">
    <link rel="stylesheet" type="text/css"  href="<c:url value='/styles/ui.jqgrid.css'/>" />

    <script src="<c:url value="/scripts/jquery.bootstrap-duallistbox.min.js"/>"></script>
    <script src="<c:url value="/scripts/jquery.ztree.all-3.5.js"/>"></script>
    <script src="<c:url value="/scripts/layer/layer.js"/>"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/scripts/i18n/grid.locale-cn.js"></script>
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
    .table-bordered>thead>tr>td, .table-bordered>thead>tr>th{
        background-color: #F5F5F6;
    }
    .laftnav{
        /*background: #ffffff;*/
        border-right: 1px solid #D9D9D9;
        border-left: 1px solid #D9D9D9;
    }
    .lazy_header{
        height: 40px;
        background: #F7F7F7 !important;
        border-bottom: 1px solid #D9D9D9;
        border-top: 1px solid #D9D9D9;
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
    .no-skin .nav-list>li.active:after{
        border: 1px;
    }
    .no-skin .nav-list>li>a{
        padding-left:20px;
    }
    ul.nav{
        margin-left:0px;
    }
    .nav-pills>li>a{
        border-radius: 0px;
    }
    th[aria-selected=true] {
        background-image: none;
        filter: none;
    }
    .ui-jqgrid .ui-jqgrid-labels {
        border-bottom: none;
        background-image: none;
        filter:none;
    }
</style>
</head>
<div class="row" id="toolbar">
    <div class="col-xs-12">
        <div  style="padding-top: 2px;background: #F8F8F8;">
            <button type="button" class="btn btn-sm btn-primary " title="添加" onclick="Add()">
                <i class="ace-icon fa fa-fire bigger-110"></i>
                <fmt:message key="button.add" />
            </button>
            <button type="button" class="btn btn-sm  btn-success" title="编辑" onclick="Edit()">
                <i class="ace-icon fa fa-pencil-square bigger-110"></i>
                <fmt:message key="button.edit" />
            </button>
            <button type="button" class="btn btn-sm btn-danger" title="删除" onclick="Delete()">
                <i class="ace-icon fa fa-times bigger-110"></i>
                <fmt:message key="button.delete" />
            </button>
            <div class="input-group col-sm-3 " style="float: right;" >
                <input type="text" id="query" class="form-control search-query" placeholder="输入编号或名称" />
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
    <div class="col-xs-12 leftContent">
        <table id="dataGrid"></table>
        <div id="dataPager"></div>
    </div>
</div>

<script type="text/javascript">
    $(function ($) {
        initGrid();
        //keyPress 回车检索
        $("#query").keypress(function(e){
            if (e.keyCode == 13){
                search();
            }
        });
    });

    /**
     * 新增指标项目
     * */
    function Add(){
        layer.open({
            type: 2,
            area: ['800px','500px'],
            fix: false, //不固定
            maxmin: true,
            shade:0.5,
            title: "添加指标",
            content:  '<%=request.getContextPath()%>/set/calculateformula/ajaxeditdformula'
        })
    }

    function Edit(){
        var id=$('#dataGrid').jqGrid('getGridParam','selrow');

        if(id==null || id==0){
            layer.msg('请先选择要编辑的数据', {icon: 2,time: 1000});
            return false;
        }
        var rowData = $("#dataGrid").jqGrid('getRowData',id);
        layer.open({
            type: 2,
            area: ['800px','500px'],
            fix: false, //不固定
            maxmin: true,
            shade:0.5,
            title: "添加指标",
            content:  '<%=request.getContextPath()%>/set/calculateformula/ajaxeditdformula?testid='+rowData.testId
        })
    }

    function Delete(){
        var id=$('#dataGrid').jqGrid('getGridParam','selrow');
        if(id==null || id==0){
            layer.msg('请先选择要删除的数据', {icon: 2,time: 1000});
            return false;
        }
        var rowData = $("#dataGrid").jqGrid('getRowData',id);
        layer.confirm('确定删除选择数据？', {icon: 2, title:'警告'}, function(index){
            $.post('<%=request.getContextPath()%>/set/calculateformula/deleteFormula',{testid:rowData.testId},function(data) {
                jQuery("#dataGrid").jqGrid('delRowData',id );
            });
            layer.close(index);
        });
    }
    /***********************************
     * 初始化列表
     *************************************/
    function initGrid() {
        var clientHeight= $(window).innerHeight();
        var height =clientHeight-$('#head').height()- $('#toolbar').height()-$('.footer-content').height()-150;

        $('.scroll-content').height(height+65);
        //设置表格宽度
        $(window).on('resize.jqGrid', function () {
            $('#dataGrid').jqGrid('setGridWidth', $(".leftContent").width(),false);
        });
        $("#dataGrid").jqGrid({
            url: '<%=request.getContextPath()%>/set/calculateformula/getList',
            datatype: "json",
            height: height,
            shrinkToFit: false,
            regional : 'cn',
            width: $('.leftContent').width(),
            colNames: ['testId', '公式名称',  '计算公式', '排斥项目'],
            colModel: [
                {name: 'testId', index: 'testid', width: 100,  hidden: true},
                {name: 'testName', index: 'testname', width: 200,sorttype:'text',sortable:true},
                {name: 'formulaDescribe', index: 'formulaDescribe', width: 300},
                {name: 'excludeDescribe', index: 'excludeDescribe', width: 200}
            ],
            loadComplete: function () {
                var table = this;
                setTimeout(function () {
                    updatePagerIcons(table);
                }, 0);
            },
            onSelectRow: function (id) {
                // var rowData = $("#rightGrid").jqGrid('getRowData',id);
            },
            ondblClickRow: function (id) {
                Edit();
            },
            //multiselect : true,
            altRows:true,
            rowNum: 10,
            viewrecords: true,
            rowList: [10, 20, 40],
            rownumbers: true, // 显示行号
            rownumWidth: 35, // the width of the row numbers columns
            pager: "#dataPager",//分页控件的id
            caption: "计算公式设置"
        });


        $(window).triggerHandler('resize.jqGrid');
    }

    function search(){
        var query = $('#query').val()||'';
        jQuery("#dataGrid").jqGrid('setGridParam',{
            url: "<%=request.getContextPath()%>/set/calculateformula/getList",
            datatype : 'json',
            //发送数据
            postData : {"query":query },
            page : 1
        }).trigger('reloadGrid');//重新载入
    }

</script>

