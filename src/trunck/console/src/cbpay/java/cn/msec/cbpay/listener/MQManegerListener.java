package cn.msec.cbpay.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import cn.msec.cbpay.util.ConsoleConstant;

import com.ztkx.cbpay.platformutil.activemq.ActiveMQInitManager;
import com.ztkx.cbpay.platformutil.baseconfig.BaseConfig;
import com.ztkx.cbpay.platformutil.baseconfig.ConstantConfigField;

public class MQManegerListener  implements ServletContextListener{

	Logger logger = Logger.getLogger(MQManegerListener.class);
	private void createMq(){
		BaseConfig.getInstence(System.getProperty("container_path")+"/"+ConsoleConstant.CONFIG_FILE_PATH);
		String container_path = System.getProperty("container_path");
		logger.info("config_path ["+container_path+"]");
		BaseConfig.setConfig(ConstantConfigField.CONFIGPATH, container_path);
		ActiveMQInitManager.getInstance().instance();
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		this.createMq();
	}

}
