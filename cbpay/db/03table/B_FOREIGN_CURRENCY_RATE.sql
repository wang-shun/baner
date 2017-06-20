--B_TRANSFER_FILE_INFO  建表语句
begin
	--建表语句
	execute immediate'
	create table B_FOREIGN_CURRENCY_RATE
	(
	  from_id              VARCHAR2(50) not null,
	  currency_code        VARCHAR2(508) not null,
	  recv_date            CHAR(8) not null,
	  recv_time            CHAR(6) not null,
	  transtime            VARCHAR2(6),
	  transdate            VARCHAR2(8),
	  cashbuyprice         NUMBER(12,6),
	  exbuyprice           NUMBER(12,6),
	  cashsellprice        NUMBER(12,6),
	  exsellprice          NUMBER(12,6),
	  exquotedate          CHAR(8),
	  exquotetime          VARCHAR2(20),
	  e3rdpayno            VARCHAR2(50),
	  price                NUMBER(12,8),
	  direction_flag       CHAR(1),
	  tran_amt             NUMBER(17,2),
	  client_exchange_rate NUMBER(12,8),
	  discount_type        CHAR(1),
	  dis_amt              NUMBER(17,8),
	  amt                  NUMBER(17,2)
	)
	tablespace CBPAY_JOURNAL
	PARTITION BY RANGE (exquotedate)
	(
		PARTITION B_FOREIGN_CURRENCY_RATE_'||to_char(sysdate+1*40,'YYMMDD')||' VALUES LESS THAN ('||to_char(sysdate+1*40,'YYYYMMDD')||') tablespace CBPAY_JOURNAL,
		PARTITION B_FOREIGN_CURRENCY_RATE_'||to_char(sysdate+2*40,'YYMMDD')||' VALUES LESS THAN ('||to_char(sysdate+2*40,'YYYYMMDD')||') tablespace CBPAY_JOURNAL,
		PARTITION B_FOREIGN_CURRENCY_RATE_'||to_char(sysdate+3*40,'YYMMDD')||' VALUES LESS THAN ('||to_char(sysdate+3*40,'YYYYMMDD')||') tablespace CBPAY_JOURNAL,
		PARTITION B_FOREIGN_CURRENCY_RATE_'||to_char(sysdate+4*40,'YYMMDD')||' VALUES LESS THAN ('||to_char(sysdate+4*40,'YYYYMMDD')||') tablespace CBPAY_JOURNAL,
		PARTITION B_FOREIGN_CURRENCY_RATE_'||to_char(sysdate+5*40,'YYMMDD')||' VALUES LESS THAN ('||to_char(sysdate+5*40,'YYYYMMDD')||') tablespace CBPAY_JOURNAL,
		PARTITION B_FOREIGN_CURRENCY_RATE_'||to_char(sysdate+6*40,'YYMMDD')||' VALUES LESS THAN ('||to_char(sysdate+6*40,'YYYYMMDD')||') tablespace CBPAY_JOURNAL,
		PARTITION B_FOREIGN_CURRENCY_RATE_'||to_char(sysdate+7*40,'YYMMDD')||' VALUES LESS THAN ('||to_char(sysdate+7*40,'YYYYMMDD')||') tablespace CBPAY_JOURNAL,
		PARTITION B_FOREIGN_CURRENCY_RATE_'||to_char(sysdate+8*40,'YYMMDD')||' VALUES LESS THAN ('||to_char(sysdate+8*40,'YYYYMMDD')||') tablespace CBPAY_JOURNAL,
		PARTITION B_FOREIGN_CURRENCY_RATE_'||to_char(sysdate+9*40,'YYMMDD')||' VALUES LESS THAN ('||to_char(sysdate+9*40,'YYYYMMDD')||') tablespace CBPAY_JOURNAL,
		PARTITION B_FOREIGN_CURRENCY_RATE_'||to_char(sysdate+10*40,'YYMMDD')||' VALUES LESS THAN ('||to_char(sysdate+10*40,'YYYYMMDD')||') tablespace CBPAY_JOURNAL,
		PARTITION B_FOREIGN_CURRENCY_RATE_MAX VALUES LESS THAN (MAXVALUE) tablespace CBPAY_JOURNAL
	)';
	--注释语句
	execute immediate 'comment on column B_FOREIGN_CURRENCY_RATE.from_id is ''来源id''';
	execute immediate 'comment on column B_FOREIGN_CURRENCY_RATE.currency_code is ''币种代码''';
	execute immediate 'comment on column B_FOREIGN_CURRENCY_RATE.recv_date is ''接收日期，yyyyMMdd''';
	execute immediate 'comment on column B_FOREIGN_CURRENCY_RATE.recv_time is ''接收时间，HHmmss''';
	execute immediate 'comment on column B_FOREIGN_CURRENCY_RATE.transtime is ''银行发起时间  HHMMSS''';
	execute immediate 'comment on column B_FOREIGN_CURRENCY_RATE.transdate is ''银行发起日期  YYYYMMDD''';
	execute immediate 'comment on column B_FOREIGN_CURRENCY_RATE.cashbuyprice is ''钞买价，以100为单位''';
	execute immediate 'comment on column B_FOREIGN_CURRENCY_RATE.exbuyprice is ''汇买价，以100为单位''';
	execute immediate 'comment on column B_FOREIGN_CURRENCY_RATE.cashsellprice is ''钞卖价，以100为单位''';
	execute immediate 'comment on column B_FOREIGN_CURRENCY_RATE.exsellprice is ''汇卖价，以100为单位''';
	execute immediate 'comment on column B_FOREIGN_CURRENCY_RATE.exquotedate is ''牌价日期''';
	execute immediate 'comment on column B_FOREIGN_CURRENCY_RATE.exquotetime is ''牌价时间''';
	execute immediate 'comment on column B_FOREIGN_CURRENCY_RATE.e3rdpayno is ''第三方支付号''';
	execute immediate 'comment on column B_FOREIGN_CURRENCY_RATE.price is ''市场价''';
	execute immediate 'comment on column B_FOREIGN_CURRENCY_RATE.direction_flag is ''汇率方向：取值为T和F 
T：表示汇率方向与货币对方向一致，即客户买入金额=客户卖出金额*汇率
F：表示汇率方向与货币对方向相反，即客户买入金额=客户卖出金额/汇率''';
	execute immediate 'comment on column B_FOREIGN_CURRENCY_RATE.tran_amt is ''按照市场价计算出来的另外一方金额''';
	execute immediate 'comment on column B_FOREIGN_CURRENCY_RATE.client_exchange_rate is ''客户成交汇率''';
	execute immediate 'comment on column B_FOREIGN_CURRENCY_RATE.discount_type is ''客户默认优惠方式 P：PIONT按点数优惠  S：SCALE按比例优惠 D：DISCOUNT折扣''';
	execute immediate 'comment on column B_FOREIGN_CURRENCY_RATE.dis_amt is ''客户默认优惠值   点数：多少点，比如50点，单位为点，显示为点 比例：偏离中间的比例，单位为万分之一，显示为%% 折扣：比如85折，单位为百分之一，显示为折''';
	execute immediate 'comment on column B_FOREIGN_CURRENCY_RATE.amt is ''市价金额''';

	--创建索引
	execute immediate '
		alter table B_FOREIGN_CURRENCY_RATE add constraint FOREIGN_CURRENCY_RATE_PK primary key (FROM_ID, RECV_DATE, RECV_TIME, CURRENCY_CODE)
		using index 
		tablespace CBPAY_INDEX
	';
end;
/
