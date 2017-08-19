package org.inn.baner.handler.data;

import com.ztkx.transplat.container.HandlerException;
import com.ztkx.transplat.container.initload.AbstractTMMybatis;

import org.apache.log4j.Logger;
import org.inn.baner.bean.AcFlow;
import org.inn.baner.bean.User;

/**
 * 
 * @description <p>
 *              账户流水表
 *              </p>
 * @author liujian
 * @date 2017年8月19日 下午4:16:56
 * @update
 */
public class AcFlowData extends AbstractTMMybatis {

	Logger logger = Logger.getLogger(AcFlowData.class);
	static String tableName = "ac_flow";

	/**
	 * 
	 * @description <p>
	 *              增加记录
	 *              </p>
	 * @auther liujian
	 * @date 2017年8月19日 下午4:19:31
	 * @update
	 * @param acFlow
	 * @throws HandlerException
	 */
	public void insertRecord(AcFlow acFlow) throws HandlerException {

	}

	/**
	 * 
	 * @description <p>
	 *              更新记录
	 *              </p>
	 * @auther liujian
	 * @date 2017年8月19日 下午4:19:43
	 * @update
	 * @param acFlow
	 * @return
	 * @throws HandlerException
	 */
	public int update(AcFlow acFlow) throws HandlerException {

	}

	/**
	 * 
	 * @description <p>
	 *              查询记录根据流水号
	 *              </p>
	 * @auther liujian
	 * @date 2017年8月19日 下午4:19:53
	 * @update
	 * @param platflow
	 * @return
	 * @throws HandlerException
	 */
	public User qryRecordByPlatflow(String platflow) throws HandlerException {
		return null;
	}

	/**
	 * 
	 * @description <p>
	 *              查询记录根据Acno
	 *              </p>
	 * @auther liujian
	 * @date 2017年8月19日 下午4:23:18
	 * @update
	 * @param acno
	 * @return
	 * @throws HandlerException
	 */
	public User qryRecordByAcno(String acno) throws HandlerException {
		return null;
	}

	/**
	 * 
	 * @description <p>
	 *              查询记录根据Ownerid
	 *              </p>
	 * @auther liujian
	 * @date 2017年8月19日 下午4:23:33
	 * @update
	 * @param acno
	 * @return
	 * @throws HandlerException
	 */
	public User qryRecordByOwnerid(String acno) throws HandlerException {
		
		return null;
	}

	/**
	 * 
	 * @description <p>
	 *              查询记录根据外部交易流水号
	 *              </p>
	 * @auther liujian
	 * @date 2017年8月19日 下午4:28:17
	 * @update
	 * @param outflowno
	 *            外部交易流水号
	 * @return
	 * @throws HandlerException
	 */
	public User qryRecordByOutflowno(String outflowno) throws HandlerException {
		return null;
	}

}
