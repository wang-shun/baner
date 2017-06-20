-- Create table
create table PROTOCOL_TYPE
(
  protocoltype    VARCHAR2(50) not null,
  protocolconfig  VARCHAR2(150) not null,
  protocolparse   VARCHAR2(150) not null,
  protocolprocess VARCHAR2(150) not null
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
comment on column PROTOCOL_TYPE.protocoltype
  is '协议类型';
comment on column PROTOCOL_TYPE.protocolconfig
  is '协议配置类';
comment on column PROTOCOL_TYPE.protocolparse
  is '协议配置解析类';
comment on column PROTOCOL_TYPE.protocolprocess
  is '协议的实现';
-- Create/Recreate primary, unique and foreign key constraints 
alter table PROTOCOL_TYPE
  add constraint PROTOCOL_TYPE_PK primary key (PROTOCOLTYPE)
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
