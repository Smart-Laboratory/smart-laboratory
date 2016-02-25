<script type="text/javascript" src="../scripts/lis/audit/right.js"></script>
<script type="text/javascript" src="../scripts/lis/audit.js"></script>
<div id="right" style="position:absolute;right:0px;width:224px">
	<div id="historyTabs"  >
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