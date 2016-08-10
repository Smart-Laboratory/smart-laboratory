<%--
  Created by IntelliJ IDEA.
  User: zhou
  Date: 2016/6/7
  Time: 15:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp" %>
<head>
    <link rel="stylesheet" type="text/css"  href="<c:url value='/styles/ui.jqgrid.css'/>" />
    <script type="text/javascript" src="../scripts/i18n/grid.locale-cn.js"></script>
    <script type="text/javascript" src="../scripts/jquery.jqGrid.js"></script>
    <script type="text/javascript" src="../scripts/layer/layer.js"></script>

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
    </style>
</head>
<div class="row" id="toolbar">
    <div class="col-xs-9">
        <div  style="padding-top: 5px;">
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
    <div class="col-xs-9 leftContent">
        <table id="leftGrid"></table>
        <div id="leftPager"></div>
    </div>
    <div class="col-xs-3 rightContent">
        <table id="rightGrid"></table>
        <div id="rightPager"></div>
    </div>
</div>


<script type="text/javascript">
    function Add(){
        layer.open({
                type: 2,
            area: ['800px','500px'],
            fix: false, //不固定
            maxmin: true,
            shade:0.5,
            title: "添加组合试验",
                content:  '../set/profiletest/ajaxprofiletest?method=add'
            })
    }
    function Edit(){
        var id=$('#leftGrid').jqGrid('getGridParam','selrow');
        if(id==null || id==0){
            layer.msg('请先选择要编辑的数据', {icon: 2,time: 1000});
            return false;
        }
       // var rowData = $("#leftGrid").jqGrid('getRowData',id);

        layer.open({
            type: 2,
            area: ['800px','500px'],
            fix: false, //不固定
            maxmin: true,
            shade:0.5,
            title: "添加组合试验",
            content:  '../set/profiletest/ajaxprofiletest?method=edit&id='+id
        })
    }

    function Delete(){
        var id=$('#leftGrid').jqGrid('getGridParam','selrow');
        if(id==null || id==0){
            layer.msg('请先选择要删除的数据', {icon: 2,time: 1000});
            return false;
        }
        layer.confirm('确定删除选择数据？', {icon: 2, title:'警告'}, function(index){
            $.post('../set/profiletest/remove',{id:id},function(data) {
                jQuery("#leftGrid").jqGrid('delRowData',id );
            });
            layer.close(index);
        });
    }

    $(function(){
        pageInit();
    });
    function pageInit(){
        //设置表格宽度
        $(window).on('resize.jqGrid', function () {
            $('#leftGrid').jqGrid('setGridWidth', $(".leftContent").width(),false);
            $('#rightGrid').jqGrid('setGridWidth', $(".rightContent").width(),false);
        });
        var clientHeight= $(window).innerHeight();
        var height =clientHeight-$('#head').height()- $('#toolbar').height()-$('.footer-content').height()-150;

        //组合试验表设置
        $("#leftGrid").jqGrid( {
            url:'../set/profiletest/getList',
            datatype : "json",
            height : height,
            shrinkToFit:false,
            width:$('.leftContent').width(),
            colNames : [ 'ID','deviceid','profiletest' ,'缩写', '代号', '组合试验名称', '分析仪器', '周期','分析科室','标本类型' ],
            colModel : [
                {name : 'id',index : 'id',width : 60,sorttype : "int",hidden:true},
                {name : 'deviceid',index : 'deviceid',width : 100,hidden:true},
                {name : 'profiletest',index : 'profiletest',width : 100,hidden:true},
                {name : 'profilename',index : 'profilename',width : 90,align : "left",sorttype : "date"},
                {name : 'profilecode',index : 'profilecode',width : 60,align : "left",},
                {name : 'profiledescribe',index : 'profiledescribe',align : "left",width : 250,sorttype : "text"},
                {name : 'devicename',index : 'devicename',width : 200,align : "left",sorttype : "float"},
                {name : 'frequencytime',index : 'frequencytime',width : 80,align : "left",sorttype : "float"},
                {name : 'sectionname',index : 'sectionname',width : 120,align : "left",sorttype : "float"},
                {name : 'sampletype',index : 'sampletype',width : 80,align : "left",sorttype : "float"}
            ],
            loadComplete : function() {
                var table = this;
                setTimeout(function(){
                    updatePagerIcons(table);
                }, 0);
            },
            onSelectRow: function(id){
               // var rowData = $("#leftGrid").jqGrid('getRowData',id);
                jQuery("#rightGrid").jqGrid('setGridParam',{
                    url: "../set/profiletest/getIndexList",
                    datatype : 'json',
                    //发送数据
                    postData : {"id":id},
                    page : 1
                }).trigger('reloadGrid');//重新载入
            },
            ondblClickRow:function(id){
                Edit();
            },
            //multiselect : true,
            rowNum: 10,
            viewrecords:true,
            rowList:[10,20,40],
            rownumbers: true, // 显示行号
            rownumWidth: 35, // the width of the row numbers columns
            pager: "#leftPager",//分页控件的id
            caption : "组合试验"
        });
        //检验项目表设置
        jQuery("#rightGrid").jqGrid( {
            //datatype : "local",
            height :height,
            width:$('.rightContent').width(),
            colNames : [ 'id', 'indexid', '英文缩写', '中文名称'],
            colModel : [
                {name : 'id',index : 'id',width : 60,hidden : true},
                {name : 'indexid',index : 'indexid',width : 90,hidden:true},
                {name : 'english',index : 'english',width : 80},
                {name : 'name',index : 'name',width : 160,align : "left",sorttype : "text"}
            ],
            loadComplete : function() {
                var table = this;
                setTimeout(function(){
                    updatePagerIcons(table);
                }, 0);
            },
            //multiselect : true,
            shrinkToFit:false,
            scrollOffset:2,
            rowNum: 100,
            rowList:[100],
            rownumbers: true, // 显示行号
            rownumWidth: 35, // the width of the row numbers columns
            pager: "#rightPager",//分页控件的id
            caption : "检验项目"
        });
        $(window).triggerHandler('resize.jqGrid');
    }


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
</script>

