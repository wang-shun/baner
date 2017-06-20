--TRANS_MSG_LOG  建表语句
begin
	execute immediate'
	create table TRANS_MSG_LOG
	(
	  flowno    VARCHAR2(50) not null,
	  trandate char(8) not null,
	  trantime char(6) not null,
	  msg_order VARCHAR2(2) not null,
	  msg       BLOB not null
	)
	lob (msg) store as TRANS_MSG_LOG_MSG
	(
	  tablespace CBPAY_JOURNAL
	  disable storage in row
	  chunk 8192
	  pctversion 10
	  NOCACHE
	)
	tablespace CBPAY_JOURNAL
	PARTITION BY RANGE (trandate)
	(
		PARTITION TRANS_MSG_LOG_'||to_char(sysdate+1*40,'YYMMDD')||' VALUES LESS THAN ('||to_char(sysdate+1*40,'YYYYMMDD')||') tablespace CBPAY_JOURNAL,
		PARTITION TRANS_MSG_LOG_'||to_char(sysdate+2*40,'YYMMDD')||' VALUES LESS THAN ('||to_char(sysdate+2*40,'YYYYMMDD')||') tablespace CBPAY_JOURNAL,
		PARTITION TRANS_MSG_LOG_'||to_char(sysdate+3*40,'YYMMDD')||' VALUES LESS THAN ('||to_char(sysdate+3*40,'YYYYMMDD')||') tablespace CBPAY_JOURNAL,
		PARTITION TRANS_MSG_LOG_'||to_char(sysdate+4*40,'YYMMDD')||' VALUES LESS THAN ('||to_char(sysdate+4*40,'YYYYMMDD')||') tablespace CBPAY_JOURNAL,
		PARTITION TRANS_MSG_LOG_'||to_char(sysdate+5*40,'YYMMDD')||' VALUES LESS THAN ('||to_char(sysdate+5*40,'YYYYMMDD')||') tablespace CBPAY_JOURNAL,
		PARTITION TRANS_MSG_LOG_'||to_char(sysdate+6*40,'YYMMDD')||' VALUES LESS THAN ('||to_char(sysdate+6*40,'YYYYMMDD')||') tablespace CBPAY_JOURNAL,
		PARTITION TRANS_MSG_LOG_'||to_char(sysdate+7*40,'YYMMDD')||' VALUES LESS THAN ('||to_char(sysdate+7*40,'YYYYMMDD')||') tablespace CBPAY_JOURNAL,
		PARTITION TRANS_MSG_LOG_'||to_char(sysdate+8*40,'YYMMDD')||' VALUES LESS THAN ('||to_char(sysdate+8*40,'YYYYMMDD')||') tablespace CBPAY_JOURNAL,
		PARTITION TRANS_MSG_LOG_'||to_char(sysdate+9*40,'YYMMDD')||' VALUES LESS THAN ('||to_char(sysdate+9*40,'YYYYMMDD')||') tablespace CBPAY_JOURNAL,
		PARTITION TRANS_MSG_LOG_'||to_char(sysdate+10*40,'YYMMDD')||' VALUES LESS THAN ('||to_char(sysdate+10*40,'YYYYMMDD')||') tablespace CBPAY_JOURNAL,
		PARTITION TRANS_MSG_LOG_MAX VALUES LESS THAN (MAXVALUE) tablespace CBPAY_JOURNAL
	)';
	execute immediate 'comment on column TRANS_MSG_LOG.flowno is ''平台流水号''';
	execute immediate 'comment on column TRANS_MSG_LOG.trandate is ''平台日期，格式：yyyyMMdd''';
	execute immediate 'comment on column TRANS_MSG_LOG.trantime  is ''平台时间，格式：HHmmss''';
	execute immediate 'comment on column TRANS_MSG_LOG.msg_order is ''消息顺序，1:第一笔报文 2:第二笔报文 3:第三笔报文 4:第四笔报文''';
	execute immediate 'comment on column TRANS_MSG_LOG.msg is ''消息内容''';
end;
/
