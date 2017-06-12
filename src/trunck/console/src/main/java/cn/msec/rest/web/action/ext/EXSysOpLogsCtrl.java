package cn.msec.rest.web.action.ext;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.msec.msc.mysqlgens.admin.entity.SysOpLogs;
import cn.msec.msc.mysqlgens.admin.mapper.SysOpLogsMapper;
import cn.msec.rest.db.ext.EXSysOpLogsMapper;
import cn.msec.rest.utils.BeanFactory;
import cn.msec.rest.web.bean.ReturnInfo;

@Slf4j
@Controller
@RequestMapping("/exsysoplogs")
public class EXSysOpLogsCtrl {

	private static EXSysOpLogsMapper exSysOpLogsMapper = (EXSysOpLogsMapper) BeanFactory
			.getBean("exSysOpLogsMapper");
	private static SysOpLogsMapper sysOpLogsMapper = (SysOpLogsMapper) BeanFactory
			.getBean("sysOpLogsMapper");
	@RequestMapping(value = "/getLogs", method = RequestMethod.GET)
	@ResponseBody
	public Object getLogs(HttpServletRequest req) {
		Object ret = exSysOpLogsMapper.selectLogs();
		log.debug("ret.getrole==" + ret);
		return ret;
	}
	/**
	 * ajax单条数据插入 
	 * url:'http://ip/rest/sysoplogs' 
	 * data:'{"key1":"value1","key2":"value2",...}' 
	 * type:’POST
	 */
	@RequestMapping(value="/saveAuditLogInfo",method=RequestMethod.POST)
	@ResponseBody
	public ReturnInfo insert( @RequestBody SysOpLogs info,HttpServletRequest req) {
		try {
			sysOpLogsMapper.insert(info);
			log.info("[zbx]:AUDIT|0000|审批信息："+info.getContents()+"|审核人:"+info.getSkeys()+"|状态："+info.getLevel1());
			return ReturnInfo.Success;
		} catch (Exception e) {
			log.warn("SysOpLogsCtrl insert error..",e);
//			e.printStackTrace();
		}
		return ReturnInfo.Faild;
	}
}
