<%@ include file="/common/taglibs.jsp"%>

<head>
	<title><fmt:message key="menu.pb" /></title>
	
</head>

<style>
select {
border: solid 1px #cfcfcf;
cursor: pointer;
position: relative;
font-size:12px;
margin-bottom:1px;
margin-top:1px;
width: 50px;
height: 25px;
padding: 0;
list-style-type: none;
}
table tr td {
	width:50px;
}
table tr th {
	width:50px;
}
div .fixed{ 
overflow-y: scroll; 
overflow-x: hidden;
width:auto;
height: 480px; 
border: 0px solid #009933; 
} 
input[type="checkbox"] {
    margin: 0 0 0;
}
</style>

<select id="labSelect" onchange="labChange(this)" class="form-control" style="margin-bottom:5px;">
		<span ><c:forEach var="depart" items="${departList}">
			<option value='<c:out value="${depart.key}" />'><c:out value="${depart.value}" /></option>
		</c:forEach></span>
	</select>

<%-- <c:choose>
	<c:when test="${size==0}">
		<div id="dialog" title="<fmt:message key='pb.section.select' />" style="text-align:left;">
			<select id="section" style="margin-left:20px;float:left;height:30px;width:120px;">
				<option value="1300100"><fmt:message key="labDepartment.1300100"/></option>
				<option value="1300200"><fmt:message key="labDepartment.1300200"/></option>
				<option value="1300400"><fmt:message key="labDepartment.1300400"/></option>
				<option value="1300500"><fmt:message key="labDepartment.1300500"/></option>
				<option value="1300501"><fmt:message key="labDepartment.1300501"/></option>
				<option value="1300600"><fmt:message key="labDepartment.1300600"/></option>
				<option value="1300700"><fmt:message key="labDepartment.1300700"/></option>
				<option value="1300800"><fmt:message key="labDepartment.1300800"/></option>
			</select>
			<button id="btn" class="btn btn-success" style="margin-left:30px;"><fmt:message key='button.submit' /></button>
		</div>
	</c:when>
	<c:otherwise>
		
		<div style="width:600px;">
			<input type="text" id="date">
			<button id="changeMonth" class="btn btn-info" style="margin-left:10px;"><fmt:message key='pb.changemonth' /></button>
			<button id="shiftBtn2" class="btn btn-success"><fmt:message key='pb.save' /></button>
			<button id="shiftBtn" class="btn btn-success"><fmt:message key='button.submit' /></button>
		</div>
		
		<table style="margin-top:10px;font-size:12px;text-align:center;" border="1px;">
		<thead>
		<c:forEach items="${arrArray[0]}" var="head" varStatus="status">
		<c:if test="${status.index==0 }"><th style='background:#7FFFD4' id="day${status.index }">${head}</th> </c:if>
		<c:if test="${status.index>=1 }"><th style='background:#7FFFD4'>${head}</th> </c:if>
		</c:forEach>
		</thead>
		</table>
		<div class="fixed">
		<table style="font-size:12px;text-align:center;" border="1px;">
		<c:forEach items="${arrArray}" var="arr" varStatus="status">
		<c:if test="${status.index >= 1}">
		<tr>
			<c:forEach items="${arr}" var="shift" varStatus="tdstatus">
			<c:if test="${tdstatus.index==0 and (status.index%6==0 || status.index%7==0) }">
				<th style='background:#7CFC00' id="day${status.index }">${shift}</th> </c:if>
			<c:if test="${tdstatus.index==0 and (status.index%6!=0 || status.index%7!=0) }">
				<th style='background:#7FFFD4' id="day${status.index }">${shift}</th> </c:if>
			<c:if test="${tdstatus.index== (size-2)}">
				<th><a onclick='checkShift(" + j + ")'>${shift}</a></th> </c:if>
			<c:if test="${tdstatus.index== (size-1)}">
				<th><a onclick='randomShift(" + j + ")'>${shift}</a></th> </c:if>
			<c:if test="${status.index>=1 and (status.index<(size-2))}">
				<td><p style='margin-top:5px;'>${shift}</p></td></c:if>
			</c:forEach>
		</tr>
		</c:if>
		</c:forEach>
		</table>
		</div>
		<div style="display:none;">	
			<c:forEach var="dsh" items="${dshList}">
				<div id='<c:out value="${dsh.day}"/>'><c:out value="${dsh.shift}"/></div>
			</c:forEach>
		</div>
	</c:otherwise>
</c:choose> --%>

<c:choose>
	<c:when test="${size==0}">
		<div id="dialog" title="<fmt:message key='pb.section.select' />" style="text-align:left;">
			<select id="section" style="margin-left:20px;float:left;height:30px;width:120px;">
				<option value="1300100"><fmt:message key="labDepartment.1300100"/></option>
				<option value="1300200"><fmt:message key="labDepartment.1300200"/></option>
				<option value="1300400"><fmt:message key="labDepartment.1300400"/></option>
				<option value="1300500"><fmt:message key="labDepartment.1300500"/></option>
				<option value="1300501"><fmt:message key="labDepartment.1300501"/></option>
				<option value="1300600"><fmt:message key="labDepartment.1300600"/></option>
				<option value="1300700"><fmt:message key="labDepartment.1300700"/></option>
				<option value="1300800"><fmt:message key="labDepartment.1300800"/></option>
			</select>
			<button id="btn" class="btn btn-success" style="margin-left:30px;"><fmt:message key='button.submit' /></button>
		</div>
	</c:when>
	<c:otherwise>
		
		<div style="width:600px;">
			<input type="text" id="date">
			<button id="changeMonth" class="btn btn-info" style="margin-left:10px;"><fmt:message key='pb.changemonth' /></button>
			<button id="shiftBtn2" class="btn btn-success"><fmt:message key='pb.save' /></button>
			<button id="shiftBtn" class="btn btn-success"><fmt:message key='button.submit' /></button>
		</div>
		
		<table style="margin-top:10px;font-size:12px;text-align:center;" border="1px;">
		<thead>
		<c:forEach items="${arrArray[0]}" var="head">
		<%-- <input id="test" value="${head}"/> --%>
		<th><script>  
	unescape("111111111111");
	</script> 
		</th>
		</c:forEach>
		</thead>
		</table>
		<div class="fixed">
		<table style="font-size:12px;text-align:center;" border="1px;">
		<c:forEach items="${arrArray}" var="arr" varStatus="status">
		<c:if test="${status.index >= 1}">
		<tr>
			<c:forEach items="${arr}" var="shift">${shift}</c:forEach>
		</tr>
		</c:if>
		</c:forEach>
		</table>
		</div>
		<div style="display:none;">	
			<c:forEach var="dsh" items="${dshList}">
				<div id='<c:out value="${dsh.day}"/>'><c:out value="${dsh.shift}"/></div>
			</c:forEach>
		</div>
	</c:otherwise>
</c:choose>
