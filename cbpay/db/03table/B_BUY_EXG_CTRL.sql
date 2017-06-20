--B_BUY_EXG_CTRL  建表语句
begin
	--建表语句
	execute immediate'
	create table B_BUY_EXG_CTRL
	(
	  buybatno             VARCHAR2(32) not null,
	  buydate              CHAR(8) not null,
	  buytime              CHAR(6),
	  quotechnl            VARCHAR2(10),
	  becif                VARCHAR2(32),
	  tot_num              NUMBER(3),
	  sale_ccy             CHAR(3),
	  buy_ccy              CHAR(3),
	  buy_sell_flag        CHAR(1),
	  total_amt            NUMBER(17,2),
	  spot_flag            CHAR(1),
	  register_date        CHAR(8),
	  price                NUMBER(17,8),
	  client_exchange_rate NUMBER(17,8),
	  discount_type        CHAR(1),
	  dis_amt              NUMBER(17,8),
	  amt                  NUMBER(17,2),
	  tran_amt             NUMBER(17,2),
	  sell_acct_no         VARCHAR2(32),
	  buy_acct_no          VARCHAR2(32),
	  sale_amount          NUMBER(17,2),
	  buy_amount           NUMBER(17,2),
	  rate_time            VARCHAR2(14),
	  exceed_flag          CHAR(1),
	  process_status       VARCHAR2(2),
	  process_msg          VARCHAR2(50),
	  txn_sts              CHAR(2),
	  chk_sts              CHAR(1)
	)
	tablespace CBPAY_JOURNAL
	PARTITION BY RANGE (buydate)
	(
		PARTITION B_BUY_EXG_CTRL_'||to_char(sysdate+1*40,'YYMMDD')||' VALUES LESS THAN ('||to_char(sysdate+1*40,'YYYYMMDD')||') tablespace CBPAY_JOURNAL,
		PARTITION B_BUY_EXG_CTRL_'||to_char(sysdate+2*40,'YYMMDD')||' VALUES LESS THAN ('||to_char(sysdate+2*40,'YYYYMMDD')||') tablespace CBPAY_JOURNAL,
		PARTITION B_BUY_EXG_CTRL_'||to_char(sysdate+3*40,'YYMMDD')||' VALUES LESS THAN ('||to_char(sysdate+3*40,'YYYYMMDD')||') tablespace CBPAY_JOURNAL,
		PARTITION B_BUY_EXG_CTRL_'||to_char(sysdate+4*40,'YYMMDD')||' VALUES LESS THAN ('||to_char(sysdate+4*40,'YYYYMMDD')||') tablespace CBPAY_JOURNAL,
		PARTITION B_BUY_EXG_CTRL_'||to_char(sysdate+5*40,'YYMMDD')||' VALUES LESS THAN ('||to_char(sysdate+5*40,'YYYYMMDD')||') tablespace CBPAY_JOURNAL,
		PARTITION B_BUY_EXG_CTRL_'||to_char(sysdate+6*40,'YYMMDD')||' VALUES LESS THAN ('||to_char(sysdate+6*40,'YYYYMMDD')||') tablespace CBPAY_JOURNAL,
		PARTITION B_BUY_EXG_CTRL_'||to_char(sysdate+7*40,'YYMMDD')||' VALUES LESS THAN ('||to_char(sysdate+7*40,'YYYYMMDD')||') tablespace CBPAY_JOURNAL,
		PARTITION B_BUY_EXG_CTRL_'||to_char(sysdate+8*40,'YYMMDD')||' VALUES LESS THAN ('||to_char(sysdate+8*40,'YYYYMMDD')||') tablespace CBPAY_JOURNAL,
		PARTITION B_BUY_EXG_CTRL_'||to_char(sysdate+9*40,'YYMMDD')||' VALUES LESS THAN ('||to_char(sysdate+9*40,'YYYYMMDD')||') tablespace CBPAY_JOURNAL,
		PARTITION B_BUY_EXG_CTRL_'||to_char(sysdate+10*40,'YYMMDD')||' VALUES LESS THAN ('||to_char(sysdate+10*40,'YYYYMMDD')||') tablespace CBPAY_JOURNAL,
		PARTITION B_BUY_EXG_CTRL_MAX VALUES LESS THAN (MAXVALUE) tablespace CBPAY_JOURNAL
	)';
	--注释语句
	execute immediate 'comment on column B_BUY_EXG_CTRL.buybatno is ''购汇批次号''';
	execute immediate 'comment on column B_BUY_EXG_CTRL.buydate is ''购汇日期''';
	execute immediate 'comment on column B_BUY_EXG_CTRL.buytime is ''购汇时间''';
	execute immediate 'comment on column B_BUY_EXG_CTRL.quotechnl is ''汇率渠道''';
	execute immediate 'comment on column B_BUY_EXG_CTRL.becif is ''客户号''';
	execute immediate 'comment on column B_BUY_EXG_CTRL.tot_num is ''笔数''';
	execute immediate 'comment on column B_BUY_EXG_CTRL.sale_ccy is ''客户卖出币种       本币币种''';
	execute immediate 'comment on column B_BUY_EXG_CTRL.buy_ccy is ''客户买入币种       外币币种''';
	execute immediate 'comment on column B_BUY_EXG_CTRL.buy_sell_flag is ''购汇方式''';
	execute immediate 'comment on column B_BUY_EXG_CTRL.total_amt is ''总金额''';
	execute immediate 'comment on column B_BUY_EXG_CTRL.spot_flag is ''T+0交割标志''';
	execute immediate 'comment on column B_BUY_EXG_CTRL.register_date is ''银行登记日期       银行受理该笔业务后所属的业务日期''';
	execute immediate 'comment on column B_BUY_EXG_CTRL.price is ''市场价 该货币对的市场价''';
	execute immediate 'comment on column B_BUY_EXG_CTRL.client_exchange_rate is ''客户成交汇率       该客户如果有我行申请的特殊优惠，将是在市场价上优惠后的价格，如果没有优惠，该价格等于市场价''';
	execute immediate 'comment on column B_BUY_EXG_CTRL.discount_type is ''客户默认优惠方式   该客户在我行申请的默认的优惠方式：P：PIONT按点数优惠 S：SCALE按比例优惠 D：DISCOUNT折扣''';
	execute immediate 'comment on column B_BUY_EXG_CTRL.dis_amt is ''客户默认优惠值     该客户在我行申请的默认的优惠值:点数：多少点，比如50点，单位为点，显示为点 比例：偏离中间的比例，单位为万分之一，显示为%%折扣：比如85折，单位为百分之一，显示为折''';
	execute immediate 'comment on column B_BUY_EXG_CTRL.amt is ''市价金额           按照市场价计算出来的另外一方金额''';
	execute immediate 'comment on column B_BUY_EXG_CTRL.tran_amt is ''优惠后金额         按照客户优惠值计算出来的另外一方金额，如果客户没有优惠，等于市场价金额''';
	execute immediate 'comment on column B_BUY_EXG_CTRL.sell_acct_no is ''卖出账号''';
	execute immediate 'comment on column B_BUY_EXG_CTRL.buy_acct_no is ''买入账号''';
	execute immediate 'comment on column B_BUY_EXG_CTRL.sale_amount is ''卖出金额''';
	execute immediate 'comment on column B_BUY_EXG_CTRL.buy_amount is ''买入金额''';
	execute immediate 'comment on column B_BUY_EXG_CTRL.rate_time is ''汇率时间           YYYYMMDDHHMMSS：获取汇率时的日期和时间''';
	execute immediate 'comment on column B_BUY_EXG_CTRL.exceed_flag is ''结售汇额度 I：额度内 O：额度外''';
	execute immediate 'comment on column B_BUY_EXG_CTRL.process_status is ''处理状态 T：成功 F：失败''';
	execute immediate 'comment on column B_BUY_EXG_CTRL.process_msg is ''处理信息 成功信息或者错误消息''';
	execute immediate 'comment on column B_BUY_EXG_CTRL.txn_sts is ''交易状态''';
	execute immediate 'comment on column B_BUY_EXG_CTRL.chk_sts is ''对账标志  0 未对账  1 已对账''';
	
	--创建索引
	execute immediate '
		alter table B_BUY_EXG_CTRL add constraint B_BUY_EXG_CTRL_PK primary key (BUYBATNO, BUYDATE)
		using index 
		tablespace CBPAY_INDEX
	';
end;
/
