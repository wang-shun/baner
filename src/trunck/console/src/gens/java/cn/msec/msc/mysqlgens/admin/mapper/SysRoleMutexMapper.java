package cn.msec.msc.mysqlgens.admin.mapper;

import cn.msec.msc.mysqlgens.admin.entity.SysRoleMutex;
import cn.msec.msc.mysqlgens.admin.entity.SysRoleMutexExample;
import cn.msec.msc.mysqlgens.admin.entity.SysRoleMutexKey;
import cn.msec.ojpa.msc.mysql.StaticTableDaoSupport;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

public interface SysRoleMutexMapper extends StaticTableDaoSupport<SysRoleMutex, SysRoleMutexExample, SysRoleMutexKey> {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_SYS_ROLE_MUTEX
     *
     * @mbggenerated Thu Dec 31 10:48:44 CST 2015
     */
    @SelectProvider(type=SysRoleMutexSqlProvider.class, method="countByExample")
    int countByExample(SysRoleMutexExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_SYS_ROLE_MUTEX
     *
     * @mbggenerated Thu Dec 31 10:48:44 CST 2015
     */
    @DeleteProvider(type=SysRoleMutexSqlProvider.class, method="deleteByExample")
    int deleteByExample(SysRoleMutexExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_SYS_ROLE_MUTEX
     *
     * @mbggenerated Thu Dec 31 10:48:44 CST 2015
     */
    @Delete({
        "delete from T_SYS_ROLE_MUTEX",
        "where ROLE_MUTEX_ID = #{roleMutexId,jdbcType=VARCHAR}"
    })
    int deleteByPrimaryKey(SysRoleMutexKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_SYS_ROLE_MUTEX
     *
     * @mbggenerated Thu Dec 31 10:48:44 CST 2015
     */
    @Insert({
        "insert into T_SYS_ROLE_MUTEX (ROLE_MUTEX_ID, ROLE_ID_A, ",
        "ROLE_ID_B, STATUS)",
        "values (#{roleMutexId,jdbcType=VARCHAR}, #{roleIdA,jdbcType=VARCHAR}, ",
        "#{roleIdB,jdbcType=VARCHAR}, #{status,jdbcType=DECIMAL})"
    })
    int insert(SysRoleMutex record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_SYS_ROLE_MUTEX
     *
     * @mbggenerated Thu Dec 31 10:48:44 CST 2015
     */
    @InsertProvider(type=SysRoleMutexSqlProvider.class, method="insertSelective")
    int insertSelective(SysRoleMutex record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_SYS_ROLE_MUTEX
     *
     * @mbggenerated Thu Dec 31 10:48:44 CST 2015
     */
    @SelectProvider(type=SysRoleMutexSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="ROLE_MUTEX_ID", property="roleMutexId", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="ROLE_ID_A", property="roleIdA", jdbcType=JdbcType.VARCHAR),
        @Result(column="ROLE_ID_B", property="roleIdB", jdbcType=JdbcType.VARCHAR),
        @Result(column="STATUS", property="status", jdbcType=JdbcType.DECIMAL)
    })
    List<SysRoleMutex> selectByExample(SysRoleMutexExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_SYS_ROLE_MUTEX
     *
     * @mbggenerated Thu Dec 31 10:48:44 CST 2015
     */
    @Select({
        "select",
        "ROLE_MUTEX_ID, ROLE_ID_A, ROLE_ID_B, STATUS",
        "from T_SYS_ROLE_MUTEX",
        "where ROLE_MUTEX_ID = #{roleMutexId,jdbcType=VARCHAR}"
    })
    @Results({
        @Result(column="ROLE_MUTEX_ID", property="roleMutexId", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="ROLE_ID_A", property="roleIdA", jdbcType=JdbcType.VARCHAR),
        @Result(column="ROLE_ID_B", property="roleIdB", jdbcType=JdbcType.VARCHAR),
        @Result(column="STATUS", property="status", jdbcType=JdbcType.DECIMAL)
    })
    SysRoleMutex selectByPrimaryKey(SysRoleMutexKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_SYS_ROLE_MUTEX
     *
     * @mbggenerated Thu Dec 31 10:48:44 CST 2015
     */
    @UpdateProvider(type=SysRoleMutexSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") SysRoleMutex record, @Param("example") SysRoleMutexExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_SYS_ROLE_MUTEX
     *
     * @mbggenerated Thu Dec 31 10:48:44 CST 2015
     */
    @UpdateProvider(type=SysRoleMutexSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") SysRoleMutex record, @Param("example") SysRoleMutexExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_SYS_ROLE_MUTEX
     *
     * @mbggenerated Thu Dec 31 10:48:44 CST 2015
     */
    @UpdateProvider(type=SysRoleMutexSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(SysRoleMutex record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_SYS_ROLE_MUTEX
     *
     * @mbggenerated Thu Dec 31 10:48:44 CST 2015
     */
    @Update({
        "update T_SYS_ROLE_MUTEX",
        "set ROLE_ID_A = #{roleIdA,jdbcType=VARCHAR},",
          "ROLE_ID_B = #{roleIdB,jdbcType=VARCHAR},",
          "STATUS = #{status,jdbcType=DECIMAL}",
        "where ROLE_MUTEX_ID = #{roleMutexId,jdbcType=VARCHAR}"
    })
    int updateByPrimaryKey(SysRoleMutex record);
}