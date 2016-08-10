/************************************
 *  新增检验段
 *  add by yzh 2016-06-21
 * **********************************/
function  AddCode(){
	layer.open({
		type: 1,
		area: ['400px','300px'],
		fix: false, //不固定
		maxmin: false,
		shade:0.6,
		title: "添加检验段",
		content: $("#addSectionCode"),
		btn:["保存","取消"],
		yes: function(index, layero){
			$.post('../set/section/addCode', {code:$('#sectioncode').val(),
				describe : $('#describe').val()
		    },function(data){
		    	layer.close(index);
	        });
			//layer.close(index); //如果设定了yes回调，需进行手工关闭
		}
	});
}

/**
 * 删除检验段
 * */
function Delete(id){
    //var id = $('#rightGrid').jqGrid('getGridParam', 'selrow');
    if (id == null || id == 0) {
        layer.msg('请先选择要删除的数据', {icon: 2, time: 1000});
        return false;
    }
    $("#segment").val($("#segment").val().replace($("#codeEdit").jqGrid("getRowData", id).code + ",", ""));
    $("#codeEdit").jqGrid('delRowData',id );
}
/************************************
 *  添加科室
 *  add by zcw 2015-05-16
 * **********************************/
function  AddSection(){
	clearData();
	layer.open({
		type: 1,
		area: ['800px','500px'],
		fix: false, //不固定
		maxmin: false,
		shade:0.6,
		title: "添加科室",
		content: $("#addDialog"),
		btn:["保存","取消"],
		yes: function(index, layero){
			$.post('../set/section/edit', {code:$('#code').val(),
				name : $('#name').val(), segment : $('#segment').val()
		    },function(data){
		    	layer.close(index);
		    	$("#sectionList").trigger('reloadGrid');
		    	jQuery("#sectionCode").jqGrid("clearGridData");
	        });
			//layer.close(index); //如果设定了yes回调，需进行手工关闭
		}
	});
}
/************************************
 *  删除科室
 *  add by zcw 2015-05-16
 * **********************************/
function deleteSection(){
	var id = $('#sectionList').jqGrid('getGridParam','selrow');
	if(id==null || id.length==0){
		layer.msg('请先选择要删除的数据', {icon: 2,time: 1000});
		return false;
	}
	layer.confirm('确定删除选择数据？', {icon: 2, title:'警告'}, function(index){
		$.post('section/remove',{id:id},function(data) {});
		$("#sectionList").trigger('reloadGrid');
		layer.close(index);
	});
}
/**
 * 查询科室
 */
function search(){
	var query = $('#query').val()||'';
	jQuery("#sectionList").jqGrid('setGridParam',{
		url: "../set/section/data",
		datatype : 'json',
		//发送数据
		postData : {"query":query },
		page : 1
	}).trigger('reloadGrid');//重新载入
}

/**
 * 编辑科室
 */
function editSection(){
	var rowId = $("#sectionList").jqGrid('getGridParam','selrow');
	var rowData = $("#sectionList").jqGrid('getRowData',rowId);
	if(!rowId || rowId =='' || rowId==null){
		layer.alert("请先选择要编辑的数据",{icon:1,title:"提示"});
		return false;
	}
	//设置数据
	$('#id').val(rowData.id);
	$('#code').val(rowData.code);
	$('#name').val(rowData.name);
	$('#segment').val(rowData.segment);
	jQuery("#codeEdit").jqGrid("clearGridData");
	jQuery("#codeEdit").jqGrid('setGridParam',{
        url: "../set/section/getCodeList",
        datatype : 'json',
        postData : {"code":rowData.segment},
        page : 1
    }).trigger('reloadGrid');
	layer.open({
		type: 1,
		area: ['800px','500px'],
		fix: false, //不固定
		maxmin: false,
		shade:0.6,
		title: "编辑科室",
		content: $("#addDialog"),
		btn:["保存","取消"],
		yes: function(index, layero){
			$.post('../set/section/edit', {id : $('#id').val(), code:$('#code').val(),
				name : $('#name').val(), segment : $('#segment').val()
		    },function(data){
		    	layer.close(index);
		    	$("#sectionList").trigger('reloadGrid');
		    	jQuery("#sectionCode").jqGrid("clearGridData");
	        })
		}
	});
}

$(function(){
	//表单校验
	$("#addSectionForm").Validform({
		tiptype:4,
		callback:function(){

		}
	});
	
	$("#addSectionCodeForm").Validform({
		tiptype:4,
		callback:function(){

		}
	});
	//keyPress 回车检索
	$("#query").keypress(function(e){
		if (e.keyCode == 13){
			search();
		}
	});
	//$(window).on('resize.jqGrid', function () {
	//	$("#sectionList").jqGrid('setGridWidth', $("#mainTable").width());
	//	$("#sectionList").jqGrid( 'setGridHeight', $("#mainTable").height() );
	//})
	
	$(window).on('resize.jqGrid', function () {
        $('#sectionList').jqGrid('setGridWidth', $(".leftContent").width(),false);
        $('#sectionCode').jqGrid('setGridWidth', $(".rightContent").width(),false);
    });
    var clientHeight= $(window).innerHeight();
    var height =clientHeight-$('#head').height()- $('#toolbar').height()-$('.footer-content').height()-150;
    
	$("#sectionList").jqGrid({
		caption: "科室设置",
		url: "../set/section/data",
		mtype: "GET",
		datatype: "json",
		width:$('.leftContent').width(),
		colNames: ['ID', '编号', '名称', '检验段'],
		colModel: [
			{ name: 'id', index: 'id', width: 60, hidden: true },
			{ name: 'code', index: 'code', width: 40},
			{ name: 'name', index: 'name', width: 60 },
			{ name: 'segment', index: 'segment', width: 60, hidden: true }
		],
		loadComplete : function() {
			var table = this;
			setTimeout(function(){
				updatePagerIcons(table);
			}, 0);
		},
		ondblClickRow: function (id) {
			editSection();
		},
		viewrecords: true,
		shrinkToFit: true,
		altRows:true,
		height: height,
		rowNum: 10,
		rowList:[10,20,30],
		rownumbers: true, // 显示行号
		rownumWidth: 35, // the width of the row numbers columns
		pager: "#pager",
		onSelectRow: function(id){
			jQuery("#sectionCode").jqGrid("clearGridData");
			var rowData = $("#sectionList").jqGrid('getRowData',id);
            jQuery("#sectionCode").jqGrid('setGridParam',{
                 url: "../set/section/getCodeList",
                 datatype : 'json',
                 postData : {"code":rowData.segment},
                 page : 1
             }).trigger('reloadGrid');//重新载入
         }
	});
	
	jQuery("#sectionCode").jqGrid( {
        //datatype : "local",
        height :height,
        width:$('#maincontent .rightContent').width(),
        colNames : [ 'id', '检验段', '检验段描述'],
        colModel : [
            {name : 'id',index : 'id',width : 60,hidden : true},
            {name : 'code',index : 'code',width : 80,},
            {name : 'describe',index : 'describe',width : 160}
        ],
        loadComplete : function() {
            var table = this;
            setTimeout(function(){
                updatePagerIcons(table);
            }, 0);
        },
        shrinkToFit:false,
        scrollOffset:2,
        rowNum: 10,
        rowList:[10,20,40],
        rownumbers: true, // 显示行号
        rownumWidth: 35, // the width of the row numbers columns
        pager: "#rightPager",//分页控件的id
        caption : "检验段"
    });
	//$(window).triggerHandler('resize.jqGrid');
	
	$("#codeEdit").jqGrid( {
        datatype : "local",
       // rowNum:indexlist.length,
        height :300,
        width:$('#addDialog').width()*0.5,
        colNames : [ 'id', '检验段', '检验段描述', '操作'],
        colModel : [
            {name : 'id',index : 'id',width : 60,hidden : true},
            {name : 'code',index : 'code',width : 80,},
            {name : 'describe',index : 'describe',width : 160},
            {name : 'Delete',index : 'Delete',width : 60}
        ],
        gridComplete: function () {
            var ids = jQuery("#codeEdit").jqGrid('getDataIDs');
            for (var i = 0; i < ids.length; i++) {
                var id = ids[i];
                var DeleteBtn = "<a href='#' style='color:#f60' onclick='Delete("+id+")' >删除</a>";
                jQuery("#codeEdit").jqGrid('setRowData', ids[i], {Delete: DeleteBtn});
            }
        },
        //multiselect : true,
        shrinkToFit:false,
        scrollOffset:2,
        rownumbers: true, // 显示行号
        rownumWidth: 35
    });
	
	//搜索检验段
	$("#searchCode").autocomplete({
        source: function( request, response ) {
            $.ajax({
                url: "../ajax/searchCode",
                dataType: "json",
                data: {
                    name : request.term
                },
                success: function( data ) {
                    response( $.map( data, function( result ) {
                        return {
                            label: result.code + " : " + result.describe,
                            value: result.code,
                            describe: result.describe,
                            id : result.id
                        }
                    }));
                    $("#searchCode").removeClass("ui-autocomplete-loading");
                }
            });
        },
        minLength: 1,
        select : function(event, ui) {
            //$("#addIndexId").val(ui.item.id);
            var obj = $("#codeEdit").jqGrid("getRowData");
            var datas = [];
            var flag = true;
            jQuery(obj).each(function(){
                if(ui.item.value == this.code){
                    flag =  false;
                    layer.msg("数据已存在");
                }

            });
            if(!flag) return ;
            var ids = $('#codeEdit').jqGrid('getDataIDs');
            //console.log(ids)
            var newId = parseInt(ids[ids.length - 1] || 0) + 1;
            var rowData = {
                id: ui.item.id,
                code: ui.item.value,
                describe: ui.item.describe
            };
            $("#segment").val($("#segment").val() + ui.item.value + ",");
            $("#codeEdit").jqGrid('addRowData', newId, rowData);
            $('#codeEdit').saveRow(newId, false, 'clientArray');
            
        },
        close: function( event, ui ) {
        	$("#searchCode").val('');
        }
    });
	
});
function  clearData(){
	$('#id').val('0');
	$('#code').val('');
	$('#name').val('');

}
function updatePagerIcons(table) {
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
}