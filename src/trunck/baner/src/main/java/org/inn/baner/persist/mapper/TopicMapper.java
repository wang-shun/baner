package org.inn.baner.persist.mapper;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.inn.baner.bean.Topic;
import org.inn.baner.bean.TopicExample;

import java.util.List;

public interface TopicMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table topic
     *
     * @mbg.generated
     */
    @SelectProvider(type=TopicSqlProvider.class, method="countByExample")
    long countByExample(TopicExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table topic
     *
     * @mbg.generated
     */
    @DeleteProvider(type=TopicSqlProvider.class, method="deleteByExample")
    int deleteByExample(TopicExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table topic
     *
     * @mbg.generated
     */
    @Delete({
        "delete from topic",
        "where topicid = #{topicid,jdbcType=VARCHAR}"
    })
    int deleteByPrimaryKey(String topicid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table topic
     *
     * @mbg.generated
     */
    @Insert({
        "insert into topic (topicid, topicdesc, ",
        "creatormobileno, parenttopicid, ",
        "topiclogo)",
        "values (#{topicid,jdbcType=VARCHAR}, #{topicdesc,jdbcType=VARCHAR}, ",
        "#{creatormobileno,jdbcType=VARCHAR}, #{parenttopicid,jdbcType=VARCHAR}, ",
        "#{topiclogo,jdbcType=LONGVARBINARY})"
    })
    int insert(Topic record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table topic
     *
     * @mbg.generated
     */
    @InsertProvider(type=TopicSqlProvider.class, method="insertSelective")
    int insertSelective(Topic record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table topic
     *
     * @mbg.generated
     */
    @SelectProvider(type=TopicSqlProvider.class, method="selectByExampleWithBLOBs")
    @Results({
        @Result(column="topicid", property="topicid", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="topicdesc", property="topicdesc", jdbcType=JdbcType.VARCHAR),
        @Result(column="creatormobileno", property="creatormobileno", jdbcType=JdbcType.VARCHAR),
        @Result(column="parenttopicid", property="parenttopicid", jdbcType=JdbcType.VARCHAR),
        @Result(column="topiclogo", property="topiclogo", jdbcType=JdbcType.LONGVARBINARY)
    })
    List<Topic> selectByExampleWithBLOBs(TopicExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table topic
     *
     * @mbg.generated
     */
    @SelectProvider(type=TopicSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="topicid", property="topicid", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="topicdesc", property="topicdesc", jdbcType=JdbcType.VARCHAR),
        @Result(column="creatormobileno", property="creatormobileno", jdbcType=JdbcType.VARCHAR),
        @Result(column="parenttopicid", property="parenttopicid", jdbcType=JdbcType.VARCHAR)
    })
    List<Topic> selectByExample(TopicExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table topic
     *
     * @mbg.generated
     */
    @Select({
        "select",
        "topicid, topicdesc, creatormobileno, parenttopicid, topiclogo",
        "from topic",
        "where topicid = #{topicid,jdbcType=VARCHAR}"
    })
    @Results({
        @Result(column="topicid", property="topicid", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="topicdesc", property="topicdesc", jdbcType=JdbcType.VARCHAR),
        @Result(column="creatormobileno", property="creatormobileno", jdbcType=JdbcType.VARCHAR),
        @Result(column="parenttopicid", property="parenttopicid", jdbcType=JdbcType.VARCHAR),
        @Result(column="topiclogo", property="topiclogo", jdbcType=JdbcType.LONGVARBINARY)
    })
    Topic selectByPrimaryKey(String topicid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table topic
     *
     * @mbg.generated
     */
    @UpdateProvider(type=TopicSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") Topic record, @Param("example") TopicExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table topic
     *
     * @mbg.generated
     */
    @UpdateProvider(type=TopicSqlProvider.class, method="updateByExampleWithBLOBs")
    int updateByExampleWithBLOBs(@Param("record") Topic record, @Param("example") TopicExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table topic
     *
     * @mbg.generated
     */
    @UpdateProvider(type=TopicSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") Topic record, @Param("example") TopicExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table topic
     *
     * @mbg.generated
     */
    @UpdateProvider(type=TopicSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Topic record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table topic
     *
     * @mbg.generated
     */
    @Update({
        "update topic",
        "set topicdesc = #{topicdesc,jdbcType=VARCHAR},",
          "creatormobileno = #{creatormobileno,jdbcType=VARCHAR},",
          "parenttopicid = #{parenttopicid,jdbcType=VARCHAR},",
          "topiclogo = #{topiclogo,jdbcType=LONGVARBINARY}",
        "where topicid = #{topicid,jdbcType=VARCHAR}"
    })
    int updateByPrimaryKeyWithBLOBs(Topic record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table topic
     *
     * @mbg.generated
     */
    @Update({
        "update topic",
        "set topicdesc = #{topicdesc,jdbcType=VARCHAR},",
          "creatormobileno = #{creatormobileno,jdbcType=VARCHAR},",
          "parenttopicid = #{parenttopicid,jdbcType=VARCHAR}",
        "where topicid = #{topicid,jdbcType=VARCHAR}"
    })
    int updateByPrimaryKey(Topic record);
}