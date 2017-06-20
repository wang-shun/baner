--B_SELL_EXG_CTRL  建表语句
begin
	--建表语句
	execute immediate'
	create table B_SELL_EXG_CTRL
	(
	  paybatno                  VARCHAR2(32) not null,
	  paydate                   CHAR(8) not null,
	  paytime                   CHAR(6) not null,
	  quotechnl                 VARCHAR2(10) not null,
	  remit_ccy                 CHAR(3) not null,
	  remit_amt                 NUMBER(17,2) not null,
	  cost_type                 VARCHAR2(3) not null,
	  payee_acct_no             VARCHAR2(34) not null,
	  payee_client_name         VARCHAR2(120) not null,
	  payee_address             VARCHAR2(120) not null,
	  payer_acct_no             VARCHAR2(34) not null,
	  payer_client_name         VARCHAR2(120) not null,
	  payer_address             VARCHAR2(120) not null,
	  payee_acct_open_branch_id VARCHAR2(14) not null,
	  remark                    VARCHAR2(120) not null,
	  clear_bank_bic            VARCHAR2(14),
	  process_msg               VARCHAR2(14),
	  process_status            CHAR(1),
	  fail_reason               VARCHAR2(50),
	  chkstatus                 CHAR(1),
	  stamp                     TIMESTAMP(6) not null,
	  filename                  VARCHAR2(32),
	  buss_status               CHAR(1),
	  tot_num                   NUMBER(3)
	)
	tablespace CBPAY_JOURNAL
	PARTITION BY RANGE (paydate)
	(
		PARTITION B_SELL_EXG_CTRL_'||to_char(sysdate+1*40,'YYMMDD')||' VALUES LESS THAN ('||to_char(sysdate+1*40,'YYYYMMDD')||') tablespace CBPAY_JOURNAL,
		PARTITION B_SELL_EXG_CTRL_'||to_char(sysdate+2*40,'YYMMDD')||' VALUES LESS THAN ('||to_char(sysdate+2*40,'YYYYMMDD')||') tablespace CBPAY_JOURNAL,
		PARTITION B_SELL_EXG_CTRL_'||to_char(sysdate+3*40,'YYMMDD')||' VALUES LESS THAN ('||to_char(sysdate+3*40,'YYYYMMDD')||') tablespace CBPAY_JOURNAL,
		PARTITION B_SELL_EXG_CTRL_'||to_char(sysdate+4*40,'YYMMDD')||' VALUES LESS THAN ('||to_char(sysdate+4*40,'YYYYMMDD')||') tablespace CBPAY_JOURNAL,
		PARTITION B_SELL_EXG_CTRL_'||to_char(sysdate+5*40,'YYMMDD')||' VALUES LESS THAN ('||to_char(sysdate+5*40,'YYYYMMDD')||') tablespace CBPAY_JOURNAL,
		PARTITION B_SELL_EXG_CTRL_'||to_char(sysdate+6*40,'YYMMDD')||' VALUES LESS THAN ('||to_char(sysdate+6*40,'YYYYMMDD')||') tablespace CBPAY_JOURNAL,
		PARTITION B_SELL_EXG_CTRL_'||to_char(sysdate+7*40,'YYMMDD')||' VALUES LESS THAN ('||to_char(sysdate+7*40,'YYYYMMDD')||') tablespace CBPAY_JOURNAL,
		PARTITION B_SELL_EXG_CTRL_'||to_char(sysdate+8*40,'YYMMDD')||' VALUES LESS THAN ('||to_char(sysdate+8*40,'YYYYMMDD')||') tablespace CBPAY_JOURNAL,
		PARTITION B_SELL_EXG_CTRL_'||to_char(sysdate+9*40,'YYMMDD')||' VALUES LESS THAN ('||to_char(sysdate+9*40,'YYYYMMDD')||') tablespace CBPAY_JOURNAL,
		PARTITION B_SELL_EXG_CTRL_'||to_char(sysdate+10*40,'YYMMDD')||' VALUES LESS THAN ('||to_char(sysdate+10*40,'YYYYMMDD')||') tablespace CBPAY_JOURNAL,
		PARTITION B_SELL_EXG_CTRL_MAX VALUES LESS THAN (MAXVALUE) tablespace CBPAY_JOURNAL
	)';
	--注释语句
	execute immediate 'comment on column B_SELL_EXG_CTRL.paybatno is ''付汇批次号''';
	execute immediate 'comment on column B_SELL_EXG_CTRL.paydate is ''付汇日期''';
	execute immediate 'comment on column B_SELL_EXG_CTRL.paytime is ''付汇时间''';
	execute immediate 'comment on column B_SELL_EXG_CTRL.quotechnl is ''汇率渠道''';
	execute immediate 'comment on column B_SELL_EXG_CTRL.remit_ccy  is ''汇款币种''';
	execute immediate 'comment on column B_SELL_EXG_CTRL.remit_amt is ''汇款金额''';
	execute immediate 'comment on column B_SELL_EXG_CTRL.cost_type is  ''费用类型   
		OUR 为付款人全部承担（我行手续费+中间行费用，另行从扣费帐号收取收取），收款人可全额到账。 
		SHA  为共同承担，（付款人承担我行手续费，另行从扣费帐号收取收取，收款人承担中间行费用，到账金额减少）
		BEN收款人全部承担（我行手续费+中间行费用，全部从到账金额中扣减）在三方支付实践中，存在报送政策问题，三方支付业务暂不支持使用''
		';
	execute immediate 'comment on column B_SELL_EXG_CTRL.payee_acct_no is  ''收款人账号''';
	execute immediate 'comment on column B_SELL_EXG_CTRL.payee_client_name is  ''收款人名称''';
	execute immediate 'comment on column B_SELL_EXG_CTRL.payee_address is  ''收款人地址''';
	execute immediate 'comment on column B_SELL_EXG_CTRL.payer_acct_no is  ''付款人账号''';
	execute immediate 'comment on column B_SELL_EXG_CTRL.payer_client_name is  ''付款人名称''';
	execute immediate 'comment on column B_SELL_EXG_CTRL.payer_address is  ''付款人地址''';
	execute immediate 'comment on column B_SELL_EXG_CTRL.payee_acct_open_branch_id is  ''收款人开户行行号''';
	execute immediate 'comment on column B_SELL_EXG_CTRL.remark is  ''汇款附言''';
	execute immediate 'comment on column B_SELL_EXG_CTRL.clear_bank_bic is  ''跨境中间行''';
	execute immediate 'comment on column B_SELL_EXG_CTRL.process_msg is  ''处理消息''';
	execute immediate 'comment on column B_SELL_EXG_CTRL.process_status is  ''付汇状态''';
	execute immediate 'comment on column B_SELL_EXG_CTRL.fail_reason is  ''失败原因''';
	execute immediate 'comment on column B_SELL_EXG_CTRL.chkstatus is  ''对账状态  0：未对账 1：对账成功 2：支付系统多账 3：状态不符''';
	execute immediate 'comment on column B_SELL_EXG_CTRL.stamp is  ''更新时间戳''';
	execute immediate 'comment on column B_SELL_EXG_CTRL.filename is  ''付汇文件''';
	execute immediate 'comment on column B_SELL_EXG_CTRL.buss_status is  ''业务状态   1：银行接收处理成功 2：汇款成功 3：退汇  4：汇款失败 5：其他异常''';
	execute immediate 'comment on column B_SELL_EXG_CTRL.tot_num is  ''总笔数''';
	
	--创建索引
	execute immediate '
		alter table B_SELL_EXG_CTRL add constraint B_SELL_EXG_CTRL_PK primary key (PAYBATNO, PAYDATE)
		using index 
		tablespace CBPAY_INDEX
	';
end;
/
