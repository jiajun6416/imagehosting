<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
 <base href="<%=basePath%>" />
 <script type="">
	var basePath = '${basePath}';
	var user = '${sessionScope.session_user_key.username}';
</script>
<link rel="icon" href="static/login/image/login.png" type="image/x-icon" />
<link rel="shortcut icon" href="static/login/image/login.png" type="image/x-icon" />

 <LINK href="static/css/common.css?v=1603010" rel="stylesheet" type="text/css" />
 <LINK href="static/css/useralbumphoto.css" rel="stylesheet" type="text/css" />
 <LINK href="static/css/userphoto.css" rel="stylesheet" type="text/css" />
 <LINK href="static/css/userupload.css" rel="stylesheet" type="text/css">
 <SCRIPT language="javascript" src="static/js/jquery-1.7.2.min.js" type="text/javascript"></SCRIPT>
 <SCRIPT language="javascript" src="static/js/drag.js" type="text/javascript"></SCRIPT>
 <SCRIPT language="javascript" src="static/js/jquery.dialog.js" type="text/javascript"></SCRIPT>
 <SCRIPT language="javascript" src="static/js/common.js" type="text/javascript"></SCRIPT>
 <SCRIPT language="javascript" src="static/js/getconnecting.js" type="text/javascript"></SCRIPT>
 <SCRIPT language="javascript" src="static/js/usercommon.js" type="text/javascript"></SCRIPT>
 <SCRIPT language="javascript" src="static/js/rotation.js" type="text/javascript"></SCRIPT>
