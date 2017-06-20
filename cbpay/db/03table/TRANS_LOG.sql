--trans_log  建表语句
begin
	execute immediate'
	create table TRANS_LOG
	(
	  flowno              VARCHAR2(50) not null,
	  trandate            VARCHAR2(8) not null,
	  trantime            VARCHAR2(8) not null,
	  trancode            VARCHAR2(6) not null,
	  tranfrom            VARCHAR2(15) not null,
	  servercode          VARCHAR2(10) not null,
	  serverid            VARCHAR2(12) not null,
	  serviceresponsecode VARCHAR2(15),
	  serviceresponsedesc VARCHAR2(250),
	  platresponsecode    VARCHAR2(50)
	)
	tablespace CBPAY_JOURNAL
	PARTITION BY RANGE (trandate)
	(
		PARTITION TRANS_LOG_'||to_char(sysdate+1*40,'YYMMDD')||' VALUES LESS THAN ('||to_char(sysdate+1*40,'YYYYMMDD')||') tablespace CBPAY_JOURNAL,
		PARTITION TRANS_LOG_'||to_char(sysdate+2*40,'YYMMDD')||' VALUES LESS THAN ('||to_char(sysdate+2*40,'YYYYMMDD')||') tablespace CBPAY_JOURNAL,
		PARTITION TRANS_LOG_'||to_char(sysdate+3*40,'YYMMDD')||' VALUES LESS THAN ('||to_char(sysdate+3*40,'YYYYMMDD')||') tablespace CBPAY_JOURNAL,
		PARTITION TRANS_LOG_'||to_char(sysdate+4*40,'YYMMDD')||' VALUES LESS THAN ('||to_char(sysdate+4*40,'YYYYMMDD')||') tablespace CBPAY_JOURNAL,
		PARTITION TRANS_LOG_'||to_char(sysdate+5*40,'YYMMDD')||' VALUES LESS THAN ('||to_char(sysdate+5*40,'YYYYMMDD')||') tablespace CBPAY_JOURNAL,
		PARTITION TRANS_LOG_'||to_char(sysdate+6*40,'YYMMDD')||' VALUES LESS THAN ('||to_char(sysdate+6*40,'YYYYMMDD')||') tablespace CBPAY_JOURNAL,
		PARTITION TRANS_LOG_'||to_char(sysdate+7*40,'YYMMDD')||' VALUES LESS THAN ('||to_char(sysdate+7*40,'YYYYMMDD')||') tablespace CBPAY_JOURNAL,
		PARTITION TRANS_LOG_'||to_char(sysdate+8*40,'YYMMDD')||' VALUES LESS THAN ('||to_char(sysdate+8*40,'YYYYMMDD')||') tablespace CBPAY_JOURNAL,
		PARTITION TRANS_LOG_'||to_char(sysdate+9*40,'YYMMDD')||' VALUES LESS THAN ('||to_char(sysdate+9*40,'YYYYMMDD')||') tablespace CBPAY_JOURNAL,
		PARTITION TRANS_LOG_'||to_char(sysdate+10*40,'YYMMDD')||' VALUES LESS THAN ('||to_char(sysdate+10*40,'YYYYMMDD')||') tablespace CBPAY_JOURNAL,
		PARTITION TRANS_LOG_MAX VALUES LESS THAN (MAXVALUE) tablespace CBPAY_JOURNAL
	)';
	execute immediate 'comment on table TRANS_LOG is ''平台日志表''';
	execute immediate 'comment on column TRANS_LOG.flowno is ''平台流水号''';
	execute immediate 'comment on column TRANS_LOG.trandate is ''平台日期''';
	execute immediate 'comment on column TRANS_LOG.trantime is ''平台时间''';
	execute immediate 'comment on column TRANS_LOG.trancode is ''交易发起方交易码''';
	execute immediate 'comment on column TRANS_LOG.tranfrom is ''交易发起方ID''';
	execute immediate 'comment on column TRANS_LOG.servercode is ''服务方交易码''';
	execute immediate 'comment on column TRANS_LOG.serverid is ''服务方编ID''';
	execute immediate 'comment on column TRANS_LOG.serviceresponsecode is ''服务方响应码''';
	execute immediate 'comment on column TRANS_LOG.serviceresponsedesc is ''服务方响应码描述''';
	execute immediate 'comment on column TRANS_LOG.platresponsecode  is ''平台相应码''';
end;
/
