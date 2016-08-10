<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<script type="text/javascript" src="../scripts/lis/audit/dialog.js"></script>

<div id="auditDialog" style="text-align:left;display:none;">
	<div id="searchPanel" align="left">
		<div style="display:none;" id="isContinued">0</div>
		<div style="border-bottom:1px solid #E1E1E1;">
			<input id="auditText2" type="text" style="margin:5px;height:34px;" placeholder="20120829SGB001-999"/>
			<button id="auditBtn2" class="btn btn-sm btn-info" style="margin:5px;"><fmt:message key='audit'/></button>
			<input type="checkbox" id="auditAllbtn" style="margin-top:-2px;margin-right:5px;"><fmt:message key='reaudit'/>
		</div>
	</div>
</div>

<div id="auditPrint" align="left" style="display:none;">
	<button class="btn btn-success" onclick="savePDF();document.getElementById('iframe_print').contentWindow.print();"><fmt:message key="audit.print" /></button>
	<div id="printFrame"></div>
	<button class="btn btn-success" onclick="savePDF();document.getElementById('iframe_print').contentWindow.print();"><fmt:message key="audit.print" /></button>
</div>

<div id="samplePrint" align="left" style="display:none;">
	<button class="btn btn-success" onclick="document.getElementById('iframe_sample').contentWindow.print();"><fmt:message key="audit.print" /></button>
	<div id="samplePrintFrame" style="height:480px;">
	</div>
	<button class="btn btn-success" onclick="document.getElementById('iframe_sample').contentWindow.print();"><fmt:message key="audit.print" /></button>
</div>

<div id="dialog" align="left" style="display:none;">
</div>

<div id="addTestResultDialog" style="text-align:left;display:none;">
	<div class="form-inline">
		<span class="form-control" style=""><fmt:message key='add.single'/></span>
		<input  id="searchProject" class="form-control" style="width:250px;" placeholder="<fmt:message key='add.single'/>">
	</div>
	
	<div class="form-inline">
		<input id="lastprofile" type="hidden" value="<c:out value='${lastprofle}'/>"  />
		<!-- <select id="profileList" class="form-control" style="width:280px;"></select> -->
		<input  id="packages" class="form-control" style="width:330px;" placeholder="<fmt:message key='button.add.profile'/>">
		<!-- <button id="addProfileBtn" class="btn btn-info"><fmt:message key="button.add.profile" /></button> -->
		<button id="deleteAllTest" class="btn btn-danger"><fmt:message key="button.empty" /></button>
	</div>
	<fieldset>
	<legend><fmt:message key="index.message" /></legend>
	<div id="addTestList">
	
	</div>
	</fieldset>
</div>

<div id="addResultDialog" style="text-align:left;display:none;">
	<form id="addResultForm" class="form-horizontal" action="<c:url value='../audit/addResult'/>" method="post">
	<div class="form-inline" style="padding:10px;">
			<div class="control-group">
				<label class="control-label form-control" ><fmt:message key="addResult.number" /> : </label>
					<input type="text" class="span3 form-control" id="span_docNo" disabled></input>
			</div>
			<div class="control-group">
				<label class="control-label form-control"><fmt:message key="addResult.result" /> : </label>
					<input id="result_result" type="text" class="span3 form-control" name="result"/>
			</div>
			<div class="control-group ">
				<label class="control-label form-control"><fmt:message key="addResult.content" /> : </label>
					<textarea id="result_content" class="span3 form-control" name="content"></textarea>
			</div>
			<div class="control-group">
				<div class="controls">
					<input id="result_docNo" name="docNo" type="hidden"></input>
					<button id="addResult" type="submit" class="btn btn-success" ><fmt:message key='button.submit' /></button>
					<button id="addCancel" class="btn btn-info" ><fmt:message key='button.cancel' /></button>
				</div>
			</div>
	</div>
	</form>
</div>

<div id="opStatusDialog" style="text-align:left;display:none;">
	<div class="col-sm-12">
		<h5 id="passNotes"></h5>
		<div id="selectNoteDiv" class="clearfix"></div>
		<h5 id="guide" style="border-top:1px solid #000000;padding-top:5px;"><b><fmt:message key='rule.guide'/></b></h5>
		<div id="guideDiv"></div>
		<div class="form-inline" style="border-top:1px solid #000000;padding-top:5px;">
			<label for="diseaseSelect"><fmt:message key='des.diseaseSelect' /></label>
			<input type="text" class="form-control" id="diseaseSelect" placeholder="<fmt:message key='placeholder.szm'/>"/>
			<label for="disease" style="margin-left:20px;"><fmt:message key='des.disease' /></label>
			<span type="text" id="disease" />
		</div>
		<div id="descriptionDiv"></div>
		<div style="border-top:1px solid #000000;margin-top:10px;">
			<h5 id="explainSelect"><b><fmt:message key='audit.infomation'/></b></h5>
			<div id="explainDiv"></div>
		</div>
		<h5 id="passNotes"><b><fmt:message key='audit.checkerOpinion'/></b></h5>
		<input type="hidden" id="checkId" />
		<textarea id="noteText" rows="3" class="form-control" style="width:100%;"></textarea>
		<input type="hidden" id="noteHidden" />
		<div id="historyChart" style="float:left;width:100%;margin-top:5px;padding-top:5px;border-top:1px solid #000000;"></div>
		<p><b><fmt:message key="sample.history.chart"/></b></p>
		<div id="chartList"></div>
	</div>
</div>

<div id="collectDialog" style="text-align:left;display:none;" >
	<div class="col-sm-12">
		<h5 id="bamc"><b><fmt:message key='collect.bamc'/></b></h5>
		<input id="collect_bamc" type="text" class="span3"/>
		<h5 id="collectType"><b><fmt:message key='collect.type'/></b></h5>
		<input id="collect_type" type="text" class="span3"/>
		<h5 id="evaluate"><b><fmt:message key='collect.evaluate'/></b></h5>
		<textarea id="collectText" rows="1" style="width:280px;height:60px;"></textarea>
		<div style="margin-top:10px;">
			<button id="collectConfirm" class="btn btn-sm btn-info" ><fmt:message key='collect.button' /></button>
			<button id="collectCancel" class="btn btn-sm" ><fmt:message key='button.cancel' /></button>
		</div>
	</div>
</div>

<div id="uploadDialog" style="text-align:left;display:none;" >
    <%-- <h5><fmt:message key='image.name'/></h5>
	<input id="image_name" type="text" class="span4"/> --%>
	<fieldset style="width:95%; margin-left:4px;">
	<legend style="margin-top:3px;"><fmt:message key='upload.picture' /></legend>
		<div>
	    <button class="btn btn-sm btn-info" onclick="createInput();"><fmt:message key='add.point'/></button>
	    <button class="btn btn-sm btn-success" onclick="ajaxFileUpload()"><fmt:message key='upload.title'/></button>
		<div id="more" style="float:left;"></div>
		</div>
	</fieldset>
    <h5><fmt:message key='image.description'/></h5>
    <textarea id="image_note" class="span4" rows="4"></textarea>
    <div id="galleria"></div>
</div>

<div id="imageDialog" style="text-align:left;display:none;" >
	<div id="showGalleria"></div>
</div>

<div id="codeSetDialog" style="text-align:left;display:none;" >
	<div style="margin:10px;">
		<button id="controlAuditBtn" class="btn btn-sm btn-info">
		<c:choose>
			<c:when test="${activeAuto}">
				<fmt:message key='audit.stop'/>
			</c:when>
			<c:otherwise>
				<fmt:message key='audit.start'/>
			</c:otherwise>
		</c:choose>
		</button>
		<blockquote><small id="autoAuditNote" ></small></blockquote>
	</div>
	<div id="codeSetDiv" style="margin:10px;">
		<c:forEach var="code" items="${codeList}">
		<div class="codeItem col-sm-12" style="margin-bottom:5px;">
			<div class="col-sm-3">
				<label class="inline">
					<input type="checkbox" class="codeCheck input-ctl" <c:if test="${code.active == true}">checked</c:if>>
					<span class="codeText"><c:out value="${code.labCode}" /></span>
				</label>
			</div>
			<div class="col-sm-9 scopeDiv" <c:if test="${code.active == false}">style="display:none;"</c:if>>
				<input class="input-ctl val-lo" style="color:#000000; width:80px;" type="text" value="${code.lo}" /> 
				<input type="text" style="width:25px;" value=" - " disabled/> 
				<input class="input-ctl val-hi" style="color:#000000; width:80px;" type="text" value="${code.hi}" /> 
			</div>
		</div>
		</c:forEach>
	</div>

</div>

<div id="allNeedWriteBackDialog" style="text-align:left;display:none;" >
	<table id="need_writeback_table" class="table">
		
	</table>
</div>

<div id="statisticDialog" style="text-align:left;display:none;" >
	<div class='col-sm-12' style="margin-top:10px;">
		<input id="statistic_code" class="form-control" style="float:left;width:150px;" type="text" placeholder="<fmt:message key='placeholder.code'/>"/>
		<input id="statistic_from" class="form-control" style="float:left;width:150px;margin-left:8px;" type="text" placeholder="<fmt:message key='placeholder.from'/>"/>
		<input id="statistic_to" class="form-control" style="float:left;width:150px;margin-left:8px;" type="text" placeholder="<fmt:message key='placeholder.to'/>"/>
		<button id="statisticBtn" class="btn btn-sm btn-info" style="margin-left:8px;"><fmt:message key='button.statistic'/></button>
	</div>
	
	<div class='col-sm-12' style="font-size: 14px;margin-top:10px;">
		<table id="statistic_table"></table>
	</div>
	
</div>

<!-- 标本批量添加默认值 张晋南20160602 -->
<div id="batchAddResultsDialog" title="<fmt:message key='batch.add.results' />" style="text-align:left;display:none;" >
	<div >
		<select id="batchAddResults_statistic_packages"  style="float:left;width:220px" class="form-control" >
		</select>
		<input id="batchAddResults_statistic_date" onfocus="setDefaultValue()" class="form-control" style="float:left;width:100px;;margin-left:8px;" type="text" placeholder="<fmt:message key='default.date'/>"/>
		<input id="batchAddResults_statistic_code" onkeyup="this.value=this.value.toUpperCase()" class="form-control" style="float:left;width:100px;;margin-left:8px;" type="text" placeholder="<fmt:message key='placeholder.code'/>"/>
		<input id="batchAddResults_statistic_begin" class="form-control" style="float:left;width:80px;margin-left:8px;" type="text" placeholder="<fmt:message key='placeholder.from'/>"/>
		<input id="batchAddResults_statistic_end" class="form-control" style="float:left;width:80px;margin-left:8px;" type="text" placeholder="<fmt:message key='placeholder.to'/>"/>
		<button id="batchAddResults_statisticBtn_save" class="btn btn-info" style="margin-left:8px;">保存</button>
	</div>
	<div class="col-sm-12" style="margin-top:10px;" id="batchAddResults_statistic_table">
	</div>
</div>

<div id="writeBackPartDialog" style="text-align:left;display:none;" >
	<div class="row">
		<input id="writeBack_text" class="col-sm-4" type="text"/>
		<button id="writePartBtn" class="btn btn-sm btn-info"><fmt:message key='writeback'/></button>
	</div>
	<%-- <label class='checkbox inline'><input type='checkbox' id='checkAll'/><fmt:message key='writebacklist.checkall'/></label> --%>
</div>

<div id="chartDialog" style="text-align:left;display:none;" >
	<p class="alert alert-danger" id="chartAlert"><fmt:message key='result.chart.alert'/></p>
	<div id="singleChartPanel" style="width:640px;height:320px"></div>
	<table id="chartTongji" class="table">
		<tbody>
		<tr><th><fmt:message key='tongji.min' /></th><td><span id="tongji_min"></span></td><th><fmt:message key='tongji.max'/></th><td><span id="tongji_max"></span></td><th><fmt:message key='tongji.mid' /></th><td><span id="tongji_mid"></span></td></tr>
		<tr><th><fmt:message key='tongji.ave'/></th><td><span id="tongji_ave"></span></td><th><fmt:message key='tongji.sd' /></th><td><span id="tongji_sd"></span></td><th><fmt:message key='tongji.cov'/></th><td><span id="tongji_cov"></span></td></tr>
		</tbody>
	</table>
	<div id="hmInfo"></div>
</div>

<div id="sopDetailDialog" style="text-align:left;display:none;">
	<div id="sopDetailHtml"></div>
</div>

<div id="tatDialog" style="text-align:left;display:none;" >
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

<div id="testModifyDialog" title="<fmt:message key='audit.testModify' />" style="text-align:left; display:none;" >
	<table id="test_modify_information"></table>
</div>

<div id="auditTraceDialog" style="text-align:left;display:none;">
	<table class="table" id="audit_trace_information"></table>
</div>