-- Create table
create table B_SERVER_PARAM
(
  serverid  VARCHAR2(50) not null,
  paraname  VARCHAR2(50) not null,
  paravalue VARCHAR2(200),
  useflg    CHAR(1),
  paramdesc VARCHAR2(100)
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
comment on column B_SERVER_PARAM.serverid
  is '服务系统id';
comment on column B_SERVER_PARAM.paraname
  is '参数名';
comment on column B_SERVER_PARAM.paravalue
  is '参数值';
comment on column B_SERVER_PARAM.useflg
  is '启用标志 1 启用  0停用';
-- Create/Recreate primary, unique and foreign key constraints 
alter table B_SERVER_PARAM
  add constraint B_SERVER_PARAM_PK primary key (SERVERID, PARANAME)
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
