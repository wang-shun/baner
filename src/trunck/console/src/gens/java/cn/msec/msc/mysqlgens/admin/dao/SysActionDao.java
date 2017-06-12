package cn.msec.msc.mysqlgens.admin.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Date;


import lombok.Data;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.transaction.annotation.Transactional;

import cn.msec.msc.mysqlgens.admin.entity.SysAction;
import cn.msec.msc.mysqlgens.admin.entity.SysActionExample;
import cn.msec.msc.mysqlgens.admin.entity.SysActionExample.Criteria;
import cn.msec.msc.mysqlgens.admin.entity.SysActionKey;
import cn.msec.msc.mysqlgens.admin.mapper.SysActionMapper;
import cn.msec.ojpa.annotations.Tab;
import cn.msec.ojpa.msc.mysql.StaticTableDaoSupport;


@Data
@Tab(name="T_SYS_ACTION")
public class SysActionDao implements StaticTableDaoSupport<SysAction, SysActionExample, SysActionKey>{

	private SysActionMapper mapper;

	private SqlSessionFactory sqlSessionFactory;
	
	
	@Override
	public int countByExample(SysActionExample example) {
		return mapper.countByExample(example);
	}

	@Override
	public int deleteByExample(SysActionExample example) {
		return mapper.deleteByExample(example);
	}

	@Override
	public int deleteByPrimaryKey(SysActionKey key) {
		return mapper.deleteByPrimaryKey(key);
	}

	@Override
	public int insert(SysAction record)  {
		return mapper.insert(record);
	}

	@Override
	public int insertSelective(SysAction record)  {
		return mapper.insertSelective(record);
	}

	@Override
	@Transactional
	public int batchUpdate(List<SysAction> records)
			 {
		for(SysAction record : records){
			mapper.updateByPrimaryKeySelective(record);
		}
		return records.size();
	}

	@Override
	@Transactional
	public int batchDelete(List<SysAction> records)
			 {
		for(SysAction record : records){
			mapper.deleteByPrimaryKey(record);
		}
		return records.size();
	}

	@Override
	public List<SysAction> selectByExample(SysActionExample example)
			 {
		return mapper.selectByExample(example);
	}

	@Override
	public SysAction selectByPrimaryKey(SysActionKey key)
			 {
		return mapper.selectByPrimaryKey(key);
	}

	@Override
	public List<SysAction> findAll(List<SysAction> records) {
		if(records==null||records.size()<=0){
			return mapper.selectByExample(new SysActionExample());
		}
		List<SysAction> list = new ArrayList<>();
		for(SysAction record : records){
			SysAction result = mapper.selectByPrimaryKey(record);
			if(result!=null){
				list.add(result);
			}
		}
		return list;
	}

	@Override
	public int updateByExampleSelective(SysAction record, SysActionExample example)  {
		return mapper.updateByExampleSelective(record, example);
	}

	@Override
	public int updateByExample(SysAction record, SysActionExample example) {
		return mapper.updateByExample(record, example);
	}

	@Override
	public int updateByPrimaryKeySelective(SysAction record) {
		return mapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(SysAction record) {
		return mapper.updateByPrimaryKey(record);
	}

	@Override
	public int sumByExample(SysActionExample example) {
		return 0;
	}

	@Override
	public void deleteAll()  {
		mapper.deleteByExample(new SysActionExample());
	}

	@Override
	public SysActionExample getExample(SysAction record) {
		SysActionExample example = new SysActionExample();
		if(record!=null){
			Criteria criteria = example.createCriteria();
							if(record.getActionId()!=null){
				criteria.andActionIdEqualTo(record.getActionId());
				}
				if(record.getActionFlag()!=null){
				criteria.andActionFlagEqualTo(record.getActionFlag());
				}
				if(record.getActionName()!=null){
				criteria.andActionNameEqualTo(record.getActionName());
				}
				if(record.getActionDesc()!=null){
				criteria.andActionDescEqualTo(record.getActionDesc());
				}
				if(record.getActionMenu()!=null){
				criteria.andActionMenuEqualTo(record.getActionMenu());
				}
				if(record.getField1()!=null){
				criteria.andField1EqualTo(record.getField1());
				}

		}
		return example;
	}
	
	
	@Override
	@Transactional
	public int batchInsert(List<SysAction> records) {
	    if(records.size()==1){
	    	//return insert(records.get(0));
	    }
		SqlSession session=sqlSessionFactory.openSession();
		Connection conn = session.getConnection();
		int result=0;
		try {
			conn.setAutoCommit(false);
			
			Statement st = conn.createStatement();
			StringBuffer sb=new StringBuffer();
			sb.append("INSERT INTO T_SYS_ACTION() values");
			int i=0;
			for (SysAction record : records) {
				if(i>0){
					sb.append(",");
				}
				i++;
				
			
				sb.append("(");
			
				if(record.getActionId()==null){
						sb.append("null");
				}else{
				    Object o=record.getActionId();
					if (o instanceof Date) {
						SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						sb.append("'" + sf.format(record.getActionId()) + "'");
					} else {
						sb.append("'" + record.getActionId() + "'");
					}
				}
			
				sb.append(",");
			
				if(record.getActionFlag()==null){
						sb.append("null");
				}else{
				    Object o=record.getActionFlag();
					if (o instanceof Date) {
						SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						sb.append("'" + sf.format(record.getActionFlag()) + "'");
					} else {
						sb.append("'" + record.getActionFlag() + "'");
					}
				}
			
				sb.append(",");
			
				if(record.getActionName()==null){
						sb.append("null");
				}else{
				    Object o=record.getActionName();
					if (o instanceof Date) {
						SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						sb.append("'" + sf.format(record.getActionName()) + "'");
					} else {
						sb.append("'" + record.getActionName() + "'");
					}
				}
			
				sb.append(",");
			
				if(record.getActionDesc()==null){
						sb.append("'"+"NULL "+"'");						
				}else{
				    Object o=record.getActionDesc();
					if (o instanceof Date) {
						SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						sb.append("'" + sf.format(record.getActionDesc()) + "'");
					} else {
						sb.append("'" + record.getActionDesc() + "'");
					}
				}
			
				sb.append(",");
			
				if(record.getActionMenu()==null){
						sb.append("null");
				}else{
				    Object o=record.getActionMenu();
					if (o instanceof Date) {
						SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						sb.append("'" + sf.format(record.getActionMenu()) + "'");
					} else {
						sb.append("'" + record.getActionMenu() + "'");
					}
				}
			
				sb.append(",");
			
				if(record.getField1()==null){
						sb.append("'"+"NULL"+"'");						
				}else{
				    Object o=record.getField1();
					if (o instanceof Date) {
						SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						sb.append("'" + sf.format(record.getField1()) + "'");
					} else {
						sb.append("'" + record.getField1() + "'");
					}
				}
							sb.append(")");
			
			}
			result=st.executeUpdate(sb.toString());
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new RuntimeException(e);
		}finally{
			session.close();
		}
		return result;
	}
	
	
}
