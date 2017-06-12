<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>success</title>
</head>
<body style="background-color: #F0F0F0; margin-left: 170px">
支付成功<br/><font id="time"></font>
<form  method="post">
	<input type="hidden" value="<%=request.getParameter("param") %>" name="param"/>
	<input type="button" value="返回商户" onclick="doUpdate()"/>
</form>
</body>
</html>
<script>
	var secs = 3; //倒计时的秒数 
	function load(sec){
			document.getElementById('time').innerHTML= '将在'+sec+'秒后自动跳转到主页' ;
			if(sec==0){
				doUpdate();
			}else{
				window.setTimeout('load('+(sec-1)+')',1000);
			}
	}
	function doUpdate() 
	{ 	document.forms[0].action = '${param.pageReturnUrl}';
		document.forms[0].submit();
	}
	load(secs);
</script>