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
	        	"sProcessing": "处理中...",
				"sLengthMenu": "显示 _MENU_ 项结果",
				"sZeroRecords": "没有匹配结果",
				"sInfo": "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
				"sInfoEmpty": "显示第 0 至 0 项结果，共 0 项",
				"sInfoFiltered": "(由 _MAX_ 项结果过滤)",
				"sInfoPostFix": "",
				"sSearch": "搜索:",
				"sUrl": "",
				"sEmptyTable": "表中数据为空",
				"sLoadingRecords": "载入中...",
				"sInfoThousands": ",",
				"oPaginate": {
					"sFirst": "首页",
				    "sPrevious": "上页",
				    "sNext": "下页",
				    "sLast": "末页"
				},
				"oAria": {
				    "sSortAscending": ": 以升序排列此列",
				    "sSortDescending": ": 以降序排列此列"
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