<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp" %>
<head>
    <title><fmt:message key="login.title"/></title>
    <style>
        body{margin:0;font:12px/1.8 Tahoma,Geneva,'\5B8B\4F53';color:#333;background:white;}
        .wrap{position:relative;z-index:0;height:600px;}
        .banner-show{position:absolute;top:0;left:0;z-index:0;width:100%;min-width:980px;height:600px;overflow:hidden;}
        .banner-show .cell{position:absolute;top:0;left:0;width:100%;overflow:hidden;background-repeat:repeat-x;background-position:center top;}
        .banner-show .cell .con{position:relative;height:600px;background-repeat:no-repeat;background-position:center top;}
        .bns-01{background-image:url(styles/images/mid_banner/banner_01_repeat.png);}
        .bns-01 .con{background-image:url(styles/images/mid_banner/banner_01.png?v=201406241538);}
        .bns-02{background-image:url(styles/images/mid_banner/banner_02_repeat.gif?v=201406241538);}
        .bns-02 .con{background-image:url(styles/images/mid_banner/banner_02.gif?v=201406241538);}
        .bns-03{background-image:url(styles/images/mid_banner/banner_03_repeat.png?v=201406241538);}
        .bns-03 .con{background-image:url(styles/images/mid_banner/banner_03.png?v=201406241538);}
        .banner-link{position:absolute;top:100px;left:50%;width:400px;height:400px;margin-left:-430px;}
        .banner-link i{display:none;}
        .banner-control{position:absolute;top:0;left:50%;width:980px;margin-left:-490px;}
        .banner-control a{position:absolute;top:258px;width:30px;height:30px;line-height:10;overflow:hidden;background-image:url(styles/images/icon_control.png?v=201406241538);background-repeat:no-repeat;background-repeat:no-repeat;}
        .banner-control a.left{left:-55px;background-position:0 0;}
        .banner-control a.left:hover{background-position:0 -30px;}
        .banner-control a.right{right:-55px;background-position:-30px 0;}
        .banner-control a.right:hover{background-position:-30px -30px;}
        .container{position:relative;top:600px;z-index:2;width:980px;height:0;margin:auto;}
        .register-box{position:absolute;top:-550px;right:0;width:396px;height:420px;background:white;border-radius:3px;background:rgba(255, 255, 255, 0.95);}
        .reg-tab span:first-child{border-top-left-radius:3px;}
        .reg-tab span:last-child{border-top-right-radius:3px;}
        .reg-slogan{height:50px;line-height:50px;text-align:center;font-size:16px;}
        .reg-form{width:328px;height:280px;margin:auto;}
        .reg-form .cell{position:relative;height:40px;margin-bottom:22px;zoom:1;}
        .reg-form .cell label{position:absolute;top:0;left:0;z-index:1;padding:0 12px;line-height:40px;font-size:14px;color:#999;font-weight:bold;cursor:text;}
        .reg-form .cell input{position:absolute;top:0;left:0;width:328px;padding:7px 11px;font-size:16px;background:none;}
        .reg-form .vcode input{width:110px;}
        .reg-form .vcode img{position:absolute;top:0;left:144px;width:110px;height:40px;}
        .reg-form .vcode span{position:absolute;top:0;left:264px;line-height:40px;font-size:14px;}
        .reg-form .user-agreement input{float:left;width:14px;height:14px;margin:1px 5px 0 0;}
        .reg-form .bottom .button{display:block;border-radius:3px;}
        .social-or-login :first-child{background: #f0f0f0 !important;}
        .input-icon>.ace-icon{line-height: 44px;}
    </style>

</head>
<body id="login">
<div class="wrap">
    <div class="banner-show" id="js_ban_content">
        <div class="cell bns-01" style="display: block;">
            <div class="con"> </div>
        </div>
        <div class="cell bns-02" style="display: none;">
            <div class="con"> <a href="#_" target="_blank" class="banner-link"> <i>智慧实验室</i></a> </div>
        </div>
        <div class="cell bns-03" style="display: none;">
            <div class="con"> <a href="#_" target="_blank" class="banner-link"> <i>智慧实验室</i></a> </div>
        </div>
    </div>
    <div class="banner-control" id="js_ban_button_box"> <a href="javascript:;" class="left">左</a> <a href="javascript:;" class="right">右</a> </div>
    <script type="text/javascript">
        (function () {
            var defaultInd = 0;
            var list = $('#js_ban_content').children();
            var count = 0;
            var change = function(newInd, callback){
                if(count) return;
                count = 2;
                $(list[defaultInd]).fadeOut(400, function(){
                    count--;
                    if(count <= 0){
                        if(start.timer) window.clearTimeout(start.timer);
                        callback && callback();
                    }
                });
                $(list[newInd]).fadeIn(400, function(){
                    defaultInd = newInd;
                    count--;
                    if(count <= 0){
                        if(start.timer) window.clearTimeout(start.timer);
                        callback && callback();
                    }
                });
            };

            var next = function(callback){
                var newInd = defaultInd + 1;
                if(newInd >= list.length){
                    newInd = 0;
                }
                change(newInd, callback);
            };

            var start = function(){
                if(start.timer) window.clearTimeout(start.timer);
                start.timer = window.setTimeout(function(){
                    next(function(){
                        start();
                    });
                }, 8000);
            };

            start();

            $('#js_ban_button_box').on('click', 'a', function(){
                var btn = $(this);
                if(btn.hasClass('right')){
                    //next
                    next(function(){
                        start();
                    });
                }
                else{
                    //prev
                    var newInd = defaultInd - 1;
                    if(newInd < 0){
                        newInd = list.length - 1;
                    }
                    change(newInd, function(){
                        start();
                    });
                }
                return false;
            });

        })();
    </script>
    <div class="container">
        <div class="register-box">
            <div class="reg-slogan">
                <h2>
                    <i class="ace-icon fa fa-leaf green"></i>
                    <span class="grey"><fmt:message key="webapp.name"/></span>
                </h2>
            </div>
            <div class="reg-form" id="js-form-mobile">
                <form method="post" id="loginForm" action="<c:url value='/j_security_check'/>"
                      onsubmit="saveUsername(this);return validateForm(this)" class="form-signin" autocomplete="off">
                    <div class="cell">
                        <span class="block input-icon input-icon-right">
                            <input type="text" name="j_username" id="j_username"
                                   placeholder="<fmt:message key="label.username"/>" required tabindex="1">
                            <i class="ace-icon fa fa-user"></i>
                        </span>
                    </div>
                    <div class="cell">
                       <span class="block input-icon input-icon-right">
                        <input type="password" name="j_password" id="j_password" tabindex="2"
                               placeholder="<fmt:message key="label.password"/>" required>
                        <i class="ace-icon fa fa-lock"></i>
                       </span>
                    </div>
                    <div class="cell">
                        <label class="inline">
                            <c:if test="${appConfig['rememberMeEnabled']}">
                                <input class="ace" type="checkbox" name="_spring_security_remember_me" id="rememberMe" tabindex="3"/>
                                <span class="lbl">&nbsp;&nbsp;<fmt:message key="login.rememberMe"/></span>
                            </c:if>
                        </label>
                        <button type="submit" class="width-35 pull-right btn btn-sm btn-primary">
                            <i class="ace-icon fa fa-key"></i>
                            <span class="bigger-110"><fmt:message key='button.login'/></span>
                        </button>
                    </div>
                </form>
                <div class="social-or-login center" style="clear: both" >
                    <span class="bigger-110"></span>

                </div>
                <style>
                    .infobox{
                        width: 130px;
                        border:none;
                        background:none;
                    }
                    .infobox>.infobox-data{
                        min-width: 60px;
                        padding-left:2px;
                    }
                    </style>
                <div class="social-login center">
                    <div class="infobox infobox-green" >
                        <div class="infobox-icon">
                            <i class="ace-icon fa fa-comments"></i>
                        </div>
                        <div class="infobox-data">
                            <a href="<c:url value="/pb/pbcx"/>" data-target="#signup-box" class="user-signup-link">
                                排班查询
                            </a>
                        </div>
                    </div>
                    <div class="infobox infobox-blue" >
                        <div class="infobox-icon">
                            <i class="ace-icon fa fa-cog"></i>
                        </div>
                        <div class="infobox-data">
                            <a href="<c:url value="/signup"/>" data-target="#signup-box" class="user-signup-link">
                                用户注册
                            </a>
                        </div>
                    </div>
                </div>
                <div class="toolbar center">
                    <c:if test="${param.error != null}">
                        <div class="alert alert-danger alert-dismissable">
                            <fmt:message key="errors.password.mismatch"/>
                        </div>
                    </c:if>
                </div>

            </div>
        </div>
    </div>


</div>
<div class="footer">
    <div class="footer-inner" style="left: 0px;">
        <div class="footer-content">
				 <span class="">
        	<fmt:message key="project.name"/> |
        	<fmt:message key="webapp.version"/>
            <c:if test="${pageContext.request.remoteUser != null}">
                | <fmt:message key="user.status"/> ${pageContext.request.remoteUser}
            </c:if>
            | <a target="_blank" href="<fmt:message key="update.url"/>"><fmt:message key="update.name"/></a>
       	    | <a target="_blank" href="<fmt:message key="wsdjk.url"/>"><fmt:message key="wsdjk.name"/></a>
   		</span>
   		<span style="float: right;">
       		 &copy; <fmt:message key="copyright.year"/> <a target="_blank" href="<fmt:message key="company.url"/>"><fmt:message key="company.name"/></a>
        </span>
        </div>
    </div>
</div>

<c:set var="scripts" scope="request">
<%@ include file="/scripts/login.js"%>
</c:set>
</body>
<script type="text/javascript" >
    $(function(){
        var cHeight= window.document.body.clientHeight;

    })

</script>