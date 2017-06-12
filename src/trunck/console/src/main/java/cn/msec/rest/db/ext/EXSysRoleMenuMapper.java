package cn.msec.rest.db.ext;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;

import cn.msec.msc.mysqlgens.admin.entity.SysMenu;


public interface EXSysRoleMenuMapper {

	@SelectProvider(type=EXSysRoleMenuSqlProvider.class,method="selectAllParent")
	@Results({
			@Result(column = "MENU_ID", property = "menuId", jdbcType = JdbcType.VARCHAR),
			@Result(column = "MENU_NAME", property = "menuName", jdbcType = JdbcType.VARCHAR),
			@Result(column = "MENU_URL", property = "menuUrl", jdbcType = JdbcType.VARCHAR),
			@Result(column = "PRAENT_ID", property = "parentId", jdbcType = JdbcType.VARCHAR),
			@Result(column = "LEVEL1", property = "level1", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ICON", property = "icon", jdbcType = JdbcType.VARCHAR)
			})
	List<SysMenu> selectAllParent(String sql);
	
	@SelectProvider(type=EXSysRoleMenuSqlProvider.class,method="selectChildrenByParent")
	@Results({
		@Result(column = "MENU_ID", property = "menuId", jdbcType = JdbcType.VARCHAR),
		@Result(column = "MENU_NAME", property = "menuName", jdbcType = JdbcType.VARCHAR),
		@Result(column = "MENU_URL", property = "menuUrl", jdbcType = JdbcType.VARCHAR),
		@Result(column = "PRAENT_ID", property = "parentId", jdbcType = JdbcType.VARCHAR),
		@Result(column = "LEVEL1", property = "level1", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ICON", property = "icon", jdbcType = JdbcType.VARCHAR)
		})
	List<SysMenu> selectChildrenByParent(String sql);
}
