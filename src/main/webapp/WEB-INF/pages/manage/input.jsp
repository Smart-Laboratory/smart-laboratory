<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp" %>

<head>
    <title><fmt:message key="sample.manage.input"/></title>
</head>
<body>
<div class="col-sm-6">
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
			<form class="form-horizontal" role="form" style="margin-top:5px;">
				<div class="form-group">
					<label class="col-sm-2 control-label no-padding-right" for="stayhospitaimode">在院方式</label>
					<div class="col-sm-3">
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
					<label class="col-sm-2 control-label no-padding-right" for="doctadviseno">医嘱号</label>
					<div class="col-sm-3">
						<input type="text" class="col-sm-12" id="doctadviseno"></input>
					</div>
					<div class="col-sm-2">&nbsp;
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label no-padding-right" for="sampleno">样本号</label>
					<div class="col-sm-3">
						<input type="text" class="col-sm-12" id="sampleno"></input>
					</div>
					<label class="col-sm-2 control-label no-padding-right" for="patientid">就诊卡号</label>
					<div class="col-sm-3">
						<input type="text" class="col-sm-12" id="patientid"></input>
					</div>
					<div class="col-sm-2">&nbsp;
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label no-padding-right" for="patientname">姓&nbsp;名</label>
					<div class="col-sm-3">
						<input type="text" class="col-sm-12" id="patientid"></input>
					</div>
					<label class="col-sm-2 control-label no-padding-right" for="hossection">性&nbsp;别</label>
					<div class="col-sm-3">
						<input type="text" class="col-sm-12" id="hossection"></input>
					</div>
					<div class="col-sm-2">&nbsp;
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label no-padding-right" for="age">年&nbsp;龄</label>
					<div class="col-sm-3">
						<span class="input-icon input-icon-right" style="width:100%">
							<input type="text" id="age" style="width:75%"></input>
							<span>岁</span>
						</span>
					</div>
					<label class="col-sm-2 control-label no-padding-right" for="diagnostic">诊&nbsp;断</label>
					<div class="col-sm-3">
						<input type="text" class="col-sm-12" id="diagnostic"></input>
					</div>
					<div class="col-sm-2">&nbsp;
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label no-padding-right" for="hossection">科&nbsp;室</label>
					<div class="col-sm-3">
						<input type="text" class="col-sm-12" id="hossection"></input>
					</div>
					<label class="col-sm-2 control-label no-padding-right" for="sampletype">样本类型</label>
					<div class="col-sm-3">
						<input type="text" class="col-sm-12" id="sampletype"></input>
					</div>
					<div class="col-sm-2">&nbsp;
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label no-padding-right" for="requester">送检医生</label>
					<div class="col-sm-3">
						<input type="text" class="col-sm-12" id="requester"></input>
					</div>
					<label class="col-sm-2 control-label no-padding-right" for="feestatus">收费状态</label>
					<div class="col-sm-3">
						<input type="text" class="col-sm-12" id="feestatus"></input>
					</div>
					<div class="col-sm-2">&nbsp;
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label no-padding-right" for="excutetime">采样时间</label>
					<div class="col-sm-3">
						<input type="text" class="col-sm-12 input-mask-date" id="excutetime"></input>
					</div>
					<label class="col-sm-2 control-label no-padding-right" for="receivetime">接收时间</label>
					<div class="col-sm-3">
						<input type="text" class="col-sm-12 input-mask-date" id="receivetime"></input>
					</div>
					<div class="col-sm-2">&nbsp;
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label no-padding-right" for="examinaim">检验目的</label>
					<div class="col-sm-8">
						<input type="text" class="col-sm-12" id="examinaim"></input>
					</div>
					<div class="col-sm-2">&nbsp;
					</div>
				</div>
				<div class="form-group">
					<div class="col-sm-6">
						<div class="col-sm-3">&nbsp;
						</div>
						<button class="btn btn-info col-sm-6" type="button">
							<i class="ace-icon fa fa-check bigger-110"></i>
							保存
						</button>
						<div class="col-sm-3">&nbsp;
						</div>
					</div>
					<div class="col-sm-6">
						<div class="col-sm-3">&nbsp;
						</div>
						<button class="btn col-sm-6" type="reset">
							<i class="ace-icon fa fa-undo bigger-110"></i>
							取消
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
<div class="col-sm-6">
	<div class="widget-box widget-color-green">
		<div class="widget-header">
			<h4 class="widget-title lighter">
				<i class="ace-icon fa fa-table"></i>
				样本录入明细
			</h4>
			<div class="widget-toolbar">
				<a href="#" data-action="collapse">
					<i class="ace-icon fa fa-chevron-up"></i>
				</a>
			</div>
		</div>
	</div>
	<div class="widget-body">
		<div class="widget-main no-padding">
			<table class="table table-striped table-bordered table-hover">
				<thead class="thin-border-bottom">
					<tr>
						<th>
							<i class="ace-icon fa fa-user"></i>
							User
						</th>

						<th>
							<i>@</i>
							Email
						</th>
						<th class="hidden-480">Status</th>
					</tr>
				</thead>

				<tbody>
					<tr>
						<td class="">Alex</td>

						<td>
							<a href="#">alex@email.com</a>
						</td>

						<td class="hidden-480">
							<span class="label label-warning">Pending</span>
						</td>
					</tr>

					<tr>
						<td class="">Fred</td>

						<td>
							<a href="#">fred@email.com</a>
						</td>

						<td class="hidden-480">
							<span class="label label-success arrowed-in arrowed-in-right">Approved</span>
						</td>
					</tr>

					<tr>
						<td class="">Jack</td>

						<td>
							<a href="#">jack@email.com</a>
						</td>

						<td class="hidden-480">
							<span class="label label-warning">Pending</span>
						</td>
					</tr>

					<tr>
						<td class="">John</td>

						<td>
							<a href="#">john@email.com</a>
						</td>

						<td class="hidden-480">
							<span class="label label-inverse arrowed">Blocked</span>
						</td>
					</tr>

					<tr>
						<td class="">James</td>

						<td>
							<a href="#">james@email.com</a>
						</td>

						<td class="hidden-480">
							<span class="label label-info arrowed-in arrowed-in-right">Online</span>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
</div>
</body>