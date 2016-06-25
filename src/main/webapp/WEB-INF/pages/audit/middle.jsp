<script type="text/javascript" src="../scripts/lis/audit/middle.js"></script>
<style>
#btnMenu button {
	font-size:12px;
}
.pinfo {
	padding-right: 5px;
	padding-left: 5px;
}
#testresultDiv .ui-jqgrid-bdiv {
	overflow-x:hidden;
}
</style>

<div id="mid" class="col-sm-9" >
	<div id="patientinfo" class="col-sm-9">
		<h2 style="display:none;" id="sampleTitle"></h2>
		<div id='passLabel' class="alert alert-success" style="display:none;margin-bottom:2px;padding:0px;padding-left:10px;padding-bottom:4px;">
			<b><fmt:message key="passreason"/>&nbsp;</b>
			<b id="passreason"></b>
		</div>
		<div class="clearfix alert alert-info" style="margin-bottom:0px;padding:2px;padding-bottom:2px;">
			<div class="col-sm-12 pinfo">
				<div class="col-sm-4 pinfo">
					<span class='col-sm-4'><fmt:message key="patient.name" />:</span><b id="pName"></b>
				</div>
				<div class="col-sm-4 pinfo">
					<span class='col-sm-4'><fmt:message key="patient.sex" />:</span><b id="pSex" class='col-sm-2'></b>
					<span class='col-sm-4'><fmt:message key="patient.age" />:</span><b id="pAge"></b>
				</div>
				<div class="col-sm-4 pinfo">
					<span class='col-sm-6'><fmt:message key="sample.type" />:</span><b id="pType"></b>
				</div>
			</div>
			<div class="col-sm-12 pinfo">
				<div class="col-sm-4 pinfo">
					<span class='col-sm-5'><fmt:message key="sample.id" />:</span><b id="doctadviseno"></b>
				</div>
				<div class="col-sm-4 pinfo">
					<span class='col-sm-7'><fmt:message key="sample.mode" />:</span><b id="stayhospitalmode"></b>
				</div>
				<div class="col-sm-4 pinfo">
					<span class='col-sm-5'><fmt:message key="patient.blh" />:</span><b id="blh"></b>
				</div>
			</div>
			<div class="col-sm-12 pinfo">
				<div class="col-sm-8 pinfo">
					<span class='col-sm-2'><fmt:message key="patient.section" />:</span><b id="pSection"></b>
				</div>
				<div id="pBedHtml" class="col-sm-4 pinfo">
					<span class='col-sm-4'><fmt:message key="patient.departbed" />:</span><b id="pBed"></b>
				</div>
			</div>
			<div class="col-sm-12 pinfo">
				<div id="pDiaHtml" class="col-sm-5 pinfo">
					<span class='col-sm-3'><fmt:message key="diagnostic" />:</span><b id="diagnostic"></b>
					<input type="hidden" id="diagnosisValue" /> 
				</div>
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
				<button id="auditUnpassBtn" class="btn btn-info"><b><fmt:message key="button.unpass" /></b></button>
				<button id="imageBtn" class="btn btn-success"><b><fmt:message key="button.image" /></b></button>
				<button id="uploadBtn" class="btn btn-info"><b><fmt:message key="button.upload" /></b></button>
				<button id="unaudit_reason_btn" type="button" data-container="body" data-toggle="popover" data-placement="bottom" class="btn btn-danger" style="width:75px;"><b><fmt:message key="sample.unpass.reason" /></b></button>
				<button id="auditPrintBtn" class="btn btn-info"><b><fmt:message key="print" /></b></button>
				<button id="collectBtn" class="btn btn-info"><b><fmt:message key="button.collect" /></b></button>
			</div>
			<div style="margin-top:4px;float:right;">
				<button id="testAdd" class="btn btn-success"><b><fmt:message key="button.add" /></b></button>
				<button id="testDelete" class="btn btn-danger" ><b><fmt:message key="button.delete" /></b></button>
				<button id="tatBtn" class="btn btn-info"><b>TAT</b></button>
				<button id="modifyBtn" class="btn btn-success"><b><fmt:message key="sample.test.modify.record" /></b></button>
			</div>
		</div>
	</div>
	
	<div id="right" class="col-sm-3" style="position:absolute;right:0px;z-index:1">
		<div id="historyTabs">
			<div>
				<ul class="nav nav-tabs">
					<li class="">
						<a data-toggle="tab" href="#tabs-0" aria-expanded="true" onclick="tabChange(0)"><fmt:message key="sample.explain"/></a>
					</li>
					<li class="active">
						<a data-toggle="tab" href="#tabs-1" aria-expanded="false" onclick="tabChange(1)"><fmt:message key="result.history"/></a>
					</li>
					<li class="">
						<a data-toggle="tab" href="#tabs-2" aria-expanded="false" onclick="tabChange(2)"><fmt:message key="knowledge"/></a>
					</li>
				</ul>
			</div>
			<div class="tab-content">
				<div id="tabs-0" class="tab-pane">
					<div style="margin:5px;" class="row">
						
						<div style="margin-right:10px;float:right;" id="reasonBtn">	
							<button id="reason_block" class="btn btn-minier btn-info" style="font-size:15px;">
								<fmt:message key="sample.explain.block" />
							</button>
							<button id="reason_none" class="btn btn-minier btn-info" style="font-size:15px;display:none;">
								<fmt:message key="sample.explain.none" />
							</button>
						</div>
					</div>
					<div style="margin:5px;" class="row">
						<div id="explainRow" style="font-size: 13px;">
							<table id="audit_information"></table>
						</div>
						<div style="margin-top:5px;">
							<button id="resultAdd" class="btn btn-sm btn-success"><fmt:message key="button.add" /></button>
							<button id="resultDelete" class="btn btn-sm btn-danger"><fmt:message key="button.delete" /></button>
						</div>
					</div>
				</div>
				<div id="tabs-1" class="tab-pane active">
					<div style="margin: 1px;">
						<input type="checkbox" id="oneColumnShowBtn" style="margin-bottom:5px;">
						<label class="label label-warning" for="oneColumnShowBtn" style="cursor:pointer;"><fmt:message key="one.column.show" /></label>
						<input type="checkbox" id="englishToChBtn" style="margin-bottom:5px;">
						<label class="label label-warning" for="englishToChBtn" style="cursor:pointer;"><fmt:message key="ab" /></label>
					</div>
				</div>
				<div id="tabs-2" class="tab-pane">
					<div id="g1"></div>
					<span class="label label-info" style="margin-left:56px;"><a onclick="getDetailSop(0)"><fmt:message key="sop.detail.g1" /></a></span>
					<div id="g2"></div>
					<span class="label label-info" style="margin-left:56px;"><button class="btn" onclick="getDetailSop(1)"><fmt:message key="sop.detail.g2" /></button></span>
					<div id="g3"></div>
					<span class="label label-info" style="margin-left:56px;"><a onclick="getDetailSop(2)"><fmt:message key="sop.detail.g3" /></a></span>
					<div id="g4"></div>
					<span class="label label-info" style="margin-left:56px;"><a onclick="getDetailSop(3)"><fmt:message key="sop.detail.g4" /></a></span>
				</div>
			</div>
		</div>
	</div>
	
	<div  class="col-sm-12" id="testresultDiv">
		<div id="patientRow" style="margin-top:5px;width:100%">
			<table id="rowed3" style="font-size: 14px;"></table>
		</div>
		<div id="twosampleTable" style="float:left;margin-top:5px;width:100%;">
			<div style="float:left;width:50%">
				<table id="sample0" style="font-size:14px;"></table>
			</div>
			<div style="float:left;width:50%">
				<table id="sample1" style="font-size:14px;"></table>
			</div>	
		</div>
		<div style="font-size: 13px; display:none;margin-top: 10px;">
			<input type="hidden" id="hiddenDocId"/>
			<input type="hidden" id="hiddenSampleNo"/>
			<input type="hidden" id="hisLastResult"/>
			<input type="hidden" id="needEdit" /> 
			<input type="hidden" id="hiddenIsPass"/>
			<input type="hidden" id="hiddenAuditConfirm" value="${activeAuto}"/>
		</div>
		<div id="colorHelp" style="float:left;margin-top:3px;width:100%">
		</div>
		<div id="critical_div" style="float:left;width:100%">
			<div id="critical_alert" class="alert" style="margin-bottom:0px;">
				<b id="critical_title" style="font-size:18px;margin-right:20px;"></b>
				<span id="critical_time"></span>
				<div id="critical_info"></div>
			</div>
		</div>
		<div id="relative-tests" style="float:left;color:#468847;background-color:#dff0d8;border-color:#d6e9c6;margin-top:2px;width:500px;">
			<div id='showGalleria'></div>
		</div>
	</div>
</div>

