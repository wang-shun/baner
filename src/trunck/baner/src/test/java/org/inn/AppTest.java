package org.inn;

/**
 * Unit test for simple App.
 */
public class AppTest {
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
    public static double GetDistance(double lon1,double lat1,double lon2, double lat2)
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

    public static void main(String[] args) {
        System.out.println(GetDistance(116.3162322664543, 39.93319953210263, 116.3160336940545, 39.93309839035292));
    }
}
