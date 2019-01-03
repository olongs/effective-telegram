<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String webCtx = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<script type="text/javascript" src="<%=webCtx %>/easyui/jquery.min.js"></script>
<script type="text/javascript" src="<%=webCtx %>/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=webCtx %>/easyui/easyui-lang-zh_CN.js"></script>
<link rel="stylesheet" href="<%=webCtx %>/easyui/themes/default/easyui.css"/>
<link rel="stylesheet" href="<%=webCtx %>/easyui/themes/icon.css"/>
<link rel="stylesheet" href="<%=webCtx %>/js/login.css"/>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登录</title>
<script>
function login(){
	var frmdata = $('#loginForm').serialize();
	$.post('/login',frmdata,function(data){
		if(data == true)
		{
			window.open('/main','_self');
		}
	})
}
</script>
</head>

 <body>
 	<div class="second_body">
    	<form id="loginForm" action="<%=webCtx %>/login" method="post">
        	<div class="logo"></div>
            <div class="title-zh">企业信息管理系统</div>
            <div class="title-en" style="">Enterprise Information Manage System</div>
            <div class="message" data-bind="html:message"></div>
            <table border="0" style="width:300px;">
            	<tr>
                	<td style="white-space:nowrap; padding-bottom: 5px;width:55px;">用户名：</td>
                    <td colspan="2">
                    <input type="text" id="loginId" name="loginId" class="login" 
                   	 value="${cookie.loginId.value}" data-bind="value:form.userCode" />
                   	 </td>
                </tr>
                <tr>
                    <td class="lable" style="white-space:nowrap; letter-spacing: 0.5em; vertical-align: middle">密码：</td>
                    <td colspan="2">
                    <input type="password" id="pwd" name="pwd" class="login"
                    	 value="${cookie.pwd.value}" data-bind="value:form.password" />
                    </td>
                </tr>
                <tr>
                    <td></td>
                    <td colspan="2"><input type="checkbox" data-bind="checked:form.remember" /><span>系统记住我</span></td>
                </tr>
                <tr>
                    <td colspan="3" style="text-align:center">
                        <input type="button" onclick="login()" value="登录" class="login_button" />
                        <input type="button" value="重置" class="reset_botton" data-bind="click:resetClick" />
                    </td>
                </tr>
            </table>
        </form>
    </div>

</html>