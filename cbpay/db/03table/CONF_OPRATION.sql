-- Create table
create table CONF_OPRATION
(
  opr_code        VARCHAR2(50) not null,
  serverid        VARCHAR2(50),
  servercode      VARCHAR2(50) not null,
  server_diy      VARCHAR2(100),
  server_overtime VARCHAR2(10) not null,
  trancode        VARCHAR2(20) not null
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
comment on column CONF_OPRATION.opr_code
  is '业务码';
comment on column CONF_OPRATION.serverid
  is '服务id';
comment on column CONF_OPRATION.servercode
  is '服务交易码';
comment on column CONF_OPRATION.server_diy
  is '交易特殊处理';
comment on column CONF_OPRATION.server_overtime
  is '服务超时时间';
comment on column CONF_OPRATION.trancode
  is '交易来源';
