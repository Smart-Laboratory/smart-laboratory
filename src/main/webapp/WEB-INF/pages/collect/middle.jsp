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
	<div id="patientinfo" class="col-sm-9">
	<h2 style="display:none;" id="sampleTitle"></h2>
	<div id='passLabel' class="alert alert-success" style="display:none;margin-bottom:2px;padding:0px;padding-left:10px;padding-bottom:4px;">
		<b><fmt:message key="passreason"/>&nbsp;</b>
		<b id="passreason"></b>
	</div>
	<div class="clearfix alert alert-info" style="margin-bottom:5px;padding:5px;padding-bottom:4px;">
		<div class="col-sm-12 pinfo">
			<div class="col-sm-4 pinfo">
				<span class='col-sm-4'><fmt:message key="patient.name" />:</span><b id="pName"></b>
			</div>
			<div class="col-sm-3 pinfo">
				<span class='col-sm-6'><fmt:message key="patient.sex" />:</span><b id="pSex"></b>
			</div>
			<div class="col-sm-2 pinfo">
				<span class='col-sm-6'><fmt:message key="patient.age" />:</span><b id="pAge"></b><fmt:message key="patient.year" />
			</div>
			<div class="col-sm-3 pinfo">
				<span class='col-sm-6'><fmt:message key="sample.type" />:</span><b id="pType"></b>
			</div>
		</div>
		<div class="col-sm-12 pinfo">
			<div class="col-sm-4 pinfo">
				<span class='col-sm-4'><fmt:message key="sample.id" />:</span><b id="doctadviseno"></b>
			</div>
			<div class="col-sm-3 pinfo">
				<span class='col-sm-6'><fmt:message key="sample.mode" />:</span><b id="stayhospitalmode"></b>
			</div>
			<div class="col-sm-5 pinfo">
				<span class='col-sm-3'><fmt:message key="patient.blh" />:</span><b id="blh"></b>
			</div>
		</div>
		<div class="col-sm-12 pinfo">
			<div class="col-sm-4 pinfo">
				<span class='col-sm-4'><fmt:message key="patient.section" />:</span><b id="pSection"></b>
			</div>
			<div id="pBedHtml" class="col-sm-3 pinfo">
				<span class='col-sm-6'><fmt:message key="patient.departbed" />:</span><b id="pBed"></b>
			</div>
			<div class="col-sm-5 pinfo">
				<span class='col-sm-3'><fmt:message key="diagnostic" />:</span><b id="diagnostic"></b>
			</div>
		</div>
		<div class="col-sm-12 pinfo">
			<div id='rbcLabel' style='display:none;float:right;height:15px;color:red;'>
				<fmt:message key="rbc.total"/>&nbsp;<b id="rbctotal"></b>
			</div>
		</div>
	</div>
	<div style="height:35px;" id="btnMenu">
		<div style="margin-top:4px;float:left;">
			<button id="cancelBtn" class="btn" style="font-size: 12px;">
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
					<b><fmt:message key="knowledge.check10" /></b>
			</button>
			<button id="uploadBtn" class="btn btn-info" style="font-size: 12px;">
					<b><fmt:message key="button.upload" /></b>
			</button>
		</div>
	</div>
	
	<marquee behavior="scroll" style="margin-bottom:5px;" bgcolor="#DFF0D8" onMouseOut="this.start()" onMouseOver="this.stop()"><a id="show_history" style="color:#3A87AD"><fmt:message key="button.upload" /></a></marquee>
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