<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp" %>
<head>
	<link rel="stylesheet" type="text/css"  href="<c:url value='/styles/jquery-ui.min.css'/>" />
    <link rel="stylesheet" type="text/css"  href="<c:url value='/styles/bootstrap.min.css'/>" />
    
    <script type="text/javascript" src="../../scripts/jquery-2.1.4.min.js"></script>
	<script type="text/javascript" src="../../scripts/bootstrap.min.js"></script>
	<script type="text/javascript" src="../../scripts/jquery-ui.min.js"></script>
	<script type="text/javascript" src="../../scripts/highcharts.js"></script>
	
	
</head>
<style>
	.containt{
		margin:10px 20px;
	}
	.sendData{
	}
	th{
		text-align:center;
	}
</style>
<script type="text/javascript">
$(function(){
	var dataUrl="";
	if("${allCheck}"==1){
		dataUrl = "../../manage/sampleHandover/ajax/sendListPrintData?allCheck=1&from="+"${from}"+"&to="+"${to}"+"&receiveLab="+"${section}";
	}else{
		dataUrl = "../../manage/sampleHandover/ajax/sendListPrintData?sendListNo="+"${sendListNo}";
	}
	$.get(dataUrl,function(data){
		data = jQuery.parseJSON(data);
		var tbody = $("#sendListData tbody")
		if(data!=null && data != ""){
			for(var i=0;i<data.length;i++){
				var da = data[i];
				tbody.append("<tr>");
				tbody.append("<td>"+da.xh+"</td>");
				tbody.append("<td>"+da.doctadviseno+"</td>");
				tbody.append("<td>"+da.xm+"</td>");
				tbody.append("<td>"+da.jzkh+"</td>");
				tbody.append("<td>"+da.examinaim+"</td>");
				tbody.append("<td>"+da.labdepartment+"</td>");
				tbody.append("<td>"+da.sendtime+"</td>");
				tbody.append("<td>"+da.sender+"</td>");
				tbody.append("</tr>");
			}
			
		}
	});
})


</script>
<html>
<div class="containt">
	<div class="col-sm-12" id="topContent">
		<h4 class="col-sm-12" style="text-align:center;"><b>门诊标本送出清单</b></h4>
		<div class="row" style="border-bottom:1px solid #000000;">
			<label class=" control-label no-padding-right" for="sendListNo" style="margin-left:40px;">标本清单号： </label>
				<span id="sendListNo" name="sendListNo" >${sendListNo }</span>
			<label class=" control-label no-padding-right" for="sender" style="margin-left:40px;">送出科室： </label>
				<span id="sender" name="sender" >门诊化验室</span>
			<label class=" control-label no-padding-right" for="receiveSection" style="margin-left:40px;">送达科室： </label>
			<span id="receiveSection" name="sender" >${section}</span>
		</div>
		
		<div class="row sendData" >
			<table id="sendListData" name="sendListData" class="table table-striped" style="font-size:12px;text-align:center;">
				<thead>
					<tr>
						<th style="width:5%">序</th>
						<th style="width:10%">医嘱号</th>
						<th style="width:8%">姓名</th>
						<th style="width:12%">就诊卡号</th>
						<th style="width:25%">检验目的</th>
						<th style="width:12%">送检科室</th>
						<th style="width:18%">送出时间</th>
						<th style="width:10%">送出人</th>
					<tr>
				</thead>
				<tbody>
					<c:forEach items="${list }" var="sl">
						<tr>
							<td>1</td>
							<td>${sl.DOCTADVISENO }</td>
							<td>${sl.PATIENTNAME }</td>
							<td>${sl.EXAMINAIM }</td>
							<td>${sl.SENDTIME}</td>
							<td>${sl.SENDER }</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</div>
</html>