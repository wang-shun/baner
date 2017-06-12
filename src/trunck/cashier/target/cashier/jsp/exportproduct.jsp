<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>exportpay</title>
<%  
String path = request.getContextPath();  
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";  
%> 
<base href="<%=basePath%>">  
<link rel="stylesheet" type="text/css" href="css/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="css/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="css/bootstramp/buttons.css">
<script type="text/javascript" src="js/easyui/jquery.min.js"></script>
<script type="text/javascript" src="js/easyui/jquery.easyui.min.js"></script>
</head>
<body style="background-color: #F0F0F0; margin-left: 170px">
<h2>${luaguageBean.cbpay }</h2>
	<div class="demo-info" style="margin-bottom: 10px; background-color: #F0F0F0; width: 85%">
		<div class="demo-tip icon-tip"></div>
		<div style="display: inline;">${luaguageBean.cash }</div>
		<div style="display: inline; text-align: right; float: right">
			<font style="color: red; font-size: 30px"><fmt:formatNumber value="23" type="currency" pattern="#.00"/></font>
		</div>
	</div>
	<div style="margin: 20px 0 10px 0;"></div>
	<div id="p" class="easyui-panel" title="${luaguageBean.orderinfo }" iconCls="icon-ok"
		style="overflow: auto; width: 90%">
		<table style="width: 90%; margin-left: 20px; height: 200px">
			<tr>
				<td class="f2" width="20%">${luaguageBean.merchantname }:</td>
				<td width="30%">${merchantOrderMsg.body.merchantName }</td>
				<td class="f2" width="20%">${luaguageBean.productname }:</td>
				<td>${merchantOrderMsg.body.productName }</td>
			</tr>
			<tr>
				<td class="f2">${luaguageBean.ordermoney }:</td>
				<td><fmt:formatNumber value="${merchantOrderMsg.body.totalAmount }" type="currency" pattern="#.00"/>￥</td>
				<td class="f2">${luaguageBean.ordercurrency }:</td>
				<td>CNY</td>
			</tr>
			<tr>
				<td class="f2">${luaguageBean.paymoney }:</td>
				<td><fmt:formatNumber value="23" type="currency" pattern="#.00"/></td>
				<td class="f2">${luaguageBean.paycurrency }:</td>
				<td>USD</td>
			</tr>
			<tr>
				<td class="f2">${luaguageBean.orderdate }:</td>
				<td>${merchantOrderMsg.body.orderDate } ${merchantOrderMsg.body.orderTime }</td>
				<td class="f2">${luaguageBean.orderId }:</td>
				<td>${merchantOrderMsg.body.orderId }</td>
			</tr>
		</table>
	</div>
	<div class="easyui-panel" title="${luaguageBean.payinfo }" iconCls="icon-man" style="width: 90%; padding: 10px;" id="bankinfo">
		<form id="pay" action="login" method="post">
			<table style="width: 90%; margin-left: 20px; height: 200px">
				<tr>
					<td class="f2" width="30%">${luaguageBean.cardId }:</td>
					<td><input id="bankCard" name="bankCard"
							class="easyui-validatebox textbox" required="required"
							validType="bankcard[16,19]" value=""
							style="height: 25px; width: 250px" />
					</td>
				</tr>
				<tr>
					<td class="f2">${luaguageBean.password }:</td>
					<td><input id="password" name="password" type="password"
						class="easyui-validatebox" required="required" validType="elength[6]" value="" style="height: 20px; width: 100px" />
				</tr>
				<tr>
					<td colspan="4" style="text-align: right">
					<a href="javascript:void(0)" onclick="pay()" class="button button-primary button-rounded">${luaguageBean.pay }</a></td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>
<style scoped>
.f1 {
	width: 200px;
}

.f2 {
	text-align: right;
}

.f3 {
	text-align: left;
}
</style>
<script>
$.extend($.fn.validatebox.defaults.rules, {
	bankcard : {
		validator : function(value, param) {
			var rex = /^\d+$/;
			return value.length >= param[0]
					&& value.length <= param[1] && rex.test(value);
		},
		message : '输入{0}到{1}位数字'
	},
	minLength : {
		validator : function(value, param) {
			return value.length >= param[0];
		},
		message : '请输入最少{0}位数字'
	},
	elength : {
		validator : function(value, param) {
			return value.length == param[0];
		},
		message : '请输入6位密码'
	},
});
var pay=function(){
	if(!$('#bankCard').validatebox("isValid")){
		$('#bankCard').click();
	}else if(!$('#password').validatebox("isValid")){
		$('#password').click();
	}else{
		location.href="success?pageReturnUrl="+encodeURI("${merchantOrderMsg.body.pageReturnUrl}");
	}
}
</script>