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

import cn.msec.msc.mysqlgens.admin.entity.SysRoleAction;
import cn.msec.msc.mysqlgens.admin.entity.SysRoleActionExample;
import cn.msec.msc.mysqlgens.admin.entity.SysRoleActionExample.Criteria;
import cn.msec.msc.mysqlgens.admin.entity.SysRoleActionKey;
import cn.msec.msc.mysqlgens.admin.mapper.SysRoleActionMapper;
import cn.msec.ojpa.annotations.Tab;
import cn.msec.ojpa.msc.mysql.StaticTableDaoSupport;


@Data
@Tab(name="T_SYS_ROLE_ACTION")
public class SysRoleActionDao implements StaticTableDaoSupport<SysRoleAction, SysRoleActionExample, SysRoleActionKey>{

	private SysRoleActionMapper mapper;

	private SqlSessionFactory sqlSessionFactory;
	
	
	@Override
	public int countByExample(SysRoleActionExample example) {
		return mapper.countByExample(example);
	}

	@Override
	public int deleteByExample(SysRoleActionExample example) {
		return mapper.deleteByExample(example);
	}

	@Override
	public int deleteByPrimaryKey(SysRoleActionKey key) {
		return mapper.deleteByPrimaryKey(key);
	}

	@Override
	public int insert(SysRoleAction record)  {
		return mapper.insert(record);
	}

	@Override
	public int insertSelective(SysRoleAction record)  {
		return mapper.insertSelective(record);
	}

	@Override
	@Transactional
	public int batchUpdate(List<SysRoleAction> records)
			 {
		for(SysRoleAction record : records){
			mapper.updateByPrimaryKeySelective(record);
		}
		return records.size();
	}

	@Override
	@Transactional
	public int batchDelete(List<SysRoleAction> records)
			 {
		for(SysRoleAction record : records){
			mapper.deleteByPrimaryKey(record);
		}
		return records.size();
	}

	@Override
	public List<SysRoleAction> selectByExample(SysRoleActionExample example)
			 {
		return mapper.selectByExample(example);
	}

	@Override
	public SysRoleAction selectByPrimaryKey(SysRoleActionKey key)
			 {
		return mapper.selectByPrimaryKey(key);
	}

	@Override
	public List<SysRoleAction> findAll(List<SysRoleAction> records) {
		if(records==null||records.size()<=0){
			return mapper.selectByExample(new SysRoleActionExample());
		}
		List<SysRoleAction> list = new ArrayList<>();
		for(SysRoleAction record : records){
			SysRoleAction result = mapper.selectByPrimaryKey(record);
			if(result!=null){
				list.add(result);
			}
		}
		return list;
	}

	@Override
	public int updateByExampleSelective(SysRoleAction record, SysRoleActionExample example)  {
		return mapper.updateByExampleSelective(record, example);
	}

	@Override
	public int updateByExample(SysRoleAction record, SysRoleActionExample example) {
		return mapper.updateByExample(record, example);
	}

	@Override
	public int updateByPrimaryKeySelective(SysRoleAction record) {
		return mapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(SysRoleAction record) {
		return mapper.updateByPrimaryKey(record);
	}

	@Override
	public int sumByExample(SysRoleActionExample example) {
		return 0;
	}

	@Override
	public void deleteAll()  {
		mapper.deleteByExample(new SysRoleActionExample());
	}

	@Override
	public SysRoleActionExample getExample(SysRoleAction record) {
		SysRoleActionExample example = new SysRoleActionExample();
		if(record!=null){
			Criteria criteria = example.createCriteria();
							if(record.getId()!=null){
				criteria.andIdEqualTo(record.getId());
				}
				if(record.getActionId()!=null){
				criteria.andActionIdEqualTo(record.getActionId());
				}
				if(record.getRoleId()!=null){
				criteria.andRoleIdEqualTo(record.getRoleId());
				}
				if(record.getStatus()!=null){
				criteria.andStatusEqualTo(record.getStatus());
				}

		}
		return example;
	}
	
	
	@Override
	@Transactional
	public int batchInsert(List<SysRoleAction> records) {
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
			sb.append("INSERT INTO T_SYS_ROLE_ACTION() values");
			int i=0;
			for (SysRoleAction record : records) {
				if(i>0){
					sb.append(",");
				}
				i++;
				
			
				sb.append("(");
			
				if(record.getId()==null){
						sb.append("null");
				}else{
				    Object o=record.getId();
					if (o instanceof Date) {
						SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						sb.append("'" + sf.format(record.getId()) + "'");
					} else {
						sb.append("'" + record.getId() + "'");
					}
				}
			
				sb.append(",");
			
				if(record.getActionId()==null){
						sb.append("'"+"NULL "+"'");						
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
			
				if(record.getRoleId()==null){
						sb.append("'"+"NULL "+"'");						
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
			
				if(record.getStatus()==null){
						sb.append("null");
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
