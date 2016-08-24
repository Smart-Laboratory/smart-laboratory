function getData(item,event){
	var e = e||event;
	var key = event.keyCode;
	if(navigator.appName=="Netscape"){
		key=e.which;
	}else{
		key=event.keyCode;
	}
	if(key == 13){
		$("#warnLabel").html("");
		var id = $(item).attr("id");
		if($("#bloodCheck").prop("checked")==true){
			//去除条码后的校验位
			//根据医嘱号，取patientid
			//是否需要留样
			
			//清空异常信息对话框
			$("#laborder").val("");
			$("#unpatientid").val("");
			$("#part").val("");
			$("#mode").val("");
			$("#reaction").val("");
			$("#time").val("");
			$("#note").val("");
			
			//获取病人信息
			var jzkh=$("#jzkh").val();
			if(jzkh==null || jzkh.length=="")
				return;
			$("laborder").val("");
			$.get("../manage/execute/getPatient",{patientId:jzkh,from:$("#from").val(),to:$("#to").val()},function(data){
				//先清空数据
				$("#blh").html("");$("#patientId").html("");$("#pName").html("");$("#pSex").html("");$("#pAge").html("");
				//插入数据
				var patient = data.patient;
				var invalidsample = data.invalidsample;
				$("#blh").html(patient.blh);$("#patientId").html(patient.patientId);$("#pName").html(patient.patientName);$("#pSex").html(patient.sexValue);$("#pAge").html(patient.age);
				if(invalidsample !=null ){
					$("#warnLabel").html("不合格标本登记：<b>"+invalidsample+"</b>  ");
				}
				//历史抽血记录
				if(data.size==0){
					$("#cxcx").html(0);
				}else{
					$("#cxcx").html(data.size);
					var labOrder = data.labOrder;
					$("#cxry").html(labOrder.executor);
					$("#cxsj").html(new Date(labOrder.executetime).Format('yyyy-MM-dd hh:mm:ss'));
					$("#cxxm").html(labOrder.examitem);
				}
				//历史检验项目
				var samples = data.samples;
				if(samples != null){
					$("#samplehis").html("");
					for(var i=0; i<samples.length ;i++){
						var sample = samples[i];
						$("#samplehis").append("<div class='col-sm-2 '><span class='col-sm-6'>结果状态:</span><b>"+sample.auditStatusValue+sample.auditMarkValue+"</b></div>");
						$("#samplehis").append("<div class='col-sm-3 '><span class='col-sm-6'>样本号:</span><b>"+sample.sampleNo+"</b></div>");
						$("#samplehis").append("<div class='col-sm-5 '><span class='col-sm-4'>检验项目:</span><b>"+sample.inspectionName+"</b></div>");
						$("#samplehis").append("<div class='col-sm-2 '><span class='col-sm-6'>检验科室:</span><b >"+sample.sectionId+"</b></div>");
						$("#samplehis").append("</div>");
					}
				}
                $("#examtodo").html("");
                if(data.examtodo!=null)
                    $("#examtodo").html("待做项："+data.examtodo);
			});
			
			$.get("../manage/execute/getTests",{patientId:jzkh,requestmode:$("input[name='select_type']:checked").val(),from:$("#from").val(),to:$("#to").val()},function(data){
				if(data!=null){
					$("#tests").html(data.html);
					if(data.examtodo!=null)
						$("#examtodo").html("待做项："+data.examtodo);
				}
			});
		}
		
		
		
	}
}

$(function(){
	
	$(".footer").css('display','none');
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
	var d = new Date();
	d.setMonth(d.getMonth()-1);
	$( "#from" ).val(d.Format("yyyy-MM-dd"));
	$( "#to" ).val(new Date().Format("yyyy-MM-dd"));
	
	$("#bloodCheck").prop("checked",'true');
	
	$("#conform").click(function(){
		
		var selval="";
		$("#tests input:checkbox").each(function(){
			if($(this).prop("checked")==true)
				selval = selval + $(this).val()+";";
		});
		if(selval==null || selval == ''){
			alert("请选择检验项目");
			return;
		}
		var selfexecute = 0;
		if($("#selfexecute").prop("checked")==true){
			selfexecute =1;
		}
		alert(selval);
//		selval:selval,patientId:jzkh,requestmode:0,from:$("#from").val(),to:$("#to").val()
		$.get("../manage/execute/ajax/submit",{selval:selval,selfexecute:selfexecute},function(data){
			data = jQuery.parseJSON(data);
			var tests = data.laborders;
			
			
			var testStr="";
			for(var i =0; i<tests.length;i++){
				testStr += tests[i] + ",";
			}
			$("#laborder").val(tests);
			if(data.error == null || data.error == undefined){
				
				layer.open({
		            type: 2,
		            area: ['550px','500px'],
		            fix: false, //不固定
		            maxmin: true,
		            shade:0.5,
		            title: "条码打印",
		            content:  "../manage/printBarcode?tests="+testStr
		        })
			}
		});
		
	});
	
	
	$("#unusualRegister").click(function(){
		var jzkh = $("#jzkh").val();
		$("#unpatientid").val(jzkh);
		var laborders = $("#laborder").val();
		if(laborders!=null && laborders!=""){
			$.get("../manage/getUnusualExecute",{laborder:laborders},function(data){
				if(data!=null && data.patientId!=null){
					$("#unpatientid").val(data.patientId);
					$("#part").val(data.part);
					$("#mode").val(data.executeMode);
					$("#reaction").val(data.reaction);
					$("#time").val(data.time);
					$("#note").val(data.note);
				}
			});
		}
		
		layer.open({
			  type: 1,
			  shade: 0.4,
			  skin: 'layui-layer-lan',
			  area:['550px','440px'],
			  title: '异常登记',
			  content: $("#executeUnusualDialog"),
			  cancel: function(index){
			    layer.close(index);
			  }
			});
	});
	
	$("#sampleQuery").click(function(){
		var jzkh = $("#jzkh").val();
		if(jzkh!=null && jzkh!=''){
			window.open("../manage/patientList?patientId="+jzkh);
		}
			
	});
});

function unusual(){
	var part,mode,reaction,time,note;
	part = $("#part").val();
	mode = $("#mode").val();
	reaction = $("#reaction").val();
	time = $("#time").val();
	note = $("#note").val();
	
	var jzkh = $("#jzkh").val();
	
	var laborders = $("#laborder").val();
	if(laborders!=null && jzkh!=null){
		$.get("../manage/ajax/unusual",{laborder:laborders,jzkh:jzkh,part:part,mode:mode,reaction:reaction,time:time,note:note},function(data){
			data = jQuery.parseJSON(data);
			alert(data.data);
			$("#executeUnusualDialog").dialog("close");
		});
	}
}




//------------------------------------------
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