<%@ include file="/common/taglibs.jsp"%>

<head>
<title><fmt:message key="set.rule"/></title>
<meta name="menu" content="SampleSet"/>

<script type="text/javascript">
function deleteConfirm() {
	if (confirm('<fmt:message key="confirm.delete" />')) {
		location.href='../index/delete?id=<c:out value="${index.id}" />';
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
	<h1><fmt:message key='index.view'/></h1>
	<div class="col-sm-8" style="float:left;">
		<table>
			<tr>
				<th class="left"><fmt:message key="index.indexId" /> :</th>
				<td><c:out value="${index.indexId}" /></td>
			</tr>
			<tr>
				<th class="left"><fmt:message key="index.name" /> :</th>
				<td><c:out value="${index.name}" /></td>
			</tr>
			<tr>
				<th class="left"><fmt:message key="index.sampleFrom" /> :</th>
				<td><c:out value="${index.sampleFrom}" /></td>
			</tr>
			<tr>
				<th class="left"><fmt:message key="index.unit" /> :</th>
				<td><c:out value="${index.unit}" /></td>
			</tr>
			<tr>
				<th class="left"><fmt:message key="index.type" /> :</th>
				<td><c:out value="${index.type}" /></td>
			</tr>
			<tr>
				<th class="left"><fmt:message key="index.algorithm" /> :</th>
				<td><c:out value="${index.algorithm}" /></td>
			</tr>
			<tr>
				<th class="left"><fmt:message key="index.history" /> :</th>
				<td><c:out value="${index.history}" /></td>
			</tr>
			<tr>
				<th class="left"><fmt:message key="index.method" /> :</th>
				<td><c:out value="${index.method}" /></td>
			</tr>
			<tr>
				<th class="left"  valign="top"><fmt:message key="index.description" /> :</th>
				<td><p style="line-height:150%;"><c:out value="${index.description}" /></p></td>
			</tr>
			<tr>
				<th class="left"><fmt:message key="rule.guide" /> :</th>
				<td><c:out value="${index.guide}" /></td>
			</tr>
			<tr>
				<th class="left"><span style="letter-spacing:6px;"><fmt:message key="rule.createBy" /></span>:</th>
				<td><c:out value="${index.createUser}" /></td>
			</tr>
			<tr>
				<th class="left"><fmt:message key="rule.createTime" /> :</th>
				<td><c:out value="${index.createTime}" /></td>
			</tr>
			<tr>
				<th class="left" ><span style="letter-spacing:6px;"><fmt:message key="rule.modifyBy" /></span>:</th>
				<td><c:out value="${index.modifyUser}" /></td>
			</tr>
			<tr>
				<th class="left"><fmt:message key="rule.modifyTime" /> :</th>
				<td><c:out value="${index.modifyTime}" /></td>
			</tr>
			<tr>
				<th></th>
				<td>
				<c:if test="${canEdit}">
					<button id="editBtn" class="btn"
					onclick="location.href='../index/edit?id=<c:out value="${index.id}" />'">
					<fmt:message key="button.edit" /></button>
					
					<button id="deleteBtn" class="btn" 
					onclick="deleteConfirm()">
					<fmt:message key="button.delete" /></button>
				</c:if>
					<button id="returnBtn" class="btn" 
					onclick="history.go(-1)">
					<fmt:message key="button.return" /></button>
				</td>
			</tr>
		</table>
	</div>
	<div class="col-sm-4" style="background-color: white;border: 1px solid #E1E1E1;padding: 5px 0px 10px 0px;" class="border-radius-6 border-shadow"">
		<h4 style="padding-left:14px;font-family:Microsoft YaHei;">
			<fmt:message key="rule.relate" />
		</h4>
		<div style="border-top: 1px solid #E1E1E1;min-height:180px; ">
			<c:forEach var="rule" items="${rulesList}">
				<div class="tag-section">
					<a class="selbtn" style="width:180px;" href="../rule/view?id=<c:out value="${rule.key}"/>"> 
					 	<span class="tag-name"><c:out value="${rule.value}" /></span>
				</a></div>
			</c:forEach>
		</div>
	</div>
</div>
