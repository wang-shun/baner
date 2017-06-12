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

import cn.msec.msc.mysqlgens.admin.entity.SysRspCode;
import cn.msec.msc.mysqlgens.admin.entity.SysRspCodeExample;
import cn.msec.msc.mysqlgens.admin.entity.SysRspCodeExample.Criteria;
import cn.msec.msc.mysqlgens.admin.entity.SysRspCodeKey;
import cn.msec.msc.mysqlgens.admin.mapper.SysRspCodeMapper;
import cn.msec.ojpa.annotations.Tab;
import cn.msec.ojpa.msc.mysql.StaticTableDaoSupport;


@Data
@Tab(name="T_SYS_RSP_CODE")
public class SysRspCodeDao implements StaticTableDaoSupport<SysRspCode, SysRspCodeExample, SysRspCodeKey>{

	private SysRspCodeMapper mapper;

	private SqlSessionFactory sqlSessionFactory;
	
	
	@Override
	public int countByExample(SysRspCodeExample example) {
		return mapper.countByExample(example);
	}

	@Override
	public int deleteByExample(SysRspCodeExample example) {
		return mapper.deleteByExample(example);
	}

	@Override
	public int deleteByPrimaryKey(SysRspCodeKey key) {
		return mapper.deleteByPrimaryKey(key);
	}

	@Override
	public int insert(SysRspCode record)  {
		return mapper.insert(record);
	}

	@Override
	public int insertSelective(SysRspCode record)  {
		return mapper.insertSelective(record);
	}

	@Override
	@Transactional
	public int batchUpdate(List<SysRspCode> records)
			 {
		for(SysRspCode record : records){
			mapper.updateByPrimaryKeySelective(record);
		}
		return records.size();
	}

	@Override
	@Transactional
	public int batchDelete(List<SysRspCode> records)
			 {
		for(SysRspCode record : records){
			mapper.deleteByPrimaryKey(record);
		}
		return records.size();
	}

	@Override
	public List<SysRspCode> selectByExample(SysRspCodeExample example)
			 {
		return mapper.selectByExample(example);
	}

	@Override
	public SysRspCode selectByPrimaryKey(SysRspCodeKey key)
			 {
		return mapper.selectByPrimaryKey(key);
	}

	@Override
	public List<SysRspCode> findAll(List<SysRspCode> records) {
		if(records==null||records.size()<=0){
			return mapper.selectByExample(new SysRspCodeExample());
		}
		List<SysRspCode> list = new ArrayList<>();
		for(SysRspCode record : records){
			SysRspCode result = mapper.selectByPrimaryKey(record);
			if(result!=null){
				list.add(result);
			}
		}
		return list;
	}

	@Override
	public int updateByExampleSelective(SysRspCode record, SysRspCodeExample example)  {
		return mapper.updateByExampleSelective(record, example);
	}

	@Override
	public int updateByExample(SysRspCode record, SysRspCodeExample example) {
		return mapper.updateByExample(record, example);
	}

	@Override
	public int updateByPrimaryKeySelective(SysRspCode record) {
		return mapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(SysRspCode record) {
		return mapper.updateByPrimaryKey(record);
	}

	@Override
	public int sumByExample(SysRspCodeExample example) {
		return 0;
	}

	@Override
	public void deleteAll()  {
		mapper.deleteByExample(new SysRspCodeExample());
	}

	@Override
	public SysRspCodeExample getExample(SysRspCode record) {
		SysRspCodeExample example = new SysRspCodeExample();
		if(record!=null){
			Criteria criteria = example.createCriteria();
							if(record.getId()!=null){
				criteria.andIdEqualTo(record.getId());
				}
				if(record.getProdId()!=null){
				criteria.andProdIdEqualTo(record.getProdId());
				}
				if(record.getOutRspCode()!=null){
				criteria.andOutRspCodeEqualTo(record.getOutRspCode());
				}
				if(record.getInRspCode2()!=null){
				criteria.andInRspCode2EqualTo(record.getInRspCode2());
				}
				if(record.getRspDesc()!=null){
				criteria.andRspDescEqualTo(record.getRspDesc());
				}

		}
		return example;
	}
	
	
	@Override
	@Transactional
	public int batchInsert(List<SysRspCode> records) {
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
			sb.append("INSERT INTO T_SYS_RSP_CODE() values");
			int i=0;
			for (SysRspCode record : records) {
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
			
				if(record.getProdId()==null){
						sb.append("null");
				}else{
				    Object o=record.getProdId();
					if (o instanceof Date) {
						SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						sb.append("'" + sf.format(record.getProdId()) + "'");
					} else {
						sb.append("'" + record.getProdId() + "'");
					}
				}
			
				sb.append(",");
			
				if(record.getOutRspCode()==null){
						sb.append("null");
				}else{
				    Object o=record.getOutRspCode();
					if (o instanceof Date) {
						SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						sb.append("'" + sf.format(record.getOutRspCode()) + "'");
					} else {
						sb.append("'" + record.getOutRspCode() + "'");
					}
				}
			
				sb.append(",");
			
				if(record.getInRspCode2()==null){
						sb.append("null");
				}else{
				    Object o=record.getInRspCode2();
					if (o instanceof Date) {
						SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						sb.append("'" + sf.format(record.getInRspCode2()) + "'");
					} else {
						sb.append("'" + record.getInRspCode2() + "'");
					}
				}
			
				sb.append(",");
			
				if(record.getRspDesc()==null){
						sb.append("'"+"NULL"+"'");						
				}else{
				    Object o=record.getRspDesc();
					if (o instanceof Date) {
						SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						sb.append("'" + sf.format(record.getRspDesc()) + "'");
					} else {
						sb.append("'" + record.getRspDesc() + "'");
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
