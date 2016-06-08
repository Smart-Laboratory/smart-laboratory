<%--
  Created by IntelliJ IDEA.
  User: zhou
  Date: 2016/6/8
  Time: 1:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp" %>
<head>


    <script type="text/javascript" src="<c:url value="/scripts/jquery-2.1.4.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/bootstrap.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/ace.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/ace-elements.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/bootstrap-tag.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/jquery-ui.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/i18n/grid.locale-cn.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/jquery.form.js"/>"></script>

    <link rel="stylesheet" type="text/css"  href="<c:url value='/styles/ui.jqgrid.css'/>" />
    <link rel="stylesheet" type="text/css"  href="<c:url value='/styles/bootstrap.min.css'/>" />
    <link rel="stylesheet" type="text/css"  href="<c:url value='/styles/font-awesome.css'/>" />
    <link rel="stylesheet" type="text/css"  href="<c:url value='/styles/ace.min.css'/>" />


    <script type="text/javascript" src="<c:url value="/scripts/i18n/grid.locale-cn.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/jquery.jqGrid.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/layer/layer.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/jquery.form.js"/>"></script>

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
        .ui-jqgrid .ui-jqgrid-htable th span.ui-jqgrid-resize{
            height: 35px !important;
        }
        .ui-jqgrid .ui-jqgrid-htable th div {
            padding-top: 5px;
            padding-bottom: 2px;
        }
        .ui-jqgrid .ui-jqgrid-htable th div{
            height: 30px;
        }
        .ui-jqgrid .ui-jqgrid-labels {
            border: none;
            background: #F2F2F2;
            /* background-image: -webkit-linear-gradient(top, #f8f8f8 0, #ececec 100%); */
            background-image:none;
            background-repeat: repeat-x;
        }
        .ui-autocomplete{
            z-index: 999;
        }
        .widget-header {
            -webkit-box-sizing: content-box;
            -moz-box-sizing: content-box;
            box-sizing: content-box;
            position: relative;
            min-height: 38px;
            background: #f7f7f7;
            /* background-image: -webkit-linear-gradient(top, #fff 0, #eee 100%); */
            /* background-image: linear-gradient(to bottom, #fff 0, #eee 100%); */
            /* background-repeat: repeat-x; */
            filter:"";
            color: #669fc7;
            border-left: 1px solid #DDD;
            border-right: 1px solid #DDD;
            padding-left: 12px;
        }
    </style>
</head>
<div class="main-container" id="content">
    <div class="row">
        <div class="space-6"></div>
        <div class="col-xs-5 form-horizontal">
            <div class="form-group">
                <label class="col-xs-4 control-label no-padding-right" for="profilename"> 缩写 </label>
                <div class="col-xs-8">
                    <input type="text" id="profilecode" name="profilecode" placeholder="缩写"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-4 control-label no-padding-right" for="profilecode"> 代号 </label>
                <div class="col-xs-8">
                    <input type="text" id="profilename" name="profilename" placeholder="代号"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-4 control-label no-padding-right" for="profiledescribe"> 组合试验名称 </label>
                <div class="col-xs-8">
                    <input type="text" id="profiledescribe" name="profiledescribe" placeholder="组合试验名称"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-4 control-label no-padding-right" for="sectionid"> 分析科室 </label>
                <div class="col-xs-8">
                    <input type="text" id="sectionid" name="sectionid" placeholder="分析科室"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-4 control-label no-padding-right" for="deviceid"> 分析仪器 </label>
                <div class="col-xs-8">
                    <input type="text" id="deviceid" name="deviceid" placeholder="分析仪器"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-4 control-label no-padding-right" for="sampletype"> 标本类型 </label>
                <div class="col-xs-8">
                    <input type="text" id="sampletype" name="sampletype" placeholder="标本类型"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-4 control-label no-padding-right" for="frequencytime"> 周期 </label>
                <div class="col-xs-8">
                    <input type="text" id="frequencytime" name="frequencytime" placeholder="周期"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-4 control-label no-padding-right" for="usenow"> 是否使用 </label>
                <div class="col-xs-8">
                    <input type="text" id="usenow" name="usenow" placeholder="是否使用"/>
                </div>
            </div>
        </div>
        <div class="col-xs-7 rightContent">
            <div class="row">
                <div class="col-xs-12">
                    <div class="col-xs-9" >
                        <input class="form-control col-xs-12" id="searchProject" type="text" placeholder="检验项目..">
                    </div>
                    <div class="col-xs-3">
                        <button type="button" class="btn btn-sm  btn-success"  style="float: right;margin-right: 15px" title="删除" onclick="Delete()">
                            <i class="ace-icon fa fa-pencil-square bigger-110"></i>
                            删除
                        </button>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="widget-body">
                    <div class="widget-main">
                        <table id="rightGrid"></table>
                        <div id="rightPager"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">

    $(function(){
        pageInit();
//        $("#tableList").jqGrid('setGridParam',{
//            datatype:'local',
//            rowNum:references.length,
//            data:references
//        }).trigger('reloadGrid');//重新载入
    });
    function pageInit(){
        $("#rightGrid").jqGrid( {
            datatype : "local",
           // rowNum:indexlist.length,
            height :350,
            width:$('.rightContent').width(),
            colNames : [ 'indexid', '英文缩写', '中文名称'],
            colModel : [
                {name : 'indexid',index : 'indexid',width : 90,hidden:true},
                {name : 'english',index : 'english',width : 80},
                {name : 'name',index : 'name',width : 160,align : "left",sorttype : "text"}
            ],
            //multiselect : true,
            shrinkToFit:false,
            scrollOffset:2,
            rownumbers: true, // 显示行号
            rownumWidth: 35
        });
    }

    $("#searchProject").autocomplete({
        source: function( request, response ) {
            $.ajax({
                url: "/ajax/searchTest",
                dataType: "json",
                data: {
                    name : request.term
                },
                success: function( data ) {
                    response( $.map( data, function( result ) {
                        return {
                            label: result.id + " : " + result.ab + " : " + result.name,
                            value: result.name,
                            id : result.id,
                            ab:result.ab
                        }
                    }));
                    $("#searchProject").removeClass("ui-autocomplete-loading");
                }
            });
        },
        minLength: 1,
        select : function(event, ui) {
            //$("#addIndexId").val(ui.item.id);
            var obj = $("#rightGrid").jqGrid("getRowData");
            var datas = [];
            var flag = true;
            jQuery(obj).each(function(){
                if(ui.item.id == this.indexid){
                    flag =  false;
                    layer.msg("数据已存在");
                }
                return;
            });
            if(!flag) return ;
            var ids = $('#rightGrid').jqGrid('getDataIDs');
            //console.log(ids)
            var newId = parseInt(ids[ids.length - 1] || 0) + 1;
            var rowData = {
                indexid: ui.item.id,
                english: ui.item.ab,
                name: ui.item.value
            };
            $("#rightGrid").jqGrid('addRowData', newId, rowData);
            //indexids.push(ui.item.indexid);
            //jQuery('#rightGrid').saveRow(newId, false, 'clientArray');
        }
    });

</script>

