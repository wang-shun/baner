package com.msds.cbpay.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.msds.cbpay.constant.ConstantField;

public class HttpSend {
	public static int connectTimeout;
	public static int readTimeout;
	public static byte[] doHttpPost(String url, byte[] content) throws Exception {
		HttpURLConnection conn = null;
		OutputStream out = null;
		byte[] rsp = null;
		try {
			try {
				// 1.閼惧嘲褰囨潻鐐村复
				URL urls = new URL(url);
				conn = getHttpConnection(urls, ConstantField.METHOD_POST);
				conn.setConnectTimeout(connectTimeout);
				conn.setReadTimeout(readTimeout);
			} catch (Exception e) {
				throw e;
			}
			try {
				// 2.閼惧嘲褰囬崘娆忓弳濞达拷
				out = conn.getOutputStream();
				// 3.閸愭瑥鍙嗛弬鍥︽
				out.write(content);
				rsp = getResponseAsByte(conn);
			} catch (IOException e) {
				throw e;
			}

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
		try {
			conn = (HttpURLConnection) url.openConnection();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw e;
		}
		conn.setRequestMethod(method);
		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.setRequestProperty("Accept", "text/xml,text/javascript,text/html");
		conn.setRequestProperty("User-Agent", "stargate");
		conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
		return conn;
	}

	protected static byte[] getResponseAsByte(HttpURLConnection conn)
			throws IOException {
		InputStream es = conn.getErrorStream();
		if (es == null) {
			return getStreamAsbyte(conn.getInputStream());
		} else {
			return getStreamAsbyte(es);
		}
	}

	private static byte[] getStreamAsbyte(InputStream stream)
			throws IOException {
		ByteArrayOutputStream writer = new ByteArrayOutputStream();
		try {
			byte[] buff = new byte[1024];
			int count = 0;
			while ((count = stream.read(buff)) > 0) {
				writer.write(buff, 0, count);
			}
		} finally {
			if (stream != null) {
				stream.close();
			}
		}
		return writer.toByteArray();
	}
}
