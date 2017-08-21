package org.inn.baner.handler.data;


import com.ztkx.transplat.container.HandlerException;
import com.ztkx.transplat.container.initload.AbstractTMMybatis;
import org.apache.log4j.Logger;
import org.inn.baner.bean.LabelSubject;
import org.inn.baner.bean.LabelSubjectExample;
import org.inn.baner.persist.mapper.LabelSubjectMapper;

import java.util.List;

public class LabelSubjectData extends AbstractTMMybatis {

	Logger logger = Logger.getLogger(LabelSubjectData.class);
	static String tableName = "label_subject";

	/**
	 * 插入帖子信息
	 * @param labelSubject
	 * @throws HandlerException
	 */
	public int insertRecord(LabelSubject labelSubject) throws HandlerException {
		int count = -1;
		try {
			//初始化sqlSession
			getSqlSession();
			 LabelSubjectMapper labelSubjectMapper = sqlSession.getMapper(LabelSubjectMapper.class);
			count = labelSubjectMapper.insert(labelSubject);
			if (logger.isDebugEnabled()) {
				logger.debug("insert table " + tableName + " success ! insert count =[" + count + "] ");
			}
		} catch (HandlerException e) {
			logger.error("exec sql error", e);
			throw new HandlerException(e);
		}finally {
			relaceResource();
		}
		return count;
	}

	public List<LabelSubject> qryByLabelId(String subject,String labelid) throws HandlerException {
		List<LabelSubject> labelSubjectList = null;
		try {
			//初始化sqlSession
			getSqlSession();
            LabelSubjectExample example = new LabelSubjectExample();
            example.createCriteria().andLabelidEqualTo(labelid).andSubjectEqualTo(subject);

            LabelSubjectMapper labelSubjectMapper = sqlSession.getMapper(LabelSubjectMapper.class);
            labelSubjectList = labelSubjectMapper.selectByExample(example);

			if (logger.isDebugEnabled()) {
				logger.debug("qry table " + tableName + " success list size ["+labelSubjectList.size()+"]");
			}
		} catch (HandlerException e) {
			logger.error("exec sql error", e);
			throw new HandlerException(e);
		}finally {
			relaceResource();
		}
		return labelSubjectList;
	}

    /**
     * 根据主体id，查询该主题标签列表
     * @param subject
     * @param subjectId
     * @return
     * @throws HandlerException
     */
    public List<LabelSubject> qryBySubjectId(String subject,String subjectId) throws HandlerException {
        List<LabelSubject> labelSubjectList = null;
        try {
            //初始化sqlSession
            getSqlSession();
            LabelSubjectExample example = new LabelSubjectExample();
            example.createCriteria().andSubjectidEqualTo(subjectId).andSubjectEqualTo(subject);

            LabelSubjectMapper labelSubjectMapper = sqlSession.getMapper(LabelSubjectMapper.class);
            labelSubjectList = labelSubjectMapper.selectByExample(example);

            if (logger.isDebugEnabled()) {
                logger.debug("qry table " + tableName + " success list size ["+labelSubjectList.size()+"]");
            }
        } catch (HandlerException e) {
            logger.error("exec sql error", e);
            throw new HandlerException(e);
        }finally {
            relaceResource();
        }
        return labelSubjectList;
    }

}
