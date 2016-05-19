<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp" %>

<head>
    <title><fmt:message key="sample.manage.input"/></title>
    <meta name="menu" content="SampleManage"/>
</head>
<body>
<div class="col-sm-10">
<form class="form-horizontal" role="form" style="margin-top:5px;">
<div class="form-group">
	<label class="col-sm-1 control-label no-padding-right" for="stayhospitaimode">在院方式</label>
	<div class="col-sm-3">
		<select class="col-sm-9" id="stayhospitaimode">
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
	<label class="col-sm-1 control-label no-padding-right" for="doctadviseno">医嘱号</label>
	<div class="col-sm-3">
		<input type="text" class="col-sm-9" id="doctadviseno"></input>
	</div>
	<label class="col-sm-1 control-label no-padding-right" for="sampleno">样本号</label>
	<div class="col-sm-3">
		<input type="text" class="col-sm-9" id="sampleno"></input>
	</div>
</div>
<div class="form-group">
	<label class="col-sm-1 control-label no-padding-right" for="patientname">姓&nbsp;名</label>
	<div class="col-sm-3">
		<input type="text" class="col-sm-9" id="patientid"></input>
	</div>
	<label class="col-sm-1 control-label no-padding-right" for="hossection">性&nbsp;别</label>
	<div class="col-sm-3">
		<input type="text" class="col-sm-9" id="hossection"></input>
	</div>
</div>
<div class="form-group">
	<label class="col-sm-1 control-label no-padding-right" for="patientid">年&nbsp;龄</label>
	<div class="col-sm-3">
		<span class="input-icon input-icon-right" style="width:80%">
			<input type="text" id="patientid"></input>
			<i class="ace-icon fa fa-leaf green"></i>
		</span>
	</div>
	<label class="col-sm-1 control-label no-padding-right" for="hossection">科&nbsp;室</label>
	<div class="col-sm-3">
		<input type="text" class="col-sm-9" id="hossection"></input>
	</div>
</div>
</form>

</div>
</body>