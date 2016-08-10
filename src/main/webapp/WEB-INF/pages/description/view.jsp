<%@ include file="/common/taglibs.jsp"%>

<head>
<title><fmt:message key="set.description"/></title>
<meta name="menu" content="SampleSet"/>

<script type="text/javascript">
function deleteConfirm() {
	if (confirm('<fmt:message key="confirm.delete" />')) {
		location.href='../description/delete?id=<c:out value="${description.id}" />';
	}	
}
</script>
</head>
<style type="text/css">
table {
	font-size:14px;border:solid #add9c0; border-width:1px 0px 0px 1px;
}
td {
	border:solid #add9c0; border-width:0px 1px 1px 0px; padding:10px 10px 10px 10px;
	line-height:20px;
}
th {
	width:90px;
	border:solid #add9c0; border-width:0px 1px 1px 0px; padding:10px 10px 10px 10px;
}
</style>

<div class="col-sm-7">
	<h1><fmt:message key='des.view'/></h1>
	<div class="col-sm-8" style="float:left;">
		<table>
			<tr>
				<th class="left"><fmt:message key="des.category" /> :</th>
				<td><c:out value="${bag}" /></td>
			</tr>
			<tr>
				<th class="left"><fmt:message key="des.name" /> :</th>
				<td><c:out value="${description.name}" /></td>
			</tr>
			<tr>
				<th class="left"><fmt:message key="des.type" /> :</th>
				<td><c:out value="${description.type}" /></td>
			</tr>
			<tr>
				<th class="left"  valign="top"><fmt:message key="des.description" /> :</th>
				<td><p style="line-height:150%;"><c:out value="${description.description}" /></p></td>
			</tr>
			<tr>
				<th class="left"><span style="letter-spacing:6px;"><fmt:message key="des.createBy" /></span>:</th>
				<td><c:out value="${description.createUser}" /></td>
			</tr>
			<tr>
				<th class="left"><fmt:message key="des.createTime" /> :</th>
				<td><c:out value="${description.createTime}" /></td>
			</tr>
			<tr>
				<th class="left" ><span style="letter-spacing:6px;"><fmt:message key="des.modifyBy" /></span>:</th>
				<td><c:out value="${description.modifyUser}" /></td>
			</tr>
			<tr>
				<th class="left"><fmt:message key="des.modifyTime" /> :</th>
				<td><c:out value="${description.modifyTime}" /></td>
			</tr>
			<tr>
				<th></th>
				<td>
				<c:if test="${canEdit}">
					<button id="editBtn" class="btn btn-success"
					onclick="location.href='../description/edit?id=<c:out value="${description.id}" />'">
					<fmt:message key="button.edit" /></button>
					
					<button id="deleteBtn" class="btn btn-danger" 
					onclick="deleteConfirm()">
					<fmt:message key="button.delete" /></button>
				</c:if>
					<button id="returnBtn" class="btn btn-info" 
					onclick="history.go(-1)">
					<fmt:message key="button.return" /></button>
				</td>
			</tr>
		</table>
	</div>
	
</div>
