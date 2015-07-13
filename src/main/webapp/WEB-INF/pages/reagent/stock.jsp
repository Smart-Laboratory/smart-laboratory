<%@ include file="/common/taglibs.jsp" %>

<head>
    <title><fmt:message key="reagent.stock"/></title>
    <meta name="menu" content="Reagent"/>
    <script type="text/javascript" src="../scripts/jquery.dataTables.min.js"></script>
    <link rel="stylesheet" type="text/css"  href="<c:url value='../styles/jquery.dataTables.min.css'/>" />
	
	<style>
		td.details-control {
		    background: url('../images/details_open.png') no-repeat center center;
		    cursor: pointer;
		}
		tr.shown td.details-control {
		    background: url('../images/details_close.png') no-repeat center center;
		}
	
	</style>
	
	<script type="text/javascript">
	function format ( d ) {
	    return '<table cellpadding="5" cellspacing="0" border="0" style="padding-left:50px;">'+
	        '<tr>'+
	            '<td>Full name:</td>'+
	            '<td>'+d.name+'</td>'+
	        '</tr>'+
	        '<tr>'+
	            '<td>Extension number:</td>'+
	            '<td>'+d.extn+'</td>'+
	        '</tr>'+
	        '<tr>'+
	            '<td>Extra info:</td>'+
	            '<td>And any further details here (images etc)...</td>'+
	        '</tr>'+
	    '</table>';
	}
	$(document).ready(function() {
	    var table = $('#example').DataTable( {
	        "ajax": "/ajax/getReagent",
	        "columns": [
	            {
	                "class":          'details-control',
	                "orderable":      false,
	                "data":           null,
	                "defaultContent": ''
	            },
	            { "data": "name" },
	            { "data": "place" },
	            { "data": "brand" },
	            { "data": "unit" },
	            { "data": "price" },
	            { "data": "address" },
	            { "data": "condition" },
	            { "data": "temp"},
	            { "data": "isself"}
	        ],
	        "order": [[1, 'asc']]
	    } );
	    $('#example tbody').on('click', 'td.details-control', function () {
	        var tr = $(this).closest('tr');
	        var row = table.row( tr );
	        if ( row.child.isShown() ) {
	            row.child.hide();
	            tr.removeClass('shown');
	        }
	        else {
	            row.child( format(row.data()) ).show();
	            tr.addClass('shown');
	        }
	    } );
	} );
	</script>

</head>
<body>
<div class="col-sm-10">

<table id="example" class="display" cellspacing="0" width="100%">
    <thead>
        <tr>
        <th><fmt:message key="reagent.name"/></th>
		<th><fmt:message key="reagent.place"/></th>
		<th><fmt:message key="reagent.brand"/></th>
		<th><fmt:message key="reagent.unit"/></th>
		<th><fmt:message key="reagent.price"/></th>
		<th><fmt:message key="reagent.address"/></th>
		<th><fmt:message key="reagent.condition"/></th>
		<th><fmt:message key="reagent.temperature"/></th>
        <th><fmt:message key="reagent.isselfmade"/></th>
        </tr>
    </thead>
</table>

</div>
</body>