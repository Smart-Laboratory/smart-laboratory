	function getRelativeTests(sample){
		$("#relative-tests").html(" ");
		$.get("../audit/ajax/relativeTest",{sample:sample}, function(data) {
			if(data != "") {
				data = jQuery.parseJSON(data);
				$("#relative-tests").html(data.html);
			}
		});
	}
	
	function getPatient(ret) {
 		var docNo = ret.id;
		$.get("../audit/patient",{id:docNo},function(data){
			if (data.isOverTime) {
				$('#tatDialog').dialog("option","title", "TAT超时");
				$("#tatBtn").html("<b style='color: #FF4500;'>TAT超时</b>");
			} else {
				$('#tatDialog').dialog("option","title", "TAT");
				$("#tatBtn").html("<b>TAT</b>");
			}
    		if(data.size > 30 && $("#oneColumnShowBtn").attr("checked") != "checked") {
    			$("#patientRow").css('display','none');
    			$("#twosampleTable").css('display','block');
    			if (isFirstTime) {
    				getSample(ret.sample);
        			getSample0(ret.sample);
        			getSample1(ret.sample);
        			isFirstTime = false;
        		} else {
        			jQuery("#sample0").jqGrid("setGridParam",{url:"<c:url value='/explain/audit/sample0'/>?id="+ret.sample,editurl: "<c:url value='/explain/audit/edit'/>?sampleNo=" + ret.sample}).trigger("reloadGrid");
        			jQuery("#sample1").jqGrid("setGridParam",{url:"<c:url value='/explain/audit/sample1'/>?id="+ret.sample,editurl: "<c:url value='/explain/audit/edit'/>?sampleNo=" + ret.sample}).trigger("reloadGrid");
        		}
			} else {
        		$("#twosampleTable").css('display','none');
        		$("#patientRow").css('display','block');
        		if (isFirstTime) {
        			getSample(ret.sample);
        			getSample0(ret.sample);
        			getSample1(ret.sample);
    				isFirstTime = false;
        		} else {
        			jQuery("#rowed3").jqGrid("setGridParam",{url:"<c:url value='/explain/audit/sample'/>?id="+ret.sample,editurl: "<c:url value='/explain/audit/edit'/>?sampleNo=" + ret.sample}).trigger("reloadGrid");
        		}
			}
    		
			if($("#englishToChBtn").attr("checked") == "checked") {
				jQuery("#rowed3").setGridParam().showCol("ab");
				jQuery("#rowed3").setGridParam().hideCol("name");
				jQuery("#sample0").setGridParam().showCol("ab");
				jQuery("#sample0").setGridParam().hideCol("name");
				jQuery("#sample1").setGridParam().showCol("ab");
				jQuery("#sample1").setGridParam().hideCol("name");
			} else {
				jQuery("#rowed3").setGridParam().showCol("name");
				jQuery("#rowed3").setGridParam().hideCol("ab");
				jQuery("#sample0").setGridParam().showCol("name");
				jQuery("#sample0").setGridParam().hideCol("ab");
				jQuery("#sample1").setGridParam().showCol("name");
				jQuery("#sample1").setGridParam().hideCol("ab");
			}
			
    		if ($("#historyTabs").tabs('option', 'selected') == 0) {
   				jQuery("#audit_information").jqGrid("setGridParam",{
   					url:"<c:url value='/explain/audit/explain'/>?id="+ret.id,
   					editurl: "<c:url value='/explain/audit/explain/edit'/>?docNo=" + ret.id
   				}).trigger("reloadGrid");
    		} else if ($("#historyTabs").tabs('option', 'selected') == 1) {
				jQuery("#rowed3").setGridParam().showCol("last2");
				jQuery("#rowed3").setGridParam().showCol("last3");
				jQuery("#rowed3").setGridParam().showCol("last4");
				jQuery("#rowed3").setGridParam().showCol("device");
				jQuery("#rowed3").setGridParam().showCol("checktime");
    		} else {
    			getSopSchedule($("#labSelect").val());
    		}
    		$("#historyTabs").css('display','block');
			
			$("#midContent").css('display','block');
			if(data.mode == 1) {
				$("#sampleTitle").html("<font color='red'><fmt:message key='jizhen'/></font>" + $("#hiddenSampleNo").val() + "  " + data.examinaim);
				$("#sample0").jqGrid("setCaption", "<font color='red'><fmt:message key='jizhen'/></font>&nbsp" + $("#hiddenSampleNo").val() + " (\u5171" + data.size + "\u9879)");        	
			} else {
        		$("#sampleTitle").html($("#hiddenSampleNo").val() + "  " + data.examinaim);
        		$("#sample0").jqGrid("setCaption", $("#hiddenSampleNo").val() + " (\u5171" + data.size + "\u9879)");
        	}
			if(data.isLack) {
				var html = $("#sampleTitle").html();
				$("#sampleTitle").html("<font color='red'>" + html + "</font>");
			}
			if (data.hasImages) {
				$("#imageBtn").css('display','inline');
			} else {
				$("#imageBtn").css('display','none');
			}
			$("#rowed3").jqGrid("setCaption", $("#sampleTitle").html());
			$("#sample1").jqGrid("setCaption", data.examinaim);
        	$("#audit_reason").html(data.reason);
        	$("#pName").html("<a href='../patientList?patientId=" + data.patientId + "&blh=" + data.blh + "' target='_blank'>" + data.name + "</a>");
        	/* $("#pName").html("<a href='../../patient/list?patientId=" + data.patientId + "&blh=" + data.blh + "' target='_blank'>" + data.name + "</a>"); */
        	$("#pAge").html(data.age);
        	$("#blh").html("http://192.168.17.102/ZWEMR/SysLogin.aspx?lcation=inside&ly=D&edt=N&pid=" + data.blh + "&gh=" + data.requester + "' target='_blank'>" + data.blh + "</a>");
        	$("#pId").html(data.id);
        	$("#pSex").html(data.sex);
        	$("#pSection").html(data.section);
        	$("#pType").html(data.type);
        	if(data.diagnosticKnow == "") {
        		$("#diagnostic").html(data.diagnostic);
        	} else {
        		$("#diagnostic").html("<a href='#' onclick='javascript:show_knowledge(\""+data.diagnosticKnow+"\")'>"+data.diagnostic+"</a>");
        	}
        	
        	var reason = data.passReason;
        	$("#passreason").html(reason);
        	if (reason != null) {
        		$("#passLabel").css('display','block');
        	} else {
        		$("#passLabel").css('display','none');
        	}
        	
        	if(data.bed == null){
        		$("#pBedText").css('display','none');
        		$("#pBedLabel").css('display','none');
        	}else{
        		$("#pBed").html(data.bed);
        		$("#pBedText").css('display','inline');
        		$("#pBedLabel").css('display','inline');
        	}
        	
        	$("#hiddenDocId").val(docNo);
        	
        	$("#critical_alert").removeClass('alert-error');
        	$("#critical_title").html("");
    		$("#critical_info").html("");
    		$("#critical_time").html("");

        	if (data.mark == 6 && data.dgFlag == 0) {
        		$("#critical_div").css('display','block');
        		$("#critical_alert").addClass('alert-error');
        		$("#critical_title").html("\u8be5\u5371\u673a\u503c\u672a\u5904\u7406");
        	} else if (data.mark == 6 && data.dgFlag == 1) {
        		$("#critical_div").css('display','block');
        		$("#critical_info").html(data.dgInfo);
        		$("#critical_time").html(data.dgTime);
        		$("#critical_title").html("\u8be5\u5371\u673a\u503c\u5df2\u5904\u7406");
        	} else {
        		$("#critical_div").css('display','none');
        	}
        	if (data.isDanger) {
        		$("#blh").children().css({
        			"color":"red"
        		});
        		$("#blh").css({
        			"-moz-animation":"twinkling 2s infinite ease-in-out"
        		});
        	} else {
        		$("#blh").css({
        			"-moz-animation":"none"
        		});
        	}
        }, "json");
		getRelativeTests(ret.sample);
	}