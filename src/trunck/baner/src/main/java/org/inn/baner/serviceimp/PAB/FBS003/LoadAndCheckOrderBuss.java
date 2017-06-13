package org.inn.baner.serviceimp.PAB.FBS003;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ztkx.cbpay.business.bean.BMerchantOrder;
import com.ztkx.cbpay.business.constant.BusinessConstantField;
import com.ztkx.cbpay.business.constant.BusinessErrorCodeConstant;
import com.ztkx.cbpay.business.handledata.BMerchantOrderData;
import com.ztkx.cbpay.business.handledata.DataHandlerUtil;
import com.ztkx.cbpay.business.serviceimp.PAB.PABBusiUtil;
import com.ztkx.cbpay.container.service.ServiceException;
import com.ztkx.cbpay.container.service.intface.BusinessService;
import com.ztkx.cbpay.container.util.ContextUtil;
import com.ztkx.cbpay.platformutil.context.CommonContext;

/**
 * 1.从数据库加载订单信息
 * 2.检查订单状态、账户划转状态、购汇状态
 * @author zhangxiaoyun
 * 2016年3月10日 下午6:25:13
 * <p>Company:ztkx</p>
 */
public class LoadAndCheckOrderBuss implements BusinessService {

	private Logger logger = Logger.getLogger(LoadAndCheckOrderBuss.class);

	@Override
	public CommonContext service(CommonContext context) throws ServiceException {
		
		// 取普通字段
		/**
		 * 从报文中获取循环报文查询数据库中的数据
		 */
		int listSize = Integer.parseInt(context.getFieldStr("listSize", CommonContext.SCOPE_GLOBAL));
		String merchantId = context.getFieldStr("merchantId", CommonContext.SCOPE_GLOBAL);
		List<Map<String,String>> cyclData = (List<Map<String,String>>)context.getObj("buyBatList", CommonContext.SCOPE_GLOBAL);
		List<BMerchantOrder> orderList = new ArrayList<BMerchantOrder>();
		BMerchantOrderData orderDataHandler = new BMerchantOrderData();
		Connection conn = orderDataHandler.getConnection();
		BMerchantOrder order = null;
		try{
			for (int i = 0; i < cyclData.size(); i++) {
				Map<String,String> map = cyclData.get(i);
				String orderId = map.get("orderId");
				String orderdate = map.get("orderDate");
				order = orderDataHandler.getOrderInfoWithLock(merchantId, orderId, orderdate);
				if(order == null){
					ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000501, context);
					logger.error("order not exist");
					throw new ServiceException("order not exist");
				}
				orderList.add(order);
			}
			/**
			 * 开始判断订单状态
			 */
			logger.info("start check order status");
			for (int i = 0; i < orderList.size(); i++) {
				order = orderList.get(i);
				// 检查订单支付状态
				PABBusiUtil.checkOrderPayStatus(context, order);
				// 检查账户划转状态
				PABBusiUtil.buyExgCheckAcTStatus(context, order);
				// 检查订单购汇状态
				PABBusiUtil.checkBuyExgStatus(context, order);
			}
		} catch (ServiceException e) {
			// 订单状态错误不能完成购汇
			ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000516, context);
			logger.error("order status is error  date ["+order.getOrderdate()+"] orderid ["+order.getOrderid()+"] merchantid ["+order.getMerchantid()+"] ");
			throw new ServiceException("order status is error",e);
		}finally{
			DataHandlerUtil.commitConn(conn);
			DataHandlerUtil.releaseSource(orderDataHandler);
		}
		
		context.setObj(BusinessConstantField.MERCHANT_ORDER_OBJ, orderList);
		return context;
	}
}
