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

import cn.msec.msc.mysqlgens.admin.entity.SysBusiness;
import cn.msec.msc.mysqlgens.admin.entity.SysBusinessExample;
import cn.msec.msc.mysqlgens.admin.entity.SysBusinessExample.Criteria;
import cn.msec.msc.mysqlgens.admin.entity.SysBusinessKey;
import cn.msec.msc.mysqlgens.admin.mapper.SysBusinessMapper;
import cn.msec.ojpa.annotations.Tab;
import cn.msec.ojpa.msc.mysql.StaticTableDaoSupport;


@Data
@Tab(name="T_SYS_BUSINESS")
public class SysBusinessDao implements StaticTableDaoSupport<SysBusiness, SysBusinessExample, SysBusinessKey>{

	private SysBusinessMapper mapper;

	private SqlSessionFactory sqlSessionFactory;
	
	
	@Override
	public int countByExample(SysBusinessExample example) {
		return mapper.countByExample(example);
	}

	@Override
	public int deleteByExample(SysBusinessExample example) {
		return mapper.deleteByExample(example);
	}

	@Override
	public int deleteByPrimaryKey(SysBusinessKey key) {
		return mapper.deleteByPrimaryKey(key);
	}

	@Override
	public int insert(SysBusiness record)  {
		return mapper.insert(record);
	}

	@Override
	public int insertSelective(SysBusiness record)  {
		return mapper.insertSelective(record);
	}

	@Override
	@Transactional
	public int batchUpdate(List<SysBusiness> records)
			 {
		for(SysBusiness record : records){
			mapper.updateByPrimaryKeySelective(record);
		}
		return records.size();
	}

	@Override
	@Transactional
	public int batchDelete(List<SysBusiness> records)
			 {
		for(SysBusiness record : records){
			mapper.deleteByPrimaryKey(record);
		}
		return records.size();
	}

	@Override
	public List<SysBusiness> selectByExample(SysBusinessExample example)
			 {
		return mapper.selectByExample(example);
	}

	@Override
	public SysBusiness selectByPrimaryKey(SysBusinessKey key)
			 {
		return mapper.selectByPrimaryKey(key);
	}

	@Override
	public List<SysBusiness> findAll(List<SysBusiness> records) {
		if(records==null||records.size()<=0){
			return mapper.selectByExample(new SysBusinessExample());
		}
		List<SysBusiness> list = new ArrayList<>();
		for(SysBusiness record : records){
			SysBusiness result = mapper.selectByPrimaryKey(record);
			if(result!=null){
				list.add(result);
			}
		}
		return list;
	}

	@Override
	public int updateByExampleSelective(SysBusiness record, SysBusinessExample example)  {
		return mapper.updateByExampleSelective(record, example);
	}

	@Override
	public int updateByExample(SysBusiness record, SysBusinessExample example) {
		return mapper.updateByExample(record, example);
	}

	@Override
	public int updateByPrimaryKeySelective(SysBusiness record) {
		return mapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(SysBusiness record) {
		return mapper.updateByPrimaryKey(record);
	}

	@Override
	public int sumByExample(SysBusinessExample example) {
		return 0;
	}

	@Override
	public void deleteAll()  {
		mapper.deleteByExample(new SysBusinessExample());
	}

	@Override
	public SysBusinessExample getExample(SysBusiness record) {
		SysBusinessExample example = new SysBusinessExample();
		if(record!=null){
			Criteria criteria = example.createCriteria();
							if(record.getBusinessId()!=null){
				criteria.andBusinessIdEqualTo(record.getBusinessId());
				}
				if(record.getBusinessName()!=null){
				criteria.andBusinessNameEqualTo(record.getBusinessName());
				}
				if(record.getBusinessDesc()!=null){
				criteria.andBusinessDescEqualTo(record.getBusinessDesc());
				}
				if(record.getBusinessManager()!=null){
				criteria.andBusinessManagerEqualTo(record.getBusinessManager());
				}
				if(record.getBusinessMobile()!=null){
				criteria.andBusinessMobileEqualTo(record.getBusinessMobile());
				}
				if(record.getBusinessSeq()!=null){
				criteria.andBusinessSeqEqualTo(record.getBusinessSeq());
				}
				if(record.getBeginTime()!=null){
				criteria.andBeginTimeEqualTo(record.getBeginTime());
				}
				if(record.getEndTime()!=null){
				criteria.andEndTimeEqualTo(record.getEndTime());
				}
				if(record.getStatus()!=null){
				criteria.andStatusEqualTo(record.getStatus());
				}
				if(record.getBusinessDataPermission()!=null){
				criteria.andBusinessDataPermissionEqualTo(record.getBusinessDataPermission());
				}

		}
		return example;
	}
	
	
	@Override
	@Transactional
	public int batchInsert(List<SysBusiness> records) {
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
			sb.append("INSERT INTO T_SYS_BUSINESS() values");
			int i=0;
			for (SysBusiness record : records) {
				if(i>0){
					sb.append(",");
				}
				i++;
				
			
				sb.append("(");
			
				if(record.getBusinessId()==null){
						sb.append("null");
				}else{
				    Object o=record.getBusinessId();
					if (o instanceof Date) {
						SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						sb.append("'" + sf.format(record.getBusinessId()) + "'");
					} else {
						sb.append("'" + record.getBusinessId() + "'");
					}
				}
			
				sb.append(",");
			
				if(record.getBusinessName()==null){
						sb.append("'"+"NULL"+"'");						
				}else{
				    Object o=record.getBusinessName();
					if (o instanceof Date) {
						SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						sb.append("'" + sf.format(record.getBusinessName()) + "'");
					} else {
						sb.append("'" + record.getBusinessName() + "'");
					}
				}
			
				sb.append(",");
			
				if(record.getBusinessDesc()==null){
						sb.append("'"+"NULL"+"'");						
				}else{
				    Object o=record.getBusinessDesc();
					if (o instanceof Date) {
						SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						sb.append("'" + sf.format(record.getBusinessDesc()) + "'");
					} else {
						sb.append("'" + record.getBusinessDesc() + "'");
					}
				}
			
				sb.append(",");
			
				if(record.getBusinessManager()==null){
						sb.append("'"+"NULL"+"'");						
				}else{
				    Object o=record.getBusinessManager();
					if (o instanceof Date) {
						SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						sb.append("'" + sf.format(record.getBusinessManager()) + "'");
					} else {
						sb.append("'" + record.getBusinessManager() + "'");
					}
				}
			
				sb.append(",");
			
				if(record.getBusinessMobile()==null){
						sb.append("'"+"NULL"+"'");						
				}else{
				    Object o=record.getBusinessMobile();
					if (o instanceof Date) {
						SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						sb.append("'" + sf.format(record.getBusinessMobile()) + "'");
					} else {
						sb.append("'" + record.getBusinessMobile() + "'");
					}
				}
			
				sb.append(",");
			
				if(record.getBusinessSeq()==null){
						sb.append("'"+"NULL"+"'");						
				}else{
				    Object o=record.getBusinessSeq();
					if (o instanceof Date) {
						SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						sb.append("'" + sf.format(record.getBusinessSeq()) + "'");
					} else {
						sb.append("'" + record.getBusinessSeq() + "'");
					}
				}
			
				sb.append(",");
			
				if(record.getBeginTime()==null){
						sb.append("'"+"NULL"+"'");						
				}else{
				    Object o=record.getBeginTime();
					if (o instanceof Date) {
						SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						sb.append("'" + sf.format(record.getBeginTime()) + "'");
					} else {
						sb.append("'" + record.getBeginTime() + "'");
					}
				}
			
				sb.append(",");
			
				if(record.getEndTime()==null){
						sb.append("'"+"NULL"+"'");						
				}else{
				    Object o=record.getEndTime();
					if (o instanceof Date) {
						SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						sb.append("'" + sf.format(record.getEndTime()) + "'");
					} else {
						sb.append("'" + record.getEndTime() + "'");
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
			
				if(record.getBusinessDataPermission()==null){
						sb.append("'"+"NULL "+"'");						
				}else{
				    Object o=record.getBusinessDataPermission();
					if (o instanceof Date) {
						SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						sb.append("'" + sf.format(record.getBusinessDataPermission()) + "'");
					} else {
						sb.append("'" + record.getBusinessDataPermission() + "'");
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
