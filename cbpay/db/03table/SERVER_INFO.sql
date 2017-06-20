-- Create table
create table SERVER_INFO
(
  serverid            VARCHAR2(50) not null,
  server_key          VARCHAR2(5) not null,
  serverdesc          VARCHAR2(100),
  ip                  VARCHAR2(17),
  prot                VARCHAR2(6),
  isencrypt           VARCHAR2(2),
  encrypt_algorithm   VARCHAR2(20),
  public_key_file     VARCHAR2(50),
  issignature         VARCHAR2(2),
  signature_algorithm VARCHAR2(20),
  remittance_fee      VARCHAR2(50),
  res_code            VARCHAR2(20),
  key_store_file      VARCHAR2(100),
  key_store_alias     VARCHAR2(20),
  key_store_password  VARCHAR2(20),
  accountno           VARCHAR2(20),
  res_msg             VARCHAR2(20)
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
comment on column SERVER_INFO.serverid
  is '通道ID';
comment on column SERVER_INFO.server_key
  is '通道简写';
comment on column SERVER_INFO.serverdesc
  is '通道描述';
comment on column SERVER_INFO.ip
  is '通讯地址';
comment on column SERVER_INFO.prot
  is '通讯端口';
comment on column SERVER_INFO.isencrypt
  is '是否加密，1:加密
0:不加密
';
comment on column SERVER_INFO.encrypt_algorithm
  is '加密算法';
comment on column SERVER_INFO.public_key_file
  is '公钥文件';
comment on column SERVER_INFO.issignature
  is '是否签名，1:签名
0:不签名
';
comment on column SERVER_INFO.signature_algorithm
  is '签名算法';
comment on column SERVER_INFO.remittance_fee
  is '汇费';
comment on column SERVER_INFO.res_code
  is '响应码';
comment on column SERVER_INFO.accountno
  is '服务方在宝易互通的虚拟账户';
comment on column SERVER_INFO.res_msg
  is '相应描述';
-- Create/Recreate primary, unique and foreign key constraints 
alter table SERVER_INFO
  add constraint SERVER_INFO_PK primary key (SERVERID)
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
