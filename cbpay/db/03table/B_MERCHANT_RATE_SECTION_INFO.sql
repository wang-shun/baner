-- Create table
create table B_MERCHANT_RATE_SECTION_INFO
(
  merchantno     VARCHAR2(30) not null,
  ruleid         VARCHAR2(24) not null,
  begininput     NUMBER(20),
  endinput       NUMBER(20),
  secchargemode  VARCHAR2(1),
  secchargeamt   NUMBER(14,2),
  secchargeratio INTEGER,
  tmsmp          VARCHAR2(14)
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
-- Add comments to the columns 
comment on column B_MERCHANT_RATE_SECTION_INFO.merchantno
  is '商户id';
comment on column B_MERCHANT_RATE_SECTION_INFO.ruleid
  is '规则ID ';
comment on column B_MERCHANT_RATE_SECTION_INFO.begininput
  is '开始笔数或金额 ';
comment on column B_MERCHANT_RATE_SECTION_INFO.endinput
  is '结束笔数或金额 ';
comment on column B_MERCHANT_RATE_SECTION_INFO.secchargemode
  is '区间收费模式 ';
comment on column B_MERCHANT_RATE_SECTION_INFO.secchargeamt
  is '区间收费金额 ';
comment on column B_MERCHANT_RATE_SECTION_INFO.secchargeratio
  is '区间收费比率 ';
comment on column B_MERCHANT_RATE_SECTION_INFO.tmsmp
  is '时间戳';
-- Create/Recreate primary, unique and foreign key constraints 
alter table B_MERCHANT_RATE_SECTION_INFO
  add constraint B_MERCHANT_RATE_DETAIL_PK primary key (MERCHANTNO, RULEID)
  using index 
  tablespace CBPAY_INDEX
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
