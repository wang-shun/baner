-- Create table
create table T_TRADE_PARAM
(
  param_id    VARCHAR2(32) not null,
  param_type  VARCHAR2(32),
  param_value VARCHAR2(200),
  dsc         VARCHAR2(100),
  crt_time    VARCHAR2(14),
  field1      VARCHAR2(100),
  field2      VARCHAR2(200),
  field3      VARCHAR2(400)
)
tablespace CBPAY_CONSOLE
  pctfree 10
  initrans 1
  maxtrans 255;
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_TRADE_PARAM
  add primary key (PARAM_ID)
  using index 
  tablespace CBPAY_CONSOLE
  pctfree 10
  initrans 2
  maxtrans 255;
