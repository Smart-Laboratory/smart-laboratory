<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: zhou
  Date: 2016/5/13
  Time: 16:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="dialogs" style="display:none;" >
    <div id="addDialog" class="col-xs-12" class="container">
        <div class="row">
            <div class="col-xs-12">
            <form id="addResultForm" class="form-horizontal" action="<c:url value='../set/section/editSection'/>" method="post">
                <label class="col-xs-3 control-label no-padding-right" > 科室编号 </label>
                <input type="text" id="code" placeholder="Username" class="col-xs-9 col-sm-5">
                <label class="col-xs-3 control-label no-padding-right" > 科室名称 </label>
                <input type="text" id="name" placeholder="Username" class="col-xs-9 col-sm-5">
            </form>
            </div>
        </div>
    </div>

</div>
