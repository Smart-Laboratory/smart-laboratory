<%@ include file="/common/taglibs.jsp"%>

<head>
	<title><fmt:message key="set.rule"/></title>
    <meta name="menu" content="SampleSet"/>
    <script>
       	$(function() {
       		$("#sample-select").change(function() {
       			var v = $("#sample-select").val();
       			location.href="../index/list?sample="+encodeURIComponent(v);
       		});
       		$("#searchButton").click(function() {
       			var search = $("#searchText").val();
       			location.href="../index/list?text="+search;
       		});
       		
       		$("#searchText").bind('keyup', function(event){
       		    if (event.keyCode=="13") {
       		    	var search = $("#searchText").val();
           			location.href="../index/list?text="+search;
       		    }
       		 });
       		
       		$("#sample-select").val('${sample}');
       		
       		$("#addbutton").click(function(){
       			self.location='<c:url value="/index/edit"/>';
       		});
		});
	</script>
	<style type="text/css">
	table td {
		height:20px;
	}
	</style>
</head>
<div class="col-sm-7">
<h1><fmt:message key='index.title'/></h1>
<div class="form-inline" style="margin-bottom:5px;">
	<div class="col-sm-4" style="margin-top:3px;">
	<label><b><fmt:message key="index.option.one"/></b></label>
	<select id="sample-select" style="width:120px;">
		<option value=""><fmt:message key="index.all"/></option>
		<c:forEach var="s" items="${sampleList}">
			<option value='<c:out value="${s.key}" escapeXml="false"/>'><c:out value="${s.value}" /></option>
		</c:forEach>
	</select>
	</div>
	<div class="col-sm-5">
		<div class="col-sm-9 input-group-sm">
		<input id="searchText" class="form-control" type="text" value="${searchText}" placeholder="<fmt:message key='index.search.holder'/>"/>
		</div>
		<button id="searchButton" class="btn btn-info btn-sm" ><fmt:message key="search"/></button>
	</div>
	<div class="col-sm-3">
	<button id="addbutton" class="btn btn-success btn-sm" style="margin-left:30px;"><fmt:message key="index.add"/></button>
	</div>
</div>

<display:table name="indexList" cellspacing="0" cellpadding="0" requestURI="/index/list" 
    id="indexs" class="table" partialList="true" sort="external"  
    size="${indexList.fullListSize}" pagesize="${indexList.objectsPerPage}">
    <display:column property="name" sortable="true" titleKey="index.name" style="width: 35%"
    	url="/index/view" paramId="id" paramProperty="id"/>
    <display:column property="indexId" sortable="true" titleKey="index.indexId" />
    <display:column property="sampleFrom" sortable="true" titleKey="index.sampleFrom" />
    <display:column property="ruleCount" titleKey="index.ruleCount" />
</display:table>
</div>
