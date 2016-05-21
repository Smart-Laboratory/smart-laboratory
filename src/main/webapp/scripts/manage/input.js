function getData(obj,event) {
	var e=e||event;
	var key=event.keyCode;;
	if(navigator.appName=="Netscape"){
		key=e.which;
	}else{
		key=event.keyCode;
	}
	switch(key){
		case 13 : 
			var id=obj.value;
			$.get("<c:url value='/sample/ajax/get'/>",{id:id},function(data) {
				var data = JSON.parse(data);
				$("#stayhospitalmode").val(data.stayhospitalmode);
				$("#doctadviseno").val(data.doctadviseno);
				if(data.sampleno != '0') {
					$("#sampleno").val(data.sampleno);
				}
				$("#patientid").val(data.patientid);
				$("#section").val(data.section);
				$("#sectionCode").val(data.sectionCode);
				$("#patientname").val(data.patientname);
				$("#sex").val(data.sex);
				$("#age").val(data.age);
				$("#diagnostic").val(data.diagnostic);
				$("#examinaim").val(data.examinaim);
				$("#sampletype").val(data.sampletype);
				$("#sampletypeCode").val(data.sampletypeCode);
				$("#fee").val(data.fee);
				$("#requester").val(data.requester);
				$("#receivetime").val(data.receivetime);
				$("#executetime").val(data.executetime);
				$("#ylxh").val(data.ylxh);
			});
			break;
	}
}

$(function() {
	$("#sampletype").val("C");
	
	var tag_input = $('#form-field-tags');
	tag_input.tag(
	  {
		placeholder:tag_input.attr('placeholder'),
		source: ace.vars['US_STATES'],
	  }
	)
	var $tag_obj = $('#form-field-tags').data('tag');
	$tag_obj.add('Programmatically Added');
	/*try{
		
	}
	catch(e) {
		alert(e.message);
		tag_input.after('<textarea id="'+tag_input.attr('id')+'" name="'+tag_input.attr('name')+'" rows="3">'+tag_input.val()+'</textarea>').remove();
	}*/
});