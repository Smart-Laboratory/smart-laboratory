<%@ include file="/common/taglibs.jsp"%>

<head>
	<title><fmt:message key="menu.quality.trace" /></title>
	<script type="text/javascript" src="<c:url value='/scripts/grid.locale-cn.js'/> "></script>
	<script type="text/javascript" src="<c:url value='/scripts/jquery.jqGrid.min.js'/> "></script>
	<script type="text/javascript" src="<c:url value='/scripts/jquery.tablednd_0_5.js'/> "></script>
	<script type="text/javascript" src="<c:url value='/scripts/jquery.json-2.3.min.js'/> "></script>
	<script type="text/javascript" src="<c:url value='/scripts/ext-all.js'/> "></script>
	<script type="text/javascript" src="<c:url value='/scripts/jquery.form.js'/> "></script>
	<script type="text/javascript" src="<c:url value='/scripts/jquery.bgiframe-2.1.2.js'/> "></script>
	<link rel="stylesheet" type="text/css" href="<c:url value='/styles/ui.jqgrid.css'/> " />
	<link rel="stylesheet" type="text/css"  href="<c:url value='/styles/ruleLib.css'/>" /> 
<style>
.ui-progressbar .ui-progressbar-value { background-image: url(<c:url value='/images/pbar-ani.gif'/>); }

#patient-info .pLabel {
}

#patient-info .pText {
	margin-right:20px;
}

#rowed3 {
	font-size: 12px;
	font-family: Microsoft YaHei;
}

select {
	padding: 0px;
}

#gview_s3list .ui-jqgrid-hdiv {
	overflow-y:hidden;
}

h2 {
	color: #B4C24B;
	font-size: 2.0em;
	letter-spacing: -1px;
	margin: 0 0 0.5em;
	padding: 0;
	display: inline;
}

.pagelinks {
	line-height: 17px;
}
.ui-jqgrid-titlebar {
	height:20px;
	font-size:16px;
	line-height:19px;
}

.ui-th-column {
	line-height:20px;
}

#gview_s3list .ui-search-toolbar th {
	border-top-width:0px !important;
}

#gview_s3list .ui-jqgrid-htable th {
	padding:0px;
}

#s3list {
	font-size:12px;
}
.ui-jqgrid-labels {
	height:25px;
}

input {
	width:20px;
}

.result_span {
	display:block;
	float:left;
	width:50px;
	margin-left:2px;
}
.ui-tabs-vertical { width: 580; }
.ui-tabs-vertical .ui-tabs-nav { padding: .2em .1em .2em .2em; float: left; width: 12em; }
.ui-tabs-vertical .ui-tabs-nav li { clear: left; width: 100%; border-bottom-width: 1px !important; border-right-width: 0 !important; margin: 0 -1px .2em 0; }
.ui-tabs-vertical .ui-tabs-nav li a { display:block; }
.ui-tabs-vertical .ui-tabs-nav li.ui-tabs-active { padding-bottom: 0; padding-right: .1em; border-right-width: 1px; border-right-width: 1px; }
.ui-tabs-vertical .ui-tabs-panel { padding: 1em; float: right; width: 370px;}
#audit_information td {white-space:normal;}
#s3list td {white-space:normal;}
</style>
<script type="text/javascript">
 	function getSample(docNo) {
		$.get("<c:url value='/sampleTrace/sample'/>",{id:docNo},function(data){
			$("#midContent").css('display','block');
        	$("#pName").html(data.name);
        	$("#pAge").html(data.age);
        	$("#blh").html(data.blh);
        	$("#pId").html(data.id);
        	$("#pSex").html(data.sex);
        	$("#pSection").html(data.section);
        	$("#pType").html(data.type);
        	$("#diagnostic").html(data.diagnostic);
        	
        	$("#tat_request").html(data.request);
			$("#tat_execute").html(data.execute);
			$("#tat_receive").html(data.receive);
			$("#tat_audit").html(data.audit);
			$("#tat_send").html(data.send);
			$("#tat_ksreceive").html(data.ksreceive);
			
			$("#tat_requester").html(data.requester);
			$("#tat_executor").html(data.executor);
			$("#tat_receiver").html(data.receiver);
			$("#tat_auditor").html(data.auditor);
			$("#tat_sender").html(data.sender);
			$("#tat_ksreceiver").html(data.ksreceiver);
			$("#tat_tester").html(data.tester);
			var time = parseInt(data.tat);
			var tStr = "";
			if (time >= 1440) {
				var day = Math.floor(time / 1440);
				tStr += day.toString();
				tStr += "<fmt:message key='day'/>";
				time = time - day * 1440;
			}
			
			if (time >= 60) {
				var hour = Math.floor(time / 60);
				tStr += hour.toString();
				tStr += "<fmt:message key='hour'/>";
				time = time - hour * 60;
			}
			tStr += time.toString();
			tStr += "<fmt:message key='minute'/>";
			
			$("#audit_tat").html(tStr);
        	
        	
        	
        	$("#hiddenDocId").val(docNo);
        }, "json");
	}
	
	function getList(doct, from, to, pName, patientId, section) {
		
		var mygrid = jQuery("#s3list").jqGrid({
        	url:"<c:url value='/sampleTrace/data'/>?doct="+ doct + "&from=" + from + "&to=" + to + "&pName=" + pName + "&patientId=" + patientId + "&section=" + section, 
        	datatype: "json", 
        	width: 350, 
        	colNames:['<fmt:message key="patient.doctadviseno" />', '<fmt:message key="audit.sample.number" />', '<fmt:message key="patientInfo.examinaim"/>'], 
        	colModel:[ 
        		{name:'id',index:'id', width:80, sortable:false},
        		{name:'sample',index:'sample',width:120, sortable:false},
        		{name:'examinaim',index:'examinaim',width:150, sortable:false}], 
        	rowNum:10,
        	height: '100%',
        	jsonReader : {repeatitems : false},
        	mtype: "GET", 
        	pager: '#s3pager',
        	onSelectRow: function(id) {    
        		getSample(id);
        		
        	},
        	loadComplete: function() {
        		var firstDocId;
        		$.each(jQuery('#s3list').jqGrid('getCol','id', false), function(k,v) {
        			var ret = jQuery("#s3list").jqGrid('getRowData',v);
        			if (k == 0) {
        				firstDocId = ret.id;
        			}
        		}); 
        		$("#s3list").setSelection(firstDocId, true);
        	}
        }).trigger("reloadGrid"); 
		jQuery("#s3list").jqGrid('navGrid','#s3pager',{edit:false,add:false,del:false,search:false,refresh:false});
        jQuery("#s3list").jqGrid('navButtonAdd',"#s3pager",{caption:"",title:"", buttonicon :'ui-icon-pin-s', onClickButton:function(){ mygrid[0].toggleToolbar() } });
	}
	
	$(function() {
		$.ajaxSetup({cache:false});
		
		$( "#from" ).datepicker({
	      changeMonth: true,
	      dateFormat:"yy-mm-dd",
	  	  monthNamesShort: ['1\u6708','2\u6708','3\u6708','4\u6708','5\u6708','6\u6708','7\u6708','8\u6708','9\u6708','10\u6708','11\u6708','12\u6708'],
	      dayNamesMin: ['\u65e5','\u4e00','\u4e8c','\u4e09','\u56db','\u4e94','\u516d'],
	      onClose: function( selectedDate ) {
	        $( "#to" ).datepicker( "option", "minDate", selectedDate );
	      }
	    });
	    $( "#to" ).datepicker({
	      changeMonth: true,
	      dateFormat:"yy-mm-dd",
	      monthNamesShort: ['1\u6708','2\u6708','3\u6708','4\u6708','5\u6708','6\u6708','7\u6708','8\u6708','9\u6708','10\u6708','11\u6708','12\u6708'],
	      dayNamesMin: ['\u65e5','\u4e00','\u4e8c','\u4e09','\u56db','\u4e94','\u516d'],
	      onClose: function( selectedDate ) {
	        $( "#from" ).datepicker( "option", "maxDate", selectedDate );
	      }
	    });
	    
	    $("#section").autocomplete({
	        source: function( request, response ) {
	            $.ajax({
	            	url: "<c:url value='/index/ajax/searchSection'/>",
	                dataType: "json",
	                data: {
	                    name : request.term
	                },
	                success: function( data ) {
	  					
	                	response( $.map( data, function( result ) {
	                        return {
	                            label: result.id + " : " + result.name,
	                            value: result.id,
	                            id : result.id
	                        }
	                    }));

	                    $("#searchResult").removeClass("ui-autocomplete-loading");
	                }
	            });
	        },
	        minLength: 1
		});
	    
	    var isFirst = true;
		$("#searchBtn").click(function() {
			
			var from = $("#from").val();
			var to = $("#to").val();
			var pName = $("#patientName").val();
			var doct = $("#doctadviseno").val();
			var patientId = $("#patientId").val();
			var section = $("#section").val();
			
			if (isFirst) {
				getList(doct, from, to, pName, patientId, section);
				isFirst = false;
			}

			jQuery("#s3list").jqGrid("setGridParam",{
				url:"<c:url value='/sampleTrace/data'/>?from="+from+"&to="+to+"&pName="+pName+"&doct="+doct+"&patientId="+patientId + "&section=" + section
			}).trigger("reloadGrid"); 
			
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
		
		$("#from").val("<c:out value='${today}'/>");
		$("#to").val("<c:out value='${today}'/>");
	});
	
</script>

</head>

<div class="form-inline">
	<label for="from" style="margin-left: 20px;"><b><fmt:message key="patient.searchfrom" /></b></label>
	<input type="text" id="from" name="from" class="span2"/>
	<label for="to" style="margin-left: 10px;"><b><fmt:message key="patient.searchto" /></b></label>
	<input type="text" id="to" name="to" class="span2" style="margin-left: 10px;"/>
	
	<label for="patientName" style="margin-left: 80px;"><b><fmt:message key="patientInfo.patientName" /></b></label>
	<input type="text" id="patientName" name="patientName" class="span2"/>
	
	<label for="section" style="margin-left: 80px;"><b><fmt:message key="patient.section" /></b></label>
	<input type="text" id="section" name="section" class="span2"/>
</div>
<div class="form-inline" style="margin-top:10px;">	
	<label for="patientId" style="margin-left: 20px;"><b><fmt:message key="patient.blh" /></b></label>
	<input type="text" id="patientId" name="patientId" class="span2"/>
	
	<label for="doctadviseno" style="margin-left: 270px;"><b><fmt:message key="patient.doctadviseno" /></b></label>
	<input type="text" id="doctadviseno" name="doctadviseno" class="span2"/>
	
	<button id="searchBtn" class="btn btn-info" style="margin-left: 160px;"><fmt:message key='search'/></button>
</div>

<div style="margin-top: 10px;">
	<div id="searchHeader" style="float: left; width: 350px;">
		<div id="sampleListPanel">
			<table id="s3list"></table>
			<div id="s3pager"></div>
		</div>
	</div>
	<div id="midContent"
		style="float: left; width: 550px; margin-left: 30px; display: none;">
		<div class="clearfix">
			<div id="patient-info" class="alert alert-info" style="margin-bottom:2px;padding:0px;padding-left:10px;padding-bottom:4px;">
				<div class="pItem">
					<span class="pLabel"><fmt:message key="patient.patientName" /></span>
					<span class="pText"><b id="pName"></b></span>
					<span class="pLabel"><fmt:message key="patient.patientSex" /></span>
					<span class="pText"><b id="pSex"></b></span>
					<span class="pLabel"><fmt:message key="patient.age" /></span>
					<span class="pText"><b id="pAge"></b></span>
					<span class="pLabel"><fmt:message key="patient.sampleType" /></span>
					<span class="pText"><b id="pType"></b></span>
				</div>
				
				<div class="pItem">
					<span class="pLabel"><fmt:message key="patient.blh" /></span>
					<span class="pText"><b id="blh"></b></span>
					<span class="pLabel"><fmt:message key="patient.patientId" /></span>
					<span class="pText"><b id="pId"></b></span>
				</div>

				<div class="pItem">
					<span class="pLabel"><fmt:message key="patient.section"/>&nbsp;</span>
					<span class="pText"><b id="pSection"></b></span>
					<span class="pLabel"><fmt:message key="diagnostic"/>&nbsp;</span>
					<span class="pText"><b id="diagnostic"></b></span>
				</div>
			</div>
		</div>
		<div style="height:20px"></div>
		<table class="table">
			<tbody>
			<tr><th><fmt:message key='tat.request' /></th><td><span id="tat_request"></span></td>
			<th><fmt:message key='tat.requester' /></th><td><span id="tat_requester"></span></td></tr>
			<tr><th><fmt:message key='tat.execute' /></th><td><span id="tat_execute"></span></td>
			<th><fmt:message key='tat.executor' /></th><td><span id="tat_executor"></span></td></tr>
			<tr><th><fmt:message key='tat.send' /></th><td><span id="tat_send"></span></td>
			<th><fmt:message key='tat.sender' /></th><td><span id="tat_sender"></span></td></tr>
			<tr><th><fmt:message key='tat.ksreceive' /></th><td><span id="tat_ksreceive"></span></td>
			<th><fmt:message key='tat.ksreceiver' /></th><td><span id="tat_ksreceiver"></span></td></tr>
			<tr><th><fmt:message key='tat.receive' /></th><td><span id="tat_receive"></span></td>
			<th><fmt:message key='tat.receiver' /></th><td><span id="tat_receiver"></span></td></tr>
			<tr><th><fmt:message key='tat.tester' /></th><td><span id="tat_tester"></span></td>
			<th><fmt:message key='tat.audit' /></th><td><span id="tat_audit"></span></td></tr>
			<tr><th><fmt:message key='tat.auditor' /></th><td><span id="tat_auditor"></span></td>
			<th><fmt:message key='tat.audit.tat' /></th><td><span id="audit_tat"></span></td></tr>
			</tbody>
		</table>
		<div style="font-size: 13px; display:none;margin-top: 10px;">
			<div style="margin-left:60px;">
				<input type="hidden" id="hiddenDocId"/>
			</div>
		</div>
	</div>
</div>
