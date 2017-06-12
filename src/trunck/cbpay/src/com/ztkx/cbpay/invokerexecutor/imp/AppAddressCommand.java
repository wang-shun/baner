package com.ztkx.cbpay.invokerexecutor.imp;

import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Element;

import com.ztkx.cbpay.container.HandlerException;
import com.ztkx.cbpay.container.initdata.AppAddressData;
import com.ztkx.cbpay.container.javabean.AppAddress;
import com.ztkx.cbpay.discenter.mqclient.send.DisSenderList;
import com.ztkx.cbpay.discenter.mqclient.send.DisSenderManager;
import com.ztkx.cbpay.invoker.InvokerConstant;
import com.ztkx.cbpay.invokerexecutor.InvokerExecutor;
import com.ztkx.cbpay.platformutil.activemq.config.ParseServiceInfoUtil;
import com.ztkx.cbpay.platformutil.activemq.config.ServiceInfo;
import com.ztkx.cbpay.platformutil.baseconfig.ConstantConfigField;
import com.ztkx.cbpay.platformutil.xml.Dom4jXmlUtil;

public class AppAddressCommand implements InvokerExecutor{
	Logger logger = Logger.getLogger(AppAddressCommand.class);
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return this.getClass().getName();
	}

	@Override
	public boolean doCommand(Map<String, String> commandparam)
			throws HandlerException {
		// TODO Auto-generated method stub
		logger.info("AppAddressComService is begin...");
		try{
			String appstatus = commandparam.get(InvokerConstant.appstatus);
			String appid = commandparam.get(InvokerConstant.appid);
			logger.info("appstatus ["+appstatus+"] appid ["+appid+"]");
			AppAddress appadrsout = null;
			
			appadrsout = findAppValue(appid);//内存中查找数据
			
			if(appadrsout==null){
				logger.error("online is fail,not find appid["+appid+"] out value in ram table[app_address]");
				return false;
			}
			
			if(ConstantConfigField.APP_ADDRESS_APPSTATUS_ON.equals(appstatus)){
				this.openApp(appadrsout);
			}else if(ConstantConfigField.APP_ADDRESS_APPSTATUS_OFF.equals(appstatus)){
				this.closeApp(appadrsout);
			}
			logger.info("AppAddressComService is succ");
		}catch(Exception e){
			logger.error("AppAddressComService is error",e);
		}
		return true;
	}
	
	private AppAddress findAppValue(String appid){
		for(AppAddress appaddress: AppAddressData.getInstance().getOutAddressList()){
			if(appaddress.getAppid().equals(appid)){
				logger.info("get appid["+appid+"] value succ");
				return appaddress;
			}
		}
		return null;
	}
	/**
	 * 开启
	 * @param appaddress
	 */
	private void openApp(AppAddress appaddress){
		logger.info("online is begin");
		appaddress.setAppstatus(ConstantConfigField.APP_ADDRESS_APPSTATUS_ON);
		String appId = appaddress.getAppid();
		String url = appaddress.getUrl();
		if(DisSenderManager.getSender(appId)==null){
			logger.info("DisSenderManager senders add appId["+appId+"] begin");
			Element rootEle =  Dom4jXmlUtil.getRootElementByStr(url);
			ServiceInfo info = ParseServiceInfoUtil.parse(rootEle);
			DisSenderList senderList = new DisSenderList(info);
			DisSenderManager.putSender(appId, senderList);
			logger.info("DisSenderManager senders add appId["+appId+"] succ");
		}
		DisSenderManager.addloopList(appId);
		logger.info("DisSenderManager loopList add appId["+appId+"] succ");
		logger.info("online is succ");
	}
	/**
	 * 关闭
	 * @param appaddress
	 */
	private void closeApp(AppAddress appaddress){
		logger.info("offline is begin");
		appaddress.setAppstatus(ConstantConfigField.APP_ADDRESS_APPSTATUS_OFF);
		String appId = appaddress.getAppid();
		DisSenderManager.removeloopList(appId);
		logger.info("DisSenderManager loopList remove appId["+appId+"] succ");
		logger.info("offline is succ");
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
