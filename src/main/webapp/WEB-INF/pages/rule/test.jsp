<%@ include file="/common/taglibs.jsp"%>
<head>
<title>Let's Test Bootstrap</title>
<meta name="menu" content="RulesManage"/>
<script type="text/javascript" src="../scripts/jquery-ui.min.js"></script>

<script type="text/javascript">
	$(function(){
		$("#datepicker").datepicker();
		
	});

</script>


</head>
<body>
<div class="col-sm-6">
<form>
  <div class="form-group">
    <label for="exampleInputEmail1">Email address</label>
    <input type="email" class="form-control" id="exampleInputEmail1" placeholder="Email">
  </div>
  <div class="form-group">
    <label for="exampleInputPassword1">Password</label>
    <input type="password" class="form-control" id="exampleInputPassword1" placeholder="Password">
  </div>
 
</form>
</div>


<div class="col-sm-6">
<table class="table table-striped table-hover">
<tr>
	<td>name</td>
	<td><input type="text" /> </td>
	<td>name</td>
	<td><input type="text" /> </td>

</tr>
<tr>
	<td>name</td>
	<td><input type="text" /> </td>
	<td>date</td>
	<td><input type="text" id="datepicker" /></td>
</tr>
</table>
</div>
</body>
