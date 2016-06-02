<%@ include file="/common/taglibs.jsp"%>
<head>
	<link rel="stylesheet" type="text/css"  href="<c:url value='/styles/jquery-ui.min.css'/>" />
    <link rel="stylesheet" type="text/css"  href="<c:url value='/styles/bootstrap.min.css'/>" />
    
    <script type="text/javascript" src="../scripts/jquery-2.1.4.min.js"></script>
	<script type="text/javascript" src="../scripts/bootstrap.min.js"></script>
	<script type="text/javascript" src="../scripts/jquery-ui.min.js"></script>
	<script type="text/javascript" src="../scripts/print/printBarcode.js"></script>
</head>

<style>
.info{
	padding-left:10px;
}
</style>
<div style="background:#999;width:450px;height:350px;padding:10px 10px;float:left;">
	<div id="top" style="text-align:center;">
		<p><span ><fmt:message key="execute.print.hzd"></fmt:message></span></p>
		<p><span ><fmt:message key="sample.section" />:<b><fmt:message key="labDepartment.1300501"/></b></span></p>
	</div>
	<div id="patient">
		<div class="col-sm-12" style="width:100%;float:left;">
			<div class="col-sm-2" style="width:16.6%;float:left;">
				<label><fmt:message key="sample.id" />:</label>
			</div>
			<div class="col-sm-10" style="width:190px;height:60px;margin-top:0px;float:left;">
				<img src='<%=request.getContextPath() %>/barcode?&msg=58019256     &hrsize=0mm' style="align:left;width:180px;height:50px;"/>
			</div>
		</div>
	
		<div class="col-sm-12" style="width:100%;float:left;">
			<div class="col-sm-4" style="width:33.3%;float:left;">
				<span class="col-sm-6"><fmt:message key="patient.name" />:</span>
				<b class="col-sm-6 info" id="name" ></b>
			</div>
			<div class="col-sm-4" style="width:33.3%;float:left;">
				<span class="col-sm-6"><fmt:message key="patient.sex" />:</span>
				<b class="col-sm-6 info" id="sex" ></b>
			</div>
			<div class="col-sm-4" style="width:33.3%;float:left;">
				<span class="col-sm-4"><fmt:message key="patient.age" />:</span>
				<b class="col-sm-5 info" id="age" >44 </b>
				<span class="col-sm-3"><fmt:message key="patient.year" /></span>
			</div>
		</div>	
		
	</div>
	<div id="sample">
		<div class="col-sm-12">
			<span class="col-sm-2"><fmt:message key="sample.inspectionName" />:</span>
			<b class="col-sm-10 info" id="examine" ></b>
		</div>
		
		<div class="col-sm-12">
			<div class="col-sm-6" style="width:50%;float:left;padding:0px 0px;">
				<span class="col-sm-4"><fmt:message key="sample.sampleType" />:</span>
				<b class="col-sm-8 info" id="sampletype" ></b>
			</div>
			<div class="col-sm-6" style="width:50%;float:left;">
				<span class="col-sm-4"><fmt:message key="execute.print.sf" />:</span>
				<b class="col-sm-6 info" id="sf" >15.00 </b>
				<span class="col-sm-2"> <fmt:message key="execute.print.yuan" /></span>
			</div>
		</div>
		
		<div class="col-sm-12">
			<span class="col-sm-2"><fmt:message key="execute.print.cxsj" />:</span>
			<b class="col-sm-10 info" id="executetime" ></b>
		</div>
		
		<div class="col-sm-12">
			<span class="col-sm-2"><fmt:message key="execute.print.qbgsj" />:</span>
			<b class="col-sm-10 info" id="qbgsj" > </b>
		</div>
		
		<div class="col-sm-12">
			<span class="col-sm-2"><fmt:message key="execute.print.qbgdd" />:</span>
			<b class="col-sm-10 info" id="qbgdd"> </b>
		</div>
	</div>
	
	<div id="hints">
		<div class="col-sm-12" >
			<p style="font-size:10px; text-align:center;margin-bottom:2px;"  id="hint1" ></p>
			<p style="font-size:10px; text-align:center;margin-bottom:2px;"  id="hint2" ></p>
			<p style="font-size:10px; text-align:center;margin-bottom:2px;"  id="hint3" ></p>
		</div>
	</div>
</div>








	
		
