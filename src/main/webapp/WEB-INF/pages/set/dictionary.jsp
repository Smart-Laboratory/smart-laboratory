<%--
  Created by IntelliJ IDEA.
  User: zhou
  Date: 2016/5/17
  Time: 16:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp" %>
<style>
.laftnav{
    background: #ffffff;
    border: 1px solid #D9D9D9;
}
.lazy_header{
    height: 35px;
    background: #F7F7F7 !important;
    border-bottom: 1px solid #D9D9D9;
    line-height: 35px
}
.lazy-list-title {
    font-size: 14px;
    max-width: 100px;
    display: inline-block;
    text-overflow: ellipsis;
    -o-text-overflow: ellipsis;
    white-space: nowrap;
    overflow: hidden;
    padding-left: 5px;
}
.no-skin .nav-list>li.active:after{
    border: 1px;
}
.no-skin .nav-list>li>a{
    padding-left:20px;
}
ul.nav{
   margin-left:0px;
}
.nav-pills>li>a{
    border-radius: 0px;
}
</style>
<div class="row">
    <div class="col-xs-2 treelist">
        <div class="laftnav">
            <div class="lazy_header">
                <span class="lazy-list-title">
                <i class="fa fa-bars"></i>
                <span class="tip" style="cursor:pointer;">全部类别</span>
                </span>
            </div>
            <ul class="nav nav-pills nav-stacked" id="ullist">
            </ul>
        </div>
    </div>
    <div class="col-xs-10">
        GRID LIST
    </div>
</div>
<div style="clear: both"></div>
<script>
  $(function() {
      /*
      * 生成字典类别列表
      * add by zcw 2016-05-19
      * */
      $.post('../set/dictionaryType/getList', function(data){
          if(data){
              var ul = $('#ullist')
              for (i=0; i< data.length; i++) {
                  var li = $('<li></li>');
                  li.html('<a href="#_" title="'+ data [i].name+'">'+data[i].name+'</a>');
                  li.bind('click',function(){
                      ul.children('li').removeClass('active');
                      $(this).addClass("active")
                  })
                  ul.append(li);
              }
          }
      })
})
</script>
