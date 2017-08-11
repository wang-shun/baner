package org.inn.baner.serviceimp;


import com.ztkx.transplat.container.service.ServiceException;
import com.ztkx.transplat.container.service.intface.BusinessService;
import com.ztkx.transplat.container.util.ContextUtil;
import com.ztkx.transplat.platformutil.context.CommonContext;
import com.ztkx.transplat.platformutil.time.TimeUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.inn.baner.bean.Userloc;
import org.inn.baner.bean.UserlocDis;
import org.inn.baner.constant.Ban;
import org.inn.baner.constant.enums.BErrorCode;
import org.inn.baner.handler.data.UserLocData;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

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
            Userloc userloc = userLocData.getLastLoc(mobileno);
            List<Userloc> list = userLocData.qryByPlatDate(date);
            LinkedList<UserlocDis> disList = sortByDistance(userloc,list);

            List<Map<String, Object>> mapArrayList = new ArrayList<Map<String, Object>>();
            for (UserlocDis userlocDis : disList) {
                if(mobileno.equals(userlocDis.getMobileno())){
                    //跳过自身
                    continue;
                }
                Map<String, Object> map = new HashMap<>();
                map.put(Ban.mobileno, userlocDis.getMobileno());
                map.put(Ban.latitude, userlocDis.getLatitude());
                map.put(Ban.longitude, userlocDis.getLongitude());
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

    private LinkedList<UserlocDis> sortByDistance(Userloc userloc,List<Userloc> list) throws InvocationTargetException, IllegalAccessException {
        LinkedList<UserlocDis> disList = new LinkedList<>();
        for(Userloc tmp : list){
            double distance = GetDistance(Double.valueOf(userloc.getLongitude()),
                    Double.valueOf(userloc.getLatitude()),
                    Double.valueOf(tmp.getLongitude()),
                    Double.valueOf(tmp.getLatitude()));
            UserlocDis userlocDis = new UserlocDis();
            BeanUtils.copyProperties(userlocDis,tmp);
            userlocDis.setDistance(distance);
            if(disList.size()==0 || (disList.size()>0 && distance < disList.getFirst().getDistance())){
                //与最小的比较
                disList.addFirst(userlocDis);
            }else{
                //依次与原列表中距离比较，并按顺序插入
                int i=1;
                while(i<=disList.size()){
                    if(distance > disList.get(i).getDistance()){
                        disList.add(i,userlocDis);
                        break;
                    }else{
                        i++;
                    }
                }
                if(i == disList.size()){
                    disList.addLast(userlocDis);
                }
            }
        }

        return disList;
    }


    private static final  double EARTH_RADIUS = 6378.137;

    private static double rad(double d)
    {
        return d * Math.PI / 180.0;
    }

    //关于经纬度求距离 - cza55007 - NO.1.LY

    /**
     * 对上面的公式解释如下：
     1.Lat1 Lung1 表示A点经纬度，Lat2 Lung2 表示B点经纬度；
     2.a=Lat1 – Lat2 为两点纬度之差  b=Lung1 -Lung2 为两点经度之差；
     */
    private static double GetDistance(double lon1,double lat1,double lon2, double lat2)
    {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lon1) - rad(lon2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2)+Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2)));
        s = s * EARTH_RADIUS;
        //s = Math.round(s * 10000) / 10000;
        return s;
    }
}
