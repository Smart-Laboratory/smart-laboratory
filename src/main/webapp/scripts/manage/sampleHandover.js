$(function() {
	laydate.skin('molv');
	laydate({
		elem: '#from',
		event: 'focus',
		festival: true,
		format: 'YYYY-MM-DD'
	});
	laydate({
		elem: '#to',
		event: 'focus',
		festival: true,
		format: 'YYYY-MM-DD'
	});
	$( "#from" ).val(new Date().Format("yyyy-MM-dd"));
	$( "#to" ).val(new Date().Format("yyyy-MM-dd"));

	$("#doctout").focus();
	getOutList();
	getWaitOutList();
	getReciveList();

	$("#refuse").click(function(){
		window.open("../quality/invalidSampleForm?id="+$("#doctadviseno").val());
	});
	$("#sampleTraceLink").click(function(){
		window.open("../doctor/sampleTrace");
	});


	$("#fullScreen").click(function(){
		if ($("#isfulltag").val() == 0) {
			$("#head").css("display","none");
			$("#content").css("margin-top","3px");
			$("#fullScreen .glyphicon").removeClass("glyphicon-fullscreen");
			$("#fullScreen .glyphicon").addClass("glyphicon-resize-small");
			$("#isfulltag").val(1);
		} else {
			$("#head").css("display","block");
			$("#fullScreen .glyphicon").removeClass("glyphicon-resize-small");
			$("#fullScreen .glyphicon").addClass("glyphicon-fullscreen");
			$("#isfulltag").val(0);
		}
	});

	$("#newSendList").click(function(){
		var newlist = new Date().Format("yyyy-MM-dd hh:mm:ss");
		$("#sendList").append("<option value='"+newlist+"'>"+newlist+"</option>");
		$("#sendList").val(newlist);
		getOutList();
	});
	$("#sendList").change(function(){
		getOutList();
	});

	$("#printSendList").click(function(){
		var text = $("#sendList").val();
		var linksrc = "../manage/sampleHandover/sendListPrint?sendListNo="+text;
		if($("#allCheck").prop("checked")){
			linksrc += "&allCheck=1&from="+$("#from").val()+"&to="+$("#to").val()+"&receiveLab="+$("#receiveLab").val();
		}
		$('#sendListPrintFrame').empty();
		$("#sendListPrintFrame").append("<iframe id='iframe_sendList' name='iframe_sendList' frameborder=0 style='background-color:transparent' width='99%' height='99%' src='"+linksrc+"' />");
		openSendListPrintDialog();
		$("#iframe_print").height(450);
	});

	$("#receiveLab").change(function(){
		clearJqgridData();
		var sendList = $("#sendList");
		sendList.empty();;
		var sectionId = $("#receiveLab").val();
		getWaitOutList();
		$.get("../manage/sampleHandover/getSendListNo",{from:$("#from").val(),to:$("#to").val(),section:sectionId},function(data){
			if(data!=null){
				for(var i=0;i<data.length;i++){
					var scode = data[i];
					if(scode != null)
						sendList.append("<option value='"+scode+"'>"+scode+"</option>");
				}

			}
			getOutList();
		});
	});

	$("#allCheck").click(function(){
		if($("#allCheck").prop("checked")){
			getOutList();
		}
	});

});
/**
 * 清空表单数据
 */
function clearJqgridData(){
	jQuery("#outList").jqGrid("clearGridData");
	jQuery("#waitOutList").jqGrid("clearGridData");
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
			if(obj.id=="doctout"){
				if($("#queryCheck").val()!=1 && ($("#sendList").val()==null || $("#sendList").val()=="") ){
					alert("送出清单为空，请新建清单！");
					return;
				}
				$("#doctadviseno").val(obj.value.substr(0,8));
				outSample(obj.value);

			}
			else if(obj.id=="doctin"){
				$("#doctadviseno").val(obj.value.substr(0,8));
				receiveSample(obj.value);

			}
			break;
	}
}
/**
 * 送出表单
 */
var outlistfirst = true;
function getOutList(){
	var width = $("#samplereceive").width()*0.9;
	if(outlistfirst){
		outlistfirst=false;
		jQuery("#outList").jqGrid({
//		url:"../manage/sampleHandover/outList?operator=" + $("#outer").val() + "(" + $("#pointout").val() + ")"
//			+ "&sendList=" + $("#sendList").val(),
			url:"../manage/sampleHandover/outList?doct="+$("#doctadviseno").val()+"&"+$('#outForm').serialize(),
			datatype:"json",
			colNames:['医嘱号','病人姓名','就诊卡号','检验项目','送检科室','采血地点','采集时间','采集者','送出时间','送出者','科室接收时间','科室接收者'],
			colModel:[
				{name:'doctadviseno',index:'doctadviseno',width:width*0.1,sortable:false},
				{name:'patientname',index:'patientname',width:width*0.1,sortable:false},
				{name:'patientid',index:'patientid',width:width*0.1,sortable:false},
				{name:'inspectionName',index:'inspectionName',width:width*0.1,sortable:false},
				{name:'labdepartment',index:'labdepartment',width:width*0.1,sortable:false},
				{name:'computername',index:'computername',width:width*0.1,sortable:false},
				{name:'executetime',index:'executetime',width:width*0.1,sortable:false},
				{name:'executor',index:'executor',width:width*0.1*0.5,sortable:false},
				{name:'sendtime',index:'sendtime',width:width*0.1,sortable:false},
				{name:'sender',index:'sender',width:width*0.1*0.5,sortable:false},
				{name:'ksreceivetime',index:'ksreceivetime',width:width*0.1,sortable:false},
				{name:'ksreceiver',reveiver:'ksreceiver',width:width*0.1*0.5,sortable:false}
			],
			rowNum:20,
			height:250,
			rownumbers:true,
			caption : "标本送出列表",
			viewrecords:true,
			jsonReader:{repeatitems:false},
			mtype:"GET",
			pager:'#pager',
			loadComplete: function () {
				var records = jQuery("#outList").jqGrid("getGridParam","records");
				$("#outRecords").html(records);
			}

		}).trigger("reloadGrid");
		jQuery("#outList").jqGrid('navGrid','#pager',{edit:false,add:false,del:false,search:false,refresh:false});
	}
	else{
		jQuery("#outList").jqGrid("clearGridData");
		jQuery("#outList").jqGrid("setGridParam",{
			url:"../manage/sampleHandover/outList?doct="+$("#doctadviseno").val()+"&"+$('#outForm').serialize()}).trigger("reloadGrid");

	}

}

//标本未送出列表
var waitOutlistfirst = true;
function getWaitOutList(){
	var width = $("#samplereceive").width()*0.9;
	if(waitOutlistfirst){
		waitOutlistfirst=false;
		jQuery("#waitOutList").jqGrid({
			url:"../manage/sampleHandover/waitOutList?"+$('#outForm').serialize(),
			datatype:"json",
			colNames:['医嘱号','病人姓名','就诊卡号','检验项目','送检科室','采集地点','采集时间','采集者','送出时间','送出者'],
			colModel:[
				{name:'doctadviseno',index:'doctadviseno',width:width*0.1,sortable:false},
				{name:'patientname',index:'patientname',width:width*0.1,sortable:false},
				{name:'patientid',index:'patientid',width:width*0.1,sortable:false},
				{name:'inspectionName',index:'inspectionName',width:width*0.1,sortable:false},
				{name:'labdepartment',index:'labdepartment',width:width*0.1,sortable:false},
				{name:'computername',index:'computername',width:width*0.1,sortable:false},
				{name:'executetime',index:'executetime',width:width*0.1,sortable:false},
				{name:'executor',index:'executor',width:width*0.1*0.5,sortable:false},
				{name:'sendtime',index:'sendtime',width:width*0.1,sortable:false},
				{name:'sender',index:'sender',width:width*0.1*0.5,sortable:false}
			],
			rowNum:20,
			height:250,
			rownumbers:true,
			caption : "标本送出列表",
			viewrecords:true,
			jsonReader:{repeatitems:false},
			mtype:"GET",
			pager:'#pager',
			loadComplete: function () {
				var records = jQuery("#waitOutList").jqGrid("getGridParam","records");
				$("#waitOutRecords").html(records);
			}

		}).trigger("reloadGrid");
		jQuery("#waitOutList").jqGrid('navGrid','#waitOutpager',{edit:false,add:false,del:false,search:false,refresh:false});
	}
	else{
		jQuery("#waitOutList").jqGrid("clearGridData");
		jQuery("#waitOutList").jqGrid("setGridParam",{
			url:"../manage/sampleHandover/waitOutList?"+$('#outForm').serialize()}).trigger("reloadGrid");

	}

}


var receivelistfirst = true;
function getReciveList(){

	var width = $("#samplereceive").width()*0.9;
	if(receivelistfirst){
		receivelistfirst=false;
		$("#receiveList").jqGrid({
			url:"../manage/sampleHandover/receiveList?type=1&operator=" + $("#outer").val() + "(" + $("#point").val() + ")",
			datatype:"json",
//		width:width,
			colNames:['医嘱号','病人姓名','就诊卡号','检验项目','送检科室','申请时间','申请者','采集时间','采集者','送出时间','送出者','科室接收时间','科室接收者'],
			colModel:[
				{name:'doctadviseno',index:'doctadviseno',width:width*0.1,sortable:false},
				{name:'patientname',index:'patientname',width:width*0.1,sortable:false},
				{name:'patientid',index:'patientid',width:width*0.1,sortable:false},
				{name:'inspectionName',index:'inspectionName',width:width*0.1,sortable:false},
				{name:'labdepartment',index:'labdepartment',width:width*0.1,sortable:false},
				{name:'requesttime',index:'requesttime',width:width*0.1,sortable:false},
				{name:'requester',index:'requester',width:width*0.1*0.5,sortable:false},
				{name:'executetime',index:'executetime',width:width*0.1,sortable:false},
				{name:'executor',index:'executor',width:width*0.1*0.5,sortable:false},
				{name:'sendtime',index:'sendtime',width:width*0.1,sortable:false},
				{name:'sender',index:'sender',width:width*0.1*0.5,sortable:false},
				{name:'ksreceivetime',index:'ksreceivetime',width:width*0.1,sortable:false},
				{name:'ksreceiver',reveiver:'ksreceiver',width:width*0.1*0.5,sortable:false}
			],
			rowNum:20,
			height:'100%',
			rownumbers:true,
			caption : "标本送出列表",
			viewrecords:true,
			jsonReader:{repeatitems:false},
			mtype:"GET",
			pager:'#rpager',

		}).trigger("reloadGrid");
		jQuery("#receiveList").jqGrid('navGrid','#rpager',{edit:false,add:false,del:false,search:false,refresh:false});
	}
	else{
		var location = $("#point").val();
		jQuery("#receiveList").jqGrid("clearGridData");
		jQuery("#receiveList").setGridParam({
			url:"../manage/sampleHandover/receiveList?type=1&operator=" + $("#operator").val() + "(" + location + ")"}).trigger("reloadGrid");
	}
}

function outSample(id){
	if($("#outer").val() == "") {
		alert("接收者不能为空，请输入接受者姓名！")
	} else {
		var doct = $("#doctout").val().substr(0,8);
		$.ajax({
			type:'GET',
			url: "../manage/sampleHandover/ajax/outsample?queryCheck="+$("#queryCheck").prop("checked")+"&doct=" + doct + "&operator=" + $("#outer").val() + "(" + $("#pointout").val() + ")"
			+ "&sendList=" + $("#sendList").val(),
			success: function(data) {
				data = jQuery.parseJSON(data);
				$("#alert").css("display","block");
				if(data.type == 1) {
					$("#alert").removeClass().addClass("alert alert-warning alert-dismissable");
					$("#alert").html("医嘱号为" + doct + "的标本不存在，请确定医嘱号是否正确！");
					$("#text").css("display","none");
				} else {
					if(data.type == 2) {
						$("#alert").removeClass().addClass("alert alert-success alert-dismissable");
						$("#alert").html("医嘱号为" + doct + "的标本送出成功！");
						jQuery("#outList").jqGrid({url:"../manage/sampleHandover/outList?type=1&operator="+$("#outer").val()}).trigger("reloadGrid");
					} else if(data.type == 3) {
						$("#alert").removeClass().addClass("alert alert-info alert-dismissable");
						$("#alert").html("医嘱号为" + doct + "的标本已送出，无需重复送出！");
					} else {
						$("#alert").removeClass().addClass("alert alert-warning alert-dismissable");
						$("#alert").html("医嘱号为" + doct + "的标本非本部门送出，请核对！");
					}
					$("#examinaim").html(data.exam);
					$("#name").html(data.name);
					$("#sex").html(data.sex == 2 ? "女" : "男");
					$("#age").html(data.age);
					$("#lab").html(data.lab);
					switch (data.stayhospitalmode) {
						case 1:
							$("#stayhospitalmode").html("门诊");
							break;
						case 2:
							$("#stayhospitalmode").html("住院");
							break;
						case 3:
							$("#stayhospitalmode").html("体检");
							break;
						default:
							$("#stayhospitalmode").html("其他");
							break;
					}
					if(data.stayhospitalmode == 2) {
						$("#outpatient").css("display","none");
						$("#wardtext").css("display","block");
						$("#ward").html(data.section);
						$("#bed").html(data.bed);
						$("#type").html(data.wardType);
						$("#phone").html(data.wardPhone);
					} else {
						$("#outpatient").css("display","block");
						$("#wardtext").css("display","none");
						$("#section").html(data.section);
					}
					$("#text").css("display","block");
				}
				$("#doctout").val("");
				getOutList();
				getWaitOutList();
			}
		});
	}
}

function receiveSample(id) {
	if($("#operator").val() == "") {
		alert("接收者不能为空，请输入接受者姓名！")
	} else {
		var doct = $("#doctin").val().substr(0,8);
		$.ajax({
			type:'GET',
			url: "../manage/sampleHandover/ajax/sample?doct=" + doct + "&operator=" + $("#operator").val() + "(" + $("#point").val() + ")",
			success: function(data) {
				data = jQuery.parseJSON(data);
				$("#inalert").css("display","block");
				if(data.type == 1) {
					$("#inalert").removeClass().addClass("alert alert-warning alert-dismissable");
					$("#inalert").html("医嘱号为" + doct + "的标本不存在，请确定医嘱号是否正确！");
					$("#intext").css("display","none");
				} else {
					if(data.type == 2) {
						$("#inalert").removeClass().addClass("alert alert-success alert-dismissable");
						$("#inalert").html("医嘱号为" + doct + "的标本接收成功！");
						$("#doctadviseno").val(doct);

					} else if(data.type == 3) {
						$("#inalert").removeClass().addClass("alert alert-info alert-dismissable");
						$("#inalert").html("医嘱号为" + doct + "的标本已接收，无需重复接收！");
					} else {
						$("#inalert").removeClass().addClass("alert alert-warning alert-dismissable");
						$("#inalert").html("医嘱号为" + doct + "的标本非本部门接收，请核对！");
					}
					$("#inexaminaim").html(data.exam);
					$("#inname").html(data.name);
					$("#insex").html(data.sex == 2 ? "女" : "男");
					$("#inage").html(data.age);
					$("#inlab").html(data.lab);
					switch (data.stayhospitalmode) {
						case 1:
							$("#instayhospitalmode").html("门诊");
							break;
						case 2:
							$("#instayhospitalmode").html("住院");
							break;
						case 3:
							$("#instayhospitalmode").html("体检");
							break;
						default:
							$("#instayhospitalmode").html("其他");
							break;
					}

					if(data.stayhospitalmode == 2) {
						$("#inoutpatient").css("display","none");
						$("#inwardtext").css("display","block");
						$("#inward").html(data.section);
						$("#inbed").html(data.bed);
						$("#intype").html(data.wardType);
						$("#inphone").html(data.wardPhone);
					} else {
						$("#inoutpatient").css("display","block");
						$("#inwardtext").css("display","none");
						$("#insection").html(data.section);
					}
					$("#intext").css("display","block");
				}
				$("#doctin").val("");
				getReciveList();
			}
		});
	}
}
function openSendListPrintDialog() {
	layer.open({
		type: 1,
		shade: 0.4,
		skin: 'layui-layer-lan',
		area:['750px','600px'],
		title: '打印预览',
		content: $("#sendListPrint"),
		cancel: function(index){
			layer.close(index);
		}
	})
}

Date.prototype.Format = function(fmt)
{ //author: meizz
	var o = {
		"M+" : this.getMonth()+1,                 //月份
		"d+" : this.getDate(),                    //日
		"h+" : this.getHours(),                   //小时
		"m+" : this.getMinutes(),                 //分
		"s+" : this.getSeconds(),                 //秒
		"q+" : Math.floor((this.getMonth()+3)/3), //季度
		"S"  : this.getMilliseconds()             //毫秒
	};
	if(/(y+)/.test(fmt))
		fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
	for(var k in o)
		if(new RegExp("("+ k +")").test(fmt))
			fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
	return fmt;
};