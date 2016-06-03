$(function() {
	
	$("#doctin").focus();
	
	
	
});



function modifySample() {
	var testSection = $("#testSection").val();
	var sampleNumber = $("#sampleNumber").val();
	var operation = $("#operation").val();
	var operationValue = $("#operationValue").val();
	
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
		url: "../manage/modify/ajax/sample?testSection=" + testSection + "&sampleNumber=" + sampleNumber + "&operation=" + operation+ "&operationValue=" + operationValue,
  		success: function(data) {
  			alert(data);
  			//data = jQuery.parseJSON(data);
  	  	}
  	});
	
}