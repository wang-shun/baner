--B_TRANSFER_FILE_INFO  建表语句
begin
	--建表语句
	execute immediate'
	create table B_TRANSFER_FILE_INFO
	(
	  transfer_date CHAR(8) not null,
	  transfer_time CHAR(6) not null,
	  seqbatchno    VARCHAR2(20) not null,
	  transfer_type CHAR(1) not null,
	  filetype      CHAR(1) not null,
	  count         NUMBER(5) not null,
	  filename      VARCHAR2(100) not null,
	  randompwd     VARCHAR2(100),
	  hashvalue     VARCHAR2(200),
	  signvalue     VARCHAR2(4000),
	  filestatus    VARCHAR2(2)
	)
	tablespace CBPAY_JOURNAL
	PARTITION BY RANGE (transfer_date)
	(
		PARTITION B_TRANSFER_FILE_INFO_'||to_char(sysdate+1*40,'YYMMDD')||' VALUES LESS THAN ('||to_char(sysdate+1*40,'YYYYMMDD')||') tablespace CBPAY_JOURNAL,
		PARTITION B_TRANSFER_FILE_INFO_'||to_char(sysdate+2*40,'YYMMDD')||' VALUES LESS THAN ('||to_char(sysdate+2*40,'YYYYMMDD')||') tablespace CBPAY_JOURNAL,
		PARTITION B_TRANSFER_FILE_INFO_'||to_char(sysdate+3*40,'YYMMDD')||' VALUES LESS THAN ('||to_char(sysdate+3*40,'YYYYMMDD')||') tablespace CBPAY_JOURNAL,
		PARTITION B_TRANSFER_FILE_INFO_'||to_char(sysdate+4*40,'YYMMDD')||' VALUES LESS THAN ('||to_char(sysdate+4*40,'YYYYMMDD')||') tablespace CBPAY_JOURNAL,
		PARTITION B_TRANSFER_FILE_INFO_'||to_char(sysdate+5*40,'YYMMDD')||' VALUES LESS THAN ('||to_char(sysdate+5*40,'YYYYMMDD')||') tablespace CBPAY_JOURNAL,
		PARTITION B_TRANSFER_FILE_INFO_'||to_char(sysdate+6*40,'YYMMDD')||' VALUES LESS THAN ('||to_char(sysdate+6*40,'YYYYMMDD')||') tablespace CBPAY_JOURNAL,
		PARTITION B_TRANSFER_FILE_INFO_'||to_char(sysdate+7*40,'YYMMDD')||' VALUES LESS THAN ('||to_char(sysdate+7*40,'YYYYMMDD')||') tablespace CBPAY_JOURNAL,
		PARTITION B_TRANSFER_FILE_INFO_'||to_char(sysdate+8*40,'YYMMDD')||' VALUES LESS THAN ('||to_char(sysdate+8*40,'YYYYMMDD')||') tablespace CBPAY_JOURNAL,
		PARTITION B_TRANSFER_FILE_INFO_'||to_char(sysdate+9*40,'YYMMDD')||' VALUES LESS THAN ('||to_char(sysdate+9*40,'YYYYMMDD')||') tablespace CBPAY_JOURNAL,
		PARTITION B_TRANSFER_FILE_INFO_'||to_char(sysdate+10*40,'YYMMDD')||' VALUES LESS THAN ('||to_char(sysdate+10*40,'YYYYMMDD')||') tablespace CBPAY_JOURNAL,
		PARTITION B_TRANSFER_FILE_INFO_MAX VALUES LESS THAN (MAXVALUE) tablespace CBPAY_JOURNAL
	)';
	--注释语句
	execute immediate 'comment on column B_TRANSFER_FILE_INFO.transfer_type is ''传输类型   2 上传  1 下载''';
	execute immediate 'comment on column B_TRANSFER_FILE_INFO.filetype is ''1:客户信息登记维护上传(客户到银行)
	5: 付汇交易明细对照文件上传(客户到银行)
	6: 收汇交易明细对照文件上传(客户到银行)  2:黑名单文件通知下载
	3:购汇登记状态查询文件
	4:购汇交易明细对照文件
	7:结汇收汇交易明细对照文件
	8: 收汇到账文件主动通知(银行到客户)
	9: SWIFT CODE文件通知（银行到支付公司）
	C：费用清单
	D：BC类消息通知''';
	execute immediate 'comment on column B_TRANSFER_FILE_INFO.count is ''明细笔数''';
	execute immediate 'comment on column B_TRANSFER_FILE_INFO.filename  is ''文件名称''';
	execute immediate 'comment on column B_TRANSFER_FILE_INFO.randompwd  is ''密码''';
	execute immediate 'comment on column B_TRANSFER_FILE_INFO.hashvalue is ''文件摘要''';
	execute immediate 'comment on column B_TRANSFER_FILE_INFO.signvalue is ''签名值''';
	execute immediate 'comment on column B_TRANSFER_FILE_INFO.filestatus  is ''文件上传下载状态 1 文件创建  2 文件开始上传  3 文件上传成功  4 文件下载开始  5 文件下载完成''';
	
	--创建索引
	execute immediate '
		alter table B_TRANSFER_FILE_INFO add constraint B_TRANSFER_FILE_INFO_PK primary key (TRANSFER_DATE, FILENAME)
		using index 
		tablespace CBPAY_INDEX
	';
end;
/
