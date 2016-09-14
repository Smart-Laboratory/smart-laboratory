<%--
  Created by IntelliJ IDEA.
  User: zhou
  Date: 2016/6/14
  Time: 11:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp" %>
<head>
    <script type="text/javascript" src="<c:url value="/scripts/jquery-2.1.4.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/jquery-ui.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/bootstrap.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/ace.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/ace-elements.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/bootstrap-tag.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/i18n/grid.locale-cn.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/jquery.form.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/jquery.jqGrid.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/layer/layer.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/validform/Validform.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/jquery.bootstrap-duallistbox.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/bootstrap-datetimepicker.min.js"/>"></script>

    <link rel="stylesheet" type="text/css"  href="<c:url value='/styles/ui.jqgrid.css'/>" />
    <link rel="stylesheet" type="text/css"  href="<c:url value='/styles/bootstrap.min.css'/>" />
    <link rel="stylesheet" type="text/css"  href="<c:url value='/styles/font-awesome.css'/>" />
    <link rel="stylesheet" type="text/css"  href="<c:url value='/styles/ace.min.css'/>" />
    <link rel="stylesheet" type="text/css"  href="<c:url value="/styles/font-awesome.css"/>" />
    <link rel="stylesheet" type="text/css"  href="<c:url value="/styles/bootstrap-datetimepicker.min.css"/>" />
    <!-- page specific plugin styles -->
    <link rel="stylesheet" type="text/css"  href="<c:url value="/styles/bootstrap-duallistbox.min.css"/>"/>
    <link rel="stylesheet" type="text/css"  href="<c:url value="/styles/ztree/zTreeStyle.css"/>" type="text/css">
    <link rel="stylesheet" type="text/css"  href="<c:url value="/styles/ruleLib.css"/>" type="text/css">
</head>
<style>
    body{
        overflow: hidden;
    }
    .tab-content{
        padding: 2px 12px;
        min-height: 510px;
    }
    .widget-toolbar{
        z-index: 999;
        line-height:34px;
    }
    .bootstrap-duallistbox-container .buttons{
        display: none;
    }
    .lazy_header .input-icon, .nav-search-input{
        width: 100%;
    }
    .form-horizontal .form-group{
        margin-right: 0px;
        margin-left: 0px;
    }
    div.treeList{
        overflow: auto;
        min-height: 510px;
        overflow: auto;
        border: 1px solid #eeeeee;
    }
    .info-container{
        display: none;
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
    #calcuator{width:210px; height:275px; padding:10px; border:1px solid #e5e5e5; background:#f8f8f8; -moz-border-radius: 3px; -webkit-border-radius: 3px; border-radius:3px; box-shadow:0px 0px 10px #f2f2f2; -moz-box-shadow:0px 0px 10px #f2f2f2; -webkit-box-shadow:0px 0px 10px #f2f2f2; margin: 0px auto; }
    #calcuator #input-box{ margin:0; width:187px; padding:9px 5px; height:14px;border:1px solid #e5e5e5; border-radius:3px; -webkit-border-radius:3px; -moz-border-radius:3px; background:#FFF; text-align:right; line-height:14px; font-size:12px; font-family:Verdana, Geneva, sans-serif; color:#666; outline:none;  text-transform:uppercase;}
    #calcuator #btn-list{ width:210px; overflow:hidden;}
    #calcuator #btn-list .btn-radius{ border-radius:2px; -webkit-border-radius:2px; -moz-border-radius:2px; border:1px solid #e5e5e5; background:-webkit-gradient(linear, 0 0, 0 100%, from(#f7f7f7), to(#ebebeb)); background:-moz-linear-gradient(top, #f7f7f7,#ebebeb);filter:progid:DXImageTransform.Microsoft.gradient(startColorstr=#f7f7f7,endColorstr=#ebebeb,grandientType=1); line-height:29px; text-align:center; text-shadow:0px 1px 1px #FFF; font-weight:bold; font-family:Verdana, Geneva, sans-serif; color:#666; float:left; margin-left:11px; margin-top:11px; font-size:12px; cursor:pointer;}
    #calcuator #btn-list .btn-radius:active{ background:#ffffff;}
    #calcuator #btn-list .clear-marginleft{ margin-left:0;}
    #calcuator #btn-list .font-14{ font-size:14px;}
    #calcuator #btn-list .color-red{ color:#ff5050}
    #calcuator #btn-list .color-blue{ color:#00b4ff}
    #calcuator #btn-list .btn-30{ width:29px; height:29px;}
    #calcuator #btn-list .btn-70{ width:70px; height:29px;}
    #calcuator #btn-list .btn-100{ width:110px; height:29px;}
</style>
<div class="main-container" id="content">
    <div class="row">
        <form id="addForm" class="form-horizontal" action="<%=request.getContextPath()%>/set/device/saveDevice" method="post">
            <div class="form-group">
                <div class="space-4"></div>
                <label class="col-xs-1 control-label no-padding-right" for="testname" > 名称 </label>
                <div class="col-xs-11">
                    <input type="text" id="testname" placeholder="名称" class="col-xs-12" value="${testName}"/>
                </div>
            </div>
            <div class="form-group">
                <div class="space-4"></div>
                <label class="col-xs-1 control-label no-padding-right" for="formula"> 计算公式 </label>
                <div class="col-xs-11">
                    <textarea id="formuladescribe" name="formuladescribe" class="col-xs-12" readonly >${calculateFormula.formulaDescribe}</textarea>
                </div>
            </div>
            <input type="hidden" name="testid" id="testid" value="${calculateFormula.testId}">
            <input type="hidden" name="formula" id="formula" value="${calculateFormula.formula}">
            <input type="hidden" name="formulaitem" id="formulaitem" value="${calculateFormula.formulaItem}">
            <input type="hidden" name="testnumb" id="testnumb" value="${calculateFormula.testNumb}">
        </form>
    </div>
    <div class="row">
        <div class="col-xs-8" id="leftContent">
            <table id="leftGrid"></table>
            <div id="leftPager"></div>
        </div>
        <div class="col-xs-4">
            <div id="calcuator">
                <div id="btn-list">
                    <div onclick="Calculate.operator('clear')" title="清除" class=" btn-30 btn-radius color-red clear-marginleft">C</div>
                    <div onclick="Calculate.typetoinput('%')" class=" btn-30 btn-radius color-blue">%</div>
                    <div onclick="Calculate.operator('backspace')" title="退格" class="btn-100 btn-radius color-red font-14">←</div>
                    <div onclick="Calculate.typetoinput('7')" class=" btn-30 btn-radius clear-marginleft">7</div>
                    <div onclick="Calculate.typetoinput('8')" class=" btn-30 btn-radius">8</div>
                    <div onclick="Calculate.typetoinput('9')" class=" btn-30 btn-radius">9</div>
                    <div onclick="Calculate.typetoinput('+')" class=" btn-30 btn-radius color-blue font-14">+</div>
                    <div onclick="Calculate.typetoinput('-')" class=" btn-30 btn-radius color-blue font-14">-</div>
                    <div onclick="Calculate.typetoinput('4')" class=" btn-30 btn-radius clear-marginleft">4</div>
                    <div onclick="Calculate.typetoinput('5')" class=" btn-30 btn-radius">5</div>
                    <div onclick="Calculate.typetoinput('6')" class=" btn-30 btn-radius">6</div>
                    <div onclick="Calculate.typetoinput('*')" class=" btn-30 btn-radius color-blue font-14">×</div>
                    <div onclick="Calculate.typetoinput('/')" class=" btn-30 btn-radius color-blue font-12">÷</div>
                    <div onclick="Calculate.typetoinput('1')" class=" btn-30 btn-radius clear-marginleft">1</div>
                    <div onclick="Calculate.typetoinput('2')" class=" btn-30 btn-radius">2</div>
                    <div onclick="Calculate.typetoinput('3')" class=" btn-30 btn-radius">3</div>
                    <div onclick="Calculate.typetoinput('(')" class=" btn-30 btn-radius color-blue font-14">(</div>
                    <div onclick="Calculate.typetoinput(')')" class=" btn-30 btn-radius color-blue font-12">)</div>
                    <div onclick="Calculate.typetoinput('0')" class=" btn-70 btn-radius clear-marginleft">0</div>
                    <div onclick="Calculate.typetoinput('.')" class=" btn-30 btn-radius">.</div>
                    <div onclick="Calculate.operator('result')" class=" btn-70 btn-radius color-red font-14">=</div>
                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="space-4"></div>
        <div class="layui-layer-btn">
            <a class="layui-layer-btn0" onclick="jQuery.Custom.Save()">保存</a>
            <a class="layui-layer-btn1" onclick="jQuery.Custom.Cancel()">取消</a>
        </div>
    </div>
</div>
<textarea id="jsFormula" style="display: none">${formula}</textarea>
<script>

    jQuery.Custom =(function(){
        var public ={
            indexGrid:$("#leftGrid"),
            updatePagerIcons:function(table){
                var replacement ={
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
            },
            initGrid:function(){
                $(window).on('resize.jqGrid', function () {
                    public.indexGrid.jqGrid('setGridWidth', $("#leftContent").width(),false);
                });
                public.indexGrid.jqGrid({
                    url: '<%=request.getContextPath()%>/set/devicerelationlist/getList',
                    datatype: "json",
                    height: 200,
                    //shrinkToFit: false,
                    regional : 'cn',
                    autoWidth:true,
                    /*width: $('.leftContent').width(),*/
                    colNames: ['id', '试验编号',  '中文名称','样本类型'],
                    colModel: [
                        {name: 'id', index: 'id', width: 100,  hidden: true},
                        {name: 'indexid', index: 'index_id', width: 80,sorttype:'text',sortable:true},
                        {name: 'name', index: 'name', width: 300},
                        {name: 'sampletype', index: 'sampletype', width: 80}
                    ],
                    loadComplete: function () {
                        var table = this;
                        setTimeout(function () {
                            public.updatePagerIcons(table);
                        }, 0);
                    },
                    onSelectRow: function (id) {
                        // var rowData = $("#rightGrid").jqGrid('getRowData',id);
                    },
                    ondblClickRow: function (id) {
                        var rowData = public.indexGrid.jqGrid('getRowData',id);
                        Calculate.typetoinput(rowData.indexid+"["+rowData.sampletype,'1',rowData.name);
                    },
                    //multiselect : true,
                    altRows:true,
                    rowNum: 10,
                    //viewrecords: true,
                    rowList: [10, 20, 40],
                    rownumbers: true, // 显示行号
                    rownumWidth: 35, // the width of the row numbers columns
                    pager: "#leftPager"
                });
                $('#leftPager_left').append('<label><input type="search" id="query" style="display:inline;height: 25px;width: 100px;" class="form-control input-sm" placeholder="输入名称" aria-controls="dynamic-table"></label>');
                $(window).triggerHandler('resize.jqGrid');
            },
             search:function(){
                var query = $('#query').val()||'';
                public.indexGrid.jqGrid('setGridParam',{
                    url: "<%=request.getContextPath()%>/set/devicerelationlist/getList",
                    datatype : 'json',
                    postData : {"query":query },
                    page : 1
                }).trigger('reloadGrid');//重新载入
            },
            /*保存数据*/
            Save:function(){
                $('#formula').val(Calculate.formula.join(""));
                $('#testnumb').val(Calculate.formulaIem.length);
                $('#formulaitem').val(Calculate.formulaIem.join(","));
                $.ajax({
                    url:'<%=request.getContextPath()%>/set/calculateformula/saveInfo',
                    dataType:'json',
                    type:'post',
                    data:$('#addForm').serialize(),
                    success:function(data){
                        if(data && data.result =='true'){
                            layer.msg("数据保存成功!",{time:1000});
                        }
                    }
                })
            },
            Cancel:function(){
                var index = parent.layer.getFrameIndex(window.name);
                parent.layer.close(index);
            }
        };
        return public;
    })();

    $(function(){
        jQuery.Custom.initGrid();
        $("#query").keypress(function(e){
            if (e.keyCode == 13){
                jQuery.Custom.search();
            }
        });
    });

    $("#testname").autocomplete({
        source: function( request, response ) {
            $.ajax({
                url: "<%=request.getContextPath()%>/ajax/searchTest",
                dataType: "json",
                data: {
                    name : request.term
                },
                success: function( data ) {
                    response( $.map( data, function( result ) {
                        return {
                            label: result.id + " : " + result.ab + " : " + result.name,
                            value: result.name,
                            id : result.id
                        }
                    }));
                    $("#testname").removeClass("ui-autocomplete-loading");
                }
            });
        },
        minLength: 1,
        select : function(event, ui) {
            $("#testid").val(ui.item.id);
        }
    });

    //计算器
    var Calculate = (function(){
        var  jsFormula=($('#jsFormula').val()=='')?'': eval("("+$('#jsFormula').val()+")");
        var public ={
            _string:[],
            _type:null,
            formula:(jsFormula.formula && jsFormula.formula.length>0)?jsFormula.formula:[],
            formulaDescribe:(jsFormula.formulaDescribe && jsFormula.formulaDescribe.length>0)?jsFormula.formulaDescribe:[],
            formulaIem:(jsFormula.formulaIem && jsFormula.formulaIem.length>0)?jsFormula.formulaIem:[],
            typetoinput: function (num,type,showname) {
                if(type && type=='1'){
                    $('#formuladescribe').text($('#formuladescribe').text()+showname);
                    public.formulaDescribe.push(showname);
                    public.formulaIem.push(num);
                }else{
                    $('#formuladescribe').text($('#formuladescribe').text()+num);
                    public.formulaDescribe.push(num);
                }
                public.formula.push(num);
                //public.formula += num;
            },
            replacename:function(tag,value){
                if(tag && tag=='' ) return value;
                tag = tag.replace('\*', '\\*');
                tag = tag.replace('\(', '\\(');
                tag = tag.replace('\)', '\\)');
                tag = tag.replace('\+', '\\+');
                tag = tag.replace('\/', '\\/');
                tag = tag.replace('\[', '\\[');
                var re = eval("/" + tag + "$/");
                var tmpVal = value.replace(re, '');
                return tmpVal;
            },
            operator:function(type){
                switch (type) {
                    case "clear":
                        public.formula = [];
                        public.formulaDescribe = [];
                        public.formulaIem = [];
                        $('#formuladescribe').text('');
                        break;
                    case "backspace":
                        var lastNum = public.formula[public.formula.length - 1] ||'';
                        //public.formula = public.replacename(lastNum,public.formula);
                        var textFormula = $('#formuladescribe').text();
                        var lastShowName = public.formulaDescribe[public.formulaDescribe.length - 1] ||'';
                        textFormula = public.replacename(lastShowName,textFormula);
                        $('#formuladescribe').text(textFormula);
                        if(lastNum.length>1 && lastNum.indexOf("[")>=0){
                            public.formulaIem.pop();
                        }
                        public.formula.pop();
                        public.formulaDescribe.pop();
                        break;
                    case "result":
                        console.log(public.formula);
                        console.log(public.formulaDescribe);
                        console.log(public.formulaIem);
                        break;
                }
            }
        };
        return public;
    })();
    // JavaScript Document
    //window.document.onkeydown = disableRefresh;
//    function disableRefresh(evt){
//        evt = (evt) ? evt : window.event
//        if (evt.keyCode)
//        {
//            if(evt.keyCode == 13){operator('result')}
//            else if(evt.keyCode == 8){input.focus();window.event.returnValue = false;operator('backspace')}
//            else if(evt.keyCode == 27){operator('clear')}
//            else if(evt.keyCode == 48){typetoinput('0')}
//            else if(evt.keyCode == 49){typetoinput('1')}
//            else if(evt.keyCode == 50){typetoinput('2')}
//            else if(evt.keyCode == 51){typetoinput('3')}
//            else if(evt.keyCode == 52){typetoinput('4')}
//            else if(evt.keyCode == 53){typetoinput('5')}
//            else if(evt.keyCode == 54){typetoinput('6')}
//            else if(evt.keyCode == 55){typetoinput('7')}
//            else if(evt.keyCode == 56){typetoinput('8')}
//            else if(evt.keyCode == 57){typetoinput('9')}
//            else if(evt.keyCode == 96){typetoinput('0')}
//            else if(evt.keyCode == 97){typetoinput('1')}
//            else if(evt.keyCode == 98){typetoinput('2')}
//            else if(evt.keyCode == 99){typetoinput('3')}
//            else if(evt.keyCode == 100){typetoinput('4')}
//            else if(evt.keyCode == 101){typetoinput('5')}
//            else if(evt.keyCode == 102){typetoinput('6')}
//            else if(evt.keyCode == 103){typetoinput('7')}
//            else if(evt.keyCode == 104){typetoinput('8')}
//            else if(evt.keyCode == 105){typetoinput('9')}
//            else if(evt.keyCode == 110){typetoinput('.')}
//            else if(evt.keyCode == 106){operator('multiply')}
//            else if(evt.keyCode == 107){operator('plus')}
//            else if(evt.keyCode == 111){operator('divide')}
//            else if(evt.keyCode == 109){operator('minus')}
//        }
//    }
</script>
