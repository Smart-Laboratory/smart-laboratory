function addTestComparison(obj) {

    var crowId = $("#leftGrid").jqGrid('getGridParam', 'selrow');
    if (!crowId || crowId == '' || crowId == null) {
        layer.alert("请先选择需要对照检验项目的客户", {icon: 1, title: "提示"});
        return false;
    }
    var rowId = $("#s3list").jqGrid('getGridParam', 'selrow');
    if (!rowId || rowId == '' || rowId == null) {
        layer.alert("请先选择要对照的检验项目", {icon: 1, title: "提示"});
        return false;
    }
    var rowData = $("#s3list").jqGrid('getRowData', rowId);

    $.post('/dsf/controlTestItems/editComparison', {
        edit: 'add',
        localIndex: $(obj).attr("relid"),
        loaclName:$(obj).attr("relname"),
        customerid: rowData.customerid,
        customeritems:rowData.customeritems,
        customeritemsname:rowData.customeritemsname,
        id:rowData.id,
        dzid:rowData.dzid,
    }, function (data) {
        var json = jQuery.parseJSON(data);
        if (parseInt(json.success) == 1) {
            jQuery("#s3list").trigger("reloadGrid");
            layer.msg("客户的检验项目" +rowData.customeritemsname+ "和本地检验项目" + name + "对照添加成功", {icon: 1, time: 1000});
        } else {
            layer.msg("客户的检验项目" +rowData.customeritemsname+ "和本地检验项目" + name + "对照添加失败", {icon: 2, time: 1000});
        }
    });

}

function removeTestComparison(obj, name, indexid) {
    var crowId = $("#leftGrid").jqGrid('getGridParam', 'selrow');
    if (!crowId || crowId == '' || crowId == null) {
        layer.alert("请先选择需要对照检验项目的客户", {icon: 1, title: "提示"});
        return false;
    }

    var rowId = $("#s3list").jqGrid('getGridParam', 'selrow');
    if (!rowId || rowId == '' || rowId == null) {
        layer.alert("请先选择要对照的检验项目", {icon: 1, title: "提示"});
        return false;
    }
    var rowData = $("#s3list").jqGrid('getRowData', rowId);

    $.post('/dsf/controlTestItems/editComparison', {
        edit: 'delete',
        localIndex: indexid,
        loaclName:name,
        customerid: rowData.customerid,
        customeritems:rowData.customeritems,
        customeritemsname:rowData.customeritemsname,
        id:rowData.id,
        dzid:rowData.dzid,
    }, function (data) {
        var json = jQuery.parseJSON(data);
        if (parseInt(json.success) == 1) {
            jQuery("#s3list").trigger("reloadGrid");
            $("#testTable").empty();
            layer.msg("客户的检验项目" +rowData.customeritemsname+ "和本地检验项目" + name + "对照删除成功", {icon: 1, time: 1000});
        } else {
            layer.msg("客户的检验项目" +rowData.customeritemsname+ "和本地检验项目" + name + "对照删除失败", {icon: 2, time: 1000});
        }
    });
}
//对照表查询
function search() {
    var id = $('#leftGrid').jqGrid('getGridParam', 'selrow');
    if (id == null || id == 0) {
        layer.msg('请先选择要添加联系人的客户', {icon: 2, time: 1000});
        return false;
    }
    var rowData = $('#leftGrid').jqGrid('getRowData', id);

    var query = $('#query').val() || '';
    jQuery("#s3list").jqGrid('setGridParam', {
        url: "/dsf/controlTestItems/data",
        datatype: 'json',
        //发送数据
        postData: {
            "query": query,
            "customerid": rowData.customerid,
        },
        page: 1
    }).trigger('reloadGrid');//重新载入
}


function getList(lab) {
    var isFirstTime = true;
    var clientHeight = $(window).innerHeight();
    var height = clientHeight - $('#head').height() - $('.footer-content').height() - 190;
    var mygrid = $("#s3list").jqGrid({
        caption: "客户检验项目对照",
        mtype: "GET",
        datatype: "json",
        width: $('.leftContent').width() - 10,
        colNames: ['id', 'dzid', 'CUSTOMERID', '客户检验项目名称', '客户检验项目ID', '本地检验项目ID', '本地检验项目名称'],
        colModel: [
            {name: 'id', index: 'id', width: 60, hidden: true, key: true},
            {name: 'dzid', index: 'dzid', width: 60, hidden: true},
            {name: 'customerid', index: 'customerid', hidden: true},
            {name: 'customeritemsname', index: 'customeritemsname', width: 60, sortable: false},
            {name: 'customeritems', index: 'customeritems', width: 50, sortable: false},
            {name: 'localitems', index: 'localitems', width: 50, sortable: false},
            {name: 'localitemsname', index: 'localitemsname', width: 60, sortable: false},
        ],
        loadComplete: function () {
            var table = this;
            setTimeout(function () {
                updatePagerIcons(table);
            }, 0);
        },
        onSelectRow: function (id) {
            var ret = $("#s3list").jqGrid('getRowData', id);
            $("#testTable").empty();
            var html = '';
            $.get('/dsf/controlTestItems/ajax/getTests', {id: ret.id, customerid: ret.customerid}, function (data) {
                var json = jQuery.parseJSON(data);
                html += "<tr><th>关联项目</th><th>&nbsp;</th></tr>";
                if(json.name!=undefined){
                    html += "<tr><td>" + json.name + "</td><td><button class='btn btn-minier btn-danger' onclick='removeTestComparison(this,\"" + json.name + "\",\"" + json.id + "\")'>删除</button></td></tr>"
                }
                $("#testTable").html(html);
            });
        },
        viewrecords: true,
        shrinkToFit: true,
        altRows: true,
        height: height,
        rowNum: 20,
        rownumbers: true, // 显示行号
        rownumWidth: 35, // the width of the row numbers columns
        pager: "#s3pager"
    });
    jQuery("#s3list").jqGrid('navGrid', '#s3pager', {
        edit: false,
        add: false,
        del: false,
        search: false,
        refresh: false
    });
    jQuery("#s3list").jqGrid('navButtonAdd', "#s3pager", {
        caption: "",
        title: "",
        buttonicon: 'ui-icon-pin-s',
        onClickButton: function () {
            mygrid[0].toggleToolbar()
        }
    });
}


$(function () {
    $('#beginTime').datetimepicker().next().on(ace.click_event, function () {
        $(this).prev().focus();
    });
    $('#endTime').datetimepicker().next().on(ace.click_event, function () {
        $(this).prev().focus();
    });
    initLeftGrid();
    $("#YlxhForm").Validform({
        tiptype: 4,
        callback: function () {

        }
    });

    $("#searchIndex").autocomplete({
        source: function (request, response) {
            $.ajax({
                url: "/ajax/searchTest",
                dataType: "json",
                data: {
                    name: request.term
                },
                success: function (data) {
                    var html='';
                    html += "<tr><th>关联项目</th><th>&nbsp;</th></tr>";
                    response($.map(data, function (result) {
                        if(result.name!=undefined){
                            html += "<tr><td>" + result.name + "</td><td><button class='btn btn-minier btn-danger' relid="+result.id+" relname="+result.name+" onclick='addTestComparison(this)'>添加</button></td></tr>"
                        }
                        $("#testTable").html(html);

                    }));
                }
            });
        },
        minLength: 1,
    });


    $(document).keydown(function (e) {
        if (e.keyCode == 40) {
            var s = jQuery("#s3list").jqGrid('getGridParam', 'selrow');
            var next = $("#" + s).next("tr").attr("id");

            if (next != null) {
                $("#s3list").setSelection(s, false);
                $("#s3list").setSelection(next, true);
            } else {
                var page = parseInt(jQuery("#s3list").jqGrid('getGridParam', 'page'));
                page = page + 1;
                var records = parseInt(jQuery("#s3list").jqGrid('getGridParam', 'records'));
                var total = (records - records % 20) / 20 + 1;
                if (page <= total) {
                    $("#s3list").setGridParam({page: page}).trigger("reloadGrid");
                }
            }
            e.prthisDefault();
        } else if (e.keyCode == 38) {
            var s = jQuery("#s3list").jqGrid('getGridParam', 'selrow');
            var prev = $("#" + s).prev("tr").attr("id");

            if (prev != null) {
                $("#s3list").setSelection(s, false);
                $("#s3list").setSelection(prev, true);
            }
            e.prthisDefault();
        }
    });
    getList($("#lab").val());

    labChange = function (select) {
        var code = $(select).children().attr("title");
        $.ajax({
            type: 'POST',
            url: "../audit/labChange?lab=" + code
        });
        $("#lab").val(code);
        $("#labText").html($(select).children().html());
        jQuery("#s3list").jqGrid('setGridParam', {
            url: "/dsf/testObjective/data",
            datatype: 'json',
            postData: {"lab": code},
            page: 1
        }).trigger('reloadGrid');
    }
});

function initLeftGrid() {
    var clientHeight = $(window).innerHeight();
    var height = clientHeight - $('#head').height() - $('.footer-content').height() - 175;

    $('.scroll-content').height(height + 55);
    //设置表格宽度
    $(window).on('resize.jqGrid', function () {
        $('#rightGrid').jqGrid('setGridWidth', $(".leftContent").width(), false);
    })
    $("#leftGrid").jqGrid({
        url: '/dsf/customer/getCustomerList',
        datatype: "json",
        height: height,
        shrinkToFit: false,
        regional: 'cn',
        width: $('.leftContent').width(),
        colNames: ['客户ID', '客户名称', '客户地址'],
        colModel: [
            {name: 'customerid', index: 'customerid', width: 50, sorttype: 'text', sortable: true},
            {name: 'customername', index: 'customername', width: 200},
            {name: 'address', index: 'address', width: 200},
        ],
        loadComplete: function () {
            var table = this;
            setTimeout(function () {
                updatePagerIcons(table);
            }, 0);
        },
        onSelectRow: function () {
            search();
        },
        //multiselect : true,
        viewrecords: true,
        rownumbers: true, // 显示行号
        rownumWidth: 35, // the width of the row numbers columns
    });
    $(window).triggerHandler('resize.jqGrid');
}

function searchCustomer() {
    var query = $('#searchCustomer').val() || '';
    jQuery("#leftGrid").jqGrid('setGridParam', {
        url: "/dsf/customer/getCustomerList",
        datatype: 'json',
        //发送数据
        postData: {"query": query},
        page: 1
    }).trigger('reloadGrid');//重新载入
}

function autoComparison(){
    var crowId = $("#leftGrid").jqGrid('getGridParam', 'selrow');
    if (!crowId || crowId == '' || crowId == null) {
        layer.alert("请先选择需要对照检验项目的客户", {icon: 1, title: "提示"});
        return false;
    }
    var rowData = $("#leftGrid").jqGrid('getRowData', crowId);

    $.post('/dsf/controlTestItems/autoComparison', {
        customerid: rowData.customerid,
    }, function (data) {
        var json = jQuery.parseJSON(data);
        if (parseInt(json.success) == 1) {
            jQuery("#s3list").trigger("reloadGrid");
            layer.msg("客户的检验项目" +rowData.customeritemsname+ "和本地检验项目" + name + "对照添加成功", {icon: 1, time: 1000});
        } else {
            layer.msg("客户的检验项目" +rowData.customeritemsname+ "和本地检验项目" + name + "对照添加失败", {icon: 2, time: 1000});
        }
    });
}