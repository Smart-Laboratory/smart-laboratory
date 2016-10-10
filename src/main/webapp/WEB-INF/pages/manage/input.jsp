<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp" %>

<head>
    <title><fmt:message key="sample.manage.input"/></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/styles/ui.jqgrid.css'/>"/>
    <%--<link rel="stylesheet" type="text/css"  href="<c:url value='/scripts/layer/skin/layer.css'/>" />--%>
    <%--<link rel="stylesheet" type="text/css"  href="<c:url value='/scripts/layer/skin/layer.ext.css'/>" />--%>
    <script type="text/javascript" src="../scripts/jquery-2.1.4.min.js"></script>
    <script type="text/javascript" src="../scripts/jquery-ui.min.js"></script>
    <script type="text/javascript" src="../scripts/bootstrap.min.js"></script>
    <script type="text/javascript" src="../scripts/i18n/grid.locale-cn.js"></script>
    <script type="text/javascript" src="../scripts/jquery.jqGrid.js"></script>
    <script type="text/javascript" src="<c:url value="/scripts/ace.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/ace-elements.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/bootstrap-tag.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/layer/layer.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/layer/extend/layer.ext.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/laydate/laydate.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/manage/input.js"/>"></script>
</head>
<style>
    select {
        height: 34px;
    }

    .ui-autocomplete {
        z-index: 99999999 !important;
        margin: 0;
        padding: 0;
        border: 0;
    }
</style>
<body>
<div class="row" style="padding-left: 5px">
    <div class="col-sm-10">
        <div class="row " style="padding: 5px 0px;">
            <div class="col-sm-5">
                <div class="input-group">
                    <span class="input-group-addon">医&#8194;嘱&#8194;号</span>
                    <input type="text" class="form-control" placeholder="医嘱号" id="receive_id" onkeypress="receive(this,event)"/>
                </div>
            </div>
            <div class="col-sm-4">
                <div class="input-group">
                    <span class="input-group-addon">样&#8194;本&#8194;号</span>
                    <input type="text" class="form-control" value="${sampleno}" id="sampleno_text" name="sampleNo"/>
                </div>
            </div>
            <div class="col-sm-3">
                <div class="input-group col-sm-12" >
            <span class="input-group-btn ">
                 <button type="button" class="btn btn-sm btn-primary col-sm-4" title="录入样本" onclick="addSample()">
                <i class="ace-icon fa fa-fire bigger-110"></i>
                录入
            </button>
            <button type="button" class="btn btn-sm  btn-success col-sm-4" title="修改样本" onclick="editSample()">
                <i class="ace-icon fa fa-pencil-square bigger-110"></i>
                修改
            </button>
            <button type="button" class="btn btn-sm btn-warning col-sm-4" title="退回样本" onclick="deleteSample()">
                <i class="ace-icon fa fa-times bigger-110"></i>
                退回
            </button>
		    </span>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-sm-5">
                <div class="input-group">
                    <span class="input-group-addon">日期范围</span>
                    <input placeholder="YYYY-MM-DD" onclick="laydate({istime: true, format: 'YYYY-MM-DD'})"
                           type="text" class="form-control" name="fromDate" id="fromDate" value="${fromDate}"/>
                    <span class="input-group-addon" style="background: #ffffff;border: 0">-</span>
                    <input placeholder="YYYY-MM-DD" onclick="laydate({istime: true, format: 'YYYY-MM-DD'})"
                           type="text" class="form-control" name="toDate" id="toDate" value="${toDate}"/>
                </div>
            </div>
            <div class="col-sm-4">
                <div class="input-group">
                    <span class="input-group-addon">样本状态</span>
                    <select class="form-control" id="samplestatus">
                        <option value="1">未接收</option>
                        <option value="5" selected>已接收</option>
                        <option value="7">已审核</option>
                    </select>
                </div>
            </div>
            <div class="col-sm-3">
                <div class="input-group col-sm-12">
            <span class="input-group-btn">
             <button type="button" class="btn btn-sm btn-info  col-sm-4" title="查询样本" onclick="searchSample()">
                <i class="ace-icon fa fa-fire bigger-110"></i>
                查询
            </button>
                <button type="button" class="btn btn-sm btn-pink col-sm-8" title="打印签收单" onclick="print()" disabled>
                <i class="ace-icon fa fa-print bigger-110"></i>
                打印签收单
            </button>
		    </span>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-sm-12" id="samplelist">
                <div class="widget-box widget-color-green">
                    <div class="widget-header">
                        <h4 class="widget-title lighter">
                            <i class="ace-icon fa fa-table"></i>
                            样本录入明细
                        </h4>
                    </div>
                    <div class="widget-body" style="overflow:auto;">
                        <div class="widget-main no-padding">
                            <table id="new" class="table table-striped table-bordered table-hover">
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="col-sm-2">
        <div class="widget-box widget-color-green">
            <div class="widget-header">
                <h4 class="widget-title lighter">
                    <i class="ace-icon fa fa-book"></i>
                    当前样本信息
                </h4>
            </div>
            <div class="widget-body" style="overflow:auto;">
                <div class="widget-main no-padding">
                    <table id="now" class="table table-striped table-bordered table-hover">
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>


<div id="formDialog" style="display:none;">
    <form class="form-horizontal" role="form" style="margin-top:5px;" id="sampleForm">
        <input type="hidden" id="sampleStatus"/>
        <div class="form-group" style="margin-right:0px;margin-left:0px;">
            <label class="col-sm-1 control-label no-padding-right" for="stayhospitalmode">在院方式</label>
            <div class="col-sm-2">
                <select class="col-sm-12" id="stayhospitalmode">
                    <option value="1">门诊</option>
                    <option value="2">住院</option>
                    <option value="3">体检</option>
                    <option value="4">血库</option>
                    <option value="5">外单位</option>
                    <option value="6">药物验证</option>
                    <option value="7">科研</option>
                    <option value="8">电子档案</option>
                </select>
            </div>
            <label class="col-sm-1 control-label no-padding-right" for="barcode">医嘱号</label>
            <div class="col-sm-2">
                <input type="text" class="col-sm-12" id="barcode" onkeypress="getData(this,event)"/>
            </div>
            <label class="col-sm-1 control-label no-padding-right" for="sampleno">样本号</label>
            <div class="col-sm-2">
                <input type="text" class="col-sm-12" id="sampleno" onkeypress="getData(this,event)"
                       value="${sampleno}"/>
                <input type="hidden" id="hiddenSegment" value="${segment}"/>
            </div>
            <label class="col-sm-1 control-label no-padding-right" for="patientid">就诊卡号</label>
            <div class="col-sm-2">
                <input type="text" class="col-sm-12" id="patientid" onkeypress="getPatient(this,event)"/>
            </div>
        </div>
        <div class="form-group" style="margin-right:0px;margin-left:0px;">
            <label class="col-sm-1 control-label no-padding-right" for="patientname">姓&nbsp;名</label>
            <div class="col-sm-2">
                <input type="text" class="col-sm-12" id="patientname"/>
            </div>
            <label class="col-sm-1 control-label no-padding-right" for="sex">性&nbsp;别</label>
            <div class="col-sm-2">
                <select class="col-sm-12" id="sex">
                    <option value="1">男</option>
                    <option value="2">女</option>
                    <option value="3">未知</option>
                </select>
            </div>
            <label class="col-sm-1 control-label no-padding-right" for="age">年&nbsp;龄</label>
            <div class="col-sm-2">
				<span class="input-icon input-icon-right" style="width:100%">
					<input type="text" id="age" style="float:left;width:75%"/>
					<select style="float:left;width:25%" id="ageunit">
						<option value="岁">岁</option>
						<option value="月">月</option>
						<option value="天">天</option>
					</select>
				</span>
            </div>
            <label class="col-sm-1 control-label no-padding-right" for="diagnostic">诊&nbsp;断</label>
            <div class="col-sm-2">
                <input type="text" class="col-sm-12" id="diagnostic"/>
            </div>
        </div>
        <div class="form-group" style="margin-right:0px;margin-left:0px;">
            <label class="col-sm-1 control-label no-padding-right" for="section">科&nbsp;室</label>
            <div class="col-sm-2">
                <input type="text" class="col-sm-12" id="section"/>
                <input type="text" id="sectionCode" style="display:none;"/>
            </div>
            <label class="col-sm-1 control-label no-padding-right" for="sampleType">样本类型</label>
            <div class="col-sm-2">
                <input type="text" class="col-sm-12" id="sampleType"/>
                <input type="hidden" class="col-sm-12" id="hiddenSampleType"/>
            </div>
            <label class="col-sm-1 control-label no-padding-right" for="requester">送检医生</label>
            <div class="col-sm-2">
                <input type="text" class="col-sm-12" id="requester"/>
            </div>
            <label class="col-sm-1 control-label no-padding-right" for="fee">收费金额</label>
            <div class="col-sm-2">
                <input type="text" class="col-sm-12" id="fee"/>
            </div>
        </div>
        <div class="form-group" style="margin-right:0px;margin-left:0px;">
            <label class="col-sm-1 control-label no-padding-right" for="feestatus">收费状态</label>
            <div class="col-sm-2">
                <input type="text" class="col-sm-12" id="feestatus"/>
            </div>
            <label class="col-sm-1 control-label no-padding-right" for="executetime">采样时间</label>
            <div class="col-sm-2">
                <input type="text" class="col-sm-12 input-mask-date" id="executetime"/>
            </div>
            <label class="col-sm-1 control-label no-padding-right" for="receivetime">接收时间</label>
            <div class="col-sm-2">
                <input type="text" class="col-sm-12 input-mask-date" id="receivetime" value='${receivetime}'/>
            </div>
            <div class="col-sm-3">&nbsp;
            </div>
        </div>
        <div class="form-group" style="margin-right:0px;margin-left:0px;">
            <label class="col-sm-1 control-label no-padding-right" for="examinaim">检验目的</label>
            <div class="col-sm-8" id="examTag">
                <input type="text" name="examinaim" id="examinaim" placeholder="输入检验目的的中文、拼音" class="col-sm-12"/>
            </div>
            <input type="text" id="ylxh" style="display:none;"/>
            <div class="col-sm-1">&nbsp;</div>
            <div class="col-sm-2">
                <button type="button" class="btn btn-sm btn-success" title="提交样本信息" onclick="sample()">
                    <i class="ace-icon fa fa-print bigger-110"></i>
                    提交
                </button>
                <button type="button" class="btn btn-sm btn-info" title="清空样本信息" onclick="clearData()">
                    <i class="ace-icon fa fa-undo bigger-110"></i>
                    清空
                </button>
            </div>
        </div>
    </form>
    <input type="hidden" id="operate"/>
</div>
</body>