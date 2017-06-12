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

import cn.msec.cbpay.entity.BUserInfo;
import cn.msec.cbpay.entity.BUserInfoExample;
import cn.msec.cbpay.entity.BUserInfoKey;
import cn.msec.cbpay.mapper.BUserInfoMapper;
import cn.msec.cbpay.util.ConsoleConstant;
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
import com.msec.cbpay.message.exchange.Col001Res;
import com.msec.cbpay.message.exchange.Col002Req;
import com.msec.cbpay.message.exchange.Col002Res;
import com.ztkx.cbpay.container.frame.FlowNoGenerator;

@Slf4j
@Controller
@RequestMapping("/foreigexchange")
public class ForeigexchangeCtrl extends BasicCtrl {

	private static BUserInfoMapper bUserInfoMapper = (BUserInfoMapper) BeanFactory
			.getBean("bUserInfoMapper");

	private static DataService mysqlDataService = (DataService) BeanFactory
			.getBean("mysqlDataService");

	/**
	 * ajax单条数据插入 url:'http://ip/rest/buserinfo'
	 * data:'{"key1":"value1","key2":"value2",...}' type:’POST
	 */
	@RequestMapping(value = "", method = RequestMethod.POST)
	@ResponseBody
	public ReturnInfo insert(@RequestBody BUserInfo info, HttpServletRequest req) {
		try {
			bUserInfoMapper.insert(info);
			return ReturnInfo.Success;
		} catch (Exception e) {
			log.warn("BUserInfoCtrl insert error..", e);
			 // e.printStackTrace();
		}
		return ReturnInfo.Faild;
	}

	/**
	 * ajax精确查询请求 url:
	 * 'http://ip/app/buserinfo/?query=({"key1":"value1","key2":"value2",...})'
	 * dataType: 'json' type: 'get'
	 * 
	 * ajax无条件查询全部请�? * url: 'http://ip/app/buserinfo' dataType: 'json' type:
	 * 'get'
	 * 
	 * ajax模糊查询请求 url:
	 * 'http://ip/app/buserinfo/?query={"(colname)":{"$regex":"(colvalue)","$opt
	 * i o n s " : " i " } dataType: 'json' type: 'get'
	 * 
	 * ajax分页查询 请求 url:
	 * 'http://ip/app/buserinfo/?query=(空或{"key1":"value1","key2":"value2",...})
	 * & s k i p = " + beginRow + "&limit=" + rowNum' dataType:'json' type:'get'
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
			dc.setEntityClass(BUserInfo.class);
			dc.setKeyClass(BUserInfoKey.class);
			dc.setMapperClass(BUserInfoMapper.class);
			dc.setQmb(info);
			dc.setPageinfo(para);
			dc.setFmb(fmb);
			this.setTableName(dc);
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
			log.warn("BUserInfoCtrl get error..", e);
		}
		if (para.isPage()) {
			return new ListInfo<>(totalCount, list, para);
		} else {
			return list;
		}
	}

	/**
	 * ajax根据ID主键查询 请求 url: 'http://ip/app/buserinfo/(_id�?' dataType: 'json'
	 * type: 'get'
	 */
	@RequestMapping(value = "/{key:.*}", method = RequestMethod.GET)
	@ResponseBody
	public ListInfo<BUserInfo> get(@PathVariable String key,
			HttpServletRequest req) {
		int totalCount = 1;
		List<BUserInfo> list = null;
		try {
			BUserInfo akey = new BUserInfo();

			Field keyField = FieldUtils.allDeclaredField(BUserInfoKey.class)
					.get(0);

			if (keyField.getType().isInstance(1)) {
				FieldUtils
						.setObjectValue(akey, keyField, Integer.parseInt(key));
			} else if (keyField.getType().isInstance(1L)) {
				FieldUtils.setObjectValue(akey, keyField, Long.parseLong(key));
			} else {
				FieldUtils.setObjectValue(akey, keyField, key);
			}

			if (true && akey.getPurchaserid() == null
					&& akey.getMerchantid() == null) {
				list = new ArrayList<BUserInfo>();
			} else {
				BUserInfoExample example = new BUserInfoExample();
				example.createCriteria()
						.andPurchaseridEqualTo(akey.getPurchaserid())
						.andMerchantidEqualTo(akey.getMerchantid());
				list = bUserInfoMapper.selectByExample(example);
			}
			totalCount = list.size();
		} catch (Exception e) {
			log.warn("BUserInfoCtrl get by key error..", e);
		}
		return new ListInfo<>(totalCount, list, 0, 1);
	}

	/**
	 * ajax根据主键删除 url:'http://ip/app/buserinfo/(_id�?' type: 'DELETE' dataType:
	 * 'json'
	 */
	@RequestMapping(value = "/{key:.*}", method = RequestMethod.DELETE)
	@ResponseBody
	public ReturnInfo delete(@PathVariable String key, HttpServletRequest req) {
		try {
			BUserInfo akey = new BUserInfo();

			Field keyField = FieldUtils.allDeclaredField(BUserInfoKey.class)
					.get(0);

			if (keyField.getType().isInstance(1)) {
				FieldUtils
						.setObjectValue(akey, keyField, Integer.parseInt(key));
			} else if (keyField.getType().isInstance(1L)) {
				FieldUtils.setObjectValue(akey, keyField, Long.parseLong(key));
			} else {
				FieldUtils.setObjectValue(akey, keyField, key);
			}

			if (true && akey.getPurchaserid() != null
					&& akey.getMerchantid() != null) {
				bUserInfoMapper.deleteByPrimaryKey(akey);
				return ReturnInfo.Success;
			}
		} catch (Exception e) {
			log.warn("BUserInfoCtrl delete by key error..");
		}
		return ReturnInfo.Faild;
	}

	/**
	 * ajax根据主键单条修改 url:'http://ip/app/buserinfo/(_id�?'
	 * data:'{"key1":"value1","key2":"value2",...}' type:'PUT'
	 */
	@RequestMapping(value = "/{key:.*}", method = RequestMethod.PUT)
	@ResponseBody
	public ReturnInfo update(@PathVariable String key,
			@RequestBody BUserInfo info, HttpServletRequest req) {
		try {
			if (info != null) {
				BUserInfo akey = new BUserInfo();

				Field keyField = FieldUtils
						.allDeclaredField(BUserInfoKey.class).get(0);

				if (keyField.getType().isInstance(1)) {
					FieldUtils.setObjectValue(akey, keyField,
							Integer.parseInt(key));
				} else if (keyField.getType().isInstance(1L)) {
					FieldUtils.setObjectValue(akey, keyField,
							Long.parseLong(key));
				} else {
					FieldUtils.setObjectValue(akey, keyField, key);
				}

				BUserInfoExample example = new BUserInfoExample();
				example.createCriteria()
						.andPurchaseridEqualTo(akey.getPurchaserid())
						.andMerchantidEqualTo(akey.getMerchantid());
				bUserInfoMapper.updateByExampleSelective(info, example);
			}
			return ReturnInfo.Success;
		} catch (Exception e) {
			log.warn("BUserInfo update by key error..");
		}
		return ReturnInfo.Faild;
	}

	private void setTableName(DbCondi dc) {
		String tName = DBBean.getTableName2Class(BUserInfo.class);
		if (dc.getOther() == null) {
			Map<String, Object> o = new HashMap<String, Object>();
			o.put(SqlMaker.TABLE_NAME, tName);
			dc.setOther(o);
		} else {
			dc.getOther().put(SqlMaker.TABLE_NAME, tName);
		}
	}

	@SuppressWarnings("serial")
	public static class BUserInfos extends ArrayList<BUserInfo> {
		public BUserInfos() {
			super();
		}
	}

	/**
	 * 接收发起付汇请求，并返回具体付汇信息（付汇金额，条数等）
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/startpay", method = RequestMethod.POST)
	@ResponseBody
	public ReturnInfo startPay(HttpServletRequest req) {
		// 返回信息实体类
		ReturnInfo returnInfo = new ReturnInfo();
		try {
			String merchantId = req.getParameter("merchantId");
			String startdate = req.getParameter("startdate");
			String enddate = req.getParameter("enddate");
			String REMARK = req.getParameter("REMARK");
			if (log.isDebugEnabled()) {
				log.debug("merchantId = [" + merchantId + "] , startdate =["
						+ startdate + "] , enddate =[" + enddate + "],REMARK=["
						+ REMARK + "]");
			}
			// 向discenter发送请求
			TCPSender sender = TCPSender.getInstance();
			String tranCode = "col001";
			// 开始拼接报文
			Col001Req col001req = new Col001Req();
			Head head = new Head();
			head.setTranCode(tranCode);
			String flowno = FlowNoGenerator.instance.getFlowNo();
			head.setFlowno(flowno);
			Col001Req.Body body = new Col001Req.Body();
			col001req.setHead(head);
			col001req.setBody(body);
			body.setEnddate(enddate);
			body.setMerchantid(merchantId);
			body.setREMARK(REMARK);
			body.setStartdate(startdate);

			// 发送请求，并将接收到的报文打印出来！
			byte[] reqMsg = sender.beanToXml(col001req, Col001Req.class);
			log.info("req msg is ["
					+ new String(reqMsg, ConsoleConstant.encoding) + "]");
			byte[] resMsg = sender.sendMsg(reqMsg);

			Col001Res col001Res = new Col001Res();
			log.info("res msg is ["
					+ new String(resMsg, ConsoleConstant.encoding) + "]");
			col001Res = (Col001Res) sender.xmlTobean(resMsg, Col001Res.class);

			String rescode = col001Res.getHead().getRespcode();
			String resmsg = col001Res.getHead().getRespmsg();
			log.debug("response code is [" + rescode + "] response msg is ["
					+ resmsg + "]");

			// 判断是否正确返回
			if (rescode.equals("CBP000")) {
				// 报文正常返回，将信息返回前台
				returnInfo.setSuccess(true);
				returnInfo.setDescription("success");
				returnInfo.setRetObj(col001Res.getBody());
				return returnInfo;
			} else {
				returnInfo.setSuccess(false);
				returnInfo.setDescription(resmsg);
				return returnInfo;
			}

			// 以下代码仅供测试
			// Col001Res.Body body = new Col001Res.Body();
			//
			// String filename = "20160330text.txt";
			// String filedate = "201603301";
			// String count = "10";
			// String totalamt = "200.33";
			// String currency = "RMB";
			// body.setStartdate(startdate);
			// body.setCount(count);
			// body.setCurrency_type(currency);
			// body.setEnddate(enddate);
			// body.setFileDate(filedate);
			// body.setFileName(filename);
			// body.setMerchantid(merchantId);
			// body.setTotalAmt(totalamt);
			// body.setTradeSn(REMARK);
			// ReturnInfo returnInfo = new ReturnInfo();
			// returnInfo.setSuccess(true);
			// returnInfo.setDescription("success");
			// returnInfo.setRetObj(body);
			// return returnInfo;

		} catch (Exception e) {
			log.error("startpay  error..", e);
			return ReturnInfo.Faild;
		}

	}

	/**
	 * 调用服务将付汇文件上传给银行
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/uploadfile", method = RequestMethod.POST)
	@ResponseBody
	public ReturnInfo uploadFile(HttpServletRequest req) {
		// 返回信息实体类
		ReturnInfo returnInfo = new ReturnInfo();
		try {
			String fileName = req.getParameter("fileName");
			String tradeSn = req.getParameter("tradeSn");
			String fileDate = req.getParameter("fileDate");
			if (log.isDebugEnabled()) {
				log.debug(" fileName = [" + fileName + "],tradeSn=[" + tradeSn
						+ "],fileDate=[" + fileDate + "]");
			}

			TCPSender sender = TCPSender.getInstance();
			String tranCode = "col002";
			// 开始拼接报文
			Col002Req col002req = new Col002Req();
			Head head = new Head();
			head.setTranCode(tranCode);
			String flowno = FlowNoGenerator.instance.getFlowNo();
			head.setFlowno(flowno);
			Col002Req.Body body = new Col002Req.Body();
			body.setFileDate(fileDate);
			body.setFileName(fileName);
			body.setTradeSn(tradeSn);

			col002req.setHead(head);
			col002req.setBody(body);

			// 发送请求，并将接收到的报文打印出来！
			byte[] reqMsg = sender.beanToXml(col002req, Col002Req.class);
			log.info("req msg is ["
					+ new String(reqMsg, ConsoleConstant.encoding) + "]");
			byte[] resMsg = sender.sendMsg(reqMsg);

			Col002Res col002Res = new Col002Res();
			log.info("res msg is ["
					+ new String(resMsg, ConsoleConstant.encoding) + "]");
			col002Res = (Col002Res) sender.xmlTobean(resMsg, Col002Res.class);

			String rescode = col002Res.getHead().getRespcode();
			String resmsg = col002Res.getHead().getRespmsg();
			if (log.isDebugEnabled()) {
				log.debug("response code is [" + rescode
						+ "] response msg is [" + resmsg + "]");
			}
			// 判断是否正确返回
			if (rescode.equals("CBP000")) {
				// 交易成功，则返回前台成功状态
				return ReturnInfo.Success;
			} else {
				returnInfo.setSuccess(false);
				returnInfo.setDescription(resmsg);
				return returnInfo;
			}
		} catch (Exception e) {
			log.error("uploadfile error..", e);
			return ReturnInfo.Faild;
		}

	}

	/**
	 * 发起购汇请求，具体流程待定
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/startbuy", method = RequestMethod.POST)
	@ResponseBody
	public ReturnInfo startBuy(HttpServletRequest req) {

		return null;
	}
}