-- Create table
create table B_MERCHANT_RATE_INFO
(
  id              VARCHAR2(24) not null,
  merchantno      VARCHAR2(30) not null,
  roundingmode    VARCHAR2(1),
  chargemode      VARCHAR2(1),
  overstrategy    VARCHAR2(1),
  chargecycle     VARCHAR2(1),
  beginamt        NUMBER(16,2),
  endamt          NUMBER(16,2),
  trantype        VARCHAR2(10),
  trancode        VARCHAR2(6),
  paychannelcode  VARCHAR2(12),
  bankcardcsttype VARCHAR2(2),
  bankcardtype    VARCHAR2(2),
  bankcode        VARCHAR2(20),
  curtype         VARCHAR2(3),
  chargestatestr  VARCHAR2(300),
  state           VARCHAR2(1),
  weight          INTEGER,
  availbegintime  VARCHAR2(14),
  availendtime    VARCHAR2(14),
  secchargetype   VARCHAR2(1),
  tmsmp           VARCHAR2(14)
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
-- Add comments to the table 
comment on table B_MERCHANT_RATE_INFO
  is '商户费率区间表';
-- Add comments to the columns 
comment on column B_MERCHANT_RATE_INFO.id
  is '收费规则ID ';
comment on column B_MERCHANT_RATE_INFO.merchantno
  is '商户号 ';
comment on column B_MERCHANT_RATE_INFO.roundingmode
  is '舍入规则 ';
comment on column B_MERCHANT_RATE_INFO.chargemode
  is '收费模式 ';
comment on column B_MERCHANT_RATE_INFO.overstrategy
  is '超出阶梯后收费办法';
comment on column B_MERCHANT_RATE_INFO.chargecycle
  is '收费周期 ';
comment on column B_MERCHANT_RATE_INFO.beginamt
  is '保底金额 ';
comment on column B_MERCHANT_RATE_INFO.endamt
  is '封顶金额 ';
comment on column B_MERCHANT_RATE_INFO.trantype
  is '交易类型 ';
comment on column B_MERCHANT_RATE_INFO.trancode
  is '交易码 ';
comment on column B_MERCHANT_RATE_INFO.paychannelcode
  is '支付渠道编号 ';
comment on column B_MERCHANT_RATE_INFO.bankcardcsttype
  is '银行客户类型 ';
comment on column B_MERCHANT_RATE_INFO.bankcardtype
  is '银行账户类型 ';
comment on column B_MERCHANT_RATE_INFO.bankcode
  is '银行编码 ';
comment on column B_MERCHANT_RATE_INFO.curtype
  is '币种 ';
comment on column B_MERCHANT_RATE_INFO.chargestatestr
  is '收费状态 ';
comment on column B_MERCHANT_RATE_INFO.state
  is '规则状态 ';
comment on column B_MERCHANT_RATE_INFO.weight
  is '权重 ';
comment on column B_MERCHANT_RATE_INFO.availbegintime
  is '生效开始时间 ';
comment on column B_MERCHANT_RATE_INFO.availendtime
  is '生效结束时间 ';
comment on column B_MERCHANT_RATE_INFO.secchargetype
  is '区间收费类型 ';
comment on column B_MERCHANT_RATE_INFO.tmsmp
  is '时间戳';
-- Create/Recreate primary, unique and foreign key constraints 
alter table B_MERCHANT_RATE_INFO
  add constraint B_MERCHANT_RATE_PK primary key (MERCHANTNO, ID)
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
