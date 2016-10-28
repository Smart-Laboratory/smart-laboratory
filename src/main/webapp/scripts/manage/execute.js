var LODOP; //声明为全局变量

function Preview() {//打印预览
	LODOP = getLodop();
	CreateDataBill(data)
	LODOP.PREVIEW();
};
function Setup() {//打印维护
	LODOP = getLodop();
	LODOP.PRINT_SETUP();
};
function printSetting() {
	//readPrintFile();
	if (LODOP.CVERSION) {
		LODOP.On_Return = function (TaskID, Value) {
			if (Value >= 0)
				setCookie("lis_print", Value);
			else
				alert("选择失败！");
		};
		LODOP.SELECT_PRINTER();
		return;
	}
};
function setCookie(name, value) {
	var Days = 9999;
	var exp = new Date();
	exp.setTime(exp.getTime() + Days * 24 * 60 * 60 * 1000);
	document.cookie = name + "=" + escape(value) + ";expires=" + exp.toGMTString();
};

function CreateDataBill(data) {
	if(data && data!=null){
	    var patientInfo = data.patientName + " "+ data.sex + " "+ data.age+data.ageUnit
		if(data.requestMode != 0) {
			patientInfo += " 急";
		}
        var exam = data.testName + "  "+ data.sampleType;
		var patientInfo1 = data.patientCode +"  "+data.hosSectionName;
        var labInfo = data.labDepartment + " " + data.sampleType + " " + data.container+data.volume;
		LODOP = getLodop();
		LODOP.PRINT_INIT("");
		LODOP.SET_PRINT_PAGESIZE(0,980,1100,"A4");
		LODOP.ADD_PRINT_IMAGE(10,10,80,80,"<img src='../images/shulan.png' style='width:80px;'/>");
        LODOP.ADD_PRINT_TEXT(10,100,230,35,"树兰（杭州）医院");
        LODOP.SET_PRINT_STYLEA(0,"FontSize",20);
        LODOP.ADD_PRINT_TEXT(45,100,230,35,"浙江大学国际医院");
        LODOP.SET_PRINT_STYLEA(0,"FontSize",20);
        LODOP.ADD_PRINT_BARCODEA("patientCode","21.98mm","27.01mm","46.57mm",40,"128B",data.patientCode);
        LODOP.SET_PRINT_STYLEA(0,"Horient",2);
        LODOP.ADD_PRINT_TEXTA("nameText","33.00mm","12.46mm",45,20,"姓名：");
        LODOP.ADD_PRINT_TEXTA("name","33.00mm","23.31mm",90,20,data.patientName);
        LODOP.SET_PRINT_STYLEA(0,"Bold",1);
        LODOP.ADD_PRINT_TEXTA("sexText","33.00mm","46.86mm",45,20,"性别：");
        LODOP.ADD_PRINT_TEXTA("sex","33.00mm","58.5mm",30,20,data.sex);
        LODOP.SET_PRINT_STYLEA(0,"Bold",1);
        LODOP.ADD_PRINT_TEXTA("ageText","33.00mm","65.91mm",45,20,"年龄：");
        LODOP.ADD_PRINT_TEXTA("age","33.00mm","77.55mm",40,20,data.age + data.ageUnit);
        LODOP.SET_PRINT_STYLEA(0,"Bold",1);
        LODOP.ADD_PRINT_TEXTA("examText","38.00mm","5.85mm",70,20,"检验目的：");
        LODOP.ADD_PRINT_TEXTA("exam","38.00mm","23.31mm",300,20,data.testName);
        LODOP.SET_PRINT_STYLEA(0,"Bold",1);
        LODOP.ADD_PRINT_TEXTA("requestTimeText","43.00mm","5.85mm",70,20,"开单时间：");
        LODOP.ADD_PRINT_TEXTA("requestTime","43.00mm","23.31mm",300,20,data.requestTime);
        LODOP.ADD_PRINT_TEXTA("requesterText","48.00mm","5.85mm",70,20,"开单信息：");
        LODOP.ADD_PRINT_TEXTA("requester","48.00mm","23.31mm",300,20,data.hosSectionName + " " + data.requester);
        LODOP.ADD_PRINT_TEXTA("executeTimeText","53.00mm","5.85mm",70,20,"采集时间：");
        LODOP.ADD_PRINT_TEXTA("executeTime","53.00mm","23.31mm",300,20,data.executeTime);
        LODOP.ADD_PRINT_TEXTA("reportTimeText","58.00mm","5.58mm",70,20,"报告时间：");
        LODOP.ADD_PRINT_TEXTA("reportTime","58.00mm","23.31mm",300,20,data.reportTime);
        LODOP.ADD_PRINT_TEXTA("reportPlaceText","63.00mm","5.58mm",70,20,"取单地点：");
        LODOP.ADD_PRINT_TEXTA("reportPlace","63.00mm","23.31mm",300,20,data.reportPlace);
        LODOP.ADD_PRINT_TEXTA("tip1","68.00mm","5.58mm",360,20,"*法定节假日(如:春节等)仪器故障报告时间顺延*");
        LODOP.ADD_PRINT_TEXTA("tip2","73.00mm","5.58mm",360,20,"*抽血时请携带就诊卡，凭此单或就诊卡取检验报告*");

		LODOP.ADD_PRINT_TEXTA("patientinfo","78mm","2.85mm",180,20,patientInfo);
        LODOP.ADD_PRINT_TEXTA("testinfo","82mm","2.95mm",180,20,data.testName);
		if(exam.length > 36) {
			LODOP.SET_PRINT_STYLEA(0,"FontSize",7);
		}
		LODOP.ADD_PRINT_BARCODEA("barcode","90mm","4.85mm","36mm",35,"128Auto",data.barcode);
		LODOP.SET_PRINT_STYLEA(0,"ShowBarText",0);
		if(data.sampleNo == '0') {
			LODOP.ADD_PRINT_TEXTA("code","100mm","5.2mm",150,20,"*"+data.barcode+"*");
			LODOP.SET_PRINT_STYLEA(0,"Bold",1);
		} else {
			LODOP.ADD_PRINT_TEXTA("code","100mm","5.2mm",150,20,"*"+data.sampleNo+"*");
			LODOP.SET_PRINT_STYLEA(0,"Bold",1);
		}
		//LODOP.ADD_PRINT_TEXTA("patientinfo1","98mm","2.95mm",180,20,patientInfo1);
		LODOP.ADD_PRINT_TEXTA("labInfo","104mm","2.95mm",180,20, labInfo);
		LODOP.ADD_PRINT_TEXTA("datetime","107mm","2.95mm",180,20,"采集时间 "+data.executeTime);

        LODOP.ADD_PRINT_TEXTA("patientinfo","78mm","51.25mm",180,20,patientInfo);
        LODOP.ADD_PRINT_TEXTA("testinfo","82mm","51.25mm",180,20,data.testName);
		if(exam.length > 36) {
			LODOP.SET_PRINT_STYLEA(0,"FontSize",7);
		}
        LODOP.ADD_PRINT_BARCODEA("barcode","90mm","53.25mm","36mm",35,"128Auto",data.barcode);
        LODOP.SET_PRINT_STYLEA(0,"ShowBarText",0);
		if(data.sampleNo == '0') {
			LODOP.ADD_PRINT_TEXTA("code","100mm","53.95mm",150,20,"*"+data.barcode+"*");
			LODOP.SET_PRINT_STYLEA(0,"Bold",1);
		} else {
			LODOP.ADD_PRINT_TEXTA("code","100mm","53.95mm",150,20,"*"+data.sampleNo+"*");
			LODOP.SET_PRINT_STYLEA(0,"Bold",1);
		}
        //LODOP.ADD_PRINT_TEXTA("patientinfo1","98mm","51.25mm",180,20,patientInfo1);
		LODOP.ADD_PRINT_TEXTA("labInfo","104mm","51.25mm",180,20, labInfo);
		LODOP.ADD_PRINT_TEXTA("datetime","107mm","51.25mm",180,20,"采集时间 "+data.executeTime);

	}
}
function startPrint(data) {
	CreateDataBill(data);
	//开始打印
	LODOP.PRINT();
}

function printSet() {
	var data ={
		"age": "55",
		"barcode": "A12000000098",
		"labDepartment": "检验科",
		"patientCode": "00000496",
		"patientName": "电子病历测试",
		"requestMode": 1,
		"sampleNo": "20160907XCG001",
        "container":"黄色管",
		"volume": "2mL",
		"sampleType": "血清",
		"sex": "男",
		"testName": "粪便常规+OB+寄生虫、虫卵镜检+虫卵计数",
		"hosSectionName": "肝胆胰外科",
        "ageUnit": "岁",
        "requestTime":"2016-09-07 08:50:28",
        "executeTime":"2016-09-07 09:17:21",
        "reportTime":"2016-09-07 13:17:00",
        "requester":"开单医生",
        "reportPlace":"在服务台或自助机上取单"
	}
	CreateDataBill(data);
	LODOP.PRINT_DESIGN();
}

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
			if(jzkh==null || jzkh.length=="") {
				layer.msg("请输入就诊卡号！", {icon: 0, time: 1000});
				return;
			}
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

			reloadTests();
		}

	}
}

function reloadTests() {
	var jzkh=$("#jzkh").val();
	if(jzkh==null || jzkh.length=="") {
		layer.msg("请输入就诊卡号！", {icon: 0, time: 1000});
		return;
	}
	$.get(baseUrl + "/manage/execute/ajax/getTests",{patientId:jzkh,from:$("#from").val(),to:$("#to").val(), isEmergency: $("#requestModeSelect").val()},function(data){
		$("#tests").html("");
		$("#tests1").html("");
		if(data!=null){
			var executeArray = data.execute;
			var unExecuteArray = data.unexecute;
			if(unExecuteArray.length == 0) {
			    if($("input[name='select_type']:checked").val() == 999) {
                    //layer.msg("当前病人没有已采集的检验项目！", {icon: 0, time: 1000});
                } else{
                    //layer.msg("当前病人没有需要采样的检验项目！", {icon: 0, time: 1000});
                }
                $("#tests").html("");
				return;
			}
			$("#checkAll").prop('checked', true);
			createHtml(unExecuteArray, 'tests');
			createHtml(executeArray, 'tests1');
			if(data.examtodo!=null)
				$("#examtodo").html("待做项："+data.examtodo);
		}
	});
}

$(function(){
	$('a[data-toggle="tab"]').on('click', function (e) {
		// 获取已激活的标签页的名称
		var text = e.currentTarget.hash;
		// if(text=='#tests'){
		// 	//未检验
		// 	reloadTests(0)
		// }
		// if(text=='#tests1'){
		// 	//已检验
		// 	reloadTests(999)
		// }
	});

	$(".footer").css('display','none');
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
	var d = new Date();
	d.setMonth(d.getMonth()-1);
	$( "#from" ).val(d.Format("yyyy-MM-dd"));
	$( "#to" ).val(new Date().Format("yyyy-MM-dd"));
	
	$("#bloodCheck").prop("checked",'true');

	$("#back").click(function(){
		var selval="";
		$("#tests1 input:checkbox").each(function(){
			if($(this).prop("checked")==true)
				selval = selval + $(this).val()+";";
		});
		if(selval==null || selval == ''){
			layer.msg("请选择需要重新退回的项目", {icon: 2, time: 1000});
			return;
		}
		$.get(baseUrl + "/manage/execute/ajax/back",{selval:selval},function(data){
			if(data.success == 1) {
				layer.msg(data.message, {icon: 1, time: 1000});
			} else {
				layer.msg(data.message, {icon: 2, time: 1000});
			}
			reloadTests();
		});
	});
	
	$("#conform").click(function(){
		var selval="";
        if($("#tests").css('display') == 'none') {
			$("#tests1 input:checkbox").each(function(){
				if($(this).prop("checked")==true)
					selval = selval + $(this).val()+";";
			});
			if(selval==null || selval == ''){
				layer.msg("请选择需要重新打印条码的项目", {icon: 2, time: 1000});
				return;
			}
			$.get(baseUrl + "/manage/execute/ajax/reprint",{selval:selval},function(data){
				for(i=0;i<data.labOrders.length;i++){
					startPrint(data.labOrders[i]);
				}
				reloadTests();
			});
        } else {
            $("#tests input:checkbox").each(function(){
                if($(this).prop("checked")==true)
                    selval = selval + $(this).val()+";";
            });
            if(selval==null || selval == ''){
                layer.msg("请选择需要采样的项目", {icon: 2, time: 1000});
                return;
            }
            var selfexecute = 0;
            if($("#selfexecute").prop("checked")==true){
                selfexecute =1;
            }
            $.get(baseUrl + "/manage/execute/ajax/submit",{selval:selval,selfexecute:selfexecute},function(data){
                for(i=0;i<data.labOrders.length;i++){
                    startPrint(data.labOrders[i]);
                }
                reloadTests();
            });
        }

		
	});
	
	
	$("#unusualRegister").click(function(){
		var jzkh = $("#jzkh").val();
		$("#unpatientid").val(jzkh);
		var laborders = $("#laborder").val();
		if(laborders!=null && laborders!=""){
			$.get(baseUrl + "/manage/getUnusualExecute",{laborder:laborders},function(data){
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

	$('#checkAll').click(function(){
		if($(this).prop("checked")) {
			if($('#tests').css("display") == 'none') {
				$('#tests1 input[type=checkbox]').prop('checked',true);
			} else {
				$('#tests input[type=checkbox]').prop('checked',true);
			}
		} else {
			if($('#tests').css("display") == 'none') {
				$('#tests1 input[type=checkbox]').prop('checked',false);
			} else {
				$('#tests input[type=checkbox]').prop('checked',false);
			}
		}
	});

	$("#requestModeSelect").change(function() {
        reloadTests();
	});

    $("#myTab a").click(function () {
		if($(this).prop("href").indexOf("tests1") > 0) {
			$("#back").css("display","inline");
		} else {
			$("#back").css("display","none");
		}
        reloadTests();
    });
	var clientHeight= $(window).innerHeight();
	var height =clientHeight-$('#menuheader').height()- $('#patientInfo').height()-160;
	$('.row').height(clientHeight-$('#menuheader').height()-20);
	$('#tests').height(height);
	$('#tests1').height(height);
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
			$("#executeUnusualDialog").dialog("close");
		});
	}
}

function createHtml(jsonArray, id) {
	var html = "";
	for(var i = 0; i<jsonArray.length; i++) {
		if(i%2 == 0) {
			html+="<div id='date"+i+"' class='alert sampleInfo' style='background-color:#f5f5f5'>";
		}else{
			html+="<div id='date"+i+"' class='alert sampleInfo' style='background-color:#fff'>";
		}
		if(jsonArray[i].zxbz == 0) {
			html+="<div class='col-sm-2' style=''>"+
				"<div class='col-sm-6'><label><input type='checkbox' checked value='"+jsonArray[i].labOrderOrg+"+"+ jsonArray[i].zxbz +"+"+jsonArray[i].qbgsj+"+"+jsonArray[i].qbgdd+"'></label></div>";
		} else {
			html+="<div class='col-sm-2' style=''>"+
				"<div class='col-sm-6'><label><input type='checkbox' value='"+jsonArray[i].labOrderOrg+"+"+ jsonArray[i].zxbz +"+"+jsonArray[i].qbgsj+"+"+jsonArray[i].qbgdd+"'></label></div>";
		}
		if(jsonArray[i].bmp == "") {
			html+="<div class='col-sm-4'>&nbsp;</div>";
		} else {
			html+="<div class='col-sm-4'><img src='"+jsonArray[i].bmp+"' alt='"+jsonArray[i].sglx +"' height='50px' /></div>";
		}
		if(jsonArray[i].requestMode == 1) {
			html+="<div class='col-sm-2'><span class='glyphicon glyphicon-star btn-lg' style='color:red;padding-left:0px;' aria-hidden='true'></span></div>";
		} else {
			html+="<div class='col-sm-2'>&nbsp;</div>";
		}
		html+="</div>";
		html+="<div class='col-sm-10' style=''>";
		html+="<div ><span class='datespan'>收费项目:</span><b id='ylmc'>"+jsonArray[i].ylmc+"</b>"+
			"<span >发票号:</span><b id='sfsb'>"+jsonArray[i].requestId+"</b>"+
			"<span >单价:</span><b id='dj'>"+jsonArray[i].price+"</b>"+
			"×<b id='sl'>"+jsonArray[i].amount+"</b>"+
			"<span >执行科室:</span><b id='ksdm'>"+jsonArray[i].labDepart+"</b>"+
			"<span >采集量:</span><b id='bbl'>"+jsonArray[i].bbl+"</b></div>"+
			"<div>"
			+ "<span >报告时间:</span><b id='qbgsj'>"+jsonArray[i].qbgsj+"</b>"+
			"<span >申请时间:</span><b id='kdsj'>"+jsonArray[i].requestTime+"</b>"+
			"<span >申请科室:</span><b id='sjksdm'>"+jsonArray[i].hosSection+"</b>"+
			"<span >地点:</span><b id='qbgdd'>"+jsonArray[i].qbgdd+"</b>"+
			"</div>";
		html+="</div></div>";
	}
	$("#" + id).html(html);
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