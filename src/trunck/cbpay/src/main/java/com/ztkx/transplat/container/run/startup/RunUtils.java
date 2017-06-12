package com.ztkx.transplat.container.run.startup;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

public class RunUtils {
	private static RandomAccessFile fi;

	public static FileLock getFileLock(File file) throws IOException {  
        fi = new RandomAccessFile(file, "rws");  
        FileChannel fc = fi.getChannel();  
        return fc.tryLock();  
    } 
	
	/**
	 * 方法暂时没用
	 * @param stream
	 * @return
	 */
	public static String getStreamAsString(InputStream stream){ StringBuffer out   = new StringBuffer();
		try {
			byte[] buff = new byte[1024];
			int count = 0;
			while ((count = stream.read(buff)) > 0) {
				out.append(new String(buff, 0, count));
			}
		} catch(Exception e){
			System.out.println("接受数据转化为String错误！");
			e.printStackTrace();
		}finally {
			if (stream != null) {
				try {
					stream.close();
				} catch (IOException e) {
					System.out.println("接受流关闭错误！");
					e.printStackTrace();
				}
			}
		}
		return out.toString();
	}
}
