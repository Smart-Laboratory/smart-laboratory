<style>
.ui-search-clear {
	display:none;
}
</style>

<div id="left" class="col-sm-3">
	<div id="btnMenu" class="form-inline">
		<select id="dataSelect" class="form-control" style="width: 80px;" onchange="dataChange(this)">
			<option value="1"><fmt:message key="collect.personal"/></option>
			<option value="2"><fmt:message key="collect.all"/></option>
		</select>
		<input id="search_bamc" class="span2 form-control" style="width:80px;" type="text"/>
		<button id="typeBth" class="btn btn-info" style="font-size:13px;width:60px;">
			<b><fmt:message key="collect.type" /></b>
		</button>
	
	</div>
	
	
	<div id="collectListPanel" style="margin-top:15px;">
			<table id="s3list"></table>
			<div id="s3pager"></div>
		</div>
		
		<div id="evaluateListPanel" style="margin-top:10px;">
			<table id="evaluatelist"></table>
			<div id="evaluatepager"></div>
		</div>
		
</div>