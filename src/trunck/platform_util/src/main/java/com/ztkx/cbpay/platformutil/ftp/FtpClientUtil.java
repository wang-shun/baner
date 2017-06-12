package com.ztkx.cbpay.platformutil.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import com.ztkx.cbpay.platformutil.baseconfig.BaseConfig;
import com.ztkx.cbpay.platformutil.baseconfig.ConstantConfigField;

/**
 * 
 * @author zhangxiaoyun
 * 2016年5月25日17:41:25
 * ftp客户端工具类
 */
public class FtpClientUtil {
	
	
	
	/**
	 * 
	 * @param localName		上传文件名称
	 * @return
	 * @throws Exception
	 */
	public static boolean downloadFile(String remoteName) throws Exception{
        FileOutputStream fos = null;
        FTPClient ftpClient = null;
		try {
			fos = new FileOutputStream(BaseConfig.getConfig(ConstantConfigField.LOCAL_FILE_PATH)+File.separator+remoteName);
			ftpClient = connectFTPServer();
            //改变工作目录到所需要的路径下  
            if(ftpClient.changeWorkingDirectory(BaseConfig.getConfig(ConstantConfigField.REMOTE_FILE_PATH))){
            	ftpClient.setBufferSize(1024);  
//                ftpClient.setControlEncoding("iso-8859-1");
                if(!ftpClient.retrieveFile(remoteName, fos)){
                	//如果下载失败
                	throw new Exception("Failed to upload");
                }else{
                	return true;
                }
            }else{
            	throw new Exception("fail to change dir ");
            }
            
        } finally { 
        	if(fos!=null){
        		fos.close();	
        	}
            closeFTPClient(ftpClient);  
        }  
	}
	
	/**
	 * 
	 * @param localName		上传文件名称
	 * @return
	 * @throws Exception
	 */
	public static boolean uploadFile(String localName) throws Exception{
        FileInputStream fis = null;
        FTPClient ftpClient = null;
		try {
			fis = new FileInputStream(BaseConfig.getConfig(ConstantConfigField.LOCAL_FILE_PATH)+File.separator+localName);
			ftpClient = connectFTPServer();
            //改变工作目录到所需要的路径下  
            if(ftpClient.changeWorkingDirectory(BaseConfig.getConfig(ConstantConfigField.REMOTE_FILE_PATH))){
            	ftpClient.setBufferSize(1024);  
//                ftpClient.setControlEncoding("iso-8859-1");
                if(!ftpClient.storeFile(localName, fis)){
                	//如果上传失败
                	throw new Exception("Failed to upload");
                }else{
                	return true;
                }
            }else{
            	throw new Exception("fail to change dir ");
            }
            
        } finally { 
        	if(fis!=null){
        		fis.close();	
        	}
            closeFTPClient(ftpClient);  
        }  
	}
	
	private static FTPClient connectFTPServer() throws Exception {
		FTPClient ftpclient = null;
		try {
			int connectionTimeOut = Integer.parseInt(BaseConfig.getConfig(ConstantConfigField.FTPCONNECTIONTIMEOUT));
			String ip = BaseConfig.getConfig(ConstantConfigField.FTPSERVERHOST);
			int port = Integer.parseInt(BaseConfig.getConfig(ConstantConfigField.FTPSERVERPORT));
			int dataTimeOut = Integer.parseInt(BaseConfig.getConfig(ConstantConfigField.DATATRANSFERTIMEOUT));

			ftpclient = new FTPClient();
			ftpclient.setConnectTimeout(connectionTimeOut);
			ftpclient.enterLocalPassiveMode(); // 设置被动模式
			ftpclient.connect(ip, port);
			ftpclient.setFileType(FTPClient.BINARY_FILE_TYPE); // 设置为二进制传输
			ftpclient.setBufferSize(8 * 1024);
			ftpclient.login(BaseConfig.getConfig(ConstantConfigField.FTPUSERNAME),BaseConfig.getConfig(ConstantConfigField.FTPPWD));
			ftpclient.setDataTimeout(dataTimeOut);
			int replyCode = ftpclient.getReplyCode();

			if ((!FTPReply.isPositiveCompletion(replyCode))) {
				// 关闭Ftp连接
				closeFTPClient(ftpclient);
				// 释放空间
				ftpclient = null;
			} else {
				return ftpclient;
			}
		} catch (Exception e) {
			if (ftpclient != null) {
				ftpclient.disconnect();
				ftpclient = null;
			}
			throw e;
		}
		return ftpclient;
	}
	/** 
	 * 关闭FTP连接 
	 *  
	 * @param ftp 
	 * @throws Exception 
	 */  
	private static void closeFTPClient(FTPClient ftp) throws Exception {
		try {
			if (ftp!= null && ftp.isConnected())
			{
				ftp.logout();
				ftp.disconnect();
			}
		} catch (Exception e) {
			throw new Exception("close FTP exception");
		}
	}
	
//	public static void main(String[] args) throws Exception {
//		BaseConfig config = BaseConfig.getInstence("E:/svn_workspack/cbpay/src/trunck/platform_util/config/baseConf.properties");
//		FtpClientUtil.uploadFile("platform_util.jar");
//	}
}
