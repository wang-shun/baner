


import java.io.UnsupportedEncodingException;

import org.apache.log4j.PropertyConfigurator;

import com.ztkx.cbpay.container.initdata.ConfXmlFormateData;
import com.ztkx.cbpay.container.preload.KeyMsgConfPreloader;
import com.ztkx.cbpay.container.preload.XmlFilePreloader;
import com.ztkx.cbpay.container.service.serviceimp.UnpackMsgBaseService;
import com.ztkx.cbpay.platformutil.baseconfig.BaseConfig;
import com.ztkx.cbpay.platformutil.baseconfig.ConstantConfigField;
import com.ztkx.cbpay.platformutil.context.CommonContext;
import com.ztkx.cbpay.platformutil.context.imp.CbpayContext;
import com.ztkx.cbpay.platformutil.db.c3p0.DataSourceUtil;

public class UnpackMsgBaseServiceTest {

	public static void main(String[] args) throws UnsupportedEncodingException {
		
		UnpackMsgBaseService service = new UnpackMsgBaseService();
		BaseConfig.getInstence("E:\\tmpfile\\CrossBorderPay\\config\\in_conf\\baseConf.properties");
		BaseConfig.setConfig(ConstantConfigField.HOMEDIR,"E:\\tmpfile\\CrossBorderPay");
		BaseConfig.setConfig(ConstantConfigField.CONFIGPATH, "E:\\tmpfile\\CrossBorderPay\\config\\in_conf");
		PropertyConfigurator.configure(BaseConfig.getConfig(ConstantConfigField.CONFIGPATH)+"\\log4j.properties");
		KeyMsgConfPreloader preload  = new KeyMsgConfPreloader();
		
		DataSourceUtil ds = DataSourceUtil.getInstance();
		
		
		ConfXmlFormateData data =ConfXmlFormateData.getInstance();
		data.getConnection();
		preload.load();
		
		XmlFilePreloader xmlpreload = new XmlFilePreloader();
		xmlpreload.load();
		
		CommonContext cc = new CbpayContext();
		cc.init(1);
		cc.getSDO().tranFrom="CBEC";
		cc.getSDO().TRANCODE="CEEXRATE";
		
		String dataValue = "<?xml version=\"1.0\" encoding=\"GBK\"?>"+
				"<stream>"+
				"<bankName>银行名称</bankName>"+
				"<e3rdPayNo>第三方支付机构号</e3rdPayNo>"+
				"<transTime>银行发起时间</transTime>"+
				"<exQuoteDate>牌价日期</exQuoteDate>"+
				"<exQuoteTime>牌价时间</exQuoteTime >"+
				"<exQuoteListNum>2</exQuoteListNum>"+
				"<list name=\"exQuoteList\">"+
					"<row>"+
						"<currencyID>币种</currencyID>"+
						"<cashBuyPrice>钞买价</cashBuyPrice>"+
						"<exBuyPrice>汇买价</exBuyPrice>"+
						"<cashSellPrice>钞卖价</cashSellPrice>"+
						"<exSellPrice>汇卖价</exSellPrice>"+
					"</row>"+
					"<row>"+
						"<currencyID>币种</currencyID>"+
						"<cashBuyPrice>钞买价</cashBuyPrice>"+
						"<exBuyPrice>汇买价</exBuyPrice>"+
						"<cashSellPrice>钞卖价</cashSellPrice>"+
						"<exSellPrice>汇卖价</exSellPrice>"+
					"</row>"+
				"</list>"+
				"</stream>";
		cc.setByteArray(ConstantConfigField.ORIGINAL_MSG, dataValue.getBytes("GBK"), CommonContext.SCOPE_GLOBAL);
		service.service(cc);
		System.out.println(cc);
	}

}
