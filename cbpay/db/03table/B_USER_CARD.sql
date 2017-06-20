-- Create table
create table B_USER_CARD
(
  merchantid   VARCHAR2(20),
  purchaserid  VARCHAR2(64),
  cardno       VARCHAR2(32),
  valid        CHAR(1),
  createtime   VARCHAR2(20),
  bankname     VARCHAR2(60),
  openprovince VARCHAR2(20),
  opencity     VARCHAR2(30),
  openname     VARCHAR2(60),
  tmsmp        VARCHAR2(14),
  createdate   VARCHAR2(20)
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
comment on column B_USER_CARD.merchantid
  is '商户号';
comment on column B_USER_CARD.purchaserid
  is '购买者标示';
comment on column B_USER_CARD.cardno
  is '银行卡号';
comment on column B_USER_CARD.valid
  is '有效标示 0 可用，1，不可用';
comment on column B_USER_CARD.createtime
  is '生效时间';
comment on column B_USER_CARD.bankname
  is '银行名称';
comment on column B_USER_CARD.openprovince
  is '开户行所在省';
comment on column B_USER_CARD.opencity
  is '开户行所在市';
comment on column B_USER_CARD.openname
  is '开户行名称';
comment on column B_USER_CARD.tmsmp
  is '时间戳';
comment on column B_USER_CARD.createdate
  is '创建日期';
