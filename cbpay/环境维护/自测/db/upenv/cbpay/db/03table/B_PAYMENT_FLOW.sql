--B_PAYMENT_FLOW  建表语句
begin
	--建表语句
	execute immediate'
	create table B_PAYMENT_FLOW
	(
	  payorderid     VARCHAR2(32) not null,
	  trandate       VARCHAR2(10),
	  trantime       VARCHAR2(10),
	  txnamt         NUMBER(17,2) not null,
	  currency       CHAR(3) not null,
	  merchantid     VARCHAR2(20),
	  orderid        VARCHAR2(30),
	  ordertime      VARCHAR2(20),
	  channel        VARCHAR2(10),
	  paytime        VARCHAR2(8),
	  paystatus      CHAR(2) not null,
	  purchaserid    VARCHAR2(64) not null,
	  checkingstatus CHAR(2) not null,
	  tmsmp          CHAR(14),
	  ordertdate     VARCHAR2(12),
	  paycard        VARCHAR2(30),
	  paydate        VARCHAR2(8)
	)
	tablespace CBPAY_JOURNAL
	PARTITION BY RANGE (trandate)
	(
		PARTITION B_PAYMENT_FLOW_'||to_char(sysdate+1*40,'YYMMDD')||' VALUES LESS THAN ('||to_char(sysdate+1*40,'YYYYMMDD')||') tablespace CBPAY_JOURNAL,
		PARTITION B_PAYMENT_FLOW_'||to_char(sysdate+2*40,'YYMMDD')||' VALUES LESS THAN ('||to_char(sysdate+2*40,'YYYYMMDD')||') tablespace CBPAY_JOURNAL,
		PARTITION B_PAYMENT_FLOW_'||to_char(sysdate+3*40,'YYMMDD')||' VALUES LESS THAN ('||to_char(sysdate+3*40,'YYYYMMDD')||') tablespace CBPAY_JOURNAL,
		PARTITION B_PAYMENT_FLOW_'||to_char(sysdate+4*40,'YYMMDD')||' VALUES LESS THAN ('||to_char(sysdate+4*40,'YYYYMMDD')||') tablespace CBPAY_JOURNAL,
		PARTITION B_PAYMENT_FLOW_'||to_char(sysdate+5*40,'YYMMDD')||' VALUES LESS THAN ('||to_char(sysdate+5*40,'YYYYMMDD')||') tablespace CBPAY_JOURNAL,
		PARTITION B_PAYMENT_FLOW_'||to_char(sysdate+6*40,'YYMMDD')||' VALUES LESS THAN ('||to_char(sysdate+6*40,'YYYYMMDD')||') tablespace CBPAY_JOURNAL,
		PARTITION B_PAYMENT_FLOW_'||to_char(sysdate+7*40,'YYMMDD')||' VALUES LESS THAN ('||to_char(sysdate+7*40,'YYYYMMDD')||') tablespace CBPAY_JOURNAL,
		PARTITION B_PAYMENT_FLOW_'||to_char(sysdate+8*40,'YYMMDD')||' VALUES LESS THAN ('||to_char(sysdate+8*40,'YYYYMMDD')||') tablespace CBPAY_JOURNAL,
		PARTITION B_PAYMENT_FLOW_'||to_char(sysdate+9*40,'YYMMDD')||' VALUES LESS THAN ('||to_char(sysdate+9*40,'YYYYMMDD')||') tablespace CBPAY_JOURNAL,
		PARTITION B_PAYMENT_FLOW_'||to_char(sysdate+10*40,'YYMMDD')||' VALUES LESS THAN ('||to_char(sysdate+10*40,'YYYYMMDD')||') tablespace CBPAY_JOURNAL,
		PARTITION B_PAYMENT_FLOW_MAX VALUES LESS THAN (MAXVALUE) tablespace CBPAY_JOURNAL
	)';
	--注释语句
	execute immediate 'comment on table B_PAYMENT_FLOW is ''订单支付流水表''';
	execute immediate 'comment on column B_PAYMENT_FLOW.payorderid is ''支付流水号''';
	execute immediate 'comment on column B_PAYMENT_FLOW.trandate is ''发起支付的平台日期''';
	execute immediate 'comment on column B_PAYMENT_FLOW.trantime is ''发起支付的平台时间''';
	execute immediate 'comment on column B_PAYMENT_FLOW.txnamt is ''支付金额''';
	execute immediate 'comment on column B_PAYMENT_FLOW.currency is ''支付币种''';
	execute immediate 'comment on column B_PAYMENT_FLOW.merchantid is ''商户号''';
	execute immediate 'comment on column B_PAYMENT_FLOW.orderid is ''商户订单号''';
	execute immediate 'comment on column B_PAYMENT_FLOW.ordertime is ''商户订单时间''';
	execute immediate 'comment on column B_PAYMENT_FLOW.channel is ''订单支付渠道''';
	execute immediate 'comment on column B_PAYMENT_FLOW.paytime is ''支付完成时间''';
	execute immediate 'comment on column B_PAYMENT_FLOW.paystatus is ''支付状态,0等待支付，1支付进行中，2支付完成，3，支付失败''';
	execute immediate 'comment on column B_PAYMENT_FLOW.purchaserid is ''购买者标识''';
	execute immediate 'comment on column B_PAYMENT_FLOW.checkingstatus is ''对账状态,0：等待对账，1：对账进行中，2：对账成功 ，3：对账失败''';
	execute immediate 'comment on column B_PAYMENT_FLOW.tmsmp is ''时间戳''';
	execute immediate 'comment on column B_PAYMENT_FLOW.ordertdate is ''商户订单日期''';
	execute immediate 'comment on column B_PAYMENT_FLOW.paycard is ''购买者付款卡号''';
	execute immediate 'comment on column B_PAYMENT_FLOW.paydate is ''支付完成日期''';
	

	--创建索引
	execute immediate '
		alter table B_PAYMENT_FLOW add constraint B_PAYMENT_FLOW_PK primary key (PAYORDERID)
		using index 
		tablespace CBPAY_INDEX
	';
end;
/
