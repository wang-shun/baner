package org.inn.baner.constant.enums;

/**
 * Created by zhangxiaoyun on 2017/6/19.
 */
public enum BErrorCode {
    SUCC("PLA000000", "交易成功"),
    FAIL("PLA000001", "交易失败"),
    PWDERROR("PLA000002", "密码错误")
    
    ;

    public String code;
    public String desc;

    private BErrorCode(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
