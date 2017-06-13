package org.inn.baner.handler.data;

import com.ztkx.cbpay.business.bean.BUserInfo;
import com.ztkx.cbpay.business.constant.BusinessConstantField;
import com.ztkx.cbpay.container.HandlerException;
import com.ztkx.cbpay.container.initload.AbstractDbMapper;
import com.ztkx.cbpay.platformutil.db.DBUtil;
import com.ztkx.cbpay.platformutil.db.c3p0.DataSourceUtil;
import com.ztkx.cbpay.platformutil.time.TimeUtil;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 操作用户信息表，存取数据
 *
 * @author tianguangzhao
 *
 */
public class BUserInfoData extends AbstractDbMapper {

	Logger logger = Logger.getLogger(BUserInfoData.class);
	static String tableName = "B_USER_INFO";

	/**
	 * 将用户信息插入表中
	 *
	 * @param userInfo
	 * @throws HandlerException
	 */
	public void insert(BUserInfo userInfo) throws HandlerException {
		// 获取连接
		getConnection();

		String sqlStatement = "insert into " + tableName
				+ " (MERCHANTID,PURCHASERID,NICKNAME,USRSTS,REALNMFLG,REALNAME,IDTYP,IDNO,REGDATE,REGTIME,REGCHNL,EMAIL,TMSMP,TELNUM) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		try {
            //获取时间戳
			String time = TimeUtil.getCurrentTime(BusinessConstantField.TIMESTAMP_FORMATE);

			LinkedHashMap map = new LinkedHashMap();
			map.put("MERCHANTID",userInfo.getMerchantid());
			map.put("PURCHASERID",userInfo.getPurchaserid());
			map.put("NICKNAME",userInfo.getNickName());
			map.put("USRSTS",BUserInfo.USER_STATUS_DISABLE);
			map.put("REALNMFLG",BUserInfo.VERIFY_INIT);
			map.put("REALNAME",userInfo.getRealName());
			map.put("IDTYP",userInfo.getIdTyp());
			map.put("IDNO",userInfo.getIdNo());
			map.put("REGDATE",userInfo.getRegDate());
			map.put("REGTIME",userInfo.getRegTime());
			map.put("REGCHNL",userInfo.getRegChnl());
			map.put("EMAIL",userInfo.getEmail());
			map.put("TMSMP",time);
			map.put("TELNUM",userInfo.getTelnum());
		    executeUpdate(sqlStatement, map);

			if (logger.isDebugEnabled()) {
				logger.debug(" insert " + tableName + " table  sucess !");
			}
		} catch (HandlerException e) {
			logger.error("insert " + tableName + " table data error !", e);
			// update by tianguangzhao 2016/4/27 事务是否回滚 交由上层
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
	 * 查询数据库中是否存在该用户的信息(处于正常状态下),
	 *
	 * @param merchantid
	 *            商户id
	 * @param purchaserid
	 *            用户id
	 * @return
	 * @throws HandlerException
	 * update by tianguangzhao 2016/4/18 只检测用户是否存在，不关心状态，主键时商户号+用户号。如果一个用户注销了，下次再注册时也必须使用不同的用户号
	 */
	public boolean checkInfoExist(String merchantid, String purchaserid) throws HandlerException {

		boolean flag = false;

		// 获取连接
		getConnection();

		//String sqlStatement = "select count(1) as num from  " + tableName + "  where  MERCHANTID = ?  and PURCHASERID = ? and USRSTS = ? and REALNMFLG = ?";
		String sqlStatement = "select count(1) as num from  " + tableName + "  where  MERCHANTID = ?  and PURCHASERID = ? ";
		try {
			LinkedHashMap map = new LinkedHashMap();
			map.put("MERCHANTID", merchantid);
			map.put("PURCHASERID", purchaserid);
//			//此处查询处于可用状态的卡信息，
//			map.put("USRSTS", BUserInfo.USER_STATUS_NORMAL);
//			map.put("REALNMFLG", BUserInfo.VERIFY_PASS);
			List<Map<String, String>> list = executeQuery(sqlStatement, map);
			if(list !=null&&list.size()!=0){
				// 查询数据库时只查询了count所以只有一条记录
				Map<String, String> resultMap = list.get(0);
				int count = Integer.parseInt(resultMap.get("NUM"));
				// 按照业务规则，同一张卡不可能注册两次，所以只能有一条记录
				if (count == 1) {
					flag = true;
				}
			}


			if (logger.isDebugEnabled()) {
				logger.debug(" select " + tableName + " table  sucess ! Exist flag =[" + flag + "]");
			}
		} catch (HandlerException e) {
			logger.error("select " + tableName + " data exception !", e);
			throw e;
		} finally {
			relaceResource();

		}
		return flag;
	}

	/**
	 * 删除用户信息，暂时未用到
	 *
	 * @param merchantid
	 * @param purchaserid
	 * @throws SQLException
	 */
	public int userCancel(String merchantid, String purchaserid) throws HandlerException {
	    int count = 0;
		String sqlStatement = "update " + tableName + " set  USRSTS = ? ,TMSMP = ? where  MERCHANTID = ?  and PURCHASERID = ?  ";
		try {
			//新增更新时间戳，update by tianguangzhao 2016/4/11
			String time = TimeUtil.getCurrentTime(BusinessConstantField.TIMESTAMP_FORMATE);

			LinkedHashMap map = new LinkedHashMap();
			map.put("USRSTS",BUserInfo.USER_STATUS_DISABLE);
			map.put("TMSMP", time);
			map.put("MERCHANTID", merchantid);
			map.put("PURCHASERID", purchaserid);
			count= executeUpdate(sqlStatement, map);

			if (logger.isDebugEnabled()) {
				logger.debug(" update table " + tableName + " sucess ! ");
			}
		} catch (HandlerException e) {
			logger.error("update " + tableName + " data exception !", e);
			throw e;
		}
		return count;
	}

	/**
	 * 根据商户号和用户表示号查询用户信息
	 *
	 * @param merchantid
	 * @param purchaserid
	 * @return
	 * @throws HandlerException
	 */
	public List<Map<String, String>> getUserInfo(String merchantid,String purchaserid) throws HandlerException {
		List<Map<String, String>> list = null;
		// 获取连接
		getConnection();

		String sqlStatement = "select * from  " + tableName + "  where  MERCHANTID = ?  and PURCHASERID = ? and USRSTS = ? ";
		try {
			LinkedHashMap map = new LinkedHashMap();
			map.put("MERCHANTID", merchantid);
			map.put("PURCHASERID", purchaserid);
			//此处查询处于可用状态的卡信息，
			map.put("USRSTS", BUserInfo.USER_STATUS_NORMAL);
		    list = executeQuery(sqlStatement, map);

			if (logger.isDebugEnabled()) {
				logger.debug(" select " + tableName + " table  sucess !");
			}
		} catch (HandlerException e) {
			logger.error("select " + tableName + " data exception !", e);
			throw e;
		} finally {
			relaceResource();
		}
		return list;
	}

	/**
	 * 更新用户信息的状态，实名认证通过之后，更新用户信息表中的状态
	 *
	 * @param merchantid
	 * @param purchaserid
	 */
	public int updateUserStatus(String merchantid, String purchaserid,String usrSts, String realnmflg) throws HandlerException {
		int result = 0;
		String sqlStatement = "update   " + tableName+ " set  USRSTS = ? , REALNMFLG = ? , TMSMP = ? where  MERCHANTID = ?  and PURCHASERID = ? ";

		try {
			//新增更新时间戳，update by tianguangzhao 2016/4/11
			String time = TimeUtil.getCurrentTime(BusinessConstantField.TIMESTAMP_FORMATE);
			LinkedHashMap map = new LinkedHashMap();
			//修改用户状态
			map.put("USRSTS",usrSts);
			//修改用户实名认证标示
			map.put("REALNMFLG", realnmflg);
			map.put("TMSMP", time);
			map.put("MERCHANTID", merchantid);
			map.put("PURCHASERID", purchaserid);
			result= executeUpdate(sqlStatement, map);

			if (logger.isDebugEnabled()) {
				logger.debug(" update table " + tableName + " sucess ! ");
			}
		} catch (HandlerException e) {
			logger.error("update " + tableName + " data exception !", e);
			throw e;
		}
		return result;
	}

	/**
	 * 用户修改注册信息时调用，更新数据库中的信息,如果该客户以前注册过，重新发起注册，则按照此次的信息更新到数据库中
	 *
	 * @param userInfo
	 * @throws HandlerException
	 */
	public int updateUserInfo(BUserInfo userInfo) throws HandlerException {
		if (logger.isDebugEnabled()) {
			logger.debug("start update table " + tableName + " values is ["
					+ userInfo.toString() + "]");
		}

		int count = 0;

		String sqlStatement = " update " + tableName+ " set NICKNAME = ?, USRSTS=? , REALNMFLG=? , REALNAME=? , IDTYP=? , IDNO=? , REGDATE=? , REGTIME=? , REGCHNL=? , EMAIL=? , TMSMP=? , TELNUM=? where MERCHANTID=? and PURCHASERID= ?";
		try {
			// 获取时间戳
			String time = TimeUtil.getCurrentTime(BusinessConstantField.TIMESTAMP_FORMATE);
			LinkedHashMap map = new LinkedHashMap();
			map.put("NICKNAME", userInfo.getNickName());
			map.put("USRSTS", BUserInfo.USER_STATUS_DISABLE);
			map.put("REALNMFLG", BUserInfo.VERIFY_INIT);
			map.put("REALNAME", userInfo.getRealName());
			map.put("IDTYP", userInfo.getIdTyp());
			map.put("IDNO", userInfo.getIdNo());
			map.put("REGDATE", userInfo.getRegDate());
			map.put("REGTIME", userInfo.getRegTime());
			map.put("REGCHNL", userInfo.getRegChnl());
			map.put("EMAIL", userInfo.getEmail());
			map.put("TMSMP", time);
			map.put("TELNUM", userInfo.getTelnum());
			map.put("MERCHANTID", userInfo.getMerchantid());
			map.put("PURCHASERID", userInfo.getPurchaserid());
			count = executeUpdate(sqlStatement, map);
			if (logger.isDebugEnabled()) {
				logger.debug(" update " + tableName + " table  sucess !");
			}
		} catch (HandlerException e) {
			logger.error("update " + tableName + " table data error !", e);
			/**update by tianguangzhao 2016/4/27 回滚交由上游控制
			try {
				connection.rollback();
			} catch (SQLException e1) {
				logger.error("connection rollback error !");
				throw new HandlerException(e1);
			} */
			throw e;
		}
		return count;
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
