package com.ztkx.cbpay.business.handledata;

import java.sql.PreparedStatement;

import org.apache.log4j.Logger;

import com.ztkx.cbpay.business.bean.BProduct;
import com.ztkx.cbpay.container.HandlerException;
import com.ztkx.cbpay.container.initload.AbstractDbMapper;
import com.ztkx.cbpay.platformutil.db.c3p0.DataSourceUtil;

public class BProductData extends AbstractDbMapper{

	Logger logger = Logger.getLogger(BProductData.class);

	public int insertData(BProduct bProduct) throws HandlerException{
		logger.debug("insert into b_product is begin ..");
		String sql = "insert into b_product (merchantid,orderid,orderdate,productname,productid,productdesc,showurl,productnum)"
				+ " values(?,?,?,?,?,?,?,?)";
		logger.info("sql["+sql+"]");
		int count = 0;
		try {
			PreparedStatement ps = DataSourceUtil.getPreparedStatement(connection, sql);
			ps.setString(1, bProduct.getMerchantid());
			ps.setString(2, bProduct.getOrderid());
			ps.setString(3, bProduct.getOrderdate());
			ps.setString(4, bProduct.getProductname());
			ps.setString(5, bProduct.getProductid());
			ps.setString(6, bProduct.getProductdesc());
			ps.setString(7, bProduct.getShowurl());
			ps.setString(8, bProduct.getProductnum());
			rs = ps.executeQuery();
			logger.debug("insert into b_product is end productid["+bProduct.getProductid()+"]");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("exec sql error",e);
			throw new HandlerException(e);
		}
		return count;
	}
}
