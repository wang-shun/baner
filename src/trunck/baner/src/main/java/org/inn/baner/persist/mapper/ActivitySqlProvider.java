package org.inn.baner.persist.mapper;

import static org.apache.ibatis.jdbc.SqlBuilder.BEGIN;
import static org.apache.ibatis.jdbc.SqlBuilder.DELETE_FROM;
import static org.apache.ibatis.jdbc.SqlBuilder.FROM;
import static org.apache.ibatis.jdbc.SqlBuilder.INSERT_INTO;
import static org.apache.ibatis.jdbc.SqlBuilder.ORDER_BY;
import static org.apache.ibatis.jdbc.SqlBuilder.SELECT;
import static org.apache.ibatis.jdbc.SqlBuilder.SELECT_DISTINCT;
import static org.apache.ibatis.jdbc.SqlBuilder.SET;
import static org.apache.ibatis.jdbc.SqlBuilder.SQL;
import static org.apache.ibatis.jdbc.SqlBuilder.UPDATE;
import static org.apache.ibatis.jdbc.SqlBuilder.VALUES;
import static org.apache.ibatis.jdbc.SqlBuilder.WHERE;

import java.util.List;
import java.util.Map;
import org.inn.baner.bean.Activity;
import org.inn.baner.bean.ActivityExample.Criteria;
import org.inn.baner.bean.ActivityExample.Criterion;
import org.inn.baner.bean.ActivityExample;

public class ActivitySqlProvider {

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table activity
     *
     * @mbggenerated
     */
    public String countByExample(ActivityExample example) {
        BEGIN();
        SELECT("count (*)");
        FROM("activity");
        applyWhere(example, false);
        return SQL();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table activity
     *
     * @mbggenerated
     */
    public String deleteByExample(ActivityExample example) {
        BEGIN();
        DELETE_FROM("activity");
        applyWhere(example, false);
        return SQL();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table activity
     *
     * @mbggenerated
     */
    public String insertSelective(Activity record) {
        BEGIN();
        INSERT_INTO("activity");
        
        if (record.getId() != null) {
            VALUES("id", "#{id,jdbcType=VARCHAR}");
        }
        
        if (record.getName() != null) {
            VALUES("name", "#{name,jdbcType=VARCHAR}");
        }
        
        if (record.getActitype() != null) {
            VALUES("actitype", "#{actitype,jdbcType=CHAR}");
        }
        
        if (record.getTotal() != null) {
            VALUES("total", "#{total,jdbcType=DECIMAL}");
        }
        
        if (record.getQuota() != null) {
            VALUES("quota", "#{quota,jdbcType=INTEGER}");
        }
        
        if (record.getMinamount() != null) {
            VALUES("minamount", "#{minamount,jdbcType=DECIMAL}");
        }
        
        if (record.getMaxamount() != null) {
            VALUES("maxamount", "#{maxamount,jdbcType=DECIMAL}");
        }
        
        if (record.getCreatetime() != null) {
            VALUES("createtime", "#{createtime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getStarttime() != null) {
            VALUES("starttime", "#{starttime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getEndtime() != null) {
            VALUES("endtime", "#{endtime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUpdatetime() != null) {
            VALUES("updatetime", "#{updatetime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getStat() != null) {
            VALUES("stat", "#{stat,jdbcType=CHAR}");
        }
        
        if (record.getListimg() != null) {
            VALUES("listimg", "#{listimg,jdbcType=VARCHAR}");
        }
        
        if (record.getLinkaddress() != null) {
            VALUES("linkaddress", "#{linkaddress,jdbcType=VARCHAR}");
        }
        
        if (record.getMerno() != null) {
            VALUES("merno", "#{merno,jdbcType=VARCHAR}");
        }
        
        if (record.getMername() != null) {
            VALUES("mername", "#{mername,jdbcType=VARCHAR}");
        }
        
        if (record.getRemark() != null) {
            VALUES("remark", "#{remark,jdbcType=VARCHAR}");
        }
        
        return SQL();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table activity
     *
     * @mbggenerated
     */
    public String selectByExample(ActivityExample example) {
        BEGIN();
        if (example != null && example.isDistinct()) {
            SELECT_DISTINCT("id");
        } else {
            SELECT("id");
        }
        SELECT("name");
        SELECT("actitype");
        SELECT("total");
        SELECT("quota");
        SELECT("minamount");
        SELECT("maxamount");
        SELECT("createtime");
        SELECT("starttime");
        SELECT("endtime");
        SELECT("updatetime");
        SELECT("stat");
        SELECT("listimg");
        SELECT("linkaddress");
        SELECT("merno");
        SELECT("mername");
        SELECT("remark");
        FROM("activity");
        applyWhere(example, false);
        
        if (example != null && example.getOrderByClause() != null) {
            ORDER_BY(example.getOrderByClause());
        }
        
        return SQL();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table activity
     *
     * @mbggenerated
     */
    public String updateByExampleSelective(Map<String, Object> parameter) {
        Activity record = (Activity) parameter.get("record");
        ActivityExample example = (ActivityExample) parameter.get("example");
        
        BEGIN();
        UPDATE("activity");
        
        if (record.getId() != null) {
            SET("id = #{record.id,jdbcType=VARCHAR}");
        }
        
        if (record.getName() != null) {
            SET("name = #{record.name,jdbcType=VARCHAR}");
        }
        
        if (record.getActitype() != null) {
            SET("actitype = #{record.actitype,jdbcType=CHAR}");
        }
        
        if (record.getTotal() != null) {
            SET("total = #{record.total,jdbcType=DECIMAL}");
        }
        
        if (record.getQuota() != null) {
            SET("quota = #{record.quota,jdbcType=INTEGER}");
        }
        
        if (record.getMinamount() != null) {
            SET("minamount = #{record.minamount,jdbcType=DECIMAL}");
        }
        
        if (record.getMaxamount() != null) {
            SET("maxamount = #{record.maxamount,jdbcType=DECIMAL}");
        }
        
        if (record.getCreatetime() != null) {
            SET("createtime = #{record.createtime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getStarttime() != null) {
            SET("starttime = #{record.starttime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getEndtime() != null) {
            SET("endtime = #{record.endtime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUpdatetime() != null) {
            SET("updatetime = #{record.updatetime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getStat() != null) {
            SET("stat = #{record.stat,jdbcType=CHAR}");
        }
        
        if (record.getListimg() != null) {
            SET("listimg = #{record.listimg,jdbcType=VARCHAR}");
        }
        
        if (record.getLinkaddress() != null) {
            SET("linkaddress = #{record.linkaddress,jdbcType=VARCHAR}");
        }
        
        if (record.getMerno() != null) {
            SET("merno = #{record.merno,jdbcType=VARCHAR}");
        }
        
        if (record.getMername() != null) {
            SET("mername = #{record.mername,jdbcType=VARCHAR}");
        }
        
        if (record.getRemark() != null) {
            SET("remark = #{record.remark,jdbcType=VARCHAR}");
        }
        
        applyWhere(example, true);
        return SQL();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table activity
     *
     * @mbggenerated
     */
    public String updateByExample(Map<String, Object> parameter) {
        BEGIN();
        UPDATE("activity");
        
        SET("id = #{record.id,jdbcType=VARCHAR}");
        SET("name = #{record.name,jdbcType=VARCHAR}");
        SET("actitype = #{record.actitype,jdbcType=CHAR}");
        SET("total = #{record.total,jdbcType=DECIMAL}");
        SET("quota = #{record.quota,jdbcType=INTEGER}");
        SET("minamount = #{record.minamount,jdbcType=DECIMAL}");
        SET("maxamount = #{record.maxamount,jdbcType=DECIMAL}");
        SET("createtime = #{record.createtime,jdbcType=TIMESTAMP}");
        SET("starttime = #{record.starttime,jdbcType=TIMESTAMP}");
        SET("endtime = #{record.endtime,jdbcType=TIMESTAMP}");
        SET("updatetime = #{record.updatetime,jdbcType=TIMESTAMP}");
        SET("stat = #{record.stat,jdbcType=CHAR}");
        SET("listimg = #{record.listimg,jdbcType=VARCHAR}");
        SET("linkaddress = #{record.linkaddress,jdbcType=VARCHAR}");
        SET("merno = #{record.merno,jdbcType=VARCHAR}");
        SET("mername = #{record.mername,jdbcType=VARCHAR}");
        SET("remark = #{record.remark,jdbcType=VARCHAR}");
        
        ActivityExample example = (ActivityExample) parameter.get("example");
        applyWhere(example, true);
        return SQL();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table activity
     *
     * @mbggenerated
     */
    public String updateByPrimaryKeySelective(Activity record) {
        BEGIN();
        UPDATE("activity");
        
        if (record.getName() != null) {
            SET("name = #{name,jdbcType=VARCHAR}");
        }
        
        if (record.getActitype() != null) {
            SET("actitype = #{actitype,jdbcType=CHAR}");
        }
        
        if (record.getTotal() != null) {
            SET("total = #{total,jdbcType=DECIMAL}");
        }
        
        if (record.getQuota() != null) {
            SET("quota = #{quota,jdbcType=INTEGER}");
        }
        
        if (record.getMinamount() != null) {
            SET("minamount = #{minamount,jdbcType=DECIMAL}");
        }
        
        if (record.getMaxamount() != null) {
            SET("maxamount = #{maxamount,jdbcType=DECIMAL}");
        }
        
        if (record.getCreatetime() != null) {
            SET("createtime = #{createtime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getStarttime() != null) {
            SET("starttime = #{starttime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getEndtime() != null) {
            SET("endtime = #{endtime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUpdatetime() != null) {
            SET("updatetime = #{updatetime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getStat() != null) {
            SET("stat = #{stat,jdbcType=CHAR}");
        }
        
        if (record.getListimg() != null) {
            SET("listimg = #{listimg,jdbcType=VARCHAR}");
        }
        
        if (record.getLinkaddress() != null) {
            SET("linkaddress = #{linkaddress,jdbcType=VARCHAR}");
        }
        
        if (record.getMerno() != null) {
            SET("merno = #{merno,jdbcType=VARCHAR}");
        }
        
        if (record.getMername() != null) {
            SET("mername = #{mername,jdbcType=VARCHAR}");
        }
        
        if (record.getRemark() != null) {
            SET("remark = #{remark,jdbcType=VARCHAR}");
        }
        
        WHERE("id = #{id,jdbcType=VARCHAR}");
        
        return SQL();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table activity
     *
     * @mbggenerated
     */
    protected void applyWhere(ActivityExample example, boolean includeExamplePhrase) {
        if (example == null) {
            return;
        }
        
        String parmPhrase1;
        String parmPhrase1_th;
        String parmPhrase2;
        String parmPhrase2_th;
        String parmPhrase3;
        String parmPhrase3_th;
        if (includeExamplePhrase) {
            parmPhrase1 = "%s #{example.oredCriteria[%d].allCriteria[%d].value}";
            parmPhrase1_th = "%s #{example.oredCriteria[%d].allCriteria[%d].value,typeHandler=%s}";
            parmPhrase2 = "%s #{example.oredCriteria[%d].allCriteria[%d].value} and #{example.oredCriteria[%d].criteria[%d].secondValue}";
            parmPhrase2_th = "%s #{example.oredCriteria[%d].allCriteria[%d].value,typeHandler=%s} and #{example.oredCriteria[%d].criteria[%d].secondValue,typeHandler=%s}";
            parmPhrase3 = "#{example.oredCriteria[%d].allCriteria[%d].value[%d]}";
            parmPhrase3_th = "#{example.oredCriteria[%d].allCriteria[%d].value[%d],typeHandler=%s}";
        } else {
            parmPhrase1 = "%s #{oredCriteria[%d].allCriteria[%d].value}";
            parmPhrase1_th = "%s #{oredCriteria[%d].allCriteria[%d].value,typeHandler=%s}";
            parmPhrase2 = "%s #{oredCriteria[%d].allCriteria[%d].value} and #{oredCriteria[%d].criteria[%d].secondValue}";
            parmPhrase2_th = "%s #{oredCriteria[%d].allCriteria[%d].value,typeHandler=%s} and #{oredCriteria[%d].criteria[%d].secondValue,typeHandler=%s}";
            parmPhrase3 = "#{oredCriteria[%d].allCriteria[%d].value[%d]}";
            parmPhrase3_th = "#{oredCriteria[%d].allCriteria[%d].value[%d],typeHandler=%s}";
        }
        
        StringBuilder sb = new StringBuilder();
        List<Criteria> oredCriteria = example.getOredCriteria();
        boolean firstCriteria = true;
        for (int i = 0; i < oredCriteria.size(); i++) {
            Criteria criteria = oredCriteria.get(i);
            if (criteria.isValid()) {
                if (firstCriteria) {
                    firstCriteria = false;
                } else {
                    sb.append(" or ");
                }
                
                sb.append('(');
                List<Criterion> criterions = criteria.getAllCriteria();
                boolean firstCriterion = true;
                for (int j = 0; j < criterions.size(); j++) {
                    Criterion criterion = criterions.get(j);
                    if (firstCriterion) {
                        firstCriterion = false;
                    } else {
                        sb.append(" and ");
                    }
                    
                    if (criterion.isNoValue()) {
                        sb.append(criterion.getCondition());
                    } else if (criterion.isSingleValue()) {
                        if (criterion.getTypeHandler() == null) {
                            sb.append(String.format(parmPhrase1, criterion.getCondition(), i, j));
                        } else {
                            sb.append(String.format(parmPhrase1_th, criterion.getCondition(), i, j,criterion.getTypeHandler()));
                        }
                    } else if (criterion.isBetweenValue()) {
                        if (criterion.getTypeHandler() == null) {
                            sb.append(String.format(parmPhrase2, criterion.getCondition(), i, j, i, j));
                        } else {
                            sb.append(String.format(parmPhrase2_th, criterion.getCondition(), i, j, criterion.getTypeHandler(), i, j, criterion.getTypeHandler()));
                        }
                    } else if (criterion.isListValue()) {
                        sb.append(criterion.getCondition());
                        sb.append(" (");
                        List<?> listItems = (List<?>) criterion.getValue();
                        boolean comma = false;
                        for (int k = 0; k < listItems.size(); k++) {
                            if (comma) {
                                sb.append(", ");
                            } else {
                                comma = true;
                            }
                            if (criterion.getTypeHandler() == null) {
                                sb.append(String.format(parmPhrase3, i, j, k));
                            } else {
                                sb.append(String.format(parmPhrase3_th, i, j, k, criterion.getTypeHandler()));
                            }
                        }
                        sb.append(')');
                    }
                }
                sb.append(')');
            }
        }
        
        if (sb.length() > 0) {
            WHERE(sb.toString());
        }
    }
}