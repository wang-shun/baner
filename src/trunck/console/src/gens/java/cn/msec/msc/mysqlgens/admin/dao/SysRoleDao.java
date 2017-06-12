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

import cn.msec.msc.mysqlgens.admin.entity.SysRole;
import cn.msec.msc.mysqlgens.admin.entity.SysRoleExample;
import cn.msec.msc.mysqlgens.admin.entity.SysRoleExample.Criteria;
import cn.msec.msc.mysqlgens.admin.entity.SysRoleKey;
import cn.msec.msc.mysqlgens.admin.mapper.SysRoleMapper;
import cn.msec.ojpa.annotations.Tab;
import cn.msec.ojpa.msc.mysql.StaticTableDaoSupport;


@Data
@Tab(name="T_SYS_ROLE")
public class SysRoleDao implements StaticTableDaoSupport<SysRole, SysRoleExample, SysRoleKey>{

	private SysRoleMapper mapper;

	private SqlSessionFactory sqlSessionFactory;
	
	
	@Override
	public int countByExample(SysRoleExample example) {
		return mapper.countByExample(example);
	}

	@Override
	public int deleteByExample(SysRoleExample example) {
		return mapper.deleteByExample(example);
	}

	@Override
	public int deleteByPrimaryKey(SysRoleKey key) {
		return mapper.deleteByPrimaryKey(key);
	}

	@Override
	public int insert(SysRole record)  {
		return mapper.insert(record);
	}

	@Override
	public int insertSelective(SysRole record)  {
		return mapper.insertSelective(record);
	}

	@Override
	@Transactional
	public int batchUpdate(List<SysRole> records)
			 {
		for(SysRole record : records){
			mapper.updateByPrimaryKeySelective(record);
		}
		return records.size();
	}

	@Override
	@Transactional
	public int batchDelete(List<SysRole> records)
			 {
		for(SysRole record : records){
			mapper.deleteByPrimaryKey(record);
		}
		return records.size();
	}

	@Override
	public List<SysRole> selectByExample(SysRoleExample example)
			 {
		return mapper.selectByExample(example);
	}

	@Override
	public SysRole selectByPrimaryKey(SysRoleKey key)
			 {
		return mapper.selectByPrimaryKey(key);
	}

	@Override
	public List<SysRole> findAll(List<SysRole> records) {
		if(records==null||records.size()<=0){
			return mapper.selectByExample(new SysRoleExample());
		}
		List<SysRole> list = new ArrayList<>();
		for(SysRole record : records){
			SysRole result = mapper.selectByPrimaryKey(record);
			if(result!=null){
				list.add(result);
			}
		}
		return list;
	}

	@Override
	public int updateByExampleSelective(SysRole record, SysRoleExample example)  {
		return mapper.updateByExampleSelective(record, example);
	}

	@Override
	public int updateByExample(SysRole record, SysRoleExample example) {
		return mapper.updateByExample(record, example);
	}

	@Override
	public int updateByPrimaryKeySelective(SysRole record) {
		return mapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(SysRole record) {
		return mapper.updateByPrimaryKey(record);
	}

	@Override
	public int sumByExample(SysRoleExample example) {
		return 0;
	}

	@Override
	public void deleteAll()  {
		mapper.deleteByExample(new SysRoleExample());
	}

	@Override
	public SysRoleExample getExample(SysRole record) {
		SysRoleExample example = new SysRoleExample();
		if(record!=null){
			Criteria criteria = example.createCriteria();
							if(record.getRoleId()!=null){
				criteria.andRoleIdEqualTo(record.getRoleId());
				}
				if(record.getRoleName()!=null){
				criteria.andRoleNameEqualTo(record.getRoleName());
				}
				if(record.getRoleDesc()!=null){
				criteria.andRoleDescEqualTo(record.getRoleDesc());
				}
				if(record.getStatus()!=null){
				criteria.andStatusEqualTo(record.getStatus());
				}

		}
		return example;
	}
	
	
	@Override
	@Transactional
	public int batchInsert(List<SysRole> records) {
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
			sb.append("INSERT INTO T_SYS_ROLE() values");
			int i=0;
			for (SysRole record : records) {
				if(i>0){
					sb.append(",");
				}
				i++;
				
			
				sb.append("(");
			
				if(record.getRoleId()==null){
						sb.append("null");
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
			
				if(record.getRoleName()==null){
						sb.append("'"+"NULL"+"'");						
				}else{
				    Object o=record.getRoleName();
					if (o instanceof Date) {
						SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						sb.append("'" + sf.format(record.getRoleName()) + "'");
					} else {
						sb.append("'" + record.getRoleName() + "'");
					}
				}
			
				sb.append(",");
			
				if(record.getRoleDesc()==null){
						sb.append("'"+"NULL"+"'");						
				}else{
				    Object o=record.getRoleDesc();
					if (o instanceof Date) {
						SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						sb.append("'" + sf.format(record.getRoleDesc()) + "'");
					} else {
						sb.append("'" + record.getRoleDesc() + "'");
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
