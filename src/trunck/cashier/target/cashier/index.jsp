<!DOCTYPE html>
<%@page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>mscbpay</title>
    <link rel="stylesheet" type="text/css" href="css/easyui/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="css/easyui/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="css/bootstramp/buttons.css">
    <script type="text/javascript" src="js/easyui/jquery.min.js"></script>
    <script type="text/javascript" src="js/easyui/jquery.easyui.min.js"></script>
</head>
<body style="background-color: #F0F0F0;margin-left: 170px">
    <h2>民生电商</h2>
    <div class="demo-info"  style="margin-bottom:10px;background-color: #F0F0F0;width: 85%">
        <div class="demo-tip icon-tip"></div>
        <div style="display: inline;">我的收银台</div>
        <div style="float: right;margin-top: 10px">&nbsp;元</div>
        <div style="display: inline;text-align: right;float: right"><font style="color: red;font-size: 30px">89.0</font></div>
    </div>
	<div style="margin:20px 0 10px 0;">
	</div>
	<div id="p" class="easyui-panel" title="订单信息" iconCls="icon-ok" style="overflow:auto;width: 90%">
		<table style="width: 90%;margin-left: 20px;height: 200px">
            <tr>
                <td class="f2">来源:</td>
                <td>淘宝网</td>
            </tr>
            <tr>
                <td class="f2">商品名称:</td>
                <td>毛衣</td>
            </tr>
            <tr>
                <td class="f2">交易金额:</td>
                <td>89.00元</td>
            </tr>
            <tr>
                <td class="f2">购买时间:</td>
                <td>2016年02月29日 16:44:52</td>
            </tr>
            <tr>
                <td class="f2">收货地址:</td>
                <td>高新区软件园A座19楼，261000（邮编） 孙有山（收） 18669398424</td>
            </tr>
            <tr>
                <td class="f2">交易号:</td>
                <td>2016022921001001570288283845</td>
            </tr>
        </table>
	</div>
    <div class="easyui-panel" title="支付信息" iconCls="icon-man" style="width:90%;padding:10px;">
        <form id="ff" action="login" method="post" enctype="multipart/form-data">
            <table style="width: 90%;margin-left: 20px;height: 200px">
                <tr>
                    <td class="f2" width="30%">银行卡号:</td>
                    <td><font>6666 6666 6666 6666</font>
                    <input id="p2ptext" name="p2ptext" class="easyui-validatebox" required="required" style="display: none;"
                    	validType="minLength[10]" value=""/>
						<a href="javascript:void(0)" class="easyui-linkbutton" onclick="$('#w').window('open');$('#first').show();$('#second').hide();$('#third').hide()">修改</a>
					</td>
                </tr>
                <tr>
                    <td class="f2">手机号码:</td>
                    <td><font>13500000000</font>
                    <input id="p2ptext" name="p2ptext" class="easyui-validatebox" required="required" style="display: none;"
                    	validType="phoneRex" value=""/>
					</td>
                </tr>
                <tr>
                    <td class="f2">短信验证码:</td>
                    <td><input id="p2ptext" name="p2ptext" class="easyui-validatebox textbox" required="required"
                    validType="minLength[5]" value="" style="height: 20px;width: 100px"/>
					</td>
                </tr>
                <tr>
                    <td colspan="4" style="text-align: right">
                    <a href="javascript:void(0)" class="button button-primary button-rounded">确认支付</a></td>
                </tr>
            </table>
        </form>
    </div>
    <div id="w" class="easyui-window" title="绑定银行卡" style="width:600px;height:300px;padding:30px;text-align: center">
		<div style="display: inline;" id="first">
				<table style="width: 90%;margin-left: 20px;height: 180px">
					<tr>
						<td width="30%" class="f2">
							<font size="3px">银行卡卡号：</font>
						</td>
						<td class="f3">
							<input id="bankCard" name="bankCard" class="easyui-validatebox textbox" required="required"
		                    	validType="minLength[10]" value="" style="height: 20px;width: 250px"/>
		                </td>
		            </tr>
		            <tr>
		            	<td class="f2">
		            		<font size="3px">银行：</font>
		            	</td>
		            	<td class="f3">
		            		<input id="bankCard" name="bankCard" class="easyui-validatebox textbox" required="required"
		                    	validType="minLength[10]" value="" style="height: 20px;width: 180px"/>  
		            	</td>
		            </tr>
		            <tr>
		            	<td class="f2">
		            		<font size="3px">开户行：</font>
		            	</td>
		            	<td class="f3">
		            		<input id="cc1" class="easyui-combobox" style="height: 25px;width: 100px" data-options=" valueField: 'id', textField: 'text', url: 'get_data1.php',  
								onSelect: function(rec){  
								var url = 'get_data2.php?id='+rec.id;  
								$('#cc2').combobox('reload', url);  
								}" />  
							<input id="cc2" class="easyui-combobox" data-options="valueField:'id',textField:'text'" style="height: 25px;width: 100px"/>  
		            	</td>
		            </tr>
	          	</table>
	          	 <a href="javascript:void(0)" class="button button-primary button-rounded button-small" onclick="$('#first').hide();$('#second').show();$('#third').hide();">下一步</a>
	        </div>
         <div style="display: none" id="second">
			<table style="width: 90%;margin-left: 20px;height: 180px">
					<tr>
						<td width="30%" class="f2">
							<font size="3px">银行卡：</font>
						</td>
						<td class="f3">
							**** 9999
		                </td>
		            </tr>
		            <tr>
		            	<td class="f2">
		            		<font size="3px">姓名：</font>
		            	</td>
		            	<td class="f3">
		            		<select id="cc" class="easyui-combobox" name="dept" style="height: 25px;width: 250px">  
								<option value="aa">aitem1</option>  
								<option>bitem2</option>  
								<option>bitem3</option>  
								<option>ditem4</option>  
								  
								<option>eitem5</option>  
							</select>  
		            	</td>
		            </tr>
		            <tr>
		            	<td class="f2">
		            		<font size="3px">证件号：</font>
		            	</td>
		            	<td class="f3">
		            		<input id="cc1" class="easyui-combobox" style="height: 25px;width: 80px" data-options=" valueField: 'id', textField: 'text', url: 'get_data1.php',  
								onSelect: function(rec){  
								var url = 'get_data2.php?id='+rec.id;  
								$('#cc2').combobox('reload', url);  
								}" />  
							<input id="cc2" class="easyui-validatebox textbox" data-options="valueField:'id',textField:'text'" style="height: 25px;width: 180px"/>  
		            	</td>
		            </tr>
		            <tr>
		            	<td class="f2">
		            		<font size="3px">手机号：</font>
		            	</td>
		            	<td class="f3">
		            		<input id="bankCard" name="bankCard" class="easyui-validatebox textbox" required="required"
		                    	validType="minLength[10]" value="" style="height: 20px;width: 180px"/>  
		            	</td>
		            </tr>
	          	</table>
	          	<a href="javascript:void(0)" class="button button-primary button-rounded button-small" onclick="$('#first').hide();$('#second').hide();$('#third').show();">同意协议并确定</a>
	        </div>
	        <div style="display: none" id="third">
	        <font size="3px">请输入手机号135****1234收到的验证码</font>
			<table style="width: 90%;margin-left: 20px;height: 160px">
					<tr>
						<td width="30%" class="f2">
							<font size="3px">校验码：</font>
						</td>
						<td class="f3">
							<input id="bankCard" name="bankCard" class="easyui-validatebox textbox" required="required"
		                    	validType="minLength[10]" value="" style="height: 20px;width: 100px"/>  
		                </td>
		            </tr>
	          	</table>
	          	<a href="javascript:void(0)" class="button button-primary button-rounded button-small" onclick="$('#w').window('close')">确认</a>
	        </div>
	</div>
    <style scoped>
        .f1{
            width:200px;
        }
        .f2{
            text-align:right;
        }
        .f3{
            text-align:left;
        }
    </style>
    <script type="text/javascript">
        $(function(){
        	$('#w').window('close');	
        	$.extend($.fn.validatebox.defaults.rules, {
        	    minLength: {
        			validator: function(value, param){
        				return value.length >= param[0];
        			},
        			message: '请输入最少{0}位数字'
        	    },
	        	elength: {
	    			validator: function(value, param){
	    				return value.length == param[0];
	    			},
	    			message: '请输入6位密码'
	    	    },
	    	    phoneRex: {
	    	        validator: function(value){
	    	        var rex=/^1[3-8]+\d{9}$/;
	    	        var rex2=/^((0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/;
	    	        if(rex.test(value)||rex2.test(value))
	    	        {
	    	          return true;
	    	        }else
	    	        {
	    	           return false;
	    	        }
	    	          
	    	        },
	    	        message: '请输入正确电话或手机格式'
	    	      }
        	});
        });
        /* var change = function(thisDom){
        	if($(thisDom).text()=="修改"){
	        	$(thisDom).text("保存");
	        	$(thisDom).parent().find('input').show();
	        	$(thisDom).parent().find('font').hide();
        	}else{
        		if($(thisDom).parent().find('input').textbox("isValid")){
	        		$(thisDom).text("修改");
		        	$(thisDom).parent().find('font').show();
		        	$(thisDom).parent().find('font').text($(thisDom).parent().find('input').val());
		        	$(thisDom).parent().find('input').hide();
		        	$(thisDom).parent().find('input').val("");
        		}
        	}
        } */
    </script>
</body>
</html>