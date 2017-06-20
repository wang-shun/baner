-- Create table
create table SYSTEM_DICT
(
  key    VARCHAR2(10),
  name   VARCHAR2(20),
  "desc" VARCHAR2(50),
  type   VARCHAR2(2)
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
-- Add comments to the columns 
comment on column SYSTEM_DICT.type
  is '类型 1：币种  2：银行';
