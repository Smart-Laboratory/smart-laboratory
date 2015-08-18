<script type="text/javascript" src="../scripts/lis/audit/middle.js"></script>
<style>
#btnMenu button {
	font-size:12px;
}
</style>

<div id="mid" class="col-sm-6">
	<h2 style="display:none;" id="sampleTitle"></h2>
	<div id='passLabel' class="alert alert-success" style="display:none;margin-bottom:2px;padding:0px;padding-left:10px;padding-bottom:4px;">
		<b><fmt:message key="passreason"/>&nbsp;</b>
		<b id="passreason"></b>
	</div>
	<div class="clearfix alert alert-info" style="margin-bottom:5px;padding:5px;padding-bottom:4px;">
		<div class="col-sm-12">
			<div class="col-sm-3">
				<fmt:message key="patient.name" />:<b id="pName"></b>
			</div>
			<div class="col-sm-3">
				<fmt:message key="patient.sex" />:<b id="pSex"></b>
			</div>
			<div class="col-sm-3">
				<fmt:message key="patient.age" />:<b id="pAge"></b><fmt:message key="patient.year" />
			</div>
			<div class="col-sm-3">
				<fmt:message key="sample.type" />:<b id="pType"></b>
			</div>
		</div>
		<div class="col-sm-12">
			<div class="col-sm-3">
				<fmt:message key="sample.mode" />:<b id="stayhospitalmode"></b>
			</div>
			<div id="pBedHtml" class="col-sm-3">
				<fmt:message key="patient.departbed" />:<b id="pBed"></b>
			</div>
			<div class="col-sm-6">
				<fmt:message key="patient.section" />:<b id="pSection"></b>
			</div>
		</div>
		<div class="col-sm-12">
			<div class="col-sm-3">
				<fmt:message key="patient.blh" />:<b id="blh"></b>
			</div>
			<div class="col-sm-3">
				<fmt:message key="sample.id" />:<b id="doctadviseno"></b>
			</div>
			<div class="col-sm-6">
				<fmt:message key="diagnostic" />:<b id="diagnostic"></b>
			</div>
		</div>
		<div class="col-sm-12">
			<div id='rbcLabel' style='display:none;float:right;height:15px;color:red;'>
				<fmt:message key="rbc.total"/>&nbsp;<b id="rbctotal"></b>
			</div>
		</div>
	</div>
	<div style="display:none;" class="clearfix" id="unaudit_reason">
		<div style="float:left;width:80px;margin:0px;padding:2px;padding-left:10px;margin-right:10px;" class="alert alert-error"><b><fmt:message key="unpass.reason" /></b></div>
		<div style="width: 350px;float:left;"><span id="audit_reason"></span></div>
	</div>
	<div style="height:35px;" id="btnMenu">
		<div style="margin-top:4px;float:left;">
			<button id="auditPassBtn" class="btn btn-success"><b><fmt:message key="button.pass" /></b></button>
			<button id="auditUnpassBtn" class="btn"><b><fmt:message key="button.unpass" /></b></button>
			<button id="imageBtn" class="btn btn-success"><b><fmt:message key="button.image" /></b></button>
			<button id="uploadBtn" class="btn btn-info"><b><fmt:message key="button.upload" /></b></button>
			<button id="unaudit_reason_btn" class="btn btn-danger" style="width:85px;"><b><fmt:message key="sample.unpass.reason" /></b></button>
			<button id="auditPrintBtn" class="btn"><b><fmt:message key="print" /></b></button>
			<button id="collectBtn" class="btn btn-info"><b><fmt:message key="button.collect" /></b></button>
		</div>
		<div style="margin-top:4px;float:right;">
			<button id="testAdd" class="btn"><b><fmt:message key="button.add" /></b></button>
			<button id="testDelete" class="btn"><b><fmt:message key="button.delete" /></b></button>
			<button id="tatBtn" class="btn"><b>TAT</b></button>
			<button id="modifyBtn" class="btn"><b><fmt:message key="sample.test.modify.record" /></b></button>
		</div>
	</div>
	
	<div id="patientRow">
		<table id="rowed3" style="font-size: 14px;"></table>
	</div>
	<div id="twosampleTable" style="float:left; width:750px;">
		<table id="sample0" style="font-size: 14px;"></table>
		<table id="sample1" style="font-size: 14px;"></table>
	</div>
	<div style="font-size: 13px; display:none;margin-top: 10px;">
		<input type="hidden" id="hiddenDocId"/>
		<input type="hidden" id="hiddenSampleNo"/>
		<input type="hidden" id="hisLastResult"/>
		<input type="hidden" id="needEdit" /> 
		<input type="hidden" id="hiddenIsPass"/>
		<input type="hidden" id="hiddenAuditConfirm" value="${activeAuto}"/>
	</div>
	<div id="colorHelp" style="float:left;margin-top:3px;">
	</div>
	<div id="critical_div" style="float:left;margin-top:10px;">
		<div id="critical_alert" class="alert">
			<b id="critical_title" style="font-size:18px;margin-right:20px;"></b>
			<span id="critical_time"></span>
			<div id="critical_info"></div>
		</div>
	</div>
	<div id="relative-tests" style="float:left;color:#468847;background-color:#dff0d8;border-color:#d6e9c6;margin-top:2px;width:500px;">
		<div id='showGalleria'></div>
	</div>
</div>