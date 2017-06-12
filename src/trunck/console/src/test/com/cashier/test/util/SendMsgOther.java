package com.cashier.test.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import cn.msec.cbpay.util.ConsoleConstant;

public class SendMsgOther {

	public static String send(String param,String trancode) throws Exception {
		param = URLEncoder.encode(param, ConsoleConstant.encoding);
		param = "MERCHANTDATA=" + param;
		String rsp = doHttpPost(CashierTestConstant.url+"/"+trancode+".action", param.getBytes(ConsoleConstant.encoding), CashierTestConstant.connectTimeout,CashierTestConstant.readTimeout);
		return rsp;
	}

	private static String doHttpPost(String url, byte[] content,
			int connectTimeout, int readTimeout) throws Exception {
		HttpURLConnection conn = null;
		OutputStream out = null;
		String rsp = null;
		try {
			URL urls = new URL(url);
			conn = getHttpConnection(urls, CashierTestConstant.METHOD_POST);
			conn.setConnectTimeout(connectTimeout);
			conn.setReadTimeout(readTimeout);
			out = conn.getOutputStream();
			out.write(content);
			rsp = getResponseAsByte(conn);
		} finally {
			if (out != null) {
				out.close();
			}
			if (conn != null) {
				conn.disconnect();
			}
		}

		return rsp;
	}

	private static HttpURLConnection getHttpConnection(URL url, String method) throws IOException {
		HttpURLConnection conn = null;
		conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod(method);
		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
		return conn;
	}

	protected static String getResponseAsByte(HttpURLConnection conn)
			throws Exception {
		InputStream es = conn.getErrorStream();
		if (conn.getResponseCode()==200) {
			return getStreamAsbyte(conn.getInputStream());
		} else {
			return getStreamAsbyte(es);
		}
	}

	private static String getStreamAsbyte(InputStream stream)
			throws IOException {
		try {
			ByteArrayOutputStream writer = new ByteArrayOutputStream();

			byte[] buff = new byte[1024];
			int count = 0;
			while ((count = stream.read(buff)) > 0) {
				writer.write(buff, 0, count);
			}
			return writer.toString(ConsoleConstant.encoding);
		} finally {
			if (stream != null) {
				stream.close();
			}
		}
	}
}
