	function getPatient(docNo) {
		$.get("../manage/patientList/patient",{id:docNo},function(data){
			$("#midContent").css('display','block');
			$("#sampleTitle").html(data.examinaim);
			$("#rowed3").jqGrid("setCaption", data.examinaim);
        	$("#audit_reason").html(data.reason);
        	$("#pName").html(data.name);
        	$("#pAge").html(data.age);
        	$("#blh").html(data.blh);
        	$("#pId").html(data.id);
        	$("#pSex").html(data.sex);
        	$("#pSection").html(data.section);
        	$("#pType").html(data.type);
        	$("#diagnostic").html(data.diagnostic);
        	
        	if(data.bed == null){
        		$("#pBedText").css('display','none');
        		$("#pBedLabel").css('display','none');
        	}else{
        		$("#pBed").html(data.bed);
        		$("#pBedText").css('display','inline');
        		$("#pBedLabel").css('display','inline');
        	}
        	
        	$("#hiddenDocId").val(docNo);
        }, "json");
	}
 	
	function getSample(sampleNo) {
        var cl = "";
		jQuery("#rowed3").jqGrid({
		   	url:"../manage/patientList/sample?id="+sampleNo,
			datatype: "json",
			jsonReader : {repeatitems : false},  
		   	colNames:['ID','项目', '结果','历史', '历史1', '历史2', '参考范围', '单位'],
		   	colModel:[
		   		{name:'id',index:'id', hidden:true},
		   		{name:'name',index:'name',width:'20%',sortable:false},
		   		{name:'result',index:'result',width:'15%', sortable:false},
		   		{name:'last',index:'last',width:'13%', sortable:false},
		   		{name:'last1',index:'last1',width:'13%', hidden:true, sortable:false},
		   		{name:'last2',index:'last2',width:'13%', hidden:true, sortable:false},
		   		{name:'scope',index:'scope',width:'15%',sortable:false},
		   		{name:'unit', sortable:false, width:'15%', index:'unit'},
		   	],
		   	width: 630,
		   	height: '404',
		   	rowNum: 100,
		   	rownumbers: true,
		    caption: " ",
			loadComplete: function() {
				$("#rowed3").jqGrid("setCaption", $("#sampleTitle").html());
				$.each(jQuery('#rowed3').jqGrid('getCol','id', false), function(k,v) {
        			var ret = jQuery("#rowed3").jqGrid('getRowData',v);
        			if (ret.last != null && ret.last != "")
        				$("#hisLastResult").val(1);
        			else
        				$("#hisLastResult").val(0);
        			var hl = ret.scope.split("-");
        			var h = parseFloat(hl[1]);
        			var l = parseFloat(hl[0]);
        			var va = parseFloat(ret.result);
        			var res = "";
        			var color = "<div>";
        			
        			if (!isNaN(h) && !isNaN(l) && !isNaN(va)) {
        				if (va < l) {
        					res = "<font color='red'>\u2193</font>";
        				} else if (va > h) {
        					res = "<font color='red'>\u2191</font>";
        				}
        			}
        			jQuery("#rowed3").jqGrid('setRowData', v, {result:color+"<span class='result_span'>"+ret.result+"</span>"+res+"</div>"});
        		}); 
			}
		});
	}
	
	function getExplain(docNo){
		jQuery("#audit_information").jqGrid({
			url:"../manage/patientList/explain?id="+docNo,
			datatype: "json",
			jsonReader : {repeatitems : false}, 
			colNames:['ID','OLDRESULT','解释','原因','RANK'],
		   	colModel:[{name:'id',index:'id',sortable:false,hidden:true},
		   		{name:'oldResult',index:'oldResult',sortable:false,hidden:true},
		   		{name:'result',index:'result',width:90,sortable:false},
		   		{name:'content',index:'content',width:100,sortable:false},
		   		{name:'rank',index:'rank',sortable:false,hidden:true}], 
		   	height: '100%'
		});
	}
	
	function getList(patientId,blh) {
		
		var isFirstTime = true;
		var isFirstTimeForResult = true;
		var mygrid = jQuery("#list").jqGrid({
        	url:"../manage/patientList/data?type=1&text=" + blh, 
        	datatype: "json", 
 //       	width: 250, 
        	colNames:['ID', '样本号', '检验目的', '状态'], 
        	colModel:[ 
        		{name:'id',index:'id', hidden:true},
        		{name:'sample',index:'sample',width:120, sortable:false},
        		{name:'examinaim',index:'examinaim',width:100, sortable:false}, 
        		{name:'type',index:'type',width:50, sortable:false}], 
        	rowNum:12,
        	height: '100%',
        	jsonReader : {repeatitems : false},
        	mtype: "GET", 
        	pager: '#pager',
        	onSelectRow: function(id) {    
        		var ret = jQuery("#list").jqGrid('getRowData',id);
        		
        		getPatient(ret.id);
        		
        		$("#hiddenSampleNo").val(ret.sample);
        		$("#hisLastResult").val(null);
        		if (isFirstTime) {
    				getSample(ret.sample);
    				isFirstTime = false;
        		} else {
        			jQuery("#rowed3").jqGrid("setGridParam",{url:"../manage/patientList/sample?id="+ret.sample}).trigger("reloadGrid");
        		}
        		
        		if ($("#historyTabs").tabs('option', 'active') == 0) {
       				jQuery("#audit_information").jqGrid("setGridParam",{
       					url:"../manage/patientList/explain?id="+ret.id
       				}).trigger("reloadGrid");
        		} else {
        			jQuery("#rowed3").setGridParam().showCol("last1");
					jQuery("#rowed3").setGridParam().showCol("last2");
        		}
        		$("#historyTabs").css('display','block');
        	},
        	loadComplete: function() {
        		var firstDocId, firstSampleNo;
        		$.each(jQuery('#list').jqGrid('getCol','id', false), function(k,v) {
        			var ret = jQuery("#list").jqGrid('getRowData',v);
        			if (k == 0) {
        				firstDocId = ret.id;
        				firstSampleNo = ret.sample;
        			}
            		
        		}); 
        		$("#list").setSelection(firstDocId, true);
        	}
        }).trigger("reloadGrid"); 
		jQuery("#list").jqGrid('navGrid','#pager',{edit:false,add:false,del:false,search:false,refresh:false});
        jQuery("#list").jqGrid('navButtonAdd',"#pager",{caption:"",title:"", buttonicon :'ui-icon-pin-s', onClickButton:function(){ mygrid[0].toggleToolbar() } });
	}
	
	$(function() {
		function getQueryString(name) {
		    var reg = new RegExp('(^|&)' + name + '=([^&]*)(&|$)', 'i');
		    var r = window.location.search.substr(1).match(reg);
		    if (r != null) {
		        return unescape(r[2]);
		    }
		    return null;
		}
		$.ajaxSetup({cache:false});
		
		$( "#from" ).datepicker({
			changeMonth: true,
			dateFormat:"yy-mm-dd",
			monthNamesShort: ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'],
			dayNamesMin: ['日','一','二','三','四','五','六'],
			onClose: function( selectedDate ) {
				$( "#to" ).datepicker( "option", "minDate", selectedDate );
			}
		});
		$( "#to" ).datepicker({
			changeMonth: true,
			dateFormat:"yy-mm-dd",
			monthNamesShort: ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'],
			dayNamesMin: ['日','一','二','三','四','五','六'],
			onClose: function( selectedDate ) {
				$( "#from" ).datepicker( "option", "maxDate", selectedDate );
		    }
		});
		
		$("#searchBtn").click(function() {
			var from = $("#from").val();
			var to = $("#to").val();
			var select = $("#search_select").val();
			var searchText = $("#search_text").val();
			
			
			jQuery("#list").jqGrid("setGridParam",{
				url:"../manage/patientList/data?from="+from+"&to="+to+"&text="+searchText+"&type="+select
			}).trigger("reloadGrid"); 
			
		});
		
		$("#historyTabs").tabs({
			active : 0,
			activate: function(event, ui) { 
				var id = ui.newPanel.attr("id");
				if(id == "tabs-1") {
					jQuery("#rowed3").setGridParam().showCol("last1");
					jQuery("#rowed3").setGridParam().showCol("last2");
				} else {
//					jQuery("#rowed3").setGridParam().hideCol("last1");
//					jQuery("#rowed3").setGridParam().hideCol("last2");
					var s = jQuery("#list").jqGrid('getGridParam','selrow');
					getExplain(s);
				}
			}
		});
		
		getList(getQueryString("patientId"),getQueryString("blh"));
	});

