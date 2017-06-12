package cn.msec.rest.web.action.ext;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.msec.msc.mysqlgens.admin.entity.SysMenu;
import cn.msec.msc.mysqlgens.admin.entity.SysRoleMenu;
import cn.msec.msc.mysqlgens.admin.entity.SysRoleMenuExample;
import cn.msec.msc.mysqlgens.admin.mapper.SysRoleMenuMapper;
import cn.msec.rest.db.ext.EXSysRoleMenuMapper;
import cn.msec.rest.utils.BeanFactory;
import cn.msec.rest.web.action.BasicCtrl;
import cn.msec.rest.web.bean.MenuInfo;
import cn.msec.rest.web.bean.ReturnInfo;

@Slf4j
@Controller
@RequestMapping("/exrolemenu")
public class EXRoleMenuCtrl extends BasicCtrl {

	private static SysRoleMenuMapper sysRoleMenuMapper = (SysRoleMenuMapper) BeanFactory.getBean("sysRoleMenuMapper");
	private static EXSysRoleMenuMapper exSysRoleMenuMapper = (EXSysRoleMenuMapper) BeanFactory.getBean("exSysRoleMenuMapper");
	//private static SysMenuMapper sysMenuMapper = (SysMenuMapper) BeanFactory.getBean("sysMenuMapper");

	//private static DataService mysqlDataService = (DataService) BeanFactory.getBean("mysqlDataService");

	@RequestMapping(value = "/batch", method = RequestMethod.POST)
	@ResponseBody
	public ReturnInfo insertBatch(@RequestBody List<LinkedHashMap<String, String>> lists, HttpServletRequest req) {
		try {
			for (LinkedHashMap<String, String> entry : lists) {
				SysRoleMenuExample menu = new SysRoleMenuExample();
				menu.createCriteria().andRoleIdEqualTo((entry.get("roleId")));
				sysRoleMenuMapper.deleteByExample(menu);
			}

			for (LinkedHashMap<String, String> entry : lists) {
				SysRoleMenu menu = new SysRoleMenu();
				menu.setId(entry.get("id"));
				menu.setMenuId(Integer.parseInt(entry.get("menuId")));
				menu.setRoleId(entry.get("roleId"));
				menu.setStatus(Integer.parseInt(entry.get("status")));
				sysRoleMenuMapper.insert(menu);
			}
			return ReturnInfo.Success;
		} catch (Exception e) {
			log.warn("SysRoleMenuCtrl insert error..", e);
			// e.printStackTrace();
		}
		return ReturnInfo.Faild;
	}

	public MenuInfo infoMenu(SysMenu menu, List<MenuInfo> children) {
		MenuInfo info = new MenuInfo();
		info.setIcon(menu.getIcon());
		info.setName(menu.getMenuName());
		info.setUrl(menu.getMenuUrl());
		info.setChildren(children);
		return info;
	}

	@RequestMapping(value = "/{roles}", method = RequestMethod.GET)
	@ResponseBody
	public Object menuByRole(@PathVariable String roles, HttpServletRequest req,HttpServletResponse res) {
		try {
			StringBuffer sb = new StringBuffer("SELECT distinct m.* FROM T_SYS_ROLE r,T_SYS_ROLE_MENU rm,T_SYS_MENU m WHERE r.ROLE_ID = rm.ROLE_ID AND m.MENU_ID = rm.MENU_ID AND m.LEVEL1=0 AND rm.ROLE_ID IN ( ");
			StringBuffer sb1 = new StringBuffer("SELECT distinct m.* FROM T_SYS_ROLE r,T_SYS_ROLE_MENU rm,T_SYS_MENU m WHERE r.ROLE_ID = rm.ROLE_ID AND m.MENU_ID = rm.MENU_ID AND m.LEVEL1=1 AND rm.ROLE_ID IN ( ");
			List<MenuInfo> menuInfoList = new ArrayList<MenuInfo>();
			for (String r : roles.split(",")) {
				sb.append(Integer.parseInt(r)+",");
				sb1.append(Integer.parseInt(r)+",");
			}
			sb = new StringBuffer(sb.substring(0,sb.length()-1));
			sb1 = new StringBuffer(sb1.substring(0,sb1.length()-1));
			sb.append(" ) ");
			List<SysMenu> menuParents = exSysRoleMenuMapper.selectAllParent(sb.toString());
			for(SysMenu menuParent:menuParents){
				MenuInfo menuInfo = new MenuInfo();
				menuInfo.setName(menuParent.getMenuName());
				menuInfo.setIcon(menuParent.getIcon());
				menuInfo.setUrl(menuParent.getMenuUrl());
				StringBuffer sb2 = new StringBuffer(sb1.toString());
				sb2.append(")  AND m.PARENT_ID = " + menuParent.getMenuId()).append(" ");
				List<SysMenu> childrens = exSysRoleMenuMapper.selectChildrenByParent(sb2.toString());
				if(childrens!=null&&childrens.size()>0){
					List<MenuInfo> menuInfoChildrenList = new ArrayList<MenuInfo>();
					for(SysMenu children:childrens){
						MenuInfo childrenMenuInfo = new MenuInfo();
						childrenMenuInfo.setName(children.getMenuName());
						childrenMenuInfo.setIcon(children.getIcon());
						childrenMenuInfo.setUrl(children.getMenuUrl());
						childrenMenuInfo.setChildren(null);
						menuInfoChildrenList.add(childrenMenuInfo);
					}
					menuInfo.setChildren(menuInfoChildrenList);
				}
				menuInfoList.add(menuInfo);
			}
			return menuInfoList;
		} catch (Exception e) {
			log.warn("SysRoleMenuCtrl insert error..", e);
		}
		return ReturnInfo.Faild;
	}
}
