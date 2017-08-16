package org.inn.baner.serviceimp;

import com.ztkx.transplat.container.HandlerException;
import org.apache.log4j.Logger;
import org.inn.baner.bean.Label;
import org.inn.baner.bean.LabelSubject;
import org.inn.baner.handler.data.LabelSubjectData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     * 入参为主体名，以及需要关联满足的
     * 出参为主体id及权重Map，key为主体id，value为该主体id关联入参标签的数量
     *
     * @param list
     * @return
     */
    public Map<String,Integer> selectRefSubject(String subject,List<Label> list){
        LabelSubjectData labelSubjectData = new LabelSubjectData();
        Map<String,Integer> resultMap = new HashMap<>();
        try {
            for(Label label : list){
                List<LabelSubject> labelSubjectList = labelSubjectData.qryByLabelId(subject,label.getLabelid());
                for(LabelSubject labelSubject : labelSubjectList){
                    if(resultMap.containsKey(labelSubject.getSubjectid())){
                        resultMap.put(labelSubject.getSubjectid(),resultMap.get(labelSubject.getSubjectid()) + 1);
                    }else{
                        resultMap.put(labelSubject.getSubjectid(),1);
                    }
                }
            }
        } catch (HandlerException e) {
            logger.error("recommend service exec exception ",e);
        }

        return resultMap;
    }
}
