function getdata(item){
	 var week = $(item).attr("name");
	 window.location.href="../pb/kscount?week=" + week+"&from=" + $("#from").val()+"&to="+$("#to").val();
 }

$(function(){
	$("#weekSelect").html($("#weeks").val());
	$(".footer").css('display','none');
		$("#pbdata").html($("#pbtext").val());
		$("#changeMonth").click(function(){
			window.location.href="../pb/kscount?from=" + $("#from").val()+"&to="+$("#to").val();
		});
		
		$( "#from" ).datepicker({
			changeMonth: true,
			changeYear: true,
			dateFormat:"yy-mm",
			monthNamesShort: ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'],
			dayNamesMin: ['日','一','二','三','四','五','六'],
			showButtonPanel: true, 
			onClose: function(dateText, inst) { 
			var month = $("#ui-datepicker-div .ui-datepicker-month :selected").val(); 
			var year = $("#ui-datepicker-div .ui-datepicker-year :selected").val(); 
			$(this).datepicker('setDate', new Date(year, month, 1)); 
//			$( "#to" ).datepicker( "option", "minDate", new Date(year, month, 1) );
			} 
			
		});
		$( "#to" ).datepicker({
			changeMonth: true,
			changeYear: true,
			dateFormat:"yy-mm",
			monthNamesShort: ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'],
			dayNamesMin: ['日','一','二','三','四','五','六'],
			showButtonPanel: true, 
			onClose: function(dateText, inst) { 
				var month = $("#ui-datepicker-div .ui-datepicker-month :selected").val(); 
				var year = $("#ui-datepicker-div .ui-datepicker-year :selected").val(); 
				$(this).datepicker('setDate', new Date(year, month, 1));
//				$( "#from" ).datepicker( "option", "maxDate", new Date(year, month, 1) );
			} 
		});
		if($("#from").val()==null){
			$( "#from" ).val(new Date().Format("yyyy-MM"));
			$( "#to" ).val(new Date().Format("yyyy-MM"));
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