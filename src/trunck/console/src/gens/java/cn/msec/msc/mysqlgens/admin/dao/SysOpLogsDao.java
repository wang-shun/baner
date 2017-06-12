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

import cn.msec.msc.mysqlgens.admin.entity.SysOpLogs;
import cn.msec.msc.mysqlgens.admin.entity.SysOpLogsExample;
import cn.msec.msc.mysqlgens.admin.entity.SysOpLogsExample.Criteria;
import cn.msec.msc.mysqlgens.admin.entity.SysOpLogsKey;
import cn.msec.msc.mysqlgens.admin.mapper.SysOpLogsMapper;
import cn.msec.ojpa.annotations.Tab;
import cn.msec.ojpa.msc.mysql.StaticTableDaoSupport;


@Data
@Tab(name="T_SYS_OP_LOGS")
public class SysOpLogsDao implements StaticTableDaoSupport<SysOpLogs, SysOpLogsExample, SysOpLogsKey>{

	private SysOpLogsMapper mapper;

	private SqlSessionFactory sqlSessionFactory;
	
	
	@Override
	public int countByExample(SysOpLogsExample example) {
		return mapper.countByExample(example);
	}

	@Override
	public int deleteByExample(SysOpLogsExample example) {
		return mapper.deleteByExample(example);
	}

	@Override
	public int deleteByPrimaryKey(SysOpLogsKey key) {
		return mapper.deleteByPrimaryKey(key);
	}

	@Override
	public int insert(SysOpLogs record)  {
		return mapper.insert(record);
	}

	@Override
	public int insertSelective(SysOpLogs record)  {
		return mapper.insertSelective(record);
	}

	@Override
	@Transactional
	public int batchUpdate(List<SysOpLogs> records)
			 {
		for(SysOpLogs record : records){
			mapper.updateByPrimaryKeySelective(record);
		}
		return records.size();
	}

	@Override
	@Transactional
	public int batchDelete(List<SysOpLogs> records)
			 {
		for(SysOpLogs record : records){
			mapper.deleteByPrimaryKey(record);
		}
		return records.size();
	}

	@Override
	public List<SysOpLogs> selectByExample(SysOpLogsExample example)
			 {
		return mapper.selectByExample(example);
	}

	@Override
	public SysOpLogs selectByPrimaryKey(SysOpLogsKey key)
			 {
		return mapper.selectByPrimaryKey(key);
	}

	@Override
	public List<SysOpLogs> findAll(List<SysOpLogs> records) {
		if(records==null||records.size()<=0){
			return mapper.selectByExample(new SysOpLogsExample());
		}
		List<SysOpLogs> list = new ArrayList<>();
		for(SysOpLogs record : records){
			SysOpLogs result = mapper.selectByPrimaryKey(record);
			if(result!=null){
				list.add(result);
			}
		}
		return list;
	}

	@Override
	public int updateByExampleSelective(SysOpLogs record, SysOpLogsExample example)  {
		return mapper.updateByExampleSelective(record, example);
	}

	@Override
	public int updateByExample(SysOpLogs record, SysOpLogsExample example) {
		return mapper.updateByExample(record, example);
	}

	@Override
	public int updateByPrimaryKeySelective(SysOpLogs record) {
		return mapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(SysOpLogs record) {
		return mapper.updateByPrimaryKey(record);
	}

	@Override
	public int sumByExample(SysOpLogsExample example) {
		return 0;
	}

	@Override
	public void deleteAll()  {
		mapper.deleteByExample(new SysOpLogsExample());
	}

	@Override
	public SysOpLogsExample getExample(SysOpLogs record) {
		SysOpLogsExample example = new SysOpLogsExample();
		if(record!=null){
			Criteria criteria = example.createCriteria();
							if(record.getId()!=null){
				criteria.andIdEqualTo(record.getId());
				}
				if(record.getSkeys()!=null){
				criteria.andSkeysEqualTo(record.getSkeys());
				}
				if(record.getContents()!=null){
				criteria.andContentsEqualTo(record.getContents());
				}
				if(record.getLevel1()!=null){
				criteria.andLevel1EqualTo(record.getLevel1());
				}
				if(record.getCrtTime()!=null){
				criteria.andCrtTimeEqualTo(record.getCrtTime());
				}

		}
		return example;
	}
	
	
	@Override
	@Transactional
	public int batchInsert(List<SysOpLogs> records) {
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
			sb.append("INSERT INTO T_SYS_OP_LOGS() values");
			int i=0;
			for (SysOpLogs record : records) {
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
			
				if(record.getSkeys()==null){
						sb.append("null");
				}else{
				    Object o=record.getSkeys();
					if (o instanceof Date) {
						SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						sb.append("'" + sf.format(record.getSkeys()) + "'");
					} else {
						sb.append("'" + record.getSkeys() + "'");
					}
				}
			
				sb.append(",");
			
				if(record.getContents()==null){
						sb.append("null");
				}else{
				    Object o=record.getContents();
					if (o instanceof Date) {
						SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						sb.append("'" + sf.format(record.getContents()) + "'");
					} else {
						sb.append("'" + record.getContents() + "'");
					}
				}
			
				sb.append(",");
			
				if(record.getLevel1()==null){
						sb.append("'"+"'INFO' "+"'");						
				}else{
				    Object o=record.getLevel1();
					if (o instanceof Date) {
						SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						sb.append("'" + sf.format(record.getLevel1()) + "'");
					} else {
						sb.append("'" + record.getLevel1() + "'");
					}
				}
			
				sb.append(",");
			
				if(record.getCrtTime()==null){
						sb.append("'"+"NULL"+"'");						
				}else{
				    Object o=record.getCrtTime();
					if (o instanceof Date) {
						SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						sb.append("'" + sf.format(record.getCrtTime()) + "'");
					} else {
						sb.append("'" + record.getCrtTime() + "'");
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
