package com.ztkx.transplat.container.msglistener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.ztkx.transplat.container.constant.ContainerConstantField;
import com.ztkx.transplat.container.frame.AdapterFrame;
import com.ztkx.transplat.container.frame.FramFactory;
import com.ztkx.transplat.container.util.ContextUtil;
import com.ztkx.transplat.platformutil.baseconfig.BaseConfig;
import com.ztkx.transplat.platformutil.baseconfig.ConstantConfigField;
import com.ztkx.transplat.platformutil.context.CommonContext;
import com.ztkx.transplat.platformutil.context.imp.ContextManager;
import com.ztkx.transplat.platformutil.serialize.SerializeContextUtil;
import com.ztkx.transplat.platformutil.threadpool.ThreadPoolManager;
import com.ztkx.transplat.platformutil.time.TimeUtil;

/**
 * ActiveMQ接收端的监听类，负责将queue中的信息接收，并进行后续处理（in容器监听类）。
 * 
 * @author tianguangzhao
 *
 */
public class TempletMsgListener implements MessageListener {
	static Logger logger = Logger.getLogger(TempletMsgListener.class);
	// 默认超时时间如果从文件中获取失败，则使用默认值,配置文件的内容启动时已经读入内存，所以此处实际是从内存中读取。
	long timeoutMillisecond = 60 * 1000L;
	private String startModel = "";

	
	
	public TempletMsgListener() {
		try {
			// 从配置文件中获取超时时长
			timeoutMillisecond = Long.parseLong(BaseConfig.getConfig(ConstantConfigField.OVER_TIME));

			if (logger.isDebugEnabled()) {
				logger.debug("get timeoutMillisecond success ! timeoutMillisecond = [ "+ timeoutMillisecond + " ]!");
			}

		} catch (Exception e) {
			logger.error("get timeoutMillisecond error param is [ "+ ConstantConfigField.OVER_TIME+ " ] !  now use defult value  [ " + timeoutMillisecond+ " ]", e);
		}
	}

	@Override
	public void onMessage(Message arg0) {
		if (logger.isDebugEnabled()) {
			logger.debug(" message listener start work ! ");
		}

		long sendTime = 1L;
		boolean flag = false;
		try {
			sendTime = arg0.getJMSTimestamp();
			flag = compareTime(sendTime);

			if (logger.isDebugEnabled()) {
				logger.debug(" judge overtime start : flag = " + flag);
			}
		} catch (JMSException e) {
			logger.error("get message over time error !", e);
			return;
		}

		// 进行判断，如果未超时则获取线程进行后续处理，如 果超时结束操作
		if (flag) {

			// 首先判断类型是否是BytesMessage，我们自己的消息都是BytesMessage,如果消息类型错误则结束。
			if (arg0 instanceof BytesMessage) {

				BytesMessage bm = (BytesMessage) arg0;
				Map<String,String> map = null;
				try {
					map = (Map<String,String>) bm.getObjectProperty(ConstantConfigField.MSG_PROPERTIES);
					startModel = map.get(ContainerConstantField.JMS_MESSAGE_FROM);

					if (logger.isDebugEnabled()) {
						logger.debug("get startModel success ! startModel = [ "
								+ startModel + " ]!");
					}
				} catch (JMSException e) {
					logger.error(
							"get jms message start model error ! param name is [ "
									+ ContainerConstantField.JMS_MESSAGE_FROM
									+ " ]", e);
					return;
				}

				// 判断消息的发送方名称，如果为空，则丢弃不作处理。
				if (StringUtils.isEmpty(startModel)) {
					logger.info("message error , can not get start model name ! param name is [ "
							+ ContainerConstantField.JMS_MESSAGE_FROM
							+ " ] !");

					return;
				} else {

					// 判断消息的发起方,根据来源不同调用不同的方法进行处理;
					if (startModel
							.equalsIgnoreCase(ConstantConfigField.CONTAINER_NAME_DISCENTER)) {
						// // 如果消息来自DISCENTER模块，进行如下处理
						handleDiscenterMessage(bm);
					} else if (startModel
							.equalsIgnoreCase(ConstantConfigField.CONTAINER_NAME_OUT)) {
						// 如果消息来自OUT模块，进行如下处理
						handleOutMessage(bm);
					} else if (startModel
							.equalsIgnoreCase(ConstantConfigField.CONTAINER_NAME_CONSOLE)) {
						// 如果消息来自CONSOLE模块，进行如下处理
						handleConsoleMessage(bm);
					} else if (startModel
							.equalsIgnoreCase(ConstantConfigField.CONTAINER_NAME_IN)) {
						// 消息来自in模块，在out容器接收in容器发送的报文时使用
						handleInMessage(bm);

					} else {

						// 消息来源于未知模块结束操作!
						logger.info("message come from wrong model ! model name is [ "
								+ startModel + " ] ");
						return;
					}

				}

			} else {
				logger.info("message type error !");
				return;
			}

		} else {
			// 超时后打印超时信息，并将message强转String打印
			logger.error(" message over time ! message is [ " + arg0.toString()
					+ " ] ");
			return;
		}
	}

	/**
	 * 此方法处理来自discenter模块的信息，由于discenter传入的是最原始信息，所以需要先将相关消息解析并封装进cbpayConext
	 * 
	 * @param bm
	 */
	public void handleDiscenterMessage(BytesMessage bm) {
		// 获取context来转载信息
		CommonContext context = ContextManager.getInstance().getContext();

		try {
			// 先要将message中的相关属性取出，并封装到context中。

			// 取出交易来源
			Map<String,String> map = (Map<String,String>) bm.getObjectProperty(ConstantConfigField.MSG_PROPERTIES);
			context.getSDO().tranFrom = map.get(ContainerConstantField.TRANFROM);

			// 将源模块注入context
			context.setFieldStr(ContainerConstantField.JMS_MESSAGE_FROM,
					startModel);

			// 将传来的原始报文放到全局map中，

			byte[] originalmsg = resolveByteArrayMessageFromJMSMessage(bm);

			context.setByteArray(ConstantConfigField.ORIGINAL_MSG,
					originalmsg, CommonContext.SCOPE_GLOBAL);

			// 将BytesMessage也存入context中，所有的信息就都保存在context中了
			context.setObj(ContainerConstantField.JMS_MSG_OBJ, bm);

		} catch (JMSException e) {
			logger.error("get message propertys error ! ", e);
			return;
		}

		// 启动frame
		startAsynFram(context);

	}

	/**
	 * 此方法处理来自console模块的信息，具体待定
	 * 
	 * @param bm
	 */
	public void handleConsoleMessage(BytesMessage bm) {

	}

	/**
	 * 此方法处理来自out模块的请求,out容器和in容器之间消息传递时都是将context序列化再传输,所以需将信息反序列化得到context
	 * 
	 * @param bm
	 */
	public void handleOutMessage(BytesMessage bm) {
		// BytesMessage还原为context
		CommonContext tmpcontext = convertMessageToContext(bm);
		CommonContext context = ContextManager.getInstance().getContext();
		ContextUtil.contextCopy(tmpcontext, context);
		// 启动frame，异步处理请求
		startAsynFram(context);

	}

	/**
	 * 此方法处理来自in模块的请求(out容器监听时
	 * ),out容器和in容器之间消息传递时都是将context序列化再传输,所以需将信息反序列化得到context
	 * 
	 * @param bm
	 */
	public void handleInMessage(BytesMessage bm) {

		// BytesMessage还原为context
		CommonContext tmpcontext = convertMessageToContext(bm);
		CommonContext context = ContextManager.getInstance().getContext();
		ContextUtil.contextCopy(tmpcontext, context);
		// 启动frame,采用同步方式
		startSynFram(context);

	}

	/**
	 * 此方法功能为：如果BytesMessage中传输的信息是CommonContext转换而来的，
	 * 就把BytesMessage中的数据还原为CommonContext
	 * 
	 * @param BytesMessage
	 * @return CommonContext
	 * 
	 */
	public CommonContext convertMessageToContext(BytesMessage bm) {

		CommonContext context = null;

		byte[] bytes = resolveByteArrayMessageFromJMSMessage(bm);

		// 调用工具类，进行反序列化，得到原始context对象
		Object object = SerializeContextUtil.unserialize(bytes);

		context = (CommonContext) object;
		// 打印相关报文调试
		if (logger.isDebugEnabled()) {
			logger.debug(" message come from " + startModel
					+ " convertMessageToContext success ! ");
		}

		return context;
	}

	/**
	 * 此方法将BytesMessage,中写入的byte数组取出还原
	 * 
	 * @param BytesMessage
	 * 
	 * @return byte 数组
	 * @throws JMSException
	 */
	public byte[] resolveByteArrayMessageFromJMSMessage(BytesMessage bm) {
		ByteArrayOutputStream writer = new ByteArrayOutputStream();
		byte[] buff = new byte[1024];
		int count = 0;
		try {
			while ((count = bm.readBytes(buff)) != -1) {
				writer.write(buff, 0, count);
			}
			return writer.toByteArray();
		} catch (JMSException e) {
			logger.error("resolve byteArrays from BytesMessage  error !", e);
			return null;
		} finally {
			try {
				writer.close();
			} catch (IOException e) {
				logger.error("close ByteArrayOutputStream error !", e);
			}
		}

	}

	/**
	 * 此方法将启动异步服务，获取fram并将context对象注入,然后开启线程
	 * 
	 * @param context
	 */
	public void startAsynFram(CommonContext context) {
		AdapterFrame fram = FramFactory.getAsynAdapterFram();
		fram.setContext(context);

		if (logger.isDebugEnabled()) {
			logger.debug("start fram context = " + context.toString());
		}
		// 启动线程开始执行
		ThreadPoolManager.getExecutorService().execute(fram);
	}

	/**
	 * 此方法将同步启动服务，获取fram并将context对象注入。然后程序顺序进行
	 * 
	 * @param context
	 */
	public void startSynFram(CommonContext context) {
		AdapterFrame fram = FramFactory.getSynAdapterFram();
		fram.setContext(context);

		if (logger.isDebugEnabled()) {
			logger.debug("start fram context = " + context.toString());
		}
		// 启动线程开始执行
		ThreadPoolManager.getExecutorService().execute(fram);
	}

	public boolean compareTime(long sendTime) {
		boolean flag = TimeUtil.compareWithCurrentTime(sendTime,timeoutMillisecond);
		return flag;
	}
}
