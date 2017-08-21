package com.ztkx.transplat.container.initload;

import com.ztkx.transplat.container.HandlerException;
import com.ztkx.transplat.platformutil.context.CommonContext;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;


public abstract class AbstractTMMybatis implements TransactionMMybatis {

	public CommonContext context = null;
	public SqlSession sqlSession = null;

	private boolean autoCommit = true;	//是否自动提交

	@Override
	public SqlSession getSqlSession() throws HandlerException{
		return getSqlSession(true);
	}

	@Override
	public SqlSession getSqlSession(Boolean autoCommit) throws HandlerException{
		this.autoCommit = autoCommit;
		sqlSession = context.getSqlSession();
		if(sqlSession==null){
			SqlSessionFactory factory = BusDbpoolInit.getFactory(context.getSDO().serverId);
			sqlSession = factory.openSession(autoCommit);
			context.setSqlSession(sqlSession);
		}
		return sqlSession;
	}

	public void setContext(CommonContext context){
		this.context = context;
	}
}
