function personal(name){
	window.location.href="../pb/grcx?name=" + name;
}

function preview1() {
	bdhtml=window.document.body.innerHTML;
	sprnstr="<!--startprint-->"; //开始打印的地方
	eprnstr="<!--endprint-->";  //结束打印标记
	prnhtml=bdhtml.substr(bdhtml.indexOf(sprnstr)+17);
	prnhtml=prnhtml.substring(0,prnhtml.indexOf(eprnstr));
	window.document.body.innerHTML=prnhtml;
	window.print();
}

$(function() {
	
	$("#data").html($("#cxdata").val());
	
	$("#date").datepicker({
		changeMonth: true,
	    changeYear: true,
		dateFormat: 'yy-mm',
		monthNamesShort: ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'],
		dayNamesMin: ['一','二','三','四','五','六','日']
	});
	
	
	$("#searchPB").click(function() {
		if($("#typeSel").val()==2){
			window.location.href="../pb/sxcx";
		}else
			window.location.href="../pb/pbcx?date=" + $("#date").val() + "&section=" + $("#sectionSel").val() + "&type=" + $("#typeSel").val();
		//$.get("<c:url value='/pb/pbcx'/>",{date:$("#date").val(),section:$("#section").val()},function() {});
	});
	
	$("#sectionSel").change(function() {
		$("#section").val($("#sectionSel").val());
		if($("#sectionSel").val() == '1300000') {
			$("#typeSel").css("display","block");
		} else {
			$("#typeSel").css("display","none");
		}
	});
	$("#typeSel").change(function(){
		if($("#typeSel").val()==2){
			window.location.href="../pb/sxcx";
		}else if($("#typeSel").val()==3){
			window.location.href="../pb/sxgroupPb?view=1&date=" + $("#date").val()+"&section="+$("#sectionSel").val();
		}else
			window.location.href="../pb/pbcx?date=" + $("#date").val() + "&section=" + $("#sectionSel").val() + "&type=" + $("#typeSel").val();
	});
	
	$("#typeSel").val($("#type").val());
	if($("#month").val().length!=0){
		$("#date").val($("#month").val());
	}else{
		$("#date").val(new Date().Format("yyyy-MM"));
	}
	
	$("#sectionSel").val($("#section").val());
	
	var section = $("#sectionSel").val();
	if(section == '1300000') {
		$("#typeSel").css("display","block");
	}
	
	$("#daochu").click(function(){
		/*$.post("../pb/pbcx/daochu",{date:$("#date").val(),section:$("#sectionSel").val(),type:$("#typeSel").val()},function(data){
			if(data){
				alert("success!");
			}else{
				alert("fail!");
			}
		});
		*/
		window.location.href = "../pb/pbcx/daochu?date="+$("#date").val()+"&section=" + $("#sectionSel").val() + "&type=" + $("#typeSel").val();
	});
	
	$("#footer").css('display','none');
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