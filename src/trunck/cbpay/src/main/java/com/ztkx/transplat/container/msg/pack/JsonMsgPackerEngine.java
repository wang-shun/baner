package com.ztkx.transplat.container.msg.pack;

import com.alibaba.fastjson.JSON;
import com.ztkx.transplat.container.constant.ErrorCodeConstant;
import com.ztkx.transplat.container.util.ContextUtil;
import com.ztkx.transplat.platformutil.context.CommonContext;
import com.ztkx.transplat.platformutil.msg.*;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.util.*;

/**
 * Created by zhangxiaoyun on 2017/6/15.
 */
public class JsonMsgPackerEngine {
    private static Logger logger = Logger.getLogger(XmlMsgPackerEngine.class);

    /**
     * json组包引擎
     * 1.先组include标签中包含的字段
     * 2.按照格式填充普通json标签
     * 3.按照格式填充循环体标签
     * @param context
     * @param msgXmlDescriber
     * @throws XMLStreamException
     */
    public static void pack(CommonContext context,MsgXmlDescriber msgXmlDescriber) throws XMLStreamException {


        Map<String, Object> rootMap = new HashMap<>();

        //key 为fieldName_2这样
        Map<String, Map<String, Object>> virtualMap = new HashMap<>();

        Map<Integer, List<Field>> virtualFieldMap = new HashMap<>();
        try {
            //组普通字段
            packGeneralField(msgXmlDescriber,virtualFieldMap,virtualMap, context);

            //组循环体报文
            packCompositField(msgXmlDescriber,virtualMap,context);

            //根据virtualMap 生成 rootMap
            rootMap = getRootMap(virtualMap, virtualFieldMap);

            String msg = JSON.toJSONString(rootMap);
            logger.info("pack msg is ["+msg+"]");
            context.setOrginalField(msg);
        }
        finally{
            rootMap.clear();
            virtualMap.clear();
            virtualFieldMap.clear();
        }
    }

    /**
     * 根据virtualMap 生成 rootMap
     * @param virtualMap
     * @param virtualFieldMap
     * @return
     */
    private static Map<String, Object> getRootMap(Map<String, Map<String, Object>> virtualMap, Map<Integer, List<Field>> virtualFieldMap) {

        Integer[] levelArray = virtualFieldMap.keySet().toArray(new Integer[1]);

        //按levle从大到小排序
        Arrays.sort(levelArray, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2.compareTo(o1);
            }
        });

        for (int i = 0; i < levelArray.length; i++) {
            int levle = levelArray[i];
            List<Field> fields = virtualFieldMap.get(levle);
            for (Field field : fields) {
                String superName = field.getFieldFormat().getSuper_field();
                int superLevel = field.getFieldFormat().getSuper_level();
                String thisName = field.getName();
                int thisLevel = field.getFieldFormat().getLevel();
                Map<String, Object> thisMap = virtualMap.get(thisName + "_" + thisLevel);
                Map<String, Object> superMap = virtualMap.get(superName + "_" + superLevel);
                if (superMap != null) {
                    //如果循环到顶层节点的时候supermap为空
                    superMap.put(field.getName(), thisMap);
                }
            }
        }
        //顶层节点的level为1，只会存在一个顶层节点
        Field rootField = virtualFieldMap.get(1).get(0);
        //数据执行到这儿的格式为
        //rootField.getName() + "_" + rootField.getFieldFormat().getLevel()="message_1"  内容为{"data":{"head":{"trancode":""},"body":{}}}
        //所以啊缺少message顶层标签
        Map<String, Object> topLayer = new HashMap<>();
        Map<String, Object> topLayerVal = virtualMap.get(rootField.getName() + "_" + rootField.getFieldFormat().getLevel());
        topLayer.put(rootField.getName(), topLayerVal);
        return topLayer;
    }

    /**
     * 组循环体报文
     * @param msgXmlDescriber
     * @param writer
     * @param context
     * @throws XMLStreamException
     */
    private static void packCompositField(MsgXmlDescriber msgXmlDescriber,Map<String, Map<String, Object>> virtualMap,CommonContext context) throws NumberFormatException  {

        CompositField cf = msgXmlDescriber.getCf();
        if(cf == null){
            //没有循环体拆包结束
            if(logger.isDebugEnabled()){
                logger.debug("compositField is null");
            }
            return;
        }

        String compsitFieldName = cf.getName();
        List<Field> cycleList  = cf.getList();
        String fieldName = null;
        try {
            /**
             * 1.如果配置的sizeRef能转化为int就直接用xml里面配置的
             * 2.如果配置的sizeRef不能转化为int，就直接获取context的循环内容，
             */
            List<Map<String,String>> compsitList = (List<Map<String,String>>)context.getObj(compsitFieldName,CommonContext.SCOPE_GLOBAL);

            String superfieldName = cf.getSuper_field();
            int superlevel = cf.getSuper_level();

            String key = superfieldName + "_" + superlevel;
            Map<String, Object> jsonObjMap = null;

            if (virtualMap.containsKey(key)) {
                jsonObjMap= virtualMap.get(key);
            }else {
                jsonObjMap = new HashMap<>();
                virtualMap.put(key, jsonObjMap);
            }
            jsonObjMap.put(cf.getName(), compsitList);


        } catch (NumberFormatException e) {
            logger.error("pack Composit field exception the fieldName is <"+fieldName+">",e);
            ContextUtil.setErrorCode(ErrorCodeConstant.BASE_PLA000009, context);
            throw e;
        }
    }

    /**
     * 处理普通字段的虚拟标签虚拟字段
     * @param writer
     * @param fieldName
     * @param stack
     * @throws XMLStreamException
     * 2016年3月9日 上午10:51:06
     * @author zhangxiaoyun
     */
    private static void packVirtualField(Field field, Map<String,Map<String,Object>> virtualMap,Map<Integer, List<Field>> virtualFieldMap) throws XMLStreamException {

            String fieldName = field.getName();
        int level = field.getFieldFormat().getLevel();
        Map<String,Object> map = new HashMap<>();
        virtualMap.put(fieldName + "_" + field.getFieldFormat().getLevel(), map);

        if(virtualFieldMap.containsKey(level)) {
            //如果当前级别已经存在list，直接将当前字段放入到list中
            virtualFieldMap.get(level).add(field);
        }else {
            ArrayList<Field> fields = new ArrayList<Field>();
            fields.add(field);
            virtualFieldMap.put(level, fields);
        }
    }


    /**
     * 组xml普通字段报文
     * @param msgXmlDescriber
     * @param writer
     * @param context
     * @throws XMLStreamException
     * @throws IOException
     */
    private static void packGeneralField(MsgXmlDescriber msgXmlDescriber,Map<Integer, List<Field>> virtualFieldMap,Map<String, Map<String, Object>> virtualMap, CommonContext context) throws XMLStreamException {

        List<Field> fieldList = msgXmlDescriber.getList();

        String fieldName = null;
        try {
            //组普通字段
            for (int i = 0; i < fieldList.size(); i++) {
                Field f = fieldList.get(i);
                fieldName = f.getName();
                FieldFormat ff = f.getFieldFormat();
                String type = ff.getType();
                if (logger.isDebugEnabled()) {
                    logger.debug("the field <" + fieldName + "> level <" + ff.getLevel() + "> type is <" + ff.getType() + "> the default is <" + ff.getDefault_value() + "> super_field <" + ff.getSuper_field() + "> super_levle <" + ff.getSuper_level() + ">");
                }
                switch (type) {
                    case MsgConstantField.ATTR_TYPE_VIRTUAL:
                        packVirtualField(f, virtualMap,virtualFieldMap);
                        break;
                    case MsgConstantField.ATTR_TYPE_JSON:
                       //这种的处理逻辑先空着
                        break;
                    case MsgConstantField.ATTR_TYPE_N:
                    case MsgConstantField.ATTR_TYPE_S:
                        //给字段赋值
                        String value = execFieldFunction(f, context, ff);
                        /**
                         * 判断字段值是否超过最大长度
                         * 如果超过最大长度则截掉超过以后的内容
                         */
                        if(isOverLenth(fieldName,value,ff)){
                            logger.warn("the field <"+fieldName+"> overLength value is <"+value+">");
                            value = value.substring(0, ff.getLength());
                        }
                        if(logger.isDebugEnabled()){
                            logger.debug("the field <"+fieldName+"> type is <"+ff.getType()+"> ultimate value is <"+value+">");
                        }
                        writeElement(virtualMap,f,value);
                        break;
                    default:
                        //TODO
                        break;
                }
            }
        } catch (XMLStreamException e) {
            logger.error("unpack general field exception the fieldName is ["+fieldName+"]",e);
            ContextUtil.setErrorCode(ErrorCodeConstant.BASE_PLA000009, context);
            throw e;
        }
    }

    /**
     * 执行普通字段的函数
     * @param subField
     * @param map
     * @param subff
     * @return
     * 2016年3月31日 下午3:08:23
     * @author zhangxiaoyun
     */
    private static String execFieldFunction(Field subField,Map<String, String> map, FieldFormat subff) {
        String value = null;
        if(subff.getDefault_value()!=null){
            value = subff.getDefault_value();
        }else if(subff.getConvert()!=null){
            value = map.get(subff.getConvert());
        }else{
            value = map.get(subField.getName());
        }
        value = value==null?"":value;

        return value ;
    }

    private static String execFieldFunction(Field f, CommonContext context,FieldFormat ff) {
        String value = null;
        if(ff.getDefault_value()!=null){
            value =  ff.getDefault_value();
        }else if(ff.getConvert()!=null){
            value = context.getFieldStr(ff.getConvert(), CommonContext.SCOPE_GLOBAL);
        }else{
            value = context.getFieldStr(f.getName(), CommonContext.SCOPE_GLOBAL);
        }
        value = value==null?"":value;

        return value;
    }

    /**
     * 执行xml对象的函数
     * @param context
     * @param f
     * @return
     * 2016年3月31日 下午3:01:39
     * @author zhangxiaoyun
     */
    private static Map<String, String> execXmlFunction(CommonContext context,Field f) {
        Map<String, String> map = null;
        String fieldName = f.getName();
        if(StringUtils.isNotBlank(f.getFieldFormat().getConvert())){
            //检查convert
            if(logger.isDebugEnabled()){
                logger.debug("field ["+fieldName+"] convert is ["+f.getFieldFormat().getConvert()+"] get value from context");
            }
            map= (Map<String, String>) context.getObj(f.getFieldFormat().getConvert(), CommonContext.SCOPE_GLOBAL);
        }else{
            map= (Map<String, String>) context.getObj(fieldName, CommonContext.SCOPE_GLOBAL);
        }
        return map;
    }



    /**
     * 字段是否超过最大长度
     * @param fieldName
     * @param value
     * @param ff
     * @return
     */
    private static boolean isOverLenth(String fieldName,String value,FieldFormat ff){
        int maxLen = ff.getLength();
        if(value.length()>maxLen){
            if(logger.isDebugEnabled()){
                logger.error("the field <"+fieldName+"> length exceed max Length! current length is :"+value.length());
            }
            return true;
        }
        return false;
    }

    public static void writeElement(Map<String, Map<String, Object>> virtualMap,Field field,String value) throws XMLStreamException{
        String superfieldName = field.getFieldFormat().getSuper_field();
        int superlevel = field.getFieldFormat().getSuper_level();

        String key = superfieldName + "_" + superlevel;
        Map<String, Object> jsonObjMap = null;
        if (virtualMap.containsKey(key)) {
            jsonObjMap= virtualMap.get(key);
        }else {
            jsonObjMap = new HashMap<>();
            virtualMap.put(key, jsonObjMap);
        }
        jsonObjMap.put(field.getName(), value);
    }

}
