-- Create table
create table PROERRLOG
(
  platdate   VARCHAR2(8),
  proname    VARCHAR2(50),
  errorcode  VARCHAR2(50),
  errormsg   VARCHAR2(500),
  stacktrace VARCHAR2(2000)
)
tablespace CBPAY_JOURNAL
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
