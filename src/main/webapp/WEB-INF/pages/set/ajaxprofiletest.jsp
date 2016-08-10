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
    <script type="text/javascript" src="<c:url value="/scripts/validform/Validform.min.js"/>"></script>
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
    <div class="row" id="toolbar">
        <div class="col-xs-12">
            <div style="padding-top: 5px;">
                <button type="button" class="btn btn-sm btn-primary " title="保存" onclick="Save()">
                    <i class="ace-icon fa fa-fire bigger-110"></i>
                    保存
                </button>
                <button type="button" class="btn btn-sm  btn-success" title="取消" onclick="Cancel()">
                    <i class="ace-icon fa fa-pencil-square bigger-110"></i>
                    取消
                </button>
                <div class="input-group col-xs-6 " style="float: right;">
                    <input class="form-control col-xs-12"  id="searchProject" type="text" placeholder="检验项目..">
                </div>
            </div>

        </div>
    </div>
    <div class="row">
        <div class="space-6"></div>
        <form class="form-horizontal" name="profiletestForm" id="profiletestForm" action="#">
        <div class="col-xs-6 ">
            <div class="form-group">
                <label class="col-xs-3 control-label no-padding-right" for="profilename"> 缩写 </label>
                <div class="col-xs-9">
                    <input type="text"  class="col-xs-9" id="profilename" name="profilename" placeholder="缩写" datatype="s3-3"  nullmsg="请输入缩写！" errormsg="缩写为3个字符！"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label no-padding-right" for="profilecode"> 代号 </label>
                <div class="col-xs-9">
                    <input type="text"  class="col-xs-9" id="profilecode" name="profilecode" placeholder="代号"  datatype="*2-2"  nullmsg="请输入代号！"  errormsg="代号为2个字符！"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label no-padding-right" for="profiledescribe"> 组合试验名称 </label>
                <div class="col-xs-9">
                    <input type="text" class="col-xs-9" id="profiledescribe" name="profiledescribe" placeholder="组合试验名称" datatype="*" nullmsg="请输入组合试验名称！"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label no-padding-right" for="sectionid"> 分析科室 </label>
                <div class="col-xs-9">
                    <input type="text" class="col-xs-9" id="sectionid" name="sectionid" placeholder="分析科室" value="" datatype="*" nullmsg="请输入分析科室！"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label no-padding-right" for="deviceid"> 分析仪器 </label>
                <div class="col-xs-9">
                    <input type="text" class="col-xs-9" id="deviceid" name="deviceid" placeholder="分析仪器" datatype="*" nullmsg="请输入分析仪器！"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label no-padding-right" for="sampletype"> 标本类型 </label>
                <div class="col-xs-9">
                    <input type="text"  class="col-xs-9" id="sampletype" name="sampletype" placeholder="标本类型"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label no-padding-right" for="frequencytime"> 周期 </label>
                <div class="col-xs-9">
                    <input type="text"  class="col-xs-9" id="frequencytime" name="frequencytime" placeholder="周期"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label no-padding-right" for="usenow"> 是否使用 </label>
                <div class="col-xs-9">
                    <input type="text"  class="col-xs-9" id="usenow" name="usenow" placeholder="是否使用"/>
                </div>
            </div>
        </div>
        <div class="col-xs-6 rightContent">
            <table id="rightGrid"></table>
            <div id="rightPager"></div>
        </div>
        </form>
    </div>
</div>

<input type="hidden" id="section" value="">
<input type="hidden" id="device" value="">
<input type="hidden" id="profiletestid" value="${id}">
<script type="text/javascript">
    var profileInfo = '${profileTest}';
    if(profileInfo !='') profileInfo = eval("("+'${profileTest}'+")");

    /*
    * 保存
    * */
    function Save(){
        var obj={};
        obj.profilecode = $('#profilecode').val()||'';
        obj.profilename = $('#profilename').val()||'';
        obj.profiledescribe = $('#profiledescribe').val()||'';
        obj.sectionid = $('#section').val()||'';
        obj.deviceid = $('#device').val()||'';
        obj.sampletype = $('#sampletype').val();
        obj.frequencytime = $('#frequencytime').val();
        obj.usenow = $('#usenow').val();
        obj.id = $('#profiletestid').val();
        obj.indexids = "";


        var rowDatas = $("#rightGrid").jqGrid("getRowData");

        console.log(rowDatas);
        //return false;
        var datas = [];
        jQuery(rowDatas).each(function(){
            console.log(this);
            datas.push(this.indexid);
        });
        if(datas.length>0) obj.indexids = datas.join(",")+",";
        console.log(obj);

        if(obj.sectionid ==''){
            layer.alert('分析科室没有输入，不允许保存',{icon:2});
            return false;
        }
        if(obj.profilename ==''){
            layer.alert('简称没有输入，不允许保存',{icon:2});
            return false;
        }
        if(obj.profilecode ==''){
            layer.alert('代码没有输入，不允许保存',{icon:2});
            return false;
        }
        if(obj.indexids ==''){
            layer.alert('检验项目没有输入，不允许保存',{icon:2});
            return false;
        }
        //保存数据
        $.post('../../set/profiletest/saveProfileTest',
                {profilecode:obj.profilecode,
                profilename:obj.profilename,
                profiledescribe:obj.profiledescribe,
                sectionid:obj.sectionid,
                deviceid:obj.deviceid,
                sampletype:obj.sampletype,
                frequencytime:obj.frequencytime,
                usenow:obj.usenow,
                indexids:obj.indexids,
                id:obj.id
        },function(data){
                var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
                parent.layer.close(index);
        })
    }
    /**
     * 删除项目
     * */
    function Delete(id){
        //var id = $('#rightGrid').jqGrid('getGridParam', 'selrow');
        if (id == null || id == 0) {
            layer.msg('请先选择要删除的数据', {icon: 2, time: 1000});
            return false;
        }
        $("#rightGrid").jqGrid('delRowData',id );
    }
    /*
    * 取消
    * */
    function Cancel(){
        var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
        parent.layer.close(index);
    }
    $(function(){
        pageInit();
        $("#profiletestForm").Validform({
            tiptype:4,
            btnSubmit:"#btn_sub",
            submitHandler: function(form) {
                save();
            }
        });

        //加载数据
        if(profileInfo){
            console.log(profileInfo);
            $('#profilecode').val(profileInfo.profileCode);
            $('#profilename').val(profileInfo.profileName);
            $('#profiledescribe').val(profileInfo.profileDescribe);
            $('#frequencytime').val(profileInfo.frequencyTime);
            $('#deviceid').val(profileInfo.deviceName);
            $('#device').val(profileInfo.deviceId);
            $('#sectionid').val(profileInfo.sectionName);
            $('#section').val(profileInfo.sectionId);
            $('#frequencytime').val(profileInfo.frequencyTime);
            $('#sampletype').val(profileInfo.sampleType);
            $('#usenow').val(profileInfo.useNow);

        $("#rightGrid").jqGrid('setGridParam',{
            datatype:'local',
            rowNum:profileInfo.indexs.length,
            data:profileInfo.indexs
        }).trigger('reloadGrid');//重新载入
        }

    });
    function pageInit(){
        $("#rightGrid").jqGrid( {
            datatype : "local",
           // rowNum:indexlist.length,
            height :350,
            width:$('.rightContent').width(),
            colNames : [ 'indexid', '英文缩写', '中文名称','操作'],
            colModel : [
                {name : 'indexid',index : 'indexid',width : 90,hidden:true},
                {name : 'english',index : 'english',width : 80},
                {name : 'name',index : 'name',width : 180,align : "left",sorttype : "text"},
                {name : 'Delete',index : 'Delete',width : 60}
            ],
            gridComplete: function () {
                var ids = jQuery("#rightGrid").jqGrid('getDataIDs');
                for (var i = 0; i < ids.length; i++) {
                    var id = ids[i];
                    var DeleteBtn = "<a href='#' style='color:#f60' onclick='Delete("+id+")' >删除</a>";
                    jQuery("#rightGrid").jqGrid('setRowData', ids[i], {Delete: DeleteBtn});
                }
            },
            //multiselect : true,
            shrinkToFit:false,
            scrollOffset:2,
            rownumbers: true, // 显示行号
            rownumWidth: 35
        });
    }


    /**
     * 搜索检验项目
     */
    $("#searchProject").autocomplete({
        source: function( request, response ) {
            $.ajax({
                url: "../ajax/searchTest",
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
            $('#rightGrid').saveRow(newId, false, 'clientArray');
            $("#searchProject").val('');
        }
    });

    /**
     * 搜索仪器
     */
    $("#deviceid").autocomplete({
        source: function( request, response ) {
            $.ajax({
                url: "../ajax/searchDevice",
                dataType: "json",
                data: {
                    name : request.term
                },
                success: function( data ) {
                    response( $.map( data, function( result ) {
                        return {
                            label: result.id + " : " + result.name,
                            value: result.name,
                            id : result.id
                        }
                    }));
                    $("#deviceid").removeClass("ui-autocomplete-loading");
                }
            });
        },
        minLength: 1,
        select : function(event, ui) {
            $("#device").val(ui.item.id);
        }
    });
    /**
     * 搜索科室
     */
    $("#sectionid").autocomplete({
        source: function( request, response ) {
            $.ajax({
                url: "../ajax/searchLab",
                dataType: "json",
                data: {
                    name : request.term
                },
                success: function( data ) {
                    response( $.map( data, function( result ) {
                        //console.log(result)
                        return {
                            label: result.code + " : " + result.name,
                            value: result.name,
                            code : result.code
                        }
                    }));
                    $("#sectionid").removeClass("ui-autocomplete-loading");
                }
            });
        },
        minLength: 1,
        select : function(event, ui) {

            $("#section").val(ui.item.code);
        }
    });


</script>

