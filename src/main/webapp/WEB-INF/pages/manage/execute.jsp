<%@ include file="/common/taglibs.jsp" %>

<head>
    <title><fmt:message key="sample.manage.receive"/></title>
    <meta name="menu" content="SampleManage"/>
    
    <link rel="stylesheet" type="text/css"  href="<c:url value='../styles/bootstrap.min.css'/>" />
    <script type="text/javascript" src="../scripts/jquery-2.1.4.min.js"></script>
    <script type="text/javascript" src="../scripts/jquery-ui.min.js"></script>
    <script type="text/javascript" src="../scripts/jquery.form.js"></script>
    <script type="text/javascript" src="../scripts/manage/receive.js"></script>
</head>
<body>

<div class="col-sm-10">
	<div class="col-sm-3" style="margin-top:5px; border:1px solid #000000;">
		<div>
			<label ><fmt:message key="execute.jzkh" /></label>
			<input type="text" id="jzkh" class="form-control">
		</div>
		<div>
			<button><fmt:message key="execute.smk" /></button>
			<button><fmt:message key="execute.jkk" /></button>
		</div>
		<div class="checkbox">
			<label>
				<input type="checkbox"><fmt:message key="execute.bloodSample" />
			</label>
		</div>
		<div id="dateChose" style="padding:5px 0px;">
			<span><fmt:message key="execute.date" /></span>
			<div class="form-inline">
				<label for="from" style="margin-left : 10px; width:50px;"><b><fmt:message key="from" /></b></label>
				<input type="text" id="from" name="from" class="form-control date" />
			</div>
			<div class="form-inline">
				<label for="to" style="margin-left : 10px; width:50px;" ><b><fmt:message key="to" /></b></label>
				<input type="text" id="to" name="to" class="form-control date">
			</div>
			
		</div>
		<button id="conform" class="btn btn-lg center-block"><fmt:message key="button.confirm" /></button>
		<div>
			<div class="radio">
				<label class="radio">
  				<input type="radio" name="select_type" id="uncheck" value="1" checked>
  				<fmt:message key="execute.uncheck" />
				</label>
			</div>
			<div class="radio">
				<label class="radio">
	  			<input type="radio" name="select_type" id="all" value="2" >
	  			<fmt:message key="execute.all" />
				</label>
			</div>
			<div class="radio">
				<label class="radio">
	  			<input type="radio" name="select_type" id="executed" value="3" >
	  			<fmt:message key="execute.executed" />
				</label>
			</div>
		</div>
		<div class="checkbox">
    		<label>
      			<input type="checkbox" value=""> <fmt:message key="execute.usebarcode" />
    		</label>
    	</div>
    	<div class="checkbox">
    		<label>
      			<input type="checkbox" value=""> <fmt:message key="execute.printbarcode" />
    		</label>
  		</div>
		
	</div>
	<div class="col-sm-9">
		<div class="col-sm-12" id="patientInfo">
			<div class="col-sm-3 pinfo">
					<span class='col-sm-5'><fmt:message key="patient.blh" />:</span><b id="blh"></b>
			</div>
			<div class="col-sm-3 pinfo">
					<span class='col-sm-5'><fmt:message key="patient.jzkh" />:</span><b id="jzkh"></b>
			</div>
			<div class="col-sm-3 pinfo">
				<span class='col-sm-4'><fmt:message key="patient.name" />:</span><b id="pName"></b>
			</div>
			<div class="col-sm-3 pinfo">
				<span class='col-sm-4'><fmt:message key="patient.sex" />:</span><b id="pSex" class='col-sm-2'></b>
				<span class='col-sm-4'><fmt:message key="patient.csrq" />:</span><b id="pCsrq"></b>
			</div>
		</div>
		<div class="col-sm-12">
			<h5>DateList</h5>
			<div id="date1" style="margin-top:5px; border:1px solid #000000; overflow-x: scroll; " >
				<div class="">
					<div class="checkbox">
    					<label>
      						<input type="checkbox" value=""> 
    					</label>
    				</div>
    				<span class="glyphicon glyphicon-fullscreen" aria-hidden="true"></span>
				</div>
				<div class="">
					<div class="form-inline">
						<%-- <div class="form-control"><span ><fmt:message key="execute." />:</span><b id=""></b></div> --%>
						<span style="width:50px;"><fmt:message key="execute.jzkh" />:</span><b id="" style="width:50px;"><fmt:message key="execute.jzkh" /></b>
						<span style="width:50px;"><fmt:message key="execute.jzkh" />:</span><b id="" style="width:50px;"><fmt:message key="execute.jzkh" /></b>
						<span style="width:50px;"><fmt:message key="execute.jzkh" />:</span><b id="" style="width:50px;"><fmt:message key="execute.jzkh" /></b>
						<span style="width:50px;"><fmt:message key="execute.jzkh" />:</span><b id="" style="width:50px;"><fmt:message key="execute.jzkh" /></b>
						<span style="width:50px;"><fmt:message key="execute.jzkh" />:</span><b id="" style="width:50px;"><fmt:message key="execute.jzkh" /></b>
						<span style="width:50px;"><fmt:message key="execute.jzkh" />:</span><b id="" style="width:50px;"><fmt:message key="execute.jzkh" /></b>
						<span style="width:50px;"><fmt:message key="execute.jzkh" />:</span><b id="" style="width:50px;"><fmt:message key="execute.jzkh" /></b>
					</div>
					<div>
					</div>
				</div>
			</div>
		</div>
	
	</div>


</div>

</body>