package org.inn.baner.util;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by nightblue on 2017/8/7.
 */
public class ListCompareUtil implements Comparator {

    /**
     * 进行比较的类
     */
    private  Class clazz;
    /**
     * 进行比较的维度
     */
    private  String[] fieldsList;

    /**
     * 对list进行自定义排序
     * @param objects 需排序list
     * @param clazz list中元素类型
     * @param fieldsList 排序优先级(有顺序的字段名称，排列最前的优先级最高)
     * @throws NoSuchMethodException
     */
    public void mysort(List objects,Class clazz,String[] fieldsList) {
        this.clazz = clazz;
        this.fieldsList = fieldsList;
        Collections.sort(objects,this);
    }

    /**
     * 倒排序
     * @param objects
     * @param clazz
     * @param fieldsList
     */
    public void mySortDesc(List objects,Class clazz,String[] fieldsList){
        mysort(objects,clazz,fieldsList);
        Collections.reverse(objects);
    }



    /**
     * 排序逻辑实现（不可直接调用）
     * @param o1
     * @param o2
     * @return
     */
    public  int compare(Object o1, Object o2) {
        try {
            int fieldLength = fieldsList.length;
            int offset = 0;
            int comResult = 0;
            while (comResult==0&&offset<fieldLength){
                comResult = valueCompare(fieldsList[offset],o1,o2);
                offset++;
            }
            return comResult;

        } catch (IntrospectionException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


        return 0;
    }

    /**
     * 实现维度比较
     * @param field
     * @param object1
     * @param object2
     * @return
     * @throws Exception
     */
    private int valueCompare(String field,Object object1,Object object2) throws Exception {
        PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field,clazz);
        Method getMethod = propertyDescriptor.getReadMethod();//获取对应字段的get方法
        Class<?> type = getMethod.getReturnType();//获取get方法返回值的类型
        if(type.equals(Byte.TYPE)||type.equals(Short.TYPE)||type.equals(Integer.TYPE)||type.equals(Long.TYPE)||type.equals(Double.TYPE)||type.equals(Float.TYPE)||type.equals(Character.TYPE)||type.newInstance() instanceof Comparable){//如果为未实现自排序的返回值类型
            //进行比较
            return  Comparable.class.cast(getMethod.invoke(object1)).compareTo(Comparable.class.cast(getMethod.invoke(object2)));
        }else{
            throw new Exception("该字段类型不能支持排序");
        }
    }
}
