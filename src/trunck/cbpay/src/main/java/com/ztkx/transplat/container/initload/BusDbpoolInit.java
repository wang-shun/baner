package com.ztkx.transplat.container.initload;

import com.ztkx.transplat.container.preload.KeyMsgConfPreloader;
import com.ztkx.transplat.platformutil.db.mybatis.MybatisUtil;
import com.ztkx.transplat.platformutil.msg.KeyMsgDescriber;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;


public class BusDbpoolInit {
	private static BusDbpoolInit busDbpoolInit = null;
	private Logger logger = Logger.getLogger(BusDbpoolInit.class);
	private static Map<String,SqlSessionFactory> factoryMap = new HashMap<>();

	private BusDbpoolInit() {
		Map<String, KeyMsgDescriber> keyMsg = KeyMsgConfPreloader.getKeyMsg();

		for (String s : keyMsg.keySet()) {
			KeyMsgDescriber keyMsgDescriber = keyMsg.get(s);
			if (keyMsgDescriber.getType().equals("server")) {
				//如果当前系统是服务方，就按照当前系统id去dbpool目录下找对应的数据库配置文件
				String id = keyMsgDescriber.getId();
				logger.info("start load system [" + id + "] dbpool ");
				SqlSessionFactory sqlSessionFactory = MybatisUtil.loadDbpool(id);
				factoryMap.put(id, sqlSessionFactory);
			}

		}
	}
	
	public static BusDbpoolInit getInstance() {
		if (busDbpoolInit == null) {
			synchronized(BusDbpoolInit.class){
				if(busDbpoolInit == null){
					busDbpoolInit = new BusDbpoolInit();
				}
			}
		}
		return busDbpoolInit;
	}

	public static SqlSessionFactory getFactory(String serverid){
		return factoryMap.get(serverid);
	}


	// 应用程序关闭时直接销毁数据源
	protected void finalize() throws Throwable {
		super.finalize();
	}
}
