package com.ztkx.transplat.invoker.bean;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Map;

import com.ztkx.transplat.platformutil.activemq.messagesend.MessageObj;

/** 
 * @author  zhagnxiaoyun: 
 * @date 2016年7月4日 下午1:40:06 
 */
public class InvokerParams implements MessageObj,Cloneable{
	
	private static final long serialVersionUID = -5329255561894606826L;
	public static final String MODE_SYN="syn_invoker";
	public static final String MODE_ASYN="asyn_invoker";
	private String invokerId;		//命令id
	private String operator;	//命令的当前操作
	private String sourceNode;	//源结点
	private String currenttarget;	//当前目标结点
	private List<String> targetNodes = null;	//目标结点
	private String mode;		//命令的模式  同步或者异步
	private boolean isAutoCommit = true;
	private boolean isSucc = false;	//命令调用过程
	private String serialId;	//命令唯一序列号
	private Map<String,String> commandparam = null;
	
	
	public String getSerialId() {
		return serialId;
	}
	public void setSerialId(String serialId) {
		this.serialId = serialId;
	}
	public String getCurrenttarget() {
		return currenttarget;
	}
	public boolean isSucc() {
		return isSucc;
	}
	public void setSucc(boolean isSucc) {
		this.isSucc = isSucc;
	}
	public void setCurrenttarget(String currenttarget) {
		this.currenttarget = currenttarget;
	}
	public boolean isAutoCommit() {
		return isAutoCommit;
	}
	public void setAutoCommit(boolean isAutoCommit) {
		this.isAutoCommit = isAutoCommit;
	}
	
	public String getInvokerId() {
		return invokerId;
	}
	public void setInvokerId(String invokerId) {
		this.invokerId = invokerId;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getSourceNode() {
		return sourceNode;
	}
	public void setSourceNode(String sourceNode) {
		this.sourceNode = sourceNode;
	}
	public List<String> getTargetNodes() {
		return targetNodes;
	}
	public void setTargetNodes(List<String> targetNodes) {
		this.targetNodes = targetNodes;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	
	
	public Map<String, String> getCommandparam() {
		return commandparam;
	}
	public void setCommandparam(Map<String, String> commandparam) {
		this.commandparam = commandparam;
	}
	/**
	 * 深度克隆
	 */
	@Override
	public InvokerParams clone() throws CloneNotSupportedException{
		
		ByteArrayOutputStream bos = null;
		ObjectOutputStream oos;
		ByteArrayInputStream bis = null;
		ObjectInputStream ois = null;
		InvokerParams prams =null;
		try {
			bos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(bos);
			oos.writeObject(this);
			bis = new ByteArrayInputStream(bos.toByteArray());
			ois = new ObjectInputStream(bis);
			prams = (InvokerParams) ois.readObject();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return prams;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("InvokerParams [invokerId=");
		builder.append(invokerId);
		builder.append(", operator=");
		builder.append(operator);
		builder.append(", sourceNode=");
		builder.append(sourceNode);
		builder.append(", currenttarget=");
		builder.append(currenttarget);
		builder.append(", targetNodes=");
		builder.append(targetNodes);
		builder.append(", isSucc=");
		builder.append(isSucc);
		builder.append(", serialId=");
		builder.append(serialId);
		builder.append("]");
		return builder.toString();
	}
}
