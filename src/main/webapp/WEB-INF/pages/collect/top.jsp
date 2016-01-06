
<div class="col-sm-12">
<div class="alert alert-success clearfix" style="margin-bottom:5px;padding:5px;">
	<div class="form-inline">
		<div class="form-group" style="margin-right:30px;">
			<b><fmt:message key="collect.userid" /> : </b><a id="userid"><span id="i_userid"><c:out
						value="${userid}" /></span></a>
		</div>
		<div class="form-group" style="margin-right:30px;">
			<b><fmt:message key="collect.username" /> : </b><a id="username"><span id="i_username"><c:out
						value="${username}" /></span></a>
		</div>
		<div class="form-group" style="margin-right:30px;">
			<b><fmt:message key="collect.collectnum" /> : </b><a id="collectnum"><span id="i_collectnum"><c:out
						value="${collectnum}" /></span></a>
		</div>
		<div class="form-group" style="margin-right:30px;">
			<b><fmt:message key="collect.evaluatenum" /> : </b><a id="evaluatenum"><span id="i_evaluatenum"><c:out
						value="${evaluatenum}" /></span></a>
		</div>
		<div class="form-group" style="margin-right:30px;">
			<b><fmt:message key="collect.checknum" /> : </b><a id="checknum"><span id="i_checknum"><c:out
						value="${checknum}" /></span></a>
		</div>
		<div class="form-group" style="margin-right:30px;">
			<b><fmt:message key="collect.integration" /> : </b><a id="integration"><span id="i_integration"><c:out
						value="${integration}" /></span></a>
		</div>
		
		<div style="float: right; margin-right:10px;margin-bottom:0px;">
			<div class="span" style="margin-right:10px;">
				<b style="font-size:14px;line-height:30px;">
					<font color='#D9D919'><c:out value="${color}"/></font>
					<font color='blue'><c:out value="${operator}"/></font>
				</b>
			</div>
		</div>
	</div>
</div>
</div>