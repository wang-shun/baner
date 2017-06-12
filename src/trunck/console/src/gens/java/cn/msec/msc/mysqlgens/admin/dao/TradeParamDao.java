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

import cn.msec.msc.mysqlgens.admin.entity.TradeParam;
import cn.msec.msc.mysqlgens.admin.entity.TradeParamExample;
import cn.msec.msc.mysqlgens.admin.entity.TradeParamExample.Criteria;
import cn.msec.msc.mysqlgens.admin.entity.TradeParamKey;
import cn.msec.msc.mysqlgens.admin.mapper.TradeParamMapper;
import cn.msec.ojpa.annotations.Tab;
import cn.msec.ojpa.msc.mysql.StaticTableDaoSupport;


@Data
@Tab(name="T_TRADE_PARAM")
public class TradeParamDao implements StaticTableDaoSupport<TradeParam, TradeParamExample, TradeParamKey>{

	private TradeParamMapper mapper;

	private SqlSessionFactory sqlSessionFactory;
	
	
	@Override
	public int countByExample(TradeParamExample example) {
		return mapper.countByExample(example);
	}

	@Override
	public int deleteByExample(TradeParamExample example) {
		return mapper.deleteByExample(example);
	}

	@Override
	public int deleteByPrimaryKey(TradeParamKey key) {
		return mapper.deleteByPrimaryKey(key);
	}

	@Override
	public int insert(TradeParam record)  {
		return mapper.insert(record);
	}

	@Override
	public int insertSelective(TradeParam record)  {
		return mapper.insertSelective(record);
	}

	@Override
	@Transactional
	public int batchUpdate(List<TradeParam> records)
			 {
		for(TradeParam record : records){
			mapper.updateByPrimaryKeySelective(record);
		}
		return records.size();
	}

	@Override
	@Transactional
	public int batchDelete(List<TradeParam> records)
			 {
		for(TradeParam record : records){
			mapper.deleteByPrimaryKey(record);
		}
		return records.size();
	}

	@Override
	public List<TradeParam> selectByExample(TradeParamExample example)
			 {
		return mapper.selectByExample(example);
	}

	@Override
	public TradeParam selectByPrimaryKey(TradeParamKey key)
			 {
		return mapper.selectByPrimaryKey(key);
	}

	@Override
	public List<TradeParam> findAll(List<TradeParam> records) {
		if(records==null||records.size()<=0){
			return mapper.selectByExample(new TradeParamExample());
		}
		List<TradeParam> list = new ArrayList<>();
		for(TradeParam record : records){
			TradeParam result = mapper.selectByPrimaryKey(record);
			if(result!=null){
				list.add(result);
			}
		}
		return list;
	}

	@Override
	public int updateByExampleSelective(TradeParam record, TradeParamExample example)  {
		return mapper.updateByExampleSelective(record, example);
	}

	@Override
	public int updateByExample(TradeParam record, TradeParamExample example) {
		return mapper.updateByExample(record, example);
	}

	@Override
	public int updateByPrimaryKeySelective(TradeParam record) {
		return mapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(TradeParam record) {
		return mapper.updateByPrimaryKey(record);
	}

	@Override
	public int sumByExample(TradeParamExample example) {
		return 0;
	}

	@Override
	public void deleteAll()  {
		mapper.deleteByExample(new TradeParamExample());
	}

	@Override
	public TradeParamExample getExample(TradeParam record) {
		TradeParamExample example = new TradeParamExample();
		if(record!=null){
			Criteria criteria = example.createCriteria();
							if(record.getParamId()!=null){
				criteria.andParamIdEqualTo(record.getParamId());
				}
				if(record.getParamType()!=null){
				criteria.andParamTypeEqualTo(record.getParamType());
				}
				if(record.getParamValue()!=null){
				criteria.andParamValueEqualTo(record.getParamValue());
				}
				if(record.getDsc()!=null){
				criteria.andDscEqualTo(record.getDsc());
				}
				if(record.getCrtTime()!=null){
				criteria.andCrtTimeEqualTo(record.getCrtTime());
				}
				if(record.getField1()!=null){
				criteria.andField1EqualTo(record.getField1());
				}
				if(record.getField2()!=null){
				criteria.andField2EqualTo(record.getField2());
				}
				if(record.getField3()!=null){
				criteria.andField3EqualTo(record.getField3());
				}

		}
		return example;
	}
	
	
	@Override
	@Transactional
	public int batchInsert(List<TradeParam> records) {
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
			sb.append("INSERT INTO T_TRADE_PARAM() values");
			int i=0;
			for (TradeParam record : records) {
				if(i>0){
					sb.append(",");
				}
				i++;
				
			
				sb.append("(");
			
				if(record.getParamId()==null){
						sb.append("null");
				}else{
				    Object o=record.getParamId();
					if (o instanceof Date) {
						SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						sb.append("'" + sf.format(record.getParamId()) + "'");
					} else {
						sb.append("'" + record.getParamId() + "'");
					}
				}
			
				sb.append(",");
			
				if(record.getParamType()==null){
						sb.append("'"+"NULL"+"'");						
				}else{
				    Object o=record.getParamType();
					if (o instanceof Date) {
						SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						sb.append("'" + sf.format(record.getParamType()) + "'");
					} else {
						sb.append("'" + record.getParamType() + "'");
					}
				}
			
				sb.append(",");
			
				if(record.getParamValue()==null){
						sb.append("'"+"NULL"+"'");						
				}else{
				    Object o=record.getParamValue();
					if (o instanceof Date) {
						SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						sb.append("'" + sf.format(record.getParamValue()) + "'");
					} else {
						sb.append("'" + record.getParamValue() + "'");
					}
				}
			
				sb.append(",");
			
				if(record.getDsc()==null){
						sb.append("'"+"NULL"+"'");						
				}else{
				    Object o=record.getDsc();
					if (o instanceof Date) {
						SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						sb.append("'" + sf.format(record.getDsc()) + "'");
					} else {
						sb.append("'" + record.getDsc() + "'");
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
			
				sb.append(",");
			
				if(record.getField2()==null){
						sb.append("'"+"NULL"+"'");						
				}else{
				    Object o=record.getField2();
					if (o instanceof Date) {
						SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						sb.append("'" + sf.format(record.getField2()) + "'");
					} else {
						sb.append("'" + record.getField2() + "'");
					}
				}
			
				sb.append(",");
			
				if(record.getField3()==null){
						sb.append("'"+"NULL"+"'");						
				}else{
				    Object o=record.getField3();
					if (o instanceof Date) {
						SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						sb.append("'" + sf.format(record.getField3()) + "'");
					} else {
						sb.append("'" + record.getField3() + "'");
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
