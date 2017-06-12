<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>success</title>
<link rel="stylesheet" type="text/css" href="css/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="css/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="css/bootstramp/buttons.css">
<script type="text/javascript" src="js/easyui/jquery.min.js"></script>
<script type="text/javascript" src="js/easyui/jquery.easyui.min.js"></script>
</head>
<body style="background-color: #F0F0F0; margin-left: 170px">
	<div style="margin: 30px 0 30px 0;"></div>
	<div style="margin-left: 150px">
		<div id="p" class="easyui-panel" title="${luaguageBean.payresult }" iconCls="icon-ok" style="maroverflow: auto; width: 60%;height: 200px;text-align: center;">
			<div style="margin: 8px 0px 8px 0px;"><font size="3">${luaguageBean.success }!</font></div>
			<form  method="post">
				<font size=3>${luaguageBean.returnpre }</font><font id="time" size="3"></font><font size=3> ${luaguageBean.returnsuf }</font>
				<input type="hidden" value="${params }" name="param"/>
				<a href="javascript:void(0)" class="button button-primary button-rounded button-small" onclick="doUpdate()">${luaguageBean.backmerchant }</a>
			</form>
		</div>
	</div>
</body>
<script type="text/javascript">
	var secs = 3; //倒计时的秒数 
	function load(sec){
			$("#time").html(sec);
			if(sec==0){
				doUpdate();
			}else{
				window.setTimeout('load('+(sec-1)+')',1000);
			}
	}
	function doUpdate() 
	{ 	document.forms[0].action = '${pageReturnUrl}';
		document.forms[0].submit();
	}
	load(secs);
</script>
</html>
