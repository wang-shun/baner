

import javax.jms.JMSException;

import org.apache.activemq.Message;
import org.apache.activemq.command.ActiveMQBytesMessage;
import org.apache.log4j.Logger;

import com.ztkx.cbpay.container.constant.ContainerConstantField;
import com.ztkx.cbpay.container.msglistener.TempletMsgListener;
import com.ztkx.cbpay.platformutil.baseconfig.ConstantConfigField;

public class ListenerTest {
	static Logger logger = Logger.getLogger(ListenerTest.class);
	public static void main(String[] args) {
		TempletMsgListener tml = new TempletMsgListener();
		Message message = new ActiveMQBytesMessage();
		try {
			message.setStringProperty(ContainerConstantField.TRANFROM,
					"zhongxin");
			message.setStringProperty(ConstantConfigField.ORIGINAL_MSG,
					"ksdfkl");
			message.setStringProperty(
					ContainerConstantField.JMS_MESSAGE_FROM, "DISCENTER");
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tml.onMessage(message);
	}
}
