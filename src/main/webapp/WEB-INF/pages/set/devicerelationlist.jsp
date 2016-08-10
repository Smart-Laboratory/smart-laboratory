<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp" %>
<head>
	<title><fmt:message key="sample.inspectionName"/></title>
    <link rel="stylesheet" href="<c:url value="/styles/font-awesome.css"/>" />
    <link rel="stylesheet" href="<c:url value="/styles/bootstrap-datetimepicker.min.css"/>" />

    <!-- page specific plugin styles -->
    <link rel="stylesheet" href="<c:url value="/styles/bootstrap-duallistbox.min.css"/>"/>
    <link rel="stylesheet" href="<c:url value="/styles/ztree/zTreeStyle.css"/>" type="text/css">
    <link rel="stylesheet" type="text/css"  href="<c:url value='/styles/ui.jqgrid.css'/>" />

    <script src="<c:url value="/scripts/jquery.bootstrap-duallistbox.min.js"/>"></script>
    <script src="<c:url value="/scripts/jquery.ztree.all-3.5.js"/>"></script>
    <script src="<c:url value="/scripts/layer/layer.js"/>"></script>
    <script type="text/javascript" src="../scripts/i18n/grid.locale-cn.js"></script>
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
                <input type="text" id="query" class="form-control search-query" placeholder = "输入编号或名称" />
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
    <div class="col-xs-2">
        <div class="widget-box widget-color-green ui-sortable-handle">
            <div class="widget-header">
                <h6 class="widget-title">部门列表</h6>
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
                            <ul id="treeList" class="ztree"></ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>


    </div>
    <div class="col-xs-10 leftContent">
        <table id="rightGrid"></table>
        <div id="leftPager"></div>
    </div>
</div>

<script type="text/javascript">
    var zNodes = '${treeNodes}';
    if(zNodes !='') zNodes = eval("("+zNodes+")");

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
                jQuery("#rightGrid").jqGrid('setGridParam',{
                    url: "../set/devicerelationlist/getList",
                    datatype : 'json',
                    //发送数据
                    postData : {"departmentid":treeNode.id},
                    page : 1
                }).trigger('reloadGrid');//重新载入
            }
        },
        view: {}
    };

    $(function ($) {
        $.fn.zTree.init($("#treeList"), setting, zNodes);
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
            area: ['1000px','610px'],
            fix: false, //不固定
            maxmin: true,
            shade:0.5,
            title: "添加指标",
            content:  '../set/devicerelationlist/ajaxeditdevicerelation?method=add'
        })
    }

function Edit(){
    var id=$('#rightGrid').jqGrid('getGridParam','selrow');
    if(id==null || id==0){
        layer.msg('请先选择要编辑的数据', {icon: 2,time: 1000});
        return false;
    }
    // var rowData = $("#rightGrid").jqGrid('getRowData',id);

    layer.open({
        type: 2,
        area: ['1000px','610px'],
        fix: false, //不固定
        maxmin: true,
        shade:0.5,
        title: "添加指标",
        content:  '../set/devicerelationlist/ajaxeditdevicerelation?method=edit&id='+id
    })
}

    function Delete(){
        var id=$('#rightGrid').jqGrid('getGridParam','selrow');
        if(id==null || id==0){
            layer.msg('请先选择要删除的数据', {icon: 2,time: 1000});
            return false;
        }
        layer.confirm('确定删除选择数据？', {icon: 2, title:'警告'}, function(index){
            $.post('../set/devicerelationlist/deleteIndex',{id:id},function(data) {
                jQuery("#rightGrid").jqGrid('delRowData',id );
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
            $('#rightGrid').jqGrid('setGridWidth', $(".leftContent").width(),false);
        });
        $("#rightGrid").jqGrid({
            url: '../set/devicerelationlist/getList',
            datatype: "json",
            height: height,
            shrinkToFit: false,
            regional : 'cn',
            width: $('.leftContent').width(),
            colNames: ['id', '试验编号',  '中文名称', '英文名称', '样本类型', '检验部门', '单位'],
            colModel: [
                {name: 'id', index: 'id', width: 100,  hidden: true},
                {name: 'indexid', index: 'index_id', width: 100,sorttype:'text',sortable:true},
                {name: 'name', index: 'name', width: 250},
                {name: 'english', index: 'english', width: 150},
                {name: 'sampletype', index: 'sampletype', width: 100},
                {name: 'labdepartment', index: 'labdepartment', width: 150},
                {name: 'unit', index: 'unit', width: 80}
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
            pager: "#leftPager",//分页控件的id
            caption: "项目"
        });
        $(window).triggerHandler('resize.jqGrid');

    }

    function search(){
        var query = $('#query').val()||'';
        jQuery("#rightGrid").jqGrid('setGridParam',{
            url: "../set/devicerelationlist/getList",
            datatype : 'json',
            //发送数据
            postData : {"query":query },
            page : 1
        }).trigger('reloadGrid');//重新载入
    }

</script>
