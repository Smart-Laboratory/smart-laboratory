<%@ include file="/common/taglibs.jsp"%>

<head>
<title><fmt:message key="menu.sample.in"/></title>

<script type="text/javascript" src="<c:url value='/scripts/grid.locale-cn.js'/> "></script>
<link rel="stylesheet" type="text/css"  href="<c:url value='/styles/ruleLib.css'/>" />


<style>
#samplein fieldset {
	border: 1px solid #B5B8C8;
	padding-bottom: 10px;
	padding-left: 10px;
	padding-right: 10px;
}

#samplein legend {
	font-weight: bold;
	padding: 2px 0 0 18px;
	display: block;
	font-size:14px;
	line-height:30px;
	width:10%;
	border-bottom:0px;
	margin-bottom:5px;
}
</style>

<script type="text/javascript">
function getData(obj,event) {
	var e=e||event;
	var key=event.keyCode;;
	if(navigator.appName=="Netscape"){
		key=e.which;
	}else{
		key=event.keyCode;
	}
	switch(key){
		case 13 : 
			var id=obj.value;
			$.get("<c:url value='/sample/ajax/get'/>",{id:id},function(data) {
				var data = JSON.parse(data);
				$("#stayhospitalmode").val(data.stayhospitalmode);
				$("#doctadviseno").val(data.doctadviseno);
				if(data.sampleno != '0') {
					$("#sampleno").val(data.sampleno);
				}
				$("#patientid").val(data.patientid);
				$("#section").val(data.section);
				$("#sectionCode").val(data.sectionCode);
				$("#patientname").val(data.patientname);
				$("#sex").val(data.sex);
				$("#age").val(data.age);
				$("#diagnostic").val(data.diagnostic);
				$("#examinaim").val(data.examinaim);
				$("#sampletype").val(data.sampletype);
				$("#sampletypeCode").val(data.sampletypeCode);
				$("#fee").val(data.fee);
				$("#requester").val(data.requester);
				$("#receivetime").val(data.receivetime);
				$("#executetime").val(data.executetime);
				$("#ylxh").val(data.ylxh);
			});
			break;
	}
}

function pad(num) {
	if(num>0 && num<10) {
		return '00' + num;
	}
	if(num>10 && num<100) {
		return '0' + num;
	}
	return '' + num;  
}

$(function() {
	$("#receivetime").val('${time}');
	$("#executetime").val('${time}');
	$("#sampleno").val('${sampleno}');
	$("#profiles").html('${profiles}');
	
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
                            label: result.name,
                            value: result.name,
                            id : result.id
                        }
                    }));
                }
            });
        },
        minLength: 1,
        select: function( event, ui ) {
        	$( "#sectionCode" ).val(ui.item.id);
        	$( "#section" ).val(ui.item.value);
            return false;
		}
	});
	
	$("#sampletype").autocomplete({
        source: function( request, response ) {
            $.ajax({
            	url: "<c:url value='/index/ajax/searchSampletype'/>",
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
                        }
                    }));
                }
            });
        },
        minLength: 1,
        select: function( event, ui ) {
        	$( "#sampletypeCode" ).val(ui.item.id);
        	$( "#sampletype" ).val(ui.item.value);
            return false;
		}
	});
	
	$("#exam_ab").autocomplete({
        source: function( request, response ) {
            $.ajax({
            	url: "<c:url value='/index/ajax/searchYlxh'/>",
                dataType: "json",
                data: {
                    name : request.term
                },
                success: function( data ) {
                	response( $.map( data, function( result ) {
                        return {
                            label: result.name,
                            value: request.term,
                            id : result.id
                        }
                    }));
                }
            });
        },
        minLength: 1,
        select: function( event, ui ) {
        	var exam = $( "#examinaim" ).val();
        	var ylxh = $( "#ylxh" ).val();
        	if(ui.item.value.indexOf("-")>=0) {
        		if (ylxh.indexOf(ui.item.id + "+")>0) {
            		$( "#examinaim" ).val( exam.replace(ui.item.label + "+", "") );
            		$( "#ylxh" ).val( ylxh.replace(ui.item.id + "+","") );
            	} else {
            		$( "#examinaim" ).val( exam.replace(ui.item.label, "") );
            		$( "#ylxh" ).val( ylxh.replace(ui.item.id,"") );
            	}
        	} else {
        		if (exam == "") {
            		$( "#examinaim" ).val( ui.item.label );
            		$( "#ylxh" ).val( ui.item.id );
            	} else {
            		$( "#examinaim" ).val( exam + "+" + ui.item.label );
            		$( "#ylxh" ).val( ylxh + "+" + ui.item.id );
            	}
        	}
            return false;
		}
	});
	
	$("#sampleIn").click(function() {
		var sampleno = $("#sampleno").val();
		var date = sampleno.substring(0,4) + "/" +sampleno.substring(4,6) + "/" + sampleno.substring(6,8);
		var code = sampleno.substring(8,11);
		var num = sampleno.substring(11,14);
		var profile = $("#profiles").html();
		var daynum = (new Date(date).getTime() - new Date().getTime())/(24 * 60 * 60 * 1000);
		var k=/^(\d{4})\/([\d]+)\/([\d]+)$/
	    var reg = date.match(/^(\d{4})\/([\d]+)\/([\d]+)$/);
	    if(!k.test(date) || reg[2]>12 || reg[3]>31 || daynum>31 ||(daynum<-1)){
	    	alert("\u6837\u672c\u53f7\u65e5\u671f\u4e0d\u6b63\u786e\uff01");
	    } else if(!/^\d+$/.test(num)){
	        alert("\u6837\u672c\u53f7\u540e\u4e09\u4f4d\u5fc5\u987b\u662f\u6570\u5b57\uff01");
	    } else if (profile.indexOf(code)<0) {
	    	alert("\u6837\u672c\u53f7\u68c0\u9a8c\u6bb5\u4e0d\u5c5e\u4e8e\u5f53\u524d\u79d1\u5ba4\uff01");
	    } else {
	    	$.get("<c:url value='/sample/ajax/update'/>",{
				doctadviseno:$("#doctadviseno").val(),
				stayhospitalmode:$("#stayhospitalmode").val(),
				sampleno:$("#sampleno").val(),
				patientid:$("#patientid").val(),
				section:$("#sectionCode").val(),
				patientname:$("#patientname").val(),
				sex:$("#sex").val(),
				age:$("#age").val(),
				diagnostic:$("#diagnostic").val(),
				examinaim:$("#examinaim").val(),
				sampletype:$("#sampletypeCode").val(),
				ylxh:$("#ylxh").val()
			},function(data) {
				$("#tbody").append(data);
				var lastsample = $("#sampleno").val();
				var number = parseInt(lastsample.substring(11,14)) + 1;
				$("#stayhospitalmode").val(1);
				$("#doctadviseno").val('');
				$("#sampleno").val(lastsample.substring(0,11) + pad(number));
				$("#patientid").val('');
				$("#sectionCode").val(''),
				$("#section").val('');
				$("#patientname").val('');
				$("#sex").val(3);
				$("#age").val(0);
				$("#diagnostic").val('');
				$("#examinaim").val('');
				$("#sampletype").val('');
				$("#sampletypeCode").val('');
				$("#fee").val('');
				$("#requester").val('');
				$("#ylxh").val('');
			});
	    }
		
	});
	
	$("#refresh").click(function() {
		window.location.href="<c:url value='/sample/in'/>";
	});
	
	$("#searchSample").click(function() {
		window.open("<c:url value='/sample/list'/>") ;
	});
	
	
}); 


</script>
</head>
<div id="samplein">
	<fieldset style="width:95%; margin-left:4px;">
		<legend style="margin-top:3px;"><fmt:message key='sample.pinfo'/></legend>
		<div class="form-inline">
			<label for="stayhospitalmode" style="margin-left:25px;"><fmt:message key="sample.shm"/></label>
			<select id="stayhospitalmode" name="stayhospitalmode" class="span2" style="margin-left:20px;">
				<option value="1">
					<fmt:message key="stayhospitalmode.1" />
				</option>
				<option value="2">
					<fmt:message key="stayhospitalmode.2" />
				</option>
				<option value="3">
					<fmt:message key="stayhospitalmode.3" />
				</option>
				<option value="4">
					<fmt:message key="stayhospitalmode.4" />
				</option>
				<option value="5">
					<fmt:message key="stayhospitalmode.5" />
				</option>
				<option value="6">
					<fmt:message key="stayhospitalmode.6" />
				</option>
				<option value="7">
					<fmt:message key="stayhospitalmode.7" />
				</option>
				<option value="8">
					<fmt:message key="stayhospitalmode.8" />
				</option>
			</select>
			<label for="doctadviseno" style="margin-left:76px;"><fmt:message key="patient.doctadviseno"/></label>
			<input type="text" id="doctadviseno" name="doctadviseno" class="span2" style="margin-left:24px;" onkeypress="getData(this,event);">
			<label for="sampleno" style="margin-left:80px;"><fmt:message key="patients.sampleNo"/></label>
			<input type="text" id="sampleno" name="sampleno" class="span2" style="margin-left:20px;">
		</div>
		<div class="form-inline" style="margin-top:5px;">
			<label for="patientid" style="margin-left:25px;"><fmt:message key="patient.patientId"/></label>
			<input type="text" id="patientid" name="patientid" class="span2" style="margin-left:20px;">
			<label for="section" style="margin-left:80px;"><fmt:message key="sample.section"/></label>
			<input type="text" id="section" name="section" class="span2" style="margin-left:34px;">
			<input type="hidden" id="sectionCode" name="sectionCode">
		</div>
		<div class="form-inline" style="margin-top:5px;">
			<label for="patientname" style="margin-left:25px;"><fmt:message key="patientInfo.patientName"/></label>
			<input type="text" id="patientname" name="patientname" class="span2" style="margin-left:20px;">
			<label for="sex" style="margin-left:80px;"><fmt:message key="sample.sex"/></label>
			<select id="sex" name="sex" class="span2" style="margin-left:34px;">
				<option value="1">
					<fmt:message key="sex.male" />
				</option>
				<option value="2">
					<fmt:message key="sex.female" />
				</option>
				<option value="3" selected>
					<fmt:message key="sex.unknow" />
				</option>
			</select>
			<label for="age" style="margin-left:76px;"><fmt:message key="sample.age"/></label>
			<div class="input-append">
				<input class="span1" id="age" name="age" type="text" style="margin-left:30px;">
				<span class="add-on"><fmt:message key="sample.years"/></span>
			</div>
		</div>
		<div class="form-inline" style="margin-top:5px;">
			<label for="sampletype" style="margin-left:25px;"><fmt:message key="patient.sampleType"/></label>
			<input type="text" id="sampletype" name="sampletype" class="span2" style="margin-left:20px;">
			<input type="hidden" id="sampletypeCode" name="sampletypeCode">
			<label for="diagnostic" style="margin-left:80px;"><fmt:message key="patient.diagnostic"/></label>
			<div class="input-append">
				<input type="text" id="diag_ab" name="diag_ab" class="span1" style="margin-left:10px;">
				<span class="add-on"></span>
				<input type="text" id="diagnostic" name="diagnostic" class="span3">
			</div>
		</div>
		<div class="form-inline" style="margin-top:5px;">
			<label for="requester" style="margin-left:25px;"><fmt:message key="requester.name"/></label>
			<input type="text" id="requester" name="requester" class="span2" style="margin-left:20px;">
			<label for="fee" style="margin-left:80px;"><fmt:message key="sample.feestatus"/></label>
			<input type="text" id="fee" name="fee" class="span2" style="margin-left:10px;">
			<label for="executetime" style="margin-left:80px;"><fmt:message key="tat.execute"/></label>
			<input type="text" id="executetime" name="executetime" class="span3" style="margin-left:6px;">
		</div>
		<div class="form-inline" style="margin-top:5px;">
			<label for="examinaim" style="margin-left:25px;"><fmt:message key="patients.examinaim"/></label>
			<div class="input-append">
				<input type="text" id="exam_ab" name="exam_ab" class="span2" style="margin-left:20px;">
				<span class="add-on"></span>
				<input type="text" id="examinaim" name="examinaim" class="span4">
				<input type="hidden" id="ylxh" name="ylxh" >
			</div>
		</div>
		<div class="form-inline" style="margin-top:10px;">
			<label for="receivetime" style="margin-left:25px;"><fmt:message key="tat.receive"/></label>
			<input type="text" id="receivetime" name="receivetime" class="span3" style="margin-left:20px;">
			<button id="sampleIn" type="button" class="btn btn-success" style="float:right;margin-right:30px;"><fmt:message key='update.name'/></button>
			<button id="refresh" type="button" class="btn btn-info" style="float:right;margin-right:10px;"><fmt:message key='button.add'/></button>
			<button id="searchSample" type="button" class="btn btn-info" style="float:right;margin-right:10px;width:160px;"><fmt:message key='sample.search'/></button>
		</div>
	</fieldset>
	<fieldset style="width:95%; margin-left:4px;">
		<legend style="margin-top:3px;"><fmt:message key='sample.ptest'/></legend>
		<div style="height:500px;overflow:auto;">
		<table style="margin-top:10px;text-align:center;">
			<thead><tr style="background-color: #9FCEE0;">
				<td width="30px"><fmt:message key='sample.sequence'/></td>
 				<td width="80px"><fmt:message key='patient.doctadviseno'/></td>
				<td width="80px"><fmt:message key='patients.sampleNo'/></td>
				<td width="70px"><fmt:message key='patient.sampleType'/></td>
				<td width="100px"><fmt:message key='patient.section'/></td>
				<td width="70px"><fmt:message key='patient.patientId'/></td>
				<td width="60px"><fmt:message key='patient.patientName'/></td>
				<td width="30px"><fmt:message key='patients.sex'/></td>
				<td width="30px"><fmt:message key='patients.age'/></td>
				<td width="100px"><fmt:message key='diagnostic'/></td>
				<td width="100px"><fmt:message key='patients.examinaim'/></td>
				<td width="70px"><fmt:message key='requester.name'/></td>
				<td width="100px"><fmt:message key='tat.request'/></td>
				<td width="100px"><fmt:message key='tat.receive'/></td>
				<td width="20px" style="border-right: solid 1 #9FCEED">&nbsp;</td>
			</tr></thead>
			<tbody id="tbody">
			</tbody>
		</table>
		</div>
	</fieldset>
</div>
<div id="profiles" style="display:none;"></div>  