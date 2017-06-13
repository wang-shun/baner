package org.inn.baner.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
/**
 * gzip 进行压缩和解压缩的工具类
 * @author tianguangzhao
 *
 */
public class GzipUtil {

	/**
	 * 加压方法
	 *
	 * @param sourBytes，待压缩的byte[]
	 * @return 压缩后的 byte[]
	 * @throws IOException
	 */
	public static byte[] compress(byte[] sourBytes) throws IOException {
		if (sourBytes == null || sourBytes.length == 0) {
			return null;
		} else {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			GZIPOutputStream gzip = new GZIPOutputStream(out);
			gzip.write(sourBytes);
			gzip.close();
			return out.toByteArray();
		}
	}

	/**
	 * 解压方法，解压后按照行，封装成list
	 *
	 * @param bytes 待解压的byte[]
	 * @return 解压后的byte[]
	 * @throws IOException
	 */
	public static List<String> uncompressToList(byte[] bytes) throws IOException {

		List<String> list = new ArrayList<String>();
		String row = "";
		if (bytes == null || bytes.length == 0) {
			return null;
		} else {
			ByteArrayInputStream in = new ByteArrayInputStream(bytes);
			GZIPInputStream gunzip = new GZIPInputStream(in);
			InputStreamReader inputStreamReader = new InputStreamReader(gunzip);
			BufferedReader bufferReader = new BufferedReader(inputStreamReader);
			while ((row = bufferReader.readLine()) != null) {
				list.add(row);
			}
			return list;
		}
	}

	/**
	 * 将原byte[]解压为字符串
	 * @param bytes 待解压的byte[]
	 * @return 解压后得到的byte[]
	 * @throws IOException
	 */
	public static byte[] uncompressToBytes (byte[] bytes) throws IOException{
		//如果传入的参数为空，则直接返回null
		if (bytes == null || bytes.length == 0) {
          return null;
		} else {
			try {
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				ByteArrayInputStream in = new ByteArrayInputStream(bytes);
				GZIPInputStream gunzip = new GZIPInputStream(in);
				byte[] buffer = new byte[256];
				int n;
				while ((n = gunzip.read(buffer)) >= 0) {
					out.write(buffer, 0, n);
				}
				// toString()使用平台默认编码，也可以显式的指定如toString(GBK)
				return out.toByteArray();
			} catch (IOException e) {
				throw e;
			}
		}
	}
}
