-- Create table
create table ROUT_TYPE
(
  routeres   VARCHAR2(100) not null,
  routetype  VARCHAR2(6) not null,
  routefield VARCHAR2(60),
  tran_opr   VARCHAR2(50) not null
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
comment on column ROUT_TYPE.routeres
  is '路由结果  SERVER: 服务方系统   TRANS:服务系统和交易码    FIELDVAL:某字段的值';
comment on column ROUT_TYPE.routetype
  is '路由类型  FTP: 文件类型  RAT:汇率  MER:商户';
comment on column ROUT_TYPE.routefield
  is '路由字段';
comment on column ROUT_TYPE.tran_opr
  is '业务码';
