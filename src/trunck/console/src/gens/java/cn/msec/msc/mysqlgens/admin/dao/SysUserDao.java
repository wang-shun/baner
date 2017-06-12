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

import cn.msec.msc.mysqlgens.admin.entity.SysUser;
import cn.msec.msc.mysqlgens.admin.entity.SysUserExample;
import cn.msec.msc.mysqlgens.admin.entity.SysUserExample.Criteria;
import cn.msec.msc.mysqlgens.admin.entity.SysUserKey;
import cn.msec.msc.mysqlgens.admin.mapper.SysUserMapper;
import cn.msec.ojpa.annotations.Tab;
import cn.msec.ojpa.msc.mysql.StaticTableDaoSupport;


@Data
@Tab(name="T_SYS_USER")
public class SysUserDao implements StaticTableDaoSupport<SysUser, SysUserExample, SysUserKey>{

	private SysUserMapper mapper;

	private SqlSessionFactory sqlSessionFactory;
	
	
	@Override
	public int countByExample(SysUserExample example) {
		return mapper.countByExample(example);
	}

	@Override
	public int deleteByExample(SysUserExample example) {
		return mapper.deleteByExample(example);
	}

	@Override
	public int deleteByPrimaryKey(SysUserKey key) {
		return mapper.deleteByPrimaryKey(key);
	}

	@Override
	public int insert(SysUser record)  {
		return mapper.insert(record);
	}

	@Override
	public int insertSelective(SysUser record)  {
		return mapper.insertSelective(record);
	}

	@Override
	@Transactional
	public int batchUpdate(List<SysUser> records)
			 {
		for(SysUser record : records){
			mapper.updateByPrimaryKeySelective(record);
		}
		return records.size();
	}

	@Override
	@Transactional
	public int batchDelete(List<SysUser> records)
			 {
		for(SysUser record : records){
			mapper.deleteByPrimaryKey(record);
		}
		return records.size();
	}

	@Override
	public List<SysUser> selectByExample(SysUserExample example)
			 {
		return mapper.selectByExample(example);
	}

	@Override
	public SysUser selectByPrimaryKey(SysUserKey key)
			 {
		return mapper.selectByPrimaryKey(key);
	}

	@Override
	public List<SysUser> findAll(List<SysUser> records) {
		if(records==null||records.size()<=0){
			return mapper.selectByExample(new SysUserExample());
		}
		List<SysUser> list = new ArrayList<>();
		for(SysUser record : records){
			SysUser result = mapper.selectByPrimaryKey(record);
			if(result!=null){
				list.add(result);
			}
		}
		return list;
	}

	@Override
	public int updateByExampleSelective(SysUser record, SysUserExample example)  {
		return mapper.updateByExampleSelective(record, example);
	}

	@Override
	public int updateByExample(SysUser record, SysUserExample example) {
		return mapper.updateByExample(record, example);
	}

	@Override
	public int updateByPrimaryKeySelective(SysUser record) {
		return mapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(SysUser record) {
		return mapper.updateByPrimaryKey(record);
	}

	@Override
	public int sumByExample(SysUserExample example) {
		return 0;
	}

	@Override
	public void deleteAll()  {
		mapper.deleteByExample(new SysUserExample());
	}

	@Override
	public SysUserExample getExample(SysUser record) {
		SysUserExample example = new SysUserExample();
		if(record!=null){
			Criteria criteria = example.createCriteria();
							if(record.getUserId()!=null){
				criteria.andUserIdEqualTo(record.getUserId());
				}
				if(record.getBranchId()!=null){
				criteria.andBranchIdEqualTo(record.getBranchId());
				}
				if(record.getLoginName()!=null){
				criteria.andLoginNameEqualTo(record.getLoginName());
				}
				if(record.getUserName()!=null){
				criteria.andUserNameEqualTo(record.getUserName());
				}
				if(record.getMobile()!=null){
				criteria.andMobileEqualTo(record.getMobile());
				}
				if(record.getUserWorkaddress()!=null){
				criteria.andUserWorkaddressEqualTo(record.getUserWorkaddress());
				}
				if(record.getStatus()!=null){
				criteria.andStatusEqualTo(record.getStatus());
				}
				if(record.getPassword()!=null){
				criteria.andPasswordEqualTo(record.getPassword());
				}
				if(record.getUpdateTime()!=null){
				criteria.andUpdateTimeEqualTo(record.getUpdateTime());
				}
				if(record.getCreateTime()!=null){
				criteria.andCreateTimeEqualTo(record.getCreateTime());
				}
				if(record.getCreatedBy()!=null){
				criteria.andCreatedByEqualTo(record.getCreatedBy());
				}
				if(record.getModifiedBy()!=null){
				criteria.andModifiedByEqualTo(record.getModifiedBy());
				}
				if(record.getEmail()!=null){
				criteria.andEmailEqualTo(record.getEmail());
				}
				if(record.getDataEnviron()!=null){
				criteria.andDataEnvironEqualTo(record.getDataEnviron());
				}
				if(record.getIcon()!=null){
				criteria.andIconEqualTo(record.getIcon());
				}

		}
		return example;
	}
	
	
	@Override
	@Transactional
	public int batchInsert(List<SysUser> records) {
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
			sb.append("INSERT INTO T_SYS_USER() values");
			int i=0;
			for (SysUser record : records) {
				if(i>0){
					sb.append(",");
				}
				i++;
				
			
				sb.append("(");
			
				if(record.getUserId()==null){
						sb.append("null");
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
			
				if(record.getBranchId()==null){
						sb.append("'"+"NULL"+"'");						
				}else{
				    Object o=record.getBranchId();
					if (o instanceof Date) {
						SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						sb.append("'" + sf.format(record.getBranchId()) + "'");
					} else {
						sb.append("'" + record.getBranchId() + "'");
					}
				}
			
				sb.append(",");
			
				if(record.getLoginName()==null){
						sb.append("null");
				}else{
				    Object o=record.getLoginName();
					if (o instanceof Date) {
						SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						sb.append("'" + sf.format(record.getLoginName()) + "'");
					} else {
						sb.append("'" + record.getLoginName() + "'");
					}
				}
			
				sb.append(",");
			
				if(record.getUserName()==null){
						sb.append("null");
				}else{
				    Object o=record.getUserName();
					if (o instanceof Date) {
						SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						sb.append("'" + sf.format(record.getUserName()) + "'");
					} else {
						sb.append("'" + record.getUserName() + "'");
					}
				}
			
				sb.append(",");
			
				if(record.getMobile()==null){
						sb.append("'"+"NULL"+"'");						
				}else{
				    Object o=record.getMobile();
					if (o instanceof Date) {
						SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						sb.append("'" + sf.format(record.getMobile()) + "'");
					} else {
						sb.append("'" + record.getMobile() + "'");
					}
				}
			
				sb.append(",");
			
				if(record.getUserWorkaddress()==null){
						sb.append("'"+"NULL"+"'");						
				}else{
				    Object o=record.getUserWorkaddress();
					if (o instanceof Date) {
						SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						sb.append("'" + sf.format(record.getUserWorkaddress()) + "'");
					} else {
						sb.append("'" + record.getUserWorkaddress() + "'");
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
			
				sb.append(",");
			
				if(record.getPassword()==null){
						sb.append("'"+"NULL"+"'");						
				}else{
				    Object o=record.getPassword();
					if (o instanceof Date) {
						SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						sb.append("'" + sf.format(record.getPassword()) + "'");
					} else {
						sb.append("'" + record.getPassword() + "'");
					}
				}
			
				sb.append(",");
			
				if(record.getUpdateTime()==null){
						sb.append("'"+"NULL"+"'");						
				}else{
				    Object o=record.getUpdateTime();
					if (o instanceof Date) {
						SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						sb.append("'" + sf.format(record.getUpdateTime()) + "'");
					} else {
						sb.append("'" + record.getUpdateTime() + "'");
					}
				}
			
				sb.append(",");
			
				if(record.getCreateTime()==null){
						sb.append("'"+"NULL"+"'");						
				}else{
				    Object o=record.getCreateTime();
					if (o instanceof Date) {
						SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						sb.append("'" + sf.format(record.getCreateTime()) + "'");
					} else {
						sb.append("'" + record.getCreateTime() + "'");
					}
				}
			
				sb.append(",");
			
				if(record.getCreatedBy()==null){
						sb.append("'"+"NULL"+"'");						
				}else{
				    Object o=record.getCreatedBy();
					if (o instanceof Date) {
						SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						sb.append("'" + sf.format(record.getCreatedBy()) + "'");
					} else {
						sb.append("'" + record.getCreatedBy() + "'");
					}
				}
			
				sb.append(",");
			
				if(record.getModifiedBy()==null){
						sb.append("'"+"NULL"+"'");						
				}else{
				    Object o=record.getModifiedBy();
					if (o instanceof Date) {
						SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						sb.append("'" + sf.format(record.getModifiedBy()) + "'");
					} else {
						sb.append("'" + record.getModifiedBy() + "'");
					}
				}
			
				sb.append(",");
			
				if(record.getEmail()==null){
						sb.append("'"+"NULL"+"'");						
				}else{
				    Object o=record.getEmail();
					if (o instanceof Date) {
						SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						sb.append("'" + sf.format(record.getEmail()) + "'");
					} else {
						sb.append("'" + record.getEmail() + "'");
					}
				}
			
				sb.append(",");
			
				if(record.getDataEnviron()==null){
						sb.append("'"+"NULL"+"'");						
				}else{
				    Object o=record.getDataEnviron();
					if (o instanceof Date) {
						SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						sb.append("'" + sf.format(record.getDataEnviron()) + "'");
					} else {
						sb.append("'" + record.getDataEnviron() + "'");
					}
				}
			
				sb.append(",");
			
				if(record.getIcon()==null){
						sb.append("'"+"NULL"+"'");						
				}else{
				    Object o=record.getIcon();
					if (o instanceof Date) {
						SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						sb.append("'" + sf.format(record.getIcon()) + "'");
					} else {
						sb.append("'" + record.getIcon() + "'");
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
