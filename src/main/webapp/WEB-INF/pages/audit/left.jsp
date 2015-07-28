<script type="text/javascript" src="../scripts/lis/audit/left.js"></script>
<style>
.ui-search-clear {
	display:none;
}
</style>

<div id="left" class="col-sm-3">
	<div style="height:35px;margin-bottom:10px;">
		<div>
			<div class="btn-group" style="margin-bottom:0px;">
				<button type="button" class="btn btn-info" style="width:75px;"><fmt:message key='batch.deal'/></button>
				<button type="button" class="btn btn-info dropdown-toggle" style="width:25px;" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
					<span class="caret"></span>
				</button>
				<ul class="dropdown-menu">
					<li><a id="manualAuditPassBtn" href="#"><fmt:message key='batch.pass'/></a></li>
					<li><a id="manualAuditUnPassBtn" href="#"><fmt:message key='batch.unpass'/></a></li>
					<li><a id="manualWriteBackBtn" href="#"><fmt:message key='batch.writeback'/></a></li>
					<li role="separator" class="divider"></li>
					<li><a id="samplePrintBtn" href="#"><fmt:message key='audit.danger.print'/></a></li>
				</ul>
			</div>
			<button id="sampleDelete" type="button" class="btn" style="width:55px;"><fmt:message key='button.delete'/></button>
			<button id="auditBtn" type="button" class="btn" style="width:55px;"><fmt:message key='audit'/></button>
			<button id="sampleListRefreshBtn" class="btn btn-success" style="width:30px;">
				<span class="glyphicon glyphicon-refresh" style="margin-top:3px;"></span>
			</button>
		</div>
	</div>
	<div id="sampleListPanel" style="margin-top:10px;">
		<table id="list"></table>
		<div id="pager"></div>
	</div>
</div>