<%--
  Created by IntelliJ IDEA.
  User: zcw
  Date: 2016/10/17
  Time: 17:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp" %>
<html>
<head>
    <title>Title</title>
    <script type="text/javascript">
        function sendReport() {
            $.post('../manage/sendreport/sendHisReport',{barcode:$('#barcode').val()||''},function(data){
               if(data.sucess==1){
                   alert("重新发送报告成功！");
               }
            })
        }
    </script>
</head>
<body>
<div class="row" style="margin-top: 5px;">
    <div class="col-sm-4">
        <div class="input-group">
            <span class="input-group-addon">条码号</span>
            <input class="form-control" value="" id="barcode" name="barcode" type="text">
        </div>
    </div>
    <div class="col-sm-3">
        <div class="input-group col-sm-12">
            <span class="input-group-btn ">
                 <button type="button" class="btn btn-sm btn-primary col-sm-4" title="发送报告" onclick="sendReport()">
                    <i class="ace-icon fa fa-fire bigger-110"></i>
                    发送报告
                </button>
            </span>
        </div>
    </div>

</div>
</body>
</html>


