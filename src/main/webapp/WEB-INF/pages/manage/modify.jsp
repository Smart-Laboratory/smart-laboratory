<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp" %>

<head>
    <title><fmt:message key="sample.manage.receive"/></title>
    <meta name="menu" content="SampleManage"/>
    <script type="text/javascript" src="/scripts/manage/modify.js"></script>
</head>
<body>
<div class="col-sm-12">
	<div class="row" style="margin-top:10px;">
		<div style="float:left;width:150px;margin-left:10px;">
			<input type="text" id="search_date" name="search_date" onfocus="setDefaultValue()"  class="form-control" />
		</div>
		<div style="float:left;width:150px;margin-left:10px;">
			 <input type="text" id="testSection" name="testSection" class="form-control" onkeyup="this.value=this.value.toUpperCase()"  placeholder="<fmt:message key='sample.modify.testSection'/>">
		</div>
		<div style="float:left;width:300px;margin-left:10px;">	 
			 <input type="text" id="sampleNumber" name="sampleNumber" class="form-control" placeholder="<fmt:message key='sample.modify.sampleNumber'/>">
		</div>
		<div style="float:left;width:150px;margin-left:10px;">
				<select id="operation" name="operation" class="form-control">
					<option value="add">样本号增加</option>
					<option value="reduce">样本号减少</option>
					<option value="inversion">样本号倒置</option>
					<option value="replace">样本号替换</option>
				</select>
		</div>
		<div style="float:left;width:150px;margin-left:10px;">
			<input type="text" id="operationValue" name="operationValue" class="form-control" placeholder="<fmt:message key='sample.modify.operationValue'/>">
		</div>
		<div style="float:left;width:150px;margin-left:10px;">
				<select id="modifyResult" name="modifyResult" class="form-control">
					<option value="1">样本号信息修改</option>
					<option value="0">样本号结果修改</option>
				</select>
		</div>
		<div style="float:left;width:10%;margin-left:10px;">
			<button id="refuse" type="button" onclick="modifySample()" class="btn btn-danger"><fmt:message key='sample.modify.button'/></button>
		</div>
	</div>
	<hr style="height:3px;"/>
	<div id="alert" class="alert alert-success alert-dismissable" style="display:none;"></div>
	<div id="text" style="display:none;">
		<div class="row">
			<div class="col-sm-2"><h3><fmt:message key='sample.inspectionName'/></h3></div>	
			<div class="col-sm-10"><h3><b id="examinaim"></b></h3></div>
			<div class="col-sm-2"><h3><fmt:message key='patient.name'/></h3></div>	
			<div class="col-sm-2"><h3><b id="name"></b></h3></div>
			<div class="col-sm-1"><h3><fmt:message key='patient.sex'/></h3></div>	
			<div class="col-sm-1"><h3><b id="sex"></b></h3></div>
			<div class="col-sm-1"><h3><fmt:message key='patient.age'/></h3></div>	
			<div class="col-sm-1"><h3><b id="age"></b></h3></div>
			<div class="col-sm-2"><h3><fmt:message key='sample.mode'/></h3></div>	
			<div class="col-sm-2"><h3><b id="stayhospitalmode"></b></h3></div>
		</div>
		<div id="outpatient" class="row">
			<div class="col-sm-2"><h3><fmt:message key='patient.section'/></h3></div>	
			<div class="col-sm-10"><h3><b id="section"></b></h3></div>
		</div>
		<div id="wardtext" class="row">
			<div class="col-sm-2"><h3><fmt:message key='ward.section'/></h3></div>	
			<div class="col-sm-6"><h3><b id="ward"></b></h3></div>
			<div class="col-sm-2"><h3><fmt:message key='patient.departbed'/></h3></div>	
			<div class="col-sm-2"><h3><b id="bed"></b></h3></div>
			<div class="col-sm-2"><h3><fmt:message key='ward.type'/></h3></div>	
			<div class="col-sm-4"><h3><b id="type"></b></h3></div>
			<div class="col-sm-2"><h3><fmt:message key='ward.phone'/></h3></div>	
			<div class="col-sm-4"><h3><b id="phone"></b></h3></div>
		</div>
		<div class="row">
			<div class="col-sm-2"><h3><fmt:message key='sample.lab'/></h3></div>	
			<div class="col-sm-10"><h3><b id="lab"></b></h3></div>
		</div>
		
	</div>
	
</div>
</body>