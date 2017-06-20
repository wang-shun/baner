--b_buy_exg_det  建表语句
begin
	--建表语句
	execute immediate'
	create table B_BUY_EXG_DET
	(
	  buybatno        VARCHAR2(32) not null,
	  tradeno         VARCHAR2(32) not null,
	  tran_amt        NUMBER(17,2),
	  client_no       VARCHAR2(20),
	  type            CHAR(1),
	  tran_code       CHAR(6),
	  payer_global_id VARCHAR2(32),
	  payer_name      VARCHAR2(128),
	  trade_type      CHAR(3),
	  country_code    CHAR(3),
	  buydate         CHAR(8)
	)
	tablespace CBPAY_JOURNAL
	PARTITION BY RANGE (buydate)
	(
		PARTITION B_BUY_EXG_DET_'||to_char(sysdate+1*40,'YYMMDD')||' VALUES LESS THAN ('||to_char(sysdate+1*40,'YYYYMMDD')||') tablespace CBPAY_JOURNAL,
		PARTITION B_BUY_EXG_DET_'||to_char(sysdate+2*40,'YYMMDD')||' VALUES LESS THAN ('||to_char(sysdate+2*40,'YYYYMMDD')||') tablespace CBPAY_JOURNAL,
		PARTITION B_BUY_EXG_DET_'||to_char(sysdate+3*40,'YYMMDD')||' VALUES LESS THAN ('||to_char(sysdate+3*40,'YYYYMMDD')||') tablespace CBPAY_JOURNAL,
		PARTITION B_BUY_EXG_DET_'||to_char(sysdate+4*40,'YYMMDD')||' VALUES LESS THAN ('||to_char(sysdate+4*40,'YYYYMMDD')||') tablespace CBPAY_JOURNAL,
		PARTITION B_BUY_EXG_DET_'||to_char(sysdate+5*40,'YYMMDD')||' VALUES LESS THAN ('||to_char(sysdate+5*40,'YYYYMMDD')||') tablespace CBPAY_JOURNAL,
		PARTITION B_BUY_EXG_DET_'||to_char(sysdate+6*40,'YYMMDD')||' VALUES LESS THAN ('||to_char(sysdate+6*40,'YYYYMMDD')||') tablespace CBPAY_JOURNAL,
		PARTITION B_BUY_EXG_DET_'||to_char(sysdate+7*40,'YYMMDD')||' VALUES LESS THAN ('||to_char(sysdate+7*40,'YYYYMMDD')||') tablespace CBPAY_JOURNAL,
		PARTITION B_BUY_EXG_DET_'||to_char(sysdate+8*40,'YYMMDD')||' VALUES LESS THAN ('||to_char(sysdate+8*40,'YYYYMMDD')||') tablespace CBPAY_JOURNAL,
		PARTITION B_BUY_EXG_DET_'||to_char(sysdate+9*40,'YYMMDD')||' VALUES LESS THAN ('||to_char(sysdate+9*40,'YYYYMMDD')||') tablespace CBPAY_JOURNAL,
		PARTITION B_BUY_EXG_DET_'||to_char(sysdate+10*40,'YYMMDD')||' VALUES LESS THAN ('||to_char(sysdate+10*40,'YYYYMMDD')||') tablespace CBPAY_JOURNAL,
		PARTITION B_BUY_EXG_DET_MAX VALUES LESS THAN (MAXVALUE) tablespace CBPAY_JOURNAL
	)';
	--注释语句
	execute immediate 'comment on column B_BUY_EXG_DET.buybatno is ''购汇批次号''';
	execute immediate 'comment on column B_BUY_EXG_DET.tradeno is ''支付系统流水号  交易编号，可以等同于订单号，后续由于业务查询，一笔业务对应一笔购汇登记，编号用于判重处理，在超时的时候，用同样的编号发送可以防止重复''';
	execute immediate 'comment on column B_BUY_EXG_DET.tran_amt is ''购汇金额  每笔订单的金额，明细汇总之后的金额等于总金额''';
	execute immediate 'comment on column B_BUY_EXG_DET.client_no is ''子客户号  证件号码''';
	execute immediate 'comment on column B_BUY_EXG_DET.type is ''汇款人类型  C－对公用户  D－对私''';
	execute immediate 'comment on column B_BUY_EXG_DET.tran_code is ''交易编码  必须在涉外收支交易代码中存在，用于账户内结售汇报送，对公必须输入''';
	execute immediate 'comment on column B_BUY_EXG_DET.payer_global_id is ''付款人证件号''';
	execute immediate 'comment on column B_BUY_EXG_DET.payer_name is ''付款人名称''';
	execute immediate 'comment on column B_BUY_EXG_DET.trade_type is ''购汇种类 对私：必输
		310货物贸易
		321运输
		3221自费出境学习
		3222因私旅游
		3223公务及商务出国
		3225旅游项下其他
		323金融和保险服务
		324专有权利使用费和特许费
		325咨询服务
		326其他服务''';
	execute immediate 'comment on column B_BUY_EXG_DET.country_code is ''汇往国家           对私：必输购汇后收汇人的国别代码''';
	execute immediate 'comment on column B_BUY_EXG_DET.buydate is ''购汇日期''';

	--创建索引
	execute immediate '
		alter table B_BUY_EXG_DET add constraint B_BUY_EXG_DET_PK primary key (BUYBATNO, TRADENO)
		using index 
		tablespace CBPAY_INDEX
	';
end;
/

