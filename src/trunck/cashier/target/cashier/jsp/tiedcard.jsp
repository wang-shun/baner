<%@ page language="java" contentType="text/html; charset=gbk" pageEncoding="gbk"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<title>tiedcard</title>
<link rel="stylesheet" type="text/css" href="css/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="css/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="css/bootstramp/buttons.css">
<script type="text/javascript" src="js/easyui/jquery.min.js"></script>
<script type="text/javascript" src="js/easyui/jquery.easyui.min.js"></script>
</head>
<body>
	<div id="p" iconCls="icon-man" class="easyui-panel" title="绑卡" style="width:90%;height:400px;padding:10px;">
		<form id="tiedcard" action="login" method="post">
			<input type="hidden" name="merchantId" value="${merchantTiedCardMsg.head.merchantNo }"/>
			<input type="hidden" name="orderDate" value="${merchantTiedCardMsg.body.createDate }"/>
			<input type="hidden" name="orderTime" value="${merchantOrderMsg.body.createTime }"/>
			<input type="hidden" name="purchaserId" value="${merchantTiedCardMsg.body.purchaserId }"/>
				<table style="width: 90%; margin-left: 20px; height: 180px">
					<tr>
						<td width="30%" class="f2"><font size="3px">银行卡卡号：</font></td>
						<td class="f3"><input id="bankCard" name="bankCard"
							class="easyui-validatebox textbox" required="required"
							validType="bankcard[16,19]" value=""
							style="height: 25px; width: 250px" /></td>
					</tr>
					<tr>
						<td class="f2"><font size="3px">银行：</font></td>
						<td class="f3"><input id="bankname" class="easyui-combobox" name="bankname"  required="required"
							style="height: 25px; width: 150px" data-options=" valueField: 'id', textField: 'text', url: 'bank'" />
						</td>
					</tr>
					<tr>
						<td class="f2"><font size="3px">开户行：</font></td>
						<td class="f3"><input id="cc1" class="easyui-combobox"
							style="height: 25px; width: 100px"
							data-options=" valueField: 'id', textField: 'text'" /> <input
							id="cc2" class="easyui-combobox"
							data-options="valueField:'id',textField:'text'"
							style="height: 25px; width: 100px" /></td>
					</tr>
					<tr>
						<td class="f2"><font size="3px">姓名：</font></td>
						<td class="f3"><input id="personname" name="personname" required="required"
							class="easyui-validatebox textbox"
							data-options="valueField:'id',textField:'text'"
							style="height: 25px; width: 180px" /></td>
					</tr>
					<tr>
						<td class="f2"><font size="3px">证件号：</font></td>
						<td class="f3"><select id="identitycard"
							class="easyui-combobox" name="identitycard"
							style="height: 27px; width: 80px">
								<option value="1" checked>身份证</option>
								<option value="2">驾驶证</option>
								<option value="3">暂住证</option>
						</select> 
						 <input id="identitycardnum" name="identitycardnum"
							class="easyui-validatebox textbox" required="required"
							data-options="valueField:'id',textField:'text'"
							style="height: 25px; width: 180px" /></td>
					</tr>
					<tr>
						<td class="f2"><font size="3px">手机号：</font></td>
						<td class="f3"><input id="mobile" name="mobile"
							class="easyui-validatebox textbox" required="required"
							style="height: 25px; width: 180px" validType="phoneRex" value=""
							style="height: 20px;width: 180px" /></td>
					</tr>
					<tr>
						<td class="f2"><font size="3px">短信验证码：</font></td>
						<td><input id="sms" name="sms"
							class="easyui-validatebox textbox" required="required"
							validType="elength[6]" value=""
							style="height: 20px; width: 100px" /><a href="javascript:void(0)" class="easyui-linkbutton"
							onclick="sendsms()">发送短信</a><font id="smsresult"></font></td>
					</tr>
					<tr>
						<td colspan="4" align="center">
							<a href="javascript:void(0)"
								class="button button-primary button-rounded button-small"
								onclick="thirdToEnd();">确认</a>
						</td>
					</tr>
		</form>
	</div>
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
<script type="text/javascript">
		$(function() {
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
				phoneRex : {
					validator : function(value) {
						var rex = /^1[3-8]+\d{9}$/;
						var rex2 = /^((0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/;
						if (rex.test(value) || rex2.test(value)) {
							return true;
						} else {
							return false;
						}
					},
					message : '请输入正确电话或手机格式'
				}
			});
		});
		var sendsms=function(){
			if(!$('#mobile').validatebox("isValid")){
				$('#mobile').click();
			}else if(!$('#personname').validatebox("isValid")){
				$('#personname').click();
			}else{
				$.ajax({
				cache : true,
				type : "POST",
				url : "smssend",
				data : $('#tiedcard').serializeArray(),// 你的formid
				async : false,
				error : function(request) {
					alert("Connection error");
				},
				success : function(data) {
					flag = data;
					if(data=="true"){
						$("#smsresult").html("短信发送成功！");
					}else{
						alert("短信发送失败，请重试！");
					}
				}
			});
			}
		}
		var thirdToEnd = function() {
			if(!$('#sms').validatebox("isValid")){
				$('#sms').click();
			}else if (!$('#personname').validatebox("isValid")) {
				$('#personname').click();
			} else if (!$('#identitycardnum').validatebox("isValid")) {
				$('#identitycardnum').click();
			} else if (!$('#mobile').validatebox("isValid")) {
				$('#mobile').click();
			}else{
				var flag;
				$.ajax({
					cache : true,
					type : "POST",
					url : "tiedcard",
					data : $('#tiedcard').serializeArray(),// 你的formid
					async : false,
					error : function(request) {
						alert("Connection error");
					},
					success : function(data) {
						flag = data;
						if(data=="true"){
							alert("绑卡成功！")
						}else if(data=="smsfail"){
							alert("验证码错误");
						}else if(data=="filterfail"){
							alert("实名认证错误");
						}else{
							alert("支付系统错误");
						}
					}
				});
				if(flag=="true"){
					location.href="${merchantTiedCardMsg.body.pageReturnUrl}";
				}
			}
		}
	</script>
</body>
</html>