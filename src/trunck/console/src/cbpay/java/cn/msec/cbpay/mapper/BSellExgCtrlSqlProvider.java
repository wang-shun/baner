package cn.msec.cbpay.mapper;

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

import cn.msec.cbpay.entity.BSellExgCtrl;
import cn.msec.cbpay.entity.BSellExgCtrlExample;
import cn.msec.cbpay.entity.BSellExgCtrlExample.Criteria;
import cn.msec.cbpay.entity.BSellExgCtrlExample.Criterion;

public class BSellExgCtrlSqlProvider {

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table B_SELL_EXG_CTRL
     *
     * @mbggenerated Thu May 05 14:51:53 CST 2016
     */
    public String countByExample(BSellExgCtrlExample example) {
        BEGIN();
        SELECT("count(*)");
        FROM("B_SELL_EXG_CTRL");
        applyWhere(example, false);
        return SQL();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table B_SELL_EXG_CTRL
     *
     * @mbggenerated Thu May 05 14:51:53 CST 2016
     */
    public String deleteByExample(BSellExgCtrlExample example) {
        BEGIN();
        DELETE_FROM("B_SELL_EXG_CTRL");
        applyWhere(example, false);
        return SQL();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table B_SELL_EXG_CTRL
     *
     * @mbggenerated Thu May 05 14:51:53 CST 2016
     */
    public String insertSelective(BSellExgCtrl record) {
        BEGIN();
        INSERT_INTO("B_SELL_EXG_CTRL");
        
        if (record.getPaybatno() != null) {
            VALUES("PAYBATNO", "#{paybatno,jdbcType=VARCHAR}");
        }
        
        if (record.getPaydate() != null) {
            VALUES("PAYDATE", "#{paydate,jdbcType=CHAR}");
        }
        
        if (record.getPaytime() != null) {
            VALUES("PAYTIME", "#{paytime,jdbcType=CHAR}");
        }
        
        if (record.getQuotechnl() != null) {
            VALUES("QUOTECHNL", "#{quotechnl,jdbcType=VARCHAR}");
        }
        
        if (record.getRemitCcy() != null) {
            VALUES("REMIT_CCY", "#{remitCcy,jdbcType=CHAR}");
        }
        
        if (record.getRemitAmt() != null) {
            VALUES("REMIT_AMT", "#{remitAmt,jdbcType=DECIMAL}");
        }
        
        if (record.getCostType() != null) {
            VALUES("COST_TYPE", "#{costType,jdbcType=VARCHAR}");
        }
        
        if (record.getPayeeAcctNo() != null) {
            VALUES("PAYEE_ACCT_NO", "#{payeeAcctNo,jdbcType=VARCHAR}");
        }
        
        if (record.getPayeeClientName() != null) {
            VALUES("PAYEE_CLIENT_NAME", "#{payeeClientName,jdbcType=VARCHAR}");
        }
        
        if (record.getPayeeAddress() != null) {
            VALUES("PAYEE_ADDRESS", "#{payeeAddress,jdbcType=VARCHAR}");
        }
        
        if (record.getPayerAcctNo() != null) {
            VALUES("PAYER_ACCT_NO", "#{payerAcctNo,jdbcType=VARCHAR}");
        }
        
        if (record.getPayerClientName() != null) {
            VALUES("PAYER_CLIENT_NAME", "#{payerClientName,jdbcType=VARCHAR}");
        }
        
        if (record.getPayerAddress() != null) {
            VALUES("PAYER_ADDRESS", "#{payerAddress,jdbcType=VARCHAR}");
        }
        
        if (record.getPayeeAcctOpenBranchId() != null) {
            VALUES("PAYEE_ACCT_OPEN_BRANCH_ID", "#{payeeAcctOpenBranchId,jdbcType=VARCHAR}");
        }
        
        if (record.getRemark() != null) {
            VALUES("REMARK", "#{remark,jdbcType=VARCHAR}");
        }
        
        if (record.getClearBankBic() != null) {
            VALUES("CLEAR_BANK_BIC", "#{clearBankBic,jdbcType=VARCHAR}");
        }
        
        if (record.getProcessMsg() != null) {
            VALUES("PROCESS_MSG", "#{processMsg,jdbcType=VARCHAR}");
        }
        
        if (record.getProcessStatus() != null) {
            VALUES("PROCESS_STATUS", "#{processStatus,jdbcType=CHAR}");
        }
        
        if (record.getFailReason() != null) {
            VALUES("FAIL_REASON", "#{failReason,jdbcType=VARCHAR}");
        }
        
        if (record.getChkstatus() != null) {
            VALUES("CHKSTATUS", "#{chkstatus,jdbcType=CHAR}");
        }
        
        
        if (record.getFilename() != null) {
            VALUES("FILENAME", "#{filename,jdbcType=VARCHAR}");
        }
        
        if (record.getBussStatus() != null) {
            VALUES("BUSS_STATUS", "#{bussStatus,jdbcType=CHAR}");
        }
        
        if (record.getTotNum() != null) {
            VALUES("TOT_NUM", "#{totNum,jdbcType=DECIMAL}");
        }
        
        return SQL();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table B_SELL_EXG_CTRL
     *
     * @mbggenerated Thu May 05 14:51:53 CST 2016
     */
    public String selectByExample(BSellExgCtrlExample example) {
        BEGIN();
        if (example != null && example.isDistinct()) {
            SELECT_DISTINCT("PAYBATNO");
        } else {
            SELECT("PAYBATNO");
        }
        SELECT("PAYDATE");
        SELECT("PAYTIME");
        SELECT("QUOTECHNL");
        SELECT("REMIT_CCY");
        SELECT("REMIT_AMT");
        SELECT("COST_TYPE");
        SELECT("PAYEE_ACCT_NO");
        SELECT("PAYEE_CLIENT_NAME");
        SELECT("PAYEE_ADDRESS");
        SELECT("PAYER_ACCT_NO");
        SELECT("PAYER_CLIENT_NAME");
        SELECT("PAYER_ADDRESS");
        SELECT("PAYEE_ACCT_OPEN_BRANCH_ID");
        SELECT("REMARK");
        SELECT("CLEAR_BANK_BIC");
        SELECT("PROCESS_MSG");
        SELECT("PROCESS_STATUS");
        SELECT("FAIL_REASON");
        SELECT("CHKSTATUS");
        SELECT("STAMP");
        SELECT("FILENAME");
        SELECT("BUSS_STATUS");
        SELECT("TOT_NUM");
        FROM("B_SELL_EXG_CTRL");
        applyWhere(example, false);
        
        if (example != null && example.getOrderByClause() != null) {
            ORDER_BY(example.getOrderByClause());
        }
        
        if(example != null){
            return SQL().concat(" limit "+example.getOffset()+","+example.getLimit());
        }
        return SQL();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table B_SELL_EXG_CTRL
     *
     * @mbggenerated Thu May 05 14:51:53 CST 2016
     */
    public String updateByExampleSelective(Map<String, Object> parameter) {
        BSellExgCtrl record = (BSellExgCtrl) parameter.get("record");
        BSellExgCtrlExample example = (BSellExgCtrlExample) parameter.get("example");
        
        BEGIN();
        UPDATE("B_SELL_EXG_CTRL");
        
        if (record.getPaybatno() != null) {
            SET("PAYBATNO = #{record.paybatno,jdbcType=VARCHAR}");
        }
        
        if (record.getPaydate() != null) {
            SET("PAYDATE = #{record.paydate,jdbcType=CHAR}");
        }
        
        if (record.getPaytime() != null) {
            SET("PAYTIME = #{record.paytime,jdbcType=CHAR}");
        }
        
        if (record.getQuotechnl() != null) {
            SET("QUOTECHNL = #{record.quotechnl,jdbcType=VARCHAR}");
        }
        
        if (record.getRemitCcy() != null) {
            SET("REMIT_CCY = #{record.remitCcy,jdbcType=CHAR}");
        }
        
        if (record.getRemitAmt() != null) {
            SET("REMIT_AMT = #{record.remitAmt,jdbcType=DECIMAL}");
        }
        
        if (record.getCostType() != null) {
            SET("COST_TYPE = #{record.costType,jdbcType=VARCHAR}");
        }
        
        if (record.getPayeeAcctNo() != null) {
            SET("PAYEE_ACCT_NO = #{record.payeeAcctNo,jdbcType=VARCHAR}");
        }
        
        if (record.getPayeeClientName() != null) {
            SET("PAYEE_CLIENT_NAME = #{record.payeeClientName,jdbcType=VARCHAR}");
        }
        
        if (record.getPayeeAddress() != null) {
            SET("PAYEE_ADDRESS = #{record.payeeAddress,jdbcType=VARCHAR}");
        }
        
        if (record.getPayerAcctNo() != null) {
            SET("PAYER_ACCT_NO = #{record.payerAcctNo,jdbcType=VARCHAR}");
        }
        
        if (record.getPayerClientName() != null) {
            SET("PAYER_CLIENT_NAME = #{record.payerClientName,jdbcType=VARCHAR}");
        }
        
        if (record.getPayerAddress() != null) {
            SET("PAYER_ADDRESS = #{record.payerAddress,jdbcType=VARCHAR}");
        }
        
        if (record.getPayeeAcctOpenBranchId() != null) {
            SET("PAYEE_ACCT_OPEN_BRANCH_ID = #{record.payeeAcctOpenBranchId,jdbcType=VARCHAR}");
        }
        
        if (record.getRemark() != null) {
            SET("REMARK = #{record.remark,jdbcType=VARCHAR}");
        }
        
        if (record.getClearBankBic() != null) {
            SET("CLEAR_BANK_BIC = #{record.clearBankBic,jdbcType=VARCHAR}");
        }
        
        if (record.getProcessMsg() != null) {
            SET("PROCESS_MSG = #{record.processMsg,jdbcType=VARCHAR}");
        }
        
        if (record.getProcessStatus() != null) {
            SET("PROCESS_STATUS = #{record.processStatus,jdbcType=CHAR}");
        }
        
        if (record.getFailReason() != null) {
            SET("FAIL_REASON = #{record.failReason,jdbcType=VARCHAR}");
        }
        
        if (record.getChkstatus() != null) {
            SET("CHKSTATUS = #{record.chkstatus,jdbcType=CHAR}");
        }
        
              
        if (record.getFilename() != null) {
            SET("FILENAME = #{record.filename,jdbcType=VARCHAR}");
        }
        
        if (record.getBussStatus() != null) {
            SET("BUSS_STATUS = #{record.bussStatus,jdbcType=CHAR}");
        }
        
        if (record.getTotNum() != null) {
            SET("TOT_NUM = #{record.totNum,jdbcType=DECIMAL}");
        }
        
        applyWhere(example, true);
        return SQL();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table B_SELL_EXG_CTRL
     *
     * @mbggenerated Thu May 05 14:51:53 CST 2016
     */
    public String updateByExample(Map<String, Object> parameter) {
        BEGIN();
        UPDATE("B_SELL_EXG_CTRL");
        
        SET("PAYBATNO = #{record.paybatno,jdbcType=VARCHAR}");
        SET("PAYDATE = #{record.paydate,jdbcType=CHAR}");
        SET("PAYTIME = #{record.paytime,jdbcType=CHAR}");
        SET("QUOTECHNL = #{record.quotechnl,jdbcType=VARCHAR}");
        SET("REMIT_CCY = #{record.remitCcy,jdbcType=CHAR}");
        SET("REMIT_AMT = #{record.remitAmt,jdbcType=DECIMAL}");
        SET("COST_TYPE = #{record.costType,jdbcType=VARCHAR}");
        SET("PAYEE_ACCT_NO = #{record.payeeAcctNo,jdbcType=VARCHAR}");
        SET("PAYEE_CLIENT_NAME = #{record.payeeClientName,jdbcType=VARCHAR}");
        SET("PAYEE_ADDRESS = #{record.payeeAddress,jdbcType=VARCHAR}");
        SET("PAYER_ACCT_NO = #{record.payerAcctNo,jdbcType=VARCHAR}");
        SET("PAYER_CLIENT_NAME = #{record.payerClientName,jdbcType=VARCHAR}");
        SET("PAYER_ADDRESS = #{record.payerAddress,jdbcType=VARCHAR}");
        SET("PAYEE_ACCT_OPEN_BRANCH_ID = #{record.payeeAcctOpenBranchId,jdbcType=VARCHAR}");
        SET("REMARK = #{record.remark,jdbcType=VARCHAR}");
        SET("CLEAR_BANK_BIC = #{record.clearBankBic,jdbcType=VARCHAR}");
        SET("PROCESS_MSG = #{record.processMsg,jdbcType=VARCHAR}");
        SET("PROCESS_STATUS = #{record.processStatus,jdbcType=CHAR}");
        SET("FAIL_REASON = #{record.failReason,jdbcType=VARCHAR}");
        SET("CHKSTATUS = #{record.chkstatus,jdbcType=CHAR}");
        SET("STAMP = #{record.stamp,jdbcType=TIMESTAMP}");
        SET("FILENAME = #{record.filename,jdbcType=VARCHAR}");
        SET("BUSS_STATUS = #{record.bussStatus,jdbcType=CHAR}");
        SET("TOT_NUM = #{record.totNum,jdbcType=DECIMAL}");
        
        BSellExgCtrlExample example = (BSellExgCtrlExample) parameter.get("example");
        applyWhere(example, true);
        return SQL();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table B_SELL_EXG_CTRL
     *
     * @mbggenerated Thu May 05 14:51:53 CST 2016
     */
    public String updateByPrimaryKeySelective(BSellExgCtrl record) {
        BEGIN();
        UPDATE("B_SELL_EXG_CTRL");
        
        if (record.getPaytime() != null) {
            SET("PAYTIME = #{paytime,jdbcType=CHAR}");
        }
        
        if (record.getQuotechnl() != null) {
            SET("QUOTECHNL = #{quotechnl,jdbcType=VARCHAR}");
        }
        
        if (record.getRemitCcy() != null) {
            SET("REMIT_CCY = #{remitCcy,jdbcType=CHAR}");
        }
        
        if (record.getRemitAmt() != null) {
            SET("REMIT_AMT = #{remitAmt,jdbcType=DECIMAL}");
        }
        
        if (record.getCostType() != null) {
            SET("COST_TYPE = #{costType,jdbcType=VARCHAR}");
        }
        
        if (record.getPayeeAcctNo() != null) {
            SET("PAYEE_ACCT_NO = #{payeeAcctNo,jdbcType=VARCHAR}");
        }
        
        if (record.getPayeeClientName() != null) {
            SET("PAYEE_CLIENT_NAME = #{payeeClientName,jdbcType=VARCHAR}");
        }
        
        if (record.getPayeeAddress() != null) {
            SET("PAYEE_ADDRESS = #{payeeAddress,jdbcType=VARCHAR}");
        }
        
        if (record.getPayerAcctNo() != null) {
            SET("PAYER_ACCT_NO = #{payerAcctNo,jdbcType=VARCHAR}");
        }
        
        if (record.getPayerClientName() != null) {
            SET("PAYER_CLIENT_NAME = #{payerClientName,jdbcType=VARCHAR}");
        }
        
        if (record.getPayerAddress() != null) {
            SET("PAYER_ADDRESS = #{payerAddress,jdbcType=VARCHAR}");
        }
        
        if (record.getPayeeAcctOpenBranchId() != null) {
            SET("PAYEE_ACCT_OPEN_BRANCH_ID = #{payeeAcctOpenBranchId,jdbcType=VARCHAR}");
        }
        
        if (record.getRemark() != null) {
            SET("REMARK = #{remark,jdbcType=VARCHAR}");
        }
        
        if (record.getClearBankBic() != null) {
            SET("CLEAR_BANK_BIC = #{clearBankBic,jdbcType=VARCHAR}");
        }
        
        if (record.getProcessMsg() != null) {
            SET("PROCESS_MSG = #{processMsg,jdbcType=VARCHAR}");
        }
        
        if (record.getProcessStatus() != null) {
            SET("PROCESS_STATUS = #{processStatus,jdbcType=CHAR}");
        }
        
        if (record.getFailReason() != null) {
            SET("FAIL_REASON = #{failReason,jdbcType=VARCHAR}");
        }
        
        if (record.getChkstatus() != null) {
            SET("CHKSTATUS = #{chkstatus,jdbcType=CHAR}");
        }
        
        if (record.getFilename() != null) {
            SET("FILENAME = #{filename,jdbcType=VARCHAR}");
        }
        
        if (record.getBussStatus() != null) {
            SET("BUSS_STATUS = #{bussStatus,jdbcType=CHAR}");
        }
        
        if (record.getTotNum() != null) {
            SET("TOT_NUM = #{totNum,jdbcType=DECIMAL}");
        }
        
        WHERE("PAYBATNO = #{paybatno,jdbcType=VARCHAR}");
        WHERE("PAYDATE = #{paydate,jdbcType=CHAR}");
        
        return SQL();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table B_SELL_EXG_CTRL
     *
     * @mbggenerated Thu May 05 14:51:53 CST 2016
     */
    protected void applyWhere(BSellExgCtrlExample example, boolean includeExamplePhrase) {
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