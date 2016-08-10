<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp" %>

<head>
    <title><fmt:message key="reagent.combo"/></title>
    <link rel="stylesheet" type="text/css"  href="<c:url value='../styles/jquery-ui.min.css'/>" />
    
    
	<link rel="stylesheet" type="text/css"  href="<c:url value='../styles/ui.jqgrid.css'/>" />
	<link rel="stylesheet" type="text/css"  href="<c:url value='../styles/bootstrap.min.css'/>" />
	
	
	<script type="text/javascript" src="../scripts/jquery-2.1.4.min.js"></script>
	<script type="text/javascript" src="../scripts/jquery-ui.min.js"></script>
	<script type="text/javascript" src="../scripts/bootstrap.min.js"></script>
	<script type="text/javascript" src="../scripts/jquery.jqGrid.js"></script>
	<script type="text/javascript" src="../scripts/i18n/grid.locale-cn.js"></script>
	<script type="text/javascript" src="../scripts/reagent/in.js"></script>
</head>	
<script type="text/javascript">

$(function(){
		$("#submit").click(function(){
			var begintime = $("#begintime").val();
			var ts = $("#ts").val();
			var fs = $("#fs").val();
			var endtime = $("#endtime").val();
			var reagent = $("#reagent").val();	
		
			
			if(begintime!=null && endtime!=null && ts!=null && fs!=null && reagent!=null){
		    		$('#printFrame').empty();
			    	$("#printFrame").append("<iframe id='iframe_print' name='iframe_print' frameborder=0 style='background-color:transparent' width='99%' height='93%' src=\"../reagent/smallprint?begintime=" + begintime + "&endtime=" + endtime + "&ts=" + ts + "&fs=" + fs + "&reagent="+reagent+"\"/>");
					$("#printDialog").dialog("open");
			}else
				alert("输入的信息不完整！");
		});
		
	})
	
	
</script>
	


<body>
<div id="mid" class="col-sm-10">
<div>
<label for="reagent">试剂名称：</label>
<input id="reagent"  />
</div>
<div>
<label for="begintime">生效时间：</label>
<input id="begintime"  />
</div>
<div>
<label for="endtime">失效时间：</label>
<input id="endtime"  />
</div>
<div>
<label for="ts">批号：</label>
<input id="ts"  />
</div>
<div>
<label for="fs">份数：</label>
<input id="fs"  />
</div>
<button id="submit">确认</button>

</div>

<div id="printDialog" align="left">
	<button class="btn btn-success" onclick="document.getElementById('iframe_print').contentWindow.print();"><fmt:message key="print"/></button>
	<div id="printFrame" style="height:500px;"></div>
</div>
</body>