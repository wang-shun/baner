package cn.msec.rest.web.action.ext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.msec.msc.mysqlgens.admin.entity.SysMenu;
import cn.msec.msc.mysqlgens.admin.entity.SysMenuKey;
import cn.msec.msc.mysqlgens.admin.entity.SysRoleAction;
import cn.msec.msc.mysqlgens.admin.entity.SysRoleActionExample;
import cn.msec.msc.mysqlgens.admin.entity.SysRoleMenu;
import cn.msec.msc.mysqlgens.admin.entity.SysRoleMenuExample;
import cn.msec.msc.mysqlgens.admin.mapper.SysActionMapper;
import cn.msec.msc.mysqlgens.admin.mapper.SysMenuMapper;
import cn.msec.msc.mysqlgens.admin.mapper.SysRoleActionMapper;
import cn.msec.msc.mysqlgens.admin.mapper.SysRoleMenuMapper;
import cn.msec.rest.db.service.DataService;
import cn.msec.rest.utils.BeanFactory;
import cn.msec.rest.web.action.BasicCtrl;
import cn.msec.rest.web.bean.MenuInfo;
import cn.msec.rest.web.bean.ReturnInfo;

@Slf4j
@Controller
@RequestMapping("/exroleaction")
public class EXRoleActionCtrl extends BasicCtrl {

	private static SysRoleActionMapper sysRoleActionMapper = (SysRoleActionMapper) BeanFactory.getBean("sysRoleActionMapper");

	private static SysActionMapper sysActionMapper = (SysActionMapper) BeanFactory.getBean("sysActionMapper");

	private static DataService mysqlDataService = (DataService) BeanFactory.getBean("mysqlDataService");

	@RequestMapping(value = "/batch", method = RequestMethod.POST)
	@ResponseBody
	public ReturnInfo insertBatch(@RequestBody List<LinkedHashMap<String, String>> lists, HttpServletRequest req) {
		try {
			for (LinkedHashMap<String, String> entry : lists) {
				SysRoleActionExample action = new SysRoleActionExample();
				action.createCriteria().andRoleIdEqualTo((entry.get("roleId")));
				sysRoleActionMapper.deleteByExample(action);
			}

			for (LinkedHashMap<String, String> entry : lists) {
				if("-1".equals(entry.get("actionId"))){
					return ReturnInfo.Success;
				}
				SysRoleAction menu = new SysRoleAction();
				menu.setId(entry.get("id"));
				menu.setActionId(Integer.parseInt(entry.get("actionId")));
				menu.setRoleId(entry.get("roleId"));
				menu.setStatus(Integer.parseInt(entry.get("status")));
				sysRoleActionMapper.insert(menu);
			}
			return ReturnInfo.Success;
		} catch (Exception e) {
			log.warn("SysRoleMenuCtrl insert error..", e);
			// e.printStackTrace();
		}
		return ReturnInfo.Faild;
	}

	/*public MenuInfo infoMenu(SysMenu menu, List<MenuInfo> children) {
		MenuInfo info = new MenuInfo();
		info.setIcon(menu.getIcon());
		info.setName(menu.getMenuName());
		info.setUrl(menu.getMenuUrl());
		info.setChildren(children);
		return info;
	}

	@RequestMapping(value = "/{roles}", method = RequestMethod.GET)
	@ResponseBody
	public Object menuByRole(@PathVariable String roles, HttpServletRequest req) {
		try {
			List<String> iroles = new ArrayList<String>();
			for (String r : roles.split(",")) {
				iroles.add(r);
			}
			SysRoleMenuExample example = new SysRoleMenuExample();
			example.createCriteria().andRoleIdIn(iroles);
			List<SysRoleMenu> rolemenus = sysRoleMenuMapper.selectByExample(example);

			HashMap<Integer, SysMenu> menubyId = new HashMap<>();
			for (SysRoleMenu rm : rolemenus) {
				SysMenuKey menukey = new SysMenuKey();
				menukey.setMenuId(rm.getMenuId());
				SysMenu menu = sysMenuMapper.selectByPrimaryKey(menukey);
				if (menu != null) {
					menubyId.put(menu.getMenuId(), menu);
				}
			}

			HashMap<Integer, MenuInfo> treemenu = new HashMap<>();
			for (Map.Entry<Integer, SysMenu> entry : menubyId.entrySet()) {
				Integer id = entry.getKey();
				SysMenu menu = entry.getValue();
				if (menu.getParentId() != null && menubyId.containsKey(menu.getParentId())) {
					SysMenu parent = menubyId.get(menu.getParentId());
					MenuInfo info = treemenu.get(parent.getMenuId());
					if (info == null || info.getChildren() == null) {
						info = infoMenu(parent, new ArrayList<MenuInfo>());
						treemenu.put(parent.getMenuId(), info);
					}

					info.getChildren().add(infoMenu(menu, null));
				} else if (!treemenu.containsKey(menu.getMenuId())) {
					treemenu.put(menu.getMenuId(), infoMenu(menu, null));
				}
			}

			return treemenu.values();
		} catch (Exception e) {
			log.warn("SysRoleMenuCtrl insert error..", e);
			// e.printStackTrace();
		}
		return ReturnInfo.Faild;
	}*/
}
