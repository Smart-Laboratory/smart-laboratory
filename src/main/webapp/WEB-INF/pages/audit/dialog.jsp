<script type="text/javascript" src="../scripts/lis/audit/dialog.js"></script>

<div id="dialogs" style="display:none;">
<div id="auditDialog" style="text-align:left;" title="<fmt:message key="manual.audit"/>">
	<div id="searchPanel" align="left">
		<div style="display:none;" id="isContinued">0</div>
		<div class="form-inline" style="margin-bottom:5px;">
			<input id="auditText2" type="text" class="span4" style="margin-left:0px;" placeholder="20120829SGB001-999"/>
			<button id="auditBtn2" class="btn" style="width:60px;margin-left:10px;margin-right:15px;"><fmt:message key='audit'/></button>
			<input type="checkbox" id="auditAllbtn" style="margin-top:-2px;margin-right:10px;"><fmt:message key='reaudit'/>
		</div>
		
		<div style="border-top:1px solid #E1E1E1;margin-bottom:10px;margin-top:10px;"></div>
	</div>
</div>

<div id="auditPrint" align="left" title='<fmt:message key="audit.preview" />'>
	<button class="btn btn-success" onclick="javascript:document.getElementById('iframe_print').contentWindow.print();;"><fmt:message key="audit.print" /></button>
	<div id="printFrame">
	</div>
	<button class="btn btn-success" onclick="javascript:document.getElementById('iframe_print').contentWindow.print();;"><fmt:message key="audit.print" /></button>
</div>

<div id="samplePrint" align="left" title='<fmt:message key="audit.preview" />'>
	<button class="btn btn-success" onclick="javascript:document.getElementById('iframe_sample').contentWindow.print();"><fmt:message key="audit.print" /></button>
	<div id="samplePrintFrame" style="height:480px;">
	</div>
	<button class="btn btn-success" onclick="javascript:document.getElementById('iframe_sample').contentWindow.print();"><fmt:message key="audit.print" /></button>
</div>


<div id="dialog" align="left" title="<fmt:message key='knowledge.info' />">
</div>

<div id="addTestResultDialog" style="text-align:left;" title="<fmt:message key='add.test.result'/>">
	<div>
		<span style=""><fmt:message key='add.single'/></span>
		<input  id="searchProject"  placeholder="<fmt:message key='add.single'/>">
	</div>
	<div>
		<select id="profileList"></select>
		<button id="addProfileBtn" class="btn btn-info"><fmt:message key="button.add.profile" /></button>
		<button id="deleteAllTest" class="btn btn-danger"><fmt:message key="button.empty" /></button>
	</div>
	<div id="addTestList">
	
	</div>
</div>

<div id="addResultDialog" style="text-align:left;" title="<fmt:message key='addReuslt'/>">
	<form id="addResultForm" class="form-horizontal" action="<c:url value='../audit/addResult'/>" method="post">
			<div class="control-group">
				<label class="control-label" ><fmt:message key="addResult.number" /></label>
					<input type="text" class="span3" id="span_docNo" disabled></input>
			</div>
			<div class="control-group">
				<label class="control-label"><fmt:message key="addResult.result" /></label>
					<input id="result_result" type="text" class="span3" name="result"/>
			</div>
			<div class="control-group">
				<label class="control-label"><fmt:message key="addResult.content" /></label>
					<textarea id="result_content" class="span3" name="content"></textarea>
			</div>
			<div class="control-group">
				<div class="controls">
					<input id="result_docNo" name="docNo" type="hidden"></input>
					<button id="addResult" type="submit" class="btn" ><fmt:message key='button.submit' /></button>
				</div>
			</div>
	</form>
</div>

	<div id="tatDialog" title="TAT"  style="text-align:left;" >
		<table class="table">
			<tbody>
			<tr><th><fmt:message key='tat.request' /></th><td><span id="tat_request"></span></td></tr>
			<tr><th><fmt:message key='tat.execute' /></th><td><span id="tat_execute"></span></td></tr>
			<tr><th><fmt:message key='tat.send' /></th><td><span id="tat_send"></span></td></tr>
			<tr><th><fmt:message key='tat.ksreceive' /></th><td><span id="tat_ksreceive"></span></td></tr>
			<tr><th><fmt:message key='tat.receive' /></th><td><span id="tat_receive"></span></td></tr>
			<tr><th><fmt:message key='tat.audit' /></th><td><span id="tat_audit"></span></td></tr>
			<tr><th><fmt:message key='tat.auditor' /></th><td><span id="tat_auditor"></span></td></tr>
			<tr><th><fmt:message key='tat.result' /></th><td><span id="tat_result"></span></td></tr>
			<tr><th><fmt:message key='tat.audit.tat' /></th><td><span id="audit_tat"></span></td></tr>
			</tbody>
		</table>
	</div>
	
	<div id="twoColumnDialog" title="" >
		<div class="clearfix">
			<h2 style="display:none;" id="sampleTitle"></h2>
			<div id="patient-info2" class="alert alert-info" style="margin-bottom:2px;padding:0px;padding-left:10px;padding-bottom:4px;">
				<div class="pItem">
					<span class="pLabel"><fmt:message key="patient.patientName" /></span>
					<span class="pText"><b id="pName2"></b></span>
					<span class="pLabel"><fmt:message key="patient.patientSex" /></span>
					<span class="pText"><b id="pSex2"></b></span>
					<span class="pLabel"><fmt:message key="patient.age" /></span>
					<span class="pText"><b id="pAge2"></b></span>
					<span class="pLabel"><fmt:message key="patient.sampleType" /></span>
					<span class="pText"><b id="pType2"></b></span>
					<span class="pLabel"><fmt:message key="patient.blh" /></span>
					<span class="pText"><b id="blh2"></b></span>
					<span class="pLabel"><fmt:message key="patient.doctadviseno" /></span>
					<span class="pText"><b id="pId2"></b></span>
					<span class="pLabel"><fmt:message key="patient.section"/>&nbsp;</span>
					<span class="pText"><b id="pSection2"></b></span>
					<span id="pBedLabel2" class="pLabel"><fmt:message key="patient.departbed"/>&nbsp;</span>
					<span id="pBedText2" class="pText"><b id="pBed2"></b></span>
					<span class="pLabel"><fmt:message key="diagnostic"/>&nbsp;</span>
					<span class="pText"><b id="diagnostic2"></b></span>
				</div>
			</div>
		</div>
	
		<div style="text-align:left;margin:5px;">
			<button id="tcPassBtn" class="btn btn-success" style="font-size:14px;">
				<b><fmt:message key="pass.button" /></b>
			</button>
			<button id="tcUnpassBtn" class="btn" style="font-size:14px;">
				<b><fmt:message key="unpass.button" /></b>
			</button>
			<button id="tcCloseBtn" class="btn" style="font-size:14px;">
				<b><fmt:message key="button.undeal" /></b>
			</button>
		</div>
		<div style="font-size: 13px;">
			<table id="twocol"></table>
		</div>
		<div id="color2Help" style="margin-top:3px;">
		</div>
	</div>
	
	<div id="opStatusDialog" title="<fmt:message key='pass.button' />/<fmt:message key='unpass.button' />" style="text-align:left;" >
		<h5 id="passNotes"></h5>
		<!-- <textarea id="noteText" rows="1" style="width:280px;"></textarea> -->
		<div id="selectNoteDiv" class="clearfix"></div>
		
		<h5 id="passNotes"><fmt:message key='audit.infomation'/></h5>
		<textarea id="noteText" rows="3" style="width:280px;"></textarea>
		
		<div style="margin-top:20px;margin-left:20px;">
			<button id="opConfirm" class="btn btn-info" ><fmt:message key='button.confirm' /></button>
			<button id="opCancel" class="btn" ><fmt:message key='button.cancel' /></button>
		</div>
	</div>
	
	<div id="collectDialog" title="<fmt:message key='collect.button' />" style="text-align:left;" >
		<h5 id="bamc"><fmt:message key='collect.bamc'/></h5>
		<input id="collect_bamc" type="text" class="span3"/>
		<h5 id="collectType"><fmt:message key='collect.type'/></h5>
		<input id="collect_type" type="text" class="span3"/>
		<h5 id="evaluate"><fmt:message key='collect.evaluate'/></h5>
		<textarea id="collectText" rows="1" style="width:280px;height:60px;"></textarea>
		<div style="margin-top:10px;">
			<button id="collectConfirm" class="btn btn-info" ><fmt:message key='collect.button' /></button>
			<button id="collectCancel" class="btn" ><fmt:message key='button.cancel' /></button>
		</div>
	</div>
	
	<div id="uploadDialog" title="<fmt:message key='upload.title' />" style="text-align:left;" >
	    <%-- <h5><fmt:message key='image.name'/></h5>
		<input id="image_name" type="text" class="span4"/> --%>
		<div>
		    <button class="btn" onclick="createInput();"><fmt:message key='add.point'/></button>
		    <button class="btn" onclick="ajaxFileUpload()"><fmt:message key='upload.title'/></button>
			<div id="more"></div>
		</div>
		<h5><fmt:message key='template.select'/></h5>
		<button id="addCellTemplate" class="btn" style="width:40px;">+</button>
		<select id="cellSelect" onchange="cellChange(this)">
			<option value='0'></option>
			<c:forEach var="cell" items="${cellList}">
				<option value='<c:out value="${cell.id}" />'><c:out value="${cell.name}" /></option>
			</c:forEach>
		</select>
		<select id="cellTemplateSelect" onchange="cellTemplateChange(this)" style="display:none;">
		</select>
	    <h5><fmt:message key='image.description'/></h5>
	    <textarea id="image_note" class="span4" rows="4"></textarea>
	    <div id="galleria"></div>
	</div>
	
	<div id="templateDialog" title="<fmt:message key='template.title' />" style="text-align:left;" >
		<button class="btn" onclick="createTemp();"><fmt:message key='add.point'/></button>
	    <button class="btn" onclick="ajaxTemplateUpload()"><fmt:message key='template.upload'/></button>
	    <div id="more2"></div>
		<h5><fmt:message key='image.name'/></h5>
		<input id="template_name" type="text" class="span4"/>
		<h5><fmt:message key='image.type'/></h5>
		<select id="template_type">
			<option value='0'></option>
			<c:forEach var="cell" items="${cellList}">
				<option value='<c:out value="${cell.id}" />'><c:out value="${cell.name}" /></option>
			</c:forEach>
		</select>
		<h5><fmt:message key='image.description'/></h5>
	    <textarea id="template_note" class="span4" rows="4"></textarea>
	</div>
	
	<div id="imageDialog" title="<fmt:message key='knowledge.check10' />" style="text-align:left;" >
	</div>
	
	<div id="codeSetDialog" title="<fmt:message key='audit.code.setting' />" style="text-align:left;" >
		<div style="margin:10px;">
			<span><fmt:message key='auto.audit'/></span>
			<button id="controlAuditBtn" class="btn btn-info">
			<c:choose>
				<c:when test="${activeAuto}">
					<fmt:message key='audit.stop'/>
				</c:when>
				<c:otherwise>
					<fmt:message key='audit.start'/>
				</c:otherwise>
			</c:choose>
			</button>
			
		</div>
		<div><blockquote><small id="autoAuditNote" ></small></blockquote></div>
		<div id="codeSetDiv" >
			<c:forEach var="code" items="${codeList}">
			<div class="codeItem" style="margin-bottom:5px;">
				<div class="span">
					<label class="checkbox inline">
						<input type="checkbox" class="codeCheck input-ctl" <c:if test="${code.active == true}">checked</c:if>>
						<span class="codeText"><c:out value="${code.labCode}" /></span>
					</label>
				</div>
				<div class="span scopeDiv" <c:if test="${code.active == false}">style="display:none;"</c:if>>
					<input class="span1 input-ctl val-lo" style="color:#000000;" type="text" value="${code.lo}" /> 
					<input style="width:17px;" type="text" value=" -" disabled/> 
					<input class="span1 input-ctl val-hi" style="color:#000000;" type="text" value="${code.hi}" /> 
				</div>
			</div>
			</c:forEach>
		</div>

	</div>
	
	<div id="testModifyDialog" title="<fmt:message key='audit.testModify' />" style="text-align:left;" >
		<table id="test_modify_information"></table>
	</div>
	
	<div id="sampleCompareDialog" title="<fmt:message key='audit.compare.sample' />" style="text-align:left;" >
		<table id="sample_compare_information"></table>
	</div>
	
	<div id="allNeedWriteBackDialog" title="<fmt:message key='audit.info.need.writeback' />" style="text-align:left;" >
		<table id="need_writeback_table" class="table">
			
		</table>
	</div>
	
	<div id="statisticDialog" title="<fmt:message key='statistic.title' />" style="text-align:left;" >
		<div class='form-inline'>
			<input id="statistic_code" class="span2" type="text" placeholder="<fmt:message key='placeholder.code'/>"/>
			<input id="statistic_from" class="span2" type="text" placeholder="<fmt:message key='placeholder.from'/>"/>
			<input id="statistic_to" class="span2" type="text" placeholder="<fmt:message key='placeholder.to'/>"/>
			<button id="statisticBtn" class="btn btn-info"><fmt:message key='button.statistic'/></button>
		</div>
		
		<table id="statistic_table" style="font-size: 14px;"></table>
	</div>
	
	<div id="writeBackPartDialog" title="<fmt:message key='writebackpart' />" style="text-align:left;" >
		<div class='form-inline'>
			<input id="writeBack_text" class="span4" type="text"/>
			<button id="writePartBtn" class="btn btn-info"><fmt:message key='writeback'/></button>
		</div>
		<%-- <label class='checkbox inline'><input type='checkbox' id='checkAll'/><fmt:message key='writebacklist.checkall'/></label> --%>
		<!-- <div id="writeBackList">
		</div> -->
	</div>
	
	
	<div id="auditTraceDialog" title="<fmt:message key='audit.trace' />" style="text-align:left;" >
		<table id="audit_trace_information"></table>
	</div>
	
	<div id="chartDialog" title="<fmt:message key='result.info' />" style="text-align:left;" >
		<div id="singleChartPanel" style="width:640px;height:320px">></div>
		<table id="chartTongji" class="table">
			<tbody>
			<tr><th><fmt:message key='tongji.min' /></th><td><span id="tongji_min"></span></td><th><fmt:message key='tongji.max'/></th><td><span id="tongji_max"></span></td><th><fmt:message key='tongji.mid' /></th><td><span id="tongji_mid"></span></td></tr>
			<tr><th><fmt:message key='tongji.ave'/></th><td><span id="tongji_ave"></span></td><th><fmt:message key='tongji.sd' /></th><td><span id="tongji_sd"></span></td><th><fmt:message key='tongji.cov'/></th><td><span id="tongji_cov"></span></td></tr>
			</tbody>
		</table>
		<div id="hmInfo">
		</div>
	</div>
	
	<div id="taskListDialog" title="<fmt:message key='usertask.title' />" style="text-align:left;">
		<table id="tasklist_table" class="table">
			
		</table>
	</div>
	
	<div id="sopDetailDialog" style="text-align:left;">
		<div id="sopDetailHtml"></div>
	</div>
</div>