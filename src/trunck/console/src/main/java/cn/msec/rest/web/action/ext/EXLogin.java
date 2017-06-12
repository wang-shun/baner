package cn.msec.rest.web.action.ext;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.msec.msc.mysqlgens.admin.entity.SysUser;
import cn.msec.msc.mysqlgens.admin.entity.SysUserExample;
import cn.msec.msc.mysqlgens.admin.entity.SysUserRole;
import cn.msec.msc.mysqlgens.admin.entity.SysUserRoleExample;
import cn.msec.msc.mysqlgens.admin.mapper.SysUserMapper;
import cn.msec.msc.mysqlgens.admin.mapper.SysUserRoleMapper;
import cn.msec.rest.db.service.DataService;
import cn.msec.rest.utils.BeanFactory;
import cn.msec.rest.web.action.BasicCtrl;
import cn.msec.rest.web.bean.ReturnInfo;

@Slf4j
@Controller
@RequestMapping("/user")
@Scope("session")
public class EXLogin extends BasicCtrl implements Serializable{

	private static SysUserMapper sysUserMapper = (SysUserMapper) BeanFactory
			.getBean("sysUserMapper");

	private static DataService mysqlDataService = (DataService) BeanFactory
			.getBean("mysqlDataService");

	private static SysUserRoleMapper sysUserRoleMapper = (SysUserRoleMapper) BeanFactory
			.getBean("sysUserRoleMapper");

	@Data
	class LoginReturn {
		List<String> roles;
		String username;
		String loginname;
		String userid;
		String branchid;
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public ReturnInfo login(HttpServletRequest req) {
		try {
			String loginName = req.getParameter("loginname");
			String password = req.getParameter("password");
			SysUserExample example = new SysUserExample();
			example.createCriteria().andLoginNameEqualTo(loginName)
					.andPasswordEqualTo(password);
			List<SysUser> users = sysUserMapper.selectByExample(example);

			if (users == null || users.size() != 1) {
				return ReturnInfo.Faild;
			}

			HttpSession session = req.getSession(true);
			SysUser user = users.get(0);
			session.setAttribute("user", user);
			// get user menus
			SysUserRoleExample roleEx = new SysUserRoleExample();
			roleEx.createCriteria().andUserIdEqualTo(user.getUserId());
			List<SysUserRole> roles = sysUserRoleMapper.selectByExample(roleEx);

			List<String> iroles = new ArrayList<String>();
			for (SysUserRole urole : roles) {
				iroles.add(urole.getRoleId());
			}
			session.setAttribute("roles", iroles);

			LoginReturn lr=new LoginReturn();
			lr.setRoles(iroles);
			lr.setLoginname(user.getLoginName());
			lr.setUserid(""+user.getUserId());
			lr.setUsername(user.getUserName());
			// sysUserMapper.insert(info);

			return new ReturnInfo("success", 0, lr, true);

		} catch (Exception e) {
			log.warn("SysUserCtrl insert error..", e);
			// e.printStackTrace();
		}
		return ReturnInfo.Faild;
	}
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	@ResponseBody
	public ReturnInfo logout(HttpServletRequest req,HttpServletResponse res) {
		try {
			HttpSession session = req.getSession(true);
			//SysUser user = (SysUser)session.getAttribute("user");
			session.removeAttribute("user");
			//req.getRequestDispatcher("/login.html").forward(req, res);
			//res.sendRedirect("http://localhost/msbao/login.html");
			//System.out.println(session.getAttribute("user")==null);
			return ReturnInfo.Success;
		} catch (Exception e) {
			log.warn("SysUserCtrl insert error..", e);
			// e.printStackTrace();
		}
		return ReturnInfo.Faild;
	}
}
