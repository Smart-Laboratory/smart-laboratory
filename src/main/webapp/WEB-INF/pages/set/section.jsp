<%@ include file="/common/taglibs.jsp" %>

<head>
    <title><fmt:message key="set.hospital"/></title>
    
    <script type="text/javascript" src="<c:url value='/scripts/jquery.json-2.3.min.js'/> "></script>
	<script type="text/javascript" src="<c:url value='/scripts/jquery.form.js'/> "></script>
	
	<script type="text/javascript">
		function edit(id){
			
		}
	
	</script>
	
</head>
<body>


<div class="col-sm-10">
	<h2>Section-info</h2>

	<div id="actions" class="btn-group">
        <a class="btn btn-primary" href="<c:url value='/sectionform/add'/>">
            <i class="icon-plus icon-white"></i> <fmt:message key="button.add"/></a>

        <a class="btn btn-default" href="<c:url value='/home'/>">
            <i class="icon-ok"></i> <fmt:message key="button.done"/></a>
    </div>
    
    
    

    <display:table name="sectionList" cellspacing="0" cellpadding="0" requestURI=""
                   defaultsort="1" id="sections" pagesize="25" class="table table-condensed table-striped table-hover" export="true">
        <display:column property="id" escapeXml="true" sortable="true" titleKey="section.id"
                        style="width: 10%"/>
        <display:column property="code" escapeXml="true" sortable="true" titleKey="section.code"
                        style="width: 35%"/>
        <display:column property="name" escapeXml="true" sortable="true" titleKey="section.name" style="width: 25%"/>
        <button id = "xiugai" name="xiugai" class="alter alter-info"  onclick="edit(id)">XiuGai</button>

    </display:table>
    
    <button class="btn btn-info  btn-sm" class="accordion-toggle" style="margin-right:10px;float:left" data-toggle="collapse" data-parent="#accordion2" href="#collapseOne">zhankai</button>
  	<div class="accordion" id="accordion2" style="margin-right:10px;float:left">  
 		<div class="accordion-group">
 			<div id="collapseOne" class="accordion-body collapse" style="height: 0px;">  
      			<div class="accordion-inner">
      				<form id="addSectionForm" action="<c:url value='/section/add'/>" method="POST">
      				<div>
      					<label for="code" style="margin-left:10px;">Code</label>
      					<input type="text" id="code" name="code" class="span1">
      				</div>
      				<div>
      					<label for="name" style="margin-left:10px;">Name</label>
      					<input type="text" id="name" name="name" class="span1"/>
      				</div>
      				<div>
      					<button type="submit" class="btn btn-success" style="height:30px;width:150px;" onclick="submit();">Tijiao</button>
      				</div>
      				</form>
      			</div>
      		</div>
      	</div>
    </div>

</div>


</div>
</body>