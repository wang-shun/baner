package org.inn;

import com.ztkx.transplat.container.service.ServiceException;
import com.ztkx.transplat.platformutil.baseconfig.BaseConfig;
import com.ztkx.transplat.platformutil.context.CommonContext;
import com.ztkx.transplat.platformutil.context.imp.CbpayContext;
import com.ztkx.transplat.platformutil.db.mybatis.MybatisUtil;
import org.inn.baner.serviceimp.Ban012_ObtainCommentByPost;
import org.junit.Test;

/**
 * Created by nightblue on 2017/8/8.
 */
public class BanTest {


    @Test
    public void ban012(){
        BaseConfig.getInstence();
        MybatisUtil.getInstance();
        CbpayContext cbpayContext = new CbpayContext();
        cbpayContext.init();
        cbpayContext.setObj("mobileno","15588888888",CommonContext.SCOPE_GLOBAL);
        cbpayContext.setObj("postid","000804",CommonContext.SCOPE_GLOBAL);
        Ban012_ObtainCommentByPost ban012Service = new Ban012_ObtainCommentByPost();

        try {
            ban012Service.service(cbpayContext);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }
}
