$(function() {
	
	$("#doctout").focus();
	getOutList();
	
	
});

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
			alert(obj.value);
			if(obj.id=="doctout")
				outSample(obj.value);
			else if(obj.id=="doctin")
				receiveSample(obj.value);
			break;
	}
}
var outlistfirst = true;
function getOutList(){
	var width = $("#outListDiv").width();
	jQuery("#outList").jqGrid({
		url:"../manage/sampleHandover/outList?type=1&operator=" + $("#outer").val() + "(" + $("#pointout").val() + ")",
		datatype:"json",
		width:width,
		colNames:['医嘱号','病人姓名','就诊卡号','检验项目','送检科室','申请时间','申请者','采集时间','采集者','送出时间','送出者'],
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
		          {name:'sender',index:'sender',width:width*0.1,sortable:false}
		          ],
		rowNum:20,
		height:'100%',
		rownumbers:true,
		caption : "标本送出列表",
		viewrecords:true,
		jsonReader:{repeatitems:false},
		mtype:"GET",
		pager:'#pager',
		
	}).trigger("reloadGrid");
	jQuery("#outList").jqGrid('navGrid','#pager',{edit:false,add:false,del:false,search:false,refresh:false});
}

function outSample(id){
	if($("#outer").val() == "") {
		alert("接收者不能为空，请输入接受者姓名！")
	} else {
		var doct = $("#doctout").val().substr(0,8);
		$.ajax({
	  		type:'GET',
			url: "../manage/sampleHandover/ajax/outsample?doct=" + doct + "&operator=" + $("#outer").val() + "(" + $("#pointout").val() + ")",
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
	  			$("#alert").css("display","block");
	  			if(data.type == 1) {
	  				$("#alert").removeClass().addClass("alert alert-warning alert-dismissable");
	  				$("#alert").html("医嘱号为" + doct + "的标本不存在，请确定医嘱号是否正确！");
	  				$("#text").css("display","none");
	  			} else {
	  				if(data.type == 2) {
	  					$("#alert").removeClass().addClass("alert alert-success alert-dismissable");
		  				$("#alert").html("医嘱号为" + doct + "的标本接收成功！");
	  				} else if(data.type == 3) {
	  					$("#alert").removeClass().addClass("alert alert-info alert-dismissable");
		  				$("#alert").html("医嘱号为" + doct + "的标本已接收，无需重复接收！");
	  				} else {
	  					$("#alert").removeClass().addClass("alert alert-warning alert-dismissable");
		  				$("#alert").html("医嘱号为" + doct + "的标本非本部门接收，请核对！");
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
	  			$("#doctin").val("");
	  	  	}
	  	});
	}
}