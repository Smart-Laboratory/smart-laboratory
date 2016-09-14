<%--
  Created by IntelliJ IDEA.
  User: zhou
  Date: 2016/5/31
  Time: 10:44
  Description:指标编辑表单
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

</head>
<style>
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
</style>
<div class="main-container" id="content">
    <div class="row">
        <div class="space-4"></div>
        <div class="col-xs-12">
            <div class="widget-toolbar no-border">
                <button class="btn btn-xs btn-info" onclick="$.Custom.save()">
                    <i class="ace-icon fa fa-floppy-o bigger-120"></i>
                    保存
                </button>
            </div>

            <div class="tabbable">
                <ul class="nav nav-tabs" id="myTab">
                    <li class="active">
                        <a data-toggle="tab" href="#activeinfo">
                            <i class="blue ace-icon fa  fa-home bigger-120"></i>
                            常用信息
                        </a>
                    </li>
                    <li>
                        <a data-toggle="tab" href="#inactiveinfo">
                            <i class="pink ace-icon fa fa-tachometer bigger-120"></i>
                            不常用信息
                        </a>
                    </li>
                    <li >
                        <a data-toggle="tab" href="#relation">
                            <i class="green ace-icon fa fa-cogs bigger-120"></i>
                            测定仪器/部门选择
                        </a>
                    </li>
                    <li>
                        <a data-toggle="tab" href="#reference">
                            <i class="purple ace-icon fa  fa-comments bigger-120"></i>
                            参考范围
                        </a>
                    </li>
                    <li>
                        <a data-toggle="tab" href="#dictionaries">
                            <i class="orange ace-icon fa  fa-book bigger-120"></i>
                            项目字典
                        </a>
                    </li>
                </ul>
                <form class="form-horizontal" name="infoForm" id="infoForm"  action="<%=request.getContextPath()%>/set/devicerelation/saveInfo">
                <div class="tab-content">
                    <!--常用信息start-->

                    <div id="activeinfo" class="tab-pane fade in active">
                        <div class="row">
                            <div class="col-xs-12">
                                    <input type="hidden" name="id" id="id" value="${index.id}">
                                    <fieldset>
                                        <div class="space-10"></div>
                                        <div class="form-group controls controls-row">
                                            <label class="col-sm-1 control-label" for="indexid">试验编号</label>
                                            <div class="col-sm-5">
                                                <input class="form-control" id="indexid" name=indexid type="text" placeholder="试验编号" value="${index.indexId}" datatype="*" nullmsg="编号不能为空"/>
                                            </div>
                                            <label class="col-sm-1 control-label" for="testclass">分类代码</label>
                                            <div class="col-sm-5">
                                                <input class="form-control" id="testclass" name="testclass" type="text" value="${index.testClass}" placeholder="分类代码"/>
                                            </div>
                                        </div>
                                        <div class="form-group controls controls-row">
                                            <label class="col-sm-1 control-label" for="name">中文名称</label>
                                            <div class="col-sm-5">
                                                <input class="form-control" id="name" name="name" type="text"value="${index.name}"  placeholder="中文名称" datatype="*" nullmsg="名称不能为空"/>
                                            </div>
                                            <label class="col-sm-1 control-label" for="eglish">英文名称</label>
                                            <div class="col-sm-5">
                                                <input class="form-control" id="eglish" name="eglish" type="text" value="${index.english}"placeholder="英文名称"/>
                                            </div>
                                        </div>

                                        <div class="form-group controls controls-row">
                                            <label class="col-sm-1 control-label" for="samplefrom">样本类型</label>
                                            <div class="col-sm-5">
                                                <input type="hidden" id="hiddenSamplefrom" name="hiddenSamplefrom" value="${index.sampleFrom}"/>
                                                <input type="text" id="samplefrom" name="samplefrom" placeholder="样本类型" style="width:100%" value="${sampleType}"/>
                                            </div>
                                            <%--<div class="col-sm-5">
                                                <select class="form-control" id="samplefrom" name="samplefrom" placeholder="样本类型" >
                                                    <c:forEach items="${samplelist}" var="entry">
                                                        <option value="<c:out value="${entry.key}" />" <c:choose>
                                                            <c:when test="${entry.key==index.sampleFrom}">
                                                                selected
                                                            </c:when>
                                                            <c:otherwise></c:otherwise></c:choose>>
                                                            <c:out value="${entry.value}" /></option>
                                                    </c:forEach>
                                                </select>
                                            </div>--%>
                                            <label class="col-sm-1 control-label" for="labdepartment">检验部门</label>
                                            <div class="col-sm-5">
                                                <input class="form-control" id="labdepartmentshow"  name="labdepartmentshow" type="text"  value="${index.labdepartment}" placeholder="检验部门" />
                                                <input class="form-control" id="labdepartment"  name="labdepartment" type="hidden"  value="${index.labdepartment}" placeholder="检验部门"/>
                                            </div>
                                        </div>
                                        <div class="form-group controls controls-row">
                                            <label class="col-sm-1 control-label" for="type">指标类型</label>
                                            <div class="col-sm-5">
                                                <select id="type" name="type" class="form-control">
                                                    <option value="S">字符型</option>
                                                    <option value="N" selected>数值型</option>
                                                    <option value="E">枚举型</option>
                                                </select>
                                            </div>
                                            <label class="col-sm-1 control-label" for="algorithm">差值算法</label>
                                            <div class="col-sm-5">
                                                <select id="algorithm" name="algorithm" class="form-control">
                                                    <option value="2" <c:if test="${index.diffAlgo=='2'}">selected</c:if>>差值百分率</option>
                                                    <option value="1" <c:if test="${index.diffAlgo=='1'}">selected</c:if>>差值</option>
                                                    <option value="3" <c:if test="${index.diffAlgo=='3'}">selected</c:if>>差值变化</option>
                                                    <option value="4" <c:if test="${index.diffAlgo=='4'}">selected</c:if>>差值变化率</option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="form-group controls controls-row">
                                            <label class="col-sm-1 control-label" for="printord">打印顺序</label>
                                            <div class="col-sm-5">
                                                <input class="form-control" id="printord" name="printord" value="${index.printord}" type="text" placeholder="打印顺序"/>
                                            </div>
                                            <label class="col-sm-1 control-label" for="description">指标说明</label>
                                            <div class="col-sm-5">
                                                <input class="form-control" id="description"  name="description" value="${index.description}" type="text" placeholder="指标说明" />
                                            </div>
                                        </div>
                                        <div class="form-group controls controls-row">
                                            <label class="col-sm-1 control-label" for="guide">指南</label>
                                            <div class="col-sm-5">
                                                <input class="form-control" id="guide" name="guide" type="text" value="${index.guide}" placeholder="指南"/>
                                            </div>
                                            <label class="col-sm-1 control-label"  style="padding-left: 2px;">显示历史记录</label>
                                            <div class="col-sm-5">
                                                <label class="control-label pull-left inline">
                                                    <input id="needhistory" type="checkbox" <c:if test="${index.needhistory=='1'}">checked</c:if> name="needhistory" value="1" class="ace ace-switch ace-switch-3">
                                                    <span class="lbl middle"></span>
                                                </label>
                                            </div>
                                        </div>
                                        <div class="form-group controls controls-row">
                                            <label class="col-sm-1 control-label" for="unit">单位</label>
                                            <div class="col-sm-5">
                                                <input class="form-control" id="unit" name="unit" type="text" value="${index.unit}" placeholder="单位"/>
                                            </div>
                                            <label class="col-sm-1 control-label" for="defaultvalue">默认结果</label>
                                            <div class="col-sm-5">
                                                <input class="form-control" id="defaultvalue" name="defaultvalue"  value="${index.defaultvalue}" type="text" placeholder="默认结果"/>
                                            </div>
                                        </div>


                                        <div class="form-group controls controls-row">
                                            <label class="col-xs-2 col-sm-1 control-label">质控需要</label>
                                            <div class="col-xs-12 col-sm-5">
                                                <label class="radio-inline">
                                                    <input name="qcneed" type="radio" class="ace" <c:if test="${index.qcNeed=='0'}">checked</c:if> value="0">
                                                    <span class="lbl"> 不需</span>
                                                </label>
                                                <label class="radio-inline">
                                                    <input name="qcneed" type="radio" class="ace"   <c:if test="${index.qcNeed=='1'}">checked</c:if> value="1">
                                                    <span class="lbl"> 1个</span>
                                                </label>
                                                <label class="radio-inline">
                                                    <input name="qcneed" type="radio" class="ace"   <c:if test="${index.qcNeed=='2'}">checked</c:if> value="2">
                                                    <span class="lbl"> 2个</span>
                                                </label>
                                                <label class="radio-inline">
                                                    <input name="qcneed" type="radio" class="ace"  <c:if test="${index.qcNeed=='3'}">checked</c:if> value="3">
                                                    <span class="lbl"> 3个</span>
                                                </label>
                                            </div>
                                            <label class="col-sm-1 control-label" for="tea">TEa</label>
                                            <div class="col-xs-12 col-sm-5">
                                                <input type="text" class=" col-sm-5" name="tea" id="tea" value="${index.TEA}"/>
                                                <label class="col-sm-2 control-label right" for="ccv">CCV</label>
                                                <input type="text" class=" col-sm-5"  name="ccv" id="ccv" value="${index.CCV}"/>
                                            </div>
                                        </div>
                                        <div class="form-group controls controls-row">
                                            <label class="col-sm-1 control-label" for="outdate">失效日期</label>
                                            <div class="col-sm-5">
                                                <div class="input-group">
                                                    <input id="outdate" type="text"  name="outdate" value="${index.outDate}" class="form-control" />
                                                <span class="input-group-addon">
                                                    <i class="fa fa-calendar bigger-110"></i>
                                                </span>
                                                </div>
                                            </div>
                                            <label class="col-sm-1 control-label" for="inuredate">生效日期</label>
                                            <div class="col-sm-5">
                                                <input class="form-control" id="inuredate" type="text" value="${index.inureDate}" placeholder="生效日期" disabled/>
                                            </div>
                                        </div>
                                    </fieldset>

                            </div>
                        </div>
                    </div><!--常用信息end-->
                    <!--不常用信息start-->
                    <div id="inactiveinfo" class="tab-pane fade">
                        <div class="row">
                            <div class="col-xs-12">

                                    <fieldset>
                                        <div class="space-10"></div>
                                        <div class="form-group controls controls-row">
                                            <label class="col-sm-1 control-label" for="name2">中文名称</label>
                                            <div class="col-sm-11">
                                                <input  type="text" class="col-sm-12" id="name2" value="${index.name}" disabled>
                                            </div>
                                            <%--<div class="col-sm-6 controls controls-row">--%>
                                            <%--<label class="control-label" for="name">试验名称</label>--%>
                                            <%--<input class="span1" type="text" placeholder="name">--%>

                                            <%--<label class="control-label pull-right inline">--%>
                                            <%--<span class="muted">是否显示加粗:</span>--%>
                                            <%--<input id="id-button-borders" checked="checked" type="checkbox" class="ace ace-switch ace-switch-3">--%>
                                            <%--<span class="lbl middle"></span>--%>
                                            <%--</label>--%>
                                            <%--</div>--%>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-1 control-label" for="principle">测定原理</label>
                                            <div class="col-sm-11">
                                                <textarea class="form-control textarea" id="principle" name="principle" placeholder="测定原理">${index.principle}</textarea>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-1 control-label" for="methodname">方法名称</label>
                                            <div class="col-sm-11">
                                                <input class="form-control textarea" id="methodname" name="methodname" value="${index.methodName}" type="text" placeholder="方法名称"/>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-1 control-label" for="workcriterion">操作规范</label>
                                            <div class="col-sm-11">
                                                <textarea class="form-control textarea" id="workcriterion" name="workcriterion" value="${index.workCriterion}" placeholder="操作规范"></textarea>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-1 control-label" for="increasedhint">升高意义</label>
                                            <div class="col-sm-11">
                                                <textarea class="form-control textarea" id="increasedhint" name="increasedhint"  placeholder="升高意义">${index.increasedHint}</textarea>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-1 control-label" for="decreasedhint"> 降低意义</label>
                                            <div class="col-sm-11">
                                                <textarea class="form-control textarea" id="decreasedhint" name="decreasedhint"  placeholder="降低意义">${index.decreasedHint}</textarea>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-1 control-label" for="notes">注意事项</label>
                                            <div class="col-sm-11">
                                                <textarea class="form-control textarea" id="notes" name="notes"  placeholder="注意事项">${index.notes}</textarea>
                                            </div>
                                        </div>
                                    </fieldset>

                            </div>
                        </div>
                    </div><!--不常用信息end-->
                    <!--仪器/部门选择start-->
                    <div id="relation" class="tab-pane fade">
                        <div class="row">
                            <div class="col-xs-12">
                                <h4 class="widget-title lighter">
                                    <i class="ace-icon fa fa-star orange"></i>
                                    仪器选择
                                </h4>
                                <div class="col-xs-12">
                                    <div class="form-group">
                                        <select multiple="multiple" size="8" name="devicelist[]" id="devicelist">
                                        </select>
                                        <!--div class="hr hr-16 hr-dotted"></div-->
                                    </div>
                                </div>
                                <h4 class="widget-title lighter">
                                    <i class="ace-icon fa fa-star orange"></i>
                                    部门选择
                                </h4>
                                <div class="col-xs-12">
                                    <div class="form-group">
                                        <select multiple="multiple" size="8" name="departlist[]" id="departlist"></select>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div><!--仪器/部门选择end-->
                    <!--参考范围start-->
                    <div id="reference" class="tab-pane fade">
                        <div class="row">
                            <div class="col-xs-12">
                                <table id="tableList"></table>
                                <div id="pager"></div>
                            </div>
                        </div>
                    </div><!--参考范围end-->
                    <!--项目字典start-->
                    <div id="dictionaries" class="tab-pane fade">
                        <div class="row">
                            <div class="col-xs-12">
                                <table id="dicTable"></table>
                                <div id="dicPager"></div>
                            </div>
                        </div>
                    </div><!--项目字典end-->
                </div>
                    <textarea style="display: none" name="datas" id="datas"></textarea>
                    <textarea style="display: none" name="dictionariesData" id="dictionariesData">${dictionaries}</textarea>
                </form>
            </div>
        </div>
    </div>
</div>
<input type="hidden" name="instrumentData" id="instrumentData" value="${index.instrument}">
<textarea style="display: none" name="departListData" id="departListData">${departlist}</textarea>
<textarea style="display: none" name="deviceListData" id="deviceListData">${devicelist}</textarea>
<textarea style="display: none" name="referencesData" id="referencesData">${references}</textarea>
<input type="hidden" name="index_Id" id="index_Id" value="${index.id}">
<input type="hidden" name="action_Method" id="action_Method" value="${method}">


<script type="text/javascript">
   jQuery.Custom = (function(){
        var cache = {
            departlist :function(){
                var str = $('#departListData').val()||'';
                if(str !='')
                   return eval("("+str+")");
                else
                    return null;
            },
            devicelist : function(){
                var str = $('#deviceListData').val()||'';
                if(str != '')
                    return eval("("+str+")");
                else
                    return null;
            },
            references : function(){
                var str = $('#referencesData').val()||'';
                if(str != '')
                    return eval("("+str+")");
                else
                    return null;
            },
            dictionaries: function(){
                var str = $('#dictionariesData').val()||'';
                if(str != '')
                    return eval("("+str+")");
                else
                    return null;
            },
            method : $('#action_Method').val()||'',
            indexid : $('#index_Id').val()||'',
            refTable:$('#tableList'),                       //参考范围列表
            dicTable:$('#dicTable'),                        //项目字典列表
            saveUrl:'<%=request.getContextPath()%>/set/devicerelationlist/saveInfo',
            lastsel:'0',
            lastsel2:'0'
        };
        var public = {
            getMethod:function(){
                return cache.method;
            },
            /***数组转JSON****/
            arrayToJson:function(o){
                var r = [];
                if (typeof o == "string") return "\"" + o.replace(/([\'\"\\])/g, "\\$1").replace(/(\n)/g, "\\n").replace(/(\r)/g, "\\r").replace(/(\t)/g, "\\t") + "\"";
                if (typeof o == "object") {
                    if (!o.sort) {
                        for (var i in o)
                            r.push(i + ":" + public.arrayToJson(o[i]));
                        if (!!document.all && !/^\n?function\s*toString\(\)\s*\{\n?\s*\[native code\]\n?\s*\}\n?\s*$/.test(o.toString)) {
                            r.push("toString:" + o.toString.toString());
                        }
                        r = "{" + r.join() + "}";
                    } else {
                        for (var i = 0; i < o.length; i++) {
                            r.push(public.arrayToJson(o[i]));
                        }
                        r = "[" + r.join() + "]";
                    }
                    return r;
                }
                return o.toString();
            },
            /***保存数据****/
            save:function(){
                //检测必填项
                if($('#indexid').val() ==''){
                    layer.msg("编号不能为空,请输入！",{icon:2,time:1000});
                    return false;
                }
                if($('#name').val() ==''){
                    layer.msg("中文名称不能为空,请输入！",{icon:2,time:1000});
                    return false;
                }
                if($('#unit').val() ==''){
                    layer.msg("单位不能为空,请输入！",{icon:2,time:1000});
                    return false;
                }
                if($('#samplefrom').val() ==''){
                    layer.msg("样本类型不能为空,请输入！",{icon:2,time:1000});
                    return false;
                }
                if($('#labdepartment').val() =='' && $('select[name="departlist[]"]').val() ==''){
                    layer.msg("部门不能为空,请输入！",{icon:2,time:1000});
                    return false;
                }
                //仪器部门信息
                var otherParam = {};
                var department = $('select[name="departlist[]"]').val() || '';
                var instrument = $('select[name="devicelist[]"]').val() || '';
                department += (department == "" ? "" : ",");
                instrument += (instrument == "" ? "" : ",");
                otherParam.id = cache.indexid;
                otherParam.department=department;
                otherParam.instrument=instrument;
                //参考范围
                cache.refTable.saveRow(cache.lastsel, false, 'clientArray');
                var rowData = cache.refTable.jqGrid("getRowData");
                var datas = [];
                $(rowData).each(function(){
                    var obj = {};
                    obj.testid = this.testid;
                    obj.deviceid = this.deviceid;
                    obj.sex= this.sex;
                    obj.ageLow= this.ageLow;
                    obj.ageLowUnit = this.ageLowUnit;
                    obj.ageHigh = this.ageHigh;
                    obj.ageHighUnit = this.ageHighUnit;
                    obj.orderno= this.orderno;
                    obj.sampletype = $("#hiddenSamplefrom").val();
                    obj.direct = this.direct;
                    obj.reference = this.reference;
                    datas.push(obj);
                });
                datas = public.arrayToJson(datas);
                $('#datas').text(datas);

                //项目字典
                cache.dicTable.saveRow(cache.lastsel2, false, 'clientArray');
                var dicData = cache.dicTable.jqGrid("getRowData");
                var dicDatas ='';

                $(dicData).each(function(){
                    if(dicDatas != '')dicDatas +=";";
                    dicDatas += this.textkey +":"+ this.textvalue;
                });
                console.log(dicDatas);
                //dicDatas = public.arrayToJson(dicDatas);
                $('#dictionariesData').text(dicDatas);
                //console.log(dicDatas);
                //return false;
                $.ajax({
                    url: cache.saveUrl,
                    type: 'POST',
                    dataType: "json",
                    data:$('#infoForm').serialize() + "&" +$.param(otherParam) ,
                    success: function (data) {
                        if (data && data.result == 'true')
                            layer.msg("数据保存成功",{icon:1,time:1000});
                        setTimeout(function(){
                            var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
                            parent.layer.close(index);
                        },1000)
                    }
                })
            },
            /***初始化部门仪器选择列表****/
            initDualListbox:function(){
                //仪器设备
                var deviceListbox = $('select[name="devicelist[]"]').bootstrapDualListbox({
                    infoText: false,
                    filterTextClear:"",
                    filterPlaceHolder: '过滤',
                    selectorMinimalHeight: 150,
                    preserveSelectionOnMove: false
                });
                var deviceCcontainer = deviceListbox.bootstrapDualListbox('getContainer');
                deviceCcontainer.find('.btn').addClass('btn-white btn-info btn-bold');
                //部门
                var departListbox = $('select[name="departlist[]"]').bootstrapDualListbox({
                    infoText: false,
                    filterTextClear:"",
                    filterPlaceHolder: '过滤',
                    selectorMinimalHeight: 150
                });
                var departCcontainer = departListbox.bootstrapDualListbox('getContainer');
                departCcontainer.find('.btn').addClass('btn-white btn-info btn-bold');
            },
            /***加载部门仪器选择列表数据****/
            loadDualListbox:function(){
                var labdepartment = $('#labdepartment').val()|| '';         //已选部门信息
                var instrument = $('#instrumentData').val()||'';           //已选仪器信息
                var selectDev = $('#devicelist');
                selectDev.empty();
                var devicelist = cache.devicelist();
                for (var k in devicelist) {
                    var option = document.createElement("option");
                    option.value = k;
                    option.text = devicelist[k];
                    if ((instrument.toString()).indexOf(k + ",") >= 0) {
                        option.selected = 'selected';
                    }
                    selectDev[0].options.add(option);
                }
                //加载部门
                var selectDep = $('#departlist');
                selectDep.empty();
                var departlist = cache.departlist();
                for (var k in departlist) {
                    var option = document.createElement("option");
                    option.value = k;
                    option.text = departlist[k];
                    if ((labdepartment.toString()).indexOf(k + ",") >= 0) {
                        option.selected = 'selected';
                    }
                    selectDep[0].options.add(option);
                }
                $('select[name="departlist[]"]').bootstrapDualListbox("refresh");
                $('select[name="devicelist[]"]').bootstrapDualListbox("refresh");
            },
            /***初始化参考范围列表****/
            initGrid:function(){
                var references= cache.references();
                cache.refTable.jqGrid({
                    caption: "参考范围设置",
                    datatype:'local',
                    rowNum:references.length,
                    data:references,
                    colNames: ['testid', '项目名称', '标本类型', '性别', '年龄低限','低限单位','年龄高限','高限单位', '序号', '设备ID', '周期', '参考范围值'],
                    colModel: [
                        {name: 'testid', index: 'testid', width: 60, hidden: true},
                        {name: 'testname', index: 'testname', width: 200},
                        {name: 'sampletype', index: 'sampletype', width: 100},
                        {name: 'sex',index: 'sex',width: 60,editable: true,formatter: "select",edittype: "select",editoptions: {value: "0:男;1:女;2:其他"}},
                        {name: 'ageLow', index: 'ageLow', width: 60, editable: true},
                        {name: 'ageLowUnit', index: 'ageLowUnit', width: 60, editable: true,formatter: "select", edittype: "select", editoptions: {value: "岁:岁;月:月;周:周;天:天"}},
                        {name: 'ageHigh', index: 'ageHigh', width: 60, editable: true},
                        {name: 'ageHighUnit', index: 'ageHighUnit', width: 60, editable: true,formatter: "select", edittype: "select", editoptions: {value: "岁:岁;月:月;周:周;天:天"}},
                        {name: 'orderno', index: 'orderno', width: 60, editable: true},
                        {name: 'deviceid', index: 'deviceid', width: 60, editable: true},
                        {name: 'direct', index: 'direct', width: 60, editable: true},
                        {name: 'reference', index: 'reference', width: 120, editable: true}
                    ],
                    onSelectRow: function (id) {
                        if (id && id !== cache.lastsel) {
                            cache.refTable.saveRow(cache.lastsel, false, 'clientArray');
                            cache.lastsel = id;
                        }
                        cache.refTable.editRow(id, false);
                    },
                    repeatitems: false,
                    viewrecords: true,
                    autowidth: true,
                    altRows: true,
                    height: 300,
                    rownumbers: true, // 显示行号
                    rownumWidth: 35
                });
                //增加工具栏按钮
                var setting = $('<span class="widget-toolbar" style="right:20px;top:4px"><button type="button" id="add" class="btn btn-xs btn-success" data-toggle="button"> <i class="ace-icon fa fa-plus"></i>增加</button>| <button type="button" id="delete" class="btn btn-xs btn-success" data-toggle="button"><i class="ace-icon fa fa-times"></i>删除</button></span>');
                $('#reference').find('#gview_tableList .ui-jqgrid-titlebar').append(setting);
                cache.refTable.jqGrid('setGridWidth', $(".rightContent").width(),false);
                $("#add").click(function (){
                    //增加表格行
                    var ids = cache.refTable.jqGrid('getDataIDs');
                    var newId = parseInt(ids[ids.length - 1] || 0) + 1;
                    var rowData = {
                        testid:$('#indexid').val()||'',
                        testname: $('#name').val()||'',
                        sampletype: $('#hiddenSamplefrom').val(),
                        sex: "0",
                        ageLow: "0",
                        ageLowUnit:'岁',
                        ageHigh: "120",
                        ageHighUnit:'岁',
                        orderno: "0",
                        deviceid: "",
                        direct: "0",
                        reference: ""
                    };
                    cache.refTable.jqGrid('addRowData', newId, rowData);
                });
                //删除表格数据
                $('#delete').bind('click', function () {
                    var id = cache.refTable.jqGrid('getGridParam', 'selrow');
                    if (id == null || id == 0) {
                        layer.msg('请先选择要删除的数据', {icon: 2, time: 1000});
                        return false;
                    }
                    layer.confirm('确定删除选择数据？', {icon: 2, title: '警告'}, function (index) {
                        var rowData = cache.refTable.jqGrid('getRowData',id);
                        var sex= $('#'+id + "_sex").val();
                        var orderno= $('#'+id + "_orderno").val();      //编辑状态数据获取
                        $.post('<%=request.getContextPath()%>/set/devicerelationlist/deleteReference',{testid:rowData.testid,sex:sex,orderno:orderno},function(data) {
                            var data = eval("("+data+")");
                            if(data && data.result=='true'){
                                layer.msg("删除成功！");
                                cache.refTable.jqGrid('delRowData',id );
                            }else{
                                layer.msg("删除失败！",{icon:2});
                            }

                        });
                        layer.close(index);
                    });
                })
            },
            initDicGrid:function(){
                var dictionaries= cache.dictionaries();
                console.log(dictionaries);
                cache.dicTable.jqGrid({
                    caption: "项目结果字典",
                    datatype:'local',
                    rowNum:dictionaries.length,
                    data:dictionaries,
                    colNames: ['快捷键', '字典值'],
                    colModel: [
                        {name: 'textkey', index: 'textkey', width: 100,editable: true},
                        {name: 'testvalue', index: 'testvalue', width: 200,editable: true}
                    ],
                    onSelectRow: function (id) {
                        if (id && id !== cache.lastsel2) {
                            cache.dicTable.saveRow(cache.lastsel2, false, 'clientArray');
                            cache.lastsel2 = id;
                        }
                        cache.dicTable.editRow(id, false);
                    },
                    repeatitems: false,
                    viewrecords: true,
                    width: 900,
                    altRows: true,
                    height: 300,
                    rownumbers: true, // 显示行号
                    rownumWidth: 35
                });
                //增加工具栏按钮
                var setting = $('<span class="widget-toolbar" style="right:20px;top:4px"><button type="button" id="dicAdd" class="btn btn-xs btn-success" data-toggle="button"> <i class="ace-icon fa fa-plus"></i>增加</button>| <button type="button" id="dicDelete" class="btn btn-xs btn-success" data-toggle="button"><i class="ace-icon fa fa-times"></i>删除</button></span>');
                $('#dictionaries').find('.ui-jqgrid-titlebar').append(setting);
                cache.dicTable.jqGrid('setGridWidth', $(".rightContent").width(),false);
                $("#dicAdd").click(function (){
                    //增加表格行
                    var ids = cache.dicTable.jqGrid('getDataIDs');
                    var newId = parseInt(ids[ids.length - 1] || 0) + 1;
                    var rowData = {
                        key:'',
                        value:''
                    };
                    cache.dicTable.jqGrid('addRowData', newId, rowData);
                });
                //删除表格数据
                $('#dicDelete').bind('click', function () {
                    var id = cache.dicTable.jqGrid('getGridParam', 'selrow');
                    if (id == null || id == 0) {
                        layer.msg('请先选择要删除的数据', {icon: 2, time: 1000});
                        return false;
                    }
                    cache.dicTable.jqGrid('delRowData',id );
                })
            },
        };
        return public;
    })();

   jQuery(function ($) {
        //编号部门不允许编辑
        $("#infoForm").Validform({
            tiptype:4,
        });
        if($.Custom.getMethod()=='edit'){
            $('#labdepartmentshow').attr("disabled",true);
            $('#indexid').attr("disabled",true);
        }

        $('#outdate').datetimepicker().next().on(ace.click_event, function () {
            $(this).prev().focus();
        });
        $.Custom.initGrid();
        $.Custom.initDicGrid();
        $.Custom.initDualListbox();
        $.Custom.loadDualListbox();
        $("#labdepartmentshow").autocomplete({
            source: function( request, response ) {
                $.ajax({
                    url: "<%=request.getContextPath()%>/ajax/searchLab",
                    dataType: "json",
                    data: {
                        name : request.term
                    },
                    success: function( data ) {
                        response( $.map( data, function( result ) {
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
                $("#labdepartment").val(ui.item.code);
                $("#departlist").val(ui.item.code);
            }
        });

       $("#samplefrom").autocomplete({
           source: function( request, response ) {
               $.ajax({
                   url: "<%=request.getContextPath()%>/ajax/searchSampleType",
                   dataType: "json",
                   data: {
                       name : request.term
                   },
                   success: function( data ) {
                       response( $.map( data, function( result ) {
                           return {
                               label: result.sign + " : " + result.value,
                               value: result.value,
                               sign : result.sign
                           }
                       }));
                   }
               });
           },
           minLength: 1,
           select : function(event, ui) {
               $("#samplefrom").val(ui.item.value);
               $("#hiddenSamplefrom").val(ui.item.sign);
           }
       });
    })
</script>