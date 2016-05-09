<%@ include file="/common/taglibs.jsp"%>

<head>
	<title><fmt:message key="set.rule"/></title>
    <meta name="menu" content="SampleSet"/>
	<style type="text/css">
	table td {
		height:20px;
	}
	</style>
</head>

<div class="col-sm-7">
<h1><fmt:message key='result.title'/></h1>
<display:table name="resultList" cellspacing="0" cellpadding="0" requestURI="/result/list" 
    id="results" defaultsort="4" defaultorder="ascending" class="table" partialList="true" sort="external"  
    size="${resultList.fullListSize}" pagesize="${resultList.objectsPerPage}">
    <display:column property="content" maxLength="25" sortable="true" titleKey="result.content" style="width: 50%"
    	url="/result/view" paramId="id" paramProperty="id"/>
    <display:column property="category" sortable="true" titleKey="result.category" />
    <display:column property="createUser" sortable="true" titleKey="result.createBy" />
    <display:column property="modifyTime" format="{0,date,yyyy-MM-dd hh:mm:ss}" sortable="true" titleKey="result.modifyTime" style="width: 23%;" />
</display:table>
</div>
<script type="text/javascript">
    highlightTableRows("results");
</script>