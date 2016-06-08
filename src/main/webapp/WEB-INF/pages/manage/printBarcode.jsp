<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp"%>
<head>
	<link rel="stylesheet" type="text/css"  href="<c:url value='/styles/jquery-ui.min.css'/>" />
    <link rel="stylesheet" type="text/css"  href="<c:url value='/styles/bootstrap.min.css'/>" />
    
    <script type="text/javascript" src="../scripts/jquery-2.1.4.min.js"></script>
	<script type="text/javascript" src="../scripts/bootstrap.min.js"></script>
	<script type="text/javascript" src="../scripts/jquery-ui.min.js"></script>
</head>

<style>
.info{
	padding-left:10px;
}
.sfont{
	font-size:12px;
}
</style>
<script type="text/javascript">

$(function(){
	var o =${html};
	
	$("#date").html(o.html);
	
	/* $.get("../manage/getlaborder",{tests:tests},function(data){
		if(data!=null){
			var array = data;
			
			
			data = data[0];
			
			$("[name='section']").html(data.section);
			$("[name='hossection']").html(data.hossection);
			
			$("[name='name']").html(data.patientname);
			
			$("#sex").html(data.sex);
			$("#age").html("44");
			$("[name='examine']").html(data.examitem);
			$("[name='sampletype']").html(data.sampletype);
			$("#sf").html(data.price);
			var executetime = new Date(data.executetime).Format("yyyy-MM-dd hh:mm:ss");
			$("[name='executetime']").html(executetime);
			$("[name='sexecutetime']").html(new Date(data.executetime).Format("yyyy/MM/dd"));
			$("#qbgsj").html(data.qbgsj);
			$("#qbgdd").html(data.qbgdt);
			$("[name='sampleid'").html(data.laborder);
			$("#blh").html(data.blh);
			$("[name='sampleno']").html(data.sampleno);
		}
			
	})
	
	
	
	
	$("#hint1").html("*法定节假日(如春节等)仪器故障报告时间顺延*");
	$("#hint2").html("*抽血时请带就诊卡，凭此单或就诊卡去检验报告*");
	$("#hint3").html("再挂号窗口留下核对密码，或者ucmed.cn//zszy.html下载掌上浙一软件或关注微信账号查询检查报告"); */
//	$("#").html("");
//	$("#").html("");
//	$("#").html("");
	
	 
})



</script>

<div id='date'>
<div style='background:#999;width:450px;height:350px;padding:10px 10px; margin-top:10px;float:left;'>
	<div id='top' style='text-align:center;'>
		<p><span ><fmt:message key='execute.print.hzd'></fmt:message></span></p>
		<p><span ><fmt:message key='sample.section' />:<b name='section'></b></span></p>
	</div>
	<div id='patient'>
		<div class='col-sm-12' style='width:100%;float:left;'>
			<div class='col-sm-4' style='width:33.3%;float:left;'>
				<span class='col-sm-6'><fmt:message key='patient.blh' />:</span>
				<b class='col-sm-6 info' id='blh' ></b>
			</div>
			<div class='col-sm-2' style='width:16.6%;float:left;'>
				<label><fmt:message key='sample.id' />:</label>
			</div>
			<div class='col-sm-6' style='width:190px;height:60px;margin-top:0px;float:left;'>
				<img src='<%=request.getContextPath() %>/barcode?&msg=58019256     &hrsize=0mm' style='align:left;width:180px;height:50px;'/>
				<div style='font-size:10px;margin-left:20px;'><span id='sampleid' name='sampleid'></span></div>
			</div>
		</div>
	
		<div class='col-sm-12' style='width:100%;float:left;'>
			<div class='col-sm-4' style='width:33.3%;float:left;'>
				<span class='col-sm-6'><fmt:message key='patient.name' />:</span>
				<b class='col-sm-6 info' id='name' name='name'></b>
			</div>
			<div class='col-sm-4' style='width:33.3%;float:left;'>
				<span class='col-sm-6'><fmt:message key='patient.sex' />:</span>
				<b class='col-sm-6 info' id='sex' ></b>
			</div>
			<div class='col-sm-4' style='width:33.3%;float:left;'>
				<span class='col-sm-4'><fmt:message key='patient.age' />:</span>
				<b class='col-sm-5 info' id='age' >44 </b>
				<span class='col-sm-3'><fmt:message key='patient.year' /></span>
			</div>
		</div>	
		
	</div>
	<div id='sample'>
		<div class='col-sm-12'>
			<span class='col-sm-2'><fmt:message key='sample.inspectionName' />:</span>
			<b class='col-sm-10 info' id='examine' name='examine'></b>
		</div>
		
		<div class='col-sm-12'>
			<div class='col-sm-6' style='width:50%;float:left;padding:0px 0px;'>
				<span class='col-sm-4'><fmt:message key='sample.sampleType' />:</span>
				<b class='col-sm-8 info' id='sampletype' name='sampletype'></b>
			</div>
			<div class='col-sm-6' style='width:50%;float:left;'>
				<span class='col-sm-4'><fmt:message key='execute.print.sf' />:</span>
				<b class='col-sm-6 info' id='sf' >15.00 </b>
				<span class='col-sm-2'> <fmt:message key='execute.print.yuan' /></span>
			</div>
		</div>
		
		<div class='col-sm-12'>
			<span class='col-sm-2'><fmt:message key='execute.print.cxsj' />:</span>
			<b class='col-sm-10 info' id='executetime' name='executetime'></b>
		</div>
		
		<div class='col-sm-12'>
			<span class='col-sm-2'><fmt:message key='execute.print.qbgsj' />:</span>
			<b class='col-sm-10 info' id='qbgsj' > </b>
		</div>
		
		<div class='col-sm-12'>
			<span class='col-sm-2'><fmt:message key='execute.print.qbgdd' />:</span>
			<b class='col-sm-10 info' id='qbgdd'> </b>
		</div>
	</div>
	
	<div id='hints'>
		<div class='col-sm-12' >
			<p style='font-size:10px; text-align:center;margin-bottom:2px;'  id='hint1' ></p>
			<p style='font-size:10px; text-align:center;margin-bottom:2px;'  id='hint2' ></p>
			<p style='font-size:10px; text-align:center;margin-bottom:2px;'  id='hint3' ></p>
		</div>
	</div>
</div>

<div  style='background:#999;width:450px;height:160px;padding:5px 5px;margin-top:15px;float:left;'>
	<div class='col-sm-6' style='width:50%;float:left;'>
		<div class='col-sm-12' style='width:99%;float:left;'>
			<span class='col-sm-4'  id='sName' style='font-size:15px;padding-top:5px;width:33.3%;float:left;'><b name='name'></b></span>
			<span class='col-sm-8' id='sExamitem' name='examine' style='width:66.6%;float:left;'></span>
		</div>
		<div class='col-sm-12' style='width:99%;float:left;'>
			<span class='col-sm-5' id='sDate' name='sexecutetime' style='width:40%;float:left;'></span>
			<span class='col-sm-2' id='sDate' name='sampletype' style='width:20%;float:left;'></span>
			<span class='col-sm-5' id='ssection' name='section'  style='width:40%;float:left;'></span>
		</div>
		<div class='col-sm-12' style='width:99%;float:left;'>
			<span name='hosSection'></span>
		</div>
		<div class='col-sm-12' style='width:190px;height:55px;margin-top:0px;float:left;'>
			<img src='<%=request.getContextPath() %>/barcode?&msg=58019256     &hrsize=0mm' style='align:left;width:180px;height:50px;'/>
		</div>
		<div class='col-sm-12' style='width:99%;float:left;'>
			<span class='col-sm-4'  name='sampleid'></span>
			<span class='col-sm-8' name='sampleno' style='text-align:right;'></span>
		</div>
	</div>

</div>



</div>













	
		
