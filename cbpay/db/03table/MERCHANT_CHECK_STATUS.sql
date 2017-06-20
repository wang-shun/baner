-- Create table
create table MERCHANT_CHECK_STATUS
(
  merchantid VARCHAR2(12) not null,
  check_date VARCHAR2(8) not null,
  check_time VARCHAR2(6) not null,
  bus_date   VARCHAR2(8) not null,
  status     VARCHAR2(3) not null
)
tablespace CBPAY_JOURNAL
  pctfree 10
  initrans 1
  maxtrans 255;
-- Add comments to the columns 
comment on column MERCHANT_CHECK_STATUS.merchantid
  is '商户id';
comment on column MERCHANT_CHECK_STATUS.check_date
  is '对账日期，yyyyMMdd';
comment on column MERCHANT_CHECK_STATUS.check_time
  is '对账时间，HHmmss';
comment on column MERCHANT_CHECK_STATUS.bus_date
  is '业务日期，yyyyMMdd';
comment on column MERCHANT_CHECK_STATUS.status
  is '对账状态，1:开始对账
2:对账成功
3:对账失败
';
-- Create/Recreate primary, unique and foreign key constraints 
alter table MERCHANT_CHECK_STATUS
  add constraint MERCHANT_CHECK_STATUS_PK primary key (MERCHANTID, CHECK_DATE)
  using index 
  tablespace CBPAY_INDEX
  pctfree 10
  initrans 2
  maxtrans 255;
