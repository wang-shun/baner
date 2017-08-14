package org.inn;

import com.ztkx.transplat.container.service.ServiceException;
import com.ztkx.transplat.platformutil.baseconfig.BaseConfig;
import com.ztkx.transplat.platformutil.baseconfig.ConstantConfigField;
import com.ztkx.transplat.platformutil.context.CommonContext;
import com.ztkx.transplat.platformutil.context.imp.CbpayContext;
import com.ztkx.transplat.platformutil.db.mybatis.MybatisUtil;
import com.ztkx.transplat.platformutil.enanddecrypt.Base64Util;
import org.inn.baner.serviceimp.*;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by nightblue on 2017/8/8.
 */
public class BanTest {
    CbpayContext cbpayContext;
    boolean test = false;

    @Before
    public void init(){
        BaseConfig.getInstence();
        MybatisUtil.getInstance();
        BaseConfig.setConfig(ConstantConfigField.ZKADDRESS,"172.30.2.102:2181");
        cbpayContext = new CbpayContext();
        cbpayContext.init();
    }


    @Test
    public void ban012(){
        cbpayContext.setObj("mobileno","15588888888",CommonContext.SCOPE_GLOBAL);
        cbpayContext.setObj("postid","000804",CommonContext.SCOPE_GLOBAL);
        Ban012_ObtainCommentByPost ban012Service = new Ban012_ObtainCommentByPost();
        try {
            ban012Service.service(cbpayContext);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void ban014(){
        cbpayContext.setObj("mobileno","15588888888",CommonContext.SCOPE_GLOBAL);
        Ban014_selectPost ban014Service = new Ban014_selectPost();
        try {
            ban014Service.service(cbpayContext);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void ban006(){
        if(test) {
            cbpayContext.setObj("mobileno", "15588888888", CommonContext.SCOPE_GLOBAL);
            cbpayContext.setObj("latitude", "39.93440159999999", CommonContext.SCOPE_GLOBAL);
            cbpayContext.setObj("longitude", "116.31404664", CommonContext.SCOPE_GLOBAL);
            Ban006_RegistAddress ban006Service = new Ban006_RegistAddress();
            try {
                ban006Service.service(cbpayContext);
            } catch (ServiceException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void ban010(){
        if(test) {
            cbpayContext.setObj("mobileno", "15588888888", CommonContext.SCOPE_GLOBAL);
            Ban010_NearUser ban010Service = new Ban010_NearUser();
            try {
                ban010Service.service(cbpayContext);
            } catch (ServiceException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void ban004(){
        if(test) {
            cbpayContext.setObj("topicid", "002", CommonContext.SCOPE_GLOBAL);
            Ban004_ObtainPostByTopic banService = new Ban004_ObtainPostByTopic();
            try {
                banService.service(cbpayContext);
            } catch (ServiceException e) {
                e.printStackTrace();
            }
        }
    }
    @Test
    public void ban008(){
        if(test) {
            cbpayContext.setObj("mobileno", "13141243080", CommonContext.SCOPE_GLOBAL);
            cbpayContext.setObj("topicid", "002", CommonContext.SCOPE_GLOBAL);
            cbpayContext.setObj("postname", "test", CommonContext.SCOPE_GLOBAL);
            cbpayContext.setObj("isAnon", "1", CommonContext.SCOPE_GLOBAL);
            cbpayContext.setObj("context", new String(Base64Util.encode("test".getBytes())), CommonContext.SCOPE_GLOBAL);
            Ban008_UploadPost banService = new Ban008_UploadPost();
            try {
                banService.service(cbpayContext);
            } catch (ServiceException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void ban009(){
        if(test) {
            cbpayContext.setObj("mobileno", "13141243080", CommonContext.SCOPE_GLOBAL);
            Ban009_QryFollowTopic banService = new Ban009_QryFollowTopic();
            try {
                banService.service(cbpayContext);
            } catch (ServiceException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void ban002(){
        if(test) {
            cbpayContext.setObj("mobileno", "13141243080", CommonContext.SCOPE_GLOBAL);
            cbpayContext.setObj("job", "附魔师", CommonContext.SCOPE_GLOBAL);
            Ban001_UserRegist banService = new Ban001_UserRegist();
            try {
                banService.service(cbpayContext);
            } catch (ServiceException e) {
                e.printStackTrace();
            }
        }
    }
}
