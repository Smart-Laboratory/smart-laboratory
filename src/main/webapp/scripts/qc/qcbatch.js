/**
 * Created by zhou on 2016/7/5.
 * 质控项目设定 Script
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
        batchSelected:{},
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

            $("#sampleType").autocomplete({
                source: function( request, response ) {
                    $.ajax({
                        url: "../set/dictionary/getList?type=1&rows=10&page=1&sidx=&sord=asc",
                        dataType: "json",
                        data: {
                            query : request.term
                        },
                        success: function( data ) {
                            response( $.map( data.rows, function( result ) {
                                return {
                                    label: result.sign + " : " + result.value,
                                    value: result.sign,
                                }
                            }));
                            $("#searchProject").removeClass("ui-autocomplete-loading");
                        }
                    });
                },
                minLength: 1,
                // select : function(event, ui) {
                //     $('#id').val(ui.item.id);
                //     $('#drugId').val(ui.item.id);
                //     $('#groupId').val(groupId);
                //     $(this).val('');
                // }
            });

            layer.open({
                type: 1,
                area: ['800px','420px'],
                fix: false, //不固定
                maxmin: false,
                shade:0.6,
                title: "添加质控批次",
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
            if(!rowId || rowId =='' || rowId==null){
                layer.msg("请先选择要编辑的数据",{icon:2});
                return false;
            }
            //获取已选择质控品
            $.ajax({
                url:'../qc/qcbatch/getBatch',
                type:"POST",
                dataType:"json",
                async:false,
                data:{id:rowId},
                success:function(data){
                    cache.batchSelected = data;
                    $("#id").val(data.id);
                    $("#qcBatch").val(data.qcBatch);
                    $("#sampleType").val(data.sampleType);
                    $("#qcLevel").val(data.qcLevel);
                    $("#qcCode").val(data.qcCode);
                    $("#factory").val(data.factory);
                    $("#labdepart").val(data.labdepart);
                    $("#indate").val(data.indate);
                    $("#outdate").val(data.outdate);
                    $("#method").val(data.method);
                    $("#expDate").val(data.expDate);
                    $("#qcBatchName").val(data.qcBatchName);
                    $("#deviceid").val(data.deviceid);
                    
                    $("#deviceVal").val(data.deviceName);
                }
            });
            
//            public.loadDualListbox();
            //设置数据
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
            obj.qcBatch = $('#qcBatch').val()||'';
            if(obj.qcBatch ==''){
                layer.alert('质控批号没有输入，不允许保存',{icon:2});
                return false;
            }
            alert($('#addForm').serialize());
            $.ajax({
                url:'../qc/qcbatch/save',
                type:"POST",
                dataType:"json",
                data:$('#addForm').serialize(),
                success:function(data){
                    //console.log(data);
                    if(parseInt(data.success)==0){
                        layer.close(index);
                        cache.drugGrid.jqGrid('setGridParam',{
                            url: "../qc/qcbatch/getList",
                            datatype : 'json',
                            postData : {lab:$("#scode").val(),deviceid:$("#deviceSelect").val()},
                            //发送数据
                            page : 1
                        }).trigger('reloadGrid');//重新载入
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
            cache.batchSelected = {};
        },
        loadAutocomplete:function(key){
        	$("#diviceList").show();
        	$.ajax({
                url: "../qc/qcbatch/searchDevice",
                dataType: "json",
                data: {
                    maxRows : 12,
                    name :key,
                    chosed :  $('#deviceid').val()
                },
                success: function( data ) {
                    var ul = $('#testList');
                    ul.empty();
                    var choose = $("#deviceid").val();
                    for(i=0;i< data.length;i++){
                        var item = data[i];
                        if(choose!=null && choose.indexOf(item.id)>=0){
                        	var li = $('<li><input type="checkbox" testid="'+item.id+'" testname="'+item.name+'">'+ item.name+'</li>');
                        	li.children("input").prop("checked",true);
                        }else{
                        	var li = $('<li><input type="checkbox" testid="'+item.id+'" testname="'+item.name+'">'+ item.name+'</li>');
                        }
                        ul.append(li);
                    }
                    ul.parent().show();
                }
            });
        },
        choseItem:function(){
            var ul = $('#testList');
            var deviceId = [];
            var deviceName =[];
            ul.children('li').each(function(index,obj){
                var checkObj = $(obj).children("input:checkbox");
                if(checkObj.prop("checked")){
                    deviceId.push(checkObj.attr('testid'));
                    deviceName.push(checkObj.attr('testname'));
                }
            });
            $('#deviceid').val(deviceId.join(","));
            $('#deviceVal').val(deviceName.join(","));
            ul.parent().hide();
            $('#device').val();
        },
        cancelChose:function(){
            $('#testList').parent().hide();
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
                url:'../qc/qcbatch/getList',
                datatype : "json",
                height : height,
                shrinkToFit:false,
                postData : {lab:$("#scode").val(),deviceid:$("#deviceSelect").val()},
                width:$('.leftContent').width(),
                colNames : ['id','质控批号','质控品名称','样本类型','质控水平','质控编码','厂商','方法','生效日期','结束日期','出单人','仪器','部门','失效日期' ],
                colModel : [
                    {name : 'id',index : 'id',width : 60,sorttype : "int",hidden:true,key:true},
                    {name : 'qcBatch',index : 'qcBatch',width : 100},
                    {name : 'qcBatchName',index : 'qcBatchName',width : 100},
                    {name : 'sampleType',index : 'sampleType',width : 60},
                    {name : 'qcLevel',index : 'qcLevel',width : 60},
                    {name : 'qcCode',index : 'qcCode',width : 60},
                    {name : 'factory',index : 'factory',width : 100},
                    {name : 'medthod',index : 'medthod',width : 100},
                    {name : 'indate',index : 'indate',width : 100},
                    {name : 'outdate',index : 'outdate',width : 100},
                    {name : 'outer',index : 'outer',width : 100},
                    {name : 'deviceid',index : 'deviceid',width : 100},
                    {name : 'labdepart',index : 'labdepart',width : 100},
                    {name : 'expDate',index : 'expDate',width : 100}
                ],
                loadComplete : function() {
                    var table = this;
                    setTimeout(function(){
                        public.updatePagerIcons(table);
                    }, 0);
                },
                onSelectRow: function(id){
                    var rowData = $("#leftGrid").jqGrid('getRowData',id);
                    cache.drugDetailGrid.jqGrid('setGridParam',{
                        url: "../qc/qctest/getList",
                        datatype : 'json',
                        //发送数据
                        postData : {"qcBatch":rowData.qcBatch},
                        page : 1
                    }).trigger('reloadGrid');//重新载入
                },
                ondblClickRow:function(id){
                    Edit();
                },
                //multiselect : true,
                rowNum: 10,
                viewrecords:true,
                rowList:[10,30],
                rownumbers: true, // 显示行号
                rownumWidth: 35, // the width of the row numbers columns
                pager: "#leftPager",//分页控件的id
                caption : "质控品设定"
            });
            //靶值设置
            cache.drugDetailGrid.jqGrid({
                url : "../qc/qctest/getList",
                height :height,
                width:$('.rightContent').width(),
                colNames : ['id', '项目id','试验项目','英文缩写', '靶值', '标准差','频率(小时)','是否在用','仪器号','科室','pt低限','pt高限'],
                colModel : [
                    {name : 'id',index : 'id',width : 60,hidden : true,key:true},
                    {name : 'testId',index : 'testId',width : 90,hidden : true, editable: true,editrules: { edithidden: true }},
                    {name : 'chineseName',index : 'chineseName',width : 150, editable: true},
                    {name : 'englishab',index : 'englishab',width : 150, editable: true},
                    {name : 'targetValue',index : 'targetValue',width : 100, editable: true},
                    {name : 'stdV',index : 'stdV',width : 100, editable: true},
                    {name : 'frequency',index : 'frequency',width : 100, editable: true},
                    {name : 'inuse',index : 'inuse',width : 100, editable: true, edittype : "checkbox",editoptions : {value : "1:0"}},
                    {name : 'deviceid',index : 'deviceid',width : 100, editable: true},
                    {name : 'labDepart',index : 'labDepart',width : 100, editable: true},
                    {name : 'ptlow',index : 'ptlow',width : 100,hidden : true, editable: true, editrules: { edithidden: true },},
                    {name : 'pthigh',index : 'pthigh',width : 100, hidden:true, editable: true, editrules: { edithidden: true },}
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
                rowNum: 10,
                rowList:[10,30],
                rowheight: 15,
                rownumbers: true, // 显示行号
                rownumWidth: 35, // the width of the row numbers columns
                pager: "#rightPager",//分页控件的id
                caption : "靶值设定",
                editurl : ""
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
                    var rowData = cache.drugGrid.jqGrid('getRowData',groupId);
                    var addParams = {
                        url:'qctest/save?qcBatch='+rowData.qcBatch+'&sampleType='+rowData.sampleType,
                        closeAfterAdd : true,
                        beforeShowForm:function(){
                        	
                        	$('#labDepart').val($("#scode").html()).attr('readonly', true);
                        	$("#deviceid").val(rowData.deviceid).attr('readonly',true);
                            $('#testId').autocomplete({
                                source: function( request, response ) {
                                    $.ajax({
                                        url: "../ajax/searchTest",
                                        dataType: "json",
                                        data: {
                                            name : request.term
                                        },
                                        success: function( data ) {
                                            response( $.map( data, function( result ) {
                                                return {
                                                    label: result.id + " : " + result.ab + " : " + result.name,
                                                    value: result.id,
                                                    id : result.id,
                                                    ab:result.ab,
                                                    name:result.name
                                                }
                                            }));
                                            $("#searchProject").removeClass("ui-autocomplete-loading");
                                        }
                                    });
                                },
                                minLength: 1,
                                select : function(event, ui) {
                                    $('#testId').val(ui.item.id);
                                    $('#chineseName').val(ui.item.name);
                                    $('#englishab').val(ui.item.ab);
                                    
                                    $(this).val('');
                                }
                            });
                        },
                        beforeSubmit : function(postdata, formid){
                            //console.log(postdata);
                            if(postdata.testId ==''){
                                layer.msg("数据不存在，请选择正确的数据后再保存。");
                                return false;
                            }
                            return[true,"success"];
                        }
                    };
                    cache.drugDetailGrid.jqGrid("editGridRow","new",addParams);
                },
                editfunc : function(rowId){
                	var groupId=cache.drugGrid.jqGrid('getGridParam','selrow');
                    var rowData = cache.drugGrid.jqGrid('getRowData',groupId);
                    var editParams = {
                        beforeShowForm:function(){
                        	$('#labDepart').attr('readonly', true);
                        	$("#deviceid").attr('readonly',true);
                        },
                        url:'qctest/save?qcBatch='+rowData.qcBatch+'&sampleType='+rowData.sampleType,
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
                        $.post('../qc/qctest/delete',{id : rowData.id},function(data) {
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

            laydate({
                elem: '#indate',
                event: 'focus',
                format: 'YYYY-MM-DD'
            });
            laydate({
                elem: '#outdate',
                event: 'focus',
                format: 'YYYY-MM-DD'
            });
            laydate({
                elem: '#expDate',
                event: 'focus',
                format: 'YYYY-MM-DD'
            });

            var timeout = null;
            $('#device').keydown(function (e) {
                if (timeout) clearTimeout(timeout);
                timeout = setTimeout(function(){
                    TSLAB.Custom.loadAutocomplete($('#device').val()||'');
                }, 400);
            });

            $('#deviceEdit').click(function (e) {
                TSLAB.Custom.loadAutocomplete($('#device').val()||'');
            });
            
            $("#deviceSelect").change(function(){
            	cache.drugGrid.jqGrid('setGridParam',{
	                url:'../qc/qcbatch/getList',
	                postData : {lab:$("#scode").val(),deviceid:$("#deviceSelect").val()},
            	}).trigger('reloadGrid');
            });
            	
            labChange=function(select){
        		$.ajax({
        			  type: 'POST',
        			  url: "../audit/labChange?lab="+$(select).children().attr("title"),
        			  success:function(data){
        				  var section = $(select).children().attr("title");
        				  $("#labText").html($(select).children().html());
        				  $("#scode").val(section);
        				  cache.drugGrid.jqGrid('setGridParam',{
        		                url:'../qc/qcbatch/getList',
        		                postData : {lab:section,deviceid:$("#deviceSelect").val()},
        				  }).trigger('reloadGrid');
        			  }
        		});
        		
        	}
            
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