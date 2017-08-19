package org.inn.baner.handler.data;

import com.ztkx.transplat.container.HandlerException;
import com.ztkx.transplat.container.initload.AbstractTMMybatis;

import org.apache.log4j.Logger;
import org.inn.baner.bean.AcCore;
import org.inn.baner.bean.User;

/**
 * 
 * @description <p>
 *              核心账户表
 *              </p>
 * @author liujian
 * @date 2017年8月19日 下午4:16:56
 * @update
 */
public class AcCoreData extends AbstractTMMybatis {

	Logger logger = Logger.getLogger(AcCoreData.class);
	static String tableName = "ac_core";

	/**
	 * 
	 * @description <p>
	 *              增加记录
	 *              </p>
	 * @auther liujian
	 * @date 2017年8月19日 下午4:19:31
	 * @update
	 * @param acCore
	 * @throws HandlerException
	 */
	public void insertRecord(AcCore acCore) throws HandlerException {

	}

	/**
	 * 
	 * @description <p>
	 *              更新记录
	 *              </p>
	 * @auther liujian
	 * @date 2017年8月19日 下午4:19:43
	 * @update
	 * @param acCore
	 * @return
	 * @throws HandlerException
	 */
	public int update(AcCore acCore) throws HandlerException {

	}

	/**
	 * 
	 * @description <p>
	 *              查询记录根据ID
	 *              </p>
	 * @auther liujian
	 * @date 2017年8月19日 下午4:19:53
	 * @update
	 * @param id
	 * @return
	 * @throws HandlerException
	 */
	public User qryRecordById(String id) throws HandlerException {

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

	}

}
