<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
    <DIV class="header" style>
      <DIV class="header_logo">
        <A href="/">
          <IMG alt="贴图库Logo" src="static/image/logo11.png"></A>
      </DIV>
      <DIV class="header_user header_user_unsignin">
        <SPAN class="header_user_up">
          <a href="album" class="header_user_up_pc-3" style="display:block;">个人相册</a>
          <A href="upload/local" class="header_user_up_pc"  id="localImage">上传电脑图片</A>
          <A href="upload/network" id="networkImage">上传网络图片</A>
         </SPAN>
      </DIV>
		<DIV class="header_nav" style="display:none;">
			<span id="sub_hov2">
				<a href="javascript:;" style="padding-right:10px;">
					<b class="caret"></b></a>
				<div id="sub_nav2">
					<ul>
						<li>
							<A href="/about">关于我们</A></li>
						<li>
							<A href="/disclaimer">免责声明</A></li>
						<li>
							<A href="/contact">联系我们</A></li>
						<li>
							<A href="/notice">上传须知</A></li>
						<li>
							<A href="/service">服务条款</A></li>
					</ul>
				</div>
			</span>
		</DIV>
		<DIV class="header_nav" style="padding-right: 50px;">
			<span id="sub_hov" style="position:relative;">
				<a href="javascript:;">${sessionScope.session_user_key.username}
				<b class="caret"></b></a>
				<SPAN>&nbsp;&nbsp;</SPAN>
				<div id="sub_nav" style="right:0;top:30px;">
					<ul>
						<li><A href="album">个人相册</A></li>
						<li><A href="logout">退出</A></li>
					</ul>
				</div>
			</span>
		</DIV>
		<DIV class="header_nav">
			<a href="#" style="border:none;">
				<img src="static/image/userphoto.png" title="登陆用户" class="vipicon" /></a>
		</DIV>
	</DIV>
</body>
</html>