package cn.msec.rest.web.action;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import cn.msec.rest.web.bean.ReturnInfo;



public class BasicCtrl {
	Logger log = Logger.getLogger(BasicCtrl.class);
	public ReturnInfo handleMethodArgumentNotValidException( MethodArgumentNotValidException error ) {
		
		//return  ReturnInfo.Faild;
	    List<ObjectError> errors =  error.getBindingResult().getAllErrors();
	   
	    StringBuffer errorStr = new StringBuffer();
	    int count = 0;
	    for(ObjectError  er: errors){
	    	if(count != 0){
	    		
	    		errorStr.append("��");
	    	}
	    	errorStr.append(er.getDefaultMessage());
	    	count++;
	    }
		return new ReturnInfo(  (errorStr.toString()), 0, null,false);
	}
	
}
