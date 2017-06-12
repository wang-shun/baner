package com.ztkx.transplat.container.preload;

import java.io.File;
import java.io.FilenameFilter;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ztkx.transplat.container.HandlerException;
import com.ztkx.transplat.container.msg.XmlParser;
import com.ztkx.transplat.invokerexecutor.InvokerExecutor;
import com.ztkx.transplat.platformutil.baseconfig.BaseConfig;
import com.ztkx.transplat.platformutil.baseconfig.ConstantConfigField;
import com.ztkx.transplat.platformutil.msg.MsgXmlDescriber;
import com.ztkx.transplat.reload.ReloadAble;


/**
 * 预加载系统CrossBorderPay/msg/errunpack目录下的所有xml文件
 * @author zhangxiaoyun
 *
 */
public class ErrXmlFilePreloader implements CommonPreloadInterface,ReloadAble,InvokerExecutor{

	private Logger logger = Logger.getLogger(ErrXmlFilePreloader.class);
	String keyMsgFileName = ConstantConfigField.KEYMSG_CONFIGFILE;
	String dirPath = BaseConfig.getConfig(ConstantConfigField.HOMEDIR)+File.separator+"msg"+File.separator+"errunpack";
	static Map<String,MsgXmlDescriber> errXmlMap = new HashMap<String, MsgXmlDescriber>();
	static Map<String,MsgXmlDescriber> tmperrXmlMap = null;
	
	@Override
	public void load() {
		logger.info("start exec errUnpack xml file...");
		File dir = new File(dirPath);
		String[] list =dir.list(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				// TODO Auto-generated method stub
				if(name.endsWith(".xml"))
					return true;
				else
					return false;
			}
		});
		String fileName = null;
		
		for (int i = 0; i < list.length; i++) {
			try{
				fileName = list[i];
				logger.info("load error msg xml ["+dirPath+File.separator+fileName+"]");
				MsgXmlDescriber describer = XmlParser.parse(dirPath+File.separator+fileName);
				errXmlMap.put(fileName, describer);
			}catch(Exception e){
				logger.error("load error msg xml expception fileName ["+fileName+']',e);
			}
		}	
		logger.info("load errUnpack xml file finish");
	}
	
	/**
	 * 获取拆包的xml对象
	 * @param fileName  根据文件名获取文件描述对象
	 * @return
	 */
	public static MsgXmlDescriber getUnpackDesc(String fileName){
		return errXmlMap.get(fileName+".xml");
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
		logger.info("start preload exec errUnpack xml file...");
		File dir = new File(dirPath);
		tmperrXmlMap = new HashMap<String, MsgXmlDescriber>();
		String[] list =dir.list(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				// TODO Auto-generated method stub
				if(name.endsWith(".xml"))
					return true;
				else
					return false;
			}
		});
		String fileName = null;
		
		for (int i = 0; i < list.length; i++) {
			try{
				fileName = list[i];
				logger.info("preload error msg xml ["+dirPath+File.separator+fileName+"]");
				MsgXmlDescriber describer = XmlParser.parse(dirPath+File.separator+fileName);
				tmperrXmlMap.put(fileName, describer);
			}catch(Exception e){
				logger.error("preload error msg xml expception fileName ["+fileName+']',e);
			}
		}	
		logger.info("preload errUnpack xml file finish");
		return true;
	}

	@Override
	public void reload() throws HandlerException {
		// TODO Auto-generated method stub
		logger.info("start reload exec errUnpack xml file...");
		errXmlMap = tmperrXmlMap;
		tmperrXmlMap = null;
		logger.info("reload errUnpack xml file finish");
	}

	@Override
	public void failHand() throws HandlerException {
		// TODO Auto-generated method stub
		logger.info("start failHand exec errUnpack xml file...");
		if(tmperrXmlMap!=null){
			tmperrXmlMap.clear();
			tmperrXmlMap = null;
		}
		logger.info("failHand errUnpack xml file finish");
	}
}
