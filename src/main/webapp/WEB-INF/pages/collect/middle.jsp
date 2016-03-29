<style>
#btnMenu button {
	font-size:12px;
}
.pinfo {
	padding-right: 5px;
	padding-left: 5px;
}
</style>

<div id="mid" class="col-sm-9" >
	<div id="patientinfo" class="col-sm-8">
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
					<span class='col-sm-8'><fmt:message key="patient.departbed" />:</span><b id="pBed"></b>
				</div>
			</div>
			<div class="col-sm-12 pinfo">
				<div id="pDiaHtml" class="col-sm-5 pinfo">
					<span class='col-sm-3'><fmt:message key="diagnostic" />:</span><b id="diagnostic"></b>
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
				<button id="cancelBtn" class="btn btn-danger" style="font-size: 12px;">
						<b><fmt:message key="button.cancel" /></b>
				</button>
				<button id="collectBtn" class="btn btn-info" style="font-size: 12px;">
						<b><fmt:message key="collect.button" /></b>
				</button>
				<button id="evaluateBtn" class="btn btn-success" style="font-size: 12px;">
						<b><fmt:message key="evaluate.button" /></b>
				</button>
			</div>
			<div style="margin-top:4px;float:right;">
				<button id="imageBtn" class="btn btn-success" style="font-size: 12px;">
						<b><fmt:message key="button.image" /></b>
				</button>
				<button id="uploadBtn" class="btn btn-info" style="font-size: 12px;">
						<b><fmt:message key="button.upload" /></b>
				</button>
			</div>
		</div>
	
		<marquee behavior="scroll" style="margin-bottom:5px;" bgcolor="#DFF0D8" onMouseOut="this.start()" onMouseOver="this.stop()"><a id="show_history" style="color:#3A87AD"><fmt:message key="button.upload" /></a></marquee>
	</div>
	
	
	<div id="right" class="col-sm-4" style="position:absolute;right:0px;">
	<div id="historyTabs" >
		<ul>
			<li><a href="#tabs-0"><fmt:message key="sample.explain"/></a></li>
			<li><a href="#tabs-1"><fmt:message key="result.history"/></a></li>
			<li><a href="#tabs-2"><fmt:message key="knowledge"/></a></li>
		</ul>
		<div id="tabs-0" style="padding:5px;">
			<div style="margin:5px;">
				
				<div style="margin-right:10px;float:right;" id="reasonBtn">	
					<button id="reason_block" class="btn btn-info" style="font-size:15px;">
						<fmt:message key="sample.explain.block" />
					</button>
					<button id="reason_none" class="btn btn-info" style="font-size:15px;display:none;">
						<fmt:message key="sample.explain.none" />
					</button>
				</div>
			</div>
			<div>
				<div style="margin-top:30px;">
					<div id="explainRow" style="margin-top: 6px; font-size: 13px;">
						<table id="audit_information"></table>
					</div>
				</div>
				<div style="margin-top:5px;">
					<button id="resultAdd" class="btn btn-success"><fmt:message key="button.add" /></button>
					<button id="resultDelete" class="btn btn-danger"><fmt:message key="button.delete" /></button>
				</div>
			</div>
		</div>
		<div id="tabs-1" style="padding:5px;">
			<div style="margin: 1px;">
				<input type="checkbox" id="oneColumnShowBtn" style="margin-bottom:5px;">
				<label class="label label-warning" for="oneColumnShowBtn" style="cursor:pointer;"><fmt:message key="one.column.show" /></label>
				<input type="checkbox" id="englishToChBtn" style="margin-bottom:5px;">
				<label class="label label-warning" for="englishToChBtn" style="cursor:pointer;"><fmt:message key="ab" /></label>
			</div>
		</div>
		<div id="tabs-2" style="padding:5px;">
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
	
	
	<div class="col-sm-12">
	<div id="patientRow" style="margin-top:5px;">
		<table id="rowed3" style="font-size: 14px;"></table>
	</div>
	
	<div id="twosampleTable" style="margin-top:5px;">
		<div class="col-sm-6">
			<table id="sample0" style="font-size: 14px;"></table>
		</div>
		<div class="col-sm-6">
			<table id="sample1" style="font-size: 14px;"></table>
		</div>	
	</div>
	<div style="font-size: 13px; display:none;margin-top: 10px;">
				<input type="hidden" id="hiddenSampleNo"/>
				<input type="hidden" id="hiddenBAMC"/>
				<input type="hidden" id="hiddenType"/>
				<input type="hidden" id="hiddenCollector"/>
				<input type="hidden" id="hiddenCollectorId"/>
				<input type="hidden" id="hisLastResult"/>
	</div>
	</div>
	
	
	
</div>