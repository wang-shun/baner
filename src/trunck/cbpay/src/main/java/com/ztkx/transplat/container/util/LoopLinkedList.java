package com.ztkx.transplat.container.util;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 当前循环队列只是为了满足循环读取数据,对写操作支持的不是很好
 * @author zhangxiaoyun
 * 2016年1月27日 下午5:17:51
 * <p>Company:ztkx</p>
 * @param <T>
 */
public  class LoopLinkedList<T> {
	  
	  private Map<T,Integer> map = new HashMap<T,Integer>();
	  private CopyOnWriteArrayList<T> list = null;
	  private int current = 0;//当前索引
	  
	  //以默认数组长度创建空循环队列  
	  public LoopLinkedList() {  
	    list= new CopyOnWriteArrayList<T>();
	  }  
	  
	  //获取循环队列的大小  
	  public int size() {  
		 return list.size();
	  }  
	  
	  //插入队列  
	  public synchronized void add(T element) {  
		  list.add(element);
		  map.put(element, list.size()-1);
	  }  
	  
	  /**
	   * 获取下一个元素
	   * @return
	   */
	  public T getNext(){
		  T ele = null;
		  if(list.size()==0){
			  return null;
		  }
		  
		  if(list.size()==1){
			  ele =  list.get(0); 
		  }else{
			  synchronized (list) {
				  ele = list.get(current++);
				  if(current>=list.size()){
					  current = 0;
				  }
			}
		  }
		  return ele;
	  }
	  
	  //移除队列  
	  public synchronized void remove(T ele) {
		  if(map.containsKey(ele)){
			  int index = map.get(ele);
			  list.remove(index); 
			  if(current>=list.size()){
				  current=0;
			  }
		  }
	  }  
	  
	  //判断循环队列是否为空队列  
	  public boolean isEmpty() {  
	    return list.isEmpty();  
	  }  
	
}
