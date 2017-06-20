--B_ACCOUNT_TRANSFER_FLOW  建表语句
begin
	--建表语句
	execute immediate'
	create table B_ACCOUNT_TRANSFER_FLOW
	(
	  transferbatchno VARCHAR2(32) not null,
	  txndate         VARCHAR2(8) not null,
	  txntime         VARCHAR2(6) not null,
	  currency        VARCHAR2(3) not null,
	  txnamt          NUMBER(17,2) not null,
	  payaccountno    VARCHAR2(50) not null,
	  transferflg     VARCHAR2(1),
	  acttrafstatus   VARCHAR2(2),
	  paytxndate      VARCHAR2(8),
	  paytxntime      VARCHAR2(6),
	  buybatno        VARCHAR2(32),
	  paybatno        VARCHAR2(32),
	  tmsmp           VARCHAR2(14),
	  checkingstatus  VARCHAR2(2),
	  orderid         VARCHAR2(64),
	  bak             VARCHAR2(250),
	  exrate          NUMBER(12,8),
	  recaccountno    VARCHAR2(50) not null,
	  transtype       VARCHAR2(2),
	  transferflowno  VARCHAR2(32) not null,
	  paycustomerno   VARCHAR2(50),
	  reccustomerno   VARCHAR2(50)
	)
	tablespace CBPAY_JOURNAL
	PARTITION BY RANGE (txndate)
	(
		PARTITION B_ACCOUNT_TRANSFER_FLOW_'||to_char(sysdate+1*40,'YYMMDD')||' VALUES LESS THAN ('||to_char(sysdate+1*40,'YYYYMMDD')||') tablespace CBPAY_JOURNAL,
		PARTITION B_ACCOUNT_TRANSFER_FLOW_'||to_char(sysdate+2*40,'YYMMDD')||' VALUES LESS THAN ('||to_char(sysdate+2*40,'YYYYMMDD')||') tablespace CBPAY_JOURNAL,
		PARTITION B_ACCOUNT_TRANSFER_FLOW_'||to_char(sysdate+3*40,'YYMMDD')||' VALUES LESS THAN ('||to_char(sysdate+3*40,'YYYYMMDD')||') tablespace CBPAY_JOURNAL,
		PARTITION B_ACCOUNT_TRANSFER_FLOW_'||to_char(sysdate+4*40,'YYMMDD')||' VALUES LESS THAN ('||to_char(sysdate+4*40,'YYYYMMDD')||') tablespace CBPAY_JOURNAL,
		PARTITION B_ACCOUNT_TRANSFER_FLOW_'||to_char(sysdate+5*40,'YYMMDD')||' VALUES LESS THAN ('||to_char(sysdate+5*40,'YYYYMMDD')||') tablespace CBPAY_JOURNAL,
		PARTITION B_ACCOUNT_TRANSFER_FLOW_'||to_char(sysdate+6*40,'YYMMDD')||' VALUES LESS THAN ('||to_char(sysdate+6*40,'YYYYMMDD')||') tablespace CBPAY_JOURNAL,
		PARTITION B_ACCOUNT_TRANSFER_FLOW_'||to_char(sysdate+7*40,'YYMMDD')||' VALUES LESS THAN ('||to_char(sysdate+7*40,'YYYYMMDD')||') tablespace CBPAY_JOURNAL,
		PARTITION B_ACCOUNT_TRANSFER_FLOW_'||to_char(sysdate+8*40,'YYMMDD')||' VALUES LESS THAN ('||to_char(sysdate+8*40,'YYYYMMDD')||') tablespace CBPAY_JOURNAL,
		PARTITION B_ACCOUNT_TRANSFER_FLOW_'||to_char(sysdate+9*40,'YYMMDD')||' VALUES LESS THAN ('||to_char(sysdate+9*40,'YYYYMMDD')||') tablespace CBPAY_JOURNAL,
		PARTITION B_ACCOUNT_TRANSFER_FLOW_'||to_char(sysdate+10*40,'YYMMDD')||' VALUES LESS THAN ('||to_char(sysdate+10*40,'YYYYMMDD')||') tablespace CBPAY_JOURNAL,
		PARTITION B_ACCOUNT_TRANSFER_FLOW_MAX VALUES LESS THAN (MAXVALUE) tablespace CBPAY_JOURNAL
	)';
	--注释语句
	execute immediate 'comment on table B_ACCOUNT_TRANSFER_FLOW is ''账户划转流水表''';
	execute immediate 'comment on column B_ACCOUNT_TRANSFER_FLOW.transferbatchno is ''账户划转批次号''';
	execute immediate 'comment on column B_ACCOUNT_TRANSFER_FLOW.txndate is ''平台日期''';
	execute immediate 'comment on column B_ACCOUNT_TRANSFER_FLOW.txntime is ''平台时间''';
	execute immediate 'comment on column B_ACCOUNT_TRANSFER_FLOW.currency is ''币种''';
	execute immediate 'comment on column B_ACCOUNT_TRANSFER_FLOW.txnamt is ''金额''';
	execute immediate 'comment on column B_ACCOUNT_TRANSFER_FLOW.payaccountno is ''付款账户号''';
	execute immediate 'comment on column B_ACCOUNT_TRANSFER_FLOW.transferflg is ''转入转出标志''';
	execute immediate 'comment on column B_ACCOUNT_TRANSFER_FLOW.acttrafstatus is ''账户划转状态,00,初始状态，01，进行中，02，划转成功，03 划转失败''';
	execute immediate 'comment on column B_ACCOUNT_TRANSFER_FLOW.paytxndate is ''宝易互通交易日期''';
	execute immediate 'comment on column B_ACCOUNT_TRANSFER_FLOW.paytxntime is ''宝易互通交易时间''';
	execute immediate 'comment on column B_ACCOUNT_TRANSFER_FLOW.buybatno is ''购汇批次号''';
	execute immediate 'comment on column B_ACCOUNT_TRANSFER_FLOW.paybatno is ''付汇批次号''';
	execute immediate 'comment on column B_ACCOUNT_TRANSFER_FLOW.tmsmp is ''时间戳''';
	execute immediate 'comment on column B_ACCOUNT_TRANSFER_FLOW.checkingstatus is ''对账状态 00，等待对账，01 ，正在对账，02 ，对账成功，03，对账失败''';
	execute immediate 'comment on column B_ACCOUNT_TRANSFER_FLOW.orderid is ''订单号''';
	execute immediate 'comment on column B_ACCOUNT_TRANSFER_FLOW.bak is ''备注''';
	execute immediate 'comment on column B_ACCOUNT_TRANSFER_FLOW.exrate is ''汇率''';
	execute immediate 'comment on column B_ACCOUNT_TRANSFER_FLOW.recaccountno is ''收款账号''';
	execute immediate 'comment on column B_ACCOUNT_TRANSFER_FLOW.transtype is ''划转类型，01，购汇前划转；02购汇后划转;03 付汇前划转''';
	execute immediate 'comment on column B_ACCOUNT_TRANSFER_FLOW.transferflowno is ''账户划转流水号''';
	execute immediate 'comment on column B_ACCOUNT_TRANSFER_FLOW.paycustomerno is ''付款客户号''';
	execute immediate 'comment on column B_ACCOUNT_TRANSFER_FLOW.reccustomerno is ''收款客户号''';

	--创建索引
	execute immediate '
		alter table B_ACCOUNT_TRANSFER_FLOW add constraint B_ACCOUNT_TRANSFER_FLOW_PK primary key (TRANSFERBATCHNO, TRANSFERFLOWNO)
		using index 
		tablespace CBPAY_INDEX
	';
end;
/
