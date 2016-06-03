<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp" %>

<head>
    <title><fmt:message key="sample.manage.receive"/></title>
    
    <link rel="stylesheet" type="text/css"  href="<c:url value='../styles/jquery-ui.min.css'/>" />
    <link rel="stylesheet" type="text/css"  href="<c:url value='../styles/bootstrap.min.css'/>" />
    <script type="text/javascript" src="../scripts/i18n/grid.locale-cn.js"></script>
    <script type="text/javascript" src="../scripts/jquery-2.1.4.min.js"></script>
    <script type="text/javascript" src="../scripts/jquery-ui.min.js"></script>
    <script type="text/javascript" src="../scripts/jquery.form.js"></script>
    <script type="text/javascript" src="../scripts/manage/execute.js"></script>
</head>
<style>

.widget-body span {
	display:inline-block;
	padding:2px 10px;
}

.sampleInfo{
	height:70px;
	padding:5px 5px;
}

</style>

<body>

<div class="col-sm-12">
	<div class="col-sm-3">
	<div class="widget-box">
	<div class="widget-header">
		<h4 class="widget-title"><fmt:message key="execute.cxxx" /></h4>
		<div class="widget-toolbar">
			<a href="#" data-action="collapse">
				<i class="ace-icon fa fa-chevron-up"></i>
			</a>
		</div>
	</div>
	<div class="widget-body">
		<div class="widget-main">
		<div>
			<label ><fmt:message key="execute.jzkh" /></label>
			<input type="text" id="jzkh" class="form-control" onkeypress="getData(this,event)">
		</div>
		<div style="padding-top:5px;">
			<button><fmt:message key="execute.smk" /></button>
			<button><fmt:message key="execute.jkk" /></button>
		</div>
		<div class="checkbox">
			<label>
				<input type="checkbox" id="bloodCheck"><fmt:message key="execute.bloodSample" />
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
		<div class="checkbox" style="border-top:1px solid #000000;">
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
		</div>
	</div>
	</div>
	<div class="col-sm-9" style="">
		<div id="patientInfo" style="width:99%;margin:10px 5px;">
		<div class="widget-box">
		<div class="widget-header">
			<h4 class="widget-title"><fmt:message key="execute.brxx" /></h4>
			<div class="widget-toolbar">
				<a href="#" data-action="collapse">
					<i class="ace-icon fa fa-chevron-up"></i>
				</a>
			</div>
		</div>
		<div class="widget-body">
			<div class="widget-main">
				<div  style="height:20px;">
					<div class="col-sm-2 ">
							<span class='col-sm-6'><fmt:message key="patient.blh" />:</span><b id="blh"></b>
					</div>
					<div class="col-sm-3 ">
							<span class='col-sm-5'><fmt:message key="patient.patientId" />:</span><b id="patientId"></b>
					</div>
					<div class="col-sm-2 ">
						<span class='col-sm-6'><fmt:message key="patient.name" />:</span><b id="pName"></b>
					</div>
					<div class="col-sm-4 ">
						<span class='col-sm-2'><fmt:message key="patient.sex" />:</span><b id="pSex" class='col-sm-2'></b>
						<span class='col-sm-4'><fmt:message key="execute.csrq" />:</span><b id="pCsrq"></b>
					</div>
				</div>
			</div>
		</div>
		</div>
		</div>
		<div style="width:99%; ">
			<div class="widget-box">
				<div class="widget-header">
					<h4 class="widget-title"><fmt:message key="execute.datelist" /></h4>
					<div class="widget-toolbar">
						<a href="#" data-action="collapse">
							<i class="ace-icon fa fa-chevron-up"></i>
						</a>
					</div>
				</div>
				<div class="widget-body" style="overflow-x:scroll;">
					<div id="tests" class="widget-main" style="width:1500px;">
					<div  id="date1" class="alert alert-info sampleInfo" style="" >
						<div class="col-sm-2" style="">
							<div class="checkbox col-sm-3">
		    					<label>
		      						<input type="checkbox" value=""> 
		    					</label>
		    				</div>
		    				<div class="checkbox col-sm-3">
		    					<span class="glyphicon glyphicon-fullscreen" aria-hidden="true"></span>
		    				</div>
		    				
						</div>
						<div class="col-sm-10" style="">
							<div >
								<span class="datespan"><fmt:message key="execute.sfxm" />:</span><b id="ylmc"></b>
								<span ><fmt:message key="execute.fph" />:</span><b id="sfsb"></b>
								<span ><fmt:message key="execute.dj" />:</span><b id="dj"></b> 
									<fmt:message key="execute.ch" /><b id="sl"></b>
								<span ><fmt:message key="execute.zxks" />:</span><b id="ksdm"></b>
							</div>
							<div>
								<span ><fmt:message key="execute.bgsj" />:</span><b id="qbgsj"></b>
								<span ><fmt:message key="execute.sqsj" />:</span><b id="kdsj"></b>
								<span ><fmt:message key="execute.sqks" />:</span><b id="sjksdm"></b>
								<span ><fmt:message key="execute.dd" />:</span><b id="qbgdd"></b>
							</div>
						</div>
					</div>
					<div  id="date2" class="alert alert-success sampleInfo" style="" >
						<div class="col-sm-2" style="">
							<div class="checkbox col-sm-3">
		    					<label>
		      						<input type="checkbox" value=""> 
		    					</label>
		    				</div>
		    				<div class="checkbox col-sm-3">
		    					<span class="glyphicon glyphicon-fullscreen" aria-hidden="true"></span>
		    				</div>
		    				
						</div>
						<div class="col-sm-10" style="">
							<div>
								<span class="datespan"><fmt:message key="execute.sfxm" />:</span><b id="ylmc"></b>
								<span ><fmt:message key="execute.fph" />:</span><b id="sfsb"></b>
								<span ><fmt:message key="execute.dj" />:</span><b id="dj"></b> 
									<fmt:message key="execute.ch" /><b id="sl"></b>
								<span ><fmt:message key="execute.zxks" />:</span><b id="ksdm"></b>
							</div>
							<div>
								<span ><fmt:message key="execute.bgsj" />:</span><b id="qbgsj"></b>
								<span ><fmt:message key="execute.sqsj" />:</span><b id="kdsj"></b>
								<span ><fmt:message key="execute.sqks" />:</span><b id="sjksdm"></b>
								<span ><fmt:message key="execute.dd" />:</span><b id="qbgdd"></b>
							</div>
						</div>
					</div>
					</div>
				</div>
			</div>
		</div>
	
	</div>


</div>

<div id="printDialog" align="left">
	<button class="btn btn-success" onclick="javascript:document.getElementById('iframe_print').contentWindow.print();"><fmt:message key="print"/></button>
	<div id="printFrame" style="height:400px;"></div>
</div>

</body>