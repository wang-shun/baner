package org.inn.baner.persist.mapper;

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
import org.inn.baner.bean.Post;
import org.inn.baner.bean.PostExample;
import org.inn.baner.bean.PostKey;

public interface PostMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table post
     *
     * @mbg.generated
     */
    @SelectProvider(type=PostSqlProvider.class, method="countByExample")
    long countByExample(PostExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table post
     *
     * @mbg.generated
     */
    @DeleteProvider(type=PostSqlProvider.class, method="deleteByExample")
    int deleteByExample(PostExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table post
     *
     * @mbg.generated
     */
    @Delete({
        "delete from post",
        "where postid = #{postid,jdbcType=VARCHAR}",
          "and topicid = #{topicid,jdbcType=VARCHAR}"
    })
    int deleteByPrimaryKey(PostKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table post
     *
     * @mbg.generated
     */
    @Insert({
        "insert into post (postid, topicid, ",
        "creatormobileno, postname, ",
        "postdesc, zantimes, ",
        "createtime, updatetime, ",
        "isanon, context)",
        "values (#{postid,jdbcType=VARCHAR}, #{topicid,jdbcType=VARCHAR}, ",
        "#{creatormobileno,jdbcType=VARCHAR}, #{postname,jdbcType=VARCHAR}, ",
        "#{postdesc,jdbcType=VARCHAR}, #{zantimes,jdbcType=INTEGER}, ",
        "#{createtime,jdbcType=TIMESTAMP}, #{updatetime,jdbcType=TIMESTAMP}, ",
        "#{isanon,jdbcType=INTEGER}, #{context,jdbcType=LONGVARBINARY})"
    })
    int insert(Post record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table post
     *
     * @mbg.generated
     */
    @InsertProvider(type=PostSqlProvider.class, method="insertSelective")
    int insertSelective(Post record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table post
     *
     * @mbg.generated
     */
    @SelectProvider(type=PostSqlProvider.class, method="selectByExampleWithBLOBs")
    @Results({
        @Result(column="postid", property="postid", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="topicid", property="topicid", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="creatormobileno", property="creatormobileno", jdbcType=JdbcType.VARCHAR),
        @Result(column="postname", property="postname", jdbcType=JdbcType.VARCHAR),
        @Result(column="postdesc", property="postdesc", jdbcType=JdbcType.VARCHAR),
        @Result(column="zantimes", property="zantimes", jdbcType=JdbcType.INTEGER),
        @Result(column="createtime", property="createtime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="updatetime", property="updatetime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="isanon", property="isanon", jdbcType=JdbcType.INTEGER),
        @Result(column="context", property="context", jdbcType=JdbcType.LONGVARBINARY)
    })
    List<Post> selectByExampleWithBLOBs(PostExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table post
     *
     * @mbg.generated
     */
    @SelectProvider(type=PostSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="postid", property="postid", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="topicid", property="topicid", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="creatormobileno", property="creatormobileno", jdbcType=JdbcType.VARCHAR),
        @Result(column="postname", property="postname", jdbcType=JdbcType.VARCHAR),
        @Result(column="postdesc", property="postdesc", jdbcType=JdbcType.VARCHAR),
        @Result(column="zantimes", property="zantimes", jdbcType=JdbcType.INTEGER),
        @Result(column="createtime", property="createtime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="updatetime", property="updatetime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="isanon", property="isanon", jdbcType=JdbcType.INTEGER)
    })
    List<Post> selectByExample(PostExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table post
     *
     * @mbg.generated
     */
    @Select({
        "select",
        "postid, topicid, creatormobileno, postname, postdesc, zantimes, createtime, ",
        "updatetime, isanon, context",
        "from post",
        "where postid = #{postid,jdbcType=VARCHAR}",
          "and topicid = #{topicid,jdbcType=VARCHAR}"
    })
    @Results({
        @Result(column="postid", property="postid", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="topicid", property="topicid", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="creatormobileno", property="creatormobileno", jdbcType=JdbcType.VARCHAR),
        @Result(column="postname", property="postname", jdbcType=JdbcType.VARCHAR),
        @Result(column="postdesc", property="postdesc", jdbcType=JdbcType.VARCHAR),
        @Result(column="zantimes", property="zantimes", jdbcType=JdbcType.INTEGER),
        @Result(column="createtime", property="createtime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="updatetime", property="updatetime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="isanon", property="isanon", jdbcType=JdbcType.INTEGER),
        @Result(column="context", property="context", jdbcType=JdbcType.LONGVARBINARY)
    })
    Post selectByPrimaryKey(PostKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table post
     *
     * @mbg.generated
     */
    @UpdateProvider(type=PostSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") Post record, @Param("example") PostExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table post
     *
     * @mbg.generated
     */
    @UpdateProvider(type=PostSqlProvider.class, method="updateByExampleWithBLOBs")
    int updateByExampleWithBLOBs(@Param("record") Post record, @Param("example") PostExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table post
     *
     * @mbg.generated
     */
    @UpdateProvider(type=PostSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") Post record, @Param("example") PostExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table post
     *
     * @mbg.generated
     */
    @UpdateProvider(type=PostSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Post record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table post
     *
     * @mbg.generated
     */
    @Update({
        "update post",
        "set creatormobileno = #{creatormobileno,jdbcType=VARCHAR},",
          "postname = #{postname,jdbcType=VARCHAR},",
          "postdesc = #{postdesc,jdbcType=VARCHAR},",
          "zantimes = #{zantimes,jdbcType=INTEGER},",
          "createtime = #{createtime,jdbcType=TIMESTAMP},",
          "updatetime = #{updatetime,jdbcType=TIMESTAMP},",
          "isanon = #{isanon,jdbcType=INTEGER},",
          "context = #{context,jdbcType=LONGVARBINARY}",
        "where postid = #{postid,jdbcType=VARCHAR}",
          "and topicid = #{topicid,jdbcType=VARCHAR}"
    })
    int updateByPrimaryKeyWithBLOBs(Post record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table post
     *
     * @mbg.generated
     */
    @Update({
        "update post",
        "set creatormobileno = #{creatormobileno,jdbcType=VARCHAR},",
          "postname = #{postname,jdbcType=VARCHAR},",
          "postdesc = #{postdesc,jdbcType=VARCHAR},",
          "zantimes = #{zantimes,jdbcType=INTEGER},",
          "createtime = #{createtime,jdbcType=TIMESTAMP},",
          "updatetime = #{updatetime,jdbcType=TIMESTAMP},",
          "isanon = #{isanon,jdbcType=INTEGER}",
        "where postid = #{postid,jdbcType=VARCHAR}",
          "and topicid = #{topicid,jdbcType=VARCHAR}"
    })
    int updateByPrimaryKey(Post record);
}