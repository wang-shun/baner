package com.ztkx.cbpay.container.preload;

import java.io.File;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ztkx.cbpay.container.HandlerException;
import com.ztkx.cbpay.container.msg.ModulesXmlParser;
import com.ztkx.cbpay.invokerexecutor.InvokerExecutor;
import com.ztkx.cbpay.platformutil.baseconfig.BaseConfig;
import com.ztkx.cbpay.platformutil.baseconfig.ConstantConfigField;
import com.ztkx.cbpay.platformutil.msg.ModulesDescriber;
import com.ztkx.cbpay.reload.ReloadAble;

/**
 * 预加载modules.xml的工具类
 * 
 * @author zhangxiaoyun 2016年3月6日 下午3:14:53
 *         <p>
 *         Company:ztkx
 *         </p>
 */
public class ModulesConfPreloader implements CommonPreloadInterface,InvokerExecutor,ReloadAble{

	private Logger logger = Logger.getLogger(ModulesConfPreloader.class);
	String modulesFileName = ConstantConfigField.MODULES_CONFIGFILE;
	String filePaht = BaseConfig.getConfig(ConstantConfigField.HOMEDIR)
			+ File.separator + "msg" + File.separator;
	static Map<String, ModulesDescriber> modules = null;
	private static Map<String, ModulesDescriber> tmpmodules = null;

	@Override
	public void load() {
		logger.info("start exec ModulesConfPreloader...");
		modules = ModulesXmlParser.parse(filePaht + modulesFileName);

		if (logger.isDebugEnabled()) {
			for (Map.Entry<String, ModulesDescriber> entry : modules.entrySet()) {
				logger.debug("key msg id:" + entry.getKey() + " value ["
						+ entry.getValue().toString() + "]");
			}
		}

		logger.info("KeyMsgConfPreloader is finish");
	}

	public static ModulesDescriber getModuleById(String moduleId) {
		return modules.get(moduleId);
	}

	@Override
	public boolean preload() throws HandlerException {
		// TODO Auto-generated method stub
		logger.info("preload start exec ModulesXmlParser...");
		tmpmodules = ModulesXmlParser.parse(filePaht + modulesFileName);
		if (logger.isDebugEnabled()) {
			for (Map.Entry<String, ModulesDescriber> entry : tmpmodules.entrySet()) {
				logger.debug("tmpmodules msg id:" + entry.getKey() + " value ["
						+ entry.getValue().toString() + "]");
			}
		}
		logger.info("preload finish exec ModulesXmlParser...");
		return true;
	}

	@Override
	public void reload() throws HandlerException {
		// TODO Auto-generated method stub
		logger.info("reload start exec ModulesXmlParser...");
		modules = tmpmodules;
		tmpmodules = null;
		if (logger.isDebugEnabled()) {
			for (Map.Entry<String, ModulesDescriber> entry : modules.entrySet()) {
				logger.debug("key msg id:" + entry.getKey() + " value ["
						+ entry.getValue().toString() + "]");
			}
		}
		logger.info("reload finish exec ModulesXmlParser...");
		
	}

	@Override
	public void failHand() throws HandlerException {
		// TODO Auto-generated method stub
		logger.info("failHand start exec ModulesXmlParser...");
		if(tmpmodules!=null){
			tmpmodules.clear();
			tmpmodules = null;
		}
		logger.info("failHand finish exec ModulesXmlParser...");
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return this.getClass().getName();
	}

	@Override
	public boolean doCommand(Map<String,String> commandparam) throws HandlerException {
		// TODO Auto-generated method stub
		return this.preload();
	}

	@Override
	public void confirmOpr() throws HandlerException {
		// TODO Auto-generated method stub
		this.reload();
	}

	@Override
	public void cancleCommand() throws HandlerException {
		// TODO Auto-generated method stub
		this.failHand();
	}

}
