<script type="text/javascript" src="../scripts/lis/audit/top.js"></script>

<div class="col-sm-12">
<div class="alert alert-success" style="margin-bottom:5px;padding:0px 5px;">
	<div class="form-inline">
		<div class="form-group" style="margin-right:30px;">
			<b><fmt:message key="info.today.unaudit" /> : </b><a id="t_info_unaudit"><span id="today_info_unaudit"><c:out
						value="${today_info_unaudit}" /></span></a>
		</div>
		<div class="form-group" style="margin-right:30px;">
			<b><fmt:message key="info.today.unpass" /> : </b><a id="t_info_unpass"><span id="today_info_unpass"><c:out
						value="${today_info_unpass}" /></span></a>
		</div>
		<div id="need_write_back_div" class="form-group alert-info" style="margin-right:30px;padding:5px;">
			<b><fmt:message key="info.need.writeback" /> : </b><span id="need_write_back"><c:out value="${need_write_back}" /></span>
		</div>
		
		<div style="padding:5px;margin-right: 15px;cursor:pointer;" class="form-group <c:if test="${dangerous_undeal != 0}">alert-error</c:if>" id="div_dangerous" >
			<b><fmt:message key="info.danger" /> : </b>
			<span id="info_dangerous_undeal"><c:out value="${dangerous_undeal}" /></span>
		</div>
		<div class="form-group" style="float: right; margin-right:10px;">
			<button id="writeBackPartBtn" class="btn" style="width:100px;height:28px;"><fmt:message key="writebackpart" /></button>
			<button id="writeBackBtn" class="btn btn-success" style="width:55px;height:28px;padding:4px 5px;"><fmt:message key="writeback" /></button>
			<button id="pageRefreshBtn" class="btn btn-info" style="width:30px;height:28px;padding:3px;">
				<span class="glyphicon glyphicon-refresh" style="margin:3px;"></span>
			</button>
			<button id="fullScreen" class="btn btn-info" style="width:30px;height:28px;padding:5px;">
				<span class="glyphicon glyphicon-fullscreen" aria-hidden="true"></span>
			</button>
			<input type="hidden" value="0" id="isfulltag">
			<input type="hidden" id="lastDepLab" value="${lab}">
			<input type="hidden" id="writebackurl" value="${catcherUrl}">
			<input type="hidden" id="userLabCode" value="${userCode}">
			<input type="hidden" id="checkOperator" value="${checkOperator}">
		</div>
	</div>
</div>
</div>