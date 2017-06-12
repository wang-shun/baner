import java.lang.reflect.Constructor;


/**
 * @author zhagnxiaoyun:
 * @date 2016年6月30日 下午3:15:39
 */
public class Test {
	public static void main(String[] args) throws Exception {
		
		Class cl = Class.forName("SingleInstance");
		Constructor con = cl.getDeclaredConstructor(null);
		con.setAccessible(true);
		System.out.println(con.getName());
		SingleInstance instance = (SingleInstance) con.newInstance(null);
		instance.setName("www");
		System.out.println(SingleInstance.getInstance().getName());
		
//		Map<String,Object> map = new HashMap<String, Object>();
//		map.put("1", 1);
//		List<List> list = new ArrayList<List>();
//		List<List> sublist1 = new ArrayList<List>();
//		List<String> subsublist = new ArrayList<String>();
//		list.add(sublist1);
//		sublist1.add(subsublist);
//		
//		subsublist.add("33");
//		subsublist.add("哈哈");
//		
//		map.put("2", list);
//		Map<String,Object> map2 = new HashMap<String, Object>();
//		map2.putAll(map);
//		map.clear();
//		System.out.println(map2);
//		System.out.println(map.size());
		
	}
}
