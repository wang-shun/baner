package com.ztkx.cbpay.platformutil.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 数据库操作的工具类
 * @author zhangxiaoyun
 * 2016年3月11日 下午7:13:26
 * <p>Company:ztkx</p>
 */
public class DBUtil {
	/**
	 * 准备preparedstatement
	 * @param map
	 * @throws SQLException
	 * 2016年3月16日 上午10:43:41
	 * @author zhangxiaoyun
	 */
	public static void preparePsmt(LinkedHashMap<String, String> map,PreparedStatement psmt)
			throws SQLException {
		int i=0;
		for (Entry<String, String> entry : map.entrySet()) {
			i++;
			psmt.setString(i, entry.getValue());
		}
	}
	
	/**
	 * 提交数据库链接
	 * @param connection
	 * @throws SQLException
	 * 2016年3月16日 下午2:34:41
	 * @author zhangxiaoyun
	 */
	public static void commit(Connection connection) throws SQLException {
		connection.commit();
		connection.setAutoCommit(true);
	}
	/**
	 * 根据查询结果接返回map对象列表
	 * 调用方需要根据返回的list的size是否为判断是否有数据查询到
	 * 如果查询的结果集ResultSet特别大建议分批次查询
	 * @param rs
	 * @return
	 * @throws SQLException
	 * 2016年3月11日 下午7:11:08
	 * @author zhangxiaoyun
	 */
	public static List<Map<String, String>> resConvertList(ResultSet rs)
			throws SQLException {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		ResultSetMetaData md = rs.getMetaData();
		int columnCount = md.getColumnCount();
		while (rs.next()) {
			Map<String,String> rowData = new HashMap<String,String>();
			for (int i = 1; i <= columnCount; i++) {
				String columnName = md.getColumnName(i);
				rowData.put(columnName,rs.getString(columnName));
			}
			list.add(rowData);
		}
		return list;
	}
}
