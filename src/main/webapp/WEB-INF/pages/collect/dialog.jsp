<div id="dialogs" style="display:none;">
<div id="evaluateDialog" title="<fmt:message key='evaluate.button' />" style="text-align:left;" >
	<div class="pItem">
		<span class="pLabel"><fmt:message key="collect.bamc" />:</span>
		<span class="pText"><b id="e_bamc"></b></span>
		<span class="pLabel" style="margin-left:75px;"><fmt:message key="evaluate.collector" />:</span>
		<span class="pText"><b id="e_collector"></b></span>
	</div>
	<h5 id="evaluate"><fmt:message key='collect.evaluate'/></h5>
	<textarea id="evaluateText" rows="1" style="width:280px;height:60px;"></textarea>
	<div style="margin-top:10px;">
		<button id="evaluateConfirm" class="btn btn-info" ><fmt:message key='evaluate.button' /></button>
		<button id="evaluateCancel" class="btn" ><fmt:message key='button.cancel' /></button>
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
<div id="typeDialog" title="<fmt:message key='collect.type' />" style="text-align:left;" >
	<c:forEach var="type" items="${typeList}" varStatus="status">
		<div style="float:left;width:145px;">
			<input type="checkbox" name="checkbox" value='<c:out value="${type}"/>'>
			<span class="text"><c:out value="${type}"/></span>
		</div>
	</c:forEach>
	<div style="float:left;margin-top:10px;width:400px;">
		<button id="typeConfirm" class="btn btn-info" style="width:60px;font-size:12px;padding:2px;"><fmt:message key='button.confirm' /></button>
		<button id="typeCancel" class="btn" style="width:60px;font-size:12px;padding:2px;"><fmt:message key='button.cancel' /></button>
	</div>
</div>

<div id="uploadDialog" title="<fmt:message key='upload.title' />" style="text-align:left;" >
	    <%-- <h5><fmt:message key='image.name'/></h5>
		<input id="image_name" type="text" class="span4"/> --%>
		<fieldset style="width:95%; margin-left:4px;">
		<legend style="margin-top:3px;"><fmt:message key='upload.picture' /></legend>
			<div>
		    <button class="btn btn-info" onclick="createInput();"><fmt:message key='add.point'/></button>
		    <button class="btn btn-success" onclick="ajaxFileUpload()"><fmt:message key='upload.title'/></button>
			<div id="more" class="form-inline">
			</div>
			</div>
		</fieldset>
	    <h5><fmt:message key='image.description'/></h5>
	    <textarea id="image_note" class="span4" rows="4"></textarea>
	    <div id="galleria"></div>
</div>

	
<div id="imageDialog" title="<fmt:message key='knowledge.check10' />" style="text-align:left;" >
	<div id='showGalleria'></div>
</div>
</div>