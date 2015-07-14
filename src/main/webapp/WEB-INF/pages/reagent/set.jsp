6<%@ include file="/common/taglibs.jsp" %>

<head>
    <title><fmt:message key="reagent.set"/></title>
    <meta name="menu" content="Reagent"/>
    <script type="text/javascript" src="../scripts/jquery.dataTables.min.js"></script>
    <link rel="stylesheet" type="text/css"  href="<c:url value='../styles/jquery.dataTables.min.css'/>" />
    <link rel="stylesheet" type="text/css"  href="<c:url value='../styles/bootstrap-datetimepicker.min.css'/>" />
    <script type="text/javascript" src="../scripts/handlebars-v3.0.1.js"></script>
    <script type="text/javascript" src="../scripts/bootstrap-datetimepicker.min.js"></script>
	<script type="text/javascript" src="../scripts/reagent/set.js"></script>
    <script id="tpl" type="text/x-handlebars-template">
    {{#each func}}
    <button type="button" class="btn btn-{{this.type}} btn-sm" onclick="{{this.fn}}">{{this.name}}</button>
    {{/each}}
	</script>
</head>
<body>
<div class="col-sm-10">
	<div class="container">
	    <table id="example" class="table table-striped table-bordered">
	        <thead>
	        <tr>
	            <th>1</th>
	            <th>2</th>
	            <th>3</th>
	            <th>4</th>
	            <th>5</th>
	            <th>6</th>
	            <th>7</th>
	        </tr>
	        </thead>
	    </table>
	</div>
 
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	    <div class="modal-dialog">
	        <div class="modal-content">
	            <div class="modal-header">
	                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
	                        aria-hidden="true">&times;</span></button>
	                <h4 class="modal-title" id="myModalLabel">add</h4>
	            </div>
	            <div class="modal-body">
	                <div class="form-group">
	                    <input type="text" class="form-control" id="name" placeholder="1">
	                </div>
	                <div class="form-group">
	                    <input type="text" class="form-control" id="position" placeholder="2">
	                </div>
	                <div class="form-group">
	                    <input type="text" class="form-control" id="salary" placeholder="3">
	                </div>
	                <div class="form-group">
	                    <input type="text" class="form-control" id="start_date" placeholder="4"
	                           data-date-format="yyyy/mm/dd">
	                </div>
	                <div class="form-group">
	                    <input type="text" class="form-control" id="office" placeholder="5">
	                </div>
	                <div class="form-group">
	                    <input type="text" class="form-control" id="extn" placeholder="6">
	                </div>
	            </div>
	            <div class="modal-footer">
	                <button type="button" class="btn btn-info" id="initData">add</button>
	                <button type="button" class="btn btn-default" data-dismiss="modal">close</button>
	                <button type="button" class="btn btn-primary" id="save">save</button>
	            </div>
	        </div>
	    </div>
	</div>
</div>
</body>