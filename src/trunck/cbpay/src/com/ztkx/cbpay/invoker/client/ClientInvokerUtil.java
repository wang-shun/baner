package com.ztkx.cbpay.invoker.client;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ztkx.cbpay.container.constant.ContainerConstantField;
import com.ztkx.cbpay.invoker.bean.InvokerParams;
import com.ztkx.cbpay.platformutil.activemq.messagesend.SenderUtil;
import com.ztkx.cbpay.platformutil.baseconfig.ConstantConfigField;

/** 
 * 客户端发送命令的工具类
 * @author  zhagnxiaoyun: 
 * @date 2016年7月5日 下午3:05:46 
 */
public class ClientInvokerUtil {
	private static Logger logger = Logger.getLogger(ClientInvokerUtil.class); 
	/**
	 * 将命令发送到服务端
	 * 2016年7月5日 下午3:34:40
	 * @author zhangxiaoyun
	 * @param param
	 * @return
	 * @throws Exception
	 * @return Map<String,InvokerParams>	自己发送命令的一个副本，客户端根据此副本判断是否所有的执行结果已经返回
	 */
	public static Map<String,InvokerParams> sendInvokParam(InvokerParams param) throws Exception{
		
		Map<String,InvokerParams> bufferMap = new HashMap<String,InvokerParams>();
		for(String targetNode:param.getTargetNodes()){
			//发送前克隆一份副本
			InvokerParams clonObj = param.clone();
			Map<String,String> titlemap = new HashMap<String,String>();
			titlemap.put(ConstantConfigField.CONTAINID,targetNode);
			clonObj.setCurrenttarget(targetNode);
			bufferMap.put(targetNode, clonObj);
			SenderUtil.sendObj(clonObj, ContainerConstantField.PROTOCOL_SERVICE_NAME_CONSOLE_OTHER, titlemap);
		}
		return bufferMap;
	}
	
	/**
	 * 根据返回的结果判断是否当前命令在所有节点上执行成功
	 * 2016年7月5日 下午3:35:53
	 * @author zhangxiaoyun
	 * @param bufferMap
	 * @param OverTime
	 * @return
	 * @return boolean
	 */
	public static boolean isSucc(Map<String,InvokerParams> bufferMap,long OverTime){
		
		int count = 0;
		while(bufferMap.size()>count){
			InvokerParams params = InvokerParamQueue.instance.take(OverTime);
			if(params == null){
				break;
			}
			InvokerParams tmp = bufferMap.get(params.getCurrenttarget());
			//判断当前当前命令批次号是否真确
			if(tmp == null || !tmp.getSerialId().equals(params.getSerialId())){
				logger.error("invoker serialid error ["+params+"]");
				continue;
			}
			count ++;
			logger.info("cureent target node is ["+params.getCurrenttarget()+"] res is ["+params.isSucc()+"]");
			bufferMap.put(params.getCurrenttarget(), params);
		}
		
		boolean isSucc = true;
		if(count == bufferMap.size()){
			//如果所有节点都有相应
			//检查是否全部节点执行成功
			for (Map.Entry<String, InvokerParams> entry : bufferMap.entrySet()) {
				if(!entry.getValue().isSucc()){
					isSucc = false;
					break;
				}
			}
		}else{
			isSucc = false;
		}
		return isSucc;
	}
}
