function AddYlxh() {
	clearData();
	layer.open({
		type: 1,
		area: ['650px','450px'],
		fix: false, //不固定
		skin: 'layui-layer-molv',
		maxmin: false,
		shade:0.6,
		title: "添加检验目的",
		content: $("#YlxhDialog"),
		btn:["保存","取消"],
		yes: function(index, layero){
			$("#ksdm").val($("#lab").val());
			$.ajax({
			  type: 'POST',
			  url: '../set/ylsf/editYlsf',
			  data: $("#YlxhForm").serialize(),
			  success: function(data){
			    if(parseInt(data.success)==0) {
	    		  search();
			      layer.close(index);
			    } else {
			    	layer.msg("检验目的添加失败",{icon:2,time: 1000});
			    }
		      }
			});
		}
	});
}

function editYlxh() {
	var rowId = $("#s3list").jqGrid('getGridParam','selrow');
	if(!rowId || rowId =='' || rowId==null){
		layer.alert("请先选择要编辑的数据",{icon:1,title:"提示"});
		return false;
	}
	var rowData = $("#s3list").jqGrid('getRowData',rowId);

	//initForm初始化Ylxh对象
	$('#ylxh').val(rowData.ylxh);
	$('#ylxh').attr("readonly","true");
	$('#ylmc').val(rowData.ylmc);
	$('#ylmc').attr("readonly","true");
	$('#english').val(rowData.english);
	$('#price').val(rowData.price);
	$('#price').attr("readonly","true");
	$('#mzpb').val(rowData.mzpb);
	$('#zypb').val(rowData.zypb);
	$('#yblx').val(rowData.yblx);
	$('#yblxzw').val(rowData.yblxzw);
	$('#sglx').val(rowData.sglx);
	$('#bbl').val(rowData.bbl);
	$('#qbgsj').val(rowData.qbgsj);
	$('#qbgdd').val(rowData.qbgdd);
	$("#ksdm").val($("#lab").val());
	$('#profiletest').val(rowData.ptest);
	$('#profiletest2').val(rowData.ptest2);
	$('#profiletest3').val(rowData.ptest3);
	$('#cjbw').val(rowData.cjbw);
	$('#sgsl').val(rowData.sgsl);
	$('#sfhb').val(rowData.sfhb);

	layer.open({
		type: 1,
		area: ['650px','450px'],
		fix: false, //不固定
		skin: 'layui-layer-molv',
		maxmin: false,
		shade:0.6,
		title: "添加检验目的",
		content: $("#YlxhDialog"),
		btn:["保存","取消"],
		yes: function(index, layero){
			$("#ksdm").val($("#lab").val());
			$.ajax({
			  type: 'POST',
			  url: '../set/ylsf/editYlsf',
			  dataType: 'json',
			  data: $("#YlxhForm").serialize(),
			  success: function(data){
				  if(parseInt(data.success)==0) {
		    		  search();
				      layer.close(index);
				    } else {
				      layer.msg("检验目的编辑失败",{icon:2,time: 1000});
				  }
		      }
			});
		}
	});
}

function addTest() {
	var rowId = $("#s3list").jqGrid('getGridParam','selrow');
	if(!rowId || rowId =='' || rowId==null){
		layer.alert("请先选择要编辑的数据",{icon:1,title:"提示"});
		return false;
	}
	if($("#searchIndexId").val() == '') {
		layer.alert("请先选择要添加的检验项目",{icon:0,title:"提示"});
		return false;
	}
	var rowData = $("#s3list").jqGrid('getRowData',rowId);
	var type = $("#profileTab .active").children().prop('id').replace("tab","");
	$.post('../set/ylsf/editProfile',{
		type:type,
		edit:'add',
		index:$("#searchIndexId").val(),
		ylxh:rowData.ylxh
	},function(data) {
		if(parseInt(data.success)==1) {
			if(type == '1') {
				layer.msg(rowData.ylmc + "的必做项目"+ $("#searchIndex").val() +"添加失败",{icon:2,time: 1000});
			} else if(type =='2') {
				layer.msg(rowData.ylmc + "的可选项目"+ $("#searchIndex").val() +"添加失败",{icon:2,time: 1000}); 
			} else {
				layer.msg(rowData.ylmc + "的关联项目"+ $("#searchIndex").val() +"添加失败",{icon:2,time: 1000});

}
        } else {
			if(type == '1') {
				$("#s3list").jqGrid('setCell',rowId,'ptest',data.success);
				$("#testTable").append("<tr><td>" + $("#searchIndex").val() +"</td><td><button class='btn btn-minier btn-danger' onclick='removeTest(this,\""+ $("#searchIndex").val() + "\",\"" +$("#searchIndexId").val() + "\")'>删除</button></td></tr>");
			} else if(type =='2') {
				$("#s3list").jqGrid('setCell',rowId,'ptest2',data.success);
				$("#testTable2").append("<tr><td>" + $("#searchIndex").val() +"</td><td><button class='btn btn-minier btn-danger' onclick='removeTest(this,\""+ $("#searchIndex").val() + "\",\"" +$("#searchIndexId").val() + "\")'>删除</button></td></tr>");
			} else {
				$("#s3list").jqGrid('setCell',rowId,'ptest3',data.success);
				$("#testTable3").append("<tr><td>" + $("#searchIndex").val() +"</td><td><button class='btn btn-minier btn-danger' onclick='removeTest(this,\""+ $("#searchIndex").val() + "\",\"" +$("#searchIndexId").val() + "\")'>删除</button></td></tr>");
			}
			$("#searchIndexId").val('');
			$("#searchIndex").val('');
		}
	});
	
}

function removeTest(obj, index, indexid) {
	var rowId = $("#s3list").jqGrid('getGridParam','selrow');
	if(!rowId || rowId =='' || rowId==null){
		layer.alert("请先选择要编辑的数据",{icon:1,title:"提示"});
		return false;
	}
	var rowData = $("#s3list").jqGrid('getRowData',rowId);
	var type = $("#profileTab .active").children().prop('id').replace("tab","");
	$.post('../set/ylsf/editProfile',{
		type:type,
		edit:'delete',
		index:indexid,
		ylxh:rowData.ylxh
	},function(data) {
		if(parseInt(data.success)==1) {
			if(type == '1') {
				layer.msg(rowData.ylmc + "的必做项目"+ index +"删除失败",{icon:2,time: 1000});
			} else if(type =='2') {
				layer.msg(rowData.ylmc + "的可选项目"+ index +"删除失败",{icon:2,time: 1000}); 
			} else {
				layer.msg(rowData.ylmc + "的关联项目"+ index +"删除失败",{icon:2,time: 1000});

}
        } else {
			if(type == '1') {
				$("#s3list").jqGrid('setCell',rowId,'ptest',data.success);
			} else if(type =='2') {
				$("#s3list").jqGrid('setCell',rowId,'ptest2',data.success);
			} else {
				$("#s3list").jqGrid('setCell',rowId,'ptest3',data.success);
			}
			$(obj).parent().parent().remove();
		}
	});
}

function search(){
	var query = $('#query').val()||'';
	jQuery("#s3list").jqGrid('setGridParam',{
		url: baseUrl + "/set/ylsf/data",
		datatype : 'json',
		//发送数据
		postData : {
			"query":query,
			"lab":$("#lab").val()
		},
		page : 1
	}).trigger('reloadGrid');//重新载入
}

function  clearData(){
	$("#ylxh").removeAttr("disabled");
	$("#ylmc").removeAttr("disabled");
	$("#price").removeAttr("disabled");
	$("#YlxhForm")[0].reset();
}

function getList(lab) {
	var isFirstTime = true;
	var clientHeight= $(window).innerHeight();
    var height =clientHeight-$('#head').height()- $('#toolbar').height()-$('.footer-content').height()-150;
	var mygrid = $("#s3list").jqGrid({
		caption: "检验目的基础设置",
		url: baseUrl + "/set/ylsf/data?lab="+lab,
		mtype: "GET",
		datatype: "json",
		width:$('.leftContent').width()-10,
		colNames:['序号', '检验目的','英文名称','价格','门诊开单','住院开单','标本类型','容器类型','标本量','取报告时间','取报告地点','采集部位','试管数量','允许合并','YBLX','PTEST','PTEST2','PTEST3','MZPB','ZYPB','SFHB'],
    	colModel:[ 
    		{name:'ylxh',index:'ylxh', width:60, sortable:false},
    		{name:'ylmc',index:'ylmc',width:160, sortable:false},
    		{name:'english',index:'english',width:50, sortable:false},
    		{name:'price',index:'price',width:30, sortable:false},
    		{name:'mzpbStr',index:'mzpbStr',width:50, sortable:false},
    		{name:'zypbStr',index:'zypbStr',width:50, sortable:false},
    		{name:'yblxzw',index:'yblxzw',width:50, sortable:false},
    		{name:'sglx',index:'sglx',width:50, sortable:false},
    		{name:'bbl',index:'bbl',width:50, sortable:false},
    		{name:'qbgsj',index:'qbgsj',width:50, sortable:false},
    		{name:'qbgdd',index:'qbgdd',width:50, sortable:false},
			{name:'cjbw',index:'cjbw',width:50, sortable:false},
			{name:'sgsl',index:'sgsl',width:50, sortable:false},
			{name:'sfhbStr',index:'sfhbStr',width:50, sortable:false},
			{name:'yblx',index:'yblx',hidden:true},
    		{name:'ptest',index:'ptest', hidden:true},
    		{name:'ptest2',index:'ptest2', hidden:true},
    		{name:'ptest3',index:'ptest3', hidden:true},
    		{name:'mzpb',index:'mzpb', hidden:true},
    		{name:'zypb',index:'zypb', hidden:true},
			{name:'sfhb',index:'sfhb', hidden:true},
    	],
    	loadComplete : function() {
            var table = this;
            setTimeout(function(){
                updatePagerIcons(table);
            }, 0);
        },
        onSelectRow: function(id) {
        	var ret = $("#s3list").jqGrid('getRowData',id);
			$("#testTable").empty();
			$("#testTable2").empty();
			$("#testTable3").empty();
			var html='', html2='', html3='';
			$.get('../set/ylsf/ajax/getTests',{ylxh:ret.ylxh},function(data) {
				var json = jQuery.parseJSON(data);
				if(json.profiletest) {
					html += "<tr><th>必做项目</th><th>&nbsp;</th></tr>";
					for(var i in json.profiletest) {
						html += "<tr><td>" + json.profiletest[i].name +"</td><td><button class='btn btn-minier btn-danger' onclick='removeTest(this,\""+ json.profiletest[i].name + "\",\"" + json.profiletest[i].id + "\")'>删除</button></td></tr>"
					}
					$("#testTable").html(html);
				}

				if(json.profiletest2) {
					html2 += "<tr><th>可选项目</th><th>&nbsp;</th></tr>";
					for(var i in json.profiletest2) {
						html2 += "<tr><td>" + json.profiletest2[i].name +"</td><td><button class='btn btn-minier btn-danger' onclick='removeTest(this,\""+ json.profiletest2[i].name + "\",\"" + json.profiletest2[i].id + "\")'>删除</button></td></tr>"
					}
					$("#testTable2").html(html2);
				}

				if(json.profiletest3) {
					html3 += "<tr><th>关联项目</th><th>&nbsp;</th></tr>";
					for(var i in json.profiletest3) {
						html3 += "<tr><td>" + json.profiletest3[i].name +"</td><td><button class='btn btn-minier btn-danger' onclick='removeTest(this,\""+ json.profiletest3[i].name + "\",\"" + json.profiletest3[i].id + "\")'>删除</button></td></tr>"
					}
					$("#testTable3").html(html3);
				}
			});
    	},
		ondblClickRow: function (id) {
			editYlxh();
		},
		viewrecords: true,
		shrinkToFit: true,
		altRows:true,
		height: height,
		rowNum: 20,
		rownumbers: true, // 显示行号
		rownumWidth: 35, // the width of the row numbers columns
		pager: "#s3pager"
	});
	jQuery("#s3list").jqGrid('navGrid','#s3pager',{edit:false,add:false,del:false,search:false,refresh:false});
    jQuery("#s3list").jqGrid('navButtonAdd',"#s3pager",{caption:"",title:"", buttonicon :'ui-icon-pin-s', onClickButton:function(){ mygrid[0].toggleToolbar() } });
}


$(function() {
	$("#YlxhForm").Validform({
		tiptype:4,
		callback:function(){

		}
	});

	$("#yblxzw").autocomplete({
		source: function( request, response ) {
			$.ajax({
				url: baseUrl + "/ajax/searchSampleType",
				dataType: "json",
				data: {
					name : request.term
				},
				success: function( data ) {
					response( $.map( data, function( result ) {
						return {
							label: result.sign + " : " + result.value,
							value: result.value,
							sign : result.sign
						}
					}));
				}
			});
		},
		minLength: 1,
		select : function(event, ui) {
			$("#yblxzw").val(ui.item.value);
			$("#yblx").val(ui.item.sign);
		}
	});
	
	$("#searchIndex").autocomplete({
        source: function( request, response ) {
            $.ajax({
            	url: baseUrl + "/ajax/searchTest",
                dataType: "json",
                data: {
                    name : request.term
                },
                success: function( data ) {
  					
                	response( $.map( data, function( result ) {
                        return {
                            label: result.id + " : " + result.ab + " : " + result.name,
                            value: result.name,
                            id : result.id
                        }
                    }));

                    $("#searchIndex").removeClass("ui-autocomplete-loading");
                }
            });
        },
        minLength: 1,
        select : function(event, ui) {
        	$("#searchIndexId").val(ui.item.id);
        }
	});
	
	
	$(document).keydown(function(e){
		if(e.keyCode == 40)
		{
			var s = jQuery("#s3list").jqGrid('getGridParam','selrow');
			var next = $("#"+s).next("tr").attr("id");
			
			if (next != null) {
				$("#s3list").setSelection(s, false);
				$("#s3list").setSelection(next, true);
			} else {
				var page = parseInt(jQuery("#s3list").jqGrid('getGridParam','page'));
				page = page + 1;
				var records = parseInt(jQuery("#s3list").jqGrid('getGridParam','records'));
				var total = (records - records % 20) / 20 + 1;
				if (page <= total) {
					$("#s3list").setGridParam({page:page}).trigger("reloadGrid");
				}
			}
			e.prthisDefault();
		} else if (e.keyCode == 38) {
			var s = jQuery("#s3list").jqGrid('getGridParam','selrow');
			var prev = $("#"+s).prev("tr").attr("id");
			
			if (prev != null) {
				$("#s3list").setSelection(s, false);
				$("#s3list").setSelection(prev, true);
			}
			e.prthisDefault();
		}
	});
	getList($("#lab").val());
	
	labChange = function(select) {
		var code = $(select).children().attr("title");
		$.ajax({
			  type: 'POST',
			  url: baseUrl + "/audit/labChange?lab="+code
		});
		$("#lab").val(code);
		$("#labText").html($(select).children().html());
		jQuery("#s3list").jqGrid('setGridParam',{
			url:baseUrl + "/set/ylsf/data",
			datatype : 'json',
			postData : {"lab":code},
			page : 1
		}).trigger('reloadGrid');
	}
});