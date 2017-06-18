import com.alibaba.fastjson.JSON;

import java.util.*;

/**
 * Created by zhangxiaoyun on 2017/6/15.
 */
public class Test {
    public static void main(String[] args) {
        /*Deque<String> queue = new LinkedList<>();

        queue.push("1");
        queue.push("2");
        queue.push("3");
        queue.push("4");

        while (queue.size() > 0) {
            System.out.println(queue.pop());
        }*/

//        String msg = "{\"data\":{\"head\":{\"channel\":\"AP\",\"merchantNo\":\"AAAAAAAAAAAA\",\"respCode\":\"C000000000\",\"respMsg\":\"交易成功\",\"serviceid\":\"ali_per_001\",\"tranCode\":\"QR0041\",\"tranDateTime\":\"20170614150626\",\"tranFlow\":\"AAAAAAAAAAAA201706143986748\",\"version\":\"1.0.0\"},\"body\":{\"data\":{\"lists\":[{\"deliverid\":\"20170613000010323\",\"delivername\":\"刘5\",\"discount\":\"\",\"enddate\":\"20170621\",\"ismsf\":\"0\",\"ismulti\":\"0\",\"logourl\":\"http://172.30.12.64:8085/group1/M00/00/04/rB4MP1jZ8IOAPsQyAAAXx3xOidk149.png\",\"maxoffsecmount\":\"\",\"merchantname\":\"小吊梨汤\",\"merchantno\":\"CF4000038168\",\"merchantrange\":\"02\",\"offsetmount\":\"100\",\"presentdesc\":\"\",\"startdate\":\"20170613\",\"state\":\"04\",\"type\":\"01\",\"usecondition\":\"无门槛\"},{\"deliverid\":\"20170614000010360\",\"delivername\":\"刘27\",\"discount\":\"\",\"enddate\":\"20170627\",\"ismsf\":\"0\",\"ismulti\":\"0\",\"logourl\":\"http://172.30.12.64:8085/group1/M00/00/04/rB4MP1jZ8IOAPsQyAAAXx3xOidk149.png\",\"maxoffsecmount\":\"\",\"merchantname\":\"小吊梨汤\",\"merchantno\":\"CF4000038168\",\"merchantrange\":\"02\",\"offsetmount\":\"\",\"presentdesc\":\"刘27\",\"startdate\":\"20170614\",\"state\":\"04\",\"type\":\"03\",\"usecondition\":\"无门槛\"},{\"deliverid\":\"20170612000010310\",\"delivername\":\"珍4\",\"discount\":\"\",\"enddate\":\"20170621\",\"ismsf\":\"0\",\"ismulti\":\"0\",\"logourl\":\"http://172.30.12.64:8085/group1/M00/00/04/rB4MP1jZ8IOAPsQyAAAXx3xOidk149.png\",\"maxoffsecmount\":\"\",\"merchantname\":\"小吊梨汤\",\"merchantno\":\"CF4000038168\",\"merchantrange\":\"02\",\"offsetmount\":\"\",\"presentdesc\":\"珍4\",\"startdate\":\"20170612\",\"state\":\"04\",\"type\":\"03\",\"usecondition\":\"无门槛\"}],\"totalnum\":3}}}}";
        Map<String, Object> map = new TreeMap<>();
        Map<String, Object> messageMap = new TreeMap<>();
        Map<String, Object> datamap = new TreeMap<>();
        messageMap.put("data", datamap);
        Map<String, Object> headMap = new TreeMap<>();
        headMap.put("channel", "ap");
        headMap.put("merchantNo", "");


        Map<String, Object> bodyMap = new TreeMap<>();
        Map<String, Object> bodydata = new TreeMap<>();
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> arr0 = new TreeMap<>();
        Map<String, Object> arr1 = new TreeMap<>();
        arr0.put("deliverid", "1432143214321");
        arr0.put("delivername", "哈哈哈");
        arr1.put("deliverid", "1432143214321_1");
        arr1.put("delivername", "哈哈哈_1");
        list.add(arr0);
        list.add(arr1);
        bodydata.put("lists", list);
        bodyMap.put("data", bodydata);

        datamap.put("head", headMap);
        datamap.put("body", bodyMap);
        map.put("message",messageMap);
        System.out.println(JSON.toJSONString(map));

        /*JSONObject obj = json.getJSONObject("data");
        obj = obj.getJSONObject("body").getJSONObject("data");
        JSONArray arr = obj.getJSONArray("lists");
        for (int i = 0; i < arr.size(); i++) {
            System.out.println(arr.getJSONObject(i));
        }*/
    }
}
