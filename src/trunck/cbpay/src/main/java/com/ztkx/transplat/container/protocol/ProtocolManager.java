package com.ztkx.transplat.container.protocol;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ztkx.transplat.container.initdata.ProtocolData;
import com.ztkx.transplat.container.initdata.ProtocolTypeData;
import com.ztkx.transplat.container.javabean.Protocol;
import com.ztkx.transplat.container.javabean.ProtocolType;
import com.ztkx.transplat.container.preload.PreLoadManager;
import com.ztkx.transplat.container.protocol.config.ProtocolConfig;
import com.ztkx.transplat.container.protocol.parser.ProtocolParserImp;
import com.ztkx.transplat.container.protocol.process.ProtocolProcess;
import com.ztkx.transplat.platformutil.threadpool.ThreadPoolManager;

/**
 * 平台所有协议的管理容器
 * @author zhangxiaoyun
 *
 */
public class ProtocolManager {
	private static ProtocolManager protocolManager = null;
	private Map<String,ProtocolProcess> processMap = new HashMap<String,ProtocolProcess>();		//协议id和协议的对应关系
	private Map<String,ProtocolProcess> systemid_Map = new HashMap<String,ProtocolProcess>();	//系统id和协议的对应关系
	private Logger logger = Logger.getLogger(ProtocolManager.class);
	
	private ProtocolManager() {
		List<Protocol> protocolList = ProtocolData.getInstance().getProtocolList();
		for (int i = 0; i < protocolList.size(); i++) {
			Protocol protocolBean = protocolList.get(i);
			final String id = protocolBean.getProtocolid();
			final String xmlConf = protocolBean.getXmlconf();
			String type = protocolBean.getProtocoltype();
			final String serverId = protocolBean.getServerid();
			ProtocolType protocolType =  ProtocolTypeData.getInstance().getProtocolType(type);
			String protocolConfig = protocolType.getProtocolconfig();	//目前没有用
			String protocolParse = protocolType.getProtocolparse();
			String protocolProcess = protocolType.getProtocolprocess();
			try {
				//初始化process
				final ProtocolParserImp parser = (ProtocolParserImp)(Class.forName(protocolParse).newInstance());
				//初始化process
				final ProtocolProcess process = (ProtocolProcess)(Class.forName(protocolProcess).newInstance());;
				/*
				switch (type) {
				case ProtocolConstantField.PROTOCOL_TYPE_HTTP_CLIENT:
					process = (HTTPClientProcessImp)(Class.forName(protocolProcess).newInstance());
					break;
				case ProtocolConstantField.PROTOCOL_TYPE_HTTPS_CLIENT:
					process = (HTTPSClientProcessImp)(Class.forName(protocolProcess).newInstance());
					break;
				case ProtocolConstantField.PROTOCOL_TYPE_JMS_CLIENT:
					process = (JMSClientProcessImp)(Class.forName(protocolProcess).newInstance());
					break;
				case ProtocolConstantField.PROTOCOL_TYPE_TCP_CLIENT:
					
					break;
				case ProtocolConstantField.PROTOCOL_TYPE_HTTP_SERVER:
					process = (HTTPServerProcessImp)(Class.forName(protocolProcess).newInstance());
					break;
				default:
					logger.error("error protocol type");
					break;
				}
				*/
				ThreadPoolManager.getExecutorService().execute(new Runnable() {
					@Override
					public void run() {
						try{
							ProtocolConfig config = parser.parse(xmlConf);
							config.getCommonConfig().setServerId(serverId);
							process.start(config);
							systemid_Map.put(serverId, process);
							processMap.put(id, process);
							if(logger.isDebugEnabled()){
								logger.debug("start protocol ["+id+"] success config info is ["+xmlConf+"]");
							}
						}catch(Exception e){
							logger.error("init protocol parser exception xmlConf["+xmlConf+"]",e);
						}
					}
				});
				
			} catch (Exception e) {
				logger.error("init protocol parser exception xmlConf["+xmlConf+"]",e);
			}
			
			
		}
	}
	
	public static ProtocolManager getInstance() {
		if (protocolManager == null) {
			synchronized(PreLoadManager.class){
				if(protocolManager == null){
					protocolManager = new ProtocolManager();
				}
			}
		}
		return protocolManager;
	}
	
	/**
	 * 根据协议id获取协议
	 * @param id
	 * @return
	 */
	@Deprecated
	public ProtocolProcess getProcotol(String id){
		return processMap.get(id);
	}
	
	/**
	 * 根据系统id获取对应系统的协议
	 * @param systemId		系统id
	 * @return
	 */
	public ProtocolProcess getProcotolBySysId(String systemId){
		return systemid_Map.get(systemId);
	}
	
}
