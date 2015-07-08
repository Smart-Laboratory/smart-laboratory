<%@ include file="/common/taglibs.jsp" %>

<head>
    <title><fmt:message key="set.section"/></title>
    <meta name="menu" content="SampleSet"/>
    
    <script type="text/javascript" src="../scripts/jquery-2.1.4.min.js"></script>
    <script type="text/javascript" src="../scripts/jquery.json-2.3.min.js"></script>
    <script type="text/javascript" src="../scripts/set/section.js"></script>
    
	<link rel="stylesheet" type="text/css"  href="<c:url value='/styles/ruleLib.css'/>" /> 
</head>
<div class="col-sm-10">
<h2><fmt:message key="set.section"/></h2>
<div class="btn-group">
    <button type="button" class="btn btn-primary" onclick="addSection()">
		<fmt:message key='button.add'/>
	</button>
</div>
<table class="table table-condensed table-striped" style="font-size:20px;">
	<thead><tr>
		<th><fmt:message key="section.id"/></th>
		<th><fmt:message key="section.code"/></th>
		<th><fmt:message key="section.name"/></th>
		<th></th>
	</tr></thead>
	<tbody id="tbody">
	<c:if test="${list != null}">
	    <c:forEach var="section" items="${list}" >
		    	<tr id="${section.id}">
		    		<td><c:out value="${section.id}"/></td>
		    		<td id="code"><c:out value="${section.code}"/></td>
					<td id="name"><c:out value="${section.name}"/></td>
					<td>
						<button id="editButton" type="button" class="btn btn-info btn-sm" onclick="editSection(${section.id})">
							<fmt:message key='button.edit'/>
						</button>
						<button id="saveButton" type="button" style="display:none" class="btn btn-info btn-sm" onclick="saveSection(${section.id})">
							<fmt:message key='button.save'/>
						</button>
						<button id="deleteButton" type="button" class="btn btn-danger btn-sm" onclick="deleteSection(${section.id})">
							<fmt:message key='button.delete'/>
						</button>
					</td>
		    	</tr> 
	    </c:forEach>
	</c:if>
	</tbody>
</table>
</div>
