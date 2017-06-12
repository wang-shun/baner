package com.ztkx.transplat.container.service.serviceimp;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.ztkx.transplat.container.constant.ErrorCodeConstant;
import com.ztkx.transplat.container.javabean.RoutType;
import com.ztkx.transplat.container.javabean.TranRouter;
import com.ztkx.transplat.container.service.intface.BaseService;
import com.ztkx.transplat.platformutil.baseconfig.ConstantConfigField;
import com.ztkx.transplat.platformutil.context.CommonContext;

/**
 * 目前没有交易用，暂时废弃下
 * 等以后有后续需求后在做修改
 * @author sunyoushan
 *
 */
public class RouterBaseService implements BaseService {
	Logger logger = Logger.getLogger(RouterBaseService.class);
	@Override
	public CommonContext service(CommonContext context) {
		logger.info("RouterBaseService start!");
		
		logger.info("RouteType["+context.getSDO().RouteType+"]");
		if(ConstantConfigField.ROUTEKEY_MER.equals(context.getSDO().RouteType)){
			String merchantId = context.getFieldStr(ConstantConfigField.SDO_MERCHANTID,CommonContext.SCOPE_GLOBAL);
			//1.获取路由类型表
//			RoutType rt = RoutTypeData.map.get(merchantId);
			RoutType rt = null;
			if(rt==null){
				logger.warn("merchantId["+merchantId+"] is not find router configration ");
				return context;
			}
			String routevalue = getRouteField(context,rt.getRouteField());//获取路由字段里面的值
			this.tranRouter(context,merchantId,rt.getRoutetype(),routevalue,ConstantConfigField.ROUTEKEY_MER);//交易路由
		}else if(ConstantConfigField.ROUTEKEY_TCD.equals(context.getSDO().RouteType)){
			String trancode = context.getSDO().TRANCODE;
//			RoutType rt = RoutTypeData.map.get(trancode);
			RoutType rt = null;
			this.tranRouter(context,trancode,rt.getRoutetype(),"",ConstantConfigField.ROUTEKEY_TCD);//交易路由
		}
		logger.info("RouterBaseService succ ...");
		return context;
	}
	/**
	 * 获取返回的路由字段的值
	 * @param context
	 * @param rountfield
	 * @return
	 */
	private String getRouteField(CommonContext context,String rountfield){
		return context.getFieldStr(rountfield,CommonContext.SCOPE_GLOBAL);
	}
	
	/**
	 * 交易路由
	 * @param merchantId
	 * @param routetype
	 * @return
	 */
	private String tranRouter(CommonContext context,String routekey,String routetype,String routevalue,String routekeytype){
		if(logger.isDebugEnabled()){
			logger.debug("routekey["+ routekey+ "]  routetype[" + routetype+ "]  routevalue[" + routevalue +"]  routekeytype["+routekeytype+"]");
		}
		String tranopr = context.getSDO().TRAN_OPR;//获取业务码
		if(logger.isDebugEnabled()){
			logger.debug("TRAN_OPR : "+ tranopr);
		}
//		List<TranRouter> tranRouterList = TranRouterData.routelistmap.get(routekey).get(tranopr+ConstantConfigField.TABLE_PRI_KEY_SEPARATOR+routetype);
		List<TranRouter> tranRouterList = null;
		if(CollectionUtils.isEmpty(tranRouterList)){
			logger.warn("warn in RouterBaseService,table Tran_router not have valid routekey["+routekey+"] type["+routetype+"] tranopr["+tranopr+"]");
			return null;
		}else{
			logger.debug("merchantId&type&tranopr count is " + tranRouterList.size());
		}
		switch (routetype.toUpperCase()) {
			case ConstantConfigField.ROUTETYPE_AMT://按交易额
				this.tranRouterAmt(context,tranRouterList,routevalue,routekeytype);
				break;
			case ConstantConfigField.ROUTETYPE_MER://按商户
				this.tranRouterMer(context,tranRouterList,routekey);
				break;
			case ConstantConfigField.ROUTETYPE_RAT://按汇率
//				this.tranRouterRat(context,tranRouterList,routevalue,routekeytype);
				break;
			case ConstantConfigField.ROUTETYPE_TRA://按交易
				this.tranRouterTra(context,tranRouterList,routevalue);
				break;
			default:
				logger.error("routetype is not found!");
				break;
		}
		return null;
	}
	/**
	 * 按金额
	 * @param context
	 * @param tranRouterList
	 * @param routevalue
	 */
	public void tranRouterAmt(CommonContext context,List<TranRouter> tranRouterList,String routevalue,String routekeytype){
		if(logger.isDebugEnabled()){
			logger.debug("ROUTETYPE : AMT");
		}
		//取出字段中金额
		BigDecimal value = null;
		if(StringUtils.isNotEmpty(routevalue)){
			try{
				//按金额类型路由的将路由中的值转化为数字
				value = new BigDecimal(routevalue);
			}catch(Exception e){
				context.setErrorCode(ErrorCodeConstant.BASE_PLA000002);
				logger.error("error in route, routevalue("+routevalue+") is not number!",e);
				return;
			}
		}else{
			context.setErrorCode(ErrorCodeConstant.BASE_PLA000002);
			logger.error("msg amt value is null!");
			return;
		}
		logger.info("this value is match left region");
		boolean flag = true;
		for(TranRouter tr : tranRouterList){
			BigDecimal min_amt = tr.getMinAmt();
			BigDecimal max_amt = tr.getMaxAmt();
			if((min_amt.compareTo(value)==-1)&&(max_amt.compareTo(value)>=0)){
				if(routekeytype.equals(ConstantConfigField.ROUTEKEY_MER)){
					context.setFieldStr("rateChannelId", tr.getRateChannelId(), CommonContext.SCOPE_GLOBAL);
					logger.info("rateChannelId["+ tr.getRateChannelId() + "]");
				}
				else if(routekeytype.equals(ConstantConfigField.ROUTEKEY_TCD)){
					context.getSDO().serverId = tr.getServerid();
					context.getSDO().serverCode = tr.getServercode();
					logger.info("serverId["+ tr.getServerid() + "]  serverCode["+tr.getServercode()+"]");
				}
				flag = false;
				break;
			}
		}
		if(flag){
			logger.warn("amt["+routevalue+"] is not match region!");
		}
	}
	/**
	 * 按商户
	 * @param context
	 * @param tranRouterList
	 * @param merchantId
	 */
	private void tranRouterMer(CommonContext context,List<TranRouter> tranRouterList, String merchantId) {
		// TODO Auto-generated method stub
		if(logger.isDebugEnabled()){
			logger.debug("ROUTETYPE : MER");
		}
		context.setFieldStr("rateChannelId", tranRouterList.get(0).getRateChannelId(), CommonContext.SCOPE_GLOBAL);
		logger.info("rateChannelId["+ tranRouterList.get(0).getRateChannelId() + "]");
	}
	
	/**
	 * 按汇率
	 * @param context
	 * @param tranRouterList
	 * @param routevalue
	 */
//	private void tranRouterRat(CommonContext context,List<TranRouter> tranRouterList, String routevalue,String routekeytype) {
//		if(logger.isDebugEnabled()){
//			logger.debug("ROUTETYPE : RAT");
//		}	
//		BForeignCurrencyRateInData bForeignCurrencyRateInData = new BForeignCurrencyRateInData();
//		bForeignCurrencyRateInData.loadBefore();
//		String currency = context.getFieldStr("currency", CommonContext.SCOPE_GLOBAL);
//		logger.info("currency["+currency+"]");
//		if(currency==null){
//			logger.warn("currency is null ,don't route");
//			return;
//		}
//		Map<String,String> map = null;
//		try{
//			for(TranRouter tr : tranRouterList){
//				if(currency.equals(tr.getCurrency_type())){
//					if(ConstantConfigField.RATE_POLICE_MAX.equalsIgnoreCase(tranRouterList.get(0).getRate_policy())){
//						logger.info("rate_policy["+tranRouterList.get(0).getRate_policy()+"]");
//						map = bForeignCurrencyRateInData.selectMaxExsellPrice(TimeUtil.getCurrentTime("YYYYMMdd"), currency);
//						if(map==null){
//							map = bForeignCurrencyRateInData.selectMaxExsellPrice(TimeUtil.getLastDate(), currency);
//						}
//					}else if(ConstantConfigField.RATE_POLICE_MIN.equalsIgnoreCase(tranRouterList.get(0).getRate_policy())){
//						logger.info("rate_policy["+tranRouterList.get(0).getRate_policy()+"]");
//						map = bForeignCurrencyRateInData.selectMinExsellPrice(TimeUtil.getCurrentTime("YYYYMMdd"), currency);
//						if(map==null){
//							map = bForeignCurrencyRateInData.selectMinExsellPrice(TimeUtil.getLastDate(), currency);
//						}
//					}else{
//						logger.info("rate_policy["+tranRouterList.get(0).getRate_policy()+"]");
//					}
//					break;
//				}
//			}
//			if(map!=null){
//				if(routekeytype.equals(ConstantConfigField.ROUTEKEY_MER)){
//					context.setFieldStr("rateChannelId", map.get("FROM_ID"), CommonContext.SCOPE_GLOBAL);
//					logger.info("rateChannelId["+map.get("FROM_ID")+"]");
//				}else if(routekeytype.equals(ConstantConfigField.ROUTEKEY_TCD)){
//					context.getSDO().serverId = map.get("FROM_ID");
//					logger.info("serverId["+map.get("FROM_ID")+"]");
//				}
//			}else{
//				logger.warn("bForeignCurrencyRate is null");
//			}
//		}catch(Exception e){
//			logger.error("query bForeignCurrencyRate is error");
//			context.setErrorCode(ErrorCodeConstant.BASE_PLA000001);
//			return;
//		}finally{
//			bForeignCurrencyRateInData.loadAfter();
//		}
//	}
	
	/**
	 * 按交易
	 * @param context
	 * @param tranRouterList
	 * @param routevalue
	 */
	private void tranRouterTra(CommonContext context,List<TranRouter> tranRouterList, String routevalue) {
		// TODO Auto-generated method stub
		if(logger.isDebugEnabled()){
			logger.debug("ROUTETYPE : TRA");
		}
		for(TranRouter tr : tranRouterList){
			//按交易路由，取出该商户交易路由的匹配值的字段
			if(StringUtils.isNotEmpty(routevalue)&&routevalue.equalsIgnoreCase(tr.getTran_opr())){
				context.setFieldStr("rateChannelId", tranRouterList.get(0).getRateChannelId(), CommonContext.SCOPE_GLOBAL);
				logger.info("rateChannelId["+ tranRouterList.get(0).getRateChannelId() + "]");
			}
		}
	}
}
