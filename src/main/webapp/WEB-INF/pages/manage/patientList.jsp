<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp" %>

<head>
    <title><fmt:message key="sample.manage.audit"/></title>
    
    <!--link rel="stylesheet" type="text/css"  href="<c:url value='/styles/jquery-ui.min.css'/>" /-->
    <link rel="stylesheet" type="text/css"  href="<c:url value='/styles/ui.jqgrid.css'/>" />

    <script type="text/javascript" src="<c:url value='/scripts/jquery-2.1.4.min.js'/> "></script>
	<script type="text/javascript" src="<c:url value='/scripts/jquery.tablednd_0_5.js'/> "></script>
    <script type="text/javascript" src="<c:url value='/scripts/jquery-ui.min.js'/> "></script>
    <script type="text/javascript" src="<c:url value='/scripts/i18n/grid.locale-cn.js'/> "></script>
    <script type="text/javascript" src="<c:url value='/scripts/jquery.jqGrid.js'/> "></script>
    <script type="text/javascript" src="<c:url value='/scripts/jquery.form.js'/> "></script>
	<script type="text/javascript" src="<c:url value='/scripts/highcharts.js'/> "></script>
	<script type="text/javascript" src="<c:url value='/scripts/layer/layer.js'/> "></script>
	<script type="text/javascript" src="<c:url value='/scripts/layer/extend/layer.ext.js'/> "></script>
    
    <script type="text/javascript" src="<c:url value='/scripts/manage/patientList.js'/> "></script>
	<script language="javascript" src="<c:url value='/scripts/LodopFuncs.js'/> "></script>
<style>
.ui-jqgrid-title{
	font-size:16px;
	color:#000;
}
</style>



</head>
<body>

<div class="row">
	<div class="form-inline">
		<label for="from" style="margin-left : 20px;"><b><fmt:message key="from" /></b></label>
		<input type="text" id="from" name="from" class="form-control" />
		<label for="to" style="margin-left : 10px;" ><b><fmt:message key="to" /></b></label>
		<input type="text" id="to" name="to" class="form-control">
		<label for="search_text" style="margin-left : 50px;"></label>
		<input type="text" id="search_text" name="search_text" class="form-control" />
		<select id="search_select" class="form-control select" >
			<option value="1"><fmt:message key="patient.blh" /></option>
			<option value="2"><fmt:message key="patient.name" /></option>
			<option value="3"><fmt:message key="sample.id"></fmt:message></option>
		</select>
		<button id="searchBtn" class="btn btn-info" style="margin-left:20px;"><fmt:message key="search" /></button>
		<button id="search_detailed_printBtn" class="btn btn-info" style="margin-left:20px;"><fmt:message key="print" /></button>
	</div>


	<div class="col-sm-12" style="margin-top: 10px;">
		<div class="col-sm-3" id="leftContent" style="float: left; ">
			<div id="sampleListPanel">
				<table id="list"></table>
				<div id="pager"></div>
			</div>
		</div>
		<div id="midContent" class="col-sm-9" style="float: left;">
			<div class="clearfix">
				<!--h2 style="display:none;" id="sampleTitle"></h2-->
				<div id="patientinfo" class="col-sm-12">
					<h2 style="display:none;" id="sampleTitle"></h2>
					<%--<div id='passLabel' class="alert alert-success" style="display:none;margin-bottom:2px;padding:0px;padding-left:10px;padding-bottom:4px;">--%>
					<%--<b><fmt:message key="passreason"/>&nbsp;</b>--%>
					<%--<b id="passreason"></b>--%>
					<%--</div>--%>
					<div class="clearfix alert alert-info" style="margin-bottom:0px;padding:2px;padding-bottom:2px;">
						<table style="width: 100%" cellpadding="0" cellspacing="0" class="patientinfo">
							<colgroup>
								<col width="30%"/>
								<col width="30%"/>
								<col width="*"/>
							</colgroup>
							<tr>
								<td>姓&#8195;名:</span><b id="pName"></b></td>
								<td>性&#8195;别:</span><b id="pSex"></b></td>
								<td>年&#8195;&#8195;龄:</span><b id="pAge"></b></td>
							</tr>
							<tr>
								<td>类&#8195;型:</span><b id="pType"></b></td>
								<td>医嘱号:</span><b id="doctadviseno"></b></td>
								<td>在院方式:</span><b id="stayhospitalmode"></b></td>
							</tr>
							<tr>
								<td>病历号:</span><b id="blh"></b></td>
								<td>科&#8195;室:</span><b id="pSection"></b></td>
								<td>床&#8195;&#8195;号:</span><b id="pBed"></b></td>
							</tr>
							<tr>
								<td colspan="2">诊&#8195;断:</span><b id="diagnostic"></b>
									<input type="hidden" id="diagnosisValue" />
									<div id='rbcLabel' style='display:none;float:right;height:15px;color:red;'>
										<fmt:message key="rbc.total"/>&nbsp;<b id="rbctotal"></b>
									</div></td>
								<td>开单医生:</span><b id="requester"></b></td>
							</tr>
						</table>
					</div>
					<div style="display:none;" class="clearfix" id="unaudit_reason">
						<div style="float:left;width:80px;margin:0px;padding:2px;padding-left:10px;margin-right:10px;" class="alert alert-error"><b><fmt:message key="unpass.reason" /></b></div>
						<div style="width: 350px;float:left;"><span id="audit_reason"></span></div>
					</div>

				</div>
			</div>
			<div class="col-sm-12" style="margin-top:10px;">
				<div id="patientRow" style="font-size: 13px;">
					<table id="rowed3"></table>
				</div>
			</div>
			<div style="font-size: 13px; display:none;margin-top: 10px;">
				<div style="margin-left:60px;">
					<input type="hidden" id="hiddenDocId"/>
					<input type="hidden" id="hiddenSampleNo"/>
					<input type="hidden" id="hisLastResult"/>
				</div>
			</div>
		</div>
		<!-- 2016-5-19  张晋南 查询详细信息打印 -->
		<div id="auditPrint" align="left"
			 title='<fmt:message key="audit.preview" />'>
			<button class="btn btn-success"
					onclick="document.getElementById('iframe_print').contentWindow.print();">
				<fmt:message key="audit.print" />
			</button>
			<div id="printFrame"></div>
			<button class="btn btn-success"
					onclick="document.getElementById('iframe_print').contentWindow.print();">
				<fmt:message key="audit.print" />
			</button>
		</div>
		<%-- <div id="rightContent" class="col-sm-2" style="position:absolute;right:0px;">
		<div id="historyTabs" style="display:none;">
			<ul>
				<li><a href="#tabs-0"><fmt:message key="addResult.result"/></a></li>
				<li><a href="#tabs-1"><fmt:message key="result.history"/></a></li>
			</ul>
			<div id="tabs-0" style="padding:5px;">
				<div style="margin: 10px;">
					<span class="label label-info"><fmt:message key="audit.infomation" /></span>
				</div>
				<div style="height:465px;overflow-y:auto;">
					<div id="explainRow" style="margin-top: 4px; font-size: 13px;">
						<table id="audit_information"></table>
					</div>
				</div>
			</div>
			<div id="tabs-1" style="padding:5px;">
				<div style="margin: 10px;">
					<span class="label label-info"><fmt:message key="show.three.history" /></span>
				</div>
			</div>
		</div>
	</div> --%>
	</div>

</div>

<div id="chartDialog" style="text-align:left;display:none;" >
	<p class="alert alert-danger" id="chartAlert"><fmt:message key='result.chart.alert'/></p>
	<div id="singleChartPanel" style="width:640px;height:320px"></div>
	<table id="chartTongji" class="table">
		<tbody>
		<tr><th><fmt:message key='tongji.min' /></th><td><span id="tongji_min"></span></td><th><fmt:message key='tongji.max'/></th><td><span id="tongji_max"></span></td><th><fmt:message key='tongji.mid' /></th><td><span id="tongji_mid"></span></td></tr>
		<tr><th><fmt:message key='tongji.ave'/></th><td><span id="tongji_ave"></span></td><th><fmt:message key='tongji.sd' /></th><td><span id="tongji_sd"></span></td><th><fmt:message key='tongji.cov'/></th><td><span id="tongji_cov"></span></td></tr>
		</tbody>
	</table>
	<div id="hmInfo"></div>
</div>


</body>