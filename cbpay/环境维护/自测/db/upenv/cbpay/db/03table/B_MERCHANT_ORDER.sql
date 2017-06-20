--B_MERCHANT_ORDER  建表语句
begin
	--建表语句
	execute immediate'
	create table B_MERCHANT_ORDER
	(
	  payjnlno           VARCHAR2(32),
	  paytime            CHAR(6),
	  merchantid         VARCHAR2(16) not null,
	  orderid            VARCHAR2(64) not null,
	  ordertime          CHAR(6),
	  clientip           VARCHAR2(15),
	  purchaserid        VARCHAR2(64),
	  validunit          varchar2(2),
	  validnum           varchar2(3),
	  orderdesc          VARCHAR2(128),
	  orderamount        NUMBER(17,2),
	  currency           VARCHAR2(10),
	  merchantpoundage   NUMBER(17,2),
	  purchaseamount     NUMBER(17,2),
	  channel            VARCHAR2(8),
	  acceptancerate     NUMBER(12,8),
	  acceptancemount    NUMBER(17,2),
	  acceptancycurrency VARCHAR2(10),
	  acceptancypoundage NUMBER(17,2),
	  orderstatus        VARCHAR2(2),
	  pagereturnurl      VARCHAR2(256),
	  offlinenotifyurl   VARCHAR2(256),
	  payorderid         VARCHAR2(32),
	  buybatno           VARCHAR2(32),
	  buybatstatus       CHAR(2),
	  buybatrate         NUMBER(12,8),
	  upordownmount      NUMBER(17,2),
	  paybatno           VARCHAR2(32),
	  paybatstatus       CHAR(2),
	  mountchangeno      VARCHAR2(32),
	  mountchangestatus  CHAR(2),
	  settlebatno        VARCHAR2(32),
	  settlebatstatus    CHAR(1),
	  userid             VARCHAR2(32),
	  orderdate          CHAR(8) not null,
	  trade_type         VARCHAR2(4),
	  buybatdate         CHAR(8),
	  paycard            VARCHAR2(32),
	  paydate            CHAR(8),
	  trade_code         VARCHAR2(6),
	  is_ref             CHAR(1),
	  paybatdate         CHAR(8),
	  productid          VARCHAR2(256),
	  productname        VARCHAR2(256),
	  productdesc        VARCHAR2(256),
	  showurl            VARCHAR2(400),
	  isresponse         CHAR(1),
	  rcvtime            VARCHAR2(6),
	  rcvdate            VARCHAR2(8),
	  tmsmp              VARCHAR2(14),
	  invoiceno          VARCHAR2(16)
	)
	tablespace CBPAY_JOURNAL
	PARTITION BY RANGE (orderdate)
	(
		PARTITION B_MERCHANT_ORDER_'||to_char(sysdate+1*40,'YYMMDD')||' VALUES LESS THAN ('||to_char(sysdate+1*40,'YYYYMMDD')||') tablespace CBPAY_JOURNAL,
		PARTITION B_MERCHANT_ORDER_'||to_char(sysdate+2*40,'YYMMDD')||' VALUES LESS THAN ('||to_char(sysdate+2*40,'YYYYMMDD')||') tablespace CBPAY_JOURNAL,
		PARTITION B_MERCHANT_ORDER_'||to_char(sysdate+3*40,'YYMMDD')||' VALUES LESS THAN ('||to_char(sysdate+3*40,'YYYYMMDD')||') tablespace CBPAY_JOURNAL,
		PARTITION B_MERCHANT_ORDER_'||to_char(sysdate+4*40,'YYMMDD')||' VALUES LESS THAN ('||to_char(sysdate+4*40,'YYYYMMDD')||') tablespace CBPAY_JOURNAL,
		PARTITION B_MERCHANT_ORDER_'||to_char(sysdate+5*40,'YYMMDD')||' VALUES LESS THAN ('||to_char(sysdate+5*40,'YYYYMMDD')||') tablespace CBPAY_JOURNAL,
		PARTITION B_MERCHANT_ORDER_'||to_char(sysdate+6*40,'YYMMDD')||' VALUES LESS THAN ('||to_char(sysdate+6*40,'YYYYMMDD')||') tablespace CBPAY_JOURNAL,
		PARTITION B_MERCHANT_ORDER_'||to_char(sysdate+7*40,'YYMMDD')||' VALUES LESS THAN ('||to_char(sysdate+7*40,'YYYYMMDD')||') tablespace CBPAY_JOURNAL,
		PARTITION B_MERCHANT_ORDER_'||to_char(sysdate+8*40,'YYMMDD')||' VALUES LESS THAN ('||to_char(sysdate+8*40,'YYYYMMDD')||') tablespace CBPAY_JOURNAL,
		PARTITION B_MERCHANT_ORDER_'||to_char(sysdate+9*40,'YYMMDD')||' VALUES LESS THAN ('||to_char(sysdate+9*40,'YYYYMMDD')||') tablespace CBPAY_JOURNAL,
		PARTITION B_MERCHANT_ORDER_'||to_char(sysdate+10*40,'YYMMDD')||' VALUES LESS THAN ('||to_char(sysdate+10*40,'YYYYMMDD')||') tablespace CBPAY_JOURNAL,
		PARTITION B_MERCHANT_ORDER_MAX VALUES LESS THAN (MAXVALUE) tablespace CBPAY_JOURNAL
	)';
	--注释语句
	execute immediate 'comment on column B_MERCHANT_ORDER.payjnlno is ''支付系统流水号''';
	execute immediate 'comment on column B_MERCHANT_ORDER.paytime is ''支付完成时间''';
	execute immediate 'comment on column B_MERCHANT_ORDER.merchantid is ''商户号''';
	execute immediate 'comment on column B_MERCHANT_ORDER.orderid is ''商户订单号''';
	execute immediate 'comment on column B_MERCHANT_ORDER.ordertime is ''商户订单时间''';
	execute immediate 'comment on column B_MERCHANT_ORDER.clientip is ''客户端地址''';
	execute immediate 'comment on column B_MERCHANT_ORDER.purchaserid is ''购买者标识''';
	execute immediate 'comment on column B_MERCHANT_ORDER.validunit is ''订单有效期单位''';
	execute immediate 'comment on column B_MERCHANT_ORDER.validnum is ''订单有效期数量''';
	execute immediate 'comment on column B_MERCHANT_ORDER.orderdesc is ''商户订单描述''';
	execute immediate 'comment on column B_MERCHANT_ORDER.orderamount is ''商户订单金额''';
	execute immediate 'comment on column B_MERCHANT_ORDER.currency is ''商户订单币种''';
	execute immediate 'comment on column B_MERCHANT_ORDER.merchantpoundage is ''商户手续费''';
	execute immediate 'comment on column B_MERCHANT_ORDER.purchaseamount is ''购汇金额''';
	execute immediate 'comment on column B_MERCHANT_ORDER.channel is ''汇率渠道''';
	execute immediate 'comment on column B_MERCHANT_ORDER.acceptancerate is ''收单汇率''';
	execute immediate 'comment on column B_MERCHANT_ORDER.acceptancemount is ''收单金额''';
	execute immediate 'comment on column B_MERCHANT_ORDER.acceptancycurrency is ''收单币种''';
	execute immediate 'comment on column B_MERCHANT_ORDER.acceptancypoundage is ''收单手续费 和后台''';
	execute immediate 'comment on column B_MERCHANT_ORDER.orderstatus is ''商户订单支付状态  00 等待付款    01 支付完成   02 订单关闭  03 订单过期  04 交易取消  05 订单退款 ''';
	execute immediate 'comment on column B_MERCHANT_ORDER.pagereturnurl is ''页面通知url''';
	execute immediate 'comment on column B_MERCHANT_ORDER.offlinenotifyurl is ''商户通知回调地址''';
	execute immediate 'comment on column B_MERCHANT_ORDER.payorderid is ''支付订单号''';
	execute immediate 'comment on column B_MERCHANT_ORDER.buybatno is ''购汇批次号''';
	execute immediate 'comment on column B_MERCHANT_ORDER.buybatstatus is ''购汇状态 00 未购汇  01 购汇登记中  02 购汇登记成功  03 购汇登记失败  04 待交割   05 购汇失败   06 购汇成功''';
	execute immediate 'comment on column B_MERCHANT_ORDER.buybatrate is ''购汇汇率''';
	execute immediate 'comment on column B_MERCHANT_ORDER.upordownmount is ''损益金额''';
	execute immediate 'comment on column B_MERCHANT_ORDER.paybatno is ''付汇批次号''';
	execute immediate 'comment on column B_MERCHANT_ORDER.paybatstatus is ''付汇状态 00 未付汇 01 生成付汇文件 02 付汇登记中 03 付汇登记成功 04 付汇登记失败 05 付汇待交割 06 付汇失败 07 付汇成功''';
	execute immediate 'comment on column B_MERCHANT_ORDER.mountchangeno is ''账户划转流水号''';
	execute immediate 'comment on column B_MERCHANT_ORDER.mountchangestatus is ''账户划转状态 00 未划转   01 购汇前账户划转开始  02 购汇前账户划转成功  03 购汇前账户划转失败  04 购汇后账户划转开始  05 购汇后账户划转成功  06 购汇后账户划转失败  07 付汇前账户划转开始  08 付汇前账户划转成功  09 付汇前账户划转失败''';
	execute immediate 'comment on column B_MERCHANT_ORDER.settlebatno is ''结算批次号''';
	execute immediate 'comment on column B_MERCHANT_ORDER.settlebatstatus is ''结算标志''';
	execute immediate 'comment on column B_MERCHANT_ORDER.userid is ''用户标识号''';
	execute immediate 'comment on column B_MERCHANT_ORDER.orderdate is ''订单日期''';
	execute immediate 'comment on column B_MERCHANT_ORDER.trade_type is ''购汇种类''';
	execute immediate 'comment on column B_MERCHANT_ORDER.buybatdate is ''购汇日期''';
	execute immediate 'comment on column B_MERCHANT_ORDER.paycard is ''付款人卡号''';
	execute immediate 'comment on column B_MERCHANT_ORDER.paydate is ''支付完成日期''';
	execute immediate 'comment on column B_MERCHANT_ORDER.trade_code is ''必须在涉外收支交易代码中存在''';
	execute immediate 'comment on column B_MERCHANT_ORDER.is_ref is ''是否保税货物项下付款  Y－是 N－否''';
	execute immediate 'comment on column B_MERCHANT_ORDER.paybatdate is ''付汇日期''';
	execute immediate 'comment on column B_MERCHANT_ORDER.productid is ''商品id''';
	execute immediate 'comment on column B_MERCHANT_ORDER.productname is ''商品名称''';
	execute immediate 'comment on column B_MERCHANT_ORDER.productdesc is ''商品描述''';
	execute immediate 'comment on column B_MERCHANT_ORDER.showurl is ''展示url''';
	execute immediate 'comment on column B_MERCHANT_ORDER.isresponse is ''是否推送''';
	execute immediate 'comment on column B_MERCHANT_ORDER.rcvtime is ''订单接收时间''';
	execute immediate 'comment on column B_MERCHANT_ORDER.rcvdate is ''订单接收日期''';
	execute immediate 'comment on column B_MERCHANT_ORDER.tmsmp is ''时间戳''';
	execute immediate 'comment on column B_MERCHANT_ORDER.invoiceno is ''发票号''';
	
	
	--创建索引
	execute immediate '
		alter table B_MERCHANT_ORDER add constraint B_MERCHANT_ORDER_PK primary key (MERCHANTID, ORDERID, ORDERDATE)
		using index 
		tablespace CBPAY_INDEX
	';
end;
/
