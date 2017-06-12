package cn.msec.cbpay.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.text.DecimalFormat;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.ztkx.cbpay.container.frame.FlowNoGenerator;
import com.ztkx.cbpay.container.protocol.ProtocolUtil;
import com.ztkx.cbpay.container.protocol.config.CommonConfig;
import com.ztkx.cbpay.container.protocol.config.ProtocolConfig;
import com.ztkx.cbpay.container.protocol.config.ProtocolConfigImp;
import com.ztkx.cbpay.container.protocol.config.RequestConfig;
import com.ztkx.cbpay.container.protocol.config.ResponseConfig;
import com.ztkx.cbpay.platformutil.baseconfig.BaseConfig;
import com.ztkx.cbpay.platformutil.log.FlowNoContainer;

public class TCPSender {
	static Logger logger = Logger.getLogger(TCPSender.class);
	public static String encoding = "GBK";
	private static ProtocolConfig protocolConfig = null;

	/**
	 * 这种单利不靠谱，但在这个场景下可以使用
	 */
	public static TCPSender getInstance(){
		return new TCPSender();
	}
	
	private TCPSender() {
		if (protocolConfig == null) {
			BaseConfig.getInstence(System.getProperty("container_path")+"/"+ConsoleConstant.CONFIG_FILE_PATH);
			protocolConfig = new ProtocolConfigImp();
			CommonConfig commnConfig = new CommonConfig();
			commnConfig.setId("");
			commnConfig.setType("tcp");
			commnConfig.setEncoding(encoding);
			commnConfig.setInOut("DataIn/DataOut");
			commnConfig.setModel("syn");
			commnConfig.setFlag("client");
			protocolConfig.setCommonConfig(commnConfig);

			String host = BaseConfig.getConfig(ConsoleConstant.TCPSERVERHOST);
			int port = Integer.parseInt(BaseConfig
					.getConfig(ConsoleConstant.TCPSERVERPORT));
			int connectTimeOut = Integer.parseInt(BaseConfig
					.getConfig(ConsoleConstant.TCPCONNECTTIMEOUT));
			int readTimeOut = Integer.parseInt(BaseConfig
					.getConfig(ConsoleConstant.TCPREADTIMEOUT));
			RequestConfig req = new RequestConfig(host, port, null,
					connectTimeOut, null, null, null, null, null, null, null,readTimeOut);
			req.setPolicy("dynamic_len");
			req.setMaxLen(999999);
			req.setStart(0);
			req.setEnd(5);
			req.setMsgHeadLen(6);
			req.setDf(new DecimalFormat("000000"));
			protocolConfig.setRequestConfig(req);
			ResponseConfig res = new ResponseConfig(readTimeOut, null, null,
					null, null, null, null, -1);
			res.setPolicy("dynamic_len");
			res.setMaxLen(999999);
			res.setStart(0);
			res.setEnd(5);
			res.setMsgHeadLen(6);
			res.setDf(new DecimalFormat("000000"));
			protocolConfig.setResponseConfig(res);
		}
	}
	
	public void before(JobExecutionContext context)
			throws JobExecutionException {
		String flowNo = FlowNoGenerator.instance.getFlowNo();
		/**
		 * 给当前线程注入交易流水号
		 */
		FlowNoContainer.setFlowNo(flowNo);
	}

	public byte[] sendMsg(byte[] msg) throws Exception {
		byte[] resMsg = null;
		OutputStream out = null;
		InputStream in = null;
		Socket socket = null;
		try {
			socket = createSocket();
			out = socket.getOutputStream();
			ProtocolUtil.writeBySocket(protocolConfig, msg, out, socket);
			in = socket.getInputStream();
			resMsg = ProtocolUtil.read(protocolConfig, in);
		} catch (IOException e) {
			logger.error("protocol send msg exception ", e);
			throw new Exception(e);
		} finally {
			/**
			 * 释放资源
			 */
			freeSystemSource(in, out, socket);
		}
		return resMsg;
	}

	/**
	 * 释放系统资源
	 * 
	 * @param in
	 * @param out
	 *            2016年3月6日 上午9:41:06
	 * @author zhangxiaoyun
	 */
	private void freeSystemSource(InputStream in, OutputStream out,
			Socket socket) {
		if (in != null) {
			try {
				in.close();
			} catch (IOException e) {
				logger.error("close inputstream exception", e);
			}
		}
		if (out != null) {
			try {
				out.close();
			} catch (IOException e) {
				logger.error("close outputStream exception", e);
			}
		}
		if (socket != null && !socket.isClosed()) {
			try {
				socket.close();
			} catch (IOException e) {
				logger.error("close client socket exception", e);
			}
		}
	}

	/**
	 * 创建客户端socket链接
	 * 
	 * @return
	 * @throws IOException
	 *             2016年3月23日 上午10:13:14
	 * @author zhangxiaoyun
	 */
	private Socket createSocket() throws IOException {
		String host = protocolConfig.getRequestConfig().getHost();
		int port = protocolConfig.getRequestConfig().getPort();
		int connectTimeOut = protocolConfig.getRequestConfig()
				.getConnectTimeout();
		int readTimeOut = protocolConfig.getResponseConfig().getReadTimeout();
		logger.debug("host [" + host + "] port [" + port + "] connectTimeOut ["
				+ connectTimeOut + "] readTimeOut [" + readTimeOut + "]");
		return ProtocolUtil.createSocketClient(host, port, connectTimeOut,
				readTimeOut);
	}

	/**
	 * 将javabean转换为xml
	 * 
	 * @param obj
	 *            javaben对象
	 * @param clazz
	 *            javabean的类
	 * @return
	 * @throws JAXBException
	 *             2016年3月23日 上午9:59:51
	 * @author zhangxiaoyun
	 */
	public byte[] beanToXml(Object obj, Class clazz) throws JAXBException {

		JAXBContext context = JAXBContext.newInstance(clazz);
		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);// 设置编码
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, false);// 设置不格式化输出
		marshaller.setProperty(Marshaller.JAXB_FRAGMENT, false);// 保存xml头
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		marshaller.marshal(obj, bos);
		return bos.toByteArray();
	}

	/**
	 * 将报文转化为javabean
	 * 
	 * @param msg
	 * @param clazz
	 * @return
	 * @throws JAXBException
	 *             2016年3月23日 下午2:04:42
	 * @author zhangxiaoyun
	 */
	public Object xmlTobean(byte[] msg, Class clazz) throws JAXBException {

		JAXBContext context = JAXBContext.newInstance(clazz);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		ByteArrayInputStream bis = new ByteArrayInputStream(msg);
		Object obj = unmarshaller.unmarshal(bis);
		return obj;
	}
}
