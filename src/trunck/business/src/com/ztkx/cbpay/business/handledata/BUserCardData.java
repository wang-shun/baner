package com.ztkx.cbpay.business.handledata;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ztkx.cbpay.business.bean.BUserCard;
import com.ztkx.cbpay.business.constant.BusinessConstantField;
import com.ztkx.cbpay.container.HandlerException;
import com.ztkx.cbpay.container.initload.AbstractDbMapper;
import com.ztkx.cbpay.platformutil.db.DBUtil;
import com.ztkx.cbpay.platformutil.db.c3p0.DataSourceUtil;
import com.ztkx.cbpay.platformutil.time.TimeUtil;
/**
 * 此类用于操作用户卡信息表
 * 
 *
 */
public class BUserCardData extends AbstractDbMapper {

	Logger logger = Logger.getLogger(BUserCardData.class);
	private static String tablename = "B_USER_CARD";

	/**
	 * 根据商户号和用户号，查找处于可用状态的卡信息
	 * @param merchantid
	 * @param usrid
	 * @return
	 * @throws HandlerException
	 */
	public List<Map<String, String>> getNormalCardInfo(String merchantid, String usrid)
			throws HandlerException {
		List<Map<String, String>> list = null;
		// 获取连接
		getConnection();
		String sqlStatement = "select * from  " + tablename + "  where  MERCHANTID = ?  and PURCHASERID = ? and VALID = ? order by tmsmp desc";
		try {
			LinkedHashMap map = new LinkedHashMap();
			map.put("MERCHANTID", merchantid);
			map.put("PURCHASERID", usrid);
			//此处查询处于可用状态的卡信息，
			map.put("VALID", BUserCard.VALID_NORMAL);
			 list = executeQuery(sqlStatement, map);
			 
			if (logger.isDebugEnabled()) {
				logger.debug(" select " + tablename + " table  sucess !");
			}
		} catch (HandlerException e) {
			logger.error("select " + tablename + " data exception !", e);
			throw e;
		} finally {
			relaceResource();

		}
		return list;
	}
	/**
	 * 将用户卡信息保存到数据库中
	 * 
	 * @param bUserCard
	 *            ,对应b_user_card 表的实体类
	 * @throws HandlerException
	 */
	public void insertUserCard(BUserCard bUserCard) throws HandlerException {
		// 获取连接
		// getConnection();
		String sqlStatement = "insert into " + tablename
				+ " (MERCHANTID,PURCHASERID,CARDNO,VALID,CREATEDATE,CREATETIME,BANKNAME,TMSMP) values(?,?,?,?,?,?,?,?)";
		try {
			LinkedHashMap map = new LinkedHashMap();
			map.put("MERCHANTID", bUserCard.getMerchantid());
			map.put("PURCHASERID",  bUserCard.getPurchaserId());
			map.put("CARDNO", bUserCard.getCardNo());
			//初次插入时状态为不可用，验证通过之后，状态改为可用
			map.put("VALID", BUserCard.VALID_DISABLE);
			map.put("CREATEDATE", TimeUtil.getCurrentTime(BusinessConstantField.PLA_DATE_FORMATE));
			map.put("CREATETIME", TimeUtil.getCurrentTime(BusinessConstantField.PLA_TIME_FORMATE));
			map.put("BANKNAME", bUserCard.getBankName());
			map.put("TMSMP", TimeUtil.getCurrentTime(BusinessConstantField.TIMESTAMP_FORMATE));
			executeUpdate(sqlStatement, map);
			if (logger.isDebugEnabled()) {
				logger.debug(" insert " + tablename + " table  sucess !");
			}
		} catch (HandlerException e) {
			logger.error("insert " + tablename + " table data error !", e);
			// update by tianguangzhao 2016/4/27 是否回滚交由业务层控制
			// try {
			// connection.rollback();
			// } catch (SQLException e1) {
			// logger.error("connection rollback error !");
			// throw new HandlerException(e1);
			// }
			throw e;
		}
	}

	/**
	 * 查询用户卡信息是否存在,用于用户实名认证交易
	 * 
	 * @param merchantno
	 *            商户号
	 * @param purchaserId
	 *            用户id
	 * @param acctNo
	 *            账号
	 * @return String ,如果用户卡信息不存在则返回null； 如果用户存在返回状态
	 * @throws HandlerException
	 */
	public String checkUserCardStatus(String merchantid, String purchaserId,String acctNo) throws HandlerException {

		String status = null;
		// 获取连接
		getConnection();

		String sqlStatement = "select VALID from  " + tablename + "  where  MERCHANTID = ?  and PURCHASERID = ?  and CARDNO = ?";
		try {
			LinkedHashMap map = new LinkedHashMap();
			map.put("MERCHANTID", merchantid);
			map.put("PURCHASERID", purchaserId);
			map.put("CARDNO", acctNo);
			List<Map<String, String>> list = executeQuery(sqlStatement, map);
			if(list !=null&&list.size()!=0){
				if (list.size() > 1) {
					String message = "user card data error ! same card num has " + list.size() + " records !";
					logger.error(message);
					throw new HandlerException(message);
				}
				// 查询数据库时最多只有一条记录，如果出现多条则说明系统出现问题
				Map<String, String> resultMap = list.get(0);
				status = resultMap.get("VALID");
			}
			if (logger.isDebugEnabled()) {
				logger.debug(" select " + tablename + " table  sucess ! user card status =[" + status + "]");
			}
		} catch (HandlerException e) {
			logger.error("select " + tablename + " data exception !", e);
			throw e;
		} finally {
			relaceResource();
		}
		return status;
	}

	/**
	 * 查询数据库中的卡信息，获取开户行名称等信息,用于支付交易和用户支付前的实名认证
	 * 
	 * @param merchantid
	 * @param usrid
	 * @param cardNum
	 * @return
	 * @throws HandlerException
	 */
	public List<Map<String, String>> getUserCardInfo(String merchantid,
			String usrid, String cardNum) throws HandlerException {
		List<Map<String, String>> list = null;
		// 获取连接
		getConnection();

		String sqlStatement = "select * from  " + tablename + "  where  MERCHANTID = ?  and PURCHASERID = ? and VALID = ? and CARDNO = ? ";
		try {	
			LinkedHashMap map = new LinkedHashMap();
			map.put("MERCHANTID", merchantid);
			map.put("PURCHASERID", usrid);
			//此处查询处于可用状态的卡信息，
			map.put("VALID", BUserCard.VALID_NORMAL);
			map.put("CARDNO", cardNum);
			list = executeQuery(sqlStatement, map);	
			
			if (logger.isDebugEnabled()) {
				logger.debug(" select " + tablename + " table  sucess !");
			}
		} catch (HandlerException e) {
			logger.error("select " + tablename + " data exception !", e);
			throw e;
		} finally {
			relaceResource();

		}
		return list;
	}

	/**
	 * 更新卡信息表中的卡状态，用于验证通过后将卡信息置为可用，并记录更新时间
	 * 
	 * @param merchantid
	 *            商户号
	 * @param purchaserid
	 *            用户号
	 * @param cardNum
	 *            卡号
	 * @param status
	 *            状态
	 * @throws HandlerException
	 */
	public int updateUserCardStatus(String merchantid, String purchaserid,String cardNum, String status) throws HandlerException {
		// 获取连接
		int result = 0;
		String sqlStatement = "update " + tablename + " set VALID = ? ,TMSMP = ? where MERCHANTID = ?  and PURCHASERID = ? and CARDNO = ? ";

		try {	
			LinkedHashMap map = new LinkedHashMap();
			map.put("VALID",status);
			map.put("TMSMP", TimeUtil.getCurrentTime(BusinessConstantField.TIMESTAMP_FORMATE));
			map.put("MERCHANTID", merchantid);
			//初次插入时状态为不可用，验证通过之后，状态改为可用
			map.put("PURCHASERID", purchaserid);
			map.put("CARDNO", cardNum);
			result= executeUpdate(sqlStatement, map);
			
			if (logger.isDebugEnabled()) {
				logger.debug(" update table " + tablename + " sucess ! ");
			}
		} catch (HandlerException e) {
			logger.error("update " + tablename + " data exception !", e);
			throw e;
		}
		return result;

	}
	
	/**
	 * 查询表中的数据
	 * 
	 * @param sqlStatement
	 *            需要查询的sql语句
	 * @param map
	 *            参数
	 * @return 符合要求的list集合
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
	public int executeUpdate(String sqlStatement,LinkedHashMap<String, String> map) throws HandlerException {

		logger.info("sql[" + sqlStatement + "]");
		logger.info("params map is " + map);
		int res = 0;
		try {
			if (map != null && map.size() > 0) {
				// 如果list不是null按照psmt的方式处理
				psmt = DataSourceUtil.getPreparedStatement(connection,
						sqlStatement);
				// 轮询将所有参数注入到psmt中
				DBUtil.preparePsmt(map, psmt);
				// 执行sql语句
				res = psmt.executeUpdate();
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

}
