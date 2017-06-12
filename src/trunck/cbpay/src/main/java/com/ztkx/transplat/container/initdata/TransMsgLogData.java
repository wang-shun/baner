package com.ztkx.transplat.container.initdata;

import java.io.ByteArrayInputStream;

import org.apache.log4j.Logger;

import com.ztkx.transplat.container.initload.AbstractDbMapper;
import com.ztkx.transplat.platformutil.db.c3p0.DataSourceUtil;
import com.ztkx.transplat.platformutil.time.TimeUtil;

/**
 * 此类完成对trans_msg_log(交易报文记录)表的相关操作
 * 
 * @author tianguangzhao update by zhangxiaoyun 2016年1月11日14:07:11
 *         1.将blog字段inser修改为一次插入流程,提高效率
 */
public class TransMsgLogData extends AbstractDbMapper {
	static Logger logger = Logger.getLogger(TransMsgLogData.class);

	/**
	 * 
	 * @param flowNo
	 *            交易流水号
	 * @param tranDate
	 *            平台日期
	 * @param MsgOrder
	 *            报文顺序号
	 * @param msg
	 *            报文内容
	 * @throws Exception
	 */
	public void insertLog(String flowNo, String tranDate, String MsgOrder,
			byte[] msg) throws Exception {
		// 获取连接
		getConnection();
		try {
			String insertSql = "insert into trans_msg_log(flowno,Trandate,Trantime,Msg_order,MSG) values(?,?,?,?,?)";
			if (logger.isDebugEnabled()) {
				logger.debug("insert sql = " + insertSql);
				logger.debug("prams flowNo= " + flowNo);
				logger.debug("prams tranDate= " + tranDate);
				logger.debug("prams MsgOrder= " + MsgOrder);
			}

			psmt = DataSourceUtil.getPreparedStatement(connection, insertSql);
			psmt.setString(1, flowNo);
			psmt.setString(2, tranDate);
			psmt.setString(3, TimeUtil.getCurrentTime("HHmmss"));
			psmt.setString(4, MsgOrder);
			ByteArrayInputStream bis = new ByteArrayInputStream(msg);
			psmt.setBlob(5, bis);
			psmt.executeUpdate();
		} catch (Exception e) {
			logger.error("insert  trans_msg_log error !", e);
			throw e;
		} finally {
			relaceResource();
		}

	}
}
