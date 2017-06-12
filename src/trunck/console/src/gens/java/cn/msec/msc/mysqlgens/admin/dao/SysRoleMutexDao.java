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

import cn.msec.msc.mysqlgens.admin.entity.SysRoleMutex;
import cn.msec.msc.mysqlgens.admin.entity.SysRoleMutexExample;
import cn.msec.msc.mysqlgens.admin.entity.SysRoleMutexExample.Criteria;
import cn.msec.msc.mysqlgens.admin.entity.SysRoleMutexKey;
import cn.msec.msc.mysqlgens.admin.mapper.SysRoleMutexMapper;
import cn.msec.ojpa.annotations.Tab;
import cn.msec.ojpa.msc.mysql.StaticTableDaoSupport;


@Data
@Tab(name="T_SYS_ROLE_MUTEX")
public class SysRoleMutexDao implements StaticTableDaoSupport<SysRoleMutex, SysRoleMutexExample, SysRoleMutexKey>{

	private SysRoleMutexMapper mapper;

	private SqlSessionFactory sqlSessionFactory;
	
	
	@Override
	public int countByExample(SysRoleMutexExample example) {
		return mapper.countByExample(example);
	}

	@Override
	public int deleteByExample(SysRoleMutexExample example) {
		return mapper.deleteByExample(example);
	}

	@Override
	public int deleteByPrimaryKey(SysRoleMutexKey key) {
		return mapper.deleteByPrimaryKey(key);
	}

	@Override
	public int insert(SysRoleMutex record)  {
		return mapper.insert(record);
	}

	@Override
	public int insertSelective(SysRoleMutex record)  {
		return mapper.insertSelective(record);
	}

	@Override
	@Transactional
	public int batchUpdate(List<SysRoleMutex> records)
			 {
		for(SysRoleMutex record : records){
			mapper.updateByPrimaryKeySelective(record);
		}
		return records.size();
	}

	@Override
	@Transactional
	public int batchDelete(List<SysRoleMutex> records)
			 {
		for(SysRoleMutex record : records){
			mapper.deleteByPrimaryKey(record);
		}
		return records.size();
	}

	@Override
	public List<SysRoleMutex> selectByExample(SysRoleMutexExample example)
			 {
		return mapper.selectByExample(example);
	}

	@Override
	public SysRoleMutex selectByPrimaryKey(SysRoleMutexKey key)
			 {
		return mapper.selectByPrimaryKey(key);
	}

	@Override
	public List<SysRoleMutex> findAll(List<SysRoleMutex> records) {
		if(records==null||records.size()<=0){
			return mapper.selectByExample(new SysRoleMutexExample());
		}
		List<SysRoleMutex> list = new ArrayList<>();
		for(SysRoleMutex record : records){
			SysRoleMutex result = mapper.selectByPrimaryKey(record);
			if(result!=null){
				list.add(result);
			}
		}
		return list;
	}

	@Override
	public int updateByExampleSelective(SysRoleMutex record, SysRoleMutexExample example)  {
		return mapper.updateByExampleSelective(record, example);
	}

	@Override
	public int updateByExample(SysRoleMutex record, SysRoleMutexExample example) {
		return mapper.updateByExample(record, example);
	}

	@Override
	public int updateByPrimaryKeySelective(SysRoleMutex record) {
		return mapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(SysRoleMutex record) {
		return mapper.updateByPrimaryKey(record);
	}

	@Override
	public int sumByExample(SysRoleMutexExample example) {
		return 0;
	}

	@Override
	public void deleteAll()  {
		mapper.deleteByExample(new SysRoleMutexExample());
	}

	@Override
	public SysRoleMutexExample getExample(SysRoleMutex record) {
		SysRoleMutexExample example = new SysRoleMutexExample();
		if(record!=null){
			Criteria criteria = example.createCriteria();
							if(record.getRoleMutexId()!=null){
				criteria.andRoleMutexIdEqualTo(record.getRoleMutexId());
				}
				if(record.getRoleIdA()!=null){
				criteria.andRoleIdAEqualTo(record.getRoleIdA());
				}
				if(record.getRoleIdB()!=null){
				criteria.andRoleIdBEqualTo(record.getRoleIdB());
				}
				if(record.getStatus()!=null){
				criteria.andStatusEqualTo(record.getStatus());
				}

		}
		return example;
	}
	
	
	@Override
	@Transactional
	public int batchInsert(List<SysRoleMutex> records) {
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
			sb.append("INSERT INTO T_SYS_ROLE_MUTEX() values");
			int i=0;
			for (SysRoleMutex record : records) {
				if(i>0){
					sb.append(",");
				}
				i++;
				
			
				sb.append("(");
			
				if(record.getRoleMutexId()==null){
						sb.append("null");
				}else{
				    Object o=record.getRoleMutexId();
					if (o instanceof Date) {
						SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						sb.append("'" + sf.format(record.getRoleMutexId()) + "'");
					} else {
						sb.append("'" + record.getRoleMutexId() + "'");
					}
				}
			
				sb.append(",");
			
				if(record.getRoleIdA()==null){
						sb.append("'"+"NULL"+"'");						
				}else{
				    Object o=record.getRoleIdA();
					if (o instanceof Date) {
						SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						sb.append("'" + sf.format(record.getRoleIdA()) + "'");
					} else {
						sb.append("'" + record.getRoleIdA() + "'");
					}
				}
			
				sb.append(",");
			
				if(record.getRoleIdB()==null){
						sb.append("'"+"NULL"+"'");						
				}else{
				    Object o=record.getRoleIdB();
					if (o instanceof Date) {
						SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						sb.append("'" + sf.format(record.getRoleIdB()) + "'");
					} else {
						sb.append("'" + record.getRoleIdB() + "'");
					}
				}
			
				sb.append(",");
			
				if(record.getStatus()==null){
						sb.append("'"+"NULL"+"'");						
				}else{
				    Object o=record.getStatus();
					if (o instanceof Date) {
						SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						sb.append("'" + sf.format(record.getStatus()) + "'");
					} else {
						sb.append("'" + record.getStatus() + "'");
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
