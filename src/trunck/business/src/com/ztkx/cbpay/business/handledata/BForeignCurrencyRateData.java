package com.ztkx.cbpay.business.handledata;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ztkx.cbpay.business.bean.BForeignCurrencyRate;
import com.ztkx.cbpay.container.HandlerException;
import com.ztkx.cbpay.container.initload.AbstractDbMapper;
import com.ztkx.cbpay.platformutil.db.DBUtil;
import com.ztkx.cbpay.platformutil.db.c3p0.DataSourceUtil;

public class BForeignCurrencyRateData extends AbstractDbMapper{

	Logger logger = Logger.getLogger(BForeignCurrencyRateData.class);
	
	
	/**
	 * B_FOREIGN_CURRENCY_RATE表的插入方法
	 * @param bForeignCurrencyRate
	 * @throws HandlerException
	 * 2016年3月21日 下午4:49:52
	 * @author zhangxiaoyun
	 */
	public void insertData(BForeignCurrencyRate bForeignCurrencyRate) throws HandlerException {
		logger.debug("insert into B_FOREIGN_CURRENCY_RATE is begin ..");
		
		String sql = "insert into b_foreign_currency_rate (from_id, currency_code, recv_date, recv_time, "
				+ "transtime, transdate, cashbuyprice, exbuyprice, cashsellprice, exsellprice, exquotedate, "
				+ "exquotetime, e3rdpayno, price, direction_flag, tran_amt, client_exchange_rate, "
				+ "discount_type, dis_amt, amt) values  (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		logger.info("sql[" + sql + "]");
		
		if(logger.isDebugEnabled()){
			logger.debug("b_foreign_currency_rate data is ["+bForeignCurrencyRate+ "]");
		}
		try {
			psmt = DataSourceUtil.getPreparedStatement(connection, sql);
			
			psmt.setString(1, bForeignCurrencyRate.getFromId());			//牌价渠道
			psmt.setString(2, bForeignCurrencyRate.getCurrencyCode());		//币种代码
			psmt.setString(3, bForeignCurrencyRate.getRecvDate());			//接收日期
			psmt.setString(4, bForeignCurrencyRate.getRecvTime());			//接收时间
			psmt.setString(5, bForeignCurrencyRate.getTranstime());			//银行发起时间
			psmt.setString(6, bForeignCurrencyRate.getTransdate());			//银行发起日期
			psmt.setBigDecimal(7, bForeignCurrencyRate.getCashbuyprice());
			psmt.setBigDecimal(8, bForeignCurrencyRate.getExbuyprice());	//汇买价
			psmt.setBigDecimal(9, bForeignCurrencyRate.getCashsellprice());	
			psmt.setBigDecimal(10, bForeignCurrencyRate.getExsellprice());	//汇卖价
			psmt.setString(11, bForeignCurrencyRate.getExquotedate());		//牌价日期
			psmt.setString(12, bForeignCurrencyRate.getExquotetime());		//牌价时间
			psmt.setString(13, bForeignCurrencyRate.getE3rdpayno());
			psmt.setBigDecimal(14, bForeignCurrencyRate.getPrice());	//市场价
			psmt.setString(15, bForeignCurrencyRate.getDirectionFlag());
			psmt.setBigDecimal(16, bForeignCurrencyRate.getTranAmt());
			psmt.setBigDecimal(17, bForeignCurrencyRate.getClientExchangeRate());					//成交汇率
			psmt.setString(18, bForeignCurrencyRate.getDiscountType());					//优惠后金额
			psmt.setBigDecimal(19, bForeignCurrencyRate.getDisAmt());				//卖出账号
			psmt.setBigDecimal(20, bForeignCurrencyRate.getAmt());
			psmt.execute();
		} catch (Exception e) {
			logger.error("exec inser error", e);
			throw new HandlerException(e);
		}
	}
	
	public String selectExsellPrice(String time,String currency) throws HandlerException{
		String sql = "select max(PRICE) from b_foreign_currency_rate where EXQUOTEDATE = ? and CURRENCY_CODE = ?";
		logger.info("sql["+sql+"]");
		String exsellPrice = null;
		try {
			psmt = DataSourceUtil.getPreparedStatement(connection, sql);
			logger.info("EXQUOTEDATE["+time+"]  CURRENCY_CODE["+currency+"]");
			psmt.setString(1, time);
			psmt.setString(2, currency);
			rs = psmt.executeQuery();
			if(rs.next()){
				exsellPrice = rs.getString(1);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("exec sql error",e);
			throw new HandlerException(e);
		}
		logger.info("PRICE["+exsellPrice+"]");
		return exsellPrice;
	}
	
	public Map<String, String> selectMaxExsellPrice(String time,String currency) throws HandlerException{
		String sql = "select PRICE,EXQUOTETIME from b_foreign_currency_rate where EXQUOTEDATE = ? and CURRENCY_CODE = ? and CLIENT_EXCHANGE_RATE is not null order by CLIENT_EXCHANGE_RATE desc";
		logger.info("sql["+sql+"]");
		List<Map<String, String>> resList = null;
		try {
			psmt = DataSourceUtil.getPreparedStatement(connection, sql);
			logger.info("EXQUOTEDATE["+time+"]  CURRENCY_CODE["+currency+"]");
			psmt.setString(1, time);
			psmt.setString(2, currency);
			rs = psmt.executeQuery();
			resList = DBUtil.resConvertList(rs);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("exec sql error",e);
			throw new HandlerException(e);
		}
		if(resList!=null&&resList.size()>0){
			return resList.get(0);
		}
		return null;
	}
	
	
	public Map<String, String> getExRate(String channel,String exRateDate,String currency_code) throws HandlerException{
		String sql = "select * from b_foreign_currency_rate t where exquotedate='"+exRateDate+"' and currency_code='"+currency_code+"' and from_id='"+channel+"'";
		logger.info("sql["+sql+"]");
		List<Map<String, String>> resList = null;
		try {
			getConnection();
			stmt = DataSourceUtil.getStatement(connection);
			rs = stmt.executeQuery(sql);
			resList = DBUtil.resConvertList(rs);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("exec sql error",e);
			throw new HandlerException(e);
		}finally{
			relaceResource();
		}
		if(resList!=null&&resList.size()>0){
			return resList.get(0);
		}
		return null;
	}
	
	/**
	 * 获取最小汇率的服务方
	 * @param exRateDate
	 * @param currency_code
	 * @return
	 * @throws HandlerException
	 */
	public String getMinRateSvr(String exRateDate,String currency_code) throws HandlerException{
		String sql = "select min(client_exchange_rate),from_id from b_foreign_currency_rate t where t.exquotedate='"+exRateDate+"' and t.currency_code='"+currency_code+"' group by t.from_id";
		logger.info("sql["+sql+"]");
		List<Map<String, String>> resList = null;
		try {
			getConnection();
			stmt = DataSourceUtil.getStatement(connection);
			rs = stmt.executeQuery(sql);
			resList = DBUtil.resConvertList(rs);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("exec sql error",e);
			throw new HandlerException(e);
		}finally{
			relaceResource();
		}
		if(resList!=null&&resList.size()>0){
			return resList.get(0).get("FROM_ID");
		}
		return null;
	}
}
