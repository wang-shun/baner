package org.inn.baner.serviceimp.PAB.FBS003;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ztkx.cbpay.business.bean.BBuyExgCtrl;
import com.ztkx.cbpay.business.bean.BBuyExgDet;
import com.ztkx.cbpay.business.bean.BMerchantOrder;
import com.ztkx.cbpay.business.constant.BusinessConstantField;
import com.ztkx.cbpay.business.constant.BusinessErrorCodeConstant;
import com.ztkx.cbpay.business.enums.BuyExgChannelEnum;
import com.ztkx.cbpay.business.handledata.BBuyExgCtrlData;
import com.ztkx.cbpay.business.handledata.BBuyExgDetData;
import com.ztkx.cbpay.business.handledata.BMerchantOrderData;
import com.ztkx.cbpay.business.handledata.DataHandlerUtil;
import com.ztkx.cbpay.container.initdata.PlatDateParamData;
import com.ztkx.cbpay.container.service.ServiceException;
import com.ztkx.cbpay.container.service.intface.BusinessService;
import com.ztkx.cbpay.container.util.ContextUtil;
import com.ztkx.cbpay.platformutil.context.CommonContext;
import com.ztkx.cbpay.platformutil.db.DBUtil;
import com.ztkx.cbpay.platformutil.time.TimeUtil;

/**
 * 发起购汇
 * 1.登记购汇总控表
 * 2.登记购汇明细表
 * 3.更新商户订单表     汇率渠道=平安银行    购汇批次号      购汇状态为初始状态
 * @author zhangxiaoyun
 * 2016-3-15 16:24:11
 * <p>Company:ztkx</p>
 */
public class BuyFCBuss implements BusinessService {

	private Logger logger = Logger.getLogger(BuyFCBuss.class);

	@Override
	public CommonContext service(CommonContext context) throws ServiceException {
		
		List<BMerchantOrder> listMapData = (List<BMerchantOrder>)context.getObj(BusinessConstantField.MERCHANT_ORDER_OBJ);
		
		if(listMapData == null || listMapData.size()<=0){
			context.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000516);
			logger.error("business service exec exception ");
			throw new ServiceException("business service exec exception");
		}
		Connection connection = null;
		BBuyExgDetData bBuyExgDetData = null;		//购汇明细表b_buy_exg_det操作句柄
		BBuyExgCtrlData bBuyExgCtrlData = null;		//购汇总控表b_buy_exg_ctrl操作句柄
		BMerchantOrderData bMerchantOrderData = null;	//商户订单表B_MERCHANT_ORDER操作句柄
		try {
			/**
			 * 1.登记购汇总控表
			 */
			logger.info("start register table b_buy_exg_ctrl ");
			BBuyExgCtrl bBuyExgCtrl = new BBuyExgCtrl();
			//批次号
			bBuyExgCtrl.setBuybatno(context.getFieldStr("SEQ_NO",CommonContext.SCOPE_GLOBAL));
			//购汇日期
			bBuyExgCtrl.setBuydate(PlatDateParamData.getInstance().getPlatDate());
			context.setFieldStr("BUYDATE", PlatDateParamData.getInstance().getPlatDate(), CommonContext.SCOPE_GLOBAL);
			//购汇时间
			bBuyExgCtrl.setBuytime(TimeUtil.getCurrentTime("HHmmss"));
			//购汇渠道
			bBuyExgCtrl.setQuotechnl(BuyExgChannelEnum.PAB.getCode());
	//		bBuyExgCtrl.setBecif(becif);			//客户号先不用
			//总笔数
			bBuyExgCtrl.setTotNum(Short.parseShort(context.getFieldStr("NUM", CommonContext.SCOPE_GLOBAL)));
			bBuyExgCtrl.setSaleCcy("RMB");
			//买入币种
			bBuyExgCtrl.setBuyCcy(context.getFieldStr("BUY_CCY", CommonContext.SCOPE_GLOBAL));
			//总金额
			bBuyExgCtrl.setTotalAmt(new BigDecimal(context.getFieldStr("TOTAL_AMT", CommonContext.SCOPE_GLOBAL)));
			
			bBuyExgCtrlData = new BBuyExgCtrlData();
			
			connection  = bBuyExgCtrlData.getConnection();
		
			connection.setAutoCommit(false);
			
			bBuyExgCtrlData.insertData(bBuyExgCtrl);
			
			logger.info("register b_buy_exg_ctrl table succ");
			/**
			 * 2.登记购汇明细表
			 */
			logger.info("start register table b_buy_exg_det");
			List<Map<String,String>> loopdata = (List<Map<String,String>>)context.getObj("HOResultSetFBS003R",CommonContext.SCOPE_GLOBAL);
			List<BBuyExgDet> bBuyExgDets = new ArrayList<BBuyExgDet>(loopdata.size());
			for (int i = 0; i < loopdata.size(); i++) {
				BBuyExgDet bBuyExgDet = new BBuyExgDet();
				Map<String,String> rowData =loopdata.get(i);
				
				bBuyExgDet.setBuybatno(context.getFieldStr("SEQ_NO",CommonContext.SCOPE_GLOBAL));
				bBuyExgDet.setTradeno(rowData.get("TRAN_SEQ_NO"));
				bBuyExgDet.setTranAmt(new BigDecimal(rowData.get("TRAN_AMT")));
				bBuyExgDet.setClientNo(rowData.get("CLIENT_NO"));
				bBuyExgDet.setTranCode(rowData.get("TRAN_CODE"));
				bBuyExgDet.setPayerGlobalId(rowData.get("PAYER_GLOBAL_ID"));
				bBuyExgDet.setPayerName(rowData.get("PAYER_NAME"));
				bBuyExgDet.setTradeType(rowData.get("TRAN_TYPE"));
				bBuyExgDet.setCountryCode(rowData.get("COUNTRY_CODE"));
				bBuyExgDet.setBuydate(PlatDateParamData.getInstance().getPlatDate());
				bBuyExgDets.add(bBuyExgDet);
			}
			bBuyExgDetData = new BBuyExgDetData();
			bBuyExgDetData.setConnection(connection);
			bBuyExgDetData.batchInsertData(bBuyExgDets);
			logger.info("register b_buy_exg_det table succ");
			/**
			 * 3.更新商户订单表
			 */
			logger.info("start update table b_merchant_order status");
			String sqlstatement = "update b_merchant_order set  channel = ?,  buybatno = ?, buybatstatus = ? ,buybatdate = ? where  merchantid = ?   and orderid = ? and orderdate = ? and buybatstatus in (?,?) ";
			List<LinkedHashMap<String, String>> list = new ArrayList<LinkedHashMap<String,String>>();
			for(int j = 0;j<listMapData.size();j++){
				LinkedHashMap<String, String> rowData = new LinkedHashMap<String, String>();
				BMerchantOrder orderData = listMapData.get(j);
				rowData.put("channel", BuyExgChannelEnum.PAB.getCode());
				rowData.put("buybatno", context.getFieldStr("SEQ_NO",CommonContext.SCOPE_GLOBAL));
				rowData.put("buybatstatus",BusinessConstantField.PURCHASESTATUS_01 );
				rowData.put("buybatdate",PlatDateParamData.getInstance().getPlatDate());
				rowData.put("merchantid", orderData.getMerchantid());
				rowData.put("orderid",orderData.getOrderid() );
				rowData.put("orderdate",orderData.getOrderdate() );
				rowData.put("buybatstatus_where1", BusinessConstantField.PURCHASESTATUS_00);
				rowData.put("buybatstatus_where2", BusinessConstantField.PURCHASESTATUS_05);
				list.add(rowData);
			}
			bMerchantOrderData = new BMerchantOrderData();
			bMerchantOrderData.setConnection(connection);
			int res = bMerchantOrderData.batchExecuteUpdate(sqlstatement, list);
			if(res==listMapData.size()){
				logger.info("update table b_merchant_order status succ");
			}else{
				logger.error("update table b_merchant_order status faile . need update data row is ["+listMapData.size()+"] actual update data row is ["+res+"]");
				throw new ServiceException("update table b_merchant_order status faile . need update data row is ["+listMapData.size()+"] actual update data row is ["+res+"]");
			}
			DBUtil.commit(connection);
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				logger.error("roll back data exception",e1);
			}
			ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000516, context);
			logger.error("buss service exec exception ,roll back data",e);
			throw new ServiceException("buss service exec exception ,roll back data",e);
		}finally{
			//释放资源
			DataHandlerUtil.releaseSource(bBuyExgCtrlData);
			DataHandlerUtil.releaseSource(bBuyExgDetData);
			DataHandlerUtil.releaseSource(bMerchantOrderData);
		}
		return context;
	}
}
