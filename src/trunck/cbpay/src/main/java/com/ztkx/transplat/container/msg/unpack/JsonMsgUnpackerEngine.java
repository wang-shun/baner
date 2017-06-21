package com.ztkx.transplat.container.msg.unpack;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ztkx.transplat.container.HandlerException;
import com.ztkx.transplat.container.constant.ErrorCodeConstant;
import com.ztkx.transplat.container.util.ContextUtil;
import com.ztkx.transplat.platformutil.context.CommonContext;
import com.ztkx.transplat.platformutil.msg.*;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import javax.xml.stream.XMLStreamException;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * json拆包引擎
 */
public class JsonMsgUnpackerEngine {
    static Logger logger = Logger.getLogger(XmlMsgUnpackerEngine.class);

    /**
     * @param context
     * @param msgXmlDescriber
     * @throws HandlerException
     */
    public static void unpack(CommonContext context, MsgXmlDescriber msgXmlDescriber) throws HandlerException {

        String msg = context.getOrginalField();
        JSONObject jsonObject = null;
        Map<String, JSONObject> virtualMap = new HashMap<>();

        try {
            jsonObject = JSONObject.parseObject(msg);
            //解析include
            unpackIncludeField(msgXmlDescriber, jsonObject, context);
            //解析普通字段
            unpackGeneralField(msgXmlDescriber, jsonObject, context,virtualMap);

            //解析循环报文
            unpackCompositField(msgXmlDescriber, jsonObject, context,virtualMap);
        } catch (Exception e) {
            logger.error("unpack Engine exec exception", e);
            ContextUtil.setErrorCode(ErrorCodeConstant.BASE_PLA000008, context);
            throw new HandlerException("unpack Engine exec exception", e);
        } finally {
            jsonObject.clear();
        }
    }


    /**
     *
     * @param msgXmlDescriber
     * @param jsonObject
     * @param context
     * @throws Exception
     */
    private static void unpackCompositField(MsgXmlDescriber msgXmlDescriber, JSONObject rootobj, CommonContext context,Map<String, JSONObject> virtualMap) throws Exception {
        CompositField cf = msgXmlDescriber.getCf();
        if (cf == null) {
            if (logger.isDebugEnabled()) {
                logger.debug("compositField is null");
            }
            return;
        }
        String compsitFieldName = cf.getName();

        String sizeStr = cf.getSizeRef();
        int size = 0;
        String fieldName = null;
//		try {
        try {
            size = Integer.parseInt(sizeStr);
        } catch (NumberFormatException e) {
            String sizeArray = context.getFieldStr(sizeStr, CommonContext.SCOPE_GLOBAL);
            if (null == sizeArray) {
                logger.error("get cycle times fail");
                ContextUtil.setErrorCode(ErrorCodeConstant.BASE_PLA000004, context);
                throw new NumberFormatException("get cycle times fail");
            }

            try {
                size = Integer.parseInt(sizeArray);
            } catch (NumberFormatException ex) {
                logger.error("get cycle times fail");
                ContextUtil.setErrorCode(ErrorCodeConstant.BASE_PLA000004, context);
                throw new NumberFormatException("get cycle times fail");
            }
        }
        if (logger.isDebugEnabled()) {
            logger.debug("cyclic times value [" + size + "]");
            logger.debug("start parse cyclic msg ");
        }

        //找当前节点的上级节点
        Deque<Field> queue = prepareComSuperField(msgXmlDescriber, cf);

        prepareVirMap(virtualMap, queue, rootobj);

        JSONArray jsonArray = virtualMap.get(cf.getSuper_field() + "_" + cf.getSuper_level()).getJSONArray(cf.getName());

        List<Map<String, String>> cyclicList = new ArrayList<Map<String, String>>(size);
        List<Field> fieldList = cf.getList();
        for (int i = 0; i < size; i++) {
            if (logger.isDebugEnabled()) {
                logger.debug("start unpack " + i + " times cyclic ");
            }
            Map<String, String> map = new HashMap<String, String>(1);
            JSONObject jsonrow = jsonArray.getJSONObject(i);

            for (int j = 0; j < fieldList.size(); j++) {

                Field f = fieldList.get(j);
                fieldName = f.getName();
                FieldFormat ff = f.getFieldFormat();

                if (!isParseFromMsg(fieldName, ff)) {
                    if (StringUtils.isNotBlank(ff.getDefault_value())) {
                        map.put(fieldName, ff.getDefault_value());
                        if (logger.isDebugEnabled()) {
                            logger.debug("the field [" + fieldName + "] type is [" + ff.getType() + "] the default is [" + ff.getDefault_value() + "]");
                        }
                    }
                    continue;
                }
                String value = jsonrow.getString(f.getName());

                if (logger.isDebugEnabled()) {
                    logger.debug("the field [" + fieldName + "] type is [" + ff.getType() + "] value is [" + value + "]");
                }
                if (!FieldFormatCheckUtil.necessaryCheck(fieldName, value, ff)) {
                    context.setErrorCode(ErrorCodeConstant.BASE_PLA000003);
                    throw new NumberFormatException(fieldName + " field is necessary current value is [" + value + "]");
                }
                if (FieldFormatCheckUtil.isLengthOverFlow(fieldName, value, ff)) {
                    value = value.substring(0, ff.getLength());
                } else if (FieldFormatCheckUtil.isFillChar(fieldName, value, ff)) {
                    value = FieldFormatCheckUtil.fillChar(fieldName, value, ff);
                }

                map.put(fieldName, value);
                execFunction(map, ff, value);
                if (logger.isDebugEnabled()) {
                    logger.debug("the field " + fieldName + " type is [" + ff.getType() + "] ultimate value is [" + value + "]");
                }
            }
            cyclicList.add(map);
        }
        context.setObj(compsitFieldName, cyclicList, CommonContext.SCOPE_GLOBAL);
//		} catch (NumberFormatException | XMLStreamException e) {
//			logger.error("unpack Composit field exception the fieldName is ["+fieldName+"]",e);
//			throw e;
//		}
    }

    private static void prepareVirMap(Map<String, JSONObject> virtualMap, Deque<Field> queue, JSONObject rootJson) {
        JSONObject tmpJsonObj = rootJson;
        while (queue.size() > 0) {
            Field virturlField = queue.pop();
            String virturlName = virturlField.getName();
            FieldFormat virff = virturlField.getFieldFormat();

            if (!virtualMap.containsKey(virturlName + "_" + virff.getLevel())) {
                virtualMap.put(virturlName + "_" + virff.getLevel(), tmpJsonObj.getJSONObject(virturlField.getName()));
            } else {
                tmpJsonObj = virtualMap.get(virturlName + "_" + virff.getLevel());
            }
        }
    }

    /**
     * include被包含在了fieldlist中
     *
     * @param msgXmlDescriber
     * @param jsonObject
     * @param context
     * @throws XMLStreamException
     */
    private static void unpackIncludeField(MsgXmlDescriber msgXmlDescriber, JSONObject jsonObject, CommonContext context) throws XMLStreamException {


    }

    /**
     * @param msgXmlDescriber
     * @param jsonObject
     * @param context
     * @throws Exception
     */
    private static void unpackGeneralField(MsgXmlDescriber msgXmlDescriber, JSONObject rootobj, CommonContext context,Map<String, JSONObject> virtualMap) throws Exception {

        List<Field> fieldList = msgXmlDescriber.getList();
        String fieldName = null;

        for (int i = 0; i < fieldList.size(); i++) {
            Field f = fieldList.get(i);
            fieldName = f.getName();
            FieldFormat ff = f.getFieldFormat();
            if (logger.isDebugEnabled()) {
                logger.debug("the field " + fieldName + " level [" + ff.getLevel() + "] type is [" + ff.getType() + "] the default is [" + ff.getDefault_value() + "] super_field [" + ff.getSuper_field() + "] super_levle [" + ff.getSuper_level() + "]");
            }
            //如果是virtual类型的字段，需要将字段名称
            if (ff.getType().equals(MsgConstantField.ATTR_TYPE_VIRTUAL)) {
                Deque<Field> queue = prepareSuperField(msgXmlDescriber, f);
                if (logger.isDebugEnabled()) {
                    logger.debug("the field " + fieldName + " paraent node size ["+queue.size()+"]");
                }
                //跟进queue将当前字段的上级节点逐个取出来
                prepareVirMap(virtualMap, queue, rootobj);

                continue;
            }

            //判断当前字段是否需要从报文中获取
            if (!isParseFromMsg(fieldName, ff)) {
                if (StringUtils.isNotBlank(ff.getDefault_value())) {
                    context.setFieldStr(fieldName, ff.getDefault_value(), CommonContext.SCOPE_GLOBAL);
                }
                continue;
            }

            /**
             * 当前字段需要从json串中获取
             */
            String value = getFieldValue(virtualMap, f);

            if (logger.isDebugEnabled()) {
                logger.debug("the field [" + fieldName + "] type is [" + ff.getType() + "] value is [" + value + "]");
            }

            if (null == value) {
                continue;
            }

            if (ff.getType().equals(MsgConstantField.ATTR_TYPE_JSON)) {
                if (logger.isDebugEnabled()) {
                    logger.debug("the field [" + fieldName + "] is " + MsgConstantField.ATTR_TYPE_JSON + " field start parse json");
                }
                //目前没有这样的情况先空着

            } else if (ff.getType().equals(MsgConstantField.ATTR_TYPE_N) || ff.getType().equals(MsgConstantField.ATTR_TYPE_S)) {
                if (!FieldFormatCheckUtil.necessaryCheck(fieldName, value, ff)) {
                    context.setErrorCode(ErrorCodeConstant.BASE_PLA000003);
                    throw new NumberFormatException(fieldName + " field is necessary current value is [" + value + "]");
                }

                if (FieldFormatCheckUtil.isLengthOverFlow(fieldName, value, ff)) {
                    value = value.substring(0, ff.getLength());
                } else if (FieldFormatCheckUtil.isFillChar(fieldName, value, ff)) {
                    value = FieldFormatCheckUtil.fillChar(fieldName, value, ff);
                }

                context.setFieldStr(fieldName, value, CommonContext.SCOPE_GLOBAL);
                execFunction(context, ff, value);

                if (logger.isDebugEnabled()) {
                    logger.debug("the field [" + fieldName + "] type is [" + ff.getType() + "] ultimate value is [" + value + "]");
                }
            }
        }
    }

    private static Deque<Field> prepareComSuperField(MsgXmlDescriber msgXmlDescriber, CompositField cf) {

        String superFieldName =  null;
        Field superField = null;
        Deque<Field> queue = new LinkedList<>();
        for(int superLevel = cf.getSuper_level();superLevel!=0;){
            superFieldName =  cf.getSuper_field();
            superField = msgXmlDescriber.getVirtualField(superFieldName, superLevel);
            queue.push(superField);
            superLevel = superField.getFieldFormat().getSuper_level();
            superFieldName = superField.getFieldFormat().getSuper_field();
        }
        return queue;
    }

    private static Deque<Field> prepareSuperField(MsgXmlDescriber msgXmlDescriber, Field field) {

        String superFieldName;

        Field superField = field;
        Deque<Field> queue = new LinkedList<>();
        //自己也是virtual类型的标签，现将自己push到队列中
        queue.push(field);
        for(int superLevel = superField.getFieldFormat().getSuper_level();superLevel!=0;){
            //父节点的级别为0，说明当前节点已经是顶层元素
            superFieldName = superField.getFieldFormat().getSuper_field();
            superField = msgXmlDescriber.getVirtualField(superFieldName, superLevel);
            queue.push(superField);
            superLevel = superField.getFieldFormat().getSuper_level();
        }
        return queue;
    }

    /**
     * 执行函数
     * @param subMap
     * @param subff
     * @param subValue
     */
    private static void execFunction(Map<String, String> subMap, FieldFormat subff, String subValue) {
        if (StringUtils.isNotEmpty(subff.getConvert())) {
            subMap.put(subff.getConvert(), subValue);
            if (logger.isDebugEnabled()) {
                logger.debug("exec convert function the file [" + subff.getConvert() + "] value is [" + subValue + "]");
            }
        }
    }

    /**
     * 执行函数
     * @param context
     * @param ff
     * @param value
     */
    private static void execFunction(CommonContext context, FieldFormat ff, Object value) {
        if (StringUtils.isNotEmpty(ff.getConvert())) {
            context.setObj(ff.getConvert(), value, CommonContext.SCOPE_GLOBAL);
            if (logger.isDebugEnabled()) {
                logger.debug("exec convert function the file [" + ff.getConvert() + "] value is [" + value + "]");
            }
        }
    }

    /**
     * @param jsonObject
     * @param fieldName
     * @return
     * @throws XMLStreamException
     */
    private static String getFieldValue(Map<String, JSONObject> virtualMap, Field field) throws XMLStreamException {
        String fileName = field.getName();
        String suerFieldName = field.getFieldFormat().getSuper_field();
        int superFieldLevel = field.getFieldFormat().getSuper_level();
        //先根据父节点的id和level确认父节点，在从父节点中获取字段内容
        String value = virtualMap.get(suerFieldName + "_" + superFieldLevel).getString(fileName);
        return value;
    }

    /**
     * 检查当前字段是否需要从reader中解析出来
     *
     * @param fieldName
     * @param ff
     * @return
     * @throws UnsupportedEncodingException
     */
    private static boolean isParseFromMsg(String fieldName, FieldFormat ff) {

        //如果当前字段不需要从报文中拆，直接取default字段里面的值
        if (!ff.isFrommsg()) {
            return false;
        }
        //如果当前字段配置了default，直接取default字段里面的值
        if (StringUtils.isNotEmpty(ff.getDefault_value())) {
            return false;
        }
        return true;
    }
}
