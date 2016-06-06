var isFirst = true;
function showData(type) {
	if(type == 1) {
		$("#tableBody").html();
	} else {
		if(isFirst) {
			$("#oldTable").jqGrid({
				url: "../sample/ajax/old",
				mtype: "GET",
				datatype: "json",
				colNames: ['样本号', '在院方式', '姓名','科室','床号','性别','年龄','医嘱号','接收时间','检验目的','样本类型','就诊卡号','收费状态','临床诊断','生理周期','申请者','采集部位','申请模式','金额'],
				colModel: [
					{ name: 'sampleno', index: 'sampleno', width: 120},
					{ name: 'shm', index: 'shm', width: 70},
					{ name: 'pname', index: 'pname', width: 80 },
					{ name: 'section', index: 'section', width: 80 },
					{ name: 'bed', index: 'bed', width: 40 },
					{ name: 'sex', index: 'sex', width: 40 },
					{ name: 'age', index: 'age', width: 40 },
					{ name: 'id', index: 'id', width: 80 },
					{ name: 'receivetime', index: 'receivetime', width: 130 },
					{ name: 'exam', index: 'exam', width: 100 },
					{ name: 'sampletype', index: 'sampletype', width: 70 },
					{ name: 'pid', index: 'pid', width: 100 },
					{ name: 'feestatus', index: 'feestatus', width: 70 },
					{ name: 'diag', index: 'diag', width: 100 },
					{ name: 'cycle', index: 'cycle', width: 70 },
					{ name: 'requester', index: 'requester', width: 70 },
					{ name: 'part', index: 'part', width: 70 },
					{ name: 'requestmode', index: 'requestmode', width: 70 },
					{ name: 'fee', index: 'fee', width: 40 }
				],
				viewrecords: true,
				autowidth:true,
				height: "100%",
				rowNum: 20,
				rowList:[10,20,30],
				rownumbers: true, 
				rownumWidth: 35,
				pager: "#pager"
			});
			isFirst = false;
		} else {
			jQuery("#oldTable").jqGrid('setGridParam',{
				url: "../sample/ajax/old"
			}).trigger('reloadGrid');//重新载入
		}
	}
}
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
			var type = 1;
			if(obj.id == 'sampleno') {
				type = 2;
			}
			$.get("../sample/ajax/get",{id:id,type:type},function(data) {
				if(data != "") {
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
					var $tag_obj = $('#examinaim').data('tag');
					$tag_obj.clear();
					var ylxhMap = data.ylxhMap;
					for(var key in ylxhMap) {
						$tag_obj.add(ylxhMap[key], key);
					}
					$("#sampletype").val(data.sampletype);
					$("#fee").val(data.fee);
					$("#feestatus").val(data.feestatus);
					$("#requester").val(data.requester);
					$("#receivetime").val(data.receivetime);
					$("#executetime").val(data.executetime);
				} else {
					if(type == 1) {
						alert("医嘱号为" + id + "的标本不存在！");
					} else {
						alert("样本号为" + id + "的标本不存在！");
					}
				}
			});
			break;
	}
}

function getPatient(obj,event) {
	var e=e||event;
	var key=event.keyCode;;
	if(navigator.appName=="Netscape"){
		key=e.which;
	}else{
		key=event.keyCode;
	}
	switch(key){
		case 13 : 
			$.get("../sample/ajax/getpatient",{pid:obj.value},function(data) {
				var data = JSON.parse(data);
				if($("#doctadviseno").val() == "") {
					if(data.ispid) {
						$("#patientid").val(data.pid);
						$("#patientname").val(data.pname);
						$("#sex").val(data.sex);
						$("#age").val(data.age);
					} else{
						alert("就诊卡号为" + obj.value + "的病人不存在！")
					}
				} else {
					alert("医嘱号已存在，无权限修改接诊卡号！");
				}
			});
			break;
	}
}

function Pad(num, n) {
    return (Array(n).join(0) + num).slice(-n);
}

function sample() {
	var operate,shm,doct,sampleno,pid,section,sectionCode,pname,sex,age,diag,ylxh,exam,feestatus,requester,receivetime,executetime,fee;
	operate = $("input[name='edittype']:checked").val();
	if(operate == 'cancel') {
		$('#sampleForm')[0].reset();
		$('#examinaim').data('tag').clear();
	} else {
		ylxh = "";
		exam ="";
		shm = $("#stayhospitalmode").val();
		doct = $("#doctadviseno").val();
		sampleno = $("#sampleno").val();
		pid = $("#patientid").val();
		section = $("#section").val();
		sectionCode = $("#sectionCode").val();
		pname = $("#patientname").val();
		sex = $("#sex").val();
		age = $("#age").val();
		ageunit = $("#ageunit").val();
		diag = $("#diagnostic").val();
		var len = $('#examTag .tag').length - 1;
		$('#examTag .tag').each(function(i) {
			if(i == len) {
				ylxh += this.id;
				exam += $(this).text().replace("×","");
			} else {
				ylxh += this.id + "+";
				exam += $(this).text().replace("×","") + "+";
			}
		});
		sampletype = $("#sampletype").val();
		fee = $("#fee").val();
		feestatus = $("#feestatus").val();
		requester = $("#requester").val();
		receivetime = $("#receivetime").val();
		executetime = $("#executetime").val();
		
		$.post("../sample/ajax/editSample", {
			operate : operate,
			shm : shm,
			doct : doct,
			sampleno : sampleno,
			pid : pid,
			section : section,
			sectionCode : sectionCode,
			pname : pname,
			sex : sex,
			age : age,
			ageunit : ageunit,
			diag : diag,
			sampletype : sampletype,
			fee : fee,
			feestatus : feestatus,
			requester : requester,
			receivetime : receivetime,
			executetime : executetime,
			exam : exam,
			ylxh : ylxh
		},
		function(data) {
			var data = JSON.parse(data);
			var sampleno = $("#sampleno").val();
			$("#cancel").click();
			$('#examinaim').data('tag').clear();
			$("#sampleno").val(sampleno.substring(0,11) + Pad((parseInt(sampleno.substring(11,14)) + 1),3));
			var html = "<tr>";
			html += "<td><i class='fa fa-pencil'/></td>";
			html += "<td>" + data.sampleno + "</td>";
			html += "<td>" + data.shm + "</td>";
			html += "<td>" + data.pname + "</td>";
			html += "<td>" + data.section + "</td>";
			html += "<td>" + data.bed + "</td>";
			html += "<td>" + data.sex + "</td>";
			html += "<td>" + data.age + "</td>";
			html += "<td>" + data.id + "</td>";
			html += "<td>" + data.receivetime + "</td>";
			html += "<td>" + data.exam + "</td>";
			html += "<td>" + data.sampletype + "</td>";
			html += "<td>" + data.pid + "</td>";
			html += "<td>" + data.feestatus + "</td>";
			html += "<td>" + data.diag + "</td>";
			html += "<td>" + data.cycle + "</td>";
			html += "<td>" + data.requester + "</td>";
			html += "<td>" + data.part + "</td>";
			html += "<td>" + data.requestmode + "</td>";
			html += "<td>" + data.fee + "</td>";
			html += "</tr>";
			$("#tableBody").append(html);
		});
	}
}

$(function() {
	$("#sampletype").val("C");
	
	$("#section").autocomplete({
        source: function( request, response ) {
            $.ajax({
            	url: "../rmi/ajax/searchSection",
                dataType: "json",
                data: {
                    name : request.term
                },
                success: function( data ) {
                	response( $.map( data, function( result ) {
                        return {
                            label: result.id + " : " + result.name,
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
	
	$("#requester").autocomplete({
        source: function( request, response ) {
            $.ajax({
            	url: "../contact/ajax/search",
                dataType: "json",
                data: {
                    name : request.term
                },
                success: function( data ) {
                	response( $.map( data, function( result ) {
                        return {
                            label: result.id + " : " + result.name,
                            value: result.name,
                            id : result.id
                        }
                    }));
                }
            });
        },
        minLength: 1
	});
	
	var tag_input = $('#examinaim');
	tag_input.tag({
		placeholder:tag_input.attr('placeholder'),
		style:tag_input.attr('class'),
		source: function(query, process) {
			$.get("../sample/ajax/searchYlsf",{query:query},function(data) {
				var json = jQuery.parseJSON(data);
				process(json.list);
			});
		}
	})
	var $tag_obj = $('#examinaim').data('tag');
	//$tag_obj.add('Programmatically Added');
});