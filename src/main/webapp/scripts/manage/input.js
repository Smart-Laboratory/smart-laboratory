function deleteYlxh(obj) {
	var fee = $("#fee").val();
	$("#fee").val(parseInt(fee) - parseInt(obj.id));
}

function receive(obj,event) {
	var e=e||event;
    var key = event.keyCode;
    if(navigator.appName=="Netscape"){
		key=e.which;
	}else{
		key=event.keyCode;
	}
	switch(key){
		case 13 : 
			var id=obj.value;
			var sampleno = $("#sampleno_text").val();
			$.get(baseUrl + "/sample/ajax/hasSameSample",{id:id,sampleno:sampleno},function(data) {
				var data = JSON.parse(data);
				if(data.success == 0) {
					layer.msg(data.message, {icon: 2, time: 1000});
					$("#sampleno_text").val(sampleno.substring(0,11) + Pad((parseInt(sampleno.substring(11,14)) + 1),3));
				} else {
					$.get(baseUrl + "/sample/ajax/receive",{id:id,sampleno:sampleno},function(data) {
						var data = JSON.parse(data);
						if(data.success == 1) {
							layer.msg(data.message, {icon: 2, time: 1000});
						} else if(data.success == 2) {
							layer.msg(data.message, {icon: 2, time: 1000});
							var html = "";
							html += "<tr><td><b>医嘱号</b></td><td>" + data.barcode + "</td></tr>";
							html += "<tr><td><b>样本号</b></td><td>" + data.sampleno + "</td></tr>";
							html += "<tr><td><b>姓名</b></td><td>" + data.pname + "</td></tr>";
							html += "<tr><td><b>就诊卡号</b></td><td>" + data.pid + "</td></tr>";
							html += "<tr><td><b>性别</b></td><td>" + data.sex + "</td></tr>";
							html += "<tr><td><b>年龄</b></td><td>" + data.age + "</td></tr>";
							html += "<tr><td><b>诊断</b></td><td>" + data.diag + "</td></tr>";
							html += "<tr><td><b>检验目的</b></td><td>" + data.exam + "</td></tr>";
							html += "<tr><td><b>检验时间</b></td><td>" + data.receivetime + "</td></tr>";
							$("#now").html(html);
						}else if(data.success == 3){
							var html = "";
							html += "<tr><td><b>医嘱号</b></td><td>" + data.barcode + "</td></tr>";
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
								barcode:data.barcode,
								sampleno:data.sampleno,
								shm:data.shm,
								pname:data.pname,
								section:data.section,
								bed:data.bed,
								sex:data.sex,
								age:data.age,
								receivetime:data.receivetime,
								exam:data.exam,
								pid:data.pid,
								feestatus:data.feestatus,
								diag:data.diag,
								cycle:data.cycle,
								requester:data.requester,
								part:data.part,
								requestmode:data.requestmode,
								fee:data.fee,
								sampleType: data.sampleType,
								sampleTypeValue: data.sampleTypeValue,
								sampleStatus:data.sampleStatus,
								sampleStatusValue:data.sampleStatusValue
							};
							var ids = $('#new').jqGrid('getDataIDs');
							var newId = parseInt(ids[ids.length - 1] || 0) + 1;
							$("#new").jqGrid('addRowData', newId, rowData);
							$("#sampleno_text").val(sampleno.substring(0,11) + Pad((parseInt(sampleno.substring(11,14)) + 1),3));
							$("#receive_id").val("").focus();
							layer.msg(data.message, {icon: 1, time: 1000});
						}else {
							layer.msg(data.message, {icon: 2, time: 1000});
						}
					});
				}
			});
			break;
	}
}

function getData(obj,event) {
	var e=e||event;
    var key = event.keyCode;
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
	$.get(baseUrl + "/sample/ajax/get",{id:id,type:type},function(data) {
		if(data != "") {
			var data = JSON.parse(data);
			$("#stayhospitalmode").val(data.stayhospitalmode);
			$("#barcode").val(data.barcode);
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
			$("#sampleType").val(data.sampleTypeValue);
			$("#hiddenSampleType").val(data.sampleType);
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
    var key = event.keyCode;
    if(navigator.appName=="Netscape"){
		key=e.which;
	}else{
		key=event.keyCode;
	}
	switch(key){
		case 13 : 
			$.get(baseUrl + "/sample/ajax/getpatient",{pid:obj.value},function(data) {
				var data = JSON.parse(data);
				if($("#barcode").val() == "") {
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
	var operate,shm,barcode,sampleno,pid,section,sectionCode,pname,sex,age,diag,ylxh,exam,feestatus,requester,receivetime,executetime,fee,ageunit,sampleType;
	operate = $("#operate").val();
	ylxh = "";
	exam ="";
	shm = $("#stayhospitalmode").val();
	barcode = $("#barcode").val();
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
	sampleType = $("#hiddenSampleType").val();
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
		$.post(baseUrl + "/sample/ajax/editSample", {
			operate : operate,
			shm : shm,
			barcode : barcode,
			sampleno : sampleno,
			pid : pid,
			section : section,
			sectionCode : sectionCode,
			pname : pname,
			sex : sex,
			age : age,
			ageunit : ageunit,
			diag : diag,
			sampleType : sampleType,
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
			var html = "";
			html += "<tr><td><b>医嘱号</b></td><td>" + data.barcode + "</td></tr>";
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
					barcode:data.barcode,
					sampleno:data.sampleno,
					shm:data.shm,
					pname:data.pname,
					section:data.section,
					bed:data.bed,
					sex:data.sex,
					age:data.age,
					receivetime:data.receivetime,
					exam:data.exam,
					pid:data.pid,
					feestatus:data.feestatus,
					diag:data.diag,
					cycle:data.cycle,
					requester:data.requester,
					part:data.part,
					requestmode:data.requestmode,
					fee:data.fee,
					sampleType: data.sampleType,
					sampleTypeValue: data.sampleTypeValue,
					sampleStatus:data.sampleStatus,
					sampleStatusValue:data.sampleStatusValue
				};
				if(operate == 'add') {
					var ids = $('#new').jqGrid('getDataIDs');
					var newId = parseInt(ids[ids.length - 1] || 0) + 1;
					$("#new").jqGrid('addRowData', newId, rowData);
				} else if(operate == 'edit') {
					var id = $("#new").jqGrid('getGridParam', 'selrow');
					$("#new").jqGrid('setRowData', id, rowData);
				} else if(operate == 'delete') {
					var id = $("#new").jqGrid('getGridParam', 'selrow');
					$("#new").jqGrid('delRowData', id);
				}
				layer.closeAll();
				layer.msg(data.message, {icon: 1, time: 1000});
			} else {
				layer.msg(data.message, {icon:2, time: 1000});
			}
		});
	} else {
		layer.msg(msg, {icon: 2, time: 1000});
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
	var rowData = $("#new").jqGrid('getRowData',id);
    if (id == null || id == 0) {
        layer.msg('请先选择要修改的数据', {icon: 2, time: 1000});
        return false;
    }
    getSampleData(rowData.barcode,1);
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
	var rowData = $("#new").jqGrid('getRowData',id);
    if (id == null || id == 0) {
        layer.msg('请先选择要删除的数据', {icon: 2, time: 1000});
        return false;
    }
    getSampleData(rowData.barcode,1);
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
            	url: baseUrl + "/ajax/searchSection",
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
            	url: baseUrl + "/ajax/searchContactInfo",
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

	$("#sampleType").autocomplete({
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
			$("#sampleType").val(ui.item.value);
			$("#hiddenSampleType").val(ui.item.sign);
		}
	});
	
	var tag_input = $('#examinaim');
	tag_input.tag({
		placeholder:tag_input.attr('placeholder'),
		style:tag_input.attr('class'),
		source: function(query, process) {
			$.get(baseUrl + "/ajax/searchYlsf",{query:query},function(data) {
				var json = jQuery.parseJSON(data);
				process(json.list);
			});
		}
	});
	var $tag_obj = $('#examinaim').data('tag');
	//$tag_obj.add('Programmatically Added');
	var clientHeight = $(window).innerHeight();
	var height = clientHeight - $('#head').height()- $('.footer-content').height() - 200;
	$("#new").jqGrid({
		url: baseUrl + "/sample/ajax/getReceived",
		mtype: "GET",
		datatype: "json",
		colNames: ['样本状态','SAMPLESTATUS','样本号', '在院方式', '姓名','科室','床号','性别','年龄','医嘱号','接收时间','检验目的','样本类型','SAMPLETYPE','就诊卡号','收费状态','临床诊断','生理周期','申请者','采集部位','申请模式','金额'],
		colModel: [
			{ name: 'sampleStatusValue', index: 'sampleStatusValue', width: 70},
			{ name: 'sampleStatus', index: 'sampleStatus', hidden: true},
			{ name: 'sampleno', index: 'sampleno', width: 120},
			{ name: 'shm', index: 'shm', width: 70},
			{ name: 'pname', index: 'pname', width: 80 },
			{ name: 'section', index: 'section', width: 80 },
			{ name: 'bed', index: 'bed', width: 40 },
			{ name: 'sex', index: 'sex', width: 40 },
			{ name: 'age', index: 'age', width: 40 },
			{ name: 'barcode', index: 'barcode', width: 120 },
			{ name: 'receivetime', index: 'receivetime', width: 130 },
			{ name: 'exam', index: 'exam', width: 100 },
			{ name: 'sampleTypeValue', index: 'sampleTypeValue', width: 70 },
			{ name: 'sampleType', index: 'sampleType', hidden: true },
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
		shrinkToFit: true,
		altRows:true,
		height: height,
		rowNum:30
	});

	labChange=function(select) {
		$.ajax({
			type: 'POST',
			url: "../audit/labChange?lab="+$(select).children().attr("title"),
			success:function(data){
				var section = $(select).children().attr("title");
				$("#labText").html($(select).children().html());
				window.location.reload();

			}
		});
	}
});

