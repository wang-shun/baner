package cn.msec.cbpay.action;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.msec.cbpay.entity.BMerchantInfo;
import cn.msec.cbpay.entity.BMerchantInfoExample;
import cn.msec.cbpay.entity.BMerchantInfoKey;
import cn.msec.cbpay.mapper.BMerchantInfoMapper;
import cn.msec.cbpay.util.TCPSender;
import cn.msec.rest.db.service.DataService;
import cn.msec.rest.utils.BeanFactory;
import cn.msec.rest.utils.DBBean;
import cn.msec.rest.utils.SerializerUtil;
import cn.msec.rest.web.action.BasicCtrl;
import cn.msec.rest.web.bean.DbCondi;
import cn.msec.rest.web.bean.FieldsMapperBean;
import cn.msec.rest.web.bean.ListInfo;
import cn.msec.rest.web.bean.PageInfo;
import cn.msec.rest.web.bean.QueryMapperBean;
import cn.msec.rest.web.bean.ReturnInfo;
import cn.msec.rest.web.bean.SqlMaker;
import cn.msec.rest.web.bind.FieldUtils;
import cn.msec.rest.web.bind.KeyExplainHandler;
import cn.msec.rest.web.bind.RequestJsonParam;

import com.msec.cbpay.message.Head;
import com.msec.cbpay.message.exchange.Col001Req;
import com.ztkx.cbpay.container.frame.FlowNoGenerator;

@Slf4j
@Controller
@RequestMapping("/bmerchantinfo")
public class BMerchantInfoCtrl extends BasicCtrl {

	private static BMerchantInfoMapper bMerchantInfoMapper = (BMerchantInfoMapper) BeanFactory
			.getBean("bMerchantInfoMapper");

	private static DataService mysqlDataService = (DataService) BeanFactory
			.getBean("mysqlDataService");

	// 设定和discenter交互时的编码格式
	private static String encoding = "GBK";

	/**
	 * ajax单条数据插入 url:'http://ip/rest/bmerchantinfo'
	 * data:'{"key1":"value1","key2":"value2",...}' type:’POST
	 */
	@RequestMapping(value = "", method = RequestMethod.POST)
	@ResponseBody
	public ReturnInfo insert(@RequestBody BMerchantInfo info,
			HttpServletRequest req) {
		try {
			bMerchantInfoMapper.insert(info);
			return ReturnInfo.Success;
		} catch (Exception e) {
			log.warn("BMerchantInfoCtrl insert error..", e);
			// e.printStackTrace();
		}
		return ReturnInfo.Faild;
	}

	/**
	 * ajax精确查询请求 url:
	 * 'http://ip/app/bmerchantinfo/?query=({"key1":"value1","key2":"value2",...
	 * } ) ' dataType: 'json' type: 'get'
	 * 
	 * ajax无条件查询全部请求 url: 'http://ip/app/bmerchantinfo' dataType: 'json' type:
	 * 'get'
	 * 
	 * ajax模糊查询请求 url:
	 * 'http://ip/app/bmerchantinfo/?query={"(colname)":{"$regex":"(colvalue)","$opt
	 * i o n s " : " i " } dataType: 'json' type: 'get'
	 * 
	 * ajax分页查询 请求 url:
	 * 'http://ip/app/bmerchantinfo/?query=(空或{"key1":"value1","key2":"value2",...
	 * } ) & s k i p = " + beginRow + "&limit=" + rowNum' dataType:'json'
	 * type:'get'
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "", method = RequestMethod.GET)
	@ResponseBody
	public Object get(
			@RequestJsonParam(value = "query", required = false) QueryMapperBean info,
			@RequestJsonParam(value = "fields", required = false) FieldsMapperBean fmb,
			PageInfo para, HttpServletRequest req) {
		int totalCount = 0;
		List<HashMap> list = null;
		try {
			DbCondi dc = new DbCondi();
			dc.setEntityClass(BMerchantInfo.class);
			dc.setKeyClass(BMerchantInfoKey.class);
			dc.setMapperClass(BMerchantInfoMapper.class);
			dc.setQmb(info);
			dc.setPageinfo(para);
			dc.setFmb(fmb);
			this.setTableName(dc);
			// TEST query
			// if(info!=null){
			// StringBuffer keyValues = new StringBuffer();
			// for(EqualBean b : info.getEqual()){
			// keyValues.append(b.getFieldName()).append(" = ").append(b.getValue()).append(",");
			// }
			// for(LikeBean b : info.getLikes()){
			// keyValues.append(b.getFieldName()).append(" like ").append(b.getRegex()).append(",");
			// }
			// for(OrBean b : info.getOr()){
			// keyValues.append(b.getFieldName()).append(" or ").append(b.getValue()).append(",");
			// }
			// for(ConditionBean b : info.getCondition()){
			// keyValues.append(b.getFieldName()).append(" ").append(b.getCondi())
			// .append(" ").append(b.getValue()).append(",");
			// }
			// log.debug("传递参数为="+keyValues.toString());
			// }
			// //////////////////
			String sql = SqlMaker.getReferenceCountSql(dc);
			totalCount = mysqlDataService.getCount(sql);
			sql = SqlMaker.getReferenceData(dc);
			list = SerializerUtil.deserializeArray(
					mysqlDataService.doBySQL(sql), HashMap.class);
			for (HashMap map : list) {
				for (Field filed : FieldUtils
						.allDeclaredField(dc.getKeyClass())) {
					if (map.get(filed.getName()) == null) {
						map.put(filed.getName(), "");
					}
				}
				map.put(KeyExplainHandler.ID_KEY,
						KeyExplainHandler.genKey(map, dc.getKeyClass()));
			}
		} catch (Exception e) {
			log.warn("BMerchantInfoCtrl get error..", e);
		}
		if (para.isPage()) {
			return new ListInfo<>(totalCount, list, para);
		} else {
			return list;
		}
	}

	/**
	 * ajax根据ID主键查询 请求 url: 'http://ip/app/bmerchantinfo/(_id值)' dataType:
	 * 'json' type: 'get'
	 */
	@RequestMapping(value = "/{key:.*}", method = RequestMethod.GET)
	@ResponseBody
	public ListInfo<BMerchantInfo> get(@PathVariable String key,HttpServletRequest req) {
		int totalCount = 1;
		List<BMerchantInfo> list = null;
		try {
			BMerchantInfo akey = new BMerchantInfo();

			Field keyField = FieldUtils
					.allDeclaredField(BMerchantInfoKey.class).get(0);

			if (keyField.getType().isInstance(1)) {
				FieldUtils
						.setObjectValue(akey, keyField, Integer.parseInt(key));
			} else if (keyField.getType().isInstance(1L)) {
				FieldUtils.setObjectValue(akey, keyField, Long.parseLong(key));
			} else {
				FieldUtils.setObjectValue(akey, keyField, key);
			}

			if (true && akey.getMerchantid() == null) {
				list = new ArrayList<BMerchantInfo>();
			} else {
				BMerchantInfoExample example = new BMerchantInfoExample();
				example.createCriteria().andMerchantidEqualTo(
						akey.getMerchantid());
				list = bMerchantInfoMapper.selectByExample(example);
			}
			totalCount = list.size();
		} catch (Exception e) {
			log.warn("BMerchantInfoCtrl get by key error..", e);
		}
		return new ListInfo<>(totalCount, list, 0, 1);
	}

	/**
	 * ajax根据主键删除 url:'http://ip/app/bmerchantinfo/(_id值)' type: 'DELETE'
	 * dataType: 'json'
	 */
	@RequestMapping(value = "/{key:.*}", method = RequestMethod.DELETE)
	@ResponseBody
	public ReturnInfo delete(@PathVariable String key, HttpServletRequest req) {
		try {
			BMerchantInfo akey = new BMerchantInfo();

			Field keyField = FieldUtils
					.allDeclaredField(BMerchantInfoKey.class).get(0);

			if (keyField.getType().isInstance(1)) {
				FieldUtils
						.setObjectValue(akey, keyField, Integer.parseInt(key));
			} else if (keyField.getType().isInstance(1L)) {
				FieldUtils.setObjectValue(akey, keyField, Long.parseLong(key));
			} else {
				FieldUtils.setObjectValue(akey, keyField, key);
			}

			if (true && akey.getMerchantid() != null) {
				bMerchantInfoMapper.deleteByPrimaryKey(akey);
				return ReturnInfo.Success;
			}
		} catch (Exception e) {
			log.warn("BMerchantInfoCtrl delete by key error..");
		}
		return ReturnInfo.Faild;
	}

	/**
	 * ajax根据主键单条修改 url:'http://ip/app/bmerchantinfo/(_id值)'
	 * data:'{"key1":"value1","key2":"value2",...}' type:'PUT'
	 */
	@RequestMapping(value = "/{key:.*}", method = RequestMethod.PUT)
	@ResponseBody
	public ReturnInfo update(@PathVariable String key,
			@RequestBody BMerchantInfo info, HttpServletRequest req) {
		try {
			if (info != null) {
				BMerchantInfo akey = new BMerchantInfo();

				Field keyField = FieldUtils.allDeclaredField(
						BMerchantInfoKey.class).get(0);

				if (keyField.getType().isInstance(1)) {
					FieldUtils.setObjectValue(akey, keyField,
							Integer.parseInt(key));
				} else if (keyField.getType().isInstance(1L)) {
					FieldUtils.setObjectValue(akey, keyField,
							Long.parseLong(key));
				} else {
					FieldUtils.setObjectValue(akey, keyField, key);
				}
				BMerchantInfoExample example = new BMerchantInfoExample();
				example.createCriteria().andMerchantidEqualTo(
						akey.getMerchantid());
				bMerchantInfoMapper.updateByExampleSelective(info, example);
			}
			return ReturnInfo.Success;
		} catch (Exception e) {
			log.warn("BMerchantInfo update by key error..");
		}
		return ReturnInfo.Faild;
	}

	private void setTableName(DbCondi dc) {
		String tName = DBBean.getTableName2Class(BMerchantInfo.class);
		if (dc.getOther() == null) {
			Map<String, Object> o = new HashMap<String, Object>();
			o.put(SqlMaker.TABLE_NAME, tName);
			dc.setOther(o);
		} else {
			dc.getOther().put(SqlMaker.TABLE_NAME, tName);
		}
	}

	@SuppressWarnings("serial")
	public static class BMerchantInfos extends ArrayList<BMerchantInfo> {
		public BMerchantInfos() {
			super();
		}
	}

	@RequestMapping(value = "/startpay", method = RequestMethod.POST)
	@ResponseBody
	public ReturnInfo startPay(HttpServletRequest req) {
		try {
			String merchertid = req.getParameter("merchertid");
			String startdate = req.getParameter("startdate");
			String enddate = req.getParameter("enddate");
			String REMARK = req.getParameter("REMARK");
			log.info("merchertid = [" + merchertid + "] , startdate =["
					+ startdate + "] , enddate =[" + enddate + "],REMARK=["
					+ REMARK + "]");

			// 向discenter发送请求
			TCPSender sender = TCPSender.getInstance();
			String tranCode = "col001";
			// 开始拼接报文
			Col001Req rechecking = new Col001Req();
			Head head = new Head();
			head.setTranCode(tranCode);
			String flowno = FlowNoGenerator.instance.getFlowNo();
			head.setFlowno(flowno);
			Col001Req.Body body = new Col001Req.Body();
			rechecking.setHead(head);
			rechecking.setBody(body);
			body.setEnddate(enddate);
			body.setMerchantid(merchertid);
			body.setREMARK(REMARK);
			body.setStartdate(startdate);

			// 发送请求，并将接收到的报文打印出来！
			byte[] reqMsg = sender.beanToXml(rechecking, Col001Req.class);
			log.info("req msg is [" + new String(reqMsg, encoding) + "]");
			byte[] resMsg = sender.sendMsg(reqMsg);
			log.info("res msg is [" + new String(resMsg, encoding) + "]");
			rechecking = (Col001Req) sender.xmlTobean(resMsg, Col001Req.class);

			String rescode = rechecking.getHead().getRespcode();
			String resmsg = rechecking.getHead().getRespmsg();
			log.debug("response code is [" + rescode + "] response msg is ["
					+ resmsg + "]");

			// 判断是否正确返回
			if (rescode.equals("CBP000")) {
				return ReturnInfo.Success;
			} else {
				return ReturnInfo.Faild;
			}

		} catch (Exception e) {
			log.error("BMerchantInfo update by key error..", e);
			return ReturnInfo.Faild;
		}
	}
}