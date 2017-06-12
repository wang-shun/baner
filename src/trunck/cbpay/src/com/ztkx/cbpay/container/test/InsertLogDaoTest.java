package com.ztkx.cbpay.container.test;
import java.io.ByteArrayInputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class InsertLogDaoTest {

	public void insertLog(String flowNo, String tranDate, String MsgOrder,
			byte[] msg) {
		String url = "jdbc:oracle:thin:@172.30.12.26:1521:msds";
		String username = "cbpay";
		String password = "cbpay";
		Connection connection = null;
		PreparedStatement ps = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			connection = DriverManager.getConnection(url, username, password);
			
			// 向oracle中写入blob类型数据时需开启事务
			connection.setAutoCommit(false);
//			String insertSql = "insert into trans_msg_log values (?,?,to_char(sysdate,'HH24mmss') ,?,empty_blob())";
			String insertSql = "insert into trans_msg_log values (?,?,to_char(sysdate,'HH24mmss') ,?,?)";
			System.out.println(insertSql);
			ps = connection.prepareStatement(insertSql);
			ps.setString(1, flowNo);
			ps.setString(2, tranDate);
			ps.setString(3, MsgOrder);
			ByteArrayInputStream bis = new ByteArrayInputStream(msg);
			long start = System.currentTimeMillis();
			ps.setBlob(4, bis);
			
			ps.executeUpdate();
			System.out.println((System.currentTimeMillis() - start));
			/*String selectSql = "select * from trans_msg_log where flowno = '"
					+ flowNo + "' ";
			stmt = connection.createStatement();
			rs = stmt.executeQuery(selectSql);
			rs.next();
			// 获取Blob对象
			Blob blob = rs.getBlob("MSG");
			// 获取向blob写入二进制数据的输出流 1为起始字节位置 L代表长整型
			OutputStream out = blob.setBinaryStream(1L);
			out.write(msg.getBytes());
			out.flush();
			rs.close();
			stmt.close();
			getTime();*/
			connection.close();
			System.out.println("成功写入！");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void getTime() {
		Date date = new Date();
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
		System.out.print("现在时间是：" + format.format(date));
	}
}