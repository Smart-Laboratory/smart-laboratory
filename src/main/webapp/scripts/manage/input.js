function deleteYlxh(obj) {
	var fee = $("#fee").val();
	$("#fee").val(parseInt(fee) - parseInt(obj.id));
}

function receive(obj,event) {
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
			var sampleno = $("#sampleno_text").val();
			$.get("../sample/ajax/receive",{id:id,sampleno:sampleno},function(data) {
				var data = JSON.parse(data);
				if(data.success == 1) {
					layer.msg(data.message, {icon: 2, time: 1000});
				} else if(data.success == 2) {
					layer.msg(data.message, {icon: 2, time: 1000});
					var html = "";
					html += "<tr><td><b>医嘱号</b></td><td>" + data.id + "</td></tr>";
					html += "<tr><td><b>样本号</b></td><td>" + data.sampleno + "</td></tr>";
					html += "<tr><td><b>姓名</b></td><td>" + data.pname + "</td></tr>";
					html += "<tr><td><b>就诊卡号</b></td><td>" + data.pid + "</td></tr>";
					html += "<tr><td><b>性别</b></td><td>" + data.sex + "</td></tr>";
					html += "<tr><td><b>年龄</b></td><td>" + data.age + "</td></tr>";
					html += "<tr><td><b>诊断</b></td><td>" + data.diag + "</td></tr>";
					html += "<tr><td><b>检验目的</b></td><td>" + data.exam + "</td></tr>";
					html += "<tr><td><b>检验时间</b></td><td>" + data.receivetime + "</td></tr>";
					$("#now").html(html);
				} else {
					var html = "";
					html += "<tr><td><b>医嘱号</b></td><td>" + data.id + "</td></tr>";
					html += "<tr><td><b>样本号</b></td><td>" + data.sampleno + "</td></tr>";
					html += "<tr><td><b>姓名</b></td><td>" + data.pname + "</td></tr>";
					html += "<tr><td><b>就诊卡号</b></td><td>" + data.pid + "</td></tr>";
					html += "<tr><td><b>性别</b></td><td>" + data.sex + "</td></tr>";
					html += "<tr><td><b>年龄</b></td><td>" + data.age + "</td></tr>";
					html += "<tr><td><b>诊断</b></td><td>" + data.diag + "</td></tr>";
					html += "<tr><td><b>检验目的</b></td><td>" + data.exam + "</td></tr>";
					html += "<tr><td><b>检验时间</b></td><td>" + data.receivetime + "</td></tr>";
					$("#now").html(html);
					var rowData = {
						id:data.id,
						sampleno:data.sampleno,
						shm:data.shm,
						pname:data.pname,
						section:data.section,
						bed:data.bed,
						sex:data.sex,
						age:data.age,
						receivetime:data.receivetime,
						exam:data.exam,
						sampletype:data.sampletype,
						pid:data.pid,
						feestatus:data.feestatus,
						diag:data.diag,
						cycle:data.cycle,
						requester:data.requester,
						part:data.part,
						requestmode:data.requestmode,
						fee:data.fee
					};
					var ids = $('#new').jqGrid('getDataIDs');
		            var newId = parseInt(ids[ids.length - 1] || 0) + 1;
					$("#new").jqGrid('addRowData', newId, rowData);
					layer.msg(data.message, {icon: 1, time: 1000});
				}
			});
			break;
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
			getSampleData(id, type);
			break;
	}
}

function getSampleData(id, type) {
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
			var feeMap = data.feeMap;
			for(var key in ylxhMap) {
				$tag_obj.add(ylxhMap[key], key, feeMap[key]);
			}
			$("#sampletype").val(data.sampletype);
			$("#fee").val(data.fee);
			$("#feestatus").val(data.feestatus);
			$("#requester").val(data.requester);
			$("#receivetime").val(data.receivetime);
			$("#executetime").val(data.executetime);
		} else {
			if(type == 1) {
				layer.msg("医嘱号为" + id + "的标本不存在！", {icon: 0, time: 1000});
			} else {
				layer.msg("样本号为" + id + "的标本不存在！", {icon: 0, time: 1000});
			}
		}
	});
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
						layer.msg("就诊卡号为" + obj.value + "的病人不存在！", {icon: 0, time: 1000})
					}
				} else {
					layer.msg("医嘱号已存在，无权限修改接诊卡号！", {icon: 0, time: 1000});
				}
			});
			break;
	}
}

function Pad(num, n) {
    return (Array(n).join(0) + num).slice(-n);
}

function isDate(str){
	if(!isNaN(str.substring(0,4)) && !isNaN(str.substring(4,6)) && !isNaN(str.substring(6,8))) {
		var year = parseInt(str.substring(0,4));
		var month = parseInt(str.substring(4,6))-1;
		var day = parseInt(str.substring(6,8));
		var date=new Date(year,month,day);
		if(date.getFullYear()==year&&date.getMonth()==month&&date.getDate()==day) {
			return true;
		} else {
			return false;
		}
	} else {
		return false;
	}
} 

function sample() {
	var operate,shm,doct,sampleno,pid,section,sectionCode,pname,sex,age,diag,ylxh,exam,feestatus,requester,receivetime,executetime,fee;
	operate = $("#operate").val();
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
		
		var msg = "";
		var post = true;
		if(ylxh == "") {
			msg = "检验目的不能为空！";
			post = false;
		}
		if(pname == "") {
			msg = "患者姓名不能为空！";
			post = false;
		}
		if(sampleno.length != 14) {
			msg = "样本号长度错误，格式不正确！";
			post = false;
		} else {
			if(!isDate(sampleno.substring(0,8))) {
				msg = "样本号日期格式不正确！";
				post = false;
			}
			if($("#hiddenSegment").val().indexOf(sampleno.substring(8,11)) == -1) {
				msg = "样本号检验段格式不正确！";
				post = false;
			}
			if(isNaN(Number(sampleno.substring(11,14)))) {
				msg = "样本号后3位编号不是数字！";
				post = false;
			}
		}
		
		if(post) {
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
				$('#sampleForm')[0].reset();
				$('#examinaim').data('tag').clear();
				$("#sampleno").val(sampleno.substring(0,11) + Pad((parseInt(sampleno.substring(11,14)) + 1),3));
				var html = "";
				html += "<tr><td><b>医嘱号</b></td><td>" + data.id + "</td></tr>";
				html += "<tr><td><b>样本号</b></td><td>" + data.sampleno + "</td></tr>";
				html += "<tr><td><b>姓名</b></td><td>" + data.pname + "</td></tr>";
				html += "<tr><td><b>就诊卡号</b></td><td>" + data.pid + "</td></tr>";
				html += "<tr><td><b>性别</b></td><td>" + data.sex + "</td></tr>";
				html += "<tr><td><b>年龄</b></td><td>" + data.age + "</td></tr>";
				html += "<tr><td><b>诊断</b></td><td>" + data.diag + "</td></tr>";
				html += "<tr><td><b>检验目的</b></td><td>" + data.exam + "</td></tr>";
				html += "<tr><td><b>检验时间</b></td><td>" + data.receivetime + "</td></tr>";
				$("#now").html(html);
				if(data.success) {
					var rowData = {
						id:data.id,
						sampleno:data.sampleno,
						shm:data.shm,
						pname:data.pname,
						section:data.section,
						bed:data.bed,
						sex:data.sex,
						age:data.age,
						receivetime:data.receivetime,
						exam:data.exam,
						sampletype:data.sampletype,
						pid:data.pid,
						feestatus:data.feestatus,
						diag:data.diag,
						cycle:data.cycle,
						requester:data.requester,
						part:data.part,
						requestmode:data.requestmode,
						fee:data.fee
					};
					var ids = $('#new').jqGrid('getDataIDs');
		            var newId = parseInt(ids[ids.length - 1] || 0) + 1;
					$("#new").jqGrid('addRowData', newId, rowData);
					layer.msg(data.message, {icon: 1, time: 1000});
				} else {
					layer.msg(data.message, {icon:2, time: 1000});
				}
			});
		} else {
			layer.msg(msg, {icon: 2, time: 1000});
		}
	}
}

function addSample() {
	$("#sampleno").val($("#sampleno_text").val());
	$("#operate").val("add");
	layer.open({
		type: 1,
		area: ['1000px','360px'],
		skin: 'layui-layer-molv',
		fix: false, //不固定
		maxmin: false,
		shade:0.6,
		title: "样本信息录入",
		content: $("#formDialog")
	});
}

function editSample() {
	$("#sampleno").val($("#sampleno_text").val());
	$("#operate").val("edit");
	var id = $("#new").jqGrid('getGridParam', 'selrow');
    if (id == null || id == 0) {
        layer.msg('请先选择要修改的数据', {icon: 2, time: 1000});
        return false;
    }
    getSampleData(id,1);
	layer.open({
		type: 1,
		area: ['1000px','360px'],
		skin: 'layui-layer-molv',
		fix: false, //不固定
		maxmin: false,
		shade:0.6,
		title: "样本信息编辑",
		content: $("#formDialog")
	});
}

function deleteSample() {
	$("#sampleno").val($("#sampleno_text").val());
	$("#operate").val("delete");
	var id = $("#new").jqGrid('getGridParam', 'selrow');
    if (id == null || id == 0) {
        layer.msg('请先选择要删除的数据', {icon: 2, time: 1000});
        return false;
    }
    getSampleData(id,1);
	layer.open({
		type: 1,
		area: ['1000px','360px'],
		skin: 'layui-layer-molv',
		fix: false, //不固定
		maxmin: false,
		shade:0.6,
		title: "样本信息删除",
		content: $("#formDialog")
	});
}

function clearData() {
	$('#sampleForm')[0].reset();
	$('#examinaim').data('tag').clear();
}

$(function() {
	$("#sampletype").val("C");
	
	$("#section").autocomplete({
        source: function( request, response ) {
            $.ajax({
            	url: "../ajax/searchSection",
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
            	url: "../ajax/searchContactInfo",
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
			$.get("../ajax/searchYlsf",{query:query},function(data) {
				var json = jQuery.parseJSON(data);
				process(json.list);
			});
		}
	})
	var $tag_obj = $('#examinaim').data('tag');
	//$tag_obj.add('Programmatically Added');
	
	$("#new").jqGrid({
		url: "../sample/ajax/getReceived",
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
		height:"100%",
		rowNum:-1
	});
});