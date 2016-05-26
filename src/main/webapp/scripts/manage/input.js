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
			$.get("../sample/ajax/get",{id:id},function(data) {
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
				var $tag_obj = $('#examinaim').data('tag');
				$tag_obj.clear();
				var ylxhMap = data.ylxhMap;
				for(var key in ylxhMap) {
					$tag_obj.add(ylxhMap[key], key);
				}
				$("#sampletype").val(data.sampletype);
				$("#feestatus").val(data.feestatus);
				$("#requester").val(data.requester);
				$("#receivetime").val(data.receivetime);
				$("#executetime").val(data.executetime);
			});
			break;
	}
}

function Pad(num, n) {
    return (Array(n).join(0) + num).slice(-n);
}

function sample(operate) {
	var shm,doct,sampleno,pid,section,sectionCode,pname,sex,age,diag,ylxh,exam,feestatus,requester,receivetime,executetime;
	ylxh = "";
	exam ="";
	shm = $("#stayhospitalmode").val();
	doct = $("#doctadviseno").val();
	sampleno = $("#sampleno").val();
	pid = $("#patientid").val();
	section = $("#section").val();
	sectionCode = $("#sectionCode").val();
	pname = $("#patientname").val();
	sex = $("#sex").val();
	age = $("#age").val();
	diag = $("#diagnostic").val();
	var len = $('#examTag .tag').length - 1;
	$('#examTag .tag').each(function(i) {
		if(i == len) {
			ylxh += this.id;
			exam += $(this).text().replace("×","");
		} else {
			ylxh += this.id + "+";
			exam += $(this).text().replace("×","") + "+";
		}
	});
	sampletype = $("#sampletype").val();
	feestatus = $("#feestatus").val();
	requester = $("#requester").val();
	receivetime = $("#receivetime").val();
	executetime = $("#executetime").val();
	
	$.post("../sample/ajax/editSample",
		{
			operate : operate,
			shm : shm,
			doct : doct,
			sampleno : sampleno,
			pid : pid,
			section : section,
			sectionCode : sectionCode,
			pname : pname,
			sex : sex,
			age : age,
			diag : diag,
			sampletype : sampletype,
			feestatus : feestatus,
			requester : requester,
			receivetime : receivetime,
			executetime : executetime,
			exam : exam,
			ylxh : ylxh
		},
		function(data) {
			var data = JSON.parse(data);
			if(data.isreceived) {
				alert("医嘱号为" + data.id + "的样本已接收！");
			} else {
				var sampleno = $("#sampleno").val();
				$("#cancel").click();
				$('#examinaim').data('tag').clear();
				$("#sampleno").val(sampleno.substring(0,11) + Pad((parseInt(sampleno.substring(11,14)) + 1),3));
				var html = "<tr>";
				html += "<td><i class='fa fa-pencil'/></td>";
				html += "<td>" + data.id + "</td>";
				html += "<td>" + data.sampleno + "</td>";
				html += "<td>" + data.pid + "</td>";
				html += "<td>" + data.pname + "</td>";
				html += "<td>" + data.sex + "</td>";
				html += "<td>" + data.age + "</td>";
				html += "<td>" + data.diag + "</td>";
				html += "<td>" + data.exam + "</td>";
				html += "<td>" + data.receiver + "</td>";
				html += "<td>" + data.receivetime + "</td>";
				html += "</tr>";
				$("#tableBody").append(html);
			}
		}
	);
}


$(function() {
	$("#sampletype").val("C");
	
	var tag_input = $('#examinaim');
	tag_input.tag({
		placeholder:tag_input.attr('placeholder'),
		source: function(query, process) {
			$.get("../sample/ajax/searchYlsf",{query:query},function(data) {
				var json = jQuery.parseJSON(data);
				process(json.list);
			});
		}
	})
	var $tag_obj = $('#examinaim').data('tag');
	//$tag_obj.add('Programmatically Added');
});