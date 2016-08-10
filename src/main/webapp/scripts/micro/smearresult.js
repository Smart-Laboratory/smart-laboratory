/**
 * Created by zhou on 2016/7/5.
 * 微生物基础信息Script
 */

var TSLAB=TSLAB ||{};
TSLAB.Custom=(function(){
    var cache = {
        listUrl:"../micro/smearresult/getList"
    };
    var public = {
        initGrid:function(){
            $('#tableList').jqGrid({
                caption: "涂片结果设置",
                url:cache.listUrl ,
                //postData:{type:typeid},
                mtype: "GET",
                datatype: "json",
                colNames: ['id','type', '编号','类型','名称','英文名'],
                colModel: [
                    { name: 'id', index: 'id', width: 60,hidden:true},
                    { name: 'type', index: 'type', width: 60,hidden:true},
                    { name: 'indexid', index: 'indexid', width: 100},
                    { name: 'typename', index: 'typename', width: 100},
                    { name: 'name', index: 'name', width: 200},
                    { name: 'english', index: 'english', width: 200 }
                ],
                loadComplete : function() {
                    var table = this;
                    setTimeout(function(){
                        updatePagerIcons(table);
                    }, 0);
                },
                ondblClickRow: function(id){
                    public.Edit();
                },
                viewrecords: true,
                //multiselect: true,
                shrinkToFit: true,
                altRows:true,
                autowidth:true,
                //height: 300,
                height: "100%",
                rowNum: 10,
                rowList:[10,30,50],
                rownumbers: true, // 显示行号
                rownumWidth: 35, // the width of the row numbers columns
                pager: "#pager",//分页控件的id
                subGrid: false//是否启用子表格
            });
        },
        updatePagerIcons:function(table){
            var replacement =
            {
                'ui-icon-seek-first' : 'ace-icon fa fa-angle-double-left bigger-140',
                'ui-icon-seek-prev' : 'ace-icon fa fa-angle-left bigger-140',
                'ui-icon-seek-next' : 'ace-icon fa fa-angle-right bigger-140',
                'ui-icon-seek-end' : 'ace-icon fa fa-angle-double-right bigger-140'
            };
            $('.ui-pg-table:not(.navtable) > tbody > tr > .ui-pg-button > .ui-icon').each(function(){
                var icon = $(this);
                var $class = $.trim(icon.attr('class').replace('ui-icon', ''));
                if($class in replacement) icon.attr('class', 'ui-icon '+replacement[$class]);
            })
        },
        search: function () {
            var query = $('#query').val()||'';
            $('#tableList').jqGrid('setGridParam',{
                url: cache.listUrl,
                datatype : 'json',
                //发送数据
                postData : {"query":query },
                page : 1
            }).trigger('reloadGrid');//重新载入
        },
        Add:function(){
            public.clearData();
            layer.open({
                type: 1,
                area: ['520px','300px'],
                fix: false, //不固定
                maxmin: false,
                shade:0.6,
                title: "添加涂片结果",
                content: $("#addDialog"),
                btn:["保存","取消"],
                yes: function(index, layero){
                    //$("#addForm").submit();
                    $.ajax({
                        url:'../micro/smearresult/save',
                        type:"POST",
                        dataType:"json",
                        data:$('#addForm').serialize(),
                        success:function(data){
                            console.log(data);
                            if(parseInt(data.success)==0){
                                layer.close(index);
                                public.search();
                            }else{
                                layer.alert(data.success);
                            }
                        }
                    });
                    //layer.close(index); //如果设定了yes回调，需进行手工关闭
                }
            });
        },
        Edit:function(){
            var rowId = $("#tableList").jqGrid('getGridParam','selrow');
            var rowData = $("#tableList").jqGrid('getRowData',rowId);
            if(!rowId || rowId =='' || rowId==null){
                layer.msg("请先选择要编辑的数据",{icon:2});
                return false;
            }
            //设置数据

            $('#id').val(rowData.id);
            $('#name').val(rowData.name);
            $('#indexid').val(rowData.indexid);
            $('#english').val(rowData.english);
            $('#type').val(rowData.type);
            layer.open({
                type: 1,
                area: ['520px','300px'],
                fix: false, //不固定
                maxmin: false,
                shade:0.6,
                title: "编辑数据",
                content: $("#addDialog"),
                btn:["保存","取消"],
                yes: function(index, layero){
                    $("#id").removeAttr("disabled");
                    $.ajax({
                        url:'../micro/smearresult/save',
                        type:"POST",
                        dataType:"json",
                        data:$('#addForm').serialize(),
                        success:function(data){
                            if(parseInt(data.success)==0){
                                layer.close(index);
                                public.search();
                            }else{
                                layer.alert(data.success);
                            }
                        }
                    });
                    //layer.close(index); //如果设定了yes回调，需进行手工关闭
                }
            });
        },
        Delete:function(){
            var id = $('#tableList').jqGrid('getGridParam','selrow');
            if(id==null || id==''){
                layer.msg('请先选择要删除的数据', {icon: 2,time: 1000});
                return false;
            }
            layer.confirm('确定删除选择数据？', {icon: 2, title:'警告'}, function(index){
                $.post('../micro/smearresult/delete',{id:id},function(data) {
                    jQuery("#tableList").jqGrid('delRowData',id);
                });
                layer.close(index);
            });
        },
        clearData:function(){
            $("#addDialog").find("input,textarea").each(function(){
                this.value = "";
            });
        }

    };
    return public;
})();


$(function(){
    var height = document.documentElement.clientHeight;

    //alert(1)
    if(height>150) $('#ullist').height(height-150);
    var isfirst = true;

    //keyPress 回车检索
    $("#query").keypress(function(e){
        if (e.keyCode == 13){
            TSLAB.Custom.search();
        }
    });
    TSLAB.Custom.initGrid();
    //$(window).triggerHandler('resize.jqGrid');

});