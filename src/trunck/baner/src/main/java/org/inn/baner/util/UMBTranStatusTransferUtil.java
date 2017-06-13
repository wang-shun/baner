package org.inn.baner.util;

import com.ztkx.cbpay.business.constant.BusinessConstantField;

/**
 * 宝易互通的状态码和我方状态码转换的工具类，（暂时使用，以后优化）
 * @author tianguangzhao
 *
 */
public class UMBTranStatusTransferUtil {

	/**
	 * 根据交易码转换，将宝易互通的交易码，转换为我们的交易码
	 * @param UMBTranStatus ,宝易互通的状态码
	 * @return
	 */
	public static String TransferToOurStatusByCode(String UMBTranStatus){

		if (UMBTranStatus == null || UMBTranStatus.equals("")) {
			// 宝易互通传回的状态码为空
			return null;
		} else if (UMBTranStatus.equals(BusinessConstantField.UMB_TRADING_INIT)) {
			// 交易初始化
			return BusinessConstantField.TRADING_INIT;
		} else if (UMBTranStatus.equals(BusinessConstantField.UMB_TRADING_PROCESSING)) {
			// 交易进行中
			return BusinessConstantField.TRADING_PROCESSING;
		} else if (UMBTranStatus.equals(BusinessConstantField.UMB_TRADING_SUCCESS)) {
			// 交易成功
			return BusinessConstantField.TRADING_SUCCESS;
		} else if (UMBTranStatus.equals(BusinessConstantField.UMB_TRADING_FAILED)) {
			// 交易失败
			return BusinessConstantField.TRADING_FAILED;
		} else {
			// 宝易互通传回的状态码有误
			return null;
		}
	}

	/**
	 * 根据宝易互通的交易状态描述，转换为我方对应的状态码,暂时使用后期修改
	 * @param desInfo 宝易互通的交易状态描述
	 * @return
	 */
	public static String TransferToOurStatusByDES(String desInfo){

		if (desInfo == null || desInfo.equals("")) {
			// 宝易互通传回的状态码为空
			return null;
		} else if (desInfo.equals("受理成功")) {
			// 交易初始化
			return BusinessConstantField.TRADING_INIT;
		} else if (desInfo.equals("处理中")) {
			// 交易进行中
			return BusinessConstantField.TRADING_PROCESSING;
		} else if (desInfo.equals("交易成功")) {
			// 交易成功
			return BusinessConstantField.TRADING_SUCCESS;
		} else if (desInfo.equals("交易失败")) {
			// 交易失败
			return BusinessConstantField.TRADING_FAILED;
		} else {
			// 宝易互通传回的状态码有误
			return null;
		}
	}
}
}
