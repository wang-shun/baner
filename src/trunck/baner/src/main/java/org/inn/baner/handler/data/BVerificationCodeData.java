package org.inn.baner.handler.data;

import com.ztkx.cbpay.business.bean.BVerificationCode;
import com.ztkx.cbpay.business.constant.BusinessConstantField;
import com.ztkx.cbpay.container.HandlerException;
import com.ztkx.cbpay.container.initload.AbstractDbMapper;
import com.ztkx.cbpay.platformutil.db.DBUtil;
import com.ztkx.cbpay.platformutil.db.c3p0.DataSourceUtil;
import com.ztkx.cbpay.platformutil.time.TimeUtil;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 此类完成对B_VERIFICATION_CODE(短信验证码表 )表的相关操作,暂时未用到，
 *
 * @author tianguangzhao
 */
public class BVerificationCodeData extends AbstractDbMapper {

	static Logger logger = Logger.getLogger(BVerificationCodeData.class);
	static String tableName = "B_VERIFICATION_CODE";

	/**
	 * 将短信验证码信息保存入库
	 *
	 * @param verificationCode
	 *            封装有短信验证码等信息的实体类
	 * @throws Exception
	 */
	public void insert(BVerificationCode verificationCode)throws HandlerException {

		if (logger.isDebugEnabled()) {
			logger.debug("start insert VerificationCode values is ["
					+ verificationCode.toString() + "]");
		}
		String sqlStatement = "insert into "
				+ tableName
				+ " (VERJNLNO,MBLNO,VERCHNL,VERBIZTYP,VERCODE,PRDTIME,STATUS,TMSMP,PRDDATE) values(?,?,?,?,?,?,?,?,?)";

		try {
			//获取时间戳
			String time = TimeUtil.getCurrentTime(BusinessConstantField.TIMESTAMP_FORMATE);
			LinkedHashMap map = new LinkedHashMap();
			List<LinkedHashMap<String, String>> list = new ArrayList<LinkedHashMap<String, String>>();
			map.put("VERJNLNO", verificationCode.getVerJnlNo());
			//支付系统流水号应该由宝易互通提供
			map.put("MBLNO", verificationCode.getMblNo());
			map.put("VERCHNL",  verificationCode.getVerChnl());
			map.put("VERBIZTYP", verificationCode.getVerBizTyp());
			map.put("VERCODE", verificationCode.getVerCode());
			map.put("PRDTIME", verificationCode.getPrdTime());
			map.put("STATUS", BVerificationCode.CODE_SATUS_DISABLE);
			map.put("TMSMP", time);
			map.put("PRDDATE", verificationCode.getPrdDate());
			list.add(map);
			batchExecuteUpdate(sqlStatement, list);

			if (logger.isDebugEnabled()) {
				logger.debug(" insert  table  sucess !");
			}
		} catch (HandlerException e) {
			logger.error("insert  table data error !", e);
			/**update by tianguangzhao 2016/4/27 事务回滚交由上层处理
			try {
				connection.rollback();
			} catch (SQLException e1) {
				logger.error("connection rollback error !");
				throw new HandlerException(e1);
			}*/
			throw e;
		}
	}

	/**
	 * 查询数据库中是否已经存在该用户的验证码,暂时未用到!
	 *
	 * @param mblNo
	 *            客户标识号
	 * @param verBizTyp
	 *            业务类型
	 * @return
	 * @throws Exception
	 */
	public boolean checkSMSExist(String mblNo, String verBizTyp) throws HandlerException {
		boolean flag = false;
		// 获取连接
		getConnection();
		String sqlStatement = "select count(1) as NUM from  " + tableName + "  where  MBLNO = ? and VERBIZTYP = ? and STATUS = ? ";
		try {
			LinkedHashMap map = new LinkedHashMap();
			map.put("MBLNO", mblNo);
			//支付系统流水号应该由宝易互通提供
			map.put("VERBIZTYP",verBizTyp);
			map.put("STATUS", BVerificationCode.CODE_SATUS_NOMARL);
		   List<Map<String,String>> list = executeQuery(sqlStatement, map);
			if(list !=null&&list.size()!=0){
				Map<String,String> resultMap = list.get(0);
				String num = resultMap.get("NUM");
				if (num != null && !num.equals("0")) {
					flag = true;
				}
			}
			if (logger.isDebugEnabled()) {
				logger.debug(" select  table "+tableName+" sucess ! Exist flag =[" + flag + "]");
			}
		} catch (HandlerException e) {
			logger.error("select data from table "+tableName+" exception !", e);
			throw e;
		} finally {
			relaceResource();
		}
		return flag;
	}

	/**
	 * 更新上次验证码的状态为失效
	 *
	 * @param mblNo
	 *            用户标识
	 * @param verBizTyp
	 *            验证类型。支付或实名认证
	 */
	public int updateStatus(String mblNo, String verBizTyp,String status) throws HandlerException {
		int count =0;
		String sqlStatement = "update  " + tableName + "  set  STATUS = ? , TMSMP = ? where  MBLNO = ? and VERBIZTYP = ? ";
		try {
			String time = TimeUtil.getCurrentTime(BusinessConstantField.TIMESTAMP_FORMATE);
			LinkedHashMap map = new LinkedHashMap();
			List<LinkedHashMap<String, String>> list = new ArrayList<LinkedHashMap<String, String>>();
			map.put("STATUS", status);
			//支付系统流水号应该由宝易互通提供
			map.put("TMSMP", time);
			map.put("MBLNO", mblNo);
			map.put("VERBIZTYP", verBizTyp);
			list.add(map);
			count=batchExecuteUpdate(sqlStatement, list);

			if (logger.isDebugEnabled()) {
				logger.debug(" update  table "+tableName+" sucess !");
			}
		} catch (HandlerException e) {
			logger.error("update  table "+tableName+" exception !", e);
			throw e;
		}
		return count;
	}

	/**
	 * 批量的执行update操作
	 *
	 * @param sqlStatement
	 *            需要执行的sql语句
	 * @param map
	 *            sql中的问号参数值 如果map为空默认按传统的statement方式执行sql
	 * @return 修改的记录数 2016年3月14日 下午2:19:21
	 * @author zhangxiaoyun
	 * @throws HandlerException
	 */
	public int batchExecuteUpdate(String sqlStatement,List<LinkedHashMap<String, String>> list) throws HandlerException {
		logger.info("sql[" + sqlStatement + "]");
		logger.info("params list is " + list);
		int res = 0;
		try {
			if (list != null && list.size() > 0) {
				// 如果list不是null按照psmt的方式处理
				psmt = DataSourceUtil.getPreparedStatement(connection,
						sqlStatement);

				LinkedHashMap<String, String> map = null;
				if (list.size() == 1) {
					map = list.get(0);
					// 轮询将所有参数注入到psmt中
					DBUtil.preparePsmt(map, psmt);
					// 执行sql语句
					res = psmt.executeUpdate();
				} else {
					// 批量更新
					for (int i = 0; i < list.size(); i++) {
						map = list.get(i);
						// 轮询将所有参数注入到psmt中
						DBUtil.preparePsmt(map, psmt);
						psmt.addBatch();
						if ((i + 1) % BusinessConstantField.BATCHNUM == 0) {
							res = res + psmt.executeBatch().length;
						}
					}
					// 最后在补个刀
					res = res + psmt.executeBatch().length;
				}
			} else {
				// 按照stmt的方式处理
				stmt = DataSourceUtil.getStatement(connection);
				res = stmt.executeUpdate(sqlStatement); // 执行sql语句
			}
		} catch (SQLException e) {
			logger.error("execute sqlstatement exception", e);
			throw new HandlerException(e);
		}
		return res;
	}

	/**
	 * 执行参数sql语句返回查询结果
	 *
	 * @param sqlStatement
	 *            需要执行的sql语句
	 * @param map
	 *            sql中的问号参数值 如果map为空默认按传统的statement方式执行sql
	 * @return 2016年3月14日 下午2:19:21
	 * @author zhangxiaoyun
	 * @throws HandlerException
	 */
	public List<Map<String, String>> executeQuery(String sqlStatement,LinkedHashMap<String, String> map) throws HandlerException {

		getConnection();
		logger.info("sql[" + sqlStatement + "]");
		logger.info("params is " + map);
		List<Map<String, String>> resList = null;
		try {
			if (map != null) {
				// 如果map不是null按照psmt的方式处理
				psmt = DataSourceUtil.getPreparedStatement(connection,
						sqlStatement);
				DBUtil.preparePsmt(map, psmt);
				// 执行sql语句
				rs = psmt.executeQuery();
			} else {
				// 按照stmt的方式处理
				stmt = DataSourceUtil.getStatement(connection);
				rs = stmt.executeQuery(sqlStatement);
			}
			// 将最终的查询结果转化为list
			resList = DBUtil.resConvertList(rs);
			if (resList.size() <= 0) {
				resList = null;
			}
		} catch (SQLException e) {
			logger.error("execute sqlstatement exception", e);
			throw new HandlerException(e);
		} finally {
			// 释放资源
			relaceResource();
		}
		return resList;

	}

	/**
	 * 下发验证码成功后根据交易流水号，更新数据
	 * @param flowNo 交易流水号，对应表中的VERJNLNO字段
	 * @return
	 * @throws HandlerException
	 */
	public int updateStatusByFlowNo(String flowNo) throws HandlerException {
		int count =0;
		String sqlStatement = "update  " + tableName + "  set  STATUS = ? , TMSMP = ? , PRDDATE = ? , PRDTIME = ? where VERJNLNO = ? ";
		try {
			LinkedHashMap map = new LinkedHashMap();
			List<LinkedHashMap<String, String>> list = new ArrayList<LinkedHashMap<String, String>>();
			//验证成功之后根据主键更新验证信息状态
			map.put("STATUS", BVerificationCode.CODE_SATUS_NOMARL);
			String time = TimeUtil.getCurrentTime(BusinessConstantField.TIMESTAMP_FORMATE);
			map.put("TMSMP", time);
			
			String prddate =TimeUtil.getCurrentTime(BusinessConstantField.PLA_DATE_FORMATE);
			String prdtime =TimeUtil.getCurrentTime(BusinessConstantField.PLA_TIME_FORMATE);
			map.put("PRDDATE", prddate);
			map.put("PRDTIME", prdtime);
			
			map.put("VERJNLNO", flowNo);
			list.add(map);
			count=batchExecuteUpdate(sqlStatement, list);		
			if (logger.isDebugEnabled()) {
				logger.debug(" update  table "+tableName+" sucess !");
			}
		} catch (HandlerException e) {
			logger.error("update  table "+tableName+" exception !", e);
			throw e;
		}
		return count;
	}
	
}
