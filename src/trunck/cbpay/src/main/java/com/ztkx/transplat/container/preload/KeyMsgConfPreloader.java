package com.ztkx.transplat.container.preload;

import com.ztkx.transplat.container.HandlerException;
import com.ztkx.transplat.container.msg.KeyMsgXmlParser;
import com.ztkx.transplat.invokerexecutor.InvokerExecutor;
import com.ztkx.transplat.platformutil.baseconfig.BaseConfig;
import com.ztkx.transplat.platformutil.baseconfig.ConstantConfigField;
import com.ztkx.transplat.platformutil.msg.KeyMsgDescriber;
import com.ztkx.transplat.reload.ReloadAble;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.Map;

/**
 * 预加载系统keyMsg.xml文件
 * @author zhangxiaoyun
 *
 */
public class KeyMsgConfPreloader implements CommonPreloadInterface,InvokerExecutor,ReloadAble{

	private Logger logger = Logger.getLogger(KeyMsgConfPreloader.class);
	String keyMsgFileName = ConstantConfigField.KEYMSG_CONFIGFILE;
	String filePaht = BaseConfig.getConfig(ConstantConfigField.HOMEDIR)+File.separator+"msg"+File.separator;
	static Map<String,KeyMsgDescriber> keyMsg = null;
	private Map<String,KeyMsgDescriber> tmpkeyMsg = null;
	
	@Override
	public void load() {
		logger.info("start exec KeyMsgConfPreloader...");
		keyMsg = KeyMsgXmlParser.parse(filePaht+keyMsgFileName);
		
		if(logger.isDebugEnabled()){
			for (Map.Entry<String,KeyMsgDescriber> entry : keyMsg.entrySet()) {
				logger.debug("key msg id:"+entry.getKey()+" value ["+entry.getValue().toString()+"]");
			}
		}
		
		logger.info("KeyMsgConfPreloader is finish");
	}

	public static Map<String, KeyMsgDescriber> getKeyMsg() {
		return keyMsg;
	}

	public static KeyMsgDescriber getKeyMsgDescriber(String tranFrom){
		return keyMsg.get(tranFrom);
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

	@Override
	public boolean preload() throws HandlerException {
		// TODO Auto-generated method stub
		logger.info("preload start exec KeyMsgConfPreloader...");
		tmpkeyMsg = KeyMsgXmlParser.parse(filePaht+keyMsgFileName); 
		if(logger.isDebugEnabled()){
			for (Map.Entry<String,KeyMsgDescriber> entry : tmpkeyMsg.entrySet()) {
				logger.debug("tmpkeyMsg msg id:"+entry.getKey()+" value ["+entry.getValue().toString()+"]");
			}
		}
		logger.info("preload finish exec KeyMsgConfPreloader...");
		return true;
	}

	@Override
	public void reload() throws HandlerException {
		// TODO Auto-generated method stub
		logger.info("reload start exec KeyMsgConfPreloader...");
		keyMsg = tmpkeyMsg;
		tmpkeyMsg = null;
		if(logger.isDebugEnabled()){
			for (Map.Entry<String,KeyMsgDescriber> entry : keyMsg.entrySet()) {
				logger.debug("keyMsg msg id:"+entry.getKey()+" value ["+entry.getValue().toString()+"]");
			}
		}
		logger.info("reload finish exec KeyMsgConfPreloader...");
	}

	@Override
	public void failHand() throws HandlerException {
		// TODO Auto-generated method stub
		logger.info("failHand start exec KeyMsgConfPreloader...");
		if(tmpkeyMsg!=null){
			tmpkeyMsg.clear();
		}
		tmpkeyMsg = null;
		logger.info("failHand finish exec KeyMsgConfPreloader...");
	}

}
