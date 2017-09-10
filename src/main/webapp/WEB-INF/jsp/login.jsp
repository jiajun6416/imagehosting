<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="UTF-8" />
<title>登陆-WELCOME</title>
 <%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
  <base href="<%=basePath%>" />
<!-- <base href="/image/static/login/"> -->
<link rel="icon" href="static/login/icon/login.png" type="image/x-icon" />
<link rel="shortcut icon" href="static/login/icon/login.png" type="image/x-icon" />

<link rel="stylesheet" type="text/css" href="static/login/css/reset.css">
<link rel="stylesheet" type="text/css" href="static/login/css/structure.css">
<style type="text/css">
</style>
</head>

<body>
<form class="box login" action="javascript:void(0)">
	<fieldset class="boxBody">
	  <label>Username</label>
	  <input type="text" tabindex="1" id="username" name="username" placeholder="username or email" required maxlength='32' minlength='3'>
	  <label>Password<span class="message"></span></label>
	  <input type="password" tabindex="2" id="password" name="password" required placeholder="password" maxlength='32' minlength='3'>
	</fieldset>
	<footer>
	  <label><input type="checkbox" tabindex="3" id="rememberme" >Keep me logged in</label>
	  <input type="submit" class="btnLogin" value="Login" tabindex="4" onclick="login()">
	</footer>
</form>
<footer id="main">
		<!-- Copyright @1993-©2017 -->
</footer>
</body>
<script type="text/javascript" src="static/login/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="static/login/js/jquery.md5.js"></script>
<script type="text/javascript" src="static/login/js/jquery.cookie.js"></script>
<script type="text/javascript">

if(window != top) {
	top.window.href = location.href;
}
$("#username").bind("focus", function() {
	$(".message").html("");
})
$("#password").bind("focus", function() {
	$(".message").html("");	
})

function login() {
	var username = $("#username").val();
	var password_text = $("#password").val();
	if(! check(username, password_text)) {
		return ;
	}
	$(".btnLogin").blur();
	var password = $.md5(password_text);
	var rememberme = false;
	if($("#rememberme").prop("checked")) {
		rememberme = true;
	}
	/* console.log({"username":username, "password":password, "rememberme":rememberme}); */
	$.ajax({
		type:'POST',
		url: 'doLogin',
		data: {"username":username, "password":password, "rememberme":rememberme},
		dataType: 'json',
		cache: false,
		success: function(data){
			if(data.code == 200) {
				//更新cookie
				updateCookie();
				location.href="index";
			} else {
				$(".message").html("用户名或者密码错误");
				setTimeout(function() {
					$(".message").html("");
				},2000)
			}
		},
		error: function() {
		}
	});
}

var nameMinLength = $("#username").attr("minlength");
var nameMaxLength = $("#username").attr("maxlength");
var pwMinLength = $("#password").attr("minlength");
var pwMaxLength = $("#password").attr("maxlength");

function check(username, password) {
	if(username != '' && username.length>=nameMinLength && username.length <= nameMaxLength
			&& password != '' && password.length>=pwMinLength && password.length <= pwMaxLength) {
		return true;
	}
	return false;
}

$(function() {
	var username = $.cookie('username');
	var password = $.cookie('password');
	if ( typeof(password) != "undefined" && password != null && password != '') {
		$("#username").val(username);
		$("#password").val(password);
		$("#rememberme").attr("checked", true);
	} else if(typeof(username) != "undefined" && username != null && username != ''){
		$("#username").val(username);
		$("#password").focus();
	} else {
		$("#username").focus();
	}
});

function updateCookie() {
	if ($("#rememberme").prop("checked")) {
		$.cookie('username', $("#username").val(), {
			expires : 7, path:'/'
		});
		$.cookie('password', $("#password").val(), {
			expires : 7,path:'/'
		});
	} else { 
		$.cookie('username', $("#username").val(), {
			expires : 7, path:'/'
		});
		//设置为0立即删除, 设置为-1表示会话级别
		$.cookie('password',null, {
			expires : 0, path:'/'
		});
	}
}
</script>
</html>
