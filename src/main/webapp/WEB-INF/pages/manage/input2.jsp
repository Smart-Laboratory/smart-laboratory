<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp" %>

<head>
    <title><fmt:message key="sample.manage.input"/></title>
    <link rel="stylesheet" type="text/css"  href="<c:url value='/styles/ui.jqgrid.css'/>" />
    	
    <script type="text/javascript" src="../scripts/jquery-2.1.4.min.js"></script>
	<script type="text/javascript" src="../scripts/jquery-ui.min.js"></script>
	<script type="text/javascript" src="../scripts/bootstrap.min.js"></script>
	<script type="text/javascript" src="../scripts/i18n/grid.locale-cn.js"></script>
	<script type="text/javascript" src="../scripts/jquery.jqGrid.js"></script>
	<script type="text/javascript" src="<c:url value="/scripts/ace.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/ace-elements.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/bootstrap-tag.min.js"/>"></script>
    <script type="text/javascript" src="../scripts/layer/layer.js"></script>
    <script type="text/javascript" src="../scripts/manage/input.js"></script>
</head>
<style>
	select {
		height:34px;
	}
</style>
<body>
<div class="row">
<div class="col-sm-12">
	<div class="widget-box widget-color-green">
		<div class="widget-header">
			<h4 class="widget-title">
				<i class="ace-icon fa fa-pencil-square-o" aria-hidden="true"></i>
				样本录入
			</h4>
			<div class="widget-toolbar">
				<a href="#" data-action="collapse">
					<i class="ace-icon fa fa-chevron-up"></i>
				</a>
			</div>
		</div>
		<div class="widget-body">
			<div class="widget-main">
			<form class="form-horizontal" role="form" style="margin-top:5px;" id="sampleForm">
				<div class="form-group" style="margin-right:0px;margin-left:0px;">
					<label class="col-sm-1 control-label no-padding-right" for="stayhospitaimode">在院方式</label>
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
					<label class="col-sm-1 control-label no-padding-right" for="doctadviseno">医嘱号</label>
					<div class="col-sm-2">
						<input type="text" class="col-sm-12" id="doctadviseno" onkeypress="getData(this,event)"></input>
					</div>
					<label class="col-sm-1 control-label no-padding-right" for="sampleno">样本号</label>
					<div class="col-sm-2">
						<input type="text" class="col-sm-12" id="sampleno" onkeypress="getData(this,event)" value="${sampleno}"></input>
						<input type="hidden" id="hiddenSegment" value="${segment}"/>
					</div>
					<label class="col-sm-1 control-label no-padding-right" for="patientid">就诊卡号</label>
					<div class="col-sm-2">
						<input type="text" class="col-sm-12" id="patientid" onkeypress="getPatient(this,event)"></input>
					</div>
				</div>
				<div class="form-group" style="margin-right:0px;margin-left:0px;">
					<label class="col-sm-1 control-label no-padding-right" for="patientname">姓&nbsp;名</label>
					<div class="col-sm-2">
						<input type="text" class="col-sm-12" id="patientname"></input>
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
							<input type="text" id="age" style="float:left;width:75%"></input>
							<select  style="float:left;width:25%" id="ageunit">
								<option value="岁">岁</option>
								<option value="月">月</option>
								<option value="天">天</option>
							</select>
						</span>
					</div>
					<label class="col-sm-1 control-label no-padding-right" for="diagnostic">诊&nbsp;断</label>
					<div class="col-sm-2">
						<input type="text" class="col-sm-12" id="diagnostic"></input>
					</div>
				</div>
				<div class="form-group" style="margin-right:0px;margin-left:0px;">
					<label class="col-sm-1 control-label no-padding-right" for="section">科&nbsp;室</label>
					<div class="col-sm-2">
						<input type="text" class="col-sm-12" id="section"></input>
						<input type="text" id="sectionCode" style="display:none;"></input>
					</div>
					<label class="col-sm-1 control-label no-padding-right" for="sampletype">样本类型</label>
					<div class="col-sm-2">
						<select class="col-sm-12" id="sampletype">
							<c:forEach var="sType" items="${typelist}">
								<option value='<c:out value="${sType.sign}" />'><c:out value="${sType.value}" /></option>
							</c:forEach>
						</select>
					</div>
					<label class="col-sm-1 control-label no-padding-right" for="requester">送检医生</label>
					<div class="col-sm-2">
						<input type="text" class="col-sm-12" id="requester"></input>
					</div>
					<label class="col-sm-1 control-label no-padding-right" for="fee">收费金额</label>
					<div class="col-sm-2">
						<input type="text" class="col-sm-12" id="fee"></input>
					</div>
				</div>
				<div class="form-group" style="margin-right:0px;margin-left:0px;">
					<label class="col-sm-1 control-label no-padding-right" for="feestatus">收费状态</label>
					<div class="col-sm-2">
						<input type="text" class="col-sm-12" id="feestatus"></input>
					</div>
					<label class="col-sm-1 control-label no-padding-right" for="executetime">采样时间</label>
					<div class="col-sm-2">
						<input type="text" class="col-sm-12 input-mask-date" id="executetime"></input>
					</div>
					<label class="col-sm-1 control-label no-padding-right" for="receivetime">接收时间</label>
					<div class="col-sm-2">
						<input type="text" class="col-sm-12 input-mask-date" id="receivetime" value='${receivetime}'></input>
					</div>
					<div class="col-sm-3">&nbsp;
					</div>
				</div>
				<div class="form-group" style="margin-right:0px;margin-left:0px;">
					<label class="col-sm-1 control-label no-padding-right" for="examinaim">检验目的</label>
					<div class="col-sm-8" id="examTag">
						<input type="text" name="examinaim" id="examinaim" placeholder="输入检验目的的中文、拼音" class="col-sm-12"/>
					</div>
					<input type="text" id="ylxh" style="display:none;"></input>
					<div class="col-sm-3">&nbsp;
					</div>
				</div>
				<div class="form-group">
					<div class="col-sm-1">&nbsp;</div>
					<div class="col-sm-2 radio">
						<label>
							<input name="edittype" type="radio" class="ace input-lg" checked value="add">
							<span class="lbl bigger-120">新增</span>
						</label>
					</div>
					<div class="col-sm-2 radio">
						<label>
							<input name="edittype" type="radio" class="ace input-lg" value="edit">
							<span class="lbl bigger-120">编辑</span>
						</label>
					</div>
					<div class="col-sm-2 radio">
						<label>
							<input name="edittype" type="radio" class="ace input-lg" value="delete">
							<span class="lbl bigger-120">删除</span>
						</label>
					</div>
					<div class="col-sm-2 radio">
						<label>
							<input name="edittype" type="radio" class="ace input-lg" value="cancel">
							<span class="lbl bigger-120">清空</span>
						</label>
					</div>
					<div class="col-sm-3">
						<div class="col-sm-3">&nbsp;
						</div>
						<button class="btn btn-info col-sm-6" type="button" onclick="sample()">
							<i class="ace-icon fa fa-folder bigger-120"></i>
							提交
						</button>
						<div class="col-sm-3">&nbsp;
						</div>
					</div>
				</div>
			</form>
			</div>
		</div>
	</div>
</div>
</div>
<div class="row">
<div class="col-sm-12">
	<div class="widget-box widget-color-green">
		<div class="widget-header">
			<h4 class="widget-title lighter">
				<i class="ace-icon fa fa-table"></i>
				样本录入明细
			</h4>
			<div class="widget-toolbar">
				<ul class="nav nav-tabs" id="myTab">
					<li class="active">
						<a data-toggle="tab" href="#new" aria-expanded="true" onclick="showData(1)">新增数据</a>
					</li>
					<li class="">
						<a data-toggle="tab" href="#old" aria-expanded="false" onclick="showData(2)">当天数据</a>
					</li>
				</ul>
			</div>
		</div>
		<div class="widget-body" style="overflow:auto;">
			<div class="widget-main no-padding">
				<div class="tab-content">
					<div id="new" class="tab-pane in active">
						<table class="table table-striped table-bordered table-hover">
							<thead class="thin-border-bottom">
								<tr>
									<th></th>
									<th>样本号</th>
									<th>在院方式</th>
									<th>姓名</th>
									<th>科室</th>
									<th>床号</th>
									<th>性别</th>
									<th>年龄</th>
									<th>医嘱号</th>
									<th>接收时间</th>
									<th>检验目的</th>
									<th>样本类型</th>
									<th>就诊卡号</th>
									<th>收费状态</th>
									<th>临床诊断</th>
									<th>生理周期</th>
									<th>申请者</th>
									<th>采集部位</th>
									<th>申请模式</th>
									<th>金额</th>
								</tr>
							</thead>
							<tbody id="tableBody">
							</tbody>
						</table>
					</div>
					<div id="old" class="tab-pane">
						<table id="oldTable" class="table table-striped table-bordered table-hover"></table>
						<div id="pager"></div>
					</div>
				</div>
				
			</div>
		</div>
	</div>
</div>
</div>
</body>