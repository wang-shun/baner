package org.inn.baner.persist.mapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.jdbc.SQL;
import org.inn.baner.bean.LabelSubject;
import org.inn.baner.bean.LabelSubjectExample.Criteria;
import org.inn.baner.bean.LabelSubjectExample.Criterion;
import org.inn.baner.bean.LabelSubjectExample;

public class LabelSubjectSqlProvider {

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table label_subject
     *
     * @mbg.generated
     */
    public String countByExample(LabelSubjectExample example) {
        SQL sql = new SQL();
        sql.SELECT("count(*)").FROM("label_subject");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table label_subject
     *
     * @mbg.generated
     */
    public String deleteByExample(LabelSubjectExample example) {
        SQL sql = new SQL();
        sql.DELETE_FROM("label_subject");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table label_subject
     *
     * @mbg.generated
     */
    public String insertSelective(LabelSubject record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("label_subject");
        
        if (record.getId() != null) {
            sql.VALUES("id", "#{id,jdbcType=BIGINT}");
        }
        
        if (record.getLabelid() != null) {
            sql.VALUES("labelid", "#{labelid,jdbcType=VARCHAR}");
        }
        
        if (record.getSubjectid() != null) {
            sql.VALUES("subjectid", "#{subjectid,jdbcType=VARCHAR}");
        }
        
        if (record.getSubject() != null) {
            sql.VALUES("subject", "#{subject,jdbcType=VARCHAR}");
        }
        
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table label_subject
     *
     * @mbg.generated
     */
    public String selectByExample(LabelSubjectExample example) {
        SQL sql = new SQL();
        if (example != null && example.isDistinct()) {
            sql.SELECT_DISTINCT("id");
        } else {
            sql.SELECT("id");
        }
        sql.SELECT("labelid");
        sql.SELECT("subjectid");
        sql.SELECT("subject");
        sql.FROM("label_subject");
        applyWhere(sql, example, false);
        
        if (example != null && example.getOrderByClause() != null) {
            sql.ORDER_BY(example.getOrderByClause());
        }
        
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table label_subject
     *
     * @mbg.generated
     */
    public String updateByExampleSelective(Map<String, Object> parameter) {
        LabelSubject record = (LabelSubject) parameter.get("record");
        LabelSubjectExample example = (LabelSubjectExample) parameter.get("example");
        
        SQL sql = new SQL();
        sql.UPDATE("label_subject");
        
        if (record.getId() != null) {
            sql.SET("id = #{record.id,jdbcType=BIGINT}");
        }
        
        if (record.getLabelid() != null) {
            sql.SET("labelid = #{record.labelid,jdbcType=VARCHAR}");
        }
        
        if (record.getSubjectid() != null) {
            sql.SET("subjectid = #{record.subjectid,jdbcType=VARCHAR}");
        }
        
        if (record.getSubject() != null) {
            sql.SET("subject = #{record.subject,jdbcType=VARCHAR}");
        }
        
        applyWhere(sql, example, true);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table label_subject
     *
     * @mbg.generated
     */
    public String updateByExample(Map<String, Object> parameter) {
        SQL sql = new SQL();
        sql.UPDATE("label_subject");
        
        sql.SET("id = #{record.id,jdbcType=BIGINT}");
        sql.SET("labelid = #{record.labelid,jdbcType=VARCHAR}");
        sql.SET("subjectid = #{record.subjectid,jdbcType=VARCHAR}");
        sql.SET("subject = #{record.subject,jdbcType=VARCHAR}");
        
        LabelSubjectExample example = (LabelSubjectExample) parameter.get("example");
        applyWhere(sql, example, true);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table label_subject
     *
     * @mbg.generated
     */
    public String updateByPrimaryKeySelective(LabelSubject record) {
        SQL sql = new SQL();
        sql.UPDATE("label_subject");
        
        if (record.getLabelid() != null) {
            sql.SET("labelid = #{labelid,jdbcType=VARCHAR}");
        }
        
        if (record.getSubjectid() != null) {
            sql.SET("subjectid = #{subjectid,jdbcType=VARCHAR}");
        }
        
        if (record.getSubject() != null) {
            sql.SET("subject = #{subject,jdbcType=VARCHAR}");
        }
        
        sql.WHERE("id = #{id,jdbcType=BIGINT}");
        
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table label_subject
     *
     * @mbg.generated
     */
    protected void applyWhere(SQL sql, LabelSubjectExample example, boolean includeExamplePhrase) {
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
            sql.WHERE(sb.toString());
        }
    }
}