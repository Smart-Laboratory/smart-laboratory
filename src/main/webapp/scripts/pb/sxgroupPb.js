

$(function() {
	$("#labSelect").val($("#section").val());
	$("#pbdata").html($("#pbtext").val());
	$(".footer").css("display","none");
	
	$("#pbdata tr td").click(function(){
		var id=this.id;
		
		var name = this.name;
		var shifts=$("#"+id).html();
		
		$.each($("#shiftSelect input"),function(name,v){
			if(v.checked){
				
				if(shifts.indexOf(v.value+";")>=0){
					shifts=shifts.replace(v.value+";","");
				}else{
					shifts = shifts + v.value+";";
				}
			}
		});
		$("#"+id).html(shifts);
//		}
	});
	
	$("#date").datepicker({
		changeMonth: true,
	    changeYear: true,
		dateFormat: 'yy-mm',
		monthNamesShort: ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'],
		dayNamesMin: ['日','一','二','三','四','五','六'],
		showButtonPanel: true, 
		onClose: function(dateText, inst) { 
		var month = $("#ui-datepicker-div .ui-datepicker-month :selected").val(); 
		var year = $("#ui-datepicker-div .ui-datepicker-year :selected").val(); 
		$(this).datepicker('setDate', new Date(year, month, 1)); 
		} 
	});
	

	
	
	$("#shiftBtn").click(function() {
		var ischecked = true;
		if (ischecked) {
			var section = $("#section").val();
			var text = "";
			var date = $("#month").val();
			$("td[name^='td']").each(function(i){
				var array = $(this).attr("id").split("-");
				var day = "";
				if(array[1].length == 1) {
					day = '0' + array[1];
				} else {
					day = array[1];
				}
				
				var value = $(this).html();
				value = value.split("<br>").join('');
//				value = value.replace("<span class=\"glyphicon glyphicon-ok\"></span> ","")
				if($(this).attr("class").indexOf("gx")>=0)
					value += "\u516C\u4F11;";
					text = text + array[0] + ":" + date + "-" + day + ":" + value  +",";
			});
			var bz= $("#bz").val();
			$.post("../pb/pb/submit",{text:text,section:section,date:date,bz:bz,isStu:"true"},function(data) {
				if(data){
					alert("Success!");
					window.location.href="../pb/sxgroupPb?date=" + $("#date").val()+"&section=" + $("#section").val();
				}else{
					alert("Fail!")
				}
				
			});
			
		}
	});
	
	$("#shiftBtn2").click(function() {
		var section = $("#section").val();
		var month = $("#month").val();
		$.get("../pb/pb/workCount",{section:section,month:month},function(data){
			alert("Success!");
			for(var i=0;i<data.length;i++){
				var worker = data[i];
				var name = worker.worker; 
				$("#nx"+name).html(worker.holiday);
				$("#yx"+name).html(worker.monthOff);
				$("#yb"+name).html(worker.workTime);
				$("#yjx"+name).html(worker.yjx);
			}
		});

	});
	
	$("#changeMonth").click(function() {
		window.location.href="../pb/sxgroupPb?date=" + $("#date").val()+"&section=" + $("#section").val();
	});
	
	$("#date").val($("#month").val());
	
		
	$("#publish").click(function(){
		$.post("../pb/pb/publish",{section:$("#section").val(), month:$("#month").val(),state:2},function(data){
			if(data){
				alert("Publish seccess!");
			}
		})
	});
	
	labChange=function(select){
		var value = $(select).children().attr("title");
		if(value ==null || value== undefined)
			value = $(select).val();
		
		$.ajax({
			  type: 'POST',
			  url: "../audit/labChange?lab="+value,
			  success:function(){
				  $("#labText").html($(select).children().html());
				  window.location.href="../pb/sxgroupPb?date=" + $("#date").val()+"&section=" +  value;
			  }
		});
		
	}
});

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