package org.inn.baner.serviceimp;

import com.ztkx.transplat.container.HandlerException;
import org.apache.log4j.Logger;
import org.inn.baner.bean.LabelSubject;
import org.inn.baner.bean.Subject;
import org.inn.baner.handler.data.LabelSubjectData;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * 名称：  RecommendService
 * 作者:   rain.wang
 * 日期:   2017/8/15
 * 简介:
 */
public class RecommendService {

    private Logger logger = Logger.getLogger(this.getClass());

    /**
     * 根据标签推荐关联主体
     * 入参为主体名，以及该主体id
     * 出参为关联的主体列表，按权重从大到小排序
     *
     * @param subjectId
     * @return
     */
    public List<Subject> selectRefSubject(String subject, String subjectId){
        LabelSubjectData labelSubjectData = new LabelSubjectData();
        LinkedList<Subject> resultList = new LinkedList<>();
        try {
            List<LabelSubject> list = labelSubjectData.qryBySubjectId(subject,subjectId);
            for(LabelSubject label : list){
                List<LabelSubject> labelSubjectList = labelSubjectData.qryByLabelId(subject,label.getLabelid());
                for(LabelSubject labelSubject : labelSubjectList){
                    if(!subjectId.equals(labelSubject.getSubjectid())){//跳过自身
                        Subject subjectObj = new Subject();
                        subjectObj.setSubjectid(labelSubject.getSubjectid());
                        subjectObj.setSubject(labelSubject.getSubject());

                        if(resultList.contains(subjectObj)){
                            resultList.get(resultList.indexOf(subjectObj)).addWeight(1);
                        }else{
                            subjectObj.setWeight(1);
                            resultList.add(subjectObj);
                        }
                    }
                }
            }
            //按权重，从大到小排序
            Collections.sort(resultList, new Comparator<Subject>() {
                @Override
                public int compare(Subject o1, Subject o2) {
                    if(o1.getWeight() > o2.getWeight()) return -1;
                    else if(o1.getWeight() < o2.getWeight()) return 1;
                    return 0;
                }
            });
        } catch (HandlerException e) {
            logger.error("recommend service exec exception ",e);
        }

        return resultList;
    }
}
