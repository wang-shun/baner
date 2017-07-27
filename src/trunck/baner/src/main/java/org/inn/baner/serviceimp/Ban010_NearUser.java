package org.inn.baner.serviceimp;


import com.ztkx.transplat.container.service.ServiceException;
import com.ztkx.transplat.container.service.intface.BusinessService;
import com.ztkx.transplat.container.util.ContextUtil;
import com.ztkx.transplat.platformutil.context.CommonContext;
import com.ztkx.transplat.platformutil.time.TimeUtil;
import org.apache.log4j.Logger;
import org.inn.baner.bean.Userloc;
import org.inn.baner.constant.Ban;
import org.inn.baner.constant.enums.BErrorCode;
import org.inn.baner.handler.data.UserLocData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 查询附近的人
 *
 * @author zhangxiaoyun
 *         2016-3-15 16:24:11
 *         <p>Company:ztkx</p>
 */
public class Ban010_NearUser implements BusinessService {

    private Logger logger = Logger.getLogger(Ban010_NearUser.class);

    @Override
    public CommonContext service(CommonContext context) throws ServiceException {

        String mobileno = context.getFieldStr(Ban.mobileno, CommonContext.SCOPE_GLOBAL);
        String date = TimeUtil.getCurrentTime(null);
        logger.info("mobileno [" + mobileno + "]");
        logger.info("platdate [" + date + "]");
        UserLocData userLocData = null;
        try {
            userLocData = new UserLocData();
            List<Userloc> list = userLocData.qryByPlatDate(date);
            List<Map<String, Object>> mapArrayList = new ArrayList<Map<String, Object>>();
            for (Userloc userloc : list) {
                Map<String, Object> map = new HashMap<>();
                map.put(Ban.mobileno, userloc.getMobileno());
                map.put(Ban.latitude, userloc.getLatitude());
                map.put(Ban.longitude, userloc.getLongitude());
                mapArrayList.add(map);
            }
            context.setObj(Ban.lists, mapArrayList, CommonContext.SCOPE_GLOBAL);
            context.setFieldStr(Ban.size, String.valueOf(mapArrayList.size()), CommonContext.SCOPE_GLOBAL);
        } catch (Exception e) {
            ContextUtil.setErrorCode(BErrorCode.FAIL.code, context);
            logger.error("buss service exec exception ", e);
        }
        return context;
    }
}
