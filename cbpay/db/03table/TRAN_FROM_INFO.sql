-- Create table
create table TRAN_FROM_INFO
(
  from_type           VARCHAR2(2) not null,
  from_id             VARCHAR2(50) not null,
  from_desc           VARCHAR2(50) not null,
  isencrypt           VARCHAR2(2),
  encrypt_algorithm   VARCHAR2(20),
  issignature         VARCHAR2(2),
  signature_algorithm VARCHAR2(20),
  public_key_file     VARCHAR2(100),
  key_file_type       VARCHAR2(2),
  encrypt_type        VARCHAR2(2),
  signature_type      VARCHAR2(2),
  chanel_code         VARCHAR2(20),
  chanel_des          VARCHAR2(200),
  key_store_file      VARCHAR2(100),
  key_store_alias     VARCHAR2(20),
  key_store_password  VARCHAR2(20)
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
comment on column TRAN_FROM_INFO.from_type
  is '来源类型，1:商户   2:银行  3:支付公司  4:内部系统';
comment on column TRAN_FROM_INFO.from_id
  is '交易来源id';
comment on column TRAN_FROM_INFO.from_desc
  is '交易来源方描述';
comment on column TRAN_FROM_INFO.isencrypt
  is '是否加密，1:加密 0:不加密';
comment on column TRAN_FROM_INFO.encrypt_algorithm
  is '加密算法';
comment on column TRAN_FROM_INFO.issignature
  is '是否加签，1:加 0:不加';
comment on column TRAN_FROM_INFO.signature_algorithm
  is '签名算法';
comment on column TRAN_FROM_INFO.key_file_type
  is '公钥类型：1 文件，0 密码串';
comment on column TRAN_FROM_INFO.chanel_code
  is '响应码';
comment on column TRAN_FROM_INFO.chanel_des
  is '响应码描述';
-- Create/Recreate primary, unique and foreign key constraints 
alter table TRAN_FROM_INFO
  add constraint TRAN_FROM_INFO_PK primary key (FROM_TYPE, FROM_ID)
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
