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

import cn.msec.msc.mysqlgens.admin.entity.SysUserRole;
import cn.msec.msc.mysqlgens.admin.entity.SysUserRoleExample;
import cn.msec.msc.mysqlgens.admin.entity.SysUserRoleExample.Criteria;
import cn.msec.msc.mysqlgens.admin.entity.SysUserRoleKey;
import cn.msec.msc.mysqlgens.admin.mapper.SysUserRoleMapper;
import cn.msec.ojpa.annotations.Tab;
import cn.msec.ojpa.msc.mysql.StaticTableDaoSupport;


@Data
@Tab(name="T_SYS_USER_ROLE")
public class SysUserRoleDao implements StaticTableDaoSupport<SysUserRole, SysUserRoleExample, SysUserRoleKey>{

	private SysUserRoleMapper mapper;

	private SqlSessionFactory sqlSessionFactory;
	
	
	@Override
	public int countByExample(SysUserRoleExample example) {
		return mapper.countByExample(example);
	}

	@Override
	public int deleteByExample(SysUserRoleExample example) {
		return mapper.deleteByExample(example);
	}

	@Override
	public int deleteByPrimaryKey(SysUserRoleKey key) {
		return mapper.deleteByPrimaryKey(key);
	}

	@Override
	public int insert(SysUserRole record)  {
		return mapper.insert(record);
	}

	@Override
	public int insertSelective(SysUserRole record)  {
		return mapper.insertSelective(record);
	}

	@Override
	@Transactional
	public int batchUpdate(List<SysUserRole> records)
			 {
		for(SysUserRole record : records){
			mapper.updateByPrimaryKeySelective(record);
		}
		return records.size();
	}

	@Override
	@Transactional
	public int batchDelete(List<SysUserRole> records)
			 {
		for(SysUserRole record : records){
			mapper.deleteByPrimaryKey(record);
		}
		return records.size();
	}

	@Override
	public List<SysUserRole> selectByExample(SysUserRoleExample example)
			 {
		return mapper.selectByExample(example);
	}

	@Override
	public SysUserRole selectByPrimaryKey(SysUserRoleKey key)
			 {
		return mapper.selectByPrimaryKey(key);
	}

	@Override
	public List<SysUserRole> findAll(List<SysUserRole> records) {
		if(records==null||records.size()<=0){
			return mapper.selectByExample(new SysUserRoleExample());
		}
		List<SysUserRole> list = new ArrayList<>();
		for(SysUserRole record : records){
			SysUserRole result = mapper.selectByPrimaryKey(record);
			if(result!=null){
				list.add(result);
			}
		}
		return list;
	}

	@Override
	public int updateByExampleSelective(SysUserRole record, SysUserRoleExample example)  {
		return mapper.updateByExampleSelective(record, example);
	}

	@Override
	public int updateByExample(SysUserRole record, SysUserRoleExample example) {
		return mapper.updateByExample(record, example);
	}

	@Override
	public int updateByPrimaryKeySelective(SysUserRole record) {
		return mapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(SysUserRole record) {
		return mapper.updateByPrimaryKey(record);
	}

	@Override
	public int sumByExample(SysUserRoleExample example) {
		return 0;
	}

	@Override
	public void deleteAll()  {
		mapper.deleteByExample(new SysUserRoleExample());
	}

	@Override
	public SysUserRoleExample getExample(SysUserRole record) {
		SysUserRoleExample example = new SysUserRoleExample();
		if(record!=null){
			Criteria criteria = example.createCriteria();
							if(record.getUserRoleId()!=null){
				criteria.andUserRoleIdEqualTo(record.getUserRoleId());
				}
				if(record.getRoleId()!=null){
				criteria.andRoleIdEqualTo(record.getRoleId());
				}
				if(record.getUserId()!=null){
				criteria.andUserIdEqualTo(record.getUserId());
				}
				if(record.getStatus()!=null){
				criteria.andStatusEqualTo(record.getStatus());
				}

		}
		return example;
	}
	
	
	@Override
	@Transactional
	public int batchInsert(List<SysUserRole> records) {
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
			sb.append("INSERT INTO T_SYS_USER_ROLE() values");
			int i=0;
			for (SysUserRole record : records) {
				if(i>0){
					sb.append(",");
				}
				i++;
				
			
				sb.append("(");
			
				if(record.getUserRoleId()==null){
						sb.append("null");
				}else{
				    Object o=record.getUserRoleId();
					if (o instanceof Date) {
						SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						sb.append("'" + sf.format(record.getUserRoleId()) + "'");
					} else {
						sb.append("'" + record.getUserRoleId() + "'");
					}
				}
			
				sb.append(",");
			
				if(record.getRoleId()==null){
						sb.append("'"+"NULL"+"'");						
				}else{
				    Object o=record.getRoleId();
					if (o instanceof Date) {
						SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						sb.append("'" + sf.format(record.getRoleId()) + "'");
					} else {
						sb.append("'" + record.getRoleId() + "'");
					}
				}
			
				sb.append(",");
			
				if(record.getUserId()==null){
						sb.append("'"+"NULL"+"'");						
				}else{
				    Object o=record.getUserId();
					if (o instanceof Date) {
						SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						sb.append("'" + sf.format(record.getUserId()) + "'");
					} else {
						sb.append("'" + record.getUserId() + "'");
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
