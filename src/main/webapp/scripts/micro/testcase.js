/**
 * Created by zhou on 2016/7/5.
 * 微生物基础信息-培养基方案 Script
 */

var TSLAB=TSLAB ||{};
TSLAB.Custom=(function(){
    var cache = {
        listUrl:"../micro/testcase/getList",
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
                title: "添加培养方案",
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
            //获取已选择培养基
            $.ajax({
                url:'../micro/testcase/getTestCaseDetails',
                type:"POST",
                dataType:"json",
                async:false,
                data:{testCaseId:rowId},
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
            $('#testCaseName').val(rowData.name);
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
            obj.testCaseName = $('#testCaseName').val()||'';
            obj.id= $('#id').val()||'';
            obj.cultureMediumList =  $('#cultureMediumList').val()||'';
            if(obj.testCaseName ==''){
                layer.alert('名称没有输入，不允许保存',{icon:2});
                return false;
            }
            $.ajax({
                url:'../micro/testcase/save',
                type:"POST",
                dataType:"json",
                data:$('#addForm').serialize(),
                success:function(data){
                    //console.log(data);
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
            layer.confirm('确定清除培养基数据？', {icon: 2, title:'警告'}, function(index){
                $.post('../micro/testcase/delete',{id:id},function(data) {
                    //cache.drugGrid.jqGrid('delRowData',id );
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
            var cultureMediumList = $('select[name="cultureMediumList"]').bootstrapDualListbox({
                infoText: false,
                filterTextClear: "",
                filterPlaceHolder: '过滤',
                selectorMinimalHeight: 160,
                preserveSelectionOnMove: false
            });
            var druglistCcontainer = cultureMediumList.bootstrapDualListbox('getContainer');
            druglistCcontainer.find('.btn').addClass('btn-white btn-info btn-bold');
        },
        loadDualListbox:function(){
            var labdepartment = $('#antibiotics').val()|| '';         //已选部门信息
            var selectDev = $('#cultureMediumList');
            selectDev.empty();
            var cultureMediumList = cache.antibiotics();
            for (var k in cultureMediumList) {
                var option = document.createElement("option");
                option.value = cultureMediumList[k].id;
                option.text = "["+cultureMediumList[k].id+"]"+cultureMediumList[k].name;
                if (cache.antiSelected[cultureMediumList[k].id] && cache.antiSelected[cultureMediumList[k].id].length >= 0) {
                    option.selected = 'selected';
                }
                selectDev[0].options.add(option);
            }
            $('select[name="cultureMediumList"]').bootstrapDualListbox("refresh");
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
                url:'../micro/testcase/getList',
                datatype : "json",
                height : height,
                shrinkToFit:false,
                width:$('.leftContent').width(),
                colNames : [ 'ID','名称','状态' ],
                colModel : [
                    {name : 'id',index : 'id',width : 60,sorttype : "int",hidden:true,key:true},
                    {name : 'name',index : 'name',width : 220},
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
                        url: "../micro/testcase/getDetailsList",
                        datatype : 'json',
                        //发送数据
                        postData : {"testCaseId":id},
                        page : 1
                    }).trigger('reloadGrid');//重新载入
                },
                ondblClickRow:function(id){
                    Edit();
                },
                //multiselect : true,
                rowNum: 1000,
                //viewrecords:true,
                //rowList:[1000],
                rownumbers: true, // 显示行号
                rownumWidth: 35, // the width of the row numbers columns
                pager: "#leftPager",//分页控件的id
                caption : "送检目的"
            });
            //检验项目表设置
            cache.drugDetailGrid.jqGrid({
                //datatype : "local",
                height :height,
                width:$('.rightContent').width(),
                colNames : ['cultureMediumId', 'testCaseId','培养基', '培养方法', '接种方法','染色方法','湿度','温度','空气','周期'],
                colModel : [
                    {name : 'cultureMediumId',index : 'cultureMediumId',width : 60,hidden : true,key:true, editable: true},
                    {name : 'testCaseId',index : 'testCaseId',width : 90,hidden:true, editable: true},
                    {name : 'cultureMedium',index : 'drugName',width : 150, editable: true},
                    {name : 'cultureMethod',index : 'quantitativeResult',width : 100, editable: true},
                    {name : 'vaccinateMethod',index : 'qualitativeResult',width : 100, editable: true},
                    {name : 'stainingMethod',index : 'method',width : 100, editable: true},
                    {name : 'humidity',index : 'micrange',width : 100, editable: true},
                    {name : 'temperature',index : 'micrange',width : 100, editable: true},
                    {name : 'air',index : 'kbrange',width : 100, editable: true},
                    {name : 'cycle',index : 'kbrange',width : 100, editable: true}
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
                caption : "培养基明细",
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
                        layer.msg('请先选择方案', {icon: 2,time: 1000});
                        return false;
                    }

                    var addParams = {
                        url:'testcase/saveDetail',
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
                        url:'testcase/saveDetail',
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
                        $.post('../micro/testcase/deleteDetails',{groupId : rowData.groupId,drugId : rowData.drugId},function(data) {
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