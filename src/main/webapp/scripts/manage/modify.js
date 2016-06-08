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

var isFirstLog = true;
function showLogData() {
	if(isFirstLog) {
		$("#sampleLog").jqGrid({
			url: "../manage/modify/getLog?text=" + $("#logtext").val() + "&type=" + $("#logtexttype").val(),
			mtype: "GET",
			datatype: "json",
			colNames: ['ID','日志记录时间','IP','操作者','操作','样本号', '在院方式', '姓名','科室','床号','性别','年龄','医嘱号','接收时间','接收实验室','检验目的','样本类型','就诊卡号','临床诊断','申请者','金额'],
			colModel: [
			    { name: 'logid', index: 'logid', hidden: true},
			    { name: 'logtime', index: 'logtime', width: 120},
			    { name: 'logip', index: 'logip', width: 120},
			    { name: 'logger', index: 'logger', width: 60},
			    { name: 'logoperate', index: 'logoperate', width: 60},
				{ name: 'sampleno', index: 'sampleno', width: 110},
				{ name: 'shm', index: 'shm', width: 70},
				{ name: 'pname', index: 'pname', width: 80 },
				{ name: 'section', index: 'section', width: 80 },
				{ name: 'bed', index: 'bed', width: 40 },
				{ name: 'sex', index: 'sex', width: 40 },
				{ name: 'age', index: 'age', width: 40 },
				{ name: 'sampleid', index: 'sampleid', width: 80 },
				{ name: 'receivetime', index: 'receivetime', width: 130 },
				{ name: 'lab', index: 'lab', width: 100 },
				{ name: 'exam', index: 'exam', width: 100 },
				{ name: 'sampletype', index: 'sampletype', width: 70 },
				{ name: 'pid', index: 'pid', width: 100 },
				{ name: 'diag', index: 'diag', width: 100 },
				{ name: 'requester', index: 'requester', width: 70 },
				{ name: 'fee', index: 'fee', width: 40 }
			],
			viewrecords: true,
			height: "100%",
			rownumbers: true, 
			rownumWidth: 35
		});
		isFirstLog = false;
	} else {
		jQuery("#sampleLog").jqGrid('setGridParam',{
			url: "../manage/modify/getLog?text=" + $("#logtext").val() + "&type=" + $("#logtexttype").val()
		}).trigger('reloadGrid');//重新载入
	}
}

function recoverLog() {
	var id = $("#sampleLog").jqGrid('getGridParam','selrow');
	var row = jQuery("#sampleLog").jqGrid('getRowData',id);
	if(id == "") {
		layer.msg("你选中的是当前的样本信息，无需恢复！", {icon: 2, time: 1000});
	} else {
		if (confirm("确认将当前样本恢复到"+row.logtime+"吗？")) {
			$.post("../manage/modify/ajax/recoverLog",{id:id},function(data){
				if(data) {
					layer.msg("当前样本信息已恢复到" + row.logtime + "!", {icon: 1, time: 1000});
					jQuery("#sampleLog").jqGrid('setGridParam',{
						url: "../manage/modify/getLog?text=" + $("#logtext").val() + "&type=" + $("#logtexttype").val()
					}).trigger('reloadGrid');
				} else {
					layer.msg("恢复失败！", {icon: 2, time: 1000});
				}
			});
		}
	}
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
		url: "../manage/modify/ajax/sample?search_date="+search_date +"&modifyResult=" + modifyResult +"&testSection=" + testSection + "&sampleNumber=" + sampleNumber + "&operation=" + operation+ "&operationValue=" + operationValue,
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