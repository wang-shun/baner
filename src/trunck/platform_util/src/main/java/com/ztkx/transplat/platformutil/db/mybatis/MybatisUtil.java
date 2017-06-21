package com.ztkx.transplat.platformutil.db.mybatis;

import com.ztkx.transplat.platformutil.baseconfig.BaseConfig;
import com.ztkx.transplat.platformutil.baseconfig.ConstantConfigField;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * 数据源工具类
 *
 * @author tianguangzhao
 */

public class MybatisUtil {

    private static MybatisUtil dataSource = null;
    private static SqlSessionFactory factory = null;
    // mybatis配置文件目录
    private String fileName = ConstantConfigField.DB_MYBATISCONFIGFILE;
    private String filePath = BaseConfig.getConfig(ConstantConfigField.CONFIGPATH) + "dbpool" + File.separator;
    static Logger logger = Logger.getLogger(MybatisUtil.class);

    // 构造方法中初始化数据库连接池
    private MybatisUtil() {
        if (factory == null) {
            try {
                logger.info("mybatis configuration file is " + (filePath + fileName));
                InputStream inputStream = new FileInputStream(filePath+fileName);
                SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
                factory = builder.build(inputStream);
                // 获取数据源信息，数据库名称，URL，最大线程数等；
            } catch (Exception e) {
                logger.error("create SqlSessionFactory error", e);
            }
        }
    }
    /**
     * update by zhangxiaoyun
     *
     * @return
     */
    public static MybatisUtil getInstance() {
        if (dataSource == null) {
            synchronized (MybatisUtil.class) {
                if (dataSource == null) {
                    dataSource = new MybatisUtil();
                }
            }
        }
        return dataSource;
    }

    // 获取数据库session
    public static SqlSession getSqlSession() {
        return getSqlSession(false);
    }

    public static SqlSession getSqlSession(boolean autoCommit) {
        return factory.openSession(autoCommit);
    }

    // 释放数据库连接
    public static void closeSqlSession(SqlSession session) {
        try {
            if (session != null) {
                session.commit();
                // c3p0的connection类的close方法不是将连接关闭而是将连接回收到资源池中，close方法被c3p0重写了。
                session.close();
            }
        } catch (Throwable e) {
            logger.error("closeConnect error !", e);
        }
    }

    //
    // 应用程序关闭时直接销毁数据源
    protected void finalize() throws Throwable {
        super.finalize();
    }

}
