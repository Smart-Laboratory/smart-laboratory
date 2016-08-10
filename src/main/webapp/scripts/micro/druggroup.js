/**
 * Created by zhou on 2016/7/5.
 * 微生物基础信息Script
 */

var TSLAB=TSLAB ||{};
TSLAB.Custom=(function(){
    var cache = {
        listUrl:"../micro/druggroup/getList",
        antibiotics : function(){
            var str = $('#antibiotics').val()||'';
            if(str != '')
                return eval("("+str+")");
            else
                return null;
        },
        antiSelected:{},
        drugGrid:$('#leftGrid'),
        drugDetailGrid:$('#rightGrid')
    };
    var public = {
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
            cache.drugGrid.jqGrid('setGridParam',{
                url: cache.listUrl,
                datatype : 'json',
                //发送数据
                postData : {"query":query },
                page : 1
            }).trigger('reloadGrid');//重新载入
        },
        Add:function(){
            public.clearData();
            public.loadDualListbox();
            layer.open({
                type: 1,
                area: ['800px','420px'],
                fix: false, //不固定
                maxmin: false,
                shade:0.6,
                title: "添加药敏组",
                content: $("#addDialog"),
                btn:["保存","取消"],
                yes: function(index, layero){
                    public.Save(index);
                    //layer.close(index); //如果设定了yes回调，需进行手工关闭
                }
            });
        },
        Edit:function(){
            var rowId = cache.drugGrid.jqGrid('getGridParam','selrow');
            var rowData = cache.drugGrid.jqGrid('getRowData',rowId);
            //获取已选择抗生素
            $.ajax({
                url:'../micro/druggroup/getDrugDetails',
                type:"POST",
                dataType:"json",
                async:false,
                data:{groupId:rowId},
                success:function(data){
                    cache.antiSelected = data;
                }
            });
            if(!rowId || rowId =='' || rowId==null){
                layer.msg("请先选择要编辑的数据",{icon:2});
                return false;
            }
            public.loadDualListbox();
            //设置数据
            $('#id').val(rowData.id);
            $('#drugGroupName').val(rowData.name);
            layer.open({
                type: 1,
                area: ['800px','420px'],
                fix: false, //不固定
                maxmin: false,
                shade:0.6,
                title: "编辑数据",
                content: $("#addDialog"),
                btn:["保存","取消"],
                yes: function(index, layero){
                    public.Save(index);
                }
            });
        },
        Save:function(index){
            var obj={};
            obj.drugGroupName = $('#drugGroupName').val()||'';
            obj.id= $('#id').val()||'';
            obj.druglist =  $('#druglist').val()||'';

            console.log(obj.druglist);
            if(obj.drugGroupName ==''){
                layer.alert('名称没有输入，不允许保存',{icon:2});
                return false;
            }
            $.ajax({
                url:'../micro/druggroup/save',
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
            })
        },
        Delete:function(){
        var id=cache.drugGrid.jqGrid('getGridParam','selrow');
        if(id==null || id==0){
            layer.msg('请先选择要删除的数据', {icon: 2,time: 1000});
            return false;
        }
        layer.confirm('确定删除选择数据？', {icon: 2, title:'警告'}, function(index){
            $.post('../micro/druggroup/delete',{id:id},function(data) {
                cache.drugGrid.jqGrid('delRowData',id );
            });
            layer.close(index);
        });
        },
        clearData:function(){
            $("#addDialog").find("input,textarea").each(function(){
                this.value = "";
            });
            cache.antiSelected = {};
        },
        initDualListbox:function() {
            //仪器设备
            var druglist = $('select[name="druglist"]').bootstrapDualListbox({
                infoText: false,
                filterTextClear: "",
                filterPlaceHolder: '过滤',
                selectorMinimalHeight: 160,
                preserveSelectionOnMove: false
            });
            var druglistCcontainer = druglist.bootstrapDualListbox('getContainer');
            druglistCcontainer.find('.btn').addClass('btn-white btn-info btn-bold');
        },
        loadDualListbox:function(){
            var labdepartment = $('#antibiotics').val()|| '';         //已选部门信息
            var selectDev = $('#druglist');
            selectDev.empty();
            var druglist = cache.antibiotics();
            for (var k in druglist) {
                var option = document.createElement("option");
                option.value = druglist[k].id;
                option.text = "["+druglist[k].indexid+"]"+druglist[k].name;
                if (cache.antiSelected[druglist[k].id] && cache.antiSelected[druglist[k].id].length >= 0) {
                    option.selected = 'selected';
                }
                selectDev[0].options.add(option);
            }
            $('select[name="druglist"]').bootstrapDualListbox("refresh");
        },
        initGrid:function(){
            //设置表格宽度
            $(window).on('resize.jqGrid', function () {
               cache.drugGrid.jqGrid('setGridWidth', $(".leftContent").width(),false);
               cache.drugDetailGrid.jqGrid('setGridWidth', $(".rightContent").width(),false);
            });
            var clientHeight= $(window).innerHeight();
            var height =clientHeight-$('#head').height()- $('#toolbar').height()-$('.footer-content').height()-150;

            //组合试验表设置
            cache.drugGrid.jqGrid( {
                url:'../micro/druggroup/getList',
                datatype : "json",
                height : height,
                shrinkToFit:false,
                width:$('.leftContent').width(),
                colNames : [ 'ID','名称','拼音码' ,'状态' ],
                colModel : [
                    {name : 'id',index : 'id',width : 60,sorttype : "int",hidden:true,key:true},
                    {name : 'name',index : 'deviceid',width : 150},
                    {name : 'spellcode',index : 'spellcode',width : 80},
                    {name : 'state',index : 'state',width : 50,formatter: "select",edittype: "select",editoptions: {value: "0:正常;1:停用"}}
                ],
                loadComplete : function() {
                    var table = this;
                    setTimeout(function(){
                        updatePagerIcons(table);
                    }, 0);
                },
                onSelectRow: function(id){
                    // var rowData = $("#leftGrid").jqGrid('getRowData',id);
                    cache.drugDetailGrid.jqGrid('setGridParam',{
                        url: "../micro/druggroup/getDetailsList",
                        datatype : 'json',
                        //发送数据
                        postData : {"groupId":id},
                        page : 1
                    }).trigger('reloadGrid');//重新载入
                },
                ondblClickRow:function(id){
                    Edit();
                },
                //multiselect : true,
                rowNum: 10,
                viewrecords:true,
                rowList:[10,20,40],
                rownumbers: true, // 显示行号
                rownumWidth: 35, // the width of the row numbers columns
                pager: "#leftPager",//分页控件的id
                caption : "药敏组"
            });
            //检验项目表设置
            cache.drugDetailGrid.jqGrid({
                //datatype : "local",
                height :height,
                width:$('.rightContent').width(),
                colNames : ['drugId', 'groupId','抗生素', '默认定量结果', '默认定性结果','检测方法','MIC法默认范围','KB法默认范围'],
                colModel : [
                    {name : 'drugId',index : 'drugId',width : 60,hidden : true,key:true, editable: true},
                    {name : 'groupId',index : 'groupId',width : 90,hidden:true, editable: true},
                    {name : 'drugName',index : 'drugName',width : 150, editable: true},
                    {name : 'quantitativeResult',index : 'quantitativeResult',width : 100, editable: true},
                    {name : 'qualitativeResult',index : 'qualitativeResult',width : 100, editable: true},
                    {name : 'method',index : 'method',width : 100, editable: true},
                    {name : 'micrange',index : 'micrange',width : 100, editable: true},
                    {name : 'kbrange',index : 'kbrange',width : 100, editable: true}
                ],
                loadComplete : function() {
                    var table = this;
                    setTimeout(function(){
                        updatePagerIcons(table);
                    }, 0);
                },
                //multiselect : true,
                shrinkToFit:false,
                scrollOffset:2,
                rowNum: 100,
                rowList:[100],
                rowheight: 15,
                rownumbers: true, // 显示行号
                rownumWidth: 35, // the width of the row numbers columns
                pager: "#rightPager",//分页控件的id
                caption : "抗生素明细",
                editurl : "editReagent"
            });
            var navParams = {
                edit:true,
                add:true,
                del:true,
                view:false,
                search:false,
                addicon: 'fa-plus red',
                searchicon:'fa-search',
                refreshicon:'fa-refresh',
                searchtext:'查找',edittext:'编辑',addtext:'添加',refreshtext:'刷新', deltext:'删除',
                addfunc : function(){
                    var groupId=cache.drugGrid.jqGrid('getGridParam','selrow');
                    if(groupId==null || groupId==0){
                        layer.msg('请先选择药敏组', {icon: 2,time: 1000});
                        return false;
                    }

                    var addParams = {
                        url:'druggroup/saveDetail',
                        closeAfterAdd : true,
                        beforeShowForm:function(){
                            $('#drugName').autocomplete({
                                source: function( request, response ) {
                                    $.ajax({
                                        url: "../../ajax/searchAntibiotic",
                                        dataType: "json",
                                        data: {
                                            name : request.term
                                        },
                                        success: function( data ) {
                                            response( $.map( data, function( result ) {
                                                return {
                                                    label: result.id + " : " + result.ab + " : " + result.name,
                                                    value: result.name,
                                                    id : result.id,
                                                    ab:result.ab
                                                }
                                            }));
                                            $("#searchProject").removeClass("ui-autocomplete-loading");
                                        }
                                    });
                                },
                                minLength: 1,
                                select : function(event, ui) {
                                    $('#id').val(ui.item.id);
                                    $('#drugId').val(ui.item.id);
                                    $('#groupId').val(groupId);
                                    $(this).val('');
                                }
                            });
                        },
                        beforeSubmit : function(postdata, formid){
                            //console.log(postdata);
                            if(postdata.drugId ==''){
                                layer.msg("数据不存在，请选择正确的数据后再保存。");
                                return false;
                            }
                            return[true,"success"];
                        }
                    };
                    cache.drugDetailGrid.jqGrid("editGridRow","new",addParams);
                },
                editfunc : function(rowId){
                    var editParams = {
                        editData : {
                            //triggerName : triggerName,
                        },
                        beforeShowForm:function(){
                            $('#tr_drugName').find('#drugName').attr('readonly','true');
                        },
                        url:'druggroup/saveDetail',
                        closeAfterEdit : true,
                        editCaption: "编辑",
                        bSubmit: "保存",
                        saveicon:[false,"left","fa-disk"]
                    };
                    cache.drugDetailGrid.jqGrid("editGridRow",rowId,editParams);
                },
                delfunc:function(rowId){
                    var rowData = cache.drugDetailGrid.jqGrid('getRowData',rowId);
                    layer.confirm('确定删除选择数据？', {icon: 2, title:'警告'}, function(index){
                        $.post('../micro/druggroup/deleteDetails',{groupId : rowData.groupId,drugId : rowData.drugId},function(data) {
                            cache.drugDetailGrid.jqGrid('delRowData',rowId);
                        });
                        layer.close(index);
                    });
                }
            };
            cache.drugDetailGrid.jqGrid('navGrid','#rightPager',navParams);
            $(window).triggerHandler('resize.jqGrid');
        },
        arrayToJson:function(o){
            var r = [];
            if (typeof o == "string") return "\"" + o.replace(/([\'\"\\])/g, "\\$1").replace(/(\n)/g, "\\n").replace(/(\r)/g, "\\r").replace(/(\t)/g, "\\t") + "\"";
            if (typeof o == "object") {
                if (!o.sort) {
                    for (var i in o)
                        r.push(i + ":" + public.arrayToJson(o[i]));
                    if (!!document.all && !/^\n?function\s*toString\(\)\s*\{\n?\s*\[native code\]\n?\s*\}\n?\s*$/.test(o.toString)) {
                        r.push("toString:" + o.toString.toString());
                    }
                    r = "{" + r.join() + "}";
                } else {
                    for (var i = 0; i < o.length; i++) {
                        r.push(public.arrayToJson(o[i]));
                    }
                    r = "[" + r.join() + "]";
                }
                return r;
            }
            return o.toString();
        },
        init:function(){
            public.initGrid();
            public.initDualListbox();
            public.loadDualListbox();
        }

    };

    return public;
})();


$(function(){
    var height = document.documentElement.clientHeight;

    if(height>150) $('#ullist').height(height-150);
    var isfirst = true;
    //keyPress 回车检索
    $("#query").keypress(function(e){
        if (e.keyCode == 13){
            TSLAB.Custom.search();
        }
    });
    TSLAB.Custom.init();

});