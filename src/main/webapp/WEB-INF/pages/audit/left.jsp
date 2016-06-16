<script type="text/javascript" src="../scripts/lis/audit/left.js"></script>
<style>
.ui-search-clear {
	display:none;
}
</style>

<div id="left" class="col-sm-3">
	<div class="btn-group col-sm-12">
		<button data-toggle="dropdown" class="btn btn-info dropdown-toggle col-sm-5">
			<fmt:message key='batch.deal'/>
			<span class="ace-icon fa fa-caret-down icon-on-right"></span>
		</button>
		<ul class="dropdown-menu dropdown-info">
			<li><a id="manualAuditPassBtn" href="#"><fmt:message key='batch.pass'/></a></li>
			<li><a id="manualAuditUnPassBtn" href="#"><fmt:message key='batch.unpass'/></a></li>
			<li><a id="manualWriteBackBtn" href="#"><fmt:message key='batch.writeback'/></a></li>
			<li><a id="statisticDialogBtn" href="#"><fmt:message key='batch.statistic'/></a></li>
			<li role="separator" class="divider"></li>
			<li><a id="batchAddResultsBtn" href="#"><fmt:message key='batch.add.results'/></a></li>
			<li><a id="samplePrintBtn" href="#"><fmt:message key='audit.danger.print'/></a></li>
		</ul>
		<button id="auditBtn" type="button" class="btn btn-success col-sm-2"><fmt:message key='audit'/></button>
		<button id="sampleDelete" type="button" class="btn btn-danger col-sm-2"><fmt:message key='button.delete'/></button>
		
		<button id="sampleListRefreshBtn" class="btn btn-success col-sm-2">
			<span class="glyphicon glyphicon-refresh"></span>
		</button>
	</div>
	<div id="sampleListPanel" class="col-sm-12" style="margin-top:5px;">
		<table id="list"></table>
		<div id="pager"></div>
	</div>
</div>