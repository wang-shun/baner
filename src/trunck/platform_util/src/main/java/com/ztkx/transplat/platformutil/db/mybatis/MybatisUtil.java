package com.ztkx.transplat.platformutil.db.mybatis;

import com.ztkx.transplat.platformutil.baseconfig.BaseConfig;
import com.ztkx.transplat.platformutil.baseconfig.ConstantConfigField;
import org.apache.ibatis.exceptions.PersistenceException;
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

    // mybatis配置文件目录
    private static String filePath = BaseConfig.getConfig(ConstantConfigField.CONFIGPATH) + "dbpool" + File.separator;
    static Logger logger = Logger.getLogger(MybatisUtil.class);

    public static SqlSessionFactory  loadDbpool(String fileName) {
        SqlSessionFactory factory = null;
        try {
            logger.info("mybatis configuration file is " + (filePath + fileName));
            InputStream inputStream = new FileInputStream(filePath+fileName);
            SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
            factory = builder.build(inputStream);
            // 获取数据源信息，数据库名称，URL，最大线程数等；
        } catch (Exception e) {
            logger.error("create SqlSessionFactory error", e);
        }
        return factory;
    }

    public static void relace(SqlSession sqlSession) {
        if (sqlSession != null) {
            try {
                sqlSession.commit();
                // c3p0的connection类的close方法不是将连接关闭而是将连接回收到资源池中，close方法被c3p0重写了。
                sqlSession.close();
            } catch (PersistenceException e) {
                logger.error("closeConnect error !", e);
            } catch (Throwable e) {
                logger.error("closeConnect error !", e);
            }
            sqlSession = null;
        }
    }

    public static void rollback(SqlSession sqlSession) {
        if (sqlSession != null) {
            try {
                sqlSession.rollback();
                // c3p0的connection类的close方法不是将连接关闭而是将连接回收到资源池中，close方法被c3p0重写了。
                sqlSession.close();
            } catch (PersistenceException e) {
                logger.error("closeConnect error !", e);
            } catch (Throwable e) {
                logger.error("closeConnect error !", e);
            }
            sqlSession = null;
        }
    }

}
