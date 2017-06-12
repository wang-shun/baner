<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel='icon' href='img/msds.ico' type='image/x-ico'/>
<title>error</title>
<link rel="stylesheet" type="text/css" href="css/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="css/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="css/bootstramp/buttons.css">
<script type="text/javascript" src="js/easyui/jquery.min.js"></script>
<script type="text/javascript" src="js/easyui/jquery.easyui.min.js"></script>
</head>
<body style="background-color: #F0F0F0; margin-left: 170px">
	<div style="margin: 70px 0 30px 0;"></div>
	<div style="margin-left: 150px">
		<div id="p" class="easyui-panel" title="订单错误" iconCls="icon-no" style="maroverflow: auto; width: 60%;height: 350px;text-align: center;">
			<div style="display:inline-block;vertical-align:middle;margin-top: 30px"><img src="img/error.png" style="height: 40px;width: 40px"></img></div>
			<div style="display:inline-block;vertical-align:middle;margin-top: 30px"><font size="5">${respMsg }</font></div>
			<div style="float: right ;margin: 110px 30px 0 0px">
				<form  method="post">
					<input type="hidden" value="${params }" name="param"/>
					<a href="javascript:void(0)" class="button button-primary button-rounded button-small" onclick="doUpdate()"><font size="3">返回商户</font></a>
				</form>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
	function doUpdate() 
	{ 	document.forms[0].action = '${pageReturnUrl}';
		document.forms[0].submit();
	}
</script>
</html>