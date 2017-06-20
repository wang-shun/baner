-- Create table
create table TRAN_ROUTER
(
  routekey      VARCHAR2(100) not null,
  type          VARCHAR2(3) not null,
  max_amt       VARCHAR2(50) not null,
  min_amt       VARCHAR2(50),
  currency_type VARCHAR2(10),
  rate_policy   VARCHAR2(20),
  tran_opr      VARCHAR2(50),
  serverid      VARCHAR2(50),
  servercode    VARCHAR2(50),
  retechannelid VARCHAR2(50)
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
comment on column TRAN_ROUTER.routekey
  is '商户ID';
comment on column TRAN_ROUTER.type
  is '路由类型';
comment on column TRAN_ROUTER.max_amt
  is '交易最大金额';
comment on column TRAN_ROUTER.min_amt
  is '交易最小金额';
comment on column TRAN_ROUTER.currency_type
  is '币种';
comment on column TRAN_ROUTER.rate_policy
  is '汇率策略，Latest 最近
Highest 最高
Lowest 最低
';
comment on column TRAN_ROUTER.tran_opr
  is '交易码';
comment on column TRAN_ROUTER.serverid
  is '服务id';
comment on column TRAN_ROUTER.servercode
  is '服务交易码';
comment on column TRAN_ROUTER.retechannelid
  is '汇率渠道id';
-- Create/Recreate primary, unique and foreign key constraints 
alter table TRAN_ROUTER
  add constraint TRAN_ROUTER_PK primary key (ROUTEKEY, TYPE, MAX_AMT)
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
