<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp" %>

<head>
    <title><fmt:message key="sample.manage.input"/></title>
    <meta name="menu" content="SampleManage"/>
</head>
<body>
<div class="col-sm-10">
	<div class="widget-box">
		<div class="widget-header">
		</div>
		<div class="widget-body">
			<div class="widget-main">
			<form class="form-horizontal" role="form" style="margin-top:5px;">
				<div class="form-group">
					<label class="col-sm-1 control-label no-padding-right" for="stayhospitaimode">在院方式</label>
					<div class="col-sm-2">
						<select class="col-sm-12" id="stayhospitaimode">
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
					<div class="col-sm-2">
						<input type="text" class="col-sm-12" id="doctadviseno"></input>
					</div>
					<label class="col-sm-1 control-label no-padding-right" for="sampleno">样本号</label>
					<div class="col-sm-2">
						<input type="text" class="col-sm-12" id="sampleno"></input>
					</div>
					<label class="col-sm-1 control-label no-padding-right" for="patientid">就诊卡号</label>
					<div class="col-sm-2">
						<input type="text" class="col-sm-12" id="patientid"></input>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-1 control-label no-padding-right" for="patientname">姓&nbsp;名</label>
					<div class="col-sm-2">
						<input type="text" class="col-sm-12" id="patientid"></input>
					</div>
					<label class="col-sm-1 control-label no-padding-right" for="hossection">性&nbsp;别</label>
					<div class="col-sm-2">
						<input type="text" class="col-sm-12" id="hossection"></input>
					</div>
					<label class="col-sm-1 control-label no-padding-right" for="age">年&nbsp;龄</label>
					<div class="col-sm-2">
						<span class="input-icon input-icon-right" style="width:100%">
							<input type="text" id="age" style="width:85%"></input>
							<span>岁</span>
						</span>
					</div>
					<label class="col-sm-1 control-label no-padding-right" for="diagnostic">诊&nbsp;断</label>
					<div class="col-sm-2">
						<input type="text" class="col-sm-12" id="diagnostic"></input>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-1 control-label no-padding-right" for="hossection">科&nbsp;室</label>
					<div class="col-sm-2">
						<input type="text" class="col-sm-12" id="hossection"></input>
					</div>
					<label class="col-sm-1 control-label no-padding-right" for="sampletype">样本类型</label>
					<div class="col-sm-2">
						<input type="text" class="col-sm-12" id="sampletype"></input>
					</div>
					<label class="col-sm-1 control-label no-padding-right" for="examinaim">检验目的</label>
					<div class="col-sm-5">
						<input type="text" class="col-sm-12" id="examinaim"></input>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-1 control-label no-padding-right" for="requester">送检医生</label>
					<div class="col-sm-2">
						<input type="text" class="col-sm-12" id="requester"></input>
					</div>
					<label class="col-sm-1 control-label no-padding-right" for="feestatus">收费状态</label>
					<div class="col-sm-2">
						<input type="text" class="col-sm-12" id="feestatus"></input>
					</div>
					<label class="col-sm-1 control-label no-padding-right" for="excutetime">采样时间</label>
					<div class="col-sm-2">
						<input type="text" class="col-sm-12 input-mask-date" id="excutetime"></input>
					</div>
					<label class="col-sm-1 control-label no-padding-right" for="receivetime">接收时间</label>
					<div class="col-sm-2">
						<input type="text" class="col-sm-9 input-mask-date" id="receivetime"></input>
					</div>
				</div>
				<div class="form-actions">
					<div class="col-md-offset-3 col-md-9">
						<button class="btn btn-info" type="button">
							<i class="ace-icon fa fa-check bigger-110"></i>
							Submit
						</button>
		
						&nbsp; &nbsp; &nbsp;
						<button class="btn" type="reset">
							<i class="ace-icon fa fa-undo bigger-110"></i>
							Reset
						</button>
					</div>
				</div>
			</form>
			</div>
		</div>
	</div>
</div>
</body>