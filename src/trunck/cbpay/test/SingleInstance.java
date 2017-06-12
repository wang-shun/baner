/** 
 * @author  zhagnxiaoyun: 
 * @date 2016年6月30日 下午3:16:34 
 */
public class SingleInstance {

	private static SingleInstance instance= null;
	private String name;
	
	private  SingleInstance() {
		instance = this;
	}
	
	public static SingleInstance getInstance(){
		if(instance==null){
			instance = new SingleInstance();
		}
		return instance;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
