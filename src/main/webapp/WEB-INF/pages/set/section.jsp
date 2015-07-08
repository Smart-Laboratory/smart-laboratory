<%@ include file="/common/taglibs.jsp" %>

<head>
    <title><fmt:message key="set.hospital"/></title>
    
    <script type="text/javascript" src="../scripts/jquery-2.1.4.min.js"></script>
    <script type="text/javascript" src="../scripts/jquery.json-2.3.min.js"></script>
    
	<link rel="stylesheet" type="text/css"  href="<c:url value='/styles/ruleLib.css'/>" /> 
	
<style type="text/css">  

  
</style> 
<script type="text/javascript">
	function editSection(id){
		var curCode = $("#" + id).children("#code");
        curCode.html('<input id="codein" type="text" value="'+curCode.html()+'" />');
        var curName = $("#" + id).children("#name");
        curName.html('<input id="namein" type="text" value="'+curName.html()+'" />');
        $("#" + id).children().eq(3).find("#editButton").css("display","none");
        $("#" + id).children().eq(3).find("#saveButton").css("display","inline");
        
        /*
        $("#" + id ).children().each(function(index,domEle){
        	if($(domEle).is($("#code"))){
        		$(domEle).html('<input type="text" value="'+$(domEle).html()+'" />');        		
        	}
        });*/		
	}
	
	function saveSection(id){
		var code=$("#" + id).children("#code").children().eq(0).val();
		var name = $("#" + id).children("#name").children().eq(0).val();
		$.post("<c:url value='/set/section/edit'/>",{id:id,code:code,name:name},function(data) {
			if(data){
				$("#" + id).children("#code").html(code);
				$("#" + id).children("#name").html(name);
				$("#" + id).children().eq(3).find("#editButton").css("display","inline");
		        $("#" + id).children().eq(3).find("#saveButton").css("display","none");
				alert("finish!");
			}
			else{
				alert("false");
			}
		});
	}
	
	function deleteSection(id){
		$.post("<c:url value='/set/section/delete'/>",{id:id},function(data) {
			if(data){
				$("#" + id).remove();
				alert("finish!");
			}
			else{
				alert("false");
			}
		});
	}
	
	
	
</script>
	
</head>
<body>


<div class="col-sm-10">
	<h2>Section-info</h2>

<table id="tblList" cellspacing="0" cellpadding="0" class="table table-condensed table-striped table-hover">
	<thead>
	<tr >
		<td >section.id</td>
		<td>code</td>
		<td>name</td>
		<td></td>
	</tr>
	</thead>
	<tbody>
	<c:if test="${list != null}">
	    <c:forEach var="section" items="${list}" >
		    	<tr id="${section.id}">
		    		<td><c:out value="${section.id}"/></td>
		    		<td id="code"><c:out value="${section.code}"/></td>
					<td id="name"><c:out value="${section.name}"/></td>
					<td>
						<button id="editButton" type="button" class="btn btn-info" onclick="editSection(${section.id})">
							<fmt:message key='button.edit'/>
						</button>
						<button id="saveButton" type="button" style="display:none" class="btn btn-danger" onclick="saveSection('${section.id}')">
							<fmt:message key='button.save'/>
						</button>
						<button id="deleteButton" type="button" class="btn btn-danger" onclick="deleteSection('${section.id}')">
							<fmt:message key='button.delete'/>
						</button>
					</td>
		    	</tr> 
	    </c:forEach>
	</c:if>
	</tbody>
</table>
    
   
    

    
    
    <button class="btn btn-info  btn-sm" class="accordion-toggle" style="margin-right:10px;float:left" data-toggle="collapse" data-parent="#accordion2" href="#collapseOne">zhankai</button>
  	<div class="accordion" id="accordion2" style="margin-right:10px;float:left">  
 		<div class="accordion-group">
 			<div id="collapseOne" class="accordion-body collapse" style="height: 0px;">  
      			<div class="accordion-inner">
      				<form id="addSectionForm" action="<c:url value='/set/section/add'/>" method="POST">
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