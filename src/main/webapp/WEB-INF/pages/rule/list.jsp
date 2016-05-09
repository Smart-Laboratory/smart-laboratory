<%@ include file="/common/taglibs.jsp"%>
<head>
    <title><fmt:message key="set.rule"/></title>
    <meta name="menu" content="SampleSet"/>
    <style type="text/css">
	table td {
		height:20px;
	}
	span.pagelinks {
		width:100%;	
		margin-top: -5px;
	}
	</style>
    <script>
       	$(function() {
       		$("#bag-select").change(function() {
       			var v = $("#bag-select").val();
       			location.href="../rule/list?bag="+v;
       		});
       		
       		$("#bag-select").val(${category});
       		
       		$(".activate").click(function() {
       			var id = $(this).next().html();
       			var value;
       			if($(this).prop("checked")) {
       				value = true;
       			} else {
       				value = false;
       			}
       			
       			$.post("../rule/ajax/activate", { id: id, state: value },
       				   function(data){
       				     //alert(data);
       				   });
       		});
       		
       		$("#rules tbody tr").each(function() {
       			if ($(this).find(".is_self").html() == "true") {
       				$(this).addClass('success');
       			}
       		});
       		
       		$("#rebuildkbase").click(function() {
       			$.get("<c:url value='/rule/list/rebuild'/>",
       				function() {
						alert("<fmt:message key='rule.alert.rebuild'/>");       				
    				}
       			);
       		});
		});
	</script>
</head>
<body>
<div class="col-sm-7">
<h1><fmt:message key='rule.title'/></h1>
<div class="form-inline" style="margin-bottom: 50px;">
	<div class="col-sm-6" style="margin-top:3px;">
		<label><b><fmt:message key="rule.bagSelect"/></b></label>
		<select id="bag-select" class="first" id="first" style="width:120px;">
			<option value="0"><fmt:message key="rule.all"/></option>
			<c:forEach var="bag" items="${bagList}">
				<option value='<c:out value="${bag.key}" />'><c:out value="${bag.value}" /></option>
			</c:forEach>
		</select>
	</div>
	<div class="col-sm-1"></div>
	<div class="col-sm-5">
		<button id="rebuildkbase" class="btn btn-info btn-sm" style="margin-right:10px;float:right">
			<fmt:message key="rule.rebuild.kbase"/>
		</button>
		<button id="addbutton" class="btn btn-success btn-sm" style="margin-right:10px;float:right" onclick="location.href='<c:url value="/rule/edit"/>'">
			<fmt:message key="rule.add"/>
		</button>
	</div>
</div>

<display:table name="ruleList" cellspacing="0" cellpadding="0" requestURI="/rule/list" partialList="true" 
    id="rules" defaultsort="5" defaultorder="ascending" sort="external"  class="table" size="${ruleList.fullListSize}" pagesize="${ruleList.objectsPerPage}">
    <display:column sortProperty="activate"  titleKey="rule.state" sortable="true" style="width:60px; ">
    	<input type="checkbox" style="width:20px;" class="activate" <c:if test="${disabled}">disabled</c:if> <c:if test="${rules.activate}">checked="checked"</c:if> >
    	<div style="display:none;"><c:out value="${rules.id}" /></div>
    	<div style="display:none;" class="is_self"><c:out value="${rules.selfCreate}" /></div>
    </display:column>
    <display:column property="name" maxLength="10" sortable="true" titleKey="rule.name"  escapeXml="false"
    	url="/rule/view" paramId="id" paramProperty="id" />
    <display:column property="resultName" maxLength="20" titleKey="rule.result" />
    <display:column property="createUser" sortable="true" titleKey="rule.createBy" />
    <display:column property="modifyTime" format="{0,date,yyyy-MM-dd hh:mm:ss}" sortable="true" titleKey="rule.modifyTime"  />
</display:table>
</div>
</body>
