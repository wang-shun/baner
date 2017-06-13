package org.inn.baner.persist.mapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.jdbc.SQL;
import org.inn.baner.bean.Topic;
import org.inn.baner.bean.TopicExample.Criteria;
import org.inn.baner.bean.TopicExample.Criterion;
import org.inn.baner.bean.TopicExample;

public class TopicSqlProvider {

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table topic
     *
     * @mbg.generated
     */
    public String countByExample(TopicExample example) {
        SQL sql = new SQL();
        sql.SELECT("count(*)").FROM("topic");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table topic
     *
     * @mbg.generated
     */
    public String deleteByExample(TopicExample example) {
        SQL sql = new SQL();
        sql.DELETE_FROM("topic");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table topic
     *
     * @mbg.generated
     */
    public String insertSelective(Topic record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("topic");
        
        if (record.getTopicid() != null) {
            sql.VALUES("topicid", "#{topicid,jdbcType=INTEGER}");
        }
        
        if (record.getTopicdesc() != null) {
            sql.VALUES("topicdesc", "#{topicdesc,jdbcType=VARCHAR}");
        }
        
        if (record.getCreatormobileno() != null) {
            sql.VALUES("creatormobileno", "#{creatormobileno,jdbcType=VARCHAR}");
        }
        
        if (record.getTopiclogo() != null) {
            sql.VALUES("topiclogo", "#{topiclogo,jdbcType=LONGVARBINARY}");
        }
        
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table topic
     *
     * @mbg.generated
     */
    public String selectByExampleWithBLOBs(TopicExample example) {
        SQL sql = new SQL();
        if (example != null && example.isDistinct()) {
            sql.SELECT_DISTINCT("topicid");
        } else {
            sql.SELECT("topicid");
        }
        sql.SELECT("topicdesc");
        sql.SELECT("creatormobileno");
        sql.SELECT("topiclogo");
        sql.FROM("topic");
        applyWhere(sql, example, false);
        
        if (example != null && example.getOrderByClause() != null) {
            sql.ORDER_BY(example.getOrderByClause());
        }
        
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table topic
     *
     * @mbg.generated
     */
    public String selectByExample(TopicExample example) {
        SQL sql = new SQL();
        if (example != null && example.isDistinct()) {
            sql.SELECT_DISTINCT("topicid");
        } else {
            sql.SELECT("topicid");
        }
        sql.SELECT("topicdesc");
        sql.SELECT("creatormobileno");
        sql.FROM("topic");
        applyWhere(sql, example, false);
        
        if (example != null && example.getOrderByClause() != null) {
            sql.ORDER_BY(example.getOrderByClause());
        }
        
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table topic
     *
     * @mbg.generated
     */
    public String updateByExampleSelective(Map<String, Object> parameter) {
        Topic record = (Topic) parameter.get("record");
        TopicExample example = (TopicExample) parameter.get("example");
        
        SQL sql = new SQL();
        sql.UPDATE("topic");
        
        if (record.getTopicid() != null) {
            sql.SET("topicid = #{record.topicid,jdbcType=INTEGER}");
        }
        
        if (record.getTopicdesc() != null) {
            sql.SET("topicdesc = #{record.topicdesc,jdbcType=VARCHAR}");
        }
        
        if (record.getCreatormobileno() != null) {
            sql.SET("creatormobileno = #{record.creatormobileno,jdbcType=VARCHAR}");
        }
        
        if (record.getTopiclogo() != null) {
            sql.SET("topiclogo = #{record.topiclogo,jdbcType=LONGVARBINARY}");
        }
        
        applyWhere(sql, example, true);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table topic
     *
     * @mbg.generated
     */
    public String updateByExampleWithBLOBs(Map<String, Object> parameter) {
        SQL sql = new SQL();
        sql.UPDATE("topic");
        
        sql.SET("topicid = #{record.topicid,jdbcType=INTEGER}");
        sql.SET("topicdesc = #{record.topicdesc,jdbcType=VARCHAR}");
        sql.SET("creatormobileno = #{record.creatormobileno,jdbcType=VARCHAR}");
        sql.SET("topiclogo = #{record.topiclogo,jdbcType=LONGVARBINARY}");
        
        TopicExample example = (TopicExample) parameter.get("example");
        applyWhere(sql, example, true);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table topic
     *
     * @mbg.generated
     */
    public String updateByExample(Map<String, Object> parameter) {
        SQL sql = new SQL();
        sql.UPDATE("topic");
        
        sql.SET("topicid = #{record.topicid,jdbcType=INTEGER}");
        sql.SET("topicdesc = #{record.topicdesc,jdbcType=VARCHAR}");
        sql.SET("creatormobileno = #{record.creatormobileno,jdbcType=VARCHAR}");
        
        TopicExample example = (TopicExample) parameter.get("example");
        applyWhere(sql, example, true);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table topic
     *
     * @mbg.generated
     */
    protected void applyWhere(SQL sql, TopicExample example, boolean includeExamplePhrase) {
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