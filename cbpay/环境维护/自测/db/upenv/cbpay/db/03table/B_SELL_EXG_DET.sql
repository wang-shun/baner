--B_SELL_EXG_DET  建表语句
begin
	--建表语句
	execute immediate'
	create table B_SELL_EXG_DET
	(
	  paybatno         VARCHAR2(32) not null,
	  paydate          VARCHAR2(8) not null,
	  pay_seqno        VARCHAR2(32) not null,
	  merchantid       VARCHAR2(16) not null,
	  payerid          VARCHAR2(32) not null,
	  payeraccount     VARCHAR2(32) not null,
	  payername        VARCHAR2(128) not null,
	  remit_ccy        CHAR(3) not null,
	  remit_amt        NUMBER(17,2) not null,
	  orderid          VARCHAR2(32) not null,
	  payeename        VARCHAR2(128),
	  payeecountrycode CHAR(3),
	  pay_type         CHAR(1),
	  tran_code        VARCHAR2(6),
	  tran_desc        VARCHAR2(50),
	  is_ref           CHAR(1),
	  contract_no      VARCHAR2(20),
	  invoice_no       VARCHAR2(35),
	  applicant        VARCHAR2(20),
	  applicant_tel    VARCHAR2(20),
	  stamp            TIMESTAMP(6)
	)
	tablespace CBPAY_JOURNAL
	PARTITION BY RANGE (paydate)
	(
		PARTITION B_SELL_EXG_DET_'||to_char(sysdate+1*40,'YYMMDD')||' VALUES LESS THAN ('||to_char(sysdate+1*40,'YYYYMMDD')||') tablespace CBPAY_JOURNAL,
		PARTITION B_SELL_EXG_DET_'||to_char(sysdate+2*40,'YYMMDD')||' VALUES LESS THAN ('||to_char(sysdate+2*40,'YYYYMMDD')||') tablespace CBPAY_JOURNAL,
		PARTITION B_SELL_EXG_DET_'||to_char(sysdate+3*40,'YYMMDD')||' VALUES LESS THAN ('||to_char(sysdate+3*40,'YYYYMMDD')||') tablespace CBPAY_JOURNAL,
		PARTITION B_SELL_EXG_DET_'||to_char(sysdate+4*40,'YYMMDD')||' VALUES LESS THAN ('||to_char(sysdate+4*40,'YYYYMMDD')||') tablespace CBPAY_JOURNAL,
		PARTITION B_SELL_EXG_DET_'||to_char(sysdate+5*40,'YYMMDD')||' VALUES LESS THAN ('||to_char(sysdate+5*40,'YYYYMMDD')||') tablespace CBPAY_JOURNAL,
		PARTITION B_SELL_EXG_DET_'||to_char(sysdate+6*40,'YYMMDD')||' VALUES LESS THAN ('||to_char(sysdate+6*40,'YYYYMMDD')||') tablespace CBPAY_JOURNAL,
		PARTITION B_SELL_EXG_DET_'||to_char(sysdate+7*40,'YYMMDD')||' VALUES LESS THAN ('||to_char(sysdate+7*40,'YYYYMMDD')||') tablespace CBPAY_JOURNAL,
		PARTITION B_SELL_EXG_DET_'||to_char(sysdate+8*40,'YYMMDD')||' VALUES LESS THAN ('||to_char(sysdate+8*40,'YYYYMMDD')||') tablespace CBPAY_JOURNAL,
		PARTITION B_SELL_EXG_DET_'||to_char(sysdate+9*40,'YYMMDD')||' VALUES LESS THAN ('||to_char(sysdate+9*40,'YYYYMMDD')||') tablespace CBPAY_JOURNAL,
		PARTITION B_SELL_EXG_DET_'||to_char(sysdate+10*40,'YYMMDD')||' VALUES LESS THAN ('||to_char(sysdate+10*40,'YYYYMMDD')||') tablespace CBPAY_JOURNAL,
		PARTITION B_SELL_EXG_DET_MAX VALUES LESS THAN (MAXVALUE) tablespace CBPAY_JOURNAL
	)';
	--注释语句
	execute immediate 'comment on column B_SELL_EXG_DET.paybatno is ''付汇批次号''';
	execute immediate 'comment on column B_SELL_EXG_DET.paydate  is ''付汇日期''';
	execute immediate 'comment on column B_SELL_EXG_DET.pay_seqno is ''付汇唯一指令码''';
	execute immediate 'comment on column B_SELL_EXG_DET.merchantid is ''商户号''';
	execute immediate 'comment on column B_SELL_EXG_DET.payerid is ''付款客户的证件号''';
	execute immediate 'comment on column B_SELL_EXG_DET.payeraccount is ''付款客户账号''';
	execute immediate 'comment on column B_SELL_EXG_DET.payername is ''付款客户名称''';
	execute immediate 'comment on column B_SELL_EXG_DET.remit_ccy is ''汇款币种''';
	execute immediate 'comment on column B_SELL_EXG_DET.remit_amt is ''汇款金额''';
	execute immediate 'comment on column B_SELL_EXG_DET.orderid is ''订单号''';
	execute immediate 'comment on column B_SELL_EXG_DET.payeename is ''收款方商户名称''';
	execute immediate 'comment on column B_SELL_EXG_DET.payeecountrycode is ''收款方所在国家''';
	execute immediate 'comment on column B_SELL_EXG_DET.pay_type is ''付款类型   A－预付货款 P－货到付款 R－退款 O-其它''';
	execute immediate 'comment on column B_SELL_EXG_DET.tran_code is ''交易编码  必须在涉外收支交易代码中存在''';
	execute immediate 'comment on column B_SELL_EXG_DET.tran_desc is ''贸易项下为商品信息''';
	execute immediate 'comment on column B_SELL_EXG_DET.is_ref is ''是否保税区 Y－是 N－否''';
	execute immediate 'comment on column B_SELL_EXG_DET.contract_no is ''合同号''';
	execute immediate 'comment on column B_SELL_EXG_DET.invoice_no is ''发票号  国际收支管理信息''';
	execute immediate 'comment on column B_SELL_EXG_DET.applicant is ''第三方支付公司子客户的联系人''';
	execute immediate 'comment on column B_SELL_EXG_DET.applicant_tel is ''第三方支付公司子客户的联系电话''';
	execute immediate 'comment on column B_SELL_EXG_DET.stamp is ''时间戳''';
	
	--创建索引
	execute immediate '
		alter table B_SELL_EXG_DET add constraint B_SELL_EXG_DET_PK primary key (PAYBATNO, PAYDATE)
		using index 
		tablespace CBPAY_INDEX
	';
end;
/

