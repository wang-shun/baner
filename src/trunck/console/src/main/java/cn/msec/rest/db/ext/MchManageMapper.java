package cn.msec.rest.db.ext;

import java.util.List;
import java.util.Map;

import lombok.Data;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.type.JdbcType;


public interface MchManageMapper {

	@Data
	public class MchInfoAll {
		String role;
		String username;
		String userid;
	}

    @Update({
        "update T_SYS_USER",
        "set PASSWORD = #{password,jdbcType=VARCHAR}",
        "where USER_ID = #{userId,jdbcType=CHAR}"
    })
    int updateByPrimaryKey(Map<String, String> record);
    
    
    @Update({
        "update T_SYS_USER",
        "set PASSWORD = #{newPassword,jdbcType=VARCHAR}",
        "where USER_ID = #{userId,jdbcType=CHAR}",
        "and PASSWORD = #{oldPassword,jdbcType=CHAR}"
    })
    int updatePwdByPrimaryKey(Map<String, String> record);
    
	@Select(value = "select r.role_name as role ,u.USER_NAME,u.USER_ID from fc_user u,FC_USER_ROLE ur,fc_role r where "
			+ "u.USER_ID = ur.USER_ID and ur.ROLE_ID=r.ROLE_ID")
	@Results({
			@Result(column = "role", property = "role", jdbcType = JdbcType.VARCHAR),
			@Result(column = "USER_NAME", property = "username", jdbcType = JdbcType.VARCHAR),
			@Result(column = "USER_ID", property = "userid", jdbcType = JdbcType.VARCHAR) })
	List<MchInfoAll> selectMchInfoAll();
}
