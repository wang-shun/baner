package com.ztkx.cbpay.container.initdata;

import java.util.Map;

import org.apache.log4j.Logger;

import com.ztkx.cbpay.container.HandlerException;
import com.ztkx.cbpay.container.initload.AbstractLoadInit;

public class TranRouterData extends AbstractLoadInit{
	Logger logger = Logger.getLogger(TranRouterData.class);
	
	/**
	 * 这个方法目前没有用
	 */
	@Override
	@Deprecated
	public boolean extracted(Map target) throws HandlerException {
//		String tableName = "TRAN_ROUTER";
//		logger.info("start load "+tableName+" table data...");
//		String sql = "select * from "+tableName +" order by max_amt,min_amt desc";
//		if(logger.isDebugEnabled()){
//			logger.debug(tableName+" init sql["+sql+"]");
//		}
//		stmt = DataSourceUtil.getStatement(connection);
//		String routelistmapkey = null;
//		String listmapkey = null;
//		try {
//			rs = stmt.executeQuery(sql);
//			while(rs.next()){
//				TranRouter tr = new TranRouter();
//				tr.setCurrency_type(rs.getString("CURRENCY_TYPE"));
//				tr.setMax_amt(rs.getString("MAX_AMT"));
//				tr.setRoutekey(rs.getString("ROUTEKEY"));
//				tr.setMin_amt(rs.getString("MIN_AMT"));
//				tr.setRate_policy(rs.getString("RATE_POLICY"));
//				tr.setServerid(rs.getString("SERVERID"));
//				tr.setTran_opr(rs.getString("TRAN_OPR"));
//				tr.setType(rs.getString("TYPE"));
//				tr.setServercode(rs.getString("SERVERCODE"));
//				tr.setRateChannelId(rs.getString("RETECHANNELID"));
//				try{
//					if(StringUtils.isEmpty(tr.getMin_amt()))
//						tr.setMinAmt(new BigDecimal("0"));
//					else
//						tr.setMinAmt(new BigDecimal(tr.getMin_amt()));
//					if(StringUtils.isEmpty(tr.getMax_amt()))
//						continue;
//					else
//						tr.setMaxAmt(new BigDecimal(tr.getMax_amt()));
//				}catch(Exception e){
//					logger.warn("ROUTEKEY["+tr.getRoutekey()+"] and TYPE["+tr.getType()+"] maxAmt or minAmt is not number",e);
//				}
//				list.add(tr);
//				routelistmapkey = rs.getString("ROUTEKEY");
//				listmapkey = rs.getString("TRAN_OPR")+ConstantConfigField.TABLE_PRI_KEY_SEPARATOR + rs.getString("TYPE");
//				if(null==routelistmap.get(routelistmapkey)){
//					Map<String,List<TranRouter>> listmap = new HashMap<String,List<TranRouter>>();
//					List<TranRouter> routeList = new ArrayList<TranRouter>();
//					routeList.add(tr);
//					listmap.put(listmapkey,routeList);
//					routelistmap.put(routelistmapkey, listmap);
//				}else{
//					Map<String,List<TranRouter>> listmap = routelistmap.get(routelistmapkey);
//					if(CollectionUtils.isNotEmpty(listmap.get(listmapkey))){
//						listmap.get(listmapkey).add(tr);
//					}else{
//						List<TranRouter> routeList = new ArrayList<TranRouter>();
//						routeList.add(tr);
//						listmap.put(listmapkey,routeList);
//					}
//				}
//			}
//			
//			logger.info(tableName+" init succ data Size:"+list.size());
//		} catch (SQLException e) {
//			logger.error("init TRAN_ROUTER table data exception",e);
//		}
		return false;
	}
}