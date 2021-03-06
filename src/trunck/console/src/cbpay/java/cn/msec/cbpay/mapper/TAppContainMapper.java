package cn.msec.cbpay.mapper;

import java.util.List;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

import cn.msec.cbpay.entity.TAppContain;
import cn.msec.cbpay.entity.TAppContainExample;
import cn.msec.ojpa.msc.mysql.StaticTableDaoSupport;

public interface TAppContainMapper extends StaticTableDaoSupport<TAppContain, TAppContainExample, TAppContain> {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_APP_CONTAIN
     *
     * @mbggenerated Wed Jul 06 17:21:01 CST 2016
     */
    @SelectProvider(type=TAppContainSqlProvider.class, method="countByExample")
    int countByExample(TAppContainExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_APP_CONTAIN
     *
     * @mbggenerated Wed Jul 06 17:21:01 CST 2016
     */
    @DeleteProvider(type=TAppContainSqlProvider.class, method="deleteByExample")
    int deleteByExample(TAppContainExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_APP_CONTAIN
     *
     * @mbggenerated Wed Jul 06 17:21:01 CST 2016
     */
    @Insert({
        "insert into T_APP_CONTAIN (APPID, CONTAINTYPE, ",
        "IP, VALID)",
        "values (#{appid,jdbcType=VARCHAR}, #{containtype,jdbcType=VARCHAR}, ",
        "#{ip,jdbcType=VARCHAR}, #{valid,jdbcType=CHAR})"
    })
    int insert(TAppContain record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_APP_CONTAIN
     *
     * @mbggenerated Wed Jul 06 17:21:01 CST 2016
     */
    @InsertProvider(type=TAppContainSqlProvider.class, method="insertSelective")
    int insertSelective(TAppContain record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_APP_CONTAIN
     *
     * @mbggenerated Wed Jul 06 17:21:01 CST 2016
     */
    @SelectProvider(type=TAppContainSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="APPID", property="appid", jdbcType=JdbcType.VARCHAR),
        @Result(column="CONTAINTYPE", property="containtype", jdbcType=JdbcType.VARCHAR),
        @Result(column="IP", property="ip", jdbcType=JdbcType.VARCHAR),
        @Result(column="VALID", property="valid", jdbcType=JdbcType.CHAR)
    })
    List<TAppContain> selectByExample(TAppContainExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_APP_CONTAIN
     *
     * @mbggenerated Wed Jul 06 17:21:01 CST 2016
     */
    @UpdateProvider(type=TAppContainSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") TAppContain record, @Param("example") TAppContainExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_APP_CONTAIN
     *
     * @mbggenerated Wed Jul 06 17:21:01 CST 2016
     */
    @UpdateProvider(type=TAppContainSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") TAppContain record, @Param("example") TAppContainExample example);
}