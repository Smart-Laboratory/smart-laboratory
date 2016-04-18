<%@ include file="/common/taglibs.jsp"%>
<head>
    <title><fmt:message key="set.description"/></title>
    <meta name="menu" content="SampleSet"/>
    <link rel="stylesheet" type="text/css"  href="<c:url value='../styles/jquery-ui.min.css'/>" />
    <script type="text/javascript" src="../scripts/jquery-ui.min.js"></script>
    <script type="text/javascript" src="../scripts/i18n/grid.locale-cn.js"></script>
    <script type="text/javascript" src="../scripts/jquery.jqGrid.js"></script>
	<link rel="stylesheet" type="text/css"  href="<c:url value='../styles/ui.jqgrid.css'/>" />
    
    
    <style type="text/css">
	table td {
		height:20px;
	}
	span.pagelinks {
		width:100%;	
		margin-top: -5px;
	}
	</style>
    <script type="text/javascript">
       	$(function() {
       		var width=$("#right").width();
       		jQuery("#diagnosis").jqGrid({
       			url:"../diagnosis/getInfo?id="+$("#desBag").val(),
       			datatype:"json",
       			width:width,
       			colNames:['','\u8BCA\u65AD\u540D\u79F0'],
       			colModel:[
       			          {name:'id',index:'id',hidden:true},
       			          {name:'diagnosisName',index:'diagnosisName',width:'90%',editable:true}
       			          ],
       			rowNum:20,
       			rownumbers:true,
       			pager:'#pager',
       			sortname:'id',
       			height:'100%',
       			editurl:'../diagnosis/edit?desId='+$("#desBag").val(),
       			viewrecords: true,
       			caption:'<h5><b>\u8BCA\u65AD\u4FE1\u606F</b></h5>' 
       		});
       		jQuery("#diagnosis").jqGrid("navGrid",'#pager',{edit:true,add:true,del:true});
       		
       		$("#bag-select").change(function() {
       			var v = $("#bag-select").val();
       			location.href="../description/list?bag="+v;
       		});
       		
       		$("#bag-select").val($("#desBag").val());
       		
       		$(".activate").click(function(name,v) {
       			var id = $(this).next().html();
       			var value;
       			if($(this).prop("checked")==true) {
       				value = true;
       			} else {
       				value = false;
       			}
       			
       			$.post("../description/ajax/activate", { id: id, state: value },
       				   function(data){
       				     //alert(data);
       			});
       		});
       		
       		$("#rules tbody tr").each(function() {
       			if ($(this).find(".is_self").html() == "true") {
       				$(this).addClass('success');
       			}
       		});
       		
       		
       		
       		
		});
	</script>
</head>
<body>
<input type="hidden" id="desBag" value="${category }" />
<div class="col-sm-6">
<h1><fmt:message key='des.title'/></h1>
<div class="form-inline" style="margin-bottom: 50px;">
	<div class="col-sm-6" style="margin-top:3px;">
		<label><b><fmt:message key="des.bagSelect"/></b></label>
		<select id="bag-select" class="first" id="first" style="width:120px;">
			<option value="0"><fmt:message key="des.all"/></option>
			<c:forEach var="bag" items="${bagList}">
				<option value='<c:out value="${bag.key}" />'><c:out value="${bag.value}" /></option>
			</c:forEach>
		</select>
	</div>
	<div class="col-sm-1"></div>
	<div class="col-sm-5">
		
		<button id="addbutton" class="btn btn-success btn-sm" style="margin-right:10px;float:right" onclick="location.href='<c:url value="/description/edit?bag=${category}"/>'">
			<fmt:message key="des.add"/>
		</button>
	</div>
</div>

<display:table name="ruleList" cellspacing="0" cellpadding="0" requestURI="/description/list" partialList="true" 
    id="description" defaultsort="5" defaultorder="ascending" sort="external"  class="table" size="${ruleList.fullListSize}" pagesize="${ruleList.objectsPerPage}">
    <display:column sortProperty="activate"  titleKey="des.state" sortable="true" style="width:60px; ">
    	<input id="da" type="checkbox" style="width:20px;" class="activate" <c:if test="${disabled}">disabled</c:if> <c:if test="${description.activate}">checked="checked"</c:if> >
    	<div style="display:none;"><c:out value="${description.id}" /></div>
    	<%-- <div style="display:none;" class="is_self"><c:out value="${rules.selfCreate}" /></div> --%>
    </display:column>
    <display:column property="name" maxLength="10" sortable="true" titleKey="des.name"  escapeXml="false"
    	url="/description/view" paramId="id" paramProperty="id" />
    <display:column property="description" maxLength="20" titleKey="des.description" />
    <display:column property="createUser" sortable="true" titleKey="des.createBy" />
    <display:column property="modifyUser" format="{0,date,yyyy-MM-dd hh:mm:ss}" sortable="true" titleKey="des.modifyTime"  />
</display:table>
</div>

<div id="right" class="col-sm-3" style="margin-top:10px;">
	<div>
		<table id="diagnosis" class="table table-condensed table-striped"></table>
		<div id="pager"></div>
	</div>
</div>



</body>
