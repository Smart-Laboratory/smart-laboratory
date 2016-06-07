$(function() {
	$("#doctin").focus();
	// 对Date的扩展，将 Date 转化为指定格式的String
	// 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符， 
	// 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字) 
	// 例子： 
	// (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423 
	// (new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18 
	Date.prototype.Format = function (fmt) { //author: meizz 
	    var o = {
	        "M+": this.getMonth() + 1, //月份 
	        "d+": this.getDate(), //日 
	        "h+": this.getHours(), //小时 
	        "m+": this.getMinutes(), //分 
	        "s+": this.getSeconds(), //秒 
	        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
	        "S": this.getMilliseconds() //毫秒 
	    };
	    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	    for (var k in o)
	    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
	    return fmt;
	}
});

function setDefaultValue(){
//	var date = new Date().Format("yyyyMMdd");
	$("#search_date").val(new Date().Format("yyyyMMdd"));
}


function modifySample() {
	var testSection = $("#testSection").val();
	var sampleNumber = $("#sampleNumber").val();
	var operation = $("#operation").val();
	var operationValue = $("#operationValue").val();
	var search_date = $("#search_date").val();
	var modifyResult = $("#modifyResult").val();
	
	if(search_date==""){
		alert("检验日期不能为空！");return ;
	}else
	if(testSection == "") {
		alert("检验段不能为空！");return ;
	}else
	if(sampleNumber == ""){
		alert("检验段编号不能为空！");return ;
	}else
	if(operation != "inversion"){
		if(operationValue == ""){
			alert("检验段操作数值不能为空！");
			return null;
		}
	}
	$.ajax({
  		type:'post',
		url: "../manage/modify/ajax/sample?search_date="+search_date+"&modifyResult=" + modifyResult +"&testSection=" + testSection + "&sampleNumber=" + sampleNumber + "&operation=" + operation+ "&operationValue=" + operationValue,
  		success: function(data) {
  			if('success'==data){
  				alert("操作成功！");
  			}
  			if('error'==data){
  				alert("填写的数据有错误！");
  			}
  	  	}
  	});
	
}