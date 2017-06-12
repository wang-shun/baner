<!DOCTYPE html>
<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel='icon' href='img/msds.ico' type='image/x-ico'/>
<title>mscbpay</title>
<link rel="stylesheet" type="text/css" href="css/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="css/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="css/bootstramp/buttons.css">
<script type="text/javascript" src="js/easyui/jquery.min.js"></script>
<script type="text/javascript" src="js/easyui/jquery.easyui.min.js"></script>
</head>
<body style="background-color: #F0F0F0; margin-left: 170px">
	<h2>民生电商</h2>
	<div class="demo-info"
		style="margin-bottom: 10px; background-color: #F0F0F0; width: 85%">
		<div class="demo-tip icon-tip"></div>
		<div style="display: inline;">我的收银台</div>
		<div style="float: right; margin-top: 10px">&nbsp;</div>
		<div style="display: inline; text-align: right; float: right">
			<font style="color: red; font-size: 30px"><fmt:formatNumber value="${orderShowRepBean.acceptanceMount }" type="currency" pattern="#,#00.00"/></font>
		</div>
	</div>
	<div style="margin: 20px 0 10px 0;"></div>
	<div id="p" class="easyui-panel" title="订单信息" iconCls="icon-ok"
		style="overflow: auto; width: 90%">
		<table style="width: 90%; margin-left: 20px; height: 200px">
			<tr>
				<td class="f2" width="20%">来源:</td>
				<td width="30%">${merchantOrderMsg.body.merchantName }</td>
				<td class="f2" width="20%">商品名称:</td>
				<td>${merchantOrderMsg.body.productName }</td>
			</tr>
			<tr>
				<td class="f2">订单金额:</td>
				<td><fmt:formatNumber value="${merchantOrderMsg.body.totalAmount }" type="currency" pattern="#,#00.00"/></td>
				<td class="f2">订单币种:</td>
				<td>${merchantOrderMsg.body.currency }</td>
			</tr>
			<tr>
				<td class="f2">收单金额:</td>
				<td><fmt:formatNumber value="${orderShowRepBean.acceptanceMount }" type="currency" pattern="#,#00.00"/></td>
				<td class="f2">收单币种:</td>
				<td>${orderShowRepBean.acceptancyCurrency }</td>
			</tr>
			<tr>
				<td class="f2">订单时间:</td>
				<td>${merchantOrderMsg.body.orderDate}${merchantOrderMsg.body.orderTime }</td>
				<td class="f2">交易号:</td>
				<td>${merchantOrderMsg.body.orderId }</td>
			</tr>
			<tr>
				<td colspan="4">
					<c:choose> 
						<c:when test="${checkOrderRepBean.poundageFlag==1}">
								支付金额=订单金额*汇率+手续费
						</c:when>
						<c:otherwise>   
								支付金额=订单金额*汇率
						</c:otherwise> 
					</c:choose>
				</td>
			</tr>
		</table>
	</div>
	<div class="easyui-panel" title="支付信息" iconCls="icon-man" style="width: 90%; padding: 10px;" id="bankinfo">
		<form id="pay" action="login" method="post">
			<input type="hidden" name="orderId" value="${merchantOrderMsg.body.orderId }">
			<input type="hidden" name="merchantId" value="${merchantOrderMsg.head.merchantNo }">
			<input type="hidden" name="orderDate" value="${merchantOrderMsg.body.orderDate }">
			<input type="hidden" name="orderTime" value="${merchantOrderMsg.body.orderTime }">
			<input type="hidden" name="tranFlow" value="${merchantOrderMsg.head.tranFlow }">
			<input type="hidden" name="purchaserId" value="${merchantOrderMsg.body.purchaserId }">
			<input type="hidden" name="pageReturnUrl" value="${merchantOrderMsg.body.pageReturnUrl }">
			<input type="hidden" name="offlineNotifyUrl" value="${merchantOrderMsg.body.offlineNotifyUrl }">
			<input type="hidden" id="pay_personname" name="personname" value="${orderShowRepBean.realName }">
			<input type="hidden" id="pay_mobile" name="mobile" value="${orderShowRepBean.telnum }">
			<input type="hidden" id="pay_cardid" name="cardid" value="${orderShowRepBean.cardId }">
			<input type="hidden" name="Flowno" value="${Flowno}"/>
			<table style="width: 90%; margin-left: 20px; height: 200px">
				<tr>
					<td class="f2" width="30%">银行卡号:</td>
					<td>**** <font id="banknum"></font>
						<a href="javascript:void(0)" class="easyui-linkbutton"
						onclick="$('#w').window('open');$('#first').show();$('#second').hide();$('#third').hide()">修改</a>
					</td>
				</tr>
				<tr>
					<td class="f2">手机号码:</td>
					<td><font id="telnumph"></font></td>
				</tr>
				<tr>
					<td class="f2">短信验证码:</td>
					<td><input id="p2ptext" name="p2ptext"
						class="easyui-validatebox textbox" required="required"
						validType="elength[6]" value=""
						style="height: 20px; width: 100px" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<a href="javascript:void(0)" class="easyui-linkbutton" id="sms_html" onclick="sendsms()">发送短信</a><font id="smsresult"></font></td>
				</tr>
				<tr>
					<td colspan="4" style="text-align: right">
					<a href="javascript:void(0)" onclick="pay()"
						class="button button-primary button-rounded">确认支付</a></td>
				</tr>
			</table>
		</form>
	</div>
	<div id="w" class="easyui-window" title="绑定银行卡" style="width: 600px; height: 300px; padding: 30px; text-align: center">
		<form id="tiedcard" action="login" method="post">
			<input type="hidden" name="orderId" value="${merchantOrderMsg.body.orderId }">
			<input type="hidden" name="tranFlow" value="${merchantOrderMsg.head.tranFlow }">
			<input type="hidden" name="merchantId" value="${merchantOrderMsg.head.merchantNo }">
			<input type="hidden" name="orderDate" value="${merchantOrderMsg.body.orderDate }">
			<input type="hidden" name="orderTime" value="${merchantOrderMsg.body.orderTime }">
			<input type="hidden" name="pageReturnUrl" value="${merchantOrderMsg.body.pageReturnUrl }">
			<input type="hidden" name="purchaserId" value="${merchantOrderMsg.body.purchaserId }">
			<input type="hidden" name="Flowno" value="${Flowno}"/>
			<div style="display: inline;" id="first">
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
						<td class="f3"><input id="bankname" class="easyui-combobox" name="bankname"
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
				</table>
				<a href="javascript:void(0)"
					class="button button-primary button-rounded button-small"
					onclick="firstTosecend();">下一步</a>
			</div>
			<div style="display: none" id="second">
				<table style="width: 90%; margin-left: 20px; height: 180px">
					<tr>
						<td width="30%" class="f2"><font size="3px">银行卡：</font></td>
						<td class="f3">**** <font id="bankCardlast"></font>
						</td>
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
				</table>
				<a href="javascript:void(0)"
					class="button button-primary button-rounded button-small"
					onclick="secendTothird();">同意协议并确定</a>
			</div>
			<div style="display: none" id="third">
				<font size="3px">请输入手机号<font id="telnum"></font>收到的验证码
				</font>
				<table style="width: 90%; margin-left: 20px; height: 160px">
					<tr>
						<td width="30%" class="f2"><font size="3px">校验码：</font></td>
						<td class="f3"><input id="sms" name="sms"
							class="easyui-validatebox textbox" required="required"
							validType="elength[6]" value="" style="height: 20px; width: 100px" /></td>
					</tr>
				</table>
				<a href="javascript:void(0)"
					class="button button-primary button-rounded button-small"
					onclick="thirdToEnd();">确认</a>
			</div>
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
			window.history.forward(1); 
			if ("${flag}" == "false") {
				$("#bankinfo").parent().hide();
			} else {
				$('#w').window('close');
				$('#banknum').html('${orderShowRepBean.cardId }'.substring('${orderShowRepBean.cardId }'.length - 4));
				$('#telnumph').html('${orderShowRepBean.telnum }'.substring(0,3)+"****"+'${orderShowRepBean.telnum }'.substring(7));
			}
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
		var firstTosecend = function() {
			var isValid = $('#bankCard').validatebox("isValid");
			if (isValid) {
				$('#first').hide();
				$('#second').show();
				$('#third').hide();
				var bankcard = $('#bankCard').val();
				$("#bankCardlast").html(bankcard.substring(bankcard.length - 4));
			} else {
				$('#bankCard').click();
			}
		}
		var secendTothird = function() {
			if (!$('#personname').validatebox("isValid")) {
				$('#personname').click();
			} else if (!$('#identitycardnum').validatebox("isValid")) {
				$('#identitycardnum').click();
			} else if (!$('#mobile').validatebox("isValid")) {
				$('#mobile').click();
			} else {
				$.ajax({
					cache : true,
					type : "POST",
					url : "smssend",
					data : $('#tiedcard').serializeArray(),// 你的formid
					async : false,
					error : function(request) {
						location.href="error?pageReturnUrl="+encodeURI("${merchantOrderMsg.body.pageReturnUrl}")+"&respMsg="+encodeURI(encodeURI("支付系统错误"));
					},
					success : function(data) {
						if(data=="true"){
							$('#first').hide();
							$('#second').hide();
							$('#third').show();
							$("#telnum").text($('#mobile').val().substring(0,3)+"****"+$('#mobile').val().substring(7));
						}else if(data=="systemfail"){
							location.href="error?pageReturnUrl="+encodeURI("${merchantOrderMsg.body.pageReturnUrl}")+"&respMsg="+encodeURI(encodeURI("支付系统错误"));
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
			}else{
				$.ajax({
					cache : true,
					type : "POST",
					url : "tiedcard",
					data : $('#tiedcard').serializeArray(),// 你的formid
					async : false,
					error : function(request) {
						location.href="error?pageReturnUrl="+encodeURI("${merchantOrderMsg.body.pageReturnUrl}")+"&respMsg="+encodeURI(encodeURI("支付系统错误"));
					},
					success : function(data) {
						var result = data.split(";");
						if(result[0]=="true"){
							$('#w').window('close');
							$('#bankinfo').parent().show();
							$('#banknum').html($('#bankCardlast').html());
							$('#telnumph').html($('#telnum').html());
							$('#pay_personname').val($('#personname').val());
							$('#pay_mobile').val($('#mobile').val());
							$('#pay_cardid').val($('#bankCard').val());
						}else if(result[0]=="smsfail"){
							alert("验证信息错误！请检查后重试");
						}else if(result[0]=="CBP002"||result[0]=="CBP003"||result[0]=="CBP004"||result[0]=="CBP005"){
							alert(result[1]);
						}else if(data=="systemfail"){
							location.href="error?pageReturnUrl="+encodeURI("${merchantOrderMsg.body.pageReturnUrl}")+"&respMsg="+encodeURI(encodeURI("支付系统错误"));
						}else{
							location.href="error?pageReturnUrl="+encodeURI("${merchantOrderMsg.body.pageReturnUrl}")+"&respMsg="+encodeURI(encodeURI(result[1]));
						}
					}
				});
			}
		}
		var pay = function(){
			if(!$('#p2ptext').validatebox("isValid")){
				$('#p2ptext').click();
			}else{
				$.ajax({
					cache : false,
					type : "POST",
					url : "smscheck",
					data : $('#pay').serializeArray(),// 你的formid
					async : false,
					error : function(request) {
						location.href="error?pageReturnUrl="+encodeURI("${merchantOrderMsg.body.pageReturnUrl}")+"&respMsg="+encodeURI(encodeURI("支付系统错误"));
					},
					success : function(data) {
						var result = data.split(";");
						if(result[0]=="true"){
							location.href="success?pageReturnUrl="+encodeURI("${merchantOrderMsg.body.pageReturnUrl}")+"&param="+result[1];
						}else if(result[0]=="smsfail"){
							alert("验证信息错误！请检查后重试");
						}else if(result[0]=="CBP002"||result[0]=="CBP003"||result[0]=="CBP004"||result[0]=="CBP005"){
							alert(result[1]);
						}else if(result[0]=="systemfail"){
							location.href="error?pageReturnUrl="+encodeURI("${merchantOrderMsg.body.pageReturnUrl}")+"&respMsg="+encodeURI(encodeURI("支付系统错误"));
						}else{
							location.href="error?pageReturnUrl="+encodeURI("${merchantOrderMsg.body.pageReturnUrl}")+"&respMsg="+encodeURI(result[1]);
						}
					}
				});
			}
		}
		var sendsms=function(){
			$.ajax({
				cache : true,
				type : "POST",
				url : "smssend",
				data : $('#pay').serializeArray(),// 你的formid
				async : false,
				error : function(request) {
					location.href="error?pageReturnUrl="+encodeURI("${merchantOrderMsg.body.pageReturnUrl}")+"&respMsg="+encodeURI(encodeURI("支付系统错误"));
				},
				success : function(data) {
					flag = data;
					if(data=="true"){
						$("#smsresult").html("");
						$('#sms_html').hide();
						waitfor(120);
					}else if(data=="systemfail"){
						location.href="error?pageReturnUrl="+encodeURI("${merchantOrderMsg.body.pageReturnUrl}")+"&respMsg="+encodeURI(encodeURI("支付系统错误"));
					}else{
						alert("下发短信异常，请重试");
					}
				}
			});
		}
		function waitfor(sec){
			$("#smsresult").html('短信发送成功！'+sec+'S后可以重试');
			if(sec==0){
				$("#smsresult").html('');
				$('#sms_html').show();
			}else{
				window.setTimeout('waitfor('+(sec-1)+')',1000);
			}
		}
	</script>
</body>
</html>