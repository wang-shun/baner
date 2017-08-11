package org.inn;

import org.inn.baner.serviceimp.Ban012_ObtainCommentByPost;
import org.inn.baner.serviceimp.Ban014_selectPost;
import org.junit.Before;
import org.junit.Test;

import com.ztkx.transplat.container.service.ServiceException;
import com.ztkx.transplat.platformutil.baseconfig.BaseConfig;
import com.ztkx.transplat.platformutil.context.CommonContext;
import com.ztkx.transplat.platformutil.context.imp.CbpayContext;
import com.ztkx.transplat.platformutil.db.mybatis.MybatisUtil;

/**
 * Created by nightblue on 2017/8/8.
 */
public class BanTest {
    CbpayContext cbpayContext;

    @Before
    public void init(){
        BaseConfig.getInstence();
        MybatisUtil.getInstance();
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
}
