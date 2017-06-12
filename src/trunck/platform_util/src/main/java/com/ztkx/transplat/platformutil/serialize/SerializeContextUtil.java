package com.ztkx.transplat.platformutil.serialize;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.apache.log4j.Logger;

/**
 * 该类用于将java实体类的序列化和反序列化。
 * 
 * @author tianguangzhao
 *
 */
public class SerializeContextUtil {

	static Logger logger = Logger.getLogger(SerializeContextUtil.class);

	/**
	 * 该方法将java对象序列化为byte数组
	 * 
	 * @param object
	 * @return object序列化得到的 byte数组
	 */
	public static byte[] serialize(Object object) {
		ObjectOutputStream oos = null;
		ByteArrayOutputStream baos = null;
		try {
			// 序列化
			baos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baos);
			oos.writeObject(object);
			oos.flush();
			byte[] bytes = baos.toByteArray();
			// 调试阶段打印出数组内容方便测试
			/*if (logger.isDebugEnabled()) {
				logger.debug("serialize object success ! source = "
						+ object.toString() + " | result = "
						+ String.valueOf(bytes));
			}*/

			return bytes;
		} catch (Exception e) {
			logger.error(
					"serialize object error ! source =" + object.toString(), e);
			return null;
		} finally {
			try {
				if (oos != null) {
					oos.close();
				}
				if (baos != null) {
					baos.close();
				}
			} catch (IOException e) {
				logger.error(
						"close ByteArrayOutputStream and ObjectOutputStream error ! ",
						e);
			}
		}

	}

	/**
	 * 该方法byte数组反序列化为java对象
	 * 
	 * @param bytes
	 * @return byte数组反序列化得到的实体类
	 */
	public static Object unserialize(byte[] bytes) {
		ByteArrayInputStream bais = null;
		Object object = null;
		ObjectInputStream ois = null;
		try {
			// 反序列化
			bais = new ByteArrayInputStream(bytes);
			ois = new ObjectInputStream(bais);
			object = ois.readObject();
			// 调试阶段打印出数组内容方便测试
			/*if (logger.isDebugEnabled()) {
				logger.debug("unserialize object success ! source = "
						+ String.valueOf(bytes) + " | result ="
						+ object.toString());
			}*/
		} catch (Exception e) {
			logger.error(
					"unserialize object error ! source ="
							+ String.valueOf(bytes), e);
		} finally {
			try {
				if (ois != null) {
					ois.close();
				}
				if (bais != null) {
					bais.close();
				}
			} catch (IOException e) {
				logger.error(
						"close ByteArrayInputStream and ObjectInputStream error !",
						e);
			}

		}
		return object;
	}
}
