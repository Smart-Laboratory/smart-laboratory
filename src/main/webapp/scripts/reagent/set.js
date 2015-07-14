	var table;
	var editFlag = false;
	$(function () {
 
        $('#start_date').datetimepicker();
 
        var tpl = $("#tpl").html();
        //预编译模板
        var template = Handlebars.compile(tpl);
 
        table = $('#example').DataTable({
            /*ajax: {
                url: "/list.jsp"
            },
            serverSide: true,
            columns: [
                {"data": "name"},
                {"data": "position"},
                {"data": "salary"},
                {"data": "start_date"},
                {"data": "office"},
                {"data": "extn"},
                {"data": null}
            ],
            columnDefs: [
                {
                    targets: 6,
                    render: function (a, b, c, d) {
                        var context =
                        {
                            func: [
                                {"name": "修改", "fn": "edit(\'" + c.name + "\',\'" + c.position + "\',\'" + c.salary + "\',\'" + c.start_date + "\',\'" + c.office + "\',\'" + c.extn + "\')", "type": "primary"},
                                {"name": "删除", "fn": "del(\'" + c.name + "\')", "type": "danger"}
                            ]
                        };
                        var html = template(context);
                        return html;
                    }
                }
 
            ],*/
            "language": {
	        	"sProcessing": "处理中...",
				"sLengthMenu": "共 _MENU_ 条记录",
				"sZeroRecords": "没有匹配记录",
				"sInfo": "显示第 _START_ 至 _END_ 条记录，共 _TOTAL_ 条",
				"sInfoEmpty": "显示第 0 至 0 条记录，共 0 条",
				"sInfoFiltered": "(由 _MAX_ 条记录过滤)",
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
            "dom": "<'row'<'col-xs-2'l><'#mytool.col-xs-4'><'col-xs-6'f>r>" +
                    "t" +
                    "<'row'<'col-xs-6'i><'col-xs-6'p>>",
            initComplete: function () {
                $("#mytool").append('<button id="datainit" type="button" class="btn btn-primary btn-sm">增加基础数据</button>&nbsp');
                $("#mytool").append('<button type="button" class="btn btn-default btn-sm" data-toggle="modal" data-target="#myModal">添加</button>');
                $("#datainit").on("click", initData);
            }
 
        });
 
        $("#save").click(add);
 
        $("#initData").click(initSingleData);
 
	});
 
    /**
     * 初始化基础数据
     */
    function initData() {
        var flag = confirm("本功能将添加数据到数据库，你确定要添加么？");
        if (flag) {
            $.get("/objects.txt", function (data) {
                var jsondata = JSON.parse(data);
                $(jsondata.data).each(function (index, obj) {
                    ajax(obj);
                });
            });
        }
    }
 
    /**
     * 初始化基础数据
     */
    function initSingleData() {
        $("#name").val("http://dt.thxopen.com");
        $("#position").val("ShiMen");
        $("#salary").val("1");
        $("#start_date").val("2015/04/01");
        $("#office").val("Home");
        $("#extn").val("001");
    }
 
    /**
     * 清除
     */
    function clear() {
        $("#name").val("").attr("disabled",false);
        $("#position").val("");
        $("#salary").val("");
        $("#start_date").val("");
        $("#office").val("");
        $("#extn").val("");
        editFlag = false;
    }
 
    /**
     * 添加数据
     **/
    function add() {
        var addJson = {
            "name": $("#name").val(),
            "position": $("#position").val(),
            "salary": $("#salary").val(),
            "start_date": $("#start_date").val(),
            "office": $("#office").val(),
            "extn": $("#extn").val()
        };
 
        ajax(addJson);
    }
 
    /**
     *编辑方法
     **/
    function edit(name,position,salary,start_date,office,extn) {
        console.log(name);
        editFlag = true;
        $("#myModalLabel").text("修改");
        $("#name").val(name).attr("disabled",true);
        $("#position").val(position);
        $("#salary").val(salary);
        $("#start_date").val(start_date);
        $("#office").val(office);
        $("#extn").val(extn);
        $("#myModal").modal("show");
    }
 
    function ajax(obj) {
        var url ="/add.jsp" ;
        if(editFlag){
            url = "/edit.jsp";
        }
        $.ajax({
            url:url ,
            data: {
                "name": obj.name,
                "position": obj.position,
                "salary": obj.salary,
                "start_date": obj.start_date,
                "office": obj.office,
                "extn": obj.extn
            }, success: function (data) {
                table.ajax.reload();
                $("#myModal").modal("hide");
                $("#myModalLabel").text("新增");
                clear();
                console.log("结果" + data);
            }
        });
    }
 
    /**
     * 删除数据
     * @param name
     */
    function del(name) {
        $.ajax({
            url: "/del.jsp",
            data: {
                "name": name
            }, success: function (data) {
                table.ajax.reload();
                console.log("删除成功" + data);
            }
        });
    }
