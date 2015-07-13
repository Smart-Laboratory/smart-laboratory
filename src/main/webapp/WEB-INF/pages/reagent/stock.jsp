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
	    	d.html + '</table>';
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
	        "language": {
	        	"sProcessing": "\u5904\u7406\u4E2D...",
				"sLengthMenu": "\u663E\u793A _MENU_ \u9879\u7ED3\u679C",
				"sZeroRecords": "\u6CA1\u6709\u5339\u914D\u7ED3\u679C",
				"sInfo": "\u663E\u793A\u7B2C _START_ \u81F3 _END_ \u9879\u7ED3\u679C\uFF0C\u5171 _TOTAL_ \u9879",
				"sInfoEmpty": "\u663E\u793A\u7B2C 0 \u81F3 0 \u9879\u7ED3\u679C\uFF0C\u5171 0 \u9879",
				"sInfoFiltered": "(\u7531 _MAX_ \u9879\u7ED3\u679C\u8FC7\u6EE4)",
				"sInfoPostFix": "",
				"sSearch": "\u641C\u7D22:",
				"sUrl": "",
				"sEmptyTable": "\u8868\u4E2D\u6570\u636E\u4E3A\u7A7A",
				"sLoadingRecords": "\u8F7D\u5165\u4E2D...",
				"sInfoThousands": ",",
				"oPaginate": {
					"sFirst": "\u9996\u9875",
				    "sPrevious": "\u4E0A\u9875",
				    "sNext": "\u4E0B\u9875",
				    "sLast": "\u672B\u9875"
				},
				"oAria": {
				    "sSortAscending": ": \u4EE5\u5347\u5E8F\u6392\u5217\u6B64\u5217",
				    "sSortDescending": ": \u4EE5\u964D\u5E8F\u6392\u5217\u6B64\u5217"
				}
            },
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
        <th></th>
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