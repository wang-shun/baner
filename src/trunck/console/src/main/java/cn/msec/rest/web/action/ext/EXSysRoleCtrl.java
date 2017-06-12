package cn.msec.rest.web.action.ext;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.msec.msc.mysqlgens.admin.entity.SysRole;
import cn.msec.msc.mysqlgens.admin.entity.SysRoleExample;
import cn.msec.msc.mysqlgens.admin.mapper.SysRoleMapper;
import cn.msec.rest.utils.BeanFactory;

@Slf4j
@Controller
@RequestMapping("/exsysrole")
public class EXSysRoleCtrl {

	private static SysRoleMapper sysRoleMapper = 
			(SysRoleMapper)BeanFactory.getBean("sysRoleMapper");

	@RequestMapping(value = "/getAllRoles", method = RequestMethod.GET)
	@ResponseBody
	public List<SysRole> getLandcount(HttpServletRequest req) {
		List<SysRole> list = null;
		SysRoleExample example = new SysRoleExample();
		example.createCriteria().andRoleIdIsNotNull().andStatusEqualTo(new Integer(1));
		list = sysRoleMapper.selectByExample(example);
		return list;
	}
	
}
