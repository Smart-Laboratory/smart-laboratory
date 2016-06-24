	var count = 1;
	
	function createInput(){
	    count++;
	    var str = '<div name="div" ><font style="font-size:12px;"></font>'+
	    '   '+ '<input type="file" contentEditable="false" id="uploads' + count + '' +
	    '" name="uploads'+ count +'" style="margin-top:5px;width:230px"/><button onclick="removeInput(event,\'more\')">'+'<fmt:message key="button.delete"/></button></div>';
	    //document.getElementById(parentId).insertAdjacentHTML("beforeEnd",str);
	    $("#more").append(str);
	}
	
	function removeInput(evt, parentId){
	   var el = evt.target == null ? evt.srcElement : evt.target;
	   var div = el.parentNode;
	   var cont = document.getElementById(parentId);       
	   if(cont.removeChild(div) == null){
	    return false;
	   }
	   return true;
	}
	
	function getImages(sampleno){
		$("#showGalleria").html("");
		$.get("../audit/ajax/getImage'/>",{sampleno:sampleno}, function(data) {
			data = jQuery.parseJSON(data);
			var html = data.html.split("fxg").join("/");
			$('#showGalleria').css('height','600px');//#galleria{height:320px}
			Galleria.loadTheme('../scripts/galleria.classic.min.js');
		    Galleria.run('#showGalleria', {
		        dataSource: html,
		        keepSource: false
			});
		});
	}
	
	function getPatient(sample) {
		$.get("../collect/list/patient",{sample:sample},function(data){
			$("#midContent").css('display','block');
			$("#sampleTitle").html(data.examinaim);
			$("#rowed3").jqGrid("setCaption", data.examinaim);
        	$("#audit_reason").html(data.reason);
        	$("#pName").html("<a href='../explain/patientList?patientId=" + data.patientId + "&blh=" + data.blh + "'   target='_blank'>" + data.name + "</a>");
        	$("#pAge").html(data.age);
        	$("#blh").html("<a href='http://192.168.17.102/ZWEMR/SysLogin.aspx?lcation=inside&ly=D&edt=N&pid=" + data.blh + "&gh=" + data.requester + "' target='_blank'>" + data.blh + "</a>");
        	$("#pSex").html(data.sex);
        	$("#pSection").html(data.section);
        	$("#pType").html(data.type);
        	$("#diagnostic").html(data.diagnostic);
        	$("#show_history").html(data.history);
        	$("#doctadviseno").html(data.id);
        	
        }, "json");
	}
	
 	
 	function getChart(sample) {
 		
 		$.get("../collect/list/chart",{sample:sample},function(data){
 			
 			$("#chartPanel").empty();
	    	if (data != "") {
	    		var length = data.length;
	    		for (var i=0; i<length; i++) {
	    			$("#chartPanel").append("<div id=\"chart" + i + "\" style=\"height:180px;width:180px;margin-bottom:15px;\"></div>");
	    			var item = data[i];
	    			$.jqplot('chart'+i, [item.array,item.array1,item.array2], {
	    				title:item.name,
	    				series:[{},{linePattern: 'dashed',markerOptions: { size:1 }},{linePattern: 'dashed',markerOptions: { size:1 }}],
	        			axes:{
	        				xaxis:{
	        					renderer:$.jqplot.DateAxisRenderer,
	        					tickOptions:{formatString:'%m/%d'}
	        				}
	    				}
	        		});
	    		}
			}
 		});
 	}
 	
	function getSample(sampleNo) {
		var width = $("#mid").width();
		jQuery("#rowed3").jqGrid({
		   	url:"../collect/list/sample?sample="+sampleNo,
			datatype: "json",
			jsonReader : {repeatitems : false},  
			colNames:['ID','Color','英文缩写','项目', '结果', '历史', '历史', '历史', '历史', '历史', '测定时间', '机器号', '参考值', '单位','KNOWLEDGE','EDITMARK','LASTEDIT'],
		   	colModel:[
		   		{name:'id',index:'id',hidden:true},
		   		{name:'color',index:'color',hidden:true},
		   		{name:'ab',index:'ab',width:width*0.15,hidden:true},
		   		{name:'name',index:'name',width:width*0.15,sortable:false},
		   		{name:'result',index:'result',width:width*0.09,sortable:false,editable:true},
		   		{name:'last',index:'last',width:width*0.09,sortable:false},
		   		{name:'last1',index:'last1',width:width*0.09,sortable:false},
		   		{name:'last2',index:'last2',width:width*0.09,sortable:false},
		   		{name:'last3',index:'last3',width:width*0.09,sortable:false},
		   		{name:'last4',index:'last4',width:width*0.09,sortable:false},
		   		{name:'checktime',index:'checktime',width:width*0.08,sortable:false},
		   		{name:'device',index:'device',width:width*0.06,sortable:false},
		   		{name:'scope',index:'scope',width:width*0.09,sortable:false},
		   		{name:'unit', sortable:false, width:width*0.08,index:'unit'},
		   		{name:'knowledgeName',index:'knowledgeName',hidden:true},
		   		{name:'editMark',index:'editMark',hidden:true},
		   		{name:'lastEdit',index:'lastEdit',hidden:true}
		   	],
		   	width: width,
		   	height: "100%",
		   	rowNum: 100,
		   	rownumbers: true,
		    caption: " ",
		    loadComplete: function() {
				if ($("#sampleTitle").html() == "") {
					$("#rowed3").jqGrid("setCaption", $("#sampleTitle").html());
				}
				var hisDate = jQuery("#rowed3").jqGrid("getGridParam", "userData").hisDate;
				$("#jqgh_rowed3_last").html("历史");
				$("#jqgh_rowed3_last1").html("历史");
				$("#jqgh_rowed3_last2").html("历史");
				$("#jqgh_rowed3_last3").html("历史");
				$("#jqgh_rowed3_last4").html("历史");
				if (hisDate != null && hisDate != "") {
					var his = hisDate.split(",");
					if (his.length == 1) {
						$("#jqgh_rowed3_last").html(his[0]);
					}else if (his.length == 2) {
						$("#jqgh_rowed3_last").html(his[0]);
						$("#jqgh_rowed3_last1").html(his[1]);
					}else if (his.length == 3) {
						$("#jqgh_rowed3_last").html(his[0]);
						$("#jqgh_rowed3_last1").html(his[1]);
						$("#jqgh_rowed3_last2").html(his[2]);
					} else if (his.length == 4) {
						$("#jqgh_rowed3_last").html(his[0]);
						$("#jqgh_rowed3_last1").html(his[1]);
						$("#jqgh_rowed3_last2").html(his[2]);
						$("#jqgh_rowed3_last3").html(his[3]);
					} else {
						$("#jqgh_rowed3_last").html(his[0]);
						$("#jqgh_rowed3_last1").html(his[1]);
						$("#jqgh_rowed3_last2").html(his[2]);
						$("#jqgh_rowed3_last3").html(his[3]);
						$("#jqgh_rowed3_last4").html(his[4]);
					}
				}
				if ($("#sampleTitle").html() == "") {
					$("#rowed3").jqGrid("setCaption", $("#sampleTitle").html());
				}
				//alert($("#rowed3").jqGrid("getCaption"));
				$.each(jQuery('#rowed3').jqGrid('getCol','id', false), function(k,v) {
        			var ret = jQuery("#rowed3").jqGrid('getRowData',v);
        			if (ret.last != null && ret.last != "")
        				$("#hisLastResult").val(1);
        			else
        				$("#hisLastResult").val(0);
        			var hl = ret.scope.split("-");
        			var h = parseFloat(hl[1]);
        			var l = parseFloat(hl[0]);
        			var color = "<div>";
        			
        			if (hl.length != 2) {
        				jQuery("#rowed3").jqGrid('setRowData', v, {
        					name:"<a href='javascript:show_knowledge(\""+ret.knowledgeName+"\")'>"+ret.name+"</a>",
        					result:color+"<span class='result_span'>"+ret.result+"</span></div>"
        				});
        				return true;
        			}
        			
        			var va = parseFloat(ret.result);
        			var la = parseFloat(ret.last);
        			var la1 = parseFloat(ret.last1);
        			var la2 = parseFloat(ret.last2);
        			var la3 = parseFloat(ret.last3);
        			var la4 = parseFloat(ret.last4);
        			var res = "";
        			var res1 = "";
        			var res2 = "";
        			var res3 = "";
        			var res4 = "";
        			var res5 = "";
        			
        			if (!isNaN(h) && !isNaN(l)) {
        				if (!isNaN(va)) {
        					if (va < l) {
	        					res = "<font color='red'>↓</font>";
	        				} else if (va > h) {
	        					res = "<font color='red'>↑</font>";
	        				}
        				}
        				
        				if (!isNaN(la)) {
        					if (la < l) {
	        					res1 = "<font color='red'>↓</font>";
	        				} else if (la > h) {
	        					res1 = "<font color='red'>↑</font>";
	        				}
        				}
        				
        				if (!isNaN(la1)) {
        					if (la1 < l) {
	        					res2 = "<font color='red'>↓</font>";
	        				} else if (la1 > h) {
	        					res2 = "<font color='red'>↑</font>";
	        				}
        				}
        				
        				if (!isNaN(la2)) {
        					if (la2 < l) {
	        					res3 = "<font color='red'>↓</font>";
	        				} else if (la2 > h) {
	        					res3 = "<font color='red'>↑</font>";
	        				}
        				}
        				
        				if (!isNaN(la3)) {
        					if (la3 < l) {
	        					res4 = "<font color='red'>↓</font>";
	        				} else if (la3 > h) {
	        					res4 = "<font color='red'>↑</font>";
	        				}
        				}
        				
        				if (!isNaN(la4)) {
        					if (la4 < l) {
	        					res5 = "<font color='red'>↓</font>";
	        				} else if (la4 > h) {
	        					res5 = "<font color='red'>↑</font>";
	        				}
        				}
        			}
        			
        		}); 
			}
		});
	}
	
	function getExplain(sample){
		jQuery("#audit_information").jqGrid({
			url:"../audit/explain?id="+sample,
			datatype: "json",
			jsonReader : {repeatitems : false}, 
			colNames:['ID','OLDRESULT','解释','详细','RANK'],
			colModel:[{name:'id',index:'id',sortable:false,hidden:true},
		   		{name:'oldResult',index:'oldResult',sortable:false,hidden:true},
		   		{name:'result',index:'result',width:190,sortable:false},
		   		{name:'content',index:'content',width:190,sortable:false,hidden:true},
		   		{name:'rank',index:'rank',sortable:false,hidden:true}], 
//		   	width: $("#left").width(),
		   	height: '100%'
		   		
		});
	}
	
	function getEvaluate(sampleno, userid){
		jQuery("#evaluatelist").jqGrid({
			url:"../collect/list/evaluatedata?sample="+sampleno +"&collector=" + userid,
			datatype: "json",
			width: $("#left").width(), 
			colNames:['ID','评价者','内容','评价时间'],
			colModel:[{name:'id',index:'id',sortable:false,hidden:true},
		        {name:'evaluator',index:'evaluator',width:50},
		   		{name:'content',index:'content',width:140},
		   		{name:'time',index:'time',width:40}], 
		   	rowNum:5,
		   	height: '100%',
		   	jsonReader : {repeatitems : false},
        	mtype: "GET", 
        	pager: '#evaluatepager'
		}).trigger("reloadGrid");
		jQuery("#evaluatelist").jqGrid('navGrid','#evaluatepager',{edit:false,add:false,del:false,search:false,refresh:false});
        jQuery("#evaluatelist").jqGrid('navButtonAdd',"#evaluatepager",{caption:"",title:"", buttonicon :'ui-icon-pin-s', onClickButton:function(){ mygrid[0].toggleToolbar() } });
	}
	
	function getList() {
		var isFirstTime = true;
		var isFirstTimeForResult = true;
		var mygrid = jQuery("#s3list").jqGrid({
        	url:"../collect/list/data", 
        	datatype: "json", 
        	width: $("#left").width(), 
        	colNames:['ID', 'USERID', 'TYPE', '病案名称', '收藏者', '样本号'], 
        	colModel:[ 
        		{name:'id',index:'id', hidden:true},
        		{name:'userid',index:'userid', hidden:true},
        		{name:'type',index:'type', hidden:true},
        		{name:'bamc',index:'bamc',width:70, sortable:false},
        		{name:'name',index:'name',width:40, sortable:false},
        		{name:'sampleno',index:'sampleno',width:110, sortable:false} 
        		/* {name:'time',index:'time',width:60, sortable:false} */], 
        	rowNum:10,
        	height: '100%',
        	jsonReader : {repeatitems : false},
        	mtype: "GET", 
        	pager: '#s3pager',
        	onSelectRow: function(id) {    
        		var ret = jQuery("#s3list").jqGrid('getRowData',id);
        		
        		getPatient(ret.sampleno);
        		
        		$("#hiddenSampleNo").val(ret.sampleno);
        		$("#hiddenBAMC").val(ret.bamc);
        		$("#hiddenCollector").val(ret.name);
        		$("#hiddenCollectorId").val(ret.userid);
        		$("#hiddenType").val(ret.type);
        		if (isFirstTime) {
    				getSample(ret.sampleno);
    				getEvaluate(ret.sampleno, ret.userid);
    				isFirstTime = false;
        		} else {
        			jQuery("#rowed3").jqGrid("clearGridData");
        			jQuery("#rowed3").jqGrid("setGridParam",{url:"../collect/list/sample?sample="+ret.sampleno}).trigger("reloadGrid");
        			jQuery("#evaluatelist").jqGrid("setGridParam",{url:"../collect/list/evaluatedata?sample="+ret.sampleno+"&collector="+ret.userid}).trigger("reloadGrid");
        		}
        		
        		if ($("#historyTabs").tabs('option', 'selected') == 0) {
        			getChart(ret.sampleno);
        		} else if ($("#historyTabs").tabs('option', 'selected') == 1) {
        			jQuery("#audit_information").jqGrid("setGridParam",{
       					url:"../collect/list/explain?sample="+ret.sampleno
       				}).trigger("reloadGrid");
        		} else {
    				jQuery("#rowed3").setGridParam().showCol("last2");
    				jQuery("#rowed3").setGridParam().showCol("last3");
    				jQuery("#rowed3").setGridParam().showCol("last4");
        		}
        		$("#historyTabs").css('display','block');
        	},
        	loadComplete: function() {
        		var firstDocId, firstSampleNo;
        		$.each(jQuery('#s3list').jqGrid('getCol','id', false), function(k,v) {
        			var ret = jQuery("#s3list").jqGrid('getRowData',v);
        			if (k == 0) {
        				firstDocId = ret.id;
        				firstSampleNo = ret.sampleno;
        			}
            		
        		}); 
        		$("#s3list").setSelection(firstDocId, true);
        	}
        }).trigger("reloadGrid"); 
		jQuery("#s3list").jqGrid('navGrid','#s3pager',{edit:false,add:false,del:false,search:false,refresh:false});
        jQuery("#s3list").jqGrid('navButtonAdd',"#s3pager",{caption:"",title:"", buttonicon :'ui-icon-pin-s', onClickButton:function(){ mygrid[0].toggleToolbar() } });
	}	

	function dataChange(select) {
		var value = select.value;
		jQuery("#s3list").jqGrid("setGridParam",{
			url:"../collect/list/data?select="+value
		}).trigger("reloadGrid"); 
	}
	$(function() {
		var left = $("#left").width();
		var mid = $("#mid").width();
		$("#evaluateDialog").dialog({
			autoOpen: false,
			resizable: false,
			modal:true,
		    width: 320,
		    height: 280
		});
		
		$("#evaluateDialog").dialog("close");
		$("#collectDialog").dialog({
			autoOpen: false,
			resizable: false,
			modal:true,
		    width: 320,
		    height: 400
		});
		
		$("#typeDialog").dialog({
			autoOpen: false,
			resizable: false,
			modal:true,
		    width: 800
		});
		
		$("#uploadDialog").dialog({
			autoOpen: false,
			resizable: false,
			modal:true,
		    width: 400,
		    height: 500
		});
		
		$("#uploadBtn").click(function(){
			$("#more").html("");
			$("#image_note").val("");
			$("#galleria").html("");
			$("#galleria").css("height", "0px");
			$("#uploadDialog").dialog("open");
		});
		
		$("#imageBtn").click(function(){
			getImages($("#hiddenSampleNo").val());
			layer.open({
				  type: 1,
				  shade: 0.4,
				  skin: 'layui-layer-lan',
				  area:['800px','700px'],
				  title: '图片',
				  content: $('#imageDialog'),
				  cancel: function(index){
				    layer.close(index);
				  }
				});
		});
		
		$("#typeBtn").click(function(){
			$("#typeDialog").dialog("open");
		});
		
		$("#evaluateBtn").click(function(){
			$("#e_bamc").html($("#hiddenBAMC").val());
			$("#e_collector").html($("#hiddenCollector").val());
			$("#evaluateDialog").dialog("open");
		});
		
		$("#cancelBtn").click(function(){
			var id = jQuery("#s3list").jqGrid('getGridParam','selrow');
			var ret = jQuery("#s3list").jqGrid('getRowData',id);
			$.post("../collect/list/cancel",{userid:ret.userid, sampleno:ret.sampleno},function(data) {
				if (data == true) {
					window.location.reload();
				} else {
					alert("不能刪除他人的收藏！");
				}
			});
		});
		
		$("#collectConfirm").click(function() {
			var sample = $("#hiddenSampleNo").val();
			var text = $("#collectText").val();
			var bamc = $("#collect_bamc").val();
			var type = $("#collect_type").val();
			
			$.post("../collect/list/collect",{sample:sample, text:text, type:type, bamc:bamc},function(data) {
				if (data == true) {
					alert("收藏成功");
				} else {
					alert("您已收藏过该样本-collect");
				}
				$("#collectDialog").dialog("close");
			});
		});
		
		$("#typeConfirm").click(function() {
			var checked = "";
			$("[name='checkbox']").each(function(){
				if($(this).attr("checked")) {
					checked = checked + "'" + $(this).val() + "',";
				}
			});
			jQuery("#s3list").jqGrid("setGridParam",{
				url:"../collect/list/data?type="+checked
			}).trigger("reloadGrid"); 
			$("#typeDialog").dialog("close");
		});
		
		$("#collectBtn").click(function(){
			$("#collect_bamc").val($("#hiddenBAMC").val());
			$("#collect_type").val($("#hiddenType").val());
			$("#collectDialog").dialog("open");
		});
		
		$("#collectCancel").click(function() {
			$("#collectDialog").dialog("close");
		});
		
		$("#typeCancel").click(function() {
			$("#typeDialog").dialog("close");
		});
		
		$("#evaluateCancel").click(function() {
			$("#evaluateDialog").dialog("close");
		});
		
		$("#evaluateConfirm").click(function() {
			var sample = $("#hiddenSampleNo").val();
			var text = $("#evaluateText").val();
			var collector = $("#hiddenCollectorId").val();
			
			$.post("../collect/list/evaluate",{sample:sample, text:text, collector:collector},function() {
				jQuery("#evaluatelist").jqGrid("setGridParam",{url:"../collect/list/evaluatedata?sample="+sample+"&collector="+collector}).trigger("reloadGrid");
				$("#evaluateDialog").dialog("close");
			});
		});
		
		$("#historyTabs").tabs({
			active : 1,
			activate : function(event, ui) {
				var id = ui.newPanel.attr("id");
				if(id == "tabs-1") {
					jQuery("#rowed3").setGridParam().showCol("last2");
					jQuery("#rowed3").setGridParam().showCol("last3");
					jQuery("#rowed3").setGridParam().showCol("last4");
					//jQuery("#rowed3").setGridParam().showCol("device");
					jQuery("#rowed3").setGridParam().showCol("unit");
				} else {
					$("#patientRow").css('display','block');
	    			$("#twosampleTable").css('display','none');
					jQuery("#rowed3").setGridParam().hideCol("last2");
					jQuery("#rowed3").setGridParam().hideCol("last3");
					jQuery("#rowed3").setGridParam().hideCol("last4");
					//jQuery("#rowed3").setGridParam().hideCol("device");
					jQuery("#rowed3").setGridParam().hideCol("unit");
//					var s = jQuery("#list").jqGrid('getGridParam','selrow');
//					var ret = jQuery("#s3list").jqGrid('getRowData',s);
					if (id == "tabs-0") {
						getExplain($("#hiddenSampleNo").val());
					}
				}
			}
		});
		
		$("#search_bamc").autocomplete({
	        source: function( request, response ) {
	            $.ajax({
	            	url: "../ajax/searchBAMC",
	                dataType: "json",
	                data: {
	                    name : request.term
	                },
	                success: function( data ) {
	                	response( $.map( data, function( result ) {
	                        return {
	                            label: result.name,
	                            value: result.name,
	                            id : result.id
	                        }}));
	                	$("#search_bamc").removeClass("ui-autocomplete-loading");
	                }
	            });
	        },
	        minLength : 1,
	        select : function(event, ui) {
	        	jQuery("#s3list").jqGrid("setGridParam",{
	    			url:"../collect/list/data?bamc="+ui.item.value
	    		}).trigger("reloadGrid"); 
	        }
		});
		
		$("#reason_block").click(function() {
			$("#reason_none").css('display','inline');
			$("#reason_block").css('display','none');
			jQuery("#audit_information").setGridParam().showCol("content");
			jQuery("#audit_information").setGridWidth(190,true);
			
		});
		
		$("#reason_none").click(function() {
			$("#reason_block").css('display','inline');
			$("#reason_none").css('display','none');
			jQuery("#audit_information").setGridParam().hideCol("content");
			jQuery("#audit_information").setGridWidth(190,true);
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
					var total = (records - records % 10) / 10 + 1;
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
		
		$("#show_history").click(function() {
			window.open("../manage/patientList?blh=" + $("#blh").children().html());
		});
		
		getList();
	});
	
	function ajaxFileUpload(){
	    var uplist = $("input[name^=uploads]");
		var arrId = [];
		for (var i=0; i< uplist.length; i++){
		    if(uplist[i].value){
		    	arrId[i] = uplist[i].id;
			}
	    }
		$.ajaxFileUpload({
			url:'../audit/ajax/uploadFile?sampleno=' + $("#hiddenSampleNo").val() + '&imgnote=' + $("#image_note").val(),
			secureuri:false,
			fileElementId: arrId,  
			success: function (){
				alert("上传成功");
				$("#uploadDialog").dialog("close");
				jQuery("#list").trigger("reloadGrid");
			},
			error: function(){
				alert("error");
			}
		});
	}

	function removeInput(evt, parentId){
		   var el = evt.target == null ? evt.srcElement : evt.target;
		   var div = el.parentNode.parentNode;
		   var cont = document.getElementById(parentId);       
		   if(cont.removeChild(div) == null){
		    return false;
		   }
		   return true;
	}