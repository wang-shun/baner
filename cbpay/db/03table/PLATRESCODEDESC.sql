-- Create table
create table PLATRESCODEDESC
(
  errorcode VARCHAR2(20),
  errordesc VARCHAR2(200)
)
tablespace CBPAY_CONF
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
