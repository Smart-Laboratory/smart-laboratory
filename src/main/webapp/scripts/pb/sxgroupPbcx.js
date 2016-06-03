

$(function() {
	$("#labSelect").val($("#section").val());
	$("#pbdata").html($("#pbtext").val());
	
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
		dayNamesMin: ['一','二','三','四','五','六','日'],
		showButtonPanel: true, 
		onClose: function(dateText, inst) { 
		var month = $("#ui-datepicker-div .ui-datepicker-month :selected").val(); 
		var year = $("#ui-datepicker-div .ui-datepicker-year :selected").val(); 
		$(this).datepicker('setDate', new Date(year, month, 1)); 
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
		window.location.href="../pb/sxgroupPbcx?date=" + $("#date").val()+"&section=" + $("#section").val();
	});
	
	$("#date").val($("#month").val());
	
	labChange=function(select){
		$.ajax({
			  type: 'POST',
			  url: "../audit/labChange?lab="+$(select).children().attr("title"),
			  success:function(){
				  $("#labText").html($(select).children().html());
					window.location.href="../pb/sxgroupPbcx?date=" + $("#date").val()+"&section=" +  $(select).children().attr("title");
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
}  