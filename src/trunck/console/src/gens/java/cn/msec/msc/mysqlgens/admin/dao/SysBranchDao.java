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

import cn.msec.msc.mysqlgens.admin.entity.SysBranch;
import cn.msec.msc.mysqlgens.admin.entity.SysBranchExample;
import cn.msec.msc.mysqlgens.admin.entity.SysBranchExample.Criteria;
import cn.msec.msc.mysqlgens.admin.entity.SysBranchKey;
import cn.msec.msc.mysqlgens.admin.mapper.SysBranchMapper;
import cn.msec.ojpa.annotations.Tab;
import cn.msec.ojpa.msc.mysql.StaticTableDaoSupport;


@Data
@Tab(name="T_SYS_BRANCH")
public class SysBranchDao implements StaticTableDaoSupport<SysBranch, SysBranchExample, SysBranchKey>{

	private SysBranchMapper mapper;

	private SqlSessionFactory sqlSessionFactory;
	
	
	@Override
	public int countByExample(SysBranchExample example) {
		return mapper.countByExample(example);
	}

	@Override
	public int deleteByExample(SysBranchExample example) {
		return mapper.deleteByExample(example);
	}

	@Override
	public int deleteByPrimaryKey(SysBranchKey key) {
		return mapper.deleteByPrimaryKey(key);
	}

	@Override
	public int insert(SysBranch record)  {
		return mapper.insert(record);
	}

	@Override
	public int insertSelective(SysBranch record)  {
		return mapper.insertSelective(record);
	}

	@Override
	@Transactional
	public int batchUpdate(List<SysBranch> records)
			 {
		for(SysBranch record : records){
			mapper.updateByPrimaryKeySelective(record);
		}
		return records.size();
	}

	@Override
	@Transactional
	public int batchDelete(List<SysBranch> records)
			 {
		for(SysBranch record : records){
			mapper.deleteByPrimaryKey(record);
		}
		return records.size();
	}

	@Override
	public List<SysBranch> selectByExample(SysBranchExample example)
			 {
		return mapper.selectByExample(example);
	}

	@Override
	public SysBranch selectByPrimaryKey(SysBranchKey key)
			 {
		return mapper.selectByPrimaryKey(key);
	}

	@Override
	public List<SysBranch> findAll(List<SysBranch> records) {
		if(records==null||records.size()<=0){
			return mapper.selectByExample(new SysBranchExample());
		}
		List<SysBranch> list = new ArrayList<>();
		for(SysBranch record : records){
			SysBranch result = mapper.selectByPrimaryKey(record);
			if(result!=null){
				list.add(result);
			}
		}
		return list;
	}

	@Override
	public int updateByExampleSelective(SysBranch record, SysBranchExample example)  {
		return mapper.updateByExampleSelective(record, example);
	}

	@Override
	public int updateByExample(SysBranch record, SysBranchExample example) {
		return mapper.updateByExample(record, example);
	}

	@Override
	public int updateByPrimaryKeySelective(SysBranch record) {
		return mapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(SysBranch record) {
		return mapper.updateByPrimaryKey(record);
	}

	@Override
	public int sumByExample(SysBranchExample example) {
		return 0;
	}

	@Override
	public void deleteAll()  {
		mapper.deleteByExample(new SysBranchExample());
	}

	@Override
	public SysBranchExample getExample(SysBranch record) {
		SysBranchExample example = new SysBranchExample();
		if(record!=null){
			Criteria criteria = example.createCriteria();
							if(record.getId()!=null){
				criteria.andIdEqualTo(record.getId());
				}
				if(record.getBranchId()!=null){
				criteria.andBranchIdEqualTo(record.getBranchId());
				}
				if(record.getBranchDesc()!=null){
				criteria.andBranchDescEqualTo(record.getBranchDesc());
				}
				if(record.getBranchName()!=null){
				criteria.andBranchNameEqualTo(record.getBranchName());
				}
				if(record.getBranchManager()!=null){
				criteria.andBranchManagerEqualTo(record.getBranchManager());
				}
				if(record.getMobile()!=null){
				criteria.andMobileEqualTo(record.getMobile());
				}
				if(record.getBeginTime()!=null){
				criteria.andBeginTimeEqualTo(record.getBeginTime());
				}
				if(record.getEndTime()!=null){
				criteria.andEndTimeEqualTo(record.getEndTime());
				}
				if(record.getParentId()!=null){
				criteria.andParentIdEqualTo(record.getParentId());
				}
				if(record.getLeafFlag()!=null){
				criteria.andLeafFlagEqualTo(record.getLeafFlag());
				}
				if(record.getBranchSeq()!=null){
				criteria.andBranchSeqEqualTo(record.getBranchSeq());
				}
				if(record.getLevel1()!=null){
				criteria.andLevel1EqualTo(record.getLevel1());
				}

		}
		return example;
	}
	
	
	@Override
	@Transactional
	public int batchInsert(List<SysBranch> records) {
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
			sb.append("INSERT INTO T_SYS_BRANCH() values");
			int i=0;
			for (SysBranch record : records) {
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
			
				if(record.getBranchId()==null){
						sb.append("null");
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
			
				if(record.getBranchDesc()==null){
						sb.append("'"+"NULL"+"'");						
				}else{
				    Object o=record.getBranchDesc();
					if (o instanceof Date) {
						SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						sb.append("'" + sf.format(record.getBranchDesc()) + "'");
					} else {
						sb.append("'" + record.getBranchDesc() + "'");
					}
				}
			
				sb.append(",");
			
				if(record.getBranchName()==null){
						sb.append("'"+"NULL"+"'");						
				}else{
				    Object o=record.getBranchName();
					if (o instanceof Date) {
						SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						sb.append("'" + sf.format(record.getBranchName()) + "'");
					} else {
						sb.append("'" + record.getBranchName() + "'");
					}
				}
			
				sb.append(",");
			
				if(record.getBranchManager()==null){
						sb.append("'"+"NULL"+"'");						
				}else{
				    Object o=record.getBranchManager();
					if (o instanceof Date) {
						SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						sb.append("'" + sf.format(record.getBranchManager()) + "'");
					} else {
						sb.append("'" + record.getBranchManager() + "'");
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
			
				if(record.getParentId()==null){
						sb.append("null");
				}else{
				    Object o=record.getParentId();
					if (o instanceof Date) {
						SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						sb.append("'" + sf.format(record.getParentId()) + "'");
					} else {
						sb.append("'" + record.getParentId() + "'");
					}
				}
			
				sb.append(",");
			
				if(record.getLeafFlag()==null){
						sb.append("null");
				}else{
				    Object o=record.getLeafFlag();
					if (o instanceof Date) {
						SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						sb.append("'" + sf.format(record.getLeafFlag()) + "'");
					} else {
						sb.append("'" + record.getLeafFlag() + "'");
					}
				}
			
				sb.append(",");
			
				if(record.getBranchSeq()==null){
						sb.append("null");
				}else{
				    Object o=record.getBranchSeq();
					if (o instanceof Date) {
						SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						sb.append("'" + sf.format(record.getBranchSeq()) + "'");
					} else {
						sb.append("'" + record.getBranchSeq() + "'");
					}
				}
			
				sb.append(",");
			
				if(record.getLevel1()==null){
						sb.append("null");
				}else{
				    Object o=record.getLevel1();
					if (o instanceof Date) {
						SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						sb.append("'" + sf.format(record.getLevel1()) + "'");
					} else {
						sb.append("'" + record.getLevel1() + "'");
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
