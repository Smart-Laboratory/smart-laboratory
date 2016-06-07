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
</head>
<div class="row">
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
        <div class="col-xs-3"></div>
    </div>
</div>
<div class="row">
    <div class="col-xs-12" class="jqGrid_wrapper">
        <table id="leftGrid"></table>
        <div id="leftPager"></div>
    </div>
    <%--<div class="col-xs-3">--%>
        <%--<table id="rightGird"></table>--%>
        <%--<div id="rightPager"></div>--%>
    <%--</div>--%>
</div>
<script type="text/javascript">
    $(function(){
        pageInit();
    });
    function pageInit(id){
        var grid_selector = "#grid-table";
        var pager_selector = "#grid-pager";

        //resize to fit page size
        $(window).on('resize.jqGrid', function () {
            $(grid_selector).jqGrid( 'setGridWidth', $(".page-content").width() );
        })
        //resize on sidebar collapse/expand
        var parent_column = $(grid_selector).closest('[class*="col-"]');
        $(document).on('settings.ace.jqGrid' , function(ev, event_name, collapsed) {
            if( event_name === 'sidebar_collapsed' || event_name === 'main_container_fixed' ) {
                //setTimeout is for webkit only to give time for DOM changes and then redraw!!!
                setTimeout(function() {
                    $(grid_selector).jqGrid( 'setGridWidth', parent_column.width() );
                }, 0);
            }
        })
        jQuery("#leftGrid").jqGrid( {
                    datatype : "local",
                    height : "100%",
                    autowidth:true,
                    colNames : [ 'Inv No', 'Date', 'Client', 'Amount', 'Tax','Total', 'Notes' ],
                    colModel : [
                        {name : 'id',index : 'id',width : 60,sorttype : "int"},
                        {name : 'invdate',index : 'invdate',width : 90,sorttype : "date"},
                        {name : 'name',index : 'name',width : 100},
                        {name : 'amount',index : 'amount',width : 80,align : "right",sorttype : "float"},
                        {name : 'tax',index : 'tax',width : 80,align : "right",sorttype : "float"},
                        {name : 'total',index : 'total',width : 80,align : "right",sorttype : "float"},
                        {name : 'note',index : 'note',width : 150,sortable : false}
                    ],
                    multiselect : true,

                    caption : "Manipulating Array Data"
                });
        var mydata = [
            {id : "1",invdate : "2007-10-01",name : "test",note : "note",amount : "200.00",tax : "10.00",total : "210.00"},
            {id : "2",invdate : "2007-10-02",name : "test2",note : "note2",amount : "300.00",tax : "20.00",total : "320.00"},
            {id : "3",invdate : "2007-09-01",name : "test3",note : "note3",amount : "400.00",tax : "30.00",total : "430.00"},
            {id : "4",invdate : "2007-10-04",name : "test",note : "note",amount : "200.00",tax : "10.00",total : "210.00"},
            {id : "5",invdate : "2007-10-05",name : "test2",note : "note2",amount : "300.00",tax : "20.00",total : "320.00"},
            {id : "6",invdate : "2007-09-06",name : "test3",note : "note3",amount : "400.00",tax : "30.00",total : "430.00"},
            {id : "7",invdate : "2007-10-04",name : "test",note : "note",amount : "200.00",tax : "10.00",total : "210.00"},
            {id : "8",invdate : "2007-10-03",name : "test2",note : "note2",amount : "300.00",tax : "20.00",total : "320.00"},
            {id : "9",invdate : "2007-09-01",name : "test3",note : "note3",amount : "400.00",tax : "30.00",total : "430.00"}
        ];
        for ( var i = 0; i <= mydata.length; i++){
            jQuery("#leftGrid").jqGrid('addRowData', i + 1, mydata[i]);
        }
        jQuery("#rightGird").jqGrid( {
            datatype : "local",
            height : "100%",
            autowidth:true,
            colNames : [ 'Inv No', 'Date', 'Client', 'Amount', 'Tax','Total', 'Notes' ],
            colModel : [
                {name : 'id',index : 'id',width : 60,sorttype : "int"},
                {name : 'invdate',index : 'invdate',width : 90,sorttype : "date"},
                {name : 'name',index : 'name',width : 100},
                {name : 'amount',index : 'amount',width : 80,align : "right",sorttype : "float"},
                {name : 'tax',index : 'tax',width : 80,align : "right",sorttype : "float"},
                {name : 'total',index : 'total',width : 80,align : "right",sorttype : "float"},
                {name : 'note',index : 'note',width : 150,sortable : false}
            ],
            multiselect : true,

            caption : "Manipulating Array Data"
        });
    }

</script>

