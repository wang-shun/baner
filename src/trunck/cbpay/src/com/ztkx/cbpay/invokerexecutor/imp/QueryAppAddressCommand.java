package com.ztkx.cbpay.invokerexecutor.imp;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ztkx.cbpay.container.HandlerException;
import com.ztkx.cbpay.container.initdata.AppAddressData;
import com.ztkx.cbpay.container.javabean.AppAddress;
import com.ztkx.cbpay.invoker.InvokerConstant;
import com.ztkx.cbpay.invokerexecutor.InvokerExecutor;

public class QueryAppAddressCommand implements InvokerExecutor{
	Logger logger = Logger.getLogger(QueryAppAddressCommand.class);
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return this.getClass().getName();
	}

	@Override
	public boolean doCommand(Map<String, String> commandparam)
			throws HandlerException {
		// TODO Auto-generated method stub
		logger.info("QueryAppAddressCommand is begin...");
		try{
			List<AppAddress> AppAddressList = AppAddressData.getInstance().getOutAddressList();
			String result = "";
			for(AppAddress appAddress:AppAddressList){
				result = result + appAddress.toString() + InvokerConstant.SEPARATE_ONE;
				if(logger.isDebugEnabled()){
					logger.debug("result["+result+"]");
				}
				commandparam.put(InvokerConstant.result, result);
			}
			logger.info("QueryAppAddressCommand is succ");
		}catch(Exception e){
			logger.error("QueryAppAddressCommand is error",e);
			return false;
		}
		return true;
	}
	

	@Override
	public void confirmOpr() throws HandlerException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cancleCommand() throws HandlerException {
		// TODO Auto-generated method stub
		
	}

}
