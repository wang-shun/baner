import com.msds.cbpay.util.Base64Util;


public class encodeBase64 {
	public static void main(String[] args) {
		String value = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
				+"<message>"
				+"	<head>"
				+"		<msgType>0001</msgType>"
				+"		<chanId>WEB</chanId>"
				+"		<merchantNo>merchant0013</merchantNo>"
				+"		<clientTime>21060317111522</clientTime>"
				+"		<serverTime></serverTime>"
				+"		<tranFlow></tranFlow>"
				+"		<tranCode>cbp001</tranCode>"
				+"		<clientIP>10.1.72.63</clientIP>"
				+"		<accdt></accdt>"
				+"		<respCode></respCode>"
				+"		<respMsg></respMsg>"
				+"	</head>"
				+"	<body>"
				+"			<pageReturnUrl>http://localhost:8080/MERCHANT/receives</pageReturnUrl>"
				+"			<offlineNotifyUrl>http://localhost:8080/MERCHANT/receive</offlineNotifyUrl>"
				+"			<purchaserId>purchaser00011</purchaserId>"
				+"			<orderId>10000001</orderId>"
				+"			<orderDate>21060317</orderDate>"
				+"			<orderTime>111522</orderTime>"
				+"			<orderDesc>qwdwsda</orderDesc>"
				+"			<tradeType>310</tradeType>"
				+"			<merchantName>aaa</merchantName>"
				+"			<totalAmount>100</totalAmount>"
				+"			<currency>CNY</currency>"
				+"			<validUnit>01</validUnit>"
				+"			<validNum>1</validNum>"
				+"			<tradeCode>12312</tradeCode>"
				+"			<isRef>Y</isRef>"
				+"			<backParam>a</backParam>"
				+"		  <productName>aa,ass,asa</productName>"
				+"			<productId>00001</productId>"
				+"			<productDesc>asas</productDesc>"
				+"			<showUrl>http://localhost:8080/MERCHANT/00001</showUrl>"
				+"	</body>"
				+"</message>";
		Base64Util.charset="GBK";
		System.out.println(Base64Util.encode(value));
	}
}
