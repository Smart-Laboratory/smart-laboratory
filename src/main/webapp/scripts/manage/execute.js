function getData(item,event){
	var e = e||event;
	var key = event.keyCode;
	if(navigator.appName=="Netscape"){
		key=e.which;
	}else{
		key=event.keyCode;
	}
	if(key == 13){
		var id = $(item).attr("id");
		if($("#bloodCheck").prop("checked")==true){
			//去除条码后的校验位
			//根据医嘱号，取patientid
			//是否需要留样
			
			//获取病人信息
			var jzkh=$("#jzkh").val();
			if(jzkh==null || jzkh.length=="")
				return;
			
			$.get("../manage/execute/getPatient",{patientId:jzkh},function(data){
				//先清空数据
				$("#blh").html("");$("#patientId").html("");$("#pName").html("");$("#pSex").html("");$("#pCsrq").html("");
				//插入数据
				var csrq = data.csrq;
				$("#blh").html(data.blh);$("#patientId").html(data.patientId);$("#pName").html(data.name);$("#pSex").html(data.sex);$("#pCsrq").html(csrq.split(".")[0]);
			})
			
			$.get("../manage/execute/getTests",{patientId:jzkh,requestmode:$("input[name='select_type']:checked").val(),from:$("#from").val(),to:$("#to").val()},function(data){
				if(data!=null){
					$("#tests").html(data.html);
				}
			});
		}
		
		
		
	}
}

$(function(){
	$("#printDialog").dialog({
		title: "条码打印",
		autoOpen: false,
		resizable: false,
		modal:true,
	    width: 550,
	    height: 500
	});
	
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
//		selval:selval,patientId:jzkh,requestmode:0,from:$("#from").val(),to:$("#to").val()
		$.get("../manage/execute/ajax/submit",{selval:selval,patientId:$("#jzkh").val(),requestmode:0,from:$("#from").val(),to:$("#to").val()},function(data){
			data = jQuery.parseJSON(data);
			var tests = data.laborders;
			var testStr="";
			for(var i =0; i<tests.length;i++){
				testStr += tests[i] + ",";
			}
			alert(testStr);
			if(data.error == null || data.error == undefined){
				$('#printFrame').empty();
		    	$("#printFrame").append("<iframe id='iframe_print' name='iframe_print' frameborder=0 style='background-color:transparent' width='99%' height='99%' src=\"../manage/printBarcode?tests="+testStr+"\"/>");
				$("#printDialog").dialog("open");
			}
		});
		
//		$('#printFrame').empty();
//    	$("#printFrame").append("<iframe id='iframe_print' name='iframe_print' frameborder=0 style='background-color:transparent' width='99%' height='99%' src=\"../manage/printBarcode\"/>");
//		$("#printDialog").dialog("open");
	});
})





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
}  