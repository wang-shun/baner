package com.ztkx.transplat.container.preload;

import com.ztkx.transplat.container.HandlerException;
import com.ztkx.transplat.container.initdata.ConfXmlFormateData;
import com.ztkx.transplat.container.javabean.ConfXmlFormate;
import com.ztkx.transplat.container.msg.XmlParser;
import com.ztkx.transplat.invokerexecutor.InvokerExecutor;
import com.ztkx.transplat.platformutil.baseconfig.BaseConfig;
import com.ztkx.transplat.platformutil.baseconfig.ConstantConfigField;
import com.ztkx.transplat.platformutil.msg.MsgXmlDescriber;
import com.ztkx.transplat.reload.ReloadAble;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 预加载系统CrossBorderPay/msg目录下的所有xml文件
 * @author zhangxiaoyun
 */
public class XmlFilePreloader implements CommonPreloadInterface,ReloadAble,InvokerExecutor{

	private Logger logger = Logger.getLogger(XmlFilePreloader.class);
	String keyMsgFileName = ConstantConfigField.KEYMSG_CONFIGFILE;
	String dirPath = BaseConfig.getConfig(ConstantConfigField.HOMEDIR)+File.separator+"msg";
	static Map<String,MsgXmlDescriber> packXmlMap = new HashMap<String, MsgXmlDescriber>();
	private Map<String,MsgXmlDescriber> tmpPackXmlMap = null;
	static Map<String,MsgXmlDescriber> unpackXmlMap = new HashMap<String, MsgXmlDescriber>();
	private Map<String,MsgXmlDescriber> tmpUnpackXmlMap = null;
	
	@Override
	public void load() {
		logger.info("start exec XmlFilePreloader...");
		load(unpackXmlMap,packXmlMap);
		logger.info("XmlFilePreloader is finish");
	}

	/**
	 * 
	 * 2016年7月8日 下午1:37:17
	 * @author zhangxiaoyun
	 * @param unpackMap
	 * @param packMap
	 * @return void
	 */
	private void load(Map<String,MsgXmlDescriber> unpackMap,Map<String,MsgXmlDescriber> packMap) {
		List<ConfXmlFormate> list = ConfXmlFormateData.getInstance().getList();
		for (int i = 0; i < list.size(); i++) {
			ConfXmlFormate confXmlFormate = list.get(i);
			StringBuilder xmlPath = new StringBuilder();
			xmlPath.append(dirPath);
			try{
				if(confXmlFormate.getType().equals(ConstantConfigField.CONF_XML_FORMATE_TYPE_UNPACK)){
					xmlPath.append(File.separator).append("unpack").append(File.separator).append(confXmlFormate.getPath());
					logger.info("load msg xml ["+xmlPath.toString()+"]");
					MsgXmlDescriber describer = XmlParser.parse(xmlPath.toString());
					logger.debug("xml file content ["+describer+"]");
					unpackMap.put(confXmlFormate.getSystemid()+ConstantConfigField.TABLE_PRI_KEY_SEPARATOR+confXmlFormate.getTrancode(), describer);
				}else if(confXmlFormate.getType().equals(ConstantConfigField.CONF_XML_FORMATE_TYPE_PACK)){
					xmlPath.append(File.separator).append("pack").append(File.separator).append(confXmlFormate.getPath());
					logger.info("load msg xml ["+xmlPath.toString()+"]");
					MsgXmlDescriber describer = XmlParser.parse(xmlPath.toString());
					packMap.put(confXmlFormate.getSystemid()+ConstantConfigField.TABLE_PRI_KEY_SEPARATOR+confXmlFormate.getTrancode(), describer);
				}
			}catch(Exception e){
				logger.error("parse xml file exception fileName ["+xmlPath.toString()+"]",e);
			}
		}
	}
	
	/**
	 * 获取拆包的xml对象
	 * @param pri_key  systemcode_trancode
	 * @return
	 */
	public static MsgXmlDescriber getUnpackDesc(String pri_key){
		return unpackXmlMap.get(pri_key);
	}
	
	/**
	 * 获取组包的xml对象
	 * @param pri_key	systemcode_trancode
	 * @return
	 */
	public static MsgXmlDescriber getpackDesc(String pri_key){
		return packXmlMap.get(pri_key);
	}

	@Override
	public boolean preload() throws HandlerException {
		logger.info("start preload");
		tmpUnpackXmlMap = new HashMap<String, MsgXmlDescriber>();
		tmpPackXmlMap = new HashMap<String, MsgXmlDescriber>();
		logger.info("unpack data size is ["+unpackXmlMap.size()+"] pack data size is ["+packXmlMap.size()+"]");
		logger.info("unpack tmp data size is ["+tmpUnpackXmlMap.size()+"] pack tmp data size is ["+tmpPackXmlMap.size()+"]");
		load(tmpUnpackXmlMap,tmpPackXmlMap);
		logger.info("unpack tmp data size is ["+tmpUnpackXmlMap.size()+"] pack tmp data size is ["+tmpPackXmlMap.size()+"]");
		logger.info("XmlFilePreloader preload finish");
		return true;
	}

	@Override
	public void reload() throws HandlerException {
		logger.info("start reload");
		unpackXmlMap = tmpUnpackXmlMap;
		packXmlMap = tmpPackXmlMap;
		tmpPackXmlMap = null;
		tmpUnpackXmlMap = null;
		logger.info("unpack data size is ["+unpackXmlMap.size()+"] pack data size is ["+packXmlMap.size()+"]");
	}

	@Override
	public void failHand() throws HandlerException {
		if(tmpPackXmlMap!=null){
			tmpPackXmlMap.clear();
		}
		if(tmpUnpackXmlMap!=null){
			tmpUnpackXmlMap.clear();
		}
		tmpPackXmlMap = null;
		tmpUnpackXmlMap = null;
	}

	@Override
	public String getName() {
		return this.getClass().getName();
	}

	@Override
	public boolean doCommand(Map<String,String> commandparam) throws HandlerException {
		return this.preload();
	}
	@Override
	public void cancleCommand() throws HandlerException {
		this.failHand();
	}
	@Override
	public void confirmOpr() throws HandlerException {
		this.reload();
	}
}
