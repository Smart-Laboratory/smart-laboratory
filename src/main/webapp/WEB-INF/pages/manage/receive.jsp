<%@ include file="/common/taglibs.jsp" %>

<head>
    <title><fmt:message key="sample.manage.receive"/></title>
    <meta name="menu" content="SampleManage"/>
    <script type="text/javascript" src="../scripts/manage/receive.js"></script>
</head>
<body>
<div class="col-sm-12">
	<div class="row" style="margin-top:10px;">
		<div style="float:left;width:30%">
			<div class="col-sm-5"><h4><fmt:message key="sample.id"/></h4></div>
			<div class="col-sm-7"><input type="text" id="doctin" name="doctin" class="form-control" onkeypress="getData(this,event);"></div>
		</div>
		<div style="float:left;width:30%">
			<div class="col-sm-5"><h4><fmt:message key="tat.receiver"/></h4></div>
			<div class="col-sm-7"><input type="text" id="operator" name="operator" class="form-control" value="${name}"></div>
		</div>
		<div style="float:left;width:30%">
			<div class="col-sm-5"><h4><fmt:message key="receive.point"/></h4></div>
			<div class="col-sm-7">
				<select id="point" name="point" class="form-control">
					<c:forEach items="${pointList}" var="point">
					<option value="${point.code}">${point.name}</option>
					</c:forEach>
				</select>
			</div>
		</div>
		<div style="float:left;width:10%">
			<button id="refuse" type="button" class="btn btn-danger"><fmt:message key='invalidSamples.refuse'/></button>
		</div>
	</div>
	<hr style="height:3px;"/>
	<div id="alert" class="alert alert-success alert-dismissable" style="display:none;"></div>
	<div id="text" style="display:none;">
		<div class="row">
			<div class="col-sm-2"><h3><fmt:message key='sample.inspectionName'/></h3></div>	
			<div class="col-sm-10"><h3><b id="examinaim"></b></h3></div>
			<div class="col-sm-2"><h3><fmt:message key='patient.name'/></h3></div>	
			<div class="col-sm-2"><h3><b id="name"></b></h3></div>
			<div class="col-sm-1"><h3><fmt:message key='patient.sex'/></h3></div>	
			<div class="col-sm-1"><h3><b id="sex"></b></h3></div>
			<div class="col-sm-1"><h3><fmt:message key='patient.age'/></h3></div>	
			<div class="col-sm-1"><h3><b id="age"></b></h3></div>
			<div class="col-sm-2"><h3><fmt:message key='sample.mode'/></h3></div>	
			<div class="col-sm-2"><h3><b id="stayhospitalmode"></b></h3></div>
		</div>
		<div id="outpatient" class="row">
			<div class="col-sm-2"><h3><fmt:message key='patient.section'/></h3></div>	
			<div class="col-sm-10"><h3><b id="section"></b></h3></div>
		</div>
		<div id="wardtext" class="row">
			<div class="col-sm-2"><h3><fmt:message key='ward.section'/></h3></div>	
			<div class="col-sm-6"><h3><b id="ward"></b></h3></div>
			<div class="col-sm-2"><h3><fmt:message key='patient.departbed'/></h3></div>	
			<div class="col-sm-2"><h3><b id="bed"></b></h3></div>
			<div class="col-sm-2"><h3><fmt:message key='ward.type'/></h3></div>	
			<div class="col-sm-4"><h3><b id="type"></b></h3></div>
			<div class="col-sm-2"><h3><fmt:message key='ward.phone'/></h3></div>	
			<div class="col-sm-4"><h3><b id="phone"></b></h3></div>
		</div>
		<div class="row">
			<div class="col-sm-2"><h3><fmt:message key='sample.lab'/></h3></div>	
			<div class="col-sm-10"><h3><b id="lab"></b></h3></div>
		</div>
		
	</div>
	
</div>
</body>