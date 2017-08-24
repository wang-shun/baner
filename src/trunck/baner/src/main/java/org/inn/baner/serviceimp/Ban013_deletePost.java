package org.inn.baner.serviceimp;

import com.ztkx.transplat.container.service.ServiceException;
import com.ztkx.transplat.container.service.intface.BusinessService;
import com.ztkx.transplat.container.util.ContextUtil;
import com.ztkx.transplat.platformutil.context.CommonContext;

import org.apache.log4j.Logger;
import org.inn.baner.constant.Ban;
import org.inn.baner.constant.enums.BErrorCode;
import org.inn.baner.handler.data.PostData;

/**
 * Created by nightblue on 2017/8/9.
 */
public class Ban013_deletePost  implements BusinessService {

    private Logger logger = Logger.getLogger(Ban013_deletePost.class);

    @Override
    public CommonContext service(CommonContext context) throws ServiceException {

        String mobileno = context.getFieldStr(Ban.mobileno,CommonContext.SCOPE_GLOBAL);
        String topicId = context.getFieldStr(Ban.postid,CommonContext.SCOPE_GLOBAL);

        PostData postData = new PostData();

        try {
            postData.setContext(context);

            int res = postData.deletePost(mobileno,topicId);
            if(res>0){
                logger.info("post delete succ");
            }else{
                logger.info("post delete failed");
            }
        }catch (Exception e){
            ContextUtil.setErrorCode(BErrorCode.FAIL.code, context);
            logger.error("post delete exec exception ",e);
        }

        return context;
    }
}
