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
		LODOP.ADD_PRINT_BARCODEA("barcode","86mm","2.85mm","46mm",30,"128B",data.barcode);
		LODOP.SET_PRINT_STYLEA(0,"ShowBarText",0);
		LODOP.ADD_PRINT_TEXTA("code","94mm","8.2mm",150,20,"*"+data.sampleNo+"*");
		LODOP.SET_PRINT_STYLEA(0,"Bold",1);
		LODOP.ADD_PRINT_TEXTA("patientinfo1","98mm","2.95mm",180,20,patientInfo1);
		LODOP.ADD_PRINT_TEXTA("labInfo","102mm","2.95mm",180,20, labInfo);
		LODOP.ADD_PRINT_TEXTA("datetime","106mm","2.95mm",180,20,"采集时间 "+data.executeTime);

        LODOP.ADD_PRINT_TEXTA("patientinfo","78mm","51.25mm",180,20,patientInfo);
        LODOP.ADD_PRINT_TEXTA("testinfo","82mm","51.25mm",180,20,data.testName);
        LODOP.ADD_PRINT_BARCODEA("barcode","86mm","51.25mm","46mm",30,"128B",data.barcode);
        LODOP.SET_PRINT_STYLEA(0,"ShowBarText",0);
        LODOP.ADD_PRINT_TEXTA("code","94mm","55.95mm",150,20,"*"+data.sampleNo+"*");
        LODOP.SET_PRINT_STYLEA(0,"Bold",1);
        LODOP.ADD_PRINT_TEXTA("patientinfo1","98mm","51.25mm",180,20,patientInfo1);
		LODOP.ADD_PRINT_TEXTA("labInfo","102mm","51.25mm",180,20, labInfo);
		LODOP.ADD_PRINT_TEXTA("datetime","106mm","51.25mm",180,20,"采集时间 "+data.executeTime);

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
		"testName": "抗精子抗体测定(IgM)",
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
	$.get("../manage/execute/ajax/getTests",{patientId:jzkh,requestmode:$("input[name='select_type']:checked").val(),from:$("#from").val(),to:$("#to").val(), isEmergency: $("#requestModeSelect").val()},function(data){
		if(data!=null){
			var jsonArr = jQuery.parseJSON(data);
			if(jsonArr.length == 0) {
			    if($("input[name='select_type']:checked").val() == 999) {
                    layer.msg("当前病人没有已采集的检验项目！", {icon: 0, time: 1000});
                } else{
                    layer.msg("当前病人没有需要采样的检验项目！", {icon: 0, time: 1000});
                }
                $("#tests").html("");
				return;
			}
			$("#checkAll").prop('checked', true);
			var html = "";
			for(var i = 0; i<jsonArr.length; i++) {
				if(i%2 == 0) {
					html+="<div id='date"+i+"' class='alert sampleInfo' style='background-color:#f5f5f5'>";
				}else{
					html+="<div id='date"+i+"' class='alert sampleInfo' style='background-color:#fff'>";
				}
				if(jsonArr[i].zxbz == 0) {
					html+="<div class='col-sm-2' style=''>"+
						"<div class='col-sm-6'><label><input type='checkbox' checked value='"+jsonArr[i].labOrderOrg+"+"+ jsonArr[i].zxbz +"+"+jsonArr[i].qbgsj+"+"+jsonArr[i].qbgdd+"'></label></div>";
				} else {
					html+="<div class='col-sm-3' style=''>"+
						"<div class='col-sm-6'><label><input type='checkbox' value='"+jsonArr[i].labOrderOrg+"+"+ jsonArr[i].zxbz +"+"+jsonArr[i].qbgsj+"+"+jsonArr[i].qbgdd+"' style='outline:5px solid greenyellow'></label></div>";
				}
				if(jsonArr[i].bmp == "") {
					html+="<div class='col-sm-4'>&nbsp;</div>";
				} else {
					html+="<div class='col-sm-4'><img src='"+jsonArr[i].bmp+"' alt='"+jsonArr[i].sglx +"' height='50px' /></div>";
				}
				if(jsonArr[i].requestMode == 1) {
					html+="<div class='col-sm-2'><span class='glyphicon glyphicon-star btn-lg' style='color:red;padding-left:0px;' aria-hidden='true'></span></div>";
				} else {
					html+="<div class='col-sm-2'>&nbsp;</div>";
				}
				html+="</div>";
				html+="<div class='col-sm-10' style=''>";
				html+="<div ><span class='datespan'>收费项目:</span><b id='ylmc'>"+jsonArr[i].ylmc+"</b>"+
					"<span >发票号:</span><b id='sfsb'>"+jsonArr[i].requestId+"</b>"+
					"<span >单价:</span><b id='dj'>"+jsonArr[i].price+"</b>"+
					"×<b id='sl'>"+jsonArr[i].amount+"</b>"+
					"<span >执行科室:</span><b id='ksdm'>"+jsonArr[i].labDepart+"</b>"+
					"<span >采集量:</span><b id='bbl'>"+jsonArr[i].bbl+"</b></div>"+
					"<div>"
					+ "<span >报告时间:</span><b id='qbgsj'>"+jsonArr[i].qbgsj+"</b>"+
					"<span >申请时间:</span><b id='kdsj'>"+jsonArr[i].requestTime+"</b>"+
					"<span >申请科室:</span><b id='sjksdm'>"+jsonArr[i].hosSection+"</b>"+
					"<span >地点:</span><b id='qbgdd'>"+jsonArr[i].qbgdd+"</b>"+
					"</div>";
				html+="</div></div>";
			}
			$("#tests").html(html);
			if(data.examtodo!=null)
				$("#examtodo").html("待做项："+data.examtodo);
		}
	});
}

$(function(){
	
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
	
	$("#conform").click(function(){
		
		var selval="";
		$("#tests input:checkbox").each(function(){
			if($(this).prop("checked")==true)
				selval = selval + $(this).val()+";";
		});
		if(selval==null || selval == ''){
			layer.msg("请选择检验项目", {icon: 2, time: 1000});
			return;
		}
		var selfexecute = 0;
		if($("#selfexecute").prop("checked")==true){
			selfexecute =1;
		}
//		selval:selval,patientId:jzkh,requestmode:0,from:$("#from").val(),to:$("#to").val()
		$.get("../manage/execute/ajax/submit",{selval:selval,selfexecute:selfexecute},function(data){
			data = jQuery.parseJSON(data);
			console.log(data);
			for(i=0;i<data.labOrders.length;i++){
				startPrint(data.labOrders[i]);
			}
			reloadTests();
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

	$('#checkAll').click(function(){
		if($(this).prop("checked")) {
			$('#tests input[type=checkbox]').prop('checked',true);
		} else {
			$('#tests input[type=checkbox]').prop('checked',false);
		}

	});

	$("#requestModeSelect").change(function() {
        reloadTests();
	});

    $("input[name='select_type']").click(function () {
        reloadTests();
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