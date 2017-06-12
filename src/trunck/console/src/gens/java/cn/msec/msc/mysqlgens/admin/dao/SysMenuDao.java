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

import cn.msec.msc.mysqlgens.admin.entity.SysMenu;
import cn.msec.msc.mysqlgens.admin.entity.SysMenuExample;
import cn.msec.msc.mysqlgens.admin.entity.SysMenuExample.Criteria;
import cn.msec.msc.mysqlgens.admin.entity.SysMenuKey;
import cn.msec.msc.mysqlgens.admin.mapper.SysMenuMapper;
import cn.msec.ojpa.annotations.Tab;
import cn.msec.ojpa.msc.mysql.StaticTableDaoSupport;


@Data
@Tab(name="T_SYS_MENU")
public class SysMenuDao implements StaticTableDaoSupport<SysMenu, SysMenuExample, SysMenuKey>{

	private SysMenuMapper mapper;

	private SqlSessionFactory sqlSessionFactory;
	
	
	@Override
	public int countByExample(SysMenuExample example) {
		return mapper.countByExample(example);
	}

	@Override
	public int deleteByExample(SysMenuExample example) {
		return mapper.deleteByExample(example);
	}

	@Override
	public int deleteByPrimaryKey(SysMenuKey key) {
		return mapper.deleteByPrimaryKey(key);
	}

	@Override
	public int insert(SysMenu record)  {
		return mapper.insert(record);
	}

	@Override
	public int insertSelective(SysMenu record)  {
		return mapper.insertSelective(record);
	}

	@Override
	@Transactional
	public int batchUpdate(List<SysMenu> records)
			 {
		for(SysMenu record : records){
			mapper.updateByPrimaryKeySelective(record);
		}
		return records.size();
	}

	@Override
	@Transactional
	public int batchDelete(List<SysMenu> records)
			 {
		for(SysMenu record : records){
			mapper.deleteByPrimaryKey(record);
		}
		return records.size();
	}

	@Override
	public List<SysMenu> selectByExample(SysMenuExample example)
			 {
		return mapper.selectByExample(example);
	}

	@Override
	public SysMenu selectByPrimaryKey(SysMenuKey key)
			 {
		return mapper.selectByPrimaryKey(key);
	}

	@Override
	public List<SysMenu> findAll(List<SysMenu> records) {
		if(records==null||records.size()<=0){
			return mapper.selectByExample(new SysMenuExample());
		}
		List<SysMenu> list = new ArrayList<>();
		for(SysMenu record : records){
			SysMenu result = mapper.selectByPrimaryKey(record);
			if(result!=null){
				list.add(result);
			}
		}
		return list;
	}

	@Override
	public int updateByExampleSelective(SysMenu record, SysMenuExample example)  {
		return mapper.updateByExampleSelective(record, example);
	}

	@Override
	public int updateByExample(SysMenu record, SysMenuExample example) {
		return mapper.updateByExample(record, example);
	}

	@Override
	public int updateByPrimaryKeySelective(SysMenu record) {
		return mapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(SysMenu record) {
		return mapper.updateByPrimaryKey(record);
	}

	@Override
	public int sumByExample(SysMenuExample example) {
		return 0;
	}

	@Override
	public void deleteAll()  {
		mapper.deleteByExample(new SysMenuExample());
	}

	@Override
	public SysMenuExample getExample(SysMenu record) {
		SysMenuExample example = new SysMenuExample();
		if(record!=null){
			Criteria criteria = example.createCriteria();
							if(record.getMenuId()!=null){
				criteria.andMenuIdEqualTo(record.getMenuId());
				}
				if(record.getMenuName()!=null){
				criteria.andMenuNameEqualTo(record.getMenuName());
				}
				if(record.getMenuDesc()!=null){
				criteria.andMenuDescEqualTo(record.getMenuDesc());
				}
				if(record.getMenuSeq()!=null){
				criteria.andMenuSeqEqualTo(record.getMenuSeq());
				}
				if(record.getMenuUrl()!=null){
				criteria.andMenuUrlEqualTo(record.getMenuUrl());
				}
				if(record.getParentId()!=null){
				criteria.andParentIdEqualTo(record.getParentId());
				}
				if(record.getLeafFlag()!=null){
				criteria.andLeafFlagEqualTo(record.getLeafFlag());
				}
				if(record.getLevel1()!=null){
				criteria.andLevel1EqualTo(record.getLevel1());
				}
				if(record.getIcon()!=null){
				criteria.andIconEqualTo(record.getIcon());
				}

		}
		return example;
	}
	
	
	@Override
	@Transactional
	public int batchInsert(List<SysMenu> records) {
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
			sb.append("INSERT INTO T_SYS_MENU() values");
			int i=0;
			for (SysMenu record : records) {
				if(i>0){
					sb.append(",");
				}
				i++;
				
			
				sb.append("(");
			
				if(record.getMenuId()==null){
						sb.append("null");
				}else{
				    Object o=record.getMenuId();
					if (o instanceof Date) {
						SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						sb.append("'" + sf.format(record.getMenuId()) + "'");
					} else {
						sb.append("'" + record.getMenuId() + "'");
					}
				}
			
				sb.append(",");
			
				if(record.getMenuName()==null){
						sb.append("'"+"NULL"+"'");						
				}else{
				    Object o=record.getMenuName();
					if (o instanceof Date) {
						SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						sb.append("'" + sf.format(record.getMenuName()) + "'");
					} else {
						sb.append("'" + record.getMenuName() + "'");
					}
				}
			
				sb.append(",");
			
				if(record.getMenuDesc()==null){
						sb.append("'"+"NULL"+"'");						
				}else{
				    Object o=record.getMenuDesc();
					if (o instanceof Date) {
						SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						sb.append("'" + sf.format(record.getMenuDesc()) + "'");
					} else {
						sb.append("'" + record.getMenuDesc() + "'");
					}
				}
			
				sb.append(",");
			
				if(record.getMenuSeq()==null){
						sb.append("'"+"NULL"+"'");						
				}else{
				    Object o=record.getMenuSeq();
					if (o instanceof Date) {
						SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						sb.append("'" + sf.format(record.getMenuSeq()) + "'");
					} else {
						sb.append("'" + record.getMenuSeq() + "'");
					}
				}
			
				sb.append(",");
			
				if(record.getMenuUrl()==null){
						sb.append("'"+"NULL"+"'");						
				}else{
				    Object o=record.getMenuUrl();
					if (o instanceof Date) {
						SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						sb.append("'" + sf.format(record.getMenuUrl()) + "'");
					} else {
						sb.append("'" + record.getMenuUrl() + "'");
					}
				}
			
				sb.append(",");
			
				if(record.getParentId()==null){
						sb.append("'"+"NULL"+"'");						
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
						sb.append("'"+"NULL"+"'");						
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
			
				if(record.getLevel1()==null){
						sb.append("'"+"NULL"+"'");						
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
