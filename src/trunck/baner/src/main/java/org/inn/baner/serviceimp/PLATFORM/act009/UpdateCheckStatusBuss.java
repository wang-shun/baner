package org.inn.baner.serviceimp.PLATFORM.act009;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.ztkx.cbpay.business.bean.BBuyExgCtrl;
import com.ztkx.cbpay.business.bean.BCheckStatus;
import com.ztkx.cbpay.business.constant.BusinessConstantField;
import com.ztkx.cbpay.business.constant.BusinessErrorCodeConstant;
import com.ztkx.cbpay.business.handledata.BBuyExgCtrlData;
import com.ztkx.cbpay.business.handledata.BCheckStatusData;
import com.ztkx.cbpay.business.handledata.DataHandlerUtil;
import com.ztkx.cbpay.container.initdata.PlatDateParamData;
import com.ztkx.cbpay.container.service.ServiceException;
import com.ztkx.cbpay.container.service.intface.BusinessService;
import com.ztkx.cbpay.container.util.ContextUtil;
import com.ztkx.cbpay.platformutil.context.CommonContext;

/**
 * 更新对账状态
 * 更新对账表
 * 更新购总控表
 * @author zhagnxiaoyun
 * 2016年4月25日14:27:04
 *
 */
public class UpdateCheckStatusBuss implements BusinessService {

	Logger logger = Logger.getLogger(UpdateCheckStatusBuss.class);

	@Override
	public CommonContext service(CommonContext context) throws ServiceException {
		
		List<BBuyExgCtrl> list = (List<BBuyExgCtrl>)context.getObj(BusinessConstantField.MERCHANT_ORDER_OBJ);
		
		BBuyExgCtrlData buyExgHandler = null;
		BCheckStatusData checkStatusHandler = null;
		Connection conn = null;
		try {
			buyExgHandler = new BBuyExgCtrlData();
			conn = buyExgHandler.getConnection();
			DataHandlerUtil.setAutoCommit(conn, false);
			
			String sqlStatement = "update b_buy_exg_ctrl  set chk_sts = ?  where buybatno = ? and buydate = ?";
			List<LinkedHashMap<String, String>> updateCondi = new ArrayList<LinkedHashMap<String,String>>();
			for (int i = 0; i < list.size(); i++) {
				LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
				map.put("chk_sts", "1");
				map.put("buybatno", list.get(i).getBuybatno());
				map.put("buydate", list.get(i).getBuydate());
				updateCondi.add(map);
			}
			//更新购汇表
			int res = buyExgHandler.batchExecuteUpdate(sqlStatement, updateCondi);
			logger.debug("update b_buy_exg_ctrl table succ update size ["+res+"]");
			if(res != list.size()){
				ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000518, context);
				throw new ServiceException("update b_buy_exg_ctrl table fial");
			}
			
			checkStatusHandler = new BCheckStatusData();
			checkStatusHandler.setConnection(conn);
			res = checkStatusHandler.updateRecord(PlatDateParamData.getInstance().getLastPlatDate(),BCheckStatus.CHANNEL_PAB,BCheckStatus.STATUS_SUCCESS,BCheckStatus.CHECK_GH);
			logger.debug("update B_CHECK_STATUS table succ update size ["+res+"]");
			if(res ==1){
				DataHandlerUtil.commitConn(conn);
			}else{
				ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000518, context);
				throw new ServiceException("update B_CHECK_STATUS table fial");
			}
		}catch(Exception e){
			DataHandlerUtil.rollbakConn(conn);
			context.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000516);
			throw new ServiceException(e);
		}finally{
			DataHandlerUtil.releaseSource(checkStatusHandler);
			DataHandlerUtil.releaseSource(buyExgHandler);
		}
		return context;
	}
	
	
	public static void main(String[] args) throws NoSuchMethodException, SecurityException {
		Class clazz =  FileInfo.class;
		Method[]  methods = clazz.getDeclaredMethods();
		Field[] fields = clazz.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			String fieldName = fields[i].getName();
			String methodName = "set"+fieldName;
			Method method = clazz.getDeclaredMethod(methodName, String.class);
			System.out.println(i+method.getName());
		}
	}
}
