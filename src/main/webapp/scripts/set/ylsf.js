	function getList(lab) {
		var isFirstTime = true;
		var mygrid = jQuery("#s3list").jqGrid({
	    	url:"../set/ylsf/data?lab="+lab, 
	    	datatype: "json", 
	    	width: 400, 
	    	colNames:['ID', '检验目的','PTEST'], 
	    	colModel:[ 
	    		{name:'id',index:'id', hidden:true},
	    		{name:'ylmc',index:'ylmc',width:300, sortable:false},
	    		{name:'ptest',index:'ptest', hidden:true}], 
	    	rowNum:20,
	    	rownumbers: true,
	    	height: 460,
	    	jsonReader : {repeatitems : false},
	    	mtype: "GET", 
	    	pager: '#s3pager',
	    	onSelectRow: function(id) {    
	    		var ret = jQuery("#s3list").jqGrid('getRowData',id);
	    		
	    		getTest(ret.id);
	    		
	    		$("#hiddenSampleNo").val(ret.sample);
	    		$("#hisLastResult").val(null);
	    		if (isFirstTime) {
	    			getTest(ret.id);
					isFirstTime = false;
	    		} else {
	    			jQuery("#tlist").jqGrid("setGridParam",{url:"../set/ylsf/test?ylsf="+ret.id}).trigger("reloadGrid");
	    			jQuery("#tlist2").jqGrid("setGridParam",{url:"../set/ylsf/test2?ylsf="+ret.id}).trigger("reloadGrid");
	    		}
	    	},
	    	loadComplete: function() {
	    		var firstId;
	    		$.each(jQuery('#s3list').jqGrid('getCol','id', false), function(k,v) {
	    			var ret = jQuery("#s3list").jqGrid('getRowData',v);
	    			if (k == 0) {
	    				firstId = ret.id;
	    			}
	    		}); 
	    		$("#s3list").setSelection(firstId, true);
	    	}
	    }); 
		jQuery("#s3list").jqGrid('navGrid','#s3pager',{edit:false,add:false,del:false,search:false,refresh:false});
	    jQuery("#s3list").jqGrid('navButtonAdd',"#s3pager",{caption:"",title:"", buttonicon :'ui-icon-pin-s', onClickButton:function(){ mygrid[0].toggleToolbar() } });
	}
	
	function getTest(ylsf) {
		jQuery("#tlist").jqGrid({
		   	url:"../set/ylsf/test?ylsf="+ylsf,
			datatype: "json",
			jsonReader : {repeatitems : false},  
		   	colNames:['ID','英文缩写','项目'],
		   	colModel:[
		   		{name:'id',index:'id', hidden:true},
		   		{name:'english',index:'english',width:120,sortable:false},
		   		{name:'name',index:'name',width:120,sortable:false},
		   	],
		   	height: '200',
		   	rowNum: 100,
		   	rownumbers: true
		});
		
		jQuery("#tlist2").jqGrid({
		   	url:"../set/ylsf/test2?ylsf="+ylsf,
			datatype: "json",
			jsonReader : {repeatitems : false},  
		   	colNames:['ID','英文缩写','可选的项目'],
		   	colModel:[
		   		{name:'id',index:'id', hidden:true},
		   		{name:'english',index:'english',width:120,sortable:false},
		   		{name:'name',index:'name',width:120,sortable:false},
		   	],
		   	height: '200',
		   	rowNum: 100,
		   	rownumbers: true
		});
	}

	$(function() {
		$("#addTestDialog").dialog({
			autoOpen: false,
		    width: 320,
		    height: 120
		});
		
		$("#addTestDialog2").dialog({
			autoOpen: false,
		    width: 320,
		    height: 120
		});
		
		$("#testAdd").click(function() {
			var id = $("#addIndexId").val();
			var s = jQuery("#s3list").jqGrid('getGridParam','selrow');
			var ret = jQuery("#s3list").jqGrid('getRowData',s);
			var ptest;
			if(ret.ptest != "") {
				ptest = ret.ptest;
			} else {
				ptest = ret.id
			}
        	
			if (ptest.indexOf(id)>0) {
				alert("项目已存在！");
			} else {
				$.post("../set/ylsf/add",{add:id,id:ret.id}, function(data) {
					if (data == true) {
						jQuery("#tlist").trigger("reloadGrid");
					} else {
						alert("Fail!!!")
					}
				});
			}
		});
		
		$("#testAdd2").click(function() {
			var id = $("#addIndexId").val();
			var s = jQuery("#s3list").jqGrid('getGridParam','selrow');
			var ret = jQuery("#s3list").jqGrid('getRowData',s);
			var ptest;
			if(ret.ptest != "") {
				ptest = ret.ptest;
			} else {
				ptest = ret.id
			}
        	
			if (ptest.indexOf(id)>0) {
				alert("项目已存在！")
			} else {
				$.post("../set/ylsf/add2",{add:id,id:ret.id}, function(data) {
					if (data == true) {
						jQuery("#tlist2").trigger("reloadGrid");
					} else {
						alert("Fail!!!")
					}
				});
			}
		});
		
		$("#testDelete").click(function() {
			var id = jQuery("#s3list").jqGrid('getGridParam','selrow');
			var del = jQuery("#tlist").jqGrid('getGridParam','selrow');
			if (del != null) {
				$.post("../set/ylsf/delete",{del:del,id:id}, function(data) {
					if (data == true) {
						var s = jQuery("#tlist").jqGrid('getGridParam','selrow');
						var next = $("#"+s).next("tr").attr("id");
						$("#tlist").jqGrid('delRowData',del);
						if (next != null) {
							$("#tlist").setSelection(next, true);
						}
					} else {
						alert("Fail!!!");
					}
				});
			}
		});
		
		$("#testDelete2").click(function() {
			var id = jQuery("#s3list").jqGrid('getGridParam','selrow');
			var del = jQuery("#tlist2").jqGrid('getGridParam','selrow');
			if (del != null) {
				$.post("../set/ylsf/delete2",{del:del,id:id}, function(data) {
					if (data == true) {
						var s = jQuery("#tlist2").jqGrid('getGridParam','selrow');
						var next = $("#"+s).next("tr").attr("id");
						$("#tlist2").jqGrid('delRowData',del);
						if (next != null) {
							$("#tlist2").setSelection(next, true);
						}
					} else {
						alert("Fail!!!");
					}
				});
			}
		});
		
		$("#searchBtn").click(function(){
			var text = $("#search_text").val();
			jQuery("#s3list").jqGrid("setGridParam",{
				url:"../ajax/searchYlxh?text="+text}).trigger("reloadGrid");
		});
		
		$("#searchProject").autocomplete({
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
	                            value: result.name,
	                            id : result.id
	                        }
	                    }));

	                    $("#searchProject").removeClass("ui-autocomplete-loading");
	                }
	            });
	        },
	        minLength: 1,
	        select : function(event, ui) {
	        	$("#addIndexId").val(ui.item.id);
	        }
		});
		
		$("#searchProject2").autocomplete({
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
	                            value: result.name,
	                            id : result.id
	                        }
	                    }));

	                    $("#searchProject2").removeClass("ui-autocomplete-loading");
	                }
	            });
	        },
	        minLength: 1,
	        select : function(event, ui) {
	        	$("#addIndexId").val(ui.item.id);
	        	
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
				e.preventDefault();
			} else if (e.keyCode == 38) {
				var s = jQuery("#s3list").jqGrid('getGridParam','selrow');
				var prev = $("#"+s).prev("tr").attr("id");
				
				if (prev != null) {
					$("#s3list").setSelection(s, false);
					$("#s3list").setSelection(prev, true);
				}
				e.preventDefault();
			}
		});
		getList($("#lab").val());
		
		labChange = function(select) {
			var code = $(select).children().attr("title");
			$.ajax({
				  type: 'POST',
				  url: "../audit/labChange?lab="+code
			});
			$("#nowLab").val(code);
			$("#labText").html($(select).children().html());
			jQuery("#s3list").jqGrid('setGridParam',{
				url:"../set/ylsf/data="+code,
				datatype : 'json',
				postData : {"lab":code},
				page : 1
			}).trigger('reloadGrid');
		}
	});
	
